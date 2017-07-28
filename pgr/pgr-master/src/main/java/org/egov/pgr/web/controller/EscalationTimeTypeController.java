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

package org.egov.pgr.web.controller;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ErrorField;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.pgr.config.ApplicationProperties;
import org.egov.pgr.model.EscalationTimeType;
import org.egov.pgr.service.EscalationTimeTypeService;
import org.egov.pgr.util.PgrMasterConstants;
import org.egov.pgr.web.contract.EscalationTimeTypeGetReq;
import org.egov.pgr.web.contract.EscalationTimeTypeReq;
import org.egov.pgr.web.contract.EscalationTimeTypeRes;
import org.egov.pgr.web.contract.RequestInfoWrapper;
import org.egov.pgr.web.contract.factory.ResponseInfoFactory;
import org.egov.pgr.web.errorhandlers.Error;
import org.egov.pgr.web.errorhandlers.ErrorHandler;
import org.egov.pgr.web.errorhandlers.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/escalation")
public class EscalationTimeTypeController {

    private static final Logger logger = LoggerFactory.getLogger(EscalationTimeTypeController.class);

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private EscalationTimeTypeService escalationTimeTypeService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ErrorHandler errHandler;

    @Autowired
    private EscalationTimeTypeService escalationSevice;


    @PostMapping(value = "/_create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody @Valid final EscalationTimeTypeReq escalationTimeTypeRequest,
                                    final BindingResult errors) {
        if (errors.hasErrors()) {
            final ErrorResponse errRes = populateErrors(errors);
            return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
        }
        logger.info("EscalationTimeType Create : Request ::" + escalationTimeTypeRequest);

        final List<ErrorResponse> errorResponses = validateServiceGroupRequest(escalationTimeTypeRequest);
        if (!errorResponses.isEmpty())
            return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);

        final EscalationTimeType escalationType = escalationTimeTypeService.createEscalationTimeType(applicationProperties.getCreateEscalationTimeTypeName(),
                applicationProperties.getCreateEscalationTimeTypeKey(), escalationTimeTypeRequest);

        final List<EscalationTimeType> escalationTimeTypes = new ArrayList<>();
        escalationTimeTypes.add(escalationType);
        return getSuccessResponse(escalationTimeTypes, escalationTimeTypeRequest.getRequestInfo());

    }

    @PostMapping(value = "/_update")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody @Valid final EscalationTimeTypeReq escalationTimeTypeRequest,
                                    final BindingResult errors) {
        if (errors.hasErrors() || escalationTimeTypeRequest.getEscalationTimeType().getId() == 0L) {
            final ErrorResponse errRes = populateErrors(errors);
            return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
        }
        logger.info("EscalationTimeType Update : Request ::" + escalationTimeTypeRequest);

        final List<ErrorResponse> errorResponses = validateServiceGroupRequest(escalationTimeTypeRequest);
        if (!errorResponses.isEmpty())
            return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);

        final EscalationTimeType escalationType = escalationTimeTypeService.updateEscalationTimeType(applicationProperties.getUpdateEscalationTimeTypeName(),
                applicationProperties.getUpdateEscalationTimeTypeKey(), escalationTimeTypeRequest);

        final List<EscalationTimeType> escalationTimeTypes = new ArrayList<>();
        escalationTimeTypes.add(escalationType);
        return getSuccessResponse(escalationTimeTypes, escalationTimeTypeRequest.getRequestInfo());

    }

    @PostMapping("_search")
    @ResponseBody
    public ResponseEntity<?> search(@ModelAttribute @Valid final EscalationTimeTypeGetReq escTimeTypeGetRequest,
                                    final BindingResult modelAttributeBindingResult, @RequestBody @Valid final RequestInfoWrapper requestInfoWrapper,
                                    final BindingResult requestBodyBindingResult) {
        final RequestInfo requestInfo = requestInfoWrapper.getRequestInfo();

        // validate input params
        if (modelAttributeBindingResult.hasErrors())
            return errHandler.getErrorResponseEntityForMissingParameters(modelAttributeBindingResult, requestInfo);

        // validate input params
        if (requestBodyBindingResult.hasErrors())
            return errHandler.getErrorResponseEntityForMissingRequestInfo(requestBodyBindingResult, requestInfo);

        // Call service
        List<EscalationTimeType> escalationTypeList = null;
        try {
            escalationTypeList = escalationSevice.getAllEscalationTimeTypes(escTimeTypeGetRequest);
        } catch (final Exception exception) {
            logger.error("Error while processing request " + escTimeTypeGetRequest, exception);
            return errHandler.getResponseEntityForUnexpectedErrors(requestInfo);
        }

        return getSuccessResponse(escalationTypeList, requestInfo);

    }

    private List<ErrorResponse> validateServiceGroupRequest(final EscalationTimeTypeReq escalationTimeTypeRequest) {
        final List<ErrorResponse> errorResponses = new ArrayList<>();
        final ErrorResponse errorResponse = new ErrorResponse();
        final Error error = getError(escalationTimeTypeRequest);
        errorResponse.setError(error);
        if (!errorResponse.getErrorFields().isEmpty())
            errorResponses.add(errorResponse);
        return errorResponses;
    }

    private Error getError(final EscalationTimeTypeReq escalationTimeTypeRequest) {
        final List<ErrorField> errorFields = getErrorFields(escalationTimeTypeRequest);
        return Error.builder().code(HttpStatus.BAD_REQUEST.value())
                .message(PgrMasterConstants.INVALID_ESCALATIONTIMETYPE_REQUEST_MESSAGE).errorFields(errorFields).build();
    }

    private List<ErrorField> getErrorFields(final EscalationTimeTypeReq escalationTimeTypeRequest) {
        final List<ErrorField> errorFields = new ArrayList<>();
        addServiceIdValidationErrors(escalationTimeTypeRequest, errorFields);
        addTeanantIdValidationErrors(escalationTimeTypeRequest, errorFields);
        return errorFields;
    }

    private void addServiceIdValidationErrors(final EscalationTimeTypeReq escalationTimeTypeRequest,
                                              final List<ErrorField> errorFields) {
        final EscalationTimeType ecalationTimeType = escalationTimeTypeRequest.getEscalationTimeType();
        if (ecalationTimeType.getGrievanceType().getId() == 0L) {
            final ErrorField errorField = ErrorField.builder().code(PgrMasterConstants.GRIEVANCETYPE_ID_MANDATORY_CODE)
                    .message(PgrMasterConstants.GRIEVANCETYPE_CODE_MANADATORY_ERROR_MESSAGE)
                    .field(PgrMasterConstants.GRIEVANCETYPE_CODE_MANADATORY_FIELD_NAME).build();
            errorFields.add(errorField);
        }
        if (ecalationTimeType.getNoOfHours() == 0L) {
            final ErrorField errorField = ErrorField.builder().code(PgrMasterConstants.NO_0F_HOURS_MANDATORY_CODE)
                    .message(PgrMasterConstants.NO_0F_HOURS_MANADATORY_ERROR_MESSAGE)
                    .field(PgrMasterConstants.NO_0F_HOURS_MANADATORY_FIELD_NAME).build();
            errorFields.add(errorField);
        }
    }

    private void addTeanantIdValidationErrors(final EscalationTimeTypeReq escalationTimeTypeRequest,
                                              final List<ErrorField> errorFields) {
        final EscalationTimeType ecalationTimeType = escalationTimeTypeRequest.getEscalationTimeType();
        if (ecalationTimeType.getTenantId() == null || ecalationTimeType.getTenantId().isEmpty()) {
            final ErrorField errorField = ErrorField.builder().code(PgrMasterConstants.TENANTID_MANDATORY_CODE)
                    .message(PgrMasterConstants.TENANTID_MANADATORY_ERROR_MESSAGE)
                    .field(PgrMasterConstants.TENANTID_MANADATORY_FIELD_NAME).build();
            errorFields.add(errorField);
        } else
            return;
    }

    private ErrorResponse populateErrors(final BindingResult errors) {
        final ErrorResponse errRes = new ErrorResponse();

        final Error error = new Error();
        error.setCode(1);
        error.setDescription("Error while binding request");
        if (errors.hasFieldErrors())
            for (final FieldError fieldError : errors.getFieldErrors())
                error.getFields().put(fieldError.getField(), fieldError.getRejectedValue());
        errRes.setError(error);
        return errRes;
    }

    private ResponseEntity<?> getSuccessResponse(final List<EscalationTimeType> escalationTimeTypeList, final RequestInfo requestInfo) {
        final EscalationTimeTypeRes escalationTimeTypeRes = new EscalationTimeTypeRes();
        final ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true);
        responseInfo.setStatus(HttpStatus.OK.toString());
        escalationTimeTypeRes.setResponseInfo(responseInfo);
        escalationTimeTypeRes.setEscalationTimeTypes(escalationTimeTypeList);
        return new ResponseEntity<>(escalationTimeTypeRes, HttpStatus.OK);

    }

}
