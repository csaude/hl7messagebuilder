package org.openmrs.module.hl7messagebuilder.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.hl7messagebuilder.api.Hl7messagebuilderService;
import org.openmrs.module.hl7messagebuilder.api.model.PatientDemographic;
import org.openmrs.module.hl7messagebuilder.util.Constants;
import org.openmrs.module.hl7messagebuilder.util.Util;
import org.openmrs.scheduler.tasks.AbstractTask;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.message.ADT_A24;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;

/**
 * @author machabane
 */
public class Hl7SchedulerTask extends AbstractTask {
	
	private String headers;
	
	private String footers;
	
	private Hl7messagebuilderService hl7messagebuilderService;
	
	//private List<PatientDemographic> demographics;
	
	private String locationAttributeUuid;
	
	public Hl7SchedulerTask() {
		locationAttributeUuid = Context.getAdministrationService()
		        .getGlobalPropertyObject(Constants.LOCATION_ATTRIBUTE_UUID).getPropertyValue();
		//hl7messagebuilderService = Context.getService(Hl7messagebuilderService.class);
		//demographics = new ArrayList<PatientDemographic>(hl7messagebuilderService.getPatientDemographicData());
	}
	
	public void execute() {
		System.out.println("Hl7SchedulerTask started...");
		try {
			Context.openSession();
			createHl7File();
			Context.closeSession();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Hl7SchedulerTask ended...");
	}
	
	private void createHl7File() throws HL7Exception, IOException {
		
		hl7messagebuilderService = Context.getService(Hl7messagebuilderService.class);
		
		String currentTimeStamp = Util.getCurrentTimeStamp();
		
		Integer maxId = 0;
		
		// prepare the headers
		headers = "FHS|^~\\&|XYZSYS|XYZ " + Context.getLocationService().getDefaultLocation() + "|DISA*LAB|SGP|"
		        + currentTimeStamp + "||chabeco_patient_demographic_data.hl7|"
		        + "WEEKLY HL7 UPLOAD|00009972|\rBHS|^~\\&|XYZSYS|XYZ " + Context.getLocationService().getDefaultLocation()
		        + "|DISA*LAB|SGP|" + currentTimeStamp + "||||00010223\r";
		
		// create the HL7 message
		System.out.println("Creating ADT A24 message...");
		//BigInteger totalNumOfRecords = hl7messagebuilderService.getPatientDemographicSize();
		
		List<ADT_A24> adtMessages = AdtMessageFactory.createMessage("A24", new ArrayList<PatientDemographic>(
		        hl7messagebuilderService.getPatientDemographicData(maxId)));
		
		PipeParser pipeParser = new PipeParser();
		pipeParser.getParserConfiguration();
		
		// serialize the message to pipe delimited output file
		writeMessageToFile(pipeParser, adtMessages, "Patient_Demographic_Data_"
		        + Context.getLocationService().getLocationAttributeByUuid(locationAttributeUuid).getValueReference()
		        + ".hl7");
	}
	
	private void writeMessageToFile(Parser parser, List<ADT_A24> adtMessages, String outputFilename) throws IOException,
	        FileNotFoundException, HL7Exception {
		OutputStream outputStream = null;
		try {
			
			// Remember that the file may not show special delimiter characters when using
			// plain text editor
			File file = new File(outputFilename);
			
			file.createNewFile();
			
			System.out.println("Serializing message to file...");
			outputStream = new FileOutputStream(file);
			
			outputStream.write(headers.getBytes());
			
			for (ADT_A24 adt_A24 : adtMessages) {
				outputStream.write(parser.encode(adt_A24).getBytes());
				outputStream.write(System.getProperty("line.separator").getBytes());
				outputStream.flush();
			}
			
			footers = "BTS|" + String.valueOf(adtMessages.size()) + "\rFTS|1";
			
			outputStream.write(footers.getBytes());
			
			System.out.printf("Message serialized to file '%s' successfully", file);
			System.out.println("\n");
			
			//send the hl7 file to disa
			//Util.sendHl7File(file.getName());
		}
		finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}
