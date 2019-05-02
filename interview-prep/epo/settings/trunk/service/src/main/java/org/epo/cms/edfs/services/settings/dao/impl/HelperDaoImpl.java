package org.epo.cms.edfs.services.settings.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.Helper;
import org.epo.cms.edfs.services.settings.dao.HelperDao;
import org.epo.cms.edfs.services.settings.dto.HelperDto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author dinagar
 *
 */
@Repository
public class HelperDaoImpl extends GenericDaoImpl<Helper, Integer> implements HelperDao {
  /**
   * {@inheritDoc}
   */

  @Override
  public HelperDto getHelperDetails(String helperName, String moduleOrWorkspace)
      throws CustomException {
    Criteria criteria = currentSession().createCriteria(Helper.class);
    criteria.add(Restrictions.eq("helperName", helperName))
        .add(Restrictions.eq("moduleOrWorkspace", moduleOrWorkspace));
    Helper helper = (Helper) criteria.uniqueResult();
    HelperDto helperDto = new HelperDto();
    helperDto.setHelperId(helper.getHelperId());
    helperDto.setHelperName(helper.getHelperName());
    helperDto.setModuleOrWorkspace(helper.getModuleOrWorkspace());
    return helperDto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<HelperDto> getHelperList() throws CustomException {
    Criteria criteria = currentSession().createCriteria(Helper.class);
    List<Helper> helperList = criteria.list();
    List<HelperDto> helperDtoList = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(helperList)) {
      for (Helper helper : helperList) {
        HelperDto helperDto = new HelperDto();
        helperDto.setHelperId(helper.getHelperId());
        helperDto.setHelperName(helper.getHelperName());
        helperDto.setModuleOrWorkspace(helper.getModuleOrWorkspace());
        helperDtoList.add(helperDto);
      }
    }
    return helperDtoList;
  }
}
