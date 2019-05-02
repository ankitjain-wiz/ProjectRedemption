package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.Helper;
import org.epo.cms.edfs.services.dossierpersistence.entity.HelperGlobalSettingMapping;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.settings.dto.HelperDto;
import org.epo.cms.edfs.services.settings.dto.HelperGlobalSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.RoleDto;
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
@PrepareForTest(HelperGlobalSettingMappingDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class HelperGlobalSettingMappingDAOImplTest {

  @InjectMocks
  HelperGlobalSettingMappingDaoImpl helperGlobalSettingMappingDAO;

  private Criteria mockCriteria;
  @Mock
  Session session;
  @Mock
  private SessionFactory sessionFactory;
  HelperGlobalSettingMappingDto helperGlobalSettingMappingDto;
  HelperDto helperDto;
  RoleDto roleDto;
  Helper helper;
  Role role;
  HelperGlobalSettingMapping helperGlobalSettingMapping;

  @Before
  public void setUp() throws CustomException {
    helperGlobalSettingMappingDto = new HelperGlobalSettingMappingDto();
    helperGlobalSettingMappingDto.setHelperGlobalSettingMappingId(1);
    
    helperDto = new HelperDto();
    helperDto.setHelperId(1);
    helperDto.setHelperName("helperName");
    helperGlobalSettingMappingDto.setHelper(helperDto);
    roleDto = new RoleDto();
    roleDto.setRoleId(1);
    roleDto.setRoleName("Chairman");
    helperGlobalSettingMappingDto.setRole(roleDto);
    helperGlobalSettingMapping = new HelperGlobalSettingMapping();
    helper = new Helper();
    helper.setHelperId(1);
    helperGlobalSettingMapping.setHelper(helper);
    helperGlobalSettingMapping.setHelperGlobalSettingMappingId(1);
    role = new Role();
    role.setRoleId(1);
    helperGlobalSettingMapping.setRole(role);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testRemoveHelperGlobalSettingMapping() throws CustomException {
    helperGlobalSettingMapping = gerHelperGlobalSettingMapping(
        helperGlobalSettingMappingDto.getHelper(), helperGlobalSettingMappingDto.getRole());
    HelperGlobalSettingMapping helperGlobalSettingMappingExc = helperGlobalSettingMappingDAO
        .updateHelperGlobalSettingMapping(helperGlobalSettingMappingDto);
    assertNotNull(helperGlobalSettingMappingExc);
    assertEquals(helperGlobalSettingMapping.getHelperGlobalSettingMappingId(),helperGlobalSettingMappingExc.getHelperGlobalSettingMappingId());
  }

  private HelperGlobalSettingMapping gerHelperGlobalSettingMapping(HelperDto helper, RoleDto role) {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(HelperGlobalSettingMapping.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(helperGlobalSettingMapping);

    return helperGlobalSettingMapping;
  }
  
  @Test
  public void testInsertHelperGlobalSettingMapping() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(HelperGlobalSettingMapping.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    helperGlobalSettingMappingDto.setCheck(true);
    HelperGlobalSettingMapping helperGlobalSettingMappingExc = helperGlobalSettingMappingDAO
        .updateHelperGlobalSettingMapping(helperGlobalSettingMappingDto);
    assertNotNull(helperGlobalSettingMappingExc);
    assertEquals(0, helperGlobalSettingMappingExc.getHelperGlobalSettingMappingId());
  }
  
  
}
