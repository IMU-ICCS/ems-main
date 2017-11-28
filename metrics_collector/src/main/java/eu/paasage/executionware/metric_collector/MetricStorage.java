package eu.paasage.executionware.metric_collector;

import java.util.Date;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

import eu.paasage.camel.Application;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.execution.ApplicationMeasurement;
import eu.paasage.camel.execution.CommunicationMeasurement;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionFactory;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.execution.InternalComponentMeasurement;
import eu.paasage.camel.execution.Measurement;
import eu.paasage.camel.execution.VMMeasurement;
import eu.paasage.camel.metric.CompositeMetricInstance;
import eu.paasage.camel.metric.MetricInstance;
import eu.paasage.executionware.metric_collector.SynchronisedMetricStorage.MeasurementType;
import eu.paasage.executionware.metric_collector.pubsub.PublicationServer;

public abstract class MetricStorage {
	static CDOTransaction trans = null;
	private static PublicationServer server = null;
	private static boolean hasServer = false;
	
	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MetricStorage.class);
	
	public static void initServer(int threadNum, int portNum){
		server = new PublicationServer(threadNum,portNum);
		hasServer = true;
	}
	
	public static void initServer(int threadNum){
		server = new PublicationServer(threadNum);
		hasServer = true;
	}
	
	public static void terminateServer(){
		server.terminate();
	}
	
	public static void storeMeasurement(double value, CDOID ID, CDOID ecID, MeasurementType measurementType, CDOID object, CDOID object2){
			ExecutionContext ec = (ExecutionContext)trans.getObject(ecID);
			ExecutionModel em = (ExecutionModel)ec.eContainer();
			MetricInstance mi = (MetricInstance)trans.getObject(ID);
			String metricId = mi.getMetric().getName();
			Measurement m = null;
			
			//Store in CDO and possibly report measurements only if push check is OK, i.e., the respective metric of the measurement is involved in a SLO, 
			//OptimisationRequirement or scalability rule NonFunctionalEvent
			//Commenting for now the check to report everything
			//if (mi instanceof CompositeMetricInstance && CDOUtils.canPush(ID, trans)){
				//Create MeasurementObject to be stored
				switch(measurementType){
					case APPLICATION_MEASUREMENT:
						ApplicationMeasurement am = ExecutionFactory.eINSTANCE.createApplicationMeasurement();
						am.setApplication((Application)trans.getObject(object));
						m = am;
						break;
					case VM_MEASUREMENT:
						VMMeasurement rm = ExecutionFactory.eINSTANCE.createVMMeasurement();
						VMInstance vmInst = (VMInstance)trans.getObject(object);
						rm.setVmInstance(vmInst);
						metricId += "_" + vmInst.getType().getName();
						m = rm;
						break;
					case COMPONENT_MEASUREMENT:
						InternalComponentMeasurement cm = ExecutionFactory.eINSTANCE.createInternalComponentMeasurement();
						InternalComponentInstance ici = (InternalComponentInstance)trans.getObject(object);
						cm.setInternalComponentInstance(ici);
						metricId += "_" + ici.getType().getName();
						m = cm;
						break;
					case COMMUNICATION_MEASUREMENT:
						CommunicationMeasurement rcm = ExecutionFactory.eINSTANCE.createCommunicationMeasurement();
						rcm.setSourceVMInstance((VMInstance)trans.getObject(object));
						rcm.setDestinationVMInstance((VMInstance)trans.getObject(object2));
						metricId += "_" + rcm.getSourceVMInstance().getType().getName() + "_" + rcm.getDestinationVMInstance().getType().getName();
						m = rcm;
						break;
				}
				m.setMetricInstance(mi);
				m.setExecutionContext(ec);
				m.setValue(value);
				m.setMeasurementTime(new Date());
				m.setName(CDOIDUtil.createUUID().toString());
				
				//Add measurement
				em.getMeasurements().add(m);
				logger.info("Adding measurement: " + value + " for metric: " + metricId + " of instance: " + mi.getName());
				
				//Push measurement if publication server initiated
				if (hasServer){
					server.submitValue(metricId, value);
					logger.info("Can push measurement: " + value + " for metric: " + metricId);
				}
			//}
	}
}
