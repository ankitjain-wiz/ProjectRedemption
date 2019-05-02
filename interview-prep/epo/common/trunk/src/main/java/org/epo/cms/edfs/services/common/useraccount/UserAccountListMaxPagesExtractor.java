package org.epo.cms.edfs.services.common.useraccount;


import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * The {@link MaxPagesFromResponseExtractor} that returns the maximum number of pages for the
 * ListAccount service.
 * 
 * @author PV53311
 *
 */
public final class UserAccountListMaxPagesExtractor implements MaxPagesFromResponseExtractor {

  private static final Logger LOGGER = LogManager.getLogger();

  /**
   * Default constructor.
   */
  public UserAccountListMaxPagesExtractor() {}

  /**
   * {@inheritDoc}.
   */
  @Override
  public int extractMaximumNumberOfPagesFromResponse(final String serviceResponse) {
    int maxPages = 0;
    ObjectMapper ob = new ObjectMapper();
    JsonNode node = null;

    try {
      node = ob.readValue(serviceResponse, JsonNode.class);
      JsonNode result = node.path("List_user_accounts").path("paging").path("maxpages");

      if (!result.isMissingNode()) {
        maxPages = result.get("#text").asInt();
        LOGGER.debug("The maxNumberOfPages from the response is {}", maxPages);
      }
    } catch (IOException e) {
      LOGGER.error("Exception occured when determining maximum number of pages.", e);
    }

    return maxPages;
  }

}
