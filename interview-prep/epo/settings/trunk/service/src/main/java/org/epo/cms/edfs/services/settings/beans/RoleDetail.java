package org.epo.cms.edfs.services.settings.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Role response  to a service 
 * @author dinagar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDetail {

  @JsonProperty("role")
  private List<Role> role;

  public List<Role> getRole() {
    return role;
  }

  public void setRole(List<Role> role) {
    this.role = role;
  }

}
