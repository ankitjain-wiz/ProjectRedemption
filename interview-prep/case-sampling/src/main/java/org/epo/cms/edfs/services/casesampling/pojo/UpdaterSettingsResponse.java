package org.epo.cms.edfs.services.casesampling.pojo;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * class used to display Custom Update Message
 * @author ankitjain2
 *
 */
@Component
public class UpdaterSettingsResponse {

  @JsonProperty("description")
  private String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }



}
