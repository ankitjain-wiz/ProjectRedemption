package com.wahisplus.in.dao.outbreak;

import com.wahisplus.in.dto.AdministrativeDivisionDto;
import com.wahisplus.in.dto.DiagnosticTestDeleteDto;
import com.wahisplus.in.dto.OutbreakInfoDto;
import com.wahisplus.in.dto.OutbreakLinkTestDto;
import com.wahisplus.in.dto.OutbreakSpeciesDto;
import com.wahisplus.in.dto.ValidationDto;
import com.wahisplus.in.model.OutbreaksMultiFilterModel;
import com.wahisplus.wcommon.dao.GenericDao;
import com.wahisplus.wcommon.domain.event.EpiEvent;
import com.wahisplus.wcommon.domain.laboratories.LabTestSummariesSpecies;
import com.wahisplus.wcommon.domain.laboratories.LabTestsSummaries;
import com.wahisplus.wcommon.domain.laboratories.LaboratoryForTestsSum;
import com.wahisplus.wcommon.domain.outbreak.OutbreakDiagnosticTests;
import com.wahisplus.wcommon.domain.outbreak.Outbreaks;
import com.wahisplus.wcommon.domain.report.ControlMeasures;
import com.wahisplus.wcommon.exception.DataNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * This is dao repositary for report table saving the data in database wherein.
 *
 * @author soprasteria
 * @version 1.0
 *
 */

public interface OutbreakDao extends GenericDao<Outbreaks, Long> {

  /**
   * Gets the outbreak details.
   *
   * @param startDate           the start date
   * @param diseaseId           the disease id
   * @param epiEventId          the epi event id
   * @param diseaseName         the disease name
   * @param outbreakDiseaseName the outbreak disease name
   * @param isevent             the isevent
   * @return the outbreak details
   */
  List<Outbreaks> getOutbreakDetails(@NotNull Date startDate, Integer diseaseId, long epiEventId, String diseaseName,
      String outbreakDiseaseName, Boolean isevent);

  /**
   * Gets the national outbreak reference.
   *
   * @param nationalObRef the national ob ref
   * @param epiEventId    the epi event id
   * @return the national outbreak reference
   */
  List<Outbreaks> getNationalOutbreakReference(String nationalObRef, long epiEventId);

  /**
   * This is an implementation for fetching an outbreak based on given parameters.
   *
   * @param refNumber - the outbreak reference number.
   * @param areaId    - the ID of the area for which outbreak is to be checked.
   * @return Outbreaks - the given outbreak found.
   */
  Outbreaks findOutbreakBasedOnRefNumber(String refNumber, int areaId);

  /**
   * delete OutbreakDetails.
   *
   * 
   * @param outbreakId     - the outBreakId.
   * @param epiEventIdList - the epiEventId .
   * 
   * @return int describing the number of rows updated or deleted.
   */

  ValidationDto deleteOutbreakDetails(List<Long> outbreakId, List<Long> epiEventIdList);

  /**
   * Gets the outbreaks by event id.
   *
   * 
   * @param epiEventIdList the epi event id
   * @param isOpen         the is open
   * @param attName        the att name
   * @param sortOrder      the sort order
   * @param searchText     the search text
   * @return the outbreaks by event id
   * @throws DataNotFoundException
   */
  List<Object[]> getOutbreaksByEventId(Long epiEventId, Boolean isOpen, String attName, String sortOrder,
      String searchText) throws DataNotFoundException;

  /**
   * Close outbreaks.
   *
   * @param outbreakModel the outbreak model
   * @return the int number of entities updated
   */
  int closeOutbreaks(List<Long> outbreakList, Date endDate);

  /**
   * Delete outbreak quantity details.
   *
   * @param specieId the specie id
   * @return the int
   */
  int deleteOutbreakQuantityDetails(Integer specieId);

  /**
   * Gets the outbreak quantity details.
   *
   * @param outbreakId the outbreak id
   * @return the outbreak quantity details
   */
  List<OutbreakSpeciesDto> getOutbreakQuantityDetails(Long outbreakId,  Locale locale);

  /**
   * . Get outbreak for Review
   *
   * @param outbreakId - the id of outbreak
   * @param reportId   - the Id of report
   */
  OutbreakInfoDto getOutbreakForReview(Long outbreakId, Long reportId);

  /**
   * Gets the editoutbreaks.
   *
   * @param epiEventId the epi event id
   * @param outbreakId the outbreak id
   * @param language   the language
   * @param reportType the report type
   * @return the editoutbreaks
   */
  OutbreakInfoDto geteditoutbreaks(Long epiEventId, Long outbreakId, String language, String reportType);

  /**
   * Gets the control measures for outbreak.
   *
   * @param cmId the cm id
   * @return the control measures for outbreak
   */
  List<String> getControlMeasuresForOutbreak(List<Integer> cmId);

  /**
   * Calculate outbreaks for reports.
   *
   * @param reportId the report id
   * @return the boolean
   */
  public List<Long> calculateOutbreaksForReports(long epiEventId);

  /**
   * Gets the outbreak for status.
   *
   * @param status   the status
   * @param epiEvent the epi event
   * @param aquatic  the aquatic
   * @return the outbreak for status
   */
  List<Outbreaks> getOutbreakForStatus(String status, EpiEvent epiEvent, boolean aquatic);

  /**
   * Gets the diagnostic test summary foroutbreaks.
   *
   * @param outbreakId the outbreak id
   * @return the diagnostic test summary foroutbreaks
   */
  List<OutbreakDiagnosticTests> getDiagnosticTestSummaryForoutbreaks();

  /**
   * Gets the date from outbreak.
   *
   * @param dateType   the date type
   * @param epiEventId the epi event id
   * @return the date from outbreak
   */
  Date getDateFromOutbreak(String dateType, Long epiEventId);

  /**
   * Validate outbreak diag for closing.
   *
   * @param outbreakList the outbreak model
   * @return the list
   */
  List<String> validateOutbreakDiagForClosing(List<Long> outbreakList);

  /**
   * Validate outbreak cm for closing.
   *
   * @param outbreakIdList the outbreak id list
   * @param measure        the measure
   * @return the list
   */
  List<Object> validateOutbreakCmForClosing(List<Long> outbreakIdList, String measure);

  /**
   * Delete diagnostic test.
   *
   * @param labTestId the lab test id
   * @param isEvent   the is event
   * @return the validation dto
   */
  ValidationDto deleteDiagnosticTest(Long labTestId, Boolean isEvent);

  /**
   * Delete diagnostic test link.
   *
   * @param obtdId the obtd id
   * @return the diagnostic test delete dto
   */
  DiagnosticTestDeleteDto deleteDiagnosticTestLink(Long obtdId);

  /**
   * Gets the link diagnostic test.
   *
   * @param epiEventId the epi event id
   * @param outbreakId the outbreak id
   * @return the link diagnostic test
   */
  Set<OutbreakLinkTestDto> getLinkDiagnosticTest(@NotNull Long epiEventId, @NotNull @NotNull Long outbreakId);

  /**
   * Gets the area name.
   *
   * @param areaId the area id
   * @return the area name
   */
  String getAreaName(int areaId);

  /**
   * get the outbreak list corresponding their epiEventId.
   * 
   * @param epiEventId as epiEventId.
   * @return return list of outbreaks.
   */
  List<Long> getOutbreakList(Long epiEventId);

  /**
   * get the specie list corresponding their epiEventId.
   * 
   * @param epiEventId as epiEventId.
   * @return return list of Specie.
   */
  List<LabTestsSummaries> getSpecieList(Long epiEventId);

  /**
   * this method is used to find the eligible outbreak for given specie list.
   * 
   * @param specieList as list of specie
   * @return return list of outbreaks which is eligible for given specie list.
   */
  List<Long> getEligibleOutbreaks(List<Integer> specieList);

  List<ControlMeasures> getEventLevelControlMeasures(Long epiEventId);

  List<Object> validateEntityValue(String entityName, String value, String category, String subCategory);

  String findOutbreakForAnimalCategory(long reportId, boolean isAquatic);

  Date getLatestOutbreakEndDate(long epiEventId);

  /**
   * .
   * 
   * @param epiEventId epiEventId
   * @param fieldName  fieldName.
   * @param sortOrder
   * @return List<Object>.
   */

  /**
   * this method is used to get the child outbreak Ids.
   * 
   * @param outbreakListId as outbreak Id.
   * @param epiEventId
   * @return
   */
  List<Long> getChildOutbreakIds(Long outbreakListId, Long epiEventId);

  /**
   * this method is used to get the child event Ids.
   * 
   * @param epiEventId
   * @return
   */
  List<Long> getEpiEventIdList(Long epiEventId);

  /**
   * Gets the result for outbreak link diagnostic.
   *
   * @param outbreakId the outbreak id
   * @return the result for outbreak link diagnostic
   */
  Set<OutbreakDiagnosticTests> getResultForOutbreakLinkDiagnostic(Long outbreakId);

  /**
   * .
   * 
   * @param epiEventId epiEventId.
   * @param fieldName  fieldName.
   * @return list of objects.
   */
  List<Object> getOutbreaksColumnByEventId(long epiEventId, String fieldName);

  /**
   * .
   * 
   * @param inModel inModel.
   * @return list of objects.
   */
  List<Object[]> getFilteredOutbreaks(@Valid OutbreaksMultiFilterModel inModel);

  /**
   * getting epi_event_id of an outbreak
   * 
   * @param outbreakId
   * @return event id
   */
  Long getEventByOutbreak(long outbreakId);

  /**
   * getting list of all lts_id of an outbreak
   * 
   * @param epiEventId
   * @param diagTestId
   * @return list of lts id
   */
  List<Long> getLabTestSummaryIdByEventAndDiagTest(Long epiEventId, Integer diagTestId);

  /**
   * getting list of all lab_id of the lts_ids
   * 
   * @param ltsId
   * @return list of lab_ids
   */
  List<LaboratoryForTestsSum> getLabIdsByLabTestSummaryIds(List<Long> ltsId);

  /**
   * getting specie_id on which test were performed on labs
   * 
   * @param ltsId
   * @return species id
   */
  List<LabTestSummariesSpecies> getSpecieIdsByLabTestSummaryIds(List<Long> ltsId);

  /**
   * .
   * 
   * @param lattitude lattitude.
   * @param longitude longitude.
   * @return List<AdministrativeDivisionDto>.
   */
  List<AdministrativeDivisionDto> getAdminDivision(@NotNull Double lattitude, @NotNull Double longitude);

  /**
   * delete bulk OutbreakDetails in/fur case.
   *
   * 
   * @param outbreakId - the outBreakId.
   * 
   * @return int describing the number of rows updated or deleted.
   */

  ValidationDto deleteBulkOutbreakForInFur(Set<Long> outbreakId);

  /**
   * this method is used to get the epi event Ids list
   * 
   * @param epiEventId
   * @return list of epi event id for all outbreaks present in list
   */
  Set<Long> getEpiEventIdList(List<Long> outbreakId);
  
  /**
   * .
   * @param areaId areaId.
   * @param reportType reportType.
   * @return List<Outbreaks>.
   */
  public List<Outbreaks> validateReasonForOutbreak(@NotNull Integer areaId, Integer reportType);

  /**
   * Gets the fur oubreaks modification.
   *
   * @param epiEventId the epi event id
   * @return the fur oubreaks modification
   */
  List<Long> getFurOubreaksModification(Long epiEventId);


  /**
   * checking existing outbreaks present in OutbreakDiagnosticNature & deleting it
   * 
   * @param outbreakId
   */
  void checkExistingOutbreakEntryAndDelete(Long outbreakId);

  
  /**
   * Gets the history outbreak ids.
   *
   * @param furOutbreakId the fur outbreak id
   * @return the history outbreak ids
   */
  List<Long> gethistoryOutbreakIds(List<Long> furOutbreakId);
  

}
