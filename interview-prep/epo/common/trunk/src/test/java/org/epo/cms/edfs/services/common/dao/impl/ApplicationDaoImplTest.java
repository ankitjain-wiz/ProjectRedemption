package org.epo.cms.edfs.services.common.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.epo.cms.edfs.services.dossierpersistence.entity.Application;
import org.epo.cms.edfs.services.dossierpersistence.entity.CMSCaseMaster;
import org.epo.cms.edfs.services.dossierpersistence.entity.ProductOrder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationDaoImplTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Criteria mockCriteria;
	@Mock
	private Session session;

	@InjectMocks
	private ApplicationDaoImpl applicationDaoImpl;

	private Application application = new Application();

	private String applicationNo = "FRA     1655188";

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.createCriteria(any(Class.class))).thenReturn(mockCriteria);
		when(session.createCriteria(any(Class.class), any())).thenReturn(mockCriteria);
		when(mockCriteria.add(any())).thenReturn(mockCriteria);
		when(mockCriteria.setFetchMode(any(), any())).thenReturn(mockCriteria);
		when(mockCriteria.createAlias(any(), any())).thenReturn(mockCriteria);

		application = new Application();
		application.setApplicationId(1);
		application.setApplicationNumber(applicationNo);
		application.setApplicationType("test");
		application.setFilingDatetime(new Date());
		application.setFilingLanguage("English");
		application.setFirstAbstractFigure("testDAta");
		application.setProceduralLanguage("English");
		application.setPublicationNumber("test");
		application.setReceiptDatetime(new Date());
		Set<CMSCaseMaster> cmsCaseMasters = new HashSet<>();
		CMSCaseMaster cmsCaseMaster = new CMSCaseMaster();
		cmsCaseMaster.setCmsCaseId(1);
		cmsCaseMasters.add(cmsCaseMaster);
		application.setCmsCaseMaster(cmsCaseMasters);
		Set<ProductOrder> productOrders = new HashSet<>();
		ProductOrder productOrder = new ProductOrder();
		productOrder.setProductOrderId(1);
		productOrder.setDossierNumber("FA       826368");
		productOrders.add(productOrder);
		application.setProductOrder(productOrders);
	}

	@Test
	public void findByApplicationNumberTest() {
		when(mockCriteria.uniqueResult()).thenReturn(application);
		assertNotNull(applicationDaoImpl.findByApplicationNumber(applicationNo));
	}

	@Test
	public void saveApplicationTest() {
		when(session.save(application)).thenReturn(1);
		assertNotNull(applicationDaoImpl.saveApplication(application));
	}

	@Test
	public void getApplicationWithCMSCaseIDTest() {
		when(mockCriteria.uniqueResult()).thenReturn(application);
		assertNotNull(applicationDaoImpl.getApplicationWithCMSCaseID(applicationNo));

	}
}
