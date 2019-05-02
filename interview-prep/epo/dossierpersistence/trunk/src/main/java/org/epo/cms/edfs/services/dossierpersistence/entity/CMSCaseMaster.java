package org.epo.cms.edfs.services.dossierpersistence.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CommonService.CMSCaseMaster")
public class CMSCaseMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CMSCaseId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cmsCaseId;
	
	@ManyToOne
	@JoinColumn(name = "ApplicationId")
	private Application application;
	
	@OneToOne
	//@PrimaryKeyJoinColumn
	@JoinColumn(name = "ProductOrderId")
	private ProductOrder productOrder;
	
	@OneToMany(mappedBy = "cmsCaseMaster")
	private Set<CMSCaseQRCode> cmsCaseQRCode;
	
	

	public int getCmsCaseId() {
		return cmsCaseId;
	}

	public void setCmsCaseId(int cmsCaseId) {
		this.cmsCaseId = cmsCaseId;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public ProductOrder getProductOrder() {
		return productOrder;
	}

	public void setProductOrder(ProductOrder productOrder) {
		this.productOrder = productOrder;
	}

	public Set<CMSCaseQRCode> getCmsCaseQRCode() {
		return cmsCaseQRCode;
	}

	public void setCmsCaseQRCode(Set<CMSCaseQRCode> cmsCaseQRCode) {
		this.cmsCaseQRCode = cmsCaseQRCode;
	}
	
}
