package org.egov.workflow.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.egov.workflow.persistence.repository.dto.RequestInfoWrapper;
import org.egov.workflow.web.contract.Employee;
import org.egov.workflow.web.contract.EmployeeRes;
import org.egov.workflow.web.contract.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeRepository {

	private final RestTemplate restTemplate;
	private final String employeesByUserIdUrl;
	private final String employeesByPositionIdurl;

	private final String employeesByRoleNameurl;

	@Autowired
	public EmployeeRepository(final RestTemplate restTemplate,
			@Value("${egov.services.hr-employee.host}") final String hrEmployeeServiceHostname,
			@Value("${egov.services.eis.employee_by_userid}") final String employeesByUserIdUrl,
			@Value("${egov.services.eis.employee_by_position}") final String employeesByPositionIdurl,
			@Value("${egov.services.eis.employee_by_role}") final String employeesByRoleNameurl) {

		this.restTemplate = restTemplate;
		this.employeesByUserIdUrl = hrEmployeeServiceHostname + employeesByUserIdUrl;
		this.employeesByPositionIdurl = hrEmployeeServiceHostname + employeesByPositionIdurl;
		this.employeesByRoleNameurl = hrEmployeeServiceHostname + employeesByRoleNameurl;
	}

	public List<Employee> getByRoleName(final String roleName) {
		final EmployeeRes employeeRes = restTemplate.getForObject(employeesByRoleNameurl, EmployeeRes.class, roleName);
		return employeeRes.getEmployees();
	}

	public EmployeeRes getEmployeeForPosition(final Long posId, final LocalDateTime asOnDate) {
		return restTemplate.getForObject(employeesByPositionIdurl, EmployeeRes.class, posId, asOnDate);
	}

	public EmployeeRes getEmployeeForUserIdAndTenantId(final Long userId,final String tenantId) {
		RequestInfoWrapper wrapper = RequestInfoWrapper.builder().requestInfo(RequestInfo.builder().apiId("apiId").ver("ver").build()).build();
		final HttpEntity<RequestInfoWrapper> request = new HttpEntity<>(wrapper);
		return restTemplate.getForObject(employeesByUserIdUrl, EmployeeRes.class, userId,tenantId);
	}

	
}
