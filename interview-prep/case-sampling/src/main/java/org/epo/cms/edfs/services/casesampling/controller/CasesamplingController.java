package org.epo.cms.edfs.services.casesampling.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.casesampling.pojo.CaseSamplingRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataRequest;
import org.epo.cms.edfs.services.casesampling.pojo.CountMetaDataResponse;
import org.epo.cms.edfs.services.casesampling.pojo.DirectorateMetaDataListResponse;
import org.epo.cms.edfs.services.casesampling.pojo.DirectorateMonthCount;
import org.epo.cms.edfs.services.casesampling.pojo.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.casesampling.service.CaseSamplingService;
import org.epo.cms.edfs.services.casesampling.service.DirectorateService;
import org.epo.cms.edfs.services.casesampling.service.RandomSamplingService;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.permission.ServicePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class defines all the services in case sampling component
 * 
 * @author ankitjain2
 *
 */
@RestController
public class CasesamplingController {

	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	private CaseSamplingService caseSamplingService;

	@Autowired
	private DirectorateService directorateService;

	@Autowired
	private RandomSamplingService randomSamplingService;

	@Autowired
	private UpdaterSettingsResponse updaterSettingsResponse;

	/**
	 * this service create examiner data from ui-jsp
	 * @param caseSamplingRequest:CaseSamplingRequest
	 * @return ModelAndView:ModelAndView
	 */
	@RequestMapping(value = "/casesample", method = RequestMethod.POST)
	@ServicePermission("get_report_permissions")
	public ModelAndView caseSamplingValidate(
			@ModelAttribute("caseSamplingRequest") CaseSamplingRequest caseSamplingRequest) {

		ModelAndView modelAndView = new ModelAndView();

		caseSamplingService.updateCaseSamplingData(caseSamplingRequest);
		modelAndView.addObject("casbean", caseSamplingRequest);
		LOGGER.info("Id->" + caseSamplingRequest.getUserID());
		modelAndView.setViewName("success");
		return modelAndView;
	}

	/**
	 * @return ResponseEntity<String> : ResponseEntity<String>
	 */
	@RequestMapping(value = "/randomsample", method = RequestMethod.GET)
	@ServicePermission("get_report_permissions")
	public ResponseEntity<String> callRandomSamplingForAll() throws CustomException {

		randomSamplingService.generateNextNumToSampleForAll();
		return new ResponseEntity<>("Generated", HttpStatus.OK);
	}

	/**
	 * @return ModelAndView : ModelAndView
	 */
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	@ServicePermission("get_report_permissions")
	public ModelAndView redirect() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}

	/**
	 * @param userId
	 *            : String
	 * @return Boolean : Boolean
	 * @throws CustomException
	 *             : CustomException
	 */
	@RequestMapping(value = "/caseSampling/{userId}/{testMonth}", method = RequestMethod.GET)
	@ServicePermission("get_report_permissions")
	public Boolean getCaseSampling(@PathVariable("userId") String userId,@PathVariable("testMonth") String testMonth) throws CustomException {

		boolean response;
		response = caseSamplingService.isCaseCheckRequired(userId,testMonth);
		LOGGER.info("response->" + response);
		/* System.out.println("\n\nresponse \t===\t" + response + "\n\n\n"); */
		return response;
	}

	/**
	 * @param id
	 *            : String
	 * @return DirectorateMonthCount : DirectorateMonthCount
	 * @throws CustomException
	 *             : handles CustomException
	 */
	@RequestMapping(value = "/ignore/{id}", method = RequestMethod.GET)
	@ServicePermission("get_report_permissions")
	public DirectorateMonthCount testCaseSampling(@PathVariable("id") String id) throws CustomException {
		DirectorateMonthCount directorateMonthCount = new DirectorateMonthCount();
		if ("Y".equals(id)) {
			directorateMonthCount.setDirDesc("Director desc availible");
		}
		directorateMonthCount.setActualCount(5);
		return directorateMonthCount;
	}

	/**
	 * This service is used to update default counts and Workloadsetting of each
	 * month
	 * 
	 * @param countMetaDataRequest
	 *            : input {@link CountMetaDataRequest} class containing default
	 *            counts to be used in case sampling algorithm
	 * @param directorateId
	 *            : directorateId of the directorate
	 * @return ResponseEntity<String> : ResponseEntity<String>
	 * @throws CustomException
	 *             : handles CustomException
	 */
	@RequestMapping(value = "count/metaData/{directorateId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ServicePermission("get_report_permissions")
	public ResponseEntity<String> setMetaData(@RequestBody CountMetaDataRequest countMetaDataRequest,
			@PathVariable("directorateId") long directorateId) throws CustomException {

		caseSamplingService.updateDefaultCountData(countMetaDataRequest, directorateId);
		return new ResponseEntity<>(updaterSettingsResponse.getDescription(), HttpStatus.OK);
	}

	/**
	 * This service is used to get the default counts and workload settings of every month
	 * @param directorateId : directorateId of the directorate
	 * @return CountMetaDataResponse: {@link CountMetaDataResponse} class consist of default counts used in case sampling algorithm
	 * @throws CustomException:handles CustomException
	 */
	@RequestMapping(value = "count/metaData/{directorateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ServicePermission("get_report_permissions")
	public CountMetaDataResponse getMetaData(@PathVariable("directorateId") long directorateId) throws CustomException {

		CountMetaDataResponse metaDataResponse;
		metaDataResponse = caseSamplingService.getDefaultCountData(directorateId);
		return metaDataResponse;
	}

	/**
	 * This service returns the list of directorate's data
	 * @return DirectorateMetaDataListResponse: This is a list containing directorate's data
	 * @throws CustomException : handles Custom Exception
	 */
	@RequestMapping(value = "/directorate/metaData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ServicePermission("get_report_permissions")
	public DirectorateMetaDataListResponse getDirectorateMetaData() throws CustomException {

		DirectorateMetaDataListResponse directorateMetaDataResponse;
		directorateMetaDataResponse = directorateService.getDirectorateData();
		return directorateMetaDataResponse;
	}

	/**
	 * This service creates examiner data from swagger 
	 * @param caseSamplingRequest : {@link CaseSamplingRequest} contains examiner data for setup 
	 * @return ResponseEntity<String> : response message with code
	 */
	@RequestMapping(value = "/casesample/create", method = RequestMethod.POST)
	@ServicePermission("get_report_permissions")
	public ResponseEntity<String> caseSamplingCreateExaminers(@RequestBody CaseSamplingRequest caseSamplingRequest) {

		caseSamplingService.updateCaseSamplingData(caseSamplingRequest);
		return new ResponseEntity<>("Created Data ", HttpStatus.CREATED);
	}

}
