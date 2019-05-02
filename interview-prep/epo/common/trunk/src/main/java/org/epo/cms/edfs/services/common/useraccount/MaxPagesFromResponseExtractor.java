package org.epo.cms.edfs.services.common.useraccount;

/**
 * The MaxPagesFromResponseExtractor is responsible for extracting from a String based response the
 * maximum number of pages that will be returned by the service.
 * 
 * @author PV53311
 *
 */
@FunctionalInterface
interface MaxPagesFromResponseExtractor {

  /**
   * Locates the exact element from the response that indicates the maximum number of pages that are
   * needed to retrieve all the requested information from the service.
   * 
   * The different implementations of this extractor will be dependent on the structure of the
   * service response.
   * 
   * @param serviceResponse - The String based first page response from the service
   * @return The maximum number of pages that the service will return.
   */
  int extractMaximumNumberOfPagesFromResponse(final String serviceResponse);
}
