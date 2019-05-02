package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CommonService.ProductOrder")
public class ProductOrder implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ProductOrderId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productOrderId;
	
	@Column(name = "DossierNumber")
	private String dossierNumber;
	
	@Column(name  = "Phase")
	private String phase;
	
	@Column(name = "ProductType")
	private String productType;
	
	@ManyToOne
	@JoinColumn(name = "ApplicationId")
	private Application application;
	
	@Column(name = "TaskId")
	private int taskId;
	
	

	public int getProductOrderId() {
		return productOrderId;
	}

	public void setProductOrderId(int productOrderId) {
		this.productOrderId = productOrderId;
	}

	public String getDossierNumber() {
		return dossierNumber;
	}

	public void setDossierNumber(String dossierNumber) {
		this.dossierNumber = dossierNumber;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}


}
