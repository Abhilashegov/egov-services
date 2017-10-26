package org.egov.lams.services.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.egov.lams.common.web.contract.Boundary;
import org.egov.lams.common.web.contract.CompensationMode;
import org.egov.lams.common.web.contract.HoldingType;
import org.egov.lams.common.web.contract.LandRegister;
import org.egov.lams.common.web.contract.ModeOfAcquisition;
import org.egov.lams.common.web.contract.PlanningType;
import org.egov.lams.common.web.contract.Purpose;
import org.egov.lams.common.web.contract.RegisterName;
import org.egov.lams.common.web.contract.RoadType;
import org.egov.lams.common.web.contract.SubRegisterName;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class LandRegisterRowMapper implements ResultSetExtractor<LandRegister> {

	@Override
	public LandRegister extractData(ResultSet rs) throws SQLException, DataAccessException {
		while (rs.next()) {

			LandRegister landRegister = new LandRegister();

			ModeOfAcquisition modeOfAcquisition = new ModeOfAcquisition();
			modeOfAcquisition.setCode(rs.getString("acquisitiontype"));

			CompensationMode compensationMode = new CompensationMode();
			compensationMode.setCode(rs.getString("modeofcompensation"));

			Purpose purpose = new Purpose();
			purpose.setCode(rs.getString("purpose"));

			RegisterName registerName = new RegisterName();
			registerName.setCode(rs.getString("registername"));

			RoadType roadType = new RoadType();
			roadType.setCode(rs.getString("roadtype"));

			SubRegisterName subRegisterName = new SubRegisterName();
			subRegisterName.setCode(rs.getString("subregistername"));

			HoldingType typeOfHolding = new HoldingType();
			typeOfHolding.setCode(rs.getString("typeofholding"));

			PlanningType typeOfPlanning = new PlanningType();
			typeOfPlanning.setCode(rs.getString("typeofplanning"));

			Boundary regionalOfficeBoundary = new Boundary();
			regionalOfficeBoundary.setId(rs.getString("regionaloffice"));

			Boundary locationBoundary = new Boundary();
			locationBoundary.setId(rs.getString("location"));

			landRegister.setId(rs.getLong("id"));
			landRegister.setTenantId(rs.getString("tenantid"));
			landRegister.setAcquisitionNo(rs.getString("acquisitionno"));
			landRegister.setAcquisitionType(modeOfAcquisition);
			landRegister.setAreaAsPerRegister(rs.getDouble("areaasperregister"));
			landRegister.setBuildingReference(rs.getString("buildingreference"));
			landRegister.setCodeOfReservation(rs.getString("codeofreservation"));
			landRegister.setComments(rs.getString("comments"));
			landRegister.setCostOfAcquisition(rs.getDouble("costofacquisition"));
			landRegister.setDateOfPayment(rs.getLong("dateofpayment"));
			// landRegister.setDocuments(documents);
			landRegister.setGattNo(rs.getString("gattno"));
			landRegister.setLandArea(rs.getDouble("landarea"));
			landRegister.setLandNumber(rs.getString("landnumber"));
			landRegister.setLandType(rs.getString("landtype"));
			landRegister.setLocation(locationBoundary);
			landRegister.setModeOfAcquisition(modeOfAcquisition);
			landRegister.setModeOfCompensation(compensationMode);
			landRegister.setOldOwnerName(rs.getString("oldownername"));
			landRegister.setPossessionDate(rs.getLong("possessiondate"));
			landRegister.setPurpose(purpose);
			landRegister.setRegionalOffice(regionalOfficeBoundary);
			landRegister.setRegisterName(registerName);
			landRegister.setReservationArea(rs.getDouble("reservationarea"));
			landRegister.setRoadType(roadType);
			landRegister.setStateId(rs.getString("stateid"));
			landRegister.setSubRegisterName(subRegisterName);
			landRegister.setSurveyNo(rs.getString("surveyno"));
			landRegister.setTypeOfHolding(typeOfHolding);
			landRegister.setTypeOfPlanning(typeOfPlanning);
			landRegister.setWidth(rs.getDouble("width"));
			return landRegister;
		}
		return null;
	}
}
