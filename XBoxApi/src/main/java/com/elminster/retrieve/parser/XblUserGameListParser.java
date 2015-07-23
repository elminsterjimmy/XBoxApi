package com.elminster.retrieve.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.elminster.common.constants.Constants.StringConstants;
import com.elminster.common.util.XMLUtil;
import com.elminster.retrieve.constants.PropertyKey;
import com.elminster.retrieve.data.game.Platform;
import com.elminster.retrieve.data.user.XblUserGame;
import com.elminster.retrieve.exception.ParseException;

public class XblUserGameListParser extends BaseParser<List<XblUserGame>> {

  @Override
  protected List<XblUserGame> parseDoc(Document doc) throws ParseException {
    List<XblUserGame> gameCollections = null;
    try {
      // the collection list
      NodeList collectionList = XMLUtil.xpathEvaluateNodeList(
          configuration.getStringProperty(PropertyKey.GAME_COLLECTION_LIST), doc);
      if (null != collectionList) {
        gameCollections = new ArrayList<XblUserGame>();
        int length = collectionList.getLength();
        if (logger.isDebugEnabled()) {
          logger.debug("game collection count: " + length);
        }
        for (int i = 0; i < length; i++) {
          if (logger.isDebugEnabled()) {
            logger.debug("loading game collection: " + i);
          }
          XblUserGame game = new XblUserGame();
          Node collection = collectionList.item(i);
          String gameTitle = XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.GAME_TITLE),
              collection);
          String gameUrl = XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.GAME_URL),
              collection);
          String gameImage = XMLUtil.xpathEvaluateString(configuration.getStringProperty(PropertyKey.GAME_IMAGE),
              collection);
          String completionPercentage = XMLUtil.xpathEvaluateString(
              configuration.getStringProperty(PropertyKey.GAME_COMPLETION), collection);
          String pointCompletion = XMLUtil.xpathEvaluateString(
              configuration.getStringProperty(PropertyKey.GAME_POINT_COMPLETION), collection);
          String achieveEarned = XMLUtil.xpathEvaluateString(
              configuration.getStringProperty(PropertyKey.GAME_ACHIEVE_EARNED), collection);
          if (logger.isDebugEnabled()) {
            logger.debug("gameTitle=" + gameTitle);
            logger.debug("gameUrl=" + gameUrl);
            logger.debug("gameImage=" + gameImage);
            logger.debug("completionPercentage=" + completionPercentage);
            logger.debug("pointCompletion=" + pointCompletion);
            logger.debug("achieveEarned=" + achieveEarned);
          }
          // completion
          Integer completion = parseString2Integer(completionPercentage, "Parsing completion failed. ");
          game.setCompletionByPercent(completion.byteValue());

          // earned achievement count
          Integer earnedAchievement = parseString2Integer(tidyUpSpanText(achieveEarned), "Parsing earned achievement failed. ");
          game.setEarnedAchievementCount(earnedAchievement.byteValue());
          
          // only set the achievement count when 100% completion.
          if (100 == completion) {
            game.setAchievementCount(earnedAchievement.byteValue());
          }

          // earned points and total points
          if (null != pointCompletion) {
            String[] split = tidyUpSpanText(pointCompletion).split(StringConstants.SLASH);
            if (2 == split.length) {
              String ep = split[0];
              String tp = split[1];
              Integer earnedPoint = parseString2Integer(ep, "Parsing earned point failed. ");
              game.setEarnedPoint(earnedPoint.shortValue());
              Integer totalPoint = parseString2Integer(tp, "Parsing total point failed. ");
              game.setTotalPoint(totalPoint.shortValue());
            }
          }
          // title
          game.setTitle(gameTitle);
          // image url
          game.setImageUrl(gameUrl);
          // game id
          String gameId = getGameIdFromUrl(gameUrl);
          game.setGameId(gameId);
          // platform
          game.setPlatform(Platform.getPlatformFromString(gameId.substring(0, gameId.lastIndexOf(StringConstants.SLASH))));

          gameCollections.add(game);
        }
      }

    } catch (Exception e) {
      throw new ParseException(e);
    }

    return gameCollections;
  }

  private String getGameIdFromUrl(String gameUrl) {
    int secLastSlashIdx = gameUrl.substring(0, gameUrl.lastIndexOf(StringConstants.SLASH)).lastIndexOf(
        StringConstants.SLASH);
    String s = gameUrl.substring(secLastSlashIdx + 1, gameUrl.lastIndexOf(StringConstants.QUESTION));
    return s;
  }
}
