package org.epo.cms.edfs.services.casesampling.dao;

import java.util.List;

import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingExaminerStatusDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateWorkloadSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.CaseSamplingRequest;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.Directorate;

/**
 * perform operations on Directorate Entity.
 * @author amigarg
 *
 */
public interface DirectorateDao extends GenericDao<Directorate, Integer> {

  /**
   * This method is used to get the DirectorateWorkloadSettingDto object.
   * @param directorateId of type long
   * @param year of type int
   * @return DirectorateWorkloadSettingDto of type 
   * @throws CustomException of type  Custom Exception
   */
  public DirectorateWorkloadSettingDto getDirectorateWorkloadSetting(long directorateId, int year)
      throws CustomException;

  /**
   * This method is used to get the List of Directorate ids from  Directorate entity.
   * @return List of type Long
   * @throws CustomException of type Custom Exception
   */
  public List<Long> getDirectorateIds() throws CustomException;

  /**
   * This method is used to get the List of CaseSamplingExaminerStatus in given Directorate.
   * @param directorateId of type long
   * @param year od type int
   * @return List of type CaseSamplingExaminerStatusDto
   * @throws CustomException of type Custom Exception
   */
  public DirectorateDto getDirectorateAllExaminers(long directorateId,
      int year) throws CustomException;

  /**
   * This method is used to update Directorate MaxTarget.
   * @param directorateId of type long
   * @param year of type int
   * @param maxTarget of type int
   * @throws CustomException of type Custom Exception
   */
  public Directorate updateDirectorateMaxtarget(long directorateId, int year, int maxTarget)
      throws CustomException;
  
  /**
   * This method is used to increment Directorate Workload count.
   * @param directorateId of type long
   * @param month of type String
   * @param year of type int
   * @throws CustomException of type Custom Exception
   */
  public void incrementDirectorateWorkloadCount(long directorateId, String month, int year)
      throws CustomException;

  /**
   * This method is used to map DirectorateSetting Entity with DTO class.
   * @param directorate of type List
   * @param directorateDto of type List
   * @return list of type DirectorateDto
   */
  public DirectorateDto directorateEntityDtoMapper(Directorate directorate,
	       List<CaseSamplingExaminerStatusDto> caseSamplingExaminerStatus, DirectorateWorkloadSettingDto workloadSettingDto);

/**
 * @param directorate : Directorate
 * @param caseSamplingRequest : CaseSamplingRequest
 * @return int: int
 */
public int updateDirectorate(Directorate directorate,CaseSamplingRequest caseSamplingRequest);

/**
 * @param directorateName:String
 * @return Directorate: Directorate
 */
public Directorate isDirectorateExist(String directorateName);

/**
 * this method returns the list of directorates
 * @return List<DirectorateDto>:List<DirectorateDto>
 * @throws CustomException:CustomException
 */
public List<DirectorateDto> getDirectoratesList() throws CustomException;



/**
 * This methods return the directorate data based on id passed
 * @param directorateId : long
 * @return : Directorate
 */
public Directorate isDirectorateExist(long directorateId);





}
