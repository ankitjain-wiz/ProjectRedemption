package org.epo.cms.edfs.services.common.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

/**
 *
 * @author kstanczy
 */

@Log4j2
@ControllerAdvice
public class EpoControllerAdvice {

  @ExceptionHandler(value = EdfsHttpException.class)
  @ResponseBody
  public ResponseEntity<EdfsError> edfsErrorHandler(HttpServletRequest req, EdfsHttpException e) {
    log.error("EdfsHttpException (uri={})", req.getRequestURI(), e);
    HttpStatus status = e.getHttpStatus();
    EdfsError error = EdfsError.builder().path(req.getRequestURI()).status(status.toString())
        .message(e.getMessage()).build();
    return new ResponseEntity<>(error, status);
  }

  @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
  @ResponseBody
  public ResponseEntity<EdfsError> methodNotSupportedErrorHandler(HttpServletRequest req,
      HttpRequestMethodNotSupportedException e) {
    log.error("Method not supported (uri={}, method={})", req.getRequestURI(), req.getMethod(), e);
    HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
    EdfsError error = EdfsError.builder().path(req.getRequestURI()).status(status.toString())
        .message(e.getMessage()).build();
    return new ResponseEntity<>(error, status);
  }

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

  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public ResponseEntity<EdfsError> defaultErrorHandler(HttpServletRequest req, Exception e) {
    log.error("Default exception handler executed (uri=)", req.getRequestURI(), e);
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    EdfsError error = EdfsError.builder().path(req.getRequestURI()).status(status.toString())
        .message(e.getMessage()).build();
    return new ResponseEntity<>(error, status);
  }
}
