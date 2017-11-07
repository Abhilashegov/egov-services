package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.MaterialReceiptHeader;
import io.swagger.model.RequestInfo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Hold the Material Receipt Note request information.
 */
@ApiModel(description = "Hold the Material Receipt Note request information.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-28T13:21:55.964+05:30")

public class MaterialReceiptHeaderRequest   {
  @JsonProperty("requestInfo")
  private org.egov.common.contract.request.RequestInfo requestInfo = null;

  @JsonProperty("MaterialReceipt")
  @Valid
  private List<MaterialReceiptHeader> materialReceipt = null;

  public MaterialReceiptHeaderRequest requestInfo(org.egov.common.contract.request.RequestInfo requestInfo) {
    this.requestInfo = requestInfo;
    return this;
  }

   /**
   * Get requestInfo
   * @return requestInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public org.egov.common.contract.request.RequestInfo getRequestInfo() {
    return requestInfo;
  }

  public void setrequestInfo(org.egov.common.contract.request.RequestInfo requestInfo) {
    this.requestInfo = requestInfo;
  }

  public MaterialReceiptHeaderRequest materialReceipt(List<MaterialReceiptHeader> materialReceipt) {
    this.materialReceipt = materialReceipt;
    return this;
  }

  public MaterialReceiptHeaderRequest addMaterialReceiptItem(MaterialReceiptHeader materialReceiptItem) {
    if (this.materialReceipt == null) {
      this.materialReceipt = new ArrayList<MaterialReceiptHeader>();
    }
    this.materialReceipt.add(materialReceiptItem);
    return this;
  }

   /**
   * Get materialReceipt
   * @return materialReceipt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<MaterialReceiptHeader> getMaterialReceipt() {
    return materialReceipt;
  }

  public void setMaterialReceipt(List<MaterialReceiptHeader> materialReceipt) {
    this.materialReceipt = materialReceipt;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MaterialReceiptHeaderRequest materialReceiptHeaderRequest = (MaterialReceiptHeaderRequest) o;
    return Objects.equals(this.requestInfo, materialReceiptHeaderRequest.requestInfo) &&
        Objects.equals(this.materialReceipt, materialReceiptHeaderRequest.materialReceipt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestInfo, materialReceipt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MaterialReceiptHeaderRequest {\n");
    
    sb.append("    requestInfo: ").append(toIndentedString(requestInfo)).append("\n");
    sb.append("    materialReceipt: ").append(toIndentedString(materialReceipt)).append("\n");
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

