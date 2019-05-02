package org.epo.cms.edfs.services.common.cache;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class CacheScheduler {

  @Autowired
  private DossierCache dossierCache;

  @Scheduled(cron = "${my.cron.expression}")
  public void storeDataInCache() throws CustomException {
    dossierCache.refreshUserDetailsCache();
  }
}
