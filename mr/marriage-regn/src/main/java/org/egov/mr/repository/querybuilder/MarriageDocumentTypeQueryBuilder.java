package org.egov.mr.repository.querybuilder;

import java.util.List;

import org.egov.mr.web.contract.MarriageDocumentTypeSearchCriteria;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MarriageDocumentTypeQueryBuilder {

	public final static String BATCH_INSERT_QUERY = "INSERT INTO egmr_marriage_document_type "
			+ "(id,name,code,isactive,isindividual,isrequired,proof,appltype,tenantid)" + " values (?,?,?,?,?,?,?,?,?)";

	public final static String BATCH_UPDATE_QUERY = "UPDATE egmr_marriage_document_type SET"
			+ " name=?, isactive=?, isindividual=?, isrequired=?, proof=?, appltype=?"
			+ " WHERE id=? AND code=? AND tenantid=?";

	public final String BASEQUERY = "SELECT * FROM egmr_marriage_document_type ";

	StringBuilder selectQuery;

	// Query for _search
	public String getSelectQuery(MarriageDocumentTypeSearchCriteria marriageDocumentTypeSearchCriteria,
			List<Object> preparedStatementValues) {
		selectQuery = new StringBuilder(BASEQUERY);
		addWhereClause(marriageDocumentTypeSearchCriteria, preparedStatementValues);
		log.info(selectQuery.toString());
		return selectQuery.toString();
	}

	private void addWhereClause(MarriageDocumentTypeSearchCriteria marriageDocumentTypeSearchCriteria,
			List<Object> preparedStatementValues) {
		if (marriageDocumentTypeSearchCriteria.getIsActive() == null
				&& marriageDocumentTypeSearchCriteria.getApplicationType() == null
				&& marriageDocumentTypeSearchCriteria.getTenantId() == null) {
			return;
		}
		selectQuery.append("WHERE ");
		boolean addAndClauseIfRequiredFlag = false;

		if (marriageDocumentTypeSearchCriteria.getIsActive() != null) {
			addAndClauseIfRequiredFlag = addAndClauseIfRequired(addAndClauseIfRequiredFlag);
			selectQuery.append("isactive=? ");
			preparedStatementValues.add(marriageDocumentTypeSearchCriteria.getIsActive());
		}
		if (marriageDocumentTypeSearchCriteria.getApplicationType() != null) {
			addAndClauseIfRequiredFlag = addAndClauseIfRequired(addAndClauseIfRequiredFlag);
			selectQuery.append("appltype=? ");
			preparedStatementValues.add(marriageDocumentTypeSearchCriteria.getApplicationType());
		}
		addAndClauseIfRequiredFlag = addAndClauseIfRequired(addAndClauseIfRequiredFlag);
		selectQuery.append("tenantid=? ");
		preparedStatementValues.add(marriageDocumentTypeSearchCriteria.getTenantId());
		selectQuery.append(";");
	}

	// Helper Method
	private Boolean addAndClauseIfRequired(Boolean addAndClauseIfRequiredFlag) {
		if (addAndClauseIfRequiredFlag) {
			selectQuery.append("AND ");
		}
		return true;
	}

	// Query to generate Unique Id
	public String getIdNextValForMarriageDocType() {
		return "SELECT NEXTVAL('seq_marriage_document_type') ;";
	}
}
