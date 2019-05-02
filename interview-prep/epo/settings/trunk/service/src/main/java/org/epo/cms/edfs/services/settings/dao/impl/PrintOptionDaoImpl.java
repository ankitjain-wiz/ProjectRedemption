package org.epo.cms.edfs.services.settings.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ErrorCodeEnum;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintOption;
import org.epo.cms.edfs.services.settings.dao.PrintOptionDao;
import org.epo.cms.edfs.services.settings.dto.PrintOptionDto;
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
public class PrintOptionDaoImpl extends GenericDaoImpl<PrintOption, Integer>
    implements PrintOptionDao {

  @Autowired
  private ResponseValidator responseValidator;

  /**
   * {@inheritDoc}
   */
  @Override
  public PrintOptionDto getPrintOption(String printAdditionalOption) throws CustomException {
    Criteria criteria = currentSession().createCriteria(PrintOption.class);
    criteria.add(Restrictions.eq("printAdditionalInfo", printAdditionalOption));
    PrintOption printOption = (PrintOption) criteria.uniqueResult();

    if (!ObjectUtils.isEmpty(printOption)) {
      PrintOptionDto printOptionDto = new PrintOptionDto();
      printOptionDto.setPrintOptionId(printOption.getPrintOptionId());
      return printOptionDto;
    } else {
      throw new CustomException(responseValidator.getErrorResponse(ErrorCodeEnum.BAD_REQUEST_CODE));
    }
  }

  

/**
 * {@inheritDoc}
 */
@Override
public List<PrintOptionDto> getPrintOptionList() {
	Criteria criteria = currentSession().createCriteria(PrintOption.class);
	List<PrintOption> printOptionList = criteria.list();
	List<PrintOptionDto> printOptionDtoList = new ArrayList<>();
	if( CollectionUtils.isNotEmpty(printOptionList)){
		for(PrintOption printOption :printOptionList){
			PrintOptionDto printOptionDto = new PrintOptionDto();
			printOptionDto.setPrintOptionId(printOption.getPrintOptionId());
			printOptionDto.setPrintAdditionalInfo(printOption.getPrintAdditionalInfo());
			printOptionDtoList.add(printOptionDto);
		}
	}
	return printOptionDtoList;
}
}
