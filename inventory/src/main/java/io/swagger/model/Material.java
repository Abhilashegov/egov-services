package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 */
@ApiModel(description = "")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-28T13:21:55.964+05:30")

public class Material {
    @JsonProperty("id")
    private String id = null;


    @JsonProperty("code")
    private String code = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("oldCode")
    private String oldCode = null;

    @JsonProperty("materialType")
    private MaterialType materialType = null;

    @JsonProperty("baseUom")
    private Uom baseUom = null;

    /**
     * inventory type of the Material
     */
    public enum InventoryTypeEnum {
        ASSET("Asset"),

        CONSUMABLE("Consumable");

        private String value;

        InventoryTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static InventoryTypeEnum fromValue(String text) {
            for (InventoryTypeEnum b : InventoryTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("inventoryType")
    private InventoryTypeEnum inventoryType = null;

    /**
     * status of the Material
     */
    public enum StatusEnum {
        ACTIVE("Active"),

        WITHDRAWN("Withdrawn"),

        OBSOLETE("Obsolete");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("status")
    private StatusEnum status = null;

    @JsonProperty("purchaseUom")
    private Uom purchaseUom = null;

    @JsonProperty("expenseAccount")
    private ChartofAccount expenseAccount = null;

    @JsonProperty("minQuantity")
    private BigDecimal minQuantity = null;

    @JsonProperty("maxQuantity")
    private BigDecimal maxQuantity = null;

    @JsonProperty("staockingUom")
    private Uom staockingUom = null;

    /**
     * material class of the Material
     */
    public enum MaterialClassEnum {
        HIGHUSAGE("HighUsage"),

        MEDIUMUSAGE("MediumUsage"),

        LOWUSAGE("LowUsage");

        private String value;

        MaterialClassEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static MaterialClassEnum fromValue(String text) {
            for (MaterialClassEnum b : MaterialClassEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("materialClass")
    private MaterialClassEnum materialClass = null;

    @JsonProperty("reorderLevel")
    private BigDecimal reorderLevel = null;

    @JsonProperty("reorderQuantity")
    private BigDecimal reorderQuantity = null;

    /**
     * material control type of the Material
     */
    public enum MaterialControlTypeEnum {
        LOTCONTROL("LOTControl"),

        SHELF_LIFE_CONTROL("Shelf_life_Control");

        private String value;

        MaterialControlTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static MaterialControlTypeEnum fromValue(String text) {
            for (MaterialControlTypeEnum b : MaterialControlTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("materialControlType")
    private MaterialControlTypeEnum materialControlType = null;

    @JsonProperty("model")
    private String model = null;

    @JsonProperty("manufacturePartNo")
    private String manufacturePartNo = null;

    @JsonProperty("techincalSpecs")
    private String techincalSpecs = null;

    @JsonProperty("termsOfDelivery")
    private String termsOfDelivery = null;

    @JsonProperty("overrideMaterialControlType")
    private Boolean overrideMaterialControlType = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    public Material id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Unique Identifier of the Material
     *
     * @return id
     **/
    @ApiModelProperty(value = "Unique Identifier of the Material ")


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Tenant id of the Material
     *
     * @return tenantId
     **/
    @ApiModelProperty(value = "Tenant id of the Material")


    public Material code(String code) {
        this.code = code;
        return this;
    }

    /**
     * code of the Material
     *
     * @return code
     **/
    @ApiModelProperty(value = "code of the Material ")

    @Size(min = 5, max = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Material name(String name) {
        this.name = name;
        return this;
    }

    /**
     * name of the Material
     *
     * @return name
     **/
    @ApiModelProperty(required = true, value = "name of the Material ")
    @NotNull

    @Size(min = 5, max = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material description(String description) {
        this.description = description;
        return this;
    }

    /**
     * description of the Material
     *
     * @return description
     **/
    @ApiModelProperty(required = true, value = "description of the Material ")
    @NotNull

    @Size(max = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Material oldCode(String oldCode) {
        this.oldCode = oldCode;
        return this;
    }

    /**
     * unique old code of the Material
     *
     * @return oldCode
     **/
    @ApiModelProperty(value = "unique old code of the Material ")

    @Size(max = 50)
    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }

    public Material materialType(MaterialType materialType) {
        this.materialType = materialType;
        return this;
    }

    /**
     * Get materialType
     *
     * @return materialType
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public Material baseUom(Uom baseUom) {
        this.baseUom = baseUom;
        return this;
    }

    /**
     * Get baseUom
     *
     * @return baseUom
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public Uom getBaseUom() {
        return baseUom;
    }

    public void setBaseUom(Uom baseUom) {
        this.baseUom = baseUom;
    }

    public Material inventoryType(InventoryTypeEnum inventoryType) {
        this.inventoryType = inventoryType;
        return this;
    }

    /**
     * inventory type of the Material
     *
     * @return inventoryType
     **/
    @ApiModelProperty(value = "inventory type of the Material ")


    public InventoryTypeEnum getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(InventoryTypeEnum inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Material status(StatusEnum status) {
        this.status = status;
        return this;
    }

    /**
     * status of the Material
     *
     * @return status
     **/
    @ApiModelProperty(value = "status of the Material ")


    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Material purchaseUom(Uom purchaseUom) {
        this.purchaseUom = purchaseUom;
        return this;
    }

    /**
     * Get purchaseUom
     *
     * @return purchaseUom
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public Uom getPurchaseUom() {
        return purchaseUom;
    }

    public void setPurchaseUom(Uom purchaseUom) {
        this.purchaseUom = purchaseUom;
    }

    public Material expenseAccount(ChartofAccount expenseAccount) {
        this.expenseAccount = expenseAccount;
        return this;
    }

    /**
     * Get expenseAccount
     *
     * @return expenseAccount
     **/
    @ApiModelProperty(value = "")

    @Valid

    public ChartofAccount getExpenseAccount() {
        return expenseAccount;
    }

    public void setExpenseAccount(ChartofAccount expenseAccount) {
        this.expenseAccount = expenseAccount;
    }

    public Material minQuantity(BigDecimal minQuantity) {
        this.minQuantity = minQuantity;
        return this;
    }

    /**
     * min quantity of the Material
     *
     * @return minQuantity
     **/
    @ApiModelProperty(required = true, value = "min quantity of the Material ")
    @NotNull

    @Valid

    public BigDecimal getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(BigDecimal minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Material maxQuantity(BigDecimal maxQuantity) {
        this.maxQuantity = maxQuantity;
        return this;
    }

    /**
     * max quantity of the Material
     *
     * @return maxQuantity
     **/
    @ApiModelProperty(required = true, value = "max quantity of the Material ")
    @NotNull

    @Valid

    public BigDecimal getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(BigDecimal maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Material staockingUom(Uom staockingUom) {
        this.staockingUom = staockingUom;
        return this;
    }

    /**
     * Get staockingUom
     *
     * @return staockingUom
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public Uom getStaockingUom() {
        return staockingUom;
    }

    public void setStaockingUom(Uom staockingUom) {
        this.staockingUom = staockingUom;
    }

    public Material materialClass(MaterialClassEnum materialClass) {
        this.materialClass = materialClass;
        return this;
    }

    /**
     * material class of the Material
     *
     * @return materialClass
     **/
    @ApiModelProperty(required = true, value = "material class of the Material ")
    @NotNull


    public MaterialClassEnum getMaterialClass() {
        return materialClass;
    }

    public void setMaterialClass(MaterialClassEnum materialClass) {
        this.materialClass = materialClass;
    }

    public Material reorderLevel(BigDecimal reorderLevel) {
        this.reorderLevel = reorderLevel;
        return this;
    }

    /**
     * reorder level of the Material
     *
     * @return reorderLevel
     **/
    @ApiModelProperty(required = true, value = "reorder level of the Material ")
    @NotNull

    @Valid

    public BigDecimal getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(BigDecimal reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Material reorderQuantity(BigDecimal reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
        return this;
    }

    /**
     * reorder quantity of the Material
     *
     * @return reorderQuantity
     **/
    @ApiModelProperty(required = true, value = "reorder quantity of the Material ")
    @NotNull

    @Valid

    public BigDecimal getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(BigDecimal reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public Material materialControlType(MaterialControlTypeEnum materialControlType) {
        this.materialControlType = materialControlType;
        return this;
    }

    /**
     * material control type of the Material
     *
     * @return materialControlType
     **/
    @ApiModelProperty(required = true, value = "material control type of the Material ")
    @NotNull


    public MaterialControlTypeEnum getMaterialControlType() {
        return materialControlType;
    }

    public void setMaterialControlType(MaterialControlTypeEnum materialControlType) {
        this.materialControlType = materialControlType;
    }

    public Material model(String model) {
        this.model = model;
        return this;
    }

    /**
     * model of the Material
     *
     * @return model
     **/
    @ApiModelProperty(value = "model of the Material ")


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Material manufacturePartNo(String manufacturePartNo) {
        this.manufacturePartNo = manufacturePartNo;
        return this;
    }

    /**
     * manufacture part no of the Material
     *
     * @return manufacturePartNo
     **/
    @ApiModelProperty(value = "manufacture part no of the Material ")


    public String getManufacturePartNo() {
        return manufacturePartNo;
    }

    public void setManufacturePartNo(String manufacturePartNo) {
        this.manufacturePartNo = manufacturePartNo;
    }

    public Material techincalSpecs(String techincalSpecs) {
        this.techincalSpecs = techincalSpecs;
        return this;
    }

    /**
     * techincal specs of the Material
     *
     * @return techincalSpecs
     **/
    @ApiModelProperty(value = "techincal specs of the Material ")


    public String getTechincalSpecs() {
        return techincalSpecs;
    }

    public void setTechincalSpecs(String techincalSpecs) {
        this.techincalSpecs = techincalSpecs;
    }

    public Material termsOfDelivery(String termsOfDelivery) {
        this.termsOfDelivery = termsOfDelivery;
        return this;
    }

    /**
     * terms of delivery of the Material
     *
     * @return termsOfDelivery
     **/
    @ApiModelProperty(value = "terms of delivery of the Material ")


    public String getTermsOfDelivery() {
        return termsOfDelivery;
    }

    public void setTermsOfDelivery(String termsOfDelivery) {
        this.termsOfDelivery = termsOfDelivery;
    }

    public Material overrideMaterialControlType(Boolean overrideMaterialControlType) {
        this.overrideMaterialControlType = overrideMaterialControlType;
        return this;
    }

    /**
     * override material control type of the Material
     *
     * @return overrideMaterialControlType
     **/
    @ApiModelProperty(value = "override material control type of the Material ")


    public Boolean isOverrideMaterialControlType() {
        return overrideMaterialControlType;
    }

    public void setOverrideMaterialControlType(Boolean overrideMaterialControlType) {
        this.overrideMaterialControlType = overrideMaterialControlType;
    }

    public Material auditDetails(AuditDetails auditDetails) {
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
        Material material = (Material) o;
        return Objects.equals(this.id, material.id) &&
                Objects.equals(this.code, material.code) &&
                Objects.equals(this.name, material.name) &&
                Objects.equals(this.description, material.description) &&
                Objects.equals(this.oldCode, material.oldCode) &&
                Objects.equals(this.materialType, material.materialType) &&
                Objects.equals(this.baseUom, material.baseUom) &&
                Objects.equals(this.inventoryType, material.inventoryType) &&
                Objects.equals(this.status, material.status) &&
                Objects.equals(this.purchaseUom, material.purchaseUom) &&
                Objects.equals(this.expenseAccount, material.expenseAccount) &&
                Objects.equals(this.minQuantity, material.minQuantity) &&
                Objects.equals(this.maxQuantity, material.maxQuantity) &&
                Objects.equals(this.staockingUom, material.staockingUom) &&
                Objects.equals(this.materialClass, material.materialClass) &&
                Objects.equals(this.reorderLevel, material.reorderLevel) &&
                Objects.equals(this.reorderQuantity, material.reorderQuantity) &&
                Objects.equals(this.materialControlType, material.materialControlType) &&
                Objects.equals(this.model, material.model) &&
                Objects.equals(this.manufacturePartNo, material.manufacturePartNo) &&
                Objects.equals(this.techincalSpecs, material.techincalSpecs) &&
                Objects.equals(this.termsOfDelivery, material.termsOfDelivery) &&
                Objects.equals(this.overrideMaterialControlType, material.overrideMaterialControlType) &&
                Objects.equals(this.auditDetails, material.auditDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, description, oldCode, materialType, baseUom, inventoryType, status, purchaseUom, expenseAccount, minQuantity, maxQuantity, staockingUom, materialClass, reorderLevel, reorderQuantity, materialControlType, model, manufacturePartNo, techincalSpecs, termsOfDelivery, overrideMaterialControlType, auditDetails);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Material {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    code: ").append(toIndentedString(code)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    oldCode: ").append(toIndentedString(oldCode)).append("\n");
        sb.append("    materialType: ").append(toIndentedString(materialType)).append("\n");
        sb.append("    baseUom: ").append(toIndentedString(baseUom)).append("\n");
        sb.append("    inventoryType: ").append(toIndentedString(inventoryType)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    purchaseUom: ").append(toIndentedString(purchaseUom)).append("\n");
        sb.append("    expenseAccount: ").append(toIndentedString(expenseAccount)).append("\n");
        sb.append("    minQuantity: ").append(toIndentedString(minQuantity)).append("\n");
        sb.append("    maxQuantity: ").append(toIndentedString(maxQuantity)).append("\n");
        sb.append("    staockingUom: ").append(toIndentedString(staockingUom)).append("\n");
        sb.append("    materialClass: ").append(toIndentedString(materialClass)).append("\n");
        sb.append("    reorderLevel: ").append(toIndentedString(reorderLevel)).append("\n");
        sb.append("    reorderQuantity: ").append(toIndentedString(reorderQuantity)).append("\n");
        sb.append("    materialControlType: ").append(toIndentedString(materialControlType)).append("\n");
        sb.append("    model: ").append(toIndentedString(model)).append("\n");
        sb.append("    manufacturePartNo: ").append(toIndentedString(manufacturePartNo)).append("\n");
        sb.append("    techincalSpecs: ").append(toIndentedString(techincalSpecs)).append("\n");
        sb.append("    termsOfDelivery: ").append(toIndentedString(termsOfDelivery)).append("\n");
        sb.append("    overrideMaterialControlType: ").append(toIndentedString(overrideMaterialControlType)).append("\n");
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

