package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the PrintUserSettingMapping database table.
 * 
 */
@Entity
@Table(name = "UserMgmt.PrintUserSettingMapping")
public class PrintUserSettingMapping implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PrintUserSettingMappingId")
  private long printUserSettingMappingId;

  @Column(name = "Active", nullable = false)
  private boolean active;

  @Column(name = "LastUpdateDateTime", length = 1)
  private Date lastUpdateDateTime;

  @ManyToOne
  @JoinColumn(name = "PrintOptionId")
  private PrintOption printOption;

  @Column(name = "UserId", nullable = false, length = 10)
  private String userId;

  public PrintUserSettingMapping() {}

  public long getPrintUserSettingMappingId() {
    return this.printUserSettingMappingId;
  }

  public void setPrintUserSettingMappingId(long printUserSettingMappingId) {
    this.printUserSettingMappingId = printUserSettingMappingId;
  }

  public boolean getActive() {
    return this.active;
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

  public PrintOption getPrintOption() {
    return printOption;
  }

  public void setPrintOption(PrintOption printOption) {
    this.printOption = printOption;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
