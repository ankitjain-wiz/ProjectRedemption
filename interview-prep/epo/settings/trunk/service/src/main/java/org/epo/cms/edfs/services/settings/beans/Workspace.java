package org.epo.cms.edfs.services.settings.beans;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * workspace response object.
 * 
 * @author dinagar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Workspace {

  private boolean resetToDefault;

  private String sectionName;

  private List<WorkspaceList> workspaceList;

  public boolean isResetToDefault() {
    return resetToDefault;
  }

  public void setResetToDefault(boolean resetToDefault) {
    this.resetToDefault = resetToDefault;
  }

  public List<WorkspaceList> getWorkspaceList() {
    return workspaceList;
  }

  public void setWorkspaceList(List<WorkspaceList> workspaceList) {
    this.workspaceList = workspaceList;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  @Override
  public String toString() {
    return "Workspace [resetToDefault=" + resetToDefault + ", sectionName=" + sectionName
        + ", workspaceList=" + workspaceList + "]";
  }
}
