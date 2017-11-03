package org.egov.works.estimate.web.contract;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * An Object that holds the basic data of Technical Sanction for Detailed Estimate
 */
@ApiModel(description = "An Object that holds the basic data of Technical Sanction for Detailed Estimate")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-03T07:36:47.547Z")

public class EstimateTechnicalSanction   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("tenantId")
  private String tenantId = null;

  @JsonProperty("technicalSanctionNumber")
  private String technicalSanctionNumber = null;

  @JsonProperty("detailedEstimate")
  private DetailedEstimate detailedEstimate = null;

  @JsonProperty("technicalSanctionDate")
  private Long technicalSanctionDate = null;

  @JsonProperty("technicalSanctionBy")
  private String technicalSanctionBy = null;

  @JsonProperty("auditDetails")
  private AuditDetails auditDetails = null;

  public EstimateTechnicalSanction id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique Identifier of the Estimate Technical Sanction
   * @return id
  **/
  @ApiModelProperty(value = "Unique Identifier of the Estimate Technical Sanction")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public EstimateTechnicalSanction tenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

   /**
   * Tenant id of the Estimate Technical Sanction
   * @return tenantId
  **/
  @ApiModelProperty(required = true, value = "Tenant id of the Estimate Technical Sanction")
  @NotNull

 @Size(min=4,max=128)
  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public EstimateTechnicalSanction technicalSanctionNumber(String technicalSanctionNumber) {
    this.technicalSanctionNumber = technicalSanctionNumber;
    return this;
  }

   /**
   * Technical Sanction Number of the Detailed Estimate
   * @return technicalSanctionNumber
  **/
  @ApiModelProperty(required = true, value = "Technical Sanction Number of the Detailed Estimate")
  @NotNull

 @Pattern(regexp="[a-zA-Z0-9-\\\\]+") @Size(min=1,max=50)
  public String getTechnicalSanctionNumber() {
    return technicalSanctionNumber;
  }

  public void setTechnicalSanctionNumber(String technicalSanctionNumber) {
    this.technicalSanctionNumber = technicalSanctionNumber;
  }

  public EstimateTechnicalSanction detailedEstimate(DetailedEstimate detailedEstimate) {
    this.detailedEstimate = detailedEstimate;
    return this;
  }

   /**
   * Reference of the Detailed Estimate for which the Technical sanction belongs to
   * @return detailedEstimate
  **/
  @ApiModelProperty(required = true, value = "Reference of the Detailed Estimate for which the Technical sanction belongs to")
  @NotNull

  @Valid

  public DetailedEstimate getDetailedEstimate() {
    return detailedEstimate;
  }

  public void setDetailedEstimate(DetailedEstimate detailedEstimate) {
    this.detailedEstimate = detailedEstimate;
  }

  public EstimateTechnicalSanction technicalSanctionDate(Long technicalSanctionDate) {
    this.technicalSanctionDate = technicalSanctionDate;
    return this;
  }

   /**
   * Epoch time of the Technical Sanction Date
   * @return technicalSanctionDate
  **/
  @ApiModelProperty(required = true, value = "Epoch time of the Technical Sanction Date")
  @NotNull


  public Long getTechnicalSanctionDate() {
    return technicalSanctionDate;
  }

  public void setTechnicalSanctionDate(Long technicalSanctionDate) {
    this.technicalSanctionDate = technicalSanctionDate;
  }

  public EstimateTechnicalSanction technicalSanctionBy(String technicalSanctionBy) {
    this.technicalSanctionBy = technicalSanctionBy;
    return this;
  }

   /**
   * User who technical sanctioned Detailed Estimate
   * @return technicalSanctionBy
  **/
  @ApiModelProperty(required = true, value = "User who technical sanctioned Detailed Estimate")
  @NotNull


  public String getTechnicalSanctionBy() {
    return technicalSanctionBy;
  }

  public void setTechnicalSanctionBy(String technicalSanctionBy) {
    this.technicalSanctionBy = technicalSanctionBy;
  }

  public EstimateTechnicalSanction auditDetails(AuditDetails auditDetails) {
    this.auditDetails = auditDetails;
    return this;
  }

   /**
   * Get auditDetails
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
    EstimateTechnicalSanction estimateTechnicalSanction = (EstimateTechnicalSanction) o;
    return Objects.equals(this.id, estimateTechnicalSanction.id) &&
        Objects.equals(this.tenantId, estimateTechnicalSanction.tenantId) &&
        Objects.equals(this.technicalSanctionNumber, estimateTechnicalSanction.technicalSanctionNumber) &&
        Objects.equals(this.detailedEstimate, estimateTechnicalSanction.detailedEstimate) &&
        Objects.equals(this.technicalSanctionDate, estimateTechnicalSanction.technicalSanctionDate) &&
        Objects.equals(this.technicalSanctionBy, estimateTechnicalSanction.technicalSanctionBy) &&
        Objects.equals(this.auditDetails, estimateTechnicalSanction.auditDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, tenantId, technicalSanctionNumber, detailedEstimate, technicalSanctionDate, technicalSanctionBy, auditDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EstimateTechnicalSanction {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    technicalSanctionNumber: ").append(toIndentedString(technicalSanctionNumber)).append("\n");
    sb.append("    detailedEstimate: ").append(toIndentedString(detailedEstimate)).append("\n");
    sb.append("    technicalSanctionDate: ").append(toIndentedString(technicalSanctionDate)).append("\n");
    sb.append("    technicalSanctionBy: ").append(toIndentedString(technicalSanctionBy)).append("\n");
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

