/*//@formatter:off

 *  $$Id$$
 *       . * .
 *     * RRRR  *    Copyright (c) 2018 EUIPO: European Union Intellectual
 *   .   RR  R   .  Property Office (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 
//@formatter:on
package eu.euipo.microservice.payment.ws.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.euipo.microservice.payment.configuration.RestServiceITConf;
import eu.euipo.microservice.payment.domain.BankAddress;
import eu.euipo.microservice.payment.domain.BankDetails;
import eu.euipo.microservice.payment.domain.BankDetailsRequest;
import eu.euipo.microservice.payment.domain.ws.BaseSapResponse;
import eu.euipo.microservice.payment.mapper.LetterDataXmlMapper;
import eu.euipo.microservice.payment.service.CorrespondenceService;
import eu.euipo.microservice.payment.service.FeeAndPaymentService;
import eu.euipo.microservice.payment.service.ReimbursementService;
import eu.euipo.microservice.payment.service.SapService;
import eu.ohim.common.test.rest.AbstractBaseRestServiceIT;
import eu.ohim.common.test.rest.RamlDefinition;

*//**
 * The Class ReimbursementControllerIT.
 *//*
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { RestServiceITConf.class })
@TestPropertySource(locations = { "classpath:/test.properties" })
@WebMvcTest(ReimbursementController.class)
public class ReimbursementControllerIT extends AbstractBaseRestServiceIT {

	*//** The Constant BASE_URI. *//*
	private static final String BASE_URI = "http://10.133.102.33";

	*//** The reimbursement service. *//*
	@MockBean
	private ReimbursementService reimbursementService;

	*//** The sap service. *//*
	@MockBean
	private SapService sapService;

	@MockBean
	private CorrespondenceService correspondenceService;

	@MockBean
	private LetterDataXmlMapper letterDataXmlMapper;

    @MockBean
    private FeeAndPaymentService feeAndPaymentService;

	*//**
	 * Test get reimbursement id.
	 *
	 * @throws Exception
	 *             the exception
	 *//*

	*//**
	 * When get bank details.
	 *
	 * @throws Exception
	 *             the exception
	 *//*
	@Test
	@RamlDefinition(value = "classpath:/raml/reimbursement.raml", baseUri = BASE_URI)
	public void WhenGetBankDetails() throws Exception {

		// Note check the required field in schema corresponding to .raml files when
		// adding values here
		when(this.sapService.getBankDetails(Mockito.anyString())).thenReturn(getBankDetailsList());

		getMockMvc().perform(get("/payment/reimbursements/123/bankdetails")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(ramlMatches())
				.andExpect(jsonPath("$.[0].ibanCode", is("CY09090")))
				.andExpect(jsonPath("$.[0].bicSwiftCode", is("CB8787")));

	}

	@Test
	@RamlDefinition(value = "classpath:/raml/reimbursement.raml", baseUri = BASE_URI)
	public void WhenUpdateBankDetails() throws Exception {
		BankDetailsRequest bankDetails = getMockData();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(bankDetails);
		BaseSapResponse baseSapResponse = new BaseSapResponse();
		baseSapResponse.setReturnCode("01");

		Mockito.when(this.sapService.syncBankDetailsWithSap(Mockito.anyBoolean(), Mockito.anyString(), Mockito.any()))
				.thenReturn(baseSapResponse);

		getMockMvc()
				.perform(post("/payment/reimbursements/123/bankdetails/synchronisation")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(jsonInString))
				.andExpect(status().isOk()).andExpect(ramlMatches());

	}

	private BankDetailsRequest getMockData() {
		BankDetailsRequest bankDetails = new BankDetailsRequest();
		bankDetails.setAccountHolderName("testaccountHolderName");
		bankDetails.setAttachment("testattachment");
		bankDetails.setCustomerId("testcutomerId");
		bankDetails.setDescription("testdescription");
		bankDetails.setDocType("testdocType");
		bankDetails.setFileName("testfileName");
		bankDetails.setIban("testiBAN");
		bankDetails.setMimeCode("testmimeCode");
		BankAddress bankAddress = new BankAddress();
		bankAddress.setAddrno("testaddrno");
		bankAddress.setBankbranch("testbankbranch");
		bankAddress.setBankgroup("testbankgroup");
		bankAddress.setBankname("testbankname");
		bankAddress.setBankno("testbankno");
		bankAddress.setCity("testcity");
		bankAddress.setPobkcurac("testpobkcurac");
		bankAddress.setPostbank("testpostbank");
		bankAddress.setRegion("testregion");
		bankAddress.setStreet("teststreet");
		bankAddress.setSwiftcode("testswiftcode");

		bankDetails.setBankAddress(bankAddress);
		return bankDetails;
	}

	*//**
	 * Gets the bank details list.
	 *
	 * @return the bank details list
	 *//*
	private List<BankDetails> getBankDetailsList() {
		BankDetails bankDetails = new BankDetails();
		bankDetails.setIbanCode("CY09090");
		bankDetails.setBicSwiftCode("CB8787");

		List<BankDetails> bankDetailsList = new ArrayList<>();
		bankDetailsList.add(bankDetails);
		return bankDetailsList;
	}
}
*/