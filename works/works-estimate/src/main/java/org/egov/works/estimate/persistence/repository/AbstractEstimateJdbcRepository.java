package org.egov.works.estimate.persistence.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.common.persistence.repository.JdbcRepository;
import org.egov.works.estimate.web.contract.AbstractEstimateSearchContract;
import org.egov.works.estimate.web.model.AbstractEstimate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

@Service
public class AbstractEstimateJdbcRepository extends JdbcRepository {
	
	public String getSequence(String sequenceName) {
        String seqQuery = "select nextval('" + sequenceName + "')";
        return String.valueOf(jdbcTemplate.queryForObject(seqQuery, Long.class) + 1);
    }

	public List<AbstractEstimate> search(AbstractEstimateSearchContract abstractEstimateSearchContract) {
		String searchQuery = "select :selectfields from :tablename :condition  :orderby   ";

		Map<String, Object> paramValues = new HashMap<>();
		StringBuffer params = new StringBuffer();

		if (abstractEstimateSearchContract.getSortBy() != null && !abstractEstimateSearchContract.getSortBy().isEmpty()) {
			validateSortByOrder(abstractEstimateSearchContract.getSortBy());
			validateEntityFieldName(abstractEstimateSearchContract.getSortBy(), AbstractEstimate.class);
		}

		String orderBy = "order by id";
		if (abstractEstimateSearchContract.getSortBy() != null && !abstractEstimateSearchContract.getSortBy().isEmpty()) {
			orderBy = "order by " + abstractEstimateSearchContract.getSortBy();
		}

		searchQuery = searchQuery.replace(":tablename", AbstractEstimate.TABLE_NAME);

		searchQuery = searchQuery.replace(":selectfields", " * ");

		// implement jdbc specfic search
		if (abstractEstimateSearchContract.getTenantId() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("tenantId =:tenantId");
			paramValues.put("tenantId", abstractEstimateSearchContract.getTenantId());
		}
		if (abstractEstimateSearchContract.getIds() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("id in(:ids) ");
			paramValues.put("ids", abstractEstimateSearchContract.getIds());
		}
		if (abstractEstimateSearchContract.getAdminSanctionNumbers() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("adminSanctionNumber in (:adminSanctionNumbers)");
			paramValues.put("adminSanctionNumbers", abstractEstimateSearchContract.getAdminSanctionNumbers());
		}
		if (abstractEstimateSearchContract.getEstimateNumbers() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("id in (select abstractEstimate from egw_abstractestimate_details where estimateNumber in (:estimateNumbers)");
			paramValues.put("estimateNumbers", abstractEstimateSearchContract.getEstimateNumbers());
		}
		if (abstractEstimateSearchContract.getDepartmentCode() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("department =:department");
			paramValues.put("department", abstractEstimateSearchContract.getDepartmentCode());
		}
		if (abstractEstimateSearchContract.getFundCode() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("fund =:fund");
			paramValues.put("fund", abstractEstimateSearchContract.getFundCode());
		}
		if (abstractEstimateSearchContract.getFunctionCode() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("function =:function");
			paramValues.put("function", abstractEstimateSearchContract.getFunctionCode());
		}
		if (abstractEstimateSearchContract.getBudgetHeadCode() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("budgetHead =:budgetHead");
			paramValues.put("budgetHead", abstractEstimateSearchContract.getBudgetHeadCode());
		}
		if (abstractEstimateSearchContract.getAdminSanctionFromDate() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("adminSanctionDate >=:adminSanctionFromDate");
			paramValues.put("adminSanctionFromDate", abstractEstimateSearchContract.getAdminSanctionFromDate());
		}
		if (abstractEstimateSearchContract.getAdminSanctionToDate() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("adminSanctionDate <:adminSanctionToDate");
			paramValues.put("adminSanctionToDate", abstractEstimateSearchContract.getAdminSanctionToDate());
		}
		if (abstractEstimateSearchContract.getSpillOverFlag() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("spillOverFlag =:spillOverFlag");
			paramValues.put("spillOverFlag", abstractEstimateSearchContract.getSpillOverFlag());
		}
		if (abstractEstimateSearchContract.getCreatedBy() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("createdBy =:createdBy");
			paramValues.put("createdBy", abstractEstimateSearchContract.getCreatedBy());
		}
		if (abstractEstimateSearchContract.getAbstractEstimateNumbers() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("abstractEstimateNumber in (:abstractEstimateNumbers)");
			paramValues.put("abstractEstimateNumbers", abstractEstimateSearchContract.getAbstractEstimateNumbers());
		}
		if (abstractEstimateSearchContract.getWorkIdentificationNumbers() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("id in (select abstractEstimate from egw_abstractestimate_details where projectCode in (select id from egw_projectcode where code in (:workIdentificationNumbers))");
			paramValues.put("workIdentificationNumbers", abstractEstimateSearchContract.getWorkIdentificationNumbers());
		}

		if (params.length() > 0) {

			searchQuery = searchQuery.replace(":condition", " where " + params.toString());

		} else

			searchQuery = searchQuery.replace(":condition", "");

		searchQuery = searchQuery.replace(":orderby", orderBy);

		BeanPropertyRowMapper row = new BeanPropertyRowMapper(AbstractEstimate.class);

		return namedParameterJdbcTemplate.query(searchQuery.toString(), paramValues, row);
	}

}
