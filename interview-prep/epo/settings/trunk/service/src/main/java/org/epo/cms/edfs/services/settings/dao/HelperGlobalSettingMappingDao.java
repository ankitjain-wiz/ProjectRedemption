package org.epo.cms.edfs.services.settings.dao;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.HelperGlobalSettingMapping;
import org.epo.cms.edfs.services.settings.dto.HelperGlobalSettingMappingDto;

/**
 * calling Entity Objects of HelperGlobalSettingMapping and filter data in DTO bjects.
 * 
 * @author dinagar
 *
 */
public interface HelperGlobalSettingMappingDao
    extends GenericDao<HelperGlobalSettingMapping, Integer> {
  /**
   * Updating HelperGlobalSettingsMapping Details in backend
   * 
   * @param helperGlobalSettingMappingDto : HelperGlobalSettingMappingDto
   * @return HelperGlobalSettingMapping : HelperGlobalSettingMapping
   * @throws CustomException : CustomException
   */
  HelperGlobalSettingMapping updateHelperGlobalSettingMapping(
      HelperGlobalSettingMappingDto helperGlobalSettingMappingDto) throws CustomException;
  
  
  
 /**
  * method to remove HelperGlobalSettingMapping
 * @param l : long
 */
void removeHelperGlobalMapping(long l);
}
