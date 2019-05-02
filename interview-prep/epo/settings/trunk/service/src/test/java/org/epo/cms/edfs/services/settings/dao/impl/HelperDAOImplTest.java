package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.Helper;
import org.epo.cms.edfs.services.settings.dto.HelperDto;
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
@PrepareForTest(HelperDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class HelperDAOImplTest {

  @InjectMocks
  HelperDaoImpl helperDAO;

  private Criteria mockCriteria;
  @Mock
  Session session;
  @Mock
  private SessionFactory sessionFactory;
  Helper helper;
  List<Helper> helperList;

  @Before
  public void setUp() throws CustomException {
    helper = new Helper();
    helper.setHelperId(1);
    helper.setHelperName("helperName");
    helper.setModuleOrWorkspace("moduleOrWorkspace");
    helperList = new ArrayList<>();
    helperList.add(helper);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetHelperDetails() throws CustomException {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(Helper.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(helper);
    HelperDto helperDto = helperDAO.getHelperDetails("helperName", "moduleOrWorkspace");
    assertNotNull(helperDto);
    assertEquals(helper.getHelperId(), helperDto.getHelperId());
    assertEquals(helper.getHelperName(), helperDto.getHelperName());
    assertEquals(helper.getModuleOrWorkspace(), helperDto.getModuleOrWorkspace());
  }

  @Test
  public void testGetHelperList() throws CustomException {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(Helper.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.list()).thenReturn(helperList);
    
    List<HelperDto> helperDtoList = helperDAO.getHelperList();
    assertNotNull(helperDtoList);
    assertEquals(helperList.get(0).getHelperId(), helperDtoList.get(0).getHelperId());
    assertEquals(helperList.get(0).getHelperName(), helperDtoList.get(0).getHelperName());
  }
}
