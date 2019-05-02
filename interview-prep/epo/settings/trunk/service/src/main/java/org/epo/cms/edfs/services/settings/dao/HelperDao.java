package org.epo.cms.edfs.services.settings.dao;

import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.Helper;
import org.epo.cms.edfs.services.settings.dto.HelperDto;

/**
 * Helper DAO to get data from  Helper Table
 * 
 * @author dinagar
 *
 */
public interface HelperDao extends GenericDao<Helper, Integer> {
  /**
   * Get helper Details
   * 
   * @param helperName : String
   * @param moduleOrWorkspace : String
   * @return List<HelperDto> : List<HelperDto>
   * @throws CustomException : CustomException
   */
	
HelperDto getHelperDetails(String helperName, String moduleOrWorkspace) throws CustomException;

  /**
   * Get list of helper Data
   * 
   * @return List<HelperDto> : List<HelperDto>
   * @throws CustomException : CustomException
   */
  List<HelperDto> getHelperList() throws CustomException;
}
