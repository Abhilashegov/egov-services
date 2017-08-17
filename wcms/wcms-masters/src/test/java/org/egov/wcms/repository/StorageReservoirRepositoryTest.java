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
package org.egov.wcms.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.wcms.model.StorageReservoir;
import org.egov.wcms.repository.builder.StorageReservoirQueryBuilder;
import org.egov.wcms.repository.rowmapper.StorageReservoirRowMapper;
import org.egov.wcms.service.RestWaterExternalMasterService;
import org.egov.wcms.web.contract.Boundary;
import org.egov.wcms.web.contract.BoundaryResponse;
import org.egov.wcms.web.contract.BoundaryResponseInfo;
import org.egov.wcms.web.contract.BoundaryType;
import org.egov.wcms.web.contract.StorageReservoirGetRequest;
import org.egov.wcms.web.contract.StorageReservoirRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(StorageReservoirRepository.class)
public class StorageReservoirRepositoryTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private StorageReservoirRowMapper storageReservoirRowMapper;

    @Mock
    private StorageReservoirQueryBuilder storageReservoirQueryBuilder;

    @InjectMocks
    private StorageReservoirRepository storageReservoirRepository;

    @Mock
    private RestWaterExternalMasterService restExternalMasterService;

    @Test
    public void test_Should_Create_StorageReservoir() {
        final StorageReservoirRequest storageReservoirRequest = new StorageReservoirRequest();
        final RequestInfo requestInfo = new RequestInfo();
        final User user = new User();
        user.setId(1l);
        requestInfo.setUserInfo(user);
        storageReservoirRequest.setRequestInfo(requestInfo);
        final List<StorageReservoir> storageReservoirList = new ArrayList<>();
        final StorageReservoir storageReservoir = getStorageReservoir();
        storageReservoirList.add(storageReservoir);
        storageReservoirRequest.setStorageReservoir(storageReservoirList);
        StorageReservoirRequest storageReserviorReq=storageReservoirRepository.persistCreateStorageReservoir(storageReservoirRequest);
        assertThat(storageReserviorReq.getStorageReservoir().size()).isEqualTo(1);
    }

    @Test
    public void test_Should_Update_StorageReservoir() {
        final StorageReservoirRequest storageReservoirRequest = new StorageReservoirRequest();
        final RequestInfo requestInfo = new RequestInfo();
        final User user = new User();
        user.setId(1l);
        requestInfo.setUserInfo(user);
        storageReservoirRequest.setRequestInfo(requestInfo);
        final List<StorageReservoir> storageReservoirList = new ArrayList<>();
        final StorageReservoir storageReservoir = getStorageReservoir();
        storageReservoirList.add(storageReservoir);
        storageReservoirRequest.setStorageReservoir(storageReservoirList);
        StorageReservoirRequest storageReserviorReq=storageReservoirRepository.persistCreateStorageReservoir(storageReservoirRequest);
        assertThat(storageReserviorReq.getStorageReservoir().size()).isEqualTo(1);
    }

    @Test
    public void test_Should_Search_StorageReservoir() {
        final List<Object> preparedStatementValues = new ArrayList<>();
        final List<StorageReservoir> storageReservoirList = new ArrayList<>();
        final StorageReservoir storageReservoir = getStorageReservoir();
        storageReservoirList.add(storageReservoir);
        final StorageReservoirGetRequest storageReservoirGetRequest =getStorageReservoirGetCriteria();
        when(namedParameterJdbcTemplate.query(any(String.class),anyMap(), any(StorageReservoirRowMapper.class)))
                .thenReturn(storageReservoirList);
        String[] wardNum={"22"};
        String[] zoneNum={"21"};
        String[] localityNum={"12"};

        when(restExternalMasterService.getBoundaryName("Ward",wardNum, "default")).thenReturn(getBoundaryWardRes());
        when(restExternalMasterService.getBoundaryName("Zone",zoneNum,"default")).thenReturn(getBoundaryZoneRes());
        when(restExternalMasterService.getBoundaryName("Locality",localityNum, "default")).thenReturn(getBoundaryLocalityRes());
       assertTrue(storageReservoirRepository.findForCriteria(storageReservoirGetRequest)
               .get(0).getCode().equals(storageReservoirList.get(0).getCode()));
    }

    private BoundaryResponse getBoundaryLocalityRes() {
        BoundaryType type=BoundaryType.builder().id("4").name("Locality").build();
        Boundary boundary =Boundary.builder().boundaryNum("12").boundaryType(type).id("4").name("kontapeta").tenantId("default").build();
        return BoundaryResponse.builder().responseInfo(getResponseInfo()).boundarys(Arrays.asList(boundary)).build();
    }

    private BoundaryResponse getBoundaryZoneRes() {
        BoundaryType type=BoundaryType.builder().id("4").name("Zone").build();
        Boundary boundary =Boundary.builder().boundaryNum("21").boundaryType(type).id("4").name("Zone-1").tenantId("default").build();
        return BoundaryResponse.builder().responseInfo(getResponseInfo()).boundarys(Arrays.asList(boundary)).build();
    }

    private BoundaryResponse getBoundaryWardRes() {
        BoundaryType type=BoundaryType.builder().id("3").name("Ward").build();
        Boundary boundary =Boundary.builder().boundaryNum("22").boundaryType(type).id("3").name("Revenue Ward").tenantId("default").build();
        return BoundaryResponse.builder().responseInfo(getResponseInfo()).boundarys(Arrays.asList(boundary)).build();
    }

    private BoundaryResponseInfo getResponseInfo() {
        return BoundaryResponseInfo.builder().apiId("api345").msgId("mkede").resMsgId("res345").status("search").ver("2").build();
    }

    private StorageReservoirGetRequest getStorageReservoirGetCriteria() {
        return StorageReservoirGetRequest.builder().code("12").name("test").zoneNum("21").wardNum("22").reservoirType("abcd")
                .tenantId("default").build();
        
    }

    private StorageReservoir getStorageReservoir() {
        final StorageReservoir storageReservoir = new StorageReservoir();
        storageReservoir.setTenantId("default");
        storageReservoir.setName("test");
        storageReservoir.setCode("12");
        storageReservoir.setLocationNum("12");
        storageReservoir.setWardNum("22");
        storageReservoir.setZoneNum("21");
        storageReservoir.setCapacity(2d);
        storageReservoir.setNoOfSubLines(2l);
        storageReservoir.setNoOfMainDistributionLines(2l);
        storageReservoir.setNoOfConnection(2l);
        storageReservoir.setReservoirType("abcd");
        return storageReservoir;
    }

}
