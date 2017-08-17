/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.wcms.repository.builder;

import java.util.List;
import java.util.Map;

import org.egov.wcms.web.contract.MeterCostGetRequest;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Component
public class MeterCostQueryBuilder {

	public static final String BASE_QUERY = "Select wmc.id as wmc_id,wmc.code as wmc_code,"
			+ "wmc.pipesizeid as wmc_pipesizeid,wmc.metermake as wmc_metermake,wmc.amount as wmc_amount,"
			+ "wmc.active as wmc_active,wmc.createdby as wmc_createdby,wmc.createddate as wmc_createddate,"
			+ "wmc.lastmodifiedby as wmc_lastmodifiedby,wmc.lastmodifieddate as wmc_lastmodifieddate,"
			+ "wmc.tenantid as wmc_tenantid from egwtr_metercost wmc";

	@SuppressWarnings("rawtypes")
	public String getQuery(MeterCostGetRequest meterCostGetRequest, Map<String, Object> preparedStatementValues) {
		StringBuilder selectQuery = new StringBuilder(BASE_QUERY);
		addWhereClause(selectQuery, meterCostGetRequest, preparedStatementValues);
		addOrderByClause(selectQuery, meterCostGetRequest);
		return selectQuery.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addWhereClause(StringBuilder selectQuery, MeterCostGetRequest meterCostGetRequest,
			Map<String, Object> preparedStatementValues) {
		if (meterCostGetRequest.getTenantId() == null)
			return;
		selectQuery.append(" WHERE");
		boolean isAppendAndClause = false;
		if (meterCostGetRequest.getTenantId() != null) {
			isAppendAndClause = true;
			selectQuery.append(" wmc.tenantId = :tenantId");
			preparedStatementValues.put("tenantId", meterCostGetRequest.getTenantId());
		}
		if (meterCostGetRequest.getCode() != null) {
			isAppendAndClause = addAndClauseIfRequired(isAppendAndClause, selectQuery);
			selectQuery.append(" wmc.code = :code");
			preparedStatementValues.put("code", meterCostGetRequest.getCode());
		}
		if (meterCostGetRequest.getName() != null) {
			isAppendAndClause = addAndClauseIfRequired(isAppendAndClause, selectQuery);
			selectQuery.append(" wmc.metermake = :metermake");
			preparedStatementValues.put("metermake", meterCostGetRequest.getName());
		}
		if (meterCostGetRequest.getActive() != null) {
			isAppendAndClause = addAndClauseIfRequired(isAppendAndClause, selectQuery);
			selectQuery.append(" wmc.active = :active");
			preparedStatementValues.put("active", meterCostGetRequest.getActive());
		}
		if (meterCostGetRequest.getPipeSizeId() != null) {
			isAppendAndClause = addAndClauseIfRequired(isAppendAndClause, selectQuery);
			selectQuery.append(" wmc.pipesizeid = :pipesizeid");
			preparedStatementValues.put("pipesizeid", meterCostGetRequest.getPipeSizeId());
		}
		if (meterCostGetRequest.getIds() != null) {
			isAppendAndClause = addAndClauseIfRequired(isAppendAndClause, selectQuery);
			selectQuery.append(" wmc.id IN (:ids)");
			preparedStatementValues.put("ids", meterCostGetRequest.getIds());
		}
	}

	private void addOrderByClause(StringBuilder selectQuery, MeterCostGetRequest meterCostGetRequest) {
		String sortBy = (meterCostGetRequest.getSortBy() == null ? "wmc.metermake"
				: "wmc." + meterCostGetRequest.getSortBy());
		String sortOrder = (meterCostGetRequest.getSortOrder() == null ? "ASC" : meterCostGetRequest.getSortOrder());
		selectQuery.append(" ORDER BY " + sortBy + " " + sortOrder);
	}

	private boolean addAndClauseIfRequired(boolean appendAndClauseFlag, StringBuilder queryString) {
		if (appendAndClauseFlag)
			queryString.append(" AND");
		return true;
	}

	private static String getIdQuery(List<Long> idList) {
		StringBuilder query = new StringBuilder();
		if (idList.size() >= 1) {
			query.append(idList.get(0).toString());
			for (int i = 1; i < idList.size(); i++) {
				query.append(", " + idList.get(i));
			}
		}
		return query.toString();
	}

	public String insertMeterCostQuery() {
		return "INSERT INTO egwtr_metercost(id,code,pipesizeid,metermake,amount,active,createdby,lastmodifiedby,createddate,"
				+ "lastmodifieddate,tenantid) values "
				+ "(:id,:code,:pipesizeid,:metermake,:amount"
				+ ",:active,:createdby,:lastmodifiedby,:createddate,:lastmodifieddate,:tenantid)";
	}

	public String updateMeterCostQuery() {
		return "Update egwtr_metercost set pipesizeid=:pipesizeid, metermake=:metermake, amount=:amount, active=:active,"
				+ " lastmodifiedby=:lastmodifiedby, lastmodifieddate=:lastmodifieddate where code = :code and tenantId = :tenantid";
	}

	public String selectMeterCostByNameAndTenantIdQuery() {
		StringBuilder selectQuery = new StringBuilder(BASE_QUERY);
		selectQuery.append(" where wmc.metermake = :name and wmc.tenantId = :tenantId");
		return selectQuery.toString();
	}

	public String selectMeterCostByNameTenantIdAndCodeNotInQuery() {
		StringBuilder selectQuery = new StringBuilder(BASE_QUERY);
		selectQuery.append(" where wmc.metermake = :name and wmc.tenantId = :tenantId and wmc.code != :code");
		return selectQuery.toString();
	}

    public String getPipeSiZeIdQuery() {
        return "Select id from egwtr_pipesize where sizeinmilimeter= :pipeSizeInMM and"
                + " tenantId= :tenantId";
    }

    public String getPipeSizeInMMQuery() {
        return "Select sizeinmilimeter from egwtr_pipesize where id= :id and tenantId= :tenantId";
    }

}
