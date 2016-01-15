package com.elminster.restful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elminster.restful.service.IUserGameAchievementService;
import com.elminster.restful.service.IUserGameService;
import com.elminster.retrieve.xbox.data.user.XblUserAchievement;
import com.elminster.retrieve.xbox.data.user.XblUserGame;
import com.elminster.retrieve.xbox.data.user.XblUserProfile;
import com.elminster.retrieve.xbox.exception.ServiceException;
import com.elminster.retrieve.xbox.service.IXboxApi;
import com.elminster.retrieve.xbox.service.XboxApiImpl;

/**
 * The XBox api RESTful controller.
 * 
 * @author jgu
 * @version 1.0
 */
@Controller
public class XBoxApiController {
  /** the XBox API. */
  private static final IXboxApi API = new XboxApiImpl();
  
  private final IUserGameAchievementService userGameAchievementService;
  private final IUserGameService userGameService;
  
  @Autowired
  public XBoxApiController(IUserGameAchievementService userGameAchievementService, IUserGameService userGameService) {
    this.userGameAchievementService = userGameAchievementService;
    this.userGameService = userGameService;
  }

  @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
  public @ResponseBody XblUserProfile getUserProfile(@PathVariable("username") String username)
      throws ServiceException {
    return API.getXblUserProfile(username);
  }

  @RequestMapping(value = "/user/{username}/gameList", method = RequestMethod.GET)
  public @ResponseBody List<XblUserGame> getUserGameList(
      @PathVariable("username") String username) throws ServiceException {
    return userGameService.getUserGameList(username);
  }
  
  @RequestMapping(value = "/user/{username}/game/{gameId}/achievement", method = RequestMethod.GET)
  public @ResponseBody List<XblUserAchievement> getUserGameTrophyList(
      @PathVariable("username") String username, @PathVariable("gameId") String gameId) throws ServiceException {
    return userGameAchievementService.getUserGameTrophyList(username, gameId);
  }
}
