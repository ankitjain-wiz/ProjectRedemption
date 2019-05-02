package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTagGlobalSettingMapping;
import org.epo.cms.edfs.services.settings.dto.PersonalLevelDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagGlobalSettingMappingDto;
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
@PrepareForTest(PersonalTagGlobalSettingMappingDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class PersonalTagGlobalSettingMappingDAOImplTest {

  @InjectMocks
  PersonalTagGlobalSettingMappingDaoImpl personalTagGlobalSettingMappingDAO;
  @Mock
  Criteria mockCriteria;

  @Mock
  Session session;
  @Mock
  SessionFactory sessionFactory;

  PersonalTagGlobalSettingMapping personalTagGlobalSettingMapping;

  PersonalTagGlobalSettingMappingDto personalTagGlobalSettingMappingDto;
  PersonalLevelDto personalLevelDto;
  PersonalTagDto personalTagDto;
  RoleDto roleDto;

  @Before
  public void setUp() throws CustomException {
    personalTagGlobalSettingMapping = new PersonalTagGlobalSettingMapping();
    personalTagGlobalSettingMapping.setPersonalTagGlobalSettingMappingId(1);
    personalTagGlobalSettingMapping.setTitleName("titleName");
    personalTagGlobalSettingMappingDto = new PersonalTagGlobalSettingMappingDto();
    personalTagGlobalSettingMappingDto.setPersonalTagGlobalSettingMappingId(1);
    personalTagGlobalSettingMappingDto.setTitleName("titleName");
    personalLevelDto = new PersonalLevelDto();
    personalLevelDto.setPersonalLevelId(1);
    personalLevelDto.setLevelName("levelName");
    personalTagDto = new PersonalTagDto();
    personalTagDto.setPersonalTagId(1);
    personalTagDto.setTagName("tagName");
    roleDto = new RoleDto();
    roleDto.setRoleId(1);
    roleDto.setRoleName("roleName");
    personalTagGlobalSettingMappingDto.setPersonalTag(personalTagDto);
    personalTagGlobalSettingMappingDto.setPersonalLevel(personalLevelDto);
    personalTagGlobalSettingMappingDto.setRole(roleDto);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testUpdatePersonalTagGlobalSettingMapping() throws CustomException {
    PersonalTagGlobalSettingMapping personalTagGlobalSettingMapping =
        getPersonalTagGlobalSettingMapping(personalTagGlobalSettingMappingDto.getPersonalLevel(),
            personalTagGlobalSettingMappingDto.getPersonalTag(),
            personalTagGlobalSettingMappingDto.getRole());
    PersonalTagGlobalSettingMapping personalTagGlobalSettingMappingExp =
        personalTagGlobalSettingMappingDAO
            .updatePersonalTagGlobalSettingMapping(personalTagGlobalSettingMappingDto);
    assertNotNull(personalTagGlobalSettingMappingExp);
    assertEquals(personalTagGlobalSettingMapping.getPersonalTagGlobalSettingMappingId(),
        personalTagGlobalSettingMappingExp.getPersonalTagGlobalSettingMappingId());

  }

  @Test
  public void testUpdatePersonalTagGlobalSettingMappingElse() throws CustomException {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PersonalTagGlobalSettingMapping.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    PersonalTagGlobalSettingMapping personalTagGlobalSettingMappingExp =
        personalTagGlobalSettingMappingDAO
            .updatePersonalTagGlobalSettingMapping(personalTagGlobalSettingMappingDto);
    assertNotNull(personalTagGlobalSettingMappingExp);
    assertEquals(0, personalTagGlobalSettingMappingExp.getPersonalTagGlobalSettingMappingId());
    assertEquals(personalTagGlobalSettingMappingDto.getTitleName(), personalTagGlobalSettingMappingExp.getTitleName());
  }

  private PersonalTagGlobalSettingMapping getPersonalTagGlobalSettingMapping(
      PersonalLevelDto personalLevelDto, PersonalTagDto personalTagDto, RoleDto roleDto) {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PersonalTagGlobalSettingMapping.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(personalTagGlobalSettingMapping);

    return personalTagGlobalSettingMapping;
  }

}
