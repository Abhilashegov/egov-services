/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) 2016  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any Long of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.egf.persistence.queue.contract;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@JsonPropertyOrder({ "id", "glcode", "name", "accountCodePurpose", "description", "isActiveForPosting", "parentId",
        "type", "classification", "functionRequired", "budgetCheckRequired", "majorCode", "isSubLedger",
        "chartOfAccountDetails" })
public class ChartOfAccountContract extends AuditableContract {

    private Long id;

    @NotNull
    @Length(max = 16, min = 1)
    private String glcode;
    @NotNull
    @Length(max = 128, min = 5)
    private String name;

    @JsonProperty(access = Access.WRITE_ONLY)
    private AccountCodePurposeContract accountCodePurpose;

    @Length(max = 256)
    private String description;

    @NotNull
    private Boolean isActiveForPosting;

    @JsonProperty(access = Access.WRITE_ONLY)
    private ChartOfAccountContract parentId;
    @NotNull
    private Character type;
    @NotNull
    private Long classification;
    @NotNull
    private Boolean functionRequired;
    @NotNull
    private Boolean budgetCheckRequired;

    @Length(max = 16)
    private String majorCode;

    @Transient
    private Boolean isSubLedger;

    @JsonProperty(access = Access.WRITE_ONLY)
    private Set<ChartOfAccountDetailContract> chartOfAccountDetails = new HashSet<ChartOfAccountDetailContract>();

    public ChartOfAccountContract(final String id) {
        super();
        this.id = Long.valueOf(id);
    }

//    public Boolean getIsSubledger() {
//        if (!chartOfAccountDetails.isEmpty())
//            return true;
//        else
//            return false;
//
//    }
}
