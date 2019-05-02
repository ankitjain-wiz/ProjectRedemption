package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalLevel;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTag;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTagUserSettingMapping;
import org.epo.cms.edfs.services.settings.dto.PersonalLevelDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagUserSettingMappingDto;
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

/**
 * @author ankitjain2
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PersonalTagUserSettingMappingDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class PersonalTagUserSettingMappingDAOImplTest {

  @InjectMocks
  PersonalTagUserSettingMappingDaoImpl personalTagUserSettingMappingDAO;
  @Mock
  Criteria mockCriteria;

  @Mock
  Session session;
  @Mock
  SessionFactory sessionFactory;

  PersonalTagUserSettingMappingDto personalTagUserSettingMappingDto;
  PersonalLevelDto personalLevelDto;
  PersonalTagDto personalTagDto;
  PersonalTagUserSettingMapping personalTagUserSettingMapping;
  PersonalLevel personalLevel;
  PersonalTag personalTag;
  List<PersonalTagUserSettingMapping> personalTagUserSettingMappingList;
  @Before
  public void setUp() throws CustomException {
    personalTagUserSettingMappingDto = new PersonalTagUserSettingMappingDto();
    personalTagUserSettingMappingDto.setActive(true);
    personalLevelDto = new PersonalLevelDto();
    personalLevelDto.setPersonalLevelId(1);
    personalLevelDto.setLevelName("levelName");

    personalTagDto = new PersonalTagDto();
    personalTagDto.setPersonalTagId(1);
    personalTagDto.setTagName("tagName");
    personalTagUserSettingMappingDto.setPersonalLevel(personalLevelDto);
    personalTagUserSettingMappingDto.setPersonalTag(personalTagDto);
    personalTagUserSettingMappingDto.setUserId("DN89578");
    personalTagUserSettingMappingDto.setPersonalTagUserSettingMappingId(1);
    personalTagUserSettingMapping = new PersonalTagUserSettingMapping();
    personalTagUserSettingMapping.setPersonalTagUserSettingMappingId(1);
    personalLevel = new PersonalLevel();
    personalLevel.setPersonalLevelId(1);
    personalTagUserSettingMapping.setPersonalLevel(personalLevel);
    personalTag = new PersonalTag();
    personalTag.setPersonalTagId(1);
    personalTagUserSettingMapping.setPersonalTag(personalTag);
    personalTagUserSettingMappingList = new ArrayList<>();
    personalTagUserSettingMappingList.add(personalTagUserSettingMapping);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testUpdatePersonalTagUserSettingMapping() throws CustomException {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PersonalTagUserSettingMapping.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(personalTagUserSettingMapping);
    PersonalTagUserSettingMapping personalTagUserSettingMappingExp =
        personalTagUserSettingMappingDAO.updatePersonalTagUserSettingMapping(personalTagUserSettingMappingDto);
    assertEquals(personalTagUserSettingMappingExp, null);
    /*assertNotNull(personalTagUserSettingMappingExp);
    assertEquals(0,personalTagUserSettingMappingExp.getPersonalTagUserSettingMappingId());
    assertEquals(true, personalTagUserSettingMappingExp.getActive());*/
  }
  
  
  @Test
  public void testUpdatePersonalTagUserSettingMappingInsert() throws CustomException {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PersonalTagUserSettingMapping.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    
    PersonalTagUserSettingMapping personalTagUserSettingMappingExp =
        personalTagUserSettingMappingDAO
            .updatePersonalTagUserSettingMapping(personalTagUserSettingMappingDto);
    assertNotNull(personalTagUserSettingMappingExp);
    assertEquals(true, personalTagUserSettingMappingExp.getActive());
  }
  
  @Test
  public void testRestPersonalTagUserSettingMapping() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PersonalTagUserSettingMapping.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.list()).thenReturn(personalTagUserSettingMappingList);
    String status = personalTagUserSettingMappingDAO.restPersonalTagUserSettingMapping("DN89578");
    assertNotNull(status);
    assertEquals(SettingsConstants.UPDATE_SUCCESS, status);
  }
  
 
@Test
  public void testCheckEmptyPersonalTagUserSettingMapping()
  {
	  mockCriteria = Mockito.mock(Criteria.class);
	    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
	    Mockito.when(session.createCriteria(PersonalTagUserSettingMapping.class))
	        .thenReturn(mockCriteria);
	    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
	    Mockito.when(mockCriteria.list()).thenReturn(new ArrayList<>());
	    boolean flag=personalTagUserSettingMappingDAO.checkEmptyPersonalTagUserSettingMapping("DN89578");
	    assertEquals(flag, true);
  }
  
  @Test
  public void testNotCheckEmptyPersonalTagUserSettingMapping()
  {

	  mockCriteria = Mockito.mock(Criteria.class);
	    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
	    Mockito.when(session.createCriteria(PersonalTagUserSettingMapping.class))
	        .thenReturn(mockCriteria);
	    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
	    Mockito.when(mockCriteria.list()).thenReturn(personalTagUserSettingMappingList);
	    boolean flag=personalTagUserSettingMappingDAO.checkEmptyPersonalTagUserSettingMapping("DN89578");
	    assertEquals(flag, false);
  }
  
  @Test
  public void testGetPersonalTagUserSettingMapping() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PersonalTagUserSettingMapping.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.list()).thenReturn(personalTagUserSettingMappingList);
    List<PersonalTagUserSettingMappingDto> personalTagUserSettingMappingDtoList=personalTagUserSettingMappingDAO.getPersonalTagUserSettingMapping("DN89578");
    assertNotNull(personalTagUserSettingMappingDtoList);
   
  }
  
  @Test
  public void testGetPersonalTagUserSettingMappingEmpty() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PersonalTagUserSettingMapping.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.list()).thenReturn(new ArrayList<>());
    List<PersonalTagUserSettingMappingDto> personalTagUserSettingMappingDtoList=personalTagUserSettingMappingDAO.getPersonalTagUserSettingMapping("DN89578");
    assertEquals(personalTagUserSettingMappingDtoList.size(), 0);
   
  }
  
  
}
