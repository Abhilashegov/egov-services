package org.egov.works.estimate.web.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.coyote.RequestInfo;
import org.egov.works.estimate.web.model.DetailedEstimate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Contract class to send response. Array of Detailed Estimate items are used in
 * case of search results, also multiple Detailed Estimate item is used for
 * create and update
 */
@ApiModel(description = "Contract class to send response. Array of Detailed Estimate items are used in case of search results, also multiple  Detailed Estimate item is used for create and update")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-24T10:20:21.690Z")

public class DetailedEstimateRequest {
	@JsonProperty("requestInfo")
	private RequestInfo requestInfo = null;

	@JsonProperty("abstractEstimates")
	private List<DetailedEstimate> abstractEstimates = null;

	public DetailedEstimateRequest requestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
		return this;
	}

	/**
	 * Get requestInfo
	 * 
	 * @return requestInfo
	 **/
	@ApiModelProperty(value = "")

	@Valid

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public DetailedEstimateRequest abstractEstimates(List<DetailedEstimate> abstractEstimates) {
		this.abstractEstimates = abstractEstimates;
		return this;
	}

	public DetailedEstimateRequest addAbstractEstimatesItem(DetailedEstimate abstractEstimatesItem) {
		if (this.abstractEstimates == null) {
			this.abstractEstimates = new ArrayList<DetailedEstimate>();
		}
		this.abstractEstimates.add(abstractEstimatesItem);
		return this;
	}

	/**
	 * Used for create and update only
	 * 
	 * @return abstractEstimates
	 **/
	@ApiModelProperty(value = "Used for create and update only")

	@Valid

	public List<DetailedEstimate> getAbstractEstimates() {
		return abstractEstimates;
	}

	public void setAbstractEstimates(List<DetailedEstimate> abstractEstimates) {
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
		DetailedEstimateRequest detailedEstimateRequest = (DetailedEstimateRequest) o;
		return Objects.equals(this.requestInfo, detailedEstimateRequest.requestInfo)
				&& Objects.equals(this.abstractEstimates, detailedEstimateRequest.abstractEstimates);
	}

	@Override
	public int hashCode() {
		return Objects.hash(requestInfo, abstractEstimates);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DetailedEstimateRequest {\n");

		sb.append("    requestInfo: ").append(toIndentedString(requestInfo)).append("\n");
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
