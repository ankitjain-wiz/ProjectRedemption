package org.epo.cms.edfs.services.settings.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.GlobalProfile;
import org.epo.cms.edfs.services.dossierpersistence.entity.Module;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.dossierpersistence.entity.WorkSpace;
import org.epo.cms.edfs.services.settings.beans.ModuleList;
import org.epo.cms.edfs.services.settings.beans.Workspace;
import org.epo.cms.edfs.services.settings.beans.WorkspaceList;
import org.epo.cms.edfs.services.settings.dao.GlobalProfileDao;
import org.epo.cms.edfs.services.settings.dto.GlobalProfileDto;
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
public class GlobalProfileDaoImpl extends GenericDaoImpl<GlobalProfile, Integer> implements GlobalProfileDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GlobalProfile updateGlobalProfile(GlobalProfileDto globalProfileDto) throws CustomException {
		GlobalProfile globalProfile = getGlobalProfileDetails(globalProfileDto.getRole());
		Module module = new Module();
		WorkSpace workSpace = new WorkSpace();
		Role role = null;
		if (!ObjectUtils.isEmpty(globalProfile)) {
			role = globalProfile.getRole();
		}

		module.setModuleId(globalProfileDto.getModule().getModuleId());
		workSpace.setWorkSpaceId(globalProfileDto.getWorkSpace().getWorkSpaceId());
		globalProfile.setAlertLevel(globalProfileDto.getAlertLevel());
		globalProfile.setFontSize(globalProfileDto.getFontSize());
		globalProfile.setRole(role);
		globalProfile.setModule(module);
		globalProfile.setWorkSpace(workSpace);
		saveOrUpdate(globalProfile);
		return globalProfile;
	}

	/**
	 * get Global Profile Details
	 * @param roleDto: RoleDto
	 * @return GlobalProfile : GlobalProfile
	 * @throws CustomException : CustomException
	 */
	private GlobalProfile getGlobalProfileDetails(RoleDto roleDto) throws CustomException {
		Role role = new Role();
		role.setRoleId(roleDto.getRoleId());
		Criteria criteria = currentSession().createCriteria(GlobalProfile.class);
		criteria.add(Restrictions.eq("role", role));
		return (GlobalProfile) criteria.uniqueResult();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getGlobalProfileAlertLevel(org.epo.cms.edfs.services.settings.beans.Module module,
			Workspace workSpace,RoleDto roleDto) throws CustomException  {
		
		Role role = new Role();
		role.setRoleId(roleDto.getRoleId());
		
		List<ModuleList> moduleList = module.getModuleList();
		List<WorkspaceList> workspaceList = workSpace.getWorkspaceList();
		
		String notificationAlertLevel = null;
		if(CollectionUtils.isNotEmpty(moduleList)&&CollectionUtils.isNotEmpty(moduleList))
		{

			List<Long> moduleIdList = new ArrayList<>();
			List<Long> workspaceIdList = new ArrayList<>();
			for (ModuleList m : moduleList) {
				moduleIdList.add(m.getModuleId());
			}
			for (WorkspaceList w : workspaceList) {
				workspaceIdList.add(w.getWorkspaceId());
			}

			Criteria criteria = currentSession().createCriteria(GlobalProfile.class);
			criteria.add(Restrictions.in("module.moduleId", moduleIdList))
					.add(Restrictions.in("workSpace.workSpaceId", workspaceIdList))
					.add(Restrictions.eq("role", role));
			
			GlobalProfile globalProfile = (GlobalProfile) criteria.uniqueResult();
			if (!ObjectUtils.isEmpty(globalProfile)) {
				notificationAlertLevel = globalProfile.getAlertLevel();
			}
		}
		

		return notificationAlertLevel;
	}

}
