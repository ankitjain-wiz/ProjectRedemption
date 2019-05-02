package org.epo.cms.edfs.services.settings.dao;

import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintOption;
import org.epo.cms.edfs.services.settings.dto.PrintOptionDto;

/**
 * PrintOption DAO class getting PrintOption data form Back end.
 * 
 * @author dinagar
 *
 */
public interface PrintOptionDao extends GenericDao<PrintOption, Integer> {

  /**
   * Get Option details
   * 
   * @param printAdditionalOption : String
   * @return PrintOptionDto : PrintOptionDto
   * @throws CustomException : CustomException
   */
  PrintOptionDto getPrintOption(String printAdditionalOption) throws CustomException;
  /**
   * PrintOption date list
   *  
   * @return List<PrintOptionDto> : List<PrintOptionDto>
   */
  List<PrintOptionDto> getPrintOptionList();
}
