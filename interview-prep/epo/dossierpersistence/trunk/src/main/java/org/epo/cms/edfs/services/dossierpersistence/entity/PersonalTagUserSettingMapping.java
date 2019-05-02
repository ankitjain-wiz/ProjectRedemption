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
 * The persistent class for the PersonalTagUserSettingMapping database table.
 * 
 */
@Entity
@Table(name = "UserMgmt.PersonalTagUserSettingMapping")
public class PersonalTagUserSettingMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PersonalTagUserSettingMappingId")
	private long personalTagUserSettingMappingId;

	@Column(name = "Active", nullable = false)
	private boolean active;

	@Column(name = "LastUpdateDateTime", length = 1)
	private Date lastUpdateDateTime;

	@ManyToOne
	@JoinColumn(name = "PersonalLevelId")
	private PersonalLevel personalLevel;

	@ManyToOne
	@JoinColumn(name = "PersonalTagId")
	private PersonalTag personalTag;

	@Column(name = "TitleName", nullable = false, length = 30)
	private String titleName;

	@Column(name = "UserId", nullable = false, length = 10)
	private String userId;

	@Column(name = "PersonalTagGlobalSettingMappingId",nullable = false)
	private long personalTagGlobalSettingMappingId;

	public PersonalTagUserSettingMapping() {
	}

	public long getPersonalTagUserSettingMappingId() {
		return this.personalTagUserSettingMappingId;
	}

	public void setPersonalTagUserSettingMappingId(long personalTagUserSettingMappingId) {
		this.personalTagUserSettingMappingId = personalTagUserSettingMappingId;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getLastUpdateDateTime() {
		return this.lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public PersonalLevel getPersonalLevel() {
		return personalLevel;
	}

	public void setPersonalLevel(PersonalLevel personalLevel) {
		this.personalLevel = personalLevel;
	}

	public PersonalTag getPersonalTag() {
		return personalTag;
	}

	public void setPersonalTag(PersonalTag personalTag) {
		this.personalTag = personalTag;
	}

	public String getTitleName() {
		return this.titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getPersonalTagGlobalSettingMappingId() {
		return personalTagGlobalSettingMappingId;
	}

	public void setPersonalTagGlobalSettingMappingId(long personalTagGlobalSettingMappingId) {
		this.personalTagGlobalSettingMappingId = personalTagGlobalSettingMappingId;
	}

}
