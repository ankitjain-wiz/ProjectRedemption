package org.epo.cms.edfs.services.common.interceptor;

/**
 * The RequestUserInfo contains the information about the user that is making the current service
 * request.
 * 
 * @author PV53311
 *
 */
public final class RequestUserInfo {

  private final String userId;


  /**
   * Constructor for the RequestUserInfo object.
   * 
   * @param userId - The userId of the user.
   */
  public RequestUserInfo(final String userId) {
    this.userId = userId;
  }

  /**
   * Getter for the userId attribute.
   * 
   * @return userId attribute.
   */
  public String getUserId() {
    return userId;
  }

}
