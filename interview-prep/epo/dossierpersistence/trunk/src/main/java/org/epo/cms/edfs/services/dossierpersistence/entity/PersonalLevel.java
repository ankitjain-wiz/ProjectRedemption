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
 * The persistent class for the PersonalLevel database table.
 * 
 */
@Entity
@Table(name = "DossierMgmt.PersonalLevel")
public class PersonalLevel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PersonalLevelId")
  private long personalLevelId;

  @Column(name = "LevelName", nullable = false, length = 30)
  private String levelName;

  @OneToMany(mappedBy = "personalLevel")
  private Set<PersonalTagGlobalSettingMapping> personalTagGlobalSettingMapping;

  // bi-directional many-to-one association to PersonalTagUserSettingMapping
  @OneToMany(mappedBy = "personalLevel")
  private Set<PersonalTagUserSettingMapping> personalTagUserSettingMapping;

  public long getPersonalLevelId() {
    return this.personalLevelId;
  }

  public void setPersonalLevelId(long personalLevelId) {
    this.personalLevelId = personalLevelId;
  }

  public String getLevelName() {
    return this.levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  public Set<PersonalTagGlobalSettingMapping> getPersonalTagGlobalSettingMapping() {
    return personalTagGlobalSettingMapping;
  }

  public void setPersonalTagGlobalSettingMapping(
      Set<PersonalTagGlobalSettingMapping> personalTagGlobalSettingMapping) {
    this.personalTagGlobalSettingMapping = personalTagGlobalSettingMapping;
  }

  public Set<PersonalTagUserSettingMapping> getPersonalTagUserSettingMapping() {
    return personalTagUserSettingMapping;
  }

  public void setPersonalTagUserSettingMapping(
      Set<PersonalTagUserSettingMapping> personalTagUserSettingMapping) {
    this.personalTagUserSettingMapping = personalTagUserSettingMapping;
  }

}
