package org.epo.cms.edfs.services.settings.dao;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.settings.beans.RoleSettingResponse;

/**
 * Setting DAO details.
 * 
 * @author dinagar
 *
 */
public interface SettingsDao {

  /**
   * to get settings details for Data base as per Role.
   * 
   * @param role : String
   * @return RoleSettingResponse : RoleSettingResponse
   * @throws CustomException : customException
   */
  RoleSettingResponse getRoleSettings(String role) throws CustomException;
}
