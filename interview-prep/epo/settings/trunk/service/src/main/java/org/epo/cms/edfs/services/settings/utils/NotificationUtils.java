package org.epo.cms.edfs.services.settings.utils;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.externalapi.ExternalApiService;
import org.epo.cms.edfs.services.common.useridrole.UserAdminRole;
import org.epo.cms.edfs.services.common.util.Constants;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.epo.cms.edfs.services.settings.beans.NotificationFilter;
import org.epo.cms.edfs.services.settings.beans.NotificationFilterReqBean;
import org.epo.cms.edfs.services.settings.beans.NotificationFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is used to get data from global and user table
 * update data of User and Global table
 * @author rapatel
 *
 */

@Component
public class NotificationUtils {
  
  private static final Logger LOGGER = LogManager.getLogger(NotificationUtils.class);
  
  @Autowired
  protected PropertyFileReader propertyFileReader;

  @Autowired
  private ResponseValidator responseValidator;

  @Autowired
  private UserAdminRole userAdminRole;
  
  @Autowired
  private ExternalApiService externalApiService;
  
 
  /**
   * This method is used to hit the notification service to update the User Notificaticion Filter Settings
 * @param notificationFilterReqBean :NotificationFilterReqBean
 * @return String: String
 * @throws CustomException : CustomException
 */
public String updateUserNotificaticionFilterSettings(NotificationFilterReqBean notificationFilterReqBean
      ) throws CustomException {

  
  String hostForNotificationFilter = propertyFileReader.getHostForCMS(SettingsConstants.HOST_FOR_NOTIFILTER)
		                              +"?localtestuser="+userAdminRole.getUser();
  String responseBody = postRequestForNotificationApi(hostForNotificationFilter, SettingsConstants.NOTIFICATION_SOURCE, notificationFilterReqBean);
  
  return responseBody;
 
}  

  private String postRequestForNotificationApi(String hostForNotificationFilter, String source, Object obj) throws CustomException{
	String responseBody = null;
	  HttpHeaders headers = new HttpHeaders();
	  headers.setContentType(MediaType.APPLICATION_JSON);  
	  HttpEntity<Object> requestEntity = new HttpEntity<>(obj, headers); 
	  
	  ResponseEntity<String> response = externalApiService.postRequestForExternalApi(hostForNotificationFilter, source, requestEntity);
	
	  if (response.getStatusCode().equals(HttpStatus.CREATED)) {		
	  	responseBody = response.getStatusCode().getReasonPhrase();
	  }
	  
	  return responseBody;
 }
  /**
 * @param notificationFilterRequestList : List<NotificationFilterRequest>
 * @param role : String
 * @return String: String
 * @throws CustomException : CustomException
 */
 public String updateGlobalNotificaticionFilterSettings(List<NotificationFilterRequest> notificationFilterRequestList,String role) throws CustomException {

    String hostForNotificationFilter = propertyFileReader.getHostForCMS(SettingsConstants.HOST_FOR_NOTIFILTER)
    		+ role+"?localtestuser="+userAdminRole.getUser();
    String  responseBody = postRequestForNotificationApi(hostForNotificationFilter, SettingsConstants.NOTIFICATION_SOURCE, notificationFilterRequestList);
    return  responseBody;
    
  }
  
  /**
   * This method is used to hit the notification service to get the role based
   * medium and low intrusive event type data.
   * 
   * @param role
   *            - Role name.
   * @return NotificationFilter - NotificationFilter.
   */
  
  public NotificationFilter getNotificationFilter(String role) throws CustomException {

    
     String hostForNotificationFilter = propertyFileReader.getHostForCMS(SettingsConstants.HOST_FOR_NOTIFILTER)+ role+"?localtestuser="+userAdminRole.getUser(); 
     NotificationFilter notificationFilter = getResponseFromNotificationApi(hostForNotificationFilter, SettingsConstants.NOTIFICATION_SOURCE);    
     return notificationFilter;

}
  /**
   * This method is used to hit the notification service to get role based
   * medium and low intrusive event types for given user.
   * 
   * @return NotificationFilter - NotificationFilter.
   * @throws CustomException
   *             - To handle CustomException.
   */
  public NotificationFilter getNotificationFilter() throws CustomException {
      
     String hostForNotificationFilter = propertyFileReader.getHostForCMS(SettingsConstants.HOST_FOR_NOTIFILTER)+ "?localtestuser="+userAdminRole.getUser(); 
     NotificationFilter notificationFilter = getResponseFromNotificationApi(hostForNotificationFilter, SettingsConstants.NOTIFICATION_SOURCE);    
      return notificationFilter;

    }
  
  /** 
   * This method is used to hit the notification service using external api.
   * @param hostForNotificationFilter - hostForNotificationFilter
   * @param source - source
   * @return NotificationFilter - NotificationFilter.
   */
  
   private NotificationFilter  getResponseFromNotificationApi( String hostForNotificationFilter,  String source ) throws CustomException{	  
	  NotificationFilter notificationFilter = null;
	  try {
	  String restResponse = externalApiService.getResponseFromExternalApi(hostForNotificationFilter, source);
      if (StringUtils.isNotEmpty(restResponse)) {
          ObjectMapper objectMapper = new ObjectMapper();
          objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
          notificationFilter = objectMapper.readValue(restResponse, NotificationFilter.class);
      }

     } catch (IOException ie) {
      LOGGER.error("Error while converting json string to desired object : {}", ie);
      throw new CustomException(
              responseValidator.getErrorResponse(Integer.parseInt(Constants.INTERNALSERVERERROR)));
  }		  
	  return notificationFilter;
  }
}