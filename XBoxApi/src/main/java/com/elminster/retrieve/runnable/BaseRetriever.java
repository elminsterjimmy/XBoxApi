package com.elminster.retrieve.runnable;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
  protected Cookie[] readCookies() throws IOException {
    if (!checkCookieValid()) {
      try {
        loginIntoLive();
      } catch (URISyntaxException | LoginFailedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    systemSetting.updateLastApiCalledTime();
    
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

  @Override
  protected void configHttpMethod(HttpMethod httpMethod) throws RetrieveException {
    // TODO Auto-generated method stub
    
  }

}
