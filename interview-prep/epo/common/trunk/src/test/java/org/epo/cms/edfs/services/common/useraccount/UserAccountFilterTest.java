package org.epo.cms.edfs.services.common.useraccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.epo.cms.edfs.services.common.beans.UserAccountDetail;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class UserAccountFilterTest {

  private UserAccountFilter userAccountFilter;

  public static final String LISTUSERACCOUNTS = "List_user_accounts";

  private String multipleResultsResponse;
  private String singleResultsResponse;
  private String noResultsResponse;


  @Before
  public void setUp() throws IOException {
    userAccountFilter = new UserAccountFilter();

    InputStream stream;
    Resource resource = new ClassPathResource("ListAccountsPage1.json");
    stream = resource.getInputStream();
    multipleResultsResponse = IOUtils.toString(stream);

    resource = new ClassPathResource("listAccountDetailsSingleResult.json");
    stream = resource.getInputStream();
    singleResultsResponse = IOUtils.toString(stream);

    resource = new ClassPathResource("listAccountDetailsNoReult.json");
    stream = resource.getInputStream();
    noResultsResponse = IOUtils.toString(stream);
  }


  @Test
  public void getUserAccountDetailsMultipleResultsTest() throws CustomException {
    Map<String, UserAccountDetail> result =
        userAccountFilter.getUserAccountDetails(multipleResultsResponse);
    assertNotNull(result);
    assertEquals(50, result.size());
  }

  @Test
  public void getUserAccountDetailsSingleResultTest() throws CustomException {
    Map<String, UserAccountDetail> result =
        userAccountFilter.getUserAccountDetails(singleResultsResponse);
    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  public void getUserAccountDetailsNoResultsTest() throws CustomException {
    Map<String, UserAccountDetail> result =
        userAccountFilter.getUserAccountDetails(noResultsResponse);
    assertNotNull(result);
    assertEquals(0, result.size());
  }
}
