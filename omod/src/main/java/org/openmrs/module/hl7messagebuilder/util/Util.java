package org.openmrs.module.hl7messagebuilder.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	
	public static String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
	}
	
	public static String DateToString(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
}
