/*
package rpa.process.app.controller;

import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import rpa.process.app.entity.ClientParameterDetail;
import rpa.process.app.repository.ClientParameterDetailRepository;
import rpa.process.app.service.impl.ClientParameterDetailServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParameterSpecificControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private ClientParameterDetailRepository clientparameterdetailrepository;
	@InjectMocks
	private ClientParameterDetailServiceImpl clientservice;

	private ClientParameterDetail clientparameterdetail; // private
	
	 * @Test public void testCreateClientSepecificParam() { }
	 * 
	 * @Test public void testCreateProcessSepecificParam() { }
	 

	@Test
	public void testGetClientSepecificParam() throws Exception {
		Mockito.when(clientservice.getClientSepcificParameters()).thenReturn(this.getClientSpecificParamlist());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getclientspecificparam")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[ {\r\n" + "        \"clientparameterdetailid\": 1,\r\n" + "        \"workingdays\": 1,\r\n"
				+ "        \"workinghours\": 1,\r\n" + "        \"overhead\": 1,\r\n"
				+ "        \"costrateprojectmanager\": 1,\r\n" + "        \"crossrateitstaf\": 1,\r\n"
				+ "        \"costratesme\": 1,\r\n" + "        \"rpalicensecost\": 1,\r\n"
				+ "        \"infracost\": 1\r\n" + "    }]";
		assumeTrue(mvcResult.getResponse().getContentAsString().contains(expected));
	}

	
	 * @Test public void testGetprocessSepecificParam() { }
	 

	private List<ClientParameterDetail> getClientSpecificParamlist() {
		List<ClientParameterDetail> list = new ArrayList<ClientParameterDetail>();
		clientparameterdetail = new ClientParameterDetail();
		clientparameterdetail.setClientparameterdetailid(1L);
		clientparameterdetail.setCostrateprojectmanager(1);
		clientparameterdetail.setCostratesme(1);
		clientparameterdetail.setCrossrateitstaf(1);
		clientparameterdetail.setInfracost(1);
		clientparameterdetail.setOverhead(1);
		clientparameterdetail.setRpalicensecost(1);
		clientparameterdetail.setWorkingdays(1);
		clientparameterdetail.setWorkinghours(1);
		;
		list.add(clientparameterdetail);
		return list;
	}
}
*/