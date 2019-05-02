package org.epo.cms.edfs.services.common.useraccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.beans.UserAccountDetail;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ExceptionHandlerBean;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.externalapi.ExternalApiService;
import org.epo.cms.edfs.services.common.util.Constants;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * An implementation of the {@link UserAccountDetailsService} which iterates through the different
 * pages of the result set.
 * 
 * @author PV53311
 *
 */
@Component
class PagingAwareUserAccountDetailsRetriever implements UserAccountDetailsService {

  private static final String USER_ID_PARAM = "UserID";

  private static final String UI_ACCOUNT_MANAGEMENT_LIST_ACCOUNTS =
      "ui/AccountManagement/ListAccounts";

  private static final Logger LOGGER = LogManager.getLogger();

  private final AbstractPagedServiceRetrievalExecutor executor;
  private final MaxPagesFromResponseExtractor extractor;
  private final UserAccountFilter filter;
  private final ExternalApiService externalApiService;

  @Autowired
  private PropertyFileReader propertyFileReader;

  @Autowired
  private ResponseValidator responseValidator;


  @Autowired
  public PagingAwareUserAccountDetailsRetriever(
      @Qualifier("ScheduledApiService") final ExternalApiService externalApiService) {
    this(externalApiService,
        new ThreadedPagedServiceRetrievalExecutor(externalApiService, "page", "pagesize", 50, 10));
  }


  PagingAwareUserAccountDetailsRetriever(final ExternalApiService externalApiService,
      final AbstractPagedServiceRetrievalExecutor executor) {
    this.externalApiService = externalApiService;
    this.executor = executor;
    this.extractor = new UserAccountListMaxPagesExtractor();
    this.filter = new UserAccountFilter();
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public Map<String, UserAccountDetail> getUserAccountDetails() throws CustomException {
    try {
      Map<String, UserAccountDetail> userDetails = new ConcurrentHashMap<>();
      LOGGER.info("Retrieving the user account details.");

      List<String> responses = executor.retrieveAllPages(Constants.HOST_FOR_CMSSERVICE,
          UI_ACCOUNT_MANAGEMENT_LIST_ACCOUNTS, extractor);

      for (String oneResponse : responses) {
        userDetails.putAll(filter.getUserAccountDetails(oneResponse));
      }

      LOGGER.info("Retrieved account details for {} accounts.", userDetails.size());

      return userDetails;
    } catch (CustomException e) {
      ExceptionHandlerBean exBean;
      exBean =
          responseValidator.getErrorResponse(Constants.CMS_SERVERERROR_CODE, Constants.CMS_ERROR);
      LOGGER.error(propertyFileReader.getValue(Constants.API_ACCESS_ERROR_DETAILS),
          "Account details API", e);

      throw new CustomException(exBean);
    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public UserAccountDetail getUserAccountDetail(final String userId) throws CustomException {
    LOGGER.info(
        "PagingAwareUserAccountDetailsRetriever.getUserAccountDetails - Retrieving account details for {}.",
        userId);

    try {
      UserAccountDetail details = null;
      String cmsUri = UriComponentsBuilder.fromPath(UI_ACCOUNT_MANAGEMENT_LIST_ACCOUNTS)
          .queryParam(USER_ID_PARAM, userId).toUriString();
      LOGGER.debug(
          "PagingAwareUserAccountDetailsRetriever.getUserAccountDetails - Calling CMS Service using URL {}",
          cmsUri);

      String response =
          externalApiService.getCmsResponseUsingExternalApi(cmsUri, Constants.HOST_FOR_CMSSERVICE);

      LOGGER.debug(
          "PagingAwareUserAccountDetailsRetriever.getUserAccountDetails - Retrieved details for {} from CMS: {}",
          userId, response);

      Map<String, UserAccountDetail> singleResult = filter.getUserAccountDetails(response);
      if (singleResult.size() == 1) {
        details = new ArrayList<UserAccountDetail>(singleResult.values()).get(0);
      }

      LOGGER.debug(
          "PagingAwareUserAccountDetailsRetriever.getUserAccountDetails - Returning response {} for {} ",
          details, userId);
      LOGGER.info(
          "PagingAwareUserAccountDetailsRetriever.getUserAccountDetails - Retrieving account details for {} finished.",
          userId);

      return details;
    } catch (CustomException e) {
      ExceptionHandlerBean exBean;
      exBean =
          responseValidator.getErrorResponse(Constants.CMS_SERVERERROR_CODE, Constants.CMS_ERROR);
      LOGGER.error(propertyFileReader.getValue(Constants.API_ACCESS_ERROR_DETAILS),
          "Account details API", e);

      throw new CustomException(exBean);
    }
  }

}
