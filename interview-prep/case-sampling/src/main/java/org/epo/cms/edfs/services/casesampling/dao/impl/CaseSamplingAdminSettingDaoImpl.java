package org.epo.cms.edfs.services.casesampling.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingAdminSettingDao;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingAdminSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingAdminSetting;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Implementation of CaseSamplingAdminSettingDao.
 * 
 * @author amigarg
 *
 */
@Repository
public class CaseSamplingAdminSettingDaoImpl extends
    GenericDaoImpl<CaseSamplingAdminSetting, Integer> implements CaseSamplingAdminSettingDao {
	
	@Autowired
	ResponseValidator responseValidator;
	
	@Autowired
	private UpdaterSettingsResponse updaterSettingsResponse;

  /**
   * {@inheritDoc}.
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CaseSamplingAdminSettingDto getCaseSamplingAdminSetting()
      throws CustomException {

    CaseSamplingAdminSettingDto caseSamplingAdminSettingDto = null;
    List<CaseSamplingAdminSetting> adminSettinglist = (List<CaseSamplingAdminSetting>) getAll();
    if (CollectionUtils.isNotEmpty(adminSettinglist)) {
      CaseSamplingAdminSetting adminSetting ;
      adminSetting = adminSettinglist.get(0);
      caseSamplingAdminSettingDto =  adminSettingEntityDtoMapper(adminSetting);
    }

    return caseSamplingAdminSettingDto;
  }


  /**
   * This method is used to map CaseSamplingAdminSetting Entity with its DTO class.
   * 
   * @param adminSetting of type CaseSamplingAdminSetting
   * @param caseSamplingAdminSettingDto of type CaseSamplingAdminSettingDto
   * @return CaseSamplingAdminSettingDto as value
   */
  private CaseSamplingAdminSettingDto adminSettingEntityDtoMapper(
      CaseSamplingAdminSetting adminSetting) {
    CaseSamplingAdminSettingDto caseSamplingAdminSettingDto = new CaseSamplingAdminSettingDto();
    caseSamplingAdminSettingDto
        .setCaseSamplingAdminSettingId(adminSetting.getCaseSamplingAdminSettingId());
    caseSamplingAdminSettingDto.setNumMaxYearTarget(adminSetting.getNumMaxYearTarget());
    caseSamplingAdminSettingDto.setNumMinYearTarget(adminSetting.getNumMinYearTarget());
    caseSamplingAdminSettingDto.setNumMustSample(adminSetting.getNumMustSample());
    return caseSamplingAdminSettingDto;
  }


  /**
   * {@inheritDoc}.
   */
@Override
public int updateCaseSamplingAdminSetting(CaseSamplingAdminSettingDto caseSamplingAdminSettingDto) throws CustomException {
	
	Criteria criteria = currentSession().createCriteria(daoType);
	CaseSamplingAdminSetting caseSamplingAdminSetting = (CaseSamplingAdminSetting) criteria.uniqueResult();
	
	if(!ObjectUtils.isEmpty(caseSamplingAdminSetting))
	{
		caseSamplingAdminSetting.setNumMinYearTarget(caseSamplingAdminSettingDto.getNumMinYearTarget());
		caseSamplingAdminSetting.setNumMaxYearTarget(caseSamplingAdminSettingDto.getNumMaxYearTarget());
		caseSamplingAdminSetting.setNumMustSample(caseSamplingAdminSettingDto.getNumMustSample());
		saveOrUpdate(caseSamplingAdminSetting);
	}
	else{
		updaterSettingsResponse.setDescription("No data in CaseCheckAdminSetting table ");
		throw new CustomException(responseValidator.getErrorResponse("0", "No data in CaseCheckAdminSetting table to update"));
	}
	return 1;
}

}
