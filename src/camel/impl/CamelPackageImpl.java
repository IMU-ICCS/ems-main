/**
 */
package camel.impl;

import camel.Action;
import camel.ActionType;
import camel.Application;
import camel.CamelFactory;
import camel.CamelModel;
import camel.CamelPackage;
import camel.DataObject;
import camel.MonetaryUnit;
import camel.PhysicalNode;
import camel.RequestUnit;
import camel.Requirement;
import camel.RequirementGroup;
import camel.RequirementOperatorType;
import camel.StorageUnit;
import camel.ThroughputUnit;
import camel.TimeIntervalUnit;
import camel.Unit;
import camel.UnitDimensionType;
import camel.UnitType;
import camel.Unitless;
import camel.VMInfo;
import camel.VMType;

import camel.deployment.DeploymentPackage;

import camel.deployment.impl.DeploymentPackageImpl;

import camel.execution.ExecutionPackage;

import camel.execution.impl.ExecutionPackageImpl;

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

import camel.util.CamelValidator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
public class CamelPackageImpl extends EPackageImpl implements CamelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass camelModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass physicalNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vmTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requirementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requirementGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass monetaryUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requestUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass storageUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass throughputUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeIntervalUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unitlessEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vmInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum actionTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum requirementOperatorTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum unitDimensionTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum unitTypeEEnum = null;

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
	 * @see camel.CamelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CamelPackageImpl() {
		super(eNS_URI, CamelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CamelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CamelPackage init() {
		if (isInited) return (CamelPackage)EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI);

		// Obtain or create and register package
		CamelPackageImpl theCamelPackage = (CamelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CamelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CamelPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		DeploymentPackageImpl theDeploymentPackage = (DeploymentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) instanceof DeploymentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) : DeploymentPackage.eINSTANCE);
		ExecutionPackageImpl theExecutionPackage = (ExecutionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) instanceof ExecutionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) : ExecutionPackage.eINSTANCE);
		OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) : OrganisationPackage.eINSTANCE);
		ProviderPackageImpl theProviderPackage = (ProviderPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) instanceof ProviderPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) : ProviderPackage.eINSTANCE);
		ScalabilityPackageImpl theScalabilityPackage = (ScalabilityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) instanceof ScalabilityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) : ScalabilityPackage.eINSTANCE);
		SecurityPackageImpl theSecurityPackage = (SecurityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) instanceof SecurityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) : SecurityPackage.eINSTANCE);
		SlaPackageImpl theSlaPackage = (SlaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) instanceof SlaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) : SlaPackage.eINSTANCE);
		TypePackageImpl theTypePackage = (TypePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) instanceof TypePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) : TypePackage.eINSTANCE);

		// Create package meta-data objects
		theCamelPackage.createPackageContents();
		theDeploymentPackage.createPackageContents();
		theExecutionPackage.createPackageContents();
		theOrganisationPackage.createPackageContents();
		theProviderPackage.createPackageContents();
		theScalabilityPackage.createPackageContents();
		theSecurityPackage.createPackageContents();
		theSlaPackage.createPackageContents();
		theTypePackage.createPackageContents();

		// Initialize created meta-data
		theCamelPackage.initializePackageContents();
		theDeploymentPackage.initializePackageContents();
		theExecutionPackage.initializePackageContents();
		theOrganisationPackage.initializePackageContents();
		theProviderPackage.initializePackageContents();
		theScalabilityPackage.initializePackageContents();
		theSecurityPackage.initializePackageContents();
		theSlaPackage.initializePackageContents();
		theTypePackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theCamelPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return CamelValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theCamelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CamelPackage.eNS_URI, theCamelPackage);
		return theCamelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCamelModel() {
		return camelModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_OrganisationModels() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_ScalabilityModels() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_ProviderModels() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_SecurityModels() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_DeploymentModels() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_ExecutionModels() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_TypeRepositories() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_AgreementModels() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_Units() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_Actions() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_Nodes() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_Objects() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_Requirements() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_Policies() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_Applications() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_VmInfos() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCamelModel_VmType() {
		return (EReference)camelModelEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAction() {
		return actionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAction_Name() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAction_Type() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplication() {
		return applicationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApplication_Name() {
		return (EAttribute)applicationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApplication_Version() {
		return (EAttribute)applicationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplication_Owner() {
		return (EReference)applicationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplication_DeploymentModels() {
		return (EReference)applicationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataObject() {
		return dataObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataObject_Replication() {
		return (EAttribute)dataObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataObject_Partitioning() {
		return (EAttribute)dataObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataObject_Consistency() {
		return (EAttribute)dataObjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataObject_Name() {
		return (EAttribute)dataObjectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPhysicalNode() {
		return physicalNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPhysicalNode_IP() {
		return (EAttribute)physicalNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPhysicalNode_Hardware() {
		return (EAttribute)physicalNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPhysicalNode_InDataCenter() {
		return (EReference)physicalNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVMType() {
		return vmTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVMType_Feature() {
		return (EReference)vmTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVMType_Constraints() {
		return (EReference)vmTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVMType_Name() {
		return (EAttribute)vmTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequirement() {
		return requirementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequirement_Priority() {
		return (EAttribute)requirementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequirement_Id() {
		return (EAttribute)requirementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequirementGroup() {
		return requirementGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRequirementGroup_PosedBy() {
		return (EReference)requirementGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRequirementGroup_Requirement() {
		return (EReference)requirementGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRequirementGroup_OnApplication() {
		return (EReference)requirementGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequirementGroup_RequirementOperator() {
		return (EAttribute)requirementGroupEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRequirementGroup__CheckRecursiveness__RequirementGroup_Requirement_boolean_EList() {
		return requirementGroupEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUnit() {
		return unitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnit_DimensionType() {
		return (EAttribute)unitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnit_Unit() {
		return (EAttribute)unitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getUnit__CheckUnit() {
		return unitEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMonetaryUnit() {
		return monetaryUnitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequestUnit() {
		return requestUnitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStorageUnit() {
		return storageUnitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getThroughputUnit() {
		return throughputUnitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimeIntervalUnit() {
		return timeIntervalUnitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUnitless() {
		return unitlessEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVMInfo() {
		return vmInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVMInfo_OfVM() {
		return (EReference)vmInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVMInfo_DataCenter() {
		return (EReference)vmInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVMInfo_CostPerHour() {
		return (EAttribute)vmInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVMInfo_CostUnit() {
		return (EReference)vmInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVMInfo_BenchmarkRate() {
		return (EAttribute)vmInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVMInfo_EvaluatedOn() {
		return (EAttribute)vmInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVMInfo_ClassifiedOn() {
		return (EAttribute)vmInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVMInfo_Name() {
		return (EAttribute)vmInfoEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getActionType() {
		return actionTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getRequirementOperatorType() {
		return requirementOperatorTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getUnitDimensionType() {
		return unitDimensionTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getUnitType() {
		return unitTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CamelFactory getCamelFactory() {
		return (CamelFactory)getEFactoryInstance();
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
		camelModelEClass = createEClass(CAMEL_MODEL);
		createEReference(camelModelEClass, CAMEL_MODEL__ORGANISATION_MODELS);
		createEReference(camelModelEClass, CAMEL_MODEL__SCALABILITY_MODELS);
		createEReference(camelModelEClass, CAMEL_MODEL__PROVIDER_MODELS);
		createEReference(camelModelEClass, CAMEL_MODEL__SECURITY_MODELS);
		createEReference(camelModelEClass, CAMEL_MODEL__DEPLOYMENT_MODELS);
		createEReference(camelModelEClass, CAMEL_MODEL__EXECUTION_MODELS);
		createEReference(camelModelEClass, CAMEL_MODEL__TYPE_REPOSITORIES);
		createEReference(camelModelEClass, CAMEL_MODEL__AGREEMENT_MODELS);
		createEReference(camelModelEClass, CAMEL_MODEL__UNITS);
		createEReference(camelModelEClass, CAMEL_MODEL__ACTIONS);
		createEReference(camelModelEClass, CAMEL_MODEL__NODES);
		createEReference(camelModelEClass, CAMEL_MODEL__OBJECTS);
		createEReference(camelModelEClass, CAMEL_MODEL__REQUIREMENTS);
		createEReference(camelModelEClass, CAMEL_MODEL__POLICIES);
		createEReference(camelModelEClass, CAMEL_MODEL__APPLICATIONS);
		createEReference(camelModelEClass, CAMEL_MODEL__VM_INFOS);
		createEReference(camelModelEClass, CAMEL_MODEL__VM_TYPE);

		actionEClass = createEClass(ACTION);
		createEAttribute(actionEClass, ACTION__NAME);
		createEAttribute(actionEClass, ACTION__TYPE);

		applicationEClass = createEClass(APPLICATION);
		createEAttribute(applicationEClass, APPLICATION__NAME);
		createEAttribute(applicationEClass, APPLICATION__VERSION);
		createEReference(applicationEClass, APPLICATION__OWNER);
		createEReference(applicationEClass, APPLICATION__DEPLOYMENT_MODELS);

		dataObjectEClass = createEClass(DATA_OBJECT);
		createEAttribute(dataObjectEClass, DATA_OBJECT__REPLICATION);
		createEAttribute(dataObjectEClass, DATA_OBJECT__PARTITIONING);
		createEAttribute(dataObjectEClass, DATA_OBJECT__CONSISTENCY);
		createEAttribute(dataObjectEClass, DATA_OBJECT__NAME);

		physicalNodeEClass = createEClass(PHYSICAL_NODE);
		createEAttribute(physicalNodeEClass, PHYSICAL_NODE__IP);
		createEAttribute(physicalNodeEClass, PHYSICAL_NODE__HARDWARE);
		createEReference(physicalNodeEClass, PHYSICAL_NODE__IN_DATA_CENTER);

		vmTypeEClass = createEClass(VM_TYPE);
		createEReference(vmTypeEClass, VM_TYPE__FEATURE);
		createEReference(vmTypeEClass, VM_TYPE__CONSTRAINTS);
		createEAttribute(vmTypeEClass, VM_TYPE__NAME);

		requirementEClass = createEClass(REQUIREMENT);
		createEAttribute(requirementEClass, REQUIREMENT__PRIORITY);
		createEAttribute(requirementEClass, REQUIREMENT__ID);

		requirementGroupEClass = createEClass(REQUIREMENT_GROUP);
		createEReference(requirementGroupEClass, REQUIREMENT_GROUP__POSED_BY);
		createEReference(requirementGroupEClass, REQUIREMENT_GROUP__REQUIREMENT);
		createEReference(requirementGroupEClass, REQUIREMENT_GROUP__ON_APPLICATION);
		createEAttribute(requirementGroupEClass, REQUIREMENT_GROUP__REQUIREMENT_OPERATOR);
		createEOperation(requirementGroupEClass, REQUIREMENT_GROUP___CHECK_RECURSIVENESS__REQUIREMENTGROUP_REQUIREMENT_BOOLEAN_ELIST);

		unitEClass = createEClass(UNIT);
		createEAttribute(unitEClass, UNIT__DIMENSION_TYPE);
		createEAttribute(unitEClass, UNIT__UNIT);
		createEOperation(unitEClass, UNIT___CHECK_UNIT);

		monetaryUnitEClass = createEClass(MONETARY_UNIT);

		requestUnitEClass = createEClass(REQUEST_UNIT);

		storageUnitEClass = createEClass(STORAGE_UNIT);

		throughputUnitEClass = createEClass(THROUGHPUT_UNIT);

		timeIntervalUnitEClass = createEClass(TIME_INTERVAL_UNIT);

		unitlessEClass = createEClass(UNITLESS);

		vmInfoEClass = createEClass(VM_INFO);
		createEReference(vmInfoEClass, VM_INFO__OF_VM);
		createEReference(vmInfoEClass, VM_INFO__DATA_CENTER);
		createEAttribute(vmInfoEClass, VM_INFO__COST_PER_HOUR);
		createEReference(vmInfoEClass, VM_INFO__COST_UNIT);
		createEAttribute(vmInfoEClass, VM_INFO__BENCHMARK_RATE);
		createEAttribute(vmInfoEClass, VM_INFO__EVALUATED_ON);
		createEAttribute(vmInfoEClass, VM_INFO__CLASSIFIED_ON);
		createEAttribute(vmInfoEClass, VM_INFO__NAME);

		// Create enums
		actionTypeEEnum = createEEnum(ACTION_TYPE);
		requirementOperatorTypeEEnum = createEEnum(REQUIREMENT_OPERATOR_TYPE);
		unitDimensionTypeEEnum = createEEnum(UNIT_DIMENSION_TYPE);
		unitTypeEEnum = createEEnum(UNIT_TYPE);
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
		DeploymentPackage theDeploymentPackage = (DeploymentPackage)EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI);
		ExecutionPackage theExecutionPackage = (ExecutionPackage)EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI);
		OrganisationPackage theOrganisationPackage = (OrganisationPackage)EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI);
		ProviderPackage theProviderPackage = (ProviderPackage)EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI);
		ScalabilityPackage theScalabilityPackage = (ScalabilityPackage)EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI);
		SecurityPackage theSecurityPackage = (SecurityPackage)EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI);
		SlaPackage theSlaPackage = (SlaPackage)EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI);
		TypePackage theTypePackage = (TypePackage)EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theDeploymentPackage);
		getESubpackages().add(theExecutionPackage);
		getESubpackages().add(theOrganisationPackage);
		getESubpackages().add(theProviderPackage);
		getESubpackages().add(theScalabilityPackage);
		getESubpackages().add(theSecurityPackage);
		getESubpackages().add(theSlaPackage);
		getESubpackages().add(theTypePackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		applicationEClass.getESuperTypes().add(theOrganisationPackage.getResource());
		dataObjectEClass.getESuperTypes().add(theOrganisationPackage.getResource());
		physicalNodeEClass.getESuperTypes().add(theOrganisationPackage.getResource());
		vmTypeEClass.getESuperTypes().add(theOrganisationPackage.getResource());
		requirementGroupEClass.getESuperTypes().add(this.getRequirement());
		monetaryUnitEClass.getESuperTypes().add(this.getUnit());
		requestUnitEClass.getESuperTypes().add(this.getUnit());
		storageUnitEClass.getESuperTypes().add(this.getUnit());
		throughputUnitEClass.getESuperTypes().add(this.getUnit());
		timeIntervalUnitEClass.getESuperTypes().add(this.getUnit());
		unitlessEClass.getESuperTypes().add(this.getUnit());

		// Initialize classes, features, and operations; add parameters
		initEClass(camelModelEClass, CamelModel.class, "CamelModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCamelModel_OrganisationModels(), theOrganisationPackage.getOrganisationModel(), null, "organisationModels", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_ScalabilityModels(), theScalabilityPackage.getScalabilityModel(), null, "scalabilityModels", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_ProviderModels(), theProviderPackage.getProviderModel(), null, "providerModels", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_SecurityModels(), theSecurityPackage.getSecurityModel(), null, "securityModels", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_DeploymentModels(), theDeploymentPackage.getDeploymentModel(), null, "deploymentModels", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_ExecutionModels(), theExecutionPackage.getExecutionModel(), null, "executionModels", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_TypeRepositories(), theTypePackage.getTypeRepository(), null, "typeRepositories", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_AgreementModels(), theSlaPackage.getAgreementType(), null, "agreementModels", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_Units(), this.getUnit(), null, "units", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_Actions(), this.getAction(), null, "actions", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_Nodes(), this.getPhysicalNode(), null, "nodes", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_Objects(), this.getDataObject(), null, "objects", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_Requirements(), this.getRequirement(), null, "requirements", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_Policies(), theScalabilityPackage.getScalabilityPolicy(), null, "policies", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_Applications(), this.getApplication(), null, "applications", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_VmInfos(), this.getVMInfo(), null, "vmInfos", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCamelModel_VmType(), this.getVMType(), null, "vmType", null, 0, -1, CamelModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(actionEClass, Action.class, "Action", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAction_Name(), ecorePackage.getEString(), "name", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAction_Type(), this.getActionType(), "type", null, 1, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(applicationEClass, Application.class, "Application", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getApplication_Name(), ecorePackage.getEString(), "name", null, 1, 1, Application.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getApplication_Version(), ecorePackage.getEString(), "version", null, 1, 1, Application.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplication_Owner(), theOrganisationPackage.getEntity(), null, "owner", null, 1, 1, Application.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplication_DeploymentModels(), theDeploymentPackage.getDeploymentModel(), null, "deploymentModels", null, 0, -1, Application.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(dataObjectEClass, DataObject.class, "DataObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDataObject_Replication(), ecorePackage.getEString(), "replication", null, 0, 1, DataObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataObject_Partitioning(), ecorePackage.getEString(), "partitioning", null, 0, 1, DataObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataObject_Consistency(), ecorePackage.getEString(), "consistency", null, 0, 1, DataObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataObject_Name(), ecorePackage.getEString(), "name", null, 1, 1, DataObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(physicalNodeEClass, PhysicalNode.class, "PhysicalNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPhysicalNode_IP(), ecorePackage.getEString(), "IP", null, 0, 1, PhysicalNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPhysicalNode_Hardware(), ecorePackage.getEString(), "hardware", null, 0, 1, PhysicalNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPhysicalNode_InDataCenter(), theOrganisationPackage.getDataCenter(), null, "inDataCenter", null, 1, 1, PhysicalNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vmTypeEClass, VMType.class, "VMType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVMType_Feature(), theProviderPackage.getFeature(), null, "feature", null, 1, 1, VMType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVMType_Constraints(), theProviderPackage.getConstraint(), null, "constraints", null, 1, -1, VMType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVMType_Name(), ecorePackage.getEString(), "name", null, 0, 1, VMType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(requirementEClass, Requirement.class, "Requirement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRequirement_Priority(), ecorePackage.getEDouble(), "priority", null, 0, 1, Requirement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequirement_Id(), ecorePackage.getEString(), "id", null, 1, 1, Requirement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(requirementGroupEClass, RequirementGroup.class, "RequirementGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRequirementGroup_PosedBy(), theOrganisationPackage.getUser(), null, "posedBy", null, 1, 1, RequirementGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRequirementGroup_Requirement(), this.getRequirement(), null, "requirement", null, 1, -1, RequirementGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRequirementGroup_OnApplication(), this.getApplication(), null, "onApplication", null, 0, -1, RequirementGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequirementGroup_RequirementOperator(), this.getRequirementOperatorType(), "requirementOperator", null, 1, 1, RequirementGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getRequirementGroup__CheckRecursiveness__RequirementGroup_Requirement_boolean_EList(), ecorePackage.getEBoolean(), "checkRecursiveness", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRequirementGroup(), "rg1", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRequirement(), "r", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "resources", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRequirementGroup(), "context", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(unitEClass, Unit.class, "Unit", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUnit_DimensionType(), this.getUnitDimensionType(), "dimensionType", null, 1, 1, Unit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnit_Unit(), this.getUnitType(), "unit", null, 1, 1, Unit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getUnit__CheckUnit(), ecorePackage.getEBoolean(), "checkUnit", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(monetaryUnitEClass, MonetaryUnit.class, "MonetaryUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(requestUnitEClass, RequestUnit.class, "RequestUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(storageUnitEClass, StorageUnit.class, "StorageUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(throughputUnitEClass, ThroughputUnit.class, "ThroughputUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(timeIntervalUnitEClass, TimeIntervalUnit.class, "TimeIntervalUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(unitlessEClass, Unitless.class, "Unitless", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(vmInfoEClass, VMInfo.class, "VMInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVMInfo_OfVM(), this.getVMType(), null, "ofVM", null, 1, 1, VMInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVMInfo_DataCenter(), theOrganisationPackage.getDataCenter(), null, "dataCenter", null, 0, 1, VMInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVMInfo_CostPerHour(), ecorePackage.getEDouble(), "costPerHour", null, 0, 1, VMInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVMInfo_CostUnit(), this.getMonetaryUnit(), null, "costUnit", null, 1, 1, VMInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVMInfo_BenchmarkRate(), ecorePackage.getEDouble(), "benchmarkRate", null, 0, 1, VMInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVMInfo_EvaluatedOn(), ecorePackage.getEDate(), "evaluatedOn", null, 1, 1, VMInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVMInfo_ClassifiedOn(), ecorePackage.getEDate(), "classifiedOn", null, 1, 1, VMInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVMInfo_Name(), ecorePackage.getEString(), "name", null, 1, 1, VMInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(actionTypeEEnum, ActionType.class, "ActionType");
		addEEnumLiteral(actionTypeEEnum, ActionType.CREATES);
		addEEnumLiteral(actionTypeEEnum, ActionType.SCALE_IN);
		addEEnumLiteral(actionTypeEEnum, ActionType.SCALE_OUT);
		addEEnumLiteral(actionTypeEEnum, ActionType.SCALE_UP);
		addEEnumLiteral(actionTypeEEnum, ActionType.SCALE_DOWN);

		initEEnum(requirementOperatorTypeEEnum, RequirementOperatorType.class, "RequirementOperatorType");
		addEEnumLiteral(requirementOperatorTypeEEnum, RequirementOperatorType.AND);
		addEEnumLiteral(requirementOperatorTypeEEnum, RequirementOperatorType.OR);

		initEEnum(unitDimensionTypeEEnum, UnitDimensionType.class, "UnitDimensionType");
		addEEnumLiteral(unitDimensionTypeEEnum, UnitDimensionType.TIME_INTERVAL);
		addEEnumLiteral(unitDimensionTypeEEnum, UnitDimensionType.STORAGE);
		addEEnumLiteral(unitDimensionTypeEEnum, UnitDimensionType.COST);
		addEEnumLiteral(unitDimensionTypeEEnum, UnitDimensionType.THROUGHPUT);
		addEEnumLiteral(unitDimensionTypeEEnum, UnitDimensionType.REQUEST_NUM);
		addEEnumLiteral(unitDimensionTypeEEnum, UnitDimensionType.UNITLESS);

		initEEnum(unitTypeEEnum, UnitType.class, "UnitType");
		addEEnumLiteral(unitTypeEEnum, UnitType.BYTES);
		addEEnumLiteral(unitTypeEEnum, UnitType.KILOBYTES);
		addEEnumLiteral(unitTypeEEnum, UnitType.GIGABYTES);
		addEEnumLiteral(unitTypeEEnum, UnitType.MEGABYTES);
		addEEnumLiteral(unitTypeEEnum, UnitType.EUROS);
		addEEnumLiteral(unitTypeEEnum, UnitType.DOLLARS);
		addEEnumLiteral(unitTypeEEnum, UnitType.POUNDS);
		addEEnumLiteral(unitTypeEEnum, UnitType.MILLISECONDS);
		addEEnumLiteral(unitTypeEEnum, UnitType.SECONDS);
		addEEnumLiteral(unitTypeEEnum, UnitType.MINUTES);
		addEEnumLiteral(unitTypeEEnum, UnitType.HOURS);
		addEEnumLiteral(unitTypeEEnum, UnitType.DAYS);
		addEEnumLiteral(unitTypeEEnum, UnitType.WEEKS);
		addEEnumLiteral(unitTypeEEnum, UnitType.MONTHS);
		addEEnumLiteral(unitTypeEEnum, UnitType.REQUESTS);
		addEEnumLiteral(unitTypeEEnum, UnitType.REQUESTS_PER_SECOND);
		addEEnumLiteral(unitTypeEEnum, UnitType.BYTES_PER_SECOND);
		addEEnumLiteral(unitTypeEEnum, UnitType.PERCENTAGE);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/OCL/Import
		createImportAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot
		createPivotAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/OCL/Import</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createImportAnnotations() {
		String source = "http://www.eclipse.org/OCL/Import";		
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "ecore", "http://www.eclipse.org/emf/2002/Ecore#/"
		   });																
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
		  (actionEClass, 
		   source, 
		   new String[] {
			 "constraints", "correct_action_type"
		   });			
		addAnnotation
		  (vmTypeEClass, 
		   source, 
		   new String[] {
			 "constraints", "VMType_one_feature_plus_its_attrs"
		   });			
		addAnnotation
		  (requirementEClass, 
		   source, 
		   new String[] {
			 "constraints", "non_negative_priorities_for_requirement"
		   });			
		addAnnotation
		  (requirementGroupEClass, 
		   source, 
		   new String[] {
			 "constraints", "RequirementGroup_Members_Posed_By_Same_User applications_in_sub_groups_in_group requirement_group_no_conflict_policies requirements_in_group_refer_to_group_applications"
		   });				
		addAnnotation
		  (unitEClass, 
		   source, 
		   new String[] {
			 "constraints", "correctUnit"
		   });					
		addAnnotation
		  (vmInfoEClass, 
		   source, 
		   new String[] {
			 "constraints", "correct_param_vals"
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
		  (actionEClass, 
		   source, 
		   new String[] {
			 "correct_action_type", "\n\t\t\t\t\t\t\tif (self.oclIsTypeOf(camel::scalability::ScalingAction)) then (self.type = ActionType::SCALE_IN or self.type = ActionType::SCALE_OUT or self.type = ActionType::SCALE_UP or self.type = ActionType::SCALE_DOWN) \n\t\t\t\t\t\t\telse not(self.type = ActionType::SCALE_IN or self.type = ActionType::SCALE_OUT or self.type = ActionType::SCALE_UP or self.type = ActionType::SCALE_DOWN) endif"
		   });			
		addAnnotation
		  (vmTypeEClass, 
		   source, 
		   new String[] {
			 "VMType_one_feature_plus_its_attrs", "\n\t\t\t\t\t\t\tconstraints->forAll(p | p.from = feature and p.to = feature)"
		   });			
		addAnnotation
		  (requirementEClass, 
		   source, 
		   new String[] {
			 "non_negative_priorities_for_requirement", "\n\t\t\t\t\tself.priority >= 0.0"
		   });			
		addAnnotation
		  (requirementGroupEClass, 
		   source, 
		   new String[] {
			 "RequirementGroup_Members_Posed_By_Same_User", "\n\t\t\t\t\t\t\tself.requirement-> forAll(p | (p.oclIsTypeOf(RequirementGroup) implies p.oclAsType(RequirementGroup).posedBy = self.posedBy) and (not(p.oclIsTypeOf(RequirementGroup)) implies self.posedBy.hasRequirement->includes(p)))",
			 "applications_in_sub_groups_in_group", "\n\t\t\t\t\t\t\tself.requirement->forAll(p | p.oclIsTypeOf(RequirementGroup) implies p.oclAsType(RequirementGroup).onApplication->forAll(a | self.onApplication->includes(a)))",
			 "requirement_group_no_conflict_policies", "\n\t\t\t\t\t\t\t\tif (self.requirementOperator = RequirementOperatorType::AND) then self.requirement->forAll(p1, p2 | (p1 <> p2 and p1.oclIsKindOf(camel::scalability::ScalabilityPolicy) and p2.oclIsKindOf(camel::scalability::ScalabilityPolicy) and p1.oclType() = p2.oclType()) implies\n\t\t\t\t\t\t\t\t\tif (p1.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy)) then p1.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm <> p2.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm\n\t\t\t\t\t\t\t\t\telse p1.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component <> p2.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t) else true endif",
			 "requirements_in_group_refer_to_group_applications", "\n\t\t\t\t\t\t\t\tif (onApplication->notEmpty()) then\n\t\t\t\t\t\t\t\t\trequirement->forAll(p | p.oclIsTypeOf(camel::sla::ServiceLevelObjectiveType) implies onApplication->includes(p.oclAsType(camel::sla::ServiceLevelObjectiveType).customServiceLevel.metric.objectBinding.executionContext.ofApplication))\n\t\t\t\t\t\t\t\telse true\n\t\t\t\t\t\t\t\tendif"
		   });				
		addAnnotation
		  (unitEClass, 
		   source, 
		   new String[] {
			 "correctUnit", "\n\t\t\t\t\t\tcheckUnit()"
		   });		
		addAnnotation
		  (getUnit__CheckUnit(), 
		   source, 
		   new String[] {
			 "body", "if (dimensionType = UnitDimensionType::TIME_INTERVAL) then \n\t\t\t\t\t\t\t\tif (unit = UnitType::MILLISECONDS or unit = UnitType::SECONDS or unit = UnitType::MINUTES or unit = UnitType::HOURS or unit = UnitType::DAYS or unit = UnitType::WEEKS or unit = UnitType::MONTHS) then true\n\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t  else \n\t\t\t\t\t\t\t\tif (dimensionType = UnitDimensionType::STORAGE) then\n\t\t\t\t\t\t\t\t\tif (unit = UnitType::BYTES or unit = UnitType::KILOBYTES or unit = UnitType::MEGABYTES or unit = UnitType::GIGABYTES) then true\n\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (dimensionType = UnitDimensionType::COST) then\n\t\t\t\t\t\t\t\t\t\tif (unit = UnitType::EUROS or unit = UnitType::DOLLARS or unit = UnitType::POUNDS) then true\n\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\tif (dimensionType = UnitDimensionType::THROUGHPUT) then\n\t\t\t\t\t\t\t\t\t\t\tif (unit = UnitType::BYTES_PER_SECOND or unit = UnitType::REQUESTS_PER_SECOND) then true\n\t\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\tif (dimensionType = UnitDimensionType::REQUEST_NUM) then\n\t\t\t\t\t\t\t\t\t\t\t\tif (unit = UnitType::REQUESTS) then true else false endif\n\t\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\t\t\tif (dimensionType = UnitDimensionType::UNITLESS) then\n\t\t\t\t\t\t\t\t\t\t\t\t\t\tif (unit = UnitType::PERCENTAGE) then true\n\t\t\t\t\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif"
		   });		
		addAnnotation
		  (getUnit_DimensionType(), 
		   source, 
		   new String[] {
			 "derivation", "if (self.oclIsTypeOf(TimeIntervalUnit)) then UnitDimensionType::TIME_INTERVAL\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(StorageUnit)) then UnitDimensionType::STORAGE\n\t\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(MonetaryUnit)) then UnitDimensionType::COST\n\t\t\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(ThroughputUnit)) then UnitDimensionType::THROUGHPUT\n\t\t\t\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(RequestUnit)) then UnitDimensionType::REQUEST_NUM\n\t\t\t\t\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(Unitless)) then UnitDimensionType::UNITLESS\n\t\t\t\t\t\t\t\t\t\t\t\t\t\telse null\n\t\t\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif"
		   });			
		addAnnotation
		  (vmInfoEClass, 
		   source, 
		   new String[] {
			 "correct_param_vals", "\n\t\t\t\t\t\tself.costPerHour >= 0 and self.benchmarkRate >= 0"
		   });
	}

} //CamelPackageImpl
