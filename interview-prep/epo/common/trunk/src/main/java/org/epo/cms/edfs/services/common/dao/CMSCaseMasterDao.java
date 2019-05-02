package org.epo.cms.edfs.services.common.dao;

import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.CMSCaseMaster;

public interface CMSCaseMasterDao extends GenericDao<CMSCaseMaster, Integer> {

   Integer saveCMSCaseMaster(CMSCaseMaster cMSCaseMaster);

   CMSCaseMaster findByApplicationNumber(String applicationNumber);


}
