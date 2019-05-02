package org.epo.cms.edfs.services.common.useridrole;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.jcs.access.CacheAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.beans.UserAccountDetail;
import org.epo.cms.edfs.services.common.cache.DossierCache;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * This class is used to get userId , organisationId , Role
 * 
 * @author ankitjain2
 *
 */
@Component
public class CommonCode {

	@Autowired
	private UserAdminRole userAdminRole;

	@Autowired
	private ResponseValidator responseValidator;

	@Autowired
	private CacheAccess<Object, Object> permissionCache;

	@Autowired
	private DossierCache dossierCache;

	private static final Logger LOGGER = LogManager.getLogger(CommonCode.class);

	/**
	 * User id if fetched from request Principal otherwise Custom Exception is thrown in epo environment
	 * @param request: HttpServletRequest
	 * @return userId : String
	 */
	/*
	 * public String getUserId(HttpServletRequest request) {
	 * 
	 * String userId = null;
	 * 
	 * ExceptionHandlerBean exBean; if
	 * (!ObjectUtils.isEmpty(request.getUserPrincipal()) &&
	 * !StringUtils.isEmpty(request.getUserPrincipal().getName()) &&
	 * !"null".equalsIgnoreCase(request.getUserPrincipal().getName())) { userId
	 * = request.getUserPrincipal().getName(); } else { exBean =
	 * responseValidator.getErrorResponseWithExpMsg(ErrorCodeEnum.
	 * UNPROCESSABLE_ENTITY, "invaid" + " " + "userId");
	 * LOGGER.error("User not found in request: "); throw new
	 * IllegalArgumentException(exBean.getDetails()); }
	 * 
	 * return userId; }
	 */

	/**
	 * Method to fetch role from UserId in epo environment
	 * @param userId:   String
	 * @return String : String
	 */
	/*
	 * public String getUserRole(String userId) {
	 * 
	 * @SuppressWarnings("unchecked") List<UserRolePermission> permissions =
	 * (List<UserRolePermission>) permissionCache.get(userId); String userRole =
	 * null; if (!StringUtils.isEmpty(permissions)) { for (UserRolePermission
	 * userRolePermission : permissions) { userRole =
	 * userRolePermission.getRole(); if (!StringUtils.isEmpty(userRole)) break;
	 * } } return userRole; }
	 */

	/**
	 * Method to fetch organisationId from cache in epo environment
	 * @param userId:  String
	 * @return String : String
	 */
	/*
	 * public String getOrganisationalId(String userId) { String
	 * organisationalId = null; ExceptionHandlerBean exBean; UserAccountDetail
	 * userDetail = dossierCache.getUserAccountDetails(userId); if
	 * (!ObjectUtils.isEmpty(userDetail)) { organisationalId =
	 * userDetail.getOrganisationID(); } else { exBean =
	 * responseValidator.getErrorResponseWithExpMsg(ErrorCodeEnum.
	 * UNPROCESSABLE_ENTITY,"Caching data error");
	 * LOGGER.error("No organisation Id: "); throw new
	 * IllegalArgumentException(exBean.getDetails()); }
	 * 
	 * return organisationalId; }
	 */

	/**
	 * Method to get role from cache (New Method)
	 * @param userId
	 * @return
	 */
	/*	public String getUserRole(String userId) {
	
			String userRole = null;
			ExceptionHandlerBean exBean;
	
			UserAccountDetail userDetail = dossierCache.getUserAccountDetails(userId);
			if (!ObjectUtils.isEmpty(userDetail)) {
				userRole = userDetail.getRole();
			} else {
				exBean = responseValidator.getErrorResponseWithExpMsg(ErrorCodeEnum.UNPROCESSABLE_ENTITY,"Caching data error");
				LOGGER.error("Caching data error ");
				throw new IllegalArgumentException(exBean.getDetails());
			}
	
			return userRole;
		}*/

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// LocalCodes Not to be sent to epo

	public String getUserId(HttpServletRequest request) {

		String userId;
		userId = userAdminRole.getUserId();
		return userId;
	}

	public String getUserRole(String userId) {

		String userRole = userAdminRole.getRole();;

		/*UserAccountDetail userDetail = dossierCache.getUserAccountDetails(userId);
		if (!ObjectUtils.isEmpty(userDetail)) {
			userRole = userDetail.getRole();
		} else {
			userRole = userAdminRole.getRole();
		}*/

		return userRole;
	}

	public String getOrganisationalId(String userId) {
		String organisationalId = null;
		UserAccountDetail userDetail = dossierCache.getUserAccountDetails(userId);
		if (!ObjectUtils.isEmpty(userDetail)) {
			return userDetail.getOrganisationID();
		} else {
			organisationalId = userAdminRole.getOrganizationId();
		}
		return organisationalId;
	}

}
