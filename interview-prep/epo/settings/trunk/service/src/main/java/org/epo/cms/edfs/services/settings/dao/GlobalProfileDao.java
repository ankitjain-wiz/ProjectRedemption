package org.epo.cms.edfs.services.settings.dao;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.GlobalProfile;
import org.epo.cms.edfs.services.settings.beans.Module;
import org.epo.cms.edfs.services.settings.beans.Workspace;
import org.epo.cms.edfs.services.settings.dto.GlobalProfileDto;
import org.epo.cms.edfs.services.settings.dto.RoleDto;

/**
 * Dao Layer to Update Global Profile 
 * @author dinagar
 *
 */
public interface GlobalProfileDao extends GenericDao<GlobalProfile, Integer> {

  /**
   * Method to update Global Profile Details
   * 
   * @param globalProfileDto : GlobalProfileDto
   * @return GlobalProfile : globalProfile
   * @throws CustomException : CustomException
   */
  GlobalProfile updateGlobalProfile(GlobalProfileDto globalProfileDto) throws CustomException;
  

  
  /**
   *Get Notification Alert Level from Global Profile
 * @param module : Module
 * @param workSpace : Workspace
 * @param roleObj : RoleDto
 * @return String: String
 */
String getGlobalProfileAlertLevel(Module module,Workspace workSpace,RoleDto roleDto) throws CustomException;


}
