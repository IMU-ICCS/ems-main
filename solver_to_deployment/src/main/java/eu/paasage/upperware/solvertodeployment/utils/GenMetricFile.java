package eu.paasage.upperware.solvertodeployment.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import eu.paasage.camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import eu.paasage.upperware.solvertodeployment.lib.SolverToDeployment;

@Slf4j
public class GenMetricFile {

	CDOClient cdoClient;
	PaasageConfiguration paasageConfiguration;
	ConstraintProblem constraintProblem;
	
	String findValue(MetricVariable mv, Solution sols)
	{
		for (MetricVariableValue mvalue : sols.getMetricVariableValue())
		{
			if (mvalue.getVariable() == mv)
			{
				NumericValueUpperware val = mvalue.getValue();
				if (val instanceof DoubleValueUpperware) 
				{
					DoubleValueUpperware dvu = (DoubleValueUpperware) val;
					return Double.toString(dvu.getValue());
				}

			}
		}
		return null;
	}

	public void doWork(String paasageConfigurationID, String outputFile)
	{
		cdoClient = new CDOClient();

		log.info("Reading CP model from CDO...");
		cdoClient.registerPackage(ApplicationPackage.eINSTANCE);
		cdoClient.registerPackage(CpPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPaasagePackage.eINSTANCE);
		cdoClient.registerPackage(TypePackage.eINSTANCE);
		CDOView cdoView = cdoClient.openView();
		log.info("Reading CP model from CDO...done");

		log.info("Extracting models...");
		EList<EObject> contentsPC = cdoView.getResource(paasageConfigurationID).getContents();
		paasageConfiguration = (PaasageConfiguration) contentsPC.get(0);
		constraintProblem = (ConstraintProblem) contentsPC.get(1);

		// Find latest solution
		Solution sol=null;
		long ts = -1;
		for(Solution current : constraintProblem.getSolution()) 
		{
			if ((sol==null) || (current.getTimestamp() > ts))
			{
				sol = current;
				ts = current.getTimestamp();
			}
		}
		log.info("Extracting models...done");

        // The name of the file to open.

        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            BufferedWriter writer = new BufferedWriter(fileWriter);

    		// Write info
    		for(MetricVariable mv : constraintProblem.getMetricVariables())
    		{
    			String val = this.findValue(mv, sol);
    			log.info(" mv: "+mv.getId()+ " = "+val);
    			writer.write(mv.getId()+":" + val+"\n");
    		}

    		// Closing the file
            writer.close();
        }
        catch(IOException ex) {
            log.error("Error writing to file '"+ outputFile + "'");
            return ;
        }
		// Closing everything
		log.info("Closing CDO stuff...");
		cdoView.close();
		cdoClient.closeSession();

		
	}
	
	public static void main(String args[])
	{
		if (args.length!=2) {
			System.err.println("Usage: GenMetricFile CPid outputFile");
		}
		String paasageConfigurationID = args[0];
		String outputFile = args[1];
		
		GenMetricFile gen = new GenMetricFile();
		
		gen.doWork(paasageConfigurationID, outputFile);
		
		log.info("All done.");
	}
}
