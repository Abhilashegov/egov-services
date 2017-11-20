package org.egov.works.measurementbook.web.contract;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * An Object that holds the basic data for a MB Measurement Sheet
 */
@ApiModel(description = "An Object that holds the basic data for a MB Measurement Sheet")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-19T06:59:20.916Z")

public class MBMeasurementSheet {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("tenantId")
	private String tenantId = null;

	@JsonProperty("remarks")
	private String remarks = null;

	@JsonProperty("no")
	private BigDecimal no = null;

	@JsonProperty("length")
	private BigDecimal length = null;

	@JsonProperty("width")
	private BigDecimal width = null;

	@JsonProperty("depthOrHeight")
	private BigDecimal depthOrHeight = null;

	@JsonProperty("quantity")
	private BigDecimal quantity = null;

	@JsonProperty("measurementBookDetail")
	private MeasurementBookDetail measurementBookDetail = null;

	@JsonProperty("auditDetails")
	private AuditDetails auditDetails = null;

	public MBMeasurementSheet id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * Unique Identifier of the Measurement Sheet
	 * 
	 * @return id
	 **/
	@ApiModelProperty(value = "Unique Identifier of the Measurement Sheet")

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MBMeasurementSheet tenantId(String tenantId) {
		this.tenantId = tenantId;
		return this;
	}

	/**
	 * Tenant id of the Measurement Sheet
	 * 
	 * @return tenantId
	 **/
	@ApiModelProperty(required = true, value = "Tenant id of the Measurement Sheet")
	@NotNull

	@Size(min = 2, max = 128)
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public MBMeasurementSheet remarks(String remarks) {
		this.remarks = remarks;
		return this;
	}

	/**
	 * Remarks of the Measurement sheet
	 * 
	 * @return remarks
	 **/
	@ApiModelProperty(value = "Remarks of the Measurement sheet")

	@Pattern(regexp = "[0-9a-zA-Z_@./#&+-/!(){}\",^$%*|=;:<>?`~ ]")
	@Size(max = 1024)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public MBMeasurementSheet no(BigDecimal no) {
		this.no = no;
		return this;
	}

	/**
	 * No. of the Measurement sheet
	 * 
	 * @return no
	 **/
	@ApiModelProperty(value = "No. of the Measurement sheet")

	@Valid

	public BigDecimal getNo() {
		return no;
	}

	public void setNo(BigDecimal no) {
		this.no = no;
	}

	public MBMeasurementSheet length(BigDecimal length) {
		this.length = length;
		return this;
	}

	/**
	 * Length of the Measurement sheet
	 * 
	 * @return length
	 **/
	@ApiModelProperty(value = "Length of the Measurement sheet")

	@Valid

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public MBMeasurementSheet width(BigDecimal width) {
		this.width = width;
		return this;
	}

	/**
	 * Width of the Measurement sheet
	 * 
	 * @return width
	 **/
	@ApiModelProperty(value = "Width of the Measurement sheet")

	@Valid

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public MBMeasurementSheet depthOrHeight(BigDecimal depthOrHeight) {
		this.depthOrHeight = depthOrHeight;
		return this;
	}

	/**
	 * Depth or Height of the Measurement sheet
	 * 
	 * @return depthOrHeight
	 **/
	@ApiModelProperty(value = "Depth or Height of the Measurement sheet")

	@Valid

	public BigDecimal getDepthOrHeight() {
		return depthOrHeight;
	}

	public void setDepthOrHeight(BigDecimal depthOrHeight) {
		this.depthOrHeight = depthOrHeight;
	}

	public MBMeasurementSheet quantity(BigDecimal quantity) {
		this.quantity = quantity;
		return this;
	}

	/**
	 * Quantity of the Measurement sheet
	 * 
	 * @return quantity
	 **/
	@ApiModelProperty(required = true, value = "Quantity of the Measurement sheet")
	@NotNull

	@Valid

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public MBMeasurementSheet measurementBookDetail(MeasurementBookDetail measurementBookDetail) {
		this.measurementBookDetail = measurementBookDetail;
		return this;
	}

	/**
	 * Measurement Book Details reference
	 * 
	 * @return measurementBookDetail
	 **/
	@ApiModelProperty(required = true, value = "Measurement Book Details reference")
	@NotNull

	@Valid

	public MeasurementBookDetail getMeasurementBookDetail() {
		return measurementBookDetail;
	}

	public void setMeasurementBookDetail(MeasurementBookDetail measurementBookDetail) {
		this.measurementBookDetail = measurementBookDetail;
	}

	public MBMeasurementSheet auditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
		return this;
	}

	/**
	 * Get auditDetails
	 * 
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
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MBMeasurementSheet mbMeasurementSheet = (MBMeasurementSheet) o;
		return Objects.equals(this.id, mbMeasurementSheet.id)
				&& Objects.equals(this.tenantId, mbMeasurementSheet.tenantId)
				&& Objects.equals(this.remarks, mbMeasurementSheet.remarks)
				&& Objects.equals(this.no, mbMeasurementSheet.no)
				&& Objects.equals(this.length, mbMeasurementSheet.length)
				&& Objects.equals(this.width, mbMeasurementSheet.width)
				&& Objects.equals(this.depthOrHeight, mbMeasurementSheet.depthOrHeight)
				&& Objects.equals(this.quantity, mbMeasurementSheet.quantity)
				&& Objects.equals(this.measurementBookDetail, mbMeasurementSheet.measurementBookDetail)
				&& Objects.equals(this.auditDetails, mbMeasurementSheet.auditDetails);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tenantId, remarks, no, length, width, depthOrHeight, quantity, measurementBookDetail,
				auditDetails);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class MBMeasurementSheet {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
		sb.append("    remarks: ").append(toIndentedString(remarks)).append("\n");
		sb.append("    no: ").append(toIndentedString(no)).append("\n");
		sb.append("    length: ").append(toIndentedString(length)).append("\n");
		sb.append("    width: ").append(toIndentedString(width)).append("\n");
		sb.append("    depthOrHeight: ").append(toIndentedString(depthOrHeight)).append("\n");
		sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
		sb.append("    measurementBookDetail: ").append(toIndentedString(measurementBookDetail)).append("\n");
		sb.append("    auditDetails: ").append(toIndentedString(auditDetails)).append("\n");
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