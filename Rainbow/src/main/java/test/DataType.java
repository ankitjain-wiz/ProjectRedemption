package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataType", propOrder = { "requestType", "designApplication", "tradeMarkApplication",
		"otherApplication", "personsApplication", "appealApplicationDetails", "applicantDetails",
		"representativeDetails", "inspectionRequestApplication", "currentAccountApplication",
		"reimbursementApplication", "otherAttachmentsDetails", "proceedingLanguageCode", "comment",
		"signatoryDetails" })
@XmlRootElement(name = "DataType")
public class DataType {
	@XmlElement(name = "RequestType")
	protected String requestType;
	@XmlElement(name = "DesignApplication")
	protected String designApplication;
	@XmlElement(name = "TradeMarkApplication")
	protected String tradeMarkApplication;
	@XmlElement(name = "OtherApplication")
	protected String otherApplication;
	@XmlElement(name = "PersonsApplication")
	protected String personsApplication;
	@XmlElement(name = "AppealApplicationDetails")
	protected String appealApplicationDetails;
	@XmlElement(name = "ApplicantDetails")
	protected String applicantDetails;
	@XmlElement(name = "RepresentativeDetails")
	protected String representativeDetails;
	@XmlElement(name = "InspectionRequestApplication")
	protected String inspectionRequestApplication;
	@XmlElement(name = "CurrentAccountApplication")
	protected String currentAccountApplication;
	@XmlElement(name = "ReimbursementApplication")
	protected String reimbursementApplication;
	@XmlElement(name = "OtherAttachmentsDetails")
	protected String otherAttachmentsDetails;
	@XmlElement(name = "ProceedingLanguageCode")
	protected String proceedingLanguageCode;
	@XmlElement(name = "Comment")
	protected Comment comment;
	@XmlElement(name = "SignatoryDetails")
	protected String signatoryDetails;

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getDesignApplication() {
		return designApplication;
	}

	public void setDesignApplication(String designApplication) {
		this.designApplication = designApplication;
	}

	public String getTradeMarkApplication() {
		return tradeMarkApplication;
	}

	public void setTradeMarkApplication(String tradeMarkApplication) {
		this.tradeMarkApplication = tradeMarkApplication;
	}

	public String getOtherApplication() {
		return otherApplication;
	}

	public void setOtherApplication(String otherApplication) {
		this.otherApplication = otherApplication;
	}

	public String getPersonsApplication() {
		return personsApplication;
	}

	public void setPersonsApplication(String personsApplication) {
		this.personsApplication = personsApplication;
	}

	public String getAppealApplicationDetails() {
		return appealApplicationDetails;
	}

	public void setAppealApplicationDetails(String appealApplicationDetails) {
		this.appealApplicationDetails = appealApplicationDetails;
	}

	public String getApplicantDetails() {
		return applicantDetails;
	}

	public void setApplicantDetails(String applicantDetails) {
		this.applicantDetails = applicantDetails;
	}

	public String getRepresentativeDetails() {
		return representativeDetails;
	}

	public void setRepresentativeDetails(String representativeDetails) {
		this.representativeDetails = representativeDetails;
	}

	public String getInspectionRequestApplication() {
		return inspectionRequestApplication;
	}

	public void setInspectionRequestApplication(String inspectionRequestApplication) {
		this.inspectionRequestApplication = inspectionRequestApplication;
	}

	public String getCurrentAccountApplication() {
		return currentAccountApplication;
	}

	public void setCurrentAccountApplication(String currentAccountApplication) {
		this.currentAccountApplication = currentAccountApplication;
	}

	public String getReimbursementApplication() {
		return reimbursementApplication;
	}

	public void setReimbursementApplication(String reimbursementApplication) {
		this.reimbursementApplication = reimbursementApplication;
	}

	public String getOtherAttachmentsDetails() {
		return otherAttachmentsDetails;
	}

	public void setOtherAttachmentsDetails(String otherAttachmentsDetails) {
		this.otherAttachmentsDetails = otherAttachmentsDetails;
	}

	public String getProceedingLanguageCode() {
		return proceedingLanguageCode;
	}

	public void setProceedingLanguageCode(String proceedingLanguageCode) {
		this.proceedingLanguageCode = proceedingLanguageCode;
	}



	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public String getSignatoryDetails() {
		return signatoryDetails;
	}

	public void setSignatoryDetails(String signatoryDetails) {
		this.signatoryDetails = signatoryDetails;
	}

	@Override
	public String toString() {
		return "DataType [requestType=" + requestType + ", designApplication=" + designApplication
				+ ", tradeMarkApplication=" + tradeMarkApplication + ", otherApplication=" + otherApplication
				+ ", personsApplication=" + personsApplication + ", appealApplicationDetails="
				+ appealApplicationDetails + ", applicantDetails=" + applicantDetails + ", representativeDetails="
				+ representativeDetails + ", inspectionRequestApplication=" + inspectionRequestApplication
				+ ", currentAccountApplication=" + currentAccountApplication + ", reimbursementApplication="
				+ reimbursementApplication + ", otherAttachmentsDetails=" + otherAttachmentsDetails
				+ ", proceedingLanguageCode=" + proceedingLanguageCode + ", comment=[" + comment.getCommentOne() +" " +comment.getCommentTwo() + "], signatoryDetails="
				+ signatoryDetails + "]";
	}

	

}
