package org.epo.cms.edfs.services.settings.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Role description bean 
 * @author dinagar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {

  @JsonProperty("roleId")
  private long roleId;
  @JsonProperty("roleName")
  private String roleName;

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

}
