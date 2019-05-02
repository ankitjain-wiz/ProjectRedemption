package org.epo.cms.edfs.services.common.dao.impl;

import org.epo.cms.edfs.services.common.dao.ApplicationDao;
import org.epo.cms.edfs.services.common.exceptions.DAOException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.Application;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ApplicationDaoImpl extends GenericDaoImpl<Application, Integer>
    implements ApplicationDao {
  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Application findByApplicationNumber(String applicationNo) throws DAOException {
    return (Application) currentSession().createCriteria(daoType)
        .add(Restrictions.eq("applicationNumber", applicationNo)).uniqueResult();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Integer saveApplication(Application application) throws DAOException {
    int applicationId=-1;
    Application existingApplication = findByApplicationNumber(application.getApplicationNumber());
    if (null == existingApplication) {
      currentSession().saveOrUpdate(application);
      applicationId = application.getApplicationId(); 
    }
    return applicationId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Application getApplicationWithCMSCaseID(String applicationNumber) throws DAOException {

    Criteria criteria = currentSession().createCriteria(daoType, "appllication")
        .add(Restrictions.eq("applicationNumber", applicationNumber));
    criteria.setFetchMode("appllication.cmsCaseMaster", FetchMode.JOIN);
    criteria.createAlias("appllication.cmsCaseMaster", "cms");
    return (Application) criteria.uniqueResult();
  }
}
