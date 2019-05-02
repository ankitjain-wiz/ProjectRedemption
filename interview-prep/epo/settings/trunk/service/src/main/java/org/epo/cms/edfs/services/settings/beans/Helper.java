package org.epo.cms.edfs.services.settings.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;



/**
 * Helper response class.
 * 
 * @author dinagar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Helper {
  private boolean resetToDefault;

  private String sectionName;

  private List<HelperList> helperList;

  public boolean isResetToDefault() {
    return resetToDefault;
  }

  public void setResetToDefault(boolean resetToDefault) {
    this.resetToDefault = resetToDefault;
  }

  public List<HelperList> getHelperList() {
    return helperList;
  }

  public void setHelperList(List<HelperList> helperList) {
    this.helperList = helperList;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  @Override
  public String toString() {
    return "Helper [resetToDefault=" + resetToDefault + ", sectionName=" + sectionName
        + ", helperList=" + helperList + "]";
  }


}
