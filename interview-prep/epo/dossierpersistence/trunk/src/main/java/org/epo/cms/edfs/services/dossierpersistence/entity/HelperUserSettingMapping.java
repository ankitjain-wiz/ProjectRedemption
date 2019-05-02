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
 * The persistent class for the HelperUserSettingMapping database table.
 * 
 */
@Entity
@Table(name = "UserMgmt.HelperUserSettingMapping")
public class HelperUserSettingMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HelperUserSettingMappingId")
	private long helperUserSettingMappingId;

	@Column(name = "Active", nullable = false)
	private boolean active;

	@ManyToOne
	@JoinColumn(name = "HelperId")
	private Helper helper;

	@Column(name = "LastUpdateDateTime", length = 1)
	private Date lastUpdateDateTime;

	@Column(name = "UserId", nullable = false, length = 10)
	private String userId;

	@Column(name = "Checked" )
	private boolean checked;

	public HelperUserSettingMapping() {
	}

	public long getHelperUserSettingMappingId() {
		return this.helperUserSettingMappingId;
	}

	public void setHelperUserSettingMappingId(long helperUserSettingMappingId) {
		this.helperUserSettingMappingId = helperUserSettingMappingId;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Helper getHelper() {
		return this.helper;
	}

	public void setHelper(Helper helper) {
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

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
