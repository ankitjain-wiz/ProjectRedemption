package org.epo.cms.edfs.services.casesampling.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingAdminSettingDao;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingExaminerStatusDao;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingSelectDao;
import org.epo.cms.edfs.services.casesampling.dao.DirectorateDao;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingAdminSettingDto;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingExaminerStatusDto;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingSelectDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateWorkloadSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.CaseSamplingRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataRequest;
import org.epo.cms.edfs.services.casesampling.service.DirectorateService;
import org.epo.cms.edfs.services.casesampling.util.DateTime;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingExaminerStatus;
import org.epo.cms.edfs.services.dossierpersistence.entity.Directorate;
import org.epo.cms.edfs.services.dossierpersistence.entity.DirectorateWorkloadSetting;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest({ RandomSamplingServiceImpl.class })
public class RandomSamplingServiceTest {

	@InjectMocks
	RandomSamplingServiceImpl randomSamplingServiceImpl;

	@Mock
	private CaseSamplingAdminSettingDao caseSamplingAdminSettingDao;

	@Mock
	private CaseSamplingExaminerStatusDao caseSamplingExaminerStatusDao;

	@Mock
	private CaseSamplingSelectDao caseSamplingSelectDao;

	@Mock
	private DirectorateDao directorateDao;

	@Mock
	private DirectorateService directorateSamplingService;

	@Mock
	private DateTime dateTime;

	@Mock
	private ResponseValidator responseValidator;

	private static final long directorateId = 1;
	private static final String userId = "Z1";
	private static final int year = 2018;

	Directorate directorate;
	DirectorateWorkloadSetting directorateWorkload;;
	CaseSamplingExaminerStatus caseSamplingExaminerStatus;;
	List<CaseSamplingExaminerStatus> caseSamplingExaminerStatusList;
	List<DirectorateWorkloadSetting> directorateWorkloadSettings;
	DirectorateWorkloadSettingDto workloadSettingDto;

	CaseSamplingAdminSettingDto caseSamplingAdminSettingDto;
	CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto;
	List<CaseSamplingExaminerStatusDto> caseSamplingExaminerStatusDtoList;
	CountMetaDataRequest countMetaDataRequest;
	CaseSamplingRequest caseSamplingRequest;
	CaseSamplingSelectDto caseSamplingSelectDto;
	DirectorateDto directorateDto;

	@Before
	public void setUp() throws CustomException {

		directorate = new Directorate();
		directorateWorkload = new DirectorateWorkloadSetting();
		caseSamplingExaminerStatus = new CaseSamplingExaminerStatus();
		caseSamplingExaminerStatusList = new ArrayList<>();
		directorateWorkloadSettings = new ArrayList<>();
		workloadSettingDto = new DirectorateWorkloadSettingDto();

		caseSamplingAdminSettingDto = new CaseSamplingAdminSettingDto();
		caseSamplingExaminerStatusDto = new CaseSamplingExaminerStatusDto();
		caseSamplingExaminerStatusDtoList = new ArrayList<>();
		countMetaDataRequest = new CountMetaDataRequest();
		caseSamplingRequest = new CaseSamplingRequest();
		caseSamplingSelectDto = new CaseSamplingSelectDto();
		directorateDto = new DirectorateDto();
		MockitoAnnotations.initMocks(this);
	}

	private void dataInsert() {

		caseSamplingExaminerStatus.setDirectorate(directorate);

		caseSamplingExaminerStatus.setNumPAX(10);
		caseSamplingExaminerStatus.setUserId(userId);
		caseSamplingExaminerStatus.setYear(year);
		caseSamplingExaminerStatusList.add(caseSamplingExaminerStatus);

		directorateWorkload.setDirectorate(directorate);
		directorateWorkload.setNumExamInDir(4);
		directorateWorkload.setMaxTarget(0);
		directorateWorkload.setNumExamInDir(4);
		directorateWorkload.setYear(year);
		directorateWorkload.setJanWorkLoadCount(2);
		directorateWorkload.setFebWorkLoadCount(2);
		directorateWorkload.setMarWorkLoadCount(2);
		directorateWorkload.setAprWorkLoadCount(2);
		directorateWorkload.setMayWorkLoadCount(2);
		directorateWorkload.setJunWorkLoadCount(2);
		directorateWorkload.setJulWorkLoadCount(2);
		directorateWorkload.setAugWorkLoadCount(2);
		directorateWorkload.setSepWorkLoadCount(2);
		directorateWorkload.setOctWorkLoadCount(2);
		directorateWorkload.setNovWorkLoadCount(2);
		directorateWorkload.setDecWorkLoadCount(2);

		directorateWorkloadSettings.add(directorateWorkload);

		directorate.setDirectorateId(directorateId);
		directorate.setDirectorId("5");
		directorate.setDirectorateName("test");
		directorate.setCaseSamplingExaminerStatus(caseSamplingExaminerStatusList);
		directorate.setDirectorateWorkloadSettings(directorateWorkloadSettings);

		workloadSettingDto.setJanWorkLoadCount(2);
		workloadSettingDto.setDirectorate(directorate);
		workloadSettingDto.setNumExamInDir(4);
		workloadSettingDto.setMaxTarget(0);
		workloadSettingDto.setNumExamInDir(4);
		workloadSettingDto.setYear(year);
		workloadSettingDto.setFebWorkLoadCount(2);
		workloadSettingDto.setMarWorkLoadCount(2);
		workloadSettingDto.setAprWorkLoadCount(2);
		workloadSettingDto.setMayWorkLoadCount(2);
		workloadSettingDto.setJunWorkLoadCount(2);
		workloadSettingDto.setJulWorkLoadCount(2);
		workloadSettingDto.setAugWorkLoadCount(2);
		workloadSettingDto.setSepWorkLoadCount(2);
		workloadSettingDto.setOctWorkLoadCount(2);
		workloadSettingDto.setNovWorkLoadCount(2);
		workloadSettingDto.setDecWorkLoadCount(2);

		caseSamplingAdminSettingDto.setNumMinYearTarget(2);
		caseSamplingAdminSettingDto.setNumMaxYearTarget(3);
		caseSamplingAdminSettingDto.setNumMustSample(3);

		caseSamplingExaminerStatusDto.setDirectorate(directorate);
		caseSamplingExaminerStatusDto.setNumSubmitted(2);
		caseSamplingExaminerStatusDto.setNumSampled(1);
		caseSamplingExaminerStatusDto.setNumPax(10);
		caseSamplingExaminerStatusDto.setUserId(userId);
		caseSamplingExaminerStatusDto.setYear(year);
		caseSamplingExaminerStatusDtoList.add(caseSamplingExaminerStatusDto);

		countMetaDataRequest.setNumMinYearTarget(2);
		countMetaDataRequest.setNumMaxYearTarget(3);
		countMetaDataRequest.setNumMustSample(3);
		countMetaDataRequest.setJanWorkLoadCount(2);

		caseSamplingRequest.setUserID(userId);
		caseSamplingRequest.setDirectorateName("test");
		caseSamplingRequest.setJanWorkLoadCount(2);

		caseSamplingSelectDto.setCaseSamplingSelectId(1);
		caseSamplingSelectDto.setUserId(userId);
		caseSamplingSelectDto.setNumPlannedToSample(2);
		caseSamplingSelectDto.setIsSampled(false);
		caseSamplingSelectDto.setComment("caseSamplingSelect");

		directorateDto.setDirectorateId(directorateId);
		directorateDto.setDirectorateName("test");
		directorateDto.setDirectorateWorkloadSettings(workloadSettingDto);
		directorateDto.setCaseSamplingExaminerStatus(caseSamplingExaminerStatusDtoList);
	}

	@Test(expected = CustomException.class)
	public void testGenerateNextNumToSampleForExaminerNoAdmin() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);
		randomSamplingServiceImpl.generateNextNumToSampleForExaminer(userId, true);

	}

	@Test
	public void testGenerateNextNumToSampleForExaminerTarget() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(directorateSamplingService.isAllExaminerMinTargetReached(Mockito.any(), Mockito.anyInt()))
				.thenReturn(true);
		randomSamplingServiceImpl.generateNextNumToSampleForExaminer(userId, true);

	}

	@Test
	public void testGenerateNextNumToSampleForExaminer() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		randomSamplingServiceImpl.generateNextNumToSampleForExaminer(userId, true);

	}

	@Test
	public void testGenerateNextNumToSampleForExaminerMaxFalse() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(directorateSamplingService.isAllExaminerMinTargetReached(Mockito.any(), Mockito.anyInt()))
				.thenReturn(true);
		randomSamplingServiceImpl.generateNextNumToSampleForExaminer(userId, false);

	}

	@Test(expected = CustomException.class)
	public void testGenerateNextNumToSampleForExaminerNull() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(null);
		randomSamplingServiceImpl.generateNextNumToSampleForExaminer(userId, true);

	}

	@Test(expected = CustomException.class)
	public void testGenerateNextNumToSampleNull() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(null);
		randomSamplingServiceImpl.generateNextNumToSampleForExaminer(userId, directorate);

	}

	@Test
	public void testGenerateNextNumToSample() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(directorateSamplingService.isAllExaminerMinTargetReached(Mockito.any(), Mockito.anyInt()))
				.thenReturn(true);
		randomSamplingServiceImpl.generateNextNumToSampleForExaminer(userId, directorate);

	}

	public void testGenerateNextNumToSampleForAllNull() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateAllExaminers(Mockito.anyLong(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(directorateDao.getDirectorateIds()).thenReturn(new ArrayList<>());
		randomSamplingServiceImpl.generateNextNumToSampleForAll();

	}

	@Test
	public void testGenerateNextNumToSampleForAll() throws CustomException {

		dataInsert();
		List<Long> idList = new ArrayList<>();
		idList.add(1L);
		idList.add(2L);

		Mockito.when(directorateDao.getDirectorateAllExaminers(Mockito.anyLong(), Mockito.anyInt()))
				.thenReturn(directorateDto);
		Mockito.when(directorateDao.getDirectorateIds()).thenReturn(idList);
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(directorateSamplingService.isAllExaminerMinTargetReached(Mockito.any(), Mockito.anyInt()))
				.thenReturn(true);

		randomSamplingServiceImpl.generateNextNumToSampleForAll();

	}
	
	@Test
	public void testGenerateNextNumToSampleForAllNoExaminer() throws CustomException {

		dataInsert();
		List<Long> idList = new ArrayList<>();
		idList.add(1L);
		idList.add(2L);
		
		directorateDto.setCaseSamplingExaminerStatus(null);

		Mockito.when(directorateDao.getDirectorateAllExaminers(Mockito.anyLong(), Mockito.anyInt()))
				.thenReturn(directorateDto);
		Mockito.when(directorateDao.getDirectorateIds()).thenReturn(idList);
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(directorateSamplingService.isAllExaminerMinTargetReached(Mockito.any(), Mockito.anyInt()))
				.thenReturn(true);

		randomSamplingServiceImpl.generateNextNumToSampleForAll();

	}
	
	
	@Test
	public void testGenerateNextNumToSampleForDirectorate() throws CustomException {

		dataInsert();


		Mockito.when(directorateDao.getDirectorateAllExaminers(Mockito.anyLong(), Mockito.anyInt()))
				.thenReturn(directorateDto);
		

		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(directorateSamplingService.isAllExaminerMinTargetReached(Mockito.any(), Mockito.anyInt()))
				.thenReturn(true);

		randomSamplingServiceImpl.generateNextNumToSampleForDirectorate(directorate);

	}
	
	@Test
	public void testGenerateNextNumToSampleForDirectorateNull() throws CustomException {

		dataInsert();

		Mockito.when(directorateDao.getDirectorateAllExaminers(Mockito.anyLong(), Mockito.anyInt()))
				.thenReturn(null);
		
		
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(directorateSamplingService.isAllExaminerMinTargetReached(Mockito.any(), Mockito.anyInt()))
				.thenReturn(true);

		randomSamplingServiceImpl.generateNextNumToSampleForDirectorate(null);

	}
	
	@Test
	public void testGenerateIncrementalRandomNumber() throws CustomException {

		dataInsert();

		Mockito.when(caseSamplingSelectDao.getSamplingDetail(Mockito.anyString()))
				.thenReturn(caseSamplingSelectDto);
		

		randomSamplingServiceImpl.generateIncrementalRandomNumber(userId);

	}
	
	@Test
	public void testRandomScenarioOne() throws CustomException {

		dataInsert();
		caseSamplingExaminerStatusDto.setDirectorate(directorate);
		caseSamplingExaminerStatusDto.setNumSubmitted(1);
		caseSamplingExaminerStatusDto.setNumSampled(1);
		caseSamplingExaminerStatusDto.setNumPax(1);
		caseSamplingExaminerStatusDto.setUserId(userId);
		caseSamplingExaminerStatusDto.setYear(year);
		caseSamplingExaminerStatusDtoList.add(caseSamplingExaminerStatusDto);
		

		caseSamplingAdminSettingDto.setNumMinYearTarget(2);
		caseSamplingAdminSettingDto.setNumMaxYearTarget(3);
		caseSamplingAdminSettingDto.setNumMustSample(0);
		
		
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		randomSamplingServiceImpl.generateNextNumToSampleForExaminer(userId, true);

	}
	
	@Test
	public void testRandomScenarioTwo() throws CustomException {

		dataInsert();
		caseSamplingExaminerStatusDto.setDirectorate(directorate);
		caseSamplingExaminerStatusDto.setNumSubmitted(3);
		caseSamplingExaminerStatusDto.setNumSampled(1);
		caseSamplingExaminerStatusDto.setNumPax(4);
		caseSamplingExaminerStatusDto.setUserId(userId);
		caseSamplingExaminerStatusDto.setYear(year);
		caseSamplingExaminerStatusDtoList.add(caseSamplingExaminerStatusDto);
		

		caseSamplingAdminSettingDto.setNumMinYearTarget(2);
		caseSamplingAdminSettingDto.setNumMaxYearTarget(3);
		caseSamplingAdminSettingDto.setNumMustSample(3);
		
		
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		randomSamplingServiceImpl.generateNextNumToSampleForExaminer(userId, true);

	}
	
	

}
