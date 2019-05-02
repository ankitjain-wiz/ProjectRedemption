package org.epo.cms.edfs.services.common.externalapi;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.util.Constants;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * This is the "default" implementation of the ExternalApiService interface. This one is to be used
 * whenever we have user triggered api calls.
 * 
 * @author pvbrabant
 * 
 */
@Component
@Primary
public class UserTriggeredExternalApiService extends AbstractExternalApiService {

  /**
   * This method is used to set connection timeout if the external API does not responds within
   * given limited time.
   * 
   * @return RestTemplate as value
   * 
   * @throws CustomException handles custom exception
   * 
   */
  @Override
  protected RestTemplate getConnection() throws CustomException {
    RestTemplate restTemplate;

      SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
      factory.setReadTimeout(Constants.TIMEOUT_TIME);
      factory.setConnectTimeout(Constants.TIMEOUT_TIME);
      LOGGER.debug("Returning RestTemplate to be used for user requests.");
      restTemplate = new RestTemplate(factory);
      return restTemplate;
  }

}


