package org.openmrs.module.hl7messagebuilder.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.api.context.Context;

public class Util {
	
	private static Process p = null;
	
	public static String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
	}
	
	public static String DateToString(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	public static void sendHl7File(String fileName) {
		System.out.println("sending Hl7 File to disa-link started...");
		
		try {
			String hl7Cmd = "sshpass -p '"
			        + Context.getAdministrationService().getGlobalPropertyObject(Constants.DISA_USERNAME).getPropertyValue()
			        + "' scp "
			        + fileName
			        + " "
			        + Context.getAdministrationService().getGlobalPropertyObject(Constants.DISA_USER).getPropertyValue()
			        + "@"
			        + Context.getAdministrationService().getGlobalPropertyObject(Constants.DISA_IPADDRESS)
			                .getPropertyValue()
			        + ":"
			        + Context.getAdministrationService().getGlobalPropertyObject(Constants.DISA_SHARED_FOLDER)
			                .getPropertyValue() + "";
			System.out.println("command to be executed " + hl7Cmd);
			p = Runtime.getRuntime().exec(hl7Cmd);
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			p.destroy();
			System.out.println("sending Hl7 File to disa-link ended...");
		}
	}
}
