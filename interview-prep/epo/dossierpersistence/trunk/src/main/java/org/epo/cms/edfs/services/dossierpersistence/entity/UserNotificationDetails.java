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

@Entity
@Table(name = "Notification.UserNotification")
public class UserNotificationDetails implements Serializable {

	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name= "NotificationId")
	private Long notificationId;

	@ManyToOne
	@JoinColumn(name="CMSCaseId")
	private CMSCaseMaster cmsCaseId;
	
	@Column(name= "DialogId")
	private Long dialogId;
	
	@Column(name= "SubjectText")
	private String subjectText;	
	
	@Column(name= "NotificationDatetime")
	private Date notificationDatetime;	

	@Column(name= "SenderId")
	private String senderId;
	
	@Column(name= "MessageText")
	private String messageText;
	

	@Column(name= "ImportantSend")
	private boolean importantSend;	
	
	@Column(name= "ImportantRecp")
	private boolean importantRecp;	
	
	@Column(name= "ReceiverID")
	private String receiverId;
	
	@Column(name= "ReadFlagRecp")
	private boolean readFlagRecp;
	@Column(name= "ReadFlagSend")
	private boolean readFlagSend ;	
	@Column(name= "DeletedRecp")
	private boolean deletedRecp;
	
	@Column(name= "DeletedSend")
	private boolean deletedSend;
	@Column(name= "PreviousDialogId")
	private Long previousDialogId;

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public Long getDialogId() {
		return dialogId;
	}

	public void setDialogId(Long dialogId) {
		this.dialogId = dialogId;
	}

	

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	

	public String getSubjectText() {
		return subjectText;
	}

	public void setSubjectText(String subjectText) {
		this.subjectText = subjectText;
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


	public boolean isImportantSend() {
		return importantSend;
	}

	public void setImportantSend(boolean importantSend) {
		this.importantSend = importantSend;
	}

	public boolean isImportantRecp() {
		return importantRecp;
	}

	public void setImportantRecp(boolean importantRecp) {
		this.importantRecp = importantRecp;
	}

	

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public boolean isReadFlagRecp() {
		return readFlagRecp;
	}
	public void setReadFlagRecp(boolean readFlagRecp) {
		this.readFlagRecp = readFlagRecp;
	}
	public boolean isReadFlagSend() {
		return readFlagSend;
	}
	public void setReadFlagSend(boolean readFlagSend) {
		this.readFlagSend = readFlagSend;
	}
	public boolean isDeletedRecp() {
		return deletedRecp;
	}

	public void setDeletedRecp(boolean deletedRecp) {
		this.deletedRecp = deletedRecp;
	}
	public boolean isDeletedSend() {
		return deletedSend;
	}
	public void setDeletedSend(boolean deletedSend) {
		this.deletedSend = deletedSend;
	}
	public Long getPreviousDialogId() {
		return previousDialogId;
	}
	public void setPreviousDialogId(Long previousDialogId) {
		this.previousDialogId = previousDialogId;
	}
	public CMSCaseMaster getCmsCaseId() {
		return cmsCaseId;
	}
	public void setCmsCaseId(CMSCaseMaster cmsCaseId) {
		this.cmsCaseId = cmsCaseId;
	}	
	
}
