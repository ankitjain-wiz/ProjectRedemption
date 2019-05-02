package org.epo.cms.edfs.services.settings.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Role Settings over all response.
 * 
 * @author dinagar
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleSettingResponse {

	private Module module;

	private Workspace workspace;

	private Helper helper;

	private PrintOption printOption;

	private PersonalTag personalTag;
	
	private Notification notification;

	private int fontSize;
	
	private CaseSample caseSample;
	
	private TimeLimit timeLimit;

	public CaseSample getCaseSample() {
		return caseSample;
	}


	public void setCaseSample(CaseSample caseSample) {
		this.caseSample = caseSample;
	}


	public TimeLimit getTimeLimit() {
		return timeLimit;
	}


	public void setTimeLimit(TimeLimit timeLimit) {
		this.timeLimit = timeLimit;
	}


	public Notification getNotification() {
		return notification;
	}

	
	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public PersonalTag getPersonalTag() {
		return personalTag;
	}

	public void setPersonalTag(PersonalTag personalTag) {
		this.personalTag = personalTag;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Helper getHelper() {
		return helper;
	}

	public void setHelper(Helper helper) {
		this.helper = helper;
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}


	public PrintOption getPrintOption() {
		return printOption;
	}

	public void setPrintOption(PrintOption printOption) {
		this.printOption = printOption;
	}
	
	@Override
	public String toString() {
		return "RoleSettingResponse [module=" + module + ", workspace=" + workspace + ", helper=" + helper
				+ ", printOption=" + printOption + ", personalTag=" + personalTag + ", notification=" + notification
				+ ", fontSize=" + fontSize + "]";
	}


	

}
