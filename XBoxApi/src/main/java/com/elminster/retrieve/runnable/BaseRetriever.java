package com.elminster.retrieve.runnable;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.constants.Constants.StringConstants;
import com.elminster.common.process.ProcessExecutor;
import com.elminster.common.util.DateUtil;
import com.elminster.common.util.FileUtil;
import com.elminster.common.util.StringUtil;
import com.elminster.retrieve.cookie.PermitAllCookiesSpec;
import com.elminster.retrieve.exception.LoginFailedException;
import com.elminster.retrieve.exception.ParseException;
import com.elminster.retrieve.exception.RetrieveException;
import com.elminster.retrieve.parser.IParser;
import com.elminster.retrieve.util.SystemSetting;

/**
 * The base retriever.
 * 
 * @author jgu
 * @version 1.0
 */
public class BaseRetriever<T> implements Callable<T> {

  /** the logger. */
  private static final Log logger = LogFactory.getLog(BaseRetriever.class);

  /** the system setting. */
  private static SystemSetting systemSetting = SystemSetting.INSTANCE;

  /** the url. */
  private final String url;

  /** the html parser for parsing the response. */
  private final IParser<T> parser;

  /**
   * Constructor.
   * 
   * @param url
   *          the url
   * @param parser
   *          the parser
   */
  public BaseRetriever(String url, IParser<T> parser) {
    this.url = url;
    this.parser = parser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T call() throws Exception {
    try {
      if (!checkCookieValid()) {
        logger.debug("Cookies unavailable. Login into MS live.");
        loginIntoLive();
      }
      HttpClient client = new HttpClient();
      injectLoginCookies(client);
      HttpMethod method = new GetMethod();
      method.setURI(new URI(url, false));
      client.executeMethod(method);
      int status = method.getStatusCode();
      if (200 != status) {
        throw new RetrieveException(url, "Status fails. Status = " + status);
      } else {
        String response = method.getResponseBodyAsString();
        return parser.parseResponse(response);
      }
    } catch (NullPointerException | IOException | URISyntaxException e) {
      throw new RetrieveException(url, e);
    } catch (ParseException e) {
      throw e;
    } catch (LoginFailedException e) {
      throw e;
    }
  }

  /**
   * Check the cookie valid or not.
   * 
   * @return check wether the cookie is valid or not.
   */
  private boolean checkCookieValid() {
    File cookieFile = new File("cookie/jinglei.gu@hotmail.com.txt");
    if (cookieFile.exists()) {
      // TODO need to be checked
      long lastApiCalledTime = systemSetting.getLastApiCalledTime();
      if (System.currentTimeMillis() - lastApiCalledTime > DateUtil.DAY) {
        // expired
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * Inject the login cookies into the http client.
   * 
   * @param client
   *          the http client
   * @throws IOException
   *           on error
   */
  private void injectLoginCookies(HttpClient client) throws IOException {
    Cookie[] cookies = readCookies();
    client.getState().addCookies(cookies);

    CookiePolicy.registerCookieSpec("PermitAllCookiesSpec", PermitAllCookiesSpec.class);
    client.getParams().setCookiePolicy("PermitAllCookiesSpec");

    // update last login time.
    systemSetting.updateLastApiCalledTime();
  }

  /**
   * Login into the MS Live.
   * 
   * @throws IOException
   *           on error
   * @throws URISyntaxException
   *           on error
   * @throws LoginFailedException
   *           when login failed
   */
  private void loginIntoLive() throws IOException, URISyntaxException, LoginFailedException {
    String loginScript = StringConstants.EMPTY_STRING;
    Path path = Paths.get(this.getClass().getClassLoader().getResource("loginLive.js").toURI());
    if (null != path) {
      loginScript = path.toString();
    }
    if (StringUtil.isEmpty(loginScript)) {
      throw new NullPointerException("loginScript is empty.");
    }
    ProcessExecutor pe = new ProcessExecutor("casperjs", loginScript, systemSetting.getMSLiveUsername(),
        systemSetting.getMSLivePassword());
    pe.execute();

    // dump the output
    StringBuilder sb = new StringBuilder();
    String standardOutput = pe.writeStandardOutputToString();
    sb.append("Standard Output:").append(standardOutput).append(StringUtil.newline());
    
    String errorMsg = pe.writeErrorOutputToString();
    sb.append("Error Output:").append(errorMsg);
    
    if (logger.isDebugEnabled()) {
      logger.debug(sb.toString());
    }
    
    if (0 != pe.getExitValue()) {
      throw new LoginFailedException(sb.toString());
    }

  }

  /**
   * Read the cookies from saved cookie file.
   * 
   * @return the cookies
   * @throws IOException
   *           on error
   */
  private Cookie[] readCookies() throws IOException {
    Cookie[] co = null;
    File cookieFile = new File("cookie/" + systemSetting.getMSLiveUsername() + ".txt");
    if (cookieFile.exists()) {
      String cookieInfo = FileUtil.readFile2String(cookieFile.getAbsolutePath());
      if (null != cookieInfo) {
        String[] cookies = cookieInfo.split("&");
        co = new Cookie[cookies.length];
        int i = 0;
        for (String cookie : cookies) {
          String[] split = cookie.split("=");
          String cookieKey = split[0];
          String cookieValue = split[1];
          logger.debug("cookie: " + cookieKey + "=" + cookieValue);
          // update the expiration to 1 day.
          co[i++] = createCookie(".xbox.com", cookieKey, cookieValue, "/", DateUtil.DAY);
        }
      }
    }
    return co;
  }

  /**
   * Create a new cookie with domain, name, value, path and expires.
   * 
   * @param domain
   *          the domain
   * @param name
   *          the name
   * @param value
   *          the value
   * @param path
   *          the path
   * @param expires
   *          the expires
   * @return a new cookie
   */
  private Cookie createCookie(String domain, String name, String value, String path, long expires) {
    Cookie cookie = new Cookie();
    cookie.setDomain(domain);
    cookie.setName(name);
    cookie.setValue(value);
    cookie.setPath(path);
    cookie.setExpiryDate(new Date(System.currentTimeMillis() + expires));
    return cookie;
  }
}
