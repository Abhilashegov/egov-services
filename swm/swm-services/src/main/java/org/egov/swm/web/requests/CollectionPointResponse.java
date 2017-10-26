package org.egov.swm.web.requests;

import java.util.List;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.swm.domain.model.CollectionPoint;
import org.egov.swm.domain.model.Pagination;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
public @Data class CollectionPointResponse {

	@JsonProperty("ResponseInfo")
	private ResponseInfo responseInfo;
	private List<CollectionPoint> collectionPoints;
	private Pagination page;
}