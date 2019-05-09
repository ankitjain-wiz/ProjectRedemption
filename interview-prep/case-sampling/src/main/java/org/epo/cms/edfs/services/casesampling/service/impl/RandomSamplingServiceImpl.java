package org.epo.cms.edfs.services.casesampling.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingAdminSettingDao;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingExaminerStatusDao;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingSelectDao;
import org.epo.cms.edfs.services.casesampling.dao.DirectorateDao;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingAdminSettingDto;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingExaminerStatusDto;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingSelectDto;
import org.epo.cms.edfs.services.casesampling.dto.DirectorateDto;
import org.epo.cms.edfs.services.casesampling.service.DirectorateService;
import org.epo.cms.edfs.services.casesampling.service.RandomSamplingService;
import org.epo.cms.edfs.services.casesampling.util.DateTime;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.dossierpersistence.entity.Directorate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Random Sampling Implementation.
 * 
 * @author amigarg
 *
 */
@Service
public class RandomSamplingServiceImpl implements RandomSamplingService {

  @Autowired
  private CaseSamplingAdminSettingDao caseSamplingAdminSettingDao;

  @Autowired
  private CaseSamplingExaminerStatusDao caseSamplingExaminerStatusDao;

  @Autowired
  private CaseSamplingSelectDao caseSamplingSelectDao;

  @Autowired
  private DirectorateDao directorateDao;

  @Autowired
  private DirectorateService directorateSamplingService;

  @Autowired
  private DateTime dateTime;
  
  @Autowired
	private ResponseValidator responseValidator;

  private static final Logger LOGGER = LogManager.getLogger();

  /**
   * {@inheritDoc}.
   */
  @Override
  public void generateNextNumToSampleForExaminer(String userId,boolean maxtargetFlag) throws CustomException {
    int numSubmitted;
    int numPax ;
    int numSampled ;
    Directorate directorate ;

    int year = dateTime.getYear();
    CaseSamplingExaminerStatusDto examinerStatusDto =
        caseSamplingExaminerStatusDao.getExaminerStatus(userId, year);
    if (!ObjectUtils.isEmpty(examinerStatusDto)) {
      directorate = examinerStatusDto.getDirectorate();
      numSubmitted = examinerStatusDto.getNumSubmitted();
      numPax = examinerStatusDto.getNumPax();
      numSampled = examinerStatusDto.getNumSampled();
    } else {
    	throw new CustomException(responseValidator.getErrorResponse("0", "Examiner status setting is not available"));
    	
    }

    randomSample(userId, directorate, numSubmitted, numPax, numSampled,maxtargetFlag);
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void generateNextNumToSampleForExaminer(String userId, Directorate directorate)
      throws CustomException {
    int numSubmitted ;
    int numPax ;
    int numSampled ;
    int year = dateTime.getYear();
    Directorate directorateNew;
    CaseSamplingExaminerStatusDto examinerStatusDto =
        caseSamplingExaminerStatusDao.getExaminerStatus(userId, year);
    if (!ObjectUtils.isEmpty(examinerStatusDto) && (!ObjectUtils.isEmpty(directorate))) {
      directorateNew = examinerStatusDto.getDirectorate();
      numSubmitted = examinerStatusDto.getNumSubmitted();
      numPax = examinerStatusDto.getNumPax();
      numSampled = examinerStatusDto.getNumSampled();
    } else {
    	throw new CustomException(responseValidator.getErrorResponse("0", "Examiner status setting is not available"));
      
    }

    randomSample(userId, directorateNew, numSubmitted, numPax, numSampled,false);
  }

  /**
 * @param userId : String
 * @param directorate : Directorate
 * @param numSubmitted : int
 * @param numPax : int
 * @param numSampled : int
 * @param targetFlag : boolean
 * @throws CustomException : CustomException
 */
private void randomSample(String userId, Directorate directorate, int numSubmitted, int numPax,
      int numSampled,boolean targetFlag) throws CustomException {
    int numMinYearTarget ;
    int numMaxYearTarget ;
    int numMustSampled ;
    int maxTarget ;
    int numPlannedToSample=0 ;
    int minRange ;
    int maxRange ;
    int year = dateTime.getYear();
    long directorateId ;
    CaseSamplingAdminSettingDto adminSettingDto =
        caseSamplingAdminSettingDao.getCaseSamplingAdminSetting();
    if (!ObjectUtils.isEmpty(adminSettingDto)) {
      numMinYearTarget = adminSettingDto.getNumMinYearTarget();
      numMaxYearTarget = adminSettingDto.getNumMaxYearTarget();
      numMustSampled = adminSettingDto.getNumMustSample();

    } else {
      
    	throw new CustomException(responseValidator.getErrorResponse("0", "CaseSampling Admin setting is not available"));
    	
    }
   
    
    
    boolean examinersReachedTarget =
        directorateSamplingService.isAllExaminerMinTargetReached(directorate, numMinYearTarget);
    if (numPax >= 3) {
      maxTarget = numMinYearTarget;
      if (examinersReachedTarget == true) {
        maxTarget = numMaxYearTarget;
      }
      
      
      //@ankit
      if(numMustSampled<(maxTarget-numSampled))
      {
      	numMustSampled=maxTarget-numSampled;
      }
      
      if(!ObjectUtils.isEmpty(directorate) && targetFlag){
        directorateId = directorate.getDirectorateId();
        directorateDao.updateDirectorateMaxtarget(directorateId,year,maxTarget);
      }
      
      
      //ankit
      if(maxTarget - numSampled>0)
      {
    	   minRange = numSubmitted + 1;
    	 
      maxRange = (int) (numSubmitted + (numPax - numSubmitted) / (maxTarget - numSampled));
      if (maxRange == numPax) {
        maxRange = maxRange - numMustSampled;
        if (maxRange <= numMustSampled) {
          maxRange = numSubmitted + 1;
        }
      }
      if (((numPax - numSubmitted) <= numMustSampled) && (numSampled < maxTarget)) {
        numPlannedToSample = numSubmitted + 1;
      } else {
        if (maxRange >= minRange) {
        	
       
        		numPlannedToSample = generateRandomNumber(minRange, maxRange);
        	
          
        } else {
          LOGGER.debug("Upper ranger is less than to lower range");
        }
      }
      }else{
    	  LOGGER.debug("maxTarget - numSampled = 0 , so random number is generated by algo , numPlannedToSample=0");
      }

     
     
      
    	  caseSamplingSelectDao.updateNextNumToSample(userId, numPlannedToSample,targetFlag,numPax);

      
      
      LOGGER.debug("generateNextNumToSample : {}", numPlannedToSample);
    } else {
      LOGGER.debug("NumPax of examiner is less than 3");
    }

  }

  /**
   * {@inheritDoc}.
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void generateNextNumToSampleForAll() throws CustomException {

    String userId ;
    int year = dateTime.getYear();
    List<Long> idList = directorateDao.getDirectorateIds();
    if (!CollectionUtils.isEmpty(idList)) {
      for (long directorateId : idList) {
        List<CaseSamplingExaminerStatusDto> examinerDto =
            new ArrayList<>();
        DirectorateDto directorateDto =
            directorateDao.getDirectorateAllExaminers(directorateId, year);
        if (!ObjectUtils.isEmpty(directorateDto)) {
          examinerDto = directorateDto.getCaseSamplingExaminerStatus();
        }

        if (!CollectionUtils.isEmpty(examinerDto)) {
          for (CaseSamplingExaminerStatusDto examiner : examinerDto) {
            userId = examiner.getUserId();
            generateNextNumToSampleForExaminer(userId,true);
          }
        }
      }
    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void generateNextNumToSampleForDirectorate(Directorate directorate)
      throws CustomException {

    String userId ;
    long directorateId = 0;
    int year = dateTime.getYear();
    if (!ObjectUtils.isEmpty(directorate)) {
      directorateId = directorate.getDirectorateId();
    }
    List<CaseSamplingExaminerStatusDto> examinerDto =
        new ArrayList<>();
    DirectorateDto directorateDto = directorateDao.getDirectorateAllExaminers(directorateId, year);
    if (!ObjectUtils.isEmpty(directorateDto)) {
      examinerDto = directorateDto.getCaseSamplingExaminerStatus();
    }

    if (!CollectionUtils.isEmpty(examinerDto)) {
      for (CaseSamplingExaminerStatusDto examiner : examinerDto) {
        userId = examiner.getUserId();
        generateNextNumToSampleForExaminer(userId, directorate);
      }
    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void generateIncrementalRandomNumber(String userId) throws CustomException {

    int numPlannedToSample ;
    CaseSamplingSelectDto caseSamplingSelectDto = caseSamplingSelectDao.getSamplingDetail(userId);
    numPlannedToSample = caseSamplingSelectDto.getNumPlannedToSample();

    numPlannedToSample = numPlannedToSample + 1;

    caseSamplingSelectDao.updateNextNumToSample(userId, numPlannedToSample,true,0);
    LOGGER.debug("generateIncrementalRandomNumber: {}", numPlannedToSample);
  }

  /**
   * This method is used to generate Random Number in the given range.
   * 
   * @param min of type int
   * @param max of type int
   * @return int as value
   */
  private int generateRandomNumber(int min, int max) {
    int randomNum ;
    Random rand = new Random();
    randomNum = rand.nextInt((max - min) + 1) + min;
    return randomNum;
  }

}
