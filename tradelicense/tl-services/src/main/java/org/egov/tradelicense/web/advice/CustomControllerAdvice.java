package org.egov.tradelicense.web.advice;

import java.util.ArrayList;
import java.util.List;

import org.egov.common.contract.response.Error;
import org.egov.common.contract.response.ErrorField;
import org.egov.common.contract.response.ErrorResponse;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.tradelicense.common.domain.exception.CustomBindException;
import org.egov.tradelicense.common.domain.exception.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class CustomControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String handleMissingParamsError(Exception ex) {
		return ex.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CustomBindException.class)
	public ErrorResponse handleBindingErrors(CustomBindException ex) {
		ErrorResponse errRes = new ErrorResponse();
		BindingResult errors = ex.getErrors();
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setStatus(HttpStatus.BAD_REQUEST.toString());
		errRes.setResponseInfo(responseInfo);
		Error error = new Error();
		if (errors.getGlobalError() != null) {
			error.setCode(Integer.valueOf(errors.getGlobalError().getCode()));
			error.setMessage(errors.getGlobalError().getObjectName());
			error.setDescription(errors.getGlobalError().getDefaultMessage());
		} else {
			if (errors.getFieldErrorCount() > 0) {
				error.setDescription("Missing fields");
			}
		}
		if (errors.hasFieldErrors()) {
			List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
			for (org.springframework.validation.FieldError errs : fieldErrors) {
				ErrorField f = new ErrorField(errs.getCode(), errs.getDefaultMessage(), errs.getField());
				error.setFields(new ArrayList<>());
				error.getFields().add(f);

			}
		}
		errRes.setError(error);
		return errRes;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidInputException.class)
	public ErrorResponse handleInvalidInputErrors(InvalidInputException ex) {
		ErrorResponse errRes = new ErrorResponse();
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setStatus(HttpStatus.BAD_REQUEST.toString());
		errRes.setResponseInfo(responseInfo);
		Error error = new Error();
		error.setCode(Integer.valueOf(HttpStatus.BAD_REQUEST.toString()));
		error.setMessage("Inavlid.Input");
		error.setDescription(ex.getCustomMsg());
		errRes.setError(error);
		return errRes;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public ErrorResponse handleThrowable(Exception ex) {
		ErrorResponse errRes = new ErrorResponse();
		ex.printStackTrace();
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setStatus(HttpStatus.BAD_REQUEST.toString());
		errRes.setResponseInfo(responseInfo);
		Error error = new Error();

		error.setCode(500);
		error.setMessage("Internal Server Error");
		error.setDescription(ex.getMessage());
		return errRes;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleServerError(Exception ex) {
		ex.printStackTrace();
		ErrorResponse errRes = new ErrorResponse();

		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		errRes.setResponseInfo(responseInfo);
		Error error = new Error();

		error.setCode(500);
		error.setMessage("Internal Server Error");
		error.setDescription(ex.getMessage());
		errRes.setError(error);
		return errRes;
	}

}