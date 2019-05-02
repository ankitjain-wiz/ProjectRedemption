package org.epo.cms.edfs.services.common.exceptions;

/**
 * This Enum for error codes used in project
 * 
 * @author gaurgarg
 */
public enum ErrorCodeEnum {

  TIMEOUT_ERROR_CODE(1001, 500), API_ACCESS_ERROR_CODE(1002, 500), NULL_EDOSSIERID_CODE(1003,
      500), SERVERERROR_CODE(1500, 500), WRONG_INPUT_CODE(10_400, 412), AUTHENTICATION_FAILED_CODE(
          10_401, 401), WRONG_KERBOROS_GRP_CODE(10_403, 401), RESOURCE_NOTFOUND_CODE(10_404,
              404), UNACCEPTABLE_RESOURCE_FORMAT_CODE(10_406, 412), VERSION_NOTSUPPORTED_CODE(10_415,
                  400), UNKNOWN_NUMBER_CODE(10_422, 400), THIRDPARTY_ERROR_CODE(10_500,
                      500), THIRDPARTY_SERVICEERROR_CODE(10_503, 500), BAD_REQUEST_CODE(1400,
                          400), NOTIFICATION_DELETED_CODE(1203, 206), UNKNOWN_CODE(-1,
                              400), DATA_ACCESS_EXCEPTION(13_001, 3001), INTERNAL_SERVER_ERROR(13_000,
                                  3000), UNPROCESSABLE_ENTITY(422, 422);

  private Integer id;
  private Integer statusCode;

   ErrorCodeEnum(Integer errorCode, Integer statusCode) {
    this.id = errorCode;
    this.statusCode = statusCode;
  }

  public Integer getId() {
    return id;
  }

  public Integer getStatusCode() {
    return statusCode;
  }
}
