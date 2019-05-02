package org.epo.cms.edfs.services.common.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class EdfsHttpException extends EdfsRunTimeException {
  private static final long serialVersionUID = 1L;

  @Getter
  private final HttpStatus httpStatus;

  public EdfsHttpException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public EdfsHttpException(Throwable cause, HttpStatus httpStatus) {
    super(cause);
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return httpStatus == null ? super.getMessage()
        : super.getMessage() + " (Status: " + httpStatus + ").";
  }
}
