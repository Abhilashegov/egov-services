package org.egov.asset.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.egov.asset.config.ApplicationProperties;
import org.egov.asset.contract.AssetRequest;
import org.egov.asset.contract.DisposalRequest;
import org.egov.asset.contract.DisposalResponse;
import org.egov.asset.contract.VoucherRequest;
import org.egov.asset.model.Asset;
import org.egov.asset.model.AssetCategory;
import org.egov.asset.model.AssetCriteria;
import org.egov.asset.model.AssetStatus;
import org.egov.asset.model.ChartOfAccountDetailContract;
import org.egov.asset.model.Disposal;
import org.egov.asset.model.DisposalCriteria;
import org.egov.asset.model.Function;
import org.egov.asset.model.Fund;
import org.egov.asset.model.VouchercreateAccountCodeDetails;
import org.egov.asset.model.enums.AssetConfigurationKeys;
import org.egov.asset.model.enums.AssetStatusObjectName;
import org.egov.asset.model.enums.KafkaTopicName;
import org.egov.asset.model.enums.Status;
import org.egov.asset.repository.AssetRepository;
import org.egov.asset.repository.DisposalRepository;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DisposalService {

    @Autowired
    private DisposalRepository disposalRepository;

    @Autowired
    private LogAwareKafkaTemplate<String, Object> logAwareKafkaTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetMasterService assetMasterService;

    @Autowired
    private AssetConfigurationService assetConfigurationService;

    @Autowired
    private AssetCommonService assetCommonService;

    public DisposalResponse search(final DisposalCriteria disposalCriteria, final RequestInfo requestInfo) {
        List<Disposal> disposals = null;

        try {
            disposals = disposalRepository.search(disposalCriteria);
        } catch (final Exception ex) {
            log.info("DisposalService:", ex);
            throw new RuntimeException(ex);
        }
        return getResponse(disposals, requestInfo);
    }

    public void create(final DisposalRequest disposalRequest) {
        disposalRepository.create(disposalRequest);
        setStatusOfAssetToDisposed(disposalRequest);
    }

    public void setStatusOfAssetToDisposed(final DisposalRequest disposalRequest) {

        final List<Long> assetIds = new ArrayList<>();
        assetIds.add(disposalRequest.getDisposal().getAssetId());
        final Asset asset = assetRepository.findForCriteria(
                AssetCriteria.builder().tenantId(disposalRequest.getDisposal().getTenantId()).id(assetIds).build())
                .get(0);
        final List<AssetStatus> assetStatuses = assetMasterService.getStatuses(AssetStatusObjectName.ASSETMASTER,
                Status.DISPOSED, disposalRequest.getDisposal().getTenantId());
        asset.setStatus(assetStatuses.get(0).getStatusValues().get(0).getCode());
        final AssetRequest assetRequest = AssetRequest.builder().asset(asset)
                .requestInfo(disposalRequest.getRequestInfo()).build();
        assetService.update(assetRequest);
    }

    public DisposalResponse createAsync(final DisposalRequest disposalRequest, final HttpHeaders headers) {
        final Disposal disposal = disposalRequest.getDisposal();

        disposal.setId(Long.valueOf(disposalRepository.getNextDisposalId().longValue()));

        if (disposal.getAuditDetails() == null)
            disposal.setAuditDetails(assetCommonService.getAuditDetails(disposalRequest.getRequestInfo()));
        if (assetConfigurationService.getEnabledVoucherGeneration(AssetConfigurationKeys.ENABLEVOUCHERGENERATION,
                disposal.getTenantId()))
            try {
                log.info("Commencing Voucher Generation for Asset Sale/Disposal");
                final String voucherNumber = createVoucherForDisposal(disposalRequest, headers);
                if (StringUtils.isNotBlank(voucherNumber))
                    disposal.setVoucherReference(voucherNumber);
            } catch (final Exception e) {
                throw new RuntimeException("Voucher Generation is failed due to :" + e.getMessage());
            }

        logAwareKafkaTemplate.send(applicationProperties.getCreateAssetDisposalTopicName(),
                KafkaTopicName.SAVEDISPOSAL.toString(), disposalRequest);

        final List<Disposal> disposals = new ArrayList<Disposal>();
        disposals.add(disposal);
        return getResponse(disposals, disposalRequest.getRequestInfo());
    }

    private String createVoucherForDisposal(final DisposalRequest disposalRequest, final HttpHeaders headers) {
        final Disposal disposal = disposalRequest.getDisposal();
        final RequestInfo requestInfo = disposalRequest.getRequestInfo();
        final List<Long> assetIds = new ArrayList<>();
        final String tenantId = disposal.getTenantId();
        assetIds.add(disposal.getAssetId());
        final Asset asset = assetRepository
                .findForCriteria(AssetCriteria.builder().tenantId(tenantId).id(assetIds).build()).get(0);

        final AssetCategory assetCategory = asset.getAssetCategory();

        final List<ChartOfAccountDetailContract> subledgerDetailsForAssetAccount = voucherService
                .getSubledgerDetails(requestInfo, tenantId, assetCategory.getAssetAccount());
        final List<ChartOfAccountDetailContract> subledgerDetailsForAssetSaleAccount = voucherService
                .getSubledgerDetails(requestInfo, tenantId, disposal.getAssetSaleAccount());
        voucherService.validateSubLedgerDetails(subledgerDetailsForAssetAccount, subledgerDetailsForAssetSaleAccount);

        final List<VouchercreateAccountCodeDetails> accountCodeDetails = getAccountDetails(disposal, assetCategory,
                requestInfo);
        log.debug("Voucher Create Account Code Details :: " + accountCodeDetails);

        final Fund fund = voucherService.getFundData(requestInfo, tenantId, disposal.getFund()).getFunds().get(0);
        final VoucherRequest voucherRequest = voucherService.createVoucherRequest(disposal, fund,
                asset.getDepartment().getId(), accountCodeDetails, requestInfo, tenantId);

        log.debug("Voucher Request for Disposal :: " + voucherRequest);

        return voucherService.createVoucher(voucherRequest, tenantId, headers);

    }

    private List<VouchercreateAccountCodeDetails> getAccountDetails(final Disposal disposal,
            final AssetCategory assetCategory, final RequestInfo requestInfo) {
        final List<VouchercreateAccountCodeDetails> accountCodeDetails = new ArrayList<VouchercreateAccountCodeDetails>();
        final String tenantId = disposal.getTenantId();
        final Function function = voucherService.getFunctionData(requestInfo, tenantId, disposal.getFunction())
                .getFunctions().get(0);
        accountCodeDetails.add(voucherService.getGlCodes(requestInfo, tenantId, disposal.getAssetSaleAccount(),
                disposal.getSaleValue(), function, false, true));
        accountCodeDetails.add(voucherService.getGlCodes(requestInfo, tenantId, assetCategory.getAssetAccount(),
                disposal.getSaleValue(), function, true, false));
        return accountCodeDetails;
    }

    public DisposalResponse getResponse(final List<Disposal> disposals, final RequestInfo requestInfo) {
        final DisposalResponse disposalResponse = new DisposalResponse();
        disposalResponse.setDisposals(disposals);

        return disposalResponse;
    }

}
