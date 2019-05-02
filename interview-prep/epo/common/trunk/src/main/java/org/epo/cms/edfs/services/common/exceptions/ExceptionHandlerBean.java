package org.epo.cms.edfs.services.common.exceptions;

/**
 * ExceptionHandlerBean used for creating error response in JSON.
 * 
 * @author amigarg
 *
 */
public class ExceptionHandlerBean {
  private String id;
  private String statusCode;
  private String details;

  public ExceptionHandlerBean() {
    super();
  }

  public ExceptionHandlerBean(String id, String statusCode, String details) {
    super();
    this.id = id;
    this.statusCode = statusCode;
    this.details = details;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

}
