package org.egov.works.estimate.web.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.egov.works.commons.domain.model.AuditDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * An Object holds the basic data for a Assets for Estimate
 */
@ApiModel(description = "An Object holds the basic data for a Assets for Estimate")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-24T10:20:21.690Z")

public class AssetsForEstimate {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("tenantId")
	private String tenantId = null;

	@JsonProperty("assetCode")
	private String assetCode = null;

	@JsonProperty("detailedEstimate")
	private DetailedEstimate detailedEstimate = null;

	@JsonProperty("auditDetails")
	private AuditDetails auditDetails = null;

	public AssetsForEstimate id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * Unique Identifier of the Assets For Estimate
	 * 
	 * @return id
	 **/
	@ApiModelProperty(value = "Unique Identifier of the Assets For Estimate")

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AssetsForEstimate tenantId(String tenantId) {
		this.tenantId = tenantId;
		return this;
	}

	/**
	 * Tenant id of the Assets For Estimate
	 * 
	 * @return tenantId
	 **/
	@ApiModelProperty(required = true, value = "Tenant id of the Assets For Estimate")
	@NotNull

	@Size(min = 4, max = 128)
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public AssetsForEstimate assetCode(String assetCode) {
		this.assetCode = assetCode;
		return this;
	}

	/**
	 * Code of the Asset
	 * 
	 * @return assetCode
	 **/
	@ApiModelProperty(required = true, value = "Code of the Asset")
	@NotNull

	@Pattern(regexp = "[a-zA-Z0-9-\\\\]")
	@Size(min = 1, max = 100)
	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public AssetsForEstimate detailedEstimate(DetailedEstimate detailedEstimate) {
		this.detailedEstimate = detailedEstimate;
		return this;
	}

	/**
	 * Get detailedEstimate
	 * 
	 * @return detailedEstimate
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull

	@Valid

	public DetailedEstimate getDetailedEstimate() {
		return detailedEstimate;
	}

	public void setDetailedEstimate(DetailedEstimate detailedEstimate) {
		this.detailedEstimate = detailedEstimate;
	}

	public AssetsForEstimate auditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
		return this;
	}

	/**
	 * Get auditDetails
	 * 
	 * @return auditDetails
	 **/
	@ApiModelProperty(value = "")

	@Valid

	public AuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AssetsForEstimate assetsForEstimate = (AssetsForEstimate) o;
		return Objects.equals(this.id, assetsForEstimate.id)
				&& Objects.equals(this.tenantId, assetsForEstimate.tenantId)
				&& Objects.equals(this.assetCode, assetsForEstimate.assetCode)
				&& Objects.equals(this.detailedEstimate, assetsForEstimate.detailedEstimate)
				&& Objects.equals(this.auditDetails, assetsForEstimate.auditDetails);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tenantId, assetCode, detailedEstimate, auditDetails);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AssetsForEstimate {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
		sb.append("    assetCode: ").append(toIndentedString(assetCode)).append("\n");
		sb.append("    detailedEstimate: ").append(toIndentedString(detailedEstimate)).append("\n");
		sb.append("    auditDetails: ").append(toIndentedString(auditDetails)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
