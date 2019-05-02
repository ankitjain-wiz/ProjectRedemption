package org.epo.cms.edfs.services.settings.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.permission.ServicePermission;
import org.epo.cms.edfs.services.common.useridrole.CommonCode;
import org.epo.cms.edfs.services.settings.beans.RoleDetail;
import org.epo.cms.edfs.services.settings.beans.RoleSettingResponse;
import org.epo.cms.edfs.services.settings.beans.SettingsRequest;
import org.epo.cms.edfs.services.settings.beans.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.settings.service.GlobalSettingsService;
import org.epo.cms.edfs.services.settings.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller to handle request and response object as per settings response.
 * 
 * @author dinagar
 *
 */
@CrossOrigin
@RestController
public class SettingsController {

	private static final Logger LOGGER = LogManager.getLogger(SettingsController.class);


	@Autowired
	private GlobalSettingsService globalSettingsService;

	@Autowired
	private UserSettingsService userSettingsService;

	@Autowired
	private CommonCode commonCode;
	


	
	

	/**
	 * Method for getting settings details response and request objects.
	 * 
	 * @param role : String
	 * @param request : HttpServletRequest
	 * @return RoleSettingsResponse : RoleSettingResponse response
	 * @throws CustomException : customException
	 */

	@RequestMapping(value = {
			"/users/preferences/settings/{role}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ServicePermission("view_admin_settings")
	public RoleSettingResponse getRoleSettingsDetails(@PathVariable("role") String role, HttpServletRequest request)
			throws CustomException {
		LOGGER.debug("Admin Settings controller entry role : {} and HTTPRequest : {} ", role, request);


		String userId = commonCode.getUserId(request);
		RoleSettingResponse roleSettingsResp=null;

	
		if (!StringUtils.isEmpty(userId)) {

		roleSettingsResp = globalSettingsService.getGlobalSettings(role);
		}

		return roleSettingsResp;

	}

	/**
	 * Settings response based on user role.
	 * 
	 * @param request : HttpServletRequest
	 * @return : RoleSettingResponse : RoleSettingResponse
	 * @throws CustomException : CustomException
	 */

	@RequestMapping(value = {
			"/users/preferences/settings" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ServicePermission("view_user_settings")
	public RoleSettingResponse getUserSettingsDetails(HttpServletRequest httpServletRequest) throws CustomException {
		
		LOGGER.debug("User Settings controller entry role : {} and HTTPRequest : {} ", httpServletRequest);
		RoleSettingResponse roleSettingsResp = null;

		
		String userId = commonCode.getUserId(httpServletRequest);
		

		if (!StringUtils.isEmpty(userId)) 
		{
			
			String userRole = commonCode.getUserRole(userId);
			
			if (!StringUtils.isEmpty(userRole)) 
			{
				
				roleSettingsResp = userSettingsService.getUserSettingsDetails(userRole, userId);
			} else {
				roleSettingsResp = userSettingsService.getUserSettingsDetails(null, userId);
			}
		}

		return roleSettingsResp;

	}

	

	/**
	 * Post service call for Admin settings update
	 * @param role : String
	 * @param settingsRequest : SettingsRequest
	 * @param request : httpServletRequest
	 * @return ResponseEntity<UpdaterSettingsResponse> : ResponseEntity<UpdaterSettingsResponse>
	 * @throws CustomException : CustomException
	 */
	@RequestMapping(value = "/users/preferences/settings/{role}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ServicePermission("update_admin_settings")
	public ResponseEntity<UpdaterSettingsResponse> updateRoleSettings(@PathVariable("role") String role,
			@RequestBody SettingsRequest settingsRequest, HttpServletRequest httpServletRequest) throws CustomException {
		LOGGER.info("Post service call for settings update");

		String userId = commonCode.getUserId(httpServletRequest);

		UpdaterSettingsResponse updaterSettingsResponse = globalSettingsService.updateSettingsDetails(settingsRequest,role, userId);
		return new ResponseEntity<>(updaterSettingsResponse, HttpStatus.CREATED);
	}

	/**
	 * Method to save User settings
	 * @param settingsRequest : SettingsRequest
	 * @param request : HttpServletRequest
	 * @return ResponseEntity<UpdaterSettingsResponse> : ResponseEntity<UpdaterSettingsResponse>
	 * @throws CustomException : CustomException
	 */
	@RequestMapping(value = "/users/preferences/settings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ServicePermission("update_user_settings")
	public ResponseEntity<UpdaterSettingsResponse> updateUserSettings(@RequestBody SettingsRequest settingsRequest,
			HttpServletRequest request) throws CustomException {
		LOGGER.info("Post service call for User settings update");

		
		String userId = commonCode.getUserId(request);

		UpdaterSettingsResponse updaterSettingsResponse = null;
		if (!StringUtils.isEmpty(userId)) {
			
			String userRole = commonCode.getUserRole(userId);
			updaterSettingsResponse = userSettingsService.updateUserSettingsDetails(settingsRequest, userRole, userId);
		}
		return new ResponseEntity<>(updaterSettingsResponse, HttpStatus.CREATED);
	}
	

	/**
	 * Method to get Role List
	 * @return ResponseEntity<RoleDetail> : ResponseEntity<RoleDetail>
	 * @throws CustomException : CustomException
	 */
	@RequestMapping(value = { "/user/role" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ServicePermission("get_roles")
	public ResponseEntity<RoleDetail> getRoleList() throws CustomException {
		LOGGER.debug("get role List for backend");
		RoleDetail roleDetail = globalSettingsService.getRoleList();
		return new ResponseEntity<>(roleDetail, org.springframework.http.HttpStatus.OK);
	}


	/**
	 * Method to get Admin details based on sectionName 
	 * @param sectionName : String
	 * @param request : HttpServletRequest
	 * @return RoleSettingResponse : RoleSettingResponse
	 * @throws CustomException : CustomException
	 */
	@RequestMapping(value = "/users/preferences/settings/reset/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ServicePermission("reset_user_settings")
	public RoleSettingResponse resetUserSettings(
			@RequestParam(value = "sectionName", required = false) String sectionName, HttpServletRequest request)
			throws CustomException {
		LOGGER.info("Reset to default for settings , will get global Details based on Section Name");

		
		String userId = commonCode.getUserId(request);
		RoleSettingResponse roleSettingsResp;
		String userRole = null;
		
		if (!StringUtils.isEmpty(userId)) 
		{
			
			 userRole = commonCode.getUserRole(userId);
			
		}
		roleSettingsResp = userSettingsService.resetUserSettingsToGlobal(sectionName, userRole, userId);
		return roleSettingsResp;
	}



	
	
}
