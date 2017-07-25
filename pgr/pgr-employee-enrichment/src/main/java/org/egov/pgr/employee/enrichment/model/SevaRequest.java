package org.egov.pgr.employee.enrichment.model;

import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.pgr.common.date.DateFormatter;
import org.egov.pgr.employee.enrichment.json.ObjectMapperFactory;
import org.egov.pgr.employee.enrichment.repository.contract.Attribute;
import org.egov.pgr.employee.enrichment.repository.contract.WorkflowRequest;
import org.egov.pgr.employee.enrichment.repository.contract.WorkflowResponse;

import java.util.*;

import static org.egov.pgr.employee.enrichment.repository.contract.WorkflowResponse.STATE_ID;

public class SevaRequest {

    public final static String SERVICE_REQUEST = "serviceRequest";
    public final static String REQUEST_INFO = "RequestInfo";
    public static final String VALUES_POSITION_ID = "systemPositionId";
    public static final String VALUES_STATE_ID = "systemStateId";
    private static final String VALUES_DESIGNATION_ID = "systemDesignationId";
    public static final String VALUES_DEPARTMENT_ID = "systemDepartmentId";
    public static final String VALUES_COMLAINT_TYPE_CODE = "complaintTypeCode";
    public static final String BOUNDARY_ID = "boundaryId";
    public static final String STATE_DETAILS = "stateDetails";
    private static final String WORKFLOW_TYPE = "Complaint";
    public static final String STATUS = "systemStatus";
    public static final String SERVICE_CODE = "serviceCode";
    public static final String VALUES_LOCATION_ID = "systemLocationId";
    public static final String VALUES_APPROVAL_COMMENT = "systemApprovalComments";
    public static final String USER_ROLE = "userRole";
    private static final String SERVICE_REQUEST_ID = "serviceRequestId";
    public static final String DEPARTMENT_ID = "systemDepartmentId";
    private static final String COMPLAINT_CRN = "crn";
    private static final String EXPECTED_DATETIME = "expectedDatetime";
    private static final String TENANT_ID = "tenantId";
    private static final String ESCALATION_HOURS = "systemEscalationHours";
    private static final String ATTRIBUTE_VALUES = "attribValues";
    private static final String ATTRIBUTE_VALUES_KEY_FIELD = "key";
    private static final String ATTRIBUTE_VALUES_NAME_FIELD = "name";
    private static final String POST = "POST";

    private HashMap<String, Object> sevaRequestMap;

    public SevaRequest(HashMap<String, Object> sevaRequestMap) {
        this.sevaRequestMap = sevaRequestMap;
    }

    public Long getPositionId() {
        final String positionId = getDynamicSingleValue(VALUES_POSITION_ID);
        return Long.valueOf(positionId);
    }

    private void setAssignee(String assignee) {
        createOrUpdateAttributeEntry(VALUES_POSITION_ID, assignee);
    }

    private void setStateId(String stateId) {
        createOrUpdateAttributeEntry(VALUES_STATE_ID, stateId);
    }

    public void setEscalationHours(String escalationHours) {
        createOrUpdateAttributeEntry(ESCALATION_HOURS, escalationHours);
    }

    private RequestInfo getRequestInfo() {
        return ObjectMapperFactory.create().convertValue(sevaRequestMap.get(REQUEST_INFO), RequestInfo.class);
    }

    public HashMap<String, Object> getRequestMap() {
        return sevaRequestMap;
    }

    public String getComplaintTypeCode() {
        return (String) this.getServiceRequest().get(SERVICE_CODE);
    }

    public WorkflowRequest getWorkFlowRequest() {
        HashMap<String, Object> serviceRequest = getServiceRequest();
        RequestInfo requestInfo = getRequestInfo();
        String complaintType = (String) serviceRequest.get(SERVICE_CODE);
        String crn = (String) serviceRequest.get(SERVICE_REQUEST_ID);
        Map<String, Attribute> valuesToSet = getWorkFlowRequestValues(complaintType);
        valuesToSet.put(COMPLAINT_CRN, Attribute.asStringAttr(COMPLAINT_CRN, crn));
        final String status = getDynamicSingleValue(STATUS);
        WorkflowRequest.WorkflowRequestBuilder workflowRequestBuilder = WorkflowRequest.builder()
            .positionId(getCurrentPositionId())
            .action(WorkflowRequest.Action.forComplaintStatus(status,isCreate()))
            .requestInfo(requestInfo)
            .values(valuesToSet)
            .status(status)
            .type(WORKFLOW_TYPE)
            .businessKey(WORKFLOW_TYPE)
            .tenantId(getTenantId())
            .crn(crn);

        return workflowRequestBuilder.build();
    }

    private Map<String, Attribute> getWorkFlowRequestValues(String complaintType) {
        final String locationId = getDynamicSingleValue(VALUES_LOCATION_ID);
        final String approvalComment = getDynamicSingleValue(VALUES_APPROVAL_COMMENT);
        final String departmentId = getDynamicSingleValue(DEPARTMENT_ID);
        Map<String, Attribute> valuesToSet = new HashMap<>();
        valuesToSet.put(VALUES_COMLAINT_TYPE_CODE, Attribute.asStringAttr(VALUES_COMLAINT_TYPE_CODE, complaintType));
        valuesToSet.put(BOUNDARY_ID, Attribute.asStringAttr(BOUNDARY_ID, locationId));
        valuesToSet.put(STATE_DETAILS, Attribute.asStringAttr(STATE_DETAILS, StringUtils.EMPTY));
        valuesToSet.put(USER_ROLE, Attribute.asStringAttr(USER_ROLE, getUserType()));
        valuesToSet.put(VALUES_STATE_ID, Attribute.asStringAttr(VALUES_STATE_ID, getCurrentStateId()));
        valuesToSet.put(VALUES_APPROVAL_COMMENT, Attribute.asStringAttr(VALUES_APPROVAL_COMMENT, approvalComment));
        valuesToSet.put(DEPARTMENT_ID, Attribute.asStringAttr(DEPARTMENT_ID, departmentId));
        return valuesToSet;
    }

    @SuppressWarnings("unchecked")
    private HashMap<String, Object> getServiceRequest() {
        return (HashMap<String, Object>) sevaRequestMap.get(SERVICE_REQUEST);
    }

    public void update(WorkflowResponse workflowResponse) {
        setAssignee(workflowResponse.getPositionId());
        setStateId(workflowResponse.getValueForKey(STATE_ID));
    }

    public void update(Position position) {
        setDesignation(position.getDesignationId());
        setDepartment(position.getDepartmentId());
    }

    public void setEscalationDate(Date date) {
        getServiceRequest().put(EXPECTED_DATETIME, DateFormatter.toString(date));
    }

    public String getTenantId() {
        return (String) getServiceRequest().get(TENANT_ID);
    }

    private String getUserType() {
        final User userInfo = getRequestInfo().getUserInfo();
        return userInfo != null ? userInfo.getType() : null;
    }

    private String getCurrentStateId() {
        return getDynamicSingleValue(VALUES_STATE_ID);
    }

    private Long getCurrentPositionId() {
        final String positionId = getDynamicSingleValue(VALUES_POSITION_ID);
        return positionId != null ? Long.valueOf(String.valueOf(positionId)) : null;
    }

    public boolean isCreate() {
        return POST.equalsIgnoreCase(this.getRequestInfo().getAction());
    }

    public void setDesignation(String designationId) {
        createOrUpdateAttributeEntry(VALUES_DESIGNATION_ID, designationId);
    }

    public String getDesignation() {
        return getDynamicSingleValue(VALUES_DESIGNATION_ID);
    }

    public void setDepartment(String departmentId) {
        createOrUpdateAttributeEntry(VALUES_DEPARTMENT_ID, departmentId);
    }

    @SuppressWarnings("unchecked")
    private List<HashMap<String, String>> getAttributeValues() {
        HashMap<String, Object> serviceRequest = getServiceRequest();
        final List<HashMap<String, String>> attributeValues =
            (List<HashMap<String, String>>) serviceRequest.get(ATTRIBUTE_VALUES);
        return attributeValues == null ? new ArrayList<>() : attributeValues;
    }

    public String getDynamicSingleValue(String key) {
        return getAttributeValues().stream()
            .filter(attribute -> key.equals(attribute.get(ATTRIBUTE_VALUES_KEY_FIELD)))
            .findFirst()
            .map(attribute -> attribute.get(ATTRIBUTE_VALUES_NAME_FIELD))
            .orElse(null);
    }

    private boolean isAttributeKeyPresent(String key) {
        return getAttributeValues().stream()
            .anyMatch(attribute -> key.equals(attribute.get(ATTRIBUTE_VALUES_KEY_FIELD)));
    }

    private void createOrUpdateAttributeEntry(String key, String name) {
        if (isAttributeKeyPresent(key)) {
            updateAttributeEntry(key, name);
        } else {
            createAttributeEntry(key, name);
        }
    }

    private void createAttributeEntry(String key, String name) {
        final HashMap<String, String> entry = new HashMap<>();
        entry.put(ATTRIBUTE_VALUES_KEY_FIELD, key);
        entry.put(ATTRIBUTE_VALUES_NAME_FIELD, name);
        getAttributeValues().add(entry);
    }

    private void updateAttributeEntry(String key, String name) {
        final HashMap<String, String> matchingEntry = getAttributeValues().stream()
            .filter(attribute -> key.equals(attribute.get(ATTRIBUTE_VALUES_KEY_FIELD)))
            .findFirst()
            .orElse(null);
        matchingEntry.put(ATTRIBUTE_VALUES_NAME_FIELD, name);
    }

}