package org.epo.cms.edfs.services.common.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.beans.AuthorisedPermission;
import org.epo.cms.edfs.services.common.beans.PermissionResponse;
import org.epo.cms.edfs.services.common.beans.UserRolePermission;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ExceptionHandlerBean;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.externalapi.ExternalApiService;
import org.epo.cms.edfs.services.common.permission.ServicePermission;
import org.epo.cms.edfs.services.common.useridrole.UserAdminInfo;
import org.epo.cms.edfs.services.common.useridrole.UserAdminRole;
import org.epo.cms.edfs.services.common.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthorisationInterceptorForDevOnly extends HandlerInterceptorAdapter {

	@Autowired
	private ExternalApiService externalApiService;

	@Autowired
	private ResponseValidator responseValidator;

	@Autowired
	private CacheAccess<Object, Object> permissionCache;

	@Autowired
	private UserAdminRole userAdminRole;

	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		boolean hasPermission;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("request : " + request);
			LOGGER.debug("handler : " + handler);
			LOGGER.debug("Requested URI Method : " + request.getMethod());
		}

		/*
		 * This OPTIONS check is required to removed the additional call to HTTP
		 * OPTIONS at the time of POST service call. Because Angular during post
		 * call Angular send first request as OPTIONS method and then second
		 * request as POST
		 */
		if (RequestMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {
			return true;
		}

		String localusertest = request.getParameter("localtestuser");
		userAdminRole.setUser(localusertest);
		if (StringUtils.isEmpty(localusertest)) {
			userAdminRole.setUserId(UserAdminInfo.user1.getUserId());
			userAdminRole.setRole(UserAdminInfo.user1.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user1.getOrganizationId());
		} else {
			assignUserId_Role(localusertest);
		}

		String userId = userAdminRole.getUserId();
		try {
			hasPermission = hasPermissionToExecute(userId, handler);
		} catch (CustomException ce) {
			LOGGER.error("Custom Exception" + ce.getMessage());
			throw ce;
		}
		return hasPermission;
	}

	private void assignUserId_Role(String user) throws CustomException {

		switch (user) {

		case "1": {
			userAdminRole.setUserId(UserAdminInfo.user1.getUserId());
			userAdminRole.setRole(UserAdminInfo.user1.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user1.getOrganizationId());
			break;
		}
		case "2": {
			userAdminRole.setUserId(UserAdminInfo.user2.getUserId());
			userAdminRole.setRole(UserAdminInfo.user2.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user2.getOrganizationId());
			break;
		}
		case "3": {
			userAdminRole.setUserId(UserAdminInfo.user3.getUserId());
			userAdminRole.setRole(UserAdminInfo.user3.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user3.getOrganizationId());
			break;
		}
		case "4": {
			userAdminRole.setUserId(UserAdminInfo.user4.getUserId());
			userAdminRole.setRole(UserAdminInfo.user4.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user4.getOrganizationId());
			break;
		}
		case "5": {
			userAdminRole.setUserId(UserAdminInfo.user5.getUserId());
			userAdminRole.setRole(UserAdminInfo.user5.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user5.getOrganizationId());
			break;
		}
		case "6": {
			userAdminRole.setUserId(UserAdminInfo.user6.getUserId());
			userAdminRole.setRole(UserAdminInfo.user6.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user6.getOrganizationId());
			break;
		}
		case "7": {
			userAdminRole.setUserId(UserAdminInfo.user7.getUserId());
			userAdminRole.setRole(UserAdminInfo.user7.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user7.getOrganizationId());
			break;
		}
		case "8": {
			userAdminRole.setUserId(UserAdminInfo.user8.getUserId());
			userAdminRole.setRole(UserAdminInfo.user8.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user8.getOrganizationId());
			break;
		}
		case "9": {
			userAdminRole.setUserId(UserAdminInfo.user9.getUserId());
			userAdminRole.setRole(UserAdminInfo.user9.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user9.getOrganizationId());
			break;
		}
		case "10": {
			userAdminRole.setUserId(UserAdminInfo.user10.getUserId());
			userAdminRole.setRole(UserAdminInfo.user10.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user10.getOrganizationId());
			break;
		}
		case "11": {
			userAdminRole.setUserId(UserAdminInfo.user11.getUserId());
			userAdminRole.setRole(UserAdminInfo.user11.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user11.getOrganizationId());
			break;
		}
		case "12": {
			userAdminRole.setUserId(UserAdminInfo.user12.getUserId());
			userAdminRole.setRole(UserAdminInfo.user12.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user12.getOrganizationId());
			break;
		}
		case "13": {
			userAdminRole.setUserId(UserAdminInfo.user13.getUserId());
			userAdminRole.setRole(UserAdminInfo.user13.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user13.getOrganizationId());
			break;
		}
		case "14": {
			userAdminRole.setUserId(UserAdminInfo.user14.getUserId());
			userAdminRole.setRole(UserAdminInfo.user14.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user14.getOrganizationId());
			break;
		}
		case "15": {
			userAdminRole.setUserId(UserAdminInfo.user15.getUserId());
			userAdminRole.setRole(UserAdminInfo.user15.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user15.getOrganizationId());
			break;
		}
		case "16": {
			userAdminRole.setUserId(UserAdminInfo.user16.getUserId());
			userAdminRole.setRole(UserAdminInfo.user16.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user16.getOrganizationId());
			break;
		}
		case "17": {
			userAdminRole.setUserId(UserAdminInfo.user17.getUserId());
			userAdminRole.setRole(UserAdminInfo.user17.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user17.getOrganizationId());
			break;
		}
		case "18": {
			userAdminRole.setUserId(UserAdminInfo.user18.getUserId());
			userAdminRole.setRole(UserAdminInfo.user18.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user18.getOrganizationId());
			break;
		}
		case "19": {
			userAdminRole.setUserId(UserAdminInfo.user19.getUserId());
			userAdminRole.setRole(UserAdminInfo.user19.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user19.getOrganizationId());
			break;
		}
		case "20": {
			userAdminRole.setUserId(UserAdminInfo.user20.getUserId());
			userAdminRole.setRole(UserAdminInfo.user20.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user20.getOrganizationId());
			break;
		}
		case "21": {
			userAdminRole.setUserId(UserAdminInfo.user21.getUserId());
			userAdminRole.setRole(UserAdminInfo.user21.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user21.getOrganizationId());
			break;
		}
		case "22": {
			userAdminRole.setUserId(UserAdminInfo.user1.getUserId());
			userAdminRole.setRole(UserAdminInfo.user1.getRole());
			userAdminRole.setOrganizationId(UserAdminInfo.user1.getOrganizationId());
			break;
		}
		default: {
			
			ExceptionHandlerBean exBean;
			exBean = responseValidator.getErrorResponse("0", "Invalid Local Test User");
			throw new CustomException(exBean);
		}
		}

	}

	private boolean hasPermissionToExecute(final String userId, final Object handler)
			throws IOException, CustomException {
		boolean hasPermission = false;
		String permissionName = "";
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Entered check on hasPermissionToExecute.");
		}
		if (!(handler instanceof HandlerMethod)) {
			// This indicates that we are not executing a controller method
			// This could be a call to a static resource, which is not captured
			// by the exclusion pattern
			// in the MappedInterceptor.
			// For now default returning true.
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The requested URI is not a controller method. Granting access to it.");
			}
			return true;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("User Id : " + userId);
		}
		if (userId != null) {
			if (permissionCache.get(userId) == null) {
				loadPermissionList(userId);
			}
			ServicePermission permission = ((HandlerMethod) handler).getMethodAnnotation(ServicePermission.class);

			if (permission == null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("No service permission specified on the controller method.", handler.toString());
				}
			} else {
				permissionName = permission.value();
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("PermissionName :" + permissionName);
				}
				hasPermission = checkPermission(userId, permissionName);
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("User Authorized :" + hasPermission);
			}
			if (!hasPermission) {
				ExceptionHandlerBean exBean;
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("User (%s) is not authorized for permission (%s).", userId, permissionName);
				}
				exBean = responseValidator.getErrorResponse(Integer.parseInt(Constants.NOT_ALLOWED));
				throw new CustomException(exBean);
			}
		} else {
			hasPermission = false;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Exiting check on hasPermissionToExecute for user " + userId + " with permission "
					+ hasPermission + ".");
		}
		return hasPermission;
	}

	/**
	 * Method Used to call the ExteranalService API utility method to hit the
	 * CMS service for Permission Details. And based on the CMS response it
	 * store permission response into the Case.
	 * 
	 * @param userId
	 *            to get userId
	 * @throws CustomException
	 *             for CustomException
	 * @throws IOException
	 *             for IOException
	 */
	private void loadPermissionList(String userId) throws CustomException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		AuthorisedPermission authorisedPermission;
		final String cmsPermissionServiceName = "GetPermissions";

		String uriPartString = cmsPermissionServiceName + "/" + userId;
		String userAuthRequestCMSStr = externalApiService.getCmsResponseUsingExternalApi(uriPartString,
				Constants.HOST_FOR_CMSSERVICE);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(" External User Permissions :" + userAuthRequestCMSStr);
		}
		authorisedPermission = objectMapper.readValue(userAuthRequestCMSStr, AuthorisedPermission.class);
		List<UserRolePermission> permissions = new ArrayList<>();

		List<PermissionResponse> aM43Permissions = null;
		if (authorisedPermission != null) {
			aM43Permissions = authorisedPermission.getaM43Permissions();
		}

		if (CollectionUtils.isNotEmpty(aM43Permissions)) {
			for (PermissionResponse permissionResponse : aM43Permissions) {
				if (StringUtils.hasText(permissionResponse.getPermission())) {
					permissions.add(new UserRolePermission(permissionResponse.getRoleName(),
							permissionResponse.getPermission()));
				}
			}
			if (CollectionUtils.isNotEmpty(permissions)) {
				permissionCache.put(userId, permissions);
			}
		}
	}

	/**
	 * Method Used to check the whether is authorised to access the service of
	 * given Controller
	 * 
	 * @param userId
	 *            to get userId
	 * @param permissionToCheck
	 *            to get permission to check
	 * @return permission to check as true or false
	 */
	private boolean checkPermission(String userId, String permissionToCheck) {
		boolean hasPermission = false;
		List<UserRolePermission> permissions = (List<UserRolePermission>) permissionCache.get(userId);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Permission Mapping:" + permissionToCheck);
			LOGGER.debug("Permission List:" + permissions);
		}
		for (UserRolePermission userRolePermission : permissions) {
			if (userRolePermission.getPermission() != null
					&& (userRolePermission.getPermission().equalsIgnoreCase(permissionToCheck))) {
				hasPermission = true;
				break;
			}
		}

		return hasPermission;
	}
}
