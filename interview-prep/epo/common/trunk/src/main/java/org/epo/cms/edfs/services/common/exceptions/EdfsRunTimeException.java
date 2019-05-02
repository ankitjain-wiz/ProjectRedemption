/*
 * (c) Sopra Steria, 2016. All rights reserved.
 */
package org.epo.cms.edfs.services.common.exceptions;

/**
 * @author Michal Pieprzyca
 */
public class EdfsRunTimeException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public EdfsRunTimeException(String message) {
    super(message);
  }

  public EdfsRunTimeException(Throwable cause) {
    super(cause);
  }
}


