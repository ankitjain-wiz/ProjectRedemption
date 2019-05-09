package org.epo.cms.edfs.services.casesampling.service;

import org.epo.cms.edfs.services.casesampling.pojo.CaseSamplingRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataResponse;
import org.epo.cms.edfs.services.common.exceptions.CustomException;

/**
 * Case Sampling Algorithm.
 * 
 * @author amigarg
 *
 */
public interface CaseSamplingService {

  /**
   * Check whether CaseSampling required to not.
   * 
   * @param userId of type String
   * @return boolean as value
   * @throws CustomException of type custom Exception
   */
  public boolean isCaseCheckRequired(String userId,String testMonth) throws CustomException;
  
  /**
 * @param caseSamplingRequest : CaseSamplingRequest
 * @return int: int
 */
public int updateCaseSamplingData(CaseSamplingRequest caseSamplingRequest);
  
  /**
 * @param directorateId :  this is directorateId of a directorate
 * @return CountMetaDataResponse:  {@link CountMetaDataResponse} containing default counts to be used in case sampling algorithm
 * @throws CustomException : handles CustomException
 */
public CountMetaDataResponse getDefaultCountData(long directorateId) throws CustomException;

/**
 * @param countMetaDataRequest : inputs {@link CountMetaDataRequest} containing default counts to be used in case sampling algorithm
 * @param directorateId : this is directorateId of a directorate
 * @throws CustomException : handles CustomException
 */
public int updateDefaultCountData(CountMetaDataRequest countMetaDataRequest,long directorateId ) throws CustomException;
  
  
  
  

}
