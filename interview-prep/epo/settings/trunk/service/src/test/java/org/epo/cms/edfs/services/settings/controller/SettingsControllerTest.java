package org.epo.cms.edfs.services.settings.controller;

import static org.junit.Assert.assertEquals;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.jcs.access.CacheAccess;
import org.epo.cms.edfs.services.common.beans.UserRolePermission;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ExceptionHandlerBean;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.useridrole.CommonCode;
import org.epo.cms.edfs.services.settings.beans.Notification;
import org.epo.cms.edfs.services.settings.beans.Role;
import org.epo.cms.edfs.services.settings.beans.RoleDetail;
import org.epo.cms.edfs.services.settings.beans.RoleSettingResponse;
import org.epo.cms.edfs.services.settings.beans.SettingsRequest;
import org.epo.cms.edfs.services.settings.beans.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.settings.service.GlobalSettingsService;
import org.epo.cms.edfs.services.settings.service.UserSettingsService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest({ SettingsController.class, ControllerLinkBuilder.class, StringUtils.class })
public class SettingsControllerTest {

	private static final String DESCRIPTION = "Updated Successfully";
	private static final String ROLE = "chairman";
	private static final String NOTIFICATION_LEVEL = "medium";
	private static final String SectionName = "Display";
	@InjectMocks
	SettingsController settingsController;
	@Mock
	private ResponseValidator responseValidator;

	@Mock
	private CacheAccess<Object, Object> permissionCache;

	@Mock
	private GlobalSettingsService globalSettingsService;

	@Mock
	private UserSettingsService userSettingsService;
	@Mock
	private HttpServletRequest request;
	@Mock
	private Principal principal;
	@Mock
	private SettingsRequest settingsRequest;

	@Mock
	private CommonCode commonCode;

	RoleSettingResponse roleSettingResponse;

	ExceptionHandlerBean exBean;

	private String epoUser = "EPOUSER@12345.EPO.PATENT";

	@Before
	public void setUp() throws CustomException {
		roleSettingResponse = getRoleSettingResponse();
		exBean = getExceptionHandlerBean();
		MockitoAnnotations.initMocks(this);
	}

	private ExceptionHandlerBean getExceptionHandlerBean() {
		ExceptionHandlerBean exBean = new ExceptionHandlerBean();
		exBean.setId("Ex");
		exBean.setStatusCode("500");
		exBean.setDetails("Custom Exceptoion");

		return exBean;
	}

	private RoleSettingResponse getRoleSettingResponse() {
		RoleSettingResponse roleSettingResponse = new RoleSettingResponse();
		Notification notification = new Notification();
		notification.setNotificationAlertLevel(NOTIFICATION_LEVEL);
		roleSettingResponse.setNotification(notification);
		return roleSettingResponse;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetRoleSettingsDetailsException() throws CustomException {
		Mockito.when(globalSettingsService.getGlobalSettings(Mockito.anyString())).thenReturn(roleSettingResponse);
		Mockito.when(responseValidator.getErrorResponseWithExpMsg(Mockito.anyObject(), Mockito.anyString()))
				.thenReturn(exBean);
		Mockito.when(commonCode.getUserId(request)).thenThrow(new IllegalArgumentException());
		RoleSettingResponse response = settingsController.getRoleSettingsDetails(ROLE, request);
		assertEquals(NOTIFICATION_LEVEL, response.getNotification().getNotificationAlertLevel());

	}

	@Test
	public void testGetRoleSettingsDetails() throws CustomException {
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(request.getUserPrincipal().getName()).thenReturn(epoUser);
		Mockito.when(commonCode.getUserId(request)).thenReturn(epoUser);
		Mockito.when(globalSettingsService.getGlobalSettings(Mockito.anyString())).thenReturn(roleSettingResponse);
		Mockito.when(responseValidator.getErrorResponseWithExpMsg(Mockito.anyObject(), Mockito.anyString()))
				.thenReturn(exBean);
		RoleSettingResponse response = settingsController.getRoleSettingsDetails(ROLE, request);
		assertEquals(NOTIFICATION_LEVEL, response.getNotification().getNotificationAlertLevel());
	}
	
	@Test
	public void testGetRoleSettingsDetailsNull() throws CustomException {
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(request.getUserPrincipal().getName()).thenReturn(epoUser);
		
		Mockito.when(globalSettingsService.getGlobalSettings(Mockito.anyString())).thenReturn(roleSettingResponse);
		Mockito.when(responseValidator.getErrorResponseWithExpMsg(Mockito.anyObject(), Mockito.anyString()))
				.thenReturn(exBean);
		RoleSettingResponse response = settingsController.getRoleSettingsDetails(ROLE, request);
		assertEquals(response, null);
		
	}


	@Test(expected = IllegalArgumentException.class)
	public void testGetUserSettingsDetailsException() throws CustomException {
		Mockito.when(userSettingsService.getUserSettingsDetails(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(roleSettingResponse);
		Mockito.when(responseValidator.getErrorResponseWithExpMsg(Mockito.anyObject(), Mockito.anyString()))
				.thenReturn(exBean);
		Mockito.when(commonCode.getUserId(request)).thenThrow(new IllegalArgumentException());
		RoleSettingResponse response = settingsController.getUserSettingsDetails(request);
		assertEquals(NOTIFICATION_LEVEL, response.getNotification().getNotificationAlertLevel());
	}

	@Test
	public void testGetUserSettingsDetails() throws CustomException {
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(request.getUserPrincipal().getName()).thenReturn(epoUser);
		Mockito.when(userSettingsService.getUserSettingsDetails(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(roleSettingResponse);
		Mockito.when(responseValidator.getErrorResponseWithExpMsg(Mockito.anyObject(), Mockito.anyString()))
				.thenReturn(exBean);
		Mockito.when(commonCode.getUserId(request)).thenReturn(epoUser);
		RoleSettingResponse response = settingsController.getUserSettingsDetails(request);
		assertEquals(NOTIFICATION_LEVEL, response.getNotification().getNotificationAlertLevel());

	}
	
	@Test
	public void testGetUserSettingsDetailsNull() throws CustomException {
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(request.getUserPrincipal().getName()).thenReturn(epoUser);
		
		Mockito.when(globalSettingsService.getGlobalSettings(Mockito.anyString())).thenReturn(roleSettingResponse);
		Mockito.when(responseValidator.getErrorResponseWithExpMsg(Mockito.anyObject(), Mockito.anyString()))
				.thenReturn(exBean);
		RoleSettingResponse response = settingsController.getUserSettingsDetails(request);
		assertEquals(response, null);
		
	}
	
	@Test
	public void testGetUserSettingsDetailsWithRole() throws CustomException {
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(request.getUserPrincipal().getName()).thenReturn(epoUser);
		Mockito.when(userSettingsService.getUserSettingsDetails(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(roleSettingResponse);
		Mockito.when(responseValidator.getErrorResponseWithExpMsg(Mockito.anyObject(), Mockito.anyString()))
				.thenReturn(exBean);
		Mockito.when(commonCode.getUserId(request)).thenReturn(epoUser);
		Mockito.when(commonCode.getUserRole(Mockito.any())).thenReturn("Examiner");
		RoleSettingResponse response = settingsController.getUserSettingsDetails(request);
		assertEquals(NOTIFICATION_LEVEL, response.getNotification().getNotificationAlertLevel());

	}
	
	

	@Test
	public void testGetUserSettingsDetailsWithUserRole() throws CustomException {
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(request.getUserPrincipal().getName()).thenReturn(epoUser);
		Mockito.when(commonCode.getUserId(request)).thenReturn(epoUser);
		Mockito.when(userSettingsService.getUserSettingsDetails(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(roleSettingResponse);
		Mockito.when(responseValidator.getErrorResponseWithExpMsg(Mockito.anyObject(), Mockito.anyString()))
				.thenReturn(exBean);
		List<UserRolePermission> roleList = new ArrayList<UserRolePermission>();
		UserRolePermission userRolePermission = new UserRolePermission();
		userRolePermission.setRole(ROLE);
		roleList.add(userRolePermission);
		Mockito.when(permissionCache.get(Mockito.any())).thenReturn(roleList);
		// PowerMockito.doReturn(20).when(classUnderTest, "privateApi",
		// anyString(), anyInt());
		RoleSettingResponse response = settingsController.getUserSettingsDetails(request);
		assertEquals(NOTIFICATION_LEVEL, response.getNotification().getNotificationAlertLevel());
	}

	@Test
	public void testUpdateRoleSettings() throws CustomException {
		UpdaterSettingsResponse updaterSettingsResponse = new UpdaterSettingsResponse();
		updaterSettingsResponse.setDescription(DESCRIPTION);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(request.getUserPrincipal().getName()).thenReturn(epoUser);
		Mockito.when(globalSettingsService.updateSettingsDetails(Mockito.anyObject(), Mockito.any(), Mockito.any()))
				.thenReturn(updaterSettingsResponse);
		ResponseEntity<UpdaterSettingsResponse> updateRoleSettingsResponse = settingsController.updateRoleSettings(ROLE,
				settingsRequest, request);
		assertEquals(HttpStatus.CREATED, updateRoleSettingsResponse.getStatusCode());
		assertEquals(DESCRIPTION, updateRoleSettingsResponse.getBody().getDescription());
	}
	
	


	@Test(expected = IllegalArgumentException.class)
	public void testUpdateRoleSettingsException() throws CustomException {
		UpdaterSettingsResponse updaterSettingsResponse = new UpdaterSettingsResponse();
		updaterSettingsResponse.setDescription(DESCRIPTION);
		Mockito.when(commonCode.getUserId(request)).thenThrow(new IllegalArgumentException());
		Mockito.when(responseValidator.getErrorResponseWithExpMsg(Mockito.anyObject(), Mockito.anyString()))
				.thenReturn(exBean);
		Mockito.when(globalSettingsService.updateSettingsDetails(Mockito.anyObject(), Mockito.any(), Mockito.any()))
				.thenReturn(updaterSettingsResponse);
		ResponseEntity<UpdaterSettingsResponse> updateRoleSettingsResponse = settingsController.updateRoleSettings(ROLE,
				settingsRequest, request);
		assertEquals(DESCRIPTION, updateRoleSettingsResponse.getBody().getDescription());
	}

	@Test
	public void testUpdateUserSettings() throws CustomException {
		UpdaterSettingsResponse updaterSettingsResponse = new UpdaterSettingsResponse();
		updaterSettingsResponse.setDescription(DESCRIPTION);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(request.getUserPrincipal().getName()).thenReturn(epoUser);
		Mockito.when(userSettingsService.updateUserSettingsDetails(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(updaterSettingsResponse);
		Mockito.when(commonCode.getUserId(request)).thenReturn(epoUser);
		ResponseEntity<UpdaterSettingsResponse> updateRoleSettingsResponse = settingsController
				.updateUserSettings(settingsRequest, request);
		assertEquals(HttpStatus.CREATED, updateRoleSettingsResponse.getStatusCode());
		assertEquals(DESCRIPTION, updateRoleSettingsResponse.getBody().getDescription());
	}
	
	
	@Test
	public void testUpdateUserSettingsNull() throws CustomException {
	
		ResponseEntity<UpdaterSettingsResponse> updateRoleSettingsResponse = settingsController
				.updateUserSettings(settingsRequest, request);
		assertEquals(updateRoleSettingsResponse.getBody(), null);
		
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateUserSettingsException() throws CustomException {
		UpdaterSettingsResponse updaterSettingsResponse = new UpdaterSettingsResponse();
		updaterSettingsResponse.setDescription(DESCRIPTION);
		Mockito.when(commonCode.getUserId(request)).thenThrow(new IllegalArgumentException());
		Mockito.when(responseValidator.getErrorResponseWithExpMsg(Mockito.anyObject(), Mockito.anyString()))
				.thenReturn(exBean);
		Mockito.when(userSettingsService.updateUserSettingsDetails(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(updaterSettingsResponse);
		ResponseEntity<UpdaterSettingsResponse> updateRoleSettingsResponse = settingsController
				.updateUserSettings(settingsRequest, request);
		assertEquals(DESCRIPTION, updateRoleSettingsResponse.getBody().getDescription());
	}

	@Test
	public void testResetUserSettings() throws CustomException {
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(request.getUserPrincipal().getName()).thenReturn(epoUser);
		Mockito.when(commonCode.getUserId(request)).thenReturn(epoUser);
		Mockito.when(userSettingsService.resetUserSettingsToGlobal(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(roleSettingResponse);
		RoleSettingResponse response = settingsController.resetUserSettings(SectionName, request);
		assertEquals(NOTIFICATION_LEVEL, response.getNotification().getNotificationAlertLevel());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testResetUserSettingsException() throws CustomException {
		UpdaterSettingsResponse updaterSettingsResponse = new UpdaterSettingsResponse();
		updaterSettingsResponse.setDescription(DESCRIPTION);

		Mockito.when(responseValidator.getErrorResponseWithExpMsg(Mockito.anyObject(), Mockito.anyString()))
				.thenReturn(exBean);
		Mockito.when(userSettingsService.resetUserSettingsToGlobal(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(roleSettingResponse);
		Mockito.when(commonCode.getUserId(request)).thenThrow(new IllegalArgumentException());
		RoleSettingResponse response = settingsController.resetUserSettings(SectionName, request);
		assertEquals(NOTIFICATION_LEVEL, response.getNotification().getNotificationAlertLevel());
	}

	@Test
	public void testGetRoleList() throws CustomException {
		RoleDetail roleDetail = new RoleDetail();
		List<Role> roleList = new ArrayList<>();
		Role role = new Role();
		role.setRoleId(123);
		role.setRoleName("Role");
		roleDetail.setRole(roleList);
		Mockito.when(globalSettingsService.getRoleList()).thenReturn(roleDetail);
		assertEquals(200, settingsController.getRoleList().getStatusCodeValue());
	}
}
