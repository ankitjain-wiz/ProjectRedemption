package org.epo.cms.edfs.services.settings.dao.impl;

import java.util.Date;
import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintOption;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintUserSettingMapping;
import org.epo.cms.edfs.services.settings.dao.PrintUserSettingMappingDao;
import org.epo.cms.edfs.services.settings.dto.PrintUserSettingMappingDto;
import org.epo.cms.edfs.services.settings.utils.SettingsConstants;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

/**
 * 
 * @author dinagar
 *
 */
@Repository
public class PrintUserSettingMappingDaoImpl extends GenericDaoImpl<PrintUserSettingMapping, Integer>
    implements PrintUserSettingMappingDao {

  private static final String ACTIVE = "active";
  private static final String USER_ID = "userId";

/**
   * {@inheritDoc}
   */
  @Override
  public PrintUserSettingMapping updatePrintUserSettingMapping(PrintUserSettingMappingDto dto)
      throws CustomException {
    Criteria criteria = currentSession().createCriteria(PrintUserSettingMapping.class);
    criteria.add(Restrictions.eq(USER_ID, dto.getUserId())).add(Restrictions.eq(ACTIVE, true));
    PrintUserSettingMapping printUserSettingMappingU =
        (PrintUserSettingMapping) criteria.uniqueResult();
    if (!ObjectUtils.isEmpty( printUserSettingMappingU)) {
      printUserSettingMappingU.setActive(false);
      saveOrUpdate(printUserSettingMappingU);
    }
    PrintUserSettingMapping printUserSettingMapping = new PrintUserSettingMapping();
    PrintOption printOption = new PrintOption();
    printOption.setPrintOptionId(dto.getPrintOption().getPrintOptionId());
    printOption.setPrintAdditionalInfo(dto.getPrintOption().getPrintAdditionalInfo());
    printUserSettingMapping.setPrintOption(printOption);
    printUserSettingMapping.setActive(true);
    printUserSettingMapping.setUserId(dto.getUserId());
    printUserSettingMapping.setLastUpdateDateTime(new Date());
    saveOrUpdate(printUserSettingMapping);
    return printUserSettingMapping;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String resetPrintUserSettings(String userId) throws CustomException {
    Criteria criteria = currentSession().createCriteria(PrintUserSettingMapping.class);
    criteria.add(Restrictions.eq(USER_ID, userId)).add(Restrictions.eq(ACTIVE, true));
    List<PrintUserSettingMapping> printUserSettingMappingList = criteria.list();
    for (PrintUserSettingMapping printUserSettingMapping : printUserSettingMappingList) {
      printUserSettingMapping.setActive(false);
      saveOrUpdate(printUserSettingMapping);
    }
    return SettingsConstants.UPDATE_SUCCESS;
  }

  /**
   * {@inheritDoc}
   */
@SuppressWarnings("unchecked")
@Override
  public List<PrintUserSettingMapping> getPrintUserSettingMapping(String userId) {
    Criteria criteria = currentSession().createCriteria(PrintUserSettingMapping.class);
    criteria.add(Restrictions.eq(USER_ID, userId)).add(Restrictions.eq(ACTIVE, true));
    return criteria.list();
  }

}
