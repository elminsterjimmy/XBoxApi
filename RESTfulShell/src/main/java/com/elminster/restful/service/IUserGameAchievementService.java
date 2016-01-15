package com.elminster.restful.service;

import java.util.List;

import com.elminster.retrieve.xbox.data.user.XblUserAchievement;
import com.elminster.retrieve.xbox.exception.ServiceException;

public interface IUserGameAchievementService {

  List<XblUserAchievement> getUserGameTrophyList(String username, String gameId) throws ServiceException;
}
