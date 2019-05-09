package org.epo.cms.edfs.services.casesampling.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class takes input from Ui to updates the default counts and default workload counts 
 * @author ankitjain2
 *
 */
public class CountMetaDataRequest {
	
	@JsonProperty("NumMinYearTarget")
	private int numMinYearTarget;
	
	@JsonProperty("NumMaxYearTarget")
	private int numMaxYearTarget;
	
	@JsonProperty("NumMustSample")
	private int numMustSample;
	
	@JsonProperty("janWorkLoadCount")
	private int janWorkLoadCount;
	
	@JsonProperty("febWorkLoadCount")
	private int febWorkLoadCount;
	
	@JsonProperty("marWorkLoadCount")
	private int marWorkLoadCount;
	
	@JsonProperty("aprWorkLoadCount")
	private int aprWorkLoadCount;
	
	@JsonProperty("mayWorkLoadCount")
	private int mayWorkLoadCount;
	
	@JsonProperty("junWorkLoadCount")
	private int junWorkLoadCount;
	
	@JsonProperty("julWorkLoadCount")
	private int julWorkLoadCount;
	
	@JsonProperty("augWorkLoadCount")
	private int augWorkLoadCount;
	
	@JsonProperty("sepWorkLoadCount")
	private int sepWorkLoadCount;
	
	@JsonProperty("octWorkLoadCount")
	private int octWorkLoadCount;
	
	@JsonProperty("novWorkLoadCount")
	private int novWorkLoadCount;
	
	@JsonProperty("decWorkLoadCount")
	private int decWorkLoadCount;

	@JsonProperty("NumMinYearTarget")
	public int getNumMinYearTarget() {
		return numMinYearTarget;
	}

	@JsonProperty("NumMinYearTarget")
	public void setNumMinYearTarget(int numMinYearTarget) {
		this.numMinYearTarget = numMinYearTarget;
	}

	@JsonProperty("NumMaxYearTarget")
	public int getNumMaxYearTarget() {
		return numMaxYearTarget;
	}

	@JsonProperty("NumMaxYearTarget")
	public void setNumMaxYearTarget(int numMaxYearTarget) {
		this.numMaxYearTarget = numMaxYearTarget;
	}

	@JsonProperty("NumMustSample")
	public int getNumMustSample() {
		return numMustSample;
	}

	@JsonProperty("NumMustSample")
	public void setNumMustSample(int numMustSample) {
		this.numMustSample = numMustSample;
	}


	@JsonProperty("janWorkLoadCount")
	public int getJanWorkLoadCount() {
		return janWorkLoadCount;
	}

	@JsonProperty("janWorkLoadCount")
	public void setJanWorkLoadCount(int janWorkLoadCount) {
		this.janWorkLoadCount = janWorkLoadCount;
	}

	@JsonProperty("febWorkLoadCount")
	public int getFebWorkLoadCount() {
		return febWorkLoadCount;
	}

	@JsonProperty("febWorkLoadCount")
	public void setFebWorkLoadCount(int febWorkLoadCount) {
		this.febWorkLoadCount = febWorkLoadCount;
	}

	@JsonProperty("marWorkLoadCount")
	public int getMarWorkLoadCount() {
		return marWorkLoadCount;
	}

	@JsonProperty("marWorkLoadCount")
	public void setMarWorkLoadCount(int marWorkLoadCount) {
		this.marWorkLoadCount = marWorkLoadCount;
	}

	@JsonProperty("aprWorkLoadCount")
	public int getAprWorkLoadCount() {
		return aprWorkLoadCount;
	}

	@JsonProperty("aprWorkLoadCount")
	public void setAprWorkLoadCount(int aprWorkLoadCount) {
		this.aprWorkLoadCount = aprWorkLoadCount;
	}

	@JsonProperty("mayWorkLoadCount")
	public int getMayWorkLoadCount() {
		return mayWorkLoadCount;
	}

	@JsonProperty("mayWorkLoadCount")
	public void setMayWorkLoadCount(int mayWorkLoadCount) {
		this.mayWorkLoadCount = mayWorkLoadCount;
	}

	@JsonProperty("junWorkLoadCount")
	public int getJunWorkLoadCount() {
		return junWorkLoadCount;
	}

	@JsonProperty("junWorkLoadCount")
	public void setJunWorkLoadCount(int junWorkLoadCount) {
		this.junWorkLoadCount = junWorkLoadCount;
	}

	@JsonProperty("julWorkLoadCount")
	public int getJulWorkLoadCount() {
		return julWorkLoadCount;
	}

	@JsonProperty("julWorkLoadCount")
	public void setJulWorkLoadCount(int julWorkLoadCount) {
		this.julWorkLoadCount = julWorkLoadCount;
	}

	@JsonProperty("augWorkLoadCount")
	public int getAugWorkLoadCount() {
		return augWorkLoadCount;
	}

	@JsonProperty("augWorkLoadCount")
	public void setAugWorkLoadCount(int augWorkLoadCount) {
		this.augWorkLoadCount = augWorkLoadCount;
	}

	@JsonProperty("sepWorkLoadCount")
	public int getSepWorkLoadCount() {
		return sepWorkLoadCount;
	}

	@JsonProperty("sepWorkLoadCount")
	public void setSepWorkLoadCount(int sepWorkLoadCount) {
		this.sepWorkLoadCount = sepWorkLoadCount;
	}

	@JsonProperty("octWorkLoadCount")
	public int getOctWorkLoadCount() {
		return octWorkLoadCount;
	}

	@JsonProperty("octWorkLoadCount")
	public void setOctWorkLoadCount(int octWorkLoadCount) {
		this.octWorkLoadCount = octWorkLoadCount;
	}

	@JsonProperty("novWorkLoadCount")
	public int getNovWorkLoadCount() {
		return novWorkLoadCount;
	}

	@JsonProperty("novWorkLoadCount")
	public void setNovWorkLoadCount(int novWorkLoadCount) {
		this.novWorkLoadCount = novWorkLoadCount;
	}

	@JsonProperty("decWorkLoadCount")
	public int getDecWorkLoadCount() {
		return decWorkLoadCount;
	}

	@JsonProperty("decWorkLoadCount")
	public void setDecWorkLoadCount(int decWorkLoadCount) {
		this.decWorkLoadCount = decWorkLoadCount;
	}
	
	

}
