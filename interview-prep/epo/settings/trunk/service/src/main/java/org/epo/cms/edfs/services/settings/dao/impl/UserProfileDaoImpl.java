package org.epo.cms.edfs.services.settings.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.Module;
import org.epo.cms.edfs.services.dossierpersistence.entity.UserProfile;
import org.epo.cms.edfs.services.dossierpersistence.entity.WorkSpace;
import org.epo.cms.edfs.services.settings.beans.ModuleList;
import org.epo.cms.edfs.services.settings.beans.Workspace;
import org.epo.cms.edfs.services.settings.beans.WorkspaceList;
import org.epo.cms.edfs.services.settings.dao.UserProfileDao;
import org.epo.cms.edfs.services.settings.dto.UserProfileDto;
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
public class UserProfileDaoImpl extends GenericDaoImpl<UserProfile, Integer> implements UserProfileDao {

	private static final String ACTIVE = "active";
	private static final String USER_ID = "userId";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserProfile updateUserProfile(UserProfileDto userProfileDto) throws CustomException {
		Criteria criteria = currentSession().createCriteria(UserProfile.class);
		criteria.add(Restrictions.eq(USER_ID, userProfileDto.getUserId())).add(Restrictions.eq(ACTIVE, true));
		List<UserProfile> userProfileList = criteria.list();
		if (CollectionUtils.isNotEmpty(userProfileList)) {
			for (UserProfile userProfile : userProfileList) {
				userProfile.setActive(false);
				saveOrUpdate(userProfile);
			}
		}
		UserProfile userProfile = new UserProfile();
		Module module = new Module();
		WorkSpace workSpace = new WorkSpace();
		module.setModuleId(userProfileDto.getModule().getModuleId());
		module.setModuleName(userProfileDto.getModule().getModuleName());
		workSpace.setWorkSpaceId(userProfileDto.getWorkSpace().getWorkSpaceId());
		workSpace.setWorkSpaceName(userProfileDto.getWorkSpace().getWorkSpaceName());
		userProfile.setActive(true);

		userProfile.setAlertLevel(userProfileDto.getAlertLevel());

		userProfile.setFontSize(userProfileDto.getFontSize());
		userProfile.setUserId(userProfileDto.getUserId());
		userProfile.setModule(module);
		userProfile.setWorkSpace(workSpace);
		userProfile.setLastUpdateDateTime(new Date());
		saveOrUpdate(userProfile);
		return userProfile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String resetToDefaultUserProfile(String userId, boolean active) throws CustomException {
		Criteria criteria = currentSession().createCriteria(UserProfile.class);
		criteria.add(Restrictions.eq(USER_ID, userId)).add(Restrictions.eq(ACTIVE, active));
		List<UserProfile> userProfileList = criteria.list();
		if (CollectionUtils.isNotEmpty(userProfileList)) {
			for (UserProfile userProfile : userProfileList) {
				userProfile.setActive(false);
				saveOrUpdate(userProfile);
			}
		}
		return SettingsConstants.UPDATE_SUCCESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserProfileDto> getUserProfileList(String userId) throws CustomException {
		Criteria criteria = currentSession().createCriteria(UserProfile.class);
		criteria.add(Restrictions.eq(USER_ID, userId)).add(Restrictions.eq(ACTIVE, true));
		List<UserProfile> userProfileList = criteria.list();
		List<UserProfileDto> userProfileDtoList = null;
		if (CollectionUtils.isNotEmpty(userProfileList)) {
			userProfileDtoList = new ArrayList<>();
			for (UserProfile userProfile : userProfileList) {
				UserProfileDto userProfileDto = new UserProfileDto();
				Mapper mapper = new DozerBeanMapper();
				mapper.map(userProfile, userProfileDto);
				userProfileDtoList.add(userProfileDto);
			}
		}
		return userProfileDtoList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkUserModuleWorkspace(String userId) {
		Criteria criteria = currentSession().createCriteria(UserProfile.class);
		criteria.add(Restrictions.eq(USER_ID, userId)).add(Restrictions.eq(ACTIVE, true));
		List<UserProfile> userProfileList = criteria.list();
		if (CollectionUtils.isNotEmpty(userProfileList)) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUserProfileAlertLevel(org.epo.cms.edfs.services.settings.beans.Module module, Workspace workSpace,
			 String userId) {
		String notificationAlertLevel = null;
		List<ModuleList> moduleList = module.getModuleList();
		List<WorkspaceList> workspaceList = workSpace.getWorkspaceList();
		List<Long> moduleIdList = new ArrayList<>();
		List<Long> workspaceIdList = new ArrayList<>();
		for (ModuleList m : moduleList) {
			moduleIdList.add(m.getModuleId());
		}
		for (WorkspaceList w : workspaceList) {
			workspaceIdList.add(w.getWorkspaceId());
		}

		Criteria criteria = currentSession().createCriteria(UserProfile.class);
		criteria.add(Restrictions.in("module.moduleId", moduleIdList))
				.add(Restrictions.in("workSpace.workSpaceId", workspaceIdList)).add(Restrictions.eq(USER_ID, userId))
				.add(Restrictions.eq(ACTIVE, true));
		UserProfile userProfile = (UserProfile) criteria.uniqueResult();
		if (!ObjectUtils.isEmpty(userProfile)) {
			notificationAlertLevel = userProfile.getAlertLevel();
		}

		return notificationAlertLevel;

	}



}
