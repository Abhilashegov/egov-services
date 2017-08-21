package org.egov.mr.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.egov.mr.TestConfiguration;
import org.egov.mr.model.Fee;
import org.egov.mr.model.Page;
import org.egov.mr.service.FeeService;
import org.egov.mr.utils.FileUtils;
import org.egov.mr.web.contract.FeeCriteria;
import org.egov.mr.web.contract.FeeRequest;
import org.egov.mr.web.contract.FeeResponse;
import org.egov.mr.web.contract.RequestInfo;
import org.egov.mr.web.contract.ResponseInfo;
import org.egov.mr.web.errorhandler.ErrorHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(FeeController.class)
@Import(TestConfiguration.class)
public class FeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ErrorHandler errorHandler;
	
	@MockBean
	private FeeService feeService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testShouldSearchFee() throws IOException, Exception {
		ResponseInfo responseinfo = ResponseInfo.builder().tenantId("ap.kurnool").apiId("uief87324").resMsgId("string")
				.key("successful").status("200").ver("string").ts("string").build();

		FeeResponse FeeResponseList = FeeResponse.builder()
				.fees(getFees()).responseInfo(responseinfo).build();
		
		when(feeService.getFee(Matchers.any(FeeCriteria.class),
				Matchers.any(RequestInfo.class))).thenReturn(FeeResponseList);

			mockMvc.perform(post("/fees/_search").content(getFileContents("ReissueCertRequest.json"))
					.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(content().json(getFileContents("FeeResponse.json")));
	
	}
	
	@Test
	public void testShouldCreateFee() throws IOException, Exception{
		ResponseInfo responseinfo = ResponseInfo.builder().tenantId("ap.kurnool").apiId("uief87324").resMsgId("string")
				.key("successful").status("200").ver("string").ts("string").build();
		Page page = Page.builder().totalResults(1).currentPage(null).pageSize(null).totalPages(null).offSet(null)
				.build();
		
		FeeResponse FeeResponseList = FeeResponse.builder()
				.fees(getFees()).responseInfo(responseinfo).build();
		
		when(feeService.createAsync(Matchers.any(FeeRequest.class))).thenReturn(FeeResponseList);

		mockMvc.perform(post("/fees/_create").contentType(MediaType.APPLICATION_JSON)
				.content(getFileContents("FeeRequest.json"))).andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(getFileContents("FeeResponse.json")));
	}
	
	@Test
	public void testShouldUpdateFee() throws IOException, Exception{
		ResponseInfo responseinfo = ResponseInfo.builder().tenantId("ap.kurnool").apiId("uief87324").resMsgId("string")
				.key("successful").status("200").ver("string").ts("string").build();
		Page page = Page.builder().totalResults(1).currentPage(null).pageSize(null).totalPages(null).offSet(null)
				.build();
		
		FeeResponse FeeResponseList = FeeResponse.builder()
				.fees(getFees()).responseInfo(responseinfo).build();
		
		when(feeService.updateAsync(Matchers.any(FeeRequest.class))).thenReturn(FeeResponseList);

		mockMvc.perform(post("/fees/_update").contentType(MediaType.APPLICATION_JSON)
				.content(getFileContents("FeeRequest.json"))).andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(getFileContents("FeeResponse.json")));
	}
	
	private String getFileContents(String filePath) throws IOException {
		return new FileUtils().getFileContents("org/egov/mr/web/controller/" + filePath);
	}

	private List<Fee> getFees() {
		Fee fee = Fee.builder().tenantId("default").fee(new BigDecimal("10")).feeCriteria("2017-2018")
				.fromDate(123456789l).toDate(23456789l).build();
		List<Fee> fees = new ArrayList<>();
		fees.add(fee);

		return fees;
	}
}
