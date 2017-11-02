package org.egov.pa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object which holds the mapping for KPI and its actual values entered by ULBs
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-02T05:16:16.756Z")

public class KpiValue   {
	
  @JsonProperty("id")
  private String id = null; 
  
  @JsonProperty("kpi")
  private KPI kpi = null;

  @JsonProperty("resultValue")
  private Long resultValue = null;

  @JsonProperty("tenantId")
  private String tenantId = null;

  @JsonProperty("documents")
  private List<Document> documents = null;

  @JsonProperty("auditDetails")
  private AuditDetails auditDetails = null;
  
  

  public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public KpiValue kpi(KPI kpi) {
    this.kpi = kpi;
    return this;
  }

   /**
   * Get kpi
   * @return kpi
  **/
  @NotNull

  @Valid

  public KPI getKpi() {
    return kpi;
  }

  public void setKpi(KPI kpi) {
    this.kpi = kpi;
  }

  public KpiValue resultValue(Long resultValue) {
    this.resultValue = resultValue;
    return this;
  }

   /**
   * Actual Value at ULB Level for the KPI Target
   * @return resultValue
  **/
  @NotNull


  public Long getResultValue() {
    return resultValue;
  }

  public void setResultValue(Long resultValue) {
    this.resultValue = resultValue;
  }

  public KpiValue tenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

   /**
   * Unique Identifier of the tenant
   * @return tenantId
  **/
  @NotNull

 @Pattern(regexp="^[a-zA-Z0-9.]+$")
  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public KpiValue documents(List<Document> documents) {
    this.documents = documents;
    return this;
  }

  public KpiValue addDocumentsItem(Document documentsItem) {
    if (this.documents == null) {
      this.documents = new ArrayList<Document>();
    }
    this.documents.add(documentsItem);
    return this;
  }

   /**
   * Get documents
   * @return documents
  **/

  @Valid

  public List<Document> getDocuments() {
    return documents;
  }

  public void setDocuments(List<Document> documents) {
    this.documents = documents;
  }

  public KpiValue auditDetails(AuditDetails auditDetails) {
    this.auditDetails = auditDetails;
    return this;
  }

   /**
   * Get auditDetails
   * @return auditDetails
  **/

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
    KpiValue kpiValue = (KpiValue) o;
    return Objects.equals(this.kpi, kpiValue.kpi) &&
        Objects.equals(this.resultValue, kpiValue.resultValue) &&
        Objects.equals(this.tenantId, kpiValue.tenantId) &&
        Objects.equals(this.documents, kpiValue.documents) &&
        Objects.equals(this.auditDetails, kpiValue.auditDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kpi, resultValue, tenantId, documents, auditDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KpiValue {\n");
    
    sb.append("    kpi: ").append(toIndentedString(kpi)).append("\n");
    sb.append("    resultValue: ").append(toIndentedString(resultValue)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    documents: ").append(toIndentedString(documents)).append("\n");
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

