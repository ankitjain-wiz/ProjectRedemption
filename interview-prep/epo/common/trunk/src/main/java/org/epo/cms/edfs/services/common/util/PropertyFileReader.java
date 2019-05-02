package org.epo.cms.edfs.services.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Reads tocConfig properties for getting custom property
 * 
 * @author amigarg
 * 
 */
public class PropertyFileReader {

  @Autowired
  private Environment environment;

  public String getValue(String key) {
    return environment.getProperty(key);
  }

  /**
   * @param paramString String value
   * @return LAL URL LAL Service URL
   */
  public String getInstancesForLAL(String paramString) {
    return environment.getProperty("hostForLal") + paramString + "?includeBlobs=true";
  }

  /**
   * @param paramString String value
   * @return DSL URL DSL Service URL
   */
  public String getInstancesForDSL(String paramString) {
    return environment.getProperty("hostForDsl") + paramString;
  }

  /**
   * @param paramString String value
   * @return CMS URL Host for CMS Service
   */
  public String getInstancesForCMS(String paramString) {
    return getValue(Constants.HOST_FOR_CMSSERVICE) + paramString;
  }

  public String getInstancesForUserAccount() {
    return getValue(Constants.HOST_FOR_USER_ACCOUNT);
  }

  /**
   * @param source: String source
   * @return CMS URL Host for CMS Service
   */
  public String getHostForCMS(String source) {
    return getValue(source);
  }
}
