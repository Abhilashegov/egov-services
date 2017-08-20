package org.egov.egf.voucher.persistence.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.egov.common.domain.model.Pagination;
import org.egov.common.persistence.repository.JdbcRepository;
import org.egov.egf.voucher.domain.model.Voucher;
import org.egov.egf.voucher.domain.model.VoucherSearch;
import org.egov.egf.voucher.persistence.entity.VoucherEntity;
import org.egov.egf.voucher.persistence.entity.VoucherSearchEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class VoucherJdbcRepository extends JdbcRepository {
	private static final Logger LOG = LoggerFactory.getLogger(VoucherJdbcRepository.class);

	static {
		LOG.debug("init voucher");
		init(VoucherEntity.class);
		LOG.debug("end init voucher");
	}

	public VoucherJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public VoucherEntity create(VoucherEntity entity) {
		entity.setId(UUID.randomUUID().toString().replace("-", ""));
		super.create(entity);
		return entity;
	}

	public VoucherEntity update(VoucherEntity entity) {
		super.update(entity);
		return entity;

	}

	public Pagination<Voucher> search(VoucherSearch domain) {
		VoucherSearchEntity voucherSearchEntity = new VoucherSearchEntity();
		voucherSearchEntity.toEntity(domain);

		String searchQuery = "select :selectfields from :tablename :condition  :orderby   ";

		Map<String, Object> paramValues = new HashMap<>();
		StringBuffer params = new StringBuffer();

		if (voucherSearchEntity.getSortBy() != null && !voucherSearchEntity.getSortBy().isEmpty()) {
			validateSortByOrder(voucherSearchEntity.getSortBy());
			validateEntityFieldName(voucherSearchEntity.getSortBy(), VoucherEntity.class);
		}

		String orderBy = "order by name";
		if (voucherSearchEntity.getSortBy() != null && !voucherSearchEntity.getSortBy().isEmpty()) {
			orderBy = "order by " + voucherSearchEntity.getSortBy();
		}

		searchQuery = searchQuery.replace(":tablename", VoucherEntity.TABLE_NAME);

		searchQuery = searchQuery.replace(":selectfields", " * ");

		// implement jdbc specfic search
		if (voucherSearchEntity.getId() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("id =:id");
			paramValues.put("id", voucherSearchEntity.getId());
		}
		if (voucherSearchEntity.getType() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("type =:type");
			paramValues.put("type", voucherSearchEntity.getType());
		}
		if (voucherSearchEntity.getName() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("name =:name");
			paramValues.put("name", voucherSearchEntity.getName());
		}
		if (voucherSearchEntity.getDescription() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("description =:description");
			paramValues.put("description", voucherSearchEntity.getDescription());
		}
		if (voucherSearchEntity.getVoucherNumber() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("voucherNumber =:voucherNumber");
			paramValues.put("voucherNumber", voucherSearchEntity.getVoucherNumber());
		}
		if (voucherSearchEntity.getVoucherDate() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("voucherDate =:voucherDate");
			paramValues.put("voucherDate", voucherSearchEntity.getVoucherDate());
		}
		if (voucherSearchEntity.getOriginalVoucherNumber() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("originalVoucherNumber =:originalVoucherNumber");
			paramValues.put("originalVoucherNumber", voucherSearchEntity.getOriginalVoucherNumber());
		}
		if (voucherSearchEntity.getRefVoucherNumber() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("refVoucherNumber =:refVoucherNumber");
			paramValues.put("refVoucherNumber", voucherSearchEntity.getRefVoucherNumber());
		}
		if (voucherSearchEntity.getModuleName() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("moduleName =:moduleName");
			paramValues.put("moduleName", voucherSearchEntity.getModuleName());
		}

		if (voucherSearchEntity.getModuleName() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("billNumber =:billNumber");
			paramValues.put("billNumber", voucherSearchEntity.getBillNumber());
		}

		if (voucherSearchEntity.getFundId() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("fundId =:fundId");
			paramValues.put("fundId", voucherSearchEntity.getFundId());
		}
		if (voucherSearchEntity.getStatusId() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("statusId =:statusId");
			paramValues.put("statusId", voucherSearchEntity.getStatusId());
		}

		if (voucherSearchEntity.getFunctionId() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("functionId =:functionId");
			paramValues.put("functionId", voucherSearchEntity.getFunctionId());
		}
		if (voucherSearchEntity.getFundsourceId() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("fundsourceId =:fundsourceId");
			paramValues.put("fundsourceId", voucherSearchEntity.getFundsourceId());
		}

		if (voucherSearchEntity.getSchemeId() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("schemeId =:schemeId");
			paramValues.put("schemeId", voucherSearchEntity.getSchemeId());
		}
		if (voucherSearchEntity.getSubSchemeId() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("subSchemeId =:subSchemeId");
			paramValues.put("subSchemeId", voucherSearchEntity.getSubSchemeId());
		}

		if (voucherSearchEntity.getFunctionaryId() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("functionaryId =:functionaryId");
			paramValues.put("functionaryId", voucherSearchEntity.getFunctionaryId());
		}
		if (voucherSearchEntity.getDivisionId() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("divisionId =:divisionId");
			paramValues.put("divisionId", voucherSearchEntity.getDivisionId());
		}

		if (voucherSearchEntity.getDepartmentId() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("departmentId =:departmentId");
			paramValues.put("departmentId", voucherSearchEntity.getDepartmentId());
		}
		if (voucherSearchEntity.getSourcePath() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("sourcePath =:sourcePath");
			paramValues.put("sourcePath", voucherSearchEntity.getSourcePath());
		}

		if (voucherSearchEntity.getBudgetCheckRequired() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("budgetCheckRequired =:budgetCheckRequired");
			paramValues.put("budgetCheckRequired", voucherSearchEntity.getBudgetCheckRequired());
		}

		if (voucherSearchEntity.getBudgetAppropriationNo() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("budgetAppropriationNo =:budgetAppropriationNo");
			paramValues.put("budgetAppropriationNo", voucherSearchEntity.getBudgetAppropriationNo());
		}
		if (voucherSearchEntity.getIds() != null) {
			if (params.length() > 0)
				params.append(" and ");
			params.append("ids =:ids");
			paramValues.put("ids", voucherSearchEntity.getIds());
		}

		if (voucherSearchEntity.getId() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("id =:id");
			paramValues.put("id", voucherSearchEntity.getId());
		}
		if (voucherSearchEntity.getName() != null) {
			if (params.length() > 0) {
				params.append(" and ");
			}
			params.append("name =:name");
			paramValues.put("name", voucherSearchEntity.getName());
		}

		Pagination<Voucher> page = new Pagination<>();
		if (voucherSearchEntity.getOffset() != null) {
			page.setOffset(voucherSearchEntity.getOffset());
		}
		if (voucherSearchEntity.getPageSize() != null) {
			page.setPageSize(voucherSearchEntity.getPageSize());
		}

		if (params.length() > 0) {

			searchQuery = searchQuery.replace(":condition", " where " + params.toString());

		} else

			searchQuery = searchQuery.replace(":condition", "");

		searchQuery = searchQuery.replace(":orderby", orderBy);

		page = (Pagination<Voucher>) getPagination(searchQuery, page, paramValues);
		searchQuery = searchQuery + " :pagination";

		searchQuery = searchQuery.replace(":pagination",
				"limit " + page.getPageSize() + " offset " + page.getOffset() * page.getPageSize());

		BeanPropertyRowMapper row = new BeanPropertyRowMapper(VoucherEntity.class);

		List<VoucherEntity> voucherEntities = namedParameterJdbcTemplate.query(searchQuery.toString(), paramValues,
				row);

		page.setTotalResults(voucherEntities.size());

		List<Voucher> vouchers = new ArrayList<>();
		for (VoucherEntity voucherEntity : voucherEntities) {

			vouchers.add(voucherEntity.toDomain());
		}
		page.setPagedData(vouchers);

		return page;
	}

	public VoucherEntity findById(VoucherEntity entity) {
		List<String> list = allIdentitiferFields.get(entity.getClass().getSimpleName());

		Map<String, Object> paramValues = new HashMap<>();

		for (String s : list) {
			paramValues.put(s, getValue(getField(entity, s), entity));
		}

		List<VoucherEntity> vouchers = namedParameterJdbcTemplate.query(
				getByIdQuery.get(entity.getClass().getSimpleName()).toString(), paramValues,
				new BeanPropertyRowMapper(VoucherEntity.class));
		if (vouchers.isEmpty()) {
			return null;
		} else {
			return vouchers.get(0);
		}

	}

}