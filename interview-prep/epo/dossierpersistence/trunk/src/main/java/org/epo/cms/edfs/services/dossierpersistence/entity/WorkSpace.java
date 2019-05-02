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
 * The persistent class for the WorkSpace database table.
 * 
 */
@Entity
@Table(name = "DossierMgmt.WorkSpace")
public class WorkSpace implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "WorkSpaceId")
  private long workSpaceId;

  @ManyToOne
  @JoinColumn(name = "RoleId")
  private Role role;

  @Column(name = "WorkSpaceName", nullable = false, length = 30)
  private String workSpaceName;

  // bi-directional many-to-one association to UserProfile
  @OneToMany(mappedBy = "workSpace")
  private Set<UserProfile> userProfile;

  // bi-directional many-to-one association to GlobalProfile
  @OneToMany(mappedBy = "workSpace")
  private Set<GlobalProfile> globalProfile;

  public long getWorkSpaceId() {
    return this.workSpaceId;
  }

  public void setWorkSpaceId(long workSpaceId) {
    this.workSpaceId = workSpaceId;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getWorkSpaceName() {
    return this.workSpaceName;
  }

  public void setWorkSpaceName(String workSpaceName) {
    this.workSpaceName = workSpaceName;
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
