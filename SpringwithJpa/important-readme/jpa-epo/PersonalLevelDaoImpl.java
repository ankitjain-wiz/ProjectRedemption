package org.epo.cms.edfs.services.settings.dao.impl;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ErrorCodeEnum;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalLevel;
import org.epo.cms.edfs.services.settings.dao.PersonalLevelDao;
import org.epo.cms.edfs.services.settings.dto.PersonalLevelDto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

/**
 * 
 * @author dinagar
 *
 */
@Repository
public class PersonalLevelDaoImpl extends GenericDaoImpl<PersonalLevel, Integer>
    implements PersonalLevelDao {

  @Autowired
  private ResponseValidator responseValidator;

  /**
   * {@inheritDoc}
   */
  @Override
  public PersonalLevelDto getPersonalLevel(String personalLevelName) throws CustomException {
    Criteria criteria = currentSession().createCriteria(PersonalLevel.class);
    criteria.add(Restrictions.eq("levelName", personalLevelName));
    PersonalLevel personalLevel = (PersonalLevel) criteria.uniqueResult();
    if (!ObjectUtils.isEmpty(personalLevel)) {
      PersonalLevelDto personalLevelDto = new PersonalLevelDto();
      personalLevelDto.setPersonalLevelId(personalLevel.getPersonalLevelId());
      return personalLevelDto;
    } else {
      throw new CustomException(responseValidator.getErrorResponse(ErrorCodeEnum.BAD_REQUEST_CODE));
    }
  }

}
