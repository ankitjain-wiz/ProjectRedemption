package org.epo.cms.edfs.services.settings.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * NotificationStatusResponse used to create response in JSON.
 * 
 * @author narosing.
 *
 */
public class NotificationStatusResponse implements Serializable{
  
  
 private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;
  
  @JsonProperty("statusCode")
  private String statusCode;
  
  @JsonProperty("details")
  private String details;

  /**
   * default constructor.
   */
  public NotificationStatusResponse() {
  }

  /**
   * @param id - id.
   * @param statusCode . status code.
   * @param details - details.
   */
  public NotificationStatusResponse(String id, String statusCode, String details) {
    super();
    this.id = id;
    this.statusCode = statusCode;
    this.details = details;
  }

  /**
   * @return id.
   */
  public String getId() {
    return id;
  }

  /**
   * @param id - id.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return statusCode - statusCode.
   */
  public String getStatusCode() {
    return statusCode;
  }

  /**
   * @param statusCode - statusCode.
   */
  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  /**
   * @return details - details.
   */
  public String getDetails() {
    return details;
  }

  /**
   * @param details - details.
   */
  public void setDetails(String details) {
    this.details = details;
  }

}
