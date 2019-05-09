package org.epo.cms.edfs.services.casesampling.pojo;

/**
 * Case Sampling input POJO class.
 * 
 * @author amigarg
 *
 */
public class SamplingInput {
  
  private String userId;
  private String month;
  private int year;
  private int numSubmitted;
  private int numSampled;
  private int numPax;
  private long directorateId;
  private int numMinYearTarget;
  private int numMaxYearTarget;
  private int numMustSample;
  private int numNextToSample;
  
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getMonth() {
    return month;
  }
  public void setMonth(String month) {
    this.month = month;
  }
  public int getYear() {
    return year;
  }
  public void setYear(int year) {
    this.year = year;
  }
  public int getNumSubmitted() {
    return numSubmitted;
  }
  public void setNumSubmitted(int numSubmitted) {
    this.numSubmitted = numSubmitted;
  }
  public int getNumSampled() {
    return numSampled;
  }
  public void setNumSampled(int numSampled) {
    this.numSampled = numSampled;
  }
  public int getNumPax() {
    return numPax;
  }
  public void setNumPax(int numPax) {
    this.numPax = numPax;
  }
  public long getDirectorateId() {
    return directorateId;
  }
  public void setDirectorateId(long directorateId) {
    this.directorateId = directorateId;
  }
  public int getNumMinYearTarget() {
    return numMinYearTarget;
  }
  public void setNumMinYearTarget(int numMinYearTarget) {
    this.numMinYearTarget = numMinYearTarget;
  }
  public int getNumMaxYearTarget() {
    return numMaxYearTarget;
  }
  public void setNumMaxYearTarget(int numMaxYearTarget) {
    this.numMaxYearTarget = numMaxYearTarget;
  }
  public int getNumMustSample() {
    return numMustSample;
  }
  public void setNumMustSample(int numMustSample) {
    this.numMustSample = numMustSample;
  }
  public int getNumNextToSample() {
    return numNextToSample;
  }
  public void setNumNextToSample(int numNextToSample) {
    this.numNextToSample = numNextToSample;
  }


}
