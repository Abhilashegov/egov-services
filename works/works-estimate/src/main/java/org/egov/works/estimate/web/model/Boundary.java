package org.egov.works.estimate.web.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Boundary
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-24T10:20:21.690Z")

@Getter
@Setter
public class Boundary {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("name")
	private String name = null;
	
	@JsonProperty("code")
	private String code = null;

	@JsonProperty("parent")
	private Boundary parent = null;

	@JsonProperty("boundary_num")
	private String boundaryNum = null;

	@JsonProperty("from_date")
	private LocalDate fromDate = null;

	@JsonProperty("to_date")
	private LocalDate toDate = null;

	@JsonProperty("is_history")
	private String isHistory = null;

	@JsonProperty("bndry_id")
	private String bndryId = null;

	@JsonProperty("local_name")
	private String localName = null;

	@JsonProperty("longitude")
	private String longitude = null;

	@JsonProperty("latitude")
	private String latitude = null;

	@JsonProperty("boundaryType")
	private BoundaryType boundaryType = null;

	@JsonProperty("materialized_path")
	private String materializedPath = null;

	public Boundary id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * unique id for the boundary.
	 * 
	 * @return id
	 **/
	@ApiModelProperty(value = "unique id for the boundary.")

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boundary name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * name of the boundary.
	 * 
	 * @return name
	 **/
	@ApiModelProperty(value = "name of the boundary.")

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boundary parent(Boundary parent) {
		this.parent = parent;
		return this;
	}

	/**
	 * Get parent
	 * 
	 * @return parent
	 **/
	@ApiModelProperty(value = "")

	@Valid

	public Boundary getParent() {
		return parent;
	}

	public void setParent(Boundary parent) {
		this.parent = parent;
	}

	public Boundary boundaryNum(String boundaryNum) {
		this.boundaryNum = boundaryNum;
		return this;
	}

	/**
	 * boundary number of the boundary.
	 * 
	 * @return boundaryNum
	 **/
	@ApiModelProperty(value = "boundary number of the boundary.")

	public String getBoundaryNum() {
		return boundaryNum;
	}

	public void setBoundaryNum(String boundaryNum) {
		this.boundaryNum = boundaryNum;
	}

	public Boundary fromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
		return this;
	}

	/**
	 * from date of the boundary.
	 * 
	 * @return fromDate
	 **/
	@ApiModelProperty(value = "from date of the boundary.")

	@Valid

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public Boundary toDate(LocalDate toDate) {
		this.toDate = toDate;
		return this;
	}

	/**
	 * to date of the boundary.
	 * 
	 * @return toDate
	 **/
	@ApiModelProperty(value = "to date of the boundary.")

	@Valid

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public Boundary isHistory(String isHistory) {
		this.isHistory = isHistory;
		return this;
	}

	/**
	 * is history of the boundary.
	 * 
	 * @return isHistory
	 **/
	@ApiModelProperty(value = "is history of the boundary.")

	public String getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(String isHistory) {
		this.isHistory = isHistory;
	}

	public Boundary bndryId(String bndryId) {
		this.bndryId = bndryId;
		return this;
	}

	/**
	 * bndry Id of the boundary.
	 * 
	 * @return bndryId
	 **/
	@ApiModelProperty(value = "bndry Id of the boundary.")

	public String getBndryId() {
		return bndryId;
	}

	public void setBndryId(String bndryId) {
		this.bndryId = bndryId;
	}

	public Boundary localName(String localName) {
		this.localName = localName;
		return this;
	}

	/**
	 * local name of the boundary.
	 * 
	 * @return localName
	 **/
	@ApiModelProperty(value = "local name of the boundary.")

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public Boundary longitude(String longitude) {
		this.longitude = longitude;
		return this;
	}

	/**
	 * longitude of the boundary.
	 * 
	 * @return longitude
	 **/
	@ApiModelProperty(value = "longitude of the boundary.")

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Boundary latitude(String latitude) {
		this.latitude = latitude;
		return this;
	}

	/**
	 * latitude of the boundary.
	 * 
	 * @return latitude
	 **/
	@ApiModelProperty(value = "latitude of the boundary.")

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Boundary boundaryType(BoundaryType boundaryType) {
		this.boundaryType = boundaryType;
		return this;
	}

	/**
	 * Get boundaryType
	 * 
	 * @return boundaryType
	 **/
	@ApiModelProperty(value = "")

	@Valid

	public BoundaryType getBoundaryType() {
		return boundaryType;
	}

	public void setBoundaryType(BoundaryType boundaryType) {
		this.boundaryType = boundaryType;
	}

	public Boundary materializedPath(String materializedPath) {
		this.materializedPath = materializedPath;
		return this;
	}

	/**
	 * materialized path of the boundary.
	 * 
	 * @return materializedPath
	 **/
	@ApiModelProperty(value = "materialized path of the boundary.")

	public String getMaterializedPath() {
		return materializedPath;
	}

	public void setMaterializedPath(String materializedPath) {
		this.materializedPath = materializedPath;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Boundary boundary = (Boundary) o;
		return Objects.equals(this.id, boundary.id) && Objects.equals(this.name, boundary.name)
				&& Objects.equals(this.parent, boundary.parent)
				&& Objects.equals(this.boundaryNum, boundary.boundaryNum)
				&& Objects.equals(this.fromDate, boundary.fromDate) && Objects.equals(this.toDate, boundary.toDate)
				&& Objects.equals(this.isHistory, boundary.isHistory) && Objects.equals(this.bndryId, boundary.bndryId)
				&& Objects.equals(this.localName, boundary.localName)
				&& Objects.equals(this.longitude, boundary.longitude)
				&& Objects.equals(this.latitude, boundary.latitude)
				&& Objects.equals(this.boundaryType, boundary.boundaryType)
				&& Objects.equals(this.materializedPath, boundary.materializedPath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, parent, boundaryNum, fromDate, toDate, isHistory, bndryId, localName, longitude,
				latitude, boundaryType, materializedPath);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Boundary {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
		sb.append("    boundaryNum: ").append(toIndentedString(boundaryNum)).append("\n");
		sb.append("    fromDate: ").append(toIndentedString(fromDate)).append("\n");
		sb.append("    toDate: ").append(toIndentedString(toDate)).append("\n");
		sb.append("    isHistory: ").append(toIndentedString(isHistory)).append("\n");
		sb.append("    bndryId: ").append(toIndentedString(bndryId)).append("\n");
		sb.append("    localName: ").append(toIndentedString(localName)).append("\n");
		sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
		sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
		sb.append("    boundaryType: ").append(toIndentedString(boundaryType)).append("\n");
		sb.append("    materializedPath: ").append(toIndentedString(materializedPath)).append("\n");
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
