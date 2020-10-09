package org.openmrs.module.hl7messagebuilder.generator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.module.hl7messagebuilder.api.model.PatientDemographic;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v25.datatype.XAD;
import ca.uhn.hl7v2.model.v25.datatype.XPN;
import ca.uhn.hl7v2.model.v25.message.ADT_A24;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.model.v25.segment.PV1;

/**
 * @author machabane
 */
public class OurAdtA04MessageBuilder {
	
	private ADT_A24 _adtMessage;
	
	public List<ADT_A24> Build(List<PatientDemographic> demographicss) throws HL7Exception, IOException {
		
		String currentDateTimeString = getCurrentTimeStamp();
		
		List<PatientDemographic> demographics = new ArrayList<PatientDemographic>();
		
		PatientDemographic demographic1 = new PatientDemographic("123", "M", "19830101", "Macaco", "", "Grande", "Matola",
		        "Zambezia", "Mozambique", "Quelimane", "823021365", "823021368", "S", "20120509");
		PatientDemographic demographic2 = new PatientDemographic("456", "F", "19830102", "Cao", "", "Medio", "Malhampsene",
		        "Zambezia", "Mozambique", "Quelimane", "823021366", "823021369", "M", "20120310");
		PatientDemographic demographic3 = new PatientDemographic("789", "M", "19830103", "Gato", "", "Pequeno", "Boane",
		        "Zambezia", "Mozambique", "Quelimane", "823021367", "823021370", "S", "20120711");
		
		demographics.add(demographic1);
		demographics.add(demographic2);
		demographics.add(demographic3);
		
		List<ADT_A24> adt_A04s = new ArrayList<ADT_A24>();
		
		for (PatientDemographic demographic : demographics) {
			_adtMessage = new ADT_A24();
			_adtMessage.initQuickstart("ADT", "A24", "P");
			createMshSegment(currentDateTimeString, demographic);
			createPidSegment(demographic);
			//createNk1Segment(demographic);
			createPv1Segment(demographic);
			adt_A04s.add(_adtMessage);
		}
		
		return adt_A04s;
	}
	
	private String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	private void createMshSegment(String currentDateTimeString, PatientDemographic demographic) throws DataTypeException {
		MSH mshSegment = _adtMessage.getMSH();
		mshSegment.getFieldSeparator().setValue("|");
		mshSegment.getEncodingCharacters().setValue("^~\\&");
		mshSegment.getSendingApplication().getNamespaceID().setValue("XYZSYS");
		mshSegment.getSendingFacility().getNamespaceID().setValue("XYZ Centro de Saude");
		mshSegment.getReceivingApplication().getNamespaceID().setValue("DISA*LAB");
		mshSegment.getReceivingFacility().getNamespaceID().setValue("***");
		mshSegment.getDateTimeOfMessage().getTime().setValue(currentDateTimeString);
		mshSegment.getMessageControlID().setValue(getSequenceNumber());
		mshSegment.getVersionID().getVersionID().setValue("2.5.1");
	}
	
	private void createPidSegment(PatientDemographic demographic) throws DataTypeException {
		PID pid = _adtMessage.getPID();
		XPN patientName = pid.getPatientName(0);
		patientName.getFamilyName().getFn1_Surname().setValue(demographic.getFamilyName());
		patientName.getGivenName().setValue(demographic.getGivenName());
		pid.getDateTimeOfBirth().getTime().setValue(demographic.getBirthDate());
		pid.getAdministrativeSex().setValue(demographic.getGender());
		pid.getPatientID().getIDNumber().setValue(demographic.getPid());
		pid.getMaritalStatus().getText().setValue(demographic.getMaritalStatus());
		XAD patientAddress = pid.getPatientAddress(0);
		patientAddress.getStreetAddress().getStreetName().setValue(demographic.getAddress());
		patientAddress.getCity().setValue(demographic.getCountryDistrict());
		patientAddress.getStateOrProvince().setValue(demographic.getStateProvince());
		patientAddress.getCountry().setValue(demographic.getCountry());
	}
	
	/*private void createNk1Segment(PatientDemographic demographic) throws DataTypeException {
		NK1 nk1 = _adtMessage.getNK1();
		nk1.getSetIDNextOfKin().setValue("1");
		nk1.getNKName(0).getFamilyName().setValue("Machabane");
		nk1.getNKName(0).getGivenName().setValue("Helio");
		nk1.getNk13_Relationship().getCe1_Identifier().setValue("EMC");
	}*/
	
	private void createPv1Segment(PatientDemographic demographic) throws DataTypeException {
		PV1 pv1 = _adtMessage.getPV1();
		pv1.getSetIDPV1().setValue("1");
		pv1.getPatientClass().setValue("O");
		pv1.getAssignedPatientLocation().getPl9_LocationDescription().setValue("Centro de Saude de Chabeco");
		pv1.getAdmissionType().setValue("R");
		pv1.getAdmitDateTime().getTime().setValue("20200728");
		
	}
	
	private String getSequenceNumber() {
		String facilityNumberPrefix = "1234"; // some arbitrary prefix for the facility
		return facilityNumberPrefix.concat(getCurrentTimeStamp());
	}
}
