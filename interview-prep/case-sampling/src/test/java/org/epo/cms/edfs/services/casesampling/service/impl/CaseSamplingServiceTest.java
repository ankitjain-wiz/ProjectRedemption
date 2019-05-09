package org.epo.cms.edfs.services.casesampling.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingAdminSettingDao;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingExaminerStatusDao;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingSelectDao;
import org.epo.cms.edfs.services.casesampling.dao.DirectorateDao;
import org.epo.cms.edfs.services.casesampling.dao.DirectorateWorkloadSettingDao;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingAdminSettingDto;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingExaminerStatusDto;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingSelectDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateWorkloadSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.CaseSamplingRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataResponse;
import org.epo.cms.edfs.services.casesampling.pojo.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.casesampling.service.DirectorateService;
import org.epo.cms.edfs.services.casesampling.service.RandomSamplingService;
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
@PrepareForTest({ CaseSamplingServiceImpl.class })
public class CaseSamplingServiceTest {

	@InjectMocks
	CaseSamplingServiceImpl caseSamplingServiceImpl;

	@Mock
	private DirectorateService directorateSamplingService;

	@Mock
	private RandomSamplingService randomSamplingService;

	@Mock
	private CaseSamplingExaminerStatusDao caseSamplingExaminerStatusDao;

	@Mock
	private CaseSamplingAdminSettingDao caseSamplingAdminSettingDao;

	@Mock
	private CaseSamplingSelectDao caseSamplingSelectDao;

	@Mock
	private DirectorateDao directorateDao;

	@Mock
	private DateTime dateTime;

	@Mock
	private ResponseValidator responseValidator;

	@Mock
	private DirectorateWorkloadSettingDao directorateWorkloadSettingDao;

	@Mock
	private UpdaterSettingsResponse updaterSettingsResponse;

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
	}

	@Test
	public void testGetDirectorateMetaData() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt()))
				.thenReturn(workloadSettingDto);
		Mockito.when(caseSamplingExaminerStatusDao.getExaminersStatus(Mockito.anyLong(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDtoList);
		CountMetaDataResponse countMetaDataResponse = caseSamplingServiceImpl.getDefaultCountData(directorateId);
		assertEquals(countMetaDataResponse.getNumMinYearTarget(), 2);
	}

	@Test
	public void testGetDirectorateMetaDataEmpty() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(null);
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt()))
				.thenReturn(null);
		Mockito.when(caseSamplingExaminerStatusDao.getExaminersStatus(Mockito.anyLong(), Mockito.anyInt()))
				.thenReturn(null);
		CountMetaDataResponse countMetaDataResponse = caseSamplingServiceImpl.getDefaultCountData(directorateId);
		assertEquals(countMetaDataResponse.getCaseSamplingExaminerStatusResponse().size(), 0);
	}

	@Test
	public void testUpdateDefaultCountData() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.isDirectorateExist(Mockito.anyLong())).thenReturn(directorate);
		Mockito.when(directorateWorkloadSettingDao.checkNumExamInDirValidation(Mockito.anyLong(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyInt())).thenReturn(1);
		int response = caseSamplingServiceImpl.updateDefaultCountData(countMetaDataRequest, directorateId);
		assertEquals(response, 1);
	}

	@Test(expected = CustomException.class)
	public void testUpdateDefaultCountDataEmpty() throws CustomException {
		dataInsert();
		Mockito.when(directorateDao.isDirectorateExist(Mockito.anyLong())).thenReturn(null);
		Mockito.when(directorateWorkloadSettingDao.checkNumExamInDirValidation(Mockito.anyLong(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyInt())).thenReturn(1);
		caseSamplingServiceImpl.updateDefaultCountData(countMetaDataRequest, directorateId);

	}

	@Test
	public void testUpdateCaseSamplingData() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.isDirectorateExist(Mockito.anyString())).thenReturn(directorate);
		int response = caseSamplingServiceImpl.updateCaseSamplingData(caseSamplingRequest);
		assertEquals(response, 1);
	}

	@Test
	public void testUpdateCaseSamplingDataEmpty() throws CustomException {
		dataInsert();
		Mockito.when(directorateDao.isDirectorateExist(Mockito.anyString())).thenReturn(null);
		int response = caseSamplingServiceImpl.updateCaseSamplingData(caseSamplingRequest);
		assertEquals(response, 1);
	}

	@Test
	public void testUpdateCaseSamplingDataDifferentUser() throws CustomException {
		dataInsert();
		caseSamplingRequest.setUserID("Z2");
		Mockito.when(directorateDao.isDirectorateExist(Mockito.anyString())).thenReturn(directorate);
		int response = caseSamplingServiceImpl.updateCaseSamplingData(caseSamplingRequest);
		assertEquals(response, 1);
	}

	@Test
	public void testIsCaseCheckRequired() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(caseSamplingSelectDao.getSamplingDetail(userId)).thenReturn(caseSamplingSelectDto);
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);

		//Boolean response = caseSamplingServiceImpl.isCaseCheckRequired(userId);
		//assertEquals(response, false);
	}

	@Test
	public void testIsCaseCheckRequiredSampledTrue() throws CustomException {

		dataInsert();
		caseSamplingExaminerStatusDto.setNumSubmitted(2);
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(caseSamplingSelectDao.getSamplingDetail(userId)).thenReturn(caseSamplingSelectDto);
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);

		//Boolean response = caseSamplingServiceImpl.isCaseCheckRequired(userId);
		//(response, true);
	}

	//@Test(expected = CustomException.class)
	public void testIsCaseCheckRequiredEmpty() throws CustomException {

		dataInsert();

		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(null);
		Mockito.when(caseSamplingSelectDao.getSamplingDetail(userId)).thenReturn(null);
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(null);

		//Boolean response = caseSamplingServiceImpl.isCaseCheckRequired(userId);
		//assertEquals(response, true);
	}

	//@Test(expected = CustomException.class)
	public void testIsCaseCheckRequiredCusExp() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenThrow(new CustomException(null));
		//Boolean response = caseSamplingServiceImpl.isCaseCheckRequired(userId);
		//assertEquals(response, true);
	}

	@SuppressWarnings("unchecked")
	//@Test(expected = Exception.class)
	public void testIsCaseCheckRequiredExp() throws CustomException {

		dataInsert();
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenThrow(Exception.class);
		//Boolean response = caseSamplingServiceImpl.isCaseCheckRequired(userId);
		//assertEquals(response, true);
	}
	
	@Test
	public void testIcreaseCoverage() throws CustomException {

		dataInsert();

		caseSamplingExaminerStatusDto.setNumSubmitted(2);
		caseSamplingExaminerStatusDto.setNumPax(3);
		
		Mockito.when(directorateSamplingService.getDirectorateMaxTarget(directorateId)).thenReturn(6);
		
		Mockito.when(caseSamplingExaminerStatusDao
				.updateNumSubmitted(Mockito.anyString(), Mockito.anyInt())).thenReturn(caseSamplingExaminerStatusDto);
		
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(caseSamplingSelectDao.getSamplingDetail(userId)).thenReturn(caseSamplingSelectDto);
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);

		//Boolean response = caseSamplingServiceImpl.isCaseCheckRequired(userId);
		//assertEquals(response, true);
	}
	
	@Test
	public void testIcreaseCoverageTwo() throws CustomException {

		dataInsert();

		caseSamplingExaminerStatusDto.setNumSubmitted(2);
		caseSamplingExaminerStatusDto.setNumPax(3);
		caseSamplingExaminerStatusDto.setNumSampled(6);
		
		Mockito.when(directorateSamplingService.getDirectorateMaxTarget(directorateId)).thenReturn(6);
		
		Mockito.when(caseSamplingExaminerStatusDao
				.updateNumSubmitted(Mockito.anyString(), Mockito.anyInt())).thenReturn(caseSamplingExaminerStatusDto);
		
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(caseSamplingSelectDao.getSamplingDetail(userId)).thenReturn(caseSamplingSelectDto);
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);

		//Boolean response = caseSamplingServiceImpl.isCaseCheckRequired(userId);
		//assertEquals(response, true);
	}
	
	@Test
	public void testIcreaseCoverageThree() throws CustomException {

		dataInsert();

		caseSamplingExaminerStatusDto.setNumSubmitted(2);
		caseSamplingExaminerStatusDto.setNumPax(3);
		caseSamplingExaminerStatusDto.setNumSampled(2);
		caseSamplingAdminSettingDto.setNumMustSample(-1);

		
		Mockito.when(directorateSamplingService.getDirectorateMaxTarget(directorateId)).thenReturn(2);
		Mockito.when(directorateSamplingService.isAllExaminerMaxTargetReached(directorate)).thenReturn(true);
		
		Mockito.when(caseSamplingExaminerStatusDao.updateNumSubmitted(Mockito.anyString(), Mockito.anyInt())).thenReturn(caseSamplingExaminerStatusDto);
		Mockito.when(caseSamplingExaminerStatusDao.updateNumSampled(Mockito.anyString(), Mockito.anyInt())).thenReturn(caseSamplingExaminerStatusDto);
		
		
		Mockito.when(caseSamplingAdminSettingDao.getCaseSamplingAdminSetting()).thenReturn(caseSamplingAdminSettingDto);
		Mockito.when(caseSamplingSelectDao.getSamplingDetail(userId)).thenReturn(caseSamplingSelectDto);
		Mockito.when(caseSamplingExaminerStatusDao.getExaminerStatus(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(caseSamplingExaminerStatusDto);

		//Boolean response = caseSamplingServiceImpl.isCaseCheckRequired(userId);
		//assertEquals(response, true);
	}

}
