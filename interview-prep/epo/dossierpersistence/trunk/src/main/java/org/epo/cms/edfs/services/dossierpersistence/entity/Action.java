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
@Table(name = "ApplicationHistory.Action")
public class Action implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888888888L;
	
	
	private int actionId;
	private Date actionDateTime;
	private String cmsCaseId;
	private String userId;
	private String commentText;
	private ActionType actionType;
	
	@Id
	@Column(name = "ACTIONID")
	@GeneratedValue
	public int getActionId() {
		return actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	public Date getActionDateTime() {
		return actionDateTime;
	}
	public void setActionDateTime(Date actionDateTime) {
		this.actionDateTime = actionDateTime;
	}
	public String getCmsCaseId() {
		return cmsCaseId;
	}
	public void setCmsCaseId(String cmsCaseId) {
		this.cmsCaseId = cmsCaseId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ACTIONTYPEID")
	public ActionType getActionType() {
		return actionType;
	}
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	
	
}
