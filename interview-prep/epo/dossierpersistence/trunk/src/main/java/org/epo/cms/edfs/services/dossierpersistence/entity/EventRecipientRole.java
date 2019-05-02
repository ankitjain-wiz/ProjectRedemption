package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DossierHistory Entity bean
 * 
 * @author narsing
 *
 */
@Entity
@Table(name = "Notification.EventRecipientRole")
public class EventRecipientRole implements Serializable{
	
	private static final long serialVersionUID = 1888888888L;
  	
	@EmbeddedId
	private EventRecipientRoleId eventRecipientRoleId;
	
	@ManyToOne
	@JoinColumn(name = "SysNotificationProfileId", insertable = false ,updatable = false)
	private SysNotificationProfile sysNotificationProfileId;
	
	@ManyToOne
	@JoinColumn(name = "EventTypeId", insertable = false ,updatable = false)
	private EventType eventTypeId;
		
	@ManyToOne
	@JoinColumn(name = "RoleId", insertable = false ,updatable = false)
	private Role roleId;

	public SysNotificationProfile getSysNotificationProfileId() {
		return sysNotificationProfileId;
	}

	public void setSysNotificationProfileId(SysNotificationProfile sysNotificationProfileId) {
		this.sysNotificationProfileId = sysNotificationProfileId;
	}

	public EventType getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(EventType eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public EventRecipientRoleId getEventRecipientRoleId() {
		return eventRecipientRoleId;
	}

	public void setEventRecipientRoleId(EventRecipientRoleId eventRecipientRoleId) {
		this.eventRecipientRoleId = eventRecipientRoleId;
	}
	
	
	
	
}

