package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;

/**
 * Dto for PrintGlobalSettingMapping entity.
 * 
 * @author bkumar
 *
 */
public class PrintGlobalSettingMappingDto implements Serializable {

  private static final long serialVersionUID = 1L;


  private long printGlobalSettingMappingId;
  private PrintOptionDto printOption;
  private RoleDto role;


  public long getPrintGlobalSettingMappingId() {
    return this.printGlobalSettingMappingId;
  }

  public void setPrintGlobalSettingMappingId(long printGlobalSettingMappingId) {
    this.printGlobalSettingMappingId = printGlobalSettingMappingId;
  }

  public PrintOptionDto getPrintOption() {
    return printOption;
  }

  public void setPrintOption(PrintOptionDto printOption) {
    this.printOption = printOption;
  }

  public RoleDto getRole() {
    return role;
  }

  public void setRole(RoleDto role) {
    this.role = role;
  }


}
