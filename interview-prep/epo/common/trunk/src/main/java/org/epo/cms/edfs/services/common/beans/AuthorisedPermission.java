package org.epo.cms.edfs.services.common.beans;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorisedPermission {
  @JsonProperty("AM43Permissions")
  private List<PermissionResponse> aM43Permissions;

  public List<PermissionResponse> getaM43Permissions() {
    List<PermissionResponse> newAM43Permissions = null;
    if (aM43Permissions != null) {
      newAM43Permissions = new ArrayList<>(aM43Permissions.size());
      for(PermissionResponse p : aM43Permissions){
        newAM43Permissions.add(p);
      }
    }
    return newAM43Permissions;
  }

  public void setaM43Permissions(List<PermissionResponse> aM43Permissions) {
    List<PermissionResponse> newAM43Permissions = null;
    if (aM43Permissions != null) {
      newAM43Permissions = new ArrayList<>(aM43Permissions.size());
      for(PermissionResponse p : aM43Permissions){
        newAM43Permissions.add(p);
      }
    }
    this.aM43Permissions = newAM43Permissions;
  }

  @Override
  public String toString() {

    return "AuthorisedPermission [aM43Permissions " + aM43Permissions + "]";
  }
}
