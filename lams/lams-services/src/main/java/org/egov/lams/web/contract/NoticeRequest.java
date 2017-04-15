package org.egov.lams.web.contract;

import org.egov.lams.model.Notice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * NoticeRequest
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoticeRequest   {
	
  @JsonProperty("RequestInfo")
  private RequestInfo requestInfo = null;
  
  @JsonProperty("Notice")
  private Notice notice;
 
}

