package org.openmrs.module.hl7messagebuilder.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

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
	
	static Logger log = Logger.getLogger(Hl7SchedulerTask.class.getName());
	
	private String headers;
	
	private String footers;
	
	private Hl7messagebuilderService hl7messagebuilderService;
	
	private BigDecimal totalNumOfRecords;
	
	private OutputStream outputStream;
	
	private File file;
	
	private int firstTime;
	
	private String locationAttributeUuid;
	
	public Hl7SchedulerTask() {
		locationAttributeUuid = Context.getAdministrationService()
		        .getGlobalPropertyObject(Constants.LOCATION_ATTRIBUTE_UUID).getPropertyValue();
	}
	
	public void execute() {
		log.info("Hl7SchedulerTask started...");
		try {
			Context.openSession();
			createHl7File();
			Context.closeSession();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Hl7SchedulerTask ended...");
	}
	
	private void createHl7File() throws HL7Exception, IOException {
		log.info("createHl7File called...");
		
		hl7messagebuilderService = Context.getService(Hl7messagebuilderService.class);
		String currentTimeStamp = Util.getCurrentTimeStamp();
		Integer maxId = 0;
		firstTime = 1;
		outputStream = null;
		file = null;
		
		// prepare the headers
		headers = "FHS|^~\\&|XYZSYS|XYZ " + Context.getLocationService().getDefaultLocation() + "|DISA*LAB|SGP|"
		        + currentTimeStamp + "||chabeco_patient_demographic_data.hl7|"
		        + "WEEKLY HL7 UPLOAD|00009972|\rBHS|^~\\&|XYZSYS|XYZ " + Context.getLocationService().getDefaultLocation()
		        + "|DISA*LAB|SGP|" + currentTimeStamp + "||||00010223\r";
		
		// create the HL7 message
		totalNumOfRecords = new BigDecimal(hl7messagebuilderService.getPatientDemographicSize());
		
		// How many times I should go to the database
		BigDecimal timesToTheDatabase = totalNumOfRecords.divide(new BigDecimal("2000"), 0, RoundingMode.CEILING);
		
		PipeParser pipeParser = new PipeParser();
		pipeParser.getParserConfiguration();
		
		List<Integer> personLst = new LinkedList<Integer>();
		for (long i = 0; i < timesToTheDatabase.longValue(); i++) {
			if (maxId == 0) {
				List<PatientDemographic> demographics = new LinkedList<PatientDemographic>(
				        hl7messagebuilderService.getPatientDemographicData(maxId));
				for (PatientDemographic pD : demographics) {
					personLst.add(pD.getPersonId());
				}
				
				List<ADT_A24> adtMessages = AdtMessageFactory.createMessage("A24", demographics);
				
				// serialize the message to pipe delimited output file
				writeMessageToFile(pipeParser, adtMessages, "Patient_Demographic_Data.hl7");
				
				//set the new maxId
				maxId = Collections.max(personLst);
				personLst.clear();
			} else if (maxId > 0) {
				firstTime = 0;
				List<PatientDemographic> demographics = new LinkedList<PatientDemographic>(
				        hl7messagebuilderService.getPatientDemographicData(maxId));
				List<ADT_A24> adtMessages = AdtMessageFactory.createMessage("A24", demographics);
				
				// serialize the message to pipe delimited output file
				writeMessageToFile(pipeParser, adtMessages, "Patient_Demographic_Data.hl7");
				
				for (PatientDemographic pD : demographics) {
					personLst.add(pD.getPersonId());
				}
				maxId = Collections.max(personLst);
				personLst.clear();
			}
		}
		
		System.out.printf("Message serialized to file '%s' successfully", file);
		System.out.println("\n");
		
		footers = "BTS|" + totalNumOfRecords.toString() + "\rFTS|1";
		outputStream.write(footers.getBytes());
		outputStream.flush();
		outputStream.close();
	}
	
	private void writeMessageToFile(Parser parser, List<ADT_A24> adtMessages, String outputFilename) throws IOException,
	        FileNotFoundException, HL7Exception {
		log.info("writeMessageToFile called...");
		
		if (firstTime == 1) {
			// Remember that the file may not show special delimiter characters when using
			// plain text editor
			file = new File(outputFilename);
			file.createNewFile();
			outputStream = new FileOutputStream(file);
			outputStream.write(headers.getBytes());
			
			for (ADT_A24 adt_A24 : adtMessages) {
				outputStream.write(parser.encode(adt_A24).getBytes());
				outputStream.write(System.getProperty("line.separator").getBytes());
			}
			
			//send the hl7 file to disa
			//Util.sendHl7File(file.getName());
		} else if (firstTime == 0) {
			for (ADT_A24 adt_A24 : adtMessages) {
				outputStream.write(parser.encode(adt_A24).getBytes());
				outputStream.write(System.getProperty("line.separator").getBytes());
			}
		}
	}
}
