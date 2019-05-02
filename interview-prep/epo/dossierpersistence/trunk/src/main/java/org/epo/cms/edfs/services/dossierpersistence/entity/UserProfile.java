package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the UserProfile database table.
 * 
 */
@Entity
@Table(name = "UserMgmt.UserProfile")
public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UserProfileId")
	private long userProfileId;

	@Column(name = "Active", nullable = false)
	private boolean active;

	@Column(name = "AlertLevel", length = 10)
	private String alertLevel;

	@Column(name = "LastUpdateDateTime", length = 1)
	private Date lastUpdateDateTime;

	@ManyToOne
	@JoinColumn(name = "ModuleId")
	private Module module;

	@Column(name = "UserId", nullable = false, length = 10)
	private String userId;

	@ManyToOne
	@JoinColumn(name = "WorkSpaceId")
	private WorkSpace workSpace;

	@Column(name = "FontSize")
	private int fontSize;

	public UserProfile() {
	}

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

	public boolean getActive() {
		return this.active;
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

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public WorkSpace getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(WorkSpace workSpace) {
		this.workSpace = workSpace;
	}

}
