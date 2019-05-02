package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Dto for PersonalTagUserSettingMapping entity.
 * 
 * @author bkumar
 *
 */
public class PersonalTagUserSettingMappingDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long personalTagUserSettingMappingId;
	private boolean active;
	private Date lastUpdateDateTime;
	private PersonalLevelDto personalLevel;
	private PersonalTagDto personalTag;
	private String titleName;
	private String userId;
	private long personalTagGlobalSettingMappingId;

	public long getPersonalTagUserSettingMappingId() {
		return personalTagUserSettingMappingId;
	}

	public void setPersonalTagUserSettingMappingId(long personalTagUserSettingMappingId) {
		this.personalTagUserSettingMappingId = personalTagUserSettingMappingId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public PersonalLevelDto getPersonalLevel() {
		return personalLevel;
	}

	public void setPersonalLevel(PersonalLevelDto personalLevel) {
		this.personalLevel = personalLevel;
	}

	public PersonalTagDto getPersonalTag() {
		return personalTag;
	}

	public void setPersonalTag(PersonalTagDto personalTag) {
		this.personalTag = personalTag;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getUserId() {
		return userId;
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
