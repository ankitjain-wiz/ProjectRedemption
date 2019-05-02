package org.epo.cms.edfs.services.settings.dao;

import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.UserProfile;
import org.epo.cms.edfs.services.settings.beans.Module;
import org.epo.cms.edfs.services.settings.beans.Workspace;
import org.epo.cms.edfs.services.settings.dto.RoleDto;
import org.epo.cms.edfs.services.settings.dto.UserProfileDto;

/**
 * Dao to get,Update and reset User Profile Data 
 * @author ankitjain2
 *
 */
public interface UserProfileDao extends GenericDao<UserProfile, Integer> {

	
	/**
	 *  Method to update User Profile
	 * @param userProfileDto : UserProfileDto
	 * @return UserProfile : UserProfile
	 * @throws CustomException : CustomException
	 */
	UserProfile updateUserProfile(UserProfileDto userProfileDto) throws CustomException;


	/**
	 *  Method to reset User Profile
	 * @param userId : String
	 * @param active : boolean
	 * @return String : String
	 * @throws CustomException : CustomException
	 */
	String resetToDefaultUserProfile(String userId, boolean active) throws CustomException;

	/**
	 * Method to get User Profile
	 * @param userId : String
	 * @return List<UserProfileDto>: List<UserProfileDto>
	 * @throws CustomException : CustomException
	 */
	List<UserProfileDto> getUserProfileList(String userId) throws CustomException;

	/**
	 *  Method to check whether UserProfile  is empty or not 
	 * @param userId : String
	 * @return boolean : boolean
	 */
	boolean checkUserModuleWorkspace(String userId);
	
	
	/**
	 * Get Notification Alert Level from User Profile
	 * @param module : Module
	 * @param workSpace : Workspace
	 * @param roleObj : RoleDto
	 * @param userId : String
	 * @return String: String
	 */
	String getUserProfileAlertLevel(Module module,Workspace workSpace ,String userId);

}
