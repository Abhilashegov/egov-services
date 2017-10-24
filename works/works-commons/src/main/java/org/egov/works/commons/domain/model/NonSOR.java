package org.egov.works.commons.domain.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * NonSOR
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-24T13:08:31.335Z")

public class NonSOR {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("tenantId")
	private String tenantId = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("uom")
	private String uom = null;

	public NonSOR id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * Unique Identifier of the Non SOR
	 * 
	 * @return id
	 **/
	@ApiModelProperty(value = "Unique Identifier of the Non SOR")

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public NonSOR tenantId(String tenantId) {
		this.tenantId = tenantId;
		return this;
	}

	/**
	 * Tenant id of the Non SOR
	 * 
	 * @return tenantId
	 **/
	@ApiModelProperty(required = true, value = "Tenant id of the Non SOR")
	@NotNull

	@Size(min = 4, max = 128)
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public NonSOR description(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Description of the Non SOR
	 * 
	 * @return description
	 **/
	@ApiModelProperty(required = true, value = "Description of the Non SOR")
	@NotNull

	@Pattern(regexp = "[0-9a-zA-Z_@./#&+-/!(){}\",^$%*|=;:<>?`~ ]")
	@Size(min = 1, max = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public NonSOR uom(String uom) {
		this.uom = uom;
		return this;
	}

	/**
	 * UOM for the NonSOR
	 * 
	 * @return uom
	 **/
	@ApiModelProperty(required = true, value = "UOM for the NonSOR")
	@NotNull

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		NonSOR nonSOR = (NonSOR) o;
		return Objects.equals(this.id, nonSOR.id) && Objects.equals(this.tenantId, nonSOR.tenantId)
				&& Objects.equals(this.description, nonSOR.description) && Objects.equals(this.uom, nonSOR.uom);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tenantId, description, uom);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class NonSOR {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    uom: ").append(toIndentedString(uom)).append("\n");
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
