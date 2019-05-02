package org.epo.cms.edfs.services.settings.beans;

/**
 * workspace list response object.
 * 
 * @author dinagar
 *
 */
public class WorkspaceList {
  private long workspaceId;

  private boolean defaultModule;

  private String workspaceName;

  public long getWorkspaceId() {
    return workspaceId;
  }

  public void setWorkspaceId(long workspaceId) {
    this.workspaceId = workspaceId;
  }

  public boolean isDefaultModule() {
    return defaultModule;
  }

  public void setDefaultModule(boolean defaultModule) {
    this.defaultModule = defaultModule;
  }

  public String getWorkspaceName() {
    return workspaceName;
  }

  public void setWorkspaceName(String workspaceName) {
    this.workspaceName = workspaceName;
  }

  @Override
  public String toString() {
    return "WorkspaceList [workspaceId=" + workspaceId + ", defaultModule=" + defaultModule
        + ", workspaceName=" + workspaceName + "]";
  }


}
