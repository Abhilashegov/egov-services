package org.egov.tradelicense.persistence.entity;

import org.egov.tradelicense.domain.model.AuditDetails;
import org.egov.tradelicense.domain.model.SupportDocument;
import org.egov.tradelicense.domain.model.SupportDocumentSearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportDocumentSearchEntity {

	public static final String TABLE_NAME = "egtl_support_document";
	public static final String SEQUENCE_NAME = "seq_egtl_support_document";

	private Long id;

	private Long licenseId;

	private Long documentTypeId;

	private String documentTypeName;

	private String fileStoreId;

	private String comments;

	private String createdBy;

	private String lastModifiedBy;

	private Long createdTime;

	private Long lastModifiedTime;

	public SupportDocumentSearch toDomain() {

		SupportDocumentSearch supportDocument = new SupportDocumentSearch();

		AuditDetails auditDetails = new AuditDetails();

		supportDocument.setId(this.id);

		supportDocument.setLicenseId(this.licenseId);

		supportDocument.setDocumentTypeId(this.documentTypeId);

		supportDocument.setFileStoreId(String.valueOf(this.fileStoreId));

		supportDocument.setComments(this.comments);

		supportDocument.setDocumentTypeName(this.documentTypeName);

		supportDocument.setCreatedBy(this.createdBy);
		supportDocument.setCreatedTime(this.createdTime);
		supportDocument.setLastModifiedBy(this.lastModifiedBy);
		supportDocument.setLastModifiedTime(this.lastModifiedTime);

		return supportDocument;
	}

	public SupportDocumentSearchEntity toEntity(SupportDocumentSearch supportDocument) {

		this.id = supportDocument.getId();

		this.licenseId = supportDocument.getLicenseId();

		this.documentTypeId = supportDocument.getDocumentTypeId();

		if (supportDocument.getFileStoreId() != null) {
			this.fileStoreId = supportDocument.getFileStoreId().toString();
		}

		this.documentTypeName = supportDocument.getDocumentTypeName();

		this.comments = supportDocument.getComments();

		this.createdBy = supportDocument.getCreatedBy();

		this.lastModifiedBy = supportDocument.getLastModifiedBy();

		this.createdTime = supportDocument.getCreatedTime();

		this.lastModifiedTime = supportDocument.getLastModifiedTime();

		return this;
	}
}
