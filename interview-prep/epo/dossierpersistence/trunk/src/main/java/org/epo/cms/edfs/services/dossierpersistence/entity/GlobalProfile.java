package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the GlobalProfile database table.
 * 
 */
@Entity
@Table(name = "UserMgmt.GlobalProfile")
public class GlobalProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GlobalProfileId")
	private long globalProfileId;

	@ManyToOne
	@JoinColumn(name = "RoleId")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "WorkSpaceId")
	private WorkSpace workSpace;

	@OneToOne
	@JoinColumn(name = "ModuleId")
	private Module module;

	@Column(name = "AlertLevel", nullable = true, length = 50)
	private String alertLevel;

	@Column(name = "FontSize")
	private int fontSize;

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public long getGlobalProfileId() {
		return globalProfileId;
	}

	public void setGlobalProfileId(long globalProfileId) {
		this.globalProfileId = globalProfileId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public WorkSpace getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(WorkSpace workSpace) {
		this.workSpace = workSpace;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getAlertLevel() {
		return alertLevel;
	}

	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}

}
