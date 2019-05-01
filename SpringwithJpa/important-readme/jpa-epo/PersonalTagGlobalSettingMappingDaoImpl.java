package org.epo.cms.edfs.services.settings.dao.impl;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalLevel;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTag;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTagGlobalSettingMapping;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.settings.dao.PersonalTagGlobalSettingMappingDao;
import org.epo.cms.edfs.services.settings.dto.PersonalLevelDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagGlobalSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.RoleDto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

/**
 * 
 * @author dinagar
 *
 */
@Repository
public class PersonalTagGlobalSettingMappingDaoImpl
    extends GenericDaoImpl<PersonalTagGlobalSettingMapping, Integer>
    implements PersonalTagGlobalSettingMappingDao {

  /**
   * {@inheritDoc}
   */
  @Override
  public PersonalTagGlobalSettingMapping updatePersonalTagGlobalSettingMapping(
      PersonalTagGlobalSettingMappingDto personalTagGlobalSettingMappingDto)
      throws CustomException {
    PersonalTagGlobalSettingMapping personalTagGlobalSettingMapping =
        getPersonalTagGlobalSettingMapping(personalTagGlobalSettingMappingDto.getPersonalLevel(),
            personalTagGlobalSettingMappingDto.getPersonalTag(),
            personalTagGlobalSettingMappingDto.getRole(),personalTagGlobalSettingMappingDto.getTitleName());
    if (!ObjectUtils.isEmpty(personalTagGlobalSettingMapping)) {
      remove(personalTagGlobalSettingMapping);
    } else {
      PersonalLevel personalLevel = new PersonalLevel();
      PersonalTag personalTag = new PersonalTag();
      Role role = new Role();
      personalTagGlobalSettingMapping = new PersonalTagGlobalSettingMapping();
      role.setRoleId(personalTagGlobalSettingMappingDto.getRole().getRoleId());
      personalLevel.setPersonalLevelId(
          personalTagGlobalSettingMappingDto.getPersonalLevel().getPersonalLevelId());
      personalTag
          .setPersonalTagId(personalTagGlobalSettingMappingDto.getPersonalTag().getPersonalTagId());
      personalTagGlobalSettingMapping.setPersonalTag(personalTag);
      personalTagGlobalSettingMapping.setPersonalLevel(personalLevel);
      personalTagGlobalSettingMapping
          .setTitleName(personalTagGlobalSettingMappingDto.getTitleName());
      personalTagGlobalSettingMapping.setRole(role);
      saveOrUpdate(personalTagGlobalSettingMapping);
    }
    return personalTagGlobalSettingMapping;
  }

  
  
  /**
 * @param personalLevelDto : PersonalLevelDto
 * @param personalTagDto : PersonalTagDto
 * @param roleDto : RoleDto
 * @return PersonalTagGlobalSettingMapping : PersonalTagGlobalSettingMapping
 */
private PersonalTagGlobalSettingMapping getPersonalTagGlobalSettingMapping(
      PersonalLevelDto personalLevelDto, PersonalTagDto personalTagDto, RoleDto roleDto,String titlename) {
    PersonalLevel personalLevel = new PersonalLevel();
    personalLevel.setPersonalLevelId(personalLevelDto.getPersonalLevelId());
    PersonalTag personalTag = new PersonalTag();
    personalTag.setPersonalTagId(personalTagDto.getPersonalTagId());
    Role role = new Role();
    role.setRoleId(roleDto.getRoleId());
    Criteria criteria = currentSession().createCriteria(PersonalTagGlobalSettingMapping.class);
    criteria.add(Restrictions.eq("personalLevel", personalLevel))
        .add(Restrictions.eq("personalTag", personalTag)).add(Restrictions.eq("role", role)).add(Restrictions.eq("titleName", titlename));
    return  (PersonalTagGlobalSettingMapping) criteria.uniqueResult();
  }


}
