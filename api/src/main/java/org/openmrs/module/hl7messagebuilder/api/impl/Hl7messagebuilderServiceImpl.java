/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hl7messagebuilder.api.impl;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hl7messagebuilder.api.Hl7messagebuilderService;
import org.openmrs.module.hl7messagebuilder.api.db.Hl7messagebuilderDAO;
import org.openmrs.module.hl7messagebuilder.api.model.PatientDemographic;

public class Hl7messagebuilderServiceImpl extends BaseOpenmrsService implements Hl7messagebuilderService {
	
	Hl7messagebuilderDAO dao;
	
	public Hl7messagebuilderDAO getDao() {
		return dao;
	}
	
	public void setDao(Hl7messagebuilderDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public List<PatientDemographic> getPatientDemographicData(String locationsByUuid) throws APIException {
		return dao.getPatientDemographicData(locationsByUuid);
	}
	
	@Override
	public List<String> getSites() {
		return dao.getSites();
	}
	
	@Override
	public List<String> getLocationsBySite(String site) {
		return dao.getLocationsBySite(site);
	}

	@Override
	public List<String> getLocationsByUuid() {
		return dao.getLocationsByUuid(); 
	}
}
