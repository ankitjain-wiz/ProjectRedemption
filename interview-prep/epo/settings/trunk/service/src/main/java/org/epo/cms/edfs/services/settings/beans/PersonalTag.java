package org.epo.cms.edfs.services.settings.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * personal tag response data object.
 * 
 * @author dinagar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalTag {
  @JsonProperty("resetToDefault")
  private boolean resetToDefault;
  
  @JsonProperty("sectionName")
  private String sectionName;
  
  @JsonProperty("personalTagList")
  private List<PersonalTagList> personalTagList;

  public List<PersonalTagList> getPersonalTagList() {
    return personalTagList;
  }

  public void setPersonalTagList(List<PersonalTagList> personalTagList) {
    this.personalTagList = personalTagList;
  }

  public boolean isResetToDefault() {
    return resetToDefault;
  }

  public void setResetToDefault(boolean resetToDefault) {
    this.resetToDefault = resetToDefault;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  @Override
  public String toString() {
    return "PersonalTag [resetToDefault=" + resetToDefault + ", sectionName=" + sectionName
        + ", personalTagList=" + personalTagList + "]";
  }

}
