package org.epo.cms.edfs.services.settings.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalLevel;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTag;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTagUserSettingMapping;
import org.epo.cms.edfs.services.settings.dao.PersonalTagUserSettingMappingDao;
import org.epo.cms.edfs.services.settings.dto.PersonalLevelDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagUserSettingMappingDto;
import org.epo.cms.edfs.services.settings.utils.SettingsConstants;
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
public class PersonalTagUserSettingMappingDaoImpl extends GenericDaoImpl<PersonalTagUserSettingMapping, Integer>
		implements PersonalTagUserSettingMappingDao {

	private static final String USER_ID = "userId";
	private static final String ACTIVE = "active";


	/**
	 * {@inheritDoc}
	 */
	@Override
	public PersonalTagUserSettingMapping updatePersonalTagUserSettingMapping(PersonalTagUserSettingMappingDto personalTagUserSettingMappingDto)
			throws CustomException {
		PersonalLevel personalLevel = new PersonalLevel();
		personalLevel.setPersonalLevelId(personalTagUserSettingMappingDto.getPersonalLevel().getPersonalLevelId());
		PersonalTag personalTag = new PersonalTag();
		personalTag.setPersonalTagId(personalTagUserSettingMappingDto.getPersonalTag().getPersonalTagId());
		Criteria criteria = currentSession().createCriteria(PersonalTagUserSettingMapping.class);

		criteria.add(Restrictions.eq(USER_ID, personalTagUserSettingMappingDto.getUserId())).add(Restrictions.eq(ACTIVE, true))
				.add(Restrictions.eq("personalLevel", personalLevel)).add(Restrictions.eq("personalTag", personalTag))
				.add(Restrictions.eq("personalTagGlobalSettingMappingId", personalTagUserSettingMappingDto.getPersonalTagGlobalSettingMappingId()))
				.add(Restrictions.eq("titleName", personalTagUserSettingMappingDto.getTitleName()));

		
		PersonalTagUserSettingMapping personalTagUserSettingMapping = null;
		
		 PersonalTagUserSettingMapping personalTagUserSettingMappingActive = 
				 (PersonalTagUserSettingMapping) criteria.uniqueResult();
		

		if (!ObjectUtils.isEmpty(personalTagUserSettingMappingActive)) {
			personalTagUserSettingMappingActive.setActive(false);
			saveOrUpdate(personalTagUserSettingMappingActive);
		} else {

			personalTagUserSettingMapping = new PersonalTagUserSettingMapping();
			personalTag = new PersonalTag();
			personalLevel = new PersonalLevel();
			personalTag.setPersonalTagId(personalTagUserSettingMappingDto.getPersonalTag().getPersonalTagId());
			personalTag.setTagName(personalTagUserSettingMappingDto.getPersonalTag().getTagName());
			personalLevel.setPersonalLevelId(personalTagUserSettingMappingDto.getPersonalLevel().getPersonalLevelId());
			personalLevel.setLevelName(personalTagUserSettingMappingDto.getPersonalLevel().getLevelName());
			personalTagUserSettingMapping.setActive(true);
			personalTagUserSettingMapping.setUserId(personalTagUserSettingMappingDto.getUserId());
			personalTagUserSettingMapping.setPersonalLevel(personalLevel);
			personalTagUserSettingMapping.setPersonalTag(personalTag);
			personalTagUserSettingMapping.setTitleName(personalTagUserSettingMappingDto.getTitleName());
			personalTagUserSettingMapping.setLastUpdateDateTime(new Date());
			personalTagUserSettingMapping.setPersonalTagGlobalSettingMappingId(personalTagUserSettingMappingDto.getPersonalTagGlobalSettingMappingId());
			saveOrUpdate(personalTagUserSettingMapping);
		}

		return personalTagUserSettingMapping;

	}

	

/**
 * {@inheritDoc}
 */
	@Override
	public String restPersonalTagUserSettingMapping(String userId) throws CustomException {
		Criteria criteria = currentSession().createCriteria(PersonalTagUserSettingMapping.class);
		criteria.add(Restrictions.eq(USER_ID, userId)).add(Restrictions.eq(ACTIVE, true));
		List<PersonalTagUserSettingMapping> personalTagUserSettingMappingList = criteria.list();
		for (PersonalTagUserSettingMapping personalTagUserSettingMapping : personalTagUserSettingMappingList) {
			personalTagUserSettingMapping.setActive(false);
			saveOrUpdate(personalTagUserSettingMapping);
		}
		return SettingsConstants.UPDATE_SUCCESS;
	}

	

/**
 * {@inheritDoc}
 */
	@Override
	public List<PersonalTagUserSettingMappingDto> getPersonalTagUserSettingMapping(String userId) {
		Criteria criteria = currentSession().createCriteria(PersonalTagUserSettingMapping.class);
		criteria.add(Restrictions.eq(USER_ID, userId)).add(Restrictions.eq(ACTIVE, true));
		List<PersonalTagUserSettingMapping> personalTagUserSettingMappingList = criteria.list();
		PersonalTagUserSettingMappingDto personalTagUserSettingMappingDto;
		PersonalTagDto personalTagDto;
		PersonalLevelDto personalLevelDto;
		List<PersonalTagUserSettingMappingDto> personalTagUserSettingMappingDtoList = new ArrayList<>();
		for (PersonalTagUserSettingMapping personalTagUserSettingMapping : personalTagUserSettingMappingList) {
			
			personalTagUserSettingMappingDto = new PersonalTagUserSettingMappingDto();
			personalTagDto = new PersonalTagDto();
			personalLevelDto = new PersonalLevelDto();
			
			personalTagUserSettingMappingDto.setActive(personalTagUserSettingMapping.getActive());
			personalTagUserSettingMappingDto.setLastUpdateDateTime(personalTagUserSettingMapping.getLastUpdateDateTime());
			personalTagUserSettingMappingDto.setPersonalTagUserSettingMappingId(personalTagUserSettingMapping.getPersonalTagUserSettingMappingId());
			personalTagUserSettingMappingDto.setPersonalTagGlobalSettingMappingId(
					personalTagUserSettingMapping.getPersonalTagGlobalSettingMappingId());
			personalTagUserSettingMappingDto.setUserId(personalTagUserSettingMapping.getUserId());
			personalTagUserSettingMappingDto.setTitleName(personalTagUserSettingMapping.getTitleName());
			personalTagDto.setPersonalTagId(personalTagUserSettingMapping.getPersonalTag().getPersonalTagId());
			personalTagDto.setTagName(personalTagUserSettingMapping.getPersonalTag().getTagName());
			personalLevelDto.setPersonalLevelId(personalTagUserSettingMapping.getPersonalLevel().getPersonalLevelId());
			personalLevelDto.setLevelName(personalTagUserSettingMapping.getPersonalLevel().getLevelName());
			personalTagUserSettingMappingDto.setPersonalTag(personalTagDto);
			personalTagUserSettingMappingDto.setPersonalLevel(personalLevelDto);
			personalTagUserSettingMappingDtoList.add(personalTagUserSettingMappingDto);
		}
		return personalTagUserSettingMappingDtoList;
	}


/**
 * {@inheritDoc}
 */
	@Override
	public Boolean checkEmptyPersonalTagUserSettingMapping(String userId) {
		Criteria criteria = currentSession().createCriteria(PersonalTagUserSettingMapping.class);
		criteria.add(Restrictions.eq(USER_ID, userId)).add(Restrictions.eq(ACTIVE, true));
		if (CollectionUtils.isNotEmpty(criteria.list())) {
			return false;
		}

		return true;
	}

}
