/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.wcms.transaction.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.common.contract.request.RequestInfo;
import org.egov.wcms.transaction.model.Connection;
import org.egov.wcms.transaction.model.DocumentOwner;
import org.egov.wcms.transaction.model.EstimationCharge;
import org.egov.wcms.transaction.model.EstimationNotice;
import org.egov.wcms.transaction.model.Material;
import org.egov.wcms.transaction.model.MeterReading;
import org.egov.wcms.transaction.model.WorkOrderFormat;
import org.egov.wcms.transaction.model.enums.NewConnectionStatus;
import org.egov.wcms.transaction.repository.builder.WaterConnectionQueryBuilder;
import org.egov.wcms.transaction.repository.rowmapper.ConnectionDocumentRowMapper;
import org.egov.wcms.transaction.repository.rowmapper.UpdateWaterConnectionRowMapper;
import org.egov.wcms.transaction.repository.rowmapper.WaterConnectionRowMapper;
import org.egov.wcms.transaction.web.contract.WaterConnectionGetReq;
import org.egov.wcms.transaction.web.contract.WaterConnectionReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class WaterConnectionRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(WaterConnectionRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate; 

    @Autowired
    private WaterConnectionQueryBuilder waterConnectionQueryBuilder;
    
    public WaterConnectionReq persistConnection(final WaterConnectionReq waterConnectionRequest) {

        String insertQuery = "";
        if (waterConnectionRequest.getConnection().getIsLegacy())
            insertQuery = WaterConnectionQueryBuilder.insertLegacyConnectionQuery();
        else if (waterConnectionRequest.getConnection().getParentConnectionId() != 0)
            insertQuery = WaterConnectionQueryBuilder.insertAdditionalConnectionQuery();
        else
            insertQuery = WaterConnectionQueryBuilder.insertConnectionQuery();

        final String query = insertQuery;
        LOGGER.info("Insert Query is : " + insertQuery);
        LOGGER.info("Created By and Updated By : " + waterConnectionRequest.getRequestInfo().getUserInfo().getId());
        
        Long connectionId = 0L;
        try {
            final KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((PreparedStatementCreator) connection -> {
                final String[] returnValColumn = new String[] { "id" };
                final PreparedStatement statement = connection.prepareStatement(query,
                        returnValColumn);
                statement.setString(1, waterConnectionRequest.getConnection().getTenantId());
                statement.setString(2, waterConnectionRequest.getConnection().getConnectionType());
                statement.setString(3, waterConnectionRequest.getConnection().getApplicationType());
                statement.setString(4, waterConnectionRequest.getConnection().getBillingType());
                statement.setString(5, waterConnectionRequest.getConnection().getCategoryId());
                statement.setString(6, waterConnectionRequest.getConnection().getPipesizeId());
                statement.setString(7, waterConnectionRequest.getConnection().getSupplyTypeId());
                statement.setString(8, waterConnectionRequest.getConnection().getSourceTypeId());
                statement.setString(9, waterConnectionRequest.getConnection().getConnectionStatus());
                statement.setDouble(10, waterConnectionRequest.getConnection().getSumpCapacity());
                statement.setInt(11, waterConnectionRequest.getConnection().getNumberOfTaps());
                statement.setInt(12, waterConnectionRequest.getConnection().getNumberOfPersons());
                statement.setString(13, waterConnectionRequest.getConnection().getAcknowledgementNumber());
                statement.setLong(14, (null!=waterConnectionRequest.getRequestInfo().getUserInfo().getId())? waterConnectionRequest.getRequestInfo().getUserInfo().getId() : 1L);
                statement.setLong(15, (null!=waterConnectionRequest.getRequestInfo().getUserInfo().getId())? waterConnectionRequest.getRequestInfo().getUserInfo().getId() : 1L);
                statement.setDate(16, new Date(new java.util.Date().getTime()));
                statement.setDate(17, new Date(new java.util.Date().getTime()));
                statement.setString(18, waterConnectionRequest.getConnection().getPropertyIdentifier());
                statement.setString(19, waterConnectionRequest.getConnection().getProperty().getUsageTypeId());
                statement.setString(20, waterConnectionRequest.getConnection().getProperty().getPropertyTypeId());
   
                statement.setString(21, "AddressTest"); // waterConnectionRequest.getConnection().getProperty().getAddress());
                statement.setDouble(22, waterConnectionRequest.getConnection().getDonationCharge());

                statement.setString(23, waterConnectionRequest.getConnection().getAssetIdentifier());
                statement.setString(24, waterConnectionRequest.getConnection().getWaterTreatmentId());
                statement.setBoolean(25, waterConnectionRequest.getConnection().getIsLegacy());
                if (!waterConnectionRequest.getConnection().getIsLegacy() && waterConnectionRequest.getConnection().getId() == 0){
                    waterConnectionRequest.getConnection().setStatus(NewConnectionStatus.CREATED.name());
                    statement.setString(26, NewConnectionStatus.CREATED.name());
                }
                else if (waterConnectionRequest.getConnection().getIsLegacy()){
                    waterConnectionRequest.getConnection().setStatus(NewConnectionStatus.SANCTIONED.name());
                    statement.setString(26, NewConnectionStatus.SANCTIONED.name());
                }
                else{
                    statement.setString(26, NewConnectionStatus.VERIFIED.name());
                    waterConnectionRequest.getConnection().setStatus(NewConnectionStatus.VERIFIED.name());
                }
                statement.setDouble(27, waterConnectionRequest.getConnection().getNumberOfFamily());
                statement.setString(28, waterConnectionRequest.getConnection().getSubUsageTypeId());
                statement.setString(29,waterConnectionRequest.getConnection().getPlumberName());
                System.out.println(waterConnectionRequest.getConnection().getBillSequenceNumber());
                statement.setLong(30, waterConnectionRequest.getConnection().getBillSequenceNumber()!=null?
                        waterConnectionRequest.getConnection().getBillSequenceNumber():0l);
                statement.setString(31, waterConnectionRequest.getConnection().getMeterOwner()!=null?
                        waterConnectionRequest.getConnection().getMeterOwner():"");
                statement.setString(32, waterConnectionRequest.getConnection().getMeterModel()!=null?
                        waterConnectionRequest.getConnection().getMeterModel():"");
                statement.setBoolean(33, Boolean.FALSE);

                if (waterConnectionRequest.getConnection().getIsLegacy()
                        ) {
                    statement.setString(34, waterConnectionRequest.getConnection().getLegacyConsumerNumber());
                    statement.setString(35, waterConnectionRequest.getConnection().getConsumerNumber());
                  statement.setLong(36, waterConnectionRequest.getConnection().getExecutionDate());
                   statement.setInt(37, waterConnectionRequest.getConnection().getNoOfFlats());

                }

                if (waterConnectionRequest.getConnection().getParentConnectionId() != 0)
                    statement.setLong(38, waterConnectionRequest.getConnection().getParentConnectionId());
                
                
                // Please verify if there's proper validation on all these fields to avoid NPE.
                
                return statement;
            }, keyHolder);

            connectionId = keyHolder.getKey().longValue();
            waterConnectionRequest.getConnection().setId(connectionId);
        } catch (final Exception e) {
            LOGGER.error("Inserting Connection Object failed!", e);
        }

        if (connectionId > 0 ) {
            final List<Object[]> values = new ArrayList<>();
            for (final DocumentOwner document : waterConnectionRequest.getConnection().getDocuments()) {
                document.setDocumentId(Integer.parseInt(document.getDocument()));
                final Object[] obj = { document.getDocumentId(),
                        document.getName(),
                        document.getFileStoreId(),
                        waterConnectionRequest.getConnection().getId(),
                        waterConnectionRequest.getConnection().getTenantId() };

                values.add(obj);
            }
            final String insertDocsQuery = WaterConnectionQueryBuilder.insertDocumentQuery();
            try {
                jdbcTemplate.batchUpdate(insertDocsQuery, values);
            } catch (final Exception e) {
                LOGGER.error("Inserting documents failed!", e);
            }
        }  if (connectionId > 0 && waterConnectionRequest.getConnection().getBillingType() != null &&
                waterConnectionRequest.getConnection().getBillingType().equals("METERED") &&
                !waterConnectionRequest.getConnection().getMeter().isEmpty()) {

            Long meterId=null;
                final String insertestQuery = WaterConnectionQueryBuilder.insertMeterQuery();
                try {
            
                    final KeyHolder keyHolder = new GeneratedKeyHolder();
                    jdbcTemplate.update((PreparedStatementCreator) connectiontemp -> {
                        final String[] returnValColumn = new String[] { "id" };
                        final PreparedStatement statement = connectiontemp.prepareStatement(insertestQuery,
                                returnValColumn);
                        statement.setLong(1, waterConnectionRequest.getConnection().getId());
                        statement.setString(2, waterConnectionRequest.getConnection().getMeter().get(0).getMeterMake());
                        statement.setString(3, waterConnectionRequest.getConnection().getMeter().get(0).getInitialMeterReading());
                        
                        statement.setString(4, waterConnectionRequest.getConnection().getMeter().get(0).getMeterSlNo());
                        statement.setString(5,  waterConnectionRequest.getConnection().getMeter().get(0).getMeterCost());  
                        statement.setString(6, waterConnectionRequest.getConnection().getTenantId());
                        statement.setLong(7, waterConnectionRequest.getRequestInfo().getUserInfo().getId());
                        statement.setDate(8, new Date(new java.util.Date().getTime()));

                        statement.setLong(9, waterConnectionRequest.getRequestInfo().getUserInfo().getId());
                        statement.setDate(10, new Date(new java.util.Date().getTime()));
                        
                        return statement;
                    }, keyHolder);

                    meterId = keyHolder.getKey().longValue();
                    
                    
                } catch (final Exception e) {LOGGER.error("Inserting meter failed!", e);
                    
                }

            if (!waterConnectionRequest.getConnection().getMeter().get(0).getMeterReadings().isEmpty()) {
                final String insertMeterReadingQuery = WaterConnectionQueryBuilder.insertMeterReadingQuery();
                final List<Object[]> values = new ArrayList<>();
                for (final MeterReading meterReading : waterConnectionRequest.getConnection().getMeter().get(0)
                        .getMeterReadings()) {

                    final Object[] obj = { meterId,
                            meterReading.getReading(),
                            meterReading.getReadingDate(),waterConnectionRequest.getConnection().getTenantId(),
                            waterConnectionRequest.getRequestInfo().getUserInfo().getId(), new Date(new java.util.Date().getTime()),
                            waterConnectionRequest.getRequestInfo().getUserInfo().getId(), new Date(new java.util.Date().getTime()) };

                    values.add(obj);
                }
                try {
                    jdbcTemplate.batchUpdate(insertMeterReadingQuery, values);
                } catch (final Exception e) {
                    LOGGER.error("Inserting documents failed!", e);
                }
            }
        }

        return waterConnectionRequest;

    }
    
    public long insertConnectionAddress(WaterConnectionReq waterConnectionReq) { 
    	String persistConnectionAddressQuery = WaterConnectionQueryBuilder.getWaterConnectionAddressQueryForInsert();
    	LOGGER.info("Persist Connection Address Query : " + persistConnectionAddressQuery);
    	Connection conn = waterConnectionReq.getConnection();
        KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(java.sql.Connection connection) throws SQLException {
					String[] returnValColumn = new String[] { "id" };
					PreparedStatement statement = connection.prepareStatement(persistConnectionAddressQuery,
							returnValColumn);
					statement.setString(1, conn.getTenantId());
					statement.setDouble(2, (null!=conn.getAddress().getLatitude() && conn.getAddress().getLatitude()>0)? conn.getAddress().getLatitude() : 0); 
					statement.setDouble(3, (null!=conn.getAddress().getLongitude() && conn.getAddress().getLongitude()>0)? conn.getAddress().getLongitude() : 0);
					statement.setString(4, (null!=conn.getAddress().getAddressId() && !conn.getAddress().getAddressId().isEmpty())? conn.getAddress().getAddressId() : "" );
					statement.setString(5, (null!=conn.getAddress().getAddressNumber() && !conn.getAddress().getAddressNumber().isEmpty())? conn.getAddress().getAddressNumber() : "" );
					statement.setString(6, (null!=conn.getAddress().getAddressLine1() && !conn.getAddress().getAddressLine1().isEmpty())? conn.getAddress().getAddressLine1() : "" );
					statement.setString(7, (null!=conn.getAddress().getAddressLine2() && !conn.getAddress().getAddressLine2().isEmpty())? conn.getAddress().getAddressLine2() : "" );
					statement.setString(8, (null!=conn.getAddress().getLandMark() && !conn.getAddress().getLandMark().isEmpty())? conn.getAddress().getLandMark() : "" );
					statement.setString(9, (null!=conn.getAddress().getDoorNo() && !conn.getAddress().getDoorNo().isEmpty())? conn.getAddress().getDoorNo() : "" );
					statement.setString(10, (null!=conn.getAddress().getCity() && !conn.getAddress().getCity().isEmpty())? conn.getAddress().getCity() : "" );
					statement.setString(11, (null!=conn.getAddress().getPinCode() && !conn.getAddress().getPinCode().isEmpty())? conn.getAddress().getPinCode() : "" );
					statement.setString(12, (null!=conn.getAddress().getDetail() && !conn.getAddress().getDetail().isEmpty())? conn.getAddress().getDetail() : "" );
					statement.setString(13, (null!=conn.getAddress().getRoute() && !conn.getAddress().getRoute().isEmpty())? conn.getAddress().getRoute() : "" );
					statement.setString(14, (null!=conn.getAddress().getStreet() && !conn.getAddress().getStreet().isEmpty())? conn.getAddress().getStreet() : "" );
					statement.setString(15, (null!=conn.getAddress().getArea() && !conn.getAddress().getArea().isEmpty())? conn.getAddress().getArea() : "" );
					statement.setString(16, (null!=conn.getAddress().getRoadName() && !conn.getAddress().getRoadName().isEmpty())? conn.getAddress().getRoadName() : "" );
					statement.setLong(17, waterConnectionReq.getRequestInfo().getUserInfo().getId());
					statement.setDate(18, new Date(new java.util.Date().getTime()));
					return statement;
				}
			}, keyHolder);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return keyHolder.getKey().longValue();
        
    }
    
    public long insertConnectionLocation(WaterConnectionReq waterConnectionReq) { 
    	String persistConnectionLocationQuery = WaterConnectionQueryBuilder.getWaterConnectionLocationQueryForInsert();
    	LOGGER.info("Persist Connection Location Query : " + persistConnectionLocationQuery);
    	Connection conn = waterConnectionReq.getConnection();
        KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(java.sql.Connection connection) throws SQLException {
					String[] returnValColumn = new String[] { "id" };
					PreparedStatement statement = connection.prepareStatement(persistConnectionLocationQuery,
							returnValColumn);
					statement.setLong(1, conn.getConnectionLocation().getRevenueBoundary().getId());
					statement.setLong(2, conn.getConnectionLocation().getLocationBoundary().getId()); 
					statement.setLong(3, conn.getConnectionLocation().getAdminBoundary().getId());
					statement.setLong(4, waterConnectionReq.getRequestInfo().getUserInfo().getId());
					statement.setDate(5, new Date(new java.util.Date().getTime()));
					return statement;
				}
			}, keyHolder);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return keyHolder.getKey().longValue();
    }
    
    public void updateConnectionAfterWorkFlowQuery(final String consumerCode)
    {
        String insertquery=waterConnectionQueryBuilder.updateConnectionAfterWorkFlowQuery();
        Object[] obj = new Object[] { 
                new Date(new java.util.Date().getTime()), NewConnectionStatus.ESTIMATIONAMOUNTCOLLECTED,
               consumerCode };
        jdbcTemplate.update(insertquery, obj);
        
    }
    
    public void updateValuesForNoPropertyConnections(WaterConnectionReq waterConnectionReq, long addressId, long locationId) {
        String updateQuery=waterConnectionQueryBuilder.updateValuesForNoPropertyConnections();
        Object[] obj = new Object[] { 
                waterConnectionReq.getConnection().getConnectionOwner().getId(), addressId, locationId, waterConnectionReq.getConnection().getAcknowledgementNumber()};
        jdbcTemplate.update(updateQuery, obj);
    }

    public WaterConnectionReq updateConnectionWorkflow(final WaterConnectionReq waterConnectionReq,Connection connectiondemand)
    {
        String insertQuery = "";
         Object[] obj =null;
        if(waterConnectionReq!=null){
            Connection connection=waterConnectionReq.getConnection();
        insertQuery = WaterConnectionQueryBuilder.updateConnectionQuery();
       obj = new Object[] { connection.getConnectionType(), connection.getApplicationType(),
                connection.getBillingType(), connection.getCategoryId(),
                connection.getPipesizeId(), connection.getSourceTypeId(), connection.getConnectionStatus(),
                connection.getSumpCapacity(), connection.getNumberOfTaps(),
                connection.getNumberOfPersons(), Long.valueOf(waterConnectionReq.getRequestInfo().getUserInfo().getId()),
                new Date(new java.util.Date().getTime()), connection.getStateId(),connection.getNumberOfFamily(),
                connection.getAcknowledgementNumber() };
       
        }
        else{
            if(connectiondemand.getDemandid() !=null)
            {
                final String insertDemandConnectionQuery = WaterConnectionQueryBuilder.insertDemandConnection();

                final Object[] objValue = new Object[] { connectiondemand.getId(),
                        connectiondemand.getDemandid(),connectiondemand.getTenantId(), Long.valueOf(waterConnectionReq.getRequestInfo().getUserInfo().getId()),
                        new Date(new java.util.Date().getTime()),
                        Long.valueOf(waterConnectionReq.getRequestInfo().getUserInfo().getId()),
                        new Date(new java.util.Date().getTime())};
                jdbcTemplate.update(insertDemandConnectionQuery, objValue);
            }
           
        }
       
        jdbcTemplate.update(insertQuery, obj);
        
        return waterConnectionReq;
    }
    public void updateConnectionOnChangeOfDemand(final String demandId ,Connection waterConn,   RequestInfo requestInfo)
    {
        if(demandId !=null)
        {
            final String insertDemandConnectionQuery = WaterConnectionQueryBuilder.insertDemandConnection();

            final Object[] objValue = new Object[] { waterConn.getId(),
                    demandId,waterConn.getTenantId(), Long.valueOf(  requestInfo.getUserInfo().getId()),
                    new Date(new java.util.Date().getTime()),
                    Long.valueOf(requestInfo.getUserInfo().getId()),
                    new Date(new java.util.Date().getTime())};
            jdbcTemplate.update(insertDemandConnectionQuery, objValue);
        }
    }
    public WaterConnectionReq updateWaterConnection(final WaterConnectionReq waterConnectionReq) {
        String insertQuery = "";
        final Connection connection = waterConnectionReq.getConnection();
        if (waterConnectionReq.getConnection().getId() != 0)
            insertQuery = WaterConnectionQueryBuilder.updateConnectionQuery();
        long estmId = 0;
        if(waterConnectionReq.getConnection().getEstimationCharge()!=null && !waterConnectionReq.getConnection().getEstimationCharge().isEmpty())
        {
        for(EstimationCharge estmaCharge:waterConnectionReq.getConnection().getEstimationCharge())
        {
            final String insertestQuery = WaterConnectionQueryBuilder.insertEstimationCharge();
            try {
        
                final KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update((PreparedStatementCreator) connectiontemp -> {
                    final String[] returnValColumn = new String[] { "id" };
                    final PreparedStatement statement = connectiontemp.prepareStatement(insertestQuery,
                            returnValColumn);
                    statement.setLong(1, connection.getId());
                    statement.setString(2, estmaCharge.getExistingDistributionPipeline());
                    statement.setDouble(3, estmaCharge.getPipelineToHomeDistance());
                    
                    statement.setDouble(4,estmaCharge.getEstimationCharges());
                    statement.setDouble(5, estmaCharge.getSupervisionCharges());  
                    statement.setDouble(6, estmaCharge.getMaterialCharges());  
                    statement.setString(7, connection.getTenantId());
                    statement.setLong(8, waterConnectionReq.getRequestInfo().getUserInfo().getId());
                    statement.setLong(9, waterConnectionReq.getRequestInfo().getUserInfo().getId());
                    statement.setDate(10, new Date(new java.util.Date().getTime()));
                    statement.setDate(11, new Date(new java.util.Date().getTime()));
                    
                    return statement;
                }, keyHolder);

                estmId = keyHolder.getKey().longValue();
                
                
            } catch (final Exception e) {
                LOGGER.error("Inserting estimation Charge failed!", e);
            }

        
            final List<Object[]> values = new ArrayList<>();
            final String insertMaterialQuery = WaterConnectionQueryBuilder.insertMaterial();
				if (null != estmaCharge.getMaterials()) {
					for (Material matObj : estmaCharge.getMaterials()) {
						final Object[] objct = new Object[] { estmId, matObj.getName(), matObj.getQuantity(),
								matObj.getSize(), matObj.getAmountDetails(),
								waterConnectionReq.getConnection().getTenantId(),
								waterConnectionReq.getRequestInfo().getUserInfo().getId(),
								new Date(new java.util.Date().getTime()),
								waterConnectionReq.getRequestInfo().getUserInfo().getId(),
								new Date(new java.util.Date().getTime()) };
						values.add(objct);
						try {
							jdbcTemplate.batchUpdate(insertMaterialQuery, values);
						} catch (final Exception e) {
							LOGGER.error("Inserting material failed!", e);
						}
					}
				}
        }
        }
        
        if(waterConnectionReq.getConnection().getDemandid() !=null)
        {
            final String insertDemandConnectionQuery = WaterConnectionQueryBuilder.insertDemandConnection();

            final Object[] objValue = new Object[] { waterConnectionReq.getConnection().getId(),
                    waterConnectionReq.getConnection().getDemandid(),waterConnectionReq.getConnection().getTenantId(), Long.valueOf(waterConnectionReq.getRequestInfo().getUserInfo().getId()),
                    new Date(new java.util.Date().getTime()),
                    Long.valueOf(waterConnectionReq.getRequestInfo().getUserInfo().getId()),
                    new Date(new java.util.Date().getTime())};
            jdbcTemplate.update(insertDemandConnectionQuery, objValue);
        }
        final Object[] obj = new Object[] { connection.getConnectionType(), connection.getApplicationType(),
                connection.getCategoryType(), connection.getBillingType(),
                connection.getHscPipeSizeType(), connection.getSourceType(), connection.getConnectionStatus(),
                connection.getSumpCapacity(), connection.getNumberOfTaps(),
                connection.getNumberOfPersons(), Long.valueOf(waterConnectionReq.getRequestInfo().getUserInfo().getId()),
                new Date(new java.util.Date().getTime()), connection.getStateId(),connection.getNumberOfFamily(),
                 connection.getStatus(),connection.getEstimationNumber(),connection.getWorkOrderNumber(),connection.getConsumerNumber(),
                 connection.getAcknowledgementNumber() };
        jdbcTemplate.update(insertQuery, obj);
        
        return waterConnectionReq;
    }

    public Connection findByApplicationNmber(final String acknowledgeNumber) {

        final Connection connection = jdbcTemplate.queryForObject(
                WaterConnectionQueryBuilder.getWaterConnectionByacknowledgenumber(), new UpdateWaterConnectionRowMapper(),
                acknowledgeNumber);
        return connection;
    }
    public Connection getWaterConnectionByConsumerNumber(final String acknowledgeNumber) {

        final Connection connection = jdbcTemplate.queryForObject(
                WaterConnectionQueryBuilder.getWaterConnectionByConsumerNumber(), new UpdateWaterConnectionRowMapper(),
                acknowledgeNumber);
        return connection;
    }


	public List<Connection> getConnectionDetails(final WaterConnectionGetReq waterConnectionGetReq) {
		final List<Object> preparedStatementValues = new ArrayList<>();
		final String fetchQuery = waterConnectionQueryBuilder.getQuery(waterConnectionGetReq, preparedStatementValues);
		LOGGER.info("Get Connection Details Query : " + fetchQuery);
		final List<Connection> connectionList = jdbcTemplate.query(fetchQuery, preparedStatementValues.toArray(),
				new WaterConnectionRowMapper().new WaterConnectionPropertyRowMapper());
		LOGGER.info(connectionList.size() + " Connection Objects fetched from DB");

		final List<Object> secondPreparedStatementValues = new ArrayList<>();
		final String secondFetchQuery = waterConnectionQueryBuilder.getSecondQuery(waterConnectionGetReq,
				secondPreparedStatementValues);
		LOGGER.info("Get Connection Details Query for Without Property Cases : " + secondFetchQuery);
		try {
			final List<Connection> secondConnectionList = jdbcTemplate.query(secondFetchQuery,
					secondPreparedStatementValues.toArray(),
					new WaterConnectionRowMapper().new WaterConnectionWithoutPropertyRowMapper());
			LOGGER.info(secondConnectionList.size() + " Connection Objects fetched from DB");
			if (secondConnectionList.size() > 0) {
				connectionList.addAll(secondConnectionList);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception encountered while fetching the Connection list without Property : " + ex);
		}
		return connectionList;
	}
    
    public boolean persistEstimationNoticeLog(EstimationNotice estimationNotice, long connectionId, String tenantId) { 
    	String persistsEstimationNoticeQuery = WaterConnectionQueryBuilder.persistEstimationNoticeQuery();
        int insertStatus = jdbcTemplate.update(persistsEstimationNoticeQuery, getObjectForInsertEstimationNotice(estimationNotice, connectionId, tenantId));
        if(insertStatus > 0) { 
        	return true;
        }
        return false;
    }
    
    public boolean persistWorkOrderLog(WorkOrderFormat workOrder) { 
    	String persistsWorkOrderLogQuery = WaterConnectionQueryBuilder.persistWorkOrderQuery();
    	LOGGER.info("Persist Work Order Query : " + persistsWorkOrderLogQuery);
        if(namedParameterJdbcTemplate.update(persistsWorkOrderLogQuery, getObjectForInsertWorkOrder(workOrder)) > 0) { 
        	return true;
        }
        return false;
    }
    
	public Map<String, Object> getObjectForInsertEstimationNotice(EstimationNotice estimationNotice, long connectionId,
			String tenantId) {
		Long createdBy = 1L;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("waterConnectionId", connectionId);
		parameters.put("tenantId", tenantId);
		parameters.put("dateOfLetter", estimationNotice.getDateOfLetter());
		parameters.put("letterNumber", estimationNotice.getLetterNumber());
		parameters.put("letterTo", estimationNotice.getLetterTo());
		parameters.put("letterIntimationSubject", estimationNotice.getLetterIntimationSubject());
		parameters.put("applicationNumber", estimationNotice.getApplicationNumber());
		parameters.put("applicationDate", estimationNotice.getApplicationDate());
		parameters.put("applicantName", estimationNotice.getApplicantName());
		parameters.put("serviceName", "New water tap connection");
		parameters.put("waterNumber", estimationNotice.getApplicationNumber());
		parameters.put("slaDays", estimationNotice.getSlaDays());
		parameters.put("chargeDescription1", estimationNotice.getChargeDescription().get(0));
		parameters.put("chargeDescription2", estimationNotice.getChargeDescription().get(1));
		parameters.put("createdBy", createdBy);
		parameters.put("createdDate", new Date(new java.util.Date().getTime()).getTime());
		return parameters;
	}
    
	public Map<String, Object> getObjectForInsertWorkOrder(WorkOrderFormat workOrder) {
		Long createdBy = 1L;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("waterConnectionId", workOrder.getConnectionId());
		parameters.put("tenantId", workOrder.getTenantId());
		parameters.put("workOrderNumber", workOrder.getWorkOrderNumber());
		parameters.put("workOrderDate", workOrder.getWorkOrderDate());
		parameters.put("waterTapOwnerName", workOrder.getWaterTapOwnerName());
		parameters.put("ackNumber", workOrder.getAckNumber());
		parameters.put("ackNumberDate", workOrder.getAckNumberDate());
		parameters.put("hscNumber", workOrder.getHscNumber());
		parameters.put("hscNumberDate", workOrder.getHscNumberDate());
		parameters.put("serviceName", "New water tap connection");
		// TODO 
		parameters.put("plumberName", "To be added");
		//
		parameters.put("createdBy", createdBy);
		parameters.put("createdDate", new Date(new java.util.Date().getTime()).getTime());
		return parameters;
	}
	
	public Map<String, Object> getObjectForConnectionAddress(WaterConnectionReq waterConnectionRequest) {
		Connection conn = waterConnectionRequest.getConnection();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tenantid", conn.getId());
		parameters.put("latitude", conn.getAddress().getLatitude());
		parameters.put("longitude", conn.getAddress().getLongitude());
		parameters.put("addressId", conn.getAddress().getAddressId());
		parameters.put("addressNumber", conn.getAddress().getAddressNumber());
		parameters.put("addressLine1", conn.getAddress().getAddressLine1());
		parameters.put("addressLine2", conn.getAddress().getAddressLine2());
		parameters.put("landmark", conn.getAddress().getLandMark());
		parameters.put("doorno", conn.getAddress().getDoorNo());
		parameters.put("city", conn.getAddress().getCity());
		parameters.put("pincode", conn.getAddress().getPinCode());
		parameters.put("detail", conn.getAddress().getDetail());
		parameters.put("route", conn.getAddress().getRoute());
		parameters.put("street", conn.getAddress().getStreet());
		parameters.put("area", conn.getAddress().getArea());
		parameters.put("roadname", conn.getAddress().getRoadName());
		parameters.put("createdby", waterConnectionRequest.getRequestInfo().getUserInfo().getId());
		parameters.put("createdtime", new Date(new java.util.Date().getTime()).getTime());
		return parameters;
	}
    
    public List<DocumentOwner> getDocumentForConnection(Connection connection) { 
    	final List<Object> preparedStatementValues = new ArrayList<>();
        final String fetchQuery = WaterConnectionQueryBuilder.getDocumentForConnection();
        LOGGER.info("Get Document for Connection Query : " + fetchQuery);
        preparedStatementValues.add(connection.getId()); 
        preparedStatementValues.add(connection.getTenantId());
        final List<DocumentOwner> documentList = jdbcTemplate.query(fetchQuery, preparedStatementValues.toArray(),
                new ConnectionDocumentRowMapper());
        return documentList;
    }
}
