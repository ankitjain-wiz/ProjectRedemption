package org.epo.cms.edfs.services.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ExceptionHandlerBean;
import org.epo.cms.edfs.services.common.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JCSCacheFactoryBean {

  private  final  Map<String, CacheAccess<Object, Object>> caches = new ConcurrentHashMap<>();

  private static final Logger LOGGER = LoggerFactory.getLogger(JCSCacheFactoryBean.class);

  private final String configLocation;
  private String region;

  public JCSCacheFactoryBean(String configLocation) {
    this.configLocation = configLocation;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public Object getObject() throws CustomException {
    try {
      LOGGER.debug("Getting caching object");
      String cacheRegionKey =
          new StringBuffer(configLocation).append(".").append(region).toString();
      CacheAccess<Object, Object> cache;
      if (caches.containsKey(cacheRegionKey)) {
        cache = caches.get(cacheRegionKey);
      } else {
        JCS.setConfigFilename(configLocation);
        cache = JCS.getInstance(region);
        caches.put(cacheRegionKey, cache);
      }

      return cache;
    } catch (CacheException e) {
      LOGGER.error("CacheException occurred in JCSCacheFactoryBean : {}", e.getMessage(), e);
      ExceptionHandlerBean exBean = new ExceptionHandlerBean(Constants.FILE_NOT_FOUND_EXCEPTION,
          Constants.FILE_NOT_FOUND_EXCEPTION,
          "Error while getting Cache object for region :- " + region);
      throw new CustomException(exBean);
    }
  }
}
