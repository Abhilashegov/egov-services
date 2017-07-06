package org.egov.pgrrest.read.persistence.repository;

import org.egov.pgrrest.common.persistence.entity.ServiceType;
import org.egov.pgrrest.common.persistence.entity.Submission;
import org.egov.pgrrest.common.persistence.entity.SubmissionAttribute;
import org.egov.pgrrest.common.persistence.repository.ServiceRequestTypeJpaRepository;
import org.egov.pgrrest.common.persistence.repository.SubmissionAttributeJpaRepository;
import org.egov.pgrrest.common.persistence.repository.SubmissionJpaRepository;
import org.egov.pgrrest.read.domain.model.ServiceRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubmissionRepository {
    private SubmissionJpaRepository submissionJpaRepository;
    private SubmissionAttributeJpaRepository submissionAttributeJpaRepository;
    private ServiceRequestTypeJpaRepository serviceTypeJpaRepository;

    public SubmissionRepository(SubmissionJpaRepository submissionJpaRepository,
                                SubmissionAttributeJpaRepository submissionAttributeJpaRepository,
                                ServiceRequestTypeJpaRepository serviceTypeJpaRepository) {
        this.submissionJpaRepository = submissionJpaRepository;
        this.submissionAttributeJpaRepository = submissionAttributeJpaRepository;
        this.serviceTypeJpaRepository = serviceTypeJpaRepository;
    }

    public List<ServiceRequest> findBy(List<String> serviceRequestIdList, String tenantId) {
        final List<Submission> submissions = submissionJpaRepository.findByCRNList(serviceRequestIdList, tenantId);
        enrichSubmissionsWithAttributeEntries(tenantId, submissions);
        enrichSubmissionsWithServiceTypes(tenantId, submissions);
        return submissions.stream()
            .map(Submission::toDomain)
            .collect(Collectors.toList());
    }

    public Long getPosition(String serviceRequestId, String tenantId) {
        return submissionJpaRepository.findPosition(serviceRequestId, tenantId);
    }

    public Long getLoggedInRequester(String serviceRequestId, String tenantId) {
        return submissionJpaRepository.findLoggedInRequester(serviceRequestId, tenantId);
    }

    private void enrichSubmissionsWithServiceTypes(String tenantId, List<Submission> submissions) {
        final List<String> serviceCodes = getServiceCodes(submissions);
        final Map<String, ServiceType> codeToServiceTypeMap = getServiceCodeToTypeMap(tenantId, serviceCodes);
        submissions.forEach(submission -> {
            final ServiceType matchingServiceType = codeToServiceTypeMap.get(submission.getServiceCode());
            submission.setServiceType(matchingServiceType);
        });
    }

    private Map<String, ServiceType> getServiceCodeToTypeMap(String tenantId,
                                                             List<String> serviceCodes) {
        final List<ServiceType> serviceTypes = serviceTypeJpaRepository
            .findByCodeInAndTenantId(serviceCodes, tenantId);
        return serviceTypes.stream()
            .collect(Collectors.toMap(ServiceType::getCode, serviceType -> serviceType));
    }

    private List<String> getServiceCodes(List<Submission> submissions) {
        return submissions.stream()
            .map(Submission::getServiceCode)
            .distinct()
            .collect(Collectors.toList());
    }

    private void enrichSubmissionsWithAttributeEntries(String tenantId,
                                                       List<Submission> submissions) {
        final Map<String, List<SubmissionAttribute>> submissionAttributes =
            getSubmissionAttributes(tenantId, submissions);
        submissions.forEach(submission -> {
            final List<SubmissionAttribute> matchingAttributes = submissionAttributes
                .getOrDefault(submission.getCrn(), Collections.emptyList());
            submission.setAttributeValues(matchingAttributes);
        });
    }

    private Map<String, List<SubmissionAttribute>> getSubmissionAttributes(String tenantId,
                                                                           List<Submission> submissions) {
        final List<String> crnList = submissions.stream()
            .map(Submission::getCrn)
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(crnList)) {
            return new HashMap<>();
        }
        final List<SubmissionAttribute> submissionAttributes = submissionAttributeJpaRepository
            .findByCrnListAndTenantId(crnList, tenantId);
        return submissionAttributes.stream()
            .collect(Collectors.groupingBy(SubmissionAttribute::getCrn));
    }

}
