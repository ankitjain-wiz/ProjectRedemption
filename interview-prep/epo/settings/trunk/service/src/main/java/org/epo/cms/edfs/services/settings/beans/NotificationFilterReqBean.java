/**
 * 
 */
package org.epo.cms.edfs.services.settings.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author vigupta
 *
 */
public class NotificationFilterReqBean {
	
	
	@JsonProperty("sectionName")
	private String sectionName;
	
	@JsonProperty("notificationFilterRequestList")
	private List<NotificationFilterRequest> notificationFilterRequestList;

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	
	public List<NotificationFilterRequest> getNotificationFilterRequestList() {
		return notificationFilterRequestList;
	}

	public void setNotificationFilterRequestList(List<NotificationFilterRequest> notificationFilterRequestList) {
		this.notificationFilterRequestList = notificationFilterRequestList;
	}

	@Override
	public String toString() {
		return "NotificationFilterReqBean [sectionName=" + sectionName + ", notificationFilterRequestList="
				+ notificationFilterRequestList + ", getSectionName()=" + getSectionName()
				+ ", getNotificationFilterRequestList()=" + getNotificationFilterRequestList() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}	
	
	

}
