package org.epo.cms.edfs.services.settings.beans;

import java.util.List;

/**
 * Notification response object.
 * 
 * @author dinagar
 *
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationFilter {

  private boolean resetToDefault;

  private List<EventTypeDetail> medium;

  private List<EventTypeDetail> low;

  public boolean isResetToDefault() {
    return resetToDefault;
  }

  public void setResetToDefault(boolean resetToDefault) {
    this.resetToDefault = resetToDefault;
  }


  public List<EventTypeDetail> getMedium() {
    return medium;
  }

  public void setMedium(List<EventTypeDetail> medium) {
    this.medium = medium;
  }

  public List<EventTypeDetail> getLow() {
    return low;
  }

  public void setLow(List<EventTypeDetail> low) {
    this.low = low;
  }

@Override
public String toString() {
	return "NotificationFilter [resetToDefault=" + resetToDefault + ", medium=" + medium + ", low=" + low + "]";
}


  
  

}
