package org.epo.cms.edfs.services.settings.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * HelperList responses class
 * 
 * @author dinagar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelperList {
  private String helperName;

  private long helperId;

  private String moduleSpace;

  private boolean check;

  public String getHelperName() {
    return helperName;
  }

  public void setHelperName(String helperName) {
    this.helperName = helperName;
  }

  public long getHelperId() {
    return helperId;
  }

  public void setHelperId(long helperId) {
    this.helperId = helperId;
  }

  public String getModuleSpace() {
    return moduleSpace;
  }

  public void setModuleSpace(String moduleSpace) {
    this.moduleSpace = moduleSpace;
  }

  public boolean isCheck() {
	return check;
}

public void setCheck(boolean check) {
	this.check = check;
}

@Override
  public String toString() {
    return "HelperList [helperName=" + helperName + ", helperId=" + helperId + ", moduleSpace="
        + moduleSpace + ", checked=" + check + "]";
  }

}
