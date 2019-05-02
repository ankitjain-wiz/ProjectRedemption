package org.epo.cms.edfs.services.settings.dao;

import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.HelperUserSettingMapping;
import org.epo.cms.edfs.services.settings.dto.HelperUserSettingMappingDto;

/**
 * HelperUserSettingsMappingDao to get,update and reset HelperUserSettingMapping
 * @author dinagar
 *
 */
public interface HelperUserSettingMappingDao extends GenericDao<HelperUserSettingMapping, Integer> {

  /**
   * update Helper details in HelperUserSettingsMapping Table
   * 
   * @param dto : HelperUserSettingMappingDto
   * @return HelperUserSettingMapping : HelperUserSettingMapping
   * @throws CustomException : CustomException
   */
  HelperUserSettingMapping updateHelperUserSettingMapping(HelperUserSettingMappingDto dto)
      throws CustomException;
  /**
   * 
   * @param userId : String
   * @return String : String
   * @throws CustomException : CustomException
   */
  String resetHelperUserSettingMapping(String userId) throws CustomException;
  
  
  /**
 * @param userId : String
 * @return List<HelperUserSettingMappingDto> : List<HelperUserSettingMappingDto>
 */
List<HelperUserSettingMappingDto> userHelperList(String userId);
}
