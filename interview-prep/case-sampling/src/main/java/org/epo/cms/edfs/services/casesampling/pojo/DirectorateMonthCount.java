package org.epo.cms.edfs.services.casesampling.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DirectorateMonthCount POJO class.
 * 
 * @author amigarg
 *
 */
public class DirectorateMonthCount {

  private int actualCount;
  private int workloadCount;
  private String name;
  @JsonProperty
   @JsonInclude(JsonInclude.Include.NON_NULL)
  private String dirDesc;

  public int getActualCount() {
    return actualCount;
  }

  public void setActualCount(int actualCount) {
    this.actualCount = actualCount;
  }

  public int getWorkloadCount() {
    return workloadCount;
  }

  public void setWorkloadCount(int workloadCount) {
    this.workloadCount = workloadCount;
  }

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}



public String getDirDesc() {
	return dirDesc;
}

public void setDirDesc(String dirDesc) {
	this.dirDesc = dirDesc;
}
  
}
