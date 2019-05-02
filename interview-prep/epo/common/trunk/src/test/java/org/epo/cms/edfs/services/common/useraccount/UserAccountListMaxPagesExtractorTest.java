package org.epo.cms.edfs.services.common.useraccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class UserAccountListMaxPagesExtractorTest {
    
    @Test
    public void retrieveMaxPagesFromUserList () throws IOException {
        Resource resource = new ClassPathResource("listAccountsPaging.json");
        InputStream stream;
       stream = resource.getInputStream();
       String listAccountResponse = IOUtils.toString(stream);
       UserAccountListMaxPagesExtractor extractor = new UserAccountListMaxPagesExtractor();
       int maxPages = extractor.extractMaximumNumberOfPagesFromResponse(listAccountResponse);
       assertEquals(376, maxPages);
       
    }
    
    @Test
    public void retrieveMaxPagesFromUserListWithDataTest() throws IOException {
        Resource resource = new ClassPathResource("listAccountComplete.json");
        InputStream stream;
        stream = resource.getInputStream();
        String listAccountResponse = IOUtils.toString(stream);
        UserAccountListMaxPagesExtractor extractor = new UserAccountListMaxPagesExtractor();
         int maxPages = extractor.extractMaximumNumberOfPagesFromResponse(listAccountResponse);
         assertEquals(2, maxPages);
    }
    
    @Test
    public void retrieveMaxPagesFromCorruptUserListTest() throws IOException {
      Resource resource = new ClassPathResource("listAccountCorrupt.json");
      InputStream stream; 
      stream = resource.getInputStream();
      String listAccountResponse = IOUtils.toString(stream);
      UserAccountListMaxPagesExtractor extractor = new UserAccountListMaxPagesExtractor();
      int maxPages = extractor.extractMaximumNumberOfPagesFromResponse(listAccountResponse);
      assertEquals(0, maxPages); 
    }
}
