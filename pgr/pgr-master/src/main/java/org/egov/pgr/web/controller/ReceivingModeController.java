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
import org.egov.pgr.config.ApplicationProperties;
import org.egov.pgr.domain.service.ReceivingModeService;
import org.egov.pgr.web.contract.ReceivingMode;
import org.egov.pgr.web.contract.ReceivingModeRequest;
import org.egov.pgr.web.contract.ReceivingModeTypeRes;
import org.egov.pgr.web.errorhandlers.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/receivingmode")
public class ReceivingModeController {

    private static final Logger logger = LoggerFactory.getLogger(ReceivingModeController.class);

    @Autowired
    private ReceivingModeService receivingModeService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ErrorHandler errHandler;

    public ReceivingModeController(ReceivingModeService receivingModeService) {
        this.receivingModeService = receivingModeService;
    }

    @PostMapping(value = "/v1/_create")
    public ReceivingModeRequest create(@RequestBody ReceivingModeRequest receivingModeRequest,
                                       @Value("${kafka.topics.receivingmode.create.name}") String createReceivingModeName,
                                       @Value("${kafka.topics.receivingmode.create.key}") String createReceivingModeKey) {
        receivingModeService.sendMessage(createReceivingModeName, createReceivingModeKey, receivingModeRequest.getReceivingMode().toDomain());
        return receivingModeRequest;
    }

    @PostMapping(value = "/v1/_update")
    public ReceivingModeRequest update(@RequestBody @Valid final ReceivingModeRequest receivingModeRequest,
                                       @Value("${kafka.topics.receivingmode.update.name}") String updateReceivingModeName,
                                       @Value("${kafka.topics.receivingmode.update.key}") String updateReceivingModeKey) {
        receivingModeService.sendMessage(updateReceivingModeName, updateReceivingModeKey, receivingModeRequest.getReceivingMode().toDomain());
        return receivingModeRequest;
    }

    /*@PostMapping("/v1/_search")
    @ResponseBody
    public ResponseEntity<?> search(@ModelAttribute @Valid final ReceivingModeTypeGetReq modeTypeGetRequest,
                                    final BindingResult modelAttributeBindingResult,
                                    @RequestBody @Valid final RequestInfoWrapper requestInfoWrapper,
                                    final BindingResult requestBodyBindingResult) {
        final RequestInfo requestInfo = requestInfoWrapper.getRequestInfo();

        // validate input params
        if (modelAttributeBindingResult.hasErrors())
            return errHandler.getErrorResponseEntityForMissingParameters(modelAttributeBindingResult, requestInfo);

        // validate input params
        if (requestBodyBindingResult.hasErrors())
            return errHandler.getErrorResponseEntityForMissingRequestInfo(requestBodyBindingResult, requestInfo);

        // Call service
        List<ReceivingMode> centerTypeList = null;
        try {
            centerTypeList = receivingModeService.getAllReceivingModeTypes(modeTypeGetRequest);
        } catch (final Exception exception) {
            logger.error("Error while processing request " + modeTypeGetRequest, exception);
            return errHandler.getResponseEntityForUnexpectedErrors(requestInfo);
        }

        return getSuccessResponse(centerTypeList, requestInfo);

    }
*/
    private ResponseEntity<?> getSuccessResponse(final List<ReceivingMode> modeList,
                                                 final RequestInfo requestInfo) {
        final ReceivingModeTypeRes receivingModeResponse = new ReceivingModeTypeRes();
        receivingModeResponse.setModeTypes(modeList);
        receivingModeResponse.setResponseInfo(null);
        return new ResponseEntity<>(receivingModeResponse, HttpStatus.OK);

    }

   /* private List<ErrorResponse> validateReceivingModeRequest(final ReceivingModeRequest receivingModeRequest,
                                                             boolean flag) {
        final List<ErrorResponse> errorResponses = new ArrayList<>();
        final ErrorResponse errorResponse = new ErrorResponse();
        final Error error = getError(receivingModeRequest, flag);
        errorResponse.setError(error);
        if (!errorResponse.getErrorFields().isEmpty())
            errorResponses.add(errorResponse);
        return errorResponses;
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

    private List<ErrorField> getErrorFields(final ReceivingModeRequest categoryRequest, boolean flag) {
        final List<ErrorField> errorFields = new ArrayList<>();
        addReceivingModeNameAndCodeValidationErrors(categoryRequest, errorFields, flag);
        addTeanantIdValidationErrors(categoryRequest, errorFields);
        addChannelValidationErrors(categoryRequest, errorFields);
        return errorFields;
    }

    private void addReceivingModeNameAndCodeValidationErrors(final ReceivingModeRequest receivingModeRequest,
                                                             final List<ErrorField> errorFields, boolean flag) {
        final ReceivingMode receivingMode = receivingModeRequest.getReceivingMode();
        if (receivingMode.getCode() == null || receivingMode.getCode().isEmpty()) {
            final ErrorField errorField = ErrorField.builder()
                    .code(PgrMasterConstants.RECEIVINGMODE_CODE_MANDATORY_CODE)
                    .message(PgrMasterConstants.RECEIVINGMODE_CODE_MANADATORY_ERROR_MESSAGE)
                    .field(PgrMasterConstants.RECEIVINGMODE_CODE_MANADATORY_FIELD_NAME).build();
            errorFields.add(errorField);
        }
        if (receivingMode.getName() == null || receivingMode.getName().isEmpty()) {
            final ErrorField errorField = ErrorField.builder()
                    .code(PgrMasterConstants.RECEIVINGMODE_NAME_MANDATORY_CODE)
                    .message(PgrMasterConstants.RECEIVINGMODE_NAME_MANADATORY_ERROR_MESSAGE)
                    .field(PgrMasterConstants.RECEIVINGMODE_NAME_MANADATORY_FIELD_NAME).build();
            errorFields.add(errorField);
        } else if (flag && receivingModeService.checkReceivingModeTypeByCode(receivingMode.getCode(), receivingMode.getTenantId())) {
            final ErrorField errorField = ErrorField.builder().code(PgrMasterConstants.RECEIVINGMODE_CODE_UNIQUE_CODE)
                    .message(PgrMasterConstants.RECEIVINGMODE_UNQ_ERROR_MESSAGE)
                    .field(PgrMasterConstants.RECEIVINGMODE_CODE_UNQ_FIELD_NAME).build();
            errorFields.add(errorField);
        }

        if (errorFields.size() == 0) {
            if (receivingModeService.checkReceivingModeTypeByName(receivingMode.getCode(), receivingMode.getName(),
                    receivingMode.getTenantId())) {
                final ErrorField errorField = ErrorField.builder()
                        .code(PgrMasterConstants.RECEIVINGMODE_NAME_UNIQUE_CODE)
                        .message(PgrMasterConstants.RECEIVINGMODE_NAME_UNIQUE_ERROR_MESSAGE)
                        .field(PgrMasterConstants.RECEIVINGMODE_NAME_UNIQUE_FIELD_NAME).build();
                errorFields.add(errorField);
            }
        }
    }

    private void addChannelValidationErrors(final ReceivingModeRequest receivingModeRequest,
                                            final List<ErrorField> errorFields) {

        final ReceivingMode receivingMode = receivingModeRequest.getReceivingMode();

        if (receivingMode.getChannels().isEmpty() || receivingMode.getChannels().size() == 0) {

            final ErrorField errorField = ErrorField.builder()
                    .code(PgrMasterConstants.RECEIVINGMODE_CHANNEL_MANDATORY_CODE)
                    .message(PgrMasterConstants.RECEIVINGMODE_CHANNEL_MANADATORY_ERROR_MESSAGE)
                    .field(PgrMasterConstants.RECEIVINGMODE_CHANNEL_MANADATORY_FIELD_NAME).build();
            errorFields.add(errorField);

        } else
            return;
    }

    private void addTeanantIdValidationErrors(final ReceivingModeRequest receivingModeRequest,
                                              final List<ErrorField> errorFields) {
        final ReceivingMode receivingMode = receivingModeRequest.getReceivingMode();
        if (receivingMode.getTenantId() == null || receivingMode.getTenantId().isEmpty()) {
            final ErrorField errorField = ErrorField.builder().code(PgrMasterConstants.TENANTID_MANDATORY_CODE)
                    .message(PgrMasterConstants.TENANTID_MANADATORY_ERROR_MESSAGE)
                    .field(PgrMasterConstants.TENANTID_MANADATORY_FIELD_NAME).build();
            errorFields.add(errorField);
        } else if (receivingMode.getChannels().size() > 0) {

            for (String chanel : receivingMode.getChannels()) {

                ChannelType chaType = ChannelType.fromValue(chanel);

                if (chaType == null) {

                    final ErrorField errorField = ErrorField.builder()
                            .code(PgrMasterConstants.RECEIVINGMODE_CHANNEL_VALID_CODE)
                            .message(PgrMasterConstants.RECEIVINGMODE_CHANNEL_VALID_ERROR_MESSAGE)
                            .field(PgrMasterConstants.RECEIVINGMODE_CHANNEL_VALID__FIELD_NAME).build();
                    errorFields.add(errorField);

                }

            }

        }
        return;
    }

    private Error getError(final ReceivingModeRequest ModeTypeRequest, boolean flag) {
        ModeTypeRequest.getReceivingMode();
        final List<ErrorField> errorFields = getErrorFields(ModeTypeRequest, flag);
        return Error.builder().code(HttpStatus.BAD_REQUEST.value())
                .message(PgrMasterConstants.INVALID_RECEIVING_MODETYPE_REQUEST_MESSAGE).errorFields(errorFields)
                .build();

    }
*/
}
