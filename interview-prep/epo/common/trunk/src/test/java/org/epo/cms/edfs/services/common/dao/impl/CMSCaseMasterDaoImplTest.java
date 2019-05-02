package org.epo.cms.edfs.services.common.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.epo.cms.edfs.services.dossierpersistence.entity.Application;
import org.epo.cms.edfs.services.dossierpersistence.entity.CMSCaseMaster;
import org.epo.cms.edfs.services.dossierpersistence.entity.ProductOrder;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CMSCaseMasterDaoImplTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@InjectMocks
	private CMSCaseMasterDaoImpl cMSCaseMasterDaoImpl;

	private CMSCaseMaster cmsCaseMaster = new CMSCaseMaster();

	private String applicationNumber = "FRA     1655188";

	@Mock
	private Criteria mockCriteria;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		when(sessionFactory.getCurrentSession()).thenReturn(session);

		when(session.createCriteria(any(Class.class), any())).thenReturn(mockCriteria);
		when(mockCriteria.setFetchMode("cMSCaseMaster.application", FetchMode.JOIN)).thenReturn(mockCriteria);
		when(mockCriteria.createAlias("cMSCaseMaster.application", "app")).thenReturn(mockCriteria);
		when(mockCriteria.add(Restrictions.eq("app.applicationNumber", applicationNumber))).thenReturn(mockCriteria);

		cmsCaseMaster.setCmsCaseId(1);
		Application application = new Application();
		application.setApplicationId(1);
		application.setApplicationNumber(applicationNumber);
		application.setApplicationType("test");
		application.setFilingDatetime(new Date());
		application.setFilingLanguage("English");
		application.setFirstAbstractFigure("testDAta");
		application.setProceduralLanguage("English");
		application.setPublicationNumber("test");
		application.setReceiptDatetime(new Date());
		cmsCaseMaster.setApplication(application);
		ProductOrder productOrder = new ProductOrder();
		productOrder.setProductOrderId(1);
		productOrder.setDossierNumber("FA       826368");
		cmsCaseMaster.setProductOrder(productOrder);
	}

	@Test
	public void saveCMSCaseMasterTest() {
		when(session.save(cmsCaseMaster)).thenReturn(1);
		assertNotNull(cMSCaseMasterDaoImpl.saveCMSCaseMaster(cmsCaseMaster));
	}

	@Test
	public void findByApplicationNumberTest() {
		List<CMSCaseMaster> cMSCaseMasterList = new ArrayList<>();
		cMSCaseMasterList.add(cmsCaseMaster);
		when(mockCriteria.list()).thenReturn(cMSCaseMasterList);
		assertNotNull(cMSCaseMasterDaoImpl.findByApplicationNumber(applicationNumber));
	}

}
