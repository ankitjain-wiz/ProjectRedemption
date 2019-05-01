package com.wahisplus.in.dao.outbreak;

import com.wahisplus.in.commons.ApplicationConstants;
import com.wahisplus.in.commons.SortingAttribute;
import com.wahisplus.in.dao.initialization.TranslationDaoImpl;
import com.wahisplus.in.dto.AdministrativeDivisionDto;
import com.wahisplus.in.dto.DiagnosticTestDeleteDto;
import com.wahisplus.in.dto.OutbreakDiagnosticTestDto;
import com.wahisplus.in.dto.OutbreakInfoDto;
import com.wahisplus.in.dto.OutbreakLinkTestDto;
import com.wahisplus.in.dto.OutbreakSpeciesDto;
import com.wahisplus.in.dto.ValidationDto;
import com.wahisplus.in.model.OutbreakSpecies;
import com.wahisplus.in.model.OutbreaksMultiFilterModel;
import com.wahisplus.in.util.CommonCodeUtil;
import com.wahisplus.in.utils.SqlConstants;
import com.wahisplus.wcommon.dao.GenericDaoImpl;
import com.wahisplus.wcommon.domain.area.Area;
import com.wahisplus.wcommon.domain.area.AreaComposition;
import com.wahisplus.wcommon.domain.area.AreaCompositionDetails;
import com.wahisplus.wcommon.domain.area.AreaDetails;
import com.wahisplus.wcommon.domain.diseases.Diseases;
import com.wahisplus.wcommon.domain.event.EpiEvent;
import com.wahisplus.wcommon.domain.event.EvtCmSummaries;
import com.wahisplus.wcommon.domain.laboratories.DiagnosticTests;
import com.wahisplus.wcommon.domain.laboratories.LabRoles;
import com.wahisplus.wcommon.domain.laboratories.LabTestSummariesSpecies;
import com.wahisplus.wcommon.domain.laboratories.LabTestsSummaries;
import com.wahisplus.wcommon.domain.laboratories.Laboratories;
import com.wahisplus.wcommon.domain.laboratories.LaboratoryForTestsSum;
import com.wahisplus.wcommon.domain.laboratories.TestsResults;
import com.wahisplus.wcommon.domain.outbreak.ClusterQuantities;
import com.wahisplus.wcommon.domain.outbreak.OutbreakControlMeasures;
import com.wahisplus.wcommon.domain.outbreak.OutbreakDiagnosticNature;
import com.wahisplus.wcommon.domain.outbreak.OutbreakDiagnosticTests;
import com.wahisplus.wcommon.domain.outbreak.OutbreakSpeciesQuantity;
import com.wahisplus.wcommon.domain.outbreak.Outbreaks;
import com.wahisplus.wcommon.domain.outbreak.OutbreaksForEvents;
import com.wahisplus.wcommon.domain.report.ControlMeasures;
import com.wahisplus.wcommon.domain.report.Report;
import com.wahisplus.wcommon.domain.species.SpeciesAndGroups;
import com.wahisplus.wcommon.domain.species.SpeciesHierarchiesTypes;
import com.wahisplus.wcommon.domain.translation.Catalog;
import com.wahisplus.wcommon.exception.DataNotFoundException;
import com.wahisplus.wcommon.util.EntityConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.eclipse.aether.util.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is dao repositary for report table saving the data in database wherein.
 *
 * @author soprasteria
 * @version 1.0
 *
 */

@Repository
@Transactional
public class OutbreakDaoImpl extends GenericDaoImpl<Outbreaks, Long> implements OutbreakDao {

  @Autowired
  private SpeciesAndGroupsDao speciesAndGroupsDao;

  @Autowired
  private TranslationDaoImpl translationDaoImpl;


  /**
   * {@inheritDoc}
   */
  @Override
  public List<Outbreaks> getOutbreakDetails(@NotNull Date startDate, Integer diseaseId, @NotNull long epiEventId,
      String diseaseName, String outbreakDiseaseName, Boolean isevent) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Outbreaks> query = builder.createQuery(Outbreaks.class);
    Root<Outbreaks> outbreakRoot = query.from(Outbreaks.class);
    Root<OutbreaksForEvents> outbreakForEventsRoot = query.from(OutbreaksForEvents.class);
    Root<EpiEvent> epiEventRoot = query.from(EpiEvent.class);
    List<Predicate> criteriaList = new ArrayList<>();
    if (isevent) {
      if (diseaseId != null) {
        Predicate predicate1 = builder.and(
            builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_START_DATE), startDate),
            builder.equal(epiEventRoot.get(EntityConstants.EPI_EVT_DISEASE).get(EntityConstants.EPI_EVT_DISEASE_ID),
                diseaseId),
            builder.equal(epiEventRoot.get(EntityConstants.EPI_EVT_ID),
                outbreakForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT)
                    .get(EntityConstants.EPI_EVT_ID)),
            builder.equal(outbreakForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK)
                .get(EntityConstants.OUTBREAK_ID), outbreakRoot.get(EntityConstants.OUTBREAK_ID)));
        criteriaList.add(predicate1);
      } else {
        Predicate predicate2 = builder.and(
            builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_START_DATE), startDate),
            builder.equal(builder.lower(epiEventRoot.get(EntityConstants.EPI_EVT_DISEASE_REQ)),
                builder.lower(builder.literal(diseaseName))),
            builder.equal(epiEventRoot.get(EntityConstants.EPI_EVT_ID),
                outbreakForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT)
                    .get(EntityConstants.EPI_EVT_ID)),
            builder.equal(outbreakForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK)
                .get(EntityConstants.OUTBREAK_ID), outbreakRoot.get(EntityConstants.OUTBREAK_ID)));
        criteriaList.add(predicate2);
      }
    } else {
      Predicate predicate3 = builder.and(
          builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_START_DATE), startDate),
          builder.equal(epiEventRoot.get(EntityConstants.EPI_EVT_ID),
              outbreakForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT).get(EntityConstants.EPI_EVT_ID)),
          builder.equal(
              outbreakForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID),
              outbreakRoot.get(EntityConstants.OUTBREAK_ID)));
      criteriaList.add(predicate3);
      if (null != diseaseId) {
        Predicate predicate4 = builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_DISEASE_ID), diseaseId);
        criteriaList.add(predicate4);
      } else {
        Predicate predicate4 = builder.equal(builder.lower(outbreakRoot.get(EntityConstants.OUTBREAK_DISEASE_REQ)),
            builder.lower(builder.literal(outbreakDiseaseName)));
        criteriaList.add(predicate4);
      }

    }
    query.select(outbreakRoot).where(builder.and(criteriaList.toArray(new Predicate[0])));
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ValidationDto deleteOutbreakDetails(List<Long> outbreakId, List<Long> epiEventId) {
    ValidationDto validateDto;
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<OutbreaksForEvents> query = builder.createQuery(OutbreaksForEvents.class);
    Root<OutbreaksForEvents> outbreakRoot = query.from(OutbreaksForEvents.class);
    query.select(outbreakRoot.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID)).where(
        outbreakRoot.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID).in(outbreakId))
        .distinct(true);
    Query q = currentSession().createQuery(query);
    List<Integer> distinctOutbreakIds = q.getResultList();
    if (distinctOutbreakIds.size() == outbreakId.size()) {
      CriteriaDelete<OutbreaksForEvents> delete = builder.createCriteriaDelete(OutbreaksForEvents.class);
      Root<OutbreaksForEvents> outbreakRootdelete = delete.from(OutbreaksForEvents.class);
      delete.where(
          builder.and(outbreakRootdelete.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT)
              .get(EntityConstants.EPI_EVT_ID).in(epiEventId)),
          outbreakRootdelete.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID)
              .in(outbreakId));
      int numEntitiesDeleted = currentSession().createQuery(delete).executeUpdate();
      if (numEntitiesDeleted == 0) {
        validateDto = new ValidationDto(false, ApplicationConstants.UNEXPECTED_ERROR);
        return validateDto;
      } else if (numEntitiesDeleted == outbreakId.size()) {
        validateDto = new ValidationDto(true, "deletion is completed");
        return validateDto;

      }
    } else {
      validateDto = new ValidationDto(false, ApplicationConstants.UNEXPECTED_ERROR);
      return validateDto;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int closeOutbreaks(List<Long> outbreakList, Date endDate) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaUpdate<Outbreaks> query = builder.createCriteriaUpdate(Outbreaks.class);
    Root<Outbreaks> outbreakRoot = query.from(Outbreaks.class);
    query.set(EntityConstants.OUTBREAK_END_DATE, endDate);
    query.set(EntityConstants.OUTBREAK_STATUS, "resolved");
    Expression<Long> exp = outbreakRoot.get(EntityConstants.OUTBREAK_ID);
    Predicate predicate = exp.in(outbreakList);
    query.where(predicate);
    return currentSession().createQuery(query).executeUpdate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int deleteOutbreakQuantityDetails(Integer specieId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaDelete<OutbreakSpeciesQuantity> delete = builder.createCriteriaDelete(OutbreakSpeciesQuantity.class);
    Root<OutbreakSpeciesQuantity> outbreakRootdelete = delete.from(OutbreakSpeciesQuantity.class);
    delete.where(builder.equal(outbreakRootdelete.get(EntityConstants.OUTBREAK_SPECIES_QUANTITY_SPECIE)
        .get(EntityConstants.OUTBREAK_SPECIE_QUANTITY_SPECIE_ID), specieId));
    return currentSession().createQuery(delete).executeUpdate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OutbreakSpeciesDto> getOutbreakQuantityDetails(Long outbreakId,  Locale locale) {
    
	List<OutbreakSpeciesDto> outbreaksQuantityList = new ArrayList<>();
	OutbreakSpeciesDto outbreaksQuantityDto = null;
    List<Long> outbreakIds = new ArrayList<>(); 
    outbreakIds.add(outbreakId);
    List<Long> outbreaksList = gethistoryOutbreakIds(outbreakIds);
    outbreaksList.add(outbreakId);
    
    for (Long outId : outbreaksList) {
    OutbreakSpeciesQuantity speceDetails = getOutbreakspecie(outId); 	
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
    Root<OutbreakSpeciesQuantity> outbreakRoot = query.from(OutbreakSpeciesQuantity.class);
    query.multiselect(
    		outbreakRoot.get(EntityConstants.OUTBREAK_SPECIE_QUANTITY_UNIT),
        builder.sumAsLong(outbreakRoot.get(EntityConstants.OUTBREAK_SPECIE_QUANTITY_DEAD)),
        builder.sumAsLong(outbreakRoot.get(EntityConstants.OUTBREAK_SPECIE_QUANTITY_SUSC)),
        builder.sumAsLong(outbreakRoot.get(EntityConstants.OUTBREAK_SPECIE_QUANTITY_KILLED_DISP)),
        builder.sumAsLong(outbreakRoot.get(EntityConstants.OUTBREAK_SPECIE_QUANTITY_SLAUGHT)),
        builder.sumAsLong(outbreakRoot.get(EntityConstants.OUTBREAK_SPECIE_QUANTITY_NCASE)),
        builder.sumAsLong(outbreakRoot.get(EntityConstants.OUTBREAK_SPECIE_QUANTITY_VACC)));
    query.groupBy(outbreakRoot.get(EntityConstants.OUTBREAK_SPECIES_QUANTITY_OUTBREAK).get(EntityConstants.OUTBREAK_ID),
        outbreakRoot.get(EntityConstants.OUTBREAK_SPECIE_QUANTITY_UNIT));
			if (null != speceDetails.getSpecie()) {
				query.where(builder.and(
						builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_SPECIES_QUANTITY_SPECIE).get(EntityConstants.DISEASE_AFFECT_SPECIES_SPECIESID),
								speceDetails.getSpecie().getSpecieId()),
						builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_SPECIES_QUANTITY_OUTBREAK)
								.get(EntityConstants.OUTBREAK_ID), outId)));

			}
			else {
				query.where(
						builder.and(builder.equal(outbreakRoot.get(EntityConstants.SPECIES_NAME_REQ), speceDetails.getSpeciesNameReq()),
								builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_SPECIES_QUANTITY_OUTBREAK)
										.get(EntityConstants.OUTBREAK_ID), outId)));
			}
    List<Object[]> specieObject =  currentSession().createQuery(query).getResultList();
    outbreaksQuantityDto = convertToOutbreaksQuantityDto(specieObject);
    String language = locale.getLanguage();
    setSpeciesName(speceDetails, outbreaksQuantityDto, language);
    outbreaksQuantityList.add(outbreaksQuantityDto);
    
    
    }
    return outbreaksQuantityList;
  }

  /**
   * Gets the outbreakspecie.
   *
   * @param outId the out id
   * @return the outbreakspecie
   */
	private OutbreakSpeciesQuantity getOutbreakspecie(Long outId) {
		CriteriaBuilder builder = currentSession().getCriteriaBuilder();
		CriteriaQuery<OutbreakSpeciesQuantity> query = builder.createQuery(OutbreakSpeciesQuantity.class);
		Root<OutbreakSpeciesQuantity> specieRoot = query.from(OutbreakSpeciesQuantity.class);
		query.select(specieRoot).where(builder.equal(
				specieRoot.get(EntityConstants.OUTBREAK_SPECIES_QUANTITY_OUTBREAK).get(EntityConstants.OUTBREAK_ID),
				outId));
		return currentSession().createQuery(query).getSingleResult();

	}

/**
   * Convert to outbreaks quantity dto.
   *
   * @param object the object
   * @return the outbreaks quantity dto
   */
  private OutbreakSpeciesDto convertToOutbreaksQuantityDto(List<Object[]> object) {

    if (null != object && !object.isEmpty()) {
      return object.stream().map(objects -> {
    	  OutbreakSpeciesDto outbreaksQuantityDto = new OutbreakSpeciesDto();
    	outbreaksQuantityDto.setUnit(objects[0].toString());
    	outbreaksQuantityDto.setTotalDead((Long) objects[1]);
        outbreaksQuantityDto.setTotalSusceptible((Long) objects[2]);
        outbreaksQuantityDto.setTotalKilledDisposed((Long) objects[3]);
        outbreaksQuantityDto.setTotalSlaughtered((Long) objects[4]);
        outbreaksQuantityDto.setTotalNcase((Long) objects[5]);
        outbreaksQuantityDto.setTotalVaccinated((Long) objects[6]);
        
        
        return outbreaksQuantityDto;
      }).collect(Collectors.toList()).get(0);
    }
    return new OutbreakSpeciesDto();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OutbreakInfoDto getOutbreakForReview(Long outbreakId, Long reportId) {

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();

    CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

    Root<Outbreaks> outbreakRoot = query.from(Outbreaks.class);
    Root<OutbreaksForEvents> outbForEvents = query.from(OutbreaksForEvents.class);
    Root<Report> reportRoot = query.from(Report.class);

    query.multiselect(outbreakRoot, reportRoot.get(EntityConstants.REPORT_STATUS));
    query.where(builder.and(builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_ID), outbreakId),
        builder.equal(outbForEvents.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID),
            outbreakRoot.get(EntityConstants.OUTBREAK_ID)),
        builder.equal(
            outbForEvents.get(EntityConstants.OUTBREAK_FOR_EVENT_REPORT).get(EntityConstants.REPORT_REPORT_ID),
            reportRoot.get(EntityConstants.REPORT_REPORT_ID)),
        builder.equal(reportRoot.get(EntityConstants.REPORT_REPORT_ID), reportId)));

    Query q = currentSession().createQuery(query);

    Object[] otbResult = null;
    OutbreakInfoDto result = null;
    try {
      otbResult = (Object[]) q.getSingleResult();
      result = convertToOutbreakReviewDto(otbResult, outbreakId);
    } catch (NoResultException e) {
      // when no outbreak for a report is found
    }

    Outbreaks outbreaks = currentSession().find(Outbreaks.class, outbreakId);
    List<AdministrativeDivisionDto> adminDivisionList = getAdminDivision(outbreaks.getLocationPoint().getCoordinate().x,
        outbreaks.getLocationPoint().getCoordinate().y);
    if (result != null) {
      result.setAdminDivisionList(adminDivisionList);
    }
    return result;
  }

  /**
   * Utility function to convert the outbreak review result into proper format.
   *
   * @param otbData - the query result
   * @return OutbreakReviewDto - the Dto of outbreak review data
   */
  private OutbreakInfoDto convertToOutbreakReviewDto(Object[] otbData, Long outbreakId) {

    OutbreakInfoDto obtReviewDto = new OutbreakInfoDto();
    Outbreaks outbreak = (Outbreaks) otbData[0];

    obtReviewDto.setOutbreakId(outbreak.getOutbreakId());
    obtReviewDto.setOieReference(outbreak.getOieReference());
    obtReviewDto.setOutbreakStatus((String) otbData[1]);
    CommonCodeUtil.setObtReviewDetails(outbreak, obtReviewDto);
    if (null != outbreak.getOutbreakSpeciesQuantity()) {

      obtReviewDto.setSpeciesDetails(setSpeciesDataForObtReview(outbreak));
    }
    if (null != outbreak.getIsAquatic()) {
      if (outbreak.getIsAquatic()) {
        obtReviewDto.setAnimalType(ApplicationConstants.ANIMAL_TYPE_AQUATIC);
      } else {
        obtReviewDto.setAnimalType(ApplicationConstants.ANIMAL_TYPE_TERRESTRIAL);
      }
    }

    // the .get(0) will be removed when there will be many quanities

    if (!outbreak.getClusterQuantities().isEmpty()) {
      obtReviewDto.setClusterQuantity(outbreak.getClusterQuantities().stream().map(ClusterQuantities::getTotalOutbreaks)
          .collect(Collectors.toList()).get(0));
    }

    if (!outbreak.getOutbreakControlMeasures().isEmpty()) {
      obtReviewDto.setControlMeasures(outbreak.getOutbreakControlMeasures().stream()
          .map(x -> x.getControlMeasures().getMeasure()).collect(Collectors.toList()));
    }

    List<String> outbreakDn = getOutbreakDn(outbreak);
    if (!outbreakDn.isEmpty()) {
      obtReviewDto.setDiagSummary(outbreakDn);
    }

    List<OutbreakDiagnosticTestDto> outDiagTestList = new ArrayList<>();
    Set<OutbreakDiagnosticTests> outDiagTests = outbreak.getOutbreakDiagTests();

    if (null != outDiagTests) {
      setDiagnosticTestDetails(outDiagTestList, outDiagTests, getEventByOutbreak(outbreakId), outbreakId);
    }
    Date date = getMinDateOfResultsAtOutbreak(outbreakId);
    if (date != null) {
      obtReviewDto.setDateOfResults(date);
    }
    obtReviewDto.setTestDetailsDtoList(outDiagTestList);

    return obtReviewDto;

  }

  /**
   * setting diagnostic test details
   * 
   * @param listOutDiagTest
   * @param outDiagTests
   * @param epiEventId
   * @param outbreakId
   */
  private void setDiagnosticTestDetails(List<OutbreakDiagnosticTestDto> listOutDiagTest,
      Set<OutbreakDiagnosticTests> outDiagTests, Long epiEventId, Long outbreakId) {

    outDiagTests.stream().forEach(outDiagTest -> {

      Map<Long, String> labNameLtsIdMap = new HashMap<>();
      Map<String, Long> specieNameLtsIdMap = new HashMap<>();
      Map<Long, String> labRoleNameLtsIdMap = new HashMap<>();

      List<Long> ltsIds = getLabTestSummaryIdByEventAndDiagTest(epiEventId, outDiagTest.getDiagTests().getDiagTestId());

      if (!ltsIds.isEmpty()) {
        ltsIds.forEach(ltsId -> {
          LabTestsSummaries labTestsSummaries = currentSession().find(LabTestsSummaries.class, ltsId);
          if (null != labTestsSummaries && null != labTestsSummaries.getLabRoles()) {
            labRoleNameLtsIdMap.put(ltsId, labTestsSummaries.getLabRoles().getLabRole());
          }
        });
        List<LaboratoryForTestsSum> labIdsEntityList = getLabIdsByLabTestSummaryIds(ltsIds);
        List<LabTestSummariesSpecies> speciesIdsEntityList = getSpecieIdsByLabTestSummaryIds(ltsIds);
        speciesIdsEntityList = mappingOutbreakSpecificSpecies(speciesIdsEntityList, outbreakId);
        speciesIdsEntityList.forEach(ltss -> specieNameLtsIdMap.put(ltss.getSpeciesAndGroups().getName(),
            ltss.getLabTestsSummaries().getLtsId()));
        labIdsEntityList.forEach(
            lfts -> labNameLtsIdMap.put(lfts.getLabTestsSummaries().getLtsId(), lfts.getLaboratories().getName()));
        if (null != specieNameLtsIdMap) {
          setDiagnosticTestDetailsForSpecies(listOutDiagTest, outDiagTest, labNameLtsIdMap, specieNameLtsIdMap,
              labRoleNameLtsIdMap);
        }
      }
    });

  }

  /**
   * mapping outbreaks specific species in the list
   * 
   * @param speciesIdsEntityList
   * @param outbreakId
   * @return list having outbreak specific species ids
   */
  private List<LabTestSummariesSpecies> mappingOutbreakSpecificSpecies(
      List<LabTestSummariesSpecies> speciesIdsEntityList, Long outbreakId) {
    Outbreaks outbreak = currentSession().find(Outbreaks.class, outbreakId);
    List<LabTestSummariesSpecies> outbreakSpecificSpecieIds = new ArrayList<>();
    outbreak.getOutbreakSpeciesQuantity().forEach(specie -> speciesIdsEntityList.forEach(ltss -> {
      if (ltss.getSpeciesAndGroups().getSpecieId().equals(specie.getSpecie().getSpecieId())) {
        outbreakSpecificSpecieIds.add(ltss);
      }
    }));
    return outbreakSpecificSpecieIds;
  }

  /**
   * setting diagnostic test details for each species
   * 
   * @param listOutDiagTest
   * @param outDiagTest
   * @param labNameLtsIdMap
   * @param specieNameLtsIdMap
   * @param labRoleNameLtsIdMap
   */
  private void setDiagnosticTestDetailsForSpecies(List<OutbreakDiagnosticTestDto> listOutDiagTest,
      OutbreakDiagnosticTests outDiagTest, Map<Long, String> labNameLtsIdMap, Map<String, Long> specieNameLtsIdMap,
      Map<Long, String> labRoleNameLtsIdMap) {
    specieNameLtsIdMap.forEach((sName, ltsId) -> {
      OutbreakDiagnosticTestDto outbreakTestDto = new OutbreakDiagnosticTestDto();
      if (null != outDiagTest.getDiagTests()) {
        outbreakTestDto.setDiagTestName(outDiagTest.getDiagTests().getName());
      } else {
        outbreakTestDto.setDiagTestName(outDiagTest.getDiagnosticTestReq());
      }
      Optional<TestsResults> testsResults = outDiagTest.getTestsResults().stream().findFirst();
      if (testsResults.isPresent()) {
        outbreakTestDto.setResult(testsResults.get().getResult());
        outbreakTestDto.setResultDate(testsResults.get().getDate());
      }
      outbreakTestDto.setTestType(outDiagTest.getIsField());
      outbreakTestDto.setSpeciesSampled(sName);
      if (null != labNameLtsIdMap) {
        outbreakTestDto.setLabName(labNameLtsIdMap.get(ltsId));
      }
      if (null != labRoleNameLtsIdMap) {
        outbreakTestDto.setLabTypeName(labRoleNameLtsIdMap.get(ltsId));
      }

      listOutDiagTest.add(outbreakTestDto);

    });
  }

  /**
   * getting earliest date of test which are positive result of an outbreak
   * 
   * @param outbreakId
   * @return earliest date
   */
  private Date getMinDateOfResultsAtOutbreak(Long outbreakId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Object> query = builder.createQuery(Object.class);
    Root<TestsResults> testResultRoot = query.from(TestsResults.class);
    Root<OutbreakDiagnosticTests> diagRoot = query.from(OutbreakDiagnosticTests.class);
    query.multiselect(builder.min(testResultRoot.get(EntityConstants.TEST_OB_DATE))).where(
        builder.equal(diagRoot.get(EntityConstants.OUT_DIAG_OUTBREAK_ID).get(EntityConstants.OUTBREAK_ID), outbreakId),
        builder.equal(
            testResultRoot.get(EntityConstants.TEST_OB_DIAG_TEST).get(EntityConstants.OUTBREAK_DIAGNOSTIC_TEST_ID),
            diagRoot.get(EntityConstants.OUTBREAK_DIAGNOSTIC_TEST_ID)),
        builder.equal(testResultRoot.get(EntityConstants.TEST_OB_RESULT), ApplicationConstants.POSITIVE));
    Object date = currentSession().createQuery(query).uniqueResult();
    if (date != null) {
      return (Date) date;
    } else {
      return null;
    }
  }

  /**
   * Utility function to fill Species data for outbreak review.
   *
   * @param outbreak - the outbreak.
   * @return OutbreakSpeciesDto - the list of species data.
   */
  private List<OutbreakSpeciesDto> setSpeciesDataForObtReview(Outbreaks outbreak) {
    List<OutbreakSpeciesDto> speciesDto = new ArrayList<>();
    for (OutbreakSpeciesQuantity species : outbreak.getOutbreakSpeciesQuantity()) {
      OutbreakSpeciesDto tempSpecieDto = new OutbreakSpeciesDto();
      tempSpecieDto.setCases(species.getNcase());
      if (null != species.getSpecie()) {
        tempSpecieDto.setSpicieName(species.getSpecie().getName());
      } else {
        tempSpecieDto.setSpicieNameReq(species.getSpeciesNameReq().split(",")[3]);
        tempSpecieDto.setSpicieName(species.getSpeciesNameReq().split(",")[3]);
      }

      CommonCodeUtil.setCommonSpeciesData(speciesDto, species, tempSpecieDto);

    }
    return speciesDto;
  }

  /**
   * Gets the species data.
   *
   * @param outbreak the outbreak
   * @return the species data
   */
  public List<OutbreakSpeciesDto> getSpeciesData(Outbreaks outbreak, String language) {
	  
    List<OutbreakSpeciesDto> outbreakSpeciesDtoList = new ArrayList<>();
    Long outbreakId = outbreak.getOutbreakId();
    List<Long> outbreakIds = new ArrayList<>();
    outbreakIds.add(outbreakId);
    List<Long> outbreakIdList = gethistoryOutbreakIds(outbreakIds);
    outbreakIdList.addAll(outbreakIds);
    List<OutbreakSpeciesQuantity> outbreakSpeciesDtoList1 = getOutbreakSpeciesQuantityDetails(outbreakIdList);
    for (OutbreakSpeciesQuantity species : outbreakSpeciesDtoList1) {
      OutbreakSpeciesDto outbreakSpecieDto = new OutbreakSpeciesDto();
      outbreakSpecieDto.setCases(species.getNcase());
      setSpeciesName(species, outbreakSpecieDto, language);
      outbreakSpecieDto.setDeaths(species.getDead());
      outbreakSpecieDto.setKilled(species.getKilledDisposed());
      outbreakSpecieDto.setMorbidity(species.getMorbidity());
      outbreakSpecieDto.setMortality(species.getMortality());
      outbreakSpecieDto.setSlaughtered(species.getSlaughtered());
      outbreakSpecieDto.setSusceptible(species.getSusceptible());
      outbreakSpecieDto.setVaccinated(species.getVaccinated());
      outbreakSpecieDto.setUnit(species.getUnit().trim());
      if (species.getType() != null) {
        outbreakSpecieDto.setTtype(species.getType().trim());
      }
      if (null != species.getSpecie()) {
        outbreakSpecieDto.setSpecieId(species.getSpecie().getSpecieId());
      }
      else
      {     outbreakSpecieDto.setFirstDecomReq(Integer.valueOf(species.getSpeciesNameReq().split(",")[0]));
    	    outbreakSpecieDto.setSecondDecompReq(species.getSpeciesNameReq().split(",")[1]);
    	    outbreakSpecieDto.setSpecieCommonNameReq(species.getSpeciesNameReq().split(",")[2]);
    	    outbreakSpecieDto.setSpecieLatinReq(species.getSpeciesNameReq().split(",")[3]);
    	    
      }
      outbreakSpecieDto.setProdTypeId(species.getProductionTypeId());
      outbreakSpecieDto.setProductionSystem(species.getProductionSystem());
      outbreakSpecieDto.setOutbreakQuantitiesId(species.getOutbreakQuantitiesId());

      setSpecieDetails(species, outbreakSpecieDto, outbreak);

      outbreakSpeciesDtoList.add(outbreakSpecieDto);
    }

    return outbreakSpeciesDtoList;
  }

/**
   * Sets the species name.
   *
   * @param species           the species
   * @param outbreakSpecieDto the outbreak specie dto
   * @param language          the language
   */
    private void setSpeciesName(OutbreakSpeciesQuantity species, OutbreakSpeciesDto outbreakSpecieDto, String language) {
    String secondLevelNameReq;
    String firstLevelNameReq;
    String specieNameToDisplay;
    String transThirdLevelLatinName;
    String transThirdLevelCommonName;
    String transSecondLevelName;
    String transFirstLevelName;
    Integer firstLevelId;
    String thirdLevelNameReq;
    String category = EntityConstants.SPECIE_CATEGORY_FOR_NAME;
    String subCategory = EntityConstants.SPECIE_SUB_CATEGORY_FOR_NAME;
    if (null != species.getSpecie()) {
      List<OutbreakSpecies> speciesTestDto = speciesAndGroupsDao.getParentSpecie(species.getSpecie().getSpecieId());
      Integer size = speciesTestDto.size();
      if (size == 3) {
        transThirdLevelCommonName = translationDaoImpl.getTranslatedDataForReview(category, subCategory, language,
            speciesTestDto.get(0).getSpeciesName());
        transThirdLevelLatinName = speciesTestDto.get(0).getSpeciesName();
        transSecondLevelName = translationDaoImpl.getTranslatedDataForReview(category, subCategory, language,
            speciesTestDto.get(1).getSpeciesName());
        transFirstLevelName = translationDaoImpl.getTranslatedDataForReview(category, subCategory, language,
            speciesTestDto.get(2).getSpeciesName());
        specieNameToDisplay = transThirdLevelCommonName + " " + transThirdLevelLatinName + " : " + transSecondLevelName
            + " - " + transFirstLevelName;
        outbreakSpecieDto.setSpicieName(specieNameToDisplay);
      }
      if (size == 2) {
        transSecondLevelName = translationDaoImpl.getTranslatedDataForReview(category, subCategory, language,
            speciesTestDto.get(0).getSpeciesName());
        transFirstLevelName = translationDaoImpl.getTranslatedDataForReview(category, subCategory, language,
            speciesTestDto.get(1).getSpeciesName());
        specieNameToDisplay = transSecondLevelName + " : " + transFirstLevelName;
        outbreakSpecieDto.setSpicieName(specieNameToDisplay);
      }
      if (size == 1) {
        transFirstLevelName = translationDaoImpl.getTranslatedDataForReview(category, subCategory, language,
            speciesTestDto.get(0).getSpeciesName());
        outbreakSpecieDto.setSpicieName(transFirstLevelName);
      }
    } else {
      thirdLevelNameReq = species.getSpeciesNameReq().split(",")[2] + " " + species.getSpeciesNameReq().split(",")[3];
      secondLevelNameReq = species.getSpeciesNameReq().split(",")[1];
      firstLevelId = Integer.valueOf(species.getSpeciesNameReq().split(",")[0]);
      SpeciesAndGroups speciesAndGroups = currentSession().find(SpeciesAndGroups.class, firstLevelId);
      firstLevelNameReq = speciesAndGroups.getName();
      transFirstLevelName = translationDaoImpl.getTranslatedDataForReview(category, subCategory, language,
          firstLevelNameReq);
      specieNameToDisplay = thirdLevelNameReq + " : " + secondLevelNameReq + " - " + transFirstLevelName;
      outbreakSpecieDto.setSpicieNameReq(specieNameToDisplay);
      outbreakSpecieDto.setSpicieName(specieNameToDisplay);
    }
  }

  /**
   * to get and set the species details.
   *
   * @param species           as species
   * @param outbreakSpecieDto the outbreak specie dto
   * @param outbreak          the outbreak
   * @return the outbreak species dto
   */

  private OutbreakSpeciesDto setSpecieDetails(OutbreakSpeciesQuantity species, OutbreakSpeciesDto outbreakSpecieDto,
      Outbreaks outbreak) {
    if (outbreak.getIsAquatic() != null && !outbreak.getIsAquatic() && null != species.getIsWild()
        && species.getIsWild()) {
      setSpecieDetailsForWild(species, outbreakSpecieDto);
    } else if (outbreak.getIsAquatic() != null && outbreak.getIsAquatic() && null != species.getIsWild()
        && species.getIsWild()) {
      outbreakSpecieDto.setAnimalSpeciesCategoryType(ApplicationConstants.SPECIE_FISHERIES_AQUATIC);
      setSpecieDetailsForAquatic(species, outbreakSpecieDto);
    } else if (outbreak.getIsAquatic() != null && outbreak.getIsAquatic() && null != species.getIsWild()
        && !species.getIsWild()) {
      outbreakSpecieDto.setAnimalSpeciesCategoryType(ApplicationConstants.SPECIE_AQUACULTURE_AQUATIC);
      setSpecieDetailsForAquatic(species, outbreakSpecieDto);
    } else if (outbreak.getIsAquatic() != null && !outbreak.getIsAquatic() && null != species.getIsWild()
        && !species.getIsWild()) {
      setSpecieDetailsForDomestic(species, outbreakSpecieDto);

    }
    return outbreakSpecieDto;
  }

  /**
   * Sets the specie details for wild.
   *
   * @param species           the species
   * @param outbreakSpecieDto the outbreak specie dto
   */
  private void setSpecieDetailsForWild(OutbreakSpeciesQuantity species, OutbreakSpeciesDto outbreakSpecieDto) {
    outbreakSpecieDto.setAnimalSpeciesCategoryType(ApplicationConstants.SPECIE_WILD_TERRESTRIAL);
    if (null != species.getSpecie()) {
      outbreakSpecieDto.setOrder(species.getSpecie().getParentSpecie().getParentSpecie().getSpecieId().toString());
      outbreakSpecieDto.setWildFamily(species.getSpecie().getParentSpecie().getSpecieId().toString());
      outbreakSpecieDto.setCommonName(species.getSpecie().getSpecieId().toString());
      outbreakSpecieDto.setLatinName(species.getSpecie().getSpecieId().toString());
    } else {
      outbreakSpecieDto.setOrder(species.getSpeciesNameReq().split(",")[0]);
      outbreakSpecieDto.setWildFamily(species.getSpeciesNameReq().split(",")[1]);
      outbreakSpecieDto.setCommonName(species.getSpeciesNameReq().split(",")[2]);
      outbreakSpecieDto.setLatinName(species.getSpeciesNameReq().split(",")[2]);
    }
  }

  /**
   * Sets the specie details for aquatic.
   *
   * @param species           the species
   * @param outbreakSpecieDto the outbreak specie dto
   */
  private void setSpecieDetailsForAquatic(OutbreakSpeciesQuantity species, OutbreakSpeciesDto outbreakSpecieDto) {
    if (null != species.getSpecie()) {
      outbreakSpecieDto.setAquaclass(species.getSpecie().getParentSpecie().getParentSpecie().getSpecieId().toString());
      outbreakSpecieDto.setAquaticFamily(species.getSpecie().getParentSpecie().getSpecieId().toString());
      outbreakSpecieDto.setCommonName(species.getSpecie().getSpecieId().toString());
      outbreakSpecieDto.setLatinName(species.getSpecie().getSpecieId().toString());
    } else {
      outbreakSpecieDto.setAquaclass(species.getSpeciesNameReq().split(",")[0]);
      outbreakSpecieDto.setAquaticFamily(species.getSpeciesNameReq().split(",")[1]);
      outbreakSpecieDto.setCommonName(species.getSpeciesNameReq().split(",")[2]);
      outbreakSpecieDto.setLatinName(species.getSpeciesNameReq().split(",")[2]);
    }
  }

  /**
   * Sets the specie details for domestic.
   *
   * @param species           the species
   * @param outbreakSpecieDto the outbreak specie dto
   */
  private void setSpecieDetailsForDomestic(OutbreakSpeciesQuantity species, OutbreakSpeciesDto outbreakSpecieDto) {
    outbreakSpecieDto.setAnimalSpeciesCategoryType(ApplicationConstants.SPECIE_DOMESTIC_TERRESTRIAL);
    if (species.getSpecie() != null) {
      if (null == species.getSpecie().getParentSpecie()) {
        outbreakSpecieDto.setGroup(species.getSpecie().getSpecieId().toString());
      } else {
        outbreakSpecieDto.setGroup(species.getSpecie().getParentSpecie().getParentSpecie().getSpecieId().toString());
        outbreakSpecieDto.setSubGroup(species.getSpecie().getParentSpecie().getSpecieId().toString());
      }
    } else {
      outbreakSpecieDto.setGroup(species.getSpeciesNameReq().split(",")[0]);
      outbreakSpecieDto.setSubGroup(species.getSpeciesNameReq().split(",")[1]);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getControlMeasuresForOutbreak(List<Integer> cmId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<String> query = builder.createQuery(String.class);
    Root<ControlMeasures> controlMea = query.from(ControlMeasures.class);
    query.multiselect(controlMea.get("measure")).where(controlMea.get("cmId").in(cmId));
    return currentSession().createQuery(query).getResultList();

  }

  /**
   * Generating outbreaks list.
   *
   * @param status     the status
   * @param aquatic    the aquatic
   * @param builder    the builder
   * @param outbreakId the outbreak id
   * @param query      the query
   * @param outbreak   the outbreak
   */
  private void generatingOutbreaksList(String status, boolean aquatic, CriteriaBuilder builder, List<Long> outbreakId,
      CriteriaQuery<Outbreaks> query, Root<Outbreaks> outbreak) {
    query.select(outbreak).where(builder.and(builder.equal(outbreak.get("techStatus"), status),
        outbreak.get(EntityConstants.OUTBREAK_ID).in(outbreakId), builder.equal(outbreak.get("isAquatic"), aquatic)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OutbreakDiagnosticTests> getDiagnosticTestSummaryForoutbreaks() {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<OutbreakDiagnosticTests> query = builder.createQuery(OutbreakDiagnosticTests.class);
    Root<OutbreakDiagnosticTests> outbreakDiagnosticTests = query.from(OutbreakDiagnosticTests.class);
    query.select(outbreakDiagnosticTests);
    return currentSession().createQuery(query).getResultList();

  }

  @Override
  public String getAreaName(int areaId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<String> query = builder.createQuery(String.class);
    Root<AreaDetails> areaDetailsRoot = query.from(AreaDetails.class);
    query.select(areaDetailsRoot.get("name")).where(builder.equal(areaDetailsRoot.get("area").get("areaId"), areaId));
    return currentSession().createQuery(query).getSingleResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Object> validateOutbreakCmForClosing(List<Long> outbreakIdList, String measure) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Object> query = builder.createQuery(Object.class);
    Root<OutbreakControlMeasures> outbreakCmRoot = query.from(OutbreakControlMeasures.class);
    List<Predicate> criteriaList = new ArrayList<>();
    Expression<Long> exp = outbreakCmRoot.get(EntityConstants.OUTBREAK_CONTROL_MEASURES_OUTBREAK)
        .get(EntityConstants.OUTBREAK_ID);
    criteriaList.add(exp.in(outbreakIdList));
    criteriaList.add(builder
        .equal(outbreakCmRoot.get(EntityConstants.OUTBREAK_CONTROL_MEASURES).get(EntityConstants.CM_MEASURE), measure));
    query.multiselect(outbreakCmRoot.get(EntityConstants.OUTBREAK_CONTROL_MEASURES_OUTBREAK))
        .where(builder.and(criteriaList.toArray(new Predicate[0])));
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Outbreaks> getNationalOutbreakReference(String nationalObRef, long epiEventId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Outbreaks> query = builder.createQuery(Outbreaks.class);
    Root<Outbreaks> outbreakRoot = query.from(Outbreaks.class);
    Root<OutbreaksForEvents> outbreakForEventsRoot = query.from(OutbreaksForEvents.class);
    query.select(outbreakRoot).where(builder.and(
        builder.like(builder.lower(outbreakRoot.get(EntityConstants.OUTBREAK_NATIONAL_OB_REFERENCE)),
            builder.lower(builder.literal(nationalObRef))),
        builder.equal(
            outbreakForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT).get(EntityConstants.EPI_EVT_ID),
            epiEventId),
        builder.equal(
            outbreakForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID),
            outbreakRoot.get(EntityConstants.OUTBREAK_ID))));
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Outbreaks findOutbreakBasedOnRefNumber(String refNumber, int areaId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Outbreaks> query = builder.createQuery(Outbreaks.class);
    Root<Outbreaks> outbreaksRoot = query.from(Outbreaks.class);

    query.select(outbreaksRoot)
        .where(builder.and(builder.equal(outbreaksRoot.get(EntityConstants.OUTBREAK_AREA_ID), areaId),
            builder.equal(outbreaksRoot.get(EntityConstants.OUTBREAK_NATIONAL_OB_REFERENCE), refNumber)));

    Query q = currentSession().createQuery(query);
    Outbreaks outbreakResult = null;
    try {
      outbreakResult = (Outbreaks) q.getSingleResult();
    } catch (NoResultException nre) {
      // exception expected when there is no data in database
    }
    return outbreakResult;
  }

  /**
   * {@inheritDoc}
   * 
   * @throws DataNotFoundException
   */
  @Override
  public List<Object[]> getOutbreaksByEventId(Long epiEventId, Boolean isOpen, String attName, String sortOrder,
      String searchText) throws DataNotFoundException {

    EpiEvent epiEvent = currentSession().find(EpiEvent.class, epiEventId);
    String reportType = epiEvent.getReport().getReportType();

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
    Root<Outbreaks> outbreakRoot = query.from(Outbreaks.class);
    Join<Outbreaks, OutbreaksForEvents> outbreaksForEventsRoot = outbreakRoot.join("outbreaksForEvents");
    Join<OutbreaksForEvents, Report> reportRoot = outbreaksForEventsRoot.join("report");
    List<Predicate> criteriaList = new ArrayList<>();

    Predicate predicate1 = null;
    if (reportType.equalsIgnoreCase("FUR")) {

      predicate1 = getFurOutbrekDetails(epiEventId, outbreakRoot);
    } else {
      predicate1 = outbreaksForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT)
          .get(EntityConstants.EPI_EVT_ID).in(epiEventId);
    }
    criteriaList.add(predicate1);
    if (isOpen) {
      Predicate predicate2 = builder.isNull(outbreakRoot.get(EntityConstants.OUTBREAK_END_DATE));
      criteriaList.add(predicate2);
    }

    List<Predicate> criteriaList1 = new ArrayList<>();
    if (!StringUtils.isEmpty(searchText)) {
      String searchKey = ApplicationConstants.SEARCH_PREFIX + searchText.trim().toLowerCase()
          + ApplicationConstants.SEARCH_SUFFIX;
      Predicate predicate3 = builder.or(
          builder.like(builder.lower(outbreakRoot.get(EntityConstants.OUTBREAK_ID).as(String.class)), searchKey),
          builder.like(builder.lower(outbreakRoot.get(EntityConstants.OUTBREAK_LOCATION_NAME)), searchKey),
          builder.like(builder.lower(outbreakRoot.get(EntityConstants.OUTBREAK_EPI_UNIT_TYPE)), searchKey),
          builder.like(builder.lower(outbreakRoot.get(EntityConstants.OUTBREAK_OIE_REFERENCE)), searchKey),
          builder.like(builder.lower(outbreakRoot.get(EntityConstants.OUTBREAK_START_DATE).as(String.class)),
              searchKey),
          builder.like(builder.lower(outbreakRoot.get(EntityConstants.OUTBREAK_END_DATE).as(String.class)), searchKey),
          builder.like(builder.lower(reportRoot.get(EntityConstants.REPORT_TYPE)), searchKey));
      criteriaList1.add(predicate3);
      query
          .multiselect(outbreakRoot, reportRoot.get(EntityConstants.REPORT_TYPE),
              reportRoot.get(EntityConstants.Number), reportRoot.get(EntityConstants.REPORT_REPORT_ID))
          .where(builder.and(criteriaList.toArray(new Predicate[0])),
              builder.or(criteriaList1.toArray(new Predicate[0])));
    } else {
      query
          .multiselect(outbreakRoot, reportRoot.get(EntityConstants.REPORT_TYPE),
              reportRoot.get(EntityConstants.Number), reportRoot.get(EntityConstants.REPORT_REPORT_ID))
          .where(builder.and(criteriaList.toArray(new Predicate[0])));
    }
    if (!StringUtils.isEmpty(attName) && !StringUtils.isEmpty(sortOrder)
        && !(attName.equalsIgnoreCase(SortingAttribute.ADMIN_DIVISION.getValue())
            || attName.equalsIgnoreCase(SortingAttribute.TOTAL_OUTBREAKS.getValue())
            || attName.equalsIgnoreCase(SortingAttribute.REPORT_TYPE.getValue()))) {
      query = sortOrder.equalsIgnoreCase(ApplicationConstants.SORT_ORDER_ASCENDING)
          ? query.orderBy(builder.asc(outbreakRoot.get(attName)))
          : query.orderBy(builder.desc(outbreakRoot.get(attName)));
    } else {
      query.orderBy(builder.desc(outbreakRoot.get(EntityConstants.OUTBREAK_START_DATE)));
    }
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * Gets the fur outbrek details in case of fur.
   *
   * @param epiEventId   the epi event id
   * @param outbreakRoot the outbreak root
   * @return the fur outbrek details
   * @throws DataNotFoundException
   */
  private Predicate getFurOutbrekDetails(Long epiEventId, Root<Outbreaks> outbreakRoot) throws DataNotFoundException {
    Predicate predicate1;
    EpiEvent epiEventdetails;
    List<Long> epiEventid = new ArrayList<>();
    epiEventdetails = currentSession().find(EpiEvent.class, epiEventId);

    Long parentEventId = null;
    if (epiEventdetails.getHistoricEpiEvent() != null) {
      parentEventId = epiEventdetails.getHistoricEpiEvent().getEpiEventId();
      epiEventid.add(parentEventId);
    }
    while (parentEventId != null) {
      epiEventdetails = currentSession().find(EpiEvent.class, parentEventId);
      if (epiEventdetails.getHistoricEpiEvent() != null) {
        parentEventId = epiEventdetails.getHistoricEpiEvent().getEpiEventId();
      } else {
        parentEventId = null;
      }
      if (parentEventId != null) {
        epiEventid.add(parentEventId);
      }
    }
    epiEventid.add(epiEventId);

    List<Long> outbreakIds = getOutbreakId(epiEventid);
    List<Long> historyOutbreakIds = null;
    if (!outbreakIds.isEmpty()) {
      historyOutbreakIds = gethistoryOutbreakIds(outbreakIds);
      if (!historyOutbreakIds.isEmpty()) {
        outbreakIds.removeAll(historyOutbreakIds);
      }
    }
    if (outbreakIds.isEmpty()) {
      throw new DataNotFoundException("No Outbreaks found regarding this Event");
    }
    predicate1 = outbreakRoot.get(EntityConstants.OUTBREAK_ID).in(outbreakIds);
    return predicate1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getLatestOutbreakEndDate(long epiEventId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Object> criteriaQuery = builder.createQuery(Object.class);
    Root<Outbreaks> outbreakRoot = criteriaQuery.from(Outbreaks.class);
    Root<OutbreaksForEvents> outbreaksForEventsRoot = criteriaQuery.from(OutbreaksForEvents.class);
    List<Predicate> criteriaList = new ArrayList<>();
    Predicate predicate = builder.and(
        builder.equal(
            outbreaksForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID),
            outbreakRoot.get(EntityConstants.OUTBREAK_ID)),
        builder.equal(
            outbreaksForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT).get(EntityConstants.EPI_EVT_ID),
            epiEventId));
    criteriaList.add(predicate);
    criteriaQuery.multiselect(builder.max(outbreakRoot.get(EntityConstants.OUTBREAK_END_DATE)));
    Query query = currentSession()
        .createQuery(criteriaQuery.where(builder.and(criteriaList.toArray(new Predicate[0]))));
    Date date = null;
    if (!query.getResultList().isEmpty()) {
      date = (Date) query.getResultList().get(0);
    }
    return date;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> validateOutbreakDiagForClosing(List<Long> outbreakList) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<String> query = builder.createQuery(String.class);
    Root<OutbreakDiagnosticTests> outbreakDiagRoot = query.from(OutbreakDiagnosticTests.class);
    Root<TestsResults> testResultsRoot = query.from(TestsResults.class);
    List<Predicate> criteriaList = new ArrayList<>();
    Expression<Long> exp = outbreakDiagRoot.get(EntityConstants.OUTBREAK_DIAG_TEST_OUTBREAK)
        .get(EntityConstants.OUTBREAK_ID);
    criteriaList.add(exp.in(outbreakList));
    criteriaList
        .add(builder.equal(testResultsRoot.get(EntityConstants.TEST_OB_DIAG_TEST).get(EntityConstants.OUTBREAK_DIAG_ID),
            outbreakDiagRoot.get(EntityConstants.OUTBREAK_DIAG_ID)));
    criteriaList
        .add(builder.equal(testResultsRoot.get(EntityConstants.TEST_OB_RESULT), ApplicationConstants.TEST_RESULT));
    query.multiselect(
        outbreakDiagRoot.get(EntityConstants.OUTBREAK_DIAG_TEST_OUTBREAK).get(EntityConstants.OUTBREAK_OIE_REFERENCE))
        .where(builder.and(criteriaList.toArray(new Predicate[0])));
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OutbreakInfoDto geteditoutbreaks(Long epiEventId, Long outbreakId, String language, String reportType) {
    OutbreakInfoDto outbreakDto = new OutbreakInfoDto();
    EpiEvent epiEvent = currentSession().find(EpiEvent.class, epiEventId);
    Outbreaks outbreaks = currentSession().find(Outbreaks.class, outbreakId);
      
    outbreakDto.setOutbreakStatus(outbreaks.getTechStatus());
    outbreakDto.setOutbreakStartDate(outbreaks.getStartDate());
    outbreakDto.setOutbreakEndDate(outbreaks.getEndDate());
    outbreakDto.setOieReference(outbreaks.getOieReference());
    outbreakDto.setLatitude(outbreaks.getLocationPoint().getCoordinate().x);
    outbreakDto.setLongitude(outbreaks.getLocationPoint().getCoordinate().y);
    outbreakDto.setOieReference(outbreaks.getOieReference());
    outbreakDto.setEpiUnitType(outbreaks.getEpiUnitType());
    outbreakDto.setNationalObReference(outbreaks.getNationalObReference());
    outbreakDto.setIsCluster(outbreaks.getIsCluster());
    outbreakDto.setLocationApprox(outbreaks.getLocationApprox());
    outbreakDto.setLocation(outbreaks.getLocationName());
    if (null != outbreaks.getDiseaseId()) {
      outbreakDto.setOutbreakDisease(outbreaks.getDiseaseId().getDiseaseId());
    } else {
      outbreakDto.setOutbreakDiseaseReq(outbreaks.getDiseaseNameReq());
    }
    outbreakDto.setAffectedDesc(outbreaks.getObDescription());

    if (reportType.equalsIgnoreCase("FUR") && null != outbreaks.getHistoricOutbreakId()) {
      outbreakDto.setForHistoryOutbeakId(outbreaks.getHistoricOutbreakId().getOutbreakId());
    }
    Integer noOfOutBreak = getNoOfOutbreak(outbreakId);
    if (noOfOutBreak != null) {
      outbreakDto.setNoOfOutbreaks(noOfOutBreak);
    }
    List<Integer> outbreakCm = getOutbreakCm(outbreaks);
    if (!outbreakCm.isEmpty()) {
      outbreakDto.setIsPlan("1");
      outbreakDto.setOutbreakCmApplied(outbreakCm);
    } else {
      outbreakDto.setIsPlan("0");
    }

    List<String> outbreakDn = getOutbreakDn(outbreaks);
    if (!outbreakDn.isEmpty()) {
      outbreakDto.setDiagSummary(outbreakDn);
    }
    outbreakDto.setSpeciesDetails(getSpeciesData(outbreaks, language));
    outbreakDto.setIsOutbreakUnitEditable(
        getOutbreakList(epiEventId).size() == 1 ? ApplicationConstants.TRUE : ApplicationConstants.FALSE);
    outbreakDto.setReportType(epiEvent.getReport().getReportType());

    return outbreakDto;
  }

  /**
   * This method is use to get the diagnostic nature list.
   *
   * @param outbreaksDto outbreak details
   * @return its return the list of diagnostic nature list
   */
  private List<String> getOutbreakDn(Outbreaks outbreaksDto) {
    return outbreaksDto.getOutbreakDiagnosticNature().stream().map(OutbreakDiagnosticNature::getDiagnosticNature)
        .collect(Collectors.toList());
  }

  /**
   * This method is use to get the control measures list.
   *
   * @param outbreaksDto outbreak details
   * @return its return the list of control measures list
   */
  private List<Integer> getOutbreakCm(Outbreaks outbreaksDto) {
    return outbreaksDto.getOutbreakControlMeasures().stream().map(obj -> obj.getControlMeasures().getCmId())
        .collect(Collectors.toList());
  }

  /**
   * This method is used provide the clustered details about the outbreaks.
   *
   * @param outbreakId outbreak id.
   * @return its return the no. of outbreaks.
   */
  private Integer getNoOfOutbreak(Long outbreakId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<ClusterQuantities> query = builder.createQuery(ClusterQuantities.class);
    Root<ClusterQuantities> editOutbreak = query.from(ClusterQuantities.class);

    query.select(editOutbreak).where(
        builder.equal(editOutbreak.get(ApplicationConstants.OUTBREAKS).get(EntityConstants.OUTBREAK_ID), outbreakId));
    Query editOutbreakquery = currentSession().createQuery(query);
    try {
      ClusterQuantities outbreakQuantity = (ClusterQuantities) editOutbreakquery.getSingleResult();
      return outbreakQuantity.getTotalOutbreaks();

    } catch (NoResultException ex) {
      // Log exception
    }
    return null;
  }

  @Override
  public List<Long> calculateOutbreaksForReports(long reportId) {
    Report report = currentSession().find(Report.class, reportId);
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Long> query = builder.createQuery(Long.class);
    Root<OutbreaksForEvents> outbreakList = query.from(OutbreaksForEvents.class);
    query.multiselect(outbreakList.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID))
        .where(builder.equal(
            outbreakList.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT).get(EntityConstants.EPI_EVT_ID),
            report.getEpiEvent().getEpiEventId()));
    return currentSession().createQuery(query).getResultList();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Outbreaks> getOutbreakForStatus(String status, EpiEvent epiEvent, boolean aquatic) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Long> queryForOutbreaks = builder.createQuery(Long.class);
    Root<OutbreaksForEvents> outbreakList = queryForOutbreaks.from(OutbreaksForEvents.class);
    queryForOutbreaks
        .multiselect(outbreakList.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID))
        .where(builder.equal(
            outbreakList.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT).get(EntityConstants.EPI_EVT_ID),
            epiEvent.getEpiEventId()));
    List<Long> outbreakId = currentSession().createQuery(queryForOutbreaks).getResultList();

    CriteriaQuery<Outbreaks> query = builder.createQuery(Outbreaks.class);
    Root<Outbreaks> outbreak = query.from(Outbreaks.class);
    // For Outbreak for status ongoing
    if (status.equalsIgnoreCase(ApplicationConstants.OUTBREAK_ONGOING)) {
      generatingOutbreaksList(ApplicationConstants.OUTBREAK_ONGOING, aquatic, builder, outbreakId, query, outbreak);
    } else if (status.equalsIgnoreCase(ApplicationConstants.OUTBREAK_RESOLVED)) {
      generatingOutbreaksList(ApplicationConstants.OUTBREAK_RESOLVED, aquatic, builder, outbreakId, query, outbreak);

    }
    currentSession().createQuery(query).getResultList();
    return currentSession().createQuery(query).getResultList();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getDateFromOutbreak(String dateType, Long epiEventId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Object> criteriaQuery = builder.createQuery(Object.class);
    Root<OutbreaksForEvents> outbreakEvent = criteriaQuery.from(OutbreaksForEvents.class);
    Root<Outbreaks> outbreakRoot = criteriaQuery.from(Outbreaks.class);

    List<Long> outbreakList = getOutbreakList(epiEventId);
    Long historyEventId = null;
    if (outbreakList.isEmpty()) {
      EpiEvent epiEvent = currentSession().find(EpiEvent.class, epiEventId);
      if (epiEvent.getReport().getReportType().equalsIgnoreCase("FUR")) {
        historyEventId = epiEvent.getHistoricEpiEvent().getEpiEventId();
      }
    }

    if (dateType.equalsIgnoreCase(ApplicationConstants.FROM)) {
      criteriaQuery.multiselect(builder.min(outbreakRoot.get(EntityConstants.OUTBREAK_START_DATE)));
    } else {
      criteriaQuery.multiselect(builder.max(outbreakRoot.get(EntityConstants.OUTBREAK_END_DATE)));
    }

    if (outbreakList.isEmpty()) {
      criteriaQuery.where(
          builder.and(builder.equal(
              outbreakEvent.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT).get(EntityConstants.EPI_EVT_ID),
              historyEventId)),
          builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_ID),
              outbreakEvent.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID)));
    } else {
      criteriaQuery.where(builder.and(builder.equal(
          outbreakEvent.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT).get(EntityConstants.EPI_EVT_ID), epiEventId)),
          builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_ID),
              outbreakEvent.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID)));
    }

    Query query = currentSession().createQuery(criteriaQuery);
    if (!query.getResultList().isEmpty()) {
      return (Date) query.getResultList().get(0);
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ValidationDto deleteDiagnosticTest(Long labTestId, Boolean isEvent) {

    ValidationDto validateDto = new ValidationDto();
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    LabTestsSummaries labTestsSummaries = currentSession().find(LabTestsSummaries.class, labTestId);

    // For Linking Table
    CriteriaDelete<OutbreakDiagnosticTests> delete = builder.createCriteriaDelete(OutbreakDiagnosticTests.class);
    delete.from(OutbreakDiagnosticTests.class);

    // For Test Result
    CriteriaDelete<TestsResults> deleteTest = builder.createCriteriaDelete(TestsResults.class);
    Root<TestsResults> deleteTestResult = deleteTest.from(TestsResults.class);

    // For Fetching the data for Linking Table
    CriteriaQuery<Long> getquery = builder.createQuery(Long.class);
    Root<OutbreakDiagnosticTests> outbreakQueryRoot = getquery.from(OutbreakDiagnosticTests.class);

    // Variables
    String diagReq = null;
    Integer diagnosticId = null;
    if (isEvent) {
      deletingDataFromLabTestSum(labTestId, builder, labTestsSummaries);
      currentSession().delete(labTestsSummaries);
      validateDto.setSuccess(true);
    } else {
      // common code for Both
      if (null != labTestsSummaries) {
        EpiEvent epiEvent = labTestsSummaries.getLabTestsForEvent();
        Set<Outbreaks> out = epiEvent.getOutbreaksForEvents().stream().map(OutbreaksForEvents::getOutbreak)
            .collect(Collectors.toSet());
        Set<Long> id = new HashSet<>();
        for (Outbreaks outbreaks : out) {
          id.add(outbreaks.getOutbreakId());
        }
        if (labTestsSummaries.getLabTestsForDiagTests() != null) {
          diagnosticId = labTestsSummaries.getLabTestsForDiagTests().getDiagTestId();
        }
        if (diagnosticId == null) {
          diagReq = fetchDiagReq(labTestsSummaries);
          deletingDataFromLabTestSum(labTestId, builder, labTestsSummaries);
          // Getting the Data for Linking table
          getquery.multiselect(outbreakQueryRoot.get(EntityConstants.OUTBREAK_DIAGNOSTIC_TEST_ID))
              .where(builder.and(builder.equal(outbreakQueryRoot.get(EntityConstants.DIAGNOSTIC_TEST_REQ), diagReq),
                  outbreakQueryRoot.get(EntityConstants.OUTBREAK).get(EntityConstants.OUTBREAK_ID).in(id)));
          List<Long> obtrList = currentSession().createQuery(getquery).getResultList();
          // Deletion for Outbreak from Linking table
          deletionFromLinkingTable(obtrList);
          // Deletion from Result Table
          deletonFromResultTable(deleteTest, deleteTestResult, obtrList);
          // Deletion from Parent Table
          currentSession().delete(labTestsSummaries);

        } else { // Deletion from Linking table on the basis of outbreakId and DiagnosticTestId
          deletingDataFromLabTestSum(labTestId, builder, labTestsSummaries);
          // Get Query For outbreakDiagnosticTest for list of ObtrIds for Deletion
          getquery.multiselect(outbreakQueryRoot.get(EntityConstants.OUTBREAK_DIAGNOSTIC_TEST_ID))
              .where(builder.and(
                  builder.equal(outbreakQueryRoot.get("diagTests").get(EntityConstants.DIAGNOSTIC_TEST_ID),
                      diagnosticId),
                  outbreakQueryRoot.get(EntityConstants.OUTBREAK).get(EntityConstants.OUTBREAK_ID).in(id)));
          List<Long> obtrList = currentSession().createQuery(getquery).getResultList();
          // From OutbreakDiagnostic Test
          if (!obtrList.isEmpty()) {
            deletionFromLinkingTable(obtrList);
          }
          // From Result Table
          deletonFromResultTable(deleteTest, deleteTestResult, obtrList);
          // Deletion from Parent Table
          currentSession().delete(labTestsSummaries);
        }
        validateDto.setSuccess(true);
      }
    }
    return validateDto;

  }

  private void deletonFromResultTable(CriteriaDelete<TestsResults> deleteTest, Root<TestsResults> deleteTestResult,
      List<Long> obtrList) {
    if (!obtrList.isEmpty()) {
      deleteTest.where(deleteTestResult.get(EntityConstants.OUTBREAK_DIAGNOSTIC_TEST)
          .get(EntityConstants.OUTBREAK_DIAGNOSTIC_TEST_ID).in(obtrList));
      currentSession().createQuery(deleteTest).executeUpdate();
    }

  }

  private void deletionFromLinkingTable(List<Long> obdtList) {
    for (Long obdt : obdtList) {
      OutbreakDiagnosticTests obdtTest = currentSession().get(OutbreakDiagnosticTests.class, obdt);
      currentSession().delete(obdtTest);
    }

  }

  private String fetchDiagReq(LabTestsSummaries labTestsSummaries) {
    String diagReq;
    String field;
    if (labTestsSummaries.getIsField()) {
      field = labTestsSummaries.getDiagFieldTestReq();
      diagReq = splitData(field);

    } else {
      field = labTestsSummaries.getDiagLabTestReq();
      diagReq = splitData(field);
    }
    return diagReq;
  }

  private String splitData(String field) {
    return field.split(",")[2];
  }

  private void deletingDataFromLabTestSum(Long labTestId, CriteriaBuilder builder,
      LabTestsSummaries labTestsSummaries) {
    Integer labId = null;
    CriteriaDelete<LaboratoryForTestsSum> deletelab = builder.createCriteriaDelete(LaboratoryForTestsSum.class);
    Root<LaboratoryForTestsSum> labRootdelete = deletelab.from(LaboratoryForTestsSum.class);
    Optional<Laboratories> labSumTestResult = labTestsSummaries.getLaboratories().stream().findFirst();
    if (labSumTestResult.isPresent()) {
      labId = labSumTestResult.get().getLabId();
    }
    deletelab
        .where(builder.and(builder.equal(labRootdelete.get("laboratories").get(EntityConstants.LAB_ID), labId), builder
            .equal(labRootdelete.get("labTestsSummaries").get(EntityConstants.LABORATORY_TEST_SUMMARY_ID), labTestId)));
    currentSession().createQuery(deletelab).executeUpdate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DiagnosticTestDeleteDto deleteDiagnosticTestLink(Long obdtId) {
    DiagnosticTestDeleteDto validateDto = new DiagnosticTestDeleteDto();
    OutbreakDiagnosticTests outbreakDiagnosticTests = currentSession().find(OutbreakDiagnosticTests.class, obdtId);
    currentSession().delete(outbreakDiagnosticTests);
    validateDto.setSuccess(true);
    if (!isNull(outbreakDiagnosticTests.getDiagTests())) {
      validateDto.setDiagTestId(outbreakDiagnosticTests.getDiagTests().getDiagTestId());
    } else {
      validateDto.setDiagName(outbreakDiagnosticTests.getDiagnosticTestReq());
    }
    validateDto.setOutbreakId(outbreakDiagnosticTests.getOutbreaks().getOutbreakId());
    return validateDto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<OutbreakLinkTestDto> getLinkDiagnosticTest(@NotNull Long epiEventId, @NotNull Long outbreakId) {

    Set<OutbreakLinkTestDto> set = new HashSet<>();
    List<Integer> diagTestId = new ArrayList<>();
    List<DiagnosticTests> diagTest;
    // Total
    List<String> totalReq = null;
    // Left
    List<String> leftOutbreak = null;
    List<Integer> diagId;
    List<OutbreakDiagnosticTests> outbreakDiagnosticTests = new ArrayList<>();
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<OutbreakDiagnosticTests> getOutbreakDiagnostic = builder.createQuery(OutbreakDiagnosticTests.class);
    Root<OutbreakDiagnosticTests> outbreakQueryRoot = getOutbreakDiagnostic.from(OutbreakDiagnosticTests.class);
    // Fetching the diagnostic test name -->Total
    totalReq = extractingDataForReqDiag(epiEventId, builder);
    // Unique combination For Diagnostic Test req --> Left
    List<OutbreakDiagnosticTests> test = extractingUniqueCombinationofReq(outbreakId, totalReq, getOutbreakDiagnostic,
        outbreakQueryRoot, builder);
    // Extracting UniqueReq in Linking Table
    leftOutbreak = test.stream().map(OutbreakDiagnosticTests::getDiagnosticTestReq).collect(Collectors.toList());
    // Minus the data for Diagnostic Test
    for (String outbreak : totalReq) {
      if (!leftOutbreak.contains(outbreak)) {
        OutbreakLinkTestDto outbreakLinkTestDto = new OutbreakLinkTestDto();
        outbreakLinkTestDto.setDiagTestId(null);
        outbreakLinkTestDto.setName(outbreak);
        outbreakLinkTestDto.setOutbreakId(outbreakId);
        set.add(outbreakLinkTestDto);
      }
    }
    // For fetching Diagnostic Test Id for EpiEvent
    CriteriaQuery<LabTestsSummaries> getquery = builder.createQuery(LabTestsSummaries.class);
    Root<LabTestsSummaries> labTestsSummaries = getquery.from(LabTestsSummaries.class);
    getquery.select(labTestsSummaries.get("labTestsForDiagTests").get(EntityConstants.DIAGNOSTIC_TEST_ID))
        .where(builder.equal(labTestsSummaries.get(EntityConstants.LABORATORY_TEST_FOR_EVENTS)
            .get(EntityConstants.DIAGNOSTIC_SUMMARIES_EPI_EVENT_ID), epiEventId))
        .distinct(true);
    List<LabTestsSummaries> testId = currentSession().createQuery(getquery).getResultList();
    diagTest = (!testId.contains(null))
        ? testId.stream().map(LabTestsSummaries::getLabTestsForDiagTests).collect(Collectors.toList())
        : new ArrayList<>();
    diagId = (!diagTest.isEmpty()) ? diagTest.stream().map(DiagnosticTests::getDiagTestId).collect(Collectors.toList())
        : new ArrayList<>();
    if (!diagId.isEmpty()) {
      // Unique Combination For Diagnostic Test
      getOutbreakDiagnostic.select(outbreakQueryRoot).where(builder.and(
          outbreakQueryRoot.get("diagTests").get("diagTestId").in(diagId),
          builder.equal(outbreakQueryRoot.get(EntityConstants.OUTBREAK).get(EntityConstants.OUTBREAK_ID), outbreakId)));
      outbreakDiagnosticTests = currentSession().createQuery(getOutbreakDiagnostic).getResultList();
    }
    // Selecting the deleted outbreak for that diagnostic id
    if (!outbreakDiagnosticTests.isEmpty()) {
      for (OutbreakDiagnosticTests outbreakDiagnostic : outbreakDiagnosticTests) {
        diagTestId.add(outbreakDiagnostic.getDiagTests().getDiagTestId());
      }
      for (Integer id : diagId) {
        if (!diagTestId.contains(id)) {
          OutbreakLinkTestDto outbreakLinkTestDto = new OutbreakLinkTestDto();
          DiagnosticTests diagnosticSummaries = currentSession().find(DiagnosticTests.class, id);
          outbreakLinkTestDto.setDiagTestId(diagnosticSummaries.getDiagTestId());
          outbreakLinkTestDto.setName(diagnosticSummaries.getName());
          outbreakLinkTestDto.setOutbreakId(outbreakId);
          set.add(outbreakLinkTestDto);
        }
      }

    }
    return set;
  }

  private List<OutbreakDiagnosticTests> extractingUniqueCombinationofReq(Long outbreakId, List<String> reqOutbreak,
      CriteriaQuery<OutbreakDiagnosticTests> getOutbreakDiagnostic, Root<OutbreakDiagnosticTests> outbreakQueryRoot,
      CriteriaBuilder builder) {
    getOutbreakDiagnostic.select(outbreakQueryRoot).where(builder.and(
        outbreakQueryRoot.get("diagnosticTestReq").in(reqOutbreak),
        builder.equal(outbreakQueryRoot.get(EntityConstants.OUTBREAK).get(EntityConstants.OUTBREAK_ID), outbreakId)));
    return currentSession().createQuery(getOutbreakDiagnostic).getResultList();
  }

  private List<String> extractingDataForReqDiag(Long epiEventId, CriteriaBuilder builder) {
    List<String> reqOutbreak = new ArrayList<>();
    List<String> diagTestReq;
    List<String> diagLabTestReq;
    CriteriaQuery<LabTestsSummaries> getquerytest = builder.createQuery(LabTestsSummaries.class);
    Root<LabTestsSummaries> labSummaries = getquerytest.from(LabTestsSummaries.class);
    getquerytest.select(labSummaries)
        .where(builder.and(builder.equal(labSummaries.get("labTestsForEvent").get("epiEventId"), epiEventId),
            builder.isNull(labSummaries.get("labTestsForDiagTests").get(EntityConstants.DIAGNOSTIC_TEST_ID))))
        .distinct(true);
    List<LabTestsSummaries> req = currentSession().createQuery(getquerytest).getResultList();
    diagTestReq = (!req.isEmpty()) ? req.stream().filter(i -> i.getIsField().equals(true))
        .map(LabTestsSummaries::getDiagFieldTestReq).collect(Collectors.toList()) : new ArrayList<>();
    diagLabTestReq = (!req.isEmpty()) ? req.stream().filter(i -> i.getIsField().equals(false))
        .map(LabTestsSummaries::getDiagLabTestReq).collect(Collectors.toList()) : new ArrayList<>();
    // Split on the basis of 3 parameter
    for (String diag : diagTestReq) {
      reqOutbreak.add(diag.split(",")[2]);
    }
    for (String diag : diagLabTestReq) {
      reqOutbreak.add(diag.split(",")[2]);
    }
    return reqOutbreak;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Long> getOutbreakList(Long epiEventId) {

    EpiEvent epiEventdetails;
    List<Long> epiEventIdList = new ArrayList<>();
    epiEventdetails = currentSession().find(EpiEvent.class, epiEventId);
    Long parentEventId = null;
    if (epiEventdetails.getHistoricEpiEvent() != null) {
      parentEventId = epiEventdetails.getHistoricEpiEvent().getEpiEventId();
      epiEventIdList.add(parentEventId);
    }
    while (parentEventId != null) {
      epiEventdetails = currentSession().find(EpiEvent.class, parentEventId);
      if (epiEventdetails.getHistoricEpiEvent() != null) {
        parentEventId = epiEventdetails.getHistoricEpiEvent().getEpiEventId();
      } else {
        parentEventId = null;
      }
      if (parentEventId != null) {
        epiEventIdList.add(parentEventId);
      }
    }
    epiEventIdList.add(epiEventId);

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Long> query = builder.createQuery(Long.class);
    Root<OutbreaksForEvents> root = query.from(OutbreaksForEvents.class);
    query.select(root.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID)).where(
        root.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT).get(EntityConstants.EPI_EVT_ID).in(epiEventIdList));
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<LabTestsSummaries> getSpecieList(Long epiEventId) {
    EpiEvent epiEventdetails;
    List<Long> epiEventIdList = new ArrayList<>();
    epiEventdetails = currentSession().find(EpiEvent.class, epiEventId);

    Long parentEventId = null;
    if (epiEventdetails.getHistoricEpiEvent() != null) {
      parentEventId = epiEventdetails.getHistoricEpiEvent().getEpiEventId();
      epiEventIdList.add(parentEventId);
    }
    while (parentEventId != null) {
      epiEventdetails = currentSession().find(EpiEvent.class, parentEventId);
      if (epiEventdetails.getHistoricEpiEvent() != null) {
        parentEventId = epiEventdetails.getHistoricEpiEvent().getEpiEventId();
      } else {
        parentEventId = null;
      }
      if (parentEventId != null) {
        epiEventIdList.add(parentEventId);
      }
    }
    epiEventIdList.add(epiEventId);
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<LabTestsSummaries> query = builder.createQuery(LabTestsSummaries.class);
    Root<LabTestsSummaries> root = query.from(LabTestsSummaries.class);
    query.select(root).where(root.get("labTestsForEvent").get(EntityConstants.EPI_EVT_ID).in(epiEventIdList));
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Long> getEligibleOutbreaks(List<Integer> specieList) {
    List<Long> outbreakIds = new ArrayList<>();
    for (Integer obj : specieList) {
      CriteriaBuilder builder = currentSession().getCriteriaBuilder();
      CriteriaQuery<Long> query = builder.createQuery(Long.class);
      Root<OutbreakSpeciesQuantity> root = query.from(OutbreakSpeciesQuantity.class);
      query.select(root.get("outbreak").get("outbreakId"))
          .where(builder.equal(root.get("specie").get("specieId"), obj));
      outbreakIds = currentSession().createQuery(query).getResultList();
    }
    return outbreakIds;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Object> validateEntityValue(String entityName, String value, String category, String subCategory) {
    Boolean valuePresent = false;
    List<Object> returnList = new ArrayList<>();
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<String> query = builder.createQuery(String.class);

    List<Object> queryAndColName = getRootQueryByEntityName(query, builder, entityName, category, subCategory);
    query = (CriteriaQuery<String>) queryAndColName.get(0);
    List<String> values = currentSession().createQuery(query).getResultList();
    if (values != null && !value.isEmpty()) {
      valuePresent = values.stream().anyMatch(value::equalsIgnoreCase) ? ApplicationConstants.TRUE
          : ApplicationConstants.FALSE;
    } else {
      valuePresent = ApplicationConstants.FALSE;
    }

    returnList.add(valuePresent);
    returnList.add(queryAndColName.get(1));

    return returnList;

  }

  /**
   * Gets the root query by entity name.
   *
   * @param query       the query
   * @param builder     the builder
   * @param entityName  the entity name
   * @param category    the category
   * @param subCategory the sub category
   * @return the root query by entity name
   */
  private List<Object> getRootQueryByEntityName(CriteriaQuery<String> query, CriteriaBuilder builder, String entityName,
      String category, String subCategory) {
    String colName = "";
    List<Object> queryAndColName = new ArrayList<>();

    if (entityName.equals(EntityConstants.DISEASES_ENTITY)) {
      Root<Diseases> root = query.from(Diseases.class);
      if (!StringUtils.isEmpty(category)) {
        query.select(root.get(EntityConstants.DISEASES_DISEASE_NAME))
            .where(builder.and(builder.isNotNull(root.get(EntityConstants.DISEASES_DISEASES_PARENT_DISEASE)),
                builder.equal(
                    root.get(EntityConstants.DISEASES_DISEASES_PARENT_DISEASE).get(EntityConstants.DISEASES_DISEASE_ID),
                    Integer.parseInt(category))));
      } else {
        query.select(root.get(EntityConstants.DISEASES_DISEASE_NAME));
      }
      colName = EntityConstants.DISEASES_ENTITY + "." + EntityConstants.DISEASES_DISEASE_NAME;
    } else if (entityName.equals(EntityConstants.SPECIE_GROUPS_ENTITY)) {
      Root<SpeciesAndGroups> root = query.from(SpeciesAndGroups.class);
      Root<SpeciesHierarchiesTypes> spHieRoot = query.from(SpeciesHierarchiesTypes.class);
      query.select(root.get(EntityConstants.SPECIE_GROUPS_NAME)).distinct(true)
          .where(builder.and(
              builder.equal(builder.lower(root.get(EntityConstants.SPECIE_GROUPS_NAME)), category.toLowerCase()),
              builder.not(root.get(EntityConstants.SPECIE_GROUPS_SPECIE_HIER)
                  .get(EntityConstants.SPECIES_HIER_TYPES_SPEC_HIER_ID)
                  .in(spHieRoot.get(EntityConstants.SPECIES_HIER_TYPES_PARENT_SPECIE)
                      .get(EntityConstants.SPECIES_HIER_TYPES_SPEC_HIER_ID)))));
      colName = EntityConstants.SPECIE_GROUPS_ENTITY + "." + EntityConstants.SPECIE_GROUPS_NAME;
    } else if (entityName.equals(EntityConstants.DIAGNOSTIC_TEST_ENTITY)) {
      Root<DiagnosticTests> root = query.from(DiagnosticTests.class);
      query.select(root.get(EntityConstants.DIAGNOSTIC_TEST_NAME));
      colName = EntityConstants.DIAGNOSTIC_TEST_ENTITY + "." + EntityConstants.DIAGNOSTIC_TEST_NAME;
    } else if (entityName.equals(EntityConstants.LAB_ENTITY)) {
      Root<Laboratories> root = query.from(Laboratories.class);
      query.select(root.get(EntityConstants.LAB_NAME));
      colName = EntityConstants.LAB_ENTITY + "." + EntityConstants.LAB_NAME;
    } else if (entityName.equals(EntityConstants.LAB_ROLE_ENTITY)) {
      Root<LabRoles> root = query.from(LabRoles.class);
      query.select(root.get(EntityConstants.LAB_ROLE));
      colName = EntityConstants.LAB_ROLE_ENTITY + "." + EntityConstants.LAB_ROLE;
    } else if (entityName.equals(EntityConstants.CONTROL_MEASURES_ENTITY)) {
      Root<ControlMeasures> root = query.from(ControlMeasures.class);
      query.select(root.get(EntityConstants.CM_MEASURE));
      colName = EntityConstants.CONTROL_MEASURES_ENTITY + "." + EntityConstants.CM_MEASURE;
    } else if (entityName.equals(EntityConstants.CATALOG_ENTITY)) {
      Root<Catalog> root = query.from(Catalog.class);
      query.select(root.get(EntityConstants.CATALOG_KEY_VALUE))
          .where(builder.and(
              builder.equal(builder.lower(root.get(EntityConstants.CATALOG_CATEGORY)), category.toLowerCase()),
              builder.equal(builder.lower(root.get(EntityConstants.CATALOG_SUB_CATEGORY)), subCategory.toLowerCase())));
      colName = EntityConstants.CATALOG_ENTITY + "." + category + "." + subCategory;
    }

    queryAndColName.add(query);
    queryAndColName.add(colName);

    return queryAndColName;
  }

  public static boolean isNull(Object str) {
    return (str == null);

  }

  @Override
  public List<ControlMeasures> getEventLevelControlMeasures(Long epiEventId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<ControlMeasures> query = builder.createQuery(ControlMeasures.class);
    Root<EvtCmSummaries> root = query.from(EvtCmSummaries.class);
    query.select(root.get("controlMeasures"))
        .where(builder.equal(root.get("epiEventMapped").get(EntityConstants.EPI_EVT_ID), epiEventId));
    return currentSession().createQuery(query).getResultList();
  }

  @Override
  public String findOutbreakForAnimalCategory(long reportId, boolean isAquatic) {
    boolean flagWild = false;
    boolean flagDomestic = false;
    List<Long> outbreakforOutbreak = calculateOutbreaksForReports(reportId);
    List<Outbreaks> out = new ArrayList<>();
    for (Long outId : outbreakforOutbreak) {
      out.add(currentSession().find(Outbreaks.class, outId));
    }
    if (!isAquatic) {
      List<OutbreakSpeciesQuantity> outbreak = new ArrayList<>();

      flagWild = validateSpecieExistForAnimalCat(flagWild, out, outbreak, ApplicationConstants.SPECIE_WILD_TERRESTRIAL);
      flagDomestic = validateSpecieExistForAnimalCat(flagDomestic, out, outbreak,
          ApplicationConstants.SPECIE_DOMESTIC_TERRESTRIAL);
    } else {
      List<OutbreakSpeciesQuantity> outbreak = new ArrayList<>();
      flagWild = validateSpecieExistForAnimalCat(flagWild, out, outbreak,
          ApplicationConstants.SPECIE_FISHERIES_AQUATIC);
      flagDomestic = validateSpecieExistForAnimalCat(flagDomestic, out, outbreak,
          ApplicationConstants.SPECIE_AQUACULTURE_AQUATIC);
    }
    return validateControlWildAndDomestic(flagWild, flagDomestic);
  }

  private String validateControlWildAndDomestic(boolean flagWild, boolean flagDomestic) {
    if (flagWild && flagDomestic) {
      return ApplicationConstants.CONTROL_MEASURE_BOTH;
    } else if (flagWild) {
      return ApplicationConstants.CONTROL_MEASURE_WILD;
    } else if (flagDomestic) {
      return ApplicationConstants.CONTROL_MEASURE_DOMESTIC;
    } else {
      return ApplicationConstants.CONTROL_MEASURE_NONE;

    }
  }

  private boolean validateSpecieExistForAnimalCat(boolean flag, List<Outbreaks> outbreakforOutbreak,
      List<OutbreakSpeciesQuantity> outbreak, String category) {
    for (Outbreaks outbreaks : outbreakforOutbreak) {
      List<OutbreakSpeciesQuantity> species = outbreaks.getOutbreakSpeciesQuantity();
      outbreak = species.stream().filter(i -> i.getSpecie() != null)
          .filter(i -> i.getSpecie().getSpeHierarchy().getAnimalCategory().equals(category))
          .collect(Collectors.toList());
    }
    if (!outbreak.isEmpty()) {
      flag = true;
    }
    return flag;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Object> getOutbreaksColumnByEventId(long epiEventId, String fieldName) {
    List<Object> list = new ArrayList<>();
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Object> query = builder.createQuery(Object.class);
    Root<Outbreaks> outbreakRoot = query.from(Outbreaks.class);
    Join<Outbreaks, OutbreaksForEvents> outbreaksForEventsRoot = outbreakRoot
        .join(EntityConstants.OUTBREAKS_FOR_EVENTS);
    Join<OutbreaksForEvents, Report> reportRoot = outbreaksForEventsRoot.join(EntityConstants.REPORT);
    List<Predicate> criteriaList = new ArrayList<>();
    Predicate predicate1 = builder.and(builder.equal(
        outbreaksForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT).get(EntityConstants.EPI_EVT_ID),
        epiEventId));
    criteriaList.add(predicate1);
    if (fieldName.equalsIgnoreCase(EntityConstants.REPORTED_IN)) {
      return populateOubreakReportType(builder, query, reportRoot, criteriaList);
    } else if (fieldName.equalsIgnoreCase(EntityConstants.OUTBREAK_CLUSTER)) {
      return populateClusterQuantities(list, builder, query, outbreakRoot, criteriaList);
    } else {
      return populateOutbreakColumn(fieldName, builder, query, outbreakRoot, criteriaList);
    }
  }

  /**
   * .
   * @param list
   * @param builder
   * @param query
   * @param outbreakRoot
   * @param criteriaList
   * @return
   */
  private List<Object> populateClusterQuantities(List<Object> list, CriteriaBuilder builder,
      CriteriaQuery<Object> query, Root<Outbreaks> outbreakRoot, List<Predicate> criteriaList) {
    query.multiselect(outbreakRoot.get(EntityConstants.CLUSTER_QUANTITIES))
        .where(builder.and(builder.equal(outbreakRoot.get(EntityConstants.IS_CLUSTER), true)),
            builder.and(criteriaList.toArray(new Predicate[0])))
        .distinct(true);
    List<Object> clusterList = currentSession().createQuery(query).getResultList();
    for (Object objects : clusterList) {
      ClusterQuantities clusterQuantities = (ClusterQuantities) objects;
      list.add(currentSession().find(ClusterQuantities.class, clusterQuantities.getClQtyId()).getTotalOutbreaks());
    }
    Object[] myArray = list.toArray();
    return new ArrayList(Arrays.asList(myArray));
  }

  /**
   * .
   * @param builder
   * @param query
   * @param reportRoot
   * @param criteriaList
   * @return
   */
  private List<Object> populateOubreakReportType(CriteriaBuilder builder, CriteriaQuery<Object> query,
      Join<OutbreaksForEvents, Report> reportRoot, List<Predicate> criteriaList) {
    query.multiselect(reportRoot.get(EntityConstants.REPORT_TYPE), reportRoot.get(EntityConstants.Number),
        reportRoot.get(EntityConstants.REPORT_REPORT_ID));
    query.where(builder.and(criteriaList.toArray(new Predicate[0]))).distinct(true);
    return currentSession().createQuery(query).getResultList();
  }
/**
 * .
 * @param fieldName
 * @param builder
 * @param query
 * @param outbreakRoot
 * @param criteriaList
 * @return
 */
  private List<Object> populateOutbreakColumn(String fieldName, CriteriaBuilder builder, CriteriaQuery<Object> query,
      Root<Outbreaks> outbreakRoot, List<Predicate> criteriaList) {
    query.multiselect(outbreakRoot.get(fieldName));
    query.where(builder.and(criteriaList.toArray(new Predicate[0]))).distinct(true);
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Long> getChildOutbreakIds(Long outbreakListId, Long epiEventId) {
    List<Long> outbreakIds = new ArrayList<>();
    EpiEvent epiEvent = currentSession().find(EpiEvent.class, epiEventId);
    String reportType = epiEvent.getReport().getReportType();

    if (reportType.equalsIgnoreCase("FUR")) {
      getFURChildOutbreak(outbreakListId, outbreakIds);
    } else {
      getINChildOutbreak(outbreakListId, outbreakIds);
    }

    return outbreakIds;

  }

  /**
   * 
   * @param outbreakListId
   * @param outbreakIds
   */
  private void getFURChildOutbreak(Long outbreakListId, List<Long> outbreakIds) {
    Outbreaks outbreak = currentSession().find(Outbreaks.class, outbreakListId);
    Long parentOutbreakId = null;
    if (null != outbreak.getHistoricOutbreakId()) {
      parentOutbreakId = outbreak.getHistoricOutbreakId().getOutbreakId();
      outbreakIds.add(parentOutbreakId);
    } else {
      outbreakIds.add(outbreakListId);
    }

    while (null != parentOutbreakId) {
      outbreak = currentSession().find(Outbreaks.class, parentOutbreakId);
      if (null != outbreak.getHistoricOutbreakId()) {
        parentOutbreakId = outbreak.getHistoricOutbreakId().getOutbreakId();
        if (null != parentOutbreakId) {
          outbreakIds.add(parentOutbreakId);
        }
      } else {
        parentOutbreakId = null;
      }
    }
  }

  /**
   * @param outbreakListId
   * @param outbreakIds
   */
  private void getINChildOutbreak(Long outbreakListId, List<Long> outbreakIds) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Long> query = builder.createQuery(Long.class);
    Root<Outbreaks> outbreakRoot = query.from(Outbreaks.class);
    query.select(outbreakRoot.get(EntityConstants.OUTBREAK_ID))
        .where(builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_HISTORIC_OUTBREAK_ID), outbreakListId));
    Long childOutbreakId = currentSession().createQuery(query).getSingleResult();
    if (null != childOutbreakId) {
      outbreakIds.add(childOutbreakId);
    } else {
      outbreakIds.add(outbreakListId);
    }

    while (null != childOutbreakId) {
      query.select(outbreakRoot.get(EntityConstants.OUTBREAK_ID))
          .where(builder.equal(outbreakRoot.get(EntityConstants.OUTBREAK_HISTORIC_OUTBREAK_ID), childOutbreakId));
      childOutbreakId = currentSession().createQuery(query).getSingleResult();
      if (null != childOutbreakId) {
        outbreakIds.add(childOutbreakId);
      } else {
        childOutbreakId = null;
      }
    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<Object[]> getFilteredOutbreaks(@Valid OutbreaksMultiFilterModel inModel) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
    Root<Outbreaks> outbreakRoot = query.from(Outbreaks.class);
    Join<Outbreaks, OutbreaksForEvents> outbreaksForEventsRoot = outbreakRoot
        .join(EntityConstants.OUTBREAKS_FOR_EVENTS);
    Join<OutbreaksForEvents, Report> reportRoot = outbreaksForEventsRoot.join(EntityConstants.REPORT);
    List<Predicate> criteriaList = new ArrayList<>();
    Predicate predicate1 = builder.equal(
        outbreaksForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT).get(EntityConstants.EPI_EVT_ID),
        inModel.getEpiEventId());
    criteriaList.add(predicate1);
    popualteOutbreakForOieRefernce(inModel, outbreakRoot, criteriaList);
    populateOutbreakForNationalObRference(inModel, outbreakRoot, criteriaList);
    populateOutbreakForEpiUnitType(inModel, outbreakRoot, criteriaList);
    populateOutbreakForStartDate(inModel, builder, outbreakRoot, criteriaList);
    populateOutbreakForEndDate(inModel, builder, outbreakRoot, criteriaList);
    populateOutbreakForCluster(inModel, builder, outbreakRoot, criteriaList);
    populateOutbreakForArea(inModel, outbreakRoot, criteriaList);
    populateOutbreakForLocation(inModel, outbreakRoot, criteriaList);
    populateOutbreakForReportedIn(inModel, builder, reportRoot, criteriaList);
    query
        .multiselect(outbreakRoot, reportRoot.get(EntityConstants.REPORT_TYPE), reportRoot.get(EntityConstants.Number),
            reportRoot.get(EntityConstants.REPORT_REPORT_ID))
        .where(builder.and(criteriaList.toArray(new Predicate[0])));
    return currentSession().createQuery(query).getResultList();
  }

  private void popualteOutbreakForOieRefernce(OutbreaksMultiFilterModel inModel, Root<Outbreaks> outbreakRoot,
      List<Predicate> criteriaList) {
    if (!inModel.getOieReference().isEmpty() && inModel.getOieReference() != null) {
      Predicate predicate2 = outbreakRoot.get(EntityConstants.OUTBREAK_OIE_REFERENCE).in(inModel.getOieReference());
      criteriaList.add(predicate2);
    }
  }

  private void populateOutbreakForNationalObRference(OutbreaksMultiFilterModel inModel, Root<Outbreaks> outbreakRoot,
      List<Predicate> criteriaList) {
    if (!inModel.getNationalObReference().isEmpty() && inModel.getNationalObReference() != null) {
      Predicate predicate3 = outbreakRoot.get(EntityConstants.OUTBREAK_NATIONAL_OB_REFERENCE)
          .in(inModel.getNationalObReference());
      criteriaList.add(predicate3);
    }
  }

  private void populateOutbreakForEpiUnitType(OutbreaksMultiFilterModel inModel, Root<Outbreaks> outbreakRoot,
      List<Predicate> criteriaList) {
    if (!inModel.getEpiUnitType().isEmpty() && inModel.getEpiUnitType() != null) {
      Predicate predicate4 = outbreakRoot.get(EntityConstants.OUTBREAK_EPI_UNIT_TYPE).in(inModel.getEpiUnitType());
      criteriaList.add(predicate4);
    }
  }

  private void populateOutbreakForEndDate(OutbreaksMultiFilterModel inModel, CriteriaBuilder builder,
      Root<Outbreaks> outbreakRoot, List<Predicate> criteriaList) {
    if (inModel.getEndDate() != null && inModel.getEndDate().getStartStartDate() != null
        && inModel.getEndDate().getStartEndDate() != null) {
      Predicate predicate6 = builder.between(outbreakRoot.get(EntityConstants.LAB_TEST_EVOL_END_DATE),
          inModel.getEndDate().getStartStartDate(), inModel.getEndDate().getStartEndDate());
      criteriaList.add(predicate6);
    }
  }

  private void populateOutbreakForStartDate(OutbreaksMultiFilterModel inModel, CriteriaBuilder builder,
      Root<Outbreaks> outbreakRoot, List<Predicate> criteriaList) {
    if (inModel.getStartDate() != null && inModel.getStartDate().getStartStartDate() != null
        && inModel.getStartDate().getStartEndDate() != null) {
      Predicate predicate5 = builder.between(outbreakRoot.get(EntityConstants.LAB_TEST_EVOL_START_DATE),
          inModel.getStartDate().getStartStartDate(), inModel.getStartDate().getStartEndDate());
      criteriaList.add(predicate5);
    }
  }

  private void populateOutbreakForLocation(OutbreaksMultiFilterModel inModel, Root<Outbreaks> outbreakRoot,
      List<Predicate> criteriaList) {
    if (!inModel.getLocationName().isEmpty() && inModel.getLocationName() != null) {
      Predicate predicate7 = outbreakRoot.get(EntityConstants.OUTBREAK_LOCATION_NAME).in(inModel.getLocationName());
      criteriaList.add(predicate7);
    }
  }

  private void populateOutbreakForReportedIn(OutbreaksMultiFilterModel inModel, CriteriaBuilder builder,
      Join<OutbreaksForEvents, Report> reportRoot, List<Predicate> criteriaList) {
    if (!inModel.getReportIn().isEmpty() && inModel.getReportIn() != null) {
      List<String> reportType = inModel.getReportIn().stream().map(str -> str.split("-")[0])
          .collect(Collectors.toList());
      List<String> reportNum = inModel.getReportIn().stream().map(str -> str.split("-")[1])
          .collect(Collectors.toList());
      Predicate predicate10 = builder.and(reportRoot.get(EntityConstants.REPORT_TYPE).in(reportType),
          reportRoot.get(EntityConstants.Number).in(reportNum));
      criteriaList.add(predicate10);
    }
  }

  private void populateOutbreakForArea(OutbreaksMultiFilterModel inModel, Root<Outbreaks> outbreakRoot,
      List<Predicate> criteriaList) {
    if (!inModel.getLocationPoint().isEmpty() && inModel.getLocationPoint() != null) {
      List<Integer> countryId = calculateCountryId(inModel.getLocationPoint());
      Predicate predicate11 = outbreakRoot.get(EntityConstants.AREA_DETAILS_AREA_ID)
          .get(EntityConstants.AREA_DETAILS_AREA_ID).in(countryId);
      criteriaList.add(predicate11);
    }
  }

  private void populateOutbreakForCluster(OutbreaksMultiFilterModel inModel, CriteriaBuilder builder,
      Root<Outbreaks> outbreakRoot, List<Predicate> criteriaList) {
    if (!inModel.getTotalOutbreaks().isEmpty() && inModel.getTotalOutbreaks() != null) {
      if (!inModel.getTotalOutbreaks().contains(1)) {
        Join<Outbreaks, ClusterQuantities> clusterRoot = outbreakRoot.join("clusterQuantities");
        Predicate predicate8 = builder.isTrue(outbreakRoot.get(EntityConstants.IS_CLUSTER));
        Predicate predicate9 = clusterRoot.get(EntityConstants.TOTAL_OUTBREAKS).in(inModel.getTotalOutbreaks());
        criteriaList.add(predicate8);
        criteriaList.add(predicate9);
      } else if (inModel.getTotalOutbreaks().contains(1) && inModel.getTotalOutbreaks().size() == 1) {
        Predicate predicate8 = builder.isFalse(outbreakRoot.get(EntityConstants.IS_CLUSTER));
        criteriaList.add(predicate8);
      }
    }
  }

  private List<Integer> calculateCountryId(List<String> areaName) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<AreaDetails> criteriaArea = builder.createQuery(AreaDetails.class);
    Root<AreaDetails> areaDetails = criteriaArea.from(AreaDetails.class);
    criteriaArea.select(areaDetails).where(areaDetails.get(EntityConstants.DIAGNOSTIC_TEST_NAME).in(areaName));
    List<AreaDetails> result = currentSession().createQuery(criteriaArea).getResultList();
    return fetchingCountryForAdminDivision(result);
  }

  private List<Integer> fetchingCountryForAdminDivision(List<AreaDetails> result) {
    List<Integer> areaId = result.stream().map(AreaDetails::getArea).map(Area::getAreaId).collect(Collectors.toList());
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Integer> criteriaDetails = builder.createQuery(Integer.class);
    Root<AreaCompositionDetails> areaList = criteriaDetails.from(AreaCompositionDetails.class);
    criteriaDetails.multiselect(areaList.get(EntityConstants.AREA_DECOMP_ID));
    criteriaDetails.where(areaList.get(EntityConstants.AREA_DETAILS_AREA_ID).in(areaId));
    List<Integer> childId = currentSession().createQuery(criteriaDetails).getResultList();

    CriteriaQuery<Integer> criteriaComp = builder.createQuery(Integer.class);
    Root<AreaComposition> areaComposition = criteriaComp.from(AreaComposition.class);
    criteriaComp
        .multiselect(
            areaComposition.get(EntityConstants.AREA_DETAILS_AREA_AREA).get(EntityConstants.AREA_DETAILS_AREA_ID))
        .where(areaComposition.get("areaDecompId").in(childId));
    return currentSession().createQuery(criteriaComp).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Long> getEpiEventIdList(Long epiEventId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Long> query = builder.createQuery(Long.class);
    Root<EpiEvent> eventRoot = query.from(EpiEvent.class);
    query.select(eventRoot.get(EntityConstants.EPI_EVT_ID)).where(
        builder.equal(eventRoot.get(EntityConstants.FOR_HISTORY_EVENT).get(EntityConstants.EPI_EVT_ID), epiEventId));
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getEventByOutbreak(long outbreakId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Long> query = builder.createQuery(Long.class);
    Root<OutbreaksForEvents> outbreaksForEventsRoot = query.from(OutbreaksForEvents.class);
    query
        .select(
            outbreaksForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENT_EPI_EVENT).get(EntityConstants.EPI_EVT_ID))
        .where(builder.equal(
            outbreaksForEventsRoot.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID),
            outbreakId));
    return currentSession().createQuery(query).uniqueResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override

  public Set<OutbreakDiagnosticTests> getResultForOutbreakLinkDiagnostic(Long outbreakId) {
    Set<OutbreakDiagnosticTests> outbreakDiagnosticTestsSet = new HashSet<>();
    Outbreaks outbreak = currentSession().find(Outbreaks.class, outbreakId);
    if (outbreak.getOutbreakDiagTests() != null) {
      return outbreak.getOutbreakDiagTests();
    } else {
      return outbreakDiagnosticTestsSet;
    }
  }

  @Override
  public List<Long> getLabTestSummaryIdByEventAndDiagTest(Long epiEventId, Integer diagTestId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Long> query = builder.createQuery(Long.class);
    Root<LabTestsSummaries> ltsRoot = query.from(LabTestsSummaries.class);
    query.select(ltsRoot.get(EntityConstants.LABORATORY_TEST_SUMMARY_ID))
        .where(builder.and(
            builder.equal(ltsRoot.get(EntityConstants.LABORATORY_TEST_FOR_EVENTS).get(EntityConstants.EPI_EVT_ID),
                epiEventId),
            builder.equal(ltsRoot.get(EntityConstants.LAB_TEST_SUM_DIAG_TEST).get(EntityConstants.DIAGNOSTIC_TEST_ID),
                diagTestId)));
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<LaboratoryForTestsSum> getLabIdsByLabTestSummaryIds(List<Long> ltsId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<LaboratoryForTestsSum> query = builder.createQuery(LaboratoryForTestsSum.class);
    Root<LaboratoryForTestsSum> laboratoryForTestsSumRoot = query.from(LaboratoryForTestsSum.class);
    In<Long> in = builder.in(laboratoryForTestsSumRoot.get(EntityConstants.LAB_TEST_SUMMARIES));
    ltsId.forEach(in::value);
    query.select(laboratoryForTestsSumRoot).where(in);
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<LabTestSummariesSpecies> getSpecieIdsByLabTestSummaryIds(List<Long> ltsId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<LabTestSummariesSpecies> query = builder.createQuery(LabTestSummariesSpecies.class);
    Root<LabTestSummariesSpecies> labTestSummariesSpeciesRoot = query.from(LabTestSummariesSpecies.class);
    In<Long> in = builder.in(labTestSummariesSpeciesRoot.get(EntityConstants.LAB_TEST_SUMMARIES));
    ltsId.forEach(in::value);
    query.select(labTestSummariesSpeciesRoot).where(in);

    return currentSession().createQuery(query).getResultList();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AdministrativeDivisionDto> getAdminDivision(@NotNull Double lattitude, @NotNull Double longitude) {
    List<AdministrativeDivisionDto> dtoList = new ArrayList<>();
    Session session = currentSession().unwrap(Session.class);
    String query = SqlConstants.FETCH_ADMIN_DIVISION;
    final String finalQuery = query;
    return session.doReturningWork(connection -> {
      PreparedStatement pstmt = connection.prepareStatement(finalQuery);
      pstmt.setDouble(1, longitude);
      pstmt.setDouble(2, lattitude);
      pstmt.execute();
      ResultSet rs = pstmt.getResultSet();
      while (rs.next()) {
        AdministrativeDivisionDto cmDto = new AdministrativeDivisionDto();
        cmDto.setAreaName(rs.getString(4));
        cmDto.setlabel(rs.getString(5));
        dtoList.add(cmDto);
      }
      return dtoList;
    });
  }

  /**
   * Gets the outbreak id.
   *
   * @param epiEventid the epi eventid
   * @return the outbreak id
   */
  private List<Long> getOutbreakId(List<Long> epiEventid) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Long> query = builder.createQuery(Long.class);
    Root<OutbreaksForEvents> root = query.from(OutbreaksForEvents.class);
    query.select(root.get("outbreak").get(EntityConstants.OUTBREAK_ID))
        .where(root.get("epiEvent").get(EntityConstants.EPI_EVT_ID).in(epiEventid));
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * Gets the history outbreak ids.
   *
   * @param furOutbreakId the fur outbreak id
   * @return the history outbreak ids
   */
  @Override
  public List<Long> gethistoryOutbreakIds(List<Long> furOutbreakId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Long> query = builder.createQuery(Long.class);
    Root<Outbreaks> root = query.from(Outbreaks.class);
    query.select(root.get(EntityConstants.OUTBREAK_HISTORIC_OUTBREAK_ID).get(EntityConstants.OUTBREAK_ID))
        .where(builder.and(root.get(EntityConstants.OUTBREAK_ID).in(furOutbreakId)),
            builder
                .isNotNull(root.get(EntityConstants.OUTBREAK_HISTORIC_OUTBREAK_ID).get(EntityConstants.OUTBREAK_ID)));
    return currentSession().createQuery(query).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ValidationDto deleteBulkOutbreakForInFur(Set<Long> outbreakId) {
    ValidationDto validateDto;
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaDelete<OutbreaksForEvents> delete = builder.createCriteriaDelete(OutbreaksForEvents.class);
    Root<OutbreaksForEvents> outbreakRootdelete = delete.from(OutbreaksForEvents.class);
    delete.where(outbreakRootdelete.get(EntityConstants.OUTBREAK_FOR_EVENTS_OUTBREAK).get(EntityConstants.OUTBREAK_ID)
        .in(outbreakId));
    int numEntitiesDeleted = currentSession().createQuery(delete).executeUpdate();
    if (numEntitiesDeleted == 0) {
      validateDto = new ValidationDto(false, "An unexpected Error occured. Please Contact Administrator");
      return validateDto;
    } else if (numEntitiesDeleted > 0) {
      validateDto = new ValidationDto(true, "deletion is completed");
      return validateDto;

    }
    return null;
  }

  @Override
  public Set<Long> getEpiEventIdList(List<Long> outbreakIdList) {
    Set<Long> epiEventIdList = new LinkedHashSet<>();
    outbreakIdList.forEach(outbreakId -> {
      Outbreaks outbreak = currentSession().find(Outbreaks.class, outbreakId);
      Optional<OutbreaksForEvents> outbreaksForEvents = outbreak.getOutbreaksForEvents().stream().findFirst();
      if (outbreaksForEvents.isPresent()) {
        epiEventIdList.add(outbreaksForEvents.get().getEpiEvent().getEpiEventId());
      }
    });

    return epiEventIdList;
  }
  
  /**
   * {@inheritDoc}
   */
  
    @Override
	public List<Outbreaks> validateReasonForOutbreak(@NotNull Integer areaId, Integer reportType) {
		CriteriaBuilder builder = currentSession().getCriteriaBuilder();
		CriteriaQuery<Outbreaks> query = builder.createQuery(Outbreaks.class);
		Root<Outbreaks> outbreakRoot = query.from(Outbreaks.class);
		Join<Outbreaks, OutbreaksForEvents> outbreaksForEventsRoot = outbreakRoot
				.join(EntityConstants.OUTBREAKS_FOR_EVENTS);
		query.select(outbreakRoot)
				.where(builder.and(builder.equal(
						outbreakRoot.get(EntityConstants.REPORT_AREA_ID).get(EntityConstants.REPORT_AREA_ID), areaId),
						builder.notEqual(outbreaksForEventsRoot.get(EntityConstants.REPORT_GUI_SELECT_REPORT)
								.get(EntityConstants.REPORT_REPORT_ID), reportType)));
		return currentSession().createQuery(query).getResultList();
	}

  /**
   * {@inheritDoc}
   */
	@Override
	public List<Long> getFurOubreaksModification(Long epiEventId) {
		ArrayList<Long> eventId = new ArrayList<>();
		eventId.add(epiEventId);
		List<Long> outbreakIds = getOutbreakId(eventId);
		if (!outbreakIds.isEmpty()) {
			return gethistoryOutbreakIds(outbreakIds);
		}
		return new ArrayList<>();

	}
	
	/**
	 * Gets the outbreak species quantity details.
	 *
	 * @param outbreakIdList the outbreak id list
	 * @return the outbreak species quantity details
	 */
	private List<OutbreakSpeciesQuantity> getOutbreakSpeciesQuantityDetails(List<Long> outbreakIdList) {
		CriteriaBuilder builder = currentSession().getCriteriaBuilder();
		CriteriaQuery<OutbreakSpeciesQuantity> query = builder.createQuery(OutbreakSpeciesQuantity.class);
		Root<OutbreakSpeciesQuantity> outbreakSpecieRoot = query.from(OutbreakSpeciesQuantity.class);
		query.select(outbreakSpecieRoot).where(outbreakSpecieRoot.get("outbreak").get("outbreakId").in(outbreakIdList));
		return currentSession().createQuery(query).getResultList();
	}

  /**
   * {@inheritDoc}
   */
  @Override
  public void checkExistingOutbreakEntryAndDelete(Long outbreakId) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<OutbreakDiagnosticNature> query = builder.createQuery(OutbreakDiagnosticNature.class);
    Root<OutbreakDiagnosticNature> outbreakDnRoot = query.from(OutbreakDiagnosticNature.class);
    query.select(outbreakDnRoot)
        .where(builder.equal(
            outbreakDnRoot.get(EntityConstants.OUTBREAK_DIAGNOSTIC_NATURE_OUTBREAK).get(EntityConstants.OUTBREAK_ID),
            outbreakId));
    List<OutbreakDiagnosticNature> outbreaksDnList = currentSession().createQuery(query).getResultList();
    if (!outbreaksDnList.isEmpty()) {
      outbreaksDnList.forEach(outbreakDn -> currentSession().delete(outbreakDn));
    }
  }

}
