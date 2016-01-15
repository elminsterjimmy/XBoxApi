package com.elminster.retrieve.xbox.parser;

import java.text.NumberFormat;
import java.util.Locale;

import org.w3c.dom.Document;

import com.elminster.common.parser.ParseException;
import com.elminster.common.util.XMLUtil;
import com.elminster.retrieve.parser.web.HttpContentParser;
import com.elminster.retrieve.xbox.constants.PropertyKey;
import com.elminster.retrieve.xbox.data.user.XblUserProfile;
import com.elminster.retrieve.xbox.util.Configuration;

/**
 * The Xbox live user profile parser.
 * 
 * @author jgu
 * @version 1.0
 */
public class XblUserProfileParser extends HttpContentParser<XblUserProfile> {
  
  /** the configuration. */
  private static final Configuration configuration = Configuration.INSTANCE;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public XblUserProfile parseDoc(Document doc) throws ParseException {
    XblUserProfile profile = null;
    try {
      profile = new XblUserProfile();
      profile.setBio(XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.USER_PROFILE_BIO), doc));
      profile.setLocation(XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.USER_PROFILE_LOCATION), doc));
      profile.setRecentActive(XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.USER_PROFILE_RECENT_ACTIVE), doc));
      String totalPoint = XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.USER_PROFILE_GAME_SCORE), doc);
      long points = 0;
      Number number = NumberFormat.getInstance(Locale.US).parse(totalPoint);
      points = number.longValue();
      profile.setTotalPoint(points);
      profile.setReputation(XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.USER_PROFILE_REPUTATION_LEVEL), doc));
      profile.setUserAvatarUrl(XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.USER_PROFILE_GAME_PIC), doc));
      profile.setUsername(XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.USER_PROFILE_GAME_TAG), doc));
      
    } catch (Exception e) {
      // TODO
      throw new ParseException(e);
    }
    return profile;
  }

}
