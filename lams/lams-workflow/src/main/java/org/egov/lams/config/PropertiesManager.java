package org.egov.lams.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Configuration
public class PropertiesManager {

	@Value("${egov.services.workflow_service.hostname}")
	private String workflowServiceHostName;

	@Value("${egov.services.workflow_service.startpath}")
	private String workflowServiceStartPath;

	@Value("${egov.services.workflow_service.updatepath}")
	private String workflowServiceUpdatePath;
	
	@Value("${egov.services.workflow_service.searchpath}")
	private String workflowServiceSearchPath;
	
	@Value("${egov.services.workflow_service.taskpath}")
	private String workflowServiceTaskPAth;
	
	@Value("${egov.services.workflow_service.hostname.create_businesskey}")
	private String workflowServiceCreateBusinessKey;
	
	@Value("${egov.services.workflow_service.hostname.renew_businesskey}")
	private String workflowServiceRenewBusinessKey;
	
	@Value("${egov.services.workflow_service.hostname.cancel_businesskey}")
	private String workflowServiceCancelBusinessKey;

	@Value("${egov.services.workflow_service.hostname.evict_businesskey}")
	private String workflowServiceEvictBusinessKey;
	
	@Value("${kafka.topics.start.workflow}")
	private String kafkaStartWorkflowTopic;
	
	@Value("${kafka.topics.update.workflow}")
	private String kafkaUpdateworkflowTopic;
	
	@Value("${kafka.topics.save.agreement}")
	private String kafkaSaveAgreementTopic;
	
	@Value("${kafka.topics.update.agreement}")
	private String kafkaUpdateAgreementTopic;
}
