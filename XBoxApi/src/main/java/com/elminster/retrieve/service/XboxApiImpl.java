package com.elminster.retrieve.service;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.util.ExceptionUtil;
import com.elminster.retrieve.constants.PropertyKey;
import com.elminster.retrieve.data.game.XblAchievement;
import com.elminster.retrieve.data.game.XblGame;
import com.elminster.retrieve.data.user.XblUserAchievement;
import com.elminster.retrieve.data.user.XblUserGame;
import com.elminster.retrieve.data.user.XblUserProfile;
import com.elminster.retrieve.exception.ServiceException;
import com.elminster.retrieve.parser.XblUserGameAchieveParser;
import com.elminster.retrieve.parser.XblUserGameListParser;
import com.elminster.retrieve.parser.XblUserProfileParser;
import com.elminster.retrieve.runnable.BaseRetriever;
import com.elminster.retrieve.util.Configuration;

/**
 * The Xbox API implementation.
 * 
 * @author jgu
 * @version 1.0
 */
public class XboxApiImpl implements IXboxApi {
  
  /** the logger. */
  private static final Log logger = LogFactory.getLog(XboxApiImpl.class);

  /**
   * {@inheritDoc}
   */
  @Override
  public XblUserProfile getXblUserProfile(String xblUsername) throws ServiceException {
    XblUserProfileParser parser = new XblUserProfileParser();
    String userProfileUrl = Configuration.INSTANCE.getStringProperty(PropertyKey.USER_PROFILE_URL);
    String url = MessageFormat.format(userProfileUrl, xblUsername);
    BaseRetriever retriever = new BaseRetriever(url);
    try {
      XblUserProfile result = (XblUserProfile) parser.parse(retriever.retrieve().getBody());
      return result;
    } catch (Exception e) {
      logger.error(ExceptionUtil.getFullStackTrace(e));
      throw new ServiceException("Failed to get user profile for user: [" + xblUsername + "]. Caused by: " + e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<XblUserGame> getXblUserGameList(String xblUsername) throws ServiceException {
    XblUserGameListParser parser = new XblUserGameListParser();
    String userGameListUrl = Configuration.INSTANCE.getStringProperty(PropertyKey.USER_GAME_LIST_URL);
    String url = MessageFormat.format(userGameListUrl, xblUsername);
    BaseRetriever retriever = new BaseRetriever(url);
    try {
      List<XblUserGame> result = parser.parse(retriever.retrieve().getBody());
      return result;
    } catch (Exception e) {
      logger.error(ExceptionUtil.getFullStackTrace(e));
      throw new ServiceException("Failed to get user's game list for user: [" + xblUsername + "]. Caused by: " + e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<XblUserAchievement> getXblUserAchievement(String xblUsername, String xblGameId) throws ServiceException {
    XblUserGameAchieveParser parser = new XblUserGameAchieveParser();
    String userGameAchieveUrl = Configuration.INSTANCE.getStringProperty(PropertyKey.USER_GAME_ACHIEVE_URL);
    String url = MessageFormat.format(userGameAchieveUrl, xblGameId, xblUsername);
    BaseRetriever retriever = new BaseRetriever(url);
    try {
      List<XblUserAchievement> result = parser.parse(retriever.retrieve().getBody());
      return result;
    } catch (Exception e) {
      logger.error(ExceptionUtil.getFullStackTrace(e));
      throw new ServiceException("Failed to get user's game achievement for user: [" + xblUsername + "], gameId: ["
          + xblGameId + "]. Caused by: " + e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<XblAchievement> getXblGameAchievements(String xblGameId) throws ServiceException {
    throw new UnsupportedOperationException("Unsupported yet.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public XblGame getXblGameSummary(String xblGameId) throws ServiceException {
    throw new UnsupportedOperationException("Unsupported yet.");
  }
}
