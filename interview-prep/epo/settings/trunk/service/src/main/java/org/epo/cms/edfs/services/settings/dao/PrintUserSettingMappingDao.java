package org.epo.cms.edfs.services.settings.dao;

import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintUserSettingMapping;
import org.epo.cms.edfs.services.settings.dto.PrintUserSettingMappingDto;

/**
 * Dao to get,update and reset PrintUserSettingMapping
 * @author dinagar
 *
 */
public interface PrintUserSettingMappingDao extends GenericDao<PrintUserSettingMapping, Integer> {

  /**
   * Update PrintUserSettingMapping details
   * 
   * @param dto: PrintUserSettingMappingDto
   * @return PrintUserSettingMapping : PrintUserSettingMapping
   * @throws CustomException : CustomException
   */
  PrintUserSettingMapping updatePrintUserSettingMapping(PrintUserSettingMappingDto dto)
      throws CustomException;

  /**
   * Reset PrintUserSettingMapping settings
   * 
   * @param userId : String
   * @return String : String
   * @throws CustomException : customException
   */
  String resetPrintUserSettings(String userId) throws CustomException;
  
  
  /**
 * @param userId : String
 * @return List<PrintUserSettingMapping> : List<PrintUserSettingMapping>
 */
List<PrintUserSettingMapping> getPrintUserSettingMapping(String userId);
}
