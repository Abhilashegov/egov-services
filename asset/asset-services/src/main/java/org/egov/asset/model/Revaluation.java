package org.egov.asset.model;

import org.egov.asset.model.enums.TypeOfChangeEnum;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Hold the asset Revaluation information.
 */

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Revaluation {

	@JsonProperty("tenantId")
	private String tenantId = null;

	@JsonProperty("id")
	private Long id = null;

	@JsonProperty("assetId")
	private Long assetId = null;

	@JsonProperty("currentCapitalizedValue")
	private Double currentCapitalizedValue = null;

	@JsonProperty("typeOfChange")
	private TypeOfChangeEnum typeOfChange = null;

	@JsonProperty("revaluationAmount")
	private Double revaluationAmount = null;

	@JsonProperty("valueAfterRevaluation")
	private Double valueAfterRevaluation = null;

	@JsonProperty("revaluationDate")
	private Long revaluationDate = null;

	@JsonProperty("reevaluatedBy")
	private String reevaluatedBy = null;

	@JsonProperty("reasonForRevaluation")
	private String reasonForRevaluation = null;

	@JsonProperty("fixedAssetsWrittenOffAccount")
	private Long fixedAssetsWrittenOffAccount = null;

	@JsonProperty("function")
	private Long function = null;

	@JsonProperty("fund")
	private Long fund = null;

	@JsonProperty("scheme")
	private Long scheme = null;

	@JsonProperty("subScheme")
	private Long subScheme = null;

	@JsonProperty("comments")
	private String comments = null;

	@JsonProperty("status")
	private String status = null;

	@JsonProperty("auditDetails")
	private AuditDetails auditDetails;

	@JsonProperty("voucherReference")
	private Long voucherReference;

}
