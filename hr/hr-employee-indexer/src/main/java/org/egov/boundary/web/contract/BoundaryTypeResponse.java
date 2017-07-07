package org.egov.boundary.web.contract;

import java.util.ArrayList;
import java.util.List;

import org.egov.boundary.persistence.entity.BoundaryType;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egov.common.contract.response.ResponseInfo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoundaryTypeResponse {

	@JsonProperty("ResponseInfo")
	private ResponseInfo responseInfo = null;

	@JsonProperty("BoundaryType")
	private List<BoundaryType> boundaryTypes = new ArrayList<BoundaryType>();

}
