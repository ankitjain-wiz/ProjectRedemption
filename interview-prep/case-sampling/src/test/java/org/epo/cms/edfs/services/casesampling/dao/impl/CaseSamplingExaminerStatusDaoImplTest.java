package org.epo.cms.edfs.services.casesampling.dao.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingExaminerStatusDto;
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
@PrepareForTest({ CaseSamplingExaminerStatusDaoImpl.class })
public class CaseSamplingExaminerStatusDaoImplTest {

	@InjectMocks
	CaseSamplingExaminerStatusDaoImpl caseSamplingExaminerStatusDaoImpl;

	@Mock
	Criteria mockCriteria;

	@Mock
	Session session;

	@Mock
	SessionFactory sessionFactory;

	@Mock
	ResponseValidator responseValidator;

	private static final long directorateId = 1;
	private static final String userId = "Z1";
	private static final int year = 2018;

	Directorate directorate;
	DirectorateWorkloadSetting directorateWorkload;;
	CaseSamplingExaminerStatus caseSamplingExaminerStatus;;
	List<CaseSamplingExaminerStatus> caseSamplingExaminerStatusList;
	List<DirectorateWorkloadSetting> directorateWorkloadSettings;

	@Before
	public void setUp() throws Exception {
		directorate = new Directorate();
		directorateWorkload = new DirectorateWorkloadSetting();
		caseSamplingExaminerStatus = new CaseSamplingExaminerStatus();
		caseSamplingExaminerStatusList = new ArrayList<>();
		directorateWorkloadSettings = new ArrayList<>();
		MockitoAnnotations.initMocks(this);
	}

	private void dataInsert() {

		caseSamplingExaminerStatus.setDirectorate(directorate);
		caseSamplingExaminerStatus.setNumPAX(10);
		caseSamplingExaminerStatus.setUserId(userId);
		caseSamplingExaminerStatus.setYear(year);
		caseSamplingExaminerStatusList.add(caseSamplingExaminerStatus);

		directorateWorkload.setDirectorate(directorate);
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
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetExaminerStatusEmpty() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingExaminerStatus caseSamplingExaminerStatus = null;
		CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingExaminerStatus);
		caseSamplingExaminerStatusDto = caseSamplingExaminerStatusDaoImpl.getExaminerStatus(userId, year);
		assertEquals(caseSamplingExaminerStatusDto, null);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetExaminerStatus() throws CustomException {
		
		dataInsert();

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		
		CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingExaminerStatus);
		caseSamplingExaminerStatusDto = caseSamplingExaminerStatusDaoImpl.getExaminerStatus(userId, year);
		assertNotNull(caseSamplingExaminerStatusDto);
		assertEquals(caseSamplingExaminerStatusDto.getNumPax(), 10);
		
	}
	
	

	@SuppressWarnings("rawtypes")
	@Test
	public void testUpdateNumSubmitted() throws CustomException {
		
		dataInsert();

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		
		CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingExaminerStatus);
		caseSamplingExaminerStatusDto = caseSamplingExaminerStatusDaoImpl.updateNumSubmitted(userId, year);
		assertNotNull(caseSamplingExaminerStatusDto);
		assertEquals(caseSamplingExaminerStatusDto.getNumSubmitted(), 1);
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testUpdateNumSubmittedEmpty() throws CustomException {
		
		

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingExaminerStatus caseSamplingExaminerStatus = null;
		CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingExaminerStatus);
		caseSamplingExaminerStatusDto = caseSamplingExaminerStatusDaoImpl.updateNumSubmitted(userId, year);
		
		assertEquals(caseSamplingExaminerStatusDto, null);
		
	}
	
	

	@SuppressWarnings("rawtypes")
	@Test
	public void testUpdateNumSampled() throws CustomException {
		
		dataInsert();

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		
		CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingExaminerStatus);
		caseSamplingExaminerStatusDto = caseSamplingExaminerStatusDaoImpl.updateNumSampled(userId, year);
		assertNotNull(caseSamplingExaminerStatusDto);
		assertEquals(caseSamplingExaminerStatusDto.getNumSampled(), 1);
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testUpdateNumSampledEmpty() throws CustomException {
		
		

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingExaminerStatus caseSamplingExaminerStatus = null;
		CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingExaminerStatus);
		caseSamplingExaminerStatusDto = caseSamplingExaminerStatusDaoImpl.updateNumSampled(userId, year);
		assertEquals(caseSamplingExaminerStatusDto, null);
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testGetExaminersStatus() throws CustomException {
		
		dataInsert();

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		
	
		Mockito.when(mockCriteria.list()).thenReturn(caseSamplingExaminerStatusList);
		List<CaseSamplingExaminerStatusDto> caseSamplingExaminerStatusDtoList=caseSamplingExaminerStatusDaoImpl.getExaminersStatus(directorateId,year);
		assertNotNull(caseSamplingExaminerStatusDtoList);
		assertEquals(caseSamplingExaminerStatusDtoList.get(0).getNumSampled(), 0);
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testGetExaminersStatusEmpty() throws CustomException {
		
		

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);


		List<CaseSamplingExaminerStatus> caseSamplingExaminerStatusList=new ArrayList<>();
		Mockito.when(mockCriteria.list()).thenReturn(caseSamplingExaminerStatusList);
		List<CaseSamplingExaminerStatusDto> caseSamplingExaminerStatusDtoList=caseSamplingExaminerStatusDaoImpl.getExaminersStatus(directorateId,year);
		assertEquals(caseSamplingExaminerStatusDtoList.size(), 0);
		
		
	}

}
