package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Dto for HelperUserSettingMapping entity.
 * 
 * @author bkumar
 *
 */
public class HelperUserSettingMappingDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private long helperUserSettingMappingId;
	private boolean active;
	private HelperDto helper;
	private Date lastUpdateDateTime;
	private String userId;
	private boolean checked;


	public long getHelperUserSettingMappingId() {
		return this.helperUserSettingMappingId;
	}

	public void setHelperUserSettingMappingId(long helperUserSettingMappingId) {
		this.helperUserSettingMappingId = helperUserSettingMappingId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public HelperDto getHelper() {
		return this.helper;
	}

	public void setHelper(HelperDto helper) {
		this.helper = helper;
	}

	public Date getLastUpdateDateTime() {
		return this.lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
