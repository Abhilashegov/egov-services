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
package org.egov.pgr.repository.builder;

import java.util.List;

import org.egov.pgr.web.contract.ServiceGetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceTypeQueryBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ServiceTypeQueryBuilder.class);

    private static final String BASE_QUERY = "SELECT * FROM egpgr_grievancetype";

    @SuppressWarnings("rawtypes")
    public String getQuery(final ServiceGetRequest categoryGetRequest, final List preparedStatementValues) {
        final StringBuilder selectQuery = new StringBuilder(BASE_QUERY);
        addWhereClause(selectQuery, preparedStatementValues, categoryGetRequest);
        addOrderByClause(selectQuery, categoryGetRequest);
        addPagingClause(selectQuery, preparedStatementValues, categoryGetRequest);
        logger.debug("Query : " + selectQuery);
        return selectQuery.toString();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void addWhereClause(final StringBuilder selectQuery, final List preparedStatementValues,
            final ServiceGetRequest categoryGetRequest) {

        if (categoryGetRequest.getId() == null && categoryGetRequest.getName() == null && categoryGetRequest.getActive() == null
                && categoryGetRequest.getTenantId() == null)
            return;

        selectQuery.append(" WHERE");
        boolean isAppendAndClause = false;

        if (categoryGetRequest.getTenantId() != null) {
            isAppendAndClause = true;
            selectQuery.append(" category.tenantId = ?");
            preparedStatementValues.add(categoryGetRequest.getTenantId());
        }

        if (categoryGetRequest.getId() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause, selectQuery);
            selectQuery.append(" category.id IN " + getIdQuery(categoryGetRequest.getId()));
        }

        if (categoryGetRequest.getName() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause, selectQuery);
            selectQuery.append(" category.name = ?");
            preparedStatementValues.add(categoryGetRequest.getName());
        }

        if (categoryGetRequest.getCode() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause, selectQuery);
            selectQuery.append(" category.code = ?");
            preparedStatementValues.add(categoryGetRequest.getCode());
        }

        if (categoryGetRequest.getActive() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause, selectQuery);
            selectQuery.append(" category.active = ?");
            preparedStatementValues.add(categoryGetRequest.getActive());
        }
    }

    private void addOrderByClause(final StringBuilder selectQuery, final ServiceGetRequest categoryGetRequest) {
        final String sortBy = categoryGetRequest.getSortBy() == null ? "category.code"
                : "category." + categoryGetRequest.getSortBy();
        final String sortOrder = categoryGetRequest.getSortOrder() == null ? "DESC" : categoryGetRequest.getSortOrder();
        selectQuery.append(" ORDER BY " + sortBy + " " + sortOrder);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void addPagingClause(final StringBuilder selectQuery, final List preparedStatementValues,
            final ServiceGetRequest categoryGetRequest) {
        // handle limit(also called pageSize) here
        selectQuery.append(" LIMIT ?");
        long pageSize = 1L;
        if (categoryGetRequest.getPageSize() != null)
            pageSize = categoryGetRequest.getPageSize();
        preparedStatementValues.add(pageSize); // Set limit to pageSize

        // handle offset here
        selectQuery.append(" OFFSET ?");
        int pageNumber = 0; // Default pageNo is zero meaning first page
        if (categoryGetRequest.getPageNumber() != null)
            pageNumber = categoryGetRequest.getPageNumber() - 1;
        preparedStatementValues.add(pageNumber * pageSize); // Set offset to
        // pageNo * pageSize
    }

    /**
     * This method is always called at the beginning of the method so that and is prepended before the field's predicate is
     * handled.
     *
     * @param appendAndClauseFlag
     * @param queryString
     * @return boolean indicates if the next predicate should append an "AND"
     */
    private boolean addAndClauseIfRequired(final boolean appendAndClauseFlag, final StringBuilder queryString) {
        if (appendAndClauseFlag)
            queryString.append(" AND");

        return true;
    }

    private static String getIdQuery(final List<Long> idList) {
        final StringBuilder query = new StringBuilder("(");
        if (idList.size() >= 1) {
            query.append(idList.get(0).toString());
            for (int i = 1; i < idList.size(); i++)
                query.append(", " + idList.get(i));
        }
        return query.append(")").toString();
    }

    public static String insertComplaintTypeQuery(){
    	return "INSERT into egpgr_complainttype (id, name, code, description, isactive, slahours, tenantid, type, createdby, createddate, category) "
    			+ "values (?,?,?,?,?,?,?,?,?,?,?)"; 
    	
    }
    public static String insertServiceTypeQuery() {
        return "INSERT INTO service_definition (code,tenantid,createdby,createddate) values "
                + "(?,?,?,?)";
    }  

    
    public static String insertServiceTypeQueryAttribValues() {
        return "INSERT INTO attribute_definition (code, variable, datatype, description, datatypedescription, servicecode,  required, "
        		+ "tenantid, createdby, createddate) values "
                + "(?,?,?,?,?,?,?,?,?,?)";
    }
    
    public static String insertValueDefinitionQuery(){
    	return "INSERT INTO value_definition (servicecode, attributecode, key, name, tenantid, createddate, createdby) "
    			+ "values (?,?,?,?,?,?,?) "; 
    }

    public static String updateServiceTypeQuery() {
        return "UPDATE egpgr_complainttype SET name = ?, description = ?, category = ?, "
                + "isactive = ?, lastmodifiedby = ?, lastmodifieddate = ? where code = ? and tenantid = ? ";
    }
    
    public static String removeAttributeQuery() {
    	return "DELETE from attribute_definition WHERE servicecode = ? " ;
    }
    
    public static String removeValueQuery() {
    	return "DELETE from value_definition WHERE servicecode = ?  " ;
    }

    public static String selectServiceNameAndCodeQuery() {
        return " select code FROM egpgr_grievancetype where name = ? and tenantId = ?";
    }

    public static String selectServiceNameAndCodeNotInQuery() {
        return " select code from egpgr_grievancetype where name = ? and tenantId = ? and code != ? ";
    }
    
    public static String getAllServiceTypes(){
    	return "select comp.tenantid, comp.code, comp.name, comp.description, adef.code attributecode, " 
    			+ " adef.datatype, adef.description, adef.datatypedescription, adef.variable, adef.required, vdef.key, vdef.name "   
    			+ " from egpgr_complainttype comp LEFT JOIN service_definition sdef ON comp.code = sdef.code LEFT JOIN attribute_definition adef ON sdef.code = adef.servicecode "
    			+ " LEFT JOIN value_definition vdef ON adef.code = vdef.attributecode " ;
    }

}
