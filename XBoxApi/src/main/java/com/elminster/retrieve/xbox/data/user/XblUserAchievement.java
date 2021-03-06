package com.elminster.retrieve.xbox.data.user;

import java.util.Date;

import com.elminster.common.util.ObjectUtil;
import com.elminster.retrieve.xbox.data.game.XblAchievement;

public class XblUserAchievement extends XblAchievement {

  private boolean earned;
  private Date earnedDate;
  /**
   * @return the earned
   */
  public boolean isEarned() {
    return earned;
  }
  /**
   * @param earned the earned to set
   */
  public void setEarned(boolean earned) {
    this.earned = earned;
  }
  /**
   * @return the earnedDate
   */
  public Date getEarnedDate() {
    return earnedDate;
  }
  /**
   * @param earnedDate the earnedDate to set
   */
  public void setEarnedDate(Date earnedDate) {
    this.earnedDate = earnedDate;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return ObjectUtil.buildToStringByReflect(this);
  }
}
