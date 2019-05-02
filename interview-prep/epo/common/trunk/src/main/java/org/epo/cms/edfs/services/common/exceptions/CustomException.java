package org.epo.cms.edfs.services.common.exceptions;

/**
 * Used for handling custom Exceptions.
 * 
 * @author amigarg
 * 
 */

public class CustomException extends Exception {

  private static final long serialVersionUID = 1L;

  private transient  ExceptionHandlerBean exceptionHandlerBean;

  public CustomException(ExceptionHandlerBean exceptionHandlerBean) {
    this.exceptionHandlerBean = exceptionHandlerBean;
  }

  public ExceptionHandlerBean getExceptionHandlerBean() {
    return exceptionHandlerBean;
  }

  public void setExceptionHandlerBean(ExceptionHandlerBean exceptionHandlerBean) {
    this.exceptionHandlerBean = exceptionHandlerBean;
  }

}
