package org.epo.cms.edfs.services.casesampling.dao;

import org.epo.cms.edfs.services.casesampling.dto.DirectorateWorkloadSettingDto;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.DirectorateWorkloadSetting;

/**
 * perform operations on Directorate Entity.
 * @author amigarg
 *
 */
public interface DirectorateWorkloadSettingDao
    extends GenericDao<DirectorateWorkloadSetting, Integer> {

  /**
   * This method is used to map WorkloadSetting Entity with its DTO class.
   * @param workloadSetting of type DirectorateWorkloadSetting
   * @param workloadSettingDto of type DirectorateWorkloadSettingDto
   * @return DirectorateWorkloadSettingDto as value
   */
  public DirectorateWorkloadSettingDto workloadSettingEntityDtoMapper(
      DirectorateWorkloadSetting workloadSetting);
  
  /**
 * This method Updates DirectorateWorkloadSetting
 * @param directorateWorkloadSettingDto : DirectorateWorkloadSettingDto
 * @param year : int
 * @return int: int
 * @throws CustomException : CustomException
 */
public int updateDirectorateWorkloadSetting(DirectorateWorkloadSettingDto directorateWorkloadSettingDto,long directorateId,int year) throws CustomException;

/**
 * This methods compare the sum of workload counts with the numMinYearTarget*numExamInDir for validation of total sampling in on year 
 * @param directorateId : long
 * @param numMinYearTarget : int
 * @param sum : int
 * @param year : int
 * @return int:int
 * @throws CustomException : CustomException
 */
public int checkNumExamInDirValidation(long directorateId ,int numMinYearTarget , int sum,int year)throws CustomException;

}
