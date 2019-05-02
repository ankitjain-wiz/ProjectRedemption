package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;

/**
 * Dto for HelperGlobalSettingMapping entity.
 * 
 * @author bkumar
 *
 */
public class HelperGlobalSettingMappingDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long helperGlobalSettingMappingId;
  private HelperDto helper;
  private RoleDto role;
  private boolean check;


 public boolean isCheck() {
	return check;
}

public void setCheck(boolean check) {
	this.check = check;
}

public long getHelperGlobalSettingMappingId() {
    return this.helperGlobalSettingMappingId;
  }

  public void setHelperGlobalSettingMappingId(long helperGlobalSettingMappingId) {
    this.helperGlobalSettingMappingId = helperGlobalSettingMappingId;
  }

  public HelperDto getHelper() {
    return this.helper;
  }

  public void setHelper(HelperDto helper) {
    this.helper = helper;
  }

  public RoleDto getRole() {
    return this.role;
  }

  public void setRole(RoleDto role) {
    this.role = role;
  }

}
