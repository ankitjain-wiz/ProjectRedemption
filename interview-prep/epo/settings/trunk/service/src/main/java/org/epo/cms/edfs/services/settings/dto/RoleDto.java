package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;
import java.util.Set;



/**
 * Dto for Role entity.
 * 
 * @author bkumar
 *
 */
public class RoleDto implements Serializable {

  private static final long serialVersionUID = 1L;

 
  private long roleId;

  private String roleName;

  private String description;

  private Set<GlobalProfileDto> globalProfile;

  private Set<ModuleDto> module;

  private Set<WorkSpaceDto> workSpace;

  private Set<HelperGlobalSettingMappingDto> helperGlobalSettingMapping;

  private Set<PrintGlobalSettingMappingDto> printGlobalSettingMapping;

  private Set<PersonalTagGlobalSettingMappingDto> personalTagGlobalSettingMapping;


  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<GlobalProfileDto> getGlobalProfile() {
    return globalProfile;
  }

  public void setGlobalProfile(Set<GlobalProfileDto> globalProfile) {
    this.globalProfile = globalProfile;
  }

  public Set<ModuleDto> getModule() {
    return module;
  }

  public void setModule(Set<ModuleDto> module) {
    this.module = module;
  }

  public Set<WorkSpaceDto> getWorkSpace() {
    return workSpace;
  }

  public void setWorkSpace(Set<WorkSpaceDto> workSpace) {
    this.workSpace = workSpace;
  }

  public Set<HelperGlobalSettingMappingDto> getHelperGlobalSettingMapping() {
    return helperGlobalSettingMapping;
  }

  public void setHelperGlobalSettingMapping(
      Set<HelperGlobalSettingMappingDto> helperGlobalSettingMapping) {
    this.helperGlobalSettingMapping = helperGlobalSettingMapping;
  }

  public Set<PrintGlobalSettingMappingDto> getPrintGlobalSettingMapping() {
    return printGlobalSettingMapping;
  }

  public void setPrintGlobalSettingMapping(
      Set<PrintGlobalSettingMappingDto> printGlobalSettingMapping) {
    this.printGlobalSettingMapping = printGlobalSettingMapping;
  }

  public Set<PersonalTagGlobalSettingMappingDto> getPersonalTagGlobalSettingMapping() {
    return personalTagGlobalSettingMapping;
  }

  public void setPersonalTagGlobalSettingMapping(
      Set<PersonalTagGlobalSettingMappingDto> personalTagGlobalSettingMapping) {
    this.personalTagGlobalSettingMapping = personalTagGlobalSettingMapping;
  }


}
