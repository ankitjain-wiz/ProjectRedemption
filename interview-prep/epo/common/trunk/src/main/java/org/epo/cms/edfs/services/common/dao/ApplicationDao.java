package org.epo.cms.edfs.services.common.dao;

import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.Application;


public interface ApplicationDao extends GenericDao<Application, Integer> {

   Application findByApplicationNumber(String appNo);

   Integer saveApplication(Application application);

   Application getApplicationWithCMSCaseID(String applicationNumber);

}
