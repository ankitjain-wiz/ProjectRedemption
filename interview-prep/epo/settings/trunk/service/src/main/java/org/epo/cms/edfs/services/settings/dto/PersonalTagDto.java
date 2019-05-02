package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * Dto for PersonalTag entity.
 * 
 * @author bkumar
 *
 */
public class PersonalTagDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long personalTagId;
  private String tagName;
  private Set<PersonalTagGlobalSettingMappingDto> personalTagGlobalSettingMapping;
  private Set<PersonalTagUserSettingMappingDto> personalTagUserSettingMapping;

 

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
