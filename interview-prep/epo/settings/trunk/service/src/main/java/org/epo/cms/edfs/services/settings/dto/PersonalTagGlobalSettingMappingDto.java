package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;

/**
 * Dto for PersonalTagGlobalSettingMapping entity.
 * 
 * @author bkumar
 *
 */
public class PersonalTagGlobalSettingMappingDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long personalTagGlobalSettingMappingId;
  private PersonalLevelDto personalLevel;
  private PersonalTagDto personalTag;
  private RoleDto role;
  private String titleName;

  public long getPersonalTagGlobalSettingMappingId() {
    return this.personalTagGlobalSettingMappingId;
  }

  public void setPersonalTagGlobalSettingMappingId(long personalTagGlobalSettingMappingId) {
    this.personalTagGlobalSettingMappingId = personalTagGlobalSettingMappingId;
  }

  public PersonalLevelDto getPersonalLevel() {
    return personalLevel;
  }

  public void setPersonalLevel(PersonalLevelDto personalLevel) {
    this.personalLevel = personalLevel;
  }

  public void setPersonalTag(PersonalTagDto personalTag) {
    this.personalTag = personalTag;
  }

  public PersonalTagDto getPersonalTag() {
    return this.personalTag;
  }

  public RoleDto getRole() {
    return this.role;
  }

  public void setRole(RoleDto role) {
    this.role = role;
  }

  public String getTitleName() {
    return this.titleName;
  }

  public void setTitleName(String titleName) {
    this.titleName = titleName;
  }

}
