package org.epo.cms.edfs.services.common.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.epo.cms.edfs.services.common.dao.CMSCaseMasterDao;
import org.epo.cms.edfs.services.common.exceptions.DAOException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.CMSCaseMaster;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CMSCaseMasterDaoImpl extends GenericDaoImpl<CMSCaseMaster, Integer>
    implements CMSCaseMasterDao {
/**
 * {@inheritDoc}
 */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Integer saveCMSCaseMaster(CMSCaseMaster cMSCaseMaster) throws DAOException {
    currentSession().saveOrUpdate(cMSCaseMaster);
    return cMSCaseMaster.getCmsCaseId();
  }

/**
 * {@inheritDoc}
 */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CMSCaseMaster findByApplicationNumber(String applicationNumber) throws DAOException {

    Criteria criteria = currentSession().createCriteria(daoType, "cMSCaseMaster");
    criteria.setFetchMode("cMSCaseMaster.application", FetchMode.JOIN);
    criteria.createAlias("cMSCaseMaster.application", "app")
        .add(Restrictions.eq("app.applicationNumber", applicationNumber));

    CMSCaseMaster cMSCaseMaster = null;
    List<CMSCaseMaster> cMSCaseMasterList = (List<CMSCaseMaster>) criteria.list();

    if (CollectionUtils.isNotEmpty(cMSCaseMasterList)) {
      cMSCaseMaster = cMSCaseMasterList.get(0);
    }


    return cMSCaseMaster;
  }

}
