package org.egov.web.controller;

import java.util.Set;

import javax.validation.Valid;

import org.egov.contract.AssetCurrentValueRequest;
import org.egov.contract.AssetCurrentValueResponse;
import org.egov.contract.AssetRequest;
import org.egov.contract.AssetResponse;
import org.egov.contract.DisposalRequest;
import org.egov.contract.DisposalResponse;
import org.egov.contract.RequestInfoWrapper;
import org.egov.contract.RevaluationRequest;
import org.egov.contract.RevaluationResponse;
import org.egov.model.criteria.AssetCriteria;
import org.egov.model.criteria.DisposalCriteria;
import org.egov.model.criteria.RevaluationCriteria;
import org.egov.service.AssetService;
import org.egov.service.CurrentValueService;
import org.egov.service.DisposalService;
import org.egov.service.RevaluationService;
import org.egov.web.validator.AssetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/assets")
public class AssetController {

	@Autowired
	private AssetService assetService;

	@Autowired
	private RevaluationService revaluationService;
	
	@Autowired
	private DisposalService  disposalService;
	
	@Autowired
	private CurrentValueService currentValueService;
	
	@Autowired
	private AssetValidator assetValidator;

	@PostMapping("_search")
	@ResponseBody
	public ResponseEntity<?> search(@RequestBody @Valid final RequestInfoWrapper requestInfoWrapper,
			@ModelAttribute @Valid final AssetCriteria assetCriteria) {
		log.debug("assetCriteria::" + assetCriteria + "requestInfoWrapper::" + requestInfoWrapper);
		AssetResponse assetResponse = assetService.getAssets(assetCriteria, requestInfoWrapper.getRequestInfo());
		return new ResponseEntity<>(assetResponse, HttpStatus.OK);

	}

	@PostMapping("_create")
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody @Valid final AssetRequest assetRequest) {
		assetValidator.validateAsset(assetRequest);
		final AssetResponse assetResponse = assetService.createAsync(assetRequest);
		return new ResponseEntity<>(assetResponse, HttpStatus.CREATED);
	}

	@PostMapping("_update")
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody final AssetRequest assetRequest) {
		final AssetResponse assetResponse = assetService.updateAsync(assetRequest);
		return new ResponseEntity<>(assetResponse, HttpStatus.OK);
	}

	@PostMapping("revaluation/_create")
	@ResponseBody
	public ResponseEntity<?> revaluate(@RequestBody @Valid final RevaluationRequest revaluationRequest,
			final BindingResult bindingResult, @RequestHeader final HttpHeaders headers) {

		// assetValidator.validateRevaluation(revaluationRequest);
		final RevaluationResponse revaluationResponse = revaluationService.createAsync(revaluationRequest, headers);
		return new ResponseEntity<>(revaluationResponse, HttpStatus.CREATED);
	}

	@PostMapping("revaluation/_search")
	@ResponseBody
	public ResponseEntity<?> reevaluateSearch(@RequestBody @Valid final RequestInfoWrapper requestInfoWrapper,
			@ModelAttribute @Valid final RevaluationCriteria revaluationCriteria, final BindingResult bindingResult) {

		log.debug("reevaluateSearch revaluationCriteria:" + revaluationCriteria);

		final RevaluationResponse revaluationResponse = revaluationService.search(revaluationCriteria,
				requestInfoWrapper.getRequestInfo());

		return new ResponseEntity<RevaluationResponse>(revaluationResponse, HttpStatus.OK);
	}

	@PostMapping("dispose/_create")
	@ResponseBody
	public ResponseEntity<?> dispose(@RequestBody @Valid final DisposalRequest disposalRequest,
			final BindingResult bindingResult, @RequestHeader final HttpHeaders headers) {

		final DisposalResponse disposalResponse = disposalService.createAsync(disposalRequest, headers);
		log.debug("dispose disposalResponse:" + disposalResponse);
		return new ResponseEntity<DisposalResponse>(disposalResponse, HttpStatus.CREATED);
	}

	@PostMapping("dispose/_search")
	@ResponseBody
	public ResponseEntity<?> disposalSearch(@RequestBody @Valid final RequestInfoWrapper requestInfoWrapper,
			@ModelAttribute @Valid final DisposalCriteria disposalCriteria, final BindingResult bindingResult) {

		final DisposalResponse disposalResponse = disposalService.search(disposalCriteria,
				requestInfoWrapper.getRequestInfo());

		return new ResponseEntity<>(disposalResponse, HttpStatus.OK);
	}
	
	@PostMapping("currentvalues/_search")
	@ResponseBody
	public ResponseEntity<?> getAssetCurrentValue(
			@RequestParam(name = "assetIds", required = true) final Set<Long> assetIds,
			@RequestParam(name = "tenantId", required = true) final String tenantId,
			@RequestBody @Valid final RequestInfoWrapper requestInfoWrapper) {

		log.debug("getAssetCurrentValue assetId:" + assetIds + ",tenantId:" + tenantId);
		final AssetCurrentValueResponse assetCurrentValueResponse = currentValueService.getCurrentValues(assetIds,
				tenantId, requestInfoWrapper.getRequestInfo());

		log.debug("getAssetCurrentValue assetCurrentValueResponse:" + assetCurrentValueResponse);
		return new ResponseEntity<>(assetCurrentValueResponse, HttpStatus.OK);
	}

	@PostMapping("currentvalues/_create")
	@ResponseBody
	public ResponseEntity<?> saveCurrentValue(
			@RequestBody @Valid final AssetCurrentValueRequest assetCurrentValueRequest) {
		log.debug("create assetcurrentvalue :" + assetCurrentValueRequest);
		
		final AssetCurrentValueResponse assetCurrentValueResponse = currentValueService
				.createCurrentValueAsync(assetCurrentValueRequest);
		return new ResponseEntity<>(assetCurrentValueResponse, HttpStatus.CREATED);
	}

}
