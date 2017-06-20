/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) 2016  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.wcms.transanction.web.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.wcms.transanction.config.ApplicationProperties;
import org.egov.wcms.transanction.model.Connection;
import org.egov.wcms.transanction.service.WaterConnectionService;
import org.egov.wcms.transanction.validator.NewWaterConnectionValidator;
import org.egov.wcms.transanction.web.contract.WaterConnectionReq;
import org.egov.wcms.transanction.web.contract.WaterConnectionRes;
import org.egov.wcms.transanction.web.contract.factory.ResponseInfoFactory;
import org.egov.wcms.transanction.web.errorhandlers.Error;
import org.egov.wcms.transanction.web.errorhandlers.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connection")
public class WaterConnectionController {

    private static final Logger logger = LoggerFactory.getLogger(WaterConnectionController.class);

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private ApplicationProperties applicationProperties;
    

    @Autowired
    private NewWaterConnectionValidator newWaterConnectionValidator;

    @Autowired
    private WaterConnectionService waterConnectionService;

    @PostMapping(value = "/_create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody @Valid final WaterConnectionReq waterConnectionRequest,
            final BindingResult errors) {
        if (errors.hasErrors()) {
            final ErrorResponse errRes = newWaterConnectionValidator.populateErrors(errors);
            return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
        }
        logger.info("WaterConnectionRequest::" + waterConnectionRequest);
        final List<ErrorResponse> errorResponses = newWaterConnectionValidator
                .validateWaterConnectionRequest(waterConnectionRequest);
        if (!errorResponses.isEmpty())
            return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);

        // Call to service.
        final Connection connection = waterConnectionService.createWaterConnection(
                applicationProperties.getCreateNewConnectionTopicName(),
                "newconnection-create", waterConnectionRequest);
        return getSuccessResponse(connection, waterConnectionRequest.getRequestInfo());

    }
    @PostMapping(value = "/{applicationCode}/_update")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody @Valid final WaterConnectionReq waterConnectionRequest,
            final BindingResult errors,@PathVariable("applicationCode") final String applicationCode) {
        String [] acknumarray=applicationCode.split(Pattern.quote("{"));
        String acknumer=acknumarray[1].split(Pattern.quote("}"))[0];
        waterConnectionRequest.getConnection().setAcknowledgementNumber(acknumer);
        if (errors.hasErrors()) {
            final ErrorResponse errRes = newWaterConnectionValidator.populateErrors(errors);
            return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
        }
        logger.info("WaterConnectionRequest::" + waterConnectionRequest);
        final List<ErrorResponse> errorResponses = newWaterConnectionValidator
                .validateWaterConnectionRequest(waterConnectionRequest);
        Connection waterConn=waterConnectionService.findByApplicationNmber(acknumer);
        if(waterConn!=null){
        final ErrorResponse errorResponse = new ErrorResponse();
        final Error error = new Error();
        error.setDescription("");
        errorResponse.setError(error);
        }
        if (!errorResponses.isEmpty())
            return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
        
        waterConnectionRequest.getConnection().setId(waterConn.getId());

        // Call to service.
        final Connection connection = waterConnectionService.updateWaterConnection(
                applicationProperties.getUpdateNewConnectionTopicName(),
                "newconnection-update", waterConnectionRequest);
        return getSuccessResponse(connection, waterConnectionRequest.getRequestInfo());

    }
    @PostMapping(value = "/legacy/_create")
    @ResponseBody
    public ResponseEntity<?> legacyConnectionCreate(@RequestBody @Valid final WaterConnectionReq legacyConnectionRequest,
            final BindingResult errors) {
        if (errors.hasErrors()) {
            final ErrorResponse errRes = newWaterConnectionValidator.populateErrors(errors);
            return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
        }
        logger.info("Legacy WaterConnectionRequest::" + legacyConnectionRequest);
        final List<ErrorResponse> errorResponses = newWaterConnectionValidator
                .validateWaterConnectionRequest(legacyConnectionRequest);
        if (!errorResponses.isEmpty())
            return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);

        // Call to service.
        final Connection connection = waterConnectionService.createWaterConnection(
                applicationProperties.getCreateLegacyConnectionTopicName(),
                "legacyconnection-create", legacyConnectionRequest);
        return getSuccessResponse(connection, legacyConnectionRequest.getRequestInfo());

    }

    private ResponseEntity<?> getSuccessResponse(final Connection connection,
            final RequestInfo requestInfo) {
        final WaterConnectionRes waterConnectionRes = new WaterConnectionRes();
        ;
        final ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true);
        responseInfo.setStatus(HttpStatus.OK.toString());
        waterConnectionRes.setResponseInfo(responseInfo);
        waterConnectionRes.setConnection(connection);
        return new ResponseEntity<>(waterConnectionRes, HttpStatus.OK);

    }

}
