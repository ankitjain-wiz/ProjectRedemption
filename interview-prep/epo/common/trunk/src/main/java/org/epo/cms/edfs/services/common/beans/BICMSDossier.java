package org.epo.cms.edfs.services.common.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BICMSDossier {

  @JsonProperty("ID06_Result")
  private String result;

  @JsonProperty("FilingDate")
  private String filingDate;

  @JsonProperty("ProcedureLabel")
  private String procedureLabel;

  @JsonProperty("Chairman")
  private String chairman;

  @JsonProperty("ProcedureType")
  private String procedureType;

  private String dossierPhase;

  @JsonProperty("ReceivingDate")
  private String receivingDate;

  @JsonProperty("SISUnitCode")
  private String sisUnitCode;

  @JsonProperty("WorkflowStatusCode")
  private String workflowStatusCode;

  @JsonProperty("BlockedForAllocation")
  private String blockedForAllocation;

  @JsonProperty("DoublureIndicator")
  private String doublureIndicator;

  @JsonProperty("SISUnit")
  private String sisUnit;

  @JsonProperty("Director")
  private String director;

  private String targetMonthCalculatedColor;

  @JsonProperty("DirectorateName")
  private String directorateName;

  @JsonProperty("DirectorCode")
  private String directorCode;

  @JsonProperty("TaskStatusLabel")
  private String taskStatusLabel;

  @JsonProperty("Ranking")
  private String ranking;

  @JsonProperty("LeadLimitDateColor")
  private String leadLimitDateColor;

  private String taskStatusCode;

  @JsonProperty("ID06_Explanation")
  private String explanation;

  @JsonProperty("LeadLimitDateType")
  private String leadLimitDateType;

  @JsonProperty("DossierNumber")
  private String dossierNumber;

  @JsonProperty("ApplicationNumber")
  private String applicationNumber;

  @JsonProperty("INSP_MarkedBy")
  private String markedBy;

  @JsonProperty("Proposed1stExaminer")
  private String proposed1stExaminer;

  @JsonProperty("ProceduralLanguage")
  private String proceduralLanguage;

  private String internalStatus;

  @JsonProperty("INSP_MarkedToInspect")
  private String markedToInspect;

  @JsonProperty("LeadLimitDate")
  private String leadLimitDate;

  @JsonProperty("ApplicantName")
  private String applicantName;

  @JsonProperty("FilingLanguage")
  private String filingLanguage;

  @JsonProperty("Examiner2")
  private String examiner2;

  @JsonProperty("Examiner1")
  private String examiner1;

  private String dossierType;

  @JsonProperty("TitleOfInvention")
  private String titleOfInvention;

  @JsonProperty("NumberOfClaims")
  private String numberOfClaims;

  private String dossierPhaseLabel;

  @JsonProperty("INSP_DateMarked")
  private String dateMarked;

  @JsonProperty("LAL_taskId")
  private String lalTaskId;

  private String taskTypeCode;

  @JsonProperty("LeadClass")
  private String leadClass;

  @JsonProperty("Roles")
  private String roles;

  @JsonProperty("Directorate")
  private String directorate;

  @JsonProperty("TargetMonth")
  private String targetMonth;

  @JsonProperty("CaseID")
  private String caseID;

  @JsonProperty("DossierIndicators")
  private String dossierIndicators;

  private String technicalAcceptanceType;

  @JsonProperty("DossierReadByUser")
  private String dossierReadByUser;

  @JsonProperty("Priority")
  private String priority;

  @JsonProperty("WorkflowStatusLabel")
  private String workflowStatusLabel;

  @JsonProperty("WorkflowStatusType")
  private String workflowStatusType;

  private String targetMonthCalculated;

  @JsonProperty("A2LimitDate")
  private String a2LimitDate;

  @JsonProperty("TechnicallyAccepted")
  private String technicallyAccepted;

  private String eDossierInd;

  private String dossierIndicatorType;

  private String dossierIndicatorValue;

  private String dossierIndicatorLabel;

  @JsonProperty("TimeLimitType")
  private String timeLimitType;

  @JsonProperty("TimeLimitStatus")
  private String timeLimitStatus;

  @JsonProperty("StatusDateTime")
  private String statusDateTime;

  @JsonProperty("CreatedDateTime")
  private String createdDateTime;

  @JsonProperty("CreatedDate")
  private String createdDate;

  @JsonProperty("LastUpdatedBy")
  private String lastUpdatedBy;

  @JsonProperty("ExpirationDate")
  private String expirationDate;

  @JsonProperty("SignalDate")
  private String signalDate;

  @JsonProperty("LimitDateType")
  private String limitDateType;

  @JsonProperty("ChangeDateTime")
  private String changeDateTime;

  @JsonProperty("Reason")
  private String reason;

  @JsonProperty("LastChange")
  private String lastChange;

  @JsonProperty("ExpirationDateColor")
  private String expirationDateColor;

  @JsonProperty("TaskProgress")
  private String taskProgress;

  @JsonProperty("TriggeredByEvent")
  private String triggeredByEvent;

  @JsonProperty("TimeLimitParameters")
  private String timeLimitParameters;

  @JsonProperty("CompletedEventLabel")
  private String completedEventLabel;

  @JsonProperty("DateTimeStamp")
  private String dateTimeStamp;

  @JsonProperty("KindOfEventCode")
  private String kindOfEventCode;

  private String dossierInvolvementAccountId;

  private String accountFullname;

  private String dossierInvolvementType;

  private String roleTypeDescription;

  private String dossierInvolvementStartDate;

  private String dossierInvolvementEndDate;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getFilingDate() {
    return filingDate;
  }

  public void setFilingDate(String filingDate) {
    this.filingDate = filingDate;
  }

  public String getProcedureLabel() {
    return procedureLabel;
  }

  public void setProcedureLabel(String procedureLabel) {
    this.procedureLabel = procedureLabel;
  }

  public String getChairman() {
    return chairman;
  }

  public void setChairman(String chairman) {
    this.chairman = chairman;
  }

  public String getProcedureType() {
    return procedureType;
  }

  public void setProcedureType(String procedureType) {
    this.procedureType = procedureType;
  }

  public String getDossierPhase() {
    return dossierPhase;
  }

  public void setDossierPhase(String dossierPhase) {
    this.dossierPhase = dossierPhase;
  }

  public String getReceivingDate() {
    return receivingDate;
  }

  public void setReceivingDate(String receivingDate) {
    this.receivingDate = receivingDate;
  }

  public String getSisUnitCode() {
    return sisUnitCode;
  }

  public void setSisUnitCode(String sisUnitCode) {
    this.sisUnitCode = sisUnitCode;
  }

  public String getWorkflowStatusCode() {
    return workflowStatusCode;
  }

  public void setWorkflowStatusCode(String workflowStatusCode) {
    this.workflowStatusCode = workflowStatusCode;
  }

  public String getBlockedForAllocation() {
    return blockedForAllocation;
  }

  public void setBlockedForAllocation(String blockedForAllocation) {
    this.blockedForAllocation = blockedForAllocation;
  }

  public String getDoublureIndicator() {
    return doublureIndicator;
  }

  public void setDoublureIndicator(String doublureIndicator) {
    this.doublureIndicator = doublureIndicator;
  }

  public String getSisUnit() {
    return sisUnit;
  }

  public void setSisUnit(String sisUnit) {
    this.sisUnit = sisUnit;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public String getTargetMonthCalculatedColor() {
    return targetMonthCalculatedColor;
  }

  public void setTargetMonthCalculatedColor(String targetMonthCalculatedColor) {
    this.targetMonthCalculatedColor = targetMonthCalculatedColor;
  }

  public String getDirectorateName() {
    return directorateName;
  }

  public void setDirectorateName(String directorateName) {
    this.directorateName = directorateName;
  }

  public String getDirectorCode() {
    return directorCode;
  }

  public void setDirectorCode(String directorCode) {
    this.directorCode = directorCode;
  }

  public String getTaskStatusLabel() {
    return taskStatusLabel;
  }

  public void setTaskStatusLabel(String taskStatusLabel) {
    this.taskStatusLabel = taskStatusLabel;
  }

  public String getRanking() {
    return ranking;
  }

  public void setRanking(String ranking) {
    this.ranking = ranking;
  }

  public String getLeadLimitDateColor() {
    return leadLimitDateColor;
  }

  public void setLeadLimitDateColor(String leadLimitDateColor) {
    this.leadLimitDateColor = leadLimitDateColor;
  }

  public String getTaskStatusCode() {
    return taskStatusCode;
  }

  public void setTaskStatusCode(String taskStatusCode) {
    this.taskStatusCode = taskStatusCode;
  }

  public String getExplanation() {
    return explanation;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  public String getLeadLimitDateType() {
    return leadLimitDateType;
  }

  public void setLeadLimitDateType(String leadLimitDateType) {
    this.leadLimitDateType = leadLimitDateType;
  }

  public String getDossierNumber() {
    return dossierNumber;
  }

  public void setDossierNumber(String dossierNumber) {
    this.dossierNumber = dossierNumber;
  }

  public String getApplicationNumber() {
    return applicationNumber;
  }

  public void setApplicationNumber(String applicationNumber) {
    this.applicationNumber = applicationNumber;
  }

  public String getMarkedBy() {
    return markedBy;
  }

  public void setMarkedBy(String markedBy) {
    this.markedBy = markedBy;
  }

  public String getProposed1stExaminer() {
    return proposed1stExaminer;
  }

  public void setProposed1stExaminer(String proposed1stExaminer) {
    this.proposed1stExaminer = proposed1stExaminer;
  }

  public String getProceduralLanguage() {
    return proceduralLanguage;
  }

  public void setProceduralLanguage(String proceduralLanguage) {
    this.proceduralLanguage = proceduralLanguage;
  }

  public String getInternalStatus() {
    return internalStatus;
  }

  public void setInternalStatus(String internalStatus) {
    this.internalStatus = internalStatus;
  }

  public String getMarkedToInspect() {
    return markedToInspect;
  }

  public void setMarkedToInspect(String markedToInspect) {
    this.markedToInspect = markedToInspect;
  }

  public String getLeadLimitDate() {
    return leadLimitDate;
  }

  public void setLeadLimitDate(String leadLimitDate) {
    this.leadLimitDate = leadLimitDate;
  }

  public String getApplicantName() {
    return applicantName;
  }

  public void setApplicantName(String applicantName) {
    this.applicantName = applicantName;
  }

  public String getFilingLanguage() {
    return filingLanguage;
  }

  public void setFilingLanguage(String filingLanguage) {
    this.filingLanguage = filingLanguage;
  }

  public String getExaminer2() {
    return examiner2;
  }

  public void setExaminer2(String examiner2) {
    this.examiner2 = examiner2;
  }

  public String getExaminer1() {
    return examiner1;
  }

  public void setExaminer1(String examiner1) {
    this.examiner1 = examiner1;
  }

  public String getDossierType() {
    return dossierType;
  }

  public void setDossierType(String dossierType) {
    this.dossierType = dossierType;
  }

  public String getTitleOfInvention() {
    return titleOfInvention;
  }

  public void setTitleOfInvention(String titleOfInvention) {
    this.titleOfInvention = titleOfInvention;
  }

  public String getNumberOfClaims() {
    return numberOfClaims;
  }

  public void setNumberOfClaims(String numberOfClaims) {
    this.numberOfClaims = numberOfClaims;
  }

  public String getDossierPhaseLabel() {
    return dossierPhaseLabel;
  }

  public void setDossierPhaseLabel(String dossierPhaseLabel) {
    this.dossierPhaseLabel = dossierPhaseLabel;
  }

  public String getDateMarked() {
    return dateMarked;
  }

  public void setDateMarked(String dateMarked) {
    this.dateMarked = dateMarked;
  }

  public String getLalTaskId() {
    return lalTaskId;
  }

  public void setLalTaskId(String lalTaskId) {
    this.lalTaskId = lalTaskId;
  }

  public String getTaskTypeCode() {
    return taskTypeCode;
  }

  public void setTaskTypeCode(String taskTypeCode) {
    this.taskTypeCode = taskTypeCode;
  }

  public String getLeadClass() {
    return leadClass;
  }

  public void setLeadClass(String leadClass) {
    this.leadClass = leadClass;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public String getDirectorate() {
    return directorate;
  }

  public void setDirectorate(String directorate) {
    this.directorate = directorate;
  }

  public String getTargetMonth() {
    return targetMonth;
  }

  public void setTargetMonth(String targetMonth) {
    this.targetMonth = targetMonth;
  }

  public String getCaseID() {
    return caseID;
  }

  public void setCaseID(String caseID) {
    this.caseID = caseID;
  }

  public String getDossierIndicators() {
    return dossierIndicators;
  }

  public void setDossierIndicators(String dossierIndicators) {
    this.dossierIndicators = dossierIndicators;
  }

  public String getTechnicalAcceptanceType() {
    return technicalAcceptanceType;
  }

  public void setTechnicalAcceptanceType(String technicalAcceptanceType) {
    this.technicalAcceptanceType = technicalAcceptanceType;
  }

  public String getDossierReadByUser() {
    return dossierReadByUser;
  }

  public void setDossierReadByUser(String dossierReadByUser) {
    this.dossierReadByUser = dossierReadByUser;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String getWorkflowStatusLabel() {
    return workflowStatusLabel;
  }

  public void setWorkflowStatusLabel(String workflowStatusLabel) {
    this.workflowStatusLabel = workflowStatusLabel;
  }

  public String getWorkflowStatusType() {
    return workflowStatusType;
  }

  public void setWorkflowStatusType(String workflowStatusType) {
    this.workflowStatusType = workflowStatusType;
  }

  public String getTargetMonthCalculated() {
    return targetMonthCalculated;
  }

  public void setTargetMonthCalculated(String targetMonthCalculated) {
    this.targetMonthCalculated = targetMonthCalculated;
  }

  public String getA2LimitDate() {
    return a2LimitDate;
  }

  public void setA2LimitDate(String a2LimitDate) {
    this.a2LimitDate = a2LimitDate;
  }

  public String getTechnicallyAccepted() {
    return technicallyAccepted;
  }

  public void setTechnicallyAccepted(String technicallyAccepted) {
    this.technicallyAccepted = technicallyAccepted;
  }

  public String geteDossierInd() {
    return eDossierInd;
  }

  public void seteDossierInd(String eDossierInd) {
    this.eDossierInd = eDossierInd;
  }

  public String getDossierIndicatorType() {
    return dossierIndicatorType;
  }

  public void setDossierIndicatorType(String dossierIndicatorType) {
    this.dossierIndicatorType = dossierIndicatorType;
  }

  public String getDossierIndicatorValue() {
    return dossierIndicatorValue;
  }

  public void setDossierIndicatorValue(String dossierIndicatorValue) {
    this.dossierIndicatorValue = dossierIndicatorValue;
  }

  public String getDossierIndicatorLabel() {
    return dossierIndicatorLabel;
  }

  public void setDossierIndicatorLabel(String dossierIndicatorLabel) {
    this.dossierIndicatorLabel = dossierIndicatorLabel;
  }

  public String getTimeLimitType() {
    return timeLimitType;
  }

  public void setTimeLimitType(String timeLimitType) {
    this.timeLimitType = timeLimitType;
  }

  public String getTimeLimitStatus() {
    return timeLimitStatus;
  }

  public void setTimeLimitStatus(String timeLimitStatus) {
    this.timeLimitStatus = timeLimitStatus;
  }

  public String getStatusDateTime() {
    return statusDateTime;
  }

  public void setStatusDateTime(String statusDateTime) {
    this.statusDateTime = statusDateTime;
  }

  public String getCreatedDateTime() {
    return createdDateTime;
  }

  public void setCreatedDateTime(String createdDateTime) {
    this.createdDateTime = createdDateTime;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  public String getSignalDate() {
    return signalDate;
  }

  public void setSignalDate(String signalDate) {
    this.signalDate = signalDate;
  }

  public String getLimitDateType() {
    return limitDateType;
  }

  public void setLimitDateType(String limitDateType) {
    this.limitDateType = limitDateType;
  }

  public String getChangeDateTime() {
    return changeDateTime;
  }

  public void setChangeDateTime(String changeDateTime) {
    this.changeDateTime = changeDateTime;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getLastChange() {
    return lastChange;
  }

  public void setLastChange(String lastChange) {
    this.lastChange = lastChange;
  }

  public String getExpirationDateColor() {
    return expirationDateColor;
  }

  public void setExpirationDateColor(String expirationDateColor) {
    this.expirationDateColor = expirationDateColor;
  }

  public String getTaskProgress() {
    return taskProgress;
  }

  public void setTaskProgress(String taskProgress) {
    this.taskProgress = taskProgress;
  }

  public String getTriggeredByEvent() {
    return triggeredByEvent;
  }

  public void setTriggeredByEvent(String triggeredByEvent) {
    this.triggeredByEvent = triggeredByEvent;
  }

  public String getTimeLimitParameters() {
    return timeLimitParameters;
  }

  public void setTimeLimitParameters(String timeLimitParameters) {
    this.timeLimitParameters = timeLimitParameters;
  }

  public String getCompletedEventLabel() {
    return completedEventLabel;
  }

  public void setCompletedEventLabel(String completedEventLabel) {
    this.completedEventLabel = completedEventLabel;
  }

  public String getDateTimeStamp() {
    return dateTimeStamp;
  }

  public void setDateTimeStamp(String dateTimeStamp) {
    this.dateTimeStamp = dateTimeStamp;
  }

  public String getKindOfEventCode() {
    return kindOfEventCode;
  }

  public void setKindOfEventCode(String kindOfEventCode) {
    this.kindOfEventCode = kindOfEventCode;
  }

  public String getDossierInvolvementAccountId() {
    return dossierInvolvementAccountId;
  }

  public void setDossierInvolvementAccountId(String dossierInvolvementAccountId) {
    this.dossierInvolvementAccountId = dossierInvolvementAccountId;
  }

  public String getAccountFullname() {
    return accountFullname;
  }

  public void setAccountFullname(String accountFullname) {
    this.accountFullname = accountFullname;
  }

  public String getDossierInvolvementType() {
    return dossierInvolvementType;
  }

  public void setDossierInvolvementType(String dossierInvolvementType) {
    this.dossierInvolvementType = dossierInvolvementType;
  }

  public String getRoleTypeDescription() {
    return roleTypeDescription;
  }

  public void setRoleTypeDescription(String roleTypeDescription) {
    this.roleTypeDescription = roleTypeDescription;
  }

  public String getDossierInvolvementStartDate() {
    return dossierInvolvementStartDate;
  }

  public void setDossierInvolvementStartDate(String dossierInvolvementStartDate) {
    this.dossierInvolvementStartDate = dossierInvolvementStartDate;
  }

  public String getDossierInvolvementEndDate() {
    return dossierInvolvementEndDate;
  }

  public void setDossierInvolvementEndDate(String dossierInvolvementEndDate) {
    this.dossierInvolvementEndDate = dossierInvolvementEndDate;
  }

  @Override
  public String toString() {
    return "BICMSDossier [result=" + result + ", filingDate=" + filingDate + ", procedureLabel="
        + procedureLabel + ", chairman=" + chairman + ", procedureType=" + procedureType
        + ", dossierPhase=" + dossierPhase + ", receivingDate=" + receivingDate + ", sisUnitCode="
        + sisUnitCode + ", workflowStatusCode=" + workflowStatusCode + ", blockedForAllocation="
        + blockedForAllocation + ", doublureIndicator=" + doublureIndicator + ", sisUnit=" + sisUnit
        + ", director=" + director + ", targetMonthCalculatedColor=" + targetMonthCalculatedColor
        + ", directorateName=" + directorateName + ", directorCode=" + directorCode
        + ", taskStatusLabel=" + taskStatusLabel + ", ranking=" + ranking + ", leadLimitDateColor="
        + leadLimitDateColor + ", taskStatusCode=" + taskStatusCode + ", explanation=" + explanation
        + ", leadLimitDateType=" + leadLimitDateType + ", dossierNumber=" + dossierNumber
        + ", applicationNumber=" + applicationNumber + ", markedBy=" + markedBy
        + ", proposed1stExaminer=" + proposed1stExaminer + ", proceduralLanguage="
        + proceduralLanguage + ", internalStatus=" + internalStatus + ", markedToInspect="
        + markedToInspect + ", leadLimitDate=" + leadLimitDate + ", applicantName=" + applicantName
        + ", filingLanguage=" + filingLanguage + ", examiner2=" + examiner2 + ", examiner1="
        + examiner1 + ", dossierType=" + dossierType + ", titleOfInvention=" + titleOfInvention
        + ", numberOfClaims=" + numberOfClaims + ", dossierPhaseLabel=" + dossierPhaseLabel
        + ", dateMarked=" + dateMarked + ", lalTaskId=" + lalTaskId + ", taskTypeCode="
        + taskTypeCode + ", leadClass=" + leadClass + ", roles=" + roles + ", directorate="
        + directorate + ", targetMonth=" + targetMonth + ", caseID=" + caseID
        + ", dossierIndicators=" + dossierIndicators + ", technicalAcceptanceType="
        + technicalAcceptanceType + ", dossierReadByUser=" + dossierReadByUser + ", priority="
        + priority + ", workflowStatusLabel=" + workflowStatusLabel + ", workflowStatusType="
        + workflowStatusType + ", targetMonthCalculated=" + targetMonthCalculated + ", a2LimitDate="
        + a2LimitDate + ", technicallyAccepted=" + technicallyAccepted + ", eDossierInd="
        + eDossierInd + ", dossierIndicatorType=" + dossierIndicatorType
        + ", dossierIndicatorValue=" + dossierIndicatorValue + ", dossierIndicatorLabel="
        + dossierIndicatorLabel + ", timeLimitType=" + timeLimitType + ", timeLimitStatus="
        + timeLimitStatus + ", statusDateTime=" + statusDateTime + ", createdDateTime="
        + createdDateTime + ", createdDate=" + createdDate + ", lastUpdatedBy=" + lastUpdatedBy
        + ", expirationDate=" + expirationDate + ", signalDate=" + signalDate + ", limitDateType="
        + limitDateType + ", changeDateTime=" + changeDateTime + ", reason=" + reason
        + ", lastChange=" + lastChange + ", expirationDateColor=" + expirationDateColor
        + ", taskProgress=" + taskProgress + ", triggeredByEvent=" + triggeredByEvent
        + ", timeLimitParameters=" + timeLimitParameters + ", completedEventLabel="
        + completedEventLabel + ", dateTimeStamp=" + dateTimeStamp + ", kindOfEventCode="
        + kindOfEventCode + ", dossierInvolvementAccountId=" + dossierInvolvementAccountId
        + ", accountFullname=" + accountFullname + ", dossierInvolvementType="
        + dossierInvolvementType + ", roleTypeDescription=" + roleTypeDescription
        + ", dossierInvolvementStartDate=" + dossierInvolvementStartDate
        + ", dossierInvolvementEndDate=" + dossierInvolvementEndDate + "]";
  }

}
