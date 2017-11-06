package org.egov.works.estimate.persistence.repository;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.commons.exception.ValidationException;
import org.egov.works.commons.web.contract.IdRequest;
import org.egov.works.estimate.config.PropertiesManager;
import org.egov.works.estimate.config.WorksEstimateServiceConstants;
import org.egov.works.estimate.web.contract.IdGenerationRequest;
import org.egov.works.estimate.web.contract.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@Slf4j
public class IdGenerationRepository {

    @Autowired
    private RestTemplate restTemplate;

    private String url;

    @Autowired
    private PropertiesManager propertiesManager;

    public IdGenerationRepository(final RestTemplate restTemplate,@Value("${egov.idgen.hostname}") final String idGenHostName,
                                  @Value("${works.numbergeneration.uri}") final String url) {
        this.restTemplate = restTemplate;
        this.url = idGenHostName + url;
    }

    public String generateAbstractEstimateNumber(final String tenantId, final RequestInfo requestInfo) {
        Object response = null;
        IdGenerationRequest idGenerationRequest = new IdGenerationRequest();
        IdRequest idRequest = new IdRequest();
        idRequest.setTenantId(tenantId);
        idRequest.setFormat(propertiesManager.getWorksAbstractEstimateNumberFormat());
        idRequest.setIdName(propertiesManager.getWorksAbstractEstimateNumber());
        idGenerationRequest.setIdRequests(Arrays.asList(idRequest));
        idGenerationRequest.setRequestInfo(requestInfo);
        try {
            response = restTemplate.postForObject(url,
                    idGenerationRequest, Object.class);
        } catch (Exception e) {
            throw new ValidationException(null,
                    WorksEstimateServiceConstants.ABSTRACT_ESTIMATE_NUMBER_GENERATION_ERROR, WorksEstimateServiceConstants.ABSTRACT_ESTIMATE_NUMBER_GENERATION_ERROR);

        }
        log.info("Response from id gen service: " + response.toString());

        return JsonPath.read(response, "$.idResponses[0].id");
    }

    public String generateDetailedEstimateNumber(final String tenantId, final RequestInfo requestInfo) {
        Object response = null;
        IdGenerationRequest idGenerationRequest = new IdGenerationRequest();
        IdRequest idRequest = new IdRequest();
        idRequest.setTenantId(tenantId);
        idRequest.setFormat(propertiesManager.getWorksDetailedEstimateNumberFormat());
        idRequest.setIdName(propertiesManager.getWorksDetailedEstimateNumber());
        idGenerationRequest.setIdRequests(Arrays.asList(idRequest));
        idGenerationRequest.setRequestInfo(requestInfo);
        try {
            response = restTemplate.postForObject(url,
                    idGenerationRequest, Object.class);
        } catch (Exception e) {
            throw new ValidationException(null,
                    WorksEstimateServiceConstants.DETAILED_ESTIMATE_NUMBER_GENERATION_ERROR, WorksEstimateServiceConstants.DETAILED_ESTIMATE_NUMBER_GENERATION_ERROR);

        }
        log.info("Response from id gen service: " + response.toString());

        return JsonPath.read(response, "$.idResponses[0].id");
    }
    
    public String generateWorkIdentificationNumber(final String tenantId, final RequestInfo requestInfo) {
        Object response = null;
        IdGenerationRequest idGenerationRequest = new IdGenerationRequest();
        IdRequest idRequest = new IdRequest();
        idRequest.setTenantId(tenantId);
        idRequest.setFormat(propertiesManager.getWorksWorkIdentificationNumberFormat());
        idRequest.setIdName(propertiesManager.getWorksWorkIdentificationNumber());
        idGenerationRequest.setIdRequests(Arrays.asList(idRequest));
        idGenerationRequest.setRequestInfo(requestInfo);
        try {
            response = restTemplate.postForObject(url,
                    idGenerationRequest, Object.class);
        } catch (Exception e) {
            throw new ValidationException(null,
                    WorksEstimateServiceConstants.WORK_IDENTIFICATION_NUMBER_GENERATION_ERROR, WorksEstimateServiceConstants.WORK_IDENTIFICATION_NUMBER_GENERATION_ERROR);

        }
        log.info("Response from id gen service: " + response.toString());

        return JsonPath.read(response, "$.idResponses[0].id");
    }
}
