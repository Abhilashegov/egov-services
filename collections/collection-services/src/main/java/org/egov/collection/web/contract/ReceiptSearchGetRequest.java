package org.egov.collection.web.contract;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;
@Setter
@Getter
@ToString
public class ReceiptSearchGetRequest {
    private List<String> receiptNumbers;

    private String consumerCode;

    private Long fromDate;

    private Long toDate;

    private String collectedBy;

    private String status;

    private String paymentType;

    private String classification;


    private String businessCode;

    @NotNull
    private String tenantId;
    
    private String sortBy;
    
    private String sortOrder;

    private String transactionId;

}
