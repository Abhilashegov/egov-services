package org.egov.lams.consumers;

import java.io.IOException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.egov.lams.config.PropertiesManager;
import org.egov.lams.contract.AgreementDetailsEs;
import org.egov.lams.contract.AgreementIndex;
import org.egov.lams.contract.AgreementRequest;
import org.egov.lams.repository.ElasticSearchRepository;
import org.egov.lams.service.AgreementAdaptorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LamsIndexerConsumer {

	public static final Logger LOGGER = LoggerFactory.getLogger(LamsIndexerConsumer.class);

	@Autowired
	private ElasticSearchRepository elasticSearchRepository;

	@Autowired
	private AgreementAdaptorService agreementAdapter;

	@Autowired
	private PropertiesManager PropertiesManager;
	
	@KafkaListener(containerFactory = "kafkaListenerContainerFactory", topics = { "${kafka.topics.save.agreement}",
			"${kafka.topics.update.agreement}" })
	public void listen(ConsumerRecord<String, String> record) {
		LOGGER.info("key:" + record.key() + ":" + "value:" + record.value());
		
		ObjectMapper objectMapper = new ObjectMapper();
		AgreementRequest agreementRequest = null;
		try {
			LOGGER.info("SaveAgreementConsumer agreement-save-db :" + elasticSearchRepository);
			agreementRequest = objectMapper.readValue(record.value(), AgreementRequest.class);
		} catch (IOException e) {
			LOGGER.info(e.getMessage(), e);
		}
		
		AgreementIndex agreementIndex = agreementAdapter.indexOnCreate(agreementRequest);
		System.err.println(agreementIndex);

		if (record.topic().equals(PropertiesManager.getKafkaSaveAgreementTopic())) {
				elasticSearchRepository.saveAgreement(agreementIndex);
		}
		else if (record.topic().equals(PropertiesManager.getKafkaUpdateAgreementTopic())) {
			elasticSearchRepository.updateAgreement(agreementIndex);
		}

	}
}