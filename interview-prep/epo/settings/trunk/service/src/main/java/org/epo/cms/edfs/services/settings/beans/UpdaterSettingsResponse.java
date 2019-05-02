package org.epo.cms.edfs.services.settings.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Settings post response.
 * 
 * @author dinagar
 *
 */
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
