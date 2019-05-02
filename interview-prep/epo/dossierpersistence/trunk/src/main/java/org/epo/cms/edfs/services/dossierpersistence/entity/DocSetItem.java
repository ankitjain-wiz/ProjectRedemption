package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DocumentManagement.DocSetItem")
public class DocSetItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private DocSetItemId docSetItemId;
	
	@ManyToOne
	@JoinColumn(name = "DocumentSetId",insertable = false ,updatable = false)
	private DocumentSet documentSet;
	
	@ManyToOne
	@JoinColumn(name = "DocCodeId",insertable = false ,updatable = false)
	private DocCodeMaster docCodeMaster;
	
	
	public DocumentSet getDocumentSet() {
		return documentSet;
	}

	public void setDocumentSet(DocumentSet documentSet) {
		this.documentSet = documentSet;
	}

	public DocCodeMaster getDocCodeMaster() {
		return docCodeMaster;
	}

	public void setDocCodeMaster(DocCodeMaster docCodeMaster) {
		this.docCodeMaster = docCodeMaster;
	}

	public DocSetItemId getDocSetItemId() {
		return docSetItemId;
	}

	public void setDocSetItemId(DocSetItemId docSetItemId) {
		this.docSetItemId = docSetItemId;
	}
		
}
