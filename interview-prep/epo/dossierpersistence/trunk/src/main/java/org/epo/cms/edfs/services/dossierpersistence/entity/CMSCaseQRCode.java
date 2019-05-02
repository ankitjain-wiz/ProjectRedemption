package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DocumentManagement.CMSCaseQRCode")
public class CMSCaseQRCode implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CMSCaseQRCodeId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cmsCaseQRCodeId;

	@ManyToOne()
	@JoinColumn(name = "CMSCaseId")
	private CMSCaseMaster cmsCaseMaster;
	
	@ManyToOne()
	@JoinColumn(name = "DocCodeId")
	private DocCodeMaster docCodeMaster;

	@Column(name = "CreationDate")
	private Date creationDate;

	@Column(name = "TrackingCode")
	private String trackingCode;
	
	@Column(name = "UploadDatetime")
	private Date uploadDatetime;

	@Column(name = "CommentText")
	private String commentText;

	@Column(name = "UserID")
	private String userID;
	
	@Column(name = "QRStatus")
	private String qrStatus;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cmsCaseQRCode")
	private Set<UploadDoc> uploadDoc;

	public CMSCaseQRCode() {
		// Requires no-args constructor
	}
	
	public int getCMSCaseQRCodeId() {
		return cmsCaseQRCodeId;
	}

	public void setCmsCaseQRCodeId(int cmsCaseQRCodeId) {
		this.cmsCaseQRCodeId = cmsCaseQRCodeId;
	}

	public CMSCaseMaster getCmsCaseMaster() {
		return cmsCaseMaster;
	}

	public void setCmsCaseMaster(CMSCaseMaster cmsCaseMaster) {
		this.cmsCaseMaster = cmsCaseMaster;
	}

	public DocCodeMaster getDocCodeMaster() {
		return docCodeMaster;
	}

	public void setDocCodeMaster(DocCodeMaster docCodeMaster) {
		this.docCodeMaster = docCodeMaster;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getTrackingCode() {
		return trackingCode;
	}

	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}

	public Date getUploadDatetime() {
		return uploadDatetime;
	}

	public void setUploadDatetime(Date uploadDatetime) {
		this.uploadDatetime = uploadDatetime;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Set<UploadDoc> getUploadDoc() {
		return uploadDoc;
	}

	public void setUploadDoc(Set<UploadDoc> uploadDoc) {
		this.uploadDoc = uploadDoc;
	}

	public int getCmsCaseQRCodeId() {
		return cmsCaseQRCodeId;
	}

	public String getQrStatus() {
		return qrStatus;
	}

	public void setQrStatus(String qrStatus) {
		this.qrStatus = qrStatus;
	}
	
}
