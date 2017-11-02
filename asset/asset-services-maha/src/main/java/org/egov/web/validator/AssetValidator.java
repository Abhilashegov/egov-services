package org.egov.web.validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.egov.contract.AssetRequest;
import org.egov.model.Asset;
import org.egov.model.AssetCategory;
import org.egov.model.DefectLiability;
import org.egov.model.Location;
import org.egov.tracer.model.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AssetValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AssetRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
	}
	
	public void validateAsset(AssetRequest assetRequest) {
		
		Map<String, String> errorMap = new HashMap<>();
		Asset asset = assetRequest.getAsset();
		AssetCategory assetCategory = asset.getAssetCategory();
		
		addMissingPathForPersister(asset);
		validateAnticipatedLife(asset.getAnticipatedLife(),asset.getOriginalValue(),assetCategory.getDepreciationRate(),errorMap);
		
		
		if(!errorMap.isEmpty())
		throw new CustomException(errorMap);
	}
	
	

	private void validateAnticipatedLife(Long anticipatedLife, BigDecimal originalValue, Double depreciationRate,
			Map<String, String> errorMap) {

		double depAmtPerYear = originalValue.doubleValue() * (depreciationRate / 100);
		Long expectedLife =  new Double(originalValue.doubleValue()/depAmtPerYear).longValue();
		if (!anticipatedLife.equals(expectedLife))
			errorMap.put("Asset_anticipatedLife", "anticipatedLife Value is wrong");

	}

	public void addMissingPathForPersister(Asset asset) {

		if (asset.getDefectLiabilityPeriod() == null)
			asset.setDefectLiabilityPeriod(new DefectLiability());
		if (asset.getLocationDetails() == null)
			asset.setLocationDetails(new Location());

	}
}
