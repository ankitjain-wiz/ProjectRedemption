package org.epo.cms.edfs.services.settings.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Module list response object.
 * 
 * @author dinagar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleList {
  private long moduleId;

  private String moduleName;

  private boolean defaultModule;

  public long getModuleId() {
    return moduleId;
  }

  public void setModuleId(long moduleId) {
    this.moduleId = moduleId;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public boolean isDefaultModule() {
    return defaultModule;
  }

  public void setDefaultModule(boolean defaultModule) {
    this.defaultModule = defaultModule;
  }

  @Override
  public String toString() {
    return "ModuleList [moduleId=" + moduleId + ", moduleName=" + moduleName + ", defaultModule="
        + defaultModule + "]";
  }


}
