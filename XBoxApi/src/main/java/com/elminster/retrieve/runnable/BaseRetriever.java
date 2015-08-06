package com.elminster.retrieve.runnable;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.constants.FileExtensionConstants;
import com.elminster.common.constants.Constants.StringConstants;
import com.elminster.common.process.ProcessExecutor;
import com.elminster.common.retrieve.RetrieveException;
import com.elminster.common.util.DateUtil;
import com.elminster.common.util.FileUtil;
import com.elminster.common.util.StringUtil;
import com.elminster.retrieve.exception.LoginFailedException;
import com.elminster.retrieve.util.SystemSetting;
import com.elminster.retrieve.web.CookieInjectRetriever;
import com.elminster.retrieve.web.data.Method;

/**
 * The base retriever.
 * 
 * @author jgu
 * @version 1.0
 */
public class BaseRetriever extends CookieInjectRetriever {

  /** the logger. */
  private static final Log logger = LogFactory.getLog(BaseRetriever.class);

  /** the system setting. */
  private static SystemSetting systemSetting = SystemSetting.INSTANCE;
  
  /** the cookie file. */
  private static final String COOKIE_FILE = "cookie/" + systemSetting.getMSLiveUsername() + FileExtensionConstants.TEXT_EXTENSION;

  /**
   * Constructor.
   * 
   * @param url
   *          the url
   * @param parser
   *          the parser
   */
  public BaseRetriever(String url) {
    super(url, Method.GET_METHOD);
  }

  /**
   * Check the cookie valid or not.
   * 
   * @return check wether the cookie is valid or not.
   */
  private boolean checkCookieValid() {
    File cookieFile = new File(COOKIE_FILE);
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
    
    int exitValue = pe.getExitValue();
    if (logger.isDebugEnabled()) {
      logger.debug("login process returns " + exitValue);
    }
    if (0 != pe.getExitValue()) {
      throw new LoginFailedException("login failed. please check your MS live username and password. login dump: " + sb.toString());
    }

  }

  /**
   * Read the cookies from saved cookie file.
   * 
   * @return the cookies
   * @throws IOException
   *           on error
   */
  protected Cookie[] readCookies() throws Exception {
    if (!checkCookieValid()) {
      try {
        loginIntoLive();
      } catch (URISyntaxException | LoginFailedException e) {
        throw e;
      }
    }
    systemSetting.updateLastApiCalledTime();
    
    Cookie[] co = null;
    File cookieFile = new File(COOKIE_FILE);
    if (cookieFile.exists()) {
      String cookieInfo = FileUtil.readFile2String(cookieFile.getAbsolutePath());
      if (null != cookieInfo) {
        String[] cookies = cookieInfo.split(StringConstants.AND);
        co = new Cookie[cookies.length];
        int i = 0;
        for (String cookie : cookies) {
          String[] split = cookie.split(StringConstants.EQUAL);
          String cookieKey = split[0];
          String cookieValue = split[1];
          if (logger.isDebugEnabled()) {
            logger.debug("cookie: " + cookieKey + "=" + cookieValue);
          }
          // update the expiration to 1 day.
          co[i++] = createCookie(".xbox.com", cookieKey, cookieValue, StringConstants.SLASH, DateUtil.DAY);
        }
      }
    } else {
      throw new IllegalStateException("Cookie file doesn't exist");
    }
    return co;
  }

  @Override
  protected void configHttpMethod(HttpClient client, HttpMethod httpMethod) throws RetrieveException {
  }

}
