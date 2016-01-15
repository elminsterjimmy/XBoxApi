package com.elminster.retrieve.xbox.data.user;

import com.elminster.common.util.ObjectUtil;

/**
 * The XBox live user profile.
 * 
 * @author jgu
 * @version 1.0
 */
public class XblUserProfile {
  /** the user id. **/
  private String userId;
  /** the user name. **/
  private String username;
  /** the user's avatar url. **/
  private String userAvatarUrl;
  /** the user total earned points. **/
  private long totalPoint;
  /** the user's reputation. **/
  private String reputation;
  /** the user's location. **/
  private String location;
  /** the user's bio . **/
  private String bio;
  /** the user's recent active. **/
  private String recentActive;
  
  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }
  /**
   * @param userId the userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }
  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }
  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }
  /**
   * @return the userAvatarUrl
   */
  public String getUserAvatarUrl() {
    return userAvatarUrl;
  }
  /**
   * @param userAvatarUrl the userAvatarUrl to set
   */
  public void setUserAvatarUrl(String userAvatarUrl) {
    this.userAvatarUrl = userAvatarUrl;
  }
  /**
   * @return the totalPoint
   */
  public long getTotalPoint() {
    return totalPoint;
  }
  /**
   * @param totalPoint the totalPoint to set
   */
  public void setTotalPoint(long totalPoint) {
    this.totalPoint = totalPoint;
  }
  /**
   * @return the reputation
   */
  public String getReputation() {
    return reputation;
  }
  /**
   * @param reputation the reputation to set
   */
  public void setReputation(String reputation) {
    this.reputation = reputation;
  }
  /**
   * @return the location
   */
  public String getLocation() {
    return location;
  }
  /**
   * @param location the location to set
   */
  public void setLocation(String location) {
    this.location = location;
  }
  /**
   * @return the bio
   */
  public String getBio() {
    return bio;
  }
  /**
   * @param bio the bio to set
   */
  public void setBio(String bio) {
    this.bio = bio;
  }
  /**
   * @return the recentActive
   */
  public String getRecentActive() {
    return recentActive;
  }
  /**
   * @param recentActive the recentActive to set
   */
  public void setRecentActive(String recentActive) {
    this.recentActive = recentActive;
  }
  
  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ObjectUtil.buildToStringByReflect(this);
  }
}
