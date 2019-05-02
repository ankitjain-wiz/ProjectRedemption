package org.epo.cms.edfs.services.common.useraccount;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.externalapi.ExternalApiService;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * The PagedServiceRetrievalExecutor is responsible for retrieving (GET method) information from the
 * specified rest endpoint.
 * 
 * The REST endpoint should be returning the information on each page as a String result.
 * 
 * @author PV53311
 *
 */
abstract class AbstractPagedServiceRetrievalExecutor {

  private static final Logger LOGGER = LogManager.getLogger();

  private final ExternalApiService externalApiService;
  private final String pageSizeParamName;
  private final int pageSizeValue;
  private final String pageParamName;

  /**
   * Constructor for the PagedServiceRetrievalExecutor.
   * 
   * @param apiService - The {@link ExternalApiService} to use for invoking the external api.
   * @param pageParamName - The name of the page indicator parameter
   * @param pageSizeParamName - The name of the pageSize parameter
   * @param pageSizeValue - The value to use for the pageSize parameter
   */
  public AbstractPagedServiceRetrievalExecutor(final ExternalApiService apiService,
      String pageParamName, final String pageSizeParamName, final int pageSizeValue) {
    this.externalApiService = apiService;
    this.pageParamName = pageParamName;
    this.pageSizeParamName = pageSizeParamName;
    this.pageSizeValue = pageSizeValue;
  }


  /**
   * Retrieves the different pages from the service using the provided {@link ExternalApiService}
   * for invoking the operation. For the initial request, the pageParamName will be added to the
   * baseApiUri with the value of 1. Based on the response for the initial first page request, we
   * will determine the maximum number of pages and will invoke the service again for each page. The
   * results from all these calls will be accumulated in a List of String.
   * 
   * @param apiHost - The identifier for the API Host.
   * @param baseApiUrl - The basEAPI url, this url does not yet contain the paging parameter.
   * @param extractor - The {@link MaxPagesFromResponseExtractor} used for extracting the maximum
   *        number of pages from the inital response.
   * 
   * @return - The accumulated result of the many different calls to the system.
   * @throws CustomException - to handle the exception
   */
  public List<String> retrieveAllPages(final String apiHost, final String baseApiUrl,
      final MaxPagesFromResponseExtractor extractor) throws CustomException {
    List<String> responses = new ArrayList<>();
    UriComponentsBuilder builder = UriComponentsBuilder.fromPath(baseApiUrl)
        .queryParam(pageParamName, 1).queryParam(pageSizeParamName, pageSizeValue);
    String cmsUri = builder.toUriString().intern();

    LOGGER.debug("Retrieving all pages for api using url {}", cmsUri);

    String response = externalApiService.getCmsResponseUsingExternalApi(cmsUri, apiHost);
    responses.add(response);

    int maxPages = extractor.extractMaximumNumberOfPagesFromResponse(response);

    List<String> remainingPages = retrieveRemainingPages(apiHost, baseApiUrl, 2, maxPages);
    responses.addAll(remainingPages);

    return responses;
  }

  /**
   * Retrieves the result of a single page call to the external api.
   * 
   * @param apiHost - The identifier for the API Host.
   * @param baseApiUrl - The basEAPI url, this url does not yet contain the paging parameter.
   * @param pageNumber - The page to retrieve.
   * @return -
   * @throws CustomException - to handle the exception
   */
  protected String retrieveSinglePage(final String apiHost, final String baseApiUrl,
      final int pageNumber) throws CustomException {
    String cmsUri = UriComponentsBuilder.fromPath(baseApiUrl).queryParam(pageParamName, pageNumber)
        .queryParam(pageSizeParamName, pageSizeValue).toUriString();
    return externalApiService.getCmsResponseUsingExternalApi(cmsUri, apiHost);
  }


  /**
   * Triggers the retrieval of the remaining pages.
   * 
   * @param apiHost - The identifier for the API Host.
   * @param baseApiUrl - The basEAPI url, this url does not yet contain the paging parameter.
   * @param fromPage - The starting page of the remaining pages
   * @param toPage - The ending page of the remaining pages
   * @return The accumulated result of the many different calls to the system.
   * @throws CustomException - to handle the exception
   */
  protected abstract List<String> retrieveRemainingPages(final String apiHost,
      final String baseApiUrl, final int fromPage, final int toPage) throws CustomException;
}
