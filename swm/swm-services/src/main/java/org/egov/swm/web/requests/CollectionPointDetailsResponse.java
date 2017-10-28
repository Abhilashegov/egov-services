package org.egov.swm.web.requests;

import java.util.List;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.swm.domain.model.CollectionPointDetails;
import org.egov.swm.domain.model.Pagination;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
public @Data class CollectionPointDetailsResponse {

	@JsonProperty("ResponseInfo")
	private ResponseInfo responseInfo;
	private List<CollectionPointDetails> collectionPointDetails;
	private Pagination page;
}