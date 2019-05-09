package org.epo.cms.edfs.services.casesampling.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.casesampling.dto.DirectorateWorkloadSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.casesampling.util.DateTime;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingExaminerStatus;
import org.epo.cms.edfs.services.dossierpersistence.entity.Directorate;
import org.epo.cms.edfs.services.dossierpersistence.entity.DirectorateWorkloadSetting;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
@PrepareForTest({ DirectorateWorkloadSettingDaoImpl.class })
public class DirectorateWorkloadSettingDaoImplTest {

	@InjectMocks
	DirectorateWorkloadSettingDaoImpl directorateWorkloadSettingDaoImpl;

	@Mock
	Criteria mockCriteria;

	@Mock
	Session session;

	@Mock
	SessionFactory sessionFactory;

	@Mock
	ResponseValidator responseValidator;
	
	@Mock
	private DateTime dateTime;

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

	@Before
	public void setUp() throws CustomException {

		directorate = new Directorate();
		directorateWorkload = new DirectorateWorkloadSetting();
		caseSamplingExaminerStatus = new CaseSamplingExaminerStatus();
		caseSamplingExaminerStatusList = new ArrayList<>();
		directorateWorkloadSettings = new ArrayList<>();
		workloadSettingDto = new DirectorateWorkloadSettingDto();
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
	}

	@Test
	public void testGetCaseSamplingAdminSetting() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		int response=directorateWorkloadSettingDaoImpl.checkNumExamInDirValidation(directorateId, 2, 24, year);
		assertEquals(response, 1);
	}

	@Test(expected = CustomException.class)
	public void testGetCaseSamplingAdminSettingLessSum() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		int response=directorateWorkloadSettingDaoImpl.checkNumExamInDirValidation(directorateId, 2, 0, year);
		assertEquals(response, 1);
	}
	
	
	@Test(expected = CustomException.class)
	public void testGetCaseSamplingAdminSettingEmpty() throws CustomException {


		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(null);
		int response=directorateWorkloadSettingDaoImpl.checkNumExamInDirValidation(directorateId, 2, 0, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingJan() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(1);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingFeb() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(2);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingMar() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(3);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingApr() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(4);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingMay() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(5);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingJun() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(6);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingJul() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(7);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingAug() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(8);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingSep() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(9);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingOct() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(10);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingNov() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(11);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	@Test
	public void testUpdateDirectorateWorkloadSettingDec() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorateWorkload);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(12);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	
	@Test(expected = CustomException.class)
	public void testUpdateDirectorateWorkloadSettingEmpty() throws CustomException {

	
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(null);
		Mockito.when(dateTime.getMonthNumber()).thenReturn(1);
		int response=directorateWorkloadSettingDaoImpl.updateDirectorateWorkloadSetting(workloadSettingDto, directorateId, year);
		assertEquals(response, 1);
	}
	
	
	@Test
	public void testWorkloadSettingEntityDtoMapper() throws CustomException {

		dataInsert();
		DirectorateWorkloadSettingDto workloadSettingRes=new DirectorateWorkloadSettingDto();
		workloadSettingRes=directorateWorkloadSettingDaoImpl.workloadSettingEntityDtoMapper(directorateWorkload);
		assertEquals(workloadSettingRes.getJanActualCount(),directorateWorkload.getJanActualCount() );
	}
	
	@SuppressWarnings("rawtypes")
	private void commonSessionMocking() {
		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
	}
	
}
