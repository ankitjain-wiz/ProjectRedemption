package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable
public class EventRecipientRoleId implements Serializable{
	
	private static final long serialVersionUID = 1888888888L;
  	

	@Column(name = "SysNotificationProfileId")
	private long sysNotificationProfileId;
	
	
	@Column(name = "EventTypeId")
	private int eventTypeId;
		
	
	@Column(name = "RoleId")
	private long roleId;


	public long getSysNotificationProfileId() {
		return sysNotificationProfileId;
	}


	public void setSysNotificationProfileId(long sysNotificationProfileId) {
		this.sysNotificationProfileId = sysNotificationProfileId;
	}


	public int getEventTypeId() {
		return eventTypeId;
	}


	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}


	public long getRoleId() {
		return roleId;
	}


	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	
	
	
}

