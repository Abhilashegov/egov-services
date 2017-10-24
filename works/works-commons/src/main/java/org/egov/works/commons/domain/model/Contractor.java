package org.egov.works.commons.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Contractor
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-24T13:08:31.335Z")

public class Contractor {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("tenantId")
	private String tenantId = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("code")
	private String code = null;

	@JsonProperty("correspondenceAddress")
	private String correspondenceAddress = null;

	@JsonProperty("paymentAddress")
	private String paymentAddress = null;

	@JsonProperty("contactPerson")
	private String contactPerson = null;

	@JsonProperty("email")
	private String email = null;

	@JsonProperty("narration")
	private String narration = null;

	@JsonProperty("mobileNumber")
	private BigDecimal mobileNumber = null;

	@JsonProperty("panNumber")
	private String panNumber = null;

	@JsonProperty("tinNumber")
	private String tinNumber = null;

	@JsonProperty("bank")
	private String bank = null;

	@JsonProperty("bankAccountNumber")
	private BigDecimal bankAccountNumber = null;

	@JsonProperty("pwdApprovalCode")
	private String pwdApprovalCode = null;

	@JsonProperty("exemptedFrom")
	private String exemptedFrom = null;

	@JsonProperty("pwdApprovalValidTill")
	private String pwdApprovalValidTill = null;

	@JsonProperty("epfRegistrationNumber")
	private String epfRegistrationNumber = null;

	@JsonProperty("accountCode")
	private BigDecimal accountCode = null;

	@JsonProperty("ifscCode")
	private BigDecimal ifscCode = null;

	@JsonProperty("contractorClass")
	private ContractorClass contractorClass = null;

	public Contractor id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * Unique Identifier of the Contractor.
	 * 
	 * @return id
	 **/
	@ApiModelProperty(value = "Unique Identifier of the Contractor.")

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Contractor tenantId(String tenantId) {
		this.tenantId = tenantId;
		return this;
	}

	/**
	 * Tenant id of the Contractor.
	 * 
	 * @return tenantId
	 **/
	@ApiModelProperty(required = true, value = "Tenant id of the Contractor.")
	@NotNull

	@Size(min = 4, max = 128)
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Contractor name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Name of the Contractor.
	 * 
	 * @return name
	 **/
	@ApiModelProperty(required = true, value = "Name of the Contractor.")
	@NotNull

	@Pattern(regexp = "[a-zA-Z0-9\\s\\.,]")
	@Size(min = 1, max = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Contractor code(String code) {
		this.code = code;
		return this;
	}

	/**
	 * Name of the Contractor.
	 * 
	 * @return code
	 **/
	@ApiModelProperty(required = true, value = "Name of the Contractor.")
	@NotNull

	@Pattern(regexp = "[a-zA-Z0-9-\\\\]")
	@Size(min = 1, max = 100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Contractor correspondenceAddress(String correspondenceAddress) {
		this.correspondenceAddress = correspondenceAddress;
		return this;
	}

	/**
	 * Correspondence Address of the Contractor.
	 * 
	 * @return correspondenceAddress
	 **/
	@ApiModelProperty(required = true, value = "Correspondence Address of the Contractor.")
	@NotNull

	@Pattern(regexp = "[0-9a-zA-Z_@./#&+-/!(){}\",^$%*|=;:<>?`~ ]")
	@Size(min = 1, max = 512)
	public String getCorrespondenceAddress() {
		return correspondenceAddress;
	}

	public void setCorrespondenceAddress(String correspondenceAddress) {
		this.correspondenceAddress = correspondenceAddress;
	}

	public Contractor paymentAddress(String paymentAddress) {
		this.paymentAddress = paymentAddress;
		return this;
	}

	/**
	 * Payment Address of the Contractor.
	 * 
	 * @return paymentAddress
	 **/
	@ApiModelProperty(value = "Payment Address of the Contractor.")

	@Pattern(regexp = "[0-9a-zA-Z_@./#&+-/!(){}\",^$%*|=;:<>?`~ ]")
	@Size(max = 512)
	public String getPaymentAddress() {
		return paymentAddress;
	}

	public void setPaymentAddress(String paymentAddress) {
		this.paymentAddress = paymentAddress;
	}

	public Contractor contactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
		return this;
	}

	/**
	 * Contact Person of the Contractor.
	 * 
	 * @return contactPerson
	 **/
	@ApiModelProperty(value = "Contact Person of the Contractor.")

	@Size(max = 100)
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public Contractor email(String email) {
		this.email = email;
		return this;
	}

	/**
	 * Email of the Contractor.
	 * 
	 * @return email
	 **/
	@ApiModelProperty(required = true, value = "Email of the Contractor.")
	@NotNull

	@Pattern(regexp = "/^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$/")
	@Size(max = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Contractor narration(String narration) {
		this.narration = narration;
		return this;
	}

	/**
	 * Narration of the Contractor.
	 * 
	 * @return narration
	 **/
	@ApiModelProperty(value = "Narration of the Contractor.")

	@Size(max = 1024)
	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public Contractor mobileNumber(BigDecimal mobileNumber) {
		this.mobileNumber = mobileNumber;
		return this;
	}

	/**
	 * Mobile Number of the Contractor
	 * 
	 * @return mobileNumber
	 **/
	@ApiModelProperty(required = true, value = "Mobile Number of the Contractor")
	@NotNull

	@Valid

	public BigDecimal getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(BigDecimal mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Contractor panNumber(String panNumber) {
		this.panNumber = panNumber;
		return this;
	}

	/**
	 * PAN Number of the Contractor
	 * 
	 * @return panNumber
	 **/
	@ApiModelProperty(required = true, value = "PAN Number of the Contractor")
	@NotNull

	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}")
	@Size(max = 10)
	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public Contractor tinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
		return this;
	}

	/**
	 * TIN Number of the Contractor
	 * 
	 * @return tinNumber
	 **/
	@ApiModelProperty(value = "TIN Number of the Contractor")

	@Size(max = 12)
	public String getTinNumber() {
		return tinNumber;
	}

	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}

	public Contractor bank(String bank) {
		this.bank = bank;
		return this;
	}

	/**
	 * Bank of the Contractor
	 * 
	 * @return bank
	 **/
	@ApiModelProperty(value = "Bank of the Contractor")

	@Size(max = 100)
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Contractor bankAccountNumber(BigDecimal bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
		return this;
	}

	/**
	 * Bank Account Number of the Contractor
	 * 
	 * @return bankAccountNumber
	 **/
	@ApiModelProperty(value = "Bank Account Number of the Contractor")

	@Valid

	public BigDecimal getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(BigDecimal bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public Contractor pwdApprovalCode(String pwdApprovalCode) {
		this.pwdApprovalCode = pwdApprovalCode;
		return this;
	}

	/**
	 * PWD Approval Code of the Contractor
	 * 
	 * @return pwdApprovalCode
	 **/
	@ApiModelProperty(value = "PWD Approval Code of the Contractor")

	@Size(max = 20)
	public String getPwdApprovalCode() {
		return pwdApprovalCode;
	}

	public void setPwdApprovalCode(String pwdApprovalCode) {
		this.pwdApprovalCode = pwdApprovalCode;
	}

	public Contractor exemptedFrom(String exemptedFrom) {
		this.exemptedFrom = exemptedFrom;
		return this;
	}

	/**
	 * Exempted From of the Contractor
	 * 
	 * @return exemptedFrom
	 **/
	@ApiModelProperty(value = "Exempted From of the Contractor")

	@Size(max = 100)
	public String getExemptedFrom() {
		return exemptedFrom;
	}

	public void setExemptedFrom(String exemptedFrom) {
		this.exemptedFrom = exemptedFrom;
	}

	public Contractor pwdApprovalValidTill(String pwdApprovalValidTill) {
		this.pwdApprovalValidTill = pwdApprovalValidTill;
		return this;
	}

	/**
	 * PWD Approval Valid Till
	 * 
	 * @return pwdApprovalValidTill
	 **/
	@ApiModelProperty(value = "PWD Approval Valid Till")

	public String getPwdApprovalValidTill() {
		return pwdApprovalValidTill;
	}

	public void setPwdApprovalValidTill(String pwdApprovalValidTill) {
		this.pwdApprovalValidTill = pwdApprovalValidTill;
	}

	public Contractor epfRegistrationNumber(String epfRegistrationNumber) {
		this.epfRegistrationNumber = epfRegistrationNumber;
		return this;
	}

	/**
	 * EPF Registration Number of the Contractor,Only Number value with decimal
	 * should be accepted
	 * 
	 * @return epfRegistrationNumber
	 **/
	@ApiModelProperty(value = "EPF Registration Number of the Contractor,Only Number value with decimal should be accepted")

	@Pattern(regexp = "[a-zA-Z0-9-\\\\]")
	@Size(max = 50)
	public String getEpfRegistrationNumber() {
		return epfRegistrationNumber;
	}

	public void setEpfRegistrationNumber(String epfRegistrationNumber) {
		this.epfRegistrationNumber = epfRegistrationNumber;
	}

	public Contractor accountCode(BigDecimal accountCode) {
		this.accountCode = accountCode;
		return this;
	}

	/**
	 * Account Code of the Contractor
	 * 
	 * @return accountCode
	 **/
	@ApiModelProperty(value = "Account Code of the Contractor")

	@Valid

	public BigDecimal getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(BigDecimal accountCode) {
		this.accountCode = accountCode;
	}

	public Contractor ifscCode(BigDecimal ifscCode) {
		this.ifscCode = ifscCode;
		return this;
	}

	/**
	 * IFSC Code of the Contractor
	 * 
	 * @return ifscCode
	 **/
	@ApiModelProperty(value = "IFSC Code of the Contractor")

	@Valid

	public BigDecimal getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(BigDecimal ifscCode) {
		this.ifscCode = ifscCode;
	}

	public Contractor contractorClass(ContractorClass contractorClass) {
		this.contractorClass = contractorClass;
		return this;
	}

	/**
	 * Get contractorClass
	 * 
	 * @return contractorClass
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull

	@Valid

	public ContractorClass getContractorClass() {
		return contractorClass;
	}

	public void setContractorClass(ContractorClass contractorClass) {
		this.contractorClass = contractorClass;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Contractor contractor = (Contractor) o;
		return Objects.equals(this.id, contractor.id) && Objects.equals(this.tenantId, contractor.tenantId)
				&& Objects.equals(this.name, contractor.name) && Objects.equals(this.code, contractor.code)
				&& Objects.equals(this.correspondenceAddress, contractor.correspondenceAddress)
				&& Objects.equals(this.paymentAddress, contractor.paymentAddress)
				&& Objects.equals(this.contactPerson, contractor.contactPerson)
				&& Objects.equals(this.email, contractor.email) && Objects.equals(this.narration, contractor.narration)
				&& Objects.equals(this.mobileNumber, contractor.mobileNumber)
				&& Objects.equals(this.panNumber, contractor.panNumber)
				&& Objects.equals(this.tinNumber, contractor.tinNumber) && Objects.equals(this.bank, contractor.bank)
				&& Objects.equals(this.bankAccountNumber, contractor.bankAccountNumber)
				&& Objects.equals(this.pwdApprovalCode, contractor.pwdApprovalCode)
				&& Objects.equals(this.exemptedFrom, contractor.exemptedFrom)
				&& Objects.equals(this.pwdApprovalValidTill, contractor.pwdApprovalValidTill)
				&& Objects.equals(this.epfRegistrationNumber, contractor.epfRegistrationNumber)
				&& Objects.equals(this.accountCode, contractor.accountCode)
				&& Objects.equals(this.ifscCode, contractor.ifscCode)
				&& Objects.equals(this.contractorClass, contractor.contractorClass);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tenantId, name, code, correspondenceAddress, paymentAddress, contactPerson, email,
				narration, mobileNumber, panNumber, tinNumber, bank, bankAccountNumber, pwdApprovalCode, exemptedFrom,
				pwdApprovalValidTill, epfRegistrationNumber, accountCode, ifscCode, contractorClass);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Contractor {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    code: ").append(toIndentedString(code)).append("\n");
		sb.append("    correspondenceAddress: ").append(toIndentedString(correspondenceAddress)).append("\n");
		sb.append("    paymentAddress: ").append(toIndentedString(paymentAddress)).append("\n");
		sb.append("    contactPerson: ").append(toIndentedString(contactPerson)).append("\n");
		sb.append("    email: ").append(toIndentedString(email)).append("\n");
		sb.append("    narration: ").append(toIndentedString(narration)).append("\n");
		sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
		sb.append("    panNumber: ").append(toIndentedString(panNumber)).append("\n");
		sb.append("    tinNumber: ").append(toIndentedString(tinNumber)).append("\n");
		sb.append("    bank: ").append(toIndentedString(bank)).append("\n");
		sb.append("    bankAccountNumber: ").append(toIndentedString(bankAccountNumber)).append("\n");
		sb.append("    pwdApprovalCode: ").append(toIndentedString(pwdApprovalCode)).append("\n");
		sb.append("    exemptedFrom: ").append(toIndentedString(exemptedFrom)).append("\n");
		sb.append("    pwdApprovalValidTill: ").append(toIndentedString(pwdApprovalValidTill)).append("\n");
		sb.append("    epfRegistrationNumber: ").append(toIndentedString(epfRegistrationNumber)).append("\n");
		sb.append("    accountCode: ").append(toIndentedString(accountCode)).append("\n");
		sb.append("    ifscCode: ").append(toIndentedString(ifscCode)).append("\n");
		sb.append("    contractorClass: ").append(toIndentedString(contractorClass)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
