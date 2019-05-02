package org.epo.cms.edfs.services.settings.dao;


import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.Module;
import org.epo.cms.edfs.services.settings.dto.ModuleDto;

/**
 * Module DAO getting data form backend and return Module DTO formate.
 * 
 * @author dinagar
 *
 */
public interface ModuleDao extends GenericDao<Module, Integer> {

  /**
   * Get module details
   * 
   * @param module : module
   * @return ModuleDto : moduleDTO Object.
   * @throws CustomException : customException
   */
  ModuleDto getModule(String module, long roleId) throws CustomException;
}
