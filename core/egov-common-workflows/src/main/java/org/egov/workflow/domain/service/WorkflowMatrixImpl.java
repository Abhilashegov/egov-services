package org.egov.workflow.domain.service;

import org.egov.workflow.domain.exception.InvalidDataException;
import org.egov.workflow.domain.exception.NoDataFoundException;
import org.egov.workflow.domain.model.WorkflowConstants;
import org.egov.workflow.persistence.entity.State;
import org.egov.workflow.persistence.entity.State.StateStatus;
import org.egov.workflow.persistence.entity.StateHistory;
import org.egov.workflow.persistence.entity.WorkFlowMatrix;
import org.egov.workflow.persistence.entity.WorkflowTypes;
import org.egov.workflow.persistence.repository.EmployeeRepository;
import org.egov.workflow.persistence.repository.PositionRepository;
import org.egov.workflow.persistence.service.StateService;
import org.egov.workflow.persistence.service.WorkFlowMatrixService;
import org.egov.workflow.persistence.service.WorkflowTypesService;
import org.egov.workflow.web.contract.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkflowMatrixImpl implements Workflow {

	private static Logger LOG = LoggerFactory.getLogger(WorkflowMatrixImpl.class);
	// private List<Object> approverList;

	@Autowired
	private EmployeeRepository employeeRepository;
	// @Autowired
	// private UserRepository userRepository;
	@Autowired
	private StateService stateService;
	@Autowired
	private WorkFlowMatrixService workflowService;

	@Autowired
	private WorkflowTypesService workflowTypeService;
	@Autowired
	private PositionRepository positionRepository;

	@Transactional
	@Override
	public ProcessInstanceResponse start(ProcessInstanceRequest processInstanceRequest) {
		LOG.debug(processInstanceRequest.toString());
		ProcessInstance processInstance = processInstanceRequest.getProcessInstance();
		final WorkFlowMatrix wfMatrix = workflowService.getWfMatrix(processInstance.getBusinessKey(), null, null, null,
				null, null,processInstance.getTenantId());
		Position owner = processInstance.getAssignee();
		if (processInstance.getAssignee() != null)
			owner = positionRepository.getById(Long.valueOf(processInstance.getAssignee().getId()),
					processInstanceRequest.getRequestInfo());

		final State state = new State();
		state.setTenantId(processInstanceRequest.getRequestInfo().getUserInfo().getTenantId());
		state.setType(processInstance.getType());
		state.setSenderName(processInstance.getSenderName());
		state.setStatus(StateStatus.INPROGRESS);
		state.setValue(wfMatrix.getNextState());
		state.setComments(processInstance.getComments());
		if (owner == null) {
			LOG.error("Owner info is not availble from respective service");
			state.setOwnerPosition(processInstance.getAssignee().getId());
		} else {
			state.setOwnerPosition(owner.getId());
		}

		RequestInfo requestInfo = processInstanceRequest.getRequestInfo();

		if (processInstance.getInitiatorPosition() != null)
			state.setInitiatorPosition(processInstance.getInitiatorPosition());
		else {
			Position initiator = positionRepository.getPrimaryPositionByEmployeeId(requestInfo.getUserInfo().getId(),
					requestInfo);
			if (initiator != null && initiator.getId() != null)
				state.setInitiatorPosition(initiator.getId());
		}

		state.setNextAction(wfMatrix.getNextAction());
		state.setType(processInstance.getBusinessKey());

		final WorkflowTypes type = workflowTypeService.getWorkflowTypeByTypeAndTenantId(state.getType(),
				processInstance.getTenantId());
		state.setMyLinkId(type.getLink());

		state.setNatureOfTask(type.getDisplayName());
		state.setExtraInfo(processInstance.getDetails());
		updateAuditDetails(state, processInstanceRequest.getRequestInfo().getUserInfo());
		stateService.create(state);
		processInstance = state.mapToProcess(processInstance);

        ProcessInstanceResponse response = ProcessInstanceResponse.builder().processInstance(processInstance).build();
		return response;
	}

	@Override
	public ProcessInstanceResponse end(ProcessInstanceRequest processInstanceRequest) {
		return null;
	}

	private void updateAuditDetails(State s, User u) {
		LOG.debug("Updating Logged in user Information... ");
		s.setCreatedBy(u.getId());
		s.setLastModifiedBy(u.getId());
		s.setCreatedDate(new Date());
		s.setLastModifiedDate(new Date());
		LOG.debug("Updating Logged in user Information complete. ");
	}

/*	private Position getInitiator() {
		Position position = null;
		try {

			String code = employeeRepository.getEmployeeForUserId(1l).getEmployees().get(0).getCode();
			List<Position> byEmployeeCode = positionRepository.getByEmployeeId(code, null);
			byEmployeeCode.get(0);
		} catch (final Exception e) {
			LOG.error("Error while setting initiator position");
		}
		return position;
	}*/
/*
	private Employee getEmp(Long userId) {
		Employee emp = null;
		try {

			emp = employeeRepository.getEmployeeForUserId(userId).getEmployees().get(0);

		} catch (final Exception e) {
			LOG.error("Error while setting initiator position");
		}
		return emp;
	}*/

	@Transactional
	@Override
	public TaskResponse update(final TaskRequest taskRequest) {
		LOG.debug("Update task api " + taskRequest.toString());
		Task task = taskRequest.getTask();
		Position owner = task.getAssignee();
		Long ownerId = task.getAssignee().getId();
		if (task.getAssignee() != null && task.getAssignee().getId() != null)
			owner = positionRepository.getById(Long.valueOf(task.getAssignee().getId()), taskRequest.getRequestInfo());
		// final WorkflowEntity entity = task.getEntity();
		String dept = null;
		if (task.getAttributes() != null && task.getAttributes().get("department") != null)
			dept = task.getAttributes().get("department").getCode();
		final WorkFlowMatrix wfMatrix = workflowService.getWfMatrix(task.getBusinessKey(), dept, null, null,
				task.getStatus(), null,task.getTenantId());

		String nextState = wfMatrix.getNextState();
		final State state = stateService.findOne(Long.valueOf(task.getId()));
		if ("END".equalsIgnoreCase(wfMatrix.getNextAction()))
			state.setStatus(State.StateStatus.ENDED);
		else
			state.setStatus(State.StateStatus.INPROGRESS);

		if (task.getAction().equalsIgnoreCase(WorkflowConstants.ACTION_REJECT)) {
			ownerId = state.getInitiatorPosition();
			if (ownerId != null) {
				task.setAssignee(Position.builder().id(ownerId).build());
			}
			// below logic required to show the messages only....
			/*
			 * final Attribute approverDesignationName = new Attribute();
			 * approverDesignationName.setCode(owner.getDeptdesig().
			 * getDesignation().getName());
			 * task.getAttributes().put("approverDesignationName",
			 * approverDesignationName);
			 * 
			 * final Attribute approverName = new Attribute();
			 * approverName.setCode(getApproverName(owner));
			 * task.getAttributes().put("approverName", approverName);
			 */
			nextState = "Rejected";
		}
		if (task.getAction().equalsIgnoreCase(WorkflowConstants.ACTION_CANCEL)) {
			state.setStatus(State.StateStatus.ENDED);
			nextState = State.DEFAULT_STATE_VALUE_CLOSED;
		}

		state.addStateHistory(new StateHistory(state));

		state.setTenantId(taskRequest.getRequestInfo().getUserInfo().getTenantId());
		state.setValue(nextState);
		state.setComments(task.getComments());
		state.setSenderName(taskRequest.getRequestInfo().getUserInfo().getName());
		if (owner != null && owner.getId() != null)
			state.setOwnerPosition(owner.getId());
		else
			state.setOwnerPosition(task.getAssignee().getId());
		state.setNextAction(wfMatrix.getNextAction());
		state.setType(task.getBusinessKey());
		if (task.getDetails() != null && !task.getDetails().isEmpty())
			state.setExtraInfo(task.getDetails());
		stateService.create(state);
		Task t = state.map();

		stateService.update(state);
		TaskResponse response = new TaskResponse();
		response.setTask(t);
		LOG.debug("Update task api completed . And response sent back is :" + response.toString());
		return response;
	}

/*	private String getApproverName(final Position owner) {
		String approverName = null;
		try {
			approverName = getEmp(1l).getName();
		} catch (final Exception e) {
			LOG.error("error while fetching users name");
		}
		return approverName;
	}*/

	private String getNextAction(final WorkflowBean workflowBean) {

		WorkFlowMatrix wfMatrix = null;
		if (null != workflowBean && null != workflowBean.getWorkflowId())
			wfMatrix = workflowService.getWfMatrix(workflowBean.getBusinessKey(), workflowBean.getWorkflowDepartment(),
					workflowBean.getAmountRule(), workflowBean.getAdditionalRule(), workflowBean.getCurrentState(),
					workflowBean.getPendingActions(), workflowBean.getCreatedDate(),workflowBean.getTenantId());
		else
			wfMatrix = workflowService.getWfMatrix(workflowBean.getBusinessKey(), workflowBean.getWorkflowDepartment(),
					workflowBean.getAmountRule(), workflowBean.getAdditionalRule(), State.DEFAULT_STATE_VALUE_CREATED,
					workflowBean.getPendingActions(), workflowBean.getCreatedDate(),workflowBean.getTenantId());
		return wfMatrix == null ? "" : wfMatrix.getNextAction();
	}

	/**
	 * @param model
	 * @param container
	 * @return List of WorkFlow Buttons From Matrix By Passing parametres
	 *         Type,CurrentState,CreatedDate
	 */
	private List<Value> getValidActions(final WorkflowBean workflowBean) {
		List<Value> values = new ArrayList<Value>();
		List<String> validActions = Collections.emptyList();
		if (null == workflowBean || workflowBean.getWorkflowId() == null)
			validActions = workflowService.getNextValidActions(workflowBean.getBusinessKey(),
					workflowBean.getWorkflowDepartment(), workflowBean.getAmountRule(),
					workflowBean.getAdditionalRule(), "NEW", workflowBean.getPendingActions(),
					workflowBean.getCreatedDate(), workflowBean.getTenantId());
		else if (null != workflowBean.getWorkflowId())
			validActions = workflowService.getNextValidActions(workflowBean.getBusinessKey(),
					workflowBean.getWorkflowDepartment(), workflowBean.getAmountRule(),
					workflowBean.getAdditionalRule(), workflowBean.getCurrentState(), workflowBean.getPendingActions(),
					workflowBean.getCreatedDate(), workflowBean.getTenantId());
		Value v = null;
		for (String s : validActions) {
			v = new Value(s, s);
			values.add(v);
		}
		return values;
	}

	@Override
	public ProcessInstance getProcess(final String jurisdiction, final ProcessInstance processInstance,final RequestInfo requestInfo) {
		LOG.debug("Starting getProcess for  " + processInstance.toString() + " for tenant" + jurisdiction);
		final WorkflowBean wfbean = new WorkflowBean();
		processInstance.setTenantId(jurisdiction);
		State state = null;
		if (processInstance.getId() != null && !processInstance.getId().isEmpty())
			state = stateService.findOne(Long.valueOf(processInstance.getId()));
		if (state != null) {
			processInstance.setBusinessKey(state.getType());
			if (state.getOwnerPosition() != null)
				processInstance.setOwner(positionRepository.getById(state.getOwnerPosition(), requestInfo));
			else if (state.getOwnerUser() != null)
				processInstance.setOwner(positionRepository.getById(state.getOwnerUser(), requestInfo));

			if (processInstance.getOwner() == null) {
				Position p = Position.builder().build();
				if (state.getOwnerPosition() != null)
					p.setId(state.getOwnerPosition());
				else if (state.getOwnerUser() != null)
					p.setId(state.getOwnerUser());
				processInstance.setOwner(p);
			}
			processInstance.setStatus(state.getValue());
			processInstance.setState(state.getStatus().toString());
			processInstance.setSenderName(state.getSenderName());
			processInstance.setComments(state.getComments());
			processInstance.setCreatedDate(state.getCreatedDate());
			processInstance.setLastupdatedSince(state.getLastModifiedDate());
			processInstance.setInitiatorPosition(state.getInitiatorPosition());
		} else {
			throw new NoDataFoundException("ProcessInstance with id " + processInstance.getId() + " not found");
		}
		// processInstance.getEntity().setProcessInstance(processInstance);
		wfbean.map(processInstance);
		processInstance.setAttributes(new HashMap<>());
		final Attribute validActions = new Attribute();
		validActions.setValues(getValidActions(wfbean));
		processInstance.getAttributes().put("validActions", validActions);
		final Attribute nextAction = new Attribute();
		nextAction.setCode(getNextAction(wfbean));
		processInstance.getAttributes().put("nextAction", nextAction);
		LOG.debug("Starting getProcess complted. And response sent back is " + processInstance);
		return processInstance;
	}

	@Override
	public TaskResponse getTasks(TaskRequest taskRequest) {
		LOG.debug("Starting getTasks for " + taskRequest + " for tenant " + taskRequest.getRequestInfo().getTenantId());
		final List<Task> tasks = new ArrayList<Task>();
		final Long userId = taskRequest.getRequestInfo().getUserInfo().getId();
		final List<String> types = workflowTypeService.getEnabledWorkflowType(false);
		final List<Long> ownerIds = positionRepository.getByEmployeeId(userId.toString(), taskRequest.getRequestInfo())
				.parallelStream().map(position -> position.getId()).collect(Collectors.toList());
		List<State> states = new ArrayList<State>();
		if (!types.isEmpty())
			states = stateService.getStates(ownerIds, types, userId,taskRequest.getRequestInfo().getTenantId());
		for (final State s : states)
			tasks.add(s.map());

		LOG.debug("getTasks completed for tenant " + taskRequest.getRequestInfo().getTenantId());
		if (LOG.isTraceEnabled())
			LOG.trace("Taks list returned" + tasks);
		TaskResponse response = new TaskResponse();
		response.setTasks(tasks);
		return response;
	}

	@Override
	public List<Task> getHistoryDetail(final String tenantId, final String workflowId) {
		LOG.debug("Starting getHistoryDetail for " + workflowId + " for tenant " + tenantId);
		final List<Task> tasks = new ArrayList<Task>();
		Task t;
		final State state = stateService.findOne(Long.valueOf(workflowId));
		final Set<StateHistory> history = state.getHistory();
		for (final StateHistory stateHistory : history) {
			t = stateHistory.map();
			tasks.add(t);
		}
		t = state.map();
		tasks.add(t);
		LOG.debug("getHistoryDetail for " + workflowId + " for tenant " + tenantId + "completed.");
		if (LOG.isTraceEnabled()) {
			LOG.trace(tasks.toString());
		}
		return tasks;
	}



	@Override
	public Object getAssignee(Long locationId, String complaintTypeId, Long assigneeId, RequestInfo requestInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Designation> getDesignations(Task t, String departmentId) {
		LOG.debug("starting getDesignations " + t + " for department" + departmentId);
		if (t == null) {
			throw new InvalidDataException("Task", "task.required", "Task data is required");
		} else

		if (t.getBusinessKey() == null) {
			throw new InvalidDataException("businessKey", "task.businesskey.required", "businesskey data is required");
		} else if (departmentId == null) {
			throw new InvalidDataException("departmentId", "task.departmentId.required",
					"departmentId data is required");
		}
		Map<String, Attribute> attributes = t.getAttributes();
		String designation = null;
		String pendingAction = null;
		String additionalRule = null;
		String businessRule = null;

		Attribute attribute = attributes.get("businessRule");
		if (attribute != null)
			businessRule = attribute.getCode();
		BigDecimal amtRule = null;
		if (businessRule != null)
			amtRule = new BigDecimal(businessRule);

		attribute = attributes.get("additionalRule");
		if (attribute != null)
			additionalRule = attribute.getCode();

		pendingAction = t.getAction();

		attribute = attributes.get("designation");
		if (attribute != null)
			designation = attribute.getCode();

		String currentState = t.getStatus();
		if ("END".equals(currentState))
			currentState = "";

		List<Designation> nextDesignations = workflowService.getNextDesignations(t.getBusinessKey(), departmentId,
				amtRule, additionalRule, t.getStatus(), pendingAction, new Date(), designation, t.getTenantId());
		LOG.debug("getDesignations completed and returning " + nextDesignations);
		return nextDesignations;
	}

	@Override
	public ProcessInstance update(String jurisdiction, ProcessInstance processInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * * @Override public List<Object> getAssignee(final String deptCode, final
	 * String designationName) { final Department dept =
	 * departmentService.getDepartmentByCode(deptCode); final Long
	 * ApproverDepartmentId = dept.getId(); final Designation desig =
	 * designationService.getDesignationByName(designationName); final Long
	 * DesignationId = desig.getId(); if (DesignationId != null && DesignationId
	 * != -1) { final HashMap<String, String> paramMap = new HashMap<String,
	 * String>(); if (ApproverDepartmentId != null && ApproverDepartmentId !=
	 * -1) paramMap.put("departmentId", ApproverDepartmentId.toString());
	 * paramMap.put("DesignationId", DesignationId.toString()); approverList =
	 * new ArrayList<Object>(); final List<Assignment> assignmentList =
	 * assignmentService
	 * .findAllAssignmentsByDeptDesigAndDates(ApproverDepartmentId,
	 * DesignationId, new Date()); for (final Assignment assignment :
	 * assignmentList) approverList.add(assignment); } return approverList; }
	 */

}
