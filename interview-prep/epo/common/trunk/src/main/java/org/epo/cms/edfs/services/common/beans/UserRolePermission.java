package org.epo.cms.edfs.services.common.beans;

public class UserRolePermission {

  private String role;
  private String permission;

  public UserRolePermission() {
    // Default Constructor
  }


  public UserRolePermission(String role, String permission) {
    this.role = role;
    this.permission = permission;
  }


  public String getRole() {
    return role;
  }


  public void setRole(String role) {
    this.role = role;
  }


  public String getPermission() {
    return permission;
  }


  public void setPermission(String permission) {
    this.permission = permission;
  }

  @Override
  public String toString() {
    return getRole() + ":" + getPermission();
  }

}
