package org.epo.cms.edfs.services.common.externalapi;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
/**
 * This interface is to get responses from external APIs.
 */
public interface ExternalApiService {
	
	
	 /**
	  * This method is used to hit external APIs for POST request to 
	  * handle 201 status code returned from notification api
	 * @param host : String
	 * @param source : String
	 * @param requestEntity : HttpEntity<Object>
	 * @return ResponseEntity<String> : ResponseEntity<String>
	 * @throws CustomException handles custom Exception
	 */
	public ResponseEntity<String> postRequestForExternalApi(String host, String source,
		      HttpEntity<Object> requestEntity) throws CustomException;

  /**
   * This method is used to hit external APIs and get response.
   * 
   * @param host as String
   * @param source as String
   * @return String as value
   * @throws CustomException handles custom Exception
   */
   String getResponseFromExternalApi(final String host, final String source)
      throws CustomException;
   
   /**
    * This method is used to hit external APIs and post response.
    * @param host
    * @param source
    * @param requestEntity
    * @return
    * @throws CustomException handles custom Exception
    */
   ResponseEntity<String> postRequestToExternalApi(String host, String source,
		   HttpEntity<String> requestEntity) throws CustomException;

  /**
   * This method is used to hit external APIs for POST request
   * 
   * @param host of type String
   * @param source of type String
   * @param requestEntity of type HttpEntity
   * @return String as value
   * @throws CustomException handles custom exception
   */
   String getPostResponseFromExternalApi(final String host, final String source,
      final HttpEntity<MultiValueMap<String, Object>> requestEntity) throws CustomException;

  /**
   * Method is used to get data from CMS system.
   * 
   * @param cmsUri : uri
   * @param source : source
   * @return String : string of JSON
   * @throws CustomException : CustomException
   */
  String getCmsResponseUsingExternalApi(final String cmsUri, final String source)
      throws CustomException;

}
