package org.epo.cms.edfs.services.settings.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * EventTypeDetail class.
 * 
 * @author dinagar
 *
 */


public class EventTypeDetail  implements Serializable{

 
  private static final long serialVersionUID = 1L;
  
  @JsonProperty("eventTypeId")
  private int eventTypeId;
  
  @JsonProperty("eventTypeCode")
  private String eventTypeCode;
  
  @JsonProperty("filterFlag")
  private boolean filterFlag;
  
  public int getEventTypeId() {
    return eventTypeId;
  }

  public void setEventTypeId(int eventTypeId) {
    this.eventTypeId = eventTypeId;
  }

  public String getEventTypeCode() {
    return eventTypeCode;
  }

  public void setEventTypeCode(String eventTypeCode) {
    this.eventTypeCode = eventTypeCode;
  }

  public boolean isFilterFlag() {
    return filterFlag;
  }

  public void setFilterFlag(boolean filterFlag) {
    this.filterFlag = filterFlag;
  }
  
  
}
