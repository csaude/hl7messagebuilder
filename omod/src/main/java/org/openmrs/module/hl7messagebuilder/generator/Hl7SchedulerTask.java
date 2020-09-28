package org.openmrs.module.hl7messagebuilder.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.hl7messagebuilder.api.model.PatientDemographic;
import org.openmrs.scheduler.tasks.AbstractTask;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.v23.message.ADT_A04;
import ca.uhn.hl7v2.parser.Parser;

/**
 * @author machabane
 */
public class Hl7SchedulerTask extends AbstractTask {
	
	private static HapiContext context = new DefaultHapiContext();
	
	//private Hl7messagebuilderService hl7messagebuilderService;
	
	private List<PatientDemographic> demographics;
	
	/*public Hl7SchedulerTask() {
		hl7messagebuilderService = Context.getService(Hl7messagebuilderService.class);
		demographics = hl7messagebuilderService.getPatientDemographicData();
	}*/
	
	@Override
	public void execute() {
		try {
			Context.openSession();
			createHl7File();
			Context.closeSession();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createHl7File() throws HL7Exception, IOException {
		
		// create the HL7 message
		System.out.println("Creating ADT A04 message...");
		List<ADT_A04> adtMessages = AdtMessageFactory.createMessage("A04", demographics);
		
		// create these parsers for file operations
		Parser pipeParser = context.getPipeParser();
		Parser xmlParser = context.getXMLParser();
		
		// serialize the message to pipe delimited output file
		writeMessageToFile(pipeParser, adtMessages, "./src/main/resources/chabeco_patient_metadata.txt");
		
		// serialize the message to XML format output file
		writeMessageToFile(xmlParser, adtMessages, "./src/main/resources/chabeco_patient_metadata.xml");
	}
	
	private static void writeMessageToFile(Parser parser, List<ADT_A04> adtMessages, String outputFilename)
	        throws IOException, FileNotFoundException, HL7Exception {
		OutputStream outputStream = null;
		try {
			
			// Remember that the file may not show special delimiter characters when using
			// plain text editor
			File file = new File(outputFilename);
			
			// quick check to create the file before writing if it does not exist already
			if (!file.exists()) {
				file.createNewFile();
			}
			
			System.out.println("Serializing message to file...");
			outputStream = new FileOutputStream(file);
			
			for (ADT_A04 adt_A04 : adtMessages) {
				outputStream.write(parser.encode(adt_A04).getBytes());
				outputStream.write(System.getProperty("line.separator").getBytes());
				outputStream.flush();
			}
			
			System.out.printf("Message serialized to file '%s' successfully", file);
			System.out.println("\n");
		}
		finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}
