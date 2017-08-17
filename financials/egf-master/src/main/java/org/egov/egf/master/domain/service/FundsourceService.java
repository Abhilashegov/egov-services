package org.egov.egf.master.domain.service;

import java.util.List;

import org.egov.common.constants.Constants;
import org.egov.common.domain.exception.CustomBindException;
import org.egov.common.domain.exception.InvalidDataException;
import org.egov.common.domain.model.Pagination;
import org.egov.egf.master.domain.model.Bank;
import org.egov.egf.master.domain.model.Fundsource;
import org.egov.egf.master.domain.model.FundsourceSearch;
import org.egov.egf.master.domain.repository.FundsourceRepository;
import org.egov.egf.master.web.requests.FundsourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.SmartValidator;

@Service
@Transactional(readOnly = true)
public class FundsourceService {

	@Autowired
	private FundsourceRepository fundsourceRepository;

	@Autowired
	private SmartValidator validator;


	private BindingResult validate(List<Fundsource> fundsources, String method, BindingResult errors) {

		try {
			switch (method) {
			case Constants.ACTION_VIEW:
				// validator.validate(fundsourceContractRequest.getFundsource(),
				// errors);
				break;
			case Constants.ACTION_CREATE:
				Assert.notNull(fundsources, "Fundsources to create must not be null");
				for (Fundsource fundsource : fundsources) {
					validator.validate(fundsource, errors);
				}
				break;
			case Constants.ACTION_UPDATE:
				Assert.notNull(fundsources, "Fundsources to update must not be null");
				for (Fundsource fundsource : fundsources) {
				        Assert.notNull(fundsource.getId(), "Fundsource ID to update must not be null");
					validator.validate(fundsource, errors);
				}
				break;
			default:

			}
		} catch (IllegalArgumentException e) {
			errors.addError(new ObjectError("Missing data", e.getMessage()));
		}
		return errors;

	}

	public List<Fundsource> fetchRelated(List<Fundsource> fundsources) {
		for (Fundsource fundsource : fundsources) {
			// fetch related items
			if (fundsource.getParent() != null) {
				Fundsource fundSource = fundsourceRepository.findById(fundsource.getParent());
				if (fundSource == null) {
					throw new InvalidDataException("fundSource", "fundSource.invalid", " Invalid fundSource");
				}
				fundsource.setParent(fundSource);
			}

		}

		return fundsources;
	}

	@Transactional
	public List<Fundsource> add(List<Fundsource> fundsources, BindingResult errors) {
		fundsources = fetchRelated(fundsources);
		validate(fundsources, Constants.ACTION_CREATE, errors);
		if (errors.hasErrors()) {
			throw new CustomBindException(errors);
		}
		for(Fundsource b:fundsources)b.setId(fundsourceRepository.getNextSequence());
		return fundsources;

	}

	@Transactional
	public List<Fundsource> update(List<Fundsource> fundsources, BindingResult errors) {
		fundsources = fetchRelated(fundsources);
		validate(fundsources, Constants.ACTION_UPDATE, errors);
		if (errors.hasErrors()) {
			throw new CustomBindException(errors);
		}
		return fundsources;

	}

	public void addToQue(FundsourceRequest request) {
		fundsourceRepository.add(request);
	}

	public Pagination<Fundsource> search(FundsourceSearch fundsourceSearch) {
		return fundsourceRepository.search(fundsourceSearch);
	}

	@Transactional
	public Fundsource save(Fundsource fundsource) {
		return fundsourceRepository.save(fundsource);
	}

	@Transactional
	public Fundsource update(Fundsource fundsource) {
		return fundsourceRepository.update(fundsource);
	}

}