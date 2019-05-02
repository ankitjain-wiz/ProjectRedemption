package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintOption;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintUserSettingMapping;
import org.epo.cms.edfs.services.settings.dto.PrintOptionDto;
import org.epo.cms.edfs.services.settings.dto.PrintUserSettingMappingDto;
import org.epo.cms.edfs.services.settings.utils.SettingsConstants;
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
@PrepareForTest(PrintUserSettingMappingDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class PrintUserSettingMappingDAOImplTest {

  @InjectMocks
  PrintUserSettingMappingDaoImpl printUserSettingMappingDAO;
  @Mock
  Criteria mockCriteria;

  @Mock
  Session session;
  @Mock
  SessionFactory sessionFactory;
  PrintUserSettingMappingDto printUserSettingMappingDto;
  PrintOptionDto printOptionDto;
  PrintUserSettingMapping printUserSettingMapping;
  PrintOption printOption;
  List<PrintUserSettingMapping> printUserSettingMappingList;

  @Before
  public void setUp() throws CustomException {
    printUserSettingMappingDto = new PrintUserSettingMappingDto();
    printUserSettingMappingDto.setActive(true);
    printOptionDto = new PrintOptionDto();
    printOptionDto.setPrintOptionId(1);
    printOptionDto.setPrintAdditionalInfo("printAdditionalInfo");
    printUserSettingMappingDto.setPrintOption(printOptionDto);
    printUserSettingMappingDto.setPrintUserSettingMappingId(1);
    printUserSettingMappingDto.setUserId("DN89578");
    printUserSettingMapping = new PrintUserSettingMapping();
    printUserSettingMapping.setPrintUserSettingMappingId(1);
    printUserSettingMapping.setActive(true);
    printUserSettingMapping.setUserId("DN89578");
    printOption = new PrintOption();
    printOption.setPrintOptionId(1);
    printUserSettingMapping.setPrintOption(printOption);
    printUserSettingMappingList = new ArrayList<>(); 
    printUserSettingMappingList.add(printUserSettingMapping);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testUpdatePrintUserSettingMapping() throws CustomException {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PrintUserSettingMapping.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(printUserSettingMapping);
    PrintUserSettingMapping printUserSettingMappingExp =
        printUserSettingMappingDAO.updatePrintUserSettingMapping(printUserSettingMappingDto);
    assertNotNull(printUserSettingMappingExp);
    assertEquals(0,
        printUserSettingMappingExp.getPrintUserSettingMappingId());
  }
  @Test
  public void TestResetPrintUserSettings() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PrintUserSettingMapping.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.list()).thenReturn(printUserSettingMappingList);
    String status =printUserSettingMappingDAO.resetPrintUserSettings("DN89578");
    assertNotNull(status);
    assertEquals(status, SettingsConstants.UPDATE_SUCCESS);
  }
}
