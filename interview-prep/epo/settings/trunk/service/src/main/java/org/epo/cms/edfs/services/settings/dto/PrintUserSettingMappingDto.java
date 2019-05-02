package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Dto for PrintUserSettingMapping entity.
 * 
 * @author bkumar
 *
 */
public class PrintUserSettingMappingDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long printUserSettingMappingId;
  private boolean active;
  private Date lastUpdateDateTime;
  private PrintOptionDto printOption;
  private String userId;

  public PrintUserSettingMappingDto() {
	//constructor
  }

  public long getPrintUserSettingMappingId() {
    return this.printUserSettingMappingId;
  }

  public void setPrintUserSettingMappingId(long printUserSettingMappingId) {
    this.printUserSettingMappingId = printUserSettingMappingId;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Date getLastUpdateDateTime() {
    return lastUpdateDateTime;
  }

  public void setLastUpdateDateTime(Date lastUpdateDateTime) {
    this.lastUpdateDateTime = lastUpdateDateTime;
  }

  public PrintOptionDto getPrintOption() {
    return printOption;
  }

  public void setPrintOption(PrintOptionDto printOption) {
    this.printOption = printOption;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
