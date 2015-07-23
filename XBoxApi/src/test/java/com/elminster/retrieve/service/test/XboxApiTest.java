package com.elminster.retrieve.service.test;

import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import com.elminster.retrieve.data.user.XblUserAchievement;
import com.elminster.retrieve.data.user.XblUserGame;
import com.elminster.retrieve.data.user.XblUserProfile;
import com.elminster.retrieve.service.IXboxApi;
import com.elminster.retrieve.service.XboxApiImpl;
import com.elminster.retrieve.util.SystemSetting;

public class XboxApiTest {
  
  IXboxApi api = new XboxApiImpl();

  @Test
  public void testRetrieveUserProfile() throws Exception {
    String xblUsername = "Stallion83";
    XblUserProfile profile = api.getXblUserProfile(xblUsername);
    System.out.println(profile);
  }
  
  @Test
  public void testRetrieveUserGameList() throws Exception {
    String xblUsername = "Stallion83";
    List<XblUserGame> gameList = api.getXblUserGameList(xblUsername);
    System.out.println(gameList);
  }
  
  @Test
  public void testRetrieveUserGameAchieves() throws Exception {
    String[] gameIds = new String[4];
    gameIds[0] = "XboxOne/217831213";
    gameIds[1] = "XboxOne/1090851408";
    gameIds[2] = "Xbox/1480659447";
    gameIds[3] = "Xbox/1313671226";
    String xblUsername = "Stallion83";
    for (String gameId : gameIds) {
      System.out.println("============================================================");
      List<XblUserAchievement> achieves = api.getXblUserAchievement(xblUsername, gameId);
      System.out.println(achieves);
    }
  }
  
  @AfterClass
  public static void done() throws IOException {
    // persist the api last called time.
    SystemSetting.INSTANCE.persist();
  }
}
