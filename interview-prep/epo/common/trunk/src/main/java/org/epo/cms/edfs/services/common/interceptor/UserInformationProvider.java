package org.epo.cms.edfs.services.common.interceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * The UserInformationProvider is responsible for providing information about the user based on the
 * HttpRequest. Different implementations can be used in different run time environments
 * 
 * @author PV53311
 *
 */
interface UserInformationProvider {

  /**
   * Retrieves the user information from the current {@link HttpServletRequest} for the service.
   * 
   * @param request - Current request
   * @return {@link RequestUserInfo} containing information about the user making the service
   *         request.
   */
  RequestUserInfo getUserInfo(final HttpServletRequest request);
}
