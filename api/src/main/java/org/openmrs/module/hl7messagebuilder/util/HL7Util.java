package org.openmrs.module.hl7messagebuilder.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.hl7messagebuilder.api.model.PatientDemographic;

public class HL7Util {
	
	public static String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	public static String DateToString(Date aux) {
		String strDate = new SimpleDateFormat("yyyyMMdd").format(aux);
		return strDate;
	}
	
	public static List<PatientDemographic> clearListFromDuplicateNid(List<PatientDemographic> demographics) {
		
		Map<String, PatientDemographic> cleanMap = new LinkedHashMap<String, PatientDemographic>();
		
		for (int i = 0; i < demographics.size(); i++) {
			cleanMap.put(demographics.get(i).getPid(), demographics.get(i));
		}
		
		List<PatientDemographic> clearedList = new ArrayList<PatientDemographic>(cleanMap.values());
		
		return clearedList;
	}
	
	public static String listToString(List<String> locationsBySite) {
		String locations = StringUtils.join(locationsBySite, "','");
		locations = "'" + locations + "'";
		return locations;
	}
}
