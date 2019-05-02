package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Notification.SysNotificationProfile")
public class SysNotificationProfile implements Serializable {

	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name= "SysNotificationProfileId")
	private Long sysNotificationProfileId;

	@Column(name= "IntrusivenessLevel")
	private String intrusivenessLevel;
	
	@Column(name= "Importance")
	private boolean importance;

	@Column(name= "Acknowledge")
	private boolean acknowledge;

	public Long getSysNotificationProfileId() {
		return sysNotificationProfileId;
	}

	public void setSysNotificationProfileId(Long sysNotificationProfileId) {
		this.sysNotificationProfileId = sysNotificationProfileId;
	}

	public String getIntrusivenessLevel() {
		return intrusivenessLevel;
	}

	public void setIntrusivenessLevel(String intrusivenessLevel) {
		this.intrusivenessLevel = intrusivenessLevel;
	}

	public boolean isImportance() {
		return importance;
	}

	public void setImportance(boolean importance) {
		this.importance = importance;
	}

	public boolean isAcknowledge() {
		return acknowledge;
	}

	public void setAcknowledge(boolean acknowledge) {
		this.acknowledge = acknowledge;
	}

		
}
