package org.egov.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.egov.enums.ApplicationEnum;
import org.egov.enums.ChannelEnum;
import org.egov.enums.CreationReasonEnum;
import org.egov.enums.SourceEnum;
import org.egov.enums.StatusEnum;
import org.egov.enums.UnitTypeEnum;
import org.egov.models.Address;
import org.egov.models.AuditDetails;
import org.egov.models.Boundary;
import org.egov.models.Department;
import org.egov.models.DepartmentRequest;
import org.egov.models.DepartmentResponseInfo;
import org.egov.models.Depreciation;
import org.egov.models.DepreciationRequest;
import org.egov.models.DepreciationResponse;
import org.egov.models.Document;
import org.egov.models.DocumentType;
import org.egov.models.Floor;
import org.egov.models.FloorType;
import org.egov.models.FloorTypeRequest;
import org.egov.models.FloorTypeResponse;
import org.egov.models.MutationMaster;
import org.egov.models.MutationMasterRequest;
import org.egov.models.MutationMasterResponse;
import org.egov.models.OccuapancyMaster;
import org.egov.models.OccuapancyMasterRequest;
import org.egov.models.OccuapancyMasterResponse;
import org.egov.models.Property;
import org.egov.models.PropertyDetail;
import org.egov.models.PropertyLocation;
import org.egov.models.PropertyRequest;
import org.egov.models.PropertyType;
import org.egov.models.PropertyTypeRequest;
import org.egov.models.PropertyTypeResponse;
import org.egov.models.RequestInfo;
import org.egov.models.RequestInfoWrapper;
import org.egov.models.Role;
import org.egov.models.RoofType;
import org.egov.models.RoofTypeRequest;
import org.egov.models.RoofTypeResponse;
import org.egov.models.Unit;
import org.egov.models.UsageMaster;
import org.egov.models.UsageMasterRequest;
import org.egov.models.UsageMasterResponse;
import org.egov.models.User;
import org.egov.models.UserDetails;
import org.egov.models.VacantLandDetail;
import org.egov.models.WallType;
import org.egov.models.WallTypeRequest;
import org.egov.models.WallTypeResponse;
import org.egov.models.WoodType;
import org.egov.models.WoodTypeRequest;
import org.egov.models.WoodTypeResponse;
import org.egov.models.WorkFlowDetails;
import org.egov.property.PtPropertyApplication;
import org.egov.property.consumer.PropertyProducer;
import org.egov.property.services.Masterservice;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { PtPropertyApplication.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("static-access")
public class PropertyServiceTest {

	@Autowired
	Masterservice masterService;

	@Autowired
	Environment environment;

	@Autowired
	PropertyProducer producer;

	public Long floorId = 1l;
	public Long roofId = 1l;
	public Long woodId = 1l;
	public Long departmentId = 1l;
	public Long occupancyId = 1l;
	public Integer structureId = 1;
	public Long usageId = 1l;
	public Integer wallTypeId = 1;
	public Long propertyTypeId = 1l;

	@Test
	public void createRoofType() {
		String tenantId = "123";
		RequestInfo requestInfo = getRequestInfoObject();

		List<RoofType> roofTypes = new ArrayList<>();

		RoofType roofType = new RoofType();
		roofType.setTenantId("1234");
		roofType.setName("Gambrel Roof");
		roofType.setCode("256");
		roofType.setNameLocal("Gambrel");
		roofType.setDescription("Imported from Kurnool");
		long createdTime = new Date().getTime();

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("prasad");
		auditDetails.setLastModifiedBy("prasad");
		auditDetails.setCreatedTime(createdTime);
		auditDetails.setLastModifiedTime(createdTime);

		roofType.setAuditDetails(auditDetails);
		roofTypes.add(roofType);

		RoofTypeRequest roofTypeRequest = new RoofTypeRequest();
		roofTypeRequest.setRoofTypes(roofTypes);
		roofTypeRequest.setRequestInfo(requestInfo);

		try {
			RoofTypeResponse roofTypeResponse = masterService.createRoofype(roofTypeRequest, tenantId);
			if (roofTypeResponse.getRoofTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void modifyRoofType() {
		RequestInfo requestInfo = getRequestInfoObject();
		List<RoofType> roofTypes = new ArrayList<>();

		RoofType roofType = new RoofType();
		roofType.setId(roofId);
		roofType.setTenantId("1234");
		roofType.setName("Mansard  Roof");
		roofType.setCode("256");
		roofType.setNameLocal("Mansard");
		roofType.setDescription("Imported from Kurnool");
		long createdTime = new Date().getTime();

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("prasad");
		auditDetails.setLastModifiedBy("prasad");
		auditDetails.setCreatedTime(createdTime);
		auditDetails.setLastModifiedTime(createdTime);

		roofType.setAuditDetails(auditDetails);
		roofTypes.add(roofType);

		RoofTypeRequest roofTypeRequest = new RoofTypeRequest();
		roofTypeRequest.setRoofTypes(roofTypes);
		roofTypeRequest.setRequestInfo(requestInfo);

		try {
			RoofTypeResponse roofTypeResponse = masterService.updateRoofType(roofTypeRequest);

			if (roofTypeResponse.getRoofTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void searchRoofType() {

		String tenantId = "1234";
		String name = "Mansard  Roof";
		String code = "256";
		String nameLocal = "Mansard";
		Integer pageSize = Integer.valueOf(environment.getProperty("default.page.size").trim());
		Integer offset = Integer.valueOf(environment.getProperty("default.offset"));
		RequestInfo requestInfo = getRequestInfoObject();

		RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
		requestInfoWrapper.setRequestInfo(requestInfo);

		try {
			RoofTypeResponse roofTypeResponse = masterService.getRoofypes(requestInfo, tenantId,
					new Integer[] { roofId.intValue() }, name, code, nameLocal, pageSize, offset);
			if (roofTypeResponse.getRoofTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void createWoodType() {

		String tenantId = "1234";
		RequestInfo requestInfo = getRequestInfoObject();
		List<WoodType> woodTypes = new ArrayList<>();
		WoodType woodType = new WoodType();
		woodType.setTenantId("1234");
		woodType.setName("Walnut Wood Type");
		woodType.setCode("256");
		woodType.setNameLocal("Walnut");
		woodType.setDescription("Imported from Kurnool");
		long createdTime = new Date().getTime();

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("prasad");
		auditDetails.setLastModifiedBy("prasad");
		auditDetails.setCreatedTime(createdTime);
		auditDetails.setLastModifiedTime(createdTime);

		woodType.setAuditDetails(auditDetails);
		woodTypes.add(woodType);

		WoodTypeRequest woodTypeRequest = new WoodTypeRequest();
		woodTypeRequest.setWoodTypes(woodTypes);
		woodTypeRequest.setRequestInfo(requestInfo);

		try {
			WoodTypeResponse woodTypeResponse = masterService.createWoodType(woodTypeRequest, tenantId);

			if (woodTypeResponse.getWoodTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void modifyWoodType() {

		RequestInfo requestInfo = getRequestInfoObject();

		List<WoodType> woodTypes = new ArrayList<>();

		WoodType woodType = new WoodType();
		woodType.setId(woodId);
		woodType.setTenantId("1234");
		woodType.setName("Maple Wood Type");
		woodType.setCode("256");
		woodType.setNameLocal("Maple");
		woodType.setDescription("Imported from Kurnool");
		long createdTime = new Date().getTime();

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("prasad");
		auditDetails.setLastModifiedBy("prasad");
		auditDetails.setCreatedTime(createdTime);
		auditDetails.setLastModifiedTime(createdTime);

		woodType.setAuditDetails(auditDetails);
		woodTypes.add(woodType);

		WoodTypeRequest woodTypeRequest = new WoodTypeRequest();
		woodTypeRequest.setWoodTypes(woodTypes);
		woodTypeRequest.setRequestInfo(requestInfo);

		try {
			WoodTypeResponse woodTypeResponse = masterService.updateWoodType(woodTypeRequest);

			if (woodTypeResponse.getWoodTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void searchWoodType() {

		String tenantId = "1234";
		String name = "Maple Wood Type";
		String code = "256";
		String nameLocal = "Maple";
		Integer pageSize = Integer.valueOf(environment.getProperty("default.page.size").trim());
		Integer offset = Integer.valueOf(environment.getProperty("default.offset"));
		RequestInfo requestInfo = getRequestInfoObject();
		RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
		requestInfoWrapper.setRequestInfo(requestInfo);
		try {
			WoodTypeResponse woodTypeResponse = masterService.getWoodTypes(requestInfo, tenantId,
					new Integer[] { woodId.intValue() }, name, code, nameLocal, pageSize, offset);

			if (woodTypeResponse.getWoodTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void createFloorTypeTest() {

		String tenantId = "123";
		RequestInfo requestInfo = getRequestInfoObject();
		List<FloorType> floorTypes = new ArrayList<>();

		FloorType floorType = new FloorType();
		floorType.setTenantId("1234");
		floorType.setName("Stone Flooring");
		floorType.setCode("256");
		floorType.setNameLocal("Stone");
		floorType.setDescription("Imported from Kurnool");
		long createdTime = new Date().getTime();

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("prasad");
		auditDetails.setLastModifiedBy("prasad");
		auditDetails.setCreatedTime(createdTime);
		auditDetails.setLastModifiedTime(createdTime);

		floorType.setAuditDetails(auditDetails);
		floorTypes.add(floorType);

		FloorTypeRequest floorTypeRequest = new FloorTypeRequest();
		floorTypeRequest.setFloorTypes(floorTypes);
		floorTypeRequest.setRequestInfo(requestInfo);

		try {
			FloorTypeResponse floorTypeResponse = masterService.createFloorType(floorTypeRequest, tenantId);

			if (floorTypeResponse.getFloorTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);

		}

	}

	@Test
	public void modifyFloorType() {
		RequestInfo requestInfo = getRequestInfoObject();
		List<FloorType> floorTypes = new ArrayList<>();

		FloorType floorType = new FloorType();
		floorType.setId(floorId);
		floorType.setTenantId("1234");
		floorType.setName("Tile Flooring");
		floorType.setCode("256");
		floorType.setNameLocal("Tile");
		floorType.setDescription("Imported from Kurnool");

		long createdTime = new Date().getTime();

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("prasad");
		auditDetails.setLastModifiedBy("prasad");
		auditDetails.setCreatedTime(createdTime);
		auditDetails.setLastModifiedTime(createdTime);

		floorType.setAuditDetails(auditDetails);
		floorTypes.add(floorType);

		FloorTypeRequest floorTypeRequest = new FloorTypeRequest();
		floorTypeRequest.setFloorTypes(floorTypes);
		floorTypeRequest.setRequestInfo(requestInfo);

		FloorTypeResponse floorTypeResponse = null;

		try {
			floorTypeResponse = masterService.updateFloorType(floorTypeRequest);
			if (floorTypeResponse.getFloorTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void searchFloorType() {

		String tenantId = "1234";
		String name = "Tile Flooring";
		String code = "256";
		String nameLocal = "Tile";
		Integer pageSize = Integer.valueOf(environment.getProperty("default.page.size").trim());
		Integer offset = Integer.valueOf(environment.getProperty("default.offset"));

		RequestInfo requestInfo = getRequestInfoObject();

		RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
		requestInfoWrapper.setRequestInfo(requestInfo);

		try {
			FloorTypeResponse floorTypeResponse = masterService.getFloorTypeMaster(requestInfo, tenantId,
					new Integer[] { floorId.intValue() }, name, code, nameLocal, pageSize, offset);

			if (floorTypeResponse.getFloorTypes().size() == 0)
				assertTrue(false);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}

	}

	/**
	 * Description : Test case for creating Occuapancy master
	 * 
	 * @param tenantId
	 * @param occuapancyRequest
	 * @throws Exception
	 */

	@Test
	public void createOccuapancyMaster() throws Exception {
		try {

			String tenantId = "default";

			List<OccuapancyMaster> occuapancyMaster = new ArrayList<OccuapancyMaster>();

			OccuapancyMaster master = new OccuapancyMaster();
			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setCreatedBy("anil");
			auditDetails.setCreatedTime((long) 123456);
			auditDetails.setLastModifiedBy("anil");
			auditDetails.setLastModifiedTime((long) 123456);

			master.setTenantId("default");
			master.setName("Anil");
			master.setCode("testingcode");
			master.setNameLocal("kumar");
			master.setDescription("test for occuapancy");
			master.setActive(true);
			master.setOrderNumber(1);
			master.setAuditDetails(auditDetails);

			occuapancyMaster.add(master);

			OccuapancyMasterRequest occuapancyMasterRequest = new OccuapancyMasterRequest();
			occuapancyMasterRequest.setRequestInfo(getRequestInfoObject());
			occuapancyMasterRequest.setOccuapancyMasters(occuapancyMaster);

			OccuapancyMasterResponse occuapancyMasterResponse = masterService.createOccuapancyMaster(tenantId,
					occuapancyMasterRequest);
			if (occuapancyMasterResponse.getOccuapancyMasters().size() == 0) {
				assertTrue(false);
			}
			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Description : Test case for updating occupancyType master api
	 * 
	 * @param tenantId
	 * @param id
	 * @param occuapancyRequest
	 * @throws Exception
	 */

	@Test
	public void modifyOccuapancyMaster() throws Exception {
		try {

			List<OccuapancyMaster> occuapancyMaster = new ArrayList<OccuapancyMaster>();

			OccuapancyMaster master = new OccuapancyMaster();
			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setCreatedBy("anil");
			auditDetails.setCreatedTime((long) 123456);
			auditDetails.setLastModifiedBy("anil");
			auditDetails.setLastModifiedTime((long) 123456);

			// master.setId((long) 4);
			master.setId(occupancyId);
			master.setTenantId("default");
			master.setName("anil");
			master.setCode("testcode");
			master.setNameLocal("kumar");
			master.setDescription("test for occuapancy");
			master.setActive(true);
			master.setOrderNumber(1);

			master.setAuditDetails(auditDetails);

			occuapancyMaster.add(master);
			OccuapancyMasterRequest occuapancyMasterRequest = new OccuapancyMasterRequest();
			occuapancyMasterRequest.setRequestInfo(getRequestInfoObject());
			occuapancyMasterRequest.setOccuapancyMasters(occuapancyMaster);

			OccuapancyMasterResponse occuapancyMasterResponse = masterService
					.updateOccuapancyMaster(occuapancyMasterRequest);
			if (occuapancyMasterResponse.getOccuapancyMasters().size() == 0) {
				assertTrue(false);
			}
			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * * Description : test case for searching occupancyType master api
	 * 
	 * @param requestInfo
	 * @param tenantId
	 * @param ids
	 * @param name
	 * @param code
	 * @param nameLocal
	 * @param active
	 * @param orderNumber
	 * @param pageSize
	 * @param offSet
	 * @throws Exception
	 */
	@Test
	public void searchOccuapancyMaster() throws Exception {
		try {

			String tenantId = "default";
			String name = "anil";
			String code = "testcode";
			String nameLocal = "kumar";
			Boolean active = true;
			Integer orderNumber = 1;
			Integer pageSize = Integer.valueOf(environment.getProperty("default.page.size").trim());
			Integer offset = Integer.valueOf(environment.getProperty("default.offset"));

			OccuapancyMasterResponse occuapancyMasterResponse = masterService.getOccuapancyMaster(
					getRequestInfoObject(), tenantId, new Integer[] { Integer.valueOf(occupancyId.toString()) }, name,
					code, nameLocal, active, orderNumber, pageSize, offset);
			if (occuapancyMasterResponse.getOccuapancyMasters().size() == 0) {
				assertTrue(false);
			}
			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Description : Test case for creating propertytypes master
	 * 
	 * @param tenantId
	 * @param propertyTypeRequest
	 * @throws Exception
	 */

	@Test
	public void createPropertyTypeMaster() throws Exception {
		try {
			String tenantId = "default";

			List<PropertyType> propertyType = new ArrayList<PropertyType>();
			PropertyType master = new PropertyType();
			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setCreatedBy("Anilkumar");
			auditDetails.setLastModifiedBy("Anilkumar");
			auditDetails.setCreatedTime((long) 564644560);
			auditDetails.setLastModifiedTime((long) 564644560);

			master.setTenantId("default");
			master.setName("Anilkumar");
			master.setCode("testcode");
			master.setNameLocal("kumar");
			master.setDescription("test for occuapancy");
			master.setActive(true);
			master.setOrderNumber(1);
			master.setAuditDetails(auditDetails);
			propertyType.add(master);

			PropertyTypeRequest propertyTypeRequest = new PropertyTypeRequest();
			propertyTypeRequest.setRequestInfo(getRequestInfoObject());
			propertyTypeRequest.setPropertyTypes(propertyType);

			PropertyTypeResponse propertyTypeResponse = masterService.createPropertyTypeMaster(tenantId,
					propertyTypeRequest);
			if (propertyTypeResponse.getPropertyTypes().size() == 0) {
				assertTrue(false);
			}
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Description : Test case for updating propertytype master api
	 * 
	 * @param tenantId
	 * @param id
	 * @param propertyTypeRequest
	 * @throws Exception
	 */

	@Test
	public void modifyPropertyTypeMaster() throws Exception {
		try {

			List<PropertyType> propertyType = new ArrayList<PropertyType>();
			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setCreatedBy("Anil");
			auditDetails.setLastModifiedBy("Anil");
			auditDetails.setCreatedTime((long) 564644560);
			auditDetails.setLastModifiedTime((long) 564644560);

			PropertyType master = new PropertyType();
			master.setId(propertyTypeId);
			master.setTenantId("default");
			master.setName("anil");
			master.setCode("testingcode");
			master.setNameLocal("kumar");
			master.setDescription("test for occuapancy");
			master.setActive(true);
			master.setOrderNumber(1);
			master.setAuditDetails(auditDetails);
			propertyType.add(master);

			PropertyTypeRequest propertyTypeRequest = new PropertyTypeRequest();
			propertyTypeRequest.setRequestInfo(getRequestInfoObject());
			propertyTypeRequest.setPropertyTypes(propertyType);

			PropertyTypeResponse propertyTypeResponse = masterService.updatePropertyTypeMaster(propertyTypeRequest);
			if (propertyTypeResponse.getPropertyTypes().size() == 0) {
				assertTrue(false);
			}
			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * * Description : test case for searching propertytype master api
	 * 
	 * @param requestInfo
	 * @param tenantId
	 * @param ids
	 * @param name
	 * @param code
	 * @param nameLocal
	 * @param active
	 * @param orderNumber
	 * @param pageSize
	 * @param offSet
	 * @throws Exception
	 */
	@Test
	public void searchPropertyTypeMaster() throws Exception {
		try {

			String tenantId = "default";
			Integer[] ids = new Integer[] { Integer.valueOf(propertyTypeId.toString()) };
			String name = "anil";
			String code = "testingcode";
			String nameLocal = "kumar";
			Boolean active = true;
			Integer orderNumber = 1;
			Integer pageSize = Integer.valueOf(environment.getProperty("default.page.size").trim());
			Integer offset = Integer.valueOf(environment.getProperty("default.offset"));

			PropertyTypeResponse propertyTypeResponse = masterService.getPropertyTypeMaster(getRequestInfoObject(),
					tenantId, ids, name, code, nameLocal, active, orderNumber, pageSize, offset);
			if (propertyTypeResponse.getPropertyTypes().size() == 0) {
				assertTrue(false);
			}
			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Description : Test case for creating department master
	 * 
	 * @param tenantId
	 * @param departmentRequest
	 * @throws Exception
	 */

	@Test
	public void createDepartmentMaster() throws Exception {
		try {

			String tenantId = "default";

			List<Department> department = new ArrayList<Department>();

			Department master = new Department();

			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setCreatedBy("Anil");
			auditDetails.setLastModifiedBy("Anil");
			auditDetails.setCreatedTime((long) 564644560);
			auditDetails.setLastModifiedTime((long) 564644560);
			master.setTenantId("default");
			master.setCategory("software");
			master.setName("anil");
			master.setCode("testcode");
			master.setNameLocal("kumar");
			master.setDescription("test for occuapancy");
			master.setAuditDetails(auditDetails);
			department.add(master);

			DepartmentRequest departmentRequest = new DepartmentRequest();
			departmentRequest.setRequestInfo(getRequestInfoObject());
			departmentRequest.setDepartments(department);
			DepartmentResponseInfo departmentResponse = masterService.createDepartmentMaster(tenantId,
					departmentRequest);
			if (departmentResponse.getDepartments().size() == 0) {
				assertTrue(false);
			}
			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Description : Test case for updating department master api
	 * 
	 * @param tenantId
	 * @param id
	 * @param departmentRequest
	 * @throws Exception
	 */

	@Test
	public void modifyDepartmentMaster() throws Exception {
		try {

			List<Department> department = new ArrayList<Department>();
			Department master = new Department();
			AuditDetails auditDetails = new AuditDetails();

			auditDetails.setCreatedBy("Anil");
			auditDetails.setLastModifiedBy("Anil");
			auditDetails.setCreatedTime((long) 564644560);
			auditDetails.setLastModifiedTime((long) 564644560);

			master.setId(departmentId);
			master.setTenantId("default");
			master.setCategory("software engineer");
			master.setName("anil");
			master.setCode("testcode");
			master.setNameLocal("kumar");
			master.setDescription("test for occuapancy");
			master.setAuditDetails(auditDetails);
			department.add(master);
			DepartmentRequest departmentRequest = new DepartmentRequest();
			departmentRequest.setRequestInfo(getRequestInfoObject());
			departmentRequest.setDepartments(department);

			DepartmentResponseInfo departmentResponse = masterService.updateDepartmentMaster(departmentRequest);
			if (departmentResponse.getDepartments().size() == 0) {
				assertTrue(false);
			}
			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * * Description : test case for searching department master api
	 * 
	 * @param requestInfo
	 * @param tenantId
	 * @param ids
	 * @param category
	 * @param name
	 * @param code
	 * @param nameLocal
	 * @param pageSize
	 * @param offSet
	 * @throws Exception
	 */
	@Test
	public void searchDepartmentMaster() throws Exception {
		try {

			String tenantId = "default";
			Integer[] ids = new Integer[] { Integer.valueOf(departmentId.toString()) };
			String category = "software engineer";
			String name = "anil";
			String code = "testcode";
			String nameLocal = "kumar";
			Integer pageSize = Integer.valueOf(environment.getProperty("default.page.size").trim());
			Integer offset = Integer.valueOf(environment.getProperty("default.offset"));

			DepartmentResponseInfo departmentResponse = masterService.getDepartmentMaster(getRequestInfoObject(),
					tenantId, ids, category, name, code, nameLocal, pageSize, offset);
			if (departmentResponse.getDepartments().size() == 0) {
				assertTrue(false);
			}
			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void createUsageMasterTest() {

		try {
			String tenantId = "default";

			RequestInfo requestInfo = new RequestInfo();
			requestInfo.setApiId("emp");
			requestInfo.setVer("1.0");
			requestInfo.setTs(new Long(122366));
			requestInfo.setDid("1");
			requestInfo.setKey("yyyykey");
			requestInfo.setMsgId("20170310130900");
			requestInfo.setRequesterId("yosadhara");
			requestInfo.setAuthToken("b5da31a4-b400-4d6e-aa46-9ebf33cce933");

			List<UsageMaster> usageMasters = new ArrayList<>();

			UsageMaster usageMaster = new UsageMaster();
			usageMaster.setTenantId("default");
			usageMaster.setName("Yoyo");
			usageMaster.setCode("1234");
			usageMaster.setNameLocal("test_namelocal");
			usageMaster.setDescription("test_description");
			usageMaster.setParent(new Long(1));

			long createdTime = new Date().getTime();

			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setCreatedBy("yosadhara");
			auditDetails.setLastModifiedBy("yosadhara");
			auditDetails.setCreatedTime(createdTime);
			auditDetails.setLastModifiedTime(createdTime);

			usageMaster.setAuditDetails(auditDetails);
			usageMasters.add(usageMaster);

			UsageMasterRequest usageMasterRequest = new UsageMasterRequest();
			usageMasterRequest.setUsageMasters(usageMasters);
			usageMasterRequest.setRequestInfo(requestInfo);

			UsageMasterResponse response = masterService.createUsageMaster(tenantId, usageMasterRequest);

			if (response.getUsageMasters().size() == 0)
				assertTrue(false);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void modifyUsageMasterTest() throws Exception {
		try {
			RequestInfo requestInfo = new RequestInfo();
			requestInfo.setApiId("emp");
			requestInfo.setVer("1.0");
			requestInfo.setTs(new Long(122366));
			requestInfo.setDid("1");
			requestInfo.setKey("abcdkey");
			requestInfo.setMsgId("20170310130900");
			requestInfo.setRequesterId("yosadhara");
			requestInfo.setAuthToken("b5da31a4-b400-4d6e-aa46-9ebf33cce933");

			List<UsageMaster> usageMasters = new ArrayList<>();

			UsageMaster usageMaster = new UsageMaster();
			usageMaster.setId(usageId);
			usageMaster.setTenantId("default");
			usageMaster.setName("Yoyo");
			usageMaster.setCode("1234");
			usageMaster.setNameLocal("update_namelocal");
			usageMaster.setDescription("update_description");
			usageMaster.setParent(new Long(1));

			long createdTime = new Date().getTime();

			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setCreatedBy("yosadhara");
			auditDetails.setLastModifiedBy("yosadhara");
			auditDetails.setCreatedTime(createdTime);
			auditDetails.setLastModifiedTime(createdTime);

			usageMaster.setAuditDetails(auditDetails);
			usageMasters.add(usageMaster);

			UsageMasterRequest usageMasterRequest = new UsageMasterRequest();
			usageMasterRequest.setUsageMasters(usageMasters);
			usageMasterRequest.setRequestInfo(requestInfo);

			UsageMasterResponse usageMasterResponse = masterService.updateUsageMaster(usageMasterRequest);

			if (usageMasterResponse.getUsageMasters().size() == 0)
				assertTrue(false);
			assertTrue(true);
		} catch (Exception ex) {
			assert (false);
		}
	}

	@Test
	public void createWallTypeTest() {
		try {
			String tenantId = "default";
			RequestInfo requestInfo = new RequestInfo();
			requestInfo.setApiId("emp");
			requestInfo.setVer("1.0");
			requestInfo.setTs(new Long(122366));
			requestInfo.setDid("1");
			requestInfo.setKey("yyyykey");
			requestInfo.setMsgId("20170310130900");
			requestInfo.setRequesterId("yosadhara");
			requestInfo.setAuthToken("b5da31a4-b400-4d6e-aa46-9ebf33cce933");
			List<WallType> wallTypes = new ArrayList<>();
			WallType wallType = new WallType();
			wallType.setTenantId("default");
			wallType.setName("Yoyo");
			wallType.setCode("1234");
			wallType.setNameLocal("test_namelocal");
			wallType.setDescription("test_description");
			long createdTime = new Date().getTime();
			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setCreatedBy("yosadhara");
			auditDetails.setLastModifiedBy("yosadhara");
			auditDetails.setCreatedTime(createdTime);
			auditDetails.setLastModifiedTime(createdTime);
			wallType.setAuditDetails(auditDetails);
			wallTypes.add(wallType);
			WallTypeRequest wallTypeRequest = new WallTypeRequest();
			wallTypeRequest.setWallTypes(wallTypes);
			wallTypeRequest.setRequestInfo(requestInfo);

			WallTypeResponse wallTypeResponse;

			wallTypeResponse = masterService.createWallTypeMaster(tenantId, wallTypeRequest);

			if (wallTypeResponse.getWallTypes().size() == 0)
				assertTrue(false);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void modifyWallTypeTest() {

		try {
			long id = 1;
			RequestInfo requestInfo = new RequestInfo();
			requestInfo.setApiId("emp");
			requestInfo.setVer("1.0");
			requestInfo.setTs(new Long(122366));
			requestInfo.setDid("1");
			requestInfo.setKey("abcdkey");
			requestInfo.setMsgId("20170310130900");
			requestInfo.setRequesterId("rajesh");
			requestInfo.setAuthToken("b5da31a4-b400-4d6e-aa46-9ebf33cce933");

			List<WallType> wallTypes = new ArrayList<>();

			WallType wallType = new WallType();
			wallType.setId(id);
			wallType.setTenantId("default");
			wallType.setName("Yoyo");
			wallType.setCode("1234");
			wallType.setNameLocal("test_namelocal");
			wallType.setDescription("test_description");

			long createdTime = new Date().getTime();

			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setCreatedBy("yosadhara");
			auditDetails.setLastModifiedBy("yosadhara");
			auditDetails.setCreatedTime(createdTime);
			auditDetails.setLastModifiedTime(createdTime);

			wallType.setAuditDetails(auditDetails);
			wallTypes.add(wallType);

			WallTypeRequest wallTypeRequest = new WallTypeRequest();
			wallTypeRequest.setWallTypes(wallTypes);
			wallTypeRequest.setRequestInfo(requestInfo);
			WallTypeResponse wallTypeResponse = masterService.updateWallTypeMaster(wallTypeRequest);
			if (wallTypeResponse.getWallTypes().size() == 0)
				assertTrue(false);
			assertTrue(true);

		} catch (Exception e) {
			assertTrue(false);
		}

	}

	@Test
	public void searchWallTypeTest() {
		try {
			String tenantId = "default";
			Integer[] ids = new Integer[] { 1 };
			String name = "Yoyo";
			String code = "1234";
			String nameLocal = "test_namelocal";
			Integer pageSize = Integer.valueOf(environment.getProperty("default.page.size").trim());
			Integer offset = Integer.valueOf(environment.getProperty("default.offset"));
			RequestInfo requestInfo = new RequestInfo();
			requestInfo.setApiId("emp");
			requestInfo.setVer("1.0");
			requestInfo.setTs(new Long(122366));
			requestInfo.setDid("1");
			requestInfo.setKey("abcdkey");
			requestInfo.setMsgId("20170310130900");
			requestInfo.setRequesterId("yosadhara");
			requestInfo.setAuthToken("b5da31a4-b400-4d6e-aa46-9ebf33cce933");

			RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
			requestInfoWrapper.setRequestInfo(requestInfo);

			WallTypeResponse wallTypeResponse = masterService.getWallTypeMaster(requestInfo, tenantId, ids, name, code,
					nameLocal, pageSize, offset);
			if (wallTypeResponse.getWallTypes().size() == 0)
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

		return requestInfo;
	}

	@Test
	public void testCreatingProperty() throws Exception {
		try {
			PropertyRequest propertyRequest = new PropertyRequest();
			List<Property> properties = new ArrayList<Property>();

			Property property = new Property();

			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setCreatedBy("Anil");
			auditDetails.setLastModifiedBy("Anil");
			auditDetails.setCreatedTime((long) 564644560);
			auditDetails.setLastModifiedTime((long) 564644560);

			RequestInfo requestInfo = new RequestInfo();
			requestInfo.setApiId("emp");
			requestInfo.setVer("1.0");
			requestInfo.setTs((long) 20171205);
			requestInfo.setAction("create");
			requestInfo.setDid("1");
			requestInfo.setKey("abcdkey");
			requestInfo.setMsgId("20170310130900");
			requestInfo.setRequesterId("rajesh");
			requestInfo.setAuthToken("08db73a8-945e-4164-94e3-63ccef7856d4");

			property.setTenantId("default");
			property.setUpicNumber("test123");
			property.setVltUpicNumber("vltUpicNumber1");
			property.setCreationReason(CreationReasonEnum.NEWPROPERTY.valueOf("NEWPROPERTY"));

			Address address = new Address();
			address.setTenantId("default");
			address.setLatitude((double) 11);
			address.setLongitude((double) 20);
			address.setAddressNumber("1-2-3");
			address.setAddressLine1("acgaurds");
			address.setAddressLine2("khirathabad");
			address.setLandmark("noori");
			address.setCity("hyderabad");
			address.setPincode("500004");
			address.setDetail("Testing");

			address.setAuditDetails(auditDetails);

			property.setAddress(address);

			List<User> owners = new ArrayList<User>();
			User owner = new User();
			owner.setTenantId("default");
			owner.setUserName("Anil");
			owner.setAuthToken("08db73a8-945e-4164-94e3-63ccef7856d4");
			owner.setSalutation("testing");
			owner.setName("anil");
			owner.setGender("male");
			owner.setMobileNumber("9333555666");
			owner.setEmailId("anil@wtc.com");
			owner.setAadhaarNumber("123456789123");
			owner.setActive(true);
			owner.setLocale("no");
			owner.setType("house");
			owner.setAccountLocked(true);
			property.getOwners().add(owner);
			List<Role> roles = new ArrayList<Role>();

			Role role = new Role();

			role.setName("kumar");
			role.setDescription("Testing");
			owner.getRoles().add(role);
			owner.setRoles(roles);

			owner.setAuditDetails(auditDetails);

			UserDetails userDetails = new UserDetails();

			userDetails.setFirstName("Anil");
			userDetails.setMiddleName("Kumar");
			userDetails.setLastName("Sandrapati");
			userDetails.setDob("25/09/1989");
			userDetails.setAltContactNumber("9874562134");
			userDetails.setFatherName("svs");
			userDetails.setHusbandName("not applicable");
			userDetails.setBloodGroup("O+");
			userDetails.setPan("stvt5854k");
			userDetails.setPermanentAddress("eluru");
			userDetails.setPermanentCity("eluru");
			userDetails.setPermanentPincode("534001");
			userDetails.setCorrespondenceCity("Hyderabad");
			userDetails.setCorrespondencePincode("500004");
			userDetails.setCorrespondenceAddress("Khirathabad");
			userDetails.setSignature("S Anilkumar");
			userDetails.setIdentificationMark("mole on right hand");
			userDetails.setPhoto("anil.png");

			owner.setIsPrimaryOwner(true);
			owner.setIsSecondaryOwner(true);
			owner.setOwnerShipPercentage((double) 10);
			owner.setOwnerType("Sandrapati Anilkumar");

			owner.setUserDetails(userDetails);

			property.setOwners(owners);

			PropertyDetail propertyDetail = new PropertyDetail();
			propertyDetail.setSource(SourceEnum.MUNICIPAL_RECORDS.valueOf("MUNICIPAL_RECORDS"));
			propertyDetail.setRegdDocNo("regdoc1");
			propertyDetail.setRegdDocDate("25/05/2017");
			propertyDetail.setReason("testing");
			propertyDetail.setStatus(StatusEnum.ACTIVE.valueOf("ACTIVE"));
			propertyDetail.setIsVerified(true);
			propertyDetail.setVerificationDate("25/05/2017");
			propertyDetail.setIsExempted(true);
			propertyDetail.setExemptionReason("Testing");
			propertyDetail.setPropertyType("Land");
			propertyDetail.setCategory("Land");
			propertyDetail.setUsage("Anil");
			propertyDetail.setDepartment("Land department");
			propertyDetail.setApartment("apartment");
			propertyDetail.setSiteLength((double) 10);
			propertyDetail.setSiteBreadth((double) 15);
			propertyDetail.setSitalArea((double) 10);
			propertyDetail.setTotalBuiltupArea((double) 20);
			propertyDetail.setUndividedShare((double) 5);
			propertyDetail.setNoOfFloors((long) 1);
			propertyDetail.setIsSuperStructure(true);
			propertyDetail.setLandOwner("Anil");
			propertyDetail.setFloorType("normal");
			propertyDetail.setWoodType("normal");
			propertyDetail.setRoofType("normal");
			propertyDetail.setWallType("normal");
			propertyDetail.setAuditDetails(auditDetails);

			List<Floor> floors = propertyDetail.getFloors();

			Floor floor = new Floor();

			floor.setFloorNo("1");
			floor.setAuditDetails(auditDetails);

			List<Unit> units = new ArrayList<Unit>();

			Unit unit = new Unit();

			unit.setUnitNo(1);
			unit.setUnitType(UnitTypeEnum.FLAT.valueOf("FLAT"));
			unit.setLength((double) 10);
			unit.setWidth((double) 15);
			unit.setBuiltupArea((double) 15);
			unit.setAssessableArea((double) 25);
			unit.setBpaBuiltupArea((double) 35);
			unit.setBpaNo("bpa1");
			unit.setBpaDate("25/05/2017");
			unit.setUsage("construction");
			unit.setOccupancyType("business");
			unit.setOccupierName("Anil");
			unit.setFirmName("wtc");
			unit.setRentCollected((double) 12);
			unit.setStructure("rectangle");
			unit.setAge("27");
			unit.setExemptionReason("new property purchase");
			unit.setIsStructured(true);
			unit.setOccupancyDate("25/05/2017");
			unit.setConstCompletionDate("25/05/2017");
			unit.setManualArv((double) 5);
			unit.setArv((double) 10);
			unit.setElectricMeterNo("emno1");
			unit.setWaterMeterNo("waterno1");
			unit.setAuditDetails(auditDetails);
			floor.getUnits().add(unit);
			floor.setUnits(units);
			propertyDetail.getFloors().add(floor);

			propertyDetail.setFloors(floors);

			List<Document> documents = propertyDetail.getDocuments();
			Document document = new Document();

			DocumentType documentType = new DocumentType();
			documentType.setName("Testoc");
			documentType.setApplication(ApplicationEnum.CREATE.valueOf("CREATE"));
			documentType.setAuditDetails(auditDetails);
			document.setFileStore("filestoredoc");
			document.setAuditDetails(auditDetails);
			propertyDetail.getDocuments().add(document);
			document.setDocumentType(documentType);
			propertyDetail.setDocuments(documents);

			propertyDetail.setStateId("stateId1");
			propertyDetail.setApplicationNo("appno1");

			WorkFlowDetails workFlowDetails = new WorkFlowDetails();
			workFlowDetails.setDepartment("IT");
			workFlowDetails.setDesignation("se");
			workFlowDetails.setAssignee((long) 10);
			workFlowDetails.setAction("working");
			workFlowDetails.setStatus("processing");

			propertyDetail.setWorkFlowDetails(workFlowDetails);

			property.setPropertyDetail(propertyDetail);

			VacantLandDetail vacantLandDetails = new VacantLandDetail();
			vacantLandDetails.setSurveyNumber("sn1");
			vacantLandDetails.setPattaNumber("pt1");
			vacantLandDetails.setMarketValue((double) 150000);
			vacantLandDetails.setCapitalValue((double) 100000);
			vacantLandDetails.setLayoutApprovedAuth("approved");
			vacantLandDetails.setLayoutPermissionNo("pn1");
			vacantLandDetails.setLayoutPermissionDate("25/05/2017");
			vacantLandDetails.setResdPlotArea((double) 152);
			vacantLandDetails.setNonResdPlotArea((double) 154);
			vacantLandDetails.setAuditDetails(auditDetails);

			property.setVacantLand(vacantLandDetails);

			property.setAssessmentDate("25/05/2017");
			property.setOccupancyDate("25/05/2017");
			property.setGisRefNo("gf10");
			property.setIsAuthorised(true);
			property.setIsUnderWorkflow(true);

			PropertyLocation propertyLocation = new PropertyLocation();

			Boundary adminBoundary = new Boundary();
			Boundary locationBoundary = new Boundary();
			Boundary revenueBoundary = new Boundary();

			adminBoundary.setName("testing");
			locationBoundary.setName("testing");
			revenueBoundary.setName("testing");

			propertyLocation.setAdminBoundary(adminBoundary);
			propertyLocation.setLocationBoundary(locationBoundary);
			propertyLocation.setRevenueBoundary(revenueBoundary);

			propertyLocation.setNorthBoundedBy("north");
			propertyLocation.setSouthBoundedBy("south");
			propertyLocation.setWestBoundedBy("west");
			propertyLocation.setEastBoundedBy("east");
			propertyLocation.setAuditDetails(auditDetails);
			property.setBoundary(propertyLocation);

			property.setActive(true);
			property.setChannel(ChannelEnum.SYSTEM.valueOf("SYSTEM"));
			property.setAuditDetails(auditDetails);
			properties.add(property);

			propertyRequest.setProperties(properties);
			propertyRequest.setRequestInfo(requestInfo);

			producer.send(environment.getProperty("egov.propertytax.property.create.approved"), propertyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateProperty() throws Exception {
		try {
			PropertyRequest propertyRequest = new PropertyRequest();
			List<Property> properties = new ArrayList<Property>();

			Property property = new Property();

			AuditDetails auditDetails = new AuditDetails();
			auditDetails.setCreatedBy("Sandrapati");
			auditDetails.setLastModifiedBy("sandrapati");
			auditDetails.setCreatedTime((long) 123456);
			auditDetails.setLastModifiedTime((long) 987654);

			RequestInfo requestInfo = new RequestInfo();
			requestInfo.setApiId("emp");
			requestInfo.setVer("1.0");
			requestInfo.setTs((long) 20171205);
			requestInfo.setAction("create");
			requestInfo.setDid("1");
			requestInfo.setKey("abcdkey");
			requestInfo.setMsgId("20170310130900");
			requestInfo.setRequesterId("rajesh");
			requestInfo.setAuthToken("08db73a8-945e-4164-94e3-63ccef7856d4");

			property.setId((long) 1);
			property.setTenantId("default");
			property.setUpicNumber("anil123");
			property.setVltUpicNumber("vltUpicNumberupdate2");
			property.setCreationReason(CreationReasonEnum.NEWPROPERTY.valueOf("NEWPROPERTY"));

			Address address = new Address();
			address.setId((long) 1);
			address.setTenantId("default");
			address.setLatitude((double) 12);
			address.setLongitude((double) 20);
			address.setAddressNumber("1-2-3");
			address.setAddressLine1("acgaurds");
			address.setAddressLine2("khirathabad");
			address.setLandmark("noori");
			address.setCity("hyderabad");
			address.setPincode("500004");
			address.setDetail("Testing");

			address.setAuditDetails(auditDetails);

			property.setAddress(address);

			List<User> owners = new ArrayList<User>();
			User owner = new User();
			owner.setId((long) 1);
			owner.setTenantId("default");
			owner.setUserName("Anilkumar");
			owner.setAuthToken("08db73a8-945e-4164-94e3-63ccef7856d4");
			owner.setSalutation("testing");
			owner.setName("anil");
			owner.setGender("male");
			owner.setMobileNumber("9333555666");
			owner.setEmailId("anil@wtc.com");
			owner.setAadhaarNumber("123456789123");
			owner.setActive(true);
			owner.setLocale("no");
			owner.setType("house");
			owner.setAccountLocked(true);
			property.getOwners().add(owner);
			List<Role> roles = new ArrayList<Role>();

			Role role = new Role();
			role.setName("ANil");
			role.setDescription("Testing");
			owner.getRoles().add(role);
			owner.setRoles(roles);

			owner.setAuditDetails(auditDetails);

			UserDetails userDetails = new UserDetails();

			userDetails.setFirstName("Anil Kumar");
			userDetails.setMiddleName("Kumar");
			userDetails.setLastName("Sandrapati");
			userDetails.setDob("25/09/1989");
			userDetails.setAltContactNumber("9874562134");
			userDetails.setFatherName("svs");
			userDetails.setHusbandName("not applicable");
			userDetails.setBloodGroup("O+");
			userDetails.setPan("stvt5854k");
			userDetails.setPermanentAddress("eluru");
			userDetails.setPermanentCity("eluru");
			userDetails.setPermanentPincode("534001");
			userDetails.setCorrespondenceCity("Hyderabad");
			userDetails.setCorrespondencePincode("500004");
			userDetails.setCorrespondenceAddress("Khirathabad");
			userDetails.setSignature("S Anilkumar");
			userDetails.setIdentificationMark("mole on right hand");
			userDetails.setPhoto("anil.png");

			owner.setIsPrimaryOwner(true);
			owner.setIsSecondaryOwner(true);
			owner.setOwnerShipPercentage((double) 10);
			owner.setOwnerType("Sandrapati Anilkumar");

			owner.setUserDetails(userDetails);

			property.setOwners(owners);

			PropertyDetail propertyDetail = new PropertyDetail();
			propertyDetail.setId((long) 1);
			propertyDetail.setSource(SourceEnum.MUNICIPAL_RECORDS.valueOf("MUNICIPAL_RECORDS"));
			propertyDetail.setRegdDocNo("regdocupdate1");
			propertyDetail.setRegdDocDate("25/05/2017");
			propertyDetail.setReason("testing");
			propertyDetail.setStatus(StatusEnum.ACTIVE.valueOf("ACTIVE"));
			propertyDetail.setIsVerified(true);
			propertyDetail.setVerificationDate("25/05/2017");
			propertyDetail.setIsExempted(true);
			propertyDetail.setExemptionReason("Testing");
			propertyDetail.setPropertyType("Land");
			propertyDetail.setCategory("Land");
			propertyDetail.setUsage("Anil");
			propertyDetail.setDepartment("Land department");
			propertyDetail.setApartment("apartment");
			propertyDetail.setSiteLength((double) 10);
			propertyDetail.setSiteBreadth((double) 15);
			propertyDetail.setSitalArea((double) 10);
			propertyDetail.setTotalBuiltupArea((double) 20);
			propertyDetail.setUndividedShare((double) 5);
			propertyDetail.setNoOfFloors((long) 1);
			propertyDetail.setIsSuperStructure(true);
			propertyDetail.setLandOwner("Anil");
			propertyDetail.setFloorType("normal");
			propertyDetail.setWoodType("normal");
			propertyDetail.setRoofType("normal");
			propertyDetail.setWallType("normal");
			propertyDetail.setAuditDetails(auditDetails);

			List<Floor> floors = propertyDetail.getFloors();

			Floor floor = new Floor();
			floor.setId((long) 1);
			floor.setFloorNo("f1");
			floor.setAuditDetails(auditDetails);

			List<Unit> units = new ArrayList<Unit>();

			Unit unit = new Unit();
			unit.setId((long) 1);
			unit.setUnitNo(1);
			unit.setUnitType(UnitTypeEnum.FLAT.valueOf("FLAT"));
			unit.setLength((double) 15);
			unit.setWidth((double) 15);
			unit.setBuiltupArea((double) 15);
			unit.setAssessableArea((double) 25);
			unit.setBpaBuiltupArea((double) 35);
			unit.setBpaNo("bpa1");
			unit.setBpaDate("25/05/2017");
			unit.setUsage("construction");
			unit.setOccupancyType("business");
			unit.setOccupierName("Anil");
			unit.setFirmName("wtc");
			unit.setRentCollected((double) 12);
			unit.setStructure("rectangle");
			unit.setAge("27");
			unit.setExemptionReason("new property purchase");
			unit.setIsStructured(true);
			unit.setOccupancyDate("25/05/2017");
			unit.setConstCompletionDate("25/05/2017");
			unit.setManualArv((double) 5);
			unit.setArv((double) 10);
			unit.setElectricMeterNo("emno1");
			unit.setWaterMeterNo("waterno1");
			unit.setAuditDetails(auditDetails);
			floor.getUnits().add(unit);
			floor.setUnits(units);
			propertyDetail.getFloors().add(floor);

			propertyDetail.setFloors(floors);

			List<Document> documents = propertyDetail.getDocuments();
			Document document = new Document();

			DocumentType documentType = new DocumentType();
			documentType.setId((long) 1);
			documentType.setName("Testocupdate");
			documentType.setApplication(ApplicationEnum.CREATE.valueOf("CREATE"));
			documentType.setAuditDetails(auditDetails);
			document.setId((long) 1);
			document.setFileStore("filestoredoc1");
			document.setAuditDetails(auditDetails);
			propertyDetail.getDocuments().add(document);
			document.setDocumentType(documentType);
			propertyDetail.setDocuments(documents);

			propertyDetail.setStateId("stateId1");
			propertyDetail.setApplicationNo("appno1");

			WorkFlowDetails workFlowDetails = new WorkFlowDetails();
			workFlowDetails.setDepartment("IT");
			workFlowDetails.setDesignation("se");
			workFlowDetails.setAssignee((long) 10);
			workFlowDetails.setAction("working");
			workFlowDetails.setStatus("processing");

			propertyDetail.setWorkFlowDetails(workFlowDetails);

			property.setPropertyDetail(propertyDetail);

			VacantLandDetail vacantLandDetails = new VacantLandDetail();
			vacantLandDetails.setId((long) 1);
			vacantLandDetails.setSurveyNumber("snupdate1");
			vacantLandDetails.setPattaNumber("pt1");
			vacantLandDetails.setMarketValue((double) 150000);
			vacantLandDetails.setCapitalValue((double) 100000);
			vacantLandDetails.setLayoutApprovedAuth("approved");
			vacantLandDetails.setLayoutPermissionNo("pn1");
			vacantLandDetails.setLayoutPermissionDate("25/05/2017");
			vacantLandDetails.setResdPlotArea((double) 152);
			vacantLandDetails.setNonResdPlotArea((double) 154);
			vacantLandDetails.setAuditDetails(auditDetails);

			property.setVacantLand(vacantLandDetails);

			property.setAssessmentDate("25/05/2017");
			property.setOccupancyDate("25/05/2017");
			property.setGisRefNo("gf10");
			property.setIsAuthorised(true);
			property.setIsUnderWorkflow(true);

			PropertyLocation propertyLocation = new PropertyLocation();

			Boundary adminBoundary = new Boundary();
			Boundary locationBoundary = new Boundary();
			Boundary revenueBoundary = new Boundary();

			adminBoundary.setName("test");
			locationBoundary.setName("testing");
			revenueBoundary.setName("testing");
			propertyLocation.setId((long) 5);
			propertyLocation.setAdminBoundary(adminBoundary);
			propertyLocation.setLocationBoundary(locationBoundary);
			propertyLocation.setRevenueBoundary(revenueBoundary);

			propertyLocation.setNorthBoundedBy("north");
			propertyLocation.setSouthBoundedBy("south");
			propertyLocation.setWestBoundedBy("west");
			propertyLocation.setEastBoundedBy("east");
			propertyLocation.setAuditDetails(auditDetails);
			property.setBoundary(propertyLocation);

			property.setActive(true);
			property.setChannel(ChannelEnum.SYSTEM.valueOf("SYSTEM"));
			property.setAuditDetails(auditDetails);
			properties.add(property);

			propertyRequest.setProperties(properties);
			propertyRequest.setRequestInfo(requestInfo);

			producer.send(environment.getProperty("egov.propertytax.property.update.approved"), propertyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createDepreciation() {

		DepreciationRequest depreciationRequest = new DepreciationRequest();
		depreciationRequest.setRequestInfo(getRequestInfoObject());

		String tenantId = "123";
		Depreciation depreciation = new Depreciation();
		List<Depreciation> depreciations = new ArrayList<>();
		depreciation.setTenantId("123");
		depreciation.setNameLocal("prasad");
		depreciation.setFromYear(2017);
		depreciation.setCode("456");
		depreciation.setToyear(2016);
		depreciation.setDescription("Intial loading");

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("prasad");
		auditDetails.setLastModifiedBy("pranav");
		auditDetails.setCreatedTime(new Long(23));
		auditDetails.setLastModifiedTime(new Long(256));

		depreciation.setAuditDetails(auditDetails);
		depreciations.add(depreciation);

		depreciationRequest.setDepreciations(depreciations);
		DepreciationResponse depreciationResponse = null;

		try {
			depreciationResponse = masterService.createDepreciation(tenantId, depreciationRequest);
		} catch (Exception e) {

			assertTrue(false);
		}

		if (depreciationResponse.getDepreciations().size() == 0) {
			assertTrue(false);
		}

		assertTrue(true);

	}

	@Test
	public void modifyDepreciation() {

		DepreciationRequest depreciationRequest = new DepreciationRequest();
		depreciationRequest.setRequestInfo(getRequestInfoObject());

		Depreciation depreciation = new Depreciation();
		List<Depreciation> depreciations = new ArrayList<>();
		depreciation.setId(1l);
		depreciation.setTenantId("12345");
		depreciation.setNameLocal("prasad");
		depreciation.setFromYear(2017);
		depreciation.setCode("4567");
		depreciation.setToyear(2016);
		depreciation.setDescription("Intial loading");

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("pankaj");
		auditDetails.setLastModifiedBy("paku");
		auditDetails.setCreatedTime(new Long(23));
		auditDetails.setLastModifiedTime(new Long(256));

		depreciation.setAuditDetails(auditDetails);
		depreciations.add(depreciation);

		depreciationRequest.setDepreciations(depreciations);
		DepreciationResponse depreciationResponse = null;

		try {
			depreciationResponse = masterService.updateDepreciation(depreciationRequest);
		} catch (Exception e) {

			assertTrue(false);
		}

		if (depreciationResponse == null)
			assertTrue(false);

		if (depreciationResponse.getDepreciations().size() == 0)
			assertTrue(false);

		assertTrue(true);
	}

	@Test
	public void searchDepreciations() {

		String tenantId = "12345";
		Integer[] ids = new Integer[] { 1, 2, 7 };

		Integer fromYear = 2017;
		Integer toYear = 2016;
		String code = "4567";
		String nameLocal = "prasad";
		Integer pageSize = 20;
		Integer offset = 0;

		DepreciationResponse depreciationResponse = null;

		try {
			depreciationResponse = masterService.searchDepreciation(getRequestInfoObject(), tenantId, ids, fromYear,
					toYear, code, nameLocal, pageSize, offset);
		} catch (Exception e) {
			assertTrue(false);
		}

		if (depreciationResponse == null)
			assertTrue(false);

		if (depreciationResponse.getDepreciations().size() == 0) {
			assertTrue(false);
		}

		assertTrue(true);

	}

	@Test
	public void createMutationTest() {

		MutationMasterRequest mutationMasterRequest = new MutationMasterRequest();
		MutationMaster mutationMaster = new MutationMaster();
		mutationMaster.setTenantId("123");
		mutationMaster.setCode("456");
		mutationMaster.setName("prasad");
		mutationMaster.setNameLocal("pranav");
		mutationMaster.setDescription("description");

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("pankaj");
		auditDetails.setLastModifiedBy("paku");
		auditDetails.setCreatedTime(new Long(23));
		auditDetails.setLastModifiedTime(new Long(256));
		mutationMaster.setAuditDetails(auditDetails);

		List<MutationMaster> mutationMasters = new ArrayList<>();
		mutationMasters.add(mutationMaster);
		String tenantId = "123";

		mutationMasterRequest.setRequestInfo(getRequestInfoObject());
		mutationMasterRequest.setMutationMasters(mutationMasters);

		MutationMasterResponse mutationMasterResponse = null;

		try {
			mutationMasterResponse = masterService.createMutationMater(tenantId, mutationMasterRequest);
		} catch (Exception e) {
			assertTrue(false);
		}

		if (mutationMasterResponse != null && mutationMasterResponse.getMutationMasters().size() > 0)
			assertTrue(true);

		else
			assertTrue(false);

	}

	@Test
	public void modifyMutationMaster() {

		MutationMasterRequest mutationMasterRequest = new MutationMasterRequest();
		MutationMaster mutationMaster = new MutationMaster();
		mutationMaster.setTenantId("1234");
		mutationMaster.setCode("4567");
		mutationMaster.setName("pankaj");
		mutationMaster.setNameLocal("paku");
		mutationMaster.setDescription("description");
		mutationMaster.setId(1l);

		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedBy("pankaj");
		auditDetails.setLastModifiedBy("paku");
		auditDetails.setCreatedTime(new Long(23));
		auditDetails.setLastModifiedTime(new Long(256));
		mutationMaster.setAuditDetails(auditDetails);

		List<MutationMaster> mutationMasters = new ArrayList<>();
		mutationMasters.add(mutationMaster);

		mutationMasterRequest.setRequestInfo(getRequestInfoObject());
		mutationMasterRequest.setMutationMasters(mutationMasters);

		MutationMasterResponse mutationMasterResponse = null;

		try {
			mutationMasterResponse = masterService.updateMutationMaster(mutationMasterRequest);
		} catch (Exception e) {
			assertTrue(false);
		}

		if (mutationMasterResponse != null && mutationMasterResponse.getMutationMasters().size() > 0)
			assertTrue(true);

		else
			assertTrue(false);

	}

	@Test
	public void searchMutationMaster() {
		String tenantId = "1234";
		String code = "4567";
		MutationMasterResponse mutationMasterResponse = null;

		try {
			mutationMasterResponse = masterService.searchMutationMaster(getRequestInfoObject(), tenantId, null, null,
					code, null, null, null);
		} catch (Exception e) {
			assertTrue(false);
		}

		if (mutationMasterResponse != null && mutationMasterResponse.getMutationMasters().size() > 0) {
			assertTrue(true);
		} else
			assertTrue(false);
	}
}
