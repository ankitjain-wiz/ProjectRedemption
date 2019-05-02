package org.epo.cms.edfs.services.common.exceptions;

import org.epo.cms.edfs.services.common.util.Constants;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 
 * @author amigarg
 * 
 */

@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private PropertyFileReader propertyFileReader;

  /**
   * Handles custom defined exception and return error response
   * 
   * @param customException handles custom exception
   * @return ResponseEntity of ExceptionHandlerBean
   */
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ExceptionHandlerBean> customExceptionHandler(
      CustomException customException) {
    ExceptionHandlerBean exceptionHandlerBean = customException.getExceptionHandlerBean();

    return new ResponseEntity<>(exceptionHandlerBean,
        HttpCode.getHttpCode(Integer.parseInt(exceptionHandlerBean.getStatusCode())));
  }

  /**
   * Handles defined exception and return error response
   * 
   * @param exception handles Exception
   * @return ResponseEntity of ExceptionHandlerBean
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionHandlerBean> exceptionHandler(Exception exception) {
    ExceptionHandlerBean exceptionHandlerBean =
        new ExceptionHandlerBean(Constants.TOC_SERVERERROR_CODE, Constants.INTERNALSERVERERROR,
            propertyFileReader.getValue("tocServerErrorDetail"));

    return new ResponseEntity<>(exceptionHandlerBean,
        HttpCode.getHttpCode(Integer.parseInt(exceptionHandlerBean.getStatusCode())));
  }
}
