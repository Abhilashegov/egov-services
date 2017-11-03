package org.egov.works.services.domain.repository;

import java.util.Arrays;

import org.egov.works.commons.exception.ValidationException;
import org.egov.works.services.config.PropertiesManager;
import org.egov.works.services.config.WorksServiceConstants;
import org.egov.works.services.web.contract.IdGenerationRequest;
import org.egov.works.services.web.contract.IdRequest;
import org.egov.works.services.web.contract.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IdGenerationRepository {

	@Autowired
	private RestTemplate restTemplate;

	private String url;

	@Autowired
	private PropertiesManager propertiesManager;

	public IdGenerationRepository(final RestTemplate restTemplate,
			@Value("${egov.idgen.hostname}") final String idGenHostName,
			@Value("${works.numbergeneration.uri}") final String url) {
		this.restTemplate = restTemplate;
		this.url = idGenHostName + url;
	}

	public String generateAppropriationNumberNumber(final String tenantId, final RequestInfo requestInfo) {
		Object response = null;
		IdGenerationRequest idGenerationRequest = new IdGenerationRequest();
		IdRequest idRequest = new IdRequest();
		idRequest.setTenantId(tenantId);
		idRequest.setFormat(propertiesManager.getWorksAppropriationNumber());
		idRequest.setIdName(propertiesManager.getWorksAppropriationNumberFormat());
		idGenerationRequest.setIdRequests(Arrays.asList(idRequest));
		idGenerationRequest.setRequestInfo(requestInfo);
		try {
			response = restTemplate.postForObject(url, idGenerationRequest, Object.class);
		} catch (Exception e) {
			throw new ValidationException(null, WorksServiceConstants.APPROPRIATION_NUMBER_GENERATION_ERROR,
					WorksServiceConstants.APPROPRIATION_NUMBER_GENERATION_ERROR);

		}
		log.info("Response from id gen service: " + response.toString());

		return JsonPath.read(response, "$.idResponses[0].id");
	}

}
