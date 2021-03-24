package org.openmrs.module.hl7messagebuilder.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	
	public static String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	public static String DateToString(Date aux) {
		String strDate = new SimpleDateFormat("yyyyMMdd").format(aux);
		return strDate;
	}
}
