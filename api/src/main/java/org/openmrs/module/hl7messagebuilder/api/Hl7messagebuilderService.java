/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hl7messagebuilder.api;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hl7messagebuilder.api.model.PatientDemographic;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface Hl7messagebuilderService extends OpenmrsService {
	
	/**
	 * Gets patient demographic data that is going to be used to produce the HL7 file
	 * 
	 * @param locationsBySite
	 * @return
	 * @throws APIException
	 */
	public List<PatientDemographic> getPatientDemographicData(String locationsByUuid) throws APIException;
	
	public List<String> getSites();
	
	public List<String> getLocationsBySite(String site);
	
	public List<String> getLocationsByUuid();
}
