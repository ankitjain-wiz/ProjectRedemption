package org.epo.cms.edfs.services.common.useraccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.epo.cms.edfs.services.common.beans.UserAccountDetail;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.externalapi.ExternalApiService;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class PagingAwareUserAccountDetailsRetrieverTest {

    private ExternalApiService externalApiService;
    
    private UserAccountDetailsService accountDetailsService;
    
    private String firstPageResponse;
    private String secondPageResponse;
    private String thirdPageResponse;
    private String fourthPageResponse;
    private String fifthPageResponse;
    private String sixthPageResponse;
    private String seventhPageResponse;
    private String eightPageResponse;


    @Test
    public void getUserDetailsEightPagesSequentialTest () throws IOException, CustomException {
        
        Resource resource = new ClassPathResource("ListAccountsPage1.json");
        InputStream stream;
        stream = resource.getInputStream();
        firstPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage2.json");
        stream = resource.getInputStream();
        secondPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage3.json");
        stream = resource.getInputStream();
        thirdPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage4.json");
        stream = resource.getInputStream();
        fourthPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage5.json");
        stream = resource.getInputStream();
        fifthPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage6.json");
        stream = resource.getInputStream();
        sixthPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage7.json");
        stream = resource.getInputStream();
        seventhPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage8.json");
        stream = resource.getInputStream();
        eightPageResponse = IOUtils.toString(stream);

        
        externalApiService = mock(ExternalApiService.class);
        when(externalApiService.getCmsResponseUsingExternalApi(anyString(), anyString())).thenReturn(firstPageResponse, secondPageResponse, 
                thirdPageResponse, fourthPageResponse, fifthPageResponse, sixthPageResponse, seventhPageResponse, eightPageResponse);

        AbstractPagedServiceRetrievalExecutor executor = new SequentialPagedServiceRetrievalExecutor(externalApiService, "page", "pagesize", 50);
        accountDetailsService = new PagingAwareUserAccountDetailsRetriever(externalApiService, executor);
        
        Map<String, UserAccountDetail> accountDetails = accountDetailsService.getUserAccountDetails();
        assertNotNull(accountDetails);
        assertFalse(accountDetails.isEmpty());
        assertEquals(400, accountDetails.size());
        
        //Checking some accounts. 2 from each page
        //YW03820  page 1
        //ZN53461  page 1
        //YP02475  page 2
        //YP02475  page 2
        assertNotNull(accountDetails.get("YW03820"));
        assertTrue(accountDetails.containsKey("YW03820"));
        assertTrue(accountDetails.containsKey("ZN53461"));
        assertTrue(accountDetails.containsKey("YP02475"));
        assertTrue(accountDetails.containsKey("YP02475"));
    }
    
    @Test
    public void getUserDetailsEightPagesThreadedTest () throws IOException, CustomException {
        
        Resource resource = new ClassPathResource("ListAccountsPage1.json");
        InputStream stream;
        stream = resource.getInputStream();
        firstPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage2.json");
        stream = resource.getInputStream();
        secondPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage3.json");
        stream = resource.getInputStream();
        thirdPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage4.json");
        stream = resource.getInputStream();
        fourthPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage5.json");
        stream = resource.getInputStream();
        fifthPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage6.json");
        stream = resource.getInputStream();
        sixthPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage7.json");
        stream = resource.getInputStream();
        seventhPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage8.json");
        stream = resource.getInputStream();
        eightPageResponse = IOUtils.toString(stream);

        
        externalApiService = mock(ExternalApiService.class);
        when(externalApiService.getCmsResponseUsingExternalApi(anyString(), anyString())).thenReturn(firstPageResponse, secondPageResponse, 
                thirdPageResponse, fourthPageResponse, fifthPageResponse, sixthPageResponse, seventhPageResponse, eightPageResponse);

        AbstractPagedServiceRetrievalExecutor executor = new ThreadedPagedServiceRetrievalExecutor(externalApiService, "page", "pagesize", 50, 3);
        accountDetailsService = new PagingAwareUserAccountDetailsRetriever(externalApiService, executor);
        
        Map<String, UserAccountDetail> accountDetails = accountDetailsService.getUserAccountDetails();
        assertNotNull(accountDetails);
        assertFalse(accountDetails.isEmpty());
        assertEquals(400, accountDetails.size());
        
        //Checking some accounts. 2 from each page
        //YW03820  page 1
        //ZN53461  page 1
        //YP02475  page 2
        //YP02475  page 2
        assertNotNull(accountDetails.get("YW03820"));
        assertTrue(accountDetails.containsKey("YW03820"));
        assertTrue(accountDetails.containsKey("ZN53461"));
        assertTrue(accountDetails.containsKey("YP02475"));
        assertTrue(accountDetails.containsKey("YP02475"));
    }
    
    @Test
    public void getUserDetailsSingleUserTest () throws IOException, CustomException {
        Resource resource = new ClassPathResource("listAccountDetailsSingleResult.json");
        InputStream stream = resource.getInputStream();
        String singleResult = IOUtils.toString(stream);
        
        externalApiService = mock(ExternalApiService.class);
        when(externalApiService.getCmsResponseUsingExternalApi(anyString(), anyString())).thenReturn(singleResult);
        accountDetailsService = new PagingAwareUserAccountDetailsRetriever(externalApiService);
        
        UserAccountDetail accountDetails = accountDetailsService.getUserAccountDetail("PV53311");
        assertNotNull(accountDetails);
        assertEquals("PV53311", accountDetails.getUserID());
        assertEquals("PV53311 Patrick Vanbrabant", accountDetails.getCaseName());
        assertEquals("en_EN", accountDetails.getPreferredLanguage());
    }
}
