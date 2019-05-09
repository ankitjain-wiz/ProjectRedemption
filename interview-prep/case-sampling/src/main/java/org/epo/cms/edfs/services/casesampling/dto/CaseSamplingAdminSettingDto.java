package org.epo.cms.edfs.services.casesampling.dto;


/**
 * CaseSamplingAdminSettingDto class.
 * 
 * @author amigarg
 *
 */

public class CaseSamplingAdminSettingDto {

  private long caseSamplingAdminSettingId;

  private int numMaxYearTarget;

  private int numMinYearTarget;

  private int numMustSample;

  public CaseSamplingAdminSettingDto() {
	  super();
  }

  public long getCaseSamplingAdminSettingId() {
    return this.caseSamplingAdminSettingId;
  }

  public void setCaseSamplingAdminSettingId(long caseSamplingAdminSettingId) {
    this.caseSamplingAdminSettingId = caseSamplingAdminSettingId;
  }

  public int getNumMaxYearTarget() {
    return this.numMaxYearTarget;
  }

  public void setNumMaxYearTarget(int numMaxYearTarget) {
    this.numMaxYearTarget = numMaxYearTarget;
  }

  public int getNumMinYearTarget() {
    return this.numMinYearTarget;
  }

  public void setNumMinYearTarget(int numMinYearTarget) {
    this.numMinYearTarget = numMinYearTarget;
  }

  public int getNumMustSample() {
    return this.numMustSample;
  }

  public void setNumMustSample(int numMustSample) {
    this.numMustSample = numMustSample;
  }

}
