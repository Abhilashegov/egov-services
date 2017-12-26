package org.egov.inv.domain.service;

import static org.springframework.util.StringUtils.isEmpty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.egov.common.Constants;
import org.egov.common.DomainService;
import org.egov.common.Pagination;
import org.egov.common.exception.CustomBindException;
import org.egov.common.exception.ErrorCode;
import org.egov.common.exception.InvalidDataException;
import org.egov.inv.model.MaterialIssue;
import org.egov.inv.model.MaterialIssueDetail;
import org.egov.inv.model.MaterialIssueSearchContract;
import org.egov.inv.model.MaterialIssuedFromReceipt;
import org.egov.inv.model.MaterialReceipt;
import org.egov.inv.model.MaterialReceipt.ReceiptTypeEnum;
import org.egov.inv.model.MaterialReceiptAddInfoSearch;
import org.egov.inv.model.MaterialReceiptDetail;
import org.egov.inv.model.MaterialReceiptDetailAddnlinfo;
import org.egov.inv.model.MaterialReceiptSearch;
import org.egov.inv.model.TransferInwardRequest;
import org.egov.inv.model.TransferInwardResponse;
import org.egov.inv.persistence.repository.MaterialIssueJdbcRepository;
import org.egov.inv.persistence.repository.MaterialReceiptDetailAddInfoJdbcRepository;
import org.egov.inv.persistence.repository.TransferInwardRepository;
import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TransferinwardsService extends DomainService {

	@Autowired
	private LogAwareKafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private TransferInwardRepository transferInwardRepository;

	@Autowired
	private MaterialIssueService materialIssuesService;

	@Autowired
	private MaterialReceiptService materialReceiptService;

	@Autowired
	private MaterialIssueJdbcRepository materialIssueJdbcRepository;

	@Autowired
	private MaterialReceiptDetailAddInfoJdbcRepository materialReceiptDetailAddInfoJdbcRepository;

	@Value("${inv.transfer.inward.save.topic}")
	private String createTopic;

	@Value("${inv.transfer.inward.update.topic}")
	private String updateTopic;

	@Value("${inv.transfer.inward.save.key}")
	private String createTopicKey;

	@Value("${inv.transfer.inward.update.key}")
	private String updateTopicKey;

	// creating inter-store transfer inwards
	public TransferInwardResponse create(TransferInwardRequest inwardRequest, String tenantId) {
		try {
			fetchRelated(inwardRequest, tenantId);
			List<MaterialReceipt> inwards = inwardRequest.getTransferInwards();
			validate(inwards, tenantId, Constants.ACTION_CREATE);
			inwards.forEach(materialReceipt -> {
				materialReceipt.setId(transferInwardRepository.getSequence("seq_materialreceipt"));
				materialReceipt.setMrnStatus(MaterialReceipt.MrnStatusEnum.CREATED);
				materialReceipt.setMrnNumber(appendString(materialReceipt));
				materialReceipt.setReceiptType(ReceiptTypeEnum.INWARD_RECEIPT);
				materialReceipt.setAuditDetails(getAuditDetails(inwardRequest.getRequestInfo(), tenantId));
				if (StringUtils.isEmpty(materialReceipt.getTenantId())) {
					materialReceipt.setTenantId(tenantId);
				}
				materialReceipt.getReceiptDetails().forEach(materialReceiptDetail -> {
					materialReceiptDetail.setId(transferInwardRepository.getSequence("seq_materialreceiptdetail"));
					materialReceiptDetail.setTenantId(tenantId);
					materialReceiptDetail.getReceiptDetailsAddnInfo().stream().forEach(addinfo -> {
						addinfo.setId(transferInwardRepository.getSequence("seq_materialreceiptdetailaddnlinfo"));
						if (isEmpty(addinfo.getTenantId())) {
							addinfo.setTenantId(tenantId);
						}
					});
				});
			});
			kafkaTemplate.send(createTopic, createTopicKey, inwardRequest);
			TransferInwardResponse response = new TransferInwardResponse();
			response.setResponseInfo(null);
			response.setTransferInwards(inwardRequest.getTransferInwards());
			return response;

		} catch (CustomBindException e) {
			throw e;
		}
	}

	// updating inter-store transfer inwards
	public TransferInwardResponse update(TransferInwardRequest inwardsRequest, String tenantId) {
		try {
			List<MaterialReceipt> inwards = inwardsRequest.getTransferInwards();
			validate(inwards, tenantId, Constants.ACTION_UPDATE);
			List<String> materialReceiptDetailIds = new ArrayList<>();
			List<String> materialReceiptDetailAddlnInfoIds = new ArrayList<>();
			inwards.forEach(materialReceipt -> {
				if (StringUtils.isEmpty(materialReceipt.getTenantId())) {
					materialReceipt.setTenantId(tenantId);
				}
				materialReceipt.getReceiptDetails().forEach(materialReceiptDetail -> {
					if (isEmpty(materialReceiptDetail.getTenantId())) {
						materialReceiptDetail.setTenantId(tenantId);
					}
					materialReceiptDetailIds.add(materialReceiptDetail.getId());
					materialReceiptDetail.getReceiptDetailsAddnInfo().forEach(materialReceiptDetailAddnlInfo -> {
						materialReceiptDetailAddlnInfoIds.add(materialReceiptDetailAddnlInfo.getId());
						if (isEmpty(materialReceiptDetailAddnlInfo.getTenantId())) {
							materialReceiptDetailAddnlInfo.setTenantId(tenantId);
						}
					});
					transferInwardRepository.markDeleted(materialReceiptDetailAddlnInfoIds, tenantId,
							"materialreceiptdetailaddnlinfo", "receiptdetailid", materialReceiptDetail.getId());
					transferInwardRepository.markDeleted(materialReceiptDetailIds, tenantId, "materialreceiptdetail",
							"mrnNumber", materialReceipt.getMrnNumber());
				});
			});
			kafkaTemplate.send(updateTopic, updateTopicKey, inwardsRequest);
			TransferInwardResponse response = new TransferInwardResponse();
			response.setResponseInfo(null);
			response.setTransferInwards(inwardsRequest.getTransferInwards());
			return response;
		} catch (CustomBindException e) {
			throw e;
		}
	}

	public TransferInwardResponse search(MaterialReceiptSearch transferinwardsSearch, String tenantId) {
		Pagination<MaterialReceipt> materialReceiptPagination = materialReceiptService.search(transferinwardsSearch);
		TransferInwardResponse response = new TransferInwardResponse();
		return response.responseInfo(null).transferInwards(materialReceiptPagination.getPagedData().size() > 0
				? materialReceiptPagination.getPagedData() : Collections.EMPTY_LIST);
	}
// validating data
	private void validate(List<MaterialReceipt> materialReceipts, String tenantId, String method) {
		InvalidDataException errors = new InvalidDataException();
		try {
			switch (method) {
			case Constants.ACTION_CREATE: {
				if (materialReceipts == null) {
					errors.addDataError("materialreceipt", ErrorCode.NOT_NULL.getCode(), null);
				}
			}
				break;

			case Constants.ACTION_UPDATE: {
				if (materialReceipts == null) {
					errors.addDataError("materialreceipt", ErrorCode.NOT_NULL.getCode(), null);
				}
			}
				break;
			}
			for (MaterialReceipt rcpt : materialReceipts) {
				if (null == rcpt.getIssueingStore() && isEmpty(rcpt.getIssueingStore())) {
					errors.addDataError(ErrorCode.NOT_NULL.getCode(), "Issuing Store", null);
				}
				if (null == rcpt.getReceivingStore() && isEmpty(rcpt.getReceivingStore())) {
					errors.addDataError(ErrorCode.NOT_NULL.getCode(), "Receiving Store", null);
				}
			}

		} catch (IllegalArgumentException e) {
		}
		if (errors.getValidationErrors().size() > 0)
			throw errors;

	}

	private String appendString(MaterialReceipt materialReceipt) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String code = "MRN/";
		int id = Integer.valueOf(transferInwardRepository.getSequence(materialReceipt));
		String idgen = String.format("%05d", id);
		String mrnNumber = code + idgen + "/" + year;
		return mrnNumber;
	}

	// fetching material issue and building with receipt data
	private void fetchRelated(TransferInwardRequest request, String tenantId) {
		InvalidDataException errors = new InvalidDataException();
		for (MaterialReceipt receipt : request.getTransferInwards()) {

			if (receipt.getIssueNumber() != null && !StringUtils.isEmpty(receipt.getIssueNumber())) {
				MaterialIssueSearchContract issue = new MaterialIssueSearchContract();
				issue.setIssueNoteNumber(receipt.getIssueNumber());
				issue.setTenantId(receipt.getTenantId());
				List<MaterialIssue> matIssues = materialIssuesService.search(issue, null).getMaterialIssues();
				Long issueDate = matIssues.get(0).getIssueDate();
				BigDecimal issuedQuantity = matIssues.get(0).getMaterialIssueDetails().get(0).getQuantityIssued();

				if (matIssues.isEmpty())
					errors.addDataError(ErrorCode.DOESNT_MATCH.getCode(), "issueNumber", null);
				else
					receipt.setIssueNumber(matIssues.get(0).getIssueNumber());
				for (MaterialReceiptDetail detail : receipt.getReceiptDetails()) {
					detail.getMaterial()
							.setCode(matIssues.get(0).getMaterialIssueDetails().get(0).getMaterial().getCode());
					detail.getUom().setCode(matIssues.get(0).getMaterialIssueDetails().get(0).getUom().getCode());
					int res = detail.getReceivedQty().compareTo(issuedQuantity);
					if (res != 0) {
						errors.addDataError(ErrorCode.QTY1_EQ_QTY2.getCode(), "Received Qty", "Issued Qty", null);
					} else
						updateStatusAsReceipted(receipt.getIssueNumber(), tenantId);

					for (MaterialReceiptDetailAddnlinfo info : detail.getReceiptDetailsAddnInfo()) {
						if (info.getReceivedDate() <= issueDate) {
							errors.addDataError(ErrorCode.DATE1_GT_DATE2.getCode(), "Receive Date", "Issue ", null);
						}
						info.setSerialNo(setDetailAddinfoFromIssueDetail(matIssues).getSerialNo());
						info.setLotNo(setDetailAddinfoFromIssueDetail(matIssues).getLotNo());
					}
				}
			} else {
				errors.addDataError(ErrorCode.MANDATORY_VALUE_MISSING.getCode(), "IssueNumber", null);
			}
		}
		if (errors.getValidationErrors().size() > 0)
			throw errors;
	}

	// used to set or build receipt detail addinfo using material issue detail
	private MaterialReceiptDetailAddnlinfo setDetailAddinfoFromIssueDetail(List<MaterialIssue> issue) {
		MaterialReceiptDetailAddnlinfo detailInfo = new MaterialReceiptDetailAddnlinfo();
		MaterialReceiptDetail details = new MaterialReceiptDetail();
		for (MaterialIssue iss : issue) {
			for (MaterialIssueDetail issDetail : iss.getMaterialIssueDetails()) {
				for (MaterialIssuedFromReceipt fromReceipt : issDetail.getMaterialIssuedFromReceipts()) {
					MaterialReceiptAddInfoSearch materialReceiptAddInfoSearch = MaterialReceiptAddInfoSearch.builder()
							.id(Arrays.asList(fromReceipt.getMaterialReceiptDetailAddnlinfoId()))
							.tenantId(fromReceipt.getTenantId()).build();
					Pagination<MaterialReceiptDetailAddnlinfo> info = materialReceiptDetailAddInfoJdbcRepository
							.search(materialReceiptAddInfoSearch);
					details.setReceiptDetailsAddnInfo(info.getPagedData());
					if (!isEmpty(details)) {
						for (MaterialReceiptDetailAddnlinfo addInfo : details.getReceiptDetailsAddnInfo()) {
							detailInfo.setSerialNo(addInfo.getSerialNo());
							detailInfo.setLotNo(addInfo.getLotNo());
							detailInfo.setManufactureDate(addInfo.getManufactureDate());
							detailInfo.setExpiryDate(addInfo.getExpiryDate());
							detailInfo.setTenantId(addInfo.getTenantId());
						}
					}
				}
			}
		}
		return detailInfo;
	}

	// used this api for updating status as receipted
	private void updateStatusAsReceipted(String issueNumber, String tenantId) {
		materialIssueJdbcRepository.updateStatus(issueNumber, "RECEIPTED", tenantId);
	}

}
