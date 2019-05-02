package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DocumentManagement.UploadDoc")
public class UploadDoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "FileContent")
	private byte[] fileContent;

	@Column(name = "ReceiveDatetime")
	private Date receiveDatetime;

	@Column(name = "NoOfPages")
	private int noOfPages;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "JobId")
	private Job job;

	@ManyToOne()
	@JoinColumn(name = "CMSCaseQRCodeId")
	private CMSCaseQRCode cmsCaseQRCode;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public Date getReceiveDatetime() {
		return receiveDatetime;
	}

	public void setReceiveDatetime(Date receiveDatetime) {
		this.receiveDatetime = receiveDatetime;
	}

	public int getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public CMSCaseQRCode getCmsCaseQRCode() {
		return cmsCaseQRCode;
	}

	public void setCmsCaseQRCode(CMSCaseQRCode cmsCaseQRCode) {
		this.cmsCaseQRCode = cmsCaseQRCode;
	}

}
