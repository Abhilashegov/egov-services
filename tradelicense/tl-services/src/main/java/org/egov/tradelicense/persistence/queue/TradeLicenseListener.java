package org.egov.tradelicense.persistence.queue;

import java.util.Map;

import org.egov.tradelicense.common.config.PropertiesManager;
import org.egov.tradelicense.domain.model.TradeLicense;
import org.egov.tradelicense.domain.service.TradeLicenseService;
import org.egov.tradelicense.web.contract.TradeLicenseContract;
import org.egov.tradelicense.web.requests.TradeLicenseRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TradeLicenseListener {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	PropertiesManager propertiesManager;
	
	@Autowired
	TradeLicenseService tradeLicenseService;

	@KafkaListener(topics = { "#{propertiesManager.getCreateLegacyTradeValidated()}" })
	public void process(Map<String, Object> mastersMap) {

		if (mastersMap.get(propertiesManager.getCreateLegacyTradeValidated()) != null) {

			TradeLicenseRequest request = objectMapper.convertValue(
					mastersMap.get(propertiesManager.getCreateLegacyTradeValidated()), TradeLicenseRequest.class);

			ModelMapper mapper = new ModelMapper();
			for (TradeLicenseContract tradeLicenseContract : request.getLicenses()) {
				TradeLicense domain = mapper.map(tradeLicenseContract, TradeLicense.class);
				tradeLicenseService.save(domain);
			}
			mastersMap.clear();
		}

	}

}