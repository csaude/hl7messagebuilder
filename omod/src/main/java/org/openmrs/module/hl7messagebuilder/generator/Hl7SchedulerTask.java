package org.openmrs.module.hl7messagebuilder.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;

import org.openmrs.api.context.Context;
import org.openmrs.module.hl7messagebuilder.api.Hl7messagebuilderService;
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
		
		String currentTimeStamp = Util.getCurrentTimeStamp();
		
		hl7messagebuilderService = Context.getService(Hl7messagebuilderService.class);
		
		// prepare the headers
		headers = "FHS|^~\\&|XYZSYS|XYZ " + Context.getLocationService().getDefaultLocation() + "|DISA*LAB|SGP|"
		        + currentTimeStamp + "||Patient_Demographic_Data.hl7|"
		        + "WEEKLY HL7 UPLOAD|00009972|\rBHS|^~\\&|XYZSYS|XYZ " + Context.getLocationService().getDefaultLocation()
		        + "|DISA*LAB|SGP|" + currentTimeStamp + "||||00010223\r";
		
		List<String> sitesByUuid = hl7messagebuilderService.getLocationsByUuid();
		
		for (String site : sitesByUuid) {
			
			// create the HL7 message
			List<ADT_A24> adtMessages = AdtMessageFactory.createMessage("A24",
			    hl7messagebuilderService.getPatientDemographicData(site));
			
			PipeParser pipeParser = new PipeParser();
			pipeParser.getParserConfiguration();
			
			// serialize the message to pipe delimited output file
			writeMessageToFile(pipeParser, adtMessages, site + ".hl7");
		}
	}
	
	private void writeMessageToFile(Parser parser, List<ADT_A24> adtMessages, String outputFilename) throws IOException,
	        FileNotFoundException, HL7Exception {
		log.info("writeMessageToFile called...");
		
		OutputStream outputStream = null;
		try {
			
			// Remember that the file may not show special delimiter characters when using
			// plain text editor
			File file = new File(Context.getAdministrationService().getGlobalPropertyObject(Constants.HL7_DOCKER_FOLDER)
			        .getPropertyValue()
			        + outputFilename);
			
			file.createNewFile();
			
			log.info("Serializing message to file...");
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
			adtMessages = null;
		}
	}
}
