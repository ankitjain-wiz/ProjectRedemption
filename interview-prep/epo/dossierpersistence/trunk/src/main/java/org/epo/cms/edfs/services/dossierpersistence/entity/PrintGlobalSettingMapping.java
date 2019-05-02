package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the PrintGlobalSettingMapping database table.
 * 
 */
@Entity
@Table(name = "UserMgmt.PrintGlobalSettingMapping")
public class PrintGlobalSettingMapping implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PrintGlobalSettingMappingId")
  private long printGlobalSettingMappingId;

  @ManyToOne
  @JoinColumn(name = "PrintOptionId")
  private PrintOption printOption;

  @ManyToOne
  @JoinColumn(name = "RoleId")
  private Role role;

  public PrintGlobalSettingMapping() {}

  public long getPrintGlobalSettingMappingId() {
    return this.printGlobalSettingMappingId;
  }

  public void setPrintGlobalSettingMappingId(long printGlobalSettingMappingId) {
    this.printGlobalSettingMappingId = printGlobalSettingMappingId;
  }

  public PrintOption getPrintOption() {
    return printOption;
  }

  public void setPrintOption(PrintOption printOption) {
    this.printOption = printOption;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }



}
