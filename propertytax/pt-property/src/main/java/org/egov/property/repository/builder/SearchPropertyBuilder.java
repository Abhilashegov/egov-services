
package org.egov.property.repository.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.models.RequestInfo;
import org.egov.models.User;
import org.egov.models.UserResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author Prasad
 *
 */
@Service
public class SearchPropertyBuilder {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	@Autowired
	RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(SearchPropertyBuilder.class);

	String BASE_SEARCH_QUERY = "select prop.* from egpt_property prop "
			+ "JOIN egpt_property_owner puser on puser.property = prop.id where";

	public Map<String, Object> createSearchPropertyQuery(RequestInfo requestInfo, String tenantId, Boolean active,
			String upicNo, Integer pageSize, Integer pageNumber, String[] sort, String oldUpicNo, String mobileNumber,
			String aadhaarNumber, String houseNoBldgApt, Integer revenueZone, Integer revenueWard, Integer locality,
			String ownerName, Integer demandFrom, Integer demandTo, String propertyId, String applicationNo,
			List<Object> preparedStatementValues) {// TODO remove unused
													// argument [Pranav]

		StringBuffer searchPropertySql = new StringBuffer();

		Map<String, Object> userSearchRequestInfo = new HashMap<String, Object>();

		if (ownerName != null && !ownerName.isEmpty())
			userSearchRequestInfo.put("name", ownerName);

		if (mobileNumber != null && !mobileNumber.isEmpty())

			userSearchRequestInfo.put("mobileNumber", mobileNumber);

		if (aadhaarNumber != null && !aadhaarNumber.isEmpty())
			userSearchRequestInfo.put("aadhaarNumber", aadhaarNumber);

		userSearchRequestInfo.put("tenantId", tenantId);
		userSearchRequestInfo.put("RequestInfo", requestInfo);

		StringBuffer userSearchUrl = new StringBuffer();
		userSearchUrl.append(environment.getProperty("egov.services.egov_user.hostname"));
		userSearchUrl.append(environment.getProperty("egov.services.egov_user.basepath"));
		userSearchUrl.append(environment.getProperty("egov.services.egov_user.searchpath"));

		UserResponseInfo userResponse = null;

		if (ownerName != null || mobileNumber != null || aadhaarNumber != null || tenantId != null) {

			logger.info("SearchpropertyBuilder userSearchUrl :: " + userSearchUrl);
			logger.info("SearchpropertyBuilder userSearchRequestInfo:: " + userSearchRequestInfo);
			userResponse = restTemplate.postForObject(userSearchUrl.toString(), userSearchRequestInfo,
					UserResponseInfo.class);
			logger.info("SearchpropertyBuilder userResponse ::" +userResponse);
		}
		String Ids = "";

		List<User> users = null;

		if (userResponse != null && userResponse.getUser() != null) {
			users = userResponse.getUser();

			int count = 1;

			for (User user : users) {
				if (count < users.size())
					Ids = Ids + user.getId() + ",";
				else
					Ids = Ids + "" + user.getId() + "";

				count++;
			}

		}
		searchPropertySql.append(BASE_SEARCH_QUERY);

		if (houseNoBldgApt != null && !houseNoBldgApt.isEmpty()) {

			searchPropertySql.append("JOIN egpt_address Addr on Addr.property =  prop.id");

		}

		if (tenantId != null && !tenantId.isEmpty()) {
			searchPropertySql.append(" prop.tenantid=?");
			preparedStatementValues.add(tenantId.trim());
		}

		if (active != null) {
			searchPropertySql.append(" AND prop.active=?");
			preparedStatementValues.add(active);
		}

		if (upicNo != null && !upicNo.isEmpty()) {
			searchPropertySql.append(" AND prop.upicnumber=?");
			preparedStatementValues.add(upicNo.trim());
		}

		if (oldUpicNo != null && !oldUpicNo.isEmpty()) {
			searchPropertySql.append(" AND prop.oldUpicNumber=?");
			preparedStatementValues.add(oldUpicNo.trim());
		}

		if (!Ids.isEmpty())
			searchPropertySql.append(" AND puser.owner IN (" + Ids + ")");

		// TODO as of now we don't have the revenue Zone ,revenue
		// Ward,locality,houseNoBldgApt
		// TODO [Ramki] what do you mean by we do not have revenue Zone ,revenue
		// Ward,locality,houseNoBldgApt ?
		// So we are not putting in search

		if (houseNoBldgApt != null && !houseNoBldgApt.isEmpty()) {
			searchPropertySql.append(" AND Addr.addressnumber=?");
			preparedStatementValues.add(houseNoBldgApt.trim());
		}

		if (propertyId != null && !propertyId.isEmpty()) {
			searchPropertySql.append(" AND prop.id =?");
			preparedStatementValues.add(propertyId);
		}

		if (sort != null && sort.length > 0) {
			searchPropertySql.append(" ORDER BY ");

			// Count loop to add the coma ,

			int orderBycount = 1;

			StringBuffer orderByCondition = new StringBuffer();
			for (String order : sort) {
				if (orderBycount < sort.length)
					orderByCondition.append("prop." + order + ",");
				else
					orderByCondition.append("prop." + order);
				orderBycount++;
			}

			if (orderBycount > 1)
				orderByCondition.append(" asc");

			searchPropertySql.append(orderByCondition.toString());
		}

		//
		// Appending the pagination related logic,if the page size and page
		// number is -1
		// then we need to put the default page size and page number
		//

		if (pageNumber == null || pageNumber == 0)
			pageNumber = Integer.valueOf(environment.getProperty("default.page.number").trim());

		if (pageSize == null)
			pageSize = Integer.valueOf(environment.getProperty("default.page.size").trim());

		int offset = 0;
		int limit = pageNumber * pageSize;

		if (pageNumber <= 1)
			offset = (limit - pageSize);
		else
			offset = (limit - pageSize) + 1;

		searchPropertySql.append(" offset ?  limit ?");
		preparedStatementValues.add(offset);
		preparedStatementValues.add(limit);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("Sql", searchPropertySql.toString());
		resultMap.put("users", users);

		return resultMap;

	}

	public static final String BASE_QUERY = "select prop.* from egpt_property prop "
			+ "JOIN egpt_propertydetails prd on prd.property= prop.id";

	public String getPropertyByUpic(String upicNo, String oldUpicNo, String houseNoBldgApt, String propertyId,
			String tenantId, List<Object> preparedStatementValues, Integer pageNumber, Integer pageSize,
			String applicationNo) {

		StringBuffer searchQuery = new StringBuffer();
		searchQuery.append(BASE_QUERY);

		if (houseNoBldgApt != null && !houseNoBldgApt.isEmpty()) {
			searchQuery.append(" JOIN egpt_address Addr on Addr.property =  prop.id WHERE ");
			searchQuery.append("Addr.addressnumber =? AND");
			preparedStatementValues.add(houseNoBldgApt);
		} else {
			searchQuery.append(" WHERE");
		}

		if (applicationNo != null && !applicationNo.isEmpty()) {

			searchQuery.append(" prd.applicationno=? AND");
			preparedStatementValues.add(applicationNo);
		}

		if (upicNo != null && !upicNo.isEmpty()) {
			searchQuery.append(" prop.upicnumber = ? AND ");
			preparedStatementValues.add(upicNo);
		}

		if (oldUpicNo != null && !oldUpicNo.isEmpty()) {
			searchQuery.append(" prop.oldupicnumber=? AND");
			preparedStatementValues.add(oldUpicNo);
		}

		if (propertyId != null && !propertyId.isEmpty()) {
			searchQuery.append(" prop.id=?::bigint AND");
			preparedStatementValues.add(Long.valueOf(propertyId.toString().trim()));
		}

		if (tenantId != null && !tenantId.isEmpty()) {
			searchQuery.append(" prop.tenantId=?");
			preparedStatementValues.add(tenantId);
		}

		searchQuery.append(" ORDER BY upicnumber");

		if (pageNumber == null || pageNumber == 0)
			pageNumber = Integer.valueOf(environment.getProperty("default.page.number").trim());

		if (pageSize == null)
			pageSize = Integer.valueOf(environment.getProperty("default.page.size").trim());

		int offset = 0;
		int limit = pageNumber * pageSize;

		if (pageNumber <= 1)
			offset = (limit - pageSize);
		else
			offset = (limit - pageSize) + 1;

		searchQuery.append(" offset ?  limit ?");
		preparedStatementValues.add(offset);
		preparedStatementValues.add(limit);

		return searchQuery.toString();

	}

	public static String getOwnersByproperty(Long propertyId, List<Object> preparedStatementValues) {

		StringBuffer query = new StringBuffer("select * from egpt_property_owner where property=?");
		preparedStatementValues.add(propertyId);
		return query.toString();
	}
}
