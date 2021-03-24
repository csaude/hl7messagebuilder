/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.hl7messagebuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.ModuleActivator;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class Hl7messagebuilderActivator implements ModuleActivator {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * @see #started()
	 */
	public void started() {
		log.info("Started Hl7messagebuilder");
	}
	
	/**
	 * @see #shutdown()
	 */
	public void shutdown() {
		log.info("Shutdown Hl7messagebuilder");
	}
	
	@Override
	public void willRefreshContext() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void contextRefreshed() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void willStart() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void willStop() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void stopped() {
		// TODO Auto-generated method stub
		
	}
	
}
