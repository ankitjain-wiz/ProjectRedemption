package org.epo.cms.edfs.services.settings.dao;

import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.settings.dto.RoleDto;

/**
 * RoleDao getting Role Entity by using roleName.
 * 
 * @author dinagar
 *
 */
public interface RoleDao extends GenericDao<Role, Integer> {
  /**
   * Get Role details
   * 
   * @param role : String
   * @param deepCopy : boolean
   * @return RoleDto : RoleDto
   * @throws CustomException : CustomException
   */
  RoleDto getRole(String role, boolean deepCopy) throws CustomException;

  /**
   * DAO call for Role Details
   * 
   * @param role : String
   * @return RoleDto : RoleDto
   * @throws CustomException : CustomException
   */
  RoleDto getRole(String role) throws CustomException;

  /**
   * All Role list details
   * 
   * @return List<RoleDto> : List<RoleDto>
   * @throws CustomException : CustomException
   */
  List<RoleDto> getRoleList() throws CustomException;

}
