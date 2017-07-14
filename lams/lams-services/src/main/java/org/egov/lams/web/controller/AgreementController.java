package org.egov.lams.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.egov.lams.TestConfiguration;
import org.egov.lams.model.Agreement;
import org.egov.lams.model.AgreementCriteria;
import org.egov.lams.model.enums.Source;
import org.egov.lams.service.AgreementService;
import org.egov.lams.web.contract.AgreementRequest;
import org.egov.lams.web.contract.AgreementResponse;
import org.egov.lams.web.contract.RequestInfo;
import org.egov.lams.web.contract.RequestInfoWrapper;
import org.egov.lams.web.contract.factory.ResponseInfoFactory;
import org.egov.lams.web.errorhandlers.Error;
import org.egov.lams.web.errorhandlers.ErrorResponse;
import org.egov.lams.web.validator.AgreementValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("agreements")
public class AgreementController {
	public static final Logger LOGGER = LoggerFactory
			.getLogger(AgreementController.class);

	@Autowired
	private AgreementService agreementService;

	@Autowired
	private ResponseInfoFactory responseInfoFactory;
	
	@Autowired
	private AgreementValidator agreementValidator;
	
	@PostMapping("_search")
	@ResponseBody
	public ResponseEntity<?> search(
			@ModelAttribute @Valid AgreementCriteria agreementCriteria,
			@RequestBody RequestInfoWrapper requestInfoWrapper, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			ErrorResponse errorResponse = populateErrors(bindingResult);
			return new ResponseEntity<>(errorResponse,
					HttpStatus.BAD_REQUEST);
		}
		
		RequestInfo requestInfo = requestInfoWrapper.getRequestInfo();
		LOGGER.info("AgreementController:getAgreements():searchAgreementsModel:"+ agreementCriteria);
		List<Agreement> agreements = agreementService
				.searchAgreement(agreementCriteria,requestInfo);
		System.err.println("before sending for response su7ccess");
		return getSuccessResponse(agreements, requestInfo);
	}

	private ResponseEntity<?> getSuccessResponse(List<Agreement> agreements, RequestInfo requestInfo) {
		AgreementResponse agreementResponse = new AgreementResponse();
		agreementResponse.setAgreement(agreements);
		agreementResponse.setResponseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true));
		System.err.println("before returning from getsucces resposne ::"+agreementResponse );
		return new ResponseEntity<>(agreementResponse, HttpStatus.OK);
	}

	@PostMapping("/_create")
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody @Valid AgreementRequest agreementRequest, BindingResult errors) {

		if (errors.hasFieldErrors()) {
			ErrorResponse errRes = populateErrors(errors);
			return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
		}
		
		LOGGER.info("agreementRequest::" + agreementRequest);
		agreementValidator.validate(agreementRequest,errors);
		
		if (errors.hasFieldErrors()) {
			ErrorResponse errRes = populateErrors(errors);
			return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
		}
		
		Agreement agreement = agreementService.createAgreement(agreementRequest);
		List<Agreement> agreements = new ArrayList<>();
		agreements.add(agreement);
		AgreementResponse agreementResponse = new AgreementResponse();
		agreementResponse.setAgreement(agreements);
		agreementResponse.setResponseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(agreementRequest.getRequestInfo(), true));
		LOGGER.info(agreementResponse.toString());
		return new ResponseEntity<>(agreementResponse, HttpStatus.CREATED);
	}
	
	@PostMapping("/_renew")
	@ResponseBody
	public ResponseEntity<?> renew(@RequestBody @Valid AgreementRequest agreementRequest, BindingResult errors) {

		if (errors.hasFieldErrors()) {
			ErrorResponse errRes = populateErrors(errors);
			return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
		}
		
		LOGGER.info("agreementRequest::" + agreementRequest);
		agreementValidator.validate(agreementRequest,errors);
		
		if (errors.hasFieldErrors()) {
			ErrorResponse errRes = populateErrors(errors);
			return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
		}
		
		Agreement agreement = agreementService.createAgreement(agreementRequest);
		List<Agreement> agreements = new ArrayList<>();
		agreements.add(agreement);
		AgreementResponse agreementResponse = new AgreementResponse();
		agreementResponse.setAgreement(agreements);
		agreementResponse.setResponseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(agreementRequest.getRequestInfo(), true));
		LOGGER.info(agreementResponse.toString());
		return new ResponseEntity<>(agreementResponse, HttpStatus.CREATED);
	}
	
	@PostMapping("/_eviction")
	@ResponseBody
	public ResponseEntity<?> eviction(@RequestBody @Valid AgreementRequest agreementRequest, BindingResult errors) {

		if (errors.hasFieldErrors()) {
			ErrorResponse errRes = populateErrors(errors);
			return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
		}
		
		LOGGER.info("agreementRequest::" + agreementRequest);
		agreementValidator.validate(agreementRequest,errors);
		
		if (errors.hasFieldErrors()) {
			ErrorResponse errRes = populateErrors(errors);
			return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
		}
		
		Agreement agreement = agreementService.createAgreement(agreementRequest);
		List<Agreement> agreements = new ArrayList<>();
		agreements.add(agreement);
		AgreementResponse agreementResponse = new AgreementResponse();
		agreementResponse.setAgreement(agreements);
		agreementResponse.setResponseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(agreementRequest.getRequestInfo(), true));
		LOGGER.info(agreementResponse.toString());
		return new ResponseEntity<>(agreementResponse, HttpStatus.CREATED);
	}
	
	@PostMapping("/_cancel")
	@ResponseBody
	public ResponseEntity<?> cancel(@RequestBody @Valid AgreementRequest agreementRequest, BindingResult errors) {

		if (errors.hasFieldErrors()) {
			ErrorResponse errRes = populateErrors(errors);
			return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
		}

		LOGGER.info("agreementRequest cancel ::" + agreementRequest);
		agreementValidator.validate(agreementRequest, errors);

		if (errors.hasFieldErrors()) {
			ErrorResponse errRes = populateErrors(errors);
			return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
		}
		
		Agreement agreement = agreementService.createAgreement(agreementRequest);
		List<Agreement> agreements = new ArrayList<>();
		agreements.add(agreement);
		AgreementResponse agreementResponse = new AgreementResponse();
		agreementResponse.setAgreement(agreements);
		agreementResponse.setResponseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(agreementRequest.getRequestInfo(), true));
		LOGGER.info(agreementResponse.toString());
		return new ResponseEntity<>(agreementResponse, HttpStatus.CREATED);
	}
	
	@PostMapping("_update/{code}")
	@ResponseBody
	public ResponseEntity<?> update(@PathVariable("code") String code, @RequestBody AgreementRequest agreementRequest,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			ErrorResponse errorResponse = populateErrors(bindingResult);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
		if(agreementRequest.getAgreement().getSource().equals(Source.SYSTEM)){
		agreementValidator.validateUpdate(agreementRequest, bindingResult);
		}

		if (!(code.equals(agreementRequest.getAgreement().getAcknowledgementNumber())
				|| code.equals(agreementRequest.getAgreement().getAgreementNumber())
						&& agreementService.isAgreementExist(code)))
			throw new RuntimeException("code mismatch or no agreement found for this value");

		Agreement agreement = null;
		agreement = agreementService.updateAgreement(agreementRequest);
		List<Agreement> agreements = new ArrayList<>();
		agreements.add(agreement);
		AgreementResponse agreementResponse = new AgreementResponse();
		agreementResponse.setAgreement(agreements);
		agreementResponse.setResponseInfo(
				responseInfoFactory.createResponseInfoFromRequestInfo(agreementRequest.getRequestInfo(), true));
		LOGGER.info("the response form update agrement call : " + agreementResponse);
		return new ResponseEntity<>(agreementResponse, HttpStatus.CREATED);
	}
	
	@PostMapping("demands/_prepare")
	@ResponseBody
	public ResponseEntity<?> prepareDemand(@RequestBody @Valid RequestInfoWrapper requestInfoWrapper, BindingResult errors,
			@RequestParam (name="agreementNumber", required=false)  String agreementNumber,
			@RequestParam (name="tenantId", required=true)  String tenantId) {

		if (errors.hasErrors()) {
			ErrorResponse errRes = populateErrors(errors);
			return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
		}
		
		AgreementRequest agreementRequest = new AgreementRequest();
		agreementRequest.setRequestInfo(requestInfoWrapper.getRequestInfo());
		
		AgreementCriteria agreementCriteria = new AgreementCriteria();
		agreementCriteria.setTenantId(tenantId);
		agreementCriteria.setAgreementNumber(agreementNumber);
		
		LOGGER.info("before search : "+agreementNumber);
		Agreement agreement = agreementService.searchAgreement(agreementCriteria,requestInfoWrapper.getRequestInfo()).get(0);
		LOGGER.info("after search "+ agreement);
		agreementRequest.setAgreement(agreement);
		agreement.setLegacyDemands(agreementService.prepareDemands(agreementRequest));
		LOGGER.info("after prepare denmands : "+agreement.getLegacyDemands());
		

		List<Agreement> agreements = new ArrayList<>();
		agreements.add(agreement);
		AgreementResponse agreementResponse = new AgreementResponse();
		agreementResponse.setAgreement(agreements);
		agreementResponse.setResponseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(agreementRequest.getRequestInfo(), true));
		LOGGER.info(agreementResponse.toString());
		return new ResponseEntity<>(agreementResponse, HttpStatus.CREATED);
	}

	private ErrorResponse populateErrors(BindingResult errors) {
		ErrorResponse errRes = new ErrorResponse();

		/*
		 * ResponseInfo responseInfo = new ResponseInfo();
		 * responseInfo.setStatus(HttpStatus.BAD_REQUEST.toString());
		 * responseInfo.setApi_id(""); errRes.setResponseInfo(responseInfo);
		 */
		Error error = new Error();
		error.setCode(1);
		error.setDescription("Error while binding request");
		if (errors.hasFieldErrors()) {
			for (FieldError errs : errors.getFieldErrors()) {
				error.getFields().put(errs.getField(),errs.getDefaultMessage());
			}
		}
		errRes.setError(error);
		return errRes;
	}
}
