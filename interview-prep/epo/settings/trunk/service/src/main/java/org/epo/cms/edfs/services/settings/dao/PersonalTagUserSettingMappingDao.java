package org.epo.cms.edfs.services.settings.dao;

import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTagUserSettingMapping;
import org.epo.cms.edfs.services.settings.dto.PersonalTagUserSettingMappingDto;

/**
 * PersonalTagUserSettingMappingDao to get,update and reset PersonalTagUserSettingMapping
 * @author dinagar
 *
 */
public interface PersonalTagUserSettingMappingDao
    extends GenericDao<PersonalTagUserSettingMapping, Integer> {
  /**
   * Updating PersonalTagUserSettingMapping
   * 
   * @param dto : PersonalTagUserSettingMappingDto
   * @return PersonalTagUserSettingMapping : PersonalTagUserSettingMapping
   * @throws CustomException : CustomException
   */
  PersonalTagUserSettingMapping updatePersonalTagUserSettingMapping(
      PersonalTagUserSettingMappingDto dto) throws CustomException;

  /**
   * Reset personal tag user settings mappings
   * 
   * @param userId : String
   * @return String : String
   * @throws CustomException : CustomException
   */
  String restPersonalTagUserSettingMapping(String userId) throws CustomException;
  
  
  /**
   * Method to get PersonalTagUserSettingMapping list
 * @param userId : String
 * @return List<PersonalTagUserSettingMappingDto> : List<PersonalTagUserSettingMappingDto>
 */
List<PersonalTagUserSettingMappingDto> getPersonalTagUserSettingMapping(String userId);
  
  
  /**
   * Method to check wether Personal Tag  is empty or not 
 * @param userId : String
 * @return Boolean : Boolean
 */
Boolean checkEmptyPersonalTagUserSettingMapping(String userId);
}
