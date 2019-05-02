package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author dbawa
 *
 */

@Entity
@Table(name = "DocumentManagement.ReasonMaster")
public class ReasonMaster implements Serializable {

	private static final long serialVersionUID = 5164307489486361788L;

	@Id
	@Column(name = "ReasonId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reasonId;

	@Column(name = "Description")
	private String description;

	@OneToMany(mappedBy = "reasonMaster")
	private Set<Job> job;

	public Set<Job> getJob() {
		return job;
	}

	public void setJob(Set<Job> job) {
		this.job = job;
	}

	public int getReasonId() {
		return reasonId;
	}

	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
