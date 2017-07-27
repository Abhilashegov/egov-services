package org.egov.calculator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author Yosadhara
 *
 */
@Configuration
@ToString
@NoArgsConstructor
@SuppressWarnings("unused")
public class PropertiesManager {

	@Autowired
	Environment environment;

	private String invalidInput;

	private String dbUrl;

	private String driverClassName;

	private String userName;

	private String password;

	private String success;

	private String failed;

	private String propertyHostName;

	private String propertyBasepath;

	private String propertySearch;

	private String rulesPath;

	private String unitType;

	private String financialMonth;

	private String financialDate;

	private String dateFormat;

	private String invalidTaxcalculation;

	private String inputDateFormat;

	private String serverContextpath;

	public String getInvalidInput() {
		return environment.getProperty("invalid.input");
	}

	public String getDbUrl() {
		return environment.getProperty("spring.datasource.url");
	}

	public String getDriverClassName() {
		return environment.getProperty("spring.datasource.driver-class-name");
	}

	public String getUserName() {
		return environment.getProperty("spring.datasource.username");
	}

	public String getPassword() {
		return environment.getProperty("spring.datasource.password");
	}

	public String getSuccess() {
		return environment.getProperty("success");
	}

	public String getFailed() {
		return environment.getProperty("failed");
	}

	public String getPropertyHostName() {
		return environment.getProperty("egov.services.pt_property.hostname");
	}

	public String getPropertyBasepath() {
		return environment.getProperty("egov.services.pt_property.basepath");
	}

	public String getPropertySearch() {
		return environment.getProperty("egov.services.pt_property.search");
	}

	public String getRulesPath() {
		return environment.getProperty("rules.path");
	}

	public String getUnitType() {
		return environment.getProperty("unit.type");
	}

	public String getFinancialMonth() {
		return environment.getProperty("finincial.month");
	}

	public String getFinancialDate() {
		return environment.getProperty("finincial.date");
	}

	public String getDateFormat() {
		return environment.getProperty("date.format");
	}

	public String getInvalidTaxcalculation() {
		return environment.getProperty("invalid.taxcalculation.details");
	}

	public String getInputDateFormat() {
		return environment.getProperty("date.input.format");
	}

	public String getServerContextpath() {
		return environment.getProperty("server.contextPath");
	}
}