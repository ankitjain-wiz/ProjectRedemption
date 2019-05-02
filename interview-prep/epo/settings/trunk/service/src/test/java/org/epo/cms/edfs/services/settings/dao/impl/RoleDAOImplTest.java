package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
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
@PrepareForTest(RoleDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class RoleDAOImplTest {

  @InjectMocks
  RoleDaoImpl roleDAO;
  @Mock
  Criteria mockCriteria;

  @Mock
  Session session;
  @Mock
  SessionFactory sessionFactory;
  Role role;
  List<Role> roleList;
  @Before
  public void setUp() throws CustomException {
    role = new Role();
    role.setRoleId(1);
    roleList = new ArrayList<>();
    roleList.add(role);
    MockitoAnnotations.initMocks(this);
  }
  @Test
  public void testGetRoleDeepCopy() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(Role.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(role);
    RoleDto roleDto = roleDAO.getRole("Chairman", true);
    assertNotNull(roleDto);
    assertEquals(role.getRoleId(), roleDto.getRoleId());
  }
  @Test
  public void testGetRole() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(Role.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(role);
    RoleDto roleDto = roleDAO.getRole("Chairman");
    assertNotNull(roleDto);
    assertEquals(role.getRoleId(), roleDto.getRoleId());
  }
  @Test(expected = NullPointerException.class)
  public void testGetRoleException() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(Role.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    RoleDto roleDto = roleDAO.getRole("Chairman");
    assertNotNull(roleDto);
  }
  @Test
  public void testGetRoleList() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(Role.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.list()).thenReturn(roleList);
    List<RoleDto> roleDtoList = roleDAO.getRoleList();
    assertNotNull(roleDtoList);
    assertEquals(roleList.size(), roleDtoList.size());
  }
  @Test(expected = NullPointerException.class)
  public void testGetRoleListException() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(Role.class))
        .thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    List<RoleDto> roleDtoList = roleDAO.getRoleList();
    assertNotNull(roleDtoList);
  }
}
