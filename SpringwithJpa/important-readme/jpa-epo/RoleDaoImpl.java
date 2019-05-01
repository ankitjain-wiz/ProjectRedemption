package org.epo.cms.edfs.services.settings.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ErrorCodeEnum;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.settings.dao.RoleDao;
import org.epo.cms.edfs.services.settings.dto.RoleDto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author dinagar
 *
 */
@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role, Integer> implements RoleDao {

  @Autowired
  ResponseValidator responseValidator;

  /**
   * {@inheritDoc}
   */
  @Override
  public RoleDto getRole(String role, boolean deepCopy) throws CustomException {
    return getRoleDetails(role, deepCopy);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly = true)
  public RoleDto getRole(String role) throws CustomException {
    return getRoleDetails(role, false);
  }

  private RoleDto getRoleDetails(String role, boolean deepCopy) throws CustomException {
    Criteria criteria = currentSession().createCriteria(Role.class);
    criteria.add(Restrictions.eq("roleName", role));
    Role roleObj = (Role) criteria.uniqueResult();
    RoleDto roleDto = new RoleDto();
    if (null != roleObj) {
      if (deepCopy) {
        Mapper mapper = new DozerBeanMapper();
        mapper.map(roleObj, roleDto);
      } else {
        roleDto.setRoleId(roleObj.getRoleId());
      }
      return roleDto;
    } else {
      throw new CustomException(responseValidator.getErrorResponse(ErrorCodeEnum.BAD_REQUEST_CODE));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<RoleDto> getRoleList() throws CustomException {
    Criteria criteria = currentSession().createCriteria(Role.class);
    List<Role> roleList = criteria.list();
    if (!roleList.isEmpty()) {
      List<RoleDto> dtoList = new ArrayList<>();
      for (Role role : roleList) {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(role.getRoleId());
        roleDto.setRoleName(role.getRoleName());
        dtoList.add(roleDto);
      }
      return dtoList;
    } else {
      throw new CustomException(responseValidator.getErrorResponse(ErrorCodeEnum.BAD_REQUEST_CODE));
    }
  }
}


