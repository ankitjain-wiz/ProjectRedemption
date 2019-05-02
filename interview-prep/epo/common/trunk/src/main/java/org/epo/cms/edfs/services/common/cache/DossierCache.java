package org.epo.cms.edfs.services.common.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jcs.access.CacheAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.beans.UserAccountDetail;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.useraccount.UserAccountDetailsService;
import org.epo.cms.edfs.services.common.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DossierCache {
  private static final String EXCEPTION = "Exception";

  private static final Logger LOGGER = LogManager.getLogger();

  @Autowired
  private UserAccountDetailsService userAccountService;

  @Autowired
  private CacheAccess<Object, Object> userCache;

  /**
   * Retrieves the {@link UserAccountDetail} for the given UserId. It tries to retrieve from the
   * cache first, if not fount then retrieve from service and store in cache.
   * 
   * @param userId - The id of the user for which the UserAccountDetails are retrieved. Can be in
   *        long or short form (so with or without the suffix @INTERNAL.EPO.ORG) Case insensitive
   * @return - The found UserAccountDetails object or {@code null} is no information found.
   */
  public UserAccountDetail getUserAccountDetails(final String userId) {
    LOGGER.debug("DossierCache.getUserAccountDetails - UserID {}", userId);
    String userIdNormalized = normalizeUserId(userId);
    LOGGER.debug("DossierCache.getUserAccountDetails - UserID {} results in Normalized UserId {}",
        userId, userIdNormalized);

    UserAccountDetail accountDetails = getFromCache(userIdNormalized);
    if (accountDetails == null) {
      LOGGER.debug("DossierCache.getUserAccountDetails - UserID {} not found in cache.",
          userIdNormalized);
      try {
        accountDetails = userAccountService.getUserAccountDetail(userIdNormalized);
        LOGGER.debug(
            "DossierCache.getUserAccountDetails - Response returned by AccountService for UserID {} is {}.",
            userIdNormalized, accountDetails);
        storeInCache(accountDetails);
      } catch (CustomException e) {
        LOGGER.error(EXCEPTION,e);
      }
    }

    return accountDetails;
  }

  private String normalizeUserId(final String userId) {
    // Remove @INTERNAL.EPO.ORG
    // And then uppercase
    return userId.split("\\@")[0].toUpperCase();
  }


  @SuppressWarnings("unchecked")
  private UserAccountDetail getFromCache(final String userIdNormalized) {
    UserAccountDetail details = null;

    Map<String, UserAccountDetail> userAccountDetails =
        (Map<String, UserAccountDetail>) userCache.get(Constants.USER_DETAILS);
    if (userAccountDetails != null) {
      details = userAccountDetails.get(userIdNormalized);
    }

    return details;
  }

  @SuppressWarnings("unchecked")
  private void storeInCache(final UserAccountDetail accountDetails) {
    if (accountDetails != null) {
      Map<String, UserAccountDetail> userAccountDetails =
          (Map<String, UserAccountDetail>) userCache.get(Constants.USER_DETAILS);
      if (userAccountDetails == null) {
        LOGGER.debug("DossierCache.storeInCache - UserAccountDetails is null. Creating empty map.");
        userAccountDetails = new HashMap<>();
        userCache.put(Constants.USER_DETAILS, userAccountDetails);
      }

      LOGGER.debug("DossierCache.storeInCache - Storing userAccountDetails in Map with key {}",
          accountDetails.getUserID());
      userAccountDetails.put(accountDetails.getUserID(), accountDetails);
    }
  }


  /**
   * Refreshes the UserDetailsCache. This refreshing can take some time because of the retrieval of
   * all the userdetails from the service. So we should first retrieve the new userDetails and then
   * update the cache object.
   * 
   * @return the refreshed map of the {@link UserAccountDetail}s.
   * @throws CustomException handles custom exception
   */
  public Map<String, UserAccountDetail> refreshUserDetailsCache() throws CustomException {
    LOGGER.info("Started refreshing the accountDetails from the CMS API.");
    Map<String, UserAccountDetail> refreshedUserAccountDetails =
        userAccountService.getUserAccountDetails();
    LOGGER.info("Finished refreshing the accountDetails from the CMS API.");

    synchronized (this) {
      userCache.clear();
      userCache.put(Constants.USER_DETAILS, refreshedUserAccountDetails);
    }

    return refreshedUserAccountDetails;
  }
  
  /**
   * Refreshes the UserDetailsCache.
   * This refreshing can take some time because of the retrieval of all the userdetails from the service.
   * So we should first retrieve the new userDetails and then update the cache object.
   * 
   * @return the refreshed map of the {@link UserAccountDetail}s.
   * @throws CustomException handles custom exception
   */
  public Map<String, UserAccountDetail> getUserDetailsCacheMap() throws CustomException {
	  LOGGER.info("Started refreshing the accountDetails from the CMS API.");
      Map<String, UserAccountDetail> userAccountDetails = (Map<String, UserAccountDetail>)userCache.get(Constants.USER_DETAILS);
      if(userAccountDetails == null || userAccountDetails.isEmpty()){
        userAccountDetails = refreshUserDetailsCache();
      }
      return userAccountDetails;
  }
}
