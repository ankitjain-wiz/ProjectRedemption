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
 * The persistent class for the HelperGlobalSettingMapping database table.
 * 
 */
@Entity
@Table(name = "UserMgmt.HelperGlobalSettingMapping")
public class HelperGlobalSettingMapping implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "HelperGlobalSettingMappingId")
  private long helperGlobalSettingMappingId;

  @ManyToOne
  @JoinColumn(name = "HelperId")
  private Helper helper;

  @ManyToOne
  @JoinColumn(name = "RoleId")
  private Role role;

  public HelperGlobalSettingMapping() {}

  public long getHelperGlobalSettingMappingId() {
    return this.helperGlobalSettingMappingId;
  }

  public void setHelperGlobalSettingMappingId(long helperGlobalSettingMappingId) {
    this.helperGlobalSettingMappingId = helperGlobalSettingMappingId;
  }

  public Helper getHelper() {
    return this.helper;
  }

  public void setHelper(Helper helper) {
    this.helper = helper;
  }

  public Role getRole() {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

}
