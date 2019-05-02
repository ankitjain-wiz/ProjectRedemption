package org.epo.cms.edfs.services.settings.dao.impl;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ErrorCodeEnum;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTag;
import org.epo.cms.edfs.services.settings.dao.PersonalTagDao;
import org.epo.cms.edfs.services.settings.dto.PersonalTagDto;
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
public class PersonalTagDaoImpl extends GenericDaoImpl<PersonalTag, Integer>
    implements PersonalTagDao {

  @Autowired
  private ResponseValidator responseValidator;


  /**
   * {@inheritDoc}
   */
  @Override
  public PersonalTagDto getPersonalTag(String tag) throws CustomException {
    Criteria criteria = currentSession().createCriteria(PersonalTag.class);
    criteria.add(Restrictions.eq("tagName", tag));
    PersonalTag personaltag = (PersonalTag) criteria.uniqueResult();
    if (!ObjectUtils.isEmpty( personaltag)) {
      PersonalTagDto personalTagDto = new PersonalTagDto();
      personalTagDto.setPersonalTagId(personaltag.getPersonalTagId());
      return personalTagDto;
    } else
      throw new CustomException(responseValidator.getErrorResponse(ErrorCodeEnum.BAD_REQUEST_CODE));

  }
}
