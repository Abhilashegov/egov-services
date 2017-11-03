package org.egov.works.estimate.persistence.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.common.persistence.repository.JdbcRepository;
import org.egov.works.estimate.persistence.entity.DetailedEstimateDeductionEntity;
import org.egov.works.estimate.web.contract.DetailedEstimateDeduction;
import org.egov.works.estimate.web.contract.DetailedEstimateDeductionSearchContract;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

@Service
public class DetailedEstimateDeductionJdbcRepository extends JdbcRepository {

	public static final String TABLE_NAME = "egw_estimate_deductions";

	public List<DetailedEstimateDeduction> search(
			DetailedEstimateDeductionSearchContract detailedEstimateDeductionSearchContract) {
		String searchQuery = "select :selectfields from :tablename :condition  :orderby   ";

		Map<String, Object> paramValues = new HashMap<>();
		StringBuffer params = new StringBuffer();

		if (detailedEstimateDeductionSearchContract.getSortBy() != null
				&& !detailedEstimateDeductionSearchContract.getSortBy().isEmpty()) {
			validateSortByOrder(detailedEstimateDeductionSearchContract.getSortBy());
			validateEntityFieldName(detailedEstimateDeductionSearchContract.getSortBy(), DetailedEstimateDeduction.class);
		}

		String orderBy = "order by id";
		if (detailedEstimateDeductionSearchContract.getSortBy() != null
				&& !detailedEstimateDeductionSearchContract.getSortBy().isEmpty()) {
			orderBy = "order by " + detailedEstimateDeductionSearchContract.getSortBy();
		}

		searchQuery = searchQuery.replace(":tablename", TABLE_NAME);

		searchQuery = searchQuery.replace(":selectfields", " * ");

		if (detailedEstimateDeductionSearchContract.getTenantId() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("tenantId =:tenantId");
			paramValues.put("tenantId", detailedEstimateDeductionSearchContract.getTenantId());
		}
		if (detailedEstimateDeductionSearchContract.getIds() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("id in(:ids) ");
			paramValues.put("ids", detailedEstimateDeductionSearchContract.getIds());
		}

		if (detailedEstimateDeductionSearchContract.getDetailedEstimateIds() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("detailedEstimate in(:detailedEstimateIds) ");
			paramValues.put("detailedEstimateIds", detailedEstimateDeductionSearchContract.getDetailedEstimateIds());
		}

		if (params.length() > 0) {

			searchQuery = searchQuery.replace(":condition", " where " + params.toString());

		} else

			searchQuery = searchQuery.replace(":condition", "");

		searchQuery = searchQuery.replace(":orderby", orderBy);

		BeanPropertyRowMapper row = new BeanPropertyRowMapper(DetailedEstimateDeductionEntity.class);

		List<DetailedEstimateDeductionEntity> detailedEstimateDeductionEntities = namedParameterJdbcTemplate
				.query(searchQuery.toString(), paramValues, row);

		List<DetailedEstimateDeduction> detailedEstimateDeductions = new ArrayList<>();

		for (DetailedEstimateDeductionEntity detailedEstimateDeductionEntity : detailedEstimateDeductionEntities) {
			detailedEstimateDeductions.add(detailedEstimateDeductionEntity.toDomain());
		}

		return detailedEstimateDeductions;
	}

}
