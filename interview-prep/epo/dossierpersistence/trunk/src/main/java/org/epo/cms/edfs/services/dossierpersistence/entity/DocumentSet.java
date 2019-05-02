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

@Entity
@Table(name = "DocumentManagement.DocumentSet")
public class DocumentSet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "DocumentSetId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int documentSetId;
	
	@Column(name = "DocumentSetType")
	private String documentSetType;
	
	@OneToMany(mappedBy = "documentSet")
	private Set<DocSetItem> docSetItem;
	
	

	public int getDocumentSetId() {
		return documentSetId;
	}

	public void setDocumentSetId(int documentSetId) {
		this.documentSetId = documentSetId;
	}

	public String getDocumentSetType() {
		return documentSetType;
	}

	public void setDocumentSetType(String documentSetType) {
		this.documentSetType = documentSetType;
	}

	public Set<DocSetItem> getDocSetItem() {
		return docSetItem;
	}

	public void setDocSetItem(Set<DocSetItem> docSetItem) {
		this.docSetItem = docSetItem;
	}

}
