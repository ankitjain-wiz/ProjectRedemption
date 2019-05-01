package com.wahisplus.in.dao.outbreak;

import com.wahisplus.in.dao.eventinfo.EventInformationDao;
import com.wahisplus.wcommon.dao.GenericDaoImpl;
import com.wahisplus.wcommon.domain.event.EpiEvent;
import com.wahisplus.wcommon.domain.quantities.QuantitiesSummaryEvolution;
import com.wahisplus.wcommon.domain.quantities.QuantitySummaries;
import com.wahisplus.wcommon.domain.report.Report;
import com.wahisplus.wcommon.domain.species.SpeciesAndGroups;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * This class is used to save and update the quatitive date.
 * 
 * @author soprasteria.
 *
 */

@Repository
public class QuantitiveDaoImpl extends GenericDaoImpl<Report, Long> implements QuantitiveDao {

  @Autowired
  private EventInformationDao eventInformationDao;

  /**
   * {@inheritDoc}
   */
  @Override
  public void saveAndUpdateQuantitiveDetails(List<Long> epiEventIdList) {
    epiEventIdList.forEach(epiEventId -> {
      EpiEvent epiEvent = currentSession().find(EpiEvent.class, epiEventId);
      Report report = epiEvent.getReport();
      if (epiEventId != null) {
        List<Object[]> dateList = eventInformationDao.getOutbreakEventsDate(epiEventId);
        List<Object[]> eventQuantitiesDetails = eventInformationDao.getEventQuantitiesDetails(epiEventId);
        if (!dateList.isEmpty()) {
          setReportDate(epiEvent, report, dateList);
        }
        setEventQuantitiesDetails(eventQuantitiesDetails, epiEvent);
      }
    });

  }

  /**
   * Sets the report date.
   *
   * @param epiEvent the epi event
   * @param report   the report
   * @param dateList the date list
   */
  private void setReportDate(EpiEvent epiEvent, Report report, List<Object[]> dateList) {
    for (Object[] obj : dateList) {
      if (obj[0] instanceof Date) {
        report.setReportingDate((Date) obj[0]);
        epiEvent.setStartDate((Date) obj[0]);
      }
      if (obj[1] instanceof Date) {
        epiEvent.setCloseDate((Date) obj[1]);
      }
    }
  }

  /**
   * Sets the event quantities details.
   *
   * @param eventQuantitiesDetails the event quantities details
   * @param epiEvent               the epi event
   */
  private void setEventQuantitiesDetails(List<Object[]> eventQuantitiesDetails, EpiEvent epiEvent) {
    List<QuantitySummaries> quantitySummariesList = null;
    List<QuantitiesSummaryEvolution> quantitiesSummaryEvolutionList = null;
    if (eventQuantitiesDetails != null) {
      quantitySummariesList = new ArrayList<>();
      quantitiesSummaryEvolutionList = new ArrayList<>();
      for (Object[] obj : eventQuantitiesDetails) {
        QuantitySummaries quantitySummaries = null;
        QuantitiesSummaryEvolution quantitiesSummaryEvolution = new QuantitiesSummaryEvolution();
        SpeciesAndGroups species = currentSession().find(SpeciesAndGroups.class, obj[0]);

        quantitySummaries = eventInformationDao.getQuantitySummaries(epiEvent.getEpiEventId(), species.getSpecieId());
        if (null != quantitySummaries) {
          if (quantitySummaries.getQuantitiesSummaryEvolutionList() != null
              && !quantitySummaries.getQuantitiesSummaryEvolutionList().isEmpty()) {
            quantitiesSummaryEvolution = (quantitySummaries.getQuantitiesSummaryEvolutionList().get(0));
            quantitiesSummaryEvolutionList = quantitySummaries.getQuantitiesSummaryEvolutionList();
          } else {
            quantitiesSummaryEvolution = new QuantitiesSummaryEvolution();
            quantitiesSummaryEvolutionList = new ArrayList<>();
          }
        } else {
          quantitySummaries = new QuantitySummaries();
        }
        quantitySummaries.setEpiEventForSummary(epiEvent);
        quantitySummaries.setSpecies(species);
        quantitiesSummaryEvolution.setQtySumUnit((String) obj[1]);
        quantitiesSummaryEvolution.setQtySumType((String) obj[2]);
        quantitiesSummaryEvolution.setQtySumSusceptible((int) (long) obj[3]);
        quantitiesSummaryEvolution.setQtySumKilledDisposed((int) (long) obj[4]);
        quantitiesSummaryEvolution.setQtySumSlaughtered((int) (long) obj[5]);
        quantitiesSummaryEvolution.setQtySumNcase((int) (long) obj[6]);
        quantitiesSummaryEvolution.setQtySumVaccinated((int) (long) obj[7]);
        quantitiesSummaryEvolution.setQtySumMorbidity((Double) obj[8]);
        quantitiesSummaryEvolution.setQtySumMortality((Double) obj[9]);
        quantitiesSummaryEvolution.setQtySumDead((int) (long) obj[10]);
        quantitiesSummaryEvolution.setQtySummary(quantitySummaries);
        quantitiesSummaryEvolutionList.add(quantitiesSummaryEvolution);
        quantitySummaries.getQuantitiesSummaryEvolutionList().addAll(quantitiesSummaryEvolutionList);
        quantitySummariesList.add(quantitySummaries);
        currentSession().saveOrUpdate(quantitySummaries);
      }
      epiEvent.setEvtSummariesForEvent(quantitySummariesList);
      currentSession().saveOrUpdate(epiEvent);
    }

  }

}
