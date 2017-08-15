package org.egov.pgr.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.egov.pgr.domain.model.ServiceDefinition;
import org.egov.pgr.domain.model.ServiceDefinitionSearchCriteria;
import org.egov.pgr.domain.service.validator.Attributedefinition.validator.AttributeDefinitionCreateValidator;
import org.egov.pgr.domain.service.validator.serviceDefinitionCreateValidator.ServiceDefinitionCreateValidator;
import org.egov.pgr.domain.service.validator.valueDeficnitionValidator.ValueDefinitionCreateValidator;
import org.egov.pgr.persistence.dto.AttributeDefinition;
import org.egov.pgr.persistence.dto.ValueDefinition;
import org.egov.pgr.persistence.repository.AttributeDefinitionRepository;
import org.egov.pgr.persistence.repository.ServiceDefinitionMessageQueueRepository;
import org.egov.pgr.persistence.repository.ServiceDefinitionRepository;
import org.egov.pgr.persistence.repository.ValueDefinitionRepository;
import org.egov.pgr.web.contract.ServiceDefinitionRequest;
import org.springframework.stereotype.Service;

@Service
public class ServiceDefinitionService {

	private List<ServiceDefinitionCreateValidator> createValidators;
	private List<AttributeDefinitionCreateValidator> attributeValidate;
	private List<ValueDefinitionCreateValidator> valueDefinitionValidate;

	private static final String CREATE = "CREATE";
	private ServiceDefinitionMessageQueueRepository serviceDefinitionMessageQueueRepository;
	private ServiceDefinitionRepository serviceDefinitionRepository;
	private AttributeDefinitionRepository attributeDefinitionRepository;
	private ValueDefinitionRepository valueDefinitionRepository;

	public ServiceDefinitionService(ServiceDefinitionMessageQueueRepository serviceDefinitionMessageQueueRepository,
			ServiceDefinitionRepository serviceDefinitionRepository,
			AttributeDefinitionRepository attributeDefinitionRepository,
			ValueDefinitionRepository valueDefinitionRepository,
			List<ServiceDefinitionCreateValidator> createValidators,
			List<AttributeDefinitionCreateValidator> attributeValidate,
			List<ValueDefinitionCreateValidator> valueDefinitionValidate) {

		this.serviceDefinitionMessageQueueRepository = serviceDefinitionMessageQueueRepository;
		this.serviceDefinitionRepository = serviceDefinitionRepository;
		this.attributeDefinitionRepository = attributeDefinitionRepository;
		this.valueDefinitionRepository = valueDefinitionRepository;
		this.createValidators = createValidators;
		this.attributeValidate = attributeValidate;
		this.valueDefinitionValidate = valueDefinitionValidate; 
	}
	
	public void create(ServiceDefinition serviceDefinition, ServiceDefinitionRequest request) {
		createMandatoryFieldValidate(serviceDefinition);
		attributeMAndatoryFieldValidation(serviceDefinition);
		valueDefinMandatoryFieldValidation(serviceDefinition);
		
		ServiceDefinitionFieldLengthValidate( serviceDefinition);
		valueDefLengthValidation(serviceDefinition);
		attributeLengthValidation(serviceDefinition);
		
		createUniqueConstraintValidation(serviceDefinition);
		
		serviceDefinitionMessageQueueRepository.save(request, CREATE);
	}

	public void persist(ServiceDefinition serviceDefinition) {
		serviceDefinitionRepository.save(serviceDefinition.toDto());
		persistServiceTypeAttributes(serviceDefinition);
	}

	public List<ServiceDefinition> search(ServiceDefinitionSearchCriteria serviceDefinitionSearchCriteria) {

		List<ServiceDefinition> serviceDefinitionList = serviceDefinitionRepository
				.search(serviceDefinitionSearchCriteria);
		setAttributes(serviceDefinitionList);

		return serviceDefinitionList;
	}

	private void persistServiceTypeAttributes(ServiceDefinition serviceDefinition) {
		List<AttributeDefinition> attributeDefinitionList = serviceDefinition.getAttributes().stream()
				.map(attributeDefinition -> attributeDefinition.toDto(serviceDefinition)).collect(Collectors.toList());

		attributeDefinitionList.forEach(this::persistAttribute);
	}

	private void persistAttribute(AttributeDefinition attributeDefinition) {
		attributeDefinitionRepository.save(attributeDefinition);

		attributeDefinition.getValueDefinitions().forEach(this::persistValueDefinition);
	}

	private void persistValueDefinition(ValueDefinition valueDefinition) {
		valueDefinitionRepository.save(valueDefinition);
	}

	private void createMandatoryFieldValidate(ServiceDefinition serviceDefinition) {
		createValidators.stream().filter(validator -> validator.canValidate(serviceDefinition))
				.forEach(v -> v.checkMandatoryField(serviceDefinition));
	}
	
	private void ServiceDefinitionFieldLengthValidate(ServiceDefinition serviceDefinition) {
		createValidators.stream().filter(validator -> validator.canValidate(serviceDefinition))
				.forEach(v -> v.checkLength(serviceDefinition));
	}

	private void createUniqueConstraintValidation(ServiceDefinition serviceDefinition) {
		createValidators.stream().filter(validator -> validator.canValidate(serviceDefinition))
				.forEach(v -> v.checkConstraints(serviceDefinition));
	}
	
	private void attributeLengthValidation(ServiceDefinition serviceDefinition) {
		serviceDefinition.getAttributes().stream().forEach(attributeDefinition -> 
		{
			attributeValidate.stream().filter(validator -> 
			validator.canValidate(attributeDefinition))
			.forEach(v -> v.validatingLength(attributeDefinition));
		});
		
	}
	
	
	private void attributeMAndatoryFieldValidation(ServiceDefinition serviceDefinition) {
		serviceDefinition.getAttributes().stream().forEach(attributeDefinition -> 
		{
			attributeValidate.stream().filter(validator -> 
			validator.canValidate(attributeDefinition))
			.forEach(v -> v.checkMandatoryField(attributeDefinition));
		});
		
	}
	
	private void valueDefLengthValidation(ServiceDefinition serviceDefinition) {
		serviceDefinition.getAttributes().stream().forEach(attributeDefinition -> 
		{
			attributeDefinition.getValueDefinitions().stream().forEach(valueDefinition ->
			{
			valueDefinitionValidate.stream().filter(validator -> 
			validator.canValidate(valueDefinition))
			.forEach(v -> v.validateLength(valueDefinition));
		});
		});
		
	}
	
	private void valueDefinMandatoryFieldValidation(ServiceDefinition serviceDefinition) {
		serviceDefinition.getAttributes().stream().forEach(attributeDefinition -> 
		{
			attributeDefinition.getValueDefinitions().stream().forEach(valueDefinition ->
			{
			valueDefinitionValidate.stream().filter(validator -> 
			validator.canValidate(valueDefinition))
			.forEach(v -> v.checkMandatoryField(valueDefinition));
		});
		});
		
	}
	
	private void setAttributes(List<ServiceDefinition> serviceDefinitions) {
		serviceDefinitions.forEach(serviceDefinition -> serviceDefinition.setAttributes(attributeDefinitionRepository
				.searchByCodeAndTenant(serviceDefinition.getCode(), serviceDefinition.getTenantId())));
	}
}