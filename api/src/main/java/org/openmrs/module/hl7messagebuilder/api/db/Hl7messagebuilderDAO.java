package org.openmrs.module.hl7messagebuilder.api.db;

import java.math.BigInteger;
import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.hl7messagebuilder.api.model.PatientDemographic;

public interface Hl7messagebuilderDAO {
	
	/**
	 * Gets patient demographic data that is going to be used to produce the HL7 file
	 * 
	 * @return
	 * @throws APIException
	 */
	public List<PatientDemographic> getPatientDemographicData(Integer maxId) throws APIException;
	
	public BigInteger getPatientDemographicSize();
}
