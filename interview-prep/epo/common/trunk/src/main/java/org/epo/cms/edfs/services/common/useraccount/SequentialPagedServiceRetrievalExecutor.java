package org.epo.cms.edfs.services.common.useraccount;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.externalapi.ExternalApiService;

/**
 * A PagedServiceRetrievalExecutor which retrieves the pages one after the other.
 * 
 * @author PV53311
 *
 */
final class SequentialPagedServiceRetrievalExecutor extends AbstractPagedServiceRetrievalExecutor {

  private static final Logger LOGGER = LogManager.getLogger();

  /**
   * Constructor for the SequentialPagedServiceRetrievalExecutor.
   * 
   * @param apiService - The {@link ExternalApiService} to use for invoking the external api.
   * @param pageParamName - The name of the page indicator parameter
   * @param pageSizeParamName - The name of the pageSize parameter
   * @param pageSizeValue - The value to use for the pageSize parameter
   */
  public SequentialPagedServiceRetrievalExecutor(final ExternalApiService apiService,
      String pageParamName, final String pageSizeParamName, final int pageSizeValue) {
    super(apiService, pageParamName, pageSizeParamName, pageSizeValue);
  }


  /**
   * {@inheritDoc}.
   */
  @Override
  protected List<String> retrieveRemainingPages(String apiHost, String baseApiUrl, int fromPage,
      int toPage) throws CustomException {

    LOGGER.debug("Retrieving pages using Sequential Retriever.");

    List<String> responses = new ArrayList<>();
    for (int pageNumber = fromPage; pageNumber <= toPage; pageNumber++) {
      LOGGER.debug("Invoking service for page number {}", pageNumber);

      String response = retrieveSinglePage(apiHost, baseApiUrl, pageNumber);
      responses.add(response);
    }

    return responses;
  }

}
