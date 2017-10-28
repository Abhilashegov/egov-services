package org.egov.lcms.service;

import java.util.List;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.lcms.config.PropertiesManager;
import org.egov.lcms.factory.ResponseFactory;
import org.egov.lcms.models.Opinion;
import org.egov.lcms.models.OpinionRequest;
import org.egov.lcms.models.OpinionResponse;
import org.egov.lcms.models.OpinionSearchCriteria;
import org.egov.lcms.repository.OpinionRepository;
import org.egov.lcms.util.UniqueCodeGeneration;
import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Veswanth
 *
 */
@Service
public class OpinionService {

	@Autowired
	LogAwareKafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	PropertiesManager propertiesManager;

	@Autowired
	ResponseFactory responseFactory;

	@Autowired
	UniqueCodeGeneration uniqueCodeGeneration;

	@Autowired
	OpinionRepository opinionRepository;

	public OpinionResponse createOpinion(OpinionRequest opinionRequest) throws Exception {
		List<Opinion> opinions = opinionRequest.getOpinions();
		RequestInfo requestInfo = opinionRequest.getRequestInfo();
		for (Opinion opinion : opinions) {
			String code = uniqueCodeGeneration.getUniqueCode(opinion.getTenantId(), requestInfo,
					propertiesManager.getOpinionUlbFormat(), propertiesManager.getOpinionUlbName());
			opinion.setCode(code);
		}
		kafkaTemplate.send(propertiesManager.getOpinionCreateValidated(), opinionRequest);
		return getResponseInfo(opinionRequest);
	}

	public OpinionResponse updateOpinion(OpinionRequest opinionRequest) {
		kafkaTemplate.send(propertiesManager.getOpinionUpdateValidated(), opinionRequest);
		return getResponseInfo(opinionRequest);
	}

	public OpinionResponse searchOpinion(RequestInfo requestInfo, OpinionSearchCriteria opinionRequest) throws Exception {
		List<Opinion> opinions = opinionRepository.search(opinionRequest);
		ResponseInfo responseInfo = responseFactory.getResponseInfo(requestInfo,
				HttpStatus.CREATED);
		OpinionResponse response = new OpinionResponse(responseInfo,opinions);		
		return response;
	}

	private OpinionResponse getResponseInfo(OpinionRequest opinionRequest) {
		ResponseInfo responseInfo = responseFactory.getResponseInfo(opinionRequest.getRequestInfo(),
				HttpStatus.CREATED);
		OpinionResponse opinionResponse = new OpinionResponse();

		opinionResponse.setResponseInfo(responseInfo);
		opinionResponse.setOpinions(opinionRequest.getOpinions());
		return opinionResponse;
	}
}