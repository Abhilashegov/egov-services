package org.egov.mr.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.egov.mr.config.PropertiesManager;
import org.egov.mr.model.ApprovalDetails;
import org.egov.mr.model.AuditDetails;
import org.egov.mr.model.MarriageCertificate;
import org.egov.mr.model.MarriageDocument;
import org.egov.mr.model.Page;
import org.egov.mr.model.ReissueApplicantInfo;
import org.egov.mr.model.ReissueCertAppl;
import org.egov.mr.model.enums.ApplicationStatus;
import org.egov.mr.model.enums.CertificateType;
import org.egov.mr.repository.MarriageCertRepository;
import org.egov.mr.util.SequenceIdGenService;
import org.egov.mr.utils.FileUtils;
import org.egov.mr.web.contract.MarriageCertCriteria;
import org.egov.mr.web.contract.ReissueCertRequest;
import org.egov.mr.web.contract.ReissueCertResponse;
import org.egov.mr.web.contract.RequestInfo;
import org.egov.mr.web.contract.ResponseInfoFactory;
import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.kafka.support.SendResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class MarriageCertServiceTest {

	@Mock
	private MarriageCertRepository marriageCertRepository;

	@Mock
	private ResponseInfoFactory responseInfoFactory;

	@InjectMocks
	private MarriageCertService marriageCertService;

	@Mock
	private SequenceIdGenService sequenceGenUtil;
	
	@Mock
	private LogAwareKafkaTemplate<String, Object> kafkaTemplate;
	
	@Mock
	private PropertiesManager propertiesManager;
	
	@Test
	public void testForGetMarriageCerts() {

		ReissueCertResponse reissueCertResponse = null;
		try {

			reissueCertResponse = getMarriageCertResponse("org/egov/mr/service/ReissueCertListForSearch.json");
		} catch (Exception e) {
			e.printStackTrace();

		}

		MarriageCertCriteria marriageCertCriteria = MarriageCertCriteria.builder().tenantId("ap.kurnool").build();
		when(marriageCertRepository.findForCriteria(any(MarriageCertCriteria.class)))
				.thenReturn(getReissueCertApplFromDB().get(0).getReissueApplications());
		when(responseInfoFactory.createResponseInfoFromRequestInfo(Matchers.any(RequestInfo.class), Matchers.anyBoolean()))
				.thenReturn(reissueCertResponse.getResponseInfo());
		ReissueCertResponse actualReissueCertAppl = marriageCertService.getMarriageCerts(marriageCertCriteria,
				new RequestInfo());
		ReissueCertResponse expectedReissueCertAppl = reissueCertResponse;
		assertEquals(actualReissueCertAppl.toString(), expectedReissueCertAppl.toString());
	}
	
	@Test
	public void testForCreate(){
		ReissueCertResponse reissueCertResp=getReissueCertApplFromDB().get(0);
		
		ReissueCertRequest reissueRequest=ReissueCertRequest.builder().reissueApplication(reissueCertResp.getReissueApplications()
				.get(0)).requestInfo(new RequestInfo()).build();
		List<String> ids=new ArrayList<>();
		ids.add("1");
		when(sequenceGenUtil.getIds(Matchers.any(int.class), Matchers.any(String.class)))
		.thenReturn(ids);
		
		when(kafkaTemplate.send(Matchers.any(String.class), Matchers.any(String.class), Matchers.any(Object.class)))
		.thenReturn(new SendResult<>(null, null));
		
		assertTrue(reissueCertResp.equals(marriageCertService.createAsync(reissueRequest)));
		
	}

	private ReissueCertResponse getMarriageCertResponse(String filePath) throws IOException {
		String reissueCertJson = new FileUtils().getFileContents(filePath);
		return new ObjectMapper().readValue(reissueCertJson, ReissueCertResponse.class);
	}

	public List<ReissueCertResponse> getReissueCertApplFromDB() {

		List<Long> demandList = new ArrayList<>();
		demandList.add(1496430744825L);
		List<ReissueCertAppl> reissueCertApplList = new ArrayList<>();
		List<ReissueApplicantInfo> reissueApplicantInfoList = new ArrayList<>();
		AuditDetails auditDetails = AuditDetails.builder().createdBy("creator").createdTime(1496430744825L)
				.lastModifiedBy("modifier").lastModifiedTime(1496430744825L).build();
		MarriageCertificate marriageCertificate = MarriageCertificate.builder().applicationNumber("applnno")
				.bridePhoto("photo").bridegroomPhoto("photo").certificateDate(1496430744825L).certificateNo("certno")
				.certificatePlace("certplace").certificateType(CertificateType.REISSUE).husbandAddress("hus address")
				.husbandName("husname").wifeName("wife name").wifeAddress("wife address").marriageDate(1496430744825L)
				.marriageVenueAddress("venu address").regnDate(1496430744825L).regnNumber("regnnumber")
				.regnSerialNo("serialno").regnVolumeNo("volumeno").templateVersion("v2").tenantId("ap.kurnool").build();
		MarriageDocument marriageDocument = MarriageDocument.builder().location("location").id("1")
				.auditDetails(auditDetails).documentType("documenttype").reissueCertificateId("1234")
				.tenantId("ap.kurnool").build();
		List<MarriageDocument> documentsList = new ArrayList<>();
		documentsList.add(marriageDocument);
		ReissueApplicantInfo reissueApplicantInfo = ReissueApplicantInfo.builder().aadhaar("123456789")
				.address("address12345").email("emailid@gmail.com").fee(new BigDecimal(34)).mobileNo("9876543210")
				.name("name").build();
		reissueApplicantInfoList.add(reissueApplicantInfo);
		ApprovalDetails approvalDetails = ApprovalDetails.builder().action("action").assignee(1496430744825L)
				.comments("comments").department(1496430744825L).designation(1496430744825L).status("Approved").build();
		ReissueCertAppl reissueCertAppl = ReissueCertAppl.builder().id("1").applicationNumber("egov1234")
				.auditDetails(auditDetails).certificate(marriageCertificate).documents(documentsList).regnNo("regnno")
				.reissueApplStatus(ApplicationStatus.CREATED).rejectionReason("rejectionreaon").remarks("remarks")
				.stateId("12345").tenantId("ap.kurnool").demands(demandList).isActive(null)
				.approvalDetails(approvalDetails).applicantInfo(reissueApplicantInfo).build();

		reissueCertApplList.add(reissueCertAppl);
		Page page = null;/*Page.builder().totalResults(1).currentPage(null).pageSize(null).totalPages(null).offSet(null)
				.build();*/
		ReissueCertResponse reissueCertResponse = ReissueCertResponse.builder().reissueApplications(reissueCertApplList)
				.page(page).build();
		List<ReissueCertResponse> responselist = new ArrayList<>();
		responselist.add(reissueCertResponse);
		return responselist;

	}

}
