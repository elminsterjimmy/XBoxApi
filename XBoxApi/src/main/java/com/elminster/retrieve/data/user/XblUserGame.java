package com.elminster.retrieve.data.user;

import com.elminster.retrieve.data.game.XblGame;

public class XblUserGame extends XblGame {

  private short earnedPoint;
  private byte earnedAchievement;
  private byte completion;
  /**
   * @return the earnedPoint
   */
  public short getEarnedPoint() {
    return earnedPoint;
  }
  /**
   * @param earnedPoint the earnedPoint to set
   */
  public void setEarnedPoint(short earnedPoint) {
    this.earnedPoint = earnedPoint;
  }
  /**
   * @return the earnedAchievement
   */
  public byte getEarnedAchievement() {
    return earnedAchievement;
  }
  /**
   * @param earnedAchievement the earnedAchievement to set
   */
  public void setEarnedAchievement(byte earnedAchievement) {
    this.earnedAchievement = earnedAchievement;
  }
  /**
   * @return the completion
   */
  public byte getCompletion() {
    return completion;
  }
  /**
   * @param completion the completion to set
   */
  public void setCompletion(byte completion) {
    this.completion = completion;
  }
}
