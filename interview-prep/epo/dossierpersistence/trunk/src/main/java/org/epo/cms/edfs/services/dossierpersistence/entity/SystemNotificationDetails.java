package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Notification.SystemNotification")
public class SystemNotificationDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name= "SysNotificationId")
	private Long sysNotificationId;
		
	@Column(name= "ReadFlag")
	private boolean readFlag;	
	
	@Column(name= "NotificationDatetime")
	private Date notificationDatetime;
	

	@Column(name = "MessageText")
	private String messageText;
	
	@Column(name= "Deleted")
	private boolean deleted;	

	@Column(name= "ImportantRecp")
	private boolean importantRecp;
	
	@ManyToOne
	@JoinColumn(name = "CMSCaseId")
	private CMSCaseMaster cmsCaseId;	
	
	@ManyToOne
	@JoinColumn(name = "EventId")
	private Event eventId;	
	
	@ManyToOne
	@JoinColumn(name = "ActionId")
	private Action action;
	@Column(name = "ReceiverID")
	private String receiverId;
	
	@Column(name= "AcknowledgeDatetime")
	private Timestamp acknowledgeDatetime;
	
	@ManyToOne
	@JoinColumn(name = "SysNotificationProfileId")
	private SysNotificationProfile sysNotificationProfileId;

	public Long getSysNotificationId() {
		return sysNotificationId;
	}

	public void setSysNotificationId(Long sysNotificationId) {
		this.sysNotificationId = sysNotificationId;
	}

	public boolean isReadFlag() {
		return readFlag;
	}

	public void setReadFlag(boolean readFlag) {
		this.readFlag = readFlag;
	}

	public Date getNotificationDatetime() {
		return notificationDatetime;
	}

	public void setNotificationDatetime(Date notificationDatetime) {
		this.notificationDatetime = notificationDatetime;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isImportantRecp() {
		return importantRecp;
	}

	public void setImportantRecp(boolean importantRecp) {
		this.importantRecp = importantRecp;
	}

	public CMSCaseMaster getCmsCaseId() {
		return cmsCaseId;
	}

	public void setCmsCaseId(CMSCaseMaster cmsCaseId) {
		this.cmsCaseId = cmsCaseId;
	}

	public Event getEventId() {
		return eventId;
	}

	public void setEventId(Event eventId) {
		this.eventId = eventId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public Timestamp getAcknowledgeDatetime() {
		return acknowledgeDatetime;
	}

	public void setAcknowledgeDatetime(Timestamp acknowledgeDatetime) {
		this.acknowledgeDatetime = acknowledgeDatetime;
	}

	public SysNotificationProfile getSysNotificationProfileId() {
		return sysNotificationProfileId;
	}

	public void setSysNotificationProfileId(SysNotificationProfile sysNotificationProfileId) {
		this.sysNotificationProfileId = sysNotificationProfileId;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	
	
}

