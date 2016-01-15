package com.elminster.restful.service;

import java.util.List;

import com.elminster.retrieve.xbox.data.user.XblUserGame;
import com.elminster.retrieve.xbox.exception.ServiceException;

public interface IUserGameService {

  public List<XblUserGame> getUserGameList(String username) throws ServiceException;
}
