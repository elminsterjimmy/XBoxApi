package com.elminster.retrieve.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

import com.elminster.retrieve.data.user.XblUserProfile;
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
    } catch (Exception e) {
      throw new ParseException(e);
    }
    return parseDoc(doc);
  }

  abstract protected T parseDoc(Document doc) throws ParseException;
}
