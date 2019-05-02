package org.epo.cms.edfs.services.common.useraccount;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.externalapi.ExternalApiService;
import org.epo.cms.edfs.services.common.util.Constants;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class SequentialPagedServiceRetrievalExecutorTest {
    
    private ExternalApiService externalApiService;
    
    private final String baseApiUrl = "ui/AccountManagement/ListAccounts";
    private final String pageParamName = "page";
    private final String pageSizeParam = "pagesize";
    private final int pageSizeValue = 50;
    
    private String firstPageResponse;
    private String secondPageResponse;
    private String thirdPageResponse;
    private String fourthPageResponse;
    private String fifthPageResponse;
    private String sixthPageResponse;
    private String seventhPageResponse;
    private String eightPageResponse;
    
    
    @Test
    public void retrieveAllPagesTest () throws CustomException, IOException {
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

        MaxPagesFromResponseExtractor extractor = new UserAccountListMaxPagesExtractor();
        AbstractPagedServiceRetrievalExecutor executor = new SequentialPagedServiceRetrievalExecutor(externalApiService, pageParamName, pageSizeParam, pageSizeValue);
        List<String> results = executor.retrieveAllPages(Constants.CMS_HOST, baseApiUrl, extractor);
        assertEquals(8, results.size());
        assertEquals(firstPageResponse, results.get(0));
        assertEquals(secondPageResponse, results.get(1));
        assertEquals(eightPageResponse, results.get(7));
    }
    
}
