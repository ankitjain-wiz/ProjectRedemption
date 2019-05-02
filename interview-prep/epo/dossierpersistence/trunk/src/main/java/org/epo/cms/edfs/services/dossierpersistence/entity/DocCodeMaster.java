package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DocumentManagement.DocCodeMaster")
public class DocCodeMaster implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "DocCodeId")
	private int docCodeId;
	
	@Column(name = "DocCode")
	private String docCode;
	
	@Column(name = "DescEn")
	private String descEn;
	
	@Column(name = "DescFr")
	private String descFr;
	
	@Column(name = "DescDe")
	private String descDe;
	
	@OneToMany(mappedBy = "docCodeMaster")
	private Set<DocSetItem> docSetItem;
	
	@OneToMany(mappedBy = "docCodeMaster")
	private Set<CMSCaseQRCode> cmsCaseQRCode;
	
	public DocCodeMaster() {
		//Requires no-args constructor
	}

	public int getDocCodeId() {
		return docCodeId;
	}

	public void setDocCodeId(int docCodeId) {
		this.docCodeId = docCodeId;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getDescEn() {
		return descEn;
	}

	public void setDescEn(String descEn) {
		this.descEn = descEn;
	}

	public String getDescFr() {
		return descFr;
	}

	public void setDescFr(String descFr) {
		this.descFr = descFr;
	}

	public String getDescDe() {
		return descDe;
	}

	public void setDescDe(String descDe) {
		this.descDe = descDe;
	}

	public Set<DocSetItem> getDocSetItem() {
		return docSetItem;
	}

	public void setDocSetItem(Set<DocSetItem> docSetItem) {
		this.docSetItem = docSetItem;
	}

	public Set<CMSCaseQRCode> getCmsCaseQRCode() {
		return cmsCaseQRCode;
	}

	public void setCmsCaseQRCode(Set<CMSCaseQRCode> cmsCaseQRCode) {
		this.cmsCaseQRCode = cmsCaseQRCode;
	}

	
}
