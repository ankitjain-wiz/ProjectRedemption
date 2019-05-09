package org.epo.cms.edfs.services.casesampling.dto;

/**
 * CaseSamplingSelectDto class.
 * 
 * @author amigarg
 *
 */
public class CaseSamplingSelectDto {

  private long caseSamplingSelectId;

  private String comment;

  private boolean isSampled;

  private int numPlannedToSample;

  private String userId;

  public CaseSamplingSelectDto() { super();}

  public long getCaseSamplingSelectId() {
    return this.caseSamplingSelectId;
  }

  public void setCaseSamplingSelectId(long caseSamplingSelectId) {
    this.caseSamplingSelectId = caseSamplingSelectId;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public boolean getIsSampled() {
    return this.isSampled;
  }

  public void setIsSampled(boolean isSampled) {
    this.isSampled = isSampled;
  }

  public int getNumPlannedToSample() {
    return this.numPlannedToSample;
  }

  public void setNumPlannedToSample(int numPlannedToSample) {
    this.numPlannedToSample = numPlannedToSample;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
