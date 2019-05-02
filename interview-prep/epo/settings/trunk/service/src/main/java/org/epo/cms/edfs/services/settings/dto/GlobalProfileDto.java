package org.epo.cms.edfs.services.settings.dto;

import java.io.Serializable;

/**
 * Dto for GlobalProfile entity.
 * 
 * @author bkumar
 *
 */
public class GlobalProfileDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private long globalProfileId;

	private RoleDto role;

	private WorkSpaceDto workSpace;

	private ModuleDto module;

	private String alertLevel;

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

	public RoleDto getRole() {
		return role;
	}

	public void setRole(RoleDto role) {
		this.role = role;
	}

	public WorkSpaceDto getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(WorkSpaceDto workSpace) {
		this.workSpace = workSpace;
	}

	public ModuleDto getModule() {
		return module;
	}

	public void setModule(ModuleDto module) {
		this.module = module;
	}

	public String getAlertLevel() {
		return alertLevel;
	}

	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}

}
