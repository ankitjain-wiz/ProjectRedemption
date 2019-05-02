package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;
import java.util.Set;


/**
 * Dto for PersonalLevel entity.
 * 
 * @author bkumar
 *
 */
public class PersonalLevelDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long personalLevelId;
  private String levelName;
  private Set<PersonalTagGlobalSettingMappingDto> personalTagGlobalSettingMapping;
  private Set<PersonalTagUserSettingMappingDto> personalTagUserSettingMapping;

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

  public Set<PersonalTagGlobalSettingMappingDto> getPersonalTagGlobalSettingMapping() {
    return personalTagGlobalSettingMapping;
  }

  public void setPersonalTagGlobalSettingMapping(
      Set<PersonalTagGlobalSettingMappingDto> personalTagGlobalSettingMapping) {
    this.personalTagGlobalSettingMapping = personalTagGlobalSettingMapping;
  }

  public Set<PersonalTagUserSettingMappingDto> getPersonalTagUserSettingMapping() {
    return personalTagUserSettingMapping;
  }

  public void setPersonalTagUserSettingMapping(
      Set<PersonalTagUserSettingMappingDto> personalTagUserSettingMapping) {
    this.personalTagUserSettingMapping = personalTagUserSettingMapping;
  }

}
