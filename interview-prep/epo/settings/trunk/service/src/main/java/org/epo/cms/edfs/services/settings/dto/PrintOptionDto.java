package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * Dto for PrintOption entity.
 * 
 * @author bkumar
 *
 */
public class PrintOptionDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long printOptionId;
  private String printAdditionalInfo;
  private Set<PrintGlobalSettingMappingDto> printGlobalSettingMapping;
  private Set<PrintUserSettingMappingDto> printUserSettingMapping;

  public long getPrintOptionId() {
    return this.printOptionId;
  }

  public void setPrintOptionId(long printOptionId) {
    this.printOptionId = printOptionId;
  }

  public String getPrintAdditionalInfo() {
    return this.printAdditionalInfo;
  }

  public void setPrintAdditionalInfo(String printAdditionalInfo) {
    this.printAdditionalInfo = printAdditionalInfo;
  }

  public Set<PrintGlobalSettingMappingDto> getPrintGlobalSettingMapping() {
    return printGlobalSettingMapping;
  }

  public void setPrintGlobalSettingMapping(
      Set<PrintGlobalSettingMappingDto> printGlobalSettingMapping) {
    this.printGlobalSettingMapping = printGlobalSettingMapping;
  }

  public Set<PrintUserSettingMappingDto> getPrintUserSettingMapping() {
    return printUserSettingMapping;
  }

  public void setPrintUserSettingMapping(Set<PrintUserSettingMappingDto> printUserSettingMapping) {
    this.printUserSettingMapping = printUserSettingMapping;
  }

}
