package org.epo.cms.edfs.services.common.exceptions;

import org.epo.cms.edfs.services.common.util.Constants;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is used to set ExceptionHandlerBean exception with specific error id, Error code and
 * error detail message.
 * 
 * @author amigarg
 *
 */
public class ResponseValidator {

  @Autowired
  private PropertyFileReader propertyFileReader;

  /**
   * Set exceptions code
   * 
   * @param statusCode of type int
   * @return ExceptionHandlerBean as value
   */
  public ExceptionHandlerBean getErrorResponse(int statusCode) {
    ExceptionHandlerBean exceptionHandlerBean = new ExceptionHandlerBean();
    switch (statusCode) {
      case 201:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.CREATED_CODE, Constants.CREATED,
            propertyFileReader.getValue(Constants.TASK_SUCCESSFUL));
        break;
      case 400:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.WRONG_INPUT_CODE,
        	Constants.BADREQUEST, propertyFileReader.getValue("wrongInputDetail"));
        break;
      case 401:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.AUTHENTICATION_FAILED_CODE,
            Constants.UNAUTHROISED, propertyFileReader.getValue("authenticationFailedDetail"));
        break;
      case 403:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.AUTHORIZATION_FAILED_ID,
            Constants.NOT_ALLOWED, propertyFileReader.getValue(Constants.NOT_ALLOWED_MSG));
        break;
      case 404:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.RESOURCE_NOTFOUND_CODE,
            Constants.NOT_FOUND, propertyFileReader.getValue("resourceNotFoundDetail"));
        break;
      case 406:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.UNACCEPTABLE_RESOURCE_FORMAT_CODE,
            Constants.PRECONDITION,
            propertyFileReader.getValue("unacceptableResourceFormatDetail"));
        break;
      case 408:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.TIMEOUT_ERROR_CODE,
            Constants.INTERNALSERVERERROR, propertyFileReader.getValue("timeoutErrorDetail"));
        break;
      case 415:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.VERSION_NOTSUPPORTED_CODE,
            Constants.BADREQUEST, propertyFileReader.getValue("versionNotSupportedDetail"));
        break;
      case 422:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.WRONG_INPUT_CODE,
        	Constants.UNPROCESSABLE_ENTITY, propertyFileReader.getValue("unprocessableEntity"));
        break;
      case 500:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.THIRDPARTY_ERROR_CODE,
            Constants.INTERNALSERVERERROR, propertyFileReader.getValue("thirdPartyErrorDetail"));
        break;
      case 503:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.THIRDPARTY_SERVICEERROR_CODE,
            Constants.INTERNALSERVERERROR,
            propertyFileReader.getValue("thirdPartyServiceErrorDetail"));
        break;
      default:
        setExceptionHandlerBean(exceptionHandlerBean, Constants.TOC_SERVERERROR_CODE,
            Constants.INTERNALSERVERERROR, propertyFileReader.getValue("tocServerErrorDetail"));
        break;

    }
    return exceptionHandlerBean;
  }

  public ExceptionHandlerBean getErrorResponse(ErrorCodeEnum statusCode) {

    ExceptionHandlerBean exceptionHandlerBean = new ExceptionHandlerBean();

    exceptionHandlerBean.setId(statusCode.getId() + "");
    exceptionHandlerBean.setStatusCode(statusCode.getStatusCode() + "");

    exceptionHandlerBean.setDetails(statusCode.name());

    return exceptionHandlerBean;
  }
  
  public ExceptionHandlerBean getErrorResponse(String statusCode, String exceptionMsg) {
    ExceptionHandlerBean exceptionHandlerBean = new ExceptionHandlerBean();
    switch (statusCode) {
      case "3001":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.DATA_ACCESS_EXCEPTION,
            Constants.DATA_ACCESS_EXCEPTION, exceptionMsg);
        break;

      case "3002":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.FILE_NOT_FOUND_EXCEPTION,
            Constants.FILE_NOT_FOUND_EXCEPTION, exceptionMsg);
        break;

      case "3003":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.UNSUPPORTED_ENCODING_EXCEPTION,
            Constants.UNSUPPORTED_ENCODING_EXCEPTION, exceptionMsg);
        break;

      case "3004":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.WRITER_EXCEPTION,
            Constants.WRITER_EXCEPTION, exceptionMsg);
        break;

      case "3005":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.IO_EXCEPTION,
            Constants.IO_EXCEPTION, exceptionMsg);
        break;

      case "3006":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.DOCUMENT_EXCEPTION,
            Constants.DOCUMENT_EXCEPTION, exceptionMsg);
        break;

      case "3007":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.JSON_PARSE_EXCEPTION,
            Constants.JSON_PARSE_EXCEPTION, exceptionMsg);
        break;

      case "3008":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.JSON_MAPPING_EXCEPTION,
            Constants.JSON_MAPPING_EXCEPTION, exceptionMsg);
        break;

      case "3009":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.ARRAY_INDEX_OUT_OF_BOUND,
            Constants.ARRAY_INDEX_OUT_OF_BOUND, exceptionMsg);
        break;

      case "3010":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.TRACKING_SHEET_PDF_ERROR_CODE,
            Constants.TRACKING_SHEET_PDF_ERROR_CODE, exceptionMsg);
        break;

      case "3011":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.INVALID_FILE_EXTENSION,
            Constants.INVALID_FILE_EXTENSION, exceptionMsg);
        break;

      case "3013":
        setHandlerBeanProperties(exceptionHandlerBean, Constants.NOT_ENOUGH_PAGES_IN_FILE,
            Constants.NOT_ENOUGH_PAGES_IN_FILE, exceptionMsg);
        break;

      default:
        setHandlerBeanProperties(exceptionHandlerBean, Constants.EXCEPTION, Constants.EXCEPTION,
            exceptionMsg);
        break;

    }
    return exceptionHandlerBean;
  }

  private void setExceptionHandlerBean(ExceptionHandlerBean exceptionHandlerBean, String id,
      String statusCode, String details) {
    exceptionHandlerBean.setId(id);
    exceptionHandlerBean.setStatusCode(statusCode);
    exceptionHandlerBean.setDetails(details);
  }

  
  private void setHandlerBeanProperties(ExceptionHandlerBean exceptionHandlerBean, String id,
      String status, String details) {
    exceptionHandlerBean.setId(id);
    exceptionHandlerBean.setStatusCode(status);
    exceptionHandlerBean.setDetails(details);
  }

 
  public ExceptionHandlerBean getErrorResponseWithExpMsg(ErrorCodeEnum statusCode,
      String exceptionMessage) {

    ExceptionHandlerBean exceptionHandlerBean = new ExceptionHandlerBean();

    exceptionHandlerBean.setId(statusCode.getId() + "");
    exceptionHandlerBean.setStatusCode(statusCode.getStatusCode() + "");

    exceptionHandlerBean.setDetails(statusCode.name() + " " + exceptionMessage);

    return exceptionHandlerBean;
  }

}
