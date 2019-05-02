package org.epo.cms.edfs.services.common.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PermissionResponse {


  @JsonProperty("AM43_Result")
  private String result;


  @JsonProperty("AM43_Explanation")
  private String explanation;

  @JsonProperty("ROLENAME")
  private String roleName;

  @JsonProperty("PERMISSION")
  private String permission;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getExplanation() {
    return explanation;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }


}
