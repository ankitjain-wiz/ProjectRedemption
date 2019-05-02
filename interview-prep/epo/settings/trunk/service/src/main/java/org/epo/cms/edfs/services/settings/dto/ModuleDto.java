package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;
import java.util.Set;



/**
 * Dto for Module entity.
 * 
 * @author bkumar
 *
 */
public class ModuleDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private long moduleId;

  private String moduleName;

  private RoleDto role;

  private Set<GlobalProfileDto> globalProfile;

  private Set<UserProfileDto> userProfile;


  public long getModuleId() {
    return this.moduleId;
  }

  public void setModuleId(long moduleId) {
    this.moduleId = moduleId;
  }

  public String getModuleName() {
    return this.moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public RoleDto getRole() {
    return role;
  }

  public void setRole(RoleDto role) {
    this.role = role;
  }

  public Set<GlobalProfileDto> getGlobalProfile() {
    return globalProfile;
  }

  public void setGlobalProfile(Set<GlobalProfileDto> globalProfile) {
    this.globalProfile = globalProfile;
  }

  public Set<UserProfileDto> getUserProfile() {
    return userProfile;
  }

  public void setUserProfile(Set<UserProfileDto> userProfile) {
    this.userProfile = userProfile;
  }

}
