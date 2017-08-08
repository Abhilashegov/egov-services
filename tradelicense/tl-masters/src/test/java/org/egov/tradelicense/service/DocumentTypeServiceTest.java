package org.egov.tradelicense.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.egov.enums.ApplicationTypeEnum;
import org.egov.models.AuditDetails;
import org.egov.models.DocumentType;
import org.egov.models.DocumentTypeRequest;
import org.egov.models.DocumentTypeResponse;
import org.egov.models.RequestInfo;
import org.egov.models.RequestInfoWrapper;
import org.egov.models.UserInfo;
import org.egov.tradelicense.TradeLicenseApplication;
import org.egov.tradelicense.config.PropertiesManager;
import org.egov.tradelicense.consumers.DocumentTypeConsumer;
import org.egov.tradelicense.domain.exception.DuplicateIdException;
import org.egov.tradelicense.domain.services.DocumentTypeService;
import org.egov.tradelicense.persistence.repository.DocumentTypeRepository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { TradeLicenseApplication.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext
@SuppressWarnings("rawtypes")
public class DocumentTypeServiceTest {

	@Autowired
	DocumentTypeService documentTypeService;

	@Autowired
	DocumentTypeRepository documentTypeRepository;

	@Autowired
	private PropertiesManager propertiesManager;
	
	@Autowired
	DocumentTypeConsumer documentTypeConsumer;
	
	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, "documenttype-create-validated",
			"documenttype-update-validated");

	public static Long DocumentTypeId = 1l;
	public String tenantId = "default";
	public String name = "shubham";
	public String ApplicationType = "NEW";
	public String updatedName = "nitin";
	public String updateApplicationType = "RENEW";
	public String enabled = "True";

	
	@Before
	public void setUp() throws Exception {
		// wait until the partitions are assigned
		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer, 0);
		}
	}
	
	/**
	 * Description : test method to test createDocumentType
	 */
	@Test
	public void testAcreateDocumentType() {
		RequestInfo requestInfo = getRequestInfoObject();

		List<DocumentType> documentTypes = new ArrayList<>();

		DocumentType documentType = new DocumentType();
		documentType.setTenantId(tenantId);
		documentType.setName(name);
		documentType.setApplicationType(ApplicationTypeEnum.fromValue(ApplicationType));
		long createdTime = new Date().getTime();

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("1");
		auditDetails.setLastModifiedBy("1");
		auditDetails.setCreatedTime(createdTime);
		auditDetails.setLastModifiedTime(createdTime);

		documentType.setAuditDetails(auditDetails);
		documentTypes.add(documentType);

		DocumentTypeRequest documentTypeRequest = new DocumentTypeRequest();
		documentTypeRequest.setDocumentTypes(documentTypes);
		documentTypeRequest.setRequestInfo(requestInfo);

		try {
			DocumentTypeResponse documentTypeResponse = documentTypeService.createDocumentTypeMaster(documentTypeRequest);
			if (documentTypeResponse.getDocumentTypes().size() == 0) {
				assertTrue(false);
			}
			DocumentTypeId = documentTypeRepository.createDocumentType(documentTypeResponse.getDocumentTypes().get(0));
			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	/**
	 * Description : test method to test searchDocumentType
	 */
	@Test
	public void testAsearchDocument() {

		Integer pageSize = Integer.valueOf(propertiesManager.getDefaultPageSize());
		Integer offset = Integer.valueOf(propertiesManager.getDefaultOffset());
		RequestInfo requestInfo = getRequestInfoObject();

		RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
		requestInfoWrapper.setRequestInfo(requestInfo);

		try {
			DocumentTypeResponse documentTypeResponse = documentTypeService.getDocumentTypeMaster(requestInfo, tenantId,
					new Integer[] { DocumentTypeId.intValue() }, name, enabled, ApplicationType, pageSize, offset);
			if (documentTypeResponse.getDocumentTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	/**
	 * Description : test method to test DocumentType for Duplicate
	 */
	@Test
	public void testAucreateDuplicateDocumentType() {
		RequestInfo requestInfo = getRequestInfoObject();

		List<DocumentType> documentTypes = new ArrayList<DocumentType>();

		DocumentType documentType = new DocumentType();
		documentType.setTenantId(tenantId);
		documentType.setName(name);
		documentType.setApplicationType(ApplicationTypeEnum.fromValue(ApplicationType));
		long createdTime = new Date().getTime();

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("1");
		auditDetails.setLastModifiedBy("1");
		auditDetails.setCreatedTime(createdTime);
		auditDetails.setLastModifiedTime(createdTime);

		documentType.setAuditDetails(auditDetails);
		documentTypes.add(documentType);

		DocumentTypeRequest documentTypeRequest = new DocumentTypeRequest();
		documentTypeRequest.setDocumentTypes(documentTypes);
		documentTypeRequest.setRequestInfo(requestInfo);

		try {
			DocumentTypeResponse documentTypeResponse = documentTypeService.createDocumentTypeMaster(documentTypeRequest);
			if (documentTypeResponse.getDocumentTypes().size() == 0) {
				assertTrue(false);
			}
			DocumentTypeId = documentTypeRepository.createDocumentType(documentTypeResponse.getDocumentTypes().get(0));
			assertTrue(true);

		} catch (Exception e) {
			if (e instanceof DuplicateIdException) {
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		}

	}

	/**
	 * Description : test method to update DocumentType name
	 */
	@Test
	public void testBmodifyDocumentuserName() {
		RequestInfo requestInfo = getRequestInfoObject();
		List<DocumentType> documentTypes = new ArrayList<>();

		DocumentType documentType = new DocumentType();
		documentType.setId(DocumentTypeId);
		documentType.setTenantId(tenantId);
		documentType.setName(updatedName);
		documentType.setApplicationType(ApplicationTypeEnum.fromValue(updateApplicationType));
		long createdTime = new Date().getTime();

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("1");
		auditDetails.setLastModifiedBy("1");
		auditDetails.setCreatedTime(createdTime);
		auditDetails.setLastModifiedTime(createdTime);

		documentType.setAuditDetails(auditDetails);
		documentTypes.add(documentType);

		DocumentTypeRequest documentTypeRequest = new DocumentTypeRequest();
		documentTypeRequest.setDocumentTypes(documentTypes);
		documentTypeRequest.setRequestInfo(requestInfo);

		try {
			DocumentTypeResponse documentTyperes = documentTypeService.updateDocumentTypeMaster(documentTypeRequest);

			if (documentTyperes.getDocumentTypes().size() == 0)
				assertTrue(false);
			
			documentTypeRepository.updateDocumentType(documentTypeRequest.getDocumentTypes().get(0));

			assertTrue(true);

		} catch (Exception e) {
			if (e.getClass().isInstance(new DuplicateIdException())) {
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		}

	}

	/**
	 * Description : test method to update DocumentType name
	 */
	@Test
	public void testBsearchUpdatedusername() {

		Integer pageSize = Integer.valueOf(propertiesManager.getDefaultPageSize());
		Integer offset = Integer.valueOf(propertiesManager.getDefaultOffset());
		RequestInfo requestInfo = getRequestInfoObject();

		RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
		requestInfoWrapper.setRequestInfo(requestInfo);

		try {
			DocumentTypeResponse documentTypeResponse = documentTypeService.getDocumentTypeMaster(requestInfo, tenantId,
					new Integer[] { DocumentTypeId.intValue() }, updatedName, enabled, updateApplicationType, pageSize,
					offset);
			if (documentTypeResponse.getDocumentTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	/**
	 * Description : test method to update DocumentType name
	 */
	@Test
	public void testCmodifydocumenttypeName() {
		RequestInfo requestInfo = getRequestInfoObject();
		List<DocumentType> documentTypes = new ArrayList<>();

		DocumentType documentType = new DocumentType();
		documentType.setId(DocumentTypeId);
		documentType.setTenantId(tenantId);
		documentType.setName(updatedName);
		documentType.setApplicationType(ApplicationTypeEnum.fromValue("NEW"));

		long createdTime = new Date().getTime();

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("1");
		auditDetails.setLastModifiedBy("1");
		auditDetails.setCreatedTime(createdTime);
		auditDetails.setLastModifiedTime(createdTime);

		documentType.setAuditDetails(auditDetails);
		documentTypes.add(documentType);

		DocumentTypeRequest documentTypeRequest = new DocumentTypeRequest();
		documentTypeRequest.setDocumentTypes(documentTypes);
		documentTypeRequest.setRequestInfo(requestInfo);

		try {
			DocumentTypeResponse categoryResponse = documentTypeService.updateDocumentTypeMaster(documentTypeRequest);

			if (categoryResponse.getDocumentTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	/**
	 * Description : test method to update DocumentType name for duplicate
	 */
	@Test
	public void testCmodifyDuplicateDocumentCode() {
		RequestInfo requestInfo = getRequestInfoObject();
		List<DocumentType> documentTypes = new ArrayList<>();

		DocumentType documentType = new DocumentType();
		documentType.setId(DocumentTypeId);
		documentType.setTenantId(tenantId);
		documentType.setName(updatedName);
		documentType.setApplicationType(ApplicationTypeEnum.fromValue(updateApplicationType));
		long createdTime = new Date().getTime();

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("1");
		auditDetails.setLastModifiedBy("1");
		auditDetails.setCreatedTime(createdTime);
		auditDetails.setLastModifiedTime(createdTime);

		documentType.setAuditDetails(auditDetails);
		documentTypes.add(documentType);

		DocumentTypeRequest documentTypeRequest = new DocumentTypeRequest();
		documentTypeRequest.setDocumentTypes(documentTypes);
		documentTypeRequest.setRequestInfo(requestInfo);

		try {
			DocumentTypeResponse documentTyperesponse = documentTypeService.updateDocumentTypeMaster(documentTypeRequest);

			if (documentTyperesponse.getDocumentTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			if (e.getClass().isInstance(new DuplicateIdException())) {
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		}

	}

	/**
	 * Description : test method to update DocumentType name
	 */

	@Test
	public void testCsearchUpdatedDocumentTypeNameCode() {

		Integer pageSize = Integer.valueOf(propertiesManager.getDefaultPageSize());
		Integer offset = Integer.valueOf(propertiesManager.getDefaultOffset());
		RequestInfo requestInfo = getRequestInfoObject();

		RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
		requestInfoWrapper.setRequestInfo(requestInfo);

		try {
			DocumentTypeResponse DocumentTypeResponse = documentTypeService.getDocumentTypeMaster(requestInfo, tenantId,
					new Integer[] { DocumentTypeId.intValue() }, updatedName, enabled, "RENEW", pageSize, offset);
			if (DocumentTypeResponse.getDocumentTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	private RequestInfo getRequestInfoObject() {
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setApiId("emp");
		requestInfo.setVer("1.0");
		requestInfo.setTs(new Long(122366));
		requestInfo.setDid("1");
		requestInfo.setKey("abcdkey");
		requestInfo.setMsgId("20170310130900");
		requestInfo.setRequesterId("rajesh");
		requestInfo.setAuthToken("b5da31a4-b400-4d6e-aa46-9ebf33cce933");
		UserInfo userInfo = new UserInfo();
		String username = "pavan";
		Integer userId = 1;
		userInfo.setUsername(username);
		userInfo.setId(userId);
		requestInfo.setUserInfo(userInfo);

		return requestInfo;
	}

}