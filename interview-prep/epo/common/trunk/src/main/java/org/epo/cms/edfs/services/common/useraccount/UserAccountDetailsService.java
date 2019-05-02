package org.epo.cms.edfs.services.common.useraccount;

import java.util.Map;

import org.epo.cms.edfs.services.common.beans.UserAccountDetail;
import org.epo.cms.edfs.services.common.exceptions.CustomException;

/**
 * The UserAccountDetailsService is responsible for retrieving the user account details information
 * from the respective CMS service.
 * 
 * @author PV53311
 *
 */
public interface UserAccountDetailsService {

  /**
   * Retrieves the UserAccountDetails information.
   * 
   * @return Map containing the information. The key in the map is the UserID as provided by the
   *         supplier. The value in the map is the {@link UserAccountDetail}.
   * 
   * @throws CustomException - Thrown when something goes wrong.
   */
  Map<String, UserAccountDetail> getUserAccountDetails() throws CustomException;

  /**
   * Retrieves the UserAccountDetails from the respective service.
   * 
   * @param userId - The userId short form (so userid and NOT userId@INTERNAL.EPO.ORG) Case
   *        insensitive.
   * @return - The UserAccountDetail for the specified userId.
   * @throws CustomException - Thrown when something goes wrong.
   */
  UserAccountDetail getUserAccountDetail(final String userId) throws CustomException;

}
