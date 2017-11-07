/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.0-SNAPSHOT).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.ErrorRes;
import io.swagger.model.RequestInfo;
import io.swagger.model.SupplierRequest;
import io.swagger.model.SupplierResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-02T13:59:35.200+05:30")

@Api(value = "suppliers", description = "the suppliers API")
public interface SuppliersApi {

    @ApiOperation(value = "Create  new  suppliers", nickname = "suppliersCreatePost", notes = "Create  new suppliers", response = SupplierResponse.class, tags={ "Supplier", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Supplier created Successfully", response = SupplierResponse.class),
        @ApiResponse(code = 400, message = "Invalid Input", response = ErrorRes.class) })
    @RequestMapping(value = "/suppliers/_create",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SupplierResponse> suppliersCreatePost(@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId,@ApiParam(value = "Create  new"  )  @Valid @RequestBody SupplierRequest supplierRequest,@RequestHeader(value = "Accept", required = false) String accept,BindingResult errors) throws Exception;

    @ApiOperation(value = "Get the list of suppliers", notes = "suppliers", response = SupplierResponse.class, tags={ "Supplier", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Supplier retrieved Successfully", response = SupplierResponse.class),
        @ApiResponse(code = 400, message = "Invalid Input", response = ErrorRes.class) })
    
    @RequestMapping(value = "/suppliers/_search",
    produces = { "application/json" }, 
    consumes = { "application/json" },
    method = RequestMethod.POST)
ResponseEntity<SupplierResponse> suppliersSearchPost( @NotNull@ApiParam(value = "Unique id for a tenant.", required = true) @RequestParam(value = "tenantId", required = true) String tenantId,@ApiParam(value = "Parameter to carry Request metadata in the request body"  )  @Valid @RequestBody org.egov.common.contract.request.RequestInfo requestInfo, @Size(max=50)@ApiParam(value = "comma seperated list of Ids") @RequestParam(value = "ids", required = false) List<String> ids,@ApiParam(value = "list of codes  of the Supplier ") @RequestParam(value = "codes", required = false) List<String> codes,@ApiParam(value = "name of the Supplier ") @RequestParam(value = "name", required = false) String name,@ApiParam(value = "type of the Supplier ") @RequestParam(value = "type", required = false) String type,@ApiParam(value = "status     ") @RequestParam(value = "status", required = false) String status,@ApiParam(value = "inActiveDate ") @RequestParam(value = "inActiveDate", required = false) Long inActiveDate,@ApiParam(value = "active or inactive status of the supplier ") @RequestParam(value = "active", required = false) Boolean active,@ApiParam(value = "contactNo of the Supplier ") @RequestParam(value = "contactNo", required = false) String contactNo,@ApiParam(value = "faxNo of the Supplier ") @RequestParam(value = "faxNo", required = false) String faxNo,@ApiParam(value = "website of the Supplier ") @RequestParam(value = "website", required = false) String website,@ApiParam(value = "email of the Supplier ") @RequestParam(value = "email", required = false) String email,@ApiParam(value = "panNo of the Supplier ") @RequestParam(value = "panNo", required = false) String panNo,@ApiParam(value = "tinNo of the Supplier ") @RequestParam(value = "tinNo", required = false) String tinNo,@ApiParam(value = "cstNo of the Supplier ") @RequestParam(value = "cstNo", required = false) String cstNo,@ApiParam(value = "vatNo  ") @RequestParam(value = "vatNo", required = false) String vatNo,@ApiParam(value = "gstNo     ") @RequestParam(value = "gstNo", required = false) String gstNo,@ApiParam(value = "contactPerson      ") @RequestParam(value = "contactPerson", required = false) String contactPerson,@ApiParam(value = "contactPersonNo      ") @RequestParam(value = "contactPersonNo", required = false) String contactPersonNo,@ApiParam(value = "code of the bank ") @RequestParam(value = "bankCode", required = false) String bankCode,@ApiParam(value = "name of the bankBranch ") @RequestParam(value = "bankBranch", required = false) String bankBranch,@ApiParam(value = "account number of the bank account ") @RequestParam(value = "bankAccNo", required = false) String bankAccNo,@ApiParam(value = "ifsc code of the bank account ") @RequestParam(value = "bankIfsc", required = false) String bankIfsc,@ApiParam(value = "micr code of the bank account ") @RequestParam(value = "bankMicr", required = false) String bankMicr, @Min(0) @Max(100)@ApiParam(value = "Number of records returned.", defaultValue = "20") @RequestParam(value = "pageSize", required = false, defaultValue="20") Integer pageSize,@ApiParam(value = "offset") @RequestParam(value = "offset", required = false) Integer offset,@ApiParam(value = "Page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue="1") Integer pageNumber,@ApiParam(value = "This takes any field from the Object seperated by comma and asc,desc keywords. example name asc,code desc or name,code or name,code desc") @RequestParam(value = "sortBy", required = false) String sortBy);
    
    @ApiOperation(value = "Update any of the suppliers", nickname = "suppliersUpdatePost", notes = "Update any of the suppliers", response = SupplierResponse.class, tags={ "Supplier", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Supplier updated Successfully", response = SupplierResponse.class),
        @ApiResponse(code = 400, message = "Invalid Input", response = ErrorRes.class) })
    @RequestMapping(value = "/suppliers/_update",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SupplierResponse> suppliersUpdatePost(@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId,@ApiParam(value = "common Request info"  )  @Valid @RequestBody SupplierRequest supplierRequest,@RequestHeader(value = "Accept", required = false) String accept,BindingResult errors) throws Exception;

}