package org.epo.cms.edfs.services.settings.dao;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintGlobalSettingMapping;
import org.epo.cms.edfs.services.settings.dto.PrintGlobalSettingMappingDto;

/**
 * calass returning data of PrintGlobalSettingMapping class.
 * 
 * @author dinagar
 *
 */
public interface PrintGlobalSettingMappingDao
    extends GenericDao<PrintGlobalSettingMapping, Integer> {
  /**
   * Update PrintGlobalSettingMapping data details
   * 
   * @param printGlobalSettingMappingDto : printGlobalSettingMappingDto
   * @return PrintGlobalSettingMapping : PrintGlobalSettingMapping
   * @throws CustomException : CustomException
   */
  PrintGlobalSettingMapping updatePrintGlobalSettingMappingDetails(PrintGlobalSettingMappingDto printGlobalSettingMappingDto) throws CustomException;

}
