package org.epo.cms.edfs.services.settings.dao;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalLevel;
import org.epo.cms.edfs.services.settings.dto.PersonalLevelDto;

/**
 * personal level user details
 * 
 * @author dinagar
 *
 */
public interface PersonalLevelDao extends GenericDao<PersonalLevel, Integer> {
  /**
   * get Personal level details.
   * 
   * @param personalLevelName : String
   * @return PersonalLevelDto : PersonalLevelDto
   * @throws CustomException : CustomException
   */
  PersonalLevelDto getPersonalLevel(String personalLevelName) throws CustomException;
}
