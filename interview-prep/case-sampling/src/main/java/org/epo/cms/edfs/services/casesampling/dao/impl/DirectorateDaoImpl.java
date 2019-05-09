package org.epo.cms.edfs.services.casesampling.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.casesampling.dao.DirectorateDao;
import org.epo.cms.edfs.services.casesampling.dao.DirectorateWorkloadSettingDao;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingExaminerStatusDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateWorkloadSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.CaseSamplingRequest;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingExaminerStatus;
import org.epo.cms.edfs.services.dossierpersistence.entity.Directorate;
import org.epo.cms.edfs.services.dossierpersistence.entity.DirectorateWorkloadSetting;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Implementation of DirectorateDao.
 * 
 * @author amigarg
 *
 */
@Repository
public class DirectorateDaoImpl extends GenericDaoImpl<Directorate, Integer> implements DirectorateDao {

	private static final String WORKLOAD = "workload";

	private static final String DIRECTORATE_WORKLOAD_SETTINGS = "directorateWorkloadSettings";

	private static final String DIRECTORATE_ID = "directorateId";

	@Autowired
	private DirectorateWorkloadSettingDao directorateWorkloadSettingDao;

	@Autowired
	ResponseValidator responseValidator;

	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public DirectorateWorkloadSettingDto getDirectorateWorkloadSetting(long directorateId, int year)
			throws CustomException {

		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq(DIRECTORATE_ID, directorateId));
		criteria.createAlias(DIRECTORATE_WORKLOAD_SETTINGS, WORKLOAD);
		criteria.add(Restrictions.eq("workload.year", year));
		Directorate directorate = (Directorate) criteria.uniqueResult();
		DirectorateWorkloadSettingDto workloadSettingDto = null;
		if (!ObjectUtils.isEmpty(directorate)) {
			DirectorateWorkloadSetting directorateWorkloadSetting = directorate.getDirectorateWorkloadSettings().get(0);
			workloadSettingDto = directorateWorkloadSettingDao
					.workloadSettingEntityDtoMapper(directorateWorkloadSetting);
			LOGGER.debug("June Count: {} ", directorateWorkloadSetting.getJunActualCount());
		}

		return workloadSettingDto;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Long> getDirectorateIds() throws CustomException {

		List<Long> idList;
		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.setProjection(
				Projections.distinct(Projections.projectionList().add(Projections.property(DIRECTORATE_ID))));
		idList = criteria.list();
		return idList;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public DirectorateDto getDirectorateAllExaminers(long directorateId, int year) throws CustomException {

		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq(DIRECTORATE_ID, directorateId));
		criteria.createAlias("caseSamplingExaminerStatus", "examinerStatus");
		criteria.add(Restrictions.eq("examinerStatus.year", year));
		Directorate directorate = (Directorate) criteria.uniqueResult();
		List<CaseSamplingExaminerStatusDto> examinerStatusDto = new ArrayList<>();
		DirectorateWorkloadSettingDto workloadSettingDto;
		DirectorateDto directorateDto = null;
		if (!ObjectUtils.isEmpty(directorate)) {
			DirectorateWorkloadSetting workloadSetting = directorate.getDirectorateWorkloadSettings().get(0);
			workloadSettingDto = directorateWorkloadSettingDao.workloadSettingEntityDtoMapper(workloadSetting);
			List<CaseSamplingExaminerStatus> caseSamplingExaminerStatus = directorate.getCaseSamplingExaminerStatus();
			examinerStatusDto = directorAllexaminerEntityDtoMapper(caseSamplingExaminerStatus, examinerStatusDto);
			directorateDto = directorateEntityDtoMapper(directorate, examinerStatusDto, workloadSettingDto);
		}

		return directorateDto;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void incrementDirectorateWorkloadCount(long directorateId, String month, int year) throws CustomException {

		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq(DIRECTORATE_ID, directorateId));
		criteria.createAlias(DIRECTORATE_WORKLOAD_SETTINGS, WORKLOAD);
		criteria.add(Restrictions.eq("workload.year", year));
		Directorate directorate = (Directorate) criteria.uniqueResult();
		if (!ObjectUtils.isEmpty(directorate)) {
			DirectorateWorkloadSetting directorateWorkloadSetting = directorate.getDirectorateWorkloadSettings().get(0);
			updateMonthCount(directorateWorkloadSetting, month);
		}
		saveOrUpdate(directorate);

	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Directorate updateDirectorateMaxtarget(long directorateId, int year, int maxTarget) throws CustomException {

		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq(DIRECTORATE_ID, directorateId));
		criteria.createAlias(DIRECTORATE_WORKLOAD_SETTINGS, WORKLOAD);
		criteria.add(Restrictions.eq("workload.year", year));
		Directorate directorate = (Directorate) criteria.uniqueResult();
		if (!ObjectUtils.isEmpty(directorate)) {
			directorate.getDirectorateWorkloadSettings().get(0).setMaxTarget(maxTarget);
		}
		saveOrUpdate(directorate);
		return directorate;

	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public DirectorateDto directorateEntityDtoMapper(Directorate directorate,
			List<CaseSamplingExaminerStatusDto> caseSamplingExaminerStatus,
			DirectorateWorkloadSettingDto workloadSettingDto) {
		DirectorateDto directorateDto = new DirectorateDto();
		directorateDto.setDirectorateId(directorate.getDirectorateId());
		directorateDto.setDirectorateName(directorate.getDirectorateName());
		directorateDto.setDirectorId(directorate.getDirectorId());
		directorateDto.setCaseSamplingExaminerStatus(caseSamplingExaminerStatus);
		directorateDto.setDirectorateWorkloadSettings(workloadSettingDto);
		return directorateDto;
	}

	/**
	 * This method is used to update Directorate workload count for
	 * corresponding month.
	 * 
	 * @param directorateWorkloadSetting
	 *            of type DirectorateWorkloadSetting
	 * @param month
	 *            of type String
	 * @return DirectorateWorkloadSetting as value
	 */
	private DirectorateWorkloadSetting updateMonthCount(DirectorateWorkloadSetting directorateWorkloadSetting,
			String month) {

		int currentWorkloadCount;

		switch (month) {
		case "JANUARY":
			currentWorkloadCount = directorateWorkloadSetting.getJanActualCount();
			directorateWorkloadSetting.setJanActualCount(currentWorkloadCount + 1);
			break;
		case "FEBRUARY":
			currentWorkloadCount = directorateWorkloadSetting.getFebActualCount();
			directorateWorkloadSetting.setFebActualCount(currentWorkloadCount + 1);
			break;
		case "MARCH":
			currentWorkloadCount = directorateWorkloadSetting.getMarActualCount();
			directorateWorkloadSetting.setMarActualCount(currentWorkloadCount + 1);
			break;
		case "APRIL":
			currentWorkloadCount = directorateWorkloadSetting.getAprActualCount();
			directorateWorkloadSetting.setAprActualCount(currentWorkloadCount + 1);
			break;
		case "MAY":
			currentWorkloadCount = directorateWorkloadSetting.getMayActualCount();
			directorateWorkloadSetting.setMayActualCount(currentWorkloadCount + 1);
			break;
		case "JUNE":
			currentWorkloadCount = directorateWorkloadSetting.getJunActualCount();
			directorateWorkloadSetting.setJunActualCount(currentWorkloadCount + 1);
			break;
		case "JULY":
			currentWorkloadCount = directorateWorkloadSetting.getJulActualCount();
			directorateWorkloadSetting.setJulActualCount(currentWorkloadCount + 1);
			break;
		case "AUGUST":
			currentWorkloadCount = directorateWorkloadSetting.getAugActualCount();
			directorateWorkloadSetting.setAugActualCount(currentWorkloadCount + 1);
			break;
		case "SEPTEMBER":
			currentWorkloadCount = directorateWorkloadSetting.getSepActualCount();
			directorateWorkloadSetting.setSepActualCount(currentWorkloadCount + 1);
			break;
		case "OCTOBER":
			currentWorkloadCount = directorateWorkloadSetting.getOctActualCount();
			directorateWorkloadSetting.setOctActualCount(currentWorkloadCount + 1);
			break;
		case "NOVEMBER":
			currentWorkloadCount = directorateWorkloadSetting.getNovActualCount();
			directorateWorkloadSetting.setNovActualCount(currentWorkloadCount + 1);
			break;
		case "DECEMBER":
			currentWorkloadCount = directorateWorkloadSetting.getDecActualCount();
			directorateWorkloadSetting.setDecActualCount(currentWorkloadCount + 1);
			break;
		default:
			break;

		}
		return directorateWorkloadSetting;
	}

	/**
	 * This method is used to map CaseSamplingExaminerStatus Entity with its DTO
	 * class.
	 * 
	 * @param caseSamplingExaminerStatusList
	 *            of type List
	 * @param examinerStatusDto
	 *            of type List
	 * @return List of type CaseSamplingExaminerStatusDto
	 */
	private List<CaseSamplingExaminerStatusDto> directorAllexaminerEntityDtoMapper(
			List<CaseSamplingExaminerStatus> caseSamplingExaminerStatusList,
			List<CaseSamplingExaminerStatusDto> examinerStatusDto) {

		for (CaseSamplingExaminerStatus caseSamplingExaminerStatus : caseSamplingExaminerStatusList) {
			CaseSamplingExaminerStatusDto examiner = new CaseSamplingExaminerStatusDto();
			examiner.setCaseSamplingExaminerStatusId(caseSamplingExaminerStatus.getCaseSamplingExaminerStatusId());
			examiner.setNumPax(caseSamplingExaminerStatus.getNumPAX());
			examiner.setNumSampled(caseSamplingExaminerStatus.getNumSampled());
			examiner.setNumSubmitted(caseSamplingExaminerStatus.getNumSubmitted());
			examiner.setUserId(caseSamplingExaminerStatus.getUserId());
			examiner.setYear(caseSamplingExaminerStatus.getYear());
			examiner.setDirectorate(caseSamplingExaminerStatus.getDirectorate());
			examinerStatusDto.add(examiner);
		}

		return examinerStatusDto;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int updateDirectorate(Directorate directorate, CaseSamplingRequest caseSamplingRequest) {

		saveOrUpdate(directorate);
		return 1;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Directorate isDirectorateExist(String directorateName) {
		Directorate directorate ;

		
			Criteria criteria = currentSession().createCriteria(daoType);
			criteria.add(Restrictions.eq("directorateName", directorateName));
			directorate = (Directorate) criteria.uniqueResult();
			if (!ObjectUtils.isEmpty(directorate)) {
				directorate.getCaseSamplingExaminerStatus();
				directorate.getDirectorateWorkloadSettings();
			}

		

		return directorate;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public List<DirectorateDto> getDirectoratesList() throws CustomException {

		Criteria criteria = currentSession().createCriteria(daoType);

		List<Directorate> directoratesList = criteria.list();
		List<DirectorateDto> directoratesDtoList = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(directoratesList)) {
			for (Directorate directorate : directoratesList) {
				DirectorateDto directorateDto = new DirectorateDto();
				directorateDto.setDirectorateId(directorate.getDirectorateId());
				directorateDto.setDirectorId(directorate.getDirectorId());
				directorateDto.setDirectorateName(directorate.getDirectorateName());
				directoratesDtoList.add(directorateDto);

			}
		}

		return directoratesDtoList;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public Directorate isDirectorateExist(long directorateId) {
		Directorate directorate;

		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq(DIRECTORATE_ID, directorateId));
		directorate = (Directorate) criteria.uniqueResult();
		if (!ObjectUtils.isEmpty(directorate)) {
			directorate.getCaseSamplingExaminerStatus();
			directorate.getDirectorateWorkloadSettings();
		}

		return directorate;
	}

}
