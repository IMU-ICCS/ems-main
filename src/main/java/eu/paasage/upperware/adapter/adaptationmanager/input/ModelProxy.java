package eu.paasage.upperware.adapter.adaptationmanager.input;

import java.io.File;
import java.util.List;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.ecore.EObject;

import com.eclipsesource.json.JsonObject;

import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.Hosting;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.RequiredCommunication;
import eu.paasage.camel.deployment.RequiredCommunicationInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.plangenerator.util.ModelToJsonConverter;

public class ModelProxy {
	
	private CDOClient client;
	private CDOTransaction transaction;

	public ModelProxy() {
		this.client = new CDOClient();	
		//this.transaction = client.openTransaction();
	}
	
	private CamelModel getCamelModel(String resource) throws Exception {
		//should be a camel model resource name rather than "test"
		CDOResource cdoRes = transaction.getOrCreateResource(resource);
		List<EObject> results = cdoRes.getContents();
		System.out.println("The results of the query are:" + results); //CamelModel@OID:http://www.paasage.eu/2015/06/camel#CamelModel#1
		CamelModel model = (CamelModel) results.get(0);
		if(model == null){
			throw new Exception("failed to get camel model");
		}
		return model;	 
	}	
	public void getTransaction(){
		this.transaction = client.openTransaction();
	}
	public void stopProxy(){
		if(this.transaction != null){
			client.closeTransaction(transaction);
			System.out.print("...stopping proxy, closed session etc.");
		}else{
			System.out.print("no transaction, just stopping session....");
		}
		client.closeSession();
	}
	
	public void testModel(CamelModel model) throws Exception{
		
		DeploymentModel dm = model.getDeploymentModels().get(0);
		if(dm != null){
			Application app = ((CamelModel) dm.eContainer()).getApplications().get(0);			
			JsonObject appObj = ModelToJsonConverter.convertApp(app);
			System.out.println("**************** Application Json Model ******************");
			System.out.println(appObj.toString() + "\n");
			//
			List<VM> vms = dm.getVms();
			List<VMInstance> vmInstances = dm.getVmInstances();
			List<InternalComponent> comps = dm.getInternalComponents();
			List<InternalComponentInstance> compInstances = dm.getInternalComponentInstances();
			List<Hosting> hostings = dm.getHostings();
			List<HostingInstance> hostingInstances = dm.getHostingInstances();
			List<Communication> communications = dm.getCommunications();
			List<CommunicationInstance> comInstances = dm.getCommunicationInstances();
			if(vms != null && !vms.isEmpty()){
				convertVMS(vms);
			}
			if(vmInstances != null && !vmInstances.isEmpty()){
				convertVMInstances(vmInstances);
			}
			if(comps != null && !comps.isEmpty()){
				convertInternalComponents(comps);
			}
			if(compInstances != null && !compInstances.isEmpty()){
				convertInternalComponentInstances(compInstances);
			}
			if(hostings != null && !hostings.isEmpty()){
				convertHostings(hostings);
			}
			if(hostingInstances != null && !hostingInstances.isEmpty()){
				convertHostingInstances(hostingInstances);
			}
			if(communications != null && !communications.isEmpty()){
				convertCommunications(communications);
			}
			if(comInstances != null && !comInstances.isEmpty()){
				convertCommunicationInstances(comInstances);
			}
			
		}else{
			throw new Exception("failed to get deployment model!");
		}
	}
	
	public void tryLoadTwoFiles(String camel, String provider){
		System.out.println("...inside tryLoadTwoFiles....");
		try{
			//loadModel is a static method
			CamelModel cModel = (CamelModel) CDOClient.loadModel(camel);
			CamelModel pModel = (CamelModel) CDOClient.loadModel(provider);
			//
			this.testModel(cModel);		
			
			
		}catch(Exception e){
			System.out.println("Exception trying to load two files for one model : " + e.getMessage());
		}
	}
	
	private void convertVMS(List<VM> vms){
		System.out.println("**************** VMs Json Model ******************");
		for(VM vm : vms){
			JsonObject vmObj = ModelToJsonConverter.convertVM(vm);			
			System.out.println(vmObj.toString() + "\n");
		}
	}
	private void convertVMInstances(List<VMInstance> vmis){
		System.out.println("**************** VMInstances Json Model ******************");
		for(VMInstance vmi : vmis){
			JsonObject vmiObj = ModelToJsonConverter.convertVMInstance(vmi);			
			System.out.println(vmiObj.toString() + "\n");
		}
	}
	private void convertInternalComponents(List<InternalComponent> ics){
		System.out.println("**************** InternalComponents Json Model ******************");
		for(InternalComponent ic : ics){
			JsonObject icObj = ModelToJsonConverter.convertInternalComponent(ic);			
			System.out.println(icObj.toString() + "\n");
		}
	}
	private void convertInternalComponentInstances(List<InternalComponentInstance> icis){
		System.out.println("**************** InternalComponents Json Model ******************");
		for(InternalComponentInstance ici : icis){
			JsonObject iciObj = ModelToJsonConverter.convertInternalComponentInstance(ici);			
			System.out.println(iciObj.toString() + "\n");
		}
	}
	private void convertHostings(List<Hosting> hostings){
		System.out.println("**************** Hostings Json Model ******************");
		for(Hosting hosting : hostings){
			JsonObject hostingObj = ModelToJsonConverter.convertHosting(hosting);			
			System.out.println(hostingObj.toString() + "\n");
		}
	}
	private void convertHostingInstances(List<HostingInstance> hostingInstances){
		System.out.println("**************** Hostings Json Model ******************");
		for(HostingInstance hostingInstance : hostingInstances){
			JsonObject hostingInsObj = ModelToJsonConverter.convertHostingInstance(hostingInstance);			
			System.out.println(hostingInsObj.toString() + "\n");
		}
	}
	private void convertCommunications(List<Communication> communications){
		System.out.println("**************** Communications Json Model ******************");
		for(Communication com : communications){
			JsonObject communicationObj = ModelToJsonConverter.convertCommunication(com);			
			System.out.println(communicationObj.toString() + "\n");
		}
	}
	private void convertCommunicationInstances(List<CommunicationInstance> communicationInstances){
		System.out.println("**************** Communication Instances Json Model ******************");
		for(CommunicationInstance comInstance : communicationInstances){
			//
			RequiredCommunicationInstance rci = comInstance.getRequiredCommunicationInstance();
			InternalComponentInstance ins = (InternalComponentInstance) rci.eContainer();
			if(this.transaction != null){
				System.out.println("Required Communication Instance(" +rci.getName() + ") has cdoid (" + rci.cdoID().toString() + ") has parent (" + ins.getName() + ") has cdoid (" + ins.cdoID().toString() + ")");
			}
			//String reqComParent = ((InternalComponentInstance) rci.eContainer()).getName();
			//System.out.println("Required Communication Instance(" +rci.getName() + ") has parent( " + reqComParent + ")");
			JsonObject comInsObj = ModelToJsonConverter.convertCommunicationInstance(comInstance);			
			System.out.println(comInsObj.toString() + "\n");
		}
	}

	public static void main(String[] args) {
		
		ModelProxy modelProxy = new ModelProxy();
		try{
			{	//use this for live cdo transaction
				if(modelProxy.transaction == null){
					modelProxy.getTransaction();
				}
				CamelModel model = modelProxy.getCamelModel("test");
				modelProxy.testModel(model);
			}
			{	//use this for loading static camelModel and providerModel files 
//				//String camel = "c:" + File.separator + File.separator + "git" + File.separator + "plan-generator" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "test_1.xmi";
//				String camel = "/home/asinha/git/paasadapterOW2/paasadapter/src/test/resources/ver2_0/test.xmi";
//				//String provider = "c:" + File.separator + File.separator + "git" + File.separator + "plan-generator" + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi";
//				String provider = "/home/asinha/git/paasadapterOW2/paasadapter/src/test/resources/ver2_0/upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi";
//				modelProxy.tryLoadTwoFiles(camel, provider);
			}
		}catch(Exception e){
			System.out.println("Exception : " + e.getMessage());
		}finally{
			modelProxy.stopProxy();
		}

	}

}
