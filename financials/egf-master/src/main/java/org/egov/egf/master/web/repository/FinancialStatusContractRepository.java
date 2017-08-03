package org.egov.egf.master.web.repository;

import org.egov.egf.master.web.contract.FinancialStatusContract;
import org.egov.egf.master.web.requests.FinancialStatusResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FinancialStatusContractRepository {

	private RestTemplate restTemplate;
	private String hostUrl;
	public static final String SEARCH_URL = "/egf-master/financialstatuses/_search?";

	public FinancialStatusContractRepository(@Value("${egf.master.host.url}") String hostUrl,
			RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.hostUrl = hostUrl;
	}

	public FinancialStatusContract findById(FinancialStatusContract financialStatusContract) {

		String url = String.format("%s%s", hostUrl, SEARCH_URL);
		StringBuffer content = new StringBuffer();
		if (financialStatusContract.getId() != null) {
			content.append("id=" + financialStatusContract.getId());
		}

		if (financialStatusContract.getTenantId() != null) {
			content.append("&tenantId=" + financialStatusContract.getTenantId());
		}
		url = url + content.toString();
		FinancialStatusResponse result = restTemplate.postForObject(url, null, FinancialStatusResponse.class);

		if (result.getFinancialStatuses() != null && result.getFinancialStatuses().size() == 1) {
			return result.getFinancialStatuses().get(0);
		} else {
			return null;
		}

	}
}