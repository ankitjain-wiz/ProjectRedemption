package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.*;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.Module;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.settings.dto.ModuleDto;
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
@PrepareForTest(ModuleDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class ModuleDAOImplTest {

  @InjectMocks
  ModuleDaoImpl moduleDAO;

  private Criteria mockCriteria;
 
  @Mock
  private Session session;
  @Mock
  private SessionFactory sessionFactory;

  Module module;
  Role role;
  @Before
  public void setUp() throws CustomException {
    module = new Module();
    module.setModuleId(1);
    module.setModuleName("moduleName");
    role = new Role();
    role.setRoleId(1);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetModule() throws CustomException {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(Module.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(module);
    ModuleDto moduleDto = moduleDAO.getModule("moduleName", 1);
    assertNotNull(moduleDto);
    assertEquals(module.getModuleId(), moduleDto.getModuleId());
    assertEquals(module.getModuleName(), moduleDto.getModuleName());
  }
  @Test(expected = NullPointerException.class)
  public void testGetModuleException() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(Module.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    ModuleDto moduleDto = moduleDAO.getModule("moduleName", 1);
    assertNotNull(moduleDto);
  }
}
