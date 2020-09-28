package org.openmrs.module.hl7messagebuilder.generator;

import java.io.IOException;
import java.util.List;

import org.openmrs.module.hl7messagebuilder.api.model.PatientDemographic;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v23.message.ADT_A04;

/**
 * @author machabane
 */
public class AdtMessageFactory {
	
	public static List<ADT_A04> createMessage(String messageType, List<PatientDemographic> demographics)
	        throws HL7Exception, IOException {
		
		if (messageType.equals("A04")) {
			return new OurAdtA04MessageBuilder().Build(demographics);
		}
		
		throw new RuntimeException(String.format("%s message type is not supported yet. Extend this if you need to",
		    messageType));
	}
}
