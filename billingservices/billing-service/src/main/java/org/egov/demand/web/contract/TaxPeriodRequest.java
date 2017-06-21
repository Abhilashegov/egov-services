package org.egov.demand.web.contract;

import java.util.ArrayList;
import java.util.List;
import org.egov.common.contract.request.RequestInfo;
import org.egov.demand.model.TaxPeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxPeriodRequest {

	private RequestInfo RequestInfo;

	List<TaxPeriod> TaxPeriods = new ArrayList<>();;
}
