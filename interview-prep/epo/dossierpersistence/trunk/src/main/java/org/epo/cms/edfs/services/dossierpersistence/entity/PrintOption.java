package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the PrintOption database table.
 * 
 */
@Entity
@Table(name = "DossierMgmt.PrintOption")
public class PrintOption implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PrintOptionId")
  private long printOptionId;

  @Column(name = "PrintAdditionalInfo", nullable = false, length = 30)
  private String printAdditionalInfo;

  // bi-directional many-to-one association to PrintGlobalSettingMapping
  @OneToMany(mappedBy = "printOption")
  private Set<PrintGlobalSettingMapping> printGlobalSettingMapping;

  // bi-directional many-to-one association to PrintUserSettingMapping
  @OneToMany(mappedBy = "printOption")
  private Set<PrintUserSettingMapping> printUserSettingMapping;

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

  public Set<PrintGlobalSettingMapping> getPrintGlobalSettingMapping() {
    return printGlobalSettingMapping;
  }

  public void setPrintGlobalSettingMapping(
      Set<PrintGlobalSettingMapping> printGlobalSettingMapping) {
    this.printGlobalSettingMapping = printGlobalSettingMapping;
  }

  public Set<PrintUserSettingMapping> getPrintUserSettingMapping() {
    return printUserSettingMapping;
  }

  public void setPrintUserSettingMapping(Set<PrintUserSettingMapping> printUserSettingMapping) {
    this.printUserSettingMapping = printUserSettingMapping;
  }

}
