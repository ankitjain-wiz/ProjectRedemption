package org.epo.cms.edfs.services.casesampling.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.casesampling.pojo.CaseSamplingRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataResponse;
import org.epo.cms.edfs.services.casesampling.pojo.DirectorateMetaDataListResponse;
import org.epo.cms.edfs.services.casesampling.pojo.DirectorateMetaDataResponse;
import org.epo.cms.edfs.services.casesampling.pojo.DirectorateMonthCount;
import org.epo.cms.edfs.services.casesampling.pojo.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.casesampling.service.CaseSamplingService;
import org.epo.cms.edfs.services.casesampling.service.DirectorateService;
import org.epo.cms.edfs.services.casesampling.service.RandomSamplingService;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest({ CasesamplingController.class })
public class CasesamplingControllerTest {

	@InjectMocks
	CasesamplingController casesamplingController;

	@Mock
	private CaseSamplingService caseSamplingService;

	@Mock
	private DirectorateService directorateService;

	@Mock
	private RandomSamplingService randomSamplingService;

	@Mock
	private UpdaterSettingsResponse updaterSettingsResponse;
	
	@Mock
	private ResponseValidator responseValidator;
	
	private static final long directorateId = 1;
	private static final String userId = "Z1";
	

	@Before
	public void setUp() throws CustomException {
		
		
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetDirectorateMetaData() throws CustomException {

		DirectorateMetaDataListResponse directorateMetaDataListResponse = new DirectorateMetaDataListResponse();
		DirectorateMetaDataResponse directorateMetaDataResponse = new DirectorateMetaDataResponse();
		List<DirectorateMetaDataResponse> dataResponses = new ArrayList<>();
		directorateMetaDataResponse.setDirectorateId(directorateId);
		directorateMetaDataResponse.setDirectorId("5");
		directorateMetaDataResponse.setDirectorateName("ankit");
		dataResponses.add(directorateMetaDataResponse);
		directorateMetaDataListResponse.setDirectorateMetaDataResponse(dataResponses);

		Mockito.when(directorateService.getDirectorateData()).thenReturn(directorateMetaDataListResponse);
		DirectorateMetaDataListResponse directorateMetaDataListResponseNew = casesamplingController
				.getDirectorateMetaData();
		assertEquals(directorateMetaDataListResponseNew.getDirectorateMetaDataResponse().get(0).getDirectorateId(), 1);
	}

	@Test
	public void testGetEmptyDirectorateMetaData() throws CustomException {

		DirectorateMetaDataListResponse directorateMetaDataListResponse = new DirectorateMetaDataListResponse();
		DirectorateMetaDataResponse directorateMetaDataResponse = new DirectorateMetaDataResponse();
		List<DirectorateMetaDataResponse> dataResponses = new ArrayList<>();
		dataResponses.add(directorateMetaDataResponse);
		directorateMetaDataListResponse.setDirectorateMetaDataResponse(dataResponses);

		Mockito.when(directorateService.getDirectorateData()).thenReturn(directorateMetaDataListResponse);
		DirectorateMetaDataListResponse directorateMetaDataListResponseNew = casesamplingController
				.getDirectorateMetaData();
		assertEquals(directorateMetaDataListResponseNew.getDirectorateMetaDataResponse().get(0).getDirectorateName(),
				null);
	}

	@Test
	public void testGetMetaData() throws CustomException {
		CountMetaDataResponse countMetaDataResponse = new CountMetaDataResponse();
		countMetaDataResponse.setNumMinYearTarget(2);
		countMetaDataResponse.setNumMaxYearTarget(3);
		countMetaDataResponse.setNumMustSample(3);
		

		Mockito.when(caseSamplingService.getDefaultCountData(directorateId)).thenReturn(countMetaDataResponse);
		CountMetaDataResponse countMetaDataResponseNew = casesamplingController.getMetaData(directorateId);
		assertEquals(countMetaDataResponseNew.getNumMinYearTarget(), 2);
	}

	@Test
	public void testSetMetaData() throws CustomException {

		CountMetaDataRequest countMetaDataRequest=new CountMetaDataRequest();
		Mockito.when(updaterSettingsResponse.getDescription()).thenReturn("Update Successfull");
		ResponseEntity<String> entity = new ResponseEntity<>(updaterSettingsResponse.getDescription(),HttpStatus.OK);
		Mockito.when(caseSamplingService.updateDefaultCountData(countMetaDataRequest, directorateId)).thenReturn(1);
		casesamplingController.setMetaData(countMetaDataRequest, directorateId);
		assertEquals(entity.getStatusCode(), HttpStatus.OK);
	}

	@Test(expected = CustomException.class)
	public void testsetMetaDataException() throws CustomException {


		CountMetaDataRequest countMetaDataRequest=new CountMetaDataRequest();
		Mockito.when(caseSamplingService.updateDefaultCountData(countMetaDataRequest, directorateId)).thenThrow(new CustomException(null));
		casesamplingController.setMetaData(countMetaDataRequest, directorateId);
				
	}
	
	@Test
	public void testGetCaseSampling() throws CustomException {
		
		

		/*Mockito.when(caseSamplingService.isCaseCheckRequired(userId)).thenReturn(true);
		Boolean response = casesamplingController.getCaseSampling(userId);
		assertEquals(response, true);*/
	}
	
	@Test
	public void testCallRandomSamplingForAll() throws CustomException {
		ResponseEntity<String> response= casesamplingController.callRandomSamplingForAll();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void testCaseSamplingValidate() throws CustomException {
		
		CaseSamplingRequest caseSamplingRequest =new CaseSamplingRequest();
		Mockito.when(caseSamplingService.updateCaseSamplingData(caseSamplingRequest)).thenReturn(1);
		ModelAndView modelAndView=casesamplingController.caseSamplingValidate(caseSamplingRequest);
		assertEquals(modelAndView.getViewName(),"success");
	}
	
	@Test
	public void testRedirect() throws CustomException {
		
		
		ModelAndView modelAndView=casesamplingController.redirect();
		assertEquals(modelAndView.getViewName(),"index");
	}
	
	@Test
	public void testCaseSampling() throws CustomException {
		
		
		DirectorateMonthCount directorateMonthCountResponse =casesamplingController.testCaseSampling("Y");
		assertEquals(directorateMonthCountResponse.getActualCount(),5);
		assertEquals(directorateMonthCountResponse.getDirDesc(),"Director desc availible");
	}
	
	@Test
	public void testNoCaseSampling() throws CustomException {
		
		
		DirectorateMonthCount directorateMonthCountResponse =casesamplingController.testCaseSampling("N");
		assertEquals(directorateMonthCountResponse.getActualCount(),5);
		
		
	}

}
