package org.epo.cms.edfs.services.settings.dao;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTag;
import org.epo.cms.edfs.services.settings.dto.PersonalTagDto;

/**
 * Return personalTag data list.
 * 
 * @author dinagar
 *
 */
public interface PersonalTagDao extends GenericDao<PersonalTag, Integer> {

  /**
   * Get Personal Tag details as per tag.
   * 
   * @param tag : String
   * @return PersonalTagDto : PersonalTagDto
   * @throws CustomException : CustomException
   */
  PersonalTagDto getPersonalTag(String tag) throws CustomException;
}
