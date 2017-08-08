package org.egov.mr.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.egov.mr.model.MarriageDocumentType;
import org.egov.mr.model.enums.ApplicationType;
import org.egov.mr.model.enums.DocumentProof;
import org.egov.mr.repository.MarriageDocumentTypeRepository;
import org.egov.mr.repository.rowmapper.MarriageDocumentTypeRowMapper;
import org.egov.mr.service.MarriageDocumentTypeService;
import org.egov.mr.utils.FileUtils;
import org.egov.mr.web.contract.MarriageDocTypeRequest;
import org.egov.mr.web.contract.MarriageDocTypeResponse;
import org.egov.mr.web.contract.MarriageDocumentTypeSearchCriteria;
import org.egov.mr.web.contract.RequestInfo;
import org.egov.mr.web.contract.ResponseInfo;
import org.egov.mr.web.errorhandler.Error;
import org.egov.mr.web.errorhandler.ErrorHandler;
import org.egov.mr.web.errorhandler.ErrorResponse;
import org.egov.mr.web.errorhandler.FieldError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.joran.conditional.ThenAction;

@RunWith(SpringRunner.class)
@WebMvcTest(MarriageDocumentTypeController.class)
public class MarriageDocumentTypeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ErrorHandler errorHandler;

	@MockBean
	private KafkaTemplate<String, Object> kafkaTemplate;

	@MockBean
	private MarriageDocumentTypeService marriageDocumentTypeService;

	@InjectMocks
	private MarriageDocumentTypeController marriageDocumentTypeController;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @Test_Create
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCreate() {

		/**
		 * @Response_Object_For_Create
		 */
		List<MarriageDocumentType> marriageDocumentTypes = getMarriageDocumentTypesForCreate();

		MarriageDocTypeResponse marriageDocTypeResponse = new MarriageDocTypeResponse();
		marriageDocTypeResponse.setMarriageDocTypes(marriageDocumentTypes);

		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setStatus(HttpStatus.OK.toString());

		marriageDocTypeResponse.setResponseInfo(responseInfo);
		when(errorHandler.handleBindingErrorsForSearch(Matchers.any(RequestInfo.class), Matchers.any(), Matchers.any()))
				.thenReturn(new ResponseEntity(marriageDocumentTypes, HttpStatus.OK));

		when(marriageDocumentTypeService.createAsync(Matchers.any(MarriageDocTypeRequest.class)))
				.thenReturn(new ResponseEntity(marriageDocTypeResponse, HttpStatus.OK));

		/**
		 * @Controller_Create
		 */
		try {
			mockMvc.perform(post("/marriageRegns/documents/_create")
					.content(getFileContents("marriageDocumentTypeCreateRequest.json"))
					.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().is(200))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(content().json(getFileContents("marriageDocumentTypeCreateResponse.json")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Test_Search
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testSearch() {

		List<MarriageDocumentType> marriageDocumentTypes = getMarriageDocumentTypesForSearch();

		/**
		 * @Response_Object_For_Search
		 */
		MarriageDocTypeResponse marriageDocTypeResponse = new MarriageDocTypeResponse();
		marriageDocTypeResponse.setMarriageDocTypes(marriageDocumentTypes);

		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setStatus(HttpStatus.OK.toString());
		marriageDocTypeResponse.setResponseInfo(responseInfo);

		when(marriageDocumentTypeService.search(Matchers.any(MarriageDocumentTypeSearchCriteria.class),
				Matchers.any(RequestInfo.class)))
						.thenReturn(new ResponseEntity(marriageDocTypeResponse, HttpStatus.OK));
		/**
		 * @Controller_Search
		 */
		try {
			mockMvc.perform(post("/marriageRegns/documents/_search")
					.content(getFileContents("marriageDocumentTypeSearchRequest.json"))
					.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().is(200))
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(content().json(getFileContents("marriageDocumentTypeSearchResponse.json")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * @UPDATE_Response
	 */
	private List<MarriageDocumentType> getMarriageDocumentTypesForUpdate() {
		List<MarriageDocumentType> mdts = new ArrayList<>();
		mdts.add(MarriageDocumentType.builder().id(Long.valueOf("1")).name("ADDRESSPROOF").tenantId("ap.kurnool")
				.applicationType(ApplicationType.REGISTRATION).isActive(true).isIndividual(true).isRequired(true)
				.code("00015").proof(DocumentProof.ADDRESS_PROOF).build());
		mdts.add(MarriageDocumentType.builder().id(Long.valueOf("2")).name("ADDRESSPROOF").tenantId("ap.kurnool")
				.applicationType(ApplicationType.REISSUE).isActive(true).isIndividual(true).isRequired(true)
				.code("00015").proof(DocumentProof.ADDRESS_PROOF).build());
		return mdts;
	}

	/**
	 * 
	 * @SEARCH_Response
	 */
	private List<MarriageDocumentType> getMarriageDocumentTypesForSearch() {
		List<MarriageDocumentType> mdts = new ArrayList<>();
		mdts.add(MarriageDocumentType.builder().id(Long.valueOf("1")).name("ADDRESSPROOF").tenantId("ap.kurnool")
				.applicationType(ApplicationType.REGISTRATION).isActive(true).isIndividual(true).isRequired(true)
				.code("00015").proof(DocumentProof.ADDRESS_PROOF).build());
		mdts.add(MarriageDocumentType.builder().id(Long.valueOf("2")).name("ADDRESSPROOF").tenantId("ap.kurnool")
				.applicationType(ApplicationType.REISSUE).isActive(true).isIndividual(false).isRequired(true)
				.code("00015").proof(DocumentProof.ADDRESS_PROOF).build());
		mdts.add(MarriageDocumentType.builder().id(Long.valueOf("3")).name("AGEPROOF").tenantId("ap.kurnool")
				.applicationType(ApplicationType.REGISTRATION).isActive(true).isIndividual(true).isRequired(false)
				.code("00015").proof(DocumentProof.AGE).build());
		mdts.add(MarriageDocumentType.builder().id(Long.valueOf("4")).name("AGEPROOF").tenantId("ap.kurnool")
				.applicationType(ApplicationType.REISSUE).isActive(false).isIndividual(false).isRequired(false)
				.code("00015").proof(DocumentProof.AGE).build());
		mdts.add(MarriageDocumentType.builder().id(Long.valueOf("5")).name("COMMONPROOF").tenantId("ap.kurnool")
				.applicationType(ApplicationType.REGISTRATION).isActive(false).isIndividual(true).isRequired(false)
				.code("00015").proof(DocumentProof.COMMON).build());
		mdts.add(MarriageDocumentType.builder().id(Long.valueOf("6")).name("COMMONPROOF").tenantId("ap.kurnool")
				.applicationType(ApplicationType.REISSUE).isActive(false).isIndividual(false).isRequired(true)
				.code("00015").proof(DocumentProof.COMMON).build());
		return mdts;
	}

	/**
	 * @CREATE_Response
	 */
	private List<MarriageDocumentType> getMarriageDocumentTypesForCreate() {
		List<MarriageDocumentType> mdts = new ArrayList<>();
		mdts.add(MarriageDocumentType.builder().id(Long.valueOf("1")).name("ADDRESSPROOF").tenantId("ap.kurnool")
				.applicationType(ApplicationType.REGISTRATION).isActive(true).isIndividual(true).isRequired(true)
				.code("00015").proof(DocumentProof.ADDRESS_PROOF).build());
		mdts.add(MarriageDocumentType.builder().id(Long.valueOf("2")).name("ADDRESSPROOF").tenantId("ap.kurnool")
				.applicationType(ApplicationType.REISSUE).isActive(true).isIndividual(true).isRequired(true)
				.code("00015").proof(DocumentProof.ADDRESS_PROOF).build());
		return mdts;
	}

	/**
	 * @Accesing values From JSON as DB
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private String getFileContents(String filePath) throws IOException {
		return new FileUtils().getFileContents("org/egov/mr/web/controller/" + filePath);
	}

	/**
	 * @Accessing the RegistrationUnitResponse Data from the JSON
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private MarriageDocTypeResponse getResponse(String filePath) throws IOException {
		String regnUnitJson = new FileUtils().getFileContents(filePath);
		return new ObjectMapper().readValue(regnUnitJson, MarriageDocTypeResponse.class);
	}
}
