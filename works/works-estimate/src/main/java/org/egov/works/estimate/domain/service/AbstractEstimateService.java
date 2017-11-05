package org.egov.works.estimate.domain.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.egov.works.commons.exception.ErrorCode;
import org.egov.works.commons.exception.InvalidDataException;
import org.egov.works.commons.utils.CommonUtils;
import org.egov.works.estimate.config.PropertiesManager;
import org.egov.works.estimate.config.WorksEstimateServiceConstants;
import org.egov.works.estimate.domain.repository.AbstractEstimateRepository;
import org.egov.works.estimate.persistence.repository.IdGenerationRepository;
import org.egov.works.estimate.utils.EstimateUtils;
import org.egov.works.estimate.web.contract.AbstractEstimate;
import org.egov.works.estimate.web.contract.AbstractEstimateDetails;
import org.egov.works.estimate.web.contract.AbstractEstimateRequest;
import org.egov.works.estimate.web.contract.AbstractEstimateSearchContract;
import org.egov.works.estimate.web.contract.AbstractEstimateStatus;
import org.egov.works.estimate.web.contract.AuditDetails;
import org.egov.works.estimate.web.contract.RequestInfo;
import org.egov.works.estimate.web.contract.WorkFlowDetails;
import org.egov.works.workflow.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import net.minidev.json.JSONArray;

@Service
@Transactional(readOnly = true)
public class AbstractEstimateService {
	
	public static final String ABSTRACT_ESTIMATE_WF_TYPE = "AbstractEstimate";

	public static final String ABSTRACT_ESTIMATE_BUSINESSKEY = "AbstractEstimate";

	@Autowired
	private AbstractEstimateRepository abstractEstimateRepository;

	@Autowired
	private LogAwareKafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private PropertiesManager propertiesManager;

	@Autowired
	private IdGenerationRepository idGenerationRepository;

	@Autowired
	private EstimateUtils estimateUtils;
	
	@Autowired
	private CommonUtils commonUtils;

	@Autowired
	private WorkflowService workflowService;

	@Transactional
	public List<AbstractEstimate> create(AbstractEstimateRequest abstractEstimateRequest) {
		for (final AbstractEstimate estimate : abstractEstimateRequest.getAbstractEstimates()) {
			estimate.setId(commonUtils.getUUID());
			estimate.setAuditDetails(
					setAuditDetails(abstractEstimateRequest.getRequestInfo().getUserInfo().getUsername(), false));
			String abstractEstimateNumber = idGenerationRepository
					.generateAbstractEstimateNumber(estimate.getTenantId(), abstractEstimateRequest.getRequestInfo());
			estimate.setAbstractEstimateNumber(propertiesManager.getEstimateNumberPrefix() + "/"
					+ estimate.getDepartment().getCode() + abstractEstimateNumber);
			for (final AbstractEstimateDetails details : estimate.getAbstractEstimateDetails()) {
				details.setId(commonUtils.getUUID());
				details.setAuditDetails(
						setAuditDetails(abstractEstimateRequest.getRequestInfo().getUserInfo().getUsername(), false));
			}
			populateWorkFlowDetails(estimate, abstractEstimateRequest.getRequestInfo());
			estimate.setStateId(workflowService.enrichWorkflow(estimate.getWorkFlowDetails(), estimate.getTenantId(),
					abstractEstimateRequest.getRequestInfo()));
			estimate.setStatus(AbstractEstimateStatus.CREATED);
		}
		kafkaTemplate.send(propertiesManager.getWorksAbstractEstimateCreateTopic(), abstractEstimateRequest);
		return abstractEstimateRequest.getAbstractEstimates();
	}

	public List<AbstractEstimate> update(AbstractEstimateRequest abstractEstimateRequest) {
		for (final AbstractEstimate estimate : abstractEstimateRequest.getAbstractEstimates()) {
			populateWorkFlowDetails(estimate, abstractEstimateRequest.getRequestInfo());
			estimate.setAuditDetails(
					setAuditDetails(abstractEstimateRequest.getRequestInfo().getUserInfo().getUsername(), true));
			estimate.setStateId(workflowService.enrichWorkflow(estimate.getWorkFlowDetails(), estimate.getTenantId(),
					abstractEstimateRequest.getRequestInfo()));
			populateNextStatus(estimate);
		}
		kafkaTemplate.send(propertiesManager.getWorksAbstractEstimateUpdateTopic(), abstractEstimateRequest);
		return abstractEstimateRequest.getAbstractEstimates();
	}
	
	private void populateWorkFlowDetails(AbstractEstimate abstractEstimate, RequestInfo requestInfo) {

		if (null != abstractEstimate && null != abstractEstimate.getWorkFlowDetails()) {

			WorkFlowDetails workFlowDetails = abstractEstimate.getWorkFlowDetails();

			workFlowDetails.setType(ABSTRACT_ESTIMATE_WF_TYPE);
			workFlowDetails.setBusinessKey(ABSTRACT_ESTIMATE_BUSINESSKEY);
			workFlowDetails.setStateId(abstractEstimate.getStateId());
			if (abstractEstimate.getStatus() != null)
				workFlowDetails.setStatus(abstractEstimate.getStatus().toString());

			if (null != requestInfo && null != requestInfo.getUserInfo()) {
				workFlowDetails.setSenderName(requestInfo.getUserInfo().getUsername());
			}

			if (abstractEstimate.getStateId() != null) {
				workFlowDetails.setStateId(abstractEstimate.getStateId());
			}
		}
	}
	
	private void populateNextStatus(AbstractEstimate abstractEstimate) {
		WorkFlowDetails workFlowDetails = null;
		String currentStatus = null;

		if (null != abstractEstimate && null != abstractEstimate.getStatus()) {
			workFlowDetails = abstractEstimate.getWorkFlowDetails();
			currentStatus = abstractEstimate.getStatus().toString();
		}

		if (null != workFlowDetails && null != workFlowDetails.getAction() && null != currentStatus
				&& workFlowDetails.getAction().equalsIgnoreCase("Submit")
				&& (currentStatus.equalsIgnoreCase(AbstractEstimateStatus.CREATED.toString())
				|| currentStatus.equalsIgnoreCase(AbstractEstimateStatus.RESUBMITTED.toString()))) {
			abstractEstimate.setStatus(AbstractEstimateStatus.CHECKED);
		}

		if (null != workFlowDetails && null != workFlowDetails.getAction() && null != currentStatus
				&& workFlowDetails.getAction().equalsIgnoreCase("Approve")
				&& currentStatus.equalsIgnoreCase(AbstractEstimateStatus.CHECKED.toString())) {
			abstractEstimate.setStatus(AbstractEstimateStatus.APPROVED);
		}

		if (null != workFlowDetails && null != workFlowDetails.getAction() && null != currentStatus
				&& workFlowDetails.getAction().equalsIgnoreCase("Reject")) {
			abstractEstimate.setStatus(AbstractEstimateStatus.REJECTED);
		}
		
		if (null != workFlowDetails && null != workFlowDetails.getAction() && null != currentStatus
				&& workFlowDetails.getAction().equalsIgnoreCase("Forward")
				&& currentStatus.equalsIgnoreCase(AbstractEstimateStatus.REJECTED.toString())) {
			abstractEstimate.setStatus(AbstractEstimateStatus.RESUBMITTED);
		}
		
		if (null != workFlowDetails && null != workFlowDetails.getAction() && null != currentStatus
				&& workFlowDetails.getAction().equalsIgnoreCase("Cancel")
				&& currentStatus.equalsIgnoreCase(AbstractEstimateStatus.REJECTED.toString())) {
			abstractEstimate.setStatus(AbstractEstimateStatus.CANCELLED);
		}
	}

	public List<AbstractEstimate> search(AbstractEstimateSearchContract abstractEstimateSearchContract) {
		return abstractEstimateRepository.search(abstractEstimateSearchContract);
	}

	public void validateEstimates(AbstractEstimateRequest abstractEstimateRequest, BindingResult errors,
			Boolean isNew) {
		for (final AbstractEstimate estimate : abstractEstimateRequest.getAbstractEstimates()) {
			if (estimate.getDateOfProposal() == null)
				throw new InvalidDataException("dateOfProposal", ErrorCode.NOT_NULL.getCode(), null);
			if (!estimate.getSpillOverFlag() && estimate.getDateOfProposal() != null
					&& new Date(estimate.getDateOfProposal()).after(new Date()))
				throw new InvalidDataException("dateOfProposal", "dateofproposal.future.date",
						estimate.getDateOfProposal().toString());
			if (estimate.getTenantId() == null)
				throw new InvalidDataException("tenantId", ErrorCode.MANDATORY_VALUE_MISSING.getCode(),
						estimate.getTenantId());
			validateMasterData(estimate, errors, abstractEstimateRequest.getRequestInfo());

			AbstractEstimateSearchContract searchContract = new AbstractEstimateSearchContract();
			if (estimate.getId() != null)
				searchContract.setIds(Arrays.asList(estimate.getId()));
			searchContract.setAbstractEstimateNumbers(Arrays.asList(estimate.getAbstractEstimateNumber()));
			searchContract.setTenantId(estimate.getTenantId());
			List<AbstractEstimate> oldEstimates = search(searchContract);
			if (isNew && !oldEstimates.isEmpty())
				throw new InvalidDataException("abstractEstimateNumber", ErrorCode.NON_UNIQUE_VALUE.getCode(),
						estimate.getAbstractEstimateNumber());
			searchContract.setAbstractEstimateNumbers(null);
			if (!isNew && estimate.getWorkFlowDetails() != null
					&& "Approve".equalsIgnoreCase(estimate.getWorkFlowDetails().getAction())) {
				if (StringUtils.isBlank(estimate.getAdminSanctionNumber()))
					throw new InvalidDataException("adminSanctionNumber", ErrorCode.NON_UNIQUE_VALUE.getCode(),
							estimate.getAdminSanctionNumber());
				if (StringUtils.isBlank(estimate.getCouncilResolutionNumber()))
					throw new InvalidDataException("councilResolutionNumber", ErrorCode.NON_UNIQUE_VALUE.getCode(),
							estimate.getCouncilResolutionNumber());
				if (estimate.getCouncilResolutionDate() == null)
					throw new InvalidDataException("councilResolutionDate", ErrorCode.NON_UNIQUE_VALUE.getCode(),
							estimate.getCouncilResolutionDate().toString());
				searchContract.setAdminSanctionNumbers(Arrays.asList(estimate.getAdminSanctionNumber()));
				oldEstimates = search(searchContract);
				if (!oldEstimates.isEmpty() && !estimate.getId().equalsIgnoreCase(oldEstimates.get(0).getId()))
					throw new InvalidDataException("adminSanctionNumber", ErrorCode.NOT_NULL.getCode(),
							estimate.getAdminSanctionNumber());
			}
			validateEstimateDetails(estimate, errors, isNew);
		}
	}

	private void validateEstimateDetails(AbstractEstimate estimate, BindingResult errors, Boolean isNew) {
		BigDecimal estimateAmount = BigDecimal.ZERO;
		for (final AbstractEstimateDetails aed : estimate.getAbstractEstimateDetails()) {
			estimateAmount = estimateAmount.add(aed.getEstimateAmount());
			AbstractEstimateSearchContract searchContract = new AbstractEstimateSearchContract();
			if (estimate.getId() != null)
				searchContract.setIds(Arrays.asList(estimate.getId()));
			searchContract.setEstimateNumbers(Arrays.asList(aed.getEstimateNumber()));
			searchContract.setTenantId(estimate.getTenantId());
			List<AbstractEstimate> oldEstimates = search(searchContract);
			if (isNew && !oldEstimates.isEmpty())
				throw new InvalidDataException("estimateNumber", ErrorCode.NON_UNIQUE_VALUE.getCode(),
						aed.getEstimateNumber());
		}
		if (Double.parseDouble(estimateAmount.toString()) <= 0)
			throw new InvalidDataException("estimateAmount", "estimateamount.notvalid", estimateAmount.toString());
	}

	public void validateMasterData(AbstractEstimate abstractEstimate, BindingResult errors, RequestInfo requestInfo) {

		JSONArray responseJSONArray = null;

		if (abstractEstimate.getFund() != null && StringUtils.isNotBlank(abstractEstimate.getFund().getCode())) {
			responseJSONArray = estimateUtils.getMDMSData(WorksEstimateServiceConstants.FUND_OBJECT,
					abstractEstimate.getFund().getCode(), null, abstractEstimate.getTenantId(), requestInfo,
					WorksEstimateServiceConstants.WORKS_MODULE_CODE);
			if (responseJSONArray != null && responseJSONArray.isEmpty()) {
				throw new InvalidDataException("Fund", "Invalid data for fund code",
						abstractEstimate.getFund().getCode());
			}
		}
		if (abstractEstimate.getFunction() != null
				&& StringUtils.isNotBlank(abstractEstimate.getFunction().getCode())) {
			responseJSONArray = estimateUtils.getMDMSData(WorksEstimateServiceConstants.FUNCTION_OBJECT,
					abstractEstimate.getFunction().getCode(), null, abstractEstimate.getTenantId(), requestInfo,
					WorksEstimateServiceConstants.WORKS_MODULE_CODE);
			if (responseJSONArray != null && responseJSONArray.isEmpty()) {
				throw new InvalidDataException("Function", "Invalid data for function code",
						abstractEstimate.getFunction().getCode());
			}
		}

		if (abstractEstimate.getTypeOfWork() != null
				&& StringUtils.isNotBlank(abstractEstimate.getTypeOfWork().getCode())) {
			responseJSONArray = estimateUtils.getMDMSData(WorksEstimateServiceConstants.TYPEOFWORK_OBJECT,
					abstractEstimate.getTypeOfWork().getCode(), null, abstractEstimate.getTenantId(), requestInfo,
					WorksEstimateServiceConstants.WORKS_MODULE_CODE);
			if (responseJSONArray != null && responseJSONArray.isEmpty()) {
				throw new InvalidDataException("TypeOfWork", "Invalid data for estimate type of work",
						abstractEstimate.getTypeOfWork().getCode());
			}
		}
		if (abstractEstimate.getSubTypeOfWork() != null
				&& StringUtils.isNotBlank(abstractEstimate.getSubTypeOfWork().getCode())) {
			responseJSONArray = estimateUtils.getMDMSData(WorksEstimateServiceConstants.SUBTYPEOFWORK_OBJECT,
					abstractEstimate.getSubTypeOfWork().getCode(), null, abstractEstimate.getTenantId(), requestInfo,
					WorksEstimateServiceConstants.WORKS_MODULE_CODE);
			if (responseJSONArray != null && responseJSONArray.isEmpty()) {
				throw new InvalidDataException("SubTypeOfWork", "Invalid data for estimate subtype of work",
						abstractEstimate.getSubTypeOfWork().getCode());
			}
		}
		if (abstractEstimate.getDepartment() != null
				& StringUtils.isNotBlank(abstractEstimate.getDepartment().getCode())) {
			responseJSONArray = estimateUtils.getMDMSData(WorksEstimateServiceConstants.DEPARTMENT_OBJECT,
					abstractEstimate.getDepartment().getCode(), null, abstractEstimate.getTenantId(), requestInfo,
					WorksEstimateServiceConstants.COMMON_MASTERS_MODULE_CODE);
			if (responseJSONArray != null && responseJSONArray.isEmpty()) {
				throw new InvalidDataException("Department", "Invalid data for estimate Department",
						abstractEstimate.getDepartment().getCode());
			}
		}
		if (abstractEstimate.getScheme() != null & StringUtils.isNotBlank(abstractEstimate.getScheme().getCode())) {
			responseJSONArray = estimateUtils.getMDMSData(WorksEstimateServiceConstants.SCHEME_OBJECT,
					abstractEstimate.getScheme().getCode(), null, abstractEstimate.getTenantId(), requestInfo,
					WorksEstimateServiceConstants.WORKS_MODULE_CODE);
			if (responseJSONArray != null && responseJSONArray.isEmpty()) {
				throw new InvalidDataException("Scheme", "Invalid data for estimate scheme",
						abstractEstimate.getScheme().getCode());
			}
		}

		if (abstractEstimate.getSubScheme() != null
				& StringUtils.isNotBlank(abstractEstimate.getSubScheme().getCode())) {
			responseJSONArray = estimateUtils.getMDMSData(WorksEstimateServiceConstants.SUBSCHEME_OBJECT,
					abstractEstimate.getSubScheme().getCode(), null, abstractEstimate.getTenantId(), requestInfo,
					WorksEstimateServiceConstants.WORKS_MODULE_CODE);
			if (responseJSONArray != null && responseJSONArray.isEmpty()) {
				throw new InvalidDataException("SubScheme", "Invalid data for estimate SubScheme",
						abstractEstimate.getSubScheme().getCode());
			}
		}

		if (abstractEstimate.getBudgetGroup() != null
				& StringUtils.isNotBlank(abstractEstimate.getBudgetGroup().getName())) {
			responseJSONArray = estimateUtils.getMDMSData(WorksEstimateServiceConstants.BUDGETGROUP_OBJECT, null,
					abstractEstimate.getBudgetGroup().getName(), abstractEstimate.getTenantId(), requestInfo,
					WorksEstimateServiceConstants.WORKS_MODULE_CODE);
			if (responseJSONArray != null && responseJSONArray.isEmpty()) {
				throw new InvalidDataException("BudgetGroup", "Invalid data for estimate Budget Group",
						abstractEstimate.getBudgetGroup().getName());
			}
		}

		/*
		 * * if(abstractEstimate.getWard() != null &
		 * StringUtils.isNotBlank(abstractEstimate.getWard().getCode())) {
		 * responseJSONArray =
		 * estimateUtils.getAppConfigurationData(WorksEstimateServiceConstants.
		 * DEPARTMENT_OBJECT,abstractEstimate.getWard().getCode(),
		 * abstractEstimate.getTenantId(),requestInfo); if(responseJSONArray !=
		 * null && responseJSONArray.isEmpty()) { throw new
		 * InvalidDataException("Boundary",
		 * "Invalid data for estimate Ward boundary",
		 * abstractEstimate.getWard().getCode()); } }
		 * if(abstractEstimate.getLocality() != null &
		 * StringUtils.isNotBlank(abstractEstimate.getLocality().getCode())) {
		 * responseJSONArray =
		 * estimateUtils.getAppConfigurationData(WorksEstimateServiceConstants.
		 * DEPARTMENT_OBJECT,abstractEstimate.getLocality().getCode(),
		 * abstractEstimate.getTenantId(),requestInfo); if(responseJSONArray !=
		 * null && responseJSONArray.isEmpty()) { throw new
		 * InvalidDataException("Boundary",
		 * "Invalid data for estimate locality boundary",
		 * abstractEstimate.getLocality().getCode()); } }
		 */

	}

	public AuditDetails setAuditDetails(final String userName, final Boolean isUpdate) {
		AuditDetails auditDetails = new AuditDetails();
		if (!isUpdate) {
			auditDetails.setCreatedBy(userName);
			auditDetails.setCreatedTime(new Date().getTime());
		}
		auditDetails.setLastModifiedBy(userName);
		auditDetails.setLastModifiedTime(new Date().getTime());

		return auditDetails;
	}

}
