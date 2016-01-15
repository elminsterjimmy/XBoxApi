package com.elminster.retrieve.xbox.data.user;

import com.elminster.common.util.ObjectUtil;
import com.elminster.retrieve.xbox.data.game.XblGame;

public class XblUserGame extends XblGame {

  private short earnedPoint;
  private byte earnedAchievementCount;
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
  public byte getEarnedAchievementCount() {
    return earnedAchievementCount;
  }
  /**
   * @param earnedAchievement the earnedAchievement to set
   */
  public void setEarnedAchievementCount(byte earnedAchievement) {
    this.earnedAchievementCount = earnedAchievement;
  }
  /**
   * @return the completion
   */
  public byte getCompletionByPercent() {
    return completion;
  }
  /**
   * @param completion the completion to set
   */
  public void setCompletionByPercent(byte completion) {
    this.completion = completion;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return ObjectUtil.buildToStringByReflect(this);
  }
}
