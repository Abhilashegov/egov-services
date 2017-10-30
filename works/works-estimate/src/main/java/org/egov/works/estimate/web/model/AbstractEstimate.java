package org.egov.works.estimate.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.egov.works.commons.domain.enums.Beneficiary;
import org.egov.works.commons.domain.enums.WorkCategory;
import org.egov.works.commons.domain.model.AuditDetails;
import org.egov.works.commons.domain.model.TypeOfWork;
import org.egov.works.commons.domain.model.WorkFlowDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * AN Object which holds the basic data for AbstractEstimate
 */
@ApiModel(description = "AN Object which holds the basic data for AbstractEstimate")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-28T12:22:31.360Z")

public class AbstractEstimate   {

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("tenantId")
  private String tenantId = null;

  @JsonProperty("abstractEstimateNumber")
  private String abstractEstimateNumber = null;

  @JsonProperty("subject")
  private String subject = null;

  @JsonProperty("referenceType")
  private String referenceType = null;

  @JsonProperty("referenceNumber")
  private String referenceNumber = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("fund")
  private Fund fund = null;

  @JsonProperty("function")
  private Function function = null;

  @JsonProperty("budgetGroup")
  private BudgetGroup budgetGroup = null;

  @JsonProperty("scheme")
  private Scheme scheme = null;

  @JsonProperty("subScheme")
  private SubScheme subScheme = null;

  @JsonProperty("dateOfProposal")
  private Long dateOfProposal = null;

  @JsonProperty("department")
  private Department department = null;

  @JsonProperty("adminSanctionNumber")
  private String adminSanctionNumber = null;

  @JsonProperty("adminSanctionDate")
  private Long adminSanctionDate = null;

  @JsonProperty("adminSanctionBy")
  private User adminSanctionBy = null;

  @JsonProperty("abstractEstimateDetails")
  private List<AbstractEstimateDetails> abstractEstimateDetails = new ArrayList<AbstractEstimateDetails>();

  @JsonProperty("documentDetails")
  private List<DocumentDetail> documentDetails = null;

  @JsonProperty("status")
  private AbstractEstimateStatus status = null;

  @JsonProperty("beneficiary")
  private Beneficiary beneficiary = null;

  @JsonProperty("modeOfAllotment")
  private ModeOfAllotment modeOfAllotment = null;

  @JsonProperty("typeOfWork")
  private TypeOfWork typeOfWork = null;

  @JsonProperty("subTypeOfWork")
  private TypeOfWork subTypeOfWork = null;

  @JsonProperty("natureOfWork")
  private NatureOfWork natureOfWork = null;

  @JsonProperty("ward")
  private Boundary ward = null;

  @JsonProperty("technicalSanctionNumber")
  private String technicalSanctionNumber = null;

  @JsonProperty("technicalSanctionDate")
  private Long technicalSanctionDate = null;

  @JsonProperty("technicalSanctionBy")
  private User technicalSanctionBy = null;

  @JsonProperty("locality")
  private Boundary locality = null;

  @JsonProperty("workCategory")
  private WorkCategory workCategory = null;

  @JsonProperty("councilResolutionNumber")
  private String councilResolutionNumber = null;

  @JsonProperty("councilResolutionDate")
  private Long councilResolutionDate = null;

  @JsonProperty("spillOverFlag")
  private Boolean spillOverFlag = null;

  @JsonProperty("detailedEstimateCreated")
  private Boolean detailedEstimateCreated = null;

  @JsonProperty("workOrderCreated")
  private Boolean workOrderCreated = null;

  @JsonProperty("billsCreated")
  private Boolean billsCreated = null;

  @JsonProperty("cancellationReason")
  private String cancellationReason = null;

  @JsonProperty("cancellationRemarks")
  private String cancellationRemarks = null;

  @JsonProperty("workFlowDetails")
  private WorkFlowDetails workFlowDetails = null;

  @JsonProperty("stateId")
  private String stateId = null;

  @JsonProperty("asset")
  private Asset asset = null;

  @JsonProperty("implementationPeriod")
  private Integer implementationPeriod = null;

  @JsonProperty("fundAvailable")
  private Boolean fundAvailable = null;

  @JsonProperty("fundSanctioningAuthority")
  private String fundSanctioningAuthority = null;

  @JsonProperty("pmcRequired")
  private Boolean pmcRequired = null;

  @JsonProperty("pmcType")
  private String pmcType = null;

  @JsonProperty("pmcName")
  private String pmcName = null;

  @JsonProperty("auditDetails")
  private AuditDetails auditDetails = null;

  public AbstractEstimate id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique Identifier of the Abstract Estimate
   * @return id
  **/
  @ApiModelProperty(value = "Unique Identifier of the Abstract Estimate")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public AbstractEstimate tenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

   /**
   * Tenant id of the Abstract Estimate
   * @return tenantId
  **/
  @ApiModelProperty(required = true, value = "Tenant id of the Abstract Estimate")
  @NotNull

 @Size(min=4,max=128)
  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public AbstractEstimate abstractEstimateNumber(String abstractEstimateNumber) {
    this.abstractEstimateNumber = abstractEstimateNumber;
    return this;
  }

   /**
   * Unique number for the Abstract Estimate
   * @return abstractEstimateNumber
  **/
  @ApiModelProperty(value = "Unique number for the Abstract Estimate")

 @Pattern(regexp="[a-zA-Z0-9-\\\\]+") @Size(min=1,max=20)
  public String getAbstractEstimateNumber() {
    return abstractEstimateNumber;
  }

  public void setAbstractEstimateNumber(String abstractEstimateNumber) {
    this.abstractEstimateNumber = abstractEstimateNumber;
  }

  public AbstractEstimate subject(String subject) {
    this.subject = subject;
    return this;
  }

   /**
   * Subject of Abstract Estimate
   * @return subject
  **/
  @ApiModelProperty(required = true, value = "Subject of Abstract Estimate")
  @NotNull

 @Size(min=1,max=256)
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public AbstractEstimate referenceType(String referenceType) {
    this.referenceType = referenceType;
    return this;
  }

   /**
   * Reference Type of the Abstract Estimate
   * @return referenceType
  **/
  @ApiModelProperty(value = "Reference Type of the Abstract Estimate")


  public String getReferenceType() {
    return referenceType;
  }

  public void setReferenceType(String referenceType) {
    this.referenceType = referenceType;
  }

  public AbstractEstimate referenceNumber(String referenceNumber) {
    this.referenceNumber = referenceNumber;
    return this;
  }

   /**
   * Letter Reference Number of the Abstract Estimate
   * @return referenceNumber
  **/
  @ApiModelProperty(required = true, value = "Letter Reference Number of the Abstract Estimate")
  @NotNull

 @Size(min=1,max=100)
  public String getReferenceNumber() {
    return referenceNumber;
  }

  public void setReferenceNumber(String referenceNumber) {
    this.referenceNumber = referenceNumber;
  }

  public AbstractEstimate description(String description) {
    this.description = description;
    return this;
  }

   /**
   * description for the Abstract Estimate
   * @return description
  **/
  @ApiModelProperty(required = true, value = "description for the Abstract Estimate")
  @NotNull

 @Pattern(regexp="[0-9a-zA-Z_@./#&+-/!(){}\",^$%*|=;:<>?`~ ]+") @Size(min=1,max=1024)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public AbstractEstimate fund(Fund fund) {
    this.fund = fund;
    return this;
  }

   /**
   * Fund of the Abstract Estimate
   * @return fund
  **/
  @ApiModelProperty(value = "Fund of the Abstract Estimate")

  @Valid

  public Fund getFund() {
    return fund;
  }

  public void setFund(Fund fund) {
    this.fund = fund;
  }

  public AbstractEstimate function(Function function) {
    this.function = function;
    return this;
  }

   /**
   * Function of the Abstract Estimate
   * @return function
  **/
  @ApiModelProperty(value = "Function of the Abstract Estimate")

  @Valid

  public Function getFunction() {
    return function;
  }

  public void setFunction(Function function) {
    this.function = function;
  }

  public AbstractEstimate budgetGroup(BudgetGroup budgetGroup) {
    this.budgetGroup = budgetGroup;
    return this;
  }

   /**
   * Budget head of the Abstract Estimate
   * @return budgetGroup
  **/
  @ApiModelProperty(value = "Budget head of the Abstract Estimate")

  @Valid

  public BudgetGroup getBudgetGroup() {
    return budgetGroup;
  }

  public void setBudgetGroup(BudgetGroup budgetGroup) {
    this.budgetGroup = budgetGroup;
  }

  public AbstractEstimate scheme(Scheme scheme) {
    this.scheme = scheme;
    return this;
  }

   /**
   * Scheme of the Abstract Estimate
   * @return scheme
  **/
  @ApiModelProperty(value = "Scheme of the Abstract Estimate")

  @Valid

  public Scheme getScheme() {
    return scheme;
  }

  public void setScheme(Scheme scheme) {
    this.scheme = scheme;
  }

  public AbstractEstimate subScheme(SubScheme subScheme) {
    this.subScheme = subScheme;
    return this;
  }

   /**
   * Sub scheme of the Abstract Estimate
   * @return subScheme
  **/
  @ApiModelProperty(value = "Sub scheme of the Abstract Estimate")

  @Valid

  public SubScheme getSubScheme() {
    return subScheme;
  }

  public void setSubScheme(SubScheme subScheme) {
    this.subScheme = subScheme;
  }

  public AbstractEstimate dateOfProposal(Long dateOfProposal) {
    this.dateOfProposal = dateOfProposal;
    return this;
  }

   /**
   * Epoch time of the Date of Proposal
   * @return dateOfProposal
  **/
  @ApiModelProperty(required = true, value = "Epoch time of the Date of Proposal")
  @NotNull


  public Long getDateOfProposal() {
    return dateOfProposal;
  }

  public void setDateOfProposal(Long dateOfProposal) {
    this.dateOfProposal = dateOfProposal;
  }

  public AbstractEstimate department(Department department) {
    this.department = department;
    return this;
  }

   /**
   * Department for which Abstract Estimate belongs to
   * @return department
  **/
  @ApiModelProperty(required = true, value = "Department for which Abstract Estimate belongs to")
  @NotNull

  @Valid

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public AbstractEstimate adminSanctionNumber(String adminSanctionNumber) {
    this.adminSanctionNumber = adminSanctionNumber;
    return this;
  }

   /**
   * Unique number after admin sanction for the Abstract Estimate
   * @return adminSanctionNumber
  **/
  @ApiModelProperty(value = "Unique number after admin sanction for the Abstract Estimate")

 @Pattern(regexp="[a-zA-Z0-9-\\\\]+") @Size(min=1,max=50)
  public String getAdminSanctionNumber() {
    return adminSanctionNumber;
  }

  public void setAdminSanctionNumber(String adminSanctionNumber) {
    this.adminSanctionNumber = adminSanctionNumber;
  }

  public AbstractEstimate adminSanctionDate(Long adminSanctionDate) {
    this.adminSanctionDate = adminSanctionDate;
    return this;
  }

   /**
   * Epoch time of when the admin santion done
   * @return adminSanctionDate
  **/
  @ApiModelProperty(value = "Epoch time of when the admin santion done")


  public Long getAdminSanctionDate() {
    return adminSanctionDate;
  }

  public void setAdminSanctionDate(Long adminSanctionDate) {
    this.adminSanctionDate = adminSanctionDate;
  }

  public AbstractEstimate adminSanctionBy(User adminSanctionBy) {
    this.adminSanctionBy = adminSanctionBy;
    return this;
  }

   /**
   * User who admin sanctioned
   * @return adminSanctionBy
  **/
  @ApiModelProperty(value = "User who admin sanctioned")

  @Valid

  public User getAdminSanctionBy() {
    return adminSanctionBy;
  }

  public void setAdminSanctionBy(User adminSanctionBy) {
    this.adminSanctionBy = adminSanctionBy;
  }

  public AbstractEstimate abstractEstimateDetails(List<AbstractEstimateDetails> abstractEstimateDetails) {
    this.abstractEstimateDetails = abstractEstimateDetails;
    return this;
  }

  public AbstractEstimate addAbstractEstimateDetailsItem(AbstractEstimateDetails abstractEstimateDetailsItem) {
    this.abstractEstimateDetails.add(abstractEstimateDetailsItem);
    return this;
  }

   /**
   * Array of Abstract Estimate Details
   * @return abstractEstimateDetails
  **/
  @ApiModelProperty(required = true, value = "Array of Abstract Estimate Details")
  @NotNull

  @Valid
 @Size(min=1)
  public List<AbstractEstimateDetails> getAbstractEstimateDetails() {
    return abstractEstimateDetails;
  }

  public void setAbstractEstimateDetails(List<AbstractEstimateDetails> abstractEstimateDetails) {
    this.abstractEstimateDetails = abstractEstimateDetails;
  }

  public AbstractEstimate documentDetails(List<DocumentDetail> documentDetails) {
    this.documentDetails = documentDetails;
    return this;
  }

  public AbstractEstimate addDocumentDetailsItem(DocumentDetail documentDetailsItem) {
    if (this.documentDetails == null) {
      this.documentDetails = new ArrayList<DocumentDetail>();
    }
    this.documentDetails.add(documentDetailsItem);
    return this;
  }

   /**
   * Array of document details
   * @return documentDetails
  **/
  @ApiModelProperty(value = "Array of document details")

  @Valid

  public List<DocumentDetail> getDocumentDetails() {
    return documentDetails;
  }

  public void setDocumentDetails(List<DocumentDetail> documentDetails) {
    this.documentDetails = documentDetails;
  }

  public AbstractEstimate status(AbstractEstimateStatus status) {
    this.status = status;
    return this;
  }

   /**
   * Status of the Abstract Estimate
   * @return status
  **/
  @ApiModelProperty(required = true, value = "Status of the Abstract Estimate")
  @NotNull

  @Valid

  public AbstractEstimateStatus getStatus() {
    return status;
  }

  public void setStatus(AbstractEstimateStatus status) {
    this.status = status;
  }

  public AbstractEstimate beneficiary(Beneficiary beneficiary) {
    this.beneficiary = beneficiary;
    return this;
  }

   /**
   * The Beneficiary of this work
   * @return beneficiary
  **/
  @ApiModelProperty(required = true, value = "The Beneficiary of this work")
  @NotNull

  @Valid

  public Beneficiary getBeneficiary() {
    return beneficiary;
  }

  public void setBeneficiary(Beneficiary beneficiary) {
    this.beneficiary = beneficiary;
  }

  public AbstractEstimate modeOfAllotment(ModeOfAllotment modeOfAllotment) {
    this.modeOfAllotment = modeOfAllotment;
    return this;
  }

   /**
   * The Recommended Mode of Allotment of the work
   * @return modeOfAllotment
  **/
  @ApiModelProperty(required = true, value = "The Recommended Mode of Allotment of the work")
  @NotNull

  @Valid

  public ModeOfAllotment getModeOfAllotment() {
    return modeOfAllotment;
  }

  public void setModeOfAllotment(ModeOfAllotment modeOfAllotment) {
    this.modeOfAllotment = modeOfAllotment;
  }

  public AbstractEstimate typeOfWork(TypeOfWork typeOfWork) {
    this.typeOfWork = typeOfWork;
    return this;
  }

   /**
   * The Type of work for which this Abstract Estimate belongs to
   * @return typeOfWork
  **/
  @ApiModelProperty(required = true, value = "The Type of work for which this Abstract Estimate belongs to")
  @NotNull

  @Valid

  public TypeOfWork getTypeOfWork() {
    return typeOfWork;
  }

  public void setTypeOfWork(TypeOfWork typeOfWork) {
    this.typeOfWork = typeOfWork;
  }

  public AbstractEstimate subTypeOfWork(TypeOfWork subTypeOfWork) {
    this.subTypeOfWork = subTypeOfWork;
    return this;
  }

   /**
   * The Sub Type of work for which this Abstract Estimate belongs to
   * @return subTypeOfWork
  **/
  @ApiModelProperty(value = "The Sub Type of work for which this Abstract Estimate belongs to")

  @Valid

  public TypeOfWork getSubTypeOfWork() {
    return subTypeOfWork;
  }

  public void setSubTypeOfWork(TypeOfWork subTypeOfWork) {
    this.subTypeOfWork = subTypeOfWork;
  }

  public AbstractEstimate natureOfWork(NatureOfWork natureOfWork) {
    this.natureOfWork = natureOfWork;
    return this;
  }

   /**
   * The Nature of work for which this Abstract Estimate belongs to
   * @return natureOfWork
  **/
  @ApiModelProperty(required = true, value = "The Nature of work for which this Abstract Estimate belongs to")
  @NotNull

  @Valid

  public NatureOfWork getNatureOfWork() {
    return natureOfWork;
  }

  public void setNatureOfWork(NatureOfWork natureOfWork) {
    this.natureOfWork = natureOfWork;
  }

  public AbstractEstimate ward(Boundary ward) {
    this.ward = ward;
    return this;
  }

   /**
   * The Admin Ward for which this Abstract Estimate belongs to
   * @return ward
  **/
  @ApiModelProperty(required = true, value = "The Admin Ward for which this Abstract Estimate belongs to")
  @NotNull

  @Valid

  public Boundary getWard() {
    return ward;
  }

  public void setWard(Boundary ward) {
    this.ward = ward;
  }

  public AbstractEstimate technicalSanctionNumber(String technicalSanctionNumber) {
    this.technicalSanctionNumber = technicalSanctionNumber;
    return this;
  }

   /**
   * Technical sanction number of the Abstract Estimate
   * @return technicalSanctionNumber
  **/
  @ApiModelProperty(value = "Technical sanction number of the Abstract Estimate")

 @Pattern(regexp="[a-zA-Z0-9-\\\\]+") @Size(max=50)
  public String getTechnicalSanctionNumber() {
    return technicalSanctionNumber;
  }

  public void setTechnicalSanctionNumber(String technicalSanctionNumber) {
    this.technicalSanctionNumber = technicalSanctionNumber;
  }

  public AbstractEstimate technicalSanctionDate(Long technicalSanctionDate) {
    this.technicalSanctionDate = technicalSanctionDate;
    return this;
  }

   /**
   * Epoch time of when technical sanctioned
   * @return technicalSanctionDate
  **/
  @ApiModelProperty(value = "Epoch time of when technical sanctioned")


  public Long getTechnicalSanctionDate() {
    return technicalSanctionDate;
  }

  public void setTechnicalSanctionDate(Long technicalSanctionDate) {
    this.technicalSanctionDate = technicalSanctionDate;
  }

  public AbstractEstimate technicalSanctionBy(User technicalSanctionBy) {
    this.technicalSanctionBy = technicalSanctionBy;
    return this;
  }

   /**
   * The user who technical sanctioned the Abstract Estimate
   * @return technicalSanctionBy
  **/
  @ApiModelProperty(value = "The user who technical sanctioned the Abstract Estimate")

  @Valid

  public User getTechnicalSanctionBy() {
    return technicalSanctionBy;
  }

  public void setTechnicalSanctionBy(User technicalSanctionBy) {
    this.technicalSanctionBy = technicalSanctionBy;
  }

  public AbstractEstimate locality(Boundary locality) {
    this.locality = locality;
    return this;
  }

   /**
   * The Locality in which the Abstract Estimate belongs to
   * @return locality
  **/
  @ApiModelProperty(value = "The Locality in which the Abstract Estimate belongs to")

  @Valid

  public Boundary getLocality() {
    return locality;
  }

  public void setLocality(Boundary locality) {
    this.locality = locality;
  }

  public AbstractEstimate workCategory(WorkCategory workCategory) {
    this.workCategory = workCategory;
    return this;
  }

   /**
   * The Work Category of the Abstract Estimate
   * @return workCategory
  **/
  @ApiModelProperty(required = true, value = "The Work Category of the Abstract Estimate")
  @NotNull

  @Valid

  public WorkCategory getWorkCategory() {
    return workCategory;
  }

  public void setWorkCategory(WorkCategory workCategory) {
    this.workCategory = workCategory;
  }

  public AbstractEstimate councilResolutionNumber(String councilResolutionNumber) {
    this.councilResolutionNumber = councilResolutionNumber;
    return this;
  }

   /**
   * Council resolution number of the Abstract Estimate
   * @return councilResolutionNumber
  **/
  @ApiModelProperty(value = "Council resolution number of the Abstract Estimate")

 @Pattern(regexp="[a-zA-Z0-9-\\\\]+")
  public String getCouncilResolutionNumber() {
    return councilResolutionNumber;
  }

  public void setCouncilResolutionNumber(String councilResolutionNumber) {
    this.councilResolutionNumber = councilResolutionNumber;
  }

  public AbstractEstimate councilResolutionDate(Long councilResolutionDate) {
    this.councilResolutionDate = councilResolutionDate;
    return this;
  }

   /**
   * Epoch time of the council resolution date
   * @return councilResolutionDate
  **/
  @ApiModelProperty(value = "Epoch time of the council resolution date")


  public Long getCouncilResolutionDate() {
    return councilResolutionDate;
  }

  public void setCouncilResolutionDate(Long councilResolutionDate) {
    this.councilResolutionDate = councilResolutionDate;
  }

  public AbstractEstimate spillOverFlag(Boolean spillOverFlag) {
    this.spillOverFlag = spillOverFlag;
    return this;
  }

   /**
   * Boolean value if spill over or not. Required only if Abstract Estimate is Spill Over. Default value is false.
   * @return spillOverFlag
  **/
  @ApiModelProperty(value = "Boolean value if spill over or not. Required only if Abstract Estimate is Spill Over. Default value is false.")


  public Boolean getSpillOverFlag() {
    return spillOverFlag;
  }

  public void setSpillOverFlag(Boolean spillOverFlag) {
    this.spillOverFlag = spillOverFlag;
  }

  public AbstractEstimate detailedEstimateCreated(Boolean detailedEstimateCreated) {
    this.detailedEstimateCreated = detailedEstimateCreated;
    return this;
  }

   /**
   * Boolean value if the detailed estimate created or not. Required only if Abstract Estimate is Spill Over. Default value is false.
   * @return detailedEstimateCreated
  **/
  @ApiModelProperty(value = "Boolean value if the detailed estimate created or not. Required only if Abstract Estimate is Spill Over. Default value is false.")


  public Boolean getDetailedEstimateCreated() {
    return detailedEstimateCreated;
  }

  public void setDetailedEstimateCreated(Boolean detailedEstimateCreated) {
    this.detailedEstimateCreated = detailedEstimateCreated;
  }

  public AbstractEstimate workOrderCreated(Boolean workOrderCreated) {
    this.workOrderCreated = workOrderCreated;
    return this;
  }

   /**
   * Boolean value if work order created or not, Required only if Abstract Estimate is Spill Over. Default value is false.
   * @return workOrderCreated
  **/
  @ApiModelProperty(value = "Boolean value if work order created or not, Required only if Abstract Estimate is Spill Over. Default value is false.")


  public Boolean getWorkOrderCreated() {
    return workOrderCreated;
  }

  public void setWorkOrderCreated(Boolean workOrderCreated) {
    this.workOrderCreated = workOrderCreated;
  }

  public AbstractEstimate billsCreated(Boolean billsCreated) {
    this.billsCreated = billsCreated;
    return this;
  }

   /**
   * Boolean value if bills created or not. Required only if Abstract Estimate is Spill Over. Default value is false.
   * @return billsCreated
  **/
  @ApiModelProperty(value = "Boolean value if bills created or not. Required only if Abstract Estimate is Spill Over. Default value is false.")


  public Boolean getBillsCreated() {
    return billsCreated;
  }

  public void setBillsCreated(Boolean billsCreated) {
    this.billsCreated = billsCreated;
  }

  public AbstractEstimate cancellationReason(String cancellationReason) {
    this.cancellationReason = cancellationReason;
    return this;
  }

   /**
   * Reason for cancellation of the Abstract Estimate, Required only while cancelling Abstract Estimate
   * @return cancellationReason
  **/
  @ApiModelProperty(value = "Reason for cancellation of the Abstract Estimate, Required only while cancelling Abstract Estimate")

 @Pattern(regexp="[0-9a-zA-Z_@./#&+-/!(){}\",^$%*|=;:<>?`~ ]+") @Size(max=100)
  public String getCancellationReason() {
    return cancellationReason;
  }

  public void setCancellationReason(String cancellationReason) {
    this.cancellationReason = cancellationReason;
  }

  public AbstractEstimate cancellationRemarks(String cancellationRemarks) {
    this.cancellationRemarks = cancellationRemarks;
    return this;
  }

   /**
   * Remarks for cancellation of the Abstract Estimate, Required only while cancelling Abstract Estimate
   * @return cancellationRemarks
  **/
  @ApiModelProperty(value = "Remarks for cancellation of the Abstract Estimate, Required only while cancelling Abstract Estimate")

 @Pattern(regexp="[0-9a-zA-Z_@./#&+-/!(){}\",^$%*|=;:<>?`~ ]+") @Size(max=512)
  public String getCancellationRemarks() {
    return cancellationRemarks;
  }

  public void setCancellationRemarks(String cancellationRemarks) {
    this.cancellationRemarks = cancellationRemarks;
  }

  public AbstractEstimate workFlowDetails(WorkFlowDetails workFlowDetails) {
    this.workFlowDetails = workFlowDetails;
    return this;
  }

   /**
   * Get workFlowDetails
   * @return workFlowDetails
  **/
  @ApiModelProperty(value = "")

  @Valid

  public WorkFlowDetails getWorkFlowDetails() {
    return workFlowDetails;
  }

  public void setWorkFlowDetails(WorkFlowDetails workFlowDetails) {
    this.workFlowDetails = workFlowDetails;
  }

  public AbstractEstimate stateId(String stateId) {
    this.stateId = stateId;
    return this;
  }

   /**
   * State id of the workflow
   * @return stateId
  **/
  @ApiModelProperty(value = "State id of the workflow")


  public String getStateId() {
    return stateId;
  }

  public void setStateId(String stateId) {
    this.stateId = stateId;
  }

  public AbstractEstimate asset(Asset asset) {
    this.asset = asset;
    return this;
  }

   /**
   * Asset for which this Abstract Estimate is created
   * @return asset
  **/
  @ApiModelProperty(value = "Asset for which this Abstract Estimate is created")

  @Valid

  public Asset getAsset() {
    return asset;
  }

  public void setAsset(Asset asset) {
    this.asset = asset;
  }

  public AbstractEstimate implementationPeriod(Integer implementationPeriod) {
    this.implementationPeriod = implementationPeriod;
    return this;
  }

   /**
   * Approximate period of implementation of the work.
   * @return implementationPeriod
  **/
  @ApiModelProperty(value = "Approximate period of implementation of the work.")


  public Integer getImplementationPeriod() {
    return implementationPeriod;
  }

  public void setImplementationPeriod(Integer implementationPeriod) {
    this.implementationPeriod = implementationPeriod;
  }

  public AbstractEstimate fundAvailable(Boolean fundAvailable) {
    this.fundAvailable = fundAvailable;
    return this;
  }

   /**
   * Boolean vlaue to capture whether the fund available to execute this work.
   * @return fundAvailable
  **/
  @ApiModelProperty(value = "Boolean vlaue to capture whether the fund available to execute this work.")


  public Boolean getFundAvailable() {
    return fundAvailable;
  }

  public void setFundAvailable(Boolean fundAvailable) {
    this.fundAvailable = fundAvailable;
  }

  public AbstractEstimate fundSanctioningAuthority(String fundSanctioningAuthority) {
    this.fundSanctioningAuthority = fundSanctioningAuthority;
    return this;
  }

   /**
   * The sanctioning Authority for fund to carry out the work
   * @return fundSanctioningAuthority
  **/
  @ApiModelProperty(value = "The sanctioning Authority for fund to carry out the work")

 @Pattern(regexp="[a-zA-Z0-9\\s\\.,]+")
  public String getFundSanctioningAuthority() {
    return fundSanctioningAuthority;
  }

  public void setFundSanctioningAuthority(String fundSanctioningAuthority) {
    this.fundSanctioningAuthority = fundSanctioningAuthority;
  }

  public AbstractEstimate pmcRequired(Boolean pmcRequired) {
    this.pmcRequired = pmcRequired;
    return this;
  }

   /**
   * Boolean vlaue to capture whether the PMC need to appoint or not.
   * @return pmcRequired
  **/
  @ApiModelProperty(value = "Boolean vlaue to capture whether the PMC need to appoint or not.")


  public Boolean getPmcRequired() {
    return pmcRequired;
  }

  public void setPmcRequired(Boolean pmcRequired) {
    this.pmcRequired = pmcRequired;
  }

  public AbstractEstimate pmcType(String pmcType) {
    this.pmcType = pmcType;
    return this;
  }

   /**
   * If PMC required is yes then to capture PMC is from Panel or New Appointment.
   * @return pmcType
  **/
  @ApiModelProperty(value = "If PMC required is yes then to capture PMC is from Panel or New Appointment.")


  public String getPmcType() {
    return pmcType;
  }

  public void setPmcType(String pmcType) {
    this.pmcType = pmcType;
  }

  public AbstractEstimate pmcName(String pmcName) {
    this.pmcName = pmcName;
    return this;
  }

   /**
   * Name of the PMC Panel/New Appointment.
   * @return pmcName
  **/
  @ApiModelProperty(value = "Name of the PMC Panel/New Appointment.")

 @Pattern(regexp="[a-zA-Z0-9\\s\\.,]+")
  public String getPmcName() {
    return pmcName;
  }

  public void setPmcName(String pmcName) {
    this.pmcName = pmcName;
  }

  public AbstractEstimate auditDetails(AuditDetails auditDetails) {
    this.auditDetails = auditDetails;
    return this;
  }

   /**
   * Get auditDetails
   * @return auditDetails
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AuditDetails getAuditDetails() {
    return auditDetails;
  }

  public void setAuditDetails(AuditDetails auditDetails) {
    this.auditDetails = auditDetails;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractEstimate abstractEstimate = (AbstractEstimate) o;
    return Objects.equals(this.id, abstractEstimate.id) &&
        Objects.equals(this.tenantId, abstractEstimate.tenantId) &&
        Objects.equals(this.abstractEstimateNumber, abstractEstimate.abstractEstimateNumber) &&
        Objects.equals(this.subject, abstractEstimate.subject) &&
        Objects.equals(this.referenceType, abstractEstimate.referenceType) &&
        Objects.equals(this.referenceNumber, abstractEstimate.referenceNumber) &&
        Objects.equals(this.description, abstractEstimate.description) &&
        Objects.equals(this.fund, abstractEstimate.fund) &&
        Objects.equals(this.function, abstractEstimate.function) &&
        Objects.equals(this.budgetGroup, abstractEstimate.budgetGroup) &&
        Objects.equals(this.scheme, abstractEstimate.scheme) &&
        Objects.equals(this.subScheme, abstractEstimate.subScheme) &&
        Objects.equals(this.dateOfProposal, abstractEstimate.dateOfProposal) &&
        Objects.equals(this.department, abstractEstimate.department) &&
        Objects.equals(this.adminSanctionNumber, abstractEstimate.adminSanctionNumber) &&
        Objects.equals(this.adminSanctionDate, abstractEstimate.adminSanctionDate) &&
        Objects.equals(this.adminSanctionBy, abstractEstimate.adminSanctionBy) &&
        Objects.equals(this.abstractEstimateDetails, abstractEstimate.abstractEstimateDetails) &&
        Objects.equals(this.documentDetails, abstractEstimate.documentDetails) &&
        Objects.equals(this.status, abstractEstimate.status) &&
        Objects.equals(this.beneficiary, abstractEstimate.beneficiary) &&
        Objects.equals(this.modeOfAllotment, abstractEstimate.modeOfAllotment) &&
        Objects.equals(this.typeOfWork, abstractEstimate.typeOfWork) &&
        Objects.equals(this.subTypeOfWork, abstractEstimate.subTypeOfWork) &&
        Objects.equals(this.natureOfWork, abstractEstimate.natureOfWork) &&
        Objects.equals(this.ward, abstractEstimate.ward) &&
        Objects.equals(this.technicalSanctionNumber, abstractEstimate.technicalSanctionNumber) &&
        Objects.equals(this.technicalSanctionDate, abstractEstimate.technicalSanctionDate) &&
        Objects.equals(this.technicalSanctionBy, abstractEstimate.technicalSanctionBy) &&
        Objects.equals(this.locality, abstractEstimate.locality) &&
        Objects.equals(this.workCategory, abstractEstimate.workCategory) &&
        Objects.equals(this.councilResolutionNumber, abstractEstimate.councilResolutionNumber) &&
        Objects.equals(this.councilResolutionDate, abstractEstimate.councilResolutionDate) &&
        Objects.equals(this.spillOverFlag, abstractEstimate.spillOverFlag) &&
        Objects.equals(this.detailedEstimateCreated, abstractEstimate.detailedEstimateCreated) &&
        Objects.equals(this.workOrderCreated, abstractEstimate.workOrderCreated) &&
        Objects.equals(this.billsCreated, abstractEstimate.billsCreated) &&
        Objects.equals(this.cancellationReason, abstractEstimate.cancellationReason) &&
        Objects.equals(this.cancellationRemarks, abstractEstimate.cancellationRemarks) &&
        Objects.equals(this.workFlowDetails, abstractEstimate.workFlowDetails) &&
        Objects.equals(this.stateId, abstractEstimate.stateId) &&
        Objects.equals(this.asset, abstractEstimate.asset) &&
        Objects.equals(this.implementationPeriod, abstractEstimate.implementationPeriod) &&
        Objects.equals(this.fundAvailable, abstractEstimate.fundAvailable) &&
        Objects.equals(this.fundSanctioningAuthority, abstractEstimate.fundSanctioningAuthority) &&
        Objects.equals(this.pmcRequired, abstractEstimate.pmcRequired) &&
        Objects.equals(this.pmcType, abstractEstimate.pmcType) &&
        Objects.equals(this.pmcName, abstractEstimate.pmcName) &&
        Objects.equals(this.auditDetails, abstractEstimate.auditDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, tenantId, abstractEstimateNumber, subject, referenceType, referenceNumber, description, fund, function, budgetGroup, scheme, subScheme, dateOfProposal, department, adminSanctionNumber, adminSanctionDate, adminSanctionBy, abstractEstimateDetails, documentDetails, status, beneficiary, modeOfAllotment, typeOfWork, subTypeOfWork, natureOfWork, ward, technicalSanctionNumber, technicalSanctionDate, technicalSanctionBy, locality, workCategory, councilResolutionNumber, councilResolutionDate, spillOverFlag, detailedEstimateCreated, workOrderCreated, billsCreated, cancellationReason, cancellationRemarks, workFlowDetails, stateId, asset, implementationPeriod, fundAvailable, fundSanctioningAuthority, pmcRequired, pmcType, pmcName, auditDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AbstractEstimate {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    abstractEstimateNumber: ").append(toIndentedString(abstractEstimateNumber)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    referenceType: ").append(toIndentedString(referenceType)).append("\n");
    sb.append("    referenceNumber: ").append(toIndentedString(referenceNumber)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    fund: ").append(toIndentedString(fund)).append("\n");
    sb.append("    function: ").append(toIndentedString(function)).append("\n");
    sb.append("    budgetGroup: ").append(toIndentedString(budgetGroup)).append("\n");
    sb.append("    scheme: ").append(toIndentedString(scheme)).append("\n");
    sb.append("    subScheme: ").append(toIndentedString(subScheme)).append("\n");
    sb.append("    dateOfProposal: ").append(toIndentedString(dateOfProposal)).append("\n");
    sb.append("    department: ").append(toIndentedString(department)).append("\n");
    sb.append("    adminSanctionNumber: ").append(toIndentedString(adminSanctionNumber)).append("\n");
    sb.append("    adminSanctionDate: ").append(toIndentedString(adminSanctionDate)).append("\n");
    sb.append("    adminSanctionBy: ").append(toIndentedString(adminSanctionBy)).append("\n");
    sb.append("    abstractEstimateDetails: ").append(toIndentedString(abstractEstimateDetails)).append("\n");
    sb.append("    documentDetails: ").append(toIndentedString(documentDetails)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    beneficiary: ").append(toIndentedString(beneficiary)).append("\n");
    sb.append("    modeOfAllotment: ").append(toIndentedString(modeOfAllotment)).append("\n");
    sb.append("    typeOfWork: ").append(toIndentedString(typeOfWork)).append("\n");
    sb.append("    subTypeOfWork: ").append(toIndentedString(subTypeOfWork)).append("\n");
    sb.append("    natureOfWork: ").append(toIndentedString(natureOfWork)).append("\n");
    sb.append("    ward: ").append(toIndentedString(ward)).append("\n");
    sb.append("    technicalSanctionNumber: ").append(toIndentedString(technicalSanctionNumber)).append("\n");
    sb.append("    technicalSanctionDate: ").append(toIndentedString(technicalSanctionDate)).append("\n");
    sb.append("    technicalSanctionBy: ").append(toIndentedString(technicalSanctionBy)).append("\n");
    sb.append("    locality: ").append(toIndentedString(locality)).append("\n");
    sb.append("    workCategory: ").append(toIndentedString(workCategory)).append("\n");
    sb.append("    councilResolutionNumber: ").append(toIndentedString(councilResolutionNumber)).append("\n");
    sb.append("    councilResolutionDate: ").append(toIndentedString(councilResolutionDate)).append("\n");
    sb.append("    spillOverFlag: ").append(toIndentedString(spillOverFlag)).append("\n");
    sb.append("    detailedEstimateCreated: ").append(toIndentedString(detailedEstimateCreated)).append("\n");
    sb.append("    workOrderCreated: ").append(toIndentedString(workOrderCreated)).append("\n");
    sb.append("    billsCreated: ").append(toIndentedString(billsCreated)).append("\n");
    sb.append("    cancellationReason: ").append(toIndentedString(cancellationReason)).append("\n");
    sb.append("    cancellationRemarks: ").append(toIndentedString(cancellationRemarks)).append("\n");
    sb.append("    workFlowDetails: ").append(toIndentedString(workFlowDetails)).append("\n");
    sb.append("    stateId: ").append(toIndentedString(stateId)).append("\n");
    sb.append("    asset: ").append(toIndentedString(asset)).append("\n");
    sb.append("    implementationPeriod: ").append(toIndentedString(implementationPeriod)).append("\n");
    sb.append("    fundAvailable: ").append(toIndentedString(fundAvailable)).append("\n");
    sb.append("    fundSanctioningAuthority: ").append(toIndentedString(fundSanctioningAuthority)).append("\n");
    sb.append("    pmcRequired: ").append(toIndentedString(pmcRequired)).append("\n");
    sb.append("    pmcType: ").append(toIndentedString(pmcType)).append("\n");
    sb.append("    pmcName: ").append(toIndentedString(pmcName)).append("\n");
    sb.append("    auditDetails: ").append(toIndentedString(auditDetails)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

