package com.elminster.retrieve.xbox.data.game;

import java.util.List;

import com.elminster.common.util.ObjectUtil;

/**
 * The game.
 * 
 * @author jgu
 * @version 1.0
 */
public class XblGame {

  private String gameId;
  private String title;
  private short totalPoint;
  private byte AchievementCount;
  private String imageUrl;
  private List<Platform> platform;
  /**
   * @return the gameId
   */
  public String getGameId() {
    return gameId;
  }
  /**
   * @param gameId the gameId to set
   */
  public void setGameId(String gameId) {
    this.gameId = gameId;
  }
  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }
  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }
  /**
   * @return the totalPoint
   */
  public short getTotalPoint() {
    return totalPoint;
  }
  /**
   * @param totalPoint the totalPoint to set
   */
  public void setTotalPoint(short totalPoint) {
    this.totalPoint = totalPoint;
  }
  /**
   * @return the achievementCount
   */
  public byte getAchievementCount() {
    return AchievementCount;
  }
  /**
   * @param achievementCount the achievementCount to set
   */
  public void setAchievementCount(byte achievementCount) {
    AchievementCount = achievementCount;
  }
  /**
   * @return the imageUrl
   */
  public String getImageUrl() {
    return imageUrl;
  }
  /**
   * @param imageUrl the imageUrl to set
   */
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
  /**
   * @return the platform
   */
  public List<Platform> getPlatform() {
    return platform;
  }
  /**
   * @param platform the platform to set
   */
  public void setPlatform(List<Platform> platform) {
    this.platform = platform;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return ObjectUtil.buildToStringByReflect(this);
  }
}
