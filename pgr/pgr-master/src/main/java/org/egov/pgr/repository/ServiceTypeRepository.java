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
package org.egov.pgr.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.egov.pgr.model.Attribute;
import org.egov.pgr.model.ServiceType;
import org.egov.pgr.model.Value;
import org.egov.pgr.repository.builder.ServiceTypeQueryBuilder;
import org.egov.pgr.repository.rowmapper.ServiceTypeRowMapper;
import org.egov.pgr.web.contract.ServiceGetRequest;
import org.egov.pgr.web.contract.ServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceTypeRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(ServiceTypeRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ServiceTypeQueryBuilder serviceTypeQueryBuilder;

	public ServiceRequest persistServiceType(final ServiceRequest serviceRequest) {
		LOGGER.info("Service Type Request::" + serviceRequest);
		final String complaintInsert = serviceTypeQueryBuilder.insertComplaintTypeQuery();
		boolean active = (null != serviceRequest.getService().getActive() ? serviceRequest.getService().getActive() : true);
		final Object[] object = new Object[] { serviceRequest.getService().getServiceName(),
				serviceRequest.getService().getServiceCode(), serviceRequest.getService().getDescription(), active, serviceRequest.getService().getSlaHours(),
				serviceRequest.getService().getTenantId(), serviceRequest.getService().getType(),
				serviceRequest.getRequestInfo().getUserInfo().getId(), new Date(new java.util.Date().getTime()), serviceRequest.getService().getCategory()
				};
		jdbcTemplate.update(complaintInsert, object);

		final String serviceInsert = serviceTypeQueryBuilder.insertServiceTypeQuery();
		final Object[] obj = new Object[] { serviceRequest.getService().getServiceCode(),
				serviceRequest.getService().getTenantId(), serviceRequest.getRequestInfo().getUserInfo().getId(),
				new Date(new java.util.Date().getTime()) };
		jdbcTemplate.update(serviceInsert, obj);
		if (serviceRequest.getService().isMetadata()) {
			persistAttributeValues(serviceRequest);
		}
		if (null != serviceRequest.getService().getKeywords()){
			if(serviceRequest.getService().getKeywords().size() > 0){
				persistKeywordServiceCodeMapping(serviceRequest);
			}
		}
		return serviceRequest;
	}
	
	private void persistAttributeValues(ServiceRequest serviceRequest) {
		final String serviceInsertAttribValues = ServiceTypeQueryBuilder.insertServiceTypeQueryAttribValues();
		List<Attribute> attributeList = new ArrayList<>();
		if (null != serviceRequest.getService().getAttributes()) {
			attributeList = serviceRequest.getService().getAttributes();
		}
		for (int i = 0; i < attributeList.size(); i++) {
			Attribute attribute = attributeList.get(i);
			final Object[] obj1 = new Object[] { attribute.getCode(), attribute.getVariable() ? "Y" : "N",
					attribute.getDatatype(), attribute.getDescription(), attribute.getDatatypeDescription(),
					serviceRequest.getService().getServiceCode(), attribute.getRequired() ? "Y" : "N",
					attribute.getGroupCode(), serviceRequest.getService().getTenantId(),
					serviceRequest.getRequestInfo().getUserInfo().getId(), new Date(new java.util.Date().getTime()) };
			jdbcTemplate.update(serviceInsertAttribValues, obj1);
			if (null != attribute.getAttributes()) {
				if (attribute.getAttributes().size() > 0) {
					final String valueInsertQuery = ServiceTypeQueryBuilder.insertValueDefinitionQuery();
					List<Value> valueList = attribute.getAttributes();
					for (int j = 0; j < valueList.size(); j++) {
						Value value = valueList.get(j);
						final Object[] obj2 = new Object[] { serviceRequest.getService().getServiceCode(),
								attribute.getCode(), value.getKey(), value.getName(),
								serviceRequest.getService().getTenantId(), new Date(new java.util.Date().getTime()),
								serviceRequest.getRequestInfo().getUserInfo().getId() };
						jdbcTemplate.update(valueInsertQuery, obj2);
					}
				}
			}
		}
	}
	
	private void persistKeywordServiceCodeMapping(ServiceRequest serviceRequest) {
		ServiceType serviceType = serviceRequest.getService();
		final String serviceKeywordMappingQuery = ServiceTypeQueryBuilder.insertServiceKeyworkMappingQuery();
		for(int i=0; i<serviceType.getKeywords().size(); i++) {
			final Object[] obj1 = new Object[] { serviceType.getServiceCode(), serviceType.getKeywords().get(i), serviceType.getTenantId(),
					serviceRequest.getRequestInfo().getUserInfo().getId(),
					new Date(new java.util.Date().getTime()) };
			jdbcTemplate.update(serviceKeywordMappingQuery, obj1);
		}
	}

    public ServiceRequest persistModifyServiceType(final ServiceRequest serviceRequest) {
        LOGGER.info("Service Type Request::" + serviceRequest);
        final String serviceTypeUpdate = ServiceTypeQueryBuilder.updateServiceTypeQuery();
        final ServiceType serviceType = serviceRequest.getService();
        final Object[] obj = new Object[] { serviceType.getServiceName(),
        		serviceType.getDescription(), serviceType.getCategory(), serviceType.getActive(), serviceType.isHasFinancialImpact(), serviceRequest.getRequestInfo().getUserInfo().getId(), 
                new Date(new java.util.Date().getTime()), serviceType.getServiceCode(), serviceType.getTenantId() };
        jdbcTemplate.update(serviceTypeUpdate, obj);
        final String valueRemove = ServiceTypeQueryBuilder.removeValueQuery();
        final Object[] objValueRemove = new Object[] { serviceType.getServiceCode(), serviceType.getTenantId()};
        jdbcTemplate.update(valueRemove, objValueRemove);
        final String attributeRemove = ServiceTypeQueryBuilder.removeAttributeQuery();
        final Object[] objAttributeRemove = new Object[] { serviceType.getServiceCode(), serviceType.getTenantId()};
        jdbcTemplate.update(attributeRemove, objAttributeRemove);
        final String serviceKeywordRemove = ServiceTypeQueryBuilder.removeServiceKeywordMapping();
        final Object[] objserviceKeywordRemove = new Object[] { serviceType.getServiceCode(), serviceType.getTenantId()};
        jdbcTemplate.update(serviceKeywordRemove, objserviceKeywordRemove);
        if (serviceRequest.getService().isMetadata()) {
			persistAttributeValues(serviceRequest);
		}
        if (null != serviceRequest.getService().getKeywords()){
			if(serviceRequest.getService().getKeywords().size() > 0){
				persistKeywordServiceCodeMapping(serviceRequest);
			}
		}
        return serviceRequest;

    }

	public boolean checkServiceByNameAndCode(final String code, final String name, final String tenantId) {
		final List<Object> preparedStatementValues = new ArrayList<>();
		preparedStatementValues.add(name);
		preparedStatementValues.add(tenantId);
		preparedStatementValues.add(code);
		final String query = ServiceTypeQueryBuilder.selectServiceNameAndCodeQuery();
		final List<Map<String, Object>> serviceTypes = jdbcTemplate.queryForList(query,
				preparedStatementValues.toArray());
		if (!serviceTypes.isEmpty())
			return false;
		return true;
	}
	
	public boolean checkServiceCodeIfExists(final String serviceCode, final String tenantId) {
		final List<Object> preparedStatementValues = new ArrayList<>();
		preparedStatementValues.add(serviceCode);
		preparedStatementValues.add(tenantId);
		final String query = ServiceTypeQueryBuilder.checkServiceCodeIfExists();
		final List<Map<String, Object>> serviceTypes = jdbcTemplate.queryForList(query,
				preparedStatementValues.toArray());
		if (!serviceTypes.isEmpty())
			return true;
		return false;
	}
	
	public boolean checkComplaintNameIfExists(final String serviceName, final String tenantId) {
		final List<Object> preparedStatementValues = new ArrayList<>();
		preparedStatementValues.add(serviceName);
		preparedStatementValues.add(tenantId);
		final String query = ServiceTypeQueryBuilder.checkServiceNameIfExists();
		final List<Map<String, Object>> serviceTypes = jdbcTemplate.queryForList(query,
				preparedStatementValues.toArray());
		if (!serviceTypes.isEmpty())
			return true;
		return false;
	}

    public List<ServiceType> findForCriteria(final ServiceGetRequest serviceTypeGetRequest) {
        final List<Object> preparedStatementValues = new ArrayList<>();
        String queryStr = serviceTypeQueryBuilder.getQuery(serviceTypeGetRequest, preparedStatementValues);
        ServiceTypeRowMapper serviceTypeRowMapper = new ServiceTypeRowMapper();
        jdbcTemplate.query(queryStr,preparedStatementValues.toArray(),  serviceTypeRowMapper);
        return assembleServiceTypeObject(serviceTypeRowMapper);
    }
    
	private List<ServiceType> assembleServiceTypeObject(ServiceTypeRowMapper rowMapper) {
		final String separator = ">";
		List<ServiceType> serviceTypeList = new ArrayList<>();
		Set<Entry<String, ServiceType>> sMapEntrySet = rowMapper.serviceMap.entrySet();
		Iterator<Entry<String, ServiceType>> sMapItr = sMapEntrySet.iterator();
		Set<Entry<String, Map<String, Attribute>>> sAttrEntrySet = rowMapper.serviceAttrib.entrySet();
		Iterator<Entry<String, Map<String, Attribute>>> sAttrItr = sAttrEntrySet.iterator();
		Set<Entry<String, List<Value>>> attrValueEntrySet = rowMapper.attribValue.entrySet();
		while (sMapItr.hasNext()) {
			Entry<String, ServiceType> srvEntry = sMapItr.next();
			ServiceType serviceType = srvEntry.getValue();
			List<String> keywordsList = getKeywordsForService(serviceType);
			serviceType.setKeywords(keywordsList);
			serviceTypeList.add(serviceType);
		}
		for (int i = 0; i < serviceTypeList.size(); i++) {
			while (sAttrItr.hasNext()) {
				Entry<String, Map<String, Attribute>> attrEntry = sAttrItr.next();
				List<Attribute> attributeList = new ArrayList<>();
				if (serviceTypeList.get(i).getServiceCode().equals(attrEntry.getKey())) {
					Iterator<Entry<String, Attribute>> attrInnerItr = attrEntry.getValue().entrySet().iterator();
					while (attrInnerItr.hasNext()) {
						Entry<String, Attribute> attrInnerEntry = attrInnerItr.next();
						Attribute attribute = attrInnerEntry.getValue();
						Iterator<Entry<String, List<Value>>> attrValueItr = attrValueEntrySet.iterator();
						while (attrValueItr.hasNext()) {
							Entry<String, List<Value>> valueEntry = attrValueItr.next();
							if (serviceTypeList.get(i).getServiceCode().concat(separator + attribute.getCode())
									.equals(valueEntry.getKey())) {
								attribute.setAttributes(valueEntry.getValue());
							}
						}
						attributeList.add(attribute);
					}
				}
				serviceTypeList.get(i).setAttributes(attributeList);
				serviceTypeList.get(i).setMetadata(true);
			}
		}
		return serviceTypeList;
	}
	
	public List<String> getKeywordsForService(ServiceType serviceType) {
        final List<Object> preparedStatementValues = new ArrayList<>();
        String queryStr = serviceTypeQueryBuilder.fetchServiceKeywords();
        preparedStatementValues.add(serviceType.getServiceCode());
        preparedStatementValues.add(serviceType.getTenantId());
        List<String> keywords = new ArrayList<>();
        try{
        	keywords = jdbcTemplate.queryForList(queryStr,preparedStatementValues.toArray(),String.class);
        } catch(EmptyResultDataAccessException ex) {
        	LOGGER.info("There are no keywords available for the Service Code : " + ex);
        } catch(Exception e) {
        	LOGGER.error("Encountered an Exception : " + e);
        }
        return keywords;
    }

}


