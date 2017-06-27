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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.ArrayUtils;
import org.egov.eis.broker.EmployeeProducer;
import org.egov.eis.config.PropertiesManager;
import org.egov.eis.model.*;
import org.egov.eis.model.enums.BloodGroup;
import org.egov.eis.repository.*;
import org.egov.eis.service.exception.EmployeeIdNotFoundException;
import org.egov.eis.service.exception.UserException;
import org.egov.eis.service.helper.EmployeeHelper;
import org.egov.eis.service.helper.EmployeeUserMapper;
import org.egov.eis.web.contract.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class EmployeeService {

    public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private ServiceHistoryService serviceHistoryService;

    @Autowired
    private ProbationService probationService;

    @Autowired
    private RegularisationService regularisationService;

    @Autowired
    private TechnicalQualificationService technicalQualificationService;

    @Autowired
    private EducationalQualificationService educationalQualificationService;

    @Autowired
    private DepartmentalTestService departmentalTestService;

    @Autowired
    private EmployeeJurisdictionService employeeJurisdictionService;

    @Autowired
    private EmployeeLanguageService employeeLanguageService;

    @Autowired
    private EmployeeDocumentsService employeeDocumentsService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private HODDepartmentRepository hodDepartmentRepository;

    @Autowired
    private ServiceHistoryRepository serviceHistoryRepository;

    @Autowired
    private ProbationRepository probationRepository;

    @Autowired
    private RegularisationRepository regularisationRepository;

    @Autowired
    private TechnicalQualificationRepository technicalQualificationRepository;

    @Autowired
    private EducationalQualificationRepository educationalQualificationRepository;

    @Autowired
    private DepartmentalTestRepository departmentalTestRepository;

    @Autowired
    private EmployeeJurisdictionRepository employeeJurisdictionRepository;

    @Autowired
    private EmployeeLanguageRepository employeeLanguageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeHelper employeeHelper;

    @Autowired
    private EmployeeUserMapper employeeUserMapper;

    @Autowired
    private EmployeeProducer employeeProducer;

    @Autowired
    PropertiesManager propertiesManager;

    @Autowired
    private ObjectMapper objectMapper;

    public List<EmployeeInfo> getEmployees(EmployeeCriteria employeeCriteria, RequestInfo requestInfo) throws CloneNotSupportedException {
        List<User> usersList = null;
        List<Long> ids = null;
        // If roleCodes or userName is present, get users first, as there will be very limited results
        if (!isEmpty(employeeCriteria.getRoleCodes()) || !isEmpty(employeeCriteria.getUserName())) {
            usersList = userService.getUsers(employeeCriteria, requestInfo);
            LOGGER.debug("usersList returned by UsersService is :: " + usersList);
            if (isEmpty(usersList))
                return Collections.EMPTY_LIST;
            ids = usersList.stream().map(user -> user.getId()).collect(Collectors.toList());
            employeeCriteria.setId(ids);
        }

        List<EmployeeInfo> employeeInfoList = employeeRepository.findForCriteria(employeeCriteria);

        if (isEmpty(employeeInfoList))
            return Collections.EMPTY_LIST;

        // If roleCodes or userName is not present, get employees first
        if (isEmpty(employeeCriteria.getRoleCodes()) || isEmpty(employeeCriteria.getUserName())) {
            ids = employeeInfoList.stream().map(employeeInfo -> employeeInfo.getId()).collect(Collectors.toList());
            LOGGER.debug("Employee ids are :: " + ids);
            employeeCriteria.setId(ids);
            usersList = userService.getUsers(employeeCriteria, requestInfo);
            LOGGER.debug("usersList returned by UsersService is :: " + usersList);
        }
        employeeInfoList = employeeUserMapper.mapUsersWithEmployees(employeeInfoList, usersList);

        if (!isEmpty(ids)) {
            List<EmployeeDocument> employeeDocuments = employeeRepository.getDocumentsForListOfEmployeeIds(ids,
                    employeeCriteria.getTenantId());
            employeeHelper.mapDocumentsWithEmployees(employeeInfoList, employeeDocuments);
        }

        return employeeInfoList;
    }

    public Employee getEmployee(Long employeeId, String tenantId, RequestInfo requestInfo) {
        Employee employee = employeeRepository.findById(employeeId, tenantId);
        List<Long> ids = new ArrayList<>();
        ids.add(employeeId);
        if (employee == null)
            throw new EmployeeIdNotFoundException(employeeId);

        EmployeeCriteria employeeCriteria = EmployeeCriteria.builder().id(ids).tenantId(tenantId).build();
        User user = userService.getUsers(employeeCriteria, requestInfo).get(0);

        user.setBloodGroup(isEmpty(user.getBloodGroup()) ? null : BloodGroup.fromValue(user.getBloodGroup()).toString());
        employee.setUser(user);

        employee.setLanguagesKnown(employeeLanguageRepository.findByEmployeeId(employeeId, tenantId));
        employee.setAssignments(assignmentRepository.findByEmployeeId(employeeId, tenantId));
        employee.getAssignments().forEach(assignment -> {
            assignment.setHod(hodDepartmentRepository.findByAssignmentId(assignment.getId(), tenantId));
        });
        employee.setServiceHistory(serviceHistoryRepository.findByEmployeeId(employeeId, tenantId));
        employee.setProbation(probationRepository.findByEmployeeId(employeeId, tenantId));
        employee.setJurisdictions(employeeJurisdictionRepository.findByEmployeeId(employeeId, tenantId));
        employee.setRegularisation(regularisationRepository.findByEmployeeId(employeeId, tenantId));
        employee.setTechnical(technicalQualificationRepository.findByEmployeeId(employeeId, tenantId));
        employee.setEducation(educationalQualificationRepository.findByEmployeeId(employeeId, tenantId));
        employee.setTest(departmentalTestRepository.findByEmployeeId(employeeId, tenantId));
        employeeDocumentsService.populateDocumentsInRespectiveObjects(employee);

        LOGGER.debug("After Employee Search: " + employee);

        return employee;
    }

    public Employee createAsync(EmployeeRequest employeeRequest) throws UserException, JsonProcessingException {
        UserRequest userRequest = employeeHelper.getUserRequest(employeeRequest);
        userRequest.getUser().setBloodGroup(isEmpty(userRequest.getUser().getBloodGroup()) ? null
                : userRequest.getUser().getBloodGroup());

        // FIXME : Fix a common standard for date formats in User Service.
        UserResponse userResponse = userService.createUser(userRequest);
        ;
        User user = userResponse.getUser().get(0);

        Employee employee = employeeRequest.getEmployee();
        employee.setId(user.getId());
        employee.setUser(user);

        employeeHelper.populateDefaultDataForCreate(employeeRequest);

        String employeeRequestJson = null;
        employeeRequestJson = objectMapper.writeValueAsString(employeeRequest);
        LOGGER.info("employeeJson::" + employeeRequestJson);

        employeeProducer.sendMessage(propertiesManager.getSaveEmployeeTopic(), propertiesManager.getEmployeeSaveKey(),
                employeeRequestJson);

        return employee;
    }

    @Transactional
    public void create(EmployeeRequest employeeRequest) {
        Employee employee = employeeRequest.getEmployee();
        employeeRepository.save(employee);
        employeeJurisdictionRepository.save(employee);
        if (employee.getLanguagesKnown() != null) {
            employeeLanguageRepository.save(employee);
        }
        assignmentRepository.save(employeeRequest);
        employee.getAssignments().forEach((assignment) -> {
            if (assignment.getHod() != null) {
                hodDepartmentRepository.save(assignment, employee.getTenantId());
            }
        });
        if (employee.getServiceHistory() != null) {
            serviceHistoryRepository.save(employeeRequest);
        }
        if (employee.getProbation() != null) {
            probationRepository.save(employeeRequest);
        }
        if (employee.getRegularisation() != null) {
            regularisationRepository.save(employeeRequest);
        }
        if (employee.getTechnical() != null) {
            technicalQualificationRepository.save(employeeRequest);
        }
        if (employee.getEducation() != null) {
            educationalQualificationRepository.save(employeeRequest);
        }
        if (employee.getTest() != null) {
            departmentalTestRepository.save(employeeRequest);
        }
    }

    public Employee updateAsync(EmployeeRequest employeeRequest) throws UserException, JsonProcessingException {

        Employee employee = employeeRequest.getEmployee();

        UserRequest userRequest = employeeHelper.getUserRequest(employeeRequest);
        userRequest.getUser().setBloodGroup(isEmpty(userRequest.getUser().getBloodGroup()) ? null
                : userRequest.getUser().getBloodGroup());

        UserResponse userResponse = userService.updateUser(userRequest.getUser().getId(), userRequest);
        User user = userResponse.getUser().get(0);
        employee.setUser(user);

        employeeHelper.populateDefaultDataForUpdate(employeeRequest);

        String employeeUpdateRequestJson = null;

        employeeUpdateRequestJson = objectMapper.writeValueAsString(employeeRequest);
        LOGGER.info("employeeJson update::" + employeeUpdateRequestJson);
        AssignmentGetRequest assignmentGetRequest = AssignmentGetRequest.builder().tenantId(employee.getTenantId()).build();
        List<Assignment> assignments = assignmentService.getAssignments(employee.getId(), assignmentGetRequest);

        List<Long> positionfromDB = assignments.stream().map(assignment -> assignment.getPosition()).collect(Collectors.toList());
        List<Long> positionFromRequest = employeeRequest.getEmployee().getAssignments().stream()
                .map(assignment -> assignment.getPosition()).collect(Collectors.toList());
        boolean isPositionModified = !(ArrayUtils.isEquals(positionfromDB, positionFromRequest));

        List<Date> fromDateFromDB = assignments.stream().map(assignment -> assignment.getFromDate()).collect(Collectors.toList());
        List<Date> fromDateFromRequest = employeeRequest.getEmployee().getAssignments().stream().map(assignment -> assignment.getFromDate()).collect(Collectors.toList());
        boolean isFromDateModified = !(ArrayUtils.isEquals(fromDateFromDB, fromDateFromRequest));

        List<Date> toDateFromDB = assignments.stream().map(assignment -> assignment.getToDate()).collect(Collectors.toList());
        List<Date> toDateFromRequest = employeeRequest.getEmployee().getAssignments().stream()
                .map(assignment -> assignment.getToDate()).collect(Collectors.toList());
        boolean isToDateModified = !(ArrayUtils.isEquals(toDateFromDB, toDateFromRequest));

        boolean isAssignmentDeleted = assignments.size() != employeeRequest.getEmployee().getAssignments().size();

        if (isPositionModified || isFromDateModified || isToDateModified || isAssignmentDeleted) {
            employeeProducer.sendMessage(propertiesManager.getAssignmentUpdateName(), propertiesManager.getAssignmentUpdateKey(),
                    employeeUpdateRequestJson);
        }


        employeeProducer.sendMessage(propertiesManager.getUpdateEmployeeTopic(), propertiesManager.getEmployeeSaveKey(),
                employeeUpdateRequestJson);
        return employee;
    }

    @Transactional
    public void update(EmployeeRequest employeeRequest) {
        Employee employee = employeeRequest.getEmployee();
        employeeRepository.update(employee);
        employeeLanguageService.update(employee);
        employeeJurisdictionService.update(employee);
        assignmentService.update(employee);
        departmentalTestService.update(employee);
        serviceHistoryService.update(employee);
        probationService.update(employee);
        regularisationService.update(employee);
        technicalQualificationService.update(employee);
        educationalQualificationService.update(employee);
        employeeDocumentsService.update(employee);
    }

    public List<EmployeeInfo> getLoggedInEmployee(RequestInfo requestInfo) throws CloneNotSupportedException {
        UserInfo userInfo = requestInfo.getUserInfo();
        EmployeeCriteria employeeCriteria = new EmployeeCriteria();
        employeeCriteria.setUserName(userInfo.getUserName());
        employeeCriteria.setTenantId(userInfo.getTenantId());
        employeeCriteria.setActive(true);

        List<User> users = userService.getUsers(employeeCriteria, requestInfo);
        List<Long> userIds = users.stream().map(user -> user.getId()).collect(Collectors.toList());
        employeeCriteria.setId(userIds);

        return getEmployees(employeeCriteria, requestInfo);
    }
}