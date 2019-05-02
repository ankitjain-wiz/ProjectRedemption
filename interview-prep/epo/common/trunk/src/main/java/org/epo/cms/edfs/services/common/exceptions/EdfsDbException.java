/*
 * (c) Sopra Steria, 2016. All rights reserved.
 */
package org.epo.cms.edfs.services.common.exceptions;

/**
 * @author wszymeczko
 */
public class EdfsDbException extends EdfsRunTimeException {
  private static final long serialVersionUID = 1L;

  public EdfsDbException(String message) {
    super(message);
  }

  public EdfsDbException(Throwable cause) {
    super(cause);
  }
}


