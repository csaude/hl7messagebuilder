package org.openmrs.module.hl7messagebuilder.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openmrs.module.hl7messagebuilder.api.model.PatientDemographic;

public class HL7Util {
	
	static Logger log = Logger.getLogger(HL7Util.class.getName());
	
	public static String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	public static String DateToString(Date aux) {
		String strDate = new SimpleDateFormat("yyyyMMdd").format(aux);
		return strDate;
	}
	
	public static List<PatientDemographic> clearListFromDuplicateNid(List<PatientDemographic> demographics) {
		log.info("clearListFromDuplicateNid called...");
		
		Map<String, PatientDemographic> cleanMap = new LinkedHashMap<String, PatientDemographic>();
		
		log.info("remove duplicates started...");
		for (int i = 0; i < demographics.size(); i++) {
			cleanMap.put(demographics.get(i).getPid(), demographics.get(i));
		}
		log.info("remove duplicates ended...");
		
		List<PatientDemographic> clearedList = new LinkedList<PatientDemographic>(cleanMap.values());
		
		return clearedList;
	}
}
