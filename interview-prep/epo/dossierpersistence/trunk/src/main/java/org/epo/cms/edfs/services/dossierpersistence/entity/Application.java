package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CommonService.Application")
public class Application implements Serializable{
	private static final long serialVersionUID = 1888888888L;
	
	@Id
	@Column(name = "ApplicationId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int applicationId;

	@Column(name = "ApplicationNumber")
	private String applicationNumber;

	@Column(name = "ApplicationType")
	private String applicationType;

	@Column(name = "ReceiptDatetime")
	private Date receiptDatetime;

	@Column(name = "FilingDatetime")
	private Date filingDatetime;

	@Column(name = "PublicationNumber")
	private String publicationNumber;

	@Column(name = "FilingLanguage")
	private String filingLanguage;

	@Column(name = "ProceduralLanguage")
	private String proceduralLanguage;

	@Column(name = "FirstAbstractFigure")
	private String firstAbstractFigure;
	
	@OneToMany(mappedBy = "application")
	private Set<CMSCaseMaster> cmsCaseMaster;
	
	@OneToMany(mappedBy = "application")
	private Set<ProductOrder> productOrder;
	
	@Column(name = "ApplicationId")
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public Date getReceiptDatetime() {
		return receiptDatetime;
	}
	public void setReceiptDatetime(Date receiptDatetime) {
		this.receiptDatetime = receiptDatetime;
	}
	public Date getFilingDatetime() {
		return filingDatetime;
	}
	public void setFilingDatetime(Date filingDatetime) {
		this.filingDatetime = filingDatetime;
	}
	public String getPublicationNumber() {
		return publicationNumber;
	}
	public void setPublicationNumber(String publicationNumber) {
		this.publicationNumber = publicationNumber;
	}
	public String getFilingLanguage() {
		return filingLanguage;
	}
	public void setFilingLanguage(String filingLanguage) {
		this.filingLanguage = filingLanguage;
	}
	public String getProceduralLanguage() {
		return proceduralLanguage;
	}
	public void setProceduralLanguage(String proceduralLanguage) {
		this.proceduralLanguage = proceduralLanguage;
	}
	public String getFirstAbstractFigure() {
		return firstAbstractFigure;
	}
	public void setFirstAbstractFigure(String firstAbstractFigure) {
		this.firstAbstractFigure = firstAbstractFigure;
	}
	
	//@OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
	//@JoinColumn(name = "ApplicationId")
	public Set<ProductOrder> getProductOrder() {
		return productOrder;
	}
	public void setProductOrder(Set<ProductOrder> productOrder) {
		this.productOrder = productOrder;
	}
	
	//@OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
	//@JoinColumn(name = "ApplicationId")
	public Set<CMSCaseMaster> getCmsCaseMaster() {
		return cmsCaseMaster;
	}
	public void setCmsCaseMaster(Set<CMSCaseMaster> cmsCaseMaster) {
		this.cmsCaseMaster = cmsCaseMaster;
	}
}
