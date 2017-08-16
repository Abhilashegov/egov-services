
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

package org.egov.wcms.consumers;

import java.util.Map;

import org.egov.wcms.config.ApplicationProperties;
import org.egov.wcms.service.CategoryTypeService;
import org.egov.wcms.service.DocumentTypeApplicationTypeService;
import org.egov.wcms.service.DocumentTypeService;
import org.egov.wcms.service.DonationService;
import org.egov.wcms.service.MeterCostService;
import org.egov.wcms.service.MeterWaterRatesService;
import org.egov.wcms.service.PipeSizeService;
import org.egov.wcms.service.PropertyCategoryService;
import org.egov.wcms.service.PropertyTypePipeSizeService;
import org.egov.wcms.service.PropertyUsageTypeService;
import org.egov.wcms.service.SourceTypeService;
import org.egov.wcms.service.StorageReservoirService;
import org.egov.wcms.service.SupplyTypeService;
import org.egov.wcms.service.TreatmentPlantService;
import org.egov.wcms.web.contract.CategoryTypeRequest;
import org.egov.wcms.web.contract.DocumentTypeApplicationTypeReq;
import org.egov.wcms.web.contract.DocumentTypeReq;
import org.egov.wcms.web.contract.DonationRequest;
import org.egov.wcms.web.contract.MeterCostReq;
import org.egov.wcms.web.contract.MeterWaterRatesRequest;
import org.egov.wcms.web.contract.PipeSizeRequest;
import org.egov.wcms.web.contract.PropertyTypeCategoryTypeReq;
import org.egov.wcms.web.contract.PropertyTypePipeSizeRequest;
import org.egov.wcms.web.contract.PropertyTypeUsageTypeReq;
import org.egov.wcms.web.contract.SourceTypeRequest;
import org.egov.wcms.web.contract.StorageReservoirRequest;
import org.egov.wcms.web.contract.SupplyTypeRequest;
import org.egov.wcms.web.contract.TreatmentPlantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WaterMasterConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private CategoryTypeService categoryService;

    @Autowired
    private SupplyTypeService supplyTypeService;

    @Autowired
    private PropertyUsageTypeService propUsageTypeService;

    @Autowired
    private DonationService donationService;

    @Autowired
    private PipeSizeService pipeSizeService;

    @Autowired
    private PropertyCategoryService propertyCategoryService;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private DocumentTypeApplicationTypeService docTypeApplTypeService;

    @Autowired
    private MeterCostService meterCostService;

    @Autowired
    private PropertyTypePipeSizeService propertyPipeSizeService;

    @Autowired
    private SourceTypeService waterSourceTypeService;

    @Autowired
    private StorageReservoirService storageReservoirService;

    @Autowired
    private TreatmentPlantService treatmentPlantService;

    @Autowired
    private MeterWaterRatesService meterWaterRatesService;

    @KafkaListener(topics = { "${kafka.topics.usagetype.create.name}", "${kafka.topics.usagetype.update.name}",
            "${kafka.topics.category.create.name}", "${kafka.topics.category.update.name}",
            "${kafka.topics.pipesize.create.name}", "${kafka.topics.pipesize.update.name}",
            "${kafka.topics.propertyCategory.create.name}", "${kafka.topics.propertyCategory.update.name}",
            "${kafka.topics.donation.create.name}", "${kafka.topics.donation.update.name}",
            "${kafka.topics.documenttype.create.name}", "${kafka.topics.documenttype.update.name}",
            "${kafka.topics.documenttype.applicationtype.create.name}",
            "${kafka.topics.documenttype.applicationtype.update.name}", "${kafka.topics.propertyusage.create.name}",
            "${kafka.topics.propertyusage.update.name}", "${kafka.topics.propertypipesize.create.name}",
            "${kafka.topics.propertypipesize.update.name}", "${kafka.topics.sourcetype.create.name}",
            "${kafka.topics.sourcetype.update.name}", "${kafka.topics.supplytype.create.name}",
            "${kafka.topics.supplytype.update.name}", "${kafka.topics.storagereservoir.create.name}",
            "${kafka.topics.storagereservoir.update.name}", "${kafka.topics.treatmentplant.create.name}",
            "${kafka.topics.treatmentplant.update.name}", "${kafka.topics.meterwaterrates.create.name}",
            "${kafka.topics.meterwaterrates.update.name}", "${kafka.topics.metercost.create.name}",
            "${kafka.topics.metercost.update.name}" })

    public void processMessage(final Map<String, Object> consumerRecord,
            @Header(KafkaHeaders.RECEIVED_TOPIC) final String topic) {
        log.debug("key:" + topic + ":" + "value:" + consumerRecord);

        try {
            if (applicationProperties.getCreateCategoryTopicName().equals(topic))
                categoryService.create(objectMapper.convertValue(consumerRecord, CategoryTypeRequest.class));
            else if (applicationProperties.getUpdateCategoryTopicName().equals(topic))
                categoryService.update(objectMapper.convertValue(consumerRecord, CategoryTypeRequest.class));
            else if (applicationProperties.getCreatePipeSizetopicName().equals(topic))
                pipeSizeService.create(objectMapper.convertValue(consumerRecord, PipeSizeRequest.class));
            else if (applicationProperties.getUpdatePipeSizeTopicName().equals(topic))
                pipeSizeService.update(objectMapper.convertValue(consumerRecord, PipeSizeRequest.class));
            else if (applicationProperties.getCreatePropertyUsageTopicName().equals(topic))
                propUsageTypeService.create(objectMapper.convertValue(consumerRecord, PropertyTypeUsageTypeReq.class));
            else if (applicationProperties.getUpdatePropertyUsageTopicName().equals(topic))
                propUsageTypeService.update(objectMapper.convertValue(consumerRecord, PropertyTypeUsageTypeReq.class));
            else if (applicationProperties.getCreateDonationTopicName().equals(topic))
                donationService.create(objectMapper.convertValue(consumerRecord, DonationRequest.class));
            else if (applicationProperties.getUpdateDonationTopicName().equals(topic))
                donationService.update(objectMapper.convertValue(consumerRecord, DonationRequest.class));
            else if (applicationProperties.getCreateDocumentTypeTopicName().equals(topic))
                documentTypeService.create(objectMapper.convertValue(consumerRecord, DocumentTypeReq.class));
            else if (applicationProperties.getUpdateDocumentTypeTopicName().equals(topic))
                documentTypeService.update(objectMapper.convertValue(consumerRecord, DocumentTypeReq.class));
            else if (applicationProperties.getCreatePropertyCategoryTopicName().equals(topic))
                propertyCategoryService
                        .create(objectMapper.convertValue(consumerRecord, PropertyTypeCategoryTypeReq.class));
            else if (applicationProperties.getUpdatePropertyCategoryTopicName().equals(topic))
                propertyCategoryService
                        .update(objectMapper.convertValue(consumerRecord, PropertyTypeCategoryTypeReq.class));
            else if (applicationProperties.getCreatePropertyPipeSizeTopicName().equals(topic))
                propertyPipeSizeService
                        .create(objectMapper.convertValue(consumerRecord, PropertyTypePipeSizeRequest.class));
            else if (applicationProperties.getUpdatePropertyPipeSizeTopicName().equals(topic))
                propertyPipeSizeService
                        .update(objectMapper.convertValue(consumerRecord, PropertyTypePipeSizeRequest.class));
            else if (applicationProperties.getCreateDocumentTypeApplicationTypeTopicName().equals(topic))
                docTypeApplTypeService
                        .create(objectMapper.convertValue(consumerRecord, DocumentTypeApplicationTypeReq.class));
            else if (applicationProperties.getUpdateDocumentTypeApplicationTypeTopicName().equals(topic))
                docTypeApplTypeService
                        .update(objectMapper.convertValue(consumerRecord, DocumentTypeApplicationTypeReq.class));
            else if (applicationProperties.getCreateMeterCostTopicName().equals(topic)) {
                log.info("Consuming MeterCostCreate Request");
                meterCostService.createMeterCost(objectMapper.convertValue(consumerRecord, MeterCostReq.class));
            } else if (applicationProperties.getUpdateMeterCostTopicName().equals(topic)) {
                log.info("Consuming MeterCostUpdate Request");
                meterCostService.updateMeterCost(objectMapper.convertValue(consumerRecord, MeterCostReq.class));
            } else if (applicationProperties.getCreateMeterCostTopicName().equals(topic))
                meterCostService.create(objectMapper.convertValue(consumerRecord, MeterCostReq.class));
            else if (applicationProperties.getCreateSourceTypeTopicName().equals(topic))
                waterSourceTypeService.create(objectMapper.convertValue(consumerRecord, SourceTypeRequest.class));
            else if (applicationProperties.getUpdateSourceTypeTopicName().equals(topic))
                waterSourceTypeService.update(objectMapper.convertValue(consumerRecord, SourceTypeRequest.class));
            else if (applicationProperties.getCreateSupplyTypeTopicName().equals(topic))
                supplyTypeService.createSupplyType(objectMapper.convertValue(consumerRecord, SupplyTypeRequest.class));
            else if (applicationProperties.getUpdateSupplyTypeTopicName().equals(topic))
                supplyTypeService.updateSupplyType(objectMapper.convertValue(consumerRecord, SupplyTypeRequest.class));
            else if (applicationProperties.getCreateStorageReservoirTopicName().equals(topic))
                storageReservoirService
                        .create(objectMapper.convertValue(consumerRecord, StorageReservoirRequest.class));
            else if (applicationProperties.getUpdateStorageReservoirTopicName().equals(topic))
                storageReservoirService
                        .update(objectMapper.convertValue(consumerRecord, StorageReservoirRequest.class));
            else if (applicationProperties.getCreateTreatmentPlantTopicName().equals(topic))
                treatmentPlantService.create(objectMapper.convertValue(consumerRecord, TreatmentPlantRequest.class));
            else if (applicationProperties.getUpdateTreatmentPlantTopicName().equals(topic))
                treatmentPlantService.update(objectMapper.convertValue(consumerRecord, TreatmentPlantRequest.class));
            else if (applicationProperties.getCreateMeterWaterRatesTopicName().equals(topic))
                meterWaterRatesService.create(objectMapper.convertValue(consumerRecord, MeterWaterRatesRequest.class));
            else if (applicationProperties.getUpdateMeterWaterRatesTopicName().equals(topic))
                meterWaterRatesService.update(objectMapper.convertValue(consumerRecord, MeterWaterRatesRequest.class));
        } catch (final Exception exception) {
            log.debug("processMessage:" + exception);
            throw exception;
        }
    }
}