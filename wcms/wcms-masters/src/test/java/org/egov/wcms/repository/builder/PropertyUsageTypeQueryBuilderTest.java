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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.egov.wcms.web.contract.PropertyTypeUsageTypeGetReq;
import org.junit.Test;

public class PropertyUsageTypeQueryBuilderTest {

    /*@Test
    public void plainQueryCheck() {
        final List<Object> preparedStatementValues = new ArrayList<>();
        final PropertyUsageTypeQueryBuilder propUsageTypeQueryBuilder = new PropertyUsageTypeQueryBuilder();
        final PropertyTypeUsageTypeGetReq propUsageTypeGetRequest = new PropertyTypeUsageTypeGetReq();
        propUsageTypeQueryBuilder.getQuery(propUsageTypeGetRequest, preparedStatementValues);
        assertEquals("SELECT * FROM egwtr_property_usage_type proUseType",
                propUsageTypeQueryBuilder.getQuery(propUsageTypeGetRequest, preparedStatementValues));
    }*/

   /* @Test
    public void queryWithTenantIdCheck() {
        final List<Object> preparedStatementValues = new ArrayList<>();
        final PropertyUsageTypeQueryBuilder propUsageTypeQueryBuilder = new PropertyUsageTypeQueryBuilder();
        final PropertyTypeUsageTypeGetReq propUsageTypeGetRequest = new PropertyTypeUsageTypeGetReq();
        propUsageTypeGetRequest.setTenantId("DEFAULT");
        propUsageTypeQueryBuilder.getQuery(propUsageTypeGetRequest, preparedStatementValues);
        assertEquals("SELECT * FROM egwtr_property_usage_type proUseType WHERE proUseType.tenantid = ?",
                propUsageTypeQueryBuilder.getQuery(propUsageTypeGetRequest, preparedStatementValues));
    }*/

    /*@Test
    public void queryWithIDWithoutTenantIdCheck() {
        final List<Object> preparedStatementValues = new ArrayList<>();
        final PropertyUsageTypeQueryBuilder propUsageTypeQueryBuilder = new PropertyUsageTypeQueryBuilder();
        final PropertyTypeUsageTypeGetReq propUsageTypeGetRequest = new PropertyTypeUsageTypeGetReq();
        final List<Long> idList = new ArrayList<>();
        idList.add(2L);
        propUsageTypeGetRequest.setId(idList);
        propUsageTypeQueryBuilder.getQuery(propUsageTypeGetRequest, preparedStatementValues);
        assertEquals("SELECT * FROM egwtr_property_usage_type proUseType WHERE proUseType.id IN (2)",
                propUsageTypeQueryBuilder.getQuery(propUsageTypeGetRequest, preparedStatementValues));
    }*/
}
