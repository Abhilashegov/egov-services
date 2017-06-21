package org.egov.demand.web.contract;

import java.util.ArrayList;
import java.util.List;
import org.egov.common.contract.request.RequestInfo;
import org.egov.demand.model.BusinessServiceDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillRequest
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessServiceDetailRequest {

	private RequestInfo RequestInfo;

	private List<BusinessServiceDetail> BusinessServiceDetails = new ArrayList<>();
}
