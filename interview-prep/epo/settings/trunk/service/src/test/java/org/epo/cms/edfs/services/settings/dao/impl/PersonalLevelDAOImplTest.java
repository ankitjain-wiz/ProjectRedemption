package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalLevel;
import org.epo.cms.edfs.services.settings.dto.PersonalLevelDto;
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
@PrepareForTest(PersonalLevelDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class PersonalLevelDAOImplTest {

  @InjectMocks
  PersonalLevelDaoImpl personalLevelDAO;
  @Mock
  Criteria mockCriteria;
  
  @Mock
  Session session;
  @Mock
  SessionFactory sessionFactory;

  PersonalLevel personalLevel;
  @Before
  public void setUp() throws CustomException {
    personalLevel = new PersonalLevel();
    personalLevel.setPersonalLevelId(1);
    personalLevel.setLevelName("levelName");
    MockitoAnnotations.initMocks(this);
  }
  @Test
  public void testGetPersonalLevel() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PersonalLevel.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(personalLevel);
    PersonalLevelDto persoanlLevelDto = personalLevelDAO.getPersonalLevel("levelName");
    assertNotNull(persoanlLevelDto);
    assertEquals(personalLevel.getPersonalLevelId(), persoanlLevelDto.getPersonalLevelId());
  }
  @Test(expected = NullPointerException.class)
  public void testGetModuleException() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PersonalLevel.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    PersonalLevelDto personalLevelDto = personalLevelDAO.getPersonalLevel("levelName");
    assertNotNull(personalLevelDto);
  }
}
