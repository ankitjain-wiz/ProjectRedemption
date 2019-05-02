package org.epo.cms.edfs.services.common.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

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
public class ProductOrderDaoImplTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Criteria mockCriteria;

	@Mock
	private Session session;

	@InjectMocks
	private ProductOrderDaoImpl productOrderDaoImpl;

	private int productOrderId = 1;

	private ProductOrder productOrder = new ProductOrder();

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.createCriteria(any(Class.class))).thenReturn(mockCriteria);
		when(mockCriteria.add(any())).thenReturn(mockCriteria);

		productOrder.setProductOrderId(1);
		productOrder.setDossierNumber("FA       826368");
	}

	@Test
	public void findByProductOrderIdTest() {
		when(mockCriteria.uniqueResult()).thenReturn(productOrder);
		assertNotNull(productOrderDaoImpl.findByProductOrderId(productOrderId));
	}

	@Test
	public void saveProductOrderTest() {
		when(session.save(productOrder)).thenReturn(1);
		assertNotNull(productOrderDaoImpl.saveProductOrder(productOrder));
	}
}
