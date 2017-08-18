package org.egov.tradelicense.domain.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.egov.tradelicense.domain.enums.ApplicationType;
import org.egov.tradelicense.domain.enums.BusinessNature;
import org.egov.tradelicense.domain.enums.OwnerShipType;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeLicenseSearch {

	private Long id;

	@JsonProperty("tenantId")
	@NotNull
	@Size(min = 4, max = 128)
	private String tenantId;

	@NotNull
	private ApplicationType applicationType;

	@JsonProperty("applicationNumber")
	private String applicationNumber;

	@JsonProperty("licenseNumber")
	private String licenseNumber;

	@JsonProperty("oldLicenseNumber")
	private String oldLicenseNumber;

	@JsonProperty("applicationDate")
	private String applicationDate;

	@JsonProperty("adhaarNumber")
	@Pattern(regexp = "[0-9]{12}")
	@Size(min = 12, max = 12)
	private String adhaarNumber;

	@NotNull
	@JsonProperty("mobileNumber")
	private String mobileNumber;

	@NotNull
	@Size(min = 4, max = 32)
	@JsonProperty("ownerName")
	private String ownerName;

	@NotNull
	@Size(min = 4, max = 32)
	@JsonProperty("fatherSpouseName")
	private String fatherSpouseName;

	@NotNull
	@Email
	@JsonProperty("emailId")
	private String emailId;

	@NotNull
	@Size(max = 256)
	@JsonProperty("ownerAddress")
	private String ownerAddress;

	@JsonProperty("propertyAssesmentNo")
	private String propertyAssesmentNo;

	@NotNull
	@JsonProperty("localityId")
	private Integer localityId;

	@JsonProperty("localityName")
	private String localityName;

	@NotNull
	@JsonProperty("revenueWardId")
	private Integer revenueWardId;

	@JsonProperty("revenueWardName")
	private String revenueWardName;

	@NotNull
	@JsonProperty("adminWardId")
	private Integer adminWardId;

	@JsonProperty("adminWardName")
	private String adminWardName;

	@NotNull
	@JsonProperty("tradeAddress")
	@Size(max = 256)
	private String tradeAddress;

	@NotNull
	@JsonProperty("ownerShipType")
	private OwnerShipType ownerShipType;

	@NotNull
	@JsonProperty("tradeTitle")
	@Size(max = 33)
	private String tradeTitle;

	@NotNull
	@JsonProperty("tradeType")
	private BusinessNature tradeType;

	@NotNull
	@JsonProperty("categoryId")
	private Long categoryId;

	@JsonProperty("category")
	private String category;

	@NotNull
	@JsonProperty("subCategoryId")
	private Long subCategoryId;

	@JsonProperty("subCategory")
	private String subCategory;

	@NotNull
	@JsonProperty("uomId")
	private Long uomId;

	@JsonProperty("uom")
	private String uom;

	@NotNull
	@JsonProperty("quantity")
	private Double quantity;

	@NotNull
	@JsonProperty("validityYears")
	private Long validityYears;

	@JsonProperty("remarks")
	private String remarks;

	@NotNull
	@JsonProperty("tradeCommencementDate")
	private String tradeCommencementDate;

	@NotNull
	@JsonProperty("licenseValidFromDate")
	private String licenseValidFromDate;

	@JsonProperty("agreementDate")
	private String agreementDate;

	@JsonProperty("agreementNo")
	private String agreementNo;

	@JsonProperty("isLegacy")
	private Boolean isLegacy = false;

	@JsonProperty("active")
	private Boolean active = true;

	@JsonProperty("expiryDate")
	private String expiryDate;

	@JsonProperty("feeDetails")
	private List<LicenseFeeDetail> feeDetails;

	@JsonProperty("isPropertyOwner")
	private Boolean isPropertyOwner = false;
	
	@JsonProperty("supportDocuments")
	private List<SupportDocument> supportDocuments;

	@JsonProperty("status")
	private Long status;

	@JsonProperty("statusName")
	private String statusName;

	@JsonProperty("auditDetails")
	private AuditDetails auditDetails;
}