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
 * The persistent class for the PersonalTag database table.
 * 
 */
@Entity
@Table(name = "DossierMgmt.PersonalTag")
public class PersonalTag implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PersonalTagId")
  private long personalTagId;

  @Column(name = "TagName", nullable = false, length = 30)
  private String tagName;

  // bi-directional many-to-one association to PersonalTagGlobalSettingMapping
  @OneToMany(mappedBy = "personalTag")
  private Set<PersonalTagGlobalSettingMapping> personalTagGlobalSettingMapping;

  // bi-directional many-to-one association to PersonalTagUserSettingMapping
  @OneToMany(mappedBy = "personalTag")
  private Set<PersonalTagUserSettingMapping> personalTagUserSettingMapping;

  public long getPersonalTagId() {
    return this.personalTagId;
  }

  public void setPersonalTagId(long personalTagId) {
    this.personalTagId = personalTagId;
  }

  public String getTagName() {
    return this.tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
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
