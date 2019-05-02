package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *  Dto for UserProfile entity
 * @author dinagar
 *
 */
public class UserProfileDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long userProfileId;

	private boolean active;

	private String alertLevel;

	private Date lastUpdateDateTime;

	private ModuleDto module;

	private String userId;

	private WorkSpaceDto workSpace;

	private int fontSize;

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public long getUserProfileId() {
		return this.userProfileId;
	}

	public void setUserProfileId(long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getAlertLevel() {
		return this.alertLevel;
	}

	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public ModuleDto getModule() {
		return module;
	}

	public void setModule(ModuleDto module) {
		this.module = module;
	}

	public WorkSpaceDto getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(WorkSpaceDto workSpace) {
		this.workSpace = workSpace;
	}

}
