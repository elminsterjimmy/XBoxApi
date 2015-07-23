package com.elminster.retrieve.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

import com.elminster.common.constants.Constants.CharacterConstants;
import com.elminster.common.util.StringUtil;
import com.elminster.retrieve.exception.ParseException;
import com.elminster.retrieve.util.Configuration;

abstract public class BaseParser<T> implements IParser<T> {

  protected static final Log logger = LogFactory.getLog(BaseParser.class);
  protected Configuration configuration = Configuration.INSTANCE;
  
  /**
   * @see com.elminster.retrieve.parser.IParser#parseResponse(java.lang.String)
   */
  @Override
  public T parseResponse(String response) throws ParseException {
    if (logger.isDebugEnabled()) {
      logger.debug(response);
    }
    // fix some illegal syntax
    HtmlCleaner cleaner = new HtmlCleaner();
    TagNode tagNode = cleaner.clean(response);
    Document doc = null;
    try {
      doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
      return parseDoc(doc);
    } catch (Exception e) {
      throw new ParseException(e);
    }
  }

  abstract protected T parseDoc(Document doc) throws ParseException;
  
  protected String tidyUpSpanText(final String spanText) {
    String rtn = spanText;
    if (null != spanText) {
      // trim both
      rtn = StringUtil.chompTrim(spanText);
      // remove first and last " if available
      if (rtn.length() >= 2) {
        char first = rtn.charAt(0);
        char last = rtn.charAt(rtn.length() - 1);
        if (CharacterConstants.DOUBLE_QUOTE == first && CharacterConstants.DOUBLE_QUOTE == last) {
          rtn = StringUtil.chompTrim(rtn.substring(1, rtn.length() - 1));
        }
      }
    }
    return rtn;
  }
  
  protected Integer parseString2Integer(String str, String failMsg) {
    Integer rtn = 0;
    try {
      rtn = Integer.parseInt(str);
    } catch (NumberFormatException nfe) {
      logger.warn(failMsg + "Caused by: " + nfe);
    }
    return rtn;
  }
}
