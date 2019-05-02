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
@Table(name = "DocumentManagement.Job")
public class Job implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "JobId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int jobId;

	@Column(name = "JobName")
	private String jobName;

	@Column(name = "Status")
	private String status;

	@Column(name = "RetryCount")
	private int retryCount;

	@Column(name = "ProcessedDatetime")
	private Date processedDatetime;

	@Column(name = "FailedDatetime")
	private Date failedDatetime;
	
	@Column(name = "NotificationStatus")
	private String notificationStatus;

	@ManyToOne()
	@JoinColumn(name = "reasonId")
	private ReasonMaster reasonMaster;

	
	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public Date getProcessedDatetime() {
		return processedDatetime;
	}

	public void setProcessedDatetime(Date processedDatetime) {
		this.processedDatetime = processedDatetime;
	}

	public Date getFailedDatetime() {
		return failedDatetime;
	}

	public void setFailedDatetime(Date failedDatetime) {
		this.failedDatetime = failedDatetime;
	}	

	public String getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public ReasonMaster getReasonMaster() {
		return reasonMaster;
	}

	public void setReasonMaster(ReasonMaster reasonMaster) {
		this.reasonMaster = reasonMaster;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((failedDatetime == null) ? 0 : failedDatetime.hashCode());
		result = prime * result + jobId;
		result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
		result = prime * result + ((notificationStatus == null) ? 0 : notificationStatus.hashCode());
		result = prime * result + ((processedDatetime == null) ? 0 : processedDatetime.hashCode());
		result = prime * result + ((reasonMaster == null) ? 0 : reasonMaster.hashCode());
		result = prime * result + retryCount;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (failedDatetime == null) {
			if (other.failedDatetime != null)
				return false;
		} else if (!failedDatetime.equals(other.failedDatetime))
			return false;
		if (jobId != other.jobId)
			return false;
		if (jobName == null) {
			if (other.jobName != null)
				return false;
		} else if (!jobName.equals(other.jobName))
			return false;
		if (notificationStatus == null) {
			if (other.notificationStatus != null)
				return false;
		} else if (!notificationStatus.equals(other.notificationStatus))
			return false;
		if (processedDatetime == null) {
			if (other.processedDatetime != null)
				return false;
		} else if (!processedDatetime.equals(other.processedDatetime))
			return false;
		if (reasonMaster == null) {
			if (other.reasonMaster != null)
				return false;
		} else if (!reasonMaster.equals(other.reasonMaster))
			return false;
		if (retryCount != other.retryCount)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
}
