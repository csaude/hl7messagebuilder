package org.openmrs.module.hl7messagebuilder.generator;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.openmrs.module.hl7messagebuilder.api.model.PatientDemographic;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.message.ADT_A24;

/**
 * @author machabane
 */
public class AdtMessageFactory {
	
	static Logger log = Logger.getLogger(AdtMessageFactory.class.getName());
	
	public static List<ADT_A24> createMessage(String messageType, List<PatientDemographic> demographics)
	        throws HL7Exception, IOException {
		
		if (messageType.equals("A24")) {
			return new OurAdtA04MessageBuilder().Build(demographics);
		}
		
		throw new RuntimeException(String.format("%s message type is not supported yet. Extend this if you need to",
		    messageType));
	}
}
