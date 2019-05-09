package org.epo.cms.edfs.services.casesampling.dto;

import java.util.List;

/**
 * DirectorateDto class.
 * 
 * @author amigarg
 *
 */

public class DirectorateDto {

  private long directorateId;

  private String directorateName;

  private String directorId;

  private DirectorateWorkloadSettingDto directorateWorkloadSettings;

  private List<CaseSamplingExaminerStatusDto> caseSamplingExaminerStatus;


  public DirectorateDto() { super();}

  public long getDirectorateId() {
    return this.directorateId;
  }

  public void setDirectorateId(long directorateId) {
    this.directorateId = directorateId;
  }

  public String getDirectorateName() {
    return this.directorateName;
  }

  public void setDirectorateName(String directorateName) {
    this.directorateName = directorateName;
  }

  public String getDirectorId() {
    return this.directorId;
  }

  public void setDirectorId(String directorId) {
    this.directorId = directorId;
  }

  public DirectorateWorkloadSettingDto getDirectorateWorkloadSettings() {
    return this.directorateWorkloadSettings;
  }

  public void setDirectorateWorkloadSettings(
		  DirectorateWorkloadSettingDto directorateWorkloadSettings) {
    this.directorateWorkloadSettings = directorateWorkloadSettings;
  }


  public List<CaseSamplingExaminerStatusDto> getCaseSamplingExaminerStatus() {
    return caseSamplingExaminerStatus;
  }

  public void setCaseSamplingExaminerStatus(
      List<CaseSamplingExaminerStatusDto> caseSamplingExaminerStatus) {
    this.caseSamplingExaminerStatus = caseSamplingExaminerStatus;
  }



}
