package com.elminster.retrieve.service.test;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Test;

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
  
  @AfterClass
  public static void done() throws IOException {
    // persist the api last called time.
    SystemSetting.INSTANCE.persist();
  }
}
