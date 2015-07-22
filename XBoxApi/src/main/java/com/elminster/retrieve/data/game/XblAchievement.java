package com.elminster.retrieve.data.game;

/**
 * The achievement.
 * 
 * @author jgu
 *
 */
public class XblAchievement {

  private String gameId;
  private String achievementId;
  private String name;
  private String description;
  private short point;
  private String imageUrl;
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
   * @return the achievementId
   */
  public String getAchievementId() {
    return achievementId;
  }
  /**
   * @param achievementId the achievementId to set
   */
  public void setAchievementId(String achievementId) {
    this.achievementId = achievementId;
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
   * @return the description
   */
  public String getDescription() {
    return description;
  }
  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }
  /**
   * @return the point
   */
  public short getPoint() {
    return point;
  }
  /**
   * @param point the point to set
   */
  public void setPoint(short point) {
    this.point = point;
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
}
