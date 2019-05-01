package org.epo.cms.edfs.services.settings.dao.impl;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ErrorCodeEnum;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.dossierpersistence.entity.WorkSpace;
import org.epo.cms.edfs.services.settings.dao.WorkspaceDao;
import org.epo.cms.edfs.services.settings.dto.WorkSpaceDto;
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
public class WorkspaceDaoImpl extends GenericDaoImpl<WorkSpace, Integer> implements WorkspaceDao {

  @Autowired
  private ResponseValidator responseValidator;

  /**
   * {@inheritDoc}
   */
  @Override
  public WorkSpaceDto getWorkSpace(String workspace, long roleId) throws CustomException {
    Role role = new Role();
    role.setRoleId(roleId);
    Criteria criteria = currentSession().createCriteria(WorkSpace.class);
    criteria.add(Restrictions.eq("workSpaceName", workspace)).add(Restrictions.eq("role", role));
    WorkSpace workSpace = (WorkSpace) criteria.uniqueResult();
    if (!ObjectUtils.isEmpty(workSpace)) {
      WorkSpaceDto workSpaceDto = new WorkSpaceDto();
      workSpaceDto.setWorkSpaceId(workSpace.getWorkSpaceId());
      workSpaceDto.setWorkSpaceName(workSpace.getWorkSpaceName());
      return workSpaceDto;
    } else {
      throw new CustomException(responseValidator.getErrorResponse(ErrorCodeEnum.BAD_REQUEST_CODE));
    }
  }

}
