package org.epo.cms.edfs.services.casesampling.dao.impl;

import org.epo.cms.edfs.services.casesampling.dao.CaseSamplingSelectDao;
import org.epo.cms.edfs.services.casesampling.dto.CaseSamplingSelectDto;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.CaseSamplingSelect;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Implementation of CaseSamplingSelectDao.
 * 
 * @author amigarg
 *
 */
@Repository
public class CaseSamplingSelectDaoImpl extends GenericDaoImpl<CaseSamplingSelect, Integer>
		implements CaseSamplingSelectDao {

	private static final String IS_SAMPLED = "isSampled";
	private static final String USER_ID = "userId";

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CaseSamplingSelectDto getSamplingDetail(String userId) throws CustomException {
		CaseSamplingSelectDto caseSamplingSelectDto = new CaseSamplingSelectDto();
		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq(USER_ID, userId));
		criteria.add(Restrictions.eq(IS_SAMPLED, false));
		CaseSamplingSelect caseSamplingSelect = (CaseSamplingSelect) criteria.uniqueResult();
		if (!ObjectUtils.isEmpty(caseSamplingSelect)) {
			caseSamplingSelectDto = samplingDetailEntityDtoMapper(caseSamplingSelect);
		}

		return caseSamplingSelectDto;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateNextNumToSampleByOne(String userId) throws CustomException {
		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq(USER_ID, userId));
		criteria.add(Restrictions.eq(IS_SAMPLED, false));
		CaseSamplingSelect caseSamplingSelect = (CaseSamplingSelect) criteria.uniqueResult();
		if (!ObjectUtils.isEmpty(caseSamplingSelect)) {
			caseSamplingSelect.setNumPlannedToSample(caseSamplingSelect.getNumPlannedToSample() + 1);
			saveOrUpdate(caseSamplingSelect);
		}
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSamplingStatus(String userId) throws CustomException {
		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq(USER_ID, userId));
		criteria.add(Restrictions.eq(IS_SAMPLED, false));
		CaseSamplingSelect caseSamplingSelect = (CaseSamplingSelect) criteria.uniqueResult();
		if (!ObjectUtils.isEmpty(caseSamplingSelect)) {
			caseSamplingSelect.setIsSampled(true);
			saveOrUpdate(caseSamplingSelect);
		}
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateNextNumToSample(String userId, int nextNumToSample, boolean initialize,int numPax) throws CustomException {

		Criteria criteria = currentSession().createCriteria(daoType);
		criteria.add(Restrictions.eq(USER_ID, userId));
		criteria.add(Restrictions.eq(IS_SAMPLED, false));
		CaseSamplingSelect caseSamplingSelect = (CaseSamplingSelect) criteria.uniqueResult();

		if (initialize) {

			if (ObjectUtils.isEmpty(caseSamplingSelect)) {
				 if(nextNumToSample>0 && nextNumToSample<=numPax)
				 {

				CaseSamplingSelect caseSamplingSelect1 = new CaseSamplingSelect();
				caseSamplingSelect1.setUserId(userId);
				caseSamplingSelect1.setNumPlannedToSample(nextNumToSample);
				caseSamplingSelect1.setIsSampled(false);
				caseSamplingSelect1.setComment("Auto");
				saveOrUpdate(caseSamplingSelect1);
				 }
			}

		} else {

			if (!ObjectUtils.isEmpty(caseSamplingSelect)) {
				caseSamplingSelect.setIsSampled(true);
				update(caseSamplingSelect);
			}

			 if(nextNumToSample>0 && nextNumToSample<=numPax)
			 {
					CaseSamplingSelect caseSamplingSelect1 = new CaseSamplingSelect();
					caseSamplingSelect1.setUserId(userId);
					caseSamplingSelect1.setNumPlannedToSample(nextNumToSample);
					caseSamplingSelect1.setIsSampled(false);
					caseSamplingSelect1.setComment("Auto");
					saveOrUpdate(caseSamplingSelect1);
			 }
		
		}

	}

	/**
	 * This method is used to map CaseSamplingSelect Entity with its DTO class.
	 * 
	 * @param caseSamplingSelect
	 *            of type CaseSamplingSelect
	 * @param caseSamplingSelectDto
	 *            of type CaseSamplingSelectDto
	 * @return CaseSamplingSelectDto as value
	 */
	private CaseSamplingSelectDto samplingDetailEntityDtoMapper(CaseSamplingSelect caseSamplingSelect) {
		CaseSamplingSelectDto caseSamplingSelectDto = new CaseSamplingSelectDto();
		caseSamplingSelectDto.setCaseSamplingSelectId(caseSamplingSelect.getCaseSamplingSelectId());
		caseSamplingSelectDto.setComment(caseSamplingSelect.getComment());
		caseSamplingSelectDto.setIsSampled(caseSamplingSelect.getIsSampled());
		caseSamplingSelectDto.setNumPlannedToSample(caseSamplingSelect.getNumPlannedToSample());
		caseSamplingSelectDto.setUserId(caseSamplingSelect.getUserId());
		return caseSamplingSelectDto;

	}

}
