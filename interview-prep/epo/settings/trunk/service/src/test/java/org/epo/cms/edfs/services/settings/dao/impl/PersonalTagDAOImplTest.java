package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTag;
import org.epo.cms.edfs.services.settings.dto.PersonalTagDto;
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
@PrepareForTest(PersonalTagDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class PersonalTagDAOImplTest {
  @InjectMocks
  PersonalTagDaoImpl personalTagDAO;
  @Mock
  Criteria mockCriteria;
  
  @Mock
  Session session;
  @Mock
  SessionFactory sessionFactory;

  PersonalTag personalTag;
  @Before
  public void setUp() throws CustomException {
    personalTag = new PersonalTag();
    personalTag.setPersonalTagId(1);
    personalTag.setTagName("tagName");
    MockitoAnnotations.initMocks(this);
  }
  @Test
  public void testGetPersonalTag() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PersonalTag.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(personalTag);
    PersonalTagDto personalTagDto = personalTagDAO.getPersonalTag("tagName");
    assertNotNull(personalTagDto);
    assertEquals(personalTag.getPersonalTagId(), personalTagDto.getPersonalTagId());
  }
  @Test(expected = NullPointerException.class)
  public void testGetModuleException() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PersonalTag.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    PersonalTagDto personalTagDto = personalTagDAO.getPersonalTag("tagName");
    assertNotNull(personalTagDto);
  }
}
