/*
 * (c) Sopra Steria, 2016. All rights reserved.
 */
package org.epo.cms.edfs.services.common.exceptions;

/**
 * @author wszymeczko
 */
public class EdfsConfigurationException extends EdfsRunTimeException {
  private static final long serialVersionUID = 1L;

  public EdfsConfigurationException(String message) {
    super(message);
  }

  public EdfsConfigurationException(Throwable cause) {
    super(cause);
  }
}


