/*//@formatter:off
*//**
 *  $$Id$$
 *       . * .
 *     * RRRR  *    Copyright (c) 2017 EUIPO: European Union Intellectual
 *   .   RR  R   .  Property Office (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 *//*
//@formatter:on
package eu.euipo.microservice.payment.ws.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.euipo.microservice.payment.domain.BankDetails;
import eu.euipo.microservice.payment.domain.BankDetailsRequest;
import eu.euipo.microservice.payment.domain.EcommLetter;
import eu.euipo.microservice.payment.domain.ws.BaseSapResponse;
import eu.euipo.microservice.payment.service.ReimbursementService;
import eu.euipo.microservice.payment.service.SapService;

*//**
 * The Reimbursement controller.
 *//*
@RestController
@RequestMapping(path = "/payment/reimbursements", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReimbursementController {

	*//** The Constant LOGGER. *//*
	private static final Logger LOGGER = LoggerFactory.getLogger(ReimbursementController.class);

	*//** The sap service. *//*
	@Autowired
	private SapService sapService;

	*//** The reimbursement service. *//*
	@Autowired
	private ReimbursementService reimbursementService;

	*//**
	 * Get bankDetails of reimbursementIdentifier.
	 *
	 * @param reimbursementIdentifier
	 *            the reimbursement identifier
	 * @return the List of BankDetail i.e List<BankDetails>
	 *//*
	@ApiMethod(description = "This is the source of bank details ")
	@ApiResponseObject
	@GetMapping(path = "/{reimbursementIdentifier}/bankdetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BankDetails> getBankDetail(@PathVariable("reimbursementIdentifier") String reimbursementIdentifier) {
		LOGGER.info("Start of getBankDetail method of ReimbursementController : {}", reimbursementIdentifier);
		List<BankDetails> listBankDetails = null;
		if (reimbursementIdentifier != null) {
			listBankDetails = sapService.getBankDetails(reimbursementIdentifier);
		}
		LOGGER.info("End of getBankDetail method of ReimbursementController");
		return listBankDetails;
	}

	*//**
	 * Update BankDetails of any reimbursementIdentifier.
	 *
	 * @param reimbursementIdentifier
	 *            the reimbursement identifier
	 * @param bankDetails
	 *            the bank details
	 * @return the void
	 *//*
	@ApiMethod(description = "This is to update the bank details in Sap")
	@ApiResponseObject
	@PostMapping(path = "/{reimbursementIdentifier}/bankdetails/synchronisation", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseSapResponse> updateBankDetail(
			@PathVariable("reimbursementIdentifier") String reimbursementIdentifier,
			@RequestBody BankDetailsRequest bankDetails) {
		LOGGER.info("Start of updateBankDetail method of ReimbursementController : {}", reimbursementIdentifier);
		BaseSapResponse baseSapResponse = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		try {
			baseSapResponse = sapService.syncBankDetailsWithSap(false, reimbursementIdentifier, bankDetails);
			if ("01".equals(baseSapResponse.getReturnCode())) {
				return new ResponseEntity<>(baseSapResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(baseSapResponse, HttpStatus.BAD_GATEWAY);
			}

		} catch (Exception e) {
			LOGGER.error("Excpetion caught in updateBankDetail method of ReimbursementController : {}", e.getMessage(),
					e);
			responseHeaders.add("ExceptionCause", e.getMessage());
			responseHeaders.add("ExceptionClass", e.getClass().getName());
			return new ResponseEntity<>(baseSapResponse, responseHeaders, HttpStatus.FORBIDDEN);

		}
	}

	*//**
	 * Update bank details.
	 *
	 * @param reimbursementIdentifier
	 *            the reimbursement identifier
	 * @param ecommLetter
	 *            the ecomm letter
	 * @return the response entity
	 *//*
	@ApiMethod(description = "This is the source of bank details ")
	@ApiResponseObject
	@PostMapping(path = "/{reimbursementIdentifier}/bankdetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateBankDetails(
			@PathVariable("reimbursementIdentifier") String reimbursementIdentifier,
			@RequestBody EcommLetter ecommLetter) {
		LOGGER.info("Start of updateBankDetails method of ReimbursementController : {}",
				ecommLetter.getReimbursementId());

		if (StringUtils.isEmpty(reimbursementIdentifier) || StringUtils.isEmpty(ecommLetter.getReimbursementId())) {
			return ResponseEntity.badRequest().body("Reimbursement Identifier cannot be null");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Start of updateBankDetails method of ReimbursementController : {}", ecommLetter);
		}

		return this.reimbursementService.updateReimbursementStatus(reimbursementIdentifier, ecommLetter);
	}

	*//**
	 * Gets the reimbursement id.
	 *
	 * @return the reimbursement id
	 *//*
	@PostMapping(path = "/identifiers")
	public String getReimbursementId() {
		return this.reimbursementService.getReimbursementId("reimbursement");
	}
}
*/