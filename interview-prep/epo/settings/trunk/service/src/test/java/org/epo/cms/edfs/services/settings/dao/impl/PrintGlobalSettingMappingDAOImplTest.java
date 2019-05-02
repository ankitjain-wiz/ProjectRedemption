package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertNotNull;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintGlobalSettingMapping;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintOption;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.settings.dto.PrintGlobalSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.PrintOptionDto;
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
@PrepareForTest(PrintGlobalSettingMappingDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class PrintGlobalSettingMappingDAOImplTest {

  @InjectMocks
  PrintGlobalSettingMappingDaoImpl printGlobalSettingMappingDAO;
  @Mock
  Criteria mockCriteria;

  @Mock
  Session session;
  @Mock
  SessionFactory sessionFactory;

  PrintGlobalSettingMapping printGlobalSettingMapping;
  PrintGlobalSettingMappingDto printGlobalSettingMappingDto;
  PrintOptionDto printOptionDto;
  RoleDto roleDto;
  PrintOption printOption;
  Role role;

  @Before
  public void setUp() throws CustomException {
    printGlobalSettingMappingDto = new PrintGlobalSettingMappingDto();
    printGlobalSettingMappingDto.setPrintGlobalSettingMappingId(1);
    printOptionDto = new PrintOptionDto();
    printOptionDto.setPrintOptionId(1);
    printOptionDto.setPrintAdditionalInfo("printAdditionalInfo");
    printGlobalSettingMappingDto.setPrintOption(printOptionDto);
    roleDto = new RoleDto();
    roleDto.setRoleId(1);
    printGlobalSettingMappingDto.setRole(roleDto);
    printGlobalSettingMapping = new PrintGlobalSettingMapping();
    printGlobalSettingMapping.setPrintGlobalSettingMappingId(1);
    printOption = new PrintOption();
    printOption.setPrintAdditionalInfo("printAdditionalInfo");
    printOption.setPrintOptionId(1);
    printGlobalSettingMapping.setPrintOption(printOption);
    role = new Role();
    role.setRoleId(1);
    printGlobalSettingMapping.setRole(role);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testUpdatePrintGlobalSettingMappingDetails() throws CustomException {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PrintGlobalSettingMapping.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(printGlobalSettingMapping);
    PrintGlobalSettingMapping PrintGlobalSettingMappingExp = printGlobalSettingMappingDAO
        .updatePrintGlobalSettingMappingDetails(printGlobalSettingMappingDto);
    assertNotNull(PrintGlobalSettingMappingExp);
  }
}
