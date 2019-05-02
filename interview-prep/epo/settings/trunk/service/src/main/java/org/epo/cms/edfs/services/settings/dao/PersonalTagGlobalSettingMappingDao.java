package org.epo.cms.edfs.services.settings.dao;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTagGlobalSettingMapping;
import org.epo.cms.edfs.services.settings.dto.PersonalTagGlobalSettingMappingDto;

/**
 * PersonalTagGlobalSettingMapping DAO class returning PersonalTagGlobalSettingMappingDTO objects
 * 
 * @author dinagar
 *
 */
public interface PersonalTagGlobalSettingMappingDao
    extends GenericDao<PersonalTagGlobalSettingMapping, Integer> {
  /**
   * update PersonalTagGlobalSettingMapping table
   * 
   * @param personalTagGlobalSettingMappingDto : PersonalTagGlobalSettingMappingDto
   * @return PersonalTagGlobalSettingMapping : PersonalTagGlobalSettingMapping Object
   * @throws CustomException : CustomException
   */
  PersonalTagGlobalSettingMapping updatePersonalTagGlobalSettingMapping(
      PersonalTagGlobalSettingMappingDto personalTagGlobalSettingMappingDto) throws CustomException;

}
