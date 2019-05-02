package org.epo.cms.edfs.services.common.util;

/**
 * Constants defined that are used in TOC application
 * 
 * @author nisharma
 * 
 */
public class Constants {

  // Constants for logger messages
  public static final String ENTRY = "Entering into method : ";
  public static final String EXIT = "Exiting from method : ";
  public static final String ERROR = "Error in Method";
  public static final String ENTRY_API = "Before hitting third party API: ";
  public static final String EXIT_API = "After hitting third party API: ";

  // Response codes
  public static final String CREATED = "201";
  public static final String BADREQUEST = "400";
  public static final String UNAUTHROISED = "401";
  public static final String NOT_FOUND = "404";
  public static final String TIME_OUT = "412";
  public static final String PRECONDITION = "412";
  public static final String INTERNALSERVERERROR = "500";
  public static final String NOT_ALLOWED = "403";
  public static final String UNPROCESSABLE_ENTITY = "422";
  
  // Response code description
  public static final String OK_MESSAGE = "Ok";
  public static final String CREATED_MESSAGE = "Created";
  public static final String BADREQUEST_MESSAGE = "Bad Request";
  public static final String UNAUTHROISED_MESSAGE = "Unauthorised";
  public static final String NOT_FOUND_MESSAGE = "Not Found";
  public static final String TIME_OUT_MESSAGE = "Time Out";
  public static final String INTERNALSERVERERROR_MESSAGE = "Unexpected Error";
  public static final String NOT_ALLOWED_MESSAGE = "Not Allowed";
  public static final String UNPROCESSABLE_ENTITY_MESSAGE = "Unprocessable Entity";
  public static final String PRECONDITION_MESSAGE = "Precondition";

  // Custom Error Codes
  public static final String TIMEOUT_ERROR_CODE = "1001";
  public static final String TOC_SERVERERROR_CODE = "1500";
  public static final String WRONG_INPUT_CODE = "10400";
  public static final String AUTHENTICATION_FAILED_CODE = "10401";
  public static final String WRONG_KERBOROS_GRP_CODE = "10403";
  public static final String RESOURCE_NOTFOUND_CODE = "10404";
  public static final String UNACCEPTABLE_RESOURCE_FORMAT_CODE = "10406";
  public static final String VERSION_NOTSUPPORTED_CODE = "10415";
  public static final String THIRDPARTY_ERROR_CODE = "10500";
  public static final String THIRDPARTY_SERVICEERROR_CODE = "10503";
  public static final String CREATED_CODE = "1301";
  public static final String CMS_SERVERERROR_CODE = "1600";
  public static final String AUTHORIZATION_FAILED_ID = "40001";

  // Timeout details
  public static final Integer TIMEOUT_TIME = 2000;
  public static final String F8_AUTHENTICATION = "f8Authentication";
  // Error Details
  public static final String API_ACCESS_ERROR_DETAILS = "apiErrorAccessDetail";

  // Exception handling codes
  public static final String EXCEPTION = "3000";
  public static final String DATA_ACCESS_EXCEPTION = "3001";
  public static final String FILE_NOT_FOUND_EXCEPTION = "3002";
  public static final String UNSUPPORTED_ENCODING_EXCEPTION = "3003";
  public static final String WRITER_EXCEPTION = "3004";
  public static final String IO_EXCEPTION = "3005";
  public static final String DOCUMENT_EXCEPTION = "3006";
  public static final String JSON_PARSE_EXCEPTION = "3007";
  public static final String JSON_MAPPING_EXCEPTION = "3008";
  public static final String ARRAY_INDEX_OUT_OF_BOUND = "3009";
  public static final String TRACKING_SHEET_PDF_ERROR_CODE = "3010";
  public static final String INVALID_FILE_EXTENSION = "3011";
  public static final String MANDATORY_FIELDS_ERROR_CODE = "3012";
  public static final String NOT_ENOUGH_PAGES_IN_FILE = "3013";
  public static final String MAGIC_EXCEPTION = "3014";
  public static final String MAGIC_MATCH_NOT_FOUND_EXCEPTION = "3014";
  public static final String MAGIC_PARSE_EXCEPTION = "3014";
  public static final String NOT_FOUND_EXCEPTION = "3015";
  public static final String CHECKSUM_EXCEPTION = "3016";
  public static final String FORMAT_EXCEPTION = "3017";
  // Notification message
  public static final String TASK_SUCCESSFUL = "taskSuccessful";
  public static final String HOST_FOR_CMSSERVICE = "hostForCms";
  public static final String HOST_FOR_USER_ACCOUNT = "hostForUserAccountDetails";
  public static final String CMS_ERROR = "CMS Services are down.";
  public static final String NOT_ALLOWED_MSG = "authorizationFailedMessage";
  public static final String CMS_PERMISSION_URL = "cmsPermissionUrl";
  public static final String ENVIRONMENT = "environment";
  public static final String DEV_ENVIRONMENT = "dev";
  public static final String CMS_LOOKUP_URL = "cmsLookupHostUrl";
  public static final String APPLICATION_NUMBER = "applicationNumber";
  public static final String GET = "GET";
  public static final String POST = "POST";
  public static final String USER_DETAILS = "userDetails";

  public static final String CMS_HOST = "cmsHost";
  public static final String CMS_PERMISSION_URI = "cmsPermissionUri";

  public static final String CMS_ACCOUNT_LIST_URI = "accountListUri";

  public static final String NUMBER_OF_THREADS = "numberOfThreads";


  private Constants() {
    // This is a private constructor.
  }
}
