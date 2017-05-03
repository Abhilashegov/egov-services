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

package org.egov.eis.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.egov.eis.model.LeaveApplication;
import org.egov.eis.model.enums.LeaveStatus;
import org.egov.eis.repository.builder.LeaveApplicationQueryBuilder;
import org.egov.eis.repository.rowmapper.LeaveApplicationRowMapper;
import org.egov.eis.service.WorkFlowService;
import org.egov.eis.web.contract.LeaveApplicationGetRequest;
import org.egov.eis.web.contract.LeaveApplicationRequest;
import org.egov.eis.web.contract.LeaveApplicationSingleRequest;
import org.egov.eis.web.contract.ProcessInstance;
import org.egov.eis.web.contract.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LeaveApplicationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LeaveApplicationRowMapper leaveApplicationRowMapper;

    @Autowired
    private LeaveApplicationQueryBuilder leaveApplicationQueryBuilder;

    @Autowired
    private WorkFlowService workFlowService;

    public List<LeaveApplication> findForCriteria(final LeaveApplicationGetRequest leaveApplicationGetRequest) {
        final List<Object> preparedStatementValues = new ArrayList<Object>();
        final String queryStr = leaveApplicationQueryBuilder.getQuery(leaveApplicationGetRequest, preparedStatementValues);
        final List<LeaveApplication> leaveApplications = jdbcTemplate.query(queryStr, preparedStatementValues.toArray(),
                leaveApplicationRowMapper);
        return leaveApplications;
    }

    public LeaveApplicationRequest saveLeaveApplication(final LeaveApplicationRequest leaveApplicationRequest) {
        ProcessInstance processInstance = new ProcessInstance();
        Long stateId = null;
        if (leaveApplicationRequest.getLeaveApplication().size() > 1)
            processInstance = workFlowService.start(leaveApplicationRequest);
        if (processInstance.getId() != null)
            stateId = Long.valueOf(processInstance.getId());
        final String leaveApplicationInsertQuery = LeaveApplicationQueryBuilder.insertLeaveApplicationQuery();
        final Date now = new Date();
        for (LeaveApplication leaveApplication : leaveApplicationRequest.getLeaveApplication()) {
            leaveApplication.setStateId(stateId);
            leaveApplicationStatusChange(leaveApplication);
            final Object[] obj = new Object[] { leaveApplication.getApplicationNumber(), leaveApplication.getEmployee(),
                    leaveApplication.getLeaveType().getId(),
                    leaveApplication.getFromDate(), leaveApplication.getToDate(), leaveApplication.getCompensatoryForDate(),
                    leaveApplication.getLeaveDays(),
                    leaveApplication.getAvailableDays(), leaveApplication.getHalfdays(), leaveApplication.getFirstHalfleave(),
                    leaveApplication.getReason(),
                    leaveApplication.getStatus().toString(), leaveApplication.getStateId(),
                    leaveApplicationRequest.getRequestInfo().getUserInfo().getId(),
                    now, leaveApplicationRequest.getRequestInfo().getUserInfo().getId(), now, leaveApplication.getTenantId() };
            jdbcTemplate.update(leaveApplicationInsertQuery, obj);
        }
        return leaveApplicationRequest;
    }

    public LeaveApplication updateLeaveApplication(final LeaveApplicationSingleRequest leaveApplicationRequest) {
        final Task task = workFlowService.update(leaveApplicationRequest);
        final String leaveApplicationInsertQuery = LeaveApplicationQueryBuilder.updateLeaveApplicationQuery();
        final Date now = new Date();
        final LeaveApplication leaveApplication = leaveApplicationRequest.getLeaveApplication();
        leaveApplication.setStateId(Long.valueOf(task.getId()));
        leaveApplicationStatusChange(leaveApplication);
        final Object[] obj = new Object[] { leaveApplication.getApplicationNumber(), leaveApplication.getEmployee(),
                leaveApplication.getLeaveType().getId(),
                leaveApplication.getFromDate(), leaveApplication.getToDate(), leaveApplication.getCompensatoryForDate(),
                leaveApplication.getLeaveDays(),
                leaveApplication.getAvailableDays(), leaveApplication.getHalfdays(), leaveApplication.getFirstHalfleave(),
                leaveApplication.getReason(),
                leaveApplication.getStatus().toString(), Long.valueOf(task.getId()),
                leaveApplicationRequest.getRequestInfo().getUserInfo().getId(), now, leaveApplication.getId(),
                leaveApplication.getTenantId() };
        jdbcTemplate.update(leaveApplicationInsertQuery, obj);
        return leaveApplication;
    }

    private void leaveApplicationStatusChange(final LeaveApplication leaveApplication) {
        final String workFlowAction = leaveApplication.getWorkflowDetails().getAction();
        if ("Approve".equalsIgnoreCase(workFlowAction))
            leaveApplication.setStatus(LeaveStatus.APPROVED);
        else if ("Reject".equalsIgnoreCase(workFlowAction))
            leaveApplication.setStatus(LeaveStatus.REJECTED);
        else if ("Cancel".equalsIgnoreCase(workFlowAction))
            leaveApplication.setStatus(LeaveStatus.CANCELLED);
        else if ("Submit".contains(workFlowAction))
            leaveApplication.setStatus(LeaveStatus.RESUBMITTED);
    }
}