package org.epo.cms.edfs.services.common.externalapi;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.kerberos.client.KerberosRestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * This is the "default" implementation of the ExternalApiService interface. This one is to be used
 * whenever we have scheduled api calls. In that case there is no "real" user and we should invoke
 * the external service call using the F8 account.
 * 
 * @author pvbrabant
 * 
 */
@Component
@Qualifier("ScheduledApiService")
public class SchedulerTriggeredExternalApiService extends AbstractExternalApiService {

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
    LOGGER.debug("Returning RestTemplate to be used for scheduling.");

    restTemplate = new KerberosRestTemplate(propertyFileReader.getValue("f8.keytab"),
        propertyFileReader.getValue("f8.principal"));

    return restTemplate;
  }



}


