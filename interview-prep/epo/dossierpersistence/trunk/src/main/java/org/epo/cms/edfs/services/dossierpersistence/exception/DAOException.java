package org.epo.cms.edfs.services.dossierpersistence.exception;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @author dbawa
 *
 */
public class DAOException extends DataAccessException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public DAOException(String msg) {
    super(msg);
  }

  public DAOException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
