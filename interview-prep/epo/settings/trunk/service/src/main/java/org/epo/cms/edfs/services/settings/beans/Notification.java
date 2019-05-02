package org.epo.cms.edfs.services.settings.beans;

/**
 * Notification Section response class
 * @author ankitjain2
 *
 */

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification {
	
	private String notificationAlertLevel;
	
	private String sectionName;
	
	private NotificationFilter notificationFilter;

	public String getNotificationAlertLevel() {
		return notificationAlertLevel;
	}

	public void setNotificationAlertLevel(String notificationAlertLevel) {
		this.notificationAlertLevel = notificationAlertLevel;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public NotificationFilter getNotificationFilter() {
		return notificationFilter;
	}

	public void setNotificationFilter(NotificationFilter notificationFilter) {
		this.notificationFilter = notificationFilter;
	}
	
	@Override
	public String toString() {
		return "Notification [notificationAlertLevel=" + notificationAlertLevel + ", sectionName=" + sectionName
				+ ", notificationFilter=" + notificationFilter + "]";
	}

}
