package org.egov.egf.master.domain.service;

import java.util.List;

import org.egov.common.domain.exception.CustomBindException;
import org.egov.common.domain.exception.InvalidDataException;
import org.egov.common.domain.model.Pagination;
import org.egov.egf.master.domain.model.AccountCodePurpose;
import org.egov.egf.master.domain.model.ChartOfAccount;
import org.egov.egf.master.domain.model.ChartOfAccountSearch;
import org.egov.egf.master.domain.repository.AccountCodePurposeRepository;
import org.egov.egf.master.domain.repository.ChartOfAccountRepository;
import org.egov.egf.master.web.requests.ChartOfAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.SmartValidator;

@Service
@Transactional(readOnly = true)
public class ChartOfAccountService {

	public static final String ACTION_CREATE = "create";
	public static final String ACTION_UPDATE = "update";
	public static final String ACTION_VIEW = "view";
	public static final String ACTION_EDIT = "edit";
	public static final String ACTION_SEARCH = "search";

	@Autowired
	private ChartOfAccountRepository chartOfAccountRepository;

	@Autowired
	private SmartValidator validator;

	@Autowired
	private AccountCodePurposeRepository accountCodePurposeRepository;

	private BindingResult validate(List<ChartOfAccount> chartofaccounts, String method, BindingResult errors) {

		try {
			switch (method) {
			case ACTION_VIEW:
				// validator.validate(chartOfAccountContractRequest.getChartOfAccount(),
				// errors);
				break;
			case ACTION_CREATE:
				Assert.notNull(chartofaccounts, "ChartOfAccounts to create must not be null");
				for (ChartOfAccount chartOfAccount : chartofaccounts) {
					validator.validate(chartOfAccount, errors);
				}
				break;
			case ACTION_UPDATE:
				Assert.notNull(chartofaccounts, "ChartOfAccounts to update must not be null");
				for (ChartOfAccount chartOfAccount : chartofaccounts) {
					validator.validate(chartOfAccount, errors);
				}
				break;
			default:

			}
		} catch (IllegalArgumentException e) {
			errors.addError(new ObjectError("Missing data", e.getMessage()));
		}
		return errors;

	}

	public List<ChartOfAccount> fetchRelated(List<ChartOfAccount> chartofaccounts) {
		for (ChartOfAccount chartOfAccount : chartofaccounts) {
			// fetch related items
			if (chartOfAccount.getAccountCodePurpose() != null) {
				AccountCodePurpose accountCodePurpose = accountCodePurposeRepository
						.findById(chartOfAccount.getAccountCodePurpose());
				if (accountCodePurpose == null) {
					throw new InvalidDataException("accountCodePurpose", "accountCodePurpose.invalid",
							" Invalid accountCodePurpose");
				}
				chartOfAccount.setAccountCodePurpose(accountCodePurpose);
			}
			if (chartOfAccount.getParentId() != null) {
				ChartOfAccount parentId = chartOfAccountRepository.findById(chartOfAccount.getParentId());
				if (parentId == null) {
					throw new InvalidDataException("parentId", "parentId.invalid", " Invalid parentId");
				}
				chartOfAccount.setParentId(parentId);
			}

		}

		return chartofaccounts;
	}

	@Transactional
	public List<ChartOfAccount> add(List<ChartOfAccount> chartofaccounts, BindingResult errors) {
		chartofaccounts = fetchRelated(chartofaccounts);
		validate(chartofaccounts, ACTION_CREATE, errors);
		if (errors.hasErrors()) {
			throw new CustomBindException(errors);
		}
		return chartofaccounts;

	}

	@Transactional
	public List<ChartOfAccount> update(List<ChartOfAccount> chartofaccounts, BindingResult errors) {
		chartofaccounts = fetchRelated(chartofaccounts);
		validate(chartofaccounts, ACTION_UPDATE, errors);
		if (errors.hasErrors()) {
			throw new CustomBindException(errors);
		}
		return chartofaccounts;

	}

	public void addToQue(ChartOfAccountRequest request) {
		chartOfAccountRepository.add(request);
	}

	public Pagination<ChartOfAccount> search(ChartOfAccountSearch chartOfAccountSearch) {
		return chartOfAccountRepository.search(chartOfAccountSearch);
	}

	@Transactional
	public ChartOfAccount save(ChartOfAccount chartOfAccount) {
		return chartOfAccountRepository.save(chartOfAccount);
	}

	@Transactional
	public ChartOfAccount update(ChartOfAccount chartOfAccount) {
		return chartOfAccountRepository.update(chartOfAccount);
	}

}