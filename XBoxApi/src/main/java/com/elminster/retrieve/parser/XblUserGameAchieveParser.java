package com.elminster.retrieve.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.elminster.common.constants.Constants.StringConstants;
import com.elminster.common.util.DateUtil;
import com.elminster.common.util.StringUtil;
import com.elminster.common.util.XMLUtil;
import com.elminster.retrieve.constants.PropertyKey;
import com.elminster.retrieve.data.user.XblUserAchievement;
import com.elminster.retrieve.exception.ParseException;

public class XblUserGameAchieveParser extends BaseParser<List<XblUserAchievement>> {

  @Override
  protected List<XblUserAchievement> parseDoc(Document doc) throws ParseException {
    List<XblUserAchievement> achieveList = null;
    try {
      String gameTitle = XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.ACHIEVE_GAME_TITLE), doc);
      // the collection list
      NodeList achievements = XMLUtil.xpathEvaluateNodeList(
          configuration.getStringProperty(PropertyKey.ACHIEVE_COLLECTION_ITEM), doc);
      if (null != achievements) {
        achieveList = new ArrayList<>();
        int length = achievements.getLength();
        // length == achievement.count
        if (logger.isDebugEnabled()) {
          logger.debug(gameTitle + "'s achievement count=" + length);
        }
        for (int i = 0; i < length; i++) {
          Node achieveNode = achievements.item(i);
          // achievement will with detail link.
          String achieveTitle = XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.ACHIEVE_TITLE1), achieveNode);
          if (StringUtil.isBlank(achieveTitle)) {
            achieveTitle = XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.ACHIEVE_TITLE2), achieveNode);
          }
          String achieveDescription = XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.ACHIEVE_DESCRIPTION), achieveNode);
          
          String achievePoint = XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.ACHIEVE_POINT), achieveNode);
          String achieveUnlockDate = null;
          String achieveImage = null;
          boolean unlocked = null != achievePoint;
          if (unlocked) {
            // unlocked achieve
            achieveUnlockDate = XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.ACHIEVE_UNLOCKED_DATE), achieveNode);
            achieveImage = XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.ACHIEVE_IMAGE), achieveNode);
          }
          
          if (logger.isDebugEnabled()) {
            logger.debug("Achievement for Game:" + gameTitle);
            logger.debug("achieveTitle=" + achieveTitle);
            logger.debug("achieveDescription=" + achieveDescription);
            logger.debug("achieveUnlocked=" + unlocked);
            logger.debug("achievePoint=" + achievePoint);
            logger.debug("achieveUnlockDate=" + achieveUnlockDate);
            logger.debug("achieveImage=" + achieveImage);
          }
          
          XblUserAchievement achieve = new XblUserAchievement();
          achieve.setDescription(tidyUpSpanText(achieveDescription));
          achieve.setTitle(tidyUpSpanText(achieveTitle));
          achieve.setEarned(unlocked);
          
          if (unlocked) {
            Integer point = parseString2Integer(tidyUpSpanText(achievePoint), "Failed to parse the achievement point. ");
            achieve.setPoint(point.shortValue());
            achieve.setImageUrl(achieveImage);
            Date earnedDate = null;
            String tidyedDate = tidyUpSpanText(achieveUnlockDate);
            try {
              earnedDate = DateUtil.parserDateStr(tidyedDate, configuration.getStringProperty(PropertyKey.ACHIEVE_DATE_FORMAT));
            } catch (java.text.ParseException pe) {
              // parse failed. 
              // first parse the string to date. eg: Unlocked 3 minutes ago, Unlocked 3 hours ago, Unlocked 1 day ago ...
              earnedDate = parseRecentlyDate(tidyedDate);
              if (null == earnedDate) {
                // failed to parse recently date, logging error
                logger.warn("fild to parse unlocked date. Caused by: " + pe);
              }
            }
            achieve.setEarnedDate(earnedDate);
          }
          
          achieveList.add(achieve);
        }
      }
    } catch (Exception e) {
      throw new ParseException(e);
    }
    return achieveList;
  }

  // Unlocked 3 minutes ago, Unlocked 3 hours ago, Unlocked 1 day ago ...
  private Date parseRecentlyDate(final String achieveUnlockDate) {
    Date date = null;
    String recentlyStr = achieveUnlockDate;
    recentlyStr = recentlyStr.replace("Unlocked ", "").replace(" ago", "");
    String[] split = recentlyStr.split(StringConstants.SPACE);
    if (2 == split.length) {
      String number = split[0];
      String unit = split[1];
      
      Integer n = Integer.parseInt(number);
      date = DateUtil.getCurrentDate();
      if (unit.startsWith("minute")) {
        date = DateUtil.addMinute(date, -1 * n);
      } else if (unit.startsWith("hour")) {
        date = DateUtil.addHour(date, -1 * n);
      } else if (unit.startsWith("day")) {
        date = DateUtil.addDay(date, -1 * n);
      }
    }
    return date;
  }
}
