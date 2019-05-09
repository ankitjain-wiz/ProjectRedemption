package org.epo.cms.edfs.services.casesampling.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.casesampling.dao.DirectorateDao;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingAdminSettingDto;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingExaminerStatusDto;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingSelectDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateWorkloadSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.CaseSamplingRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataRequest;
import org.epo.cms.edfs.services.casesampling.pojo.DirectorateMetaDataListResponse;
import org.epo.cms.edfs.services.casesampling.util.DateTime;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
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
@PrepareForTest({ DirectorateServiceImpl.class })
public class DirectorateServiceTest {

	@InjectMocks
	DirectorateServiceImpl directorateServiceImpl;

	@Mock
	private DirectorateDao directorateDao;

	@Mock
	private DateTime dateTime;
	
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
		 directorateDto=new DirectorateDto();
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
		
		directorateDto.setDirectorateId(directorateId);
		directorateDto.setDirectorateName("test");
		directorateDto.setDirectorateWorkloadSettings(workloadSettingDto);
		directorateDto.setCaseSamplingExaminerStatus(caseSamplingExaminerStatusDtoList);
	}
	
	@Test
	public void testGetDirectorateData() throws CustomException {

		dataInsert();
		List<DirectorateDto> directoratesDtoList=new ArrayList<>() ;
		directoratesDtoList.add(directorateDto);
		Mockito.when(directorateDao.getDirectoratesList()).thenReturn(directoratesDtoList);
		DirectorateMetaDataListResponse directorateMetaDataListResponse=directorateServiceImpl.getDirectorateData();
		assertEquals(directorateMetaDataListResponse.getDirectorateMetaDataResponse().get(0).getDirectorateName(), "test");
	}

	@Test
	public void testGetDirectorateDataEmpty() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectoratesList()).thenReturn(new ArrayList<>());
		DirectorateMetaDataListResponse directorateMetaDataListResponse=directorateServiceImpl.getDirectorateData();
		assertEquals(directorateMetaDataListResponse.getDirectorateMetaDataResponse().size(),0 );
	}
	
	/*@Test
	public void testIsDirectorateMonthTargetReached() throws CustomException {

		dataInsert();
		workloadSettingDto.setFebActualCount(2);
		workloadSettingDto.setFebWorkLoadCount(2);
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("FEBRUARY");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,true );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedNegative() throws CustomException {

		dataInsert();
		workloadSettingDto.setFebActualCount(2);
		workloadSettingDto.setFebWorkLoadCount(2);
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("test");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,true );
	}
	

	@Test
	public void testIsDirectorateMonthTargetReachedFalseJan() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("JANUARY");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedFalseFeb() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("FEBRUARY");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedFalseMar() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("MARCH");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedFalseApr() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("APRIL");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedFalseMay() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("MAY");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedFalseJun() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("JUNE");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedFalseJul() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("JULY");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedFalseAug() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("AUGUST");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedFalseSep() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("SEPTEMBER");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedFalseOct() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("OCTOBER");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedFalseNov() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("NOVEMBER");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsDirectorateMonthTargetReachedFalseDec() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		Mockito.when(dateTime.getMonth()).thenReturn("DECEMBER");
		boolean response=directorateServiceImpl.isDirectorateMonthTargetReached(directorateId);
		assertEquals(response,false );
	}*/
	
	
	
	@Test
	public void testGetDirectorateMaxTarget() throws CustomException {

		dataInsert();
		workloadSettingDto.setMaxTarget(5);
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		int response=directorateServiceImpl.getDirectorateMaxTarget(directorateId);
		assertEquals(response,5 );
	}
	
	@Test
	public void testIsAllExaminerMaxTargetReached() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		boolean response=directorateServiceImpl.isAllExaminerMaxTargetReached(directorate);
		assertEquals(response,true );
	}
	
	@Test
	public void testIsAllExaminerMaxTargetReachedNull() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(null);
		boolean response=directorateServiceImpl.isAllExaminerMaxTargetReached(directorate);
		assertEquals(response,true );
	}

	@Test
	public void testIsAllExaminerMaxTargetReachedFalse() throws CustomException {

		dataInsert();
		directorateWorkload.setMaxTarget(5);
		caseSamplingExaminerStatus.setNumSampled(6);
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		boolean response=directorateServiceImpl.isAllExaminerMaxTargetReached(directorate);
		assertEquals(response,false );
	}
	
	@Test
	public void testIsAllExaminerMinTargetReached() throws CustomException {

		dataInsert();
		caseSamplingExaminerStatus.setNumSampled(2);
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		boolean response=directorateServiceImpl.isAllExaminerMinTargetReached(directorate, 2);
		assertEquals(response,true );
	}
	
	@Test
	public void testIsAllExaminerMinTargetReachedNull() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(null);
		boolean response=directorateServiceImpl.isAllExaminerMinTargetReached(null, 2);
		assertEquals(response,true );
	}

	@Test
	public void testIsAllExaminerMinTargetReachedFalse() throws CustomException {

		dataInsert();
		Mockito.when(directorateDao.getDirectorateWorkloadSetting(Mockito.anyLong(), Mockito.anyInt())).thenReturn(workloadSettingDto);
		boolean response=directorateServiceImpl.isAllExaminerMinTargetReached(directorate, 2);
		assertEquals(response,false );
	}


	

}
