package org.epo.cms.edfs.services.settings.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SettingsRequest to capture post data
 * @author dinagar
 *
 */
public class SettingsRequest {
	@JsonProperty("defaultModule")
	private String defaultModule;
	@JsonProperty("defaultWorkspace")
	private String defaultWorkspace;
	@JsonProperty("printAdditionalOption")
	private String printAdditionalOption;
	@JsonProperty("notificationAlertLevel")
	private String notificationAlertLevel;
	@JsonProperty("helperList")
	private List<HelperList> helperList;
	@JsonProperty("notificationFilter")
	private List<NotificationFilterRequest> notificationFilterRequest;
	@JsonProperty("personalTagList")
	private List<PersonalTagList> personalTagList;
	
	@JsonProperty("fontSize")
	private int fontSize;

	@JsonProperty("sectionNameList")
	private List<String> sectionNameList;
	
	public List<String> getSectionNameList() {
		return sectionNameList;
	}

	public void setSectionNameList(List<String> sectionNameList) {
		this.sectionNameList = sectionNameList;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getDefaultModule() {
		return defaultModule;
	}

	public void setDefaultModule(String defaultModule) {
		this.defaultModule = defaultModule;
	}

	public String getDefaultWorkspace() {
		return defaultWorkspace;
	}

	public void setDefaultWorkspace(String defaultWorkspace) {
		this.defaultWorkspace = defaultWorkspace;
	}

	public String getPrintAdditionalOption() {
		return printAdditionalOption;
	}

	public void setPrintAdditionalOption(String printAdditionalOption) {
		this.printAdditionalOption = printAdditionalOption;
	}

	public String getNotificationAlertLevel() {
		return notificationAlertLevel;
	}

	public void setNotificationAlertLevel(String notificationAlertLevel) {
		this.notificationAlertLevel = notificationAlertLevel;
	}

	public List<HelperList> getHelperList() {
		return helperList;
	}

	public void setHelperList(List<HelperList> helperList) {
		this.helperList = helperList;
	}

	public List<PersonalTagList> getPersonalTagList() {
		return personalTagList;
	}

	public void setPersonalTagList(List<PersonalTagList> personalTagList) {
		this.personalTagList = personalTagList;
	}

	public List<NotificationFilterRequest> getNotificationFilterRequest() {
		return notificationFilterRequest;
	}

	public void setNotificationFilterRequest(List<NotificationFilterRequest> notificationFilterRequest) {
		this.notificationFilterRequest = notificationFilterRequest;
	}

	@Override
	public String toString() {
		return "SettingsRequest [defaultModule=" + defaultModule + ", defaultWorkspace=" + defaultWorkspace
				+ ", printAdditionalOption=" + printAdditionalOption + ", notificationAlertLevel="
				+ notificationAlertLevel + ", helperList=" + helperList + ", notificationFilterRequest="
				+ notificationFilterRequest + ", personalTagList=" + personalTagList + ", fontSize=" + fontSize
				+ ", sectionNameList=" + sectionNameList + "]";
	}


}
