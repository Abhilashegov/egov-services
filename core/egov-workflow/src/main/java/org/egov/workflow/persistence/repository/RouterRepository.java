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
package org.egov.workflow.persistence.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.egov.workflow.domain.model.Attribute;
import org.egov.workflow.domain.model.PersistRouter;
import org.egov.workflow.domain.model.PersistRouterReq;
import org.egov.workflow.domain.model.ServiceType;
import org.egov.workflow.domain.model.Value;
import org.egov.workflow.persistence.repository.builder.RouterQueryBuilder;
import org.egov.workflow.persistence.repository.rowmapper.PersistRouteRowMapper;
import org.egov.workflow.persistence.repository.rowmapper.RouterRowMapper;
import org.egov.workflow.web.contract.RouterType;
import org.egov.workflow.web.contract.RouterTypeGetReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RouterRepository {

	public static final Logger LOGGER = LoggerFactory.getLogger(RouterRepository.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Autowired 
    private RouterQueryBuilder routerQueryBuilder;
	
	@Autowired 
	private RouterRowMapper routerRowMapper;
	

	public PersistRouterReq createRouter(final PersistRouterReq routerReq) {
		
		LOGGER.info("Router Request::" + routerReq);
		final String routerInsert = RouterQueryBuilder.insertRouter();
		final PersistRouter persistRouter = routerReq.getRouterType();
		final Object[] obj = new Object[] {persistRouter.getService(),persistRouter.getPosition(), persistRouter.getBoundary(),0,
				Long.valueOf(routerReq.getRequestInfo().getUserInfo().getId()),new Date(),
				Long.valueOf(routerReq.getRequestInfo().getUserInfo().getId()),new Date(),
				persistRouter.getTenantId() };
		
		jdbcTemplate.update(routerInsert, obj);
		return routerReq;
	}
public PersistRouterReq updateRouter(final PersistRouterReq routerReq) {
		
		LOGGER.info("Update Router Request::" + routerReq);
		final String routerUpdate = RouterQueryBuilder.updateRouter();
		final PersistRouter persistRouter = routerReq.getRouterType();
		final Object[] obj = new Object[] {persistRouter.getPosition(),0,
				Long.valueOf(routerReq.getRequestInfo().getUserInfo().getId()),new Date(),
				Long.valueOf(routerReq.getRequestInfo().getUserInfo().getId()),new Date(),
				persistRouter.getBoundary(),persistRouter.getService(),persistRouter.getTenantId() };
		
		jdbcTemplate.update(routerUpdate, obj);
		return routerReq;
	}
public PersistRouter ValidateRouter(final PersistRouterReq routerReq) {
		
		
		final String validateQuery = RouterQueryBuilder.validateRouter();
		PersistRouter persistRouter = new PersistRouter();
		try{
		persistRouter = jdbcTemplate.queryForObject(
				validateQuery, new Object[] { routerReq.getRouterType().getService(),routerReq.getRouterType().getBoundary(),routerReq.getRouterType().getTenantId()}, new PersistRouteRowMapper());
		LOGGER.info("Value coming from validate query boundary::" + persistRouter.getBoundary());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
		return persistRouter;
	}

	
	
	

	
	public List<RouterType> findForCriteria(final RouterTypeGetReq routerTypeGetRequest) {
		final List<Object> preparedStatementValues = new ArrayList<>();
        final String queryStr = routerQueryBuilder.getQuery(routerTypeGetRequest, preparedStatementValues);
        jdbcTemplate.query(queryStr, preparedStatementValues.toArray(), routerRowMapper);
        return prepareRouterTypeList(routerRowMapper);
    }
	
	private List<RouterType> prepareRouterTypeList(RouterRowMapper rowMapper){
		Map<String, List<Value>> attribValue = rowMapper.attribValue;
		Map<String, Map<String, Attribute>> serviceAttrib = rowMapper.serviceAttrib;
		Map<Long, Map<String, List<ServiceType>>> serviceMap = rowMapper.serviceMap;
		Map<Long, RouterType> routerMap = rowMapper.routerMap;
		RouterType routerType = new RouterType();
		List<RouterType> routerTypes = new ArrayList<>();
		List<ServiceType> serviceTypeList = new ArrayList<>();
		
		Iterator<Entry<Long, RouterType>> itr = routerMap.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Long, RouterType> routerEntry = itr.next();
			routerType = routerEntry.getValue();
			Long routerId = routerEntry.getKey();
			Map<String, List<ServiceType>> innerServiceMap = serviceMap.get(routerId);
			Iterator<Entry<String, List<ServiceType>>> innerItr = innerServiceMap.entrySet().iterator();
			while (innerItr.hasNext()) {
				Entry<String, List<ServiceType>> innerEntry = innerItr.next();
				serviceTypeList = innerEntry.getValue();
				List<ServiceType> finalServiceList = new ArrayList<>();
				Iterator<ServiceType> serviceItr = serviceTypeList.iterator();
				while (serviceItr.hasNext()) {
					ServiceType serviceType = new ServiceType();
					serviceType = serviceItr.next();
					Map<String, Attribute> innerAttrMap = serviceAttrib.get(serviceType.getServiceCode());
					Iterator<Entry<String, Attribute>> innerAttrItr = innerAttrMap.entrySet().iterator();
					List<Attribute> finalAttributeList = new ArrayList<>();
					while (innerAttrItr.hasNext()) {
						Entry<String,Attribute> attrEntry = innerAttrItr.next();
						List<Value> valueList = attribValue.get(attrEntry.getValue().getCode());
						attrEntry.getValue().setAttributes(valueList);
						finalAttributeList.add(attrEntry.getValue());
					}
					serviceType.setAttributes(finalAttributeList);
					finalServiceList.add(serviceType);
				}
				routerType.setServices(finalServiceList);
				routerTypes.add(routerType);
			}
		}
		return routerTypes;
	}
	
	
	

}
