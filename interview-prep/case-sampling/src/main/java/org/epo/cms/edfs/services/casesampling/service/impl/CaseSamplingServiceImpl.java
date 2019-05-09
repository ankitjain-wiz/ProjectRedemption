package org.epo.cms.edfs.services.casesampling.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingAdminSettingDao;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingExaminerStatusDao;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingSelectDao;
import org.epo.cms.edfs.services.casesampling.dao.DirectorateDao;
import org.epo.cms.edfs.services.casesampling.dao.DirectorateWorkloadSettingDao;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingAdminSettingDto;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingExaminerStatusDto;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingSelectDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateWorkloadSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.CaseSamplingExaminerStatusResponse;
import org.epo.cms.edfs.services.casesampling.pojo.CaseSamplingRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataResponse;
import org.epo.cms.edfs.services.casesampling.pojo.SamplingInput;
import org.epo.cms.edfs.services.casesampling.pojo.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.casesampling.service.CaseSamplingService;
import org.epo.cms.edfs.services.casesampling.service.DirectorateService;
import org.epo.cms.edfs.services.casesampling.service.RandomSamplingService;
import org.epo.cms.edfs.services.casesampling.util.DateTime;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ExceptionHandlerBean;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.util.Constants;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingExaminerStatus;
import org.epo.cms.edfs.services.dossierpersistence.entity.Directorate;
import org.epo.cms.edfs.services.dossierpersistence.entity.DirectorateWorkloadSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Case Sampling Implementation.
 * 
 * @author amigarg
 *
 */

@Service
public class CaseSamplingServiceImpl implements CaseSamplingService {

	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	private DirectorateService directorateSamplingService;

	@Autowired
	private RandomSamplingService randomSamplingService;

	@Autowired
	private CaseSamplingExaminerStatusDao caseSamplingExaminerStatusDao;

	@Autowired
	private CaseSamplingAdminSettingDao caseSamplingAdminSettingDao;

	@Autowired
	private CaseSamplingSelectDao caseSamplingSelectDao;

	@Autowired
	private DirectorateDao directorateDao;

	@Autowired
	private DateTime dateTime;

	@Autowired
	private ResponseValidator responseValidator;

	@Autowired
	private DirectorateWorkloadSettingDao directorateWorkloadSettingDao;

	@Autowired
	private UpdaterSettingsResponse updaterSettingsResponse;

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean isCaseCheckRequired(String userId,String testMonth) throws CustomException {

		CaseSamplingExaminerStatusDto examinerStatusDto;
		CaseSamplingAdminSettingDto adminSettingDto;
		CaseSamplingSelectDto caseSamplingSelectDto;

		boolean caseCheckRequired = false;
		int year = dateTime.getYear();
		LOGGER.debug("user id : {}", userId);
		SamplingInput samplingInput = new SamplingInput();
		samplingInput.setUserId(userId);
		
		//TestChangeForReport
		/*samplingInput.setMonth(dateTime.getMonth());*/
		samplingInput.setMonth(testMonth);
		
		samplingInput.setYear(year);
		
		try {
			adminSettingDto = caseSamplingAdminSettingDao.getCaseSamplingAdminSetting();
			caseSamplingSelectDto = caseSamplingSelectDao.getSamplingDetail(userId);
			examinerStatusDto = caseSamplingExaminerStatusDao.getExaminerStatus(userId, year);
		} catch (CustomException ce) {
			LOGGER.error("Exception in data fetching : {}", ce.getMessage());
			throw ce;
		} catch (Exception e) {
			LOGGER.error("Exception in data fetching : {}", e.getMessage());
			throw e;
		}

		if (adminSettingDto != null && examinerStatusDto != null && caseSamplingSelectDto != null) {
			samplingInput.setNumMinYearTarget(adminSettingDto.getNumMinYearTarget());
			samplingInput.setNumMaxYearTarget(adminSettingDto.getNumMaxYearTarget());
			samplingInput.setNumMustSample(adminSettingDto.getNumMustSample());
			samplingInput.setNumSubmitted(examinerStatusDto.getNumSubmitted());
			samplingInput.setNumSampled(examinerStatusDto.getNumSampled());
			samplingInput.setNumPax(examinerStatusDto.getNumPax());
			samplingInput.setDirectorateId(examinerStatusDto.getDirectorate().getDirectorateId());
			samplingInput.setNumNextToSample(caseSamplingSelectDto.getNumPlannedToSample());
			
		} else {
			ExceptionHandlerBean exBean;
			exBean = responseValidator.getErrorResponse(Integer.parseInt(Constants.PRECONDITION));
			throw new CustomException(exBean);
		}

		LOGGER.debug("caseCheckRequired : {}", caseCheckRequired);
		caseCheckRequired = caseSamplingProcessCheck(samplingInput);
		return caseCheckRequired;
	}

	/**
	 * Case Sampling Algorithm.
	 * 
	 * @param samplingInput
	 *            of type SamplingInput
	 * @return boolean as value
	 * @throws CustomException
	 *             of type Custom Exception
	 */
	private boolean caseSamplingProcessCheck(SamplingInput samplingInput) throws CustomException {

		boolean caseCheckRequired;
		
		//TestChangeForReport
		boolean directorateTargetReached = directorateSamplingService
				.isDirectorateMonthTargetReached(samplingInput.getDirectorateId(),samplingInput.getMonth());
		
		LOGGER.debug("directorateTargetReached : {}", directorateTargetReached);
		CaseSamplingExaminerStatusDto examinerStatus = caseSamplingExaminerStatusDao
				.updateNumSubmitted(samplingInput.getUserId(), samplingInput.getYear());

		if (!ObjectUtils.isEmpty(examinerStatus)) {
			samplingInput.setNumSubmitted(examinerStatus.getNumSubmitted());
		}

		if (samplingInput.getNumNextToSample() == samplingInput.getNumSubmitted()) {
			boolean isMustSampled = isMustSampled(samplingInput,directorateTargetReached);
			if (isMustSampled == true) {
				caseCheckRequired = true;
				caseSamplingSubProcessCheck(samplingInput);

			} else {
				if (directorateTargetReached == false) {
					caseCheckRequired = true;
					caseSamplingSubProcessCheck(samplingInput);

				} else {
					
					//ankit19-7-2018
					if(samplingInput.getNumSubmitted()<samplingInput.getNumPax()){
						caseSamplingSelectDao.updateNextNumToSampleByOne(samplingInput.getUserId());
					}
					
					caseCheckRequired = false;
				}
			}
		} else {
			caseCheckRequired = false;
		}
		return caseCheckRequired;
	}

	/**
	 * Subprocess of Case Sampling Algorithm.
	 * 
	 * @param samplingInput
	 *            of type SamplingInput
	 * @return boolean as value
	 * @throws CustomException
	 *             of type Custom Exception
	 */
	private boolean caseSamplingSubProcessCheck(SamplingInput samplingInput) throws CustomException {
		boolean subCaseCheck = true;
		Directorate directorate = null;
		int maxTarget = directorateSamplingService.getDirectorateMaxTarget(samplingInput.getDirectorateId());
		CaseSamplingExaminerStatusDto examinerStatus1 = caseSamplingExaminerStatusDao
				.updateNumSampled(samplingInput.getUserId(), samplingInput.getYear());
		if (!ObjectUtils.isEmpty(examinerStatus1)) {
			samplingInput.setNumSampled(examinerStatus1.getNumSampled());
			directorate = examinerStatus1.getDirectorate();
		}
		boolean maxTargetReached = directorateSamplingService.isAllExaminerMaxTargetReached(directorate);
		directorateDao.incrementDirectorateWorkloadCount(samplingInput.getDirectorateId(), samplingInput.getMonth(),
				samplingInput.getYear());
		if (samplingInput.getNumSampled() == maxTarget) {
			if (maxTargetReached == true) {
				if ((maxTarget == samplingInput.getNumMinYearTarget())
						&& (samplingInput.getNumMinYearTarget() != samplingInput.getNumMaxYearTarget())) {
					maxTarget = samplingInput.getNumMaxYearTarget();
					Directorate uDirectorate = directorateDao.updateDirectorateMaxtarget(
							samplingInput.getDirectorateId(), samplingInput.getYear(), maxTarget);
					randomSamplingService.generateNextNumToSampleForDirectorate(uDirectorate);
				} else {
					caseSamplingSelectDao.updateSamplingStatus(samplingInput.getUserId());
				}
			} else {
				caseSamplingSelectDao.updateSamplingStatus(samplingInput.getUserId());
			}
		} else {
			randomSamplingService.generateNextNumToSampleForExaminer(samplingInput.getUserId(), false);
		}

		return subCaseCheck;
	}

	/**
	 * check whether Examiner must be sampled.
	 * 
	 * @param samplingInput
	 *            of type SamplingInput
	 * @return boolean as value
	 * @throws CustomException
	 *             of type Custom Exception
	 */
	private boolean isMustSampled(SamplingInput samplingInput,boolean directorateTargetReached) throws CustomException {

		boolean mustSampledFlag = false;
		int maxTarget = directorateSamplingService.getDirectorateMaxTarget(samplingInput.getDirectorateId());
		
		//ankit19-7-2018
		if(maxTarget!=samplingInput.getNumMinYearTarget() && directorateTargetReached)
		{
			return false;
		}

		
		if (samplingInput.getNumMustSample() < (maxTarget - samplingInput.getNumSampled())) {
			samplingInput.setNumMustSample(maxTarget - samplingInput.getNumSampled());
		}

		if (((samplingInput.getNumPax() - samplingInput.getNumSubmitted()) < samplingInput.getNumMustSample())
				&& (samplingInput.getNumSampled() < maxTarget)) {
			mustSampledFlag = true;
		}
		return mustSampledFlag;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int updateCaseSamplingData(CaseSamplingRequest caseSamplingRequest) {

		Directorate directorate = new Directorate();

		DirectorateWorkloadSetting directorateWorkload = new DirectorateWorkloadSetting();
		CaseSamplingExaminerStatus caseSamplingExaminer = new CaseSamplingExaminerStatus();
		List<CaseSamplingExaminerStatus> caseSamplingExaminerStatus = new ArrayList<>();
		List<DirectorateWorkloadSetting> directorateWorkloadSettings = new ArrayList<>();

		directorateWorkloadSettings.add(directorateWorkload);
		Directorate directorate1 = directorateDao.isDirectorateExist(caseSamplingRequest.getDirectorateName());

		if (ObjectUtils.isEmpty(directorate1)) {
			caseSamplingExaminer.setDirectorate(directorate);
			caseSamplingExaminer.setNumPAX(caseSamplingRequest.getNumPAX());
			caseSamplingExaminer.setUserId(caseSamplingRequest.getUserID());

			caseSamplingExaminer.setYear(dateTime.getYear());

			caseSamplingExaminerStatus.add(caseSamplingExaminer);
			directorateWorkload.setDirectorate(directorate);
			directorateWorkload.setMaxTarget(0);
			directorateWorkload.setNumExamInDir(caseSamplingRequest.getNumExamInDir());
			directorateWorkload.setYear(dateTime.getYear());
			directorateWorkload.setJanWorkLoadCount(caseSamplingRequest.getJanWorkLoadCount());
			directorateWorkload.setFebWorkLoadCount(caseSamplingRequest.getFebWorkLoadCount());
			directorateWorkload.setMarWorkLoadCount(caseSamplingRequest.getMarWorkLoadCount());
			directorateWorkload.setAprWorkLoadCount(caseSamplingRequest.getAprWorkLoadCount());
			directorateWorkload.setMayWorkLoadCount(caseSamplingRequest.getMayWorkLoadCount());
			directorateWorkload.setJunWorkLoadCount(caseSamplingRequest.getJunWorkLoadCount());
			directorateWorkload.setJulWorkLoadCount(caseSamplingRequest.getJulWorkLoadCount());
			directorateWorkload.setAugWorkLoadCount(caseSamplingRequest.getAugWorkLoadCount());
			directorateWorkload.setSepWorkLoadCount(caseSamplingRequest.getSepWorkLoadCount());
			directorateWorkload.setOctWorkLoadCount(caseSamplingRequest.getOctWorkLoadCount());
			directorateWorkload.setNovWorkLoadCount(caseSamplingRequest.getNovWorkLoadCount());
			directorateWorkload.setDecWorkLoadCount(caseSamplingRequest.getDecWorkLoadCount());
			directorate.setDirectorId(caseSamplingRequest.getDirectorId());
			directorate.setDirectorateName(caseSamplingRequest.getDirectorateName());
			directorate.setCaseSamplingExaminerStatus(caseSamplingExaminerStatus);
			directorate.setDirectorateWorkloadSettings(directorateWorkloadSettings);
			directorateDao.updateDirectorate(directorate, caseSamplingRequest);
		} else {
			boolean flag = false;
			List<CaseSamplingExaminerStatus> examinerStatusList = (List<CaseSamplingExaminerStatus>) directorate1
					.getCaseSamplingExaminerStatus();
			caseSamplingExaminer.setDirectorate(directorate1);
			DirectorateWorkloadSetting directorateWorkloadSetting = directorate1.getDirectorateWorkloadSettings()
					.get(0);
			for (int i = 0; i < examinerStatusList.size(); i++) {
				if (examinerStatusList.get(i).getUserId().equals(caseSamplingRequest.getUserID())) {
					examinerStatusList.get(i).setNumPAX(caseSamplingRequest.getNumPAX());
					flag = true;
					break;
				}
			}
			if (flag == false) {

				caseSamplingExaminer.setNumPAX(caseSamplingRequest.getNumPAX());
				caseSamplingExaminer.setUserId(caseSamplingRequest.getUserID());

				caseSamplingExaminer.setYear(dateTime.getYear());
				examinerStatusList.add(caseSamplingExaminer);
			}
			directorateWorkloadSetting.setNumExamInDir(caseSamplingRequest.getNumExamInDir());
			directorateWorkloadSetting.setJanWorkLoadCount(caseSamplingRequest.getJanWorkLoadCount());
			directorateWorkloadSetting.setFebWorkLoadCount(caseSamplingRequest.getFebWorkLoadCount());
			directorateWorkloadSetting.setMarWorkLoadCount(caseSamplingRequest.getMarWorkLoadCount());
			directorateWorkloadSetting.setAprWorkLoadCount(caseSamplingRequest.getAprWorkLoadCount());
			directorateWorkloadSetting.setMayWorkLoadCount(caseSamplingRequest.getMayWorkLoadCount());
			directorateWorkloadSetting.setJunWorkLoadCount(caseSamplingRequest.getJunWorkLoadCount());
			directorateWorkloadSetting.setJulWorkLoadCount(caseSamplingRequest.getJulWorkLoadCount());
			directorateWorkloadSetting.setAugWorkLoadCount(caseSamplingRequest.getAugWorkLoadCount());
			directorateWorkloadSetting.setSepWorkLoadCount(caseSamplingRequest.getSepWorkLoadCount());
			directorateWorkloadSetting.setOctWorkLoadCount(caseSamplingRequest.getOctWorkLoadCount());
			directorateWorkloadSetting.setNovWorkLoadCount(caseSamplingRequest.getNovWorkLoadCount());
			directorateWorkloadSetting.setDecWorkLoadCount(caseSamplingRequest.getDecWorkLoadCount());
			directorate1.setDirectorId(caseSamplingRequest.getDirectorId());
			directorate1.setCaseSamplingExaminerStatus(examinerStatusList);
			directorateDao.updateDirectorate(directorate1, caseSamplingRequest);
		}

		return 1;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public CountMetaDataResponse getDefaultCountData(long directorateId) throws CustomException {

		int year = dateTime.getYear();
		CountMetaDataResponse countMetaDataResponse = new CountMetaDataResponse();
		CaseSamplingAdminSettingDto caseSamplingAdminSettingDto = caseSamplingAdminSettingDao
				.getCaseSamplingAdminSetting();
		if (!ObjectUtils.isEmpty(caseSamplingAdminSettingDto)) {
			countMetaDataResponse.setNumMinYearTarget(caseSamplingAdminSettingDto.getNumMinYearTarget());
			countMetaDataResponse.setNumMaxYearTarget(caseSamplingAdminSettingDto.getNumMaxYearTarget());
			countMetaDataResponse.setNumMustSample(caseSamplingAdminSettingDto.getNumMustSample());
		}

		DirectorateWorkloadSettingDto directorateWorkloadSettingDto = directorateDao
				.getDirectorateWorkloadSetting(directorateId, year);
		if (!ObjectUtils.isEmpty(directorateWorkloadSettingDto)) {
			countMetaDataResponse.setJanWorkLoadCount(directorateWorkloadSettingDto.getJanWorkLoadCount());
			countMetaDataResponse.setFebWorkLoadCount(directorateWorkloadSettingDto.getFebWorkLoadCount());
			countMetaDataResponse.setMarWorkLoadCount(directorateWorkloadSettingDto.getMarWorkLoadCount());
			countMetaDataResponse.setAprWorkLoadCount(directorateWorkloadSettingDto.getAprWorkLoadCount());
			countMetaDataResponse.setMayWorkLoadCount(directorateWorkloadSettingDto.getMayWorkLoadCount());
			countMetaDataResponse.setJunWorkLoadCount(directorateWorkloadSettingDto.getJunWorkLoadCount());
			countMetaDataResponse.setJulWorkLoadCount(directorateWorkloadSettingDto.getJulWorkLoadCount());
			countMetaDataResponse.setAugWorkLoadCount(directorateWorkloadSettingDto.getAugWorkLoadCount());
			countMetaDataResponse.setSepWorkLoadCount(directorateWorkloadSettingDto.getSepWorkLoadCount());
			countMetaDataResponse.setOctWorkLoadCount(directorateWorkloadSettingDto.getOctWorkLoadCount());
			countMetaDataResponse.setNovWorkLoadCount(directorateWorkloadSettingDto.getNovWorkLoadCount());
			countMetaDataResponse.setDecWorkLoadCount(directorateWorkloadSettingDto.getDecWorkLoadCount());

			countMetaDataResponse.setJanActualCount(directorateWorkloadSettingDto.getJanActualCount());
			countMetaDataResponse.setFebActualCount(directorateWorkloadSettingDto.getFebActualCount());
			countMetaDataResponse.setMarActualCount(directorateWorkloadSettingDto.getMarActualCount());
			countMetaDataResponse.setAprActualCount(directorateWorkloadSettingDto.getAprActualCount());
			countMetaDataResponse.setMayActualCount(directorateWorkloadSettingDto.getMayActualCount());
			countMetaDataResponse.setJunActualCount(directorateWorkloadSettingDto.getJunActualCount());
			countMetaDataResponse.setJulActualCount(directorateWorkloadSettingDto.getJulActualCount());
			countMetaDataResponse.setAugActualCount(directorateWorkloadSettingDto.getAugActualCount());
			countMetaDataResponse.setSepActualCount(directorateWorkloadSettingDto.getSepActualCount());
			countMetaDataResponse.setOctActualCount(directorateWorkloadSettingDto.getOctActualCount());
			countMetaDataResponse.setNovActualCount(directorateWorkloadSettingDto.getNovActualCount());
			countMetaDataResponse.setDecActualCount(directorateWorkloadSettingDto.getDecActualCount());

		}

		List<CaseSamplingExaminerStatusResponse> caseSamplingExaminerStatusResponseList = new ArrayList<>();
		List<CaseSamplingExaminerStatusDto> caseSamplingExaminerStatusDtoList = caseSamplingExaminerStatusDao
				.getExaminersStatus(directorateId, year);

		if (CollectionUtils.isNotEmpty(caseSamplingExaminerStatusDtoList)) {
			for (CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto : caseSamplingExaminerStatusDtoList) {
				CaseSamplingExaminerStatusResponse caseSamplingExaminerStatusResponse = new CaseSamplingExaminerStatusResponse();
				caseSamplingExaminerStatusResponse.setUserId(caseSamplingExaminerStatusDto.getUserId());
				caseSamplingExaminerStatusResponse.setNumSampled(caseSamplingExaminerStatusDto.getNumSampled());
				caseSamplingExaminerStatusResponseList.add(caseSamplingExaminerStatusResponse);
			}

		}
		countMetaDataResponse.setCaseSamplingExaminerStatusResponse(caseSamplingExaminerStatusResponseList);

		return countMetaDataResponse;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int updateDefaultCountData(CountMetaDataRequest countMetaDataRequest, long directorateId)
			throws CustomException {

		int year = dateTime.getYear();
		updaterSettingsResponse.setDescription("UPDATE SUCCESSFULL");
		CaseSamplingAdminSettingDto caseSamplingAdminSettingDto = new CaseSamplingAdminSettingDto();
		caseSamplingAdminSettingDto.setNumMinYearTarget(countMetaDataRequest.getNumMinYearTarget());
		caseSamplingAdminSettingDto.setNumMaxYearTarget(countMetaDataRequest.getNumMaxYearTarget());
		caseSamplingAdminSettingDto.setNumMustSample(countMetaDataRequest.getNumMustSample());
		caseSamplingAdminSettingDao.updateCaseSamplingAdminSetting(caseSamplingAdminSettingDto);

		int sum = sumOfMonthWorkloadCounts(countMetaDataRequest);
		Directorate directorate = directorateDao.isDirectorateExist(directorateId);

		if (!ObjectUtils.isEmpty(directorate)) {

			directorateWorkloadSettingDao.checkNumExamInDirValidation(directorateId,
					countMetaDataRequest.getNumMinYearTarget(), sum, year);

			DirectorateWorkloadSettingDto directorateWorkloadSettingDto = new DirectorateWorkloadSettingDto();
			directorateWorkloadSettingDto.setDirectorate(directorate);
			directorateWorkloadSettingDto.setJanWorkLoadCount(countMetaDataRequest.getJanWorkLoadCount());
			directorateWorkloadSettingDto.setFebWorkLoadCount(countMetaDataRequest.getFebWorkLoadCount());
			directorateWorkloadSettingDto.setMarWorkLoadCount(countMetaDataRequest.getMarWorkLoadCount());
			directorateWorkloadSettingDto.setAprWorkLoadCount(countMetaDataRequest.getAprWorkLoadCount());
			directorateWorkloadSettingDto.setMayWorkLoadCount(countMetaDataRequest.getMayWorkLoadCount());
			directorateWorkloadSettingDto.setJunWorkLoadCount(countMetaDataRequest.getJunWorkLoadCount());
			directorateWorkloadSettingDto.setJulWorkLoadCount(countMetaDataRequest.getJulWorkLoadCount());
			directorateWorkloadSettingDto.setAugWorkLoadCount(countMetaDataRequest.getAugWorkLoadCount());
			directorateWorkloadSettingDto.setSepWorkLoadCount(countMetaDataRequest.getSepWorkLoadCount());
			directorateWorkloadSettingDto.setOctWorkLoadCount(countMetaDataRequest.getOctWorkLoadCount());
			directorateWorkloadSettingDto.setNovWorkLoadCount(countMetaDataRequest.getNovWorkLoadCount());
			directorateWorkloadSettingDto.setDecWorkLoadCount(countMetaDataRequest.getDecWorkLoadCount());
			directorateWorkloadSettingDao.updateDirectorateWorkloadSetting(directorateWorkloadSettingDto, directorateId,
					year);

		} else {

			updaterSettingsResponse.setDescription("No Direcorate Found for this directorateid");
			throw new CustomException(
					responseValidator.getErrorResponse("0", "No Direcorate Found for this directorateid"));

		}

		return 1;
	}

	/**
	 * return sum of workload counts
	 * @param countMetaDataRequest : CountMetaDataRequest
	 * @return int: int
	 */
	private int sumOfMonthWorkloadCounts(CountMetaDataRequest countMetaDataRequest) {
		return countMetaDataRequest.getJanWorkLoadCount() + countMetaDataRequest.getFebWorkLoadCount()
				+ countMetaDataRequest.getMarWorkLoadCount() + countMetaDataRequest.getAprWorkLoadCount()
				+ countMetaDataRequest.getMayWorkLoadCount() + countMetaDataRequest.getJunWorkLoadCount()
				+ countMetaDataRequest.getJulWorkLoadCount() + countMetaDataRequest.getAugWorkLoadCount()
				+ countMetaDataRequest.getSepWorkLoadCount() + countMetaDataRequest.getOctWorkLoadCount()
				+ countMetaDataRequest.getNovWorkLoadCount() + countMetaDataRequest.getDecWorkLoadCount();

	}

}
