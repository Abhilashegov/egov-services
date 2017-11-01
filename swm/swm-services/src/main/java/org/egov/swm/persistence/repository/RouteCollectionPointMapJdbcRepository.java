package org.egov.swm.persistence.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.swm.domain.model.RouteCollectionPointMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RouteCollectionPointMapJdbcRepository {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Transactional
	public void delete(String route) {
		String delQuery = "delete from  egswm_routecollectionpointmap where route = '" + route + "'";
		jdbcTemplate.execute(delQuery);
	}

	public List<RouteCollectionPointMap> search(RouteCollectionPointMap searchRequest) {

		String searchQuery = "select * from egswm_routecollectionpointmap :condition ";

		Map<String, Object> paramValues = new HashMap<>();
		StringBuffer params = new StringBuffer();

		if (searchRequest.getTenantId() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("tenantId =:tenantId");
			paramValues.put("tenantId", searchRequest.getTenantId());
		}

		if (searchRequest.getRoute() != null && searchRequest.getRoute() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("route =:route");
			paramValues.put("route", searchRequest.getRoute());
		}

		if (searchRequest.getCollectionPoint() != null && searchRequest.getCollectionPoint() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("collectionPoint =:collectionPoint");
			paramValues.put("collectionPoint", searchRequest.getCollectionPoint());
		}

		if (params.length() > 0) {

			searchQuery = searchQuery.replace(":condition", " where " + params.toString());

		} else

			searchQuery = searchQuery.replace(":condition", "");

		BeanPropertyRowMapper row = new BeanPropertyRowMapper(RouteCollectionPointMap.class);

		return namedParameterJdbcTemplate.query(searchQuery.toString(), paramValues, row);

	}

}