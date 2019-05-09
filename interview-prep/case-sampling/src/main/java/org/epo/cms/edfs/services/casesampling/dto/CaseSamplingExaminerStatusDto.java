package org.epo.cms.edfs.services.casesampling.dto;

import org.epo.cms.edfs.services.dossierpersistence.entity.Directorate;

/**
 * CaseSamplingExaminerStatusDto class.
 * 
 * @author amigarg
 *
 */

public class CaseSamplingExaminerStatusDto {

  private long caseSamplingExaminerStatusId;

  private String userId;

  private int numPax;

  private int numSubmitted;

  private int numSampled;

  private int year;

  private Directorate directorate;

  public CaseSamplingExaminerStatusDto() {
	    super();
	  }

  public Directorate getDirectorate() {
    return directorate;
  }

  public void setDirectorate(Directorate directorate) {
    this.directorate = directorate;
  }

  public long getCaseSamplingExaminerStatusId() {
    return this.caseSamplingExaminerStatusId;
  }

  public void setCaseSamplingExaminerStatusId(long caseSamplingExaminerStatusId) {
    this.caseSamplingExaminerStatusId = caseSamplingExaminerStatusId;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public int getNumPax() {
    return this.numPax;
  }

  public void setNumPax(int numPax) {
    this.numPax = numPax;
  }

  public int getNumSubmitted() {
    return this.numSubmitted;
  }

  public void setNumSubmitted(int numSubmitted) {
    this.numSubmitted = numSubmitted;
  }

  public int getNumSampled() {
    return this.numSampled;
  }

  public void setNumSampled(int numSampled) {
    this.numSampled = numSampled;
  }

  public int getYear() {
    return this.year;
  }

  public void setYear(int year) {
    this.year = year;
  }

}
