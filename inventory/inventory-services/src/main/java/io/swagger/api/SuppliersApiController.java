/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package io.swagger.api;


import io.swagger.model.Pagination;
import io.swagger.model.Supplier;
import io.swagger.model.SupplierRequest;
import io.swagger.model.SupplierResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.inv.domain.model.SupplierGetRequest;
import org.egov.inv.domain.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-02T13:59:35.200+05:30")

@Controller
public class SuppliersApiController implements SuppliersApi {

	@Autowired
	private SupplierService supplierService;

	private static final Logger log = LoggerFactory.getLogger(SuppliersApiController.class);

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@org.springframework.beans.factory.annotation.Autowired
	public SuppliersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	public ResponseEntity<SupplierResponse> suppliersCreatePost(
			@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId,
			@ApiParam(value = "Create  new") @Valid @RequestBody SupplierRequest supplierRequest,
			@RequestHeader(value = "Accept", required = false) String accept, BindingResult errors) throws Exception {
		List<Supplier> suppliers = supplierService.create(supplierRequest, tenantId, errors);
		SupplierResponse supplierResponse = buildSupplierResponse(suppliers, supplierRequest.getRequestInfo());
		if (accept != null && accept.contains("application/json")) {
			return new ResponseEntity<SupplierResponse>(objectMapper.readValue(
					"{  \"suppliers\" : [ {    \"bankInfo\" : {      \"acctNo\" : \"acctNo\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\",      \"micr\" : \"micr\",      \"ifsc\" : \"ifsc\"    },    \"website\" : \"website\",    \"code\" : \"code\",    \"address\" : \"address\",    \"panNo\" : \"panNo\",    \"vatNo\" : \"vatNo\",    \"contactPerson\" : \"contactPerson\",    \"cstNo\" : \"cstNo\",    \"tinNo\" : \"tinNo\",    \"contactPersonNo\" : \"contactPersonNo\",    \"faxNo\" : \"faxNo\",    \"supplierContactNo\" : \"supplierContactNo\",    \"narration\" : \"narration\",    \"auditDetails\" : {      \"lastModifiedTime\" : 1,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 6    },    \"tenantId\" : \"tenantId\",    \"name\" : \"name\",    \"inActiveDate\" : 0,    \"id\" : \"id\",    \"supplierType\" : {      \"code\" : \"code\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\"    },    \"email\" : \"email\",    \"status\" : {      \"code\" : \"code\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\"    }  }, {    \"bankInfo\" : {      \"acctNo\" : \"acctNo\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\",      \"micr\" : \"micr\",      \"ifsc\" : \"ifsc\"    },    \"website\" : \"website\",    \"code\" : \"code\",    \"address\" : \"address\",    \"panNo\" : \"panNo\",    \"vatNo\" : \"vatNo\",    \"contactPerson\" : \"contactPerson\",    \"cstNo\" : \"cstNo\",    \"tinNo\" : \"tinNo\",    \"contactPersonNo\" : \"contactPersonNo\",    \"faxNo\" : \"faxNo\",    \"supplierContactNo\" : \"supplierContactNo\",    \"narration\" : \"narration\",    \"auditDetails\" : {      \"lastModifiedTime\" : 1,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 6    },    \"tenantId\" : \"tenantId\",    \"name\" : \"name\",    \"inActiveDate\" : 0,    \"id\" : \"id\",    \"supplierType\" : {      \"code\" : \"code\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\"    },    \"email\" : \"email\",    \"status\" : {      \"code\" : \"code\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\"    }  } ],  \"page\" : {    \"totalResults\" : 1,    \"offSet\" : 1,    \"totalPages\" : 1,    \"pageSize\" : 6,    \"currentPage\" : 7  },  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}",
					SupplierResponse.class), HttpStatus.OK);
		}
		return new ResponseEntity(supplierResponse, HttpStatus.OK);
	}

	public ResponseEntity<SupplierResponse> suppliersSearchPost(
			@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @RequestParam(value = "tenantId", required = true) String tenantId,
			@ApiParam(value = "Parameter to carry Request metadata in the request body") @Valid @RequestBody RequestInfo requestInfo,
			@Size(max = 50) @ApiParam(value = "comma seperated list of Ids") @RequestParam(value = "ids", required = false) List<String> ids,
			@ApiParam(value = "code of the Supplier ") @RequestParam(value = "code", required = false) String code,
			@ApiParam(value = "name of the Supplier ") @RequestParam(value = "name", required = false) String name,
			@ApiParam(value = "type of the Supplier ") @RequestParam(value = "supplyType", required = false) String supplyType,
			@ApiParam(value = "address of the Supplier ") @RequestParam(value = "address", required = false) String address,
			@ApiParam(value = "status     ") @RequestParam(value = "status", required = false) String status,
			@ApiParam(value = "inActiveDate ") @RequestParam(value = "inActiveDate", required = false) Long inActiveDate,
			@ApiParam(value = "contactNo of the Supplier ") @RequestParam(value = "supplierContactNo", required = false) String supplierContactNo,
			@ApiParam(value = "faxNo of the Supplier ") @RequestParam(value = "faxNo", required = false) String faxNo,
			@ApiParam(value = "website of the Supplier ") @RequestParam(value = "website", required = false) String website,
			@ApiParam(value = "email of the Supplier ") @RequestParam(value = "email", required = false) String email,
			@ApiParam(value = "panNo of the Supplier ") @RequestParam(value = "panNo", required = false) String panNo,
			@ApiParam(value = "tinNo of the Supplier ") @RequestParam(value = "tinNo", required = false) String tinNo,
			@ApiParam(value = "cstNo of the Supplier ") @RequestParam(value = "cstNo", required = false) String cstNo,
			@ApiParam(value = "vatNo    ") @RequestParam(value = "vatNo", required = false) String vatNo,
	        @ApiParam(value = "gstNo    ") @RequestParam(value = "gstNo", required = false) String gstNo,
			@ApiParam(value = "contactPerson      ") @RequestParam(value = "contactPerson", required = false) String contactPerson,
			@ApiParam(value = "contactPersonNo      ") @RequestParam(value = "contactPersonNo", required = false) String contactPersonNo,
	        @ApiParam(value = "code of the bank ") @RequestParam(value = "bankCode", required = false) String bankCode,
	        @ApiParam(value = "code of the bankBranch ") @RequestParam(value = "bankBranchCode", required = false) String bankBranchCode,
	        @ApiParam(value = "account number of the bank account ") @RequestParam(value = "bankAccNo", required = false) String bankAccNo,
			@ApiParam(value = "ifsc code of the bank account ") @RequestParam(value = "bankIfsc", required = false) String bankIfsc,
			@Min(0) @Max(100) @ApiParam(value = "Number of records returned.", defaultValue = "20") @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
			@ApiParam(value = "offset") @RequestParam(value = "offset", required = false) Integer offset,
			@ApiParam(value = "Page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
			@ApiParam(value = "This takes any field from the Object seperated by comma and asc,desc keywords. example name asc,code desc or name,code or name,code desc") @RequestParam(value = "sortBy", required = false) String sortBy) {
		SupplierGetRequest supplierGetRequest = SupplierGetRequest.builder().ids(ids).code(code).name(name)
				.supplyType(supplyType).address(address).status(status).inActiveDate(inActiveDate)
				.supplierContactNo(supplierContactNo).faxNo(faxNo).website(website).email(email).panNo(panNo)
				.tinNo(tinNo).cstNo(cstNo).vatNo(vatNo).gstNo(gstNo).contactPerson(contactPerson).contactPersonNo(contactPersonNo)
				.bankCode(bankCode).bankBranchCode(bankBranchCode).bankAccNo(bankAccNo).bankIfsc(bankIfsc).pageSize(pageSize).offset(offset)
				.pageNumber(pageNumber).sortBy(sortBy).build();
		Pagination<Supplier> suppliers = supplierService.search(supplierGetRequest);
		SupplierResponse response = new SupplierResponse();
		response.setSuppliers(suppliers.getPagedData());
		response.setResponseInfo(getResponseInfo(requestInfo));

		return new ResponseEntity(response, HttpStatus.OK);
	}

	public ResponseEntity<SupplierResponse> suppliersUpdatePost(
			@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId,
			@ApiParam(value = "common Request info") @Valid @RequestBody SupplierRequest supplierRequest,
			@RequestHeader(value = "Accept", required = false) String accept, BindingResult errors) throws Exception {
		List<Supplier> suppliers = supplierService.update(supplierRequest, tenantId, errors);
		SupplierResponse supplierResponse = buildSupplierResponse(suppliers, supplierRequest.getRequestInfo());
		if (accept != null && accept.contains("application/json")) {
			return new ResponseEntity<SupplierResponse>(objectMapper.readValue(
					"{  \"suppliers\" : [ {    \"bankInfo\" : {      \"acctNo\" : \"acctNo\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\",      \"micr\" : \"micr\",      \"ifsc\" : \"ifsc\"    },    \"website\" : \"website\",    \"code\" : \"code\",    \"address\" : \"address\",    \"panNo\" : \"panNo\",    \"vatNo\" : \"vatNo\",    \"contactPerson\" : \"contactPerson\",    \"cstNo\" : \"cstNo\",    \"tinNo\" : \"tinNo\",    \"contactPersonNo\" : \"contactPersonNo\",    \"faxNo\" : \"faxNo\",    \"supplierContactNo\" : \"supplierContactNo\",    \"narration\" : \"narration\",    \"auditDetails\" : {      \"lastModifiedTime\" : 1,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 6    },    \"tenantId\" : \"tenantId\",    \"name\" : \"name\",    \"inActiveDate\" : 0,    \"id\" : \"id\",    \"supplierType\" : {      \"code\" : \"code\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\"    },    \"email\" : \"email\",    \"status\" : {      \"code\" : \"code\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\"    }  }, {    \"bankInfo\" : {      \"acctNo\" : \"acctNo\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\",      \"micr\" : \"micr\",      \"ifsc\" : \"ifsc\"    },    \"website\" : \"website\",    \"code\" : \"code\",    \"address\" : \"address\",    \"panNo\" : \"panNo\",    \"vatNo\" : \"vatNo\",    \"contactPerson\" : \"contactPerson\",    \"cstNo\" : \"cstNo\",    \"tinNo\" : \"tinNo\",    \"contactPersonNo\" : \"contactPersonNo\",    \"faxNo\" : \"faxNo\",    \"supplierContactNo\" : \"supplierContactNo\",    \"narration\" : \"narration\",    \"auditDetails\" : {      \"lastModifiedTime\" : 1,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 6    },    \"tenantId\" : \"tenantId\",    \"name\" : \"name\",    \"inActiveDate\" : 0,    \"id\" : \"id\",    \"supplierType\" : {      \"code\" : \"code\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\"    },    \"email\" : \"email\",    \"status\" : {      \"code\" : \"code\",      \"tenantId\" : \"tenantId\",      \"name\" : \"name\"    }  } ],  \"page\" : {    \"totalResults\" : 1,    \"offSet\" : 1,    \"totalPages\" : 1,    \"pageSize\" : 6,    \"currentPage\" : 7  },  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}",
					SupplierResponse.class), HttpStatus.NOT_IMPLEMENTED);
		}
		return new ResponseEntity(supplierResponse, HttpStatus.OK);
	}

	private SupplierResponse buildSupplierResponse(List<Supplier> suppliers, RequestInfo requestInfo) {
		return SupplierResponse.builder().responseInfo(getResponseInfo(requestInfo)).suppliers(suppliers).build();
	}

	private ResponseInfo getResponseInfo(RequestInfo requestInfo) {
		return ResponseInfo.builder().apiId(requestInfo.getApiId()).ver(requestInfo.getVer())
				.resMsgId(requestInfo.getMsgId()).resMsgId("placeholder").status("placeholder").build();
	}

}