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

package org.egov.wcms.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.egov.wcms.config.ApplicationProperties;
import org.egov.wcms.model.PropertyTypeUsageType;
import org.egov.wcms.repository.PropertyUsageTypeRepository;
import org.egov.wcms.web.contract.PropertyTypeUsageTypeGetReq;
import org.egov.wcms.web.contract.PropertyTypeUsageTypeReq;
import org.egov.wcms.web.contract.PropertyTypeUsageTypesRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(PropertyUsageTypeService.class)
public class PropertyUsageTypeServiceTest {

    @Mock
    private PropertyUsageTypeRepository propUsageTypeRepository;

    @Mock
    private LogAwareKafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private ApplicationProperties applicationProperties;

    @InjectMocks
    private PropertyUsageTypeService propUsageTypeService;
    @Mock
    private CodeGeneratorService codeGeneratorService;

    @SuppressWarnings("unchecked")
    @Test(expected = Exception.class)
    public void test_Should_Find_Search_Valid() {
        final List<PropertyTypeUsageType> propUsageTypes = new ArrayList<>();
        propUsageTypes.add(getPropertyUsageType());
        final PropertyTypeUsageTypeGetReq propertyTypeUsageTypeGetReq = Mockito.mock(PropertyTypeUsageTypeGetReq.class);
        when(propUsageTypeRepository.getPropertyUsageType(propertyTypeUsageTypeGetReq)).thenThrow(Exception.class);
        assertTrue(propUsageTypes.equals(propUsageTypeService.getPropertyUsageTypes(propertyTypeUsageTypeGetReq)));
    }

    /*
     * @Test public void test_Should_Find_Search_Invalid() { final List<PropertyTypeUsageType> propUsageTypes = new ArrayList<>();
     * propUsageTypes.add(getPropertyUsageType()); final PropertyTypeUsageTypeGetReq propertyTypeUsageTypeGetReq =
     * Mockito.mock(PropertyTypeUsageTypeGetReq.class);
     * when(propUsageTypeRepository.getPropertyUsageType(any(PropertyTypeUsageTypeGetReq.class))).thenReturn(null);
     * assertNotNull(propUsageTypes.equals(propUsageTypeService.getPropertyUsageTypes(any(PropertyTypeUsageTypeGetReq.class)))); }
     */

    @Test
    public void test_Should_Create() {
   
        final List<PropertyTypeUsageType> propertyUsageTypes = new ArrayList<>();
        propertyUsageTypes.add(getPropertyUsageType());
        final PropertyTypeUsageTypeReq propUsageTypeRequest = new PropertyTypeUsageTypeReq();
        propUsageTypeRequest.setPropertyTypeUsageType(propertyUsageTypes);
        final PropertyTypeUsageTypesRes propUsageTypeResponse = new PropertyTypeUsageTypesRes();
        propUsageTypeResponse.setResponseInfo(null);
        propUsageTypeResponse.setPropertyTypeUsageTypes(propertyUsageTypes);

        when(propUsageTypeService.create(any(PropertyTypeUsageTypeReq.class))).thenReturn(propUsageTypeRequest);
        assertTrue(propUsageTypeRequest.equals(propUsageTypeService.create(propUsageTypeRequest)));

    }

    @Test
    public void test_Should_Update() {
        final List<PropertyTypeUsageType> propertyUsageTypes = new ArrayList<>();
        propertyUsageTypes.add(getPropertyUsageType());
        final PropertyTypeUsageTypeReq propUsageTypeRequest = new PropertyTypeUsageTypeReq();
        propUsageTypeRequest.setPropertyTypeUsageType(propertyUsageTypes);
        final PropertyTypeUsageTypesRes propUsageTypeResponse = new PropertyTypeUsageTypesRes();
        propUsageTypeResponse.setResponseInfo(null);
        propUsageTypeResponse.setPropertyTypeUsageTypes(propertyUsageTypes);

        when(propUsageTypeService.update(any(PropertyTypeUsageTypeReq.class))).thenReturn(propUsageTypeRequest);
        assertTrue(propUsageTypeRequest.equals(propUsageTypeService.update(propUsageTypeRequest)));

    }

    private PropertyTypeUsageType getPropertyUsageType() {

        final PropertyTypeUsageType propUsageType = new PropertyTypeUsageType();
        propUsageType.setActive(true);
        propUsageType.setId(2L);
        propUsageType.setPropertyType("RES");
        propUsageType.setTenantId("default");
        propUsageType.setUsageType("COM");
        return propUsageType;
    }

}
