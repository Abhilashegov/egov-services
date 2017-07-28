package org.egov.propertyUser.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.egov.models.RequestInfo;
import org.egov.models.User;
import org.egov.models.UserResponseInfo;
import org.egov.propertyUser.config.PropertiesManager;
import org.egov.propertyUser.model.UserRequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserUtil {

	private static final Logger logger = LoggerFactory.getLogger(UserUtil.class);

	@Autowired
	PropertiesManager propertiesManager;

	@Autowired
	RestTemplate restTemplate;

	public User getUserId(User user, RequestInfo requestInfo) throws Exception {
		StringBuffer createUrl = new StringBuffer();
		createUrl.append(propertiesManager.getUserHostName());
		createUrl.append(propertiesManager.getUserBasepath());
		createUrl.append(propertiesManager.getUserCreatepath());

		StringBuffer searchUrl = new StringBuffer();
		searchUrl.append(propertiesManager.getUserHostName());
		searchUrl.append(propertiesManager.getUserBasepath());
		searchUrl.append(propertiesManager.getUserSearchpath());

		  // search user
        UserResponseInfo userResponse = null;
        logger.info("UserUtil searchUrl ---->> " + searchUrl.toString() + " \n userSearchRequestInfo ---->> "
                + userSearchRequestInfo);
        userResponse = restTemplate.postForObject(searchUrl.toString(), userSearchRequestInfo, UserResponseInfo.class);
        logger.info("UserUtil userResponse ---->> " + userResponse);

        if (userResponse == null || userResponse.getUser().size() == 0) {
            userSearchRequestInfo.put("name", user.getName());
            userSearchRequestInfo.put("mobileNumber", user.getMobileNumber());

            if (user.getAadhaarNumber() != null && !user.getAadhaarNumber().isEmpty()) {
                userSearchRequestInfo.put("aadharNumber", user.getAadhaarNumber());
            }

            if (user.getEmailId() != null && !user.getEmailId().isEmpty()) {
                userSearchRequestInfo.put("emailId", user.getEmailId());
            }

            // search user
            logger.info("UserUtil searchUrl ---->> " + searchUrl.toString() + " \n userSearchRequestInfo ---->> "
                    + userSearchRequestInfo);
            userResponse = restTemplate.postForObject(searchUrl.toString(), userSearchRequestInfo,
                    UserResponseInfo.class);
            logger.info("UserUtil userResponse ---->> " + userResponse);
            if (userResponse == null || userResponse.getUser().size() == 0) {
                UserRequestInfo userRequestInfo = new UserRequestInfo();
                userRequestInfo.setRequestInfo(requestInfo);
                user.setPassword(environment.getProperty("default.password"));
                userRequestInfo.setUser(user);
                logger.info("UserUtil createUrl ---->> " + createUrl.toString() + " \n userRequestInfo ---->> "
                        + userRequestInfo);
                UserResponseInfo userCreateResponse = restTemplate.postForObject(createUrl.toString(), userRequestInfo,
                        UserResponseInfo.class);
                logger.info("UserUtil userCreateResponse ---->> " + userCreateResponse);
                user.setId(userCreateResponse.getUser().get(0).getId());
            }

        }

        if (userResponse.getUser().size() > 1) {

            List<User> userFromReponse = userResponse.getUser();

            List<User> result = userFromReponse.stream()
                    .filter(value -> value.getGender().equalsIgnoreCase(user.getGender())
                            && value.getAltContactNumber().equalsIgnoreCase(user.getAltContactNumber())
                            && value.getPan().equalsIgnoreCase(user.getPan())
                            && value.getPermanentAddress().equalsIgnoreCase(user.getPermanentAddress())
                            && value.getPermanentCity().equalsIgnoreCase(user.getPermanentCity())
                            && value.getPermanentPincode().equalsIgnoreCase(user.getPermanentPincode())
                            && value.getCorrespondenceAddress().equalsIgnoreCase(user.getCorrespondenceAddress())
                            && value.getCorrespondenceCity().equalsIgnoreCase(user.getCorrespondenceCity())
                            && value.getCorrespondencePincode().equalsIgnoreCase(user.getCorrespondencePincode())
                            && value.getLocale().equalsIgnoreCase(user.getLocale())
                            && value.getFatherOrHusbandName().equalsIgnoreCase(user.getFatherOrHusbandName())
                            && value.getBloodGroup().equalsIgnoreCase(user.getBloodGroup())
                            && value.getIdentificationMark().equalsIgnoreCase(user.getIdentificationMark()))
                    .collect(Collectors.toList());

            user.setId(result.get(0).getId());
        } else {
            user.setId(userResponse.getUser().get(0).getId());
        }

        return user;

    }
}
