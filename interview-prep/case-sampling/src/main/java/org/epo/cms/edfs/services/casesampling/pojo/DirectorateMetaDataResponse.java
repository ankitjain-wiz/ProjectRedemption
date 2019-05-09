package org.epo.cms.edfs.services.casesampling.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class holds the data of particular Directorate 
 * @author ankitjain2
 *
 */


@JsonPropertyOrder({
"DirectorateId",
"DirectorateName",
"DirectorId"
})
public class DirectorateMetaDataResponse {
	
	@JsonProperty("DirectorateId")
	private long directorateId;
	
	@JsonProperty("DirectorateName")
	private String directorateName;
	
	@JsonProperty("DirectorId")
	private String directorId;

	@JsonProperty("DirectorateId")
	public long getDirectorateId() {
		return directorateId;
	}

	@JsonProperty("DirectorateId")
	public void setDirectorateId(long directorateId) {
		this.directorateId = directorateId;
	}

	@JsonProperty("DirectorateName")
	public String getDirectorateName() {
		return directorateName;
	}

	@JsonProperty("DirectorateName")
	public void setDirectorateName(String directorateName) {
		this.directorateName = directorateName;
	}

	@JsonProperty("DirectorId")
	public String getDirectorId() {
		return directorId;
	}

	@JsonProperty("DirectorId")
	public void setDirectorId(String directorId) {
		this.directorId = directorId;
	}
	
	

}
