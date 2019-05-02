package org.epo.cms.edfs.services.settings.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Module class response object.
 * 
 * @author dinagar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Module {

  private boolean resetToDefault;

  private String sectionName;

  private List<ModuleList> moduleList;

  public List<ModuleList> getModuleList() {
    return moduleList;
  }

  public void setModuleList(List<ModuleList> moduleList) {
    this.moduleList = moduleList;
  }

  public boolean isResetToDefault() {
    return resetToDefault;
  }

  public void setResetToDefault(boolean resetToDefault) {
    this.resetToDefault = resetToDefault;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  @Override
  public String toString() {
    return "Module [resetToDefault=" + resetToDefault + ", sectionName=" + sectionName
        + ", moduleList=" + moduleList + "]";
  }

}
