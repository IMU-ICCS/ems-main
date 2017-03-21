package eu.paasage.upperware.fakeSolver;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.upperware.loadPaaSageInstance.ConfigurationLoad;
import eu.paasage.upperware.loadPaaSageInstance.ConstraintProblemLoad;
import eu.paasage.upperware.loadPaaSageInstance.ModelProcess;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.VirtualMachine;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;

public class Solver {
	private ModelProcess instanceModel;

	public ModelProcess getInstanceModel() {
		return instanceModel;
	}

	public void setInstanceModel(ModelProcess instanceModel) {
		this.instanceModel = instanceModel;
	}

	public Solver(ConfigurationLoad config,
			ConstraintProblemLoad constraintProblem) {
		super();
		this.instanceModel = new ModelProcess(config,constraintProblem);
	}

	public Solver() {
		super();
		this.instanceModel = null;
	}

	public Solver(String configpath, String constraintProblempath) {
		super();
		this.instanceModel = new ModelProcess(configpath,constraintProblempath);
	}
	public void solve(String configpath) {
		
		if (instanceModel!= null){
			instanceModel.loadModel();
			ConfigurationLoad config = instanceModel.getConfig();
			PaasageConfiguration application = config.getConfig();
			
			// Create a virtual machine, with an id (MUST BE UNIQUE!!!)
			VirtualMachine vm = ApplicationFactory.eINSTANCE.createVirtualMachine();
			application.getVms().add(vm);
			vm.setId("aDummyVMid");

			// Retrieve a VM Profile
			VirtualMachineProfile vmprofile = application.getVmProfiles().get(0);
			// Set the type of the newly created vm to this VMProfile
			vm.setProfile(vmprofile);

			// Directly write a solution : associate all components to this VM
			for (ApplicationComponent component : application.getComponents() ) {
				component.setVm(vm);
			}
						
			// Initialize the model
			ApplicationPackage.eINSTANCE.eClass();
			// Register the XMI resource factory for the .xmi extension
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("xmi", new XMIResourceFactoryImpl());
			
	
		    // Obtain a new resource set
		    ResourceSet resSet = new ResourceSetImpl();
	
		    // create a resource
		    Resource resource = resSet.createResource(URI.createURI(configpath));
		    
		    resource.getContents().add(application);
	
		    // now save the content.
		    try {
		      resource.save(Collections.EMPTY_MAP);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
			
		}
		
		
	}
	
}
