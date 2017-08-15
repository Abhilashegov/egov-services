package org.egov.property.services;

import org.egov.models.*;

public interface PropertyService {

	/**
	 * This method for validating request and generating id,send property object
	 * to kafka
	 * 
	 * @param propertyRequest
	 * @return propertyResponse
	 */
	public PropertyResponse createProperty(PropertyRequest propertyRequest);

	/**
	 * This method for updating property
	 * 
	 * @param propertyRequest
	 * @return propertyResponse
	 */

	public PropertyResponse updateProperty(PropertyRequest propertyRequest);

	/**
	 * This method for search properties based on input parameters
	 * 
	 * @param requestInfo
	 * @param tenantId
	 * @param active
	 * @param upicNo
	 * @param pageSize
	 * @param pageNumber
	 * @param sort
	 * @param oldUpicNo
	 * @param mobileNumber
	 * @param aadhaarNumber
	 * @param houseNoBldgApt
	 * @param revenueZone
	 * @param revenueWard
	 * @param locality
	 * @param ownerName
	 * @param demandFrom
	 * @param demandTo
	 * @return
	 */

	public PropertyResponse searchProperty(RequestInfo requestInfo, String tenantId, Boolean active, String upicNo,
			Integer pageSize, Integer pageNumber, String[] sort, String oldUpicNo, String mobileNumber,
			String aadhaarNumber, String houseNoBldgApt, Integer revenueZone, Integer revenueWard, Integer locality,
			String ownerName, Integer demandFrom, Integer demandTo,String propertyId,String applicationNo) throws Exception;
	
	
	/**
	 * This api for creating title transfer request for property
	 * @param titleTransferRequest
	 * @return titleTransferResponse
	 * @throws Exception
	 */
	
	public TitleTransferResponse createTitleTransfer(TitleTransferRequest titleTransferRequest) throws Exception;
	
	/**
	 * This api for updating title transfer request for property
	 * @param titleTransferRequest
	 * @return titleTransferResponse
	 * @throws Exception
	 */
	public TitleTransferResponse updateTitleTransfer(TitleTransferRequest titleTransferRequest) throws Exception;
	
	/**
	 * Save property history and update property
	 * 
	 * @param titleTransferRequest
	 * @throws Exception
	 */
	public PropertyRequest savePropertyHistoryandUpdateProperty(TitleTransferRequest titleTransferRequest)
			throws Exception;

	
	/**
	 * This Api will generate the special based on the given special notice request
	 * @param specialNoticeRequest
	 * @return {@link SpecialNoticeResponse}
	 * @throws Exception
	 */
	public SpecialNoticeResponse generateSpecialNotice(SpecialNoticeRequest specialNoticeRequest) throws Exception;

	/**
	 * API prepares DCB data for Add/Edit DCB feature
	 * @param requestInfo
	 * @param tenantId
	 * @param upicNumber
	 * @throws Exception
	 */
	public DemandResponse getDemandsForProperty(RequestInfoWrapper requestInfo, String tenantId, String upicNumber) throws Exception;
}
