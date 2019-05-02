/*
package com.integration.test;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import eu.euipo.microservice.payment.domain.BankDetails;
import eu.euipo.microservice.payment.service.ReimbursementService;
import eu.euipo.microservice.payment.service.SapService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ReimbursementController.class, secure = false)
public class ParameterSpecificControllerTes {

	@Autowired
	private MockMvc mockMvc;

	*//** The sap service. *//*
	@MockBean
	private SapService sapService;
	
	@MockBean
	private ReimbursementService reimbursementService;


	@Test
	public void WhenGetBankDetails() throws Exception {

		when(this.sapService.getBankDetails(Mockito.anyString())).thenReturn(getBankDetailsList());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/payment/reimbursements/123/bankdetails");

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(mvcResult.getResponse().getContentAsString());

	}

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