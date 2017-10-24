package org.egov.repository.rowmapper;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.model.Asset;
import org.egov.model.AssetCategory;
import org.egov.model.Attributes;
import org.egov.model.AuditDetails;
import org.egov.model.DefectLiability;
import org.egov.model.Location;
import org.egov.model.enums.ModeOfAcquisitionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AssetRowMapper implements ResultSetExtractor<List<Asset>> {

	@Autowired
    private ObjectMapper objectMapper;

    @Override
    public ArrayList<Asset> extractData(final ResultSet rs) throws SQLException, DataAccessException {
        final Map<Long, Asset> map = new HashMap<Long, Asset>();

        while (rs.next()) {
            final Long assetId = (Long) rs.getObject("id");

            log.debug("agreementid in row mapper" + assetId);

            Asset asset = map.get(assetId);
            if (asset == null) {
                asset = new Asset();
                asset.setId(assetId);
                asset.setName(rs.getString("name"));
                asset.setCode(rs.getString("code"));
                asset.setOldCode(rs.getString("oldcode"));
               
                asset.setTenantId(rs.getString("tenantId"));
                asset.setModeOfAcquisition(ModeOfAcquisitionEnum.fromValue(rs.getString("modeofacquisition")));
                asset.setStatus(rs.getString("status"));
                asset.setGrossValue((rs.getBigDecimal("grossvalue")));
                asset.setAccumulatedDepreciation(rs.getBigDecimal("accumulateddepreciation"));
                asset.setDescription(rs.getString("description"));
                asset.setDateOfCreation((Long) rs.getObject("dateOfCreation"));
                asset.setRemarks(rs.getString("remarks"));
                asset.setVersion(rs.getString("version"));
                asset.setAssetReference((Long) rs.getObject("assetreference"));
                asset.setEnableYearWiseDepreciation(rs.getBoolean("enableyearwisedepreciation"));
                asset.setDepreciationRate(getDoubleFromBigDecimal(rs.getBigDecimal("depreciationrate")));
                asset.setAnticipatedLife((Long) rs.getObject("anticipatedlife"));
                asset.setOrderNumber(rs.getString("ordernumber"));
                asset.setOrderDate((Long) rs.getObject("orderdate"));
                asset.setWipReferenceNo(rs.getString("wipreferenceno"));
                asset.setAcquiredFrom(rs.getString("acquiredfrom"));
                asset.setWarrantyAvailable(rs.getBoolean("warrantyavailable"));
                asset.setWarrantyExpiryDate((Long) rs.getObject("warrantyexpirydate"));
                asset.setSecurityDepositRealized(rs.getBigDecimal("securitydepositrealized"));
                asset.setSecurityDepositRetained(rs.getBigDecimal("securitydepositretained"));
                asset.setAcquisitionDate((Long) rs.getObject("acquisitiondate"));
                asset.setOriginalValue(rs.getBigDecimal("originalvalue"));
                asset.setAssetAccount(rs.getString("assetaccount"));
                asset.setAccumulatedDepreciationAccount(rs.getString("accumulateddepreciationaccount"));
                asset.setRevaluationReserveAccount(rs.getString("revaluationreserveaccount"));
                asset.setDepreciationExpenseAccount(rs.getString("depreciationexpenseaccount"));
                String titleDocument = rs.getString("titledocumentsavalable");
                List<String> titleDocumentsAvalable= new ArrayList<>();
                titleDocumentsAvalable.add(titleDocument);
                asset.setTitleDocumentsAvalable(titleDocumentsAvalable);
                asset.setUsage(rs.getString("usage"));
                asset.setLength(getDoubleFromBigDecimal(rs.getBigDecimal("length")));
                asset.setWidth(getDoubleFromBigDecimal(rs.getBigDecimal("width")));
                asset.setHeight(getDoubleFromBigDecimal(rs.getBigDecimal("height")));
                asset.setTotalArea(getDoubleFromBigDecimal(rs.getBigDecimal("totalArea")));
                asset.setPlinthArea(getDoubleFromBigDecimal(rs.getBigDecimal("plintHarea")));
                asset.setAddress(rs.getString("address"));
                asset.setLongitude(getDoubleFromBigDecimal(rs.getBigDecimal("longitude")));
                asset.setLatitude(getDoubleFromBigDecimal(rs.getBigDecimal("latitude")));
                asset.setFloors((Long) rs.getObject("floors"));
                asset.setLandSurveyNo(rs.getString("landsurveyno"));
                asset.setCubicContents(rs.getString("cubiccontents"));
                asset.setQuantity((Long) rs.getObject("quantity"));
                asset.setAssetReference((Long) rs.getObject("assetreference"));
              

                final String properties = rs.getString("assetAttributes");
                List<Attributes> asset2 = null;
                try {
                    asset2 = objectMapper.readValue(properties, List.class);
                } catch (final JsonParseException e) {
                    e.printStackTrace();
                } catch (final JsonMappingException e) {
                    e.printStackTrace();
                } catch (final IOException e) {
                    e.printStackTrace();
                }

                asset.setAssetAttributes(asset2);
                asset.setDepartmentCode(rs.getString("departmentCode"));

                final  DefectLiability defectLiabilityPeriod= new DefectLiability();
                defectLiabilityPeriod.setDay((Long) rs.getObject("defectliabilityday"));
                defectLiabilityPeriod.setMonth((Long) rs.getObject("defectliabilitymonth"));
                defectLiabilityPeriod.setYear((Long) rs.getObject("defectliabilityyear"));
                asset.setDefectLiabilityPeriod(defectLiabilityPeriod);
                final Location location = new Location();
                location.setBlock((Long) rs.getObject("block"));
                location.setLocality((Long) rs.getObject("locality"));
                location.setDoorNo(rs.getString("doorNo"));
                location.setElectionWard((Long) rs.getObject("electionWard"));
                location.setRevenueWard((Long) rs.getObject("revenueWard"));
                location.setPinCode((Long) rs.getObject("pincode"));
                location.setZone((Long) rs.getObject("zone"));
                location.setStreet((Long) rs.getObject("street"));
                asset.setLocationDetails(location);

                final AssetCategory assetCategory = new AssetCategory();
                assetCategory.setId((Long) rs.getObject("assetcategory"));
                asset.setAssetCategory(assetCategory);
                
                final AuditDetails auditDetails=new AuditDetails();
                auditDetails.setCreatedBy(rs.getString("createdby"));
                auditDetails.setCreatedDate(rs.getLong("createddate"));
                auditDetails.setLastModifiedBy(rs.getString("lastmodifiedby"));
                auditDetails.setLastModifiedDate(rs.getLong("lastmodifieddate"));
                asset.setAuditDetails(auditDetails);

                log.debug("AssetRowMapper asset:: " + asset);
                map.put(assetId, asset);
            }
          }
        return new ArrayList<Asset>(map.values());
        
}
    
	public Double getDoubleFromBigDecimal(BigDecimal obj) {

		if (obj == null)
			return null;
		else {
			return new Double(obj.doubleValue());
		}
	}
    // assetCategory fields wont be coming from db anymore due to master application 
    /* assetCategory.setAccumulatedDepreciationAccount((Long) rs.getObject("accumulatedDepreciationAccount"));
    assetCategory.setAssetCategoryType(AssetCategoryType.fromValue(rs.getString("assetcategorytype")));
    assetCategory.setAssetAccount((Long) rs.getObject("assetAccount"));
    assetCategory.setName(rs.getString("assetCategoryName"));
    assetCategory.setCode(rs.getString("assetcategorycode"));
    assetCategory.setParent((Long) rs.getObject("parentId"));
    assetCategory.setDepreciationRate(rs.getDouble("depreciationrate"));
    assetCategory.setDepreciationExpenseAccount((Long) rs.getObject("depreciationExpenseAccount"));
    assetCategory.setDepreciationMethod(DepreciationMethod.fromValue(rs.getString("depreciationMethod")));
    assetCategory.setAccumulatedDepreciationAccount((Long) rs.getObject("accumulatedDepreciationAccount"));
    assetCategory.setRevaluationReserveAccount((Long) rs.getObject("revaluationReserveAccount"));
    assetCategory.setUnitOfMeasurement((Long) rs.getObject("unitOfMeasurement"));
    assetCategory.setTenantId(rs.getString("tenantId"));*/
}