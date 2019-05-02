package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ApplicationHistory.EventType")
public class EventType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888888888L;

	private int eventTypeId;
	private String eventTypeName;
	private String EventTypeDescription;
	private String source;
	private Set<Event> event;
	private String eventTypeCode;
	
	
	@Id
	@Column(name = "EventTypeId")
	@GeneratedValue
	public int getEventTypeId() {
		return eventTypeId;
	}
	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	public String getEventTypeName() {
		return eventTypeName;
	}
	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}
	public String getEventTypeDescription() {
		return EventTypeDescription;
	}
	public void setEventTypeDescription(String eventTypeDescription) {
		EventTypeDescription = eventTypeDescription;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	@OneToMany(mappedBy = "eventType", cascade = CascadeType.ALL)
	public Set<Event> getEvent() {
		return event;
	}
	public void setEvent(Set<Event> event) {
		this.event = event;
	}
	public String getEventTypeCode() {
		return eventTypeCode;
	}
	public void setEventTypeCode(String eventTypeCode) {
		this.eventTypeCode = eventTypeCode;
	}
	
	
}
