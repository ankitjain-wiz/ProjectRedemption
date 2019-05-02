package org.epo.cms.edfs.services.common.useraccount;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.externalapi.ExternalApiService;
import org.epo.cms.edfs.services.common.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ThreadedPageServiceRetrievalExecutorTest {

    private final String baseApiUrl = "ui/AccountManagement/ListAccounts";
    private final String pageParamName = "page";
    private final String pageSizeParam = "pagesize";
    private final int pageSizeValue = 50;
    @Mock
    private ExternalApiService externalApiService;
    @Mock
    private MaxPagesFromResponseExtractor extractor;
    
    @Before
    public void setUp() throws IOException, CustomException {
        MockitoAnnotations.initMocks(this);
        Resource resource = new ClassPathResource("ListAccountsPage1.json");
        InputStream stream;
        stream = resource.getInputStream();
        String firstPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage2.json");
        stream = resource.getInputStream();
        String secondPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage3.json");
        stream = resource.getInputStream();
        String thirdPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage4.json");
        stream = resource.getInputStream();
        String fourthPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage5.json");
        stream = resource.getInputStream();
        String fifthPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage6.json");
        stream = resource.getInputStream();
        String sixthPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage7.json");
        stream = resource.getInputStream();
        String seventhPageResponse = IOUtils.toString(stream);
        resource = new ClassPathResource("ListAccountsPage8.json");
        stream = resource.getInputStream();
        String eightPageResponse = IOUtils.toString(stream); 
        extractor = new UserAccountListMaxPagesExtractor();    
        when(externalApiService.getCmsResponseUsingExternalApi(anyString(), anyString())).thenReturn(firstPageResponse, secondPageResponse, 
                thirdPageResponse, fourthPageResponse, fifthPageResponse, sixthPageResponse, seventhPageResponse, eightPageResponse);
    }
    
    
    @Test
    public void retrieveAllPagesEight() throws CustomException {
      ThreadedPagedServiceRetrievalExecutor executor = new ThreadedPagedServiceRetrievalExecutor(externalApiService, pageParamName, pageSizeParam, pageSizeValue, 3);
       List<String> results = executor.retrieveAllPages(Constants.CMS_HOST, baseApiUrl, extractor);
       assertEquals(8, results.size());
       //I can not assume any ordering.
       //But all responses should be in the result.
       
    }
}
