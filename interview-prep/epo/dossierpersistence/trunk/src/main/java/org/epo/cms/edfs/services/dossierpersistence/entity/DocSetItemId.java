package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocSetItemId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "DocumentSetId")
	private int documentSetId;
	
	@Column(name = "DocCodeId")
	private int docCodeId;

	public int getDocumentSetId() {
		return documentSetId;
	}

	public void setDocumentSetId(int documentSetId) {
		this.documentSetId = documentSetId;
	}

	public int getDocCodeId() {
		return docCodeId;
	}

	public void setDocCodeId(int docCodeId) {
		this.docCodeId = docCodeId;
	}

}
