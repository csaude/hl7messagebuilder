package org.openmrs.module.hl7messagebuilder.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	
	static Process p = null;
	
	public static String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
	}
	
	public static String DateToString(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	public static void sendHl7File() {
		try {
			p = Runtime.getRuntime().exec("cp Patient_Demographic_Data_1040114.hl7 Helio/");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			p.destroy();
		}
	}
}
