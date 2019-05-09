package org.epo.cms.edfs.services.casesampling.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.epo.cms.edfs.services.casesampling.dao.DirectorateDao;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateWorkloadSettingDto;
import org.epo.cms.edfs.services.casesampling.pojo.DirectorateMetaDataListResponse;
import org.epo.cms.edfs.services.casesampling.pojo.DirectorateMetaDataResponse;
import org.epo.cms.edfs.services.casesampling.pojo.DirectorateMonthCount;
import org.epo.cms.edfs.services.casesampling.service.DirectorateService;
import org.epo.cms.edfs.services.casesampling.util.DateTime;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingExaminerStatus;
import org.epo.cms.edfs.services.dossierpersistence.entity.Directorate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * DirectorateService service Implementation.
 * 
 * @author amigarg
 *
 */
@Service
public class DirectorateServiceImpl implements DirectorateService {

  @Autowired
  private DirectorateDao directorateDao;

  @Autowired
  private DateTime dateTime;

  /**
   * {@inheritDoc}.
   */
  @Override
  public boolean isDirectorateMonthTargetReached(long directorateId,String testMonth)
      throws CustomException {
    DirectorateMonthCount monthCount;
    boolean targetReached = false;
    int year = dateTime.getYear();
    
    //TestChangeForReport
    /*String month = dateTime.getMonth();*/
    String month = testMonth;
    
    DirectorateWorkloadSettingDto workloadSettingDto =
        directorateDao.getDirectorateWorkloadSetting(directorateId, year);
    monthCount = getDirectorateCurrentMonthCount(workloadSettingDto, month);
    if (monthCount.getActualCount() >= monthCount.getWorkloadCount()) {
      targetReached = true;
    }
    return targetReached;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public int getDirectorateMaxTarget(long directorateId) throws CustomException {
    int year = dateTime.getYear();
    DirectorateWorkloadSettingDto workloadSettingDto =
        directorateDao.getDirectorateWorkloadSetting(directorateId, year);
    return workloadSettingDto.getMaxTarget();
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public boolean isAllExaminerMaxTargetReached(Directorate directorate)
      throws CustomException {
    Boolean flag = true;
    int maxTarget = 0;
    List<CaseSamplingExaminerStatus> examinerStatus = null;
    if(!ObjectUtils.isEmpty(directorate)){
    	maxTarget = directorate.getDirectorateWorkloadSettings().get(0).getMaxTarget();
    	examinerStatus = directorate.getCaseSamplingExaminerStatus();
    }
           
    if(!CollectionUtils.isEmpty(examinerStatus)) {
	    for (CaseSamplingExaminerStatus c : examinerStatus) {
	      if (c.getNumPAX()>=3  && c.getNumSampled() != maxTarget) {
	        flag = false;
	        break;
	      }
	    }
    }
    return flag;
  }
  
  /**
   * {@inheritDoc}.
   */
  @Override
  public boolean isAllExaminerMinTargetReached(Directorate directorate,int numMinYearTarget)
      throws CustomException {
    Boolean flag = true;
    List<CaseSamplingExaminerStatus> examinerStatus = null;
    if(!ObjectUtils.isEmpty(directorate)){
    	examinerStatus = directorate.getCaseSamplingExaminerStatus();
    }
           
    if(!CollectionUtils.isEmpty(examinerStatus)) {
	    for (CaseSamplingExaminerStatus c : examinerStatus) {
	      if (c.getNumPAX()>=3 && c.getNumSampled() < numMinYearTarget) {
	        flag = false;
	        break;
	      }
	    }
    }
    return flag;
  }

  /**
   * This method is used to get Directorate Current month count.
   * 
   * @param workloadSettingDto of type DirectorateWorkloadSettingDto
   * @param month of type String
   * @return DirectorateMonthCount as value
   */
  private DirectorateMonthCount getDirectorateCurrentMonthCount(
      DirectorateWorkloadSettingDto workloadSettingDto, String month) {
    DirectorateMonthCount count = new DirectorateMonthCount();
    switch (month) {
      case "JANUARY":
        count.setActualCount(workloadSettingDto.getJanActualCount());
        count.setWorkloadCount(workloadSettingDto.getJanWorkLoadCount());
        break;
      case "FEBRUARY":
        count.setActualCount(workloadSettingDto.getFebActualCount());
        count.setWorkloadCount(workloadSettingDto.getFebWorkLoadCount());
        break;
      case "MARCH":
        count.setActualCount(workloadSettingDto.getMarActualCount());
        count.setWorkloadCount(workloadSettingDto.getMarWorkLoadCount());
        break;
      case "APRIL":
        count.setActualCount(workloadSettingDto.getAprActualCount());
        count.setWorkloadCount(workloadSettingDto.getAprWorkLoadCount());
        break;
      case "MAY":
        count.setActualCount(workloadSettingDto.getMayActualCount());
        count.setWorkloadCount(workloadSettingDto.getMayWorkLoadCount());
        break;
      case "JUNE":
        count.setActualCount(workloadSettingDto.getJunActualCount());
        count.setWorkloadCount(workloadSettingDto.getJunWorkLoadCount());
        break;
      case "JULY":
        count.setActualCount(workloadSettingDto.getJulActualCount());
        count.setWorkloadCount(workloadSettingDto.getJulWorkLoadCount());
        break;
      case "AUGUST":
        count.setActualCount(workloadSettingDto.getAugActualCount());
        count.setWorkloadCount(workloadSettingDto.getAugWorkLoadCount());
        break;
      case "SEPTEMBER":
        count.setActualCount(workloadSettingDto.getSepActualCount());
        count.setWorkloadCount(workloadSettingDto.getSepWorkLoadCount());
        break;
      case "OCTOBER":
        count.setActualCount(workloadSettingDto.getOctActualCount());
        count.setWorkloadCount(workloadSettingDto.getOctWorkLoadCount());
        break;
      case "NOVEMBER":
        count.setActualCount(workloadSettingDto.getNovActualCount());
        count.setWorkloadCount(workloadSettingDto.getNovWorkLoadCount());
        break;
      case "DECEMBER":
        count.setActualCount(workloadSettingDto.getDecActualCount());
        count.setWorkloadCount(workloadSettingDto.getDecWorkLoadCount());
        break;
      default:
        break;

    }
    return count;
  }

  
  /**
   * {@inheritDoc}.
   */
@Override
@Transactional(propagation = Propagation.REQUIRES_NEW)
public DirectorateMetaDataListResponse getDirectorateData() throws CustomException {
	
	List<DirectorateDto> directorateDtos=directorateDao.getDirectoratesList();
	List<DirectorateMetaDataResponse> dataResponses=new ArrayList<>();
	for(DirectorateDto directorateDto:directorateDtos)
	{
		DirectorateMetaDataResponse directorateMetaDataResponse=new DirectorateMetaDataResponse();
		directorateMetaDataResponse.setDirectorateId(directorateDto.getDirectorateId());
		directorateMetaDataResponse.setDirectorId(directorateDto.getDirectorId());
		directorateMetaDataResponse.setDirectorateName(directorateDto.getDirectorateName());
		dataResponses.add(directorateMetaDataResponse);
	}
	DirectorateMetaDataListResponse directorateMetaDataListResponse=new DirectorateMetaDataListResponse();
	directorateMetaDataListResponse.setDirectorateMetaDataResponse(dataResponses);
	
	return directorateMetaDataListResponse;
	
}





}
