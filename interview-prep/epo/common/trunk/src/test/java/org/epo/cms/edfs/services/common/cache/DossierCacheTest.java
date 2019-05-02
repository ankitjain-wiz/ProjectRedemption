package org.epo.cms.edfs.services.common.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jcs.access.CacheAccess;
import org.epo.cms.edfs.services.common.beans.UserAccountDetail;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.useraccount.UserAccountDetailsService;
import org.epo.cms.edfs.services.common.util.Constants;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(PowerMockRunner.class)
@PowerMockIgnore({ "javax.management.*" })
@SuppressWarnings("unchecked")
@PrepareForTest({ DossierCache.class, RestTemplate.class, Map.class, ObjectMapper.class, JsonNode.class })
public class DossierCacheTest {

	private static final String DEFAULT_USER_ID = "edoDI02";

  private static final String E_DOSSIER_TEST_DIRECTOR = "eDossier Test Director";

  private static final String USER_ID = "EDODI02";

  @Mock
	private CacheAccess<Object, Object> userCache;

	@Mock
	private PropertyFileReader propertyFileReader;

	@InjectMocks
	private DossierCache dossierCache;

	@Mock
	private UserAccountDetailsService userAccountService;

	@Mock
	private ResponseValidator responseValidator;
	
	@Mock
	private Map<String, UserAccountDetail> mockDetails;

	private UserAccountDetail detail;
	
	private String userId;
	
	@Before
	public void setUp () {
	    detail = new UserAccountDetail();
	    detail.setUserID(USER_ID);
	    detail.setCaseName(USER_ID);
	    detail.setGivenName(E_DOSSIER_TEST_DIRECTOR);
	    detail.setFamilyName(E_DOSSIER_TEST_DIRECTOR);
	}

	@Test 
	public void getUserDetailsCachingCacheNullUserFoundInServiceTest () throws CustomException {
	    userId = DEFAULT_USER_ID;
	    when(userCache.get(Constants.USER_DETAILS)).thenReturn(null);
	    when(userAccountService.getUserAccountDetail(anyString())).thenReturn(detail);
	    
	    UserAccountDetail retrieved = dossierCache.getUserAccountDetails(userId);
	    assertNotNull(retrieved);
	    assertEquals(detail, retrieved);
	    //Because of the normalizationof tjhe userId paramter, the call is with the uppercase.
	    verify(userAccountService, times(1)).getUserAccountDetail(USER_ID);
	    verify(userAccountService, times(0)).getUserAccountDetail(userId);
	    verify(userCache, times(1)).put(any(), any());
	}
	
	
	@Test 
    public void getUserDetailsCachingCacheEmptyUserFoundInServiceTest () throws CustomException {
        userId = DEFAULT_USER_ID;
        Map<String, UserAccountDetail> details = new HashMap<String,UserAccountDetail>();
        when(userCache.get(Constants.USER_DETAILS)).thenReturn(details);
        when(userAccountService.getUserAccountDetail(anyString())).thenReturn(detail);
        
        UserAccountDetail retrieved = dossierCache.getUserAccountDetails(userId);
        assertNotNull(retrieved);
        assertEquals(detail, retrieved);
        assertEquals(1, details.size());
        
        //Because of the normalizationof tjhe userId paramter, the call is with the uppercase.
        verify(userAccountService, times(1)).getUserAccountDetail(USER_ID);
        verify(userAccountService, times(0)).getUserAccountDetail(userId);
        verify(userCache, times(0)).put(any(), any());
    }
	
    @Test 
    public void getUserDetailsCachingCacheNullUserNotFoundInServiceTest () throws CustomException {
        userId = DEFAULT_USER_ID;
        when(userCache.get(Constants.USER_DETAILS)).thenReturn(null);
        when(userAccountService.getUserAccountDetail(anyString())).thenReturn(null);
        
        UserAccountDetail retrieved = dossierCache.getUserAccountDetails(userId);
        assertNull(retrieved);
        
        //Because of the normalizationof tjhe userId paramter, the call is with the uppercase.
        verify(userAccountService, times(1)).getUserAccountDetail(USER_ID);
        verify(userAccountService, times(0)).getUserAccountDetail(userId);
        //Retriever user from service was null. Is not put into the cache.
        verify(userCache, times(0)).put(any(), any());
    }

    @Test 
    public void getUserDetailsCachingCacheEmptyUserNotFoundInServiceTest () throws CustomException {
        userId = DEFAULT_USER_ID;
        Map<String, UserAccountDetail> details = new HashMap<String,UserAccountDetail>();
        when(userCache.get(Constants.USER_DETAILS)).thenReturn(null);
        when(userAccountService.getUserAccountDetail(anyString())).thenReturn(null);
        
        UserAccountDetail retrieved = dossierCache.getUserAccountDetails(userId);
        assertNull(retrieved);
        //User was not found, so also not added to existing user map
        assertEquals(0, details.size());
        
        //Because of the normalizationof tjhe userId paramter, the call is with the uppercase.
        verify(userAccountService, times(1)).getUserAccountDetail(USER_ID);
        verify(userAccountService, times(0)).getUserAccountDetail(userId);
        //Retriever user from service was null. Also cache was already in place.
        verify(userCache, times(0)).put(any(), any());
    }
    
    @Test 
    public void getUserDetailsCachingCacheContainsUserTest () throws CustomException {
        userId =USER_ID;
        Map<String, UserAccountDetail> details = new HashMap<String,UserAccountDetail>();
        details.put(detail.getUserID(), detail);
        when(userCache.get(Constants.USER_DETAILS)).thenReturn(details);
        when(userAccountService.getUserAccountDetail(anyString())).thenReturn(detail);
        
        UserAccountDetail retrieved = dossierCache.getUserAccountDetails(userId);
        assertNotNull(retrieved);
        assertEquals(detail, retrieved);

        //User was found in cache, so also service should not be called.
        //Because of the normalizationof tjhe userId paramter, the call is with the uppercase.
        verify(userAccountService, times(0)).getUserAccountDetail(anyString());
    }
    

    @Test 
    public void getUserDetailsCachingCacheContainsUserLongFromatTest () throws CustomException {
        userId = "edoDI02@INTERNAL.epo.org";
        when(mockDetails.get(any())).thenReturn(detail);
        when(userCache.get(Constants.USER_DETAILS)).thenReturn(mockDetails);
        when(userAccountService.getUserAccountDetail(anyString())).thenReturn(detail);
        
        UserAccountDetail retrieved = dossierCache.getUserAccountDetails(userId);
        assertNotNull(retrieved);
        assertEquals(detail, retrieved);

        //User was found in cache, so also service should not be called.
        //Because of the normalizationof tjhe userId paramter, the call is with the uppercase.
        verify(userAccountService, times(0)).getUserAccountDetail(anyString());
        //Validate that the mas was indeed called with the normalized userid
        verify(mockDetails, times(1)).get(USER_ID);
    }


    @Test
    public void refreshUserDetailsCacheTest() throws CustomException {
        Map<String, UserAccountDetail> details = new HashMap<String,UserAccountDetail>();
        when(userAccountService.getUserAccountDetails()).thenReturn(details);
        
        Map<String, UserAccountDetail> retrievedDetails = dossierCache.refreshUserDetailsCache();
        assertNotNull(retrievedDetails);
        assertEquals(details, retrievedDetails);
        verify(userCache, times(1)).clear();
        verify(userCache, times(1)).put(any(), any());
    }

}
