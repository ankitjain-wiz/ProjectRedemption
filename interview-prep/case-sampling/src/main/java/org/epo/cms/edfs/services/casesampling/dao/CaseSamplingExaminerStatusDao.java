package org.epo.cms.edfs.services.casesampling.dao;

import java.util.List;

import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingExaminerStatusDto;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingExaminerStatus;

/**
 * perform operations on CaseSamplingExaminerStatus Entity.
 * 
 * @author amigarg
 *
 */
public interface CaseSamplingExaminerStatusDao
    extends GenericDao<CaseSamplingExaminerStatus, Integer> {

  /**
   * This method is used to get the CaseSamplingExaminerStatusDto object.
   * 
   * @param userId of type String
   * @param year of type int
   * @return CaseSamplingExaminerStatusDto of type CaseSamplingExaminerStatusDto
   * @throws CustomException of type Custom Exception
   */
  public CaseSamplingExaminerStatusDto getExaminerStatus(String userId, int year)
      throws CustomException;

  /**
   * This method persist the NumSubmitted in CaseSamplingExaminerStatus Entity.
   * 
   * @param userId of type String
   * @param year of type int
   * @throws CustomException of type Custom Exception
   */
  public CaseSamplingExaminerStatusDto updateNumSubmitted(String userId, int year) throws CustomException;

  /**
   * This method persist the NumSampled in CaseSamplingExaminerStatus Entity.
   * 
   * @param userId of type String
   * @param year of type int
   * @throws CustomException of type Custom Exception
   */
  public CaseSamplingExaminerStatusDto updateNumSampled(String userId, int year) throws CustomException;
  
  /**
   * This method returns data(numPax,numSampled,numSubmitted) related to every user in given directorate
 * @param directorateId : long
 * @return List<CaseSamplingExaminerStatusDto> : List<CaseSamplingExaminerStatusDto>
 * @param year : int
 * @throws CustomException : CustomException
 */
public List<CaseSamplingExaminerStatusDto> getExaminersStatus(long directorateId,int year)
	      throws CustomException;

}
