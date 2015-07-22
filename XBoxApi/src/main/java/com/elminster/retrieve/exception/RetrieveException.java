package com.elminster.retrieve.exception;

import com.elminster.common.util.StringUtil;

public class RetrieveException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -5565295860222806264L;

  public RetrieveException(String url, String message) {
    super(getUrlInfo(url).append(message).toString());
  }
  
  public RetrieveException(String url, Throwable t) {
    super(getUrlInfo(url).toString(), t);
  }
  
  private static StringBuilder getUrlInfo(String url) {
    if (StringUtil.isEmpty(url)) {
      url = "Unknown URL";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("Exception on retrieve URL: [").append(url).append("]. ");
    return sb;
  }
}
