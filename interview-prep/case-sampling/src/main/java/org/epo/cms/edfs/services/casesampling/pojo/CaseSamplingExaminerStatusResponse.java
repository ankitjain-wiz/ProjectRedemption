package org.epo.cms.edfs.services.casesampling.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class returns CaseSamplingExaminerStatusResponse (number sampled of given examiner)
 * @author ankitjain2
 *
 */
@JsonPropertyOrder({
"userId",
"numSampled",
})
public class CaseSamplingExaminerStatusResponse {
	
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("numSampled")
	private int numSampled;
	
	@JsonProperty("userId")
	public String getUserId() {
		return userId;
	}
	
	@JsonProperty("userId")
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@JsonProperty("numSampled")
	public int getNumSampled() {
		return numSampled;
	}
	
	@JsonProperty("numSampled")
	public void setNumSampled(int numSampled) {
		this.numSampled = numSampled;
	}
	

}
