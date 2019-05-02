package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ApplicationHistory.Event")
public class Event implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888888888L;

	private int evemtId;
	private Date eventDateTime;
	private String cmsCaseId;
	private EventType eventType;
	
	@Id
	@Column(name = "EVENTID")
	@GeneratedValue
	public int getEvemtId() {
		return evemtId;
	}
	public void setEvemtId(int evemtId) {
		this.evemtId = evemtId;
	}
	public Date getEventDateTime() {
		return eventDateTime;
	}
	public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}
	public String getCmsCaseId() {
		return cmsCaseId;
	}
	public void setCmsCaseId(String cmsCaseId) {
		this.cmsCaseId = cmsCaseId;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EVENTTYPEID")
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
}
