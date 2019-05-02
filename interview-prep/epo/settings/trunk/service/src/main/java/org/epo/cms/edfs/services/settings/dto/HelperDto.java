package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * Dto for Helper entity.
 * 
 * @author bkumar
 * 
 */
public class HelperDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private long helperId;
  private String helperName;
  private String moduleOrWorkspace;
  private Set<HelperGlobalSettingMappingDto> helperGlobalSettingMapping;
  private Set<HelperUserSettingMappingDto> helperUserSettingMapping;


  public long getHelperId() {
    return this.helperId;
  }

  public void setHelperId(long helperId) {
    this.helperId = helperId;
  }

  public String getHelperName() {
    return this.helperName;
  }

  public void setHelperName(String helperName) {
    this.helperName = helperName;
  }

  public String getModuleOrWorkspace() {
    return this.moduleOrWorkspace;
  }

  public void setModuleOrWorkspace(String moduleOrWorkspace) {
    this.moduleOrWorkspace = moduleOrWorkspace;
  }

  public Set<HelperGlobalSettingMappingDto> getHelperGlobalSettingMapping() {
    return helperGlobalSettingMapping;
  }

  public void setHelperGlobalSettingMapping(
      Set<HelperGlobalSettingMappingDto> helperGlobalSettingMapping) {
    this.helperGlobalSettingMapping = helperGlobalSettingMapping;
  }

  public Set<HelperUserSettingMappingDto> getHelperUserSettingMapping() {
    return helperUserSettingMapping;
  }

  public void setHelperUserSettingMapping(
      Set<HelperUserSettingMappingDto> helperUserSettingMapping) {
    this.helperUserSettingMapping = helperUserSettingMapping;
  }



}
