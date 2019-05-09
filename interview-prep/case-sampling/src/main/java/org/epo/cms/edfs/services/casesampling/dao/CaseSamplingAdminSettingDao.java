package org.epo.cms.edfs.services.casesampling.dao;

import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingAdminSettingDto;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingAdminSetting;

/**
 * perform operations on CaseSamplingAdminSetting Entity. 
 * @author amigarg
 */
public interface CaseSamplingAdminSettingDao extends GenericDao<CaseSamplingAdminSetting, Integer> {


  /**
   * This method is used to get the CaseSamplingAdminSettingDto object.
   * 
   * @return CaseSamplingAdminSettingDto
   * @throws CustomException of custom Exception
   */
  public CaseSamplingAdminSettingDto getCaseSamplingAdminSetting()
      throws CustomException;
  
  /**
   * This method updates CaseSamplingAdminSetting data 
 * @param caseSamplingAdminSettingDto : CaseSamplingAdminSettingDto
 * @return int: int
 * @throws CustomException : CustomException
 */
public int updateCaseSamplingAdminSetting(CaseSamplingAdminSettingDto caseSamplingAdminSettingDto) throws CustomException;
  
  

}
