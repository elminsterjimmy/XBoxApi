package com.elminster.restful.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elminster.common.util.CollectionUtil;
import com.elminster.restful.dao.ITrophyDao;
import com.elminster.restful.domain.Trophy;
import com.elminster.retrieve.xbox.data.user.XblUserAchievement;
import com.elminster.retrieve.xbox.exception.ServiceException;
import com.elminster.retrieve.xbox.service.IXboxApi;
import com.elminster.retrieve.xbox.service.XboxApiImpl;

@Service
@Transactional
public class UserGameAchievementServiceImpl implements IUserGameAchievementService {
  
  /** the XBox API. */
  private static final IXboxApi API = new XboxApiImpl();
  
  private final ITrophyDao trohpyDao;
  
  @Autowired
  public UserGameAchievementServiceImpl(ITrophyDao trophyDao) {
    this.trohpyDao = trophyDao;
  }
  
  public List<XblUserAchievement> getUserGameTrophyList(String username, String gameId) throws ServiceException {
    // call the api
    List<XblUserAchievement> list = API.getXblUserAchievement(username, gameId);
    if (CollectionUtil.isNotEmpty(list)) {
      // insert/update trophy info
      List<Trophy> trophies = trohpyDao.findByGameId(gameId);
      List<Trophy> insert = new ArrayList<Trophy>(list.size());
      if (CollectionUtil.isNotEmpty(trophies)) {
        // exist, update
        int fetchedSize = list.size();
        int cachedSize = trophies.size();
        if (fetchedSize >= cachedSize) {
          for (int i = 0; i < fetchedSize; i++) {
            Trophy entity = trophies.get(i);
            XblUserAchievement ut = list.get(i);
            if ((0 == entity.getStatus()) && ut.isEarned()) {
              entity.setDescription(ut.getDescription());
              entity.setGameId(ut.getGameId());
              entity.setImageUrl(ut.getImageUrl());
              entity.setTrophyOrder(i);
              //entity.setTrophyId(ut.getTrophyId());
              entity.setTitle(ut.getTitle());
              //entity.setType(ut.getType().getType());
              entity.setStatus(ut.isEarned() ? 1 : 0);
              insert.add(entity);
            } else if ((1 == entity.getStatus()) && !ut.isEarned()) {
              // feed back with cached info
              ut.setTitle(entity.getTitle());
              ut.setDescription(entity.getDescription());
              ut.setImageUrl(entity.getImageUrl());
            }
          }
          // add new ones
          for (int i = cachedSize; i < fetchedSize; i++) {
            XblUserAchievement ut = list.get(i);
            Trophy entity = new Trophy();
            entity.setDescription(ut.getDescription());
            entity.setGameId(ut.getGameId());
            entity.setImageUrl(ut.getImageUrl());
//            entity.setTrophyId(ut.getTrophyId());
            entity.setTrophyOrder(i);
            entity.setTitle(ut.getTitle());
//            entity.setType(ut.getType().getType());
            entity.setStatus(ut.isEarned() ? 1 : 0);
            insert.add(entity);
          }
        }
      } else {
        // not exist, insert
        int idx = 0;
        for (XblUserAchievement ut : list) {
          Trophy entity = new Trophy();
          entity.setDescription(ut.getDescription());
          entity.setGameId(ut.getGameId());
          entity.setImageUrl(ut.getImageUrl());
//          entity.setTrophyId(ut.getTrophyId());
          entity.setTrophyOrder(idx++);
          entity.setTitle(ut.getTitle());
//          entity.setType(ut.getType().getType());
          entity.setStatus(ut.isEarned() ? 1 : 0);
          insert.add(entity);
        }
      }
      trohpyDao.save(insert);
    }
    return list;
  }
}
