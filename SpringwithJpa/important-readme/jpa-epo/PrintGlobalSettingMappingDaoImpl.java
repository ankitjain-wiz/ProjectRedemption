package org.epo.cms.edfs.services.settings.dao.impl;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintGlobalSettingMapping;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintOption;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.settings.dao.PrintGlobalSettingMappingDao;
import org.epo.cms.edfs.services.settings.dto.PrintGlobalSettingMappingDto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author dinagar
 *
 */
@Repository
public class PrintGlobalSettingMappingDaoImpl extends
    GenericDaoImpl<PrintGlobalSettingMapping, Integer> implements PrintGlobalSettingMappingDao {

  /**
   * {@inheritDoc}
   */
  @Override
  public PrintGlobalSettingMapping updatePrintGlobalSettingMappingDetails(
      PrintGlobalSettingMappingDto printGlobalSettingMappingDto) throws CustomException {
    PrintGlobalSettingMapping printGlobalSettingMapping =
        getPrintGlobalSettings(printGlobalSettingMappingDto);
    long printOptionId = printGlobalSettingMappingDto.getPrintOption().getPrintOptionId();
    PrintOption printOption = new PrintOption();
    printOption.setPrintOptionId(printOptionId);
    printGlobalSettingMapping.setPrintOption(printOption);
    saveOrUpdate(printGlobalSettingMapping);
    return printGlobalSettingMapping;
  }

  /**
 * @param printGlobalSettingMappingDto : PrintGlobalSettingMappingDto
 * @return PrintGlobalSettingMapping : PrintGlobalSettingMapping
 */
private PrintGlobalSettingMapping getPrintGlobalSettings(
      PrintGlobalSettingMappingDto printGlobalSettingMappingDto) {
    long roleId = printGlobalSettingMappingDto.getRole().getRoleId();
    Role role = new Role();
    role.setRoleId(roleId);
    Criteria criteria = currentSession().createCriteria(PrintGlobalSettingMapping.class);
    criteria.add(Restrictions.eq("role", role));
    return (PrintGlobalSettingMapping) criteria.uniqueResult();
  }


}
