package org.epo.cms.edfs.services.casesampling.service;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.Directorate;

/**
 * Random Sampling logic for Case Sampling algorithm.
 * 
 * @author amigarg
 *
 */
public interface RandomSamplingService {


  /**
 * This method is used to generate Next Number to Sample for a Examiner.
 * @param userId : String
 * @param maxtargetFlag : boolean
 * @throws CustomException : CustomException
 */
public void generateNextNumToSampleForExaminer(String userId,boolean maxtargetFlag) throws CustomException;
  

  /**
   * Overloaded method of generateNextNumToSampleForExaminer.
 * @param userId : String
 * @param directorate : Directorate
 * @throws CustomException : CustomException
 */
public void generateNextNumToSampleForExaminer(String userId,Directorate directorate) throws CustomException;

  /**
   * This method is used to generate Next number to sample for all the examiners of all Directorate.
   * 
   * @throws CustomException :  Custom Exception
   */
  public void generateNextNumToSampleForAll() throws CustomException;
  
  /**
   * This method is used to generate Next number to sample for all the examiners of given Directorate.
   * 
   * @throws CustomException : Custom Exception
   */
  public void generateNextNumToSampleForDirectorate(Directorate directorate) throws CustomException;

  /**
   * This method is used to Generate the next Random number for a examiner.
   * 
   * @param userId : String
   * @throws CustomException :  Custom Exception
   */
  public void generateIncrementalRandomNumber(String userId) throws CustomException;


}
