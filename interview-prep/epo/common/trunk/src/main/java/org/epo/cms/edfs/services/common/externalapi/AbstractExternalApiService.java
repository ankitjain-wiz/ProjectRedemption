package org.epo.cms.edfs.services.common.externalapi;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ExceptionHandlerBean;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.useridrole.UserAdminRole;
import org.epo.cms.edfs.services.common.util.Constants;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Abstract base class for the different implementation of the ExternalApiService interface.
 * 
 * @author PV53311
 *
 */
abstract class AbstractExternalApiService implements ExternalApiService {

  protected static final Logger LOGGER = LogManager.getLogger();
  private static final String ERROR_DETAILS = "Error Details: ";
  @Autowired
  protected ResponseValidator responseValidator;
  @Autowired
  protected PropertyFileReader propertyFileReader;
  


  protected AbstractExternalApiService() {
    super();
  }

  /**
   * This method is used to set connection timeout if the external API does not responds within
   * given limited time.
   * 
   * @return RestTemplate as value
   * @throws CustomException handles custom exception
   * 
   */
  protected abstract RestTemplate getConnection() throws CustomException;

  @Override
  public String getResponseFromExternalApi(String host, String source) throws CustomException {
	  
	  
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
    String responseBody = null;
    //RestTemplate restTemplate = getConnection(); 
    RestTemplate restTemplate=new RestTemplate();
    ExceptionHandlerBean bean = new ExceptionHandlerBean();

    try {
      LOGGER.debug("Call to externl API getResponseFromExternalApi : ", Calendar.getInstance().getTime());
      LOGGER.debug("RequestEntity value in getResponseFromExternalApi : ", entity.getBody());
      ResponseEntity<String> response =
          restTemplate.exchange(host, HttpMethod.GET, entity, String.class);
      LOGGER.debug("Call after externl API : ", Calendar.getInstance().getTime());
      responseBody = response.getBody();
      LOGGER.debug("ResponseEntity value in getResponseFromExternalApi : ", responseBody);
      if (HttpStatus.OK.equals(response.getStatusCode())) {
        return responseBody;
      } else {
        bean.setId(response.getStatusCode().toString());
        bean.setStatusCode(response.getStatusCode().toString());
        bean.setDetails(response.getStatusCode().getReasonPhrase());
        LOGGER.debug("Response is not OK from externl APIgetPostResponseFromExternalApi : ", bean.toString());
        throw new CustomException(bean);
      }
    } catch (RestClientException e) {
      HttpClientErrorException httpClientErrorException;
      ExceptionHandlerBean exBean;
      if (e.getMostSpecificCause() instanceof HttpClientErrorException) {
        httpClientErrorException = (HttpClientErrorException) e.getMostSpecificCause();
        exBean = responseValidator.getErrorResponse(httpClientErrorException.getRawStatusCode());
      } else {
        exBean = responseValidator.getErrorResponse(Integer.parseInt(Constants.NOT_FOUND));
      }
      LOGGER.error(propertyFileReader.getValue(Constants.API_ACCESS_ERROR_DETAILS) + source
          + ERROR_DETAILS + e);
      throw new CustomException(exBean);
    } 
  }

  @Override
  public ResponseEntity<String> postRequestToExternalApi(String host, String source,
      HttpEntity<String> requestEntity) throws CustomException {
    ExceptionHandlerBean bean = new ExceptionHandlerBean();
    try {
    	LOGGER.debug("Call to externl API postRequestToExternalApi : ", Calendar.getInstance().getTime());
        LOGGER.debug("RequestEntity value in postRequestToExternalApi : ", requestEntity.getBody());
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> response =
          restTemplate.exchange(host, HttpMethod.POST, requestEntity, String.class);
      LOGGER.debug("Exit from externl API postRequestToExternalApi : ", Calendar.getInstance().getTime());
      LOGGER.debug("Response value in postRequestToExternalApi : ", response.getBody());
      if (response.getStatusCode().equals(HttpStatus.OK)) {
        return response;
      } else {
        bean.setId(response.getStatusCode().toString());
        bean.setStatusCode(response.getStatusCode().toString());
        bean.setDetails(response.getStatusCode().getReasonPhrase());
        LOGGER.debug("Response is not OK from externl APIgetPostResponseFromExternalApi : ", bean.toString());
        throw new CustomException(bean);
      }
    } catch (RestClientException e) {
      HttpClientErrorException httpClientErrorException;
      ExceptionHandlerBean exBean;

      if (e.getMostSpecificCause() instanceof HttpClientErrorException) {
        httpClientErrorException = (HttpClientErrorException) e.getMostSpecificCause();
        exBean = responseValidator.getErrorResponse(httpClientErrorException.getRawStatusCode());
      } else {
        exBean = responseValidator.getErrorResponse(Integer.parseInt(Constants.NOT_FOUND));
      }

      LOGGER.error(propertyFileReader.getValue(Constants.API_ACCESS_ERROR_DETAILS) + source
          + ERROR_DETAILS + e);
      throw new CustomException(exBean);
    } catch (Exception ex) {
      LOGGER.error(propertyFileReader.getValue(Constants.API_ACCESS_ERROR_DETAILS) + source
          + ERROR_DETAILS + ex);
      return null;
    }
  }

  @Override
  public String getPostResponseFromExternalApi(String host, String source,
      HttpEntity<MultiValueMap<String, Object>> requestEntity) throws CustomException {

    LOGGER.debug("Call to externl APIgetPostResponseFromExternalApi at : " , Calendar.getInstance().getTime());
    String responseBody = null;
    // LAL service
    ExceptionHandlerBean bean = new ExceptionHandlerBean();
    try {
      // real LAL service
      RestTemplate restTemplate = getConnection();      
      ResponseEntity<String> response =
          restTemplate.exchange(host, HttpMethod.POST, requestEntity, String.class);
      LOGGER.debug("Response from externl APIgetPostResponseFromExternalApi : ", Calendar.getInstance().getTime());
      responseBody = response.getStatusCode().getReasonPhrase();
      LOGGER.debug("Response Value from externl APIgetPostResponseFromExternalApi : ", responseBody);
      if (HttpStatus.OK.equals(response.getStatusCode())) {
        return responseBody;
      } else {
        bean.setId(response.getStatusCode().toString());
        bean.setStatusCode(response.getStatusCode().toString());
        bean.setDetails(response.getStatusCode().getReasonPhrase());
        LOGGER.debug("Response is not OK from externl APIgetPostResponseFromExternalApi : ", bean.toString());
        throw new CustomException(bean);
      }
    } catch (RestClientException e) {
      HttpClientErrorException httpClientErrorException;
      ExceptionHandlerBean exBean;

      if (e.getMostSpecificCause() instanceof HttpClientErrorException) {
        httpClientErrorException = (HttpClientErrorException) e.getMostSpecificCause();
        exBean = responseValidator.getErrorResponse(httpClientErrorException.getRawStatusCode());
      } else {
        exBean = responseValidator.getErrorResponse(Integer.parseInt(Constants.NOT_FOUND));
      }

      LOGGER.error(propertyFileReader.getValue(Constants.API_ACCESS_ERROR_DETAILS) + source
          + ERROR_DETAILS + e);
      throw new CustomException(exBean);
    }
  }

  @Override
  public String getCmsResponseUsingExternalApi(String cmsUri, String source)
      throws CustomException {
    try {
      String hostForCms = propertyFileReader.getHostForCMS(source);      
     // RestTemplate restTemplate = getConnection();    
      RestTemplate restTemplate = new RestTemplate(); 
      String restResponse = restTemplate.getForObject(hostForCms + cmsUri, String.class);      
      return restResponse;
    } catch (RestClientException e) {
      ExceptionHandlerBean exBean;
      exBean =
          responseValidator.getErrorResponse(Constants.CMS_SERVERERROR_CODE, Constants.CMS_ERROR);
      LOGGER.error(propertyFileReader.getValue(Constants.API_ACCESS_ERROR_DETAILS) + "CMS"
          + ERROR_DETAILS + e);
      throw new CustomException(exBean);

    }
  }
  
  

  /**
	 * {@inheritDoc}
	 */
  @Override
  public ResponseEntity<String> postRequestForExternalApi(String host, String source,
	      HttpEntity<Object> requestEntity) throws CustomException {

	    LOGGER.debug(
	        Constants.ENTRY + "postRequestToExternalApi at :" + Calendar.getInstance().getTime());
	    ExceptionHandlerBean bean = new ExceptionHandlerBean();
	    try {
	      LOGGER.debug(Constants.ENTRY_API + source + "at " + Calendar.getInstance().getTime());
	      // RestTemplate restTemplate = getConnection();
	      RestTemplate restTemplate = new RestTemplate();
	      ResponseEntity<String> response =
	          restTemplate.exchange(host, HttpMethod.POST, requestEntity, String.class);
	      LOGGER.debug(Constants.EXIT_API + source + "at " + Calendar.getInstance().getTime());

	      if (response.getStatusCode().equals(HttpStatus.OK) || response.getStatusCode().equals(HttpStatus.CREATED)) {
	        LOGGER.debug(
	            Constants.EXIT + "postRequestToExternalApi at :" + Calendar.getInstance().getTime());

	        return response;
	      } else {
	        bean.setId(response.getStatusCode().toString());
	        bean.setStatusCode(response.getStatusCode().toString());
	        bean.setDetails(response.getStatusCode().getReasonPhrase());
	        throw new CustomException(bean);
	      }
	    } catch (RestClientException e) {
	      HttpClientErrorException httpClientErrorException;
	      ExceptionHandlerBean exBean;

	      if (e.getMostSpecificCause() instanceof HttpClientErrorException) {
	        httpClientErrorException = (HttpClientErrorException) e.getMostSpecificCause();
	        exBean = responseValidator.getErrorResponse(httpClientErrorException.getRawStatusCode());
	      } else {
	        exBean = responseValidator.getErrorResponse(Integer.parseInt(Constants.NOT_FOUND));
	      }

	      LOGGER.error(propertyFileReader.getValue(Constants.API_ACCESS_ERROR_DETAILS) + source
	          + ERROR_DETAILS + e);
	      throw new CustomException(exBean);
	    } catch (Exception ex) {
	      LOGGER.error(propertyFileReader.getValue(Constants.API_ACCESS_ERROR_DETAILS) + source
	          + ERROR_DETAILS + ex);
	      return null;
	    }
	  }

}
