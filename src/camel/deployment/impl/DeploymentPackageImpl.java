/**
 */
package camel.deployment.impl;

import camel.CamelPackage;

import camel.deployment.CloudMLElement;
import camel.deployment.Communication;
import camel.deployment.CommunicationInstance;
import camel.deployment.CommunicationPort;
import camel.deployment.CommunicationPortInstance;
import camel.deployment.Component;
import camel.deployment.ComponentGroup;
import camel.deployment.ComponentInstance;
import camel.deployment.ComputationalResource;
import camel.deployment.DeploymentFactory;
import camel.deployment.DeploymentModel;
import camel.deployment.DeploymentPackage;
import camel.deployment.ExternalComponent;
import camel.deployment.ExternalComponentInstance;
import camel.deployment.Hosting;
import camel.deployment.HostingInstance;
import camel.deployment.HostingPort;
import camel.deployment.HostingPortInstance;
import camel.deployment.Image;
import camel.deployment.InternalComponent;
import camel.deployment.InternalComponentInstance;
import camel.deployment.ProvidedCommunication;
import camel.deployment.ProvidedCommunicationInstance;
import camel.deployment.ProvidedHost;
import camel.deployment.ProvidedHostInstance;
import camel.deployment.RequiredCommunication;
import camel.deployment.RequiredCommunicationInstance;
import camel.deployment.RequiredHost;
import camel.deployment.RequiredHostInstance;
import camel.deployment.VMInstance;

import camel.deployment.util.DeploymentValidator;

import camel.execution.ExecutionPackage;

import camel.execution.impl.ExecutionPackageImpl;

import camel.impl.CamelPackageImpl;

import camel.organisation.OrganisationPackage;

import camel.organisation.impl.OrganisationPackageImpl;

import camel.provider.ProviderPackage;

import camel.provider.impl.ProviderPackageImpl;

import camel.scalability.ScalabilityPackage;

import camel.scalability.impl.ScalabilityPackageImpl;

import camel.security.SecurityPackage;

import camel.security.impl.SecurityPackageImpl;

import camel.sla.SlaPackage;

import camel.sla.impl.SlaPackageImpl;

import camel.type.TypePackage;

import camel.type.impl.TypePackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DeploymentPackageImpl extends EPackageImpl implements DeploymentPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cloudMLElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deploymentModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass internalComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vmEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass computationalResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communicationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communicationPortEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass providedCommunicationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requiredCommunicationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hostingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hostingPortEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass providedHostEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requiredHostEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass imageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass internalComponentInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalComponentInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vmInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communicationInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communicationPortInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass providedCommunicationInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requiredCommunicationInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hostingInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hostingPortInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass providedHostInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requiredHostInstanceEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see camel.deployment.DeploymentPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DeploymentPackageImpl() {
		super(eNS_URI, DeploymentFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link DeploymentPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DeploymentPackage init() {
		if (isInited) return (DeploymentPackage)EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI);

		// Obtain or create and register package
		DeploymentPackageImpl theDeploymentPackage = (DeploymentPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DeploymentPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DeploymentPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		CamelPackageImpl theCamelPackage = (CamelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) instanceof CamelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) : CamelPackage.eINSTANCE);
		ExecutionPackageImpl theExecutionPackage = (ExecutionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) instanceof ExecutionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) : ExecutionPackage.eINSTANCE);
		OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) : OrganisationPackage.eINSTANCE);
		ProviderPackageImpl theProviderPackage = (ProviderPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) instanceof ProviderPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) : ProviderPackage.eINSTANCE);
		ScalabilityPackageImpl theScalabilityPackage = (ScalabilityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) instanceof ScalabilityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) : ScalabilityPackage.eINSTANCE);
		SecurityPackageImpl theSecurityPackage = (SecurityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) instanceof SecurityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) : SecurityPackage.eINSTANCE);
		SlaPackageImpl theSlaPackage = (SlaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) instanceof SlaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) : SlaPackage.eINSTANCE);
		TypePackageImpl theTypePackage = (TypePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) instanceof TypePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) : TypePackage.eINSTANCE);

		// Create package meta-data objects
		theDeploymentPackage.createPackageContents();
		theCamelPackage.createPackageContents();
		theExecutionPackage.createPackageContents();
		theOrganisationPackage.createPackageContents();
		theProviderPackage.createPackageContents();
		theScalabilityPackage.createPackageContents();
		theSecurityPackage.createPackageContents();
		theSlaPackage.createPackageContents();
		theTypePackage.createPackageContents();

		// Initialize created meta-data
		theDeploymentPackage.initializePackageContents();
		theCamelPackage.initializePackageContents();
		theExecutionPackage.initializePackageContents();
		theOrganisationPackage.initializePackageContents();
		theProviderPackage.initializePackageContents();
		theScalabilityPackage.initializePackageContents();
		theSecurityPackage.initializePackageContents();
		theSlaPackage.initializePackageContents();
		theTypePackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theDeploymentPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return DeploymentValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theDeploymentPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DeploymentPackage.eNS_URI, theDeploymentPackage);
		return theDeploymentPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCloudMLElement() {
		return cloudMLElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCloudMLElement_Name() {
		return (EAttribute)cloudMLElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCloudMLElement_Properties() {
		return (EReference)cloudMLElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCloudMLElement_Resources() {
		return (EReference)cloudMLElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeploymentModel() {
		return deploymentModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentModel_Components() {
		return (EReference)deploymentModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentModel_Communications() {
		return (EReference)deploymentModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentModel_Hostings() {
		return (EReference)deploymentModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentModel_Images() {
		return (EReference)deploymentModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentModel_Providers() {
		return (EReference)deploymentModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentModel_ComponentInstances() {
		return (EReference)deploymentModelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentModel_CommunicationInstances() {
		return (EReference)deploymentModelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentModel_HostingInstances() {
		return (EReference)deploymentModelEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponent() {
		return componentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_ProvidedCommunications() {
		return (EReference)componentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_ProvidedHosts() {
		return (EReference)componentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInternalComponent() {
		return internalComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInternalComponent_RequiredCommunications() {
		return (EReference)internalComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInternalComponent_CompositeInternalComponents() {
		return (EReference)internalComponentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInternalComponent_RequiredHost() {
		return (EReference)internalComponentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInternalComponent__Contains__InternalComponent_InternalComponent() {
		return internalComponentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExternalComponent() {
		return externalComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExternalComponent_Provider() {
		return (EReference)externalComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExternalComponent_Location() {
		return (EReference)externalComponentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVM() {
		return vmEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVM_MinRam() {
		return (EAttribute)vmEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVM_MaxRam() {
		return (EAttribute)vmEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVM_RamUnit() {
		return (EReference)vmEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVM_MinCores() {
		return (EAttribute)vmEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVM_MaxCores() {
		return (EAttribute)vmEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVM_MinStorage() {
		return (EAttribute)vmEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVM_MaxStorage() {
		return (EAttribute)vmEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVM_StorageUnit() {
		return (EReference)vmEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVM_MinCPU() {
		return (EAttribute)vmEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVM_MaxCPU() {
		return (EAttribute)vmEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVM_Os() {
		return (EAttribute)vmEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVM_Is64os() {
		return (EAttribute)vmEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVM_VmType() {
		return (EReference)vmEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponentGroup() {
		return componentGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentGroup_Id() {
		return (EAttribute)componentGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComputationalResource() {
		return computationalResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComputationalResource_DownloadCommand() {
		return (EAttribute)computationalResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComputationalResource_UploadCommand() {
		return (EAttribute)computationalResourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComputationalResource_InstallCommand() {
		return (EAttribute)computationalResourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComputationalResource_ConfigureCommand() {
		return (EAttribute)computationalResourceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComputationalResource_StartCommand() {
		return (EAttribute)computationalResourceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComputationalResource_StopCommand() {
		return (EAttribute)computationalResourceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommunication() {
		return communicationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunication_RequiredCommunication() {
		return (EReference)communicationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunication_ProvidedCommunication() {
		return (EReference)communicationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunication_RequiredPortResource() {
		return (EReference)communicationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunication_ProvidedPortResource() {
		return (EReference)communicationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunication_OfDataObject() {
		return (EReference)communicationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommunicationPort() {
		return communicationPortEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunicationPort_Component() {
		return (EReference)communicationPortEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommunicationPort_IsLocal() {
		return (EAttribute)communicationPortEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommunicationPort_PortNumber() {
		return (EAttribute)communicationPortEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProvidedCommunication() {
		return providedCommunicationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequiredCommunication() {
		return requiredCommunicationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequiredCommunication_IsMandatory() {
		return (EAttribute)requiredCommunicationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHosting() {
		return hostingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHosting_ProvidedHost() {
		return (EReference)hostingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHosting_RequiredHost() {
		return (EReference)hostingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHosting_RequiredHostResource() {
		return (EReference)hostingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHosting_ProvidedHostResource() {
		return (EReference)hostingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHostingPort() {
		return hostingPortEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHostingPort_Owner() {
		return (EReference)hostingPortEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProvidedHost() {
		return providedHostEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProvidedHost_Offers() {
		return (EReference)providedHostEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequiredHost() {
		return requiredHostEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRequiredHost_Demands() {
		return (EReference)requiredHostEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImage() {
		return imageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImage_Os() {
		return (EAttribute)imageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImage_Is64os() {
		return (EAttribute)imageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImage_ImageId() {
		return (EAttribute)imageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImage_ImageGroup() {
		return (EAttribute)imageEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponentInstance() {
		return componentInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentInstance_ProvidedCommunicationInstances() {
		return (EReference)componentInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentInstance_Type() {
		return (EReference)componentInstanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentInstance_ProvidedHostInstances() {
		return (EReference)componentInstanceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInternalComponentInstance() {
		return internalComponentInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInternalComponentInstance_RequiredCommunicationInstances() {
		return (EReference)internalComponentInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInternalComponentInstance_RequiredHostInstance() {
		return (EReference)internalComponentInstanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExternalComponentInstance() {
		return externalComponentInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExternalComponentInstance_Ips() {
		return (EAttribute)externalComponentInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVMInstance() {
		return vmInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVMInstance_PublicAddress() {
		return (EAttribute)vmInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVMInstance_CreatedOn() {
		return (EAttribute)vmInstanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVMInstance_DestroyedOn() {
		return (EAttribute)vmInstanceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVMInstance_HasConfig() {
		return (EReference)vmInstanceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVMInstance_HasInfo() {
		return (EReference)vmInstanceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getVMInstance__CheckDates__VMInstance() {
		return vmInstanceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommunicationInstance() {
		return communicationInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunicationInstance_Type() {
		return (EReference)communicationInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunicationInstance_RequiredCommunicationInstance() {
		return (EReference)communicationInstanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunicationInstance_ProvidedCommunicationInstance() {
		return (EReference)communicationInstanceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommunicationPortInstance() {
		return communicationPortInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunicationPortInstance_Type() {
		return (EReference)communicationPortInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunicationPortInstance_ComponentInstance() {
		return (EReference)communicationPortInstanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProvidedCommunicationInstance() {
		return providedCommunicationInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequiredCommunicationInstance() {
		return requiredCommunicationInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHostingInstance() {
		return hostingInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHostingInstance_ProvidedHostInstance() {
		return (EReference)hostingInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHostingInstance_RequiredHostInstance() {
		return (EReference)hostingInstanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHostingInstance_Type() {
		return (EReference)hostingInstanceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHostingPortInstance() {
		return hostingPortInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHostingPortInstance_Owner() {
		return (EReference)hostingPortInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHostingPortInstance_Type() {
		return (EReference)hostingPortInstanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProvidedHostInstance() {
		return providedHostInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequiredHostInstance() {
		return requiredHostInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentFactory getDeploymentFactory() {
		return (DeploymentFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		cloudMLElementEClass = createEClass(CLOUD_ML_ELEMENT);
		createEAttribute(cloudMLElementEClass, CLOUD_ML_ELEMENT__NAME);
		createEReference(cloudMLElementEClass, CLOUD_ML_ELEMENT__PROPERTIES);
		createEReference(cloudMLElementEClass, CLOUD_ML_ELEMENT__RESOURCES);

		deploymentModelEClass = createEClass(DEPLOYMENT_MODEL);
		createEReference(deploymentModelEClass, DEPLOYMENT_MODEL__COMPONENTS);
		createEReference(deploymentModelEClass, DEPLOYMENT_MODEL__COMMUNICATIONS);
		createEReference(deploymentModelEClass, DEPLOYMENT_MODEL__HOSTINGS);
		createEReference(deploymentModelEClass, DEPLOYMENT_MODEL__IMAGES);
		createEReference(deploymentModelEClass, DEPLOYMENT_MODEL__PROVIDERS);
		createEReference(deploymentModelEClass, DEPLOYMENT_MODEL__COMPONENT_INSTANCES);
		createEReference(deploymentModelEClass, DEPLOYMENT_MODEL__COMMUNICATION_INSTANCES);
		createEReference(deploymentModelEClass, DEPLOYMENT_MODEL__HOSTING_INSTANCES);

		componentEClass = createEClass(COMPONENT);
		createEReference(componentEClass, COMPONENT__PROVIDED_COMMUNICATIONS);
		createEReference(componentEClass, COMPONENT__PROVIDED_HOSTS);

		internalComponentEClass = createEClass(INTERNAL_COMPONENT);
		createEReference(internalComponentEClass, INTERNAL_COMPONENT__REQUIRED_COMMUNICATIONS);
		createEReference(internalComponentEClass, INTERNAL_COMPONENT__COMPOSITE_INTERNAL_COMPONENTS);
		createEReference(internalComponentEClass, INTERNAL_COMPONENT__REQUIRED_HOST);
		createEOperation(internalComponentEClass, INTERNAL_COMPONENT___CONTAINS__INTERNALCOMPONENT_INTERNALCOMPONENT);

		externalComponentEClass = createEClass(EXTERNAL_COMPONENT);
		createEReference(externalComponentEClass, EXTERNAL_COMPONENT__PROVIDER);
		createEReference(externalComponentEClass, EXTERNAL_COMPONENT__LOCATION);

		vmEClass = createEClass(VM);
		createEAttribute(vmEClass, VM__MIN_RAM);
		createEAttribute(vmEClass, VM__MAX_RAM);
		createEReference(vmEClass, VM__RAM_UNIT);
		createEAttribute(vmEClass, VM__MIN_CORES);
		createEAttribute(vmEClass, VM__MAX_CORES);
		createEAttribute(vmEClass, VM__MIN_STORAGE);
		createEAttribute(vmEClass, VM__MAX_STORAGE);
		createEReference(vmEClass, VM__STORAGE_UNIT);
		createEAttribute(vmEClass, VM__MIN_CPU);
		createEAttribute(vmEClass, VM__MAX_CPU);
		createEAttribute(vmEClass, VM__OS);
		createEAttribute(vmEClass, VM__IS64OS);
		createEReference(vmEClass, VM__VM_TYPE);

		componentGroupEClass = createEClass(COMPONENT_GROUP);
		createEAttribute(componentGroupEClass, COMPONENT_GROUP__ID);

		computationalResourceEClass = createEClass(COMPUTATIONAL_RESOURCE);
		createEAttribute(computationalResourceEClass, COMPUTATIONAL_RESOURCE__DOWNLOAD_COMMAND);
		createEAttribute(computationalResourceEClass, COMPUTATIONAL_RESOURCE__UPLOAD_COMMAND);
		createEAttribute(computationalResourceEClass, COMPUTATIONAL_RESOURCE__INSTALL_COMMAND);
		createEAttribute(computationalResourceEClass, COMPUTATIONAL_RESOURCE__CONFIGURE_COMMAND);
		createEAttribute(computationalResourceEClass, COMPUTATIONAL_RESOURCE__START_COMMAND);
		createEAttribute(computationalResourceEClass, COMPUTATIONAL_RESOURCE__STOP_COMMAND);

		communicationEClass = createEClass(COMMUNICATION);
		createEReference(communicationEClass, COMMUNICATION__REQUIRED_COMMUNICATION);
		createEReference(communicationEClass, COMMUNICATION__PROVIDED_COMMUNICATION);
		createEReference(communicationEClass, COMMUNICATION__REQUIRED_PORT_RESOURCE);
		createEReference(communicationEClass, COMMUNICATION__PROVIDED_PORT_RESOURCE);
		createEReference(communicationEClass, COMMUNICATION__OF_DATA_OBJECT);

		communicationPortEClass = createEClass(COMMUNICATION_PORT);
		createEReference(communicationPortEClass, COMMUNICATION_PORT__COMPONENT);
		createEAttribute(communicationPortEClass, COMMUNICATION_PORT__IS_LOCAL);
		createEAttribute(communicationPortEClass, COMMUNICATION_PORT__PORT_NUMBER);

		providedCommunicationEClass = createEClass(PROVIDED_COMMUNICATION);

		requiredCommunicationEClass = createEClass(REQUIRED_COMMUNICATION);
		createEAttribute(requiredCommunicationEClass, REQUIRED_COMMUNICATION__IS_MANDATORY);

		hostingEClass = createEClass(HOSTING);
		createEReference(hostingEClass, HOSTING__PROVIDED_HOST);
		createEReference(hostingEClass, HOSTING__REQUIRED_HOST);
		createEReference(hostingEClass, HOSTING__REQUIRED_HOST_RESOURCE);
		createEReference(hostingEClass, HOSTING__PROVIDED_HOST_RESOURCE);

		hostingPortEClass = createEClass(HOSTING_PORT);
		createEReference(hostingPortEClass, HOSTING_PORT__OWNER);

		providedHostEClass = createEClass(PROVIDED_HOST);
		createEReference(providedHostEClass, PROVIDED_HOST__OFFERS);

		requiredHostEClass = createEClass(REQUIRED_HOST);
		createEReference(requiredHostEClass, REQUIRED_HOST__DEMANDS);

		imageEClass = createEClass(IMAGE);
		createEAttribute(imageEClass, IMAGE__OS);
		createEAttribute(imageEClass, IMAGE__IS64OS);
		createEAttribute(imageEClass, IMAGE__IMAGE_ID);
		createEAttribute(imageEClass, IMAGE__IMAGE_GROUP);

		componentInstanceEClass = createEClass(COMPONENT_INSTANCE);
		createEReference(componentInstanceEClass, COMPONENT_INSTANCE__PROVIDED_COMMUNICATION_INSTANCES);
		createEReference(componentInstanceEClass, COMPONENT_INSTANCE__TYPE);
		createEReference(componentInstanceEClass, COMPONENT_INSTANCE__PROVIDED_HOST_INSTANCES);

		internalComponentInstanceEClass = createEClass(INTERNAL_COMPONENT_INSTANCE);
		createEReference(internalComponentInstanceEClass, INTERNAL_COMPONENT_INSTANCE__REQUIRED_COMMUNICATION_INSTANCES);
		createEReference(internalComponentInstanceEClass, INTERNAL_COMPONENT_INSTANCE__REQUIRED_HOST_INSTANCE);

		externalComponentInstanceEClass = createEClass(EXTERNAL_COMPONENT_INSTANCE);
		createEAttribute(externalComponentInstanceEClass, EXTERNAL_COMPONENT_INSTANCE__IPS);

		vmInstanceEClass = createEClass(VM_INSTANCE);
		createEAttribute(vmInstanceEClass, VM_INSTANCE__PUBLIC_ADDRESS);
		createEAttribute(vmInstanceEClass, VM_INSTANCE__CREATED_ON);
		createEAttribute(vmInstanceEClass, VM_INSTANCE__DESTROYED_ON);
		createEReference(vmInstanceEClass, VM_INSTANCE__HAS_CONFIG);
		createEReference(vmInstanceEClass, VM_INSTANCE__HAS_INFO);
		createEOperation(vmInstanceEClass, VM_INSTANCE___CHECK_DATES__VMINSTANCE);

		communicationInstanceEClass = createEClass(COMMUNICATION_INSTANCE);
		createEReference(communicationInstanceEClass, COMMUNICATION_INSTANCE__TYPE);
		createEReference(communicationInstanceEClass, COMMUNICATION_INSTANCE__REQUIRED_COMMUNICATION_INSTANCE);
		createEReference(communicationInstanceEClass, COMMUNICATION_INSTANCE__PROVIDED_COMMUNICATION_INSTANCE);

		communicationPortInstanceEClass = createEClass(COMMUNICATION_PORT_INSTANCE);
		createEReference(communicationPortInstanceEClass, COMMUNICATION_PORT_INSTANCE__TYPE);
		createEReference(communicationPortInstanceEClass, COMMUNICATION_PORT_INSTANCE__COMPONENT_INSTANCE);

		providedCommunicationInstanceEClass = createEClass(PROVIDED_COMMUNICATION_INSTANCE);

		requiredCommunicationInstanceEClass = createEClass(REQUIRED_COMMUNICATION_INSTANCE);

		hostingInstanceEClass = createEClass(HOSTING_INSTANCE);
		createEReference(hostingInstanceEClass, HOSTING_INSTANCE__PROVIDED_HOST_INSTANCE);
		createEReference(hostingInstanceEClass, HOSTING_INSTANCE__REQUIRED_HOST_INSTANCE);
		createEReference(hostingInstanceEClass, HOSTING_INSTANCE__TYPE);

		hostingPortInstanceEClass = createEClass(HOSTING_PORT_INSTANCE);
		createEReference(hostingPortInstanceEClass, HOSTING_PORT_INSTANCE__OWNER);
		createEReference(hostingPortInstanceEClass, HOSTING_PORT_INSTANCE__TYPE);

		providedHostInstanceEClass = createEClass(PROVIDED_HOST_INSTANCE);

		requiredHostInstanceEClass = createEClass(REQUIRED_HOST_INSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ProviderPackage theProviderPackage = (ProviderPackage)EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI);
		OrganisationPackage theOrganisationPackage = (OrganisationPackage)EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI);
		CamelPackage theCamelPackage = (CamelPackage)EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		deploymentModelEClass.getESuperTypes().add(this.getCloudMLElement());
		componentEClass.getESuperTypes().add(this.getCloudMLElement());
		internalComponentEClass.getESuperTypes().add(this.getComponent());
		externalComponentEClass.getESuperTypes().add(this.getComponent());
		vmEClass.getESuperTypes().add(this.getExternalComponent());
		componentGroupEClass.getESuperTypes().add(this.getCloudMLElement());
		computationalResourceEClass.getESuperTypes().add(this.getCloudMLElement());
		communicationEClass.getESuperTypes().add(this.getCloudMLElement());
		communicationPortEClass.getESuperTypes().add(this.getCloudMLElement());
		providedCommunicationEClass.getESuperTypes().add(this.getCommunicationPort());
		requiredCommunicationEClass.getESuperTypes().add(this.getCommunicationPort());
		hostingEClass.getESuperTypes().add(this.getCloudMLElement());
		hostingPortEClass.getESuperTypes().add(this.getCloudMLElement());
		providedHostEClass.getESuperTypes().add(this.getHostingPort());
		requiredHostEClass.getESuperTypes().add(this.getHostingPort());
		imageEClass.getESuperTypes().add(this.getCloudMLElement());
		componentInstanceEClass.getESuperTypes().add(this.getCloudMLElement());
		internalComponentInstanceEClass.getESuperTypes().add(this.getComponentInstance());
		externalComponentInstanceEClass.getESuperTypes().add(this.getComponentInstance());
		vmInstanceEClass.getESuperTypes().add(this.getExternalComponentInstance());
		communicationInstanceEClass.getESuperTypes().add(this.getCloudMLElement());
		communicationPortInstanceEClass.getESuperTypes().add(this.getCloudMLElement());
		providedCommunicationInstanceEClass.getESuperTypes().add(this.getCommunicationPortInstance());
		requiredCommunicationInstanceEClass.getESuperTypes().add(this.getCommunicationPortInstance());
		hostingInstanceEClass.getESuperTypes().add(this.getCloudMLElement());
		hostingPortInstanceEClass.getESuperTypes().add(this.getCloudMLElement());
		providedHostInstanceEClass.getESuperTypes().add(this.getHostingPortInstance());
		requiredHostInstanceEClass.getESuperTypes().add(this.getHostingPortInstance());

		// Initialize classes, features, and operations; add parameters
		initEClass(cloudMLElementEClass, CloudMLElement.class, "CloudMLElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCloudMLElement_Name(), ecorePackage.getEString(), "name", null, 1, 1, CloudMLElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCloudMLElement_Properties(), theProviderPackage.getAttribute(), null, "properties", null, 0, -1, CloudMLElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCloudMLElement_Resources(), this.getComputationalResource(), null, "resources", null, 0, -1, CloudMLElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deploymentModelEClass, DeploymentModel.class, "DeploymentModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDeploymentModel_Components(), this.getComponent(), null, "components", null, 0, -1, DeploymentModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeploymentModel_Communications(), this.getCommunication(), null, "communications", null, 0, -1, DeploymentModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeploymentModel_Hostings(), this.getHosting(), null, "hostings", null, 0, -1, DeploymentModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeploymentModel_Images(), this.getImage(), null, "images", null, 0, -1, DeploymentModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeploymentModel_Providers(), theOrganisationPackage.getCloudProvider(), null, "providers", null, 0, -1, DeploymentModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeploymentModel_ComponentInstances(), this.getComponentInstance(), null, "componentInstances", null, 0, -1, DeploymentModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeploymentModel_CommunicationInstances(), this.getCommunicationInstance(), null, "communicationInstances", null, 0, -1, DeploymentModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeploymentModel_HostingInstances(), this.getHostingInstance(), null, "hostingInstances", null, 0, -1, DeploymentModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(componentEClass, Component.class, "Component", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComponent_ProvidedCommunications(), this.getProvidedCommunication(), null, "ProvidedCommunications", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_ProvidedHosts(), this.getProvidedHost(), null, "providedHosts", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(internalComponentEClass, InternalComponent.class, "InternalComponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInternalComponent_RequiredCommunications(), this.getRequiredCommunication(), null, "requiredCommunications", null, 0, -1, InternalComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInternalComponent_CompositeInternalComponents(), this.getInternalComponent(), null, "compositeInternalComponents", null, 0, -1, InternalComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInternalComponent_RequiredHost(), this.getRequiredHost(), null, "requiredHost", null, 0, 1, InternalComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getInternalComponent__Contains__InternalComponent_InternalComponent(), ecorePackage.getEBoolean(), "contains", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getInternalComponent(), "ic", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getInternalComponent(), "rc", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(externalComponentEClass, ExternalComponent.class, "ExternalComponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExternalComponent_Provider(), theOrganisationPackage.getCloudProvider(), null, "provider", null, 1, 1, ExternalComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExternalComponent_Location(), theOrganisationPackage.getLocation(), null, "location", null, 1, 1, ExternalComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vmEClass, camel.deployment.VM.class, "VM", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVM_MinRam(), ecorePackage.getEInt(), "minRam", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVM_MaxRam(), ecorePackage.getEInt(), "maxRam", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVM_RamUnit(), theCamelPackage.getStorageUnit(), null, "ramUnit", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVM_MinCores(), ecorePackage.getEInt(), "minCores", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVM_MaxCores(), ecorePackage.getEInt(), "maxCores", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVM_MinStorage(), ecorePackage.getEInt(), "minStorage", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVM_MaxStorage(), ecorePackage.getEInt(), "maxStorage", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVM_StorageUnit(), theCamelPackage.getStorageUnit(), null, "storageUnit", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVM_MinCPU(), ecorePackage.getEDouble(), "minCPU", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVM_MaxCPU(), ecorePackage.getEDouble(), "maxCPU", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVM_Os(), ecorePackage.getEString(), "os", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVM_Is64os(), ecorePackage.getEBoolean(), "is64os", "true", 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVM_VmType(), theCamelPackage.getVMType(), null, "vmType", null, 0, 1, camel.deployment.VM.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(componentGroupEClass, ComponentGroup.class, "ComponentGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComponentGroup_Id(), ecorePackage.getEString(), "id", null, 1, 1, ComponentGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(computationalResourceEClass, ComputationalResource.class, "ComputationalResource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComputationalResource_DownloadCommand(), ecorePackage.getEString(), "downloadCommand", null, 0, 1, ComputationalResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComputationalResource_UploadCommand(), ecorePackage.getEString(), "uploadCommand", null, 0, 1, ComputationalResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComputationalResource_InstallCommand(), ecorePackage.getEString(), "installCommand", null, 0, 1, ComputationalResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComputationalResource_ConfigureCommand(), ecorePackage.getEString(), "configureCommand", null, 0, 1, ComputationalResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComputationalResource_StartCommand(), ecorePackage.getEString(), "startCommand", null, 0, 1, ComputationalResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComputationalResource_StopCommand(), ecorePackage.getEString(), "stopCommand", null, 0, 1, ComputationalResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(communicationEClass, Communication.class, "Communication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCommunication_RequiredCommunication(), this.getRequiredCommunication(), null, "requiredCommunication", null, 1, 1, Communication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommunication_ProvidedCommunication(), this.getProvidedCommunication(), null, "providedCommunication", null, 1, 1, Communication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommunication_RequiredPortResource(), this.getComputationalResource(), null, "requiredPortResource", null, 0, 1, Communication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommunication_ProvidedPortResource(), this.getComputationalResource(), null, "providedPortResource", null, 0, 1, Communication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommunication_OfDataObject(), theCamelPackage.getDataObject(), null, "ofDataObject", null, 0, 1, Communication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(communicationPortEClass, CommunicationPort.class, "CommunicationPort", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCommunicationPort_Component(), this.getComponent(), null, "component", null, 1, 1, CommunicationPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommunicationPort_IsLocal(), ecorePackage.getEBoolean(), "isLocal", null, 1, 1, CommunicationPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommunicationPort_PortNumber(), ecorePackage.getEInt(), "portNumber", null, 0, 1, CommunicationPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(providedCommunicationEClass, ProvidedCommunication.class, "ProvidedCommunication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(requiredCommunicationEClass, RequiredCommunication.class, "RequiredCommunication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRequiredCommunication_IsMandatory(), ecorePackage.getEBoolean(), "isMandatory", null, 1, 1, RequiredCommunication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(hostingEClass, Hosting.class, "Hosting", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHosting_ProvidedHost(), this.getProvidedHost(), null, "providedHost", null, 1, 1, Hosting.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHosting_RequiredHost(), this.getRequiredHost(), null, "requiredHost", null, 1, 1, Hosting.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHosting_RequiredHostResource(), this.getComputationalResource(), null, "requiredHostResource", null, 0, 1, Hosting.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHosting_ProvidedHostResource(), this.getComputationalResource(), null, "providedHostResource", null, 0, 1, Hosting.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(hostingPortEClass, HostingPort.class, "HostingPort", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHostingPort_Owner(), this.getComponent(), null, "owner", null, 1, 1, HostingPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(providedHostEClass, ProvidedHost.class, "ProvidedHost", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProvidedHost_Offers(), theProviderPackage.getAttribute(), null, "offers", null, 0, -1, ProvidedHost.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(requiredHostEClass, RequiredHost.class, "RequiredHost", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRequiredHost_Demands(), theProviderPackage.getAttribute(), null, "demands", null, 0, -1, RequiredHost.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(imageEClass, Image.class, "Image", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImage_Os(), ecorePackage.getEString(), "os", null, 0, 1, Image.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImage_Is64os(), ecorePackage.getEBoolean(), "is64os", "true", 0, 1, Image.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImage_ImageId(), ecorePackage.getEString(), "imageId", null, 0, 1, Image.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImage_ImageGroup(), ecorePackage.getEString(), "imageGroup", null, 0, 1, Image.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(componentInstanceEClass, ComponentInstance.class, "ComponentInstance", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComponentInstance_ProvidedCommunicationInstances(), this.getProvidedCommunicationInstance(), null, "ProvidedCommunicationInstances", null, 0, -1, ComponentInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentInstance_Type(), this.getComponent(), null, "type", null, 1, 1, ComponentInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentInstance_ProvidedHostInstances(), this.getProvidedHostInstance(), null, "providedHostInstances", null, 0, -1, ComponentInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(internalComponentInstanceEClass, InternalComponentInstance.class, "InternalComponentInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInternalComponentInstance_RequiredCommunicationInstances(), this.getRequiredCommunicationInstance(), null, "requiredCommunicationInstances", null, 0, -1, InternalComponentInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInternalComponentInstance_RequiredHostInstance(), this.getRequiredHostInstance(), null, "requiredHostInstance", null, 0, 1, InternalComponentInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(externalComponentInstanceEClass, ExternalComponentInstance.class, "ExternalComponentInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExternalComponentInstance_Ips(), ecorePackage.getEString(), "ips", null, 0, -1, ExternalComponentInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vmInstanceEClass, VMInstance.class, "VMInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVMInstance_PublicAddress(), ecorePackage.getEString(), "publicAddress", null, 0, 1, VMInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVMInstance_CreatedOn(), ecorePackage.getEDate(), "createdOn", null, 0, 1, VMInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVMInstance_DestroyedOn(), ecorePackage.getEDate(), "destroyedOn", null, 0, 1, VMInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVMInstance_HasConfig(), this.getImage(), null, "hasConfig", null, 1, 1, VMInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVMInstance_HasInfo(), theCamelPackage.getVMInfo(), null, "hasInfo", null, 1, 1, VMInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getVMInstance__CheckDates__VMInstance(), ecorePackage.getEBoolean(), "checkDates", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVMInstance(), "vm", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(communicationInstanceEClass, CommunicationInstance.class, "CommunicationInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCommunicationInstance_Type(), this.getCommunication(), null, "type", null, 1, 1, CommunicationInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommunicationInstance_RequiredCommunicationInstance(), this.getRequiredCommunicationInstance(), null, "requiredCommunicationInstance", null, 1, 1, CommunicationInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommunicationInstance_ProvidedCommunicationInstance(), this.getProvidedCommunicationInstance(), null, "providedCommunicationInstance", null, 1, 1, CommunicationInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(communicationPortInstanceEClass, CommunicationPortInstance.class, "CommunicationPortInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCommunicationPortInstance_Type(), this.getCommunicationPort(), null, "type", null, 1, 1, CommunicationPortInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommunicationPortInstance_ComponentInstance(), this.getComponentInstance(), null, "componentInstance", null, 1, 1, CommunicationPortInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(providedCommunicationInstanceEClass, ProvidedCommunicationInstance.class, "ProvidedCommunicationInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(requiredCommunicationInstanceEClass, RequiredCommunicationInstance.class, "RequiredCommunicationInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(hostingInstanceEClass, HostingInstance.class, "HostingInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHostingInstance_ProvidedHostInstance(), this.getProvidedHostInstance(), null, "providedHostInstance", null, 1, 1, HostingInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHostingInstance_RequiredHostInstance(), this.getRequiredHostInstance(), null, "requiredHostInstance", null, 1, 1, HostingInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHostingInstance_Type(), this.getHosting(), null, "type", null, 1, 1, HostingInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(hostingPortInstanceEClass, HostingPortInstance.class, "HostingPortInstance", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHostingPortInstance_Owner(), this.getComponentInstance(), null, "owner", null, 1, 1, HostingPortInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHostingPortInstance_Type(), this.getHostingPort(), null, "type", null, 1, 1, HostingPortInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(providedHostInstanceEClass, ProvidedHostInstance.class, "ProvidedHostInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(requiredHostInstanceEClass, RequiredHostInstance.class, "RequiredHostInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot
		createPivotAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";		
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "invocationDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
			 "settingDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
			 "validationDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot"
		   });		
		addAnnotation
		  (cloudMLElementEClass, 
		   source, 
		   new String[] {
			 "constraints", "different_properties_in_CloudMLElement"
		   });				
		addAnnotation
		  (componentEClass, 
		   source, 
		   new String[] {
			 "constraints", "provided_component_ports_should_point_to_component"
		   });			
		addAnnotation
		  (internalComponentEClass, 
		   source, 
		   new String[] {
			 "constraints", "recursion_in_parts_of_component requiredHost_owner_is_self requiredCommunications_owner_is_self"
		   });				
		addAnnotation
		  (vmEClass, 
		   source, 
		   new String[] {
			 "constraints", "one_alternative_provided correct_input"
		   });			
		addAnnotation
		  (componentInstanceEClass, 
		   source, 
		   new String[] {
			 "constraints", "component_instance_ports_belong_to_instance component_port_instances_of_correct_type"
		   });			
		addAnnotation
		  (internalComponentInstanceEClass, 
		   source, 
		   new String[] {
			 "constraints", "internal_component_instance_ports_belong_to_instance internal_component_port_instances_of_correct_type"
		   });			
		addAnnotation
		  (vmInstanceEClass, 
		   source, 
		   new String[] {
			 "constraints", "correct_type_for_vm_instance vm_instance_type_map_to_VMInfo"
		   });				
		addAnnotation
		  (communicationInstanceEClass, 
		   source, 
		   new String[] {
			 "constraints", "communication_instance_correct_port_instances"
		   });			
		addAnnotation
		  (hostingInstanceEClass, 
		   source, 
		   new String[] {
			 "constraints", "containment_instance_correct_port_instance"
		   });	
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createPivotAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot";					
		addAnnotation
		  (cloudMLElementEClass, 
		   source, 
		   new String[] {
			 "different_properties_in_CloudMLElement", "\n\t\t\t\t\t\tproperties->forAll(p1, p2 | p1 <> p2 implies p1.name <> p2.name)"
		   });			
		addAnnotation
		  (componentEClass, 
		   source, 
		   new String[] {
			 "provided_component_ports_should_point_to_component", "\n\t\t\t\t\t\t\t\tProvidedCommunications->forAll(p | p.component = self) \n\t\t\t\t\t\t\t\tand\n\t\t\t\t\t\t\t\tprovidedHosts->forAll(p | p.owner = self)"
		   });			
		addAnnotation
		  (internalComponentEClass, 
		   source, 
		   new String[] {
			 "recursion_in_parts_of_component", "\n\t\t\t\t\t\t\t\tnot(self.contains(self,self))",
			 "requiredHost_owner_is_self", "\n\t\t\t\t\t\t\t\trequiredHost.owner = self",
			 "requiredCommunications_owner_is_self", "\n\t\t\t\t\t\t\t\trequiredCommunications->forAll(p | p.component = self)"
		   });		
		addAnnotation
		  (getInternalComponent__Contains__InternalComponent_InternalComponent(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\t\t\t\tic.compositeInternalComponents->exists(p | p.name = rc.name or p.contains(p,rc))"
		   });			
		addAnnotation
		  (vmEClass, 
		   source, 
		   new String[] {
			 "one_alternative_provided", "\n\t\t\t\t\t\t\t\t(vmType <> null and (minRam = 0 and maxRam = 0 and ramUnit = null and minStorage = 0 and maxStorage = 0 and storageUnit = null and minCPU = 0 and maxCPU = 0 and minCores = 0 and maxCores = 0)) or (vmType = null and (((minRam > 0 or maxRam > 0) and ramUnit <> null) or ((minStorage > 0 or maxStorage > 0) and storageUnit <> null) or (minCPU > 0 or maxCPU > 0) or (minCores > 0 or maxCores > 0)))",
			 "correct_input", "\n\t\t\t\t\t\tminRam >= 0 and maxRam >= 0 and minCores >= 0 and maxCores >= 0 and minStorage >= 0 and maxStorage >= 0 and minCPU >= 0 and maxCPU >= 0"
		   });			
		addAnnotation
		  (componentInstanceEClass, 
		   source, 
		   new String[] {
			 "component_instance_ports_belong_to_instance", "\n\t\t\t\t\t\t\t\tProvidedCommunicationInstances->forAll(p | p.componentInstance = self)\n\t\t\t\t\t\t\t\tand\n\t\t\t\t\t\t\t\tprovidedHostInstances->forAll(p | p.owner = self)",
			 "component_port_instances_of_correct_type", "\n\t\t\t\t\t\tProvidedCommunicationInstances->forAll(p | type.ProvidedCommunications->includes(p.type))\n\t\t\t\t\t\tand\n\t\t\t\t\t\tprovidedHostInstances->forAll(p | type.providedHosts->includes(p.type))"
		   });			
		addAnnotation
		  (internalComponentInstanceEClass, 
		   source, 
		   new String[] {
			 "internal_component_instance_ports_belong_to_instance", "\n\t\t\t\t\t\t\t\trequiredCommunicationInstances->forAll(p | p.componentInstance = self)\n\t\t\t\t\t\t\t\tand\n\t\t\t\t\t\t\t\trequiredHostInstance.owner = self",
			 "internal_component_port_instances_of_correct_type", "\n\t\t\t\t\t\ttype.oclIsKindOf(InternalComponent)\n\t\t\t\t\t\tand \n\t\t\t\t\t\trequiredCommunicationInstances->forAll(p | type.oclAsType(InternalComponent).requiredCommunications->includes(p.type))\n\t\t\t\t\t\tand\n\t\t\t\t\t\trequiredHostInstance.type = type.oclAsType(InternalComponent).requiredHost"
		   });			
		addAnnotation
		  (vmInstanceEClass, 
		   source, 
		   new String[] {
			 "correct_type_for_vm_instance", "\n\t\t\t\t\t\t\t\ttype.oclIsTypeOf(VM)",
			 "vm_instance_type_map_to_VMInfo", "\n\t\t\t\t\t\t\t\t(type.oclIsTypeOf(VM) and type.oclAsType(VM).vmType <> null) implies hasInfo.ofVM = type.oclAsType(VM).vmType"
		   });				
		addAnnotation
		  (communicationInstanceEClass, 
		   source, 
		   new String[] {
			 "communication_instance_correct_port_instances", "\n\t\t\t\t\t\trequiredCommunicationInstance.type = type.requiredCommunication\n\t\t\t\t\t\tand\n\t\t\t\t\t\tprovidedCommunicationInstance.type = type.providedCommunication"
		   });			
		addAnnotation
		  (hostingInstanceEClass, 
		   source, 
		   new String[] {
			 "containment_instance_correct_port_instance", "\n\t\t\t\t\t\tprovidedHostInstance.type = type.providedHost\n\t\t\t\t\t\tand\n\t\t\t\t\t\trequiredHostInstance.type = type.requiredHost"
		   });
	}

} //DeploymentPackageImpl
