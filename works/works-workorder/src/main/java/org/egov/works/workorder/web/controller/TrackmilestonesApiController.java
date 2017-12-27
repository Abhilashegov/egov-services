package org.egov.works.workorder.web.controller;

import io.swagger.annotations.ApiParam;
import org.egov.works.workorder.domain.service.TrackMilestoneService;
import org.egov.works.workorder.web.contract.RequestInfo;
import org.egov.works.workorder.web.contract.TrackMilestoneRequest;
import org.egov.works.workorder.web.contract.TrackMilestoneResponse;
import org.egov.works.workorder.web.contract.TrackMilestoneSearchContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-19T12:22:16.579Z")

@Controller
public class TrackmilestonesApiController implements TrackmilestonesApi {

    @Autowired
    private TrackMilestoneService trackMilestoneService;

    public ResponseEntity<TrackMilestoneResponse> trackmilestonesCreatePost(@ApiParam(value = "Details of new Track Milestone(s) + RequestInfo meta data." ,required=true )  @Valid @RequestBody TrackMilestoneRequest trackMilestoneRequest) {
        TrackMilestoneResponse trackMilestoneResponse = trackMilestoneService.create(trackMilestoneRequest);
        return new ResponseEntity(trackMilestoneResponse, HttpStatus.OK);
    }

    public ResponseEntity<TrackMilestoneResponse> trackmilestonesSearchPost( @NotNull@ApiParam(value = "Unique id for a tenant.", required = true) @RequestParam(value = "tenantId", required = true) String tenantId,
        @ApiParam(value = "Parameter to carry Request metadata in the request body"  )  @RequestBody RequestInfo requestInfo,
         @Min(0) @Max(100)@ApiParam(value = "Number of records returned.", defaultValue = "20") @RequestParam(value = "pageSize", required = false, defaultValue="20") Integer pageSize,
        @ApiParam(value = "Page number", defaultValue = "1") @RequestParam(value = "pageNumber", required = false, defaultValue="1") Integer pageNumber,
        @ApiParam(value = "This takes any field from the Object seperated by comma and asc,desc keywords. example name asc,code desc or name,code or name,code desc", defaultValue = "id") @RequestParam(value = "sortBy", required = false, defaultValue="id") String sortBy,
         @Size(max=50)@ApiParam(value = "Comma separated list of Ids of Track Milestone to get the Track Milestones") @RequestParam(value = "ids", required = false) List<String> ids,
         @Size(max=50)@ApiParam(value = "Status of the Track Milestone") @RequestParam(value = "statuses", required = false) List<String> statuses,
         @Size(max=50)@ApiParam(value = "Comma separated list of Work Order Numbers") @RequestParam(value = "workOrderNumbers", required = false) List<String> workOrderNumbers,
         @Size(max=50)@ApiParam(value = "Comma separated list of LOA Numbers") @RequestParam(value = "loaNumbers", required = false) List<String> loaNumbers,
         @Size(max=50)@ApiParam(value = "Comma separated list of Detailed Estimate Numbers") @RequestParam(value = "detailedEstimateNumbers", required = false) List<String> detailedEstimateNumbers,
         @Size(max=50)@ApiParam(value = "Comma separated list of Work Identification Numbers") @RequestParam(value = "workIdentificationNumbers", required = false) List<String> workIdentificationNumbers,
         @Size(max=50)@ApiParam(value = "Comma separated list of the Department for which the Track Milestone belongs to") @RequestParam(value = "departments", required = false) List<String> departments,
         @Size(max=50)@ApiParam(value = "Comma separated list of Names of the contractor to which Track Milestone belongs to") @RequestParam(value = "contractorNames", required = false) List<String> contractorNames,
         @Size(max=50)@ApiParam(value = "Comma separated list of codes of the contractor to which Track Milestone belongs to") @RequestParam(value = "contractorCodes", required = false) List<String> contractorCodes) {
        TrackMilestoneSearchContract trackMilestoneSearchContract = TrackMilestoneSearchContract.builder()
                .pageSize(pageSize).pageNumber(pageNumber).tenantId(tenantId).ids(ids).statuses(statuses).workOrderNumbers(workOrderNumbers).loaNumbers(loaNumbers).detailedEstimateNumbers(detailedEstimateNumbers)
                .workIdentificationNumbers(workIdentificationNumbers).departments(departments).contractorNames(contractorNames).contractorCodes(contractorCodes)
                .pageNumber(pageNumber).pageSize(pageSize).build();
        TrackMilestoneResponse trackMilestoneResponse = trackMilestoneService.search(trackMilestoneSearchContract, requestInfo);
        return new ResponseEntity(trackMilestoneResponse, HttpStatus.OK);
    }

    public ResponseEntity<TrackMilestoneResponse> trackmilestonesUpdatePost(@ApiParam(value = "Details of Track Milestone(s) + RequestInfo meta data." ,required=true )  @Valid @RequestBody TrackMilestoneRequest trackMilestoneRequest) {
        TrackMilestoneResponse trackMilestoneResponse = trackMilestoneService.update(trackMilestoneRequest);
        return new ResponseEntity(trackMilestoneResponse, HttpStatus.OK);
    }

}
