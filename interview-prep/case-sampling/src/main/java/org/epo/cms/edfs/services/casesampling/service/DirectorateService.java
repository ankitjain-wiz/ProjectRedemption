package org.epo.cms.edfs.services.casesampling.service;

import org.epo.cms.edfs.services.casesampling.pojo.DirectorateMetaDataListResponse;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.Directorate;

/**
 * Directorate Scenario in Case Sampling algorithm.
 * 
 * @author amigarg
 *
 */
public interface DirectorateService {

  /**
   * Check whether Directorate Month target is reached.
   * 
   * @param directorateId of type long
   * @return boolean as value
   * @throws CustomException of type Custom Exception
   */
  public boolean isDirectorateMonthTargetReached(long directorateId , String testMonth)
      throws CustomException;

  /**
   * This method is used to get Directorate Maxtarget.
   * 
   * @param directorateId of type long
   * @return int as value
   * @throws CustomException of type Custom Exception
   */
  public int getDirectorateMaxTarget(long directorateId) throws CustomException;
  
  
	  /**
	 * @param directorate : Directorate
	 * @return boolean: boolean
	 * @throws CustomException : CustomException
	 */
	public boolean isAllExaminerMaxTargetReached(Directorate directorate) throws CustomException;

  /**
   * check whether All Examiner reached minTarget.
   * 
   * @param directorateId of type long
   * @return boolean as value
   * @throws CustomException of type Custom Exception
   */
  public boolean isAllExaminerMinTargetReached(Directorate directorate,int numMinYearTarget)
      throws CustomException;
  
  /**
   * Returns the list of directorate's
 * @return DirectorateMetaDataListResponse: this contains the list of directorates's data
 * @throws CustomException : handles CustomException
 */
  public DirectorateMetaDataListResponse getDirectorateData() throws CustomException;
  
  



}
