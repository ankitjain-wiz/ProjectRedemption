package org.epo.cms.edfs.services.casesampling.dao;

import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingSelectDto;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingSelect;

/**
 * perform operations on CaseSamplingSelect Entity.
 * @author amigarg
 *
 */
public interface CaseSamplingSelectDao extends GenericDao<CaseSamplingSelect, Integer> {

  /**
   * This method is used to get the CaseSamplingSelectDto object.
   * @param userId of type String
   * @return CaseSamplingSelectDto of type CaseSamplingSelectDto
   * @throws CustomException of type Custom Exception
   */
  public CaseSamplingSelectDto getSamplingDetail(String userId) throws CustomException;

  /**
   * This method is used to update or increment NextNumToSample by one in CaseSamplingSelect Entity.
   * @param userId of type String
   * @throws CustomException of type Custom Exception
   */
  public void updateNextNumToSampleByOne(String userId) throws CustomException;

  /**
   * This method is used to update NextNumToSample in CaseSamplingSelect Entity.
   * @param userId of type String
   * @param nextNumToSample of type int
   * @param initialize of type boolean
   * @throws CustomException of type Custom Exception
   */
  public void updateNextNumToSample(String userId, int nextNumToSample , boolean initialize , int numPax) throws CustomException;
  
  /**
   * This method is used to update isSampled status in CaseSamplingSelect Entity.
   * @param userId of type String
   * @throws CustomException of type Custom Exception
   */
  public void updateSamplingStatus(String userId) throws CustomException;
 
}
