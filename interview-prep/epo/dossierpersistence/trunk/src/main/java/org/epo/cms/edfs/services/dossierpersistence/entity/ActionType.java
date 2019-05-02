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
@Table(name = "ApplicationHistory.ActionType")
public class ActionType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888888888L;
	
	
	private int actionTypeId;
	private String actionTypeName;
	private String actionTypeDescription;
	
	private Set<Action> action;
	private String source;
	@Id
	@Column(name = "ACTIONTYPEID")
	@GeneratedValue
	public int getActionTypeId() {
		return actionTypeId;
	}

	public void setActionTypeId(int actionTypeId) {
		this.actionTypeId = actionTypeId;
	}

	public String getActionTypeName() {
		return actionTypeName;
	}

	public void setActionTypeName(String actionTypeName) {
		this.actionTypeName = actionTypeName;
	}

	public String getActionTypeDescription() {
		return actionTypeDescription;
	}

	public void setActionTypeDescription(String actionTypeDescription) {
		this.actionTypeDescription = actionTypeDescription;
	}

	@OneToMany(mappedBy = "actionType", cascade = CascadeType.ALL)
	public Set<Action> getAction() {
		return action;
	}

	public void setAction(Set<Action> action) {
		this.action = action;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}
