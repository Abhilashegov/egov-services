package org.egov.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
/**
 * 
 * @author Prasad Khandagale
 *
 */
public class SpecialNoticeRequest {

	@JsonProperty("RequestInfo")
	private RequestInfo requestInfo;

	@NotNull
	@JsonProperty("upicNo")
	private String upicNo;

	@NotNull
	@Size(min = 4, max = 128)
	@JsonProperty("tenantId")
	private String tenantId;

}
