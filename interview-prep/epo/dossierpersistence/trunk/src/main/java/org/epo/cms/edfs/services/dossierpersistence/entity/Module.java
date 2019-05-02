package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the Module database table.
 * 
 */
@Entity
@Table(name = "DossierMgmt.Module")
public class Module implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ModuleId")
  private long moduleId;

  @Column(name = "ModuleName", nullable = false, length = 30)
  private String moduleName;

  @ManyToOne
  @JoinColumn(name = "RoleId")
  private Role role;

  // bi-directional many-to-one association to GlobalProfile
  @OneToMany(mappedBy = "module")
  private Set<GlobalProfile> globalProfile;

  // bi-directional many-to-one association to UserProfile
  @OneToMany(mappedBy = "module")
  private Set<UserProfile> userProfile;


  public long getModuleId() {
    return this.moduleId;
  }

  public void setModuleId(long moduleId) {
    this.moduleId = moduleId;
  }

  public String getModuleName() {
    return this.moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public Role getRole() {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Set<GlobalProfile> getGlobalProfile() {
    return globalProfile;
  }

  public void setGlobalProfile(Set<GlobalProfile> globalProfile) {
    this.globalProfile = globalProfile;
  }

  public Set<UserProfile> getUserProfile() {
    return userProfile;
  }

  public void setUserProfile(Set<UserProfile> userProfile) {
    this.userProfile = userProfile;
  }

}
