package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.GlobalProfile;
import org.epo.cms.edfs.services.dossierpersistence.entity.UserProfile;
import org.epo.cms.edfs.services.settings.beans.ModuleList;
import org.epo.cms.edfs.services.settings.beans.Workspace;
import org.epo.cms.edfs.services.settings.beans.WorkspaceList;
import org.epo.cms.edfs.services.settings.dto.ModuleDto;
import org.epo.cms.edfs.services.settings.dto.UserProfileDto;
import org.epo.cms.edfs.services.settings.dto.WorkSpaceDto;
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
@PrepareForTest(UserProfileDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class UserProfileDAoImplTest {
  @InjectMocks
  UserProfileDaoImpl userProfileDAO;
  @Mock
  Criteria mockCriteria;

  @Mock
  Session session;
  @Mock
  SessionFactory sessionFactory;
  UserProfileDto userProfileDto;
  ModuleDto moduleDto;
  WorkSpaceDto workSpaceDto;
  UserProfile userProfile;
  List<UserProfile> userProfileList;
  ModuleList moduleList;
  WorkspaceList workspaceList;
	
  @Before
  public void setUp() throws CustomException {
    userProfileDto = new UserProfileDto();
    userProfileDto.setUserProfileId(1);
    userProfileDto.setUserId("DN89578");
    moduleDto = new ModuleDto();
    moduleDto.setModuleId(1);
    moduleDto.setModuleName("moduleName");
    userProfileDto.setModule(moduleDto);
    workSpaceDto = new WorkSpaceDto();
    workSpaceDto.setWorkSpaceId(1);
    workSpaceDto.setWorkSpaceName("workSpaceName");
    userProfileDto.setWorkSpace(workSpaceDto);
    userProfile = new UserProfile();
    userProfile.setActive(true);
    userProfile.setAlertLevel("low");
    userProfile.setUserProfileId(1);
    userProfile.setUserId("DN89578");
    userProfileList = new ArrayList<>();
    
    userProfileList.add(userProfile);
    moduleList = new ModuleList();
	workspaceList = new WorkspaceList();
    MockitoAnnotations.initMocks(this);
  }
  @Test
  public void testUpdateUserProfile() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(UserProfile.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(userProfile);
    UserProfile userProfileExp = userProfileDAO.updateUserProfile(userProfileDto);
    assertNotNull(userProfileExp);
    assertEquals(userProfileExp.getUserId(), userProfile.getUserId());
  }
  @Test
  public void testResetToDefaultUserProfile() throws CustomException{
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(UserProfile.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.list()).thenReturn(userProfileList);
    String status = userProfileDAO.resetToDefaultUserProfile("DN89578", true);
    assertNotNull(status);
    assertEquals(SettingsConstants.UPDATE_SUCCESS, status);
    
  }
  @Test 
  public void testGetUserProfileList() throws CustomException{
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(UserProfile.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.list()).thenReturn(userProfileList);
    List<UserProfileDto> userProfileDtoList = userProfileDAO.getUserProfileList("DN89578");
    assertNotNull(userProfileDtoList);
    assertEquals(userProfileList.size(), userProfileDtoList.size());
  }
  
  /**
	 * @throws CustomException : CustomException
	 */
	@Test
	public void getUserProfileAlertLevel() throws CustomException {
		org.epo.cms.edfs.services.settings.beans.Module module = new org.epo.cms.edfs.services.settings.beans.Module();
		Workspace workspace = new Workspace();

		moduleList.setModuleId(20);
		List<ModuleList> moduleLists = new ArrayList<>();
		moduleLists.add(moduleList);
		module.setModuleList(moduleLists);

		workspaceList.setWorkspaceId(20);
		List<WorkspaceList> workspaceLists = new ArrayList<>();
		workspaceLists.add(workspaceList);
		workspace.setWorkspaceList(workspaceLists);

		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria(UserProfile.class)).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(userProfile);

		String notificationAlertLevel = userProfileDAO.getUserProfileAlertLevel(module, workspace,"DN89578");
		assertEquals(notificationAlertLevel, "low");
	}

	/**
	 * @throws CustomException : CustomException
	 */
	@Test
	public void getNullUserProfileAlertLevel() throws CustomException {
		org.epo.cms.edfs.services.settings.beans.Module module = new org.epo.cms.edfs.services.settings.beans.Module();
		Workspace workspace = new Workspace();

		moduleList.setModuleId(20);
		List<ModuleList> moduleLists = new ArrayList<>();
		moduleLists.add(moduleList);
		module.setModuleList(moduleLists);

		workspaceList.setWorkspaceId(20);
		List<WorkspaceList> workspaceLists = new ArrayList<>();
		workspaceLists.add(workspaceList);
		workspace.setWorkspaceList(workspaceLists);

		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria(UserProfile.class)).thenReturn(mockCriteria);
		Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
		// Returning Empty List
		Mockito.when(mockCriteria.uniqueResult()).thenReturn(new UserProfile());

		String notificationAlertLevel = userProfileDAO.getUserProfileAlertLevel(module, workspace, "DN89578");
		assertEquals(notificationAlertLevel, null);
	}
  
}
