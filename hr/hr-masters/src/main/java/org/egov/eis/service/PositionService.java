/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) 2016  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.eis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.eis.model.Department;
import org.egov.eis.model.DepartmentDesignation;
import org.egov.eis.model.Designation;
import org.egov.eis.model.Position;
import org.egov.eis.repository.PositionRepository;
import org.egov.eis.web.contract.*;
import org.egov.eis.web.contract.factory.ResponseInfoFactory;
import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class PositionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PositionService.class);

	@Value("${kafka.topics.position.db_persist.name}")
	private String positionDBPersistTopic;

	@Value("${kafka.topics.position.create.name}")
	private String positionCreateTopic;

	@Value("${kafka.topics.position.update.name}")
	private String positionUpdateTopic;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private DepartmentDesignationService deptDesigService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private ResponseInfoFactory responseInfoFactory;

	@Autowired
	private LogAwareKafkaTemplate<String, Object> kafkaTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;

	public List<Position> getPositions(PositionGetRequest positionGetRequest) {
		return positionRepository.findForCriteria(positionGetRequest);
	}

	public ResponseEntity<?> createPosition(PositionRequest positionRequest) {
		List<Position> positions = positionRequest.getPosition();
		kafkaTemplate.send(positionDBPersistTopic, positionRequest);
		return getSuccessResponseForCreate(positions, positionRequest.getRequestInfo());
	}

	public ResponseEntity<?> updatePosition(PositionRequest positionRequest) {
		List<Position> positions = positionRequest.getPosition();
		kafkaTemplate.send(positionUpdateTopic, positionRequest);
		return getSuccessResponseForCreate(positions, positionRequest.getRequestInfo());
	}

	/**
	 * Populate PositionResponse object & returns ResponseEntity of type
	 * PositionResponse containing ResponseInfo & array of Position objects
	 * 
	 * @param positionList
	 * @param requestInfo
	 * @return ResponseEntity<?>
	 */
	public ResponseEntity<?> getSuccessResponseForCreate(List<Position> positionList, RequestInfo requestInfo) {
		PositionResponse positionResponse = new PositionResponse();
		positionResponse.getPosition().addAll(positionList);

		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true);
		responseInfo.setStatus(HttpStatus.OK.toString());
		positionResponse.setResponseInfo(responseInfo);
		return new ResponseEntity<PositionResponse>(positionResponse, HttpStatus.OK);
	}

	@Transactional
	public void create(PositionRequest positionRequest) {
		populateCreatePositionDetails(positionRequest);
		List<Long> ids = positionRequest.getPosition().stream().map(Position::getId).collect(Collectors.toList());
		positionRepository.create(positionRequest);
		PositionGetRequest positionGetRequest = new PositionGetRequest();
		positionGetRequest.setId(ids);
		// FIXME : Assuming request will come from same tenant & so, adding common tenantId for all positions search.
		positionGetRequest.setTenantId(positionRequest.getRequestInfo().getUserInfo().getTenantId());
		List<Position> positions = positionRepository.findForCriteria(positionGetRequest);
		positionRequest.setPosition(positions);
		kafkaTemplate.send(positionCreateTopic, positionRequest);
	}

	@Transactional
	public void update(PositionRequest positionRequest) {
		positionRepository.update(positionRequest);
	}

	private void populateCreatePositionDetails(PositionRequest positionRequest) {
		List<Position> positions = positionRequest.getPosition();
		RequestInfo requestInfo = positionRequest.getRequestInfo();
		RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

		List<Position> positionsList = new ArrayList<>();
		for (int i = 0; i < positions.size(); i++) {
			DepartmentDesignation deptDesig = positions.get(i).getDeptdesig();
			String tenantId = positions.get(i).getTenantId();

			Department department = departmentService.getDepartments(Arrays.asList(deptDesig.getDepartmentId()),
					tenantId, requestInfoWrapper).get(0);

			DesignationGetRequest designationGetRequest = DesignationGetRequest.builder()
					.id(Arrays.asList(deptDesig.getDesignation().getId())).tenantId(tenantId).build();
			Designation designation = designationService.getDesignations(designationGetRequest).get(0);

			deptDesig = deptDesigService.getByDepartmentAndDesignation(department.getId(), designation.getId(), tenantId);

			if (isEmpty(deptDesig)) {
				deptDesig = DepartmentDesignation.builder()
						.departmentId(department.getId()).designation(designation).tenantId(tenantId).build();
				deptDesigService.create(deptDesig);
			}
			deptDesig = deptDesigService.getByDepartmentAndDesignation(department.getId(), designation.getId(), tenantId);

			List<Long> sequences = positionRepository.generateSequences(positions.get(i).getNoOfPositions());

			for (int j = 0; j < positions.get(i).getNoOfPositions(); j++) {
				String name = department.getCode().toUpperCase() + "_" + designation.getCode().toUpperCase() + "_";
				Position pos = Position.builder().id(sequences.get(j)).name(name).active(true).deptdesig(deptDesig)
						.isPostOutsourced(positions.get(i).getIsPostOutsourced()).tenantId(positions.get(i).getTenantId()).build();
				positionsList.add(pos);
			}
		}

		positionRequest.setPosition(positionsList);
	}

}