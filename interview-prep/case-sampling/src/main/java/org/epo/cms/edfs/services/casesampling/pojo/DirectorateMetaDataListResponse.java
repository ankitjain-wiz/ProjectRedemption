package org.epo.cms.edfs.services.casesampling.pojo;

import java.util.List;

/**
 * This class returns the list of directorate's 
 * @author ankitjain2
 *
 */
public class DirectorateMetaDataListResponse {
	 private List<DirectorateMetaDataResponse> directorateMetaDataResponse;

	public List<DirectorateMetaDataResponse> getDirectorateMetaDataResponse() {
		return directorateMetaDataResponse;
	}

	public void setDirectorateMetaDataResponse(List<DirectorateMetaDataResponse> directorateMetaDataResponse) {
		this.directorateMetaDataResponse = directorateMetaDataResponse;
	}

}
