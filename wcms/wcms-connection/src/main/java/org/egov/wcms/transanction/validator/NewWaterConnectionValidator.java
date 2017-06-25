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
package org.egov.wcms.transanction.validator;

import java.util.ArrayList;
import java.util.List;

import org.egov.common.contract.response.ErrorField;
import org.egov.wcms.transanction.model.DocumentOwner;
import org.egov.wcms.transanction.util.WcmsTranasanctionConstants;
import org.egov.wcms.transanction.web.contract.DonationResponseInfo;
import org.egov.wcms.transanction.web.contract.WaterConnectionReq;
import org.egov.wcms.transanction.web.errorhandlers.Error;
import org.egov.wcms.transanction.web.errorhandlers.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class NewWaterConnectionValidator {

    @Autowired
    private ConnectionMasterValidator connectionMasterValidator;

    public static final Logger LOGGER = LoggerFactory.getLogger(NewWaterConnectionValidator.class);

    public ErrorResponse populateErrors(final BindingResult errors) {
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

    public List<ErrorResponse> validateWaterConnectionRequest(final WaterConnectionReq waterConnectionRequest) {
        final List<ErrorResponse> errorResponses = new ArrayList<>();
        final ErrorResponse errorResponse = new ErrorResponse();
        final Error error = getError(waterConnectionRequest);
        errorResponse.setError(error);
        if (!errorResponse.getErrorFields().isEmpty())
            errorResponses.add(errorResponse);

        return errorResponses;
    }

    public Error getError(final WaterConnectionReq waterConnectionRequest) {
        final List<ErrorField> errorFields = new ArrayList<>();
        if (waterConnectionRequest.getConnection().getBillingType() == null
                || waterConnectionRequest.getConnection().getBillingType().isEmpty()) {
            final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.BILLING_TYPE_INVALID_CODE)
                    .message(WcmsTranasanctionConstants.BILLING_TYPE_INVALID_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.BILLING_TYPE_INVALID_FIELD_NAME).build();
            errorFields.add(errorField);
        } else if (waterConnectionRequest.getConnection().getCategoryType() == null) {
            final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.CATEGORY_NAME_MANDATORY_CODE)
                    .message(WcmsTranasanctionConstants.CATEGORY_NAME_MANADATORY_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.CATEGORY_NAME_MANADATORY_FIELD_NAME).build();
            errorFields.add(errorField);
        } 
        else if (waterConnectionRequest.getConnection().getApplicationType() == null) {
            final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.APPLICATIONTYPE_MANDATORY_CODE)
                    .message(WcmsTranasanctionConstants.APPLICATIONTYPE_INVALID_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.APPLICATIONTYPE_INVALID_FIELD_NAME).build();
            errorFields.add(errorField);
        }
        else if (waterConnectionRequest.getConnection().getConnectionType() == null
                || waterConnectionRequest.getConnection().getConnectionType().isEmpty()) {
            final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.CONNECTION_TYPE_INVALID_CODE)
                    .message(WcmsTranasanctionConstants.CONNECTION_INVALID_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.CONNECTION_TYPE_INVALID_FIELD_NAME).build();
            errorFields.add(errorField);
        } else if (waterConnectionRequest.getConnection().getHscPipeSizeType() == null) {
            final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.PIPESIZE_SIZEINMM_MANDATORY_CODE)
                    .message(WcmsTranasanctionConstants.PIPESIZE_SIZEINMM__MANADATORY_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.PIPESIZE_SIZEINMM__MANADATORY_FIELD_NAME).build();
            errorFields.add(errorField);
        } else if (waterConnectionRequest.getConnection().getProperty() == null
                || waterConnectionRequest.getConnection().getProperty().getPropertyType().isEmpty()) {
            final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.PROPERTY_TYPE_MANDATORY_CODE)
                    .message(WcmsTranasanctionConstants.PROPERTY_TYPE_MANDATORY_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.PROPERTY_TYPE_MANDATORY_FIELD_NAME).build();
            errorFields.add(errorField);
        } else if (waterConnectionRequest.getConnection().getProperty() == null
                || waterConnectionRequest.getConnection().getProperty().getUsageType().isEmpty()) {
            final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.USAGETYPE_NAME_MANDATORY_CODE)
                    .message(WcmsTranasanctionConstants.USAGETYPE_NAME_MANADATORY_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.USAGETYPE_NAME_MANADATORY_FIELD_NAME).build();
            errorFields.add(errorField);
        } else if (waterConnectionRequest.getConnection().getSourceType() == null) {
            final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.SOURCE_TYPE_INVALID_CODE)
                    .message(WcmsTranasanctionConstants.SOURCE_TYPE_INVALID_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.SOURCE_TYPE_INVALID_FIELD_NAME).build();
            errorFields.add(errorField);
        } else if (waterConnectionRequest.getConnection().getSumpCapacity() == 0L) {
            final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.SUMP_CAPACITY_INVALID_CODE)
                    .message(WcmsTranasanctionConstants.SUMP_CAPACITY_INVALID_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.SUMP_CAPACITY_INVALID_FIELD_NAME).build();
            errorFields.add(errorField);
        } else if (waterConnectionRequest.getConnection().getSupplyType() == null) {
            final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.SUPPLY_TYPE_INVALID_CODE)
                    .message(WcmsTranasanctionConstants.SUPPLY_TYPE_INVALID_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.SUPPLY_TYPE_INVALID_FIELD_NAME).build();
            errorFields.add(errorField);
        }

        if (waterConnectionRequest.getConnection().getLegacyConsumerNumber() == null)
            if (waterConnectionRequest.getConnection().getDocuments() == null
                    || waterConnectionRequest.getConnection().getDocuments().isEmpty()) {
                final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.DOCUMENTS_INVALID_CODE)
                        .message(WcmsTranasanctionConstants.DOCUMENTS_INVALID_ERROR_MESSAGE)
                        .field(WcmsTranasanctionConstants.DOCUMENTS_INVALID_FIELD_NAME).build();
                errorFields.add(errorField);

            } else
                for (final DocumentOwner document : waterConnectionRequest.getConnection().getDocuments())
                    if (null == document.getDocument()) {
                        final ErrorField errorField = ErrorField.builder().code(WcmsTranasanctionConstants.DOCUMENTS_INVALID_CODE)
                                .message(WcmsTranasanctionConstants.DOCUMENTS_INVALID_ERROR_MESSAGE)
                                .field(WcmsTranasanctionConstants.DOCUMENTS_INVALID_FIELD_NAME).build();
                        errorFields.add(errorField);
                    } else if (null == document.getDocument()) {
                        // need to check validation prop
                        final ErrorField errorField = ErrorField.builder()
                                .code(WcmsTranasanctionConstants.DOCUMENTS_INVALID_CODE)
                                .message(WcmsTranasanctionConstants.DOCUMENTS_INVALID_ERROR_MESSAGE)
                                .field(WcmsTranasanctionConstants.DOCUMENTS_INVALID_FIELD_NAME).build();
                        errorFields.add(errorField);
                    }

        if (errorFields.size() > 0)
            return Error.builder().code(HttpStatus.BAD_REQUEST.value())
                    .message(WcmsTranasanctionConstants.INVALID_REQUEST_MESSAGE)
                    .errorFields(errorFields).build();

        final List<ErrorField> masterfielderrorList = connectionMasterValidator.getMasterValidation(waterConnectionRequest);
        errorFields.addAll(masterfielderrorList);

        final List<ErrorField> errorFieldList = validateNewConnectionBusinessRules(waterConnectionRequest);
        errorFields.addAll(errorFieldList);

        return Error.builder().code(HttpStatus.BAD_REQUEST.value()).message(WcmsTranasanctionConstants.INVALID_REQUEST_MESSAGE)
                .errorFields(errorFields).build();
    }

    public List<ErrorField> validateNewConnectionBusinessRules(final WaterConnectionReq waterConnectionRequest) {
        boolean isRequestValid = false;
        final List<ErrorField> errorFields = new ArrayList<>();

        // isRequestValid = validatePropertyUsageMapping(waterConnectionRequest);
        /*
         * if (!isRequestValid) { final ErrorField errorField = ErrorField.builder()
         * .code(WcmsTranasanctionConstants.PROPERTY_USAGE_INVALID_CODE)
         * .message(WcmsTranasanctionConstants.PROPERTY_USAGE_INVALID_ERROR_MESSAGE)
         * .field(WcmsTranasanctionConstants.PROPERTY_USAGE_INVALID_FIELD_NAME) .build(); errorFields.add(errorField); }
         * //isRequestValid = validatePropertyCategoryMapping(waterConnectionRequest); if (!isRequestValid) { final ErrorField
         * errorField = ErrorField.builder() .code(WcmsTranasanctionConstants.PROPERTY_CATEGORY_INVALID_CODE)
         * .message(WcmsTranasanctionConstants.PROPERTY_CATEGORY_INVALID_ERROR_MESSAGE)
         * .field(WcmsTranasanctionConstants.PROPERTY_CATEGORY_INVALID_FIELD_NAME) .build(); errorFields.add(errorField); } if
         * (waterConnectionRequest.getConnection().getLegacyConsumerNumber() == null) { // isRequestValid =
         * validateDocumentApplicationType(waterConnectionRequest); if (!isRequestValid) { final ErrorField errorField =
         * ErrorField.builder() .code(WcmsTranasanctionConstants.DOCUMENT_APPLICATION_INVALID_CODE)
         * .message(WcmsTranasanctionConstants.DOCUMENT_APPLICATION_INVALID_ERROR_MESSAGE)
         * .field(WcmsTranasanctionConstants.DOCUMENT_APPLICATION_INVALID_FIELD_NAME) .build(); errorFields.add(errorField); } }
         */

        isRequestValid = validateStaticFields(waterConnectionRequest);
        if (!isRequestValid) {
            final ErrorField errorField = ErrorField.builder()
                    .code(WcmsTranasanctionConstants.STATIC_INVALID_CODE)
                    .message(WcmsTranasanctionConstants.STATIC_INVALID_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.STATIC_INVALID_FIELD_NAME)
                    .build();
            errorFields.add(errorField);
        }

        if (waterConnectionRequest.getConnection().getCategoryType().equals("BPL"))
            if (waterConnectionRequest.getConnection().getBplCardHolderName() == null ||
                    waterConnectionRequest.getConnection().getBplCardHolderName().isEmpty()) {
                final ErrorField errorField = ErrorField.builder()
                        .code(WcmsTranasanctionConstants.BPL_INVALID_CODE)
                        .message(WcmsTranasanctionConstants.BPL_INVALID_ERROR_MESSAGE)
                        .field(WcmsTranasanctionConstants.BPL_INVALID_FIELD_NAME)
                        .build();
                errorFields.add(errorField);
            }

        final DonationResponseInfo donationresInfo = connectionMasterValidator.validateDonationAmount(waterConnectionRequest);
        if (donationresInfo == null) {
            final ErrorField errorField = ErrorField.builder()
                    .code(WcmsTranasanctionConstants.DONATION_INVALID_CODE)
                    .message(WcmsTranasanctionConstants.DONATION_INVALID_ERROR_MESSAGE)
                    .field(WcmsTranasanctionConstants.DONATION_INVALID_FIELD_NAME)
                    .build();
            errorFields.add(errorField);
        }

        return errorFields;
    }

    // TODO:Donation master validation need to do

    /*
     * @SuppressWarnings("rawtypes") private boolean validateDonationAmount(final WaterConnectionReq waterConnectionRequest) {
     * final List<Donation> donationList = donationService.getDonationList(prepareDonationGetRequest(waterConnectionRequest));
     * final Iterator itr = donationList.iterator(); Donation donation = null; while (itr.hasNext()) { donation = (Donation)
     * itr.next(); if (null == donation.getDonationAmount() || donation.getDonationAmount().isEmpty()) return false; }
     * waterConnectionRequest.getConnection().setDonationCharge("400"); return true; }
     */

    // validatePropertyUsageMapping master validation need to do

    /*
     * private boolean validatePropertyUsageMapping(final WaterConnectionReq waterConnectionRequest) { LOGGER.info(
     * "Validating Property - Usage Mapping"); boolean result = false; final PropertyTypeUsageTypeReq propUsageTypeRequest = new
     * PropertyTypeUsageTypeReq(); final PropertyTypeUsageType propertyTypeUsageType = new PropertyTypeUsageType();
     * propertyTypeUsageType.setPropertyType(waterConnectionRequest.getConnection().getProperty().getPropertyType());
     * propertyTypeUsageType.setUsageType(waterConnectionRequest.getConnection().getProperty().getUsageType());
     * propertyTypeUsageType.setTenantId(waterConnectionRequest.getConnection().getTenantId());
     * propUsageTypeRequest.setPropertyTypeUsageType(propertyTypeUsageType); try { result =
     * propertyUsageTypeService.checkPropertyUsageTypeExists(propUsageTypeRequest); } catch (final Exception e) { LOGGER.info(
     * "Validating Property - Usage Mapping FAILED!"); } return result; }
     */
    /*
     * private boolean validatePropertyCategoryMapping(final WaterConnectionReq waterConnectionRequest) { LOGGER.info(
     * "Validating Property - Category Mapping"); boolean result = false; try { result =
     * propertyCategoryService.checkIfMappingExists( waterConnectionRequest.getConnection().getProperty().getPropertyType(),
     * waterConnectionRequest.getConnection().getCategoryType().getName(), waterConnectionRequest.getConnection().getTenantId());
     * } catch (final Exception e) { LOGGER.info("Validating Property - Category Mapping FAILED!"); } return result; }
     */
    /*
     * private boolean validateDocumentApplicationType(final WaterConnectionReq waterConnectionRequest) { LOGGER.info(
     * "Validating Document - Application Mapping"); boolean isDocumentValid = true; int countOfDocs = 0; final List<Long>
     * mandatoryDocs = documentTypeService.getAllMandatoryDocs(ApplicationType.NEWCONNECTION.toString()); if
     * (waterConnectionRequest.getConnection().getLegacyConsumerNumber() == null) for (final DocumentOwner documentOwner :
     * waterConnectionRequest.getConnection().getDocuments()) if (mandatoryDocs.contains(documentOwner.getDocument().getTypeId()))
     * { countOfDocs++; if (documentOwner.getFileStoreId() == null || documentOwner.getFileStoreId().isEmpty()) { LOGGER.info(
     * "File Upload FAILED for the document: " + documentOwner.toString()); isDocumentValid = false; return isDocumentValid; } }
     * if (countOfDocs != mandatoryDocs.size()) isDocumentValid = false; return isDocumentValid; }
     */

    private boolean validateStaticFields(final WaterConnectionReq waterConnectionRequest) {
        LOGGER.info("Validating ConnectionType, BillingType, SupplyType, SourceType");

        boolean isRequestValid = false;

        if (!(waterConnectionRequest.getConnection().getConnectionType().equals("TEMPORARY") ||
                waterConnectionRequest.getConnection().getConnectionType().equals("PERMANENT"))) {
            LOGGER.info("ConnectionType is INVALID");
            return isRequestValid;
        } else if (!(waterConnectionRequest.getConnection().getBillingType().equals("METERED") ||
                waterConnectionRequest.getConnection().getBillingType().equals("NON-METERED"))) {
            LOGGER.info("BillingType is INVALID");
            return isRequestValid;
        }

        isRequestValid = true;
        return isRequestValid;

        // Refactoring needed to reduce the if-else ladder and other optimization.
    }

    /*
     * private DonationGetRequest prepareDonationGetRequest(final WaterConnectionReq waterConnectionRequest) { // Receive new
     * connection request as a parameter for this method // Then using the values in the New Connection Request, prepare a
     * Donation Get Request Object // Pass this Object to Get Method of Donation Service final DonationGetRequest
     * donationGetRequest = new DonationGetRequest();
     * donationGetRequest.setPropertyType(waterConnectionRequest.getConnection().getProperty().getPropertyType());
     * donationGetRequest.setUsageType(waterConnectionRequest.getConnection().getProperty()..());
     * donationGetRequest.setCategoryType(waterConnectionRequest.getConnection().getCategoryType().getName());
     * donationGetRequest.setMaxHSCPipeSize(waterConnectionRequest.getConnection().getHscPipeSizeType().getSizeInInch());
     * donationGetRequest.setMinHSCPipeSize(waterConnectionRequest.getConnection().getHscPipeSizeType().getSizeInInch());
     * donationGetRequest.setTenantId(waterConnectionRequest.getConnection().getTenantId()); return donationGetRequest; }
     */

}
