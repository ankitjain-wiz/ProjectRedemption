package org.epo.cms.edfs.services.settings.service;
/**
 * 
 * @author dinagar
 *
 */

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.settings.beans.RoleDetail;
import org.epo.cms.edfs.services.settings.beans.RoleSettingResponse;
import org.epo.cms.edfs.services.settings.beans.SettingsRequest;
import org.epo.cms.edfs.services.settings.beans.UpdaterSettingsResponse;

/**
 * Service layer for Global Settings  
 * @author dinagar
 *
 */
public interface GlobalSettingsService {

  /**
   * this method used to get settings based on role and get global settings details as par Role;
   * populate data in response objects
   * 
   * @param role : String
   * @return RoleSettingsResponse : RoleSettingResponse
   * @throws CustomException : customException
   */
  RoleSettingResponse getGlobalSettings(String role) throws CustomException;

  /**
   * update global settings details
   * 
   * @param settingsRequest : SettingsRequest
   * @param role : String
   * @param userId : String
   * @return UpdaterSettingsResponse : UpdaterSettingsResponse
   * @throws CustomException : : customException
   */
  UpdaterSettingsResponse updateSettingsDetails(SettingsRequest settingsRequest, String role, String userId)
      throws CustomException;

  /**
   * Service of role List.
   * 
   * @return RoleDetail : RoleDetail
   * @throws CustomException : customException
   */
  RoleDetail getRoleList() throws CustomException;
}
