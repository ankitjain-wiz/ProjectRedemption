package org.epo.cms.edfs.services.settings.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.Helper;
import org.epo.cms.edfs.services.dossierpersistence.entity.HelperUserSettingMapping;
import org.epo.cms.edfs.services.settings.dao.HelperUserSettingMappingDao;
import org.epo.cms.edfs.services.settings.dto.HelperDto;
import org.epo.cms.edfs.services.settings.dto.HelperUserSettingMappingDto;
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
public class HelperUserSettingMappingDaoImpl extends GenericDaoImpl<HelperUserSettingMapping, Integer>
		implements HelperUserSettingMappingDao {

	private static final String ACTIVE = "active";
	private static final String USER_ID = "userId";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HelperUserSettingMapping updateHelperUserSettingMapping(HelperUserSettingMappingDto dto)
			throws CustomException {
		Helper helper = new Helper();
		helper.setHelperId(dto.getHelper().getHelperId());
		Criteria criteria = currentSession().createCriteria(HelperUserSettingMapping.class);
		criteria.add(Restrictions.eq(USER_ID, dto.getUserId())).add(Restrictions.eq(ACTIVE, true))
				.add(Restrictions.eq("helper", helper));
		HelperUserSettingMapping helperUserSettingMapping = (HelperUserSettingMapping) criteria.uniqueResult();

		if (!ObjectUtils.isEmpty(helperUserSettingMapping)) {
			helperUserSettingMapping.setActive(false);
			saveOrUpdate(helperUserSettingMapping);

		}

		helperUserSettingMapping = new HelperUserSettingMapping();
		helper = new Helper();
		helper.setHelperId(dto.getHelper().getHelperId());
		helper.setHelperName(dto.getHelper().getHelperName());
		helperUserSettingMapping.setActive(true);
		helperUserSettingMapping.setHelper(helper);
		helperUserSettingMapping.setUserId(dto.getUserId());
		helperUserSettingMapping.setChecked(dto.isChecked());
		helperUserSettingMapping.setLastUpdateDateTime(new Date());
		saveOrUpdate(helperUserSettingMapping);

		return helperUserSettingMapping;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String resetHelperUserSettingMapping(String userId) throws CustomException {
		Criteria criteria = currentSession().createCriteria(HelperUserSettingMapping.class);
		criteria.add(Restrictions.eq(USER_ID, userId)).add(Restrictions.eq(ACTIVE, true));
		List<HelperUserSettingMapping> helperUserSettingMappingList = criteria.list();
		if (CollectionUtils.isNotEmpty(helperUserSettingMappingList)) {
			for (HelperUserSettingMapping helperUserSettingMapping : helperUserSettingMappingList) {
				helperUserSettingMapping.setActive(false);
				saveOrUpdate(helperUserSettingMapping);
			}
		}
		return SettingsConstants.UPDATE_SUCCESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HelperUserSettingMappingDto> userHelperList(String userId) {
		Criteria criteria = currentSession().createCriteria(HelperUserSettingMapping.class);
		criteria.add(Restrictions.eq(USER_ID, userId));
		List<HelperUserSettingMapping> helperUserSettingMappingList = criteria.list();
		List<HelperUserSettingMappingDto> helperUserSettingMappingDtoList = new ArrayList<>();
		HelperUserSettingMappingDto dto;
		HelperDto helperDto;
		for (HelperUserSettingMapping helperUserSettingMapping : helperUserSettingMappingList) {
			dto = new HelperUserSettingMappingDto();
			helperDto = new HelperDto();
			dto.setHelperUserSettingMappingId(helperUserSettingMapping.getHelperUserSettingMappingId());
			dto.setUserId(helperUserSettingMapping.getUserId());
			dto.setActive(helperUserSettingMapping.getActive());
			dto.setLastUpdateDateTime(helperUserSettingMapping.getLastUpdateDateTime());
			dto.setChecked(helperUserSettingMapping.getChecked());
			helperDto.setHelperId(helperUserSettingMapping.getHelper().getHelperId());
			dto.setHelper(helperDto);
			helperUserSettingMappingDtoList.add(dto);

		}

		return helperUserSettingMappingDtoList;
	}

}
