package org.epo.cms.edfs.services.common.exceptions;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author amigarg
 *
 */

public class HttpCode {

  /**
   * class that return HttpStatus code.
   * 
   * @param httpCodeStatus of type int
   * @return HttpStatus value
   */

  // Suppresses default constructor, ensuring non-instantiability.
  private HttpCode() {}

  public static HttpStatus getHttpCode(int httpCodeStatus) {

    HttpStatus status;
    switch (httpCodeStatus) {
    case 200:
        return HttpStatus.OK;
      case 201:
        return HttpStatus.CREATED;
      case 400:
        status = HttpStatus.BAD_REQUEST;
        break;
      case 401:
        status = HttpStatus.UNAUTHORIZED;
        break;
      case 403:
        status = HttpStatus.FORBIDDEN;
        break;
      case 404:
        status = HttpStatus.NOT_FOUND;
        break;
      case 412:
        status = HttpStatus.PRECONDITION_FAILED;
        break;
      case 422:
        status = HttpStatus.UNPROCESSABLE_ENTITY;
        break;
      case 500:
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        break;
      default:
        status = HttpStatus.CONFLICT;
        break;
    }
    return status;

  }


}
