package org.egov.swm.persistence.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.swm.domain.model.CollectionPoint;
import org.egov.swm.domain.model.CollectionPointSearch;
import org.egov.swm.domain.model.Pagination;
import org.egov.swm.domain.model.Route;
import org.egov.swm.domain.model.RouteCollectionPointMap;
import org.egov.swm.domain.model.RouteSearch;
import org.egov.swm.persistence.entity.RouteEntity;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RouteJdbcRepository {

	@Autowired
	public RouteCollectionPointMapJdbcRepository routeCollectionPointMapJdbcRepository;

	@Autowired
	public CollectionPointJdbcRepository collectionPointJdbcRepository;

	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public Pagination<Route> search(RouteSearch searchRequest) {

		String searchQuery = "select * from egswm_route :condition  :orderby ";

		Map<String, Object> paramValues = new HashMap<>();
		StringBuffer params = new StringBuffer();

		if (searchRequest.getSortBy() != null && !searchRequest.getSortBy().isEmpty()) {
			validateSortByOrder(searchRequest.getSortBy());
			validateEntityFieldName(searchRequest.getSortBy(), RouteSearch.class);
		}

		String orderBy = "order by name";
		if (searchRequest.getSortBy() != null && !searchRequest.getSortBy().isEmpty()) {
			orderBy = "order by " + searchRequest.getSortBy();
		}

		if (searchRequest.getTenantId() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("tenantId =:tenantId");
			paramValues.put("tenantId", searchRequest.getTenantId());
		}

		if (searchRequest.getCode() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("code =:code");
			paramValues.put("code", searchRequest.getCode());
		}

		if (searchRequest.getName() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("name =:name");
			paramValues.put("name", searchRequest.getName());
		}

		if (searchRequest.getCollectionTypeCode() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("collectionType =:collectionType");
			paramValues.put("collectionType", searchRequest.getCollectionTypeCode());
		}

		if (searchRequest.getEndingCollectionPointName() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("endingCollectionPoint =:endingCollectionPoint");
			paramValues.put("endingCollectionPoint", searchRequest.getEndingCollectionPointName());
		}

		if (searchRequest.getEndingDumpingGroundPointName() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("endingDumpingGroundPoint =:endingDumpingGroundPoint");
			paramValues.put("endingDumpingGroundPoint", searchRequest.getEndingDumpingGroundPointName());
		}

		if (searchRequest.getDistance() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("distance =:distance");
			paramValues.put("distance", searchRequest.getDistance());
		}

		if (searchRequest.getGarbageEstimate() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("gabageEstimate =:garbageEstimate");
			paramValues.put("garbageEstimate", searchRequest.getGarbageEstimate());
		}

		Pagination<Route> page = new Pagination<>();
		if (searchRequest.getOffset() != null) {
			page.setOffset(searchRequest.getOffset());
		}
		if (searchRequest.getPageSize() != null) {
			page.setPageSize(searchRequest.getPageSize());
		}

		if (params.length() > 0) {

			searchQuery = searchQuery.replace(":condition", " where " + params.toString());

		} else

			searchQuery = searchQuery.replace(":condition", "");

		searchQuery = searchQuery.replace(":orderby", orderBy);

		page = (Pagination<Route>) getPagination(searchQuery, page, paramValues);
		searchQuery = searchQuery + " :pagination";

		searchQuery = searchQuery.replace(":pagination",
				"limit " + page.getPageSize() + " offset " + page.getOffset() * page.getPageSize());

		BeanPropertyRowMapper row = new BeanPropertyRowMapper(RouteEntity.class);

		List<Route> routeList = new ArrayList<>();

		List<RouteEntity> routeEntities = namedParameterJdbcTemplate.query(searchQuery.toString(), paramValues, row);
		StringBuffer cpCodes = new StringBuffer();
		RouteCollectionPointMap sr;
		List<RouteCollectionPointMap> collectionPoints;
		Route route;
		CollectionPointSearch cps;
		Pagination<CollectionPoint> collectionPointList;
		for (RouteEntity routeEntity : routeEntities) {
			sr = RouteCollectionPointMap.builder().route(routeEntity.getCode()).build();
			collectionPoints = routeCollectionPointMapJdbcRepository.search(sr);
			if (collectionPoints != null)
				for (RouteCollectionPointMap map : collectionPoints) {
					if (cpCodes.length() > 0)
						cpCodes.append(",");
					cpCodes.append(map.getCollectionPoint());
				}
			cps = new CollectionPointSearch();
			cps.setCodes(cpCodes.toString());
			collectionPointList = collectionPointJdbcRepository.search(cps);
			route = routeEntity.toDomain();
			route.setCollectionPoints(collectionPointList.getPagedData());
			routeList.add(route);
		}

		page.setTotalResults(routeList.size());

		page.setPagedData(routeList);

		return page;
	}

	public Pagination<?> getPagination(String searchQuery, Pagination<?> page, Map<String, Object> paramValues) {
		String countQuery = "select count(*) from (" + searchQuery + ") as x";
		Long count = namedParameterJdbcTemplate.queryForObject(countQuery.toString(), paramValues, Long.class);
		Integer totalpages = (int) Math.ceil((double) count / page.getPageSize());
		page.setTotalPages(totalpages);
		page.setCurrentPage(page.getOffset());
		return page;
	}

	public void validateSortByOrder(final String sortBy) {
		List<String> sortByList = new ArrayList<String>();
		if (sortBy.contains(",")) {
			sortByList = Arrays.asList(sortBy.split(","));
		} else {
			sortByList = Arrays.asList(sortBy);
		}
		for (String s : sortByList) {
			if (s.contains(" ")
					&& (!s.toLowerCase().trim().endsWith("asc") && !s.toLowerCase().trim().endsWith("desc"))) {

				throw new CustomException(s.split(" ")[0],
						"Please send the proper sortBy order for the field " + s.split(" ")[0]);
			}
		}

	}

	public void validateEntityFieldName(String sortBy, final Class<?> object) {
		List<String> sortByList = new ArrayList<String>();
		if (sortBy.contains(",")) {
			sortByList = Arrays.asList(sortBy.split(","));
		} else {
			sortByList = Arrays.asList(sortBy);
		}
		Boolean isFieldExist = Boolean.FALSE;
		for (String s : sortByList) {
			for (int i = 0; i < object.getDeclaredFields().length; i++) {
				if (object.getDeclaredFields()[i].getName().equals(s.contains(" ") ? s.split(" ")[0] : s)) {
					isFieldExist = Boolean.TRUE;
					break;
				} else {
					isFieldExist = Boolean.FALSE;
				}
			}
			if (!isFieldExist) {
				throw new CustomException(s.contains(" ") ? s.split(" ")[0] : s, "Please send the proper Field Names ");

			}
		}

	}

}