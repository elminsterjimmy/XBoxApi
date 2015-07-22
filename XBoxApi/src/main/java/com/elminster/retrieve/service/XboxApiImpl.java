package com.elminster.retrieve.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.elminster.common.pool.ThreadPool;
import com.elminster.retrieve.constants.PropertyKey;
import com.elminster.retrieve.data.game.XblAchievement;
import com.elminster.retrieve.data.game.XblGame;
import com.elminster.retrieve.data.user.XblUserAchievement;
import com.elminster.retrieve.data.user.XblUserGame;
import com.elminster.retrieve.data.user.XblUserProfile;
import com.elminster.retrieve.exception.ServiceException;
import com.elminster.retrieve.parser.IParser;
import com.elminster.retrieve.parser.XblUserGameListParser;
import com.elminster.retrieve.parser.XblUserProfileParser;
import com.elminster.retrieve.runnable.BaseRetriever;
import com.elminster.retrieve.util.Configuration;

public class XboxApiImpl implements IXboxApi {

  @Override
  public XblUserProfile getXblUserProfile(String xblUsername) throws Exception {
    IParser<XblUserProfile> userProfileParser = new XblUserProfileParser();
    String userProfileUrl = Configuration.INSTANCE.getStringProperty(PropertyKey.USER_PROFILE_URL);
    String url = MessageFormat.format(userProfileUrl, xblUsername);
    BaseRetriever<XblUserProfile> retriever = new BaseRetriever<XblUserProfile>(url, userProfileParser);
    try {
      XblUserProfile result = (XblUserProfile) executeRetriever(retriever);
      return result;
    } catch (Exception e) {
      throw new ServiceException("Failed to get user profile for user: [" + xblUsername + "]. Caused by: " + e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<XblUserGame> getXblUserGameList(String xblUsername) throws Exception {
    XblUserGameListParser parser = new XblUserGameListParser();
    String userGameListUrl = Configuration.INSTANCE.getStringProperty(PropertyKey.USER_GAME_LIST_URL);
    String url = MessageFormat.format(userGameListUrl, xblUsername);
    BaseRetriever<List<XblUserGame>> retriever = new BaseRetriever<List<XblUserGame>>(url, parser);
    try {
      List<XblUserGame> result = (List<XblUserGame>) executeRetriever(retriever);
      return result;
    } catch (Exception e) {
      throw new ServiceException("Failed to get user's game list for user: [" + xblUsername + "]. Caused by: " + e);
    }
  }

  @Override
  public List<XblAchievement> getXblGameAchievements(String xblGameId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public XblGame getXblGameSummary(String xblGameId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<XblUserAchievement> getXblUserAchievement(String xblUsername, String xblGameId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
  
  private Object executeRetriever(Callable<?> retriever) throws Exception {
    Future<?> future = ThreadPool.getThreadPool().submit(retriever);
    return future.get();
  }
}
