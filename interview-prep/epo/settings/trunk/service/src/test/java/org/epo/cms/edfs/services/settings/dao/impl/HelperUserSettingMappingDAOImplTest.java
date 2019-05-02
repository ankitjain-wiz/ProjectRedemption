package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.Helper;
import org.epo.cms.edfs.services.dossierpersistence.entity.HelperUserSettingMapping;
import org.epo.cms.edfs.services.settings.dto.HelperDto;
import org.epo.cms.edfs.services.settings.dto.HelperUserSettingMappingDto;
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
@PrepareForTest(HelperUserSettingMappingDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class HelperUserSettingMappingDAOImplTest {

  @InjectMocks
  HelperUserSettingMappingDaoImpl  helperUserSettingMappingDAO;
  
  @Mock
  Criteria criteria;
  @Mock
  Session session;
  @Mock
  private SessionFactory sessionFactory;
  
  private Criteria mockCriteria;
  HelperUserSettingMapping helperUserSettingMapping;
  HelperUserSettingMappingDto helperUserSettingMappingDto;
  HelperDto helperDto;
  Helper helper;
  List<HelperUserSettingMapping> helperUserSettingMappingList;
  @Before
  public void setUp() throws CustomException {
    helperUserSettingMappingDto = new HelperUserSettingMappingDto();
    helperDto = new HelperDto();
    helperDto.setHelperId(1);
    helperUserSettingMappingDto.setHelperUserSettingMappingId(1);
    helperUserSettingMappingDto.setUserId("DN89578");
    helperUserSettingMappingDto.setActive(true);
    helperUserSettingMappingDto.setHelper(helperDto);
    helperUserSettingMapping = new HelperUserSettingMapping();
    helperUserSettingMapping.setActive(true);
    helperUserSettingMapping.setHelperUserSettingMappingId(1);
    helperUserSettingMapping.setUserId("DN89578");
    helper = new Helper();
    helper.setHelperId(1);
    helperUserSettingMapping.setHelper(helper);
    helperUserSettingMappingList = new ArrayList<>();
    helperUserSettingMappingList.add(helperUserSettingMapping);
    helperUserSettingMappingList.add(helperUserSettingMapping);
    MockitoAnnotations.initMocks(this);
  }
  @Test
  public void testUpdateHelperUserSettingMapping() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(HelperUserSettingMapping.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(helperUserSettingMapping);
    HelperUserSettingMapping helperUserSettingMappingExp = helperUserSettingMappingDAO.updateHelperUserSettingMapping(helperUserSettingMappingDto);
    assertNotNull(helperUserSettingMappingExp);
    assertEquals(true, helperUserSettingMappingExp.getActive());
    //assertEquals(1,helperUserSettingMappingExp.getHelperUserSettingMappingId());
    assertEquals(helperUserSettingMapping.getUserId(),helperUserSettingMappingExp.getUserId());
  }
  @Test
  public void testInsertHelperUserSettingMapping() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(HelperUserSettingMapping.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    HelperUserSettingMapping helperUserSettingMappingExp = helperUserSettingMappingDAO.updateHelperUserSettingMapping(helperUserSettingMappingDto);
    assertNotNull(helperUserSettingMappingExp);
    assertEquals(0,helperUserSettingMappingExp.getHelperUserSettingMappingId());
  }
  @Test
  public void testResetHelperUserSettingMapping() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(HelperUserSettingMapping.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.list()).thenReturn(helperUserSettingMappingList);
    String status = helperUserSettingMappingDAO.resetHelperUserSettingMapping(helperUserSettingMappingDto.getUserId());
    assertNotNull(status);
    assertEquals(SettingsConstants.UPDATE_SUCCESS, status);
  }
  
  @Test
  public void testUserHelperList() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(HelperUserSettingMapping.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    
    Mockito.when(mockCriteria.list()).thenReturn(helperUserSettingMappingList);
    List<HelperUserSettingMappingDto> helperUserSettingMappingDtoList = helperUserSettingMappingDAO.userHelperList("DN89578");
    assertNotNull(helperUserSettingMappingDtoList);
    
  }
  
  @Test
  public void testUserHelperListEmpty() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(HelperUserSettingMapping.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.list()).thenReturn(new ArrayList<>());
    List<HelperUserSettingMappingDto> helperUserSettingMappingDtoList = helperUserSettingMappingDAO.userHelperList("DN89578");
    assertEquals(helperUserSettingMappingDtoList.size(), 0);
    
  }
  
}
