package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * Dto For WorkSpace entity.
 * 
 * @author bkumar
 *
 */
public class WorkSpaceDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long workSpaceId;
  private RoleDto role;
  private String workSpaceName;
  private Set<GlobalProfileDto> globalProfile;
  private Set<UserProfileDto> userProfile;
  public long getWorkSpaceId() {
    return this.workSpaceId;
  }

  public void setWorkSpaceId(long workSpaceId) {
    this.workSpaceId = workSpaceId;
  }

  public RoleDto getRole() {
    return role;
  }

  public void setRole(RoleDto role) {
    this.role = role;
  }

  public String getWorkSpaceName() {
    return this.workSpaceName;
  }

  public void setWorkSpaceName(String workSpaceName) {
    this.workSpaceName = workSpaceName;
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
