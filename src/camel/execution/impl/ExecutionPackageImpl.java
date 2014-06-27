/**
 */
package camel.execution.impl;

import camel.CamelPackage;

import camel.deployment.DeploymentPackage;

import camel.deployment.impl.DeploymentPackageImpl;

import camel.execution.ActionRealization;
import camel.execution.ApplicationMeasurement;
import camel.execution.ExecutionContext;
import camel.execution.ExecutionFactory;
import camel.execution.ExecutionModel;
import camel.execution.ExecutionPackage;
import camel.execution.InternalComponentMeasurement;
import camel.execution.Measurement;
import camel.execution.ResourceCouplingMeasurement;
import camel.execution.ResourceMeasurement;
import camel.execution.RuleTrigger;
import camel.execution.SLOAssessment;

import camel.execution.util.ExecutionValidator;

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
public class ExecutionPackageImpl extends EPackageImpl implements ExecutionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executionModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionRealizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executionContextEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass measurementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationMeasurementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass internalComponentMeasurementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceCouplingMeasurementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceMeasurementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleTriggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sloAssessmentEClass = null;

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
	 * @see camel.execution.ExecutionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ExecutionPackageImpl() {
		super(eNS_URI, ExecutionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ExecutionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ExecutionPackage init() {
		if (isInited) return (ExecutionPackage)EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI);

		// Obtain or create and register package
		ExecutionPackageImpl theExecutionPackage = (ExecutionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ExecutionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ExecutionPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		CamelPackageImpl theCamelPackage = (CamelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) instanceof CamelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) : CamelPackage.eINSTANCE);
		DeploymentPackageImpl theDeploymentPackage = (DeploymentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) instanceof DeploymentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) : DeploymentPackage.eINSTANCE);
		OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) : OrganisationPackage.eINSTANCE);
		ProviderPackageImpl theProviderPackage = (ProviderPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) instanceof ProviderPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) : ProviderPackage.eINSTANCE);
		ScalabilityPackageImpl theScalabilityPackage = (ScalabilityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) instanceof ScalabilityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) : ScalabilityPackage.eINSTANCE);
		SecurityPackageImpl theSecurityPackage = (SecurityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) instanceof SecurityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) : SecurityPackage.eINSTANCE);
		SlaPackageImpl theSlaPackage = (SlaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) instanceof SlaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) : SlaPackage.eINSTANCE);
		TypePackageImpl theTypePackage = (TypePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) instanceof TypePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) : TypePackage.eINSTANCE);

		// Create package meta-data objects
		theExecutionPackage.createPackageContents();
		theCamelPackage.createPackageContents();
		theDeploymentPackage.createPackageContents();
		theOrganisationPackage.createPackageContents();
		theProviderPackage.createPackageContents();
		theScalabilityPackage.createPackageContents();
		theSecurityPackage.createPackageContents();
		theSlaPackage.createPackageContents();
		theTypePackage.createPackageContents();

		// Initialize created meta-data
		theExecutionPackage.initializePackageContents();
		theCamelPackage.initializePackageContents();
		theDeploymentPackage.initializePackageContents();
		theOrganisationPackage.initializePackageContents();
		theProviderPackage.initializePackageContents();
		theScalabilityPackage.initializePackageContents();
		theSecurityPackage.initializePackageContents();
		theSlaPackage.initializePackageContents();
		theTypePackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theExecutionPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return ExecutionValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theExecutionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ExecutionPackage.eNS_URI, theExecutionPackage);
		return theExecutionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExecutionModel() {
		return executionModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionModel_ExecutionContext() {
		return (EReference)executionModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionModel_Measurement() {
		return (EReference)executionModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionModel_SloAssess() {
		return (EReference)executionModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionModel_RuleTrigger() {
		return (EReference)executionModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionModel_ActionRealization() {
		return (EReference)executionModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionRealization() {
		return actionRealizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActionRealization_Action() {
		return (EReference)actionRealizationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionRealization_StartedOn() {
		return (EAttribute)actionRealizationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionRealization_EndedOn() {
		return (EAttribute)actionRealizationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionRealization_LowLevelActions() {
		return (EAttribute)actionRealizationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getActionRealization__CheckStartEndDates__ActionRealization() {
		return actionRealizationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExecutionContext() {
		return executionContextEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionContext_OfApplication() {
		return (EReference)executionContextEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExecutionContext_ID() {
		return (EAttribute)executionContextEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExecutionContext_StartTime() {
		return (EAttribute)executionContextEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExecutionContext_EndTime() {
		return (EAttribute)executionContextEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExecutionContext_TotalCost() {
		return (EAttribute)executionContextEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionContext_CostUnit() {
		return (EReference)executionContextEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionContext_InvolvesDeployment() {
		return (EReference)executionContextEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionContext_BasedOnRequirements() {
		return (EReference)executionContextEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExecutionContext__CheckStartEndDates__ExecutionContext() {
		return executionContextEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMeasurement() {
		return measurementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMeasurement_ID() {
		return (EAttribute)measurementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMeasurement_InExecutionContext() {
		return (EReference)measurementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMeasurement_OfMetric() {
		return (EReference)measurementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMeasurement_Value() {
		return (EAttribute)measurementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMeasurement_RawData() {
		return (EAttribute)measurementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMeasurement_ReportedOn() {
		return (EAttribute)measurementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMeasurement_ItSLO() {
		return (EReference)measurementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMeasurement_Triggers() {
		return (EReference)measurementEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMeasurement__CheckDate__Measurement() {
		return measurementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplicationMeasurement() {
		return applicationMeasurementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationMeasurement_ForApplication() {
		return (EReference)applicationMeasurementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInternalComponentMeasurement() {
		return internalComponentMeasurementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInternalComponentMeasurement_OfComponentInstance() {
		return (EReference)internalComponentMeasurementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResourceCouplingMeasurement() {
		return resourceCouplingMeasurementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceCouplingMeasurement_SourceVMInstance() {
		return (EReference)resourceCouplingMeasurementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceCouplingMeasurement_DestinationVMInstance() {
		return (EReference)resourceCouplingMeasurementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResourceMeasurement() {
		return resourceMeasurementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceMeasurement_OfVMInstance() {
		return (EReference)resourceMeasurementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceMeasurement_ResourceClass() {
		return (EReference)resourceMeasurementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceMeasurement_PhysicalNode() {
		return (EReference)resourceMeasurementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceMeasurement_DataObject() {
		return (EReference)resourceMeasurementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleTrigger() {
		return ruleTriggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleTrigger_ScalabilityRule() {
		return (EReference)ruleTriggerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleTrigger_EventInstances() {
		return (EReference)ruleTriggerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleTrigger_ActionRealizations() {
		return (EReference)ruleTriggerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleTrigger_FiredOn() {
		return (EAttribute)ruleTriggerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleTrigger_ExecutionContext() {
		return (EReference)ruleTriggerEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRuleTrigger__CheckDate__RuleTrigger() {
		return ruleTriggerEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSLOAssessment() {
		return sloAssessmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSLOAssessment_ItSLO() {
		return (EReference)sloAssessmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSLOAssessment_Assessment() {
		return (EAttribute)sloAssessmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSLOAssessment_InExecutionContext() {
		return (EReference)sloAssessmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSLOAssessment_Measurement() {
		return (EReference)sloAssessmentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSLOAssessment_AssessmentTime() {
		return (EAttribute)sloAssessmentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSLOAssessment__CheckDate__SLOAssessment() {
		return sloAssessmentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionFactory getExecutionFactory() {
		return (ExecutionFactory)getEFactoryInstance();
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
		executionModelEClass = createEClass(EXECUTION_MODEL);
		createEReference(executionModelEClass, EXECUTION_MODEL__EXECUTION_CONTEXT);
		createEReference(executionModelEClass, EXECUTION_MODEL__MEASUREMENT);
		createEReference(executionModelEClass, EXECUTION_MODEL__SLO_ASSESS);
		createEReference(executionModelEClass, EXECUTION_MODEL__RULE_TRIGGER);
		createEReference(executionModelEClass, EXECUTION_MODEL__ACTION_REALIZATION);

		actionRealizationEClass = createEClass(ACTION_REALIZATION);
		createEReference(actionRealizationEClass, ACTION_REALIZATION__ACTION);
		createEAttribute(actionRealizationEClass, ACTION_REALIZATION__STARTED_ON);
		createEAttribute(actionRealizationEClass, ACTION_REALIZATION__ENDED_ON);
		createEAttribute(actionRealizationEClass, ACTION_REALIZATION__LOW_LEVEL_ACTIONS);
		createEOperation(actionRealizationEClass, ACTION_REALIZATION___CHECK_START_END_DATES__ACTIONREALIZATION);

		executionContextEClass = createEClass(EXECUTION_CONTEXT);
		createEReference(executionContextEClass, EXECUTION_CONTEXT__OF_APPLICATION);
		createEAttribute(executionContextEClass, EXECUTION_CONTEXT__ID);
		createEAttribute(executionContextEClass, EXECUTION_CONTEXT__START_TIME);
		createEAttribute(executionContextEClass, EXECUTION_CONTEXT__END_TIME);
		createEAttribute(executionContextEClass, EXECUTION_CONTEXT__TOTAL_COST);
		createEReference(executionContextEClass, EXECUTION_CONTEXT__COST_UNIT);
		createEReference(executionContextEClass, EXECUTION_CONTEXT__INVOLVES_DEPLOYMENT);
		createEReference(executionContextEClass, EXECUTION_CONTEXT__BASED_ON_REQUIREMENTS);
		createEOperation(executionContextEClass, EXECUTION_CONTEXT___CHECK_START_END_DATES__EXECUTIONCONTEXT);

		measurementEClass = createEClass(MEASUREMENT);
		createEAttribute(measurementEClass, MEASUREMENT__ID);
		createEReference(measurementEClass, MEASUREMENT__IN_EXECUTION_CONTEXT);
		createEReference(measurementEClass, MEASUREMENT__OF_METRIC);
		createEAttribute(measurementEClass, MEASUREMENT__VALUE);
		createEAttribute(measurementEClass, MEASUREMENT__RAW_DATA);
		createEAttribute(measurementEClass, MEASUREMENT__REPORTED_ON);
		createEReference(measurementEClass, MEASUREMENT__IT_SLO);
		createEReference(measurementEClass, MEASUREMENT__TRIGGERS);
		createEOperation(measurementEClass, MEASUREMENT___CHECK_DATE__MEASUREMENT);

		applicationMeasurementEClass = createEClass(APPLICATION_MEASUREMENT);
		createEReference(applicationMeasurementEClass, APPLICATION_MEASUREMENT__FOR_APPLICATION);

		internalComponentMeasurementEClass = createEClass(INTERNAL_COMPONENT_MEASUREMENT);
		createEReference(internalComponentMeasurementEClass, INTERNAL_COMPONENT_MEASUREMENT__OF_COMPONENT_INSTANCE);

		resourceCouplingMeasurementEClass = createEClass(RESOURCE_COUPLING_MEASUREMENT);
		createEReference(resourceCouplingMeasurementEClass, RESOURCE_COUPLING_MEASUREMENT__SOURCE_VM_INSTANCE);
		createEReference(resourceCouplingMeasurementEClass, RESOURCE_COUPLING_MEASUREMENT__DESTINATION_VM_INSTANCE);

		resourceMeasurementEClass = createEClass(RESOURCE_MEASUREMENT);
		createEReference(resourceMeasurementEClass, RESOURCE_MEASUREMENT__OF_VM_INSTANCE);
		createEReference(resourceMeasurementEClass, RESOURCE_MEASUREMENT__RESOURCE_CLASS);
		createEReference(resourceMeasurementEClass, RESOURCE_MEASUREMENT__PHYSICAL_NODE);
		createEReference(resourceMeasurementEClass, RESOURCE_MEASUREMENT__DATA_OBJECT);

		ruleTriggerEClass = createEClass(RULE_TRIGGER);
		createEReference(ruleTriggerEClass, RULE_TRIGGER__SCALABILITY_RULE);
		createEReference(ruleTriggerEClass, RULE_TRIGGER__EVENT_INSTANCES);
		createEReference(ruleTriggerEClass, RULE_TRIGGER__ACTION_REALIZATIONS);
		createEAttribute(ruleTriggerEClass, RULE_TRIGGER__FIRED_ON);
		createEReference(ruleTriggerEClass, RULE_TRIGGER__EXECUTION_CONTEXT);
		createEOperation(ruleTriggerEClass, RULE_TRIGGER___CHECK_DATE__RULETRIGGER);

		sloAssessmentEClass = createEClass(SLO_ASSESSMENT);
		createEReference(sloAssessmentEClass, SLO_ASSESSMENT__IT_SLO);
		createEAttribute(sloAssessmentEClass, SLO_ASSESSMENT__ASSESSMENT);
		createEReference(sloAssessmentEClass, SLO_ASSESSMENT__IN_EXECUTION_CONTEXT);
		createEReference(sloAssessmentEClass, SLO_ASSESSMENT__MEASUREMENT);
		createEAttribute(sloAssessmentEClass, SLO_ASSESSMENT__ASSESSMENT_TIME);
		createEOperation(sloAssessmentEClass, SLO_ASSESSMENT___CHECK_DATE__SLOASSESSMENT);
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
		CamelPackage theCamelPackage = (CamelPackage)EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI);
		DeploymentPackage theDeploymentPackage = (DeploymentPackage)EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI);
		ScalabilityPackage theScalabilityPackage = (ScalabilityPackage)EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI);
		SlaPackage theSlaPackage = (SlaPackage)EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		applicationMeasurementEClass.getESuperTypes().add(this.getMeasurement());
		internalComponentMeasurementEClass.getESuperTypes().add(this.getMeasurement());
		resourceCouplingMeasurementEClass.getESuperTypes().add(this.getMeasurement());
		resourceMeasurementEClass.getESuperTypes().add(this.getMeasurement());

		// Initialize classes, features, and operations; add parameters
		initEClass(executionModelEClass, ExecutionModel.class, "ExecutionModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExecutionModel_ExecutionContext(), this.getExecutionContext(), null, "executionContext", null, 0, -1, ExecutionModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getExecutionModel_Measurement(), this.getMeasurement(), null, "measurement", null, 0, -1, ExecutionModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getExecutionModel_SloAssess(), this.getSLOAssessment(), null, "sloAssess", null, 0, -1, ExecutionModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getExecutionModel_RuleTrigger(), this.getRuleTrigger(), null, "ruleTrigger", null, 0, -1, ExecutionModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getExecutionModel_ActionRealization(), this.getActionRealization(), null, "actionRealization", null, 0, -1, ExecutionModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(actionRealizationEClass, ActionRealization.class, "ActionRealization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActionRealization_Action(), theCamelPackage.getAction(), null, "action", null, 1, 1, ActionRealization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActionRealization_StartedOn(), ecorePackage.getEDate(), "startedOn", null, 0, 1, ActionRealization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActionRealization_EndedOn(), ecorePackage.getEDate(), "endedOn", null, 0, 1, ActionRealization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActionRealization_LowLevelActions(), ecorePackage.getEString(), "lowLevelActions", null, 0, 1, ActionRealization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getActionRealization__CheckStartEndDates__ActionRealization(), ecorePackage.getEBoolean(), "checkStartEndDates", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getActionRealization(), "this_", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(executionContextEClass, ExecutionContext.class, "ExecutionContext", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExecutionContext_OfApplication(), theCamelPackage.getApplication(), null, "ofApplication", null, 1, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExecutionContext_ID(), ecorePackage.getEString(), "ID", null, 1, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExecutionContext_StartTime(), ecorePackage.getEDate(), "startTime", null, 0, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExecutionContext_EndTime(), ecorePackage.getEDate(), "endTime", null, 0, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExecutionContext_TotalCost(), ecorePackage.getEDouble(), "totalCost", null, 0, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExecutionContext_CostUnit(), theCamelPackage.getMonetaryUnit(), null, "costUnit", null, 0, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExecutionContext_InvolvesDeployment(), theDeploymentPackage.getDeploymentModel(), null, "involvesDeployment", null, 1, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExecutionContext_BasedOnRequirements(), theCamelPackage.getRequirementGroup(), null, "basedOnRequirements", null, 1, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getExecutionContext__CheckStartEndDates__ExecutionContext(), ecorePackage.getEBoolean(), "checkStartEndDates", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getExecutionContext(), "this_", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(measurementEClass, Measurement.class, "Measurement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMeasurement_ID(), ecorePackage.getEString(), "ID", null, 1, 1, Measurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMeasurement_InExecutionContext(), this.getExecutionContext(), null, "inExecutionContext", null, 1, 1, Measurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMeasurement_OfMetric(), theScalabilityPackage.getMetric(), null, "ofMetric", null, 1, 1, Measurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMeasurement_Value(), ecorePackage.getEDouble(), "value", null, 1, 1, Measurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMeasurement_RawData(), ecorePackage.getEString(), "rawData", null, 0, 1, Measurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMeasurement_ReportedOn(), ecorePackage.getEDate(), "reportedOn", null, 1, 1, Measurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMeasurement_ItSLO(), theSlaPackage.getServiceLevelObjectiveType(), null, "itSLO", null, 0, 1, Measurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMeasurement_Triggers(), theScalabilityPackage.getEventInstance(), null, "triggers", null, 0, 1, Measurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getMeasurement__CheckDate__Measurement(), ecorePackage.getEBoolean(), "checkDate", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMeasurement(), "m", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(applicationMeasurementEClass, ApplicationMeasurement.class, "ApplicationMeasurement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getApplicationMeasurement_ForApplication(), theCamelPackage.getApplication(), null, "forApplication", null, 1, 1, ApplicationMeasurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(internalComponentMeasurementEClass, InternalComponentMeasurement.class, "InternalComponentMeasurement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInternalComponentMeasurement_OfComponentInstance(), theDeploymentPackage.getComponentInstance(), null, "ofComponentInstance", null, 0, 1, InternalComponentMeasurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(resourceCouplingMeasurementEClass, ResourceCouplingMeasurement.class, "ResourceCouplingMeasurement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getResourceCouplingMeasurement_SourceVMInstance(), theDeploymentPackage.getVMInstance(), null, "sourceVMInstance", null, 1, 1, ResourceCouplingMeasurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceCouplingMeasurement_DestinationVMInstance(), theDeploymentPackage.getVMInstance(), null, "destinationVMInstance", null, 1, 1, ResourceCouplingMeasurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(resourceMeasurementEClass, ResourceMeasurement.class, "ResourceMeasurement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getResourceMeasurement_OfVMInstance(), theDeploymentPackage.getVMInstance(), null, "ofVMInstance", null, 0, 1, ResourceMeasurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceMeasurement_ResourceClass(), ecorePackage.getEClass(), null, "resourceClass", null, 0, 1, ResourceMeasurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceMeasurement_PhysicalNode(), theCamelPackage.getPhysicalNode(), null, "physicalNode", null, 0, 1, ResourceMeasurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceMeasurement_DataObject(), theCamelPackage.getDataObject(), null, "dataObject", null, 0, 1, ResourceMeasurement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleTriggerEClass, RuleTrigger.class, "RuleTrigger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleTrigger_ScalabilityRule(), theScalabilityPackage.getScalabilityRule(), null, "scalabilityRule", null, 1, 1, RuleTrigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleTrigger_EventInstances(), theScalabilityPackage.getEventInstance(), null, "eventInstances", null, 1, -1, RuleTrigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleTrigger_ActionRealizations(), this.getActionRealization(), null, "actionRealizations", null, 1, -1, RuleTrigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleTrigger_FiredOn(), ecorePackage.getEDate(), "firedOn", null, 1, 1, RuleTrigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleTrigger_ExecutionContext(), this.getExecutionContext(), null, "executionContext", null, 1, 1, RuleTrigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getRuleTrigger__CheckDate__RuleTrigger(), ecorePackage.getEBoolean(), "checkDate", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRuleTrigger(), "rt", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(sloAssessmentEClass, SLOAssessment.class, "SLOAssessment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSLOAssessment_ItSLO(), theSlaPackage.getServiceLevelObjectiveType(), null, "itSLO", null, 1, 1, SLOAssessment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSLOAssessment_Assessment(), ecorePackage.getEBoolean(), "assessment", null, 1, 1, SLOAssessment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSLOAssessment_InExecutionContext(), this.getExecutionContext(), null, "inExecutionContext", null, 1, 1, SLOAssessment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSLOAssessment_Measurement(), this.getMeasurement(), null, "measurement", null, 1, 1, SLOAssessment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSLOAssessment_AssessmentTime(), ecorePackage.getEDate(), "assessmentTime", null, 1, 1, SLOAssessment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getSLOAssessment__CheckDate__SLOAssessment(), ecorePackage.getEBoolean(), "checkDate", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSLOAssessment(), "sa", 0, 1, IS_UNIQUE, IS_ORDERED);

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
		  (executionContextEClass, 
		   source, 
		   new String[] {
			 "constraints", "ExecutionContext_Total_Cost ExecutionContext_Unit_Cost ExecutionContext_Correct_Reqs"
		   });	
		addAnnotation
		  (executionContextEClass, 
		   1,
		   "http://www.eclipse.org/emf/2002/GenModel",
		   new String[] {
			 "documentation", "invariant ExecutionContext_Start_End:\nself.checkStartEndDates(self);"
		   });			
		addAnnotation
		  (measurementEClass, 
		   source, 
		   new String[] {
			 "constraints", "Measurement_ItSLO_refer_to_Correct_Metric Measurement_eventInstance_same_Metric Correct_Measurement_Value Measurement_metric_refers_to_correct_ec"
		   });				
		addAnnotation
		  (applicationMeasurementEClass, 
		   source, 
		   new String[] {
			 "constraints", "AM_Same_App"
		   });			
		addAnnotation
		  (internalComponentMeasurementEClass, 
		   source, 
		   new String[] {
			 "constraints", "SCM_metric_refer_to_same_component SCM_included_in_EC"
		   });			
		addAnnotation
		  (resourceCouplingMeasurementEClass, 
		   source, 
		   new String[] {
			 "constraints", "RCMeasurement_Diff_VM_Instances"
		   });			
		addAnnotation
		  (resourceMeasurementEClass, 
		   source, 
		   new String[] {
			 "constraints", "RM_At_Least_One_Not_Null RM_DataObject_Alone RM_EC_ClouMLModel_VM_INSTANCE RM_Metric_VM_Instance RM_DataObject_ec"
		   });			
		addAnnotation
		  (ruleTriggerEClass, 
		   source, 
		   new String[] {
			 "constraints", "Rule_Trig_Event_Ins_Correct_Events Rule_Trig_Correct_Action Rule_Trig_Event_Metric_EC Rule_Trig_Scal_Policies_of_Correct_Dep_Model"
		   });				
		addAnnotation
		  (sloAssessmentEClass, 
		   source, 
		   new String[] {
			 "constraints", "SLOAss_Same_metric SLOAss_itSLO_in_Reqs_for_EC SLOAss_same_exec_context"
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
		  (executionContextEClass, 
		   source, 
		   new String[] {
			 "ExecutionContext_Total_Cost", "\n\t\t\t\t\t\tself.totalCost >= 0",
			 "ExecutionContext_Unit_Cost", "\n\t\t\t\t\t\tself.totalCost > 0 implies costUnit <> null",
			 "ExecutionContext_Correct_Reqs", "\n\t\t\t\t\t\t\t\tbasedOnRequirements.requirement->forAll(p | p.oclIsTypeOf(camel::sla::ServiceLevelObjectiveType) implies (p.oclAsType(camel::sla::ServiceLevelObjectiveType).customServiceLevel.metric.objectBinding.executionContext=self))"
		   });
		addAnnotation
		  (executionContextEClass, 
		   1,
		   "http://www.eclipse.org/emf/2002/GenModel",
		   new String[] {
			 "documentation", "invariant ExecutionContext_Start_End:\nself.checkStartEndDates(self);"
		   });				
		addAnnotation
		  (measurementEClass, 
		   source, 
		   new String[] {
			 "Measurement_ItSLO_refer_to_Correct_Metric", "\n\t\t\t\t\t\tself.itSLO <> null implies self.itSLO.customServiceLevel.metric = self.ofMetric",
			 "Measurement_eventInstance_same_Metric", "\n\t\t\t\t\t\t(self.triggers <> null and self.triggers.onEvent.oclIsTypeOf(camel::scalability::NonFunctionalEvent)) implies self.ofMetric = self.triggers.onEvent.oclAsType(camel::scalability::NonFunctionalEvent).condition.metric",
			 "Correct_Measurement_Value", "\n\t\t\t\t\t\tif (ofMetric.valueType.oclIsTypeOf(camel::type::Range)) then ofMetric.valueType.oclAsType(camel::type::Range).includesValue(self.value)\n\t\t\t\t\t\telse if (ofMetric.valueType.oclIsTypeOf(camel::type::RangeUnion)) then ofMetric.valueType.oclAsType(camel::type::RangeUnion).includesValue(self.value)\n\t\t\t\t\t\telse true endif\n\t\t\t\t\t\tendif",
			 "Measurement_metric_refers_to_correct_ec", "\n\t\t\t\t\t\t\t\tinExecutionContext = ofMetric.objectBinding.executionContext"
		   });				
		addAnnotation
		  (applicationMeasurementEClass, 
		   source, 
		   new String[] {
			 "AM_Same_App", "\n\t\t\t\t\t\t\t\tself.inExecutionContext.ofApplication = self.forApplication"
		   });			
		addAnnotation
		  (internalComponentMeasurementEClass, 
		   source, 
		   new String[] {
			 "SCM_metric_refer_to_same_component", "\n\t\t\t\t\t\t\t\tofMetric.objectBinding.oclIsTypeOf(camel::scalability::MetricComponentInstanceBinding) and ofMetric.objectBinding.oclAsType(camel::scalability::MetricComponentInstanceBinding).componentInstance = ofComponentInstance",
			 "SCM_included_in_EC", "\n\t\t\t\t\t\t\t\tinExecutionContext.involvesDeployment.componentInstances->includes(ofComponentInstance)"
		   });			
		addAnnotation
		  (resourceCouplingMeasurementEClass, 
		   source, 
		   new String[] {
			 "RCMeasurement_Diff_VM_Instances", "\n\t\t\t\t\t\t\t\tsourceVMInstance <> destinationVMInstance and inExecutionContext.involvesDeployment.componentInstances->includes(sourceVMInstance) and inExecutionContext.involvesDeployment.componentInstances->includes(destinationVMInstance)"
		   });			
		addAnnotation
		  (resourceMeasurementEClass, 
		   source, 
		   new String[] {
			 "RM_At_Least_One_Not_Null", "\n\t\t\t\t\t\t\t\tofVMInstance <> null or physicalNode <> null or dataObject <> null",
			 "RM_DataObject_Alone", "\n\t\t\t\t\t\t\t\tdataObject <> null implies (ofVMInstance = null and physicalNode = null)",
			 "RM_EC_ClouMLModel_VM_INSTANCE", "\n\t\t\t\t\t\t\t\tofVMInstance <> null implies inExecutionContext.involvesDeployment.componentInstances->includes(ofVMInstance)",
			 "RM_Metric_VM_Instance", "\n\t\t\t\t\t\t\t\tofVMInstance <> null implies (ofMetric.objectBinding.oclIsTypeOf(camel::scalability::MetricVMInstanceBinding) and ofMetric.objectBinding.oclAsType(camel::scalability::MetricVMInstanceBinding).vmInstance = ofVMInstance)",
			 "RM_DataObject_ec", "\n\t\t\t\t\t\t\t\tdataObject <> null implies inExecutionContext.involvesDeployment.communications->exists(p | p.ofDataObject = dataObject)"
		   });			
		addAnnotation
		  (ruleTriggerEClass, 
		   source, 
		   new String[] {
			 "Rule_Trig_Event_Ins_Correct_Events", "\n\t\t\t\t\t\t\t\tif (self.scalabilityRule.relatedEvent.oclIsKindOf(camel::scalability::SimpleEvent)) then (self.eventInstances->size() = 1 and self.eventInstances->exists(p | p.onEvent.oclAsType(camel::scalability::SimpleEvent) = self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::SimpleEvent)))\n\t\t\t\t\t\t\t\telse self.eventInstances->forAll(p | self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::EventPattern).includesEvent(p.onEvent)) \n\t\t\t\t\t\t\t\tendif",
			 "Rule_Trig_Correct_Action", "\n\t\t\t\t\t\t\t\t(self.actionRealizations->size() = self.scalabilityRule.mapsToActions->size()) and (self.actionRealizations->forAll(p | self.scalabilityRule.mapsToActions->exists(q | q = p.action)))",
			 "Rule_Trig_Event_Metric_EC", "\n\t\t\t\t\t\t\t\tif (self.scalabilityRule.relatedEvent.oclIsTypeOf(camel::scalability::NonFunctionalEvent)) then (self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::NonFunctionalEvent).condition.metric.objectBinding.executionContext = executionContext)\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (self.scalabilityRule.relatedEvent.oclIsKindOf(camel::scalability::EventPattern)) then self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::EventPattern).relatedToExecutionContext(executionContext)\n\t\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t\tendif",
			 "Rule_Trig_Scal_Policies_of_Correct_Dep_Model", "\n\t\t\t\t\t\t\t\tscalabilityRule.invariantPolicies->forAll(p | \n\t\t\t\t\t\t\t\t\tif (p.oclIsTypeOf(camel::scalability::HorizontalScalabilityPolicy) and p.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component <> null) then executionContext.involvesDeployment.components->includes(p.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component)\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tif (p.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy) and p.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm <> null) then executionContext.involvesDeployment.components->includes(p.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm)\n\t\t\t\t\t\t\t\t\t\telse true\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif \n\t\t\t\t\t\t\t\t)"
		   });				
		addAnnotation
		  (sloAssessmentEClass, 
		   source, 
		   new String[] {
			 "SLOAss_Same_metric", "\n\t\t\t\t\t\t\t\tmeasurement.ofMetric = itSLO.customServiceLevel.metric",
			 "SLOAss_itSLO_in_Reqs_for_EC", "\n\t\t\t\t\t\t\t\tself.inExecutionContext.basedOnRequirements.requirement->includes(self.itSLO)",
			 "SLOAss_same_exec_context", "\n\t\t\t\t\t\t\t\tinExecutionContext = measurement.inExecutionContext"
		   });	
	}

} //ExecutionPackageImpl
