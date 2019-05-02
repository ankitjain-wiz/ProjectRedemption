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
 * The persistent class for the PersonalTagGlobalSettingMapping database table.
 * 
 */
@Entity
@Table(name = "UserMgmt.PersonalTagGlobalSettingMapping")
public class PersonalTagGlobalSettingMapping implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PersonalTagGlobalSettingMappingId")
  private long personalTagGlobalSettingMappingId;

  @ManyToOne
  @JoinColumn(name = "PersonalLevelId")
  private PersonalLevel personalLevel;

  @ManyToOne
  @JoinColumn(name = "PersonalTagId")
  private PersonalTag personalTag;

  @ManyToOne
  @JoinColumn(name = "RoleId")
  private Role role;

  @Column(name = "TitleName", nullable = false, length = 30)
  private String titleName;

  public PersonalTagGlobalSettingMapping() {}

  public long getPersonalTagGlobalSettingMappingId() {
    return this.personalTagGlobalSettingMappingId;
  }

  public void setPersonalTagGlobalSettingMappingId(long personalTagGlobalSettingMappingId) {
    this.personalTagGlobalSettingMappingId = personalTagGlobalSettingMappingId;
  }

  public PersonalLevel getPersonalLevel() {
    return personalLevel;
  }

  public void setPersonalLevel(PersonalLevel personalLevel) {
    this.personalLevel = personalLevel;
  }

  public PersonalTag getPersonalTag() {
    return personalTag;
  }

  public void setPersonalTag(PersonalTag personalTag) {
    this.personalTag = personalTag;
  }

  public Role getRole() {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getTitleName() {
    return this.titleName;
  }

  public void setTitleName(String titleName) {
    this.titleName = titleName;
  }

}
