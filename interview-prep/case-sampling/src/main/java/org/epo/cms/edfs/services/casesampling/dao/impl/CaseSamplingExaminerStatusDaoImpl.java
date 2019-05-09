package org.epo.cms.edfs.services.casesampling.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingExaminerStatusDao;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingExaminerStatusDto;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingExaminerStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Implementation of CaseSamplingExaminerStatusDao.
 * 
 * @author amigarg
 *
 */
@Repository
public class CaseSamplingExaminerStatusDaoImpl extends
    GenericDaoImpl<CaseSamplingExaminerStatus, Integer> implements CaseSamplingExaminerStatusDao {

  private static final String USER_ID = "userId";



/**
   * {@inheritDoc}.
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CaseSamplingExaminerStatusDto getExaminerStatus(String userId, int year)
      throws CustomException {
    CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto = null;
      

    Criteria criteria = currentSession().createCriteria(daoType);
    criteria.add(Restrictions.eq(USER_ID, userId));
    criteria.add(Restrictions.eq("year", year));

    CaseSamplingExaminerStatus caseSamplingExaminerStatus =
        (CaseSamplingExaminerStatus) criteria.uniqueResult();

    if (!ObjectUtils.isEmpty(caseSamplingExaminerStatus)) {
      caseSamplingExaminerStatus.getDirectorate();
      caseSamplingExaminerStatusDto =
          examinerStatusEntityDtoMapper(caseSamplingExaminerStatus);
    }

    return caseSamplingExaminerStatusDto;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CaseSamplingExaminerStatusDto updateNumSubmitted(String userId, int year) throws CustomException {
    
	CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto = null;
	Criteria criteria = currentSession().createCriteria(daoType);
    criteria.add(Restrictions.eq(USER_ID, userId));
    criteria.add(Restrictions.eq("year", year));
    CaseSamplingExaminerStatus caseSamplingExaminerStatus =
        (CaseSamplingExaminerStatus) criteria.uniqueResult();
    if (!ObjectUtils.isEmpty(caseSamplingExaminerStatus)) {
        caseSamplingExaminerStatus.setNumSubmitted(caseSamplingExaminerStatus.getNumSubmitted() + 1);
        saveOrUpdate(caseSamplingExaminerStatus);
        caseSamplingExaminerStatusDto =
            examinerStatusEntityDtoMapper(caseSamplingExaminerStatus);
      }

    return caseSamplingExaminerStatusDto;

  }

  /**
   * {@inheritDoc}.
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CaseSamplingExaminerStatusDto updateNumSampled(String userId, int year) throws CustomException {
	CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto = null;
	Criteria criteria = currentSession().createCriteria(daoType);
    criteria.add(Restrictions.eq(USER_ID, userId));
    criteria.add(Restrictions.eq("year", year));
    CaseSamplingExaminerStatus caseSamplingExaminerStatus =
        (CaseSamplingExaminerStatus) criteria.uniqueResult();
    if (!ObjectUtils.isEmpty(caseSamplingExaminerStatus)) {
        caseSamplingExaminerStatus.setNumSampled(caseSamplingExaminerStatus.getNumSampled() + 1);
        saveOrUpdate(caseSamplingExaminerStatus);
        caseSamplingExaminerStatus.getDirectorate();
        caseSamplingExaminerStatusDto =
            examinerStatusEntityDtoMapper(caseSamplingExaminerStatus);
      }


    return caseSamplingExaminerStatusDto;
  }


  /**
   * This method is used to map CaseSamplingExaminerStatus Entity with its DTO class.
   * 
   * @param examinerStatus of type CaseSamplingExaminerStatus
   * @param examinerStatusDto of type CaseSamplingExaminerStatusDto
   * @return CaseSamplingExaminerStatusDto as value
   */
  private CaseSamplingExaminerStatusDto examinerStatusEntityDtoMapper(CaseSamplingExaminerStatus examinerStatus) {
    CaseSamplingExaminerStatusDto examinerStatusDto = new CaseSamplingExaminerStatusDto();
    examinerStatusDto
        .setCaseSamplingExaminerStatusId(examinerStatus.getCaseSamplingExaminerStatusId());
    examinerStatusDto.setDirectorate(examinerStatus.getDirectorate());
    examinerStatusDto.setNumPax(examinerStatus.getNumPAX());
    examinerStatusDto.setNumSampled(examinerStatus.getNumSampled());
    examinerStatusDto.setNumSubmitted(examinerStatus.getNumSubmitted());
    examinerStatusDto.setUserId(examinerStatus.getUserId());
    examinerStatusDto.setYear(examinerStatus.getYear());
    examinerStatusDto.setDirectorate(examinerStatus.getDirectorate());
    return examinerStatusDto;
  }
  
  
  
  /**
   * {@inheritDoc}.
   */
	@Override
	public List<CaseSamplingExaminerStatusDto> getExaminersStatus(long directorateId,int year)
			throws CustomException {

		CaseSamplingExaminerStatusDto caseSamplingExaminerStatusDto ;
		List<CaseSamplingExaminerStatusDto> caseSamplingExaminerStatusDtoList = new ArrayList<>();

		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq("directorate.directorateId", directorateId));
		criteria.add(Restrictions.eq("year", year));

		List<CaseSamplingExaminerStatus> caseSamplingExaminerStatusList = criteria.list();

		if (CollectionUtils.isNotEmpty(caseSamplingExaminerStatusList)) {
			for (CaseSamplingExaminerStatus caseSamplingExaminerStatus : caseSamplingExaminerStatusList) {
				caseSamplingExaminerStatus.getDirectorate();
				caseSamplingExaminerStatusDto = examinerStatusEntityDtoMapper(caseSamplingExaminerStatus);

				caseSamplingExaminerStatusDtoList.add(caseSamplingExaminerStatusDto);
			}

		}

		return caseSamplingExaminerStatusDtoList;

	}

}
