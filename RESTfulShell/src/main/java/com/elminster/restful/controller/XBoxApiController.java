package com.elminster.restful.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elminster.retrieve.data.user.XblUserAchievement;
import com.elminster.retrieve.data.user.XblUserGame;
import com.elminster.retrieve.data.user.XblUserProfile;
import com.elminster.retrieve.exception.ServiceException;
import com.elminster.retrieve.service.IXboxApi;
import com.elminster.retrieve.service.XboxApiImpl;

/**
 * The PSN api RESTful controller.
 * 
 * @author jgu
 * @version 1.0
 */
@Controller
public class XBoxApiController {
  /** the PSN API. */
  private static final IXboxApi API = new XboxApiImpl();

  @RequestMapping(value = "/userProfile/{username}", method = RequestMethod.GET)
  public @ResponseBody XblUserProfile getUserProfile(@PathVariable("username") String username)
      throws ServiceException {
    return API.getXblUserProfile(username);
  }

  @RequestMapping(value = "/userGameList/{username}", method = RequestMethod.GET)
  public @ResponseBody List<XblUserGame> getUserGameList(
      @PathVariable("username") String username) throws ServiceException {
    return API.getXblUserGameList(username);
  }
  
  @RequestMapping(value = "/userGameTrophyList/{username}/{gameId}", method = RequestMethod.GET)
  public @ResponseBody List<XblUserAchievement> getUserGameTrophyList(
      @PathVariable("username") String username, @PathVariable("gameId") String gameId) throws ServiceException {
    return API.getXblUserAchievement(username, gameId);
  }
}
