
package org.epo.cms.edfs.services.casesampling.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.casesampling.dao.DirectorateWorkloadSettingDao;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateWorkloadSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.CaseSamplingRequest;
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
@PrepareForTest({ DirectorateDaoImpl.class })
public class DirectorateDaoImplTest {

	@InjectMocks
	DirectorateDaoImpl directorateDaoImpl;

	@Mock
	Criteria mockCriteria;

	@Mock
	Session session;

	@Mock
	SessionFactory sessionFactory;

	@Mock
	ResponseValidator responseValidator;

	@Mock
	private DirectorateWorkloadSettingDao directorateWorkloadSettingDao;

	Directorate directorate;
	DirectorateWorkloadSetting directorateWorkload;;
	CaseSamplingExaminerStatus caseSamplingExaminerStatus;;
	List<CaseSamplingExaminerStatus> caseSamplingExaminerStatusList;
	List<DirectorateWorkloadSetting> directorateWorkloadSettings;
	DirectorateWorkloadSettingDto workloadSettingDto;

	private static final long directorateId = 1;
	private static final String userId = "Z1";
	private static final int year = 2018;

	@Before
	public void setUp() throws Exception {

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
	}

	@Test
	public void testGetDirectorateWorkloadSettingEmpty() throws CustomException {

		commonSessionMocking();

		Directorate directorate = null;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorate);
		DirectorateWorkloadSettingDto DirectorateWorkloadSettingDto = directorateDaoImpl
				.getDirectorateWorkloadSetting(directorateId, year);
		assertEquals(DirectorateWorkloadSettingDto, null);
	}

	@Test
	public void testGetDirectorateWorkloadSetting() throws CustomException {

		commonWithData();
		Mockito.when(directorateWorkloadSettingDao
				.workloadSettingEntityDtoMapper(directorate.getDirectorateWorkloadSettings().get(0)))
				.thenReturn(workloadSettingDto);
		DirectorateWorkloadSettingDto directorateWorkloadSettingDto = directorateDaoImpl
				.getDirectorateWorkloadSetting(directorateId, year);
		assertEquals(directorateWorkloadSettingDto.getJanWorkLoadCount(), 2);

	}

	@Test
	public void testGetDirectorateIds() throws CustomException {

		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.setProjection(Mockito.any())).thenReturn(mockCriteria);

		List<Long> idList = new ArrayList<>();
		idList.add(1L);
		Mockito.when(mockCriteria.list()).thenReturn(idList);

		List<Long> idListResponse = directorateDaoImpl.getDirectorateIds();
		assertEquals(idList.get(0), idListResponse.get(0));

	}

	@Test
	public void testGetDirectorateAllExaminersEmpty() throws CustomException {

		commonSessionMocking();

		Directorate directorate = null;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorate);
		DirectorateDto directorateDto = directorateDaoImpl.getDirectorateAllExaminers(directorateId, year);
		assertEquals(directorateDto, null);
	}

	@Test
	public void testGetDirectorateAllExaminers() throws CustomException {

		commonWithData();
		Mockito.when(directorateWorkloadSettingDao
				.workloadSettingEntityDtoMapper(directorate.getDirectorateWorkloadSettings().get(0)))
				.thenReturn(workloadSettingDto);
		DirectorateDto directorateDto = directorateDaoImpl.getDirectorateAllExaminers(directorateId, year);
		assertEquals(directorateDto.getDirectorateName(), "test");

	}

	@Test
	public void testIncrementDirectorateWorkloadCountEmpty() throws CustomException {

		commonSessionMocking();

		Directorate directorate = null;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorate);
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "JANUARY", year);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountJan() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "JANUARY", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getJanActualCount(), 1);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountFeb() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "FEBRUARY", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getFebActualCount(), 1);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountMar() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "MARCH", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getMarActualCount(), 1);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountApr() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "APRIL", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getAprActualCount(), 1);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountMay() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "MAY", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getMayActualCount(), 1);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountJun() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "JUNE", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getJunActualCount(), 1);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountJul() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "JULY", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getJulActualCount(), 1);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountAug() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "AUGUST", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getAugActualCount(), 1);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountSep() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "SEPTEMBER", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getSepActualCount(), 1);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountOct() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "OCTOBER", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getOctActualCount(), 1);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountNov() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "NOVEMBER", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getNovActualCount(), 1);

	}

	@Test
	public void testIncrementDirectorateWorkloadCountDec() throws CustomException {

		commonWithData();
		directorateDaoImpl.incrementDirectorateWorkloadCount(directorateId, "DECEMBER", year);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getDecActualCount(), 1);

	}

	@Test
	public void testUpdateDirectorateMaxtarget() throws CustomException {

		commonWithData();
		directorateDaoImpl.updateDirectorateMaxtarget(directorateId, year, 5);
		assertEquals(directorate.getDirectorateWorkloadSettings().get(0).getMaxTarget(), 5);

	}

	@Test
	public void testUpdateDirectorateMaxtargetEmpty() throws CustomException {

		commonSessionMocking();
		Directorate directorate = null;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorate);
		directorateDaoImpl.updateDirectorateMaxtarget(directorateId, year, 5);
		assertEquals(directorate, null);

	}

	@Test
	public void testUpdateDirectorate() throws CustomException {

		commonWithData();
		CaseSamplingRequest caseSamplingRequest = new CaseSamplingRequest();
		int response = directorateDaoImpl.updateDirectorate(directorate, caseSamplingRequest);
		assertEquals(response, 1);

	}

	@Test
	public void testIsDirectorateExist() throws CustomException {

		commonWithData();
		Directorate directorate = directorateDaoImpl.isDirectorateExist("test");
		assertEquals(directorate.getDirectorateName(), "test");

	}

	@Test
	public void testIsDirectorateExistEmpty() throws CustomException {

		commonSessionMocking();

		Directorate directorate = null;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorate);
		Directorate directorateRes = directorateDaoImpl.isDirectorateExist("testEmpty");
		assertEquals(directorateRes, null);

	}

	@Test
	public void testIsDirectorateExistId() throws CustomException {

		commonWithData();
		Directorate directorate = directorateDaoImpl.isDirectorateExist(directorateId);
		assertEquals(directorate.getDirectorateName(), "test");

	}

	@Test
	public void testIsDirectorateExistIdEmpty() throws CustomException {

		commonSessionMocking();

		Directorate directorate = null;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorate);
		Directorate directorateRes = directorateDaoImpl.isDirectorateExist(directorateId);
		assertEquals(directorateRes, null);

	}
	
	@Test
	public void testGetDirectoratesList() throws CustomException {
		
		dataInsert();
		commonSessionMocking();
		List<Directorate> directoratesList = new ArrayList<>();
		directoratesList.add(directorate);
		List<DirectorateDto> directoratesDtoList ;
		Mockito.when(mockCriteria.list()).thenReturn(directoratesList);
		directoratesDtoList = directorateDaoImpl.getDirectoratesList();
		assertEquals(directoratesDtoList.get(0).getDirectorateName(), "test");

	}

	@Test
	public void testGetDirectoratesListEmpty() throws CustomException {

	
		commonSessionMocking();
		List<Directorate> directoratesList = new ArrayList<>();
		List<DirectorateDto> directoratesDtoList ;
		Mockito.when(mockCriteria.list()).thenReturn(directoratesList);
		directoratesDtoList = directorateDaoImpl.getDirectoratesList();
		assertEquals(directoratesDtoList.size(), 0);

	}
	
	

	private void commonWithData() {
		dataInsert();
		commonSessionMocking();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(directorate);
	}

	@SuppressWarnings("rawtypes")
	private void commonSessionMocking() {
		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.createAlias(Mockito.anyString(), Mockito.anyString())).thenReturn(mockCriteria);
	}
}
