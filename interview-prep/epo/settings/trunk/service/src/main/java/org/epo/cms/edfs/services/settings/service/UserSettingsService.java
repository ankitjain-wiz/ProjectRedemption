package org.epo.cms.edfs.services.settings.service;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.settings.beans.RoleSettingResponse;
import org.epo.cms.edfs.services.settings.beans.SettingsRequest;
import org.epo.cms.edfs.services.settings.beans.UpdaterSettingsResponse;

/**
 * 
 * Service layer for User Settings  
 * @author dinagar
 *
 */
public interface UserSettingsService {

 
  /**
 * @param role : String
 * @param userId : String
 * @return RoleSettingResponse : RoleSettingResponse
 * @throws CustomException : : customException
 */
RoleSettingResponse getUserSettingsDetails(String role, String userId) throws CustomException;

  /**
   * update user settings details
   * 
   * @param settingsRequest : SettingsRequest
   * @param role : String
   * @param userId : String
   * @return UpdaterSettingsResponse: UpdaterSettingsResponse
   * @throws CustomException : : customException
   */
  UpdaterSettingsResponse updateUserSettingsDetails(SettingsRequest settingsRequest, String role,
      String userId) throws CustomException;


  
  /**
   * Get Global Details based on Section Name 
 * @param sectionName : String
 * @param role : String
 * @param userId : String
 * @return RoleSettingResponse : RoleSettingResponse
 * @throws CustomException : : customException
 */
  RoleSettingResponse resetUserSettingsToGlobal( String sectionName,String role, String userId)
			throws CustomException ;
  
 
  
}
