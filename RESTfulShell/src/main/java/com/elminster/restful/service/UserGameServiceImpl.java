package com.elminster.restful.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elminster.common.util.CollectionUtil;
import com.elminster.restful.dao.IGameDao;
import com.elminster.restful.dao.IPlatformDao;
import com.elminster.restful.dao.ITrophyDao;
import com.elminster.restful.domain.Game;
import com.elminster.retrieve.xbox.data.game.Platform;
import com.elminster.retrieve.xbox.data.user.XblUserGame;
import com.elminster.retrieve.xbox.exception.ServiceException;
import com.elminster.retrieve.xbox.service.IXboxApi;
import com.elminster.retrieve.xbox.service.XboxApiImpl;

@Service
@Transactional
public class UserGameServiceImpl implements IUserGameService {
  
  /** the XBOX API. */
  private static final IXboxApi API = new XboxApiImpl();
  
  private final ITrophyDao trohpyDao;
  
  private final IGameDao gameDao;
  
  private final IPlatformDao platformDao;
  
  @Autowired
  public UserGameServiceImpl(IGameDao gameDao, ITrophyDao trophyDao, IPlatformDao platformDao) {
    this.gameDao = gameDao;
    this.trohpyDao = trophyDao;
    this.platformDao = platformDao;
  }

  @Override
  public List<XblUserGame> getUserGameList(String username) throws ServiceException {
    // call api
    List<XblUserGame> list = API.getXblUserGameList(username);
    if (CollectionUtil.isNotEmpty(list)) {
      for (XblUserGame ug : list) {
        String gameId = ug.getGameId();
        Game game = gameDao.findByGameId(gameId);
        if (null == game) {
          game = new Game();
        }
        game.setGameId(gameId);
        game.setImageUrl(ug.getImageUrl());
        List<Platform> platforms = ug.getPlatform();
        if (CollectionUtil.isNotEmpty(platforms)) {
          List<com.elminster.restful.domain.Platform> pl = new ArrayList<>();
          for (Platform platform : platforms) {
            String platformName = platform.getName();
            com.elminster.restful.domain.Platform p = platformDao.findByPlatform(platformName);
            if (p == null) {
              p = new com.elminster.restful.domain.Platform();
              p.setPlatform(platformName);
            }
            pl.add(p);
          }
          game.setPlatform(pl);
        }
        game.setTitle(ug.getTitle());
        // update trophies
        updateTrophies(game);
        gameDao.save(game);
      }
    }
    return list;
  }

  private Game updateTrophies(final Game game) {
    // get trophies
    int count = trohpyDao.countByGameId(game.getId());
    if (count > 0) {
      game.setTotalCount(count);
    }
    return game;
  }

}
