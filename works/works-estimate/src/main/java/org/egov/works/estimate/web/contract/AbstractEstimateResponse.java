package org.egov.works.estimate.web.contract;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Contract class to send response. Array of AbstractEstimate items are used in case of search results, also multiple  AbstractEstimate item is used for create and update
 */
@ApiModel(description = "Contract class to send response. Array of AbstractEstimate items are used in case of search results, also multiple  AbstractEstimate item is used for create and update")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-03T07:36:47.547Z")

public class AbstractEstimateResponse   {
  @JsonProperty("responseInfo")
  private ResponseInfo responseInfo = null;

  @JsonProperty("abstractEstimates")
  private List<AbstractEstimate> abstractEstimates = null;

  public AbstractEstimateResponse responseInfo(ResponseInfo responseInfo) {
    this.responseInfo = responseInfo;
    return this;
  }

   /**
   * Get responseInfo
   * @return responseInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ResponseInfo getResponseInfo() {
    return responseInfo;
  }

  public void setResponseInfo(ResponseInfo responseInfo) {
    this.responseInfo = responseInfo;
  }

  public AbstractEstimateResponse abstractEstimates(List<AbstractEstimate> abstractEstimates) {
    this.abstractEstimates = abstractEstimates;
    return this;
  }

  public AbstractEstimateResponse addAbstractEstimatesItem(AbstractEstimate abstractEstimatesItem) {
    if (this.abstractEstimates == null) {
      this.abstractEstimates = new ArrayList<AbstractEstimate>();
    }
    this.abstractEstimates.add(abstractEstimatesItem);
    return this;
  }

   /**
   * Used for search result and create only
   * @return abstractEstimates
  **/
  @ApiModelProperty(value = "Used for search result and create only")

  @Valid

  public List<AbstractEstimate> getAbstractEstimates() {
    return abstractEstimates;
  }

  public void setAbstractEstimates(List<AbstractEstimate> abstractEstimates) {
    this.abstractEstimates = abstractEstimates;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractEstimateResponse abstractEstimateResponse = (AbstractEstimateResponse) o;
    return Objects.equals(this.responseInfo, abstractEstimateResponse.responseInfo) &&
        Objects.equals(this.abstractEstimates, abstractEstimateResponse.abstractEstimates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseInfo, abstractEstimates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AbstractEstimateResponse {\n");
    
    sb.append("    responseInfo: ").append(toIndentedString(responseInfo)).append("\n");
    sb.append("    abstractEstimates: ").append(toIndentedString(abstractEstimates)).append("\n");
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

