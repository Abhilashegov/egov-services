package org.egov.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.egov.ReportApp;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.domain.model.MetaDataRequest;
import org.egov.domain.model.ReportDefinitions;
import org.egov.domain.model.Response;
import org.egov.report.repository.ReportRepository;
import org.egov.swagger.model.ColumnDetail;
import org.egov.swagger.model.ColumnDetail.TypeEnum;
import org.egov.swagger.model.MetadataResponse;
import org.egov.swagger.model.ReportDefinition;
import org.egov.swagger.model.ReportMetadata;
import org.egov.swagger.model.ReportRequest;
import org.egov.swagger.model.ReportResponse;
import org.egov.swagger.model.SearchColumn;
import org.egov.swagger.model.SourceColumn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private Response responseInfoFactory;
	
	@Autowired
	private IntegrationService integrationService;

	public static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);

	public MetadataResponse getMetaData(MetaDataRequest metaDataRequest) {
		MetadataResponse metadataResponse = new MetadataResponse();
		ReportDefinitions rds = ReportApp.getReportDefs();
		ReportDefinition reportDefinition = new ReportDefinition();
		LOGGER.info("updated repot defs " + ReportApp.getReportDefs() + "\n\n\n");
		reportDefinition = rds.getReportDefinition(metaDataRequest.getReportName());
		ReportMetadata rmt = new ReportMetadata();
		rmt.setReportName(reportDefinition.getReportName());
        rmt.setSummary(reportDefinition.getSummary());
		List<ColumnDetail> reportHeaders = new ArrayList<>();
		List<ColumnDetail> searchParams = new ArrayList<>();
		for (SourceColumn cd : reportDefinition.getSourceColumns()) {
			ColumnDetail reportheader = new ColumnDetail();
			reportheader.setLabel(cd.getLabel());
			reportheader.setName(cd.getName());
			TypeEnum te = TypeEnum.valueOf(cd.getType().toString().toUpperCase());
			
			reportheader.setType(te);
			reportHeaders.add(reportheader);
            
		}
		for (SearchColumn cd : reportDefinition.getSearchParams()) {
			
			ColumnDetail sc = new ColumnDetail();
			
			TypeEnum te = TypeEnum.valueOf(cd.getType().toString().toUpperCase());
			sc.setType(te);
			sc.setLabel(cd.getLabel());
			sc.setName(cd.getName());

			sc.setDefaultValue(cd.getPattern());
			sc.setIsMandatory(cd.getIsMandatory());

			searchParams.add(sc);

		}
		rmt.setReportHeader(reportHeaders);
		rmt.setSearchParams(searchParams);
		metadataResponse.setReportDetails(rmt);
		metadataResponse.setTenantId(metaDataRequest.getTenantId());
		try {
			integrationService.getData(reportDefinition, metadataResponse, metaDataRequest.getRequestInfo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metadataResponse;
	}

	

	public ResponseEntity<?> getSuccessResponse(final MetadataResponse metadataResponse, final RequestInfo requestInfo,
			String tenantID) {
		final MetadataResponse metadataResponses = new MetadataResponse();
		final ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true);
		responseInfo.setStatus(HttpStatus.OK.toString());
		metadataResponses.setRequestInfo(responseInfo);
		metadataResponses.setTenantId(tenantID);
		metadataResponses.setReportDetails(metadataResponse.getReportDetails());
		return new ResponseEntity<>(metadataResponses, HttpStatus.OK);

	}
	public ResponseEntity<?> getFailureResponse( final RequestInfo requestInfo,
			String tenantID) {
		final MetadataResponse metadataResponses = new MetadataResponse();
		final ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, false);
		responseInfo.setResMsgId("Report Defintion not found");
		metadataResponses.setRequestInfo(responseInfo);
		metadataResponses.setTenantId(tenantID);
		return new ResponseEntity<>(metadataResponses, HttpStatus.NOT_FOUND);

	}
	public ResponseEntity<?> reloadResponse( final RequestInfo requestInfo) {
		final MetadataResponse metadataResponses = new MetadataResponse();
		final ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true);
		responseInfo.setResMsgId("Report reloaded successfully");
		metadataResponses.setRequestInfo(responseInfo);
		return new ResponseEntity<>(metadataResponses, HttpStatus.OK);

	}

	public ReportResponse getReportData(ReportRequest reportRequest) {
		
		List<ReportDefinition> listReportDefinitions  = ReportApp.getReportDefs().getReportDefinitions();
		 
		ReportDefinition reportDefinition = listReportDefinitions.stream()
				.filter(t -> t.getReportName().equals(reportRequest.getReportName())).findFirst().orElse(null);
		LOGGER.info("reportYamlMetaData::" + reportDefinition);
		List<Map<String, Object>> maps = reportRepository.getData(reportRequest, reportDefinition);
		List<SourceColumn> columns = reportDefinition.getSourceColumns();
		LOGGER.info("columns::" + columns);
		LOGGER.info("maps::" + maps);

		ReportResponse reportResponse = new ReportResponse();
		populateData(columns, maps, reportResponse);
		populateReportHeader(reportDefinition, reportResponse);

		return reportResponse;
	}

	private void populateData(List<SourceColumn> columns, List<Map<String, Object>> maps,
			ReportResponse reportResponse) {

		List<List<Object>> lists = new ArrayList<>();

		for (int i = 0; i < maps.size(); i++) {
			List<Object> objects = new ArrayList<>();
			Map<String, Object> map = maps.get(i);
			for (SourceColumn sourceColm : columns) {
				
				objects.add(map.get(sourceColm.getName()));
			}
			lists.add(objects);
		}
		reportResponse.setReportData(lists);
	}
	private void populateReportHeader(ReportDefinition reportDefinition, ReportResponse reportResponse) {
		
		//Let's check whether there's a linked report, we will set the default value in header columns according to that
				
				String pattern = null;
				String defaultValue = null;
				
				
				List<SourceColumn> columns = reportDefinition.getSourceColumns();
				for(SourceColumn sc : columns) {
					pattern = "";
					defaultValue="";
					if (sc.getLinkedReport() != null)
					{
						LOGGER.info("Linked Report Pattern is: "+sc.getLinkedReport());
						pattern = sc.getLinkedReport().getLinkedColumn();
						defaultValue = pattern.replace("{reportName}", sc.getLinkedReport().getReportName());
						sc.setDefaultValue(defaultValue.replace("{currentColumnName}", sc.getName()));
					}
				}
				List<ColumnDetail> columnDetails = columns.stream()
						.map(p -> new ColumnDetail(p.getShowColumn(),p.getLabel(), p.getType(),p.getDefaultValue(),p.getTotal(),p.getName()))
						.collect(Collectors.toList());
				

				
		reportResponse.setReportHeader(columnDetails);
	}
}
