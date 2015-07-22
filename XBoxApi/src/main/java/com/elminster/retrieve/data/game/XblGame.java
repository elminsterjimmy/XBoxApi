package com.elminster.retrieve.data.game;

/**
 * The game.
 * 
 * @author jgu
 * @version 1.0
 */
public class XblGame {

  private String gameId;
  private String name;
  private short totalPoint;
  private byte AchievementCount;
  private String imageUrl;
  private Platform platform;
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
   * @return the name
   */
  public String getName() {
    return name;
  }
  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
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
  public Platform getPlatform() {
    return platform;
  }
  /**
   * @param platform the platform to set
   */
  public void setPlatform(Platform platform) {
    this.platform = platform;
  }
}