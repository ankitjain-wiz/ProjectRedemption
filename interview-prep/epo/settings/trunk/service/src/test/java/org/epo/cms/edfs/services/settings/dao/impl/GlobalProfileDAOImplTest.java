package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.GlobalProfile;
import org.epo.cms.edfs.services.dossierpersistence.entity.Module;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.dossierpersistence.entity.WorkSpace;
import org.epo.cms.edfs.services.settings.beans.ModuleList;
import org.epo.cms.edfs.services.settings.beans.Workspace;
import org.epo.cms.edfs.services.settings.beans.WorkspaceList;
import org.epo.cms.edfs.services.settings.dto.GlobalProfileDto;
import org.epo.cms.edfs.services.settings.dto.ModuleDto;
import org.epo.cms.edfs.services.settings.dto.RoleDto;
import org.epo.cms.edfs.services.settings.dto.WorkSpaceDto;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
@PrepareForTest(GlobalProfileDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class GlobalProfileDAOImplTest {

	@InjectMocks
	GlobalProfileDaoImpl globalProfileDAO;
	@Mock
	Criteria criteria;
	@Mock
	Session session;
	@Mock
	private SessionFactory sessionFactory;

	GlobalProfileDto globalProfileDto;
	ModuleDto moduleDto;
	WorkSpaceDto workSpaceDto;
	RoleDto roleDto;
	GlobalProfile globalProfile;
	ModuleList moduleList;
	WorkspaceList workspaceList;

	@Before
	public void setUp() throws CustomException {
		globalProfileDto = new GlobalProfileDto();
		moduleDto = new ModuleDto();
		moduleDto.setModuleId(1);
		moduleDto.setModuleName("moduleName");
		globalProfileDto.setAlertLevel("low");
		globalProfileDto.setGlobalProfileId(1);
		globalProfileDto.setModule(moduleDto);
		workSpaceDto = new WorkSpaceDto();
		workSpaceDto.setWorkSpaceId(1);
		workSpaceDto.setWorkSpaceName("workSpaceName");
		globalProfileDto.setWorkSpace(workSpaceDto);
		roleDto = new RoleDto();
		roleDto.setRoleId(1);
		roleDto.setRoleName("roleName");
		globalProfileDto.setRole(roleDto);
		globalProfile = new GlobalProfile();
		globalProfile.setAlertLevel("low");
		globalProfile.setGlobalProfileId(1);

		moduleList = new ModuleList();
		workspaceList = new WorkspaceList();

		MockitoAnnotations.initMocks(this);
	}

	/**
	 * @throws CustomException : CustomException
	 */
	@Test
	public void testUpdateGlobalProfile() throws CustomException {

		GlobalProfile globalProfile = getGlobalProfileDetails(moduleDto, workSpaceDto, roleDto);
		GlobalProfile globalProfile1 = globalProfileDAO.updateGlobalProfile(globalProfileDto);
		assertNotNull(globalProfile1);
		assertEquals(globalProfile1.getAlertLevel(), globalProfile.getAlertLevel());
		assertEquals(globalProfile.getModule().getModuleId(), globalProfile1.getModule().getModuleId());
		assertEquals(globalProfile.getWorkSpace().getWorkSpaceId(), globalProfile1.getWorkSpace().getWorkSpaceId());
		assertEquals(globalProfile.getRole().getRoleId(), globalProfile.getRole().getRoleId());

	}

	private GlobalProfile getGlobalProfileDetails(ModuleDto moduleDto, WorkSpaceDto workSpaceDto, RoleDto roleDto) {
		Module module = new Module();
		module.setModuleId(moduleDto.getModuleId());
		WorkSpace workSpace = new WorkSpace();
		workSpace.setWorkSpaceId(workSpaceDto.getWorkSpaceId());
		Role role = new Role();
		role.setRoleId(roleDto.getRoleId());
		globalProfile.setRole(role);
		globalProfile.setModule(module);
		globalProfile.setWorkSpace(workSpace);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria(GlobalProfile.class)).thenReturn(criteria);
		Mockito.when(criteria.add(Restrictions.eq("role", role))).thenReturn(criteria);
		Mockito.when(criteria.uniqueResult()).thenReturn(globalProfile);
		return globalProfile;

	}

	/**
	 * @throws CustomException : CustomException
	 */
	@Test
	public void getGlobalProfileAlertLevel() throws CustomException {
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
		
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleId(1);

		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria(GlobalProfile.class)).thenReturn(criteria);
		Mockito.when(criteria.add(Mockito.any())).thenReturn(criteria);
		Mockito.when(criteria.uniqueResult()).thenReturn(globalProfile);

		String notificationAlertLevel = globalProfileDAO.getGlobalProfileAlertLevel(module, workspace,roleDto);
		assertEquals(notificationAlertLevel, "low");
	}

	/**
	 * @throws CustomException : CustomException
	 */
	@Test
	public void getNullGlobalProfileAlertLevel() throws CustomException {
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
		
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleId(1);

		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria(GlobalProfile.class)).thenReturn(criteria);
		Mockito.when(criteria.add(Mockito.any())).thenReturn(criteria);
		// Returning Empty List
		Mockito.when(criteria.uniqueResult()).thenReturn(new GlobalProfile());

		String notificationAlertLevel = globalProfileDAO.getGlobalProfileAlertLevel(module, workspace,roleDto);
		assertEquals(notificationAlertLevel, null);
	}

}
