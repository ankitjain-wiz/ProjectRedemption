package org.epo.cms.edfs.services.settings.dao.impl;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ErrorCodeEnum;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.Module;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.settings.dao.ModuleDao;
import org.epo.cms.edfs.services.settings.dto.ModuleDto;
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
public class ModuleDaoImpl extends GenericDaoImpl<Module, Integer> implements ModuleDao {

  @Autowired
  private ResponseValidator responseValidator;

  /**
   * {@inheritDoc}
   */
  @Override
  public ModuleDto getModule(String module, long roleId) throws CustomException {
    Role role = new Role();
    role.setRoleId(roleId);
    Criteria criteria = currentSession().createCriteria(Module.class);
    criteria.add(Restrictions.eq("moduleName", module)).add(Restrictions.eq("role", role));
    Module moduleObj = (Module) criteria.uniqueResult();
    if (!ObjectUtils.isEmpty(moduleObj)) {
      ModuleDto moduleDto = new ModuleDto();
      moduleDto.setModuleId(moduleObj.getModuleId());
      moduleDto.setModuleName(moduleObj.getModuleName());
      return moduleDto;
    } else
      throw new CustomException(responseValidator.getErrorResponse(ErrorCodeEnum.BAD_REQUEST_CODE));
  }

}
