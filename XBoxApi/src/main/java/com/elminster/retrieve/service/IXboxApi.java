package com.elminster.retrieve.service;

import java.util.List;

import com.elminster.retrieve.data.game.XblAchievement;
import com.elminster.retrieve.data.game.XblGame;
import com.elminster.retrieve.data.user.XblUserAchievement;
import com.elminster.retrieve.data.user.XblUserGame;
import com.elminster.retrieve.data.user.XblUserProfile;

public interface IXboxApi {

  public XblUserProfile getXblUserProfile(String xblUsername) throws Exception;
  
  public List<XblUserGame> getXblUserGameList(String xblUsername) throws Exception;
  
  public List<XblAchievement> getXblGameAchievements(String xblGameId) throws Exception;
  
  public XblGame getXblGameSummary(String xblGameId) throws Exception;
  
  public List<XblUserAchievement> getXblUserAchievement(String xblUsername, String xblGameId) throws Exception;
}
