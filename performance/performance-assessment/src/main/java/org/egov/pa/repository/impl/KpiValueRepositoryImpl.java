package org.egov.pa.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.egov.pa.model.KPI;
import org.egov.pa.model.KpiValue;
import org.egov.pa.model.KpiValueList;
import org.egov.pa.repository.KpiValueRepository;
import org.egov.pa.repository.builder.PerformanceAssessmentQueryBuilder;
import org.egov.pa.repository.rowmapper.PerformanceAssessmentRowMapper;
import org.egov.pa.repository.rowmapper.PerformanceAssessmentRowMapper.KPIValueRowMapper;
import org.egov.pa.web.contract.KPIValueRequest;
import org.egov.pa.web.contract.KPIValueSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component("kpiValueRepo")
@Slf4j
public class KpiValueRepositoryImpl implements KpiValueRepository{
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PerformanceAssessmentQueryBuilder queryBuilder; 
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Value("${kafka.topics.kpivalue.create.name}")
	private String createKpiValueTopic;
    
    @Value("${kafka.topics.kpivalue.update.name}")
	private String updateKpiValueTopic;

	@Override
	public void persistKpiValue(KPIValueRequest kpiValueRequest) {
		log.info("Request before pushing to Kafka Queue : " + kpiValueRequest);
    	kafkaTemplate.send(createKpiValueTopic, kpiValueRequest);	
	}

	@Override
	public void updateKpiValue(KPIValueRequest kpiValueRequest) {
		log.info("Request before pushing to Kafka Queue : " + kpiValueRequest);
    	kafkaTemplate.send(updateKpiValueTopic, kpiValueRequest);
	}

	@Override
	public List<KpiValueList> searchKpiValue(KPIValueSearchRequest kpiValueSearchReq) {
		final List<Object> preparedStatementValues = new ArrayList<>();
    	String query = queryBuilder.getValueSearchQuery(kpiValueSearchReq,preparedStatementValues);
    	KPIValueRowMapper mapper = new PerformanceAssessmentRowMapper().new KPIValueRowMapper(); 
    	List<KpiValueList> listOfValues = jdbcTemplate.query(query, preparedStatementValues.toArray(), mapper);
    	return listOfValues;
	}

	@Override
	public List<KPI> checkKpiExists(String kpiCode) {
		String query = PerformanceAssessmentQueryBuilder.fetchKpiByCode();
		final HashMap<String, Object> parametersMap = new HashMap<>();
		parametersMap.put("code", kpiCode);
		List<KPI> list = namedParameterJdbcTemplate.query(query, parametersMap, new BeanPropertyRowMapper<>(KPI.class));
		return list;
	}

	@Override
	public List<KPI> fetchTargetForKpi(String kpiCode, String finYear) {
		String query = PerformanceAssessmentQueryBuilder.fetchTargetForKpi(); 
		final HashMap<String, Object> parametersMap = new HashMap<>(); 
		parametersMap.put("kpiCode", kpiCode); 
		parametersMap.put("finYear", finYear);
		List<KPI> list = namedParameterJdbcTemplate.query(query, parametersMap, new BeanPropertyRowMapper<>(KPI.class));
		return list;
	}

	@Override
	public KpiValue checkKpiValueExistsForTenant(String kpiCode, String tenantId) {
		String query = PerformanceAssessmentQueryBuilder.fetchKpiValueForCodeAndTenant(); 
		final HashMap<String, Object> parametersMap = new HashMap<>(); 
		parametersMap.put("kpiCode", kpiCode); 
		parametersMap.put("tenantId", tenantId);
		KpiValue value = null ; 
		try { 
			value = namedParameterJdbcTemplate.queryForObject(query, parametersMap, new BeanPropertyRowMapper<>(KpiValue.class));			
		} catch(EmptyResultDataAccessException exec) { 
			log.info("Empty Data Set resulted - Code and Tenant ID does not have a record : " + exec);
		} catch(Exception e) { 
			log.error("Encountered an exception while fetching the data for Code and Tenant Id : " + e);
		}
		return value;
	}

	@Override
	public String searchPossibilityCheck(String tenantCount, String kpiCount, String finYearCount) {
		String query = PerformanceAssessmentQueryBuilder.queryForSearchConfig();
		final HashMap<String, Object> parametersMap = new HashMap<>(); 
		parametersMap.put("tenant", tenantCount); 
		parametersMap.put("kpi", kpiCount);
		parametersMap.put("finYear", finYearCount);
		String possibilityCheck = null;
		try { 
			possibilityCheck = namedParameterJdbcTemplate.queryForObject(query, parametersMap, String.class);			
		} catch(EmptyResultDataAccessException exec) { 
			log.info("Empty Data Set resulted - Code and Tenant ID does not have a record : " + exec);
		} catch(Exception e) { 
			log.error("Encountered an exception while fetching the data for Code and Tenant Id : " + e);
		}
		return possibilityCheck;
	}

}
