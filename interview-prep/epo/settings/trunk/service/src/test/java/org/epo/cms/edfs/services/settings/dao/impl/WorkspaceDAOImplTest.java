package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.WorkSpace;
import org.epo.cms.edfs.services.settings.dto.WorkSpaceDto;
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
@PrepareForTest(UserProfileDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class WorkspaceDAOImplTest {

  @InjectMocks
  WorkspaceDaoImpl workspaceDAO;
  @Mock
  Criteria mockCriteria;

  @Mock
  Session session;
  @Mock
  SessionFactory sessionFactory;
  WorkSpace workspace;

  @Before
  public void setUp() throws CustomException {
    workspace = new WorkSpace();
    workspace.setWorkSpaceId(1);
    workspace.setWorkSpaceName("workSpaceName");
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetWorkSpace() throws CustomException {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(WorkSpace.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(workspace);
    WorkSpaceDto workSpaceDto = workspaceDAO.getWorkSpace("workSpaceName", 1);
    assertNotNull(workSpaceDto);
    assertEquals(workspace.getWorkSpaceId(), workSpaceDto.getWorkSpaceId());
    assertEquals(workspace.getWorkSpaceName(), workSpaceDto.getWorkSpaceName());
  }

  @Test(expected = NullPointerException.class)
  public void testGetWorkspaceException() throws CustomException {
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(WorkSpace.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    WorkSpaceDto workSpaceDto = workspaceDAO.getWorkSpace("workSpaceName", 1);
    assertNotNull(workSpaceDto);
  }
}
