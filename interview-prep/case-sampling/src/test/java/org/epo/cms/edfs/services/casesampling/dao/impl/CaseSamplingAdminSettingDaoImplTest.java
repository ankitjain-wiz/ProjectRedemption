package org.epo.cms.edfs.services.casesampling.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingAdminSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingAdminSetting;
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
@PrepareForTest({ CaseSamplingAdminSettingDaoImpl.class })
public class CaseSamplingAdminSettingDaoImplTest {

	@InjectMocks
	CaseSamplingAdminSettingDaoImpl caseSamplingAdminSettingDaoImpl;

	@Mock
	Criteria mockCriteria;

	@Mock
	Session session;

	@Mock
	SessionFactory sessionFactory;

	@Mock
	ResponseValidator responseValidator;

	@Mock
	private UpdaterSettingsResponse updaterSettingsResponse;

	@Before
	public void setUp() throws CustomException {

		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetCaseSamplingAdminSettingEmpty() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
		CaseSamplingAdminSettingDto caseSamplingAdminSettingDto;
		Mockito.when(mockCriteria.list()).thenReturn(new ArrayList<>());
		caseSamplingAdminSettingDto = caseSamplingAdminSettingDaoImpl.getCaseSamplingAdminSetting();
		assertEquals(caseSamplingAdminSettingDto, null);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetCaseSamplingAdminSetting() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingAdminSettingDto caseSamplingAdminSettingDto;

		List<CaseSamplingAdminSetting> adminSettinglist = new ArrayList<>();
		CaseSamplingAdminSetting caseSamplingAdminSetting = new CaseSamplingAdminSetting();
		caseSamplingAdminSetting.setNumMinYearTarget(2);
		caseSamplingAdminSetting.setNumMaxYearTarget(3);
		caseSamplingAdminSetting.setNumMustSample(3);
		adminSettinglist.add(caseSamplingAdminSetting);
		Mockito.when(mockCriteria.list()).thenReturn(adminSettinglist);
		caseSamplingAdminSettingDto = caseSamplingAdminSettingDaoImpl.getCaseSamplingAdminSetting();
		assertEquals(caseSamplingAdminSettingDto.getNumMinYearTarget(), 2);
	}

	@SuppressWarnings("rawtypes")
	@Test(expected = CustomException.class)
	public void testUpdateCaseSamplingAdminSettingException() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingAdminSettingDto caseSamplingAdminSettingDto = new CaseSamplingAdminSettingDto();
		UpdaterSettingsResponse updaterSettingsResponse = new UpdaterSettingsResponse();

		CaseSamplingAdminSetting caseSamplingAdminSetting = null;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingAdminSetting);
		caseSamplingAdminSettingDaoImpl.updateCaseSamplingAdminSetting(caseSamplingAdminSettingDto);
		assertEquals(updaterSettingsResponse.getDescription(), "No data in CaseCheckAdminSetting table");
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testUpdateCaseSamplingAdminSetting() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
		CaseSamplingAdminSettingDto caseSamplingAdminSettingDto = new CaseSamplingAdminSettingDto();
		CaseSamplingAdminSetting caseSamplingAdminSetting = new CaseSamplingAdminSetting();
		caseSamplingAdminSetting.setNumMinYearTarget(2);
		caseSamplingAdminSetting.setNumMaxYearTarget(3);
		caseSamplingAdminSetting.setNumMustSample(3);
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingAdminSetting);
		int response = caseSamplingAdminSettingDaoImpl.updateCaseSamplingAdminSetting(caseSamplingAdminSettingDto);
		assertEquals(response, 1);
	}

}
