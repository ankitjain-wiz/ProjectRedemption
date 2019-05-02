package org.epo.cms.edfs.services.settings.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * NotificationFilterRequest response to Notification Section response class
 * @author dinagar
 *
 */
public class NotificationFilterRequest {
  @JsonProperty("eventTypeId")
  private String eventTypeId;
  @JsonProperty("eventTypeCode")
  private String eventTypeCode;
  @JsonProperty("intrusivenessLevel")
  private String intrusivenessLevel;
  @JsonProperty("filterFlag")
  private boolean filterFlag;

  public String getEventTypeId() {
    return eventTypeId;
  }

  public void setEventTypeId(String eventTypeId) {
    this.eventTypeId = eventTypeId;
  }

  public String getEventTypeCode() {
    return eventTypeCode;
  }

  public void setEventTypeCode(String eventTypeCode) {
    this.eventTypeCode = eventTypeCode;
  }

  public String getIntrusivenessLevel() {
    return intrusivenessLevel;
  }

  public void setIntrusivenessLevel(String intrusivenessLevel) {
    this.intrusivenessLevel = intrusivenessLevel;
  }

  public boolean isFilterFlag() {
    return filterFlag;
  }

  public void setFilterFlag(boolean filterFlag) {
    this.filterFlag = filterFlag;
  }

  @Override
  public String toString() {
    return "NotificationFilterRequest [eventTypeId=" + eventTypeId + ", eventTypeCode="
        + eventTypeCode + ", intrusivenessLevel=" + intrusivenessLevel + ", filterFlag="
        + filterFlag + "]";
  }


}
