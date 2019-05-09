package org.epo.cms.edfs.services.casesampling.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;



/**
 * This class returns the default counts and workload settings counts of every month
 * @author ankitjain2
 *
 */

@JsonPropertyOrder({
"NumMinYearTarget",
"NumMaxYearTarget",
"NumMustSample",

"JanWorkLoadCount",
"FebWorkLoadCount",
"MarWorkLoadCount",
"AprWorkLoadCount",
"MayWorkLoadCount",
"JunWorkLoadCount",
"JulWorkLoadCount",
"AugWorkLoadCount",
"SepWorkLoadCount",
"OctWorkLoadCount",
"NovWorkLoadCount",
"DecWorkLoadCount",

"JanActualCount",
"FebActualCount",
"MarActualCount",
"AprActualCount",
"MayActualCount",
"JunActualCount",
"JulActualCount",
"AugActualCount",
"SepActualCount",
"OctActualCount",
"NovActualCount",
"DecActualCount",
"caseSamplingExaminerStatusResponse"


})
public class CountMetaDataResponse {

	@JsonProperty("NumMinYearTarget")
	private int numMinYearTarget;

	@JsonProperty("NumMaxYearTarget")
	private int numMaxYearTarget;

	@JsonProperty("NumMustSample")
	private int numMustSample;

	@JsonProperty("JanWorkLoadCount")
	private int janWorkLoadCount;

	@JsonProperty("FebWorkLoadCount")
	private int febWorkLoadCount;

	@JsonProperty("MarWorkLoadCount")
	private int marWorkLoadCount;

	@JsonProperty("AprWorkLoadCount")
	private int aprWorkLoadCount;

	@JsonProperty("MayWorkLoadCount")
	private int mayWorkLoadCount;

	@JsonProperty("JunWorkLoadCount")
	private int junWorkLoadCount;

	@JsonProperty("JulWorkLoadCount")
	private int julWorkLoadCount;

	@JsonProperty("AugWorkLoadCount")
	private int augWorkLoadCount;

	@JsonProperty("SepWorkLoadCount")
	private int sepWorkLoadCount;

	@JsonProperty("OctWorkLoadCount")
	private int octWorkLoadCount;

	@JsonProperty("NovWorkLoadCount")
	private int novWorkLoadCount;

	@JsonProperty("DecWorkLoadCount")
	private int decWorkLoadCount;
	
	@JsonProperty("JanActualCount")
	private int janActualCount;
	
	@JsonProperty("FebActualCount")
	private int febActualCount;
	
	@JsonProperty("MarActualCount")
	private int marActualCount;
	
	@JsonProperty("AprActualCount")
	private int aprActualCount;
	
	@JsonProperty("MayActualCount")
	private int mayActualCount;
	
	@JsonProperty("JunActualCount")
	private int junActualCount;
	
	@JsonProperty("JulActualCount")
	private int julActualCount;
	
	@JsonProperty("AugActualCount")
	private int augActualCount;
	
	@JsonProperty("SepActualCount")
	private int sepActualCount;
	
	@JsonProperty("OctActualCount")
	private int octActualCount;
	
	@JsonProperty("NovActualCount")
	private int novActualCount;
	
	@JsonProperty("DecActualCount")
	private int decActualCount;
	
	@JsonProperty("caseSamplingExaminerStatusResponse")
	List<CaseSamplingExaminerStatusResponse> caseSamplingExaminerStatusResponse;

	@JsonProperty("caseSamplingExaminerStatusResponse")
	public List<CaseSamplingExaminerStatusResponse> getCaseSamplingExaminerStatusResponse() {
		return caseSamplingExaminerStatusResponse;
	}

	@JsonProperty("caseSamplingExaminerStatusResponse")
	public void setCaseSamplingExaminerStatusResponse(
			List<CaseSamplingExaminerStatusResponse> caseSamplingExaminerStatusResponse) {
		this.caseSamplingExaminerStatusResponse = caseSamplingExaminerStatusResponse;
	}

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

	@JsonProperty("JanWorkLoadCount")
	public int getJanWorkLoadCount() {
		return janWorkLoadCount;
	}

	@JsonProperty("JanWorkLoadCount")
	public void setJanWorkLoadCount(int janWorkLoadCount) {
		this.janWorkLoadCount = janWorkLoadCount;
	}

	@JsonProperty("FebWorkLoadCount")
	public int getFebWorkLoadCount() {
		return febWorkLoadCount;
	}

	@JsonProperty("FebWorkLoadCount")
	public void setFebWorkLoadCount(int febWorkLoadCount) {
		this.febWorkLoadCount = febWorkLoadCount;
	}

	@JsonProperty("MarWorkLoadCount")
	public int getMarWorkLoadCount() {
		return marWorkLoadCount;
	}

	@JsonProperty("MarWorkLoadCount")
	public void setMarWorkLoadCount(int marWorkLoadCount) {
		this.marWorkLoadCount = marWorkLoadCount;
	}

	@JsonProperty("AprWorkLoadCount")
	public int getAprWorkLoadCount() {
		return aprWorkLoadCount;
	}

	@JsonProperty("AprWorkLoadCount")
	public void setAprWorkLoadCount(int aprWorkLoadCount) {
		this.aprWorkLoadCount = aprWorkLoadCount;
	}

	@JsonProperty("MayWorkLoadCount")
	public int getMayWorkLoadCount() {
		return mayWorkLoadCount;
	}

	@JsonProperty("MayWorkLoadCount")
	public void setMayWorkLoadCount(int mayWorkLoadCount) {
		this.mayWorkLoadCount = mayWorkLoadCount;
	}

	@JsonProperty("JunWorkLoadCount")
	public int getJunWorkLoadCount() {
		return junWorkLoadCount;
	}

	@JsonProperty("JunWorkLoadCount")
	public void setJunWorkLoadCount(int junWorkLoadCount) {
		this.junWorkLoadCount = junWorkLoadCount;
	}

	@JsonProperty("JulWorkLoadCount")
	public int getJulWorkLoadCount() {
		return julWorkLoadCount;
	}

	@JsonProperty("JulWorkLoadCount")
	public void setJulWorkLoadCount(int julWorkLoadCount) {
		this.julWorkLoadCount = julWorkLoadCount;
	}

	@JsonProperty("AugWorkLoadCount")
	public int getAugWorkLoadCount() {
		return augWorkLoadCount;
	}

	@JsonProperty("AugWorkLoadCount")
	public void setAugWorkLoadCount(int augWorkLoadCount) {
		this.augWorkLoadCount = augWorkLoadCount;
	}

	@JsonProperty("SepWorkLoadCount")
	public int getSepWorkLoadCount() {
		return sepWorkLoadCount;
	}

	@JsonProperty("SepWorkLoadCount")
	public void setSepWorkLoadCount(int sepWorkLoadCount) {
		this.sepWorkLoadCount = sepWorkLoadCount;
	}

	@JsonProperty("OctWorkLoadCount")
	public int getOctWorkLoadCount() {
		return octWorkLoadCount;
	}

	@JsonProperty("OctWorkLoadCount")
	public void setOctWorkLoadCount(int octWorkLoadCount) {
		this.octWorkLoadCount = octWorkLoadCount;
	}

	@JsonProperty("NovWorkLoadCount")
	public int getNovWorkLoadCount() {
		return novWorkLoadCount;
	}

	@JsonProperty("NovWorkLoadCount")
	public void setNovWorkLoadCount(int novWorkLoadCount) {
		this.novWorkLoadCount = novWorkLoadCount;
	}

	@JsonProperty("DecWorkLoadCount")
	public int getDecWorkLoadCount() {
		return decWorkLoadCount;
	}

	@JsonProperty("DecWorkLoadCount")
	public void setDecWorkLoadCount(int decWorkLoadCount) {
		this.decWorkLoadCount = decWorkLoadCount;
	}

	@JsonProperty("JanActualCount")
	public int getJanActualCount() {
		return janActualCount;
	}

	@JsonProperty("JanActualCount")
	public void setJanActualCount(int janActualCount) {
		this.janActualCount = janActualCount;
	}

	@JsonProperty("FebActualCount")
	public int getFebActualCount() {
		return febActualCount;
	}

	@JsonProperty("FebActualCount")
	public void setFebActualCount(int febActualCount) {
		this.febActualCount = febActualCount;
	}

	@JsonProperty("MarActualCount")
	public int getMarActualCount() {
		return marActualCount;
	}

	@JsonProperty("MarActualCount")
	public void setMarActualCount(int marActualCount) {
		this.marActualCount = marActualCount;
	}

	@JsonProperty("AprActualCount")
	public int getAprActualCount() {
		return aprActualCount;
	}

	@JsonProperty("AprActualCount")
	public void setAprActualCount(int aprActualCount) {
		this.aprActualCount = aprActualCount;
	}

	@JsonProperty("MayActualCount")
	public int getMayActualCount() {
		return mayActualCount;
	}

	@JsonProperty("MayActualCount")
	public void setMayActualCount(int mayActualCount) {
		this.mayActualCount = mayActualCount;
	}

	@JsonProperty("JunActualCount")
	public int getJunActualCount() {
		return junActualCount;
	}

	@JsonProperty("JunActualCount")
	public void setJunActualCount(int junActualCount) {
		this.junActualCount = junActualCount;
	}

	@JsonProperty("JulActualCount")
	public int getJulActualCount() {
		return julActualCount;
	}

	@JsonProperty("JulActualCount")
	public void setJulActualCount(int julActualCount) {
		this.julActualCount = julActualCount;
	}

	@JsonProperty("AugActualCount")
	public int getAugActualCount() {
		return augActualCount;
	}

	@JsonProperty("AugActualCount")
	public void setAugActualCount(int augActualCount) {
		this.augActualCount = augActualCount;
	}

	@JsonProperty("SepActualCount")
	public int getSepActualCount() {
		return sepActualCount;
	}

	@JsonProperty("SepActualCount")
	public void setSepActualCount(int sepActualCount) {
		this.sepActualCount = sepActualCount;
	}

	@JsonProperty("OctActualCount")
	public int getOctActualCount() {
		return octActualCount;
	}

	@JsonProperty("OctActualCount")
	public void setOctActualCount(int octActualCount) {
		this.octActualCount = octActualCount;
	}

	@JsonProperty("NovActualCount")
	public int getNovActualCount() {
		return novActualCount;
	}

	@JsonProperty("NovActualCount")
	public void setNovActualCount(int novActualCount) {
		this.novActualCount = novActualCount;
	}

	@JsonProperty("DecActualCount")
	public int getDecActualCount() {
		return decActualCount;
	}

	@JsonProperty("DecActualCount")
	public void setDecActualCount(int decActualCount) {
		this.decActualCount = decActualCount;
	}
	

	

	
	
	
}
