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

@Entity
@Table(name = "CommonService.Role")
public class Role implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "RoleId")
  private long roleId;

  @Column(name = "RoleName", nullable = false, length = 16)
  private String roleName;

  @Column(name = "Description", nullable = false, length = 255)
  private String description;


  // bi-directional many-to-one association to GlobalProfile
  @OneToMany(mappedBy = "role")
  private Set<GlobalProfile> globalProfile;

  // bi-directional many-to-one association to Module
  @OneToMany(mappedBy = "role")
  private Set<Module> module;

  // bi-directional many-to-one association to WorkSpace
  @OneToMany(mappedBy = "role")
  private Set<WorkSpace> workSpace;

  // bi-directional many-to-one association to HelperGlobalSettingMapping
  @OneToMany(mappedBy = "role")
  private Set<HelperGlobalSettingMapping> helperGlobalSettingMapping;

  // bi-directional many-to-one association to PrintGlobalSettingMapping
  @OneToMany(mappedBy = "role")
  private Set<PrintGlobalSettingMapping> printGlobalSettingMapping;

  // bi-directional many-to-one association to PersonalTagGlobalSettingMapping
  @OneToMany(mappedBy = "role")
  private Set<PersonalTagGlobalSettingMapping> personalTagGlobalSettingMapping;



  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

 

  public Set<GlobalProfile> getGlobalProfile() {
    return globalProfile;
  }

  public void setGlobalProfile(Set<GlobalProfile> globalProfile) {
    this.globalProfile = globalProfile;
  }

  public Set<Module> getModule() {
    return module;
  }

  public void setModule(Set<Module> module) {
    this.module = module;
  }

  public Set<WorkSpace> getWorkSpace() {
    return workSpace;
  }

  public void setWorkSpace(Set<WorkSpace> workSpace) {
    this.workSpace = workSpace;
  }

  public Set<HelperGlobalSettingMapping> getHelperGlobalSettingMapping() {
    return helperGlobalSettingMapping;
  }

  public void setHelperGlobalSettingMapping(
      Set<HelperGlobalSettingMapping> helperGlobalSettingMapping) {
    this.helperGlobalSettingMapping = helperGlobalSettingMapping;
  }

  public Set<PrintGlobalSettingMapping> getPrintGlobalSettingMapping() {
    return printGlobalSettingMapping;
  }

  public void setPrintGlobalSettingMapping(
      Set<PrintGlobalSettingMapping> printGlobalSettingMapping) {
    this.printGlobalSettingMapping = printGlobalSettingMapping;
  }

  public Set<PersonalTagGlobalSettingMapping> getPersonalTagGlobalSettingMapping() {
    return personalTagGlobalSettingMapping;
  }

  public void setPersonalTagGlobalSettingMapping(
      Set<PersonalTagGlobalSettingMapping> personalTagGlobalSettingMapping) {
    this.personalTagGlobalSettingMapping = personalTagGlobalSettingMapping;
  }
}
