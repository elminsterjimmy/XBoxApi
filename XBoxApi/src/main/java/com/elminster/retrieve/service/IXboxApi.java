package com.elminster.retrieve.service;

import java.util.List;

import com.elminster.retrieve.data.game.XblAchievement;
import com.elminster.retrieve.data.game.XblGame;
import com.elminster.retrieve.data.user.XblUserAchievement;
import com.elminster.retrieve.data.user.XblUserGame;
import com.elminster.retrieve.data.user.XblUserProfile;
import com.elminster.retrieve.exception.ServiceException;

/**
 * The XBox API interface.
 * 
 * @author jgu
 * @version 1.0
 */
public interface IXboxApi {

  /**
   * Get the Xbox live user's profile.
   * @param xblUsername the Xbox live username
   * @return the Xbox live user's profile
   * @throws Exception on error
   */
  public XblUserProfile getXblUserProfile(String xblUsername) throws ServiceException;
  
  /**
   * Get the Xbox live user's game list.
   * @param xblUsername the Xbox live username
   * @return the Xbox live user's game list
   * @throws Exception on error
   */
  public List<XblUserGame> getXblUserGameList(String xblUsername) throws ServiceException;
  
  /**
   * Get the Xbox live user's game achievements.
   * @param xblUsername the Xbox live username
   * @param xblGameId the game id
   * @return the Xbox live user's game achievements
   * @throws Exception on error
   */
  public List<XblUserAchievement> getXblUserAchievement(String xblUsername, String xblGameId) throws ServiceException;
  
  // depending on aggregation in background. Need to think what happened if the game id is not being retrieved before.
  /**
   * Get the Xbox game's achievements.
   * @param xblGameId the game id
   * @return the Xbox game's achievements
   * @throws Exception on error
   */
  public List<XblAchievement> getXblGameAchievements(String xblGameId) throws ServiceException;
  
  /**
   * Get the the Xbox game's summary.
   * @param xblGameId the game id
   * @return the Xbox game's summary
   * @throws Exception on error
   */
  public XblGame getXblGameSummary(String xblGameId) throws ServiceException;
  
}
