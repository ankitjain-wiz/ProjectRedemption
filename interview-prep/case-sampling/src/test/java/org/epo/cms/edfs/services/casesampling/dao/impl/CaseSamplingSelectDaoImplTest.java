package org.epo.cms.edfs.services.casesampling.dao.impl;

import static org.junit.Assert.assertEquals;

import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingSelectDto;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingSelect;
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
@PrepareForTest({ CaseSamplingSelectDaoImpl.class })
public class CaseSamplingSelectDaoImplTest {

	@InjectMocks
	CaseSamplingSelectDaoImpl caseSamplingSelectDaoImpl;

	@Mock
	Criteria mockCriteria;

	@Mock
	Session session;

	@Mock
	SessionFactory sessionFactory;

	@Mock
	ResponseValidator responseValidator;

	/* private static final long directorateId = 1; */
	private static final String userId = "Z1";
	/* private static final int year = 2018; */

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetSamplingDetailEmpty() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingSelect caseSamplingSelect = new CaseSamplingSelect();
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingSelect);

		CaseSamplingSelectDto caseSamplingSelectDto = caseSamplingSelectDaoImpl.getSamplingDetail(userId);
		assertEquals(caseSamplingSelectDto.getComment(), null);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetSamplingDetail() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingSelect caseSamplingSelect = new CaseSamplingSelect();
		caseSamplingSelect.setUserId(userId);
		caseSamplingSelect.setIsSampled(false);
		caseSamplingSelect.setNumPlannedToSample(3);
		caseSamplingSelect.setCaseSamplingSelectId(1);

		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingSelect);

		CaseSamplingSelectDto caseSamplingSelectDto = caseSamplingSelectDaoImpl.getSamplingDetail(userId);
		assertEquals(caseSamplingSelectDto.getCaseSamplingSelectId(), 1);

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testUpdateSamplingStatusEmpty() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingSelect caseSamplingSelect = null;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingSelect);

		caseSamplingSelectDaoImpl.updateSamplingStatus(userId);
		assertEquals(caseSamplingSelect, null);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testUpdateSamplingStatus() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingSelect caseSamplingSelect = new CaseSamplingSelect();
		caseSamplingSelect.setUserId(userId);
		caseSamplingSelect.setIsSampled(false);
		caseSamplingSelect.setNumPlannedToSample(3);
		caseSamplingSelect.setCaseSamplingSelectId(1);

		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingSelect);
		caseSamplingSelectDaoImpl.updateSamplingStatus(userId);
		assertEquals(caseSamplingSelect.getIsSampled(), true);

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testUpdateNextNumToSampleEmpty() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingSelect caseSamplingSelect = null;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingSelect);
		//caseSamplingSelectDaoImpl.updateNextNumToSample(userId, 0,true);

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testUpdateNextNumToSample() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingSelect caseSamplingSelect = new CaseSamplingSelect();
		caseSamplingSelect.setUserId(userId);
		caseSamplingSelect.setIsSampled(false);
		caseSamplingSelect.setNumPlannedToSample(3);
		caseSamplingSelect.setCaseSamplingSelectId(1);

		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingSelect);
		//caseSamplingSelectDaoImpl.updateNextNumToSample(userId, 0,false);
		//assertEquals(caseSamplingSelect.getIsSampled(), true);

	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testUpdateNextNumToSampleByOneEmpty() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
		CaseSamplingSelect caseSamplingSelect = null;
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingSelect);
		caseSamplingSelectDaoImpl.updateNextNumToSampleByOne(userId);
		

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testUpdateNextNumToSampleByOne() throws CustomException {

		mockCriteria = Mockito.mock(Criteria.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria((Class) Mockito.isNull())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);

		CaseSamplingSelect caseSamplingSelect = new CaseSamplingSelect();
		caseSamplingSelect.setUserId(userId);
		caseSamplingSelect.setIsSampled(false);
		caseSamplingSelect.setNumPlannedToSample(3);
		caseSamplingSelect.setCaseSamplingSelectId(1);

		Mockito.when(mockCriteria.uniqueResult()).thenReturn(caseSamplingSelect);
		caseSamplingSelectDaoImpl.updateNextNumToSampleByOne(userId);
		assertEquals(caseSamplingSelect.getNumPlannedToSample(), 4);

	}

}
