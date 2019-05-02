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
 * The persistent class for the Helper database table.
 * 
 */
@Entity
@Table(name = "DossierMgmt.Helper")
public class Helper implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "HelperId")
  private long helperId;

  @Column(name = "HelperName", nullable = false, length = 30)
  private String helperName;

  @Column(name = "ModuleOrWorkspace", nullable = false, length = 10)
  private String moduleOrWorkspace;

  // bi-directional many-to-one association to HelperGlobalSettingMapping
  @OneToMany(mappedBy = "helper")
  private Set<HelperGlobalSettingMapping> helperGlobalSettingMapping;

  // bi-directional many-to-one association to HelperUserSettingMapping
  @OneToMany(mappedBy = "helper")
  private Set<HelperUserSettingMapping> helperUserSettingMapping;


  public long getHelperId() {
    return this.helperId;
  }

  public void setHelperId(long helperId) {
    this.helperId = helperId;
  }

  public String getHelperName() {
    return this.helperName;
  }

  public void setHelperName(String helperName) {
    this.helperName = helperName;
  }

  public String getModuleOrWorkspace() {
    return this.moduleOrWorkspace;
  }

  public void setModuleOrWorkspace(String moduleOrWorkspace) {
    this.moduleOrWorkspace = moduleOrWorkspace;
  }

  public Set<HelperGlobalSettingMapping> getHelperGlobalSettingMapping() {
    return helperGlobalSettingMapping;
  }

  public void setHelperGlobalSettingMapping(
      Set<HelperGlobalSettingMapping> helperGlobalSettingMapping) {
    this.helperGlobalSettingMapping = helperGlobalSettingMapping;
  }

  public Set<HelperUserSettingMapping> getHelperUserSettingMapping() {
    return helperUserSettingMapping;
  }

  public void setHelperUserSettingMapping(Set<HelperUserSettingMapping> helperUserSettingMapping) {
    this.helperUserSettingMapping = helperUserSettingMapping;
  }

}
