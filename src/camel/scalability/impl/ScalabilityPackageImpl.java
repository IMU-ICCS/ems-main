/**
 */
package camel.scalability.impl;

import camel.CamelPackage;

import camel.deployment.DeploymentPackage;

import camel.deployment.impl.DeploymentPackageImpl;

import camel.execution.ExecutionPackage;

import camel.execution.impl.ExecutionPackageImpl;

import camel.impl.CamelPackageImpl;

import camel.organisation.OrganisationPackage;

import camel.organisation.impl.OrganisationPackageImpl;

import camel.provider.ProviderPackage;

import camel.provider.impl.ProviderPackageImpl;

import camel.scalability.BinaryEventPattern;
import camel.scalability.BinaryPatternOperatorType;
import camel.scalability.ComparisonOperatorType;
import camel.scalability.Condition;
import camel.scalability.Event;
import camel.scalability.EventInstance;
import camel.scalability.EventPattern;
import camel.scalability.FunctionalEvent;
import camel.scalability.HorizontalScalabilityPolicy;
import camel.scalability.LayerType;
import camel.scalability.Metric;
import camel.scalability.MetricApplicationBinding;
import camel.scalability.MetricApplicationInstanceBinding;
import camel.scalability.MetricComponentBinding;
import camel.scalability.MetricComponentInstanceBinding;
import camel.scalability.MetricCondition;
import camel.scalability.MetricFormula;
import camel.scalability.MetricFormulaParameter;
import camel.scalability.MetricFunctionArityType;
import camel.scalability.MetricFunctionType;
import camel.scalability.MetricObjectBinding;
import camel.scalability.MetricObjectInstanceBinding;
import camel.scalability.MetricTemplate;
import camel.scalability.MetricTemplateCondition;
import camel.scalability.MetricType;
import camel.scalability.MetricVMBinding;
import camel.scalability.MetricVMInstanceBinding;
import camel.scalability.NonFunctionalEvent;
import camel.scalability.Property;
import camel.scalability.PropertyType;
import camel.scalability.ScalabilityFactory;
import camel.scalability.ScalabilityModel;
import camel.scalability.ScalabilityPackage;
import camel.scalability.ScalabilityPolicy;
import camel.scalability.ScalabilityRule;
import camel.scalability.ScalingAction;
import camel.scalability.Schedule;
import camel.scalability.ScheduleType;
import camel.scalability.Sensor;
import camel.scalability.SimpleEvent;
import camel.scalability.StatusType;
import camel.scalability.Timer;
import camel.scalability.TimerType;
import camel.scalability.UnaryEventPattern;
import camel.scalability.UnaryPatternOperatorType;
import camel.scalability.VerticalScalabilityPolicy;
import camel.scalability.Window;
import camel.scalability.WindowSizeType;
import camel.scalability.WindowType;

import camel.scalability.util.ScalabilityValidator;

import camel.security.SecurityPackage;

import camel.security.impl.SecurityPackageImpl;

import camel.sla.SlaPackage;

import camel.sla.impl.SlaPackageImpl;

import camel.type.TypePackage;

import camel.type.impl.TypePackageImpl;

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
public class ScalabilityPackageImpl extends EPackageImpl implements ScalabilityPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scalabilityModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricConditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricTemplateConditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binaryEventPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unaryEventPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionalEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nonFunctionalEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricFormulaParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricFormulaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricObjectBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricApplicationBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricComponentBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricVMBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricObjectInstanceBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricApplicationInstanceBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricComponentInstanceBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricVMInstanceBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scalabilityPolicyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass horizontalScalabilityPolicyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass verticalScalabilityPolicyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scalabilityRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scalingActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scheduleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sensorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass windowEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum binaryPatternOperatorTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum comparisonOperatorTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum layerTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum metricFunctionArityTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum metricFunctionTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum metricTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum propertyTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum scheduleTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum statusTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum timerTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum unaryPatternOperatorTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum windowSizeTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum windowTypeEEnum = null;

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
	 * @see camel.scalability.ScalabilityPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ScalabilityPackageImpl() {
		super(eNS_URI, ScalabilityFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ScalabilityPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ScalabilityPackage init() {
		if (isInited) return (ScalabilityPackage)EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI);

		// Obtain or create and register package
		ScalabilityPackageImpl theScalabilityPackage = (ScalabilityPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ScalabilityPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ScalabilityPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		CamelPackageImpl theCamelPackage = (CamelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) instanceof CamelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) : CamelPackage.eINSTANCE);
		DeploymentPackageImpl theDeploymentPackage = (DeploymentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) instanceof DeploymentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) : DeploymentPackage.eINSTANCE);
		ExecutionPackageImpl theExecutionPackage = (ExecutionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) instanceof ExecutionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) : ExecutionPackage.eINSTANCE);
		OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) : OrganisationPackage.eINSTANCE);
		ProviderPackageImpl theProviderPackage = (ProviderPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) instanceof ProviderPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) : ProviderPackage.eINSTANCE);
		SecurityPackageImpl theSecurityPackage = (SecurityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) instanceof SecurityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) : SecurityPackage.eINSTANCE);
		SlaPackageImpl theSlaPackage = (SlaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) instanceof SlaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) : SlaPackage.eINSTANCE);
		TypePackageImpl theTypePackage = (TypePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) instanceof TypePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) : TypePackage.eINSTANCE);

		// Create package meta-data objects
		theScalabilityPackage.createPackageContents();
		theCamelPackage.createPackageContents();
		theDeploymentPackage.createPackageContents();
		theExecutionPackage.createPackageContents();
		theOrganisationPackage.createPackageContents();
		theProviderPackage.createPackageContents();
		theSecurityPackage.createPackageContents();
		theSlaPackage.createPackageContents();
		theTypePackage.createPackageContents();

		// Initialize created meta-data
		theScalabilityPackage.initializePackageContents();
		theCamelPackage.initializePackageContents();
		theDeploymentPackage.initializePackageContents();
		theExecutionPackage.initializePackageContents();
		theOrganisationPackage.initializePackageContents();
		theProviderPackage.initializePackageContents();
		theSecurityPackage.initializePackageContents();
		theSlaPackage.initializePackageContents();
		theTypePackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theScalabilityPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return ScalabilityValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theScalabilityPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ScalabilityPackage.eNS_URI, theScalabilityPackage);
		return theScalabilityPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScalabilityModel() {
		return scalabilityModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_MetricTemplates() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Metrics() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Rules() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Events() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_EventInstances() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Conditions() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Properties() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Actions() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Formulas() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Bindings() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_BindingInstances() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Windows() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Schedules() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Parameters() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Patterns() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Timers() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Sensors() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Units() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_ValueTypes() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityModel_Policies() {
		return (EReference)scalabilityModelEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCondition() {
		return conditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCondition_Validity() {
		return (EAttribute)conditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCondition_Threshold() {
		return (EAttribute)conditionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCondition_ComparisonOperator() {
		return (EAttribute)conditionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricCondition() {
		return metricConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricCondition_Metric() {
		return (EReference)metricConditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricTemplateCondition() {
		return metricTemplateConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricTemplateCondition_MetricTemplate() {
		return (EReference)metricTemplateConditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEvent() {
		return eventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvent_Name() {
		return (EAttribute)eventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventPattern() {
		return eventPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventPattern_Timer() {
		return (EReference)eventPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEventPattern__IncludesEvent__SimpleEvent() {
		return eventPatternEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEventPattern__IncludesLeftEvent__SimpleEvent() {
		return eventPatternEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEventPattern__IncludesRightEvent__SimpleEvent() {
		return eventPatternEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEventPattern__RelatedToExecutionContext__ExecutionContext() {
		return eventPatternEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEventPattern__LeftRelatedToExecutionContext__ExecutionContext() {
		return eventPatternEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEventPattern__RightRelatedToExecutionContext__ExecutionContext() {
		return eventPatternEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBinaryEventPattern() {
		return binaryEventPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryEventPattern_Left() {
		return (EReference)binaryEventPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryEventPattern_Right() {
		return (EReference)binaryEventPatternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryEventPattern_LowerOccurrenceBound() {
		return (EAttribute)binaryEventPatternEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryEventPattern_UpperOccurrenceBound() {
		return (EAttribute)binaryEventPatternEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryEventPattern_Operator() {
		return (EAttribute)binaryEventPatternEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUnaryEventPattern() {
		return unaryEventPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUnaryEventPattern_Event() {
		return (EReference)unaryEventPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnaryEventPattern_OccurrenceNum() {
		return (EAttribute)unaryEventPatternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnaryEventPattern_Operator() {
		return (EAttribute)unaryEventPatternEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimpleEvent() {
		return simpleEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunctionalEvent() {
		return functionalEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunctionalEvent_FunctionalType() {
		return (EAttribute)functionalEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNonFunctionalEvent() {
		return nonFunctionalEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNonFunctionalEvent_Condition() {
		return (EReference)nonFunctionalEventEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNonFunctionalEvent_IsViolation() {
		return (EAttribute)nonFunctionalEventEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventInstance() {
		return eventInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventInstance_Status() {
		return (EAttribute)eventInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventInstance_Layer() {
		return (EAttribute)eventInstanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventInstance_OnEvent() {
		return (EReference)eventInstanceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEventInstance__EqualLayer__LayerType_LayerType() {
		return eventInstanceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetric() {
		return metricEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetric_Id() {
		return (EAttribute)metricEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetric_HasTemplate() {
		return (EReference)metricEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetric_HasSchedule() {
		return (EReference)metricEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetric_Window() {
		return (EReference)metricEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetric_ComponentMetrics() {
		return (EReference)metricEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetric_Sensor() {
		return (EReference)metricEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetric_ObjectBinding() {
		return (EReference)metricEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetric_ValueType() {
		return (EReference)metricEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMetric__CheckRecursiveness__Metric_Metric() {
		return metricEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricFormulaParameter() {
		return metricFormulaParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricFormulaParameter_Value() {
		return (EReference)metricFormulaParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricFormula() {
		return metricFormulaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetricFormula_Function() {
		return (EAttribute)metricFormulaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetricFormula_FunctionArity() {
		return (EAttribute)metricFormulaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricFormula_Parameters() {
		return (EReference)metricFormulaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricTemplate() {
		return metricTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetricTemplate_Name() {
		return (EAttribute)metricTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetricTemplate_Description() {
		return (EAttribute)metricTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetricTemplate_ValueDirection() {
		return (EAttribute)metricTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricTemplate_Unit() {
		return (EReference)metricTemplateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetricTemplate_Layer() {
		return (EAttribute)metricTemplateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricTemplate_Measures() {
		return (EReference)metricTemplateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetricTemplate_Type() {
		return (EAttribute)metricTemplateEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricTemplate_Formula() {
		return (EReference)metricTemplateEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricTemplate_ObjectBinding() {
		return (EReference)metricTemplateEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMetricTemplate__CheckRecursiveness__MetricTemplate_MetricTemplate() {
		return metricTemplateEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMetricTemplate__GreaterEqualThanLayer__LayerType_LayerType() {
		return metricTemplateEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricObjectBinding() {
		return metricObjectBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricObjectBinding_Application() {
		return (EReference)metricObjectBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricApplicationBinding() {
		return metricApplicationBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricComponentBinding() {
		return metricComponentBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricComponentBinding_Vm() {
		return (EReference)metricComponentBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricComponentBinding_Component() {
		return (EReference)metricComponentBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricVMBinding() {
		return metricVMBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricVMBinding_Vm() {
		return (EReference)metricVMBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricObjectInstanceBinding() {
		return metricObjectInstanceBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricObjectInstanceBinding_ExecutionContext() {
		return (EReference)metricObjectInstanceBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricApplicationInstanceBinding() {
		return metricApplicationInstanceBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricComponentInstanceBinding() {
		return metricComponentInstanceBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricComponentInstanceBinding_VmInstance() {
		return (EReference)metricComponentInstanceBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricComponentInstanceBinding_ComponentInstance() {
		return (EReference)metricComponentInstanceBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetricVMInstanceBinding() {
		return metricVMInstanceBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetricVMInstanceBinding_VmInstance() {
		return (EReference)metricVMInstanceBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Id() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Name() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Description() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_RealizedBy() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Type() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScalabilityPolicy() {
		return scalabilityPolicyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHorizontalScalabilityPolicy() {
		return horizontalScalabilityPolicyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHorizontalScalabilityPolicy_MinInstances() {
		return (EAttribute)horizontalScalabilityPolicyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHorizontalScalabilityPolicy_MaxInstances() {
		return (EAttribute)horizontalScalabilityPolicyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHorizontalScalabilityPolicy_Component() {
		return (EReference)horizontalScalabilityPolicyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVerticalScalabilityPolicy() {
		return verticalScalabilityPolicyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVerticalScalabilityPolicy_MinCores() {
		return (EAttribute)verticalScalabilityPolicyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVerticalScalabilityPolicy_MaxCores() {
		return (EAttribute)verticalScalabilityPolicyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVerticalScalabilityPolicy_MinMemory() {
		return (EAttribute)verticalScalabilityPolicyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVerticalScalabilityPolicy_MaxMemory() {
		return (EAttribute)verticalScalabilityPolicyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVerticalScalabilityPolicy_MinCPU() {
		return (EAttribute)verticalScalabilityPolicyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVerticalScalabilityPolicy_MaxCPU() {
		return (EAttribute)verticalScalabilityPolicyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVerticalScalabilityPolicy_MinStorage() {
		return (EAttribute)verticalScalabilityPolicyEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVerticalScalabilityPolicy_MaxStorage() {
		return (EAttribute)verticalScalabilityPolicyEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVerticalScalabilityPolicy_Vm() {
		return (EReference)verticalScalabilityPolicyEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScalabilityRule() {
		return scalabilityRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScalabilityRule_Name() {
		return (EAttribute)scalabilityRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityRule_RelatedEvent() {
		return (EReference)scalabilityRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityRule_MapsToActions() {
		return (EReference)scalabilityRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityRule_DefinedBy() {
		return (EReference)scalabilityRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalabilityRule_InvariantPolicies() {
		return (EReference)scalabilityRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScalingAction() {
		return scalingActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScalingAction_Count() {
		return (EAttribute)scalingActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScalingAction_MemoryUpdate() {
		return (EAttribute)scalingActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScalingAction_CPUUpdate() {
		return (EAttribute)scalingActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScalingAction_CoreUpdate() {
		return (EAttribute)scalingActionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScalingAction_StorageUpdate() {
		return (EAttribute)scalingActionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScalingAction_IoUpdate() {
		return (EAttribute)scalingActionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScalingAction_NetworkUpdate() {
		return (EAttribute)scalingActionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalingAction_VmInstance() {
		return (EReference)scalingActionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalingAction_ComponentInstance() {
		return (EReference)scalingActionEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScalingAction_ContainmentInstance() {
		return (EReference)scalingActionEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSchedule() {
		return scheduleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSchedule_Start() {
		return (EAttribute)scheduleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSchedule_End() {
		return (EAttribute)scheduleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSchedule_Type() {
		return (EAttribute)scheduleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSchedule_Unit() {
		return (EReference)scheduleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSchedule_Repetitions() {
		return (EAttribute)scheduleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSchedule_Interval() {
		return (EAttribute)scheduleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSchedule__CheckStartEndDates__Schedule() {
		return scheduleEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSchedule__CheckIntervalRepetitions__Schedule() {
		return scheduleEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSensor() {
		return sensorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSensor_Configuration() {
		return (EAttribute)sensorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSensor_IsPush() {
		return (EAttribute)sensorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimer() {
		return timerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTimer_Type() {
		return (EAttribute)timerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTimer_TimeValue() {
		return (EAttribute)timerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTimer_MaxOccurrenceNum() {
		return (EAttribute)timerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTimer_Unit() {
		return (EReference)timerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWindow() {
		return windowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWindow_Unit() {
		return (EReference)windowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWindow_WindowType() {
		return (EAttribute)windowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWindow_SizeType() {
		return (EAttribute)windowEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWindow_MeasurementSize() {
		return (EAttribute)windowEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWindow_TimeSize() {
		return (EAttribute)windowEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBinaryPatternOperatorType() {
		return binaryPatternOperatorTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getComparisonOperatorType() {
		return comparisonOperatorTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getLayerType() {
		return layerTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMetricFunctionArityType() {
		return metricFunctionArityTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMetricFunctionType() {
		return metricFunctionTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMetricType() {
		return metricTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPropertyType() {
		return propertyTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getScheduleType() {
		return scheduleTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getStatusType() {
		return statusTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTimerType() {
		return timerTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getUnaryPatternOperatorType() {
		return unaryPatternOperatorTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getWindowSizeType() {
		return windowSizeTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getWindowType() {
		return windowTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalabilityFactory getScalabilityFactory() {
		return (ScalabilityFactory)getEFactoryInstance();
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
		scalabilityModelEClass = createEClass(SCALABILITY_MODEL);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__METRIC_TEMPLATES);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__METRICS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__RULES);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__EVENTS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__EVENT_INSTANCES);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__CONDITIONS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__PROPERTIES);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__ACTIONS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__FORMULAS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__BINDINGS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__BINDING_INSTANCES);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__WINDOWS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__SCHEDULES);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__PARAMETERS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__PATTERNS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__TIMERS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__SENSORS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__UNITS);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__VALUE_TYPES);
		createEReference(scalabilityModelEClass, SCALABILITY_MODEL__POLICIES);

		conditionEClass = createEClass(CONDITION);
		createEAttribute(conditionEClass, CONDITION__VALIDITY);
		createEAttribute(conditionEClass, CONDITION__THRESHOLD);
		createEAttribute(conditionEClass, CONDITION__COMPARISON_OPERATOR);

		metricConditionEClass = createEClass(METRIC_CONDITION);
		createEReference(metricConditionEClass, METRIC_CONDITION__METRIC);

		metricTemplateConditionEClass = createEClass(METRIC_TEMPLATE_CONDITION);
		createEReference(metricTemplateConditionEClass, METRIC_TEMPLATE_CONDITION__METRIC_TEMPLATE);

		eventEClass = createEClass(EVENT);
		createEAttribute(eventEClass, EVENT__NAME);

		eventPatternEClass = createEClass(EVENT_PATTERN);
		createEReference(eventPatternEClass, EVENT_PATTERN__TIMER);
		createEOperation(eventPatternEClass, EVENT_PATTERN___INCLUDES_EVENT__SIMPLEEVENT);
		createEOperation(eventPatternEClass, EVENT_PATTERN___INCLUDES_LEFT_EVENT__SIMPLEEVENT);
		createEOperation(eventPatternEClass, EVENT_PATTERN___INCLUDES_RIGHT_EVENT__SIMPLEEVENT);
		createEOperation(eventPatternEClass, EVENT_PATTERN___RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT);
		createEOperation(eventPatternEClass, EVENT_PATTERN___LEFT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT);
		createEOperation(eventPatternEClass, EVENT_PATTERN___RIGHT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT);

		binaryEventPatternEClass = createEClass(BINARY_EVENT_PATTERN);
		createEReference(binaryEventPatternEClass, BINARY_EVENT_PATTERN__LEFT);
		createEReference(binaryEventPatternEClass, BINARY_EVENT_PATTERN__RIGHT);
		createEAttribute(binaryEventPatternEClass, BINARY_EVENT_PATTERN__LOWER_OCCURRENCE_BOUND);
		createEAttribute(binaryEventPatternEClass, BINARY_EVENT_PATTERN__UPPER_OCCURRENCE_BOUND);
		createEAttribute(binaryEventPatternEClass, BINARY_EVENT_PATTERN__OPERATOR);

		unaryEventPatternEClass = createEClass(UNARY_EVENT_PATTERN);
		createEReference(unaryEventPatternEClass, UNARY_EVENT_PATTERN__EVENT);
		createEAttribute(unaryEventPatternEClass, UNARY_EVENT_PATTERN__OCCURRENCE_NUM);
		createEAttribute(unaryEventPatternEClass, UNARY_EVENT_PATTERN__OPERATOR);

		simpleEventEClass = createEClass(SIMPLE_EVENT);

		functionalEventEClass = createEClass(FUNCTIONAL_EVENT);
		createEAttribute(functionalEventEClass, FUNCTIONAL_EVENT__FUNCTIONAL_TYPE);

		nonFunctionalEventEClass = createEClass(NON_FUNCTIONAL_EVENT);
		createEReference(nonFunctionalEventEClass, NON_FUNCTIONAL_EVENT__CONDITION);
		createEAttribute(nonFunctionalEventEClass, NON_FUNCTIONAL_EVENT__IS_VIOLATION);

		eventInstanceEClass = createEClass(EVENT_INSTANCE);
		createEAttribute(eventInstanceEClass, EVENT_INSTANCE__STATUS);
		createEAttribute(eventInstanceEClass, EVENT_INSTANCE__LAYER);
		createEReference(eventInstanceEClass, EVENT_INSTANCE__ON_EVENT);
		createEOperation(eventInstanceEClass, EVENT_INSTANCE___EQUAL_LAYER__LAYERTYPE_LAYERTYPE);

		metricEClass = createEClass(METRIC);
		createEAttribute(metricEClass, METRIC__ID);
		createEReference(metricEClass, METRIC__HAS_TEMPLATE);
		createEReference(metricEClass, METRIC__HAS_SCHEDULE);
		createEReference(metricEClass, METRIC__WINDOW);
		createEReference(metricEClass, METRIC__COMPONENT_METRICS);
		createEReference(metricEClass, METRIC__SENSOR);
		createEReference(metricEClass, METRIC__OBJECT_BINDING);
		createEReference(metricEClass, METRIC__VALUE_TYPE);
		createEOperation(metricEClass, METRIC___CHECK_RECURSIVENESS__METRIC_METRIC);

		metricFormulaParameterEClass = createEClass(METRIC_FORMULA_PARAMETER);
		createEReference(metricFormulaParameterEClass, METRIC_FORMULA_PARAMETER__VALUE);

		metricFormulaEClass = createEClass(METRIC_FORMULA);
		createEAttribute(metricFormulaEClass, METRIC_FORMULA__FUNCTION);
		createEAttribute(metricFormulaEClass, METRIC_FORMULA__FUNCTION_ARITY);
		createEReference(metricFormulaEClass, METRIC_FORMULA__PARAMETERS);

		metricTemplateEClass = createEClass(METRIC_TEMPLATE);
		createEAttribute(metricTemplateEClass, METRIC_TEMPLATE__NAME);
		createEAttribute(metricTemplateEClass, METRIC_TEMPLATE__DESCRIPTION);
		createEAttribute(metricTemplateEClass, METRIC_TEMPLATE__VALUE_DIRECTION);
		createEReference(metricTemplateEClass, METRIC_TEMPLATE__UNIT);
		createEAttribute(metricTemplateEClass, METRIC_TEMPLATE__LAYER);
		createEReference(metricTemplateEClass, METRIC_TEMPLATE__MEASURES);
		createEAttribute(metricTemplateEClass, METRIC_TEMPLATE__TYPE);
		createEReference(metricTemplateEClass, METRIC_TEMPLATE__FORMULA);
		createEReference(metricTemplateEClass, METRIC_TEMPLATE__OBJECT_BINDING);
		createEOperation(metricTemplateEClass, METRIC_TEMPLATE___CHECK_RECURSIVENESS__METRICTEMPLATE_METRICTEMPLATE);
		createEOperation(metricTemplateEClass, METRIC_TEMPLATE___GREATER_EQUAL_THAN_LAYER__LAYERTYPE_LAYERTYPE);

		metricObjectBindingEClass = createEClass(METRIC_OBJECT_BINDING);
		createEReference(metricObjectBindingEClass, METRIC_OBJECT_BINDING__APPLICATION);

		metricApplicationBindingEClass = createEClass(METRIC_APPLICATION_BINDING);

		metricComponentBindingEClass = createEClass(METRIC_COMPONENT_BINDING);
		createEReference(metricComponentBindingEClass, METRIC_COMPONENT_BINDING__VM);
		createEReference(metricComponentBindingEClass, METRIC_COMPONENT_BINDING__COMPONENT);

		metricVMBindingEClass = createEClass(METRIC_VM_BINDING);
		createEReference(metricVMBindingEClass, METRIC_VM_BINDING__VM);

		metricObjectInstanceBindingEClass = createEClass(METRIC_OBJECT_INSTANCE_BINDING);
		createEReference(metricObjectInstanceBindingEClass, METRIC_OBJECT_INSTANCE_BINDING__EXECUTION_CONTEXT);

		metricApplicationInstanceBindingEClass = createEClass(METRIC_APPLICATION_INSTANCE_BINDING);

		metricComponentInstanceBindingEClass = createEClass(METRIC_COMPONENT_INSTANCE_BINDING);
		createEReference(metricComponentInstanceBindingEClass, METRIC_COMPONENT_INSTANCE_BINDING__VM_INSTANCE);
		createEReference(metricComponentInstanceBindingEClass, METRIC_COMPONENT_INSTANCE_BINDING__COMPONENT_INSTANCE);

		metricVMInstanceBindingEClass = createEClass(METRIC_VM_INSTANCE_BINDING);
		createEReference(metricVMInstanceBindingEClass, METRIC_VM_INSTANCE_BINDING__VM_INSTANCE);

		propertyEClass = createEClass(PROPERTY);
		createEAttribute(propertyEClass, PROPERTY__ID);
		createEAttribute(propertyEClass, PROPERTY__NAME);
		createEAttribute(propertyEClass, PROPERTY__DESCRIPTION);
		createEReference(propertyEClass, PROPERTY__REALIZED_BY);
		createEAttribute(propertyEClass, PROPERTY__TYPE);

		scalabilityPolicyEClass = createEClass(SCALABILITY_POLICY);

		horizontalScalabilityPolicyEClass = createEClass(HORIZONTAL_SCALABILITY_POLICY);
		createEAttribute(horizontalScalabilityPolicyEClass, HORIZONTAL_SCALABILITY_POLICY__MIN_INSTANCES);
		createEAttribute(horizontalScalabilityPolicyEClass, HORIZONTAL_SCALABILITY_POLICY__MAX_INSTANCES);
		createEReference(horizontalScalabilityPolicyEClass, HORIZONTAL_SCALABILITY_POLICY__COMPONENT);

		verticalScalabilityPolicyEClass = createEClass(VERTICAL_SCALABILITY_POLICY);
		createEAttribute(verticalScalabilityPolicyEClass, VERTICAL_SCALABILITY_POLICY__MIN_CORES);
		createEAttribute(verticalScalabilityPolicyEClass, VERTICAL_SCALABILITY_POLICY__MAX_CORES);
		createEAttribute(verticalScalabilityPolicyEClass, VERTICAL_SCALABILITY_POLICY__MIN_MEMORY);
		createEAttribute(verticalScalabilityPolicyEClass, VERTICAL_SCALABILITY_POLICY__MAX_MEMORY);
		createEAttribute(verticalScalabilityPolicyEClass, VERTICAL_SCALABILITY_POLICY__MIN_CPU);
		createEAttribute(verticalScalabilityPolicyEClass, VERTICAL_SCALABILITY_POLICY__MAX_CPU);
		createEAttribute(verticalScalabilityPolicyEClass, VERTICAL_SCALABILITY_POLICY__MIN_STORAGE);
		createEAttribute(verticalScalabilityPolicyEClass, VERTICAL_SCALABILITY_POLICY__MAX_STORAGE);
		createEReference(verticalScalabilityPolicyEClass, VERTICAL_SCALABILITY_POLICY__VM);

		scalabilityRuleEClass = createEClass(SCALABILITY_RULE);
		createEAttribute(scalabilityRuleEClass, SCALABILITY_RULE__NAME);
		createEReference(scalabilityRuleEClass, SCALABILITY_RULE__RELATED_EVENT);
		createEReference(scalabilityRuleEClass, SCALABILITY_RULE__MAPS_TO_ACTIONS);
		createEReference(scalabilityRuleEClass, SCALABILITY_RULE__DEFINED_BY);
		createEReference(scalabilityRuleEClass, SCALABILITY_RULE__INVARIANT_POLICIES);

		scalingActionEClass = createEClass(SCALING_ACTION);
		createEAttribute(scalingActionEClass, SCALING_ACTION__COUNT);
		createEAttribute(scalingActionEClass, SCALING_ACTION__MEMORY_UPDATE);
		createEAttribute(scalingActionEClass, SCALING_ACTION__CPU_UPDATE);
		createEAttribute(scalingActionEClass, SCALING_ACTION__CORE_UPDATE);
		createEAttribute(scalingActionEClass, SCALING_ACTION__STORAGE_UPDATE);
		createEAttribute(scalingActionEClass, SCALING_ACTION__IO_UPDATE);
		createEAttribute(scalingActionEClass, SCALING_ACTION__NETWORK_UPDATE);
		createEReference(scalingActionEClass, SCALING_ACTION__VM_INSTANCE);
		createEReference(scalingActionEClass, SCALING_ACTION__COMPONENT_INSTANCE);
		createEReference(scalingActionEClass, SCALING_ACTION__CONTAINMENT_INSTANCE);

		scheduleEClass = createEClass(SCHEDULE);
		createEAttribute(scheduleEClass, SCHEDULE__START);
		createEAttribute(scheduleEClass, SCHEDULE__END);
		createEAttribute(scheduleEClass, SCHEDULE__TYPE);
		createEReference(scheduleEClass, SCHEDULE__UNIT);
		createEAttribute(scheduleEClass, SCHEDULE__REPETITIONS);
		createEAttribute(scheduleEClass, SCHEDULE__INTERVAL);
		createEOperation(scheduleEClass, SCHEDULE___CHECK_START_END_DATES__SCHEDULE);
		createEOperation(scheduleEClass, SCHEDULE___CHECK_INTERVAL_REPETITIONS__SCHEDULE);

		sensorEClass = createEClass(SENSOR);
		createEAttribute(sensorEClass, SENSOR__CONFIGURATION);
		createEAttribute(sensorEClass, SENSOR__IS_PUSH);

		timerEClass = createEClass(TIMER);
		createEAttribute(timerEClass, TIMER__TYPE);
		createEAttribute(timerEClass, TIMER__TIME_VALUE);
		createEAttribute(timerEClass, TIMER__MAX_OCCURRENCE_NUM);
		createEReference(timerEClass, TIMER__UNIT);

		windowEClass = createEClass(WINDOW);
		createEReference(windowEClass, WINDOW__UNIT);
		createEAttribute(windowEClass, WINDOW__WINDOW_TYPE);
		createEAttribute(windowEClass, WINDOW__SIZE_TYPE);
		createEAttribute(windowEClass, WINDOW__MEASUREMENT_SIZE);
		createEAttribute(windowEClass, WINDOW__TIME_SIZE);

		// Create enums
		binaryPatternOperatorTypeEEnum = createEEnum(BINARY_PATTERN_OPERATOR_TYPE);
		comparisonOperatorTypeEEnum = createEEnum(COMPARISON_OPERATOR_TYPE);
		layerTypeEEnum = createEEnum(LAYER_TYPE);
		metricFunctionArityTypeEEnum = createEEnum(METRIC_FUNCTION_ARITY_TYPE);
		metricFunctionTypeEEnum = createEEnum(METRIC_FUNCTION_TYPE);
		metricTypeEEnum = createEEnum(METRIC_TYPE);
		propertyTypeEEnum = createEEnum(PROPERTY_TYPE);
		scheduleTypeEEnum = createEEnum(SCHEDULE_TYPE);
		statusTypeEEnum = createEEnum(STATUS_TYPE);
		timerTypeEEnum = createEEnum(TIMER_TYPE);
		unaryPatternOperatorTypeEEnum = createEEnum(UNARY_PATTERN_OPERATOR_TYPE);
		windowSizeTypeEEnum = createEEnum(WINDOW_SIZE_TYPE);
		windowTypeEEnum = createEEnum(WINDOW_TYPE);
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
		TypePackage theTypePackage = (TypePackage)EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI);
		ExecutionPackage theExecutionPackage = (ExecutionPackage)EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI);
		DeploymentPackage theDeploymentPackage = (DeploymentPackage)EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI);
		OrganisationPackage theOrganisationPackage = (OrganisationPackage)EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		metricConditionEClass.getESuperTypes().add(this.getCondition());
		metricTemplateConditionEClass.getESuperTypes().add(this.getCondition());
		eventPatternEClass.getESuperTypes().add(this.getEvent());
		binaryEventPatternEClass.getESuperTypes().add(this.getEventPattern());
		unaryEventPatternEClass.getESuperTypes().add(this.getEventPattern());
		simpleEventEClass.getESuperTypes().add(this.getEvent());
		functionalEventEClass.getESuperTypes().add(this.getSimpleEvent());
		nonFunctionalEventEClass.getESuperTypes().add(this.getSimpleEvent());
		metricFormulaEClass.getESuperTypes().add(this.getMetricFormulaParameter());
		metricTemplateEClass.getESuperTypes().add(this.getMetricFormulaParameter());
		metricApplicationBindingEClass.getESuperTypes().add(this.getMetricObjectBinding());
		metricComponentBindingEClass.getESuperTypes().add(this.getMetricObjectBinding());
		metricVMBindingEClass.getESuperTypes().add(this.getMetricObjectBinding());
		metricApplicationInstanceBindingEClass.getESuperTypes().add(this.getMetricObjectInstanceBinding());
		metricComponentInstanceBindingEClass.getESuperTypes().add(this.getMetricObjectInstanceBinding());
		metricVMInstanceBindingEClass.getESuperTypes().add(this.getMetricObjectInstanceBinding());
		scalabilityPolicyEClass.getESuperTypes().add(theCamelPackage.getRequirement());
		horizontalScalabilityPolicyEClass.getESuperTypes().add(this.getScalabilityPolicy());
		verticalScalabilityPolicyEClass.getESuperTypes().add(this.getScalabilityPolicy());
		scalingActionEClass.getESuperTypes().add(theCamelPackage.getAction());

		// Initialize classes, features, and operations; add parameters
		initEClass(scalabilityModelEClass, ScalabilityModel.class, "ScalabilityModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getScalabilityModel_MetricTemplates(), this.getMetricTemplate(), null, "metricTemplates", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Metrics(), this.getMetric(), null, "metrics", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Rules(), this.getScalabilityRule(), null, "rules", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Events(), this.getEvent(), null, "events", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_EventInstances(), this.getEventInstance(), null, "eventInstances", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Conditions(), this.getCondition(), null, "conditions", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Properties(), this.getProperty(), null, "properties", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Actions(), this.getScalingAction(), null, "actions", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Formulas(), this.getMetricFormula(), null, "formulas", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Bindings(), this.getMetricObjectBinding(), null, "bindings", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_BindingInstances(), this.getMetricObjectInstanceBinding(), null, "bindingInstances", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Windows(), this.getWindow(), null, "windows", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Schedules(), this.getSchedule(), null, "schedules", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Parameters(), this.getMetricFormulaParameter(), null, "parameters", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Patterns(), this.getEventPattern(), null, "patterns", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Timers(), this.getTimer(), null, "timers", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Sensors(), this.getSensor(), null, "sensors", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Units(), theCamelPackage.getUnit(), null, "units", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_ValueTypes(), theTypePackage.getValueType(), null, "valueTypes", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getScalabilityModel_Policies(), this.getScalabilityPolicy(), null, "policies", null, 0, -1, ScalabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionEClass, Condition.class, "Condition", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCondition_Validity(), ecorePackage.getEDate(), "validity", null, 0, 1, Condition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCondition_Threshold(), ecorePackage.getEDouble(), "threshold", null, 1, 1, Condition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCondition_ComparisonOperator(), this.getComparisonOperatorType(), "comparisonOperator", null, 1, 1, Condition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metricConditionEClass, MetricCondition.class, "MetricCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetricCondition_Metric(), this.getMetric(), null, "metric", null, 1, 1, MetricCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metricTemplateConditionEClass, MetricTemplateCondition.class, "MetricTemplateCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetricTemplateCondition_MetricTemplate(), this.getMetricTemplate(), null, "metricTemplate", null, 1, 1, MetricTemplateCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventEClass, Event.class, "Event", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEvent_Name(), ecorePackage.getEString(), "name", null, 1, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventPatternEClass, EventPattern.class, "EventPattern", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEventPattern_Timer(), this.getTimer(), null, "timer", null, 0, 1, EventPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getEventPattern__IncludesEvent__SimpleEvent(), ecorePackage.getEBoolean(), "includesEvent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSimpleEvent(), "e", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getEventPattern__IncludesLeftEvent__SimpleEvent(), ecorePackage.getEBoolean(), "includesLeftEvent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSimpleEvent(), "e", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getEventPattern__IncludesRightEvent__SimpleEvent(), ecorePackage.getEBoolean(), "includesRightEvent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSimpleEvent(), "e", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getEventPattern__RelatedToExecutionContext__ExecutionContext(), ecorePackage.getEBoolean(), "relatedToExecutionContext", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theExecutionPackage.getExecutionContext(), "ec", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getEventPattern__LeftRelatedToExecutionContext__ExecutionContext(), ecorePackage.getEBoolean(), "leftRelatedToExecutionContext", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theExecutionPackage.getExecutionContext(), "ec", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getEventPattern__RightRelatedToExecutionContext__ExecutionContext(), ecorePackage.getEBoolean(), "rightRelatedToExecutionContext", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theExecutionPackage.getExecutionContext(), "ec", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(binaryEventPatternEClass, BinaryEventPattern.class, "BinaryEventPattern", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBinaryEventPattern_Left(), this.getEvent(), null, "left", null, 0, 1, BinaryEventPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinaryEventPattern_Right(), this.getEvent(), null, "right", null, 0, 1, BinaryEventPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinaryEventPattern_LowerOccurrenceBound(), ecorePackage.getEInt(), "lowerOccurrenceBound", null, 0, 1, BinaryEventPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinaryEventPattern_UpperOccurrenceBound(), ecorePackage.getEInt(), "upperOccurrenceBound", null, 0, 1, BinaryEventPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinaryEventPattern_Operator(), this.getBinaryPatternOperatorType(), "operator", null, 1, 1, BinaryEventPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(unaryEventPatternEClass, UnaryEventPattern.class, "UnaryEventPattern", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUnaryEventPattern_Event(), this.getEvent(), null, "event", null, 1, 1, UnaryEventPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnaryEventPattern_OccurrenceNum(), ecorePackage.getEInt(), "occurrenceNum", null, 0, 1, UnaryEventPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnaryEventPattern_Operator(), this.getUnaryPatternOperatorType(), "operator", null, 1, 1, UnaryEventPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simpleEventEClass, SimpleEvent.class, "SimpleEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(functionalEventEClass, FunctionalEvent.class, "FunctionalEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFunctionalEvent_FunctionalType(), ecorePackage.getEString(), "functionalType", null, 1, 1, FunctionalEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nonFunctionalEventEClass, NonFunctionalEvent.class, "NonFunctionalEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNonFunctionalEvent_Condition(), this.getMetricCondition(), null, "condition", null, 1, 1, NonFunctionalEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNonFunctionalEvent_IsViolation(), ecorePackage.getEBoolean(), "isViolation", null, 1, 1, NonFunctionalEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventInstanceEClass, EventInstance.class, "EventInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEventInstance_Status(), this.getStatusType(), "status", null, 1, 1, EventInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventInstance_Layer(), this.getLayerType(), "layer", null, 0, 1, EventInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEventInstance_OnEvent(), this.getSimpleEvent(), null, "onEvent", null, 1, 1, EventInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getEventInstance__EqualLayer__LayerType_LayerType(), ecorePackage.getEBoolean(), "equalLayer", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLayerType(), "l1", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLayerType(), "l2", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(metricEClass, Metric.class, "Metric", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMetric_Id(), ecorePackage.getEString(), "id", null, 1, 1, Metric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetric_HasTemplate(), this.getMetricTemplate(), null, "hasTemplate", null, 1, 1, Metric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetric_HasSchedule(), this.getSchedule(), null, "hasSchedule", null, 0, 1, Metric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetric_Window(), this.getWindow(), null, "window", null, 0, 1, Metric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetric_ComponentMetrics(), this.getMetric(), null, "componentMetrics", null, 0, -1, Metric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetric_Sensor(), this.getSensor(), null, "sensor", null, 0, 1, Metric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetric_ObjectBinding(), this.getMetricObjectInstanceBinding(), null, "objectBinding", null, 1, 1, Metric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetric_ValueType(), theTypePackage.getValueType(), null, "valueType", null, 1, 1, Metric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getMetric__CheckRecursiveness__Metric_Metric(), ecorePackage.getEBoolean(), "checkRecursiveness", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMetric(), "m1", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMetric(), "m2", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(metricFormulaParameterEClass, MetricFormulaParameter.class, "MetricFormulaParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetricFormulaParameter_Value(), theTypePackage.getValue(), null, "value", null, 0, 1, MetricFormulaParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metricFormulaEClass, MetricFormula.class, "MetricFormula", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMetricFormula_Function(), this.getMetricFunctionType(), "function", null, 1, 1, MetricFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMetricFormula_FunctionArity(), this.getMetricFunctionArityType(), "functionArity", null, 1, 1, MetricFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetricFormula_Parameters(), this.getMetricFormulaParameter(), null, "parameters", null, 1, -1, MetricFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metricTemplateEClass, MetricTemplate.class, "MetricTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMetricTemplate_Name(), ecorePackage.getEString(), "name", null, 1, 1, MetricTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMetricTemplate_Description(), ecorePackage.getEString(), "description", null, 0, 1, MetricTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMetricTemplate_ValueDirection(), ecorePackage.getEShort(), "valueDirection", null, 1, 1, MetricTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetricTemplate_Unit(), theCamelPackage.getUnit(), null, "unit", null, 1, 1, MetricTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMetricTemplate_Layer(), this.getLayerType(), "layer", null, 0, 1, MetricTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetricTemplate_Measures(), this.getProperty(), null, "measures", null, 1, 1, MetricTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMetricTemplate_Type(), this.getMetricType(), "type", null, 1, 1, MetricTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetricTemplate_Formula(), this.getMetricFormula(), null, "formula", null, 0, 1, MetricTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetricTemplate_ObjectBinding(), this.getMetricObjectBinding(), null, "objectBinding", null, 0, 1, MetricTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getMetricTemplate__CheckRecursiveness__MetricTemplate_MetricTemplate(), ecorePackage.getEBoolean(), "checkRecursiveness", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMetricTemplate(), "mt1", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMetricTemplate(), "mt2", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getMetricTemplate__GreaterEqualThanLayer__LayerType_LayerType(), ecorePackage.getEBoolean(), "greaterEqualThanLayer", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLayerType(), "l1", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLayerType(), "l2", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(metricObjectBindingEClass, MetricObjectBinding.class, "MetricObjectBinding", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetricObjectBinding_Application(), theCamelPackage.getApplication(), null, "application", null, 1, 1, MetricObjectBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metricApplicationBindingEClass, MetricApplicationBinding.class, "MetricApplicationBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(metricComponentBindingEClass, MetricComponentBinding.class, "MetricComponentBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetricComponentBinding_Vm(), theDeploymentPackage.getVM(), null, "vm", null, 0, 1, MetricComponentBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetricComponentBinding_Component(), theDeploymentPackage.getComponent(), null, "component", null, 1, 1, MetricComponentBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metricVMBindingEClass, MetricVMBinding.class, "MetricVMBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetricVMBinding_Vm(), theDeploymentPackage.getVM(), null, "vm", null, 1, 1, MetricVMBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metricObjectInstanceBindingEClass, MetricObjectInstanceBinding.class, "MetricObjectInstanceBinding", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetricObjectInstanceBinding_ExecutionContext(), theExecutionPackage.getExecutionContext(), null, "executionContext", null, 1, 1, MetricObjectInstanceBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metricApplicationInstanceBindingEClass, MetricApplicationInstanceBinding.class, "MetricApplicationInstanceBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(metricComponentInstanceBindingEClass, MetricComponentInstanceBinding.class, "MetricComponentInstanceBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetricComponentInstanceBinding_VmInstance(), theDeploymentPackage.getVMInstance(), null, "vmInstance", null, 0, 1, MetricComponentInstanceBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetricComponentInstanceBinding_ComponentInstance(), theDeploymentPackage.getComponentInstance(), null, "componentInstance", null, 1, 1, MetricComponentInstanceBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metricVMInstanceBindingEClass, MetricVMInstanceBinding.class, "MetricVMInstanceBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetricVMInstanceBinding_VmInstance(), theDeploymentPackage.getVMInstance(), null, "vmInstance", null, 1, 1, MetricVMInstanceBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProperty_Id(), ecorePackage.getEString(), "id", null, 1, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Name(), ecorePackage.getEString(), "name", null, 1, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Description(), ecorePackage.getEString(), "description", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProperty_RealizedBy(), this.getProperty(), null, "realizedBy", null, 0, -1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Type(), this.getPropertyType(), "type", null, 1, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(scalabilityPolicyEClass, ScalabilityPolicy.class, "ScalabilityPolicy", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(horizontalScalabilityPolicyEClass, HorizontalScalabilityPolicy.class, "HorizontalScalabilityPolicy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHorizontalScalabilityPolicy_MinInstances(), ecorePackage.getEInt(), "minInstances", null, 1, 1, HorizontalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHorizontalScalabilityPolicy_MaxInstances(), ecorePackage.getEInt(), "maxInstances", null, 1, 1, HorizontalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHorizontalScalabilityPolicy_Component(), theDeploymentPackage.getInternalComponent(), null, "component", null, 0, 1, HorizontalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(verticalScalabilityPolicyEClass, VerticalScalabilityPolicy.class, "VerticalScalabilityPolicy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVerticalScalabilityPolicy_MinCores(), ecorePackage.getEInt(), "minCores", null, 0, 1, VerticalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVerticalScalabilityPolicy_MaxCores(), ecorePackage.getEInt(), "maxCores", null, 0, 1, VerticalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVerticalScalabilityPolicy_MinMemory(), ecorePackage.getEInt(), "minMemory", null, 0, 1, VerticalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVerticalScalabilityPolicy_MaxMemory(), ecorePackage.getEInt(), "maxMemory", null, 0, 1, VerticalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVerticalScalabilityPolicy_MinCPU(), ecorePackage.getEDouble(), "minCPU", null, 0, 1, VerticalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVerticalScalabilityPolicy_MaxCPU(), ecorePackage.getEDouble(), "maxCPU", null, 0, 1, VerticalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVerticalScalabilityPolicy_MinStorage(), ecorePackage.getEInt(), "minStorage", null, 0, 1, VerticalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVerticalScalabilityPolicy_MaxStorage(), ecorePackage.getEInt(), "maxStorage", null, 0, 1, VerticalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVerticalScalabilityPolicy_Vm(), theDeploymentPackage.getVM(), null, "vm", null, 0, 1, VerticalScalabilityPolicy.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(scalabilityRuleEClass, ScalabilityRule.class, "ScalabilityRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getScalabilityRule_Name(), ecorePackage.getEString(), "name", null, 1, 1, ScalabilityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScalabilityRule_RelatedEvent(), this.getEvent(), null, "relatedEvent", null, 1, 1, ScalabilityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScalabilityRule_MapsToActions(), theCamelPackage.getAction(), null, "mapsToActions", null, 1, -1, ScalabilityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScalabilityRule_DefinedBy(), theOrganisationPackage.getEntity(), null, "definedBy", null, 0, -1, ScalabilityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScalabilityRule_InvariantPolicies(), this.getScalabilityPolicy(), null, "invariantPolicies", null, 0, -1, ScalabilityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(scalingActionEClass, ScalingAction.class, "ScalingAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getScalingAction_Count(), ecorePackage.getEInt(), "count", null, 0, 1, ScalingAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScalingAction_MemoryUpdate(), ecorePackage.getEInt(), "memoryUpdate", null, 0, 1, ScalingAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScalingAction_CPUUpdate(), ecorePackage.getEDouble(), "CPUUpdate", null, 0, 1, ScalingAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScalingAction_CoreUpdate(), ecorePackage.getEInt(), "coreUpdate", null, 0, 1, ScalingAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScalingAction_StorageUpdate(), ecorePackage.getEInt(), "storageUpdate", null, 0, 1, ScalingAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScalingAction_IoUpdate(), ecorePackage.getEInt(), "ioUpdate", null, 0, 1, ScalingAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScalingAction_NetworkUpdate(), ecorePackage.getEInt(), "networkUpdate", null, 0, 1, ScalingAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScalingAction_VmInstance(), theDeploymentPackage.getVMInstance(), null, "vmInstance", null, 1, 1, ScalingAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScalingAction_ComponentInstance(), theDeploymentPackage.getComponentInstance(), null, "componentInstance", null, 0, 1, ScalingAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScalingAction_ContainmentInstance(), theDeploymentPackage.getHostingInstance(), null, "containmentInstance", null, 0, 1, ScalingAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(scheduleEClass, Schedule.class, "Schedule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSchedule_Start(), ecorePackage.getEDate(), "start", null, 0, 1, Schedule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSchedule_End(), ecorePackage.getEDate(), "end", null, 0, 1, Schedule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSchedule_Type(), this.getScheduleType(), "type", null, 1, 1, Schedule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSchedule_Unit(), theCamelPackage.getTimeIntervalUnit(), null, "unit", null, 1, 1, Schedule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSchedule_Repetitions(), ecorePackage.getEInt(), "repetitions", null, 0, 1, Schedule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSchedule_Interval(), ecorePackage.getELong(), "interval", null, 1, 1, Schedule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getSchedule__CheckStartEndDates__Schedule(), ecorePackage.getEBoolean(), "checkStartEndDates", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSchedule(), "this_", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getSchedule__CheckIntervalRepetitions__Schedule(), ecorePackage.getEBoolean(), "checkIntervalRepetitions", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSchedule(), "s", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(sensorEClass, Sensor.class, "Sensor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSensor_Configuration(), ecorePackage.getEString(), "configuration", null, 0, 1, Sensor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSensor_IsPush(), ecorePackage.getEBoolean(), "isPush", null, 0, 1, Sensor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(timerEClass, Timer.class, "Timer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTimer_Type(), this.getTimerType(), "type", null, 1, 1, Timer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTimer_TimeValue(), ecorePackage.getEInt(), "timeValue", null, 1, 1, Timer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTimer_MaxOccurrenceNum(), ecorePackage.getEInt(), "maxOccurrenceNum", null, 0, 1, Timer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTimer_Unit(), theCamelPackage.getTimeIntervalUnit(), null, "unit", null, 1, 1, Timer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(windowEClass, Window.class, "Window", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWindow_Unit(), theCamelPackage.getTimeIntervalUnit(), null, "unit", null, 0, 1, Window.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWindow_WindowType(), this.getWindowType(), "windowType", null, 1, 1, Window.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWindow_SizeType(), this.getWindowSizeType(), "sizeType", null, 1, 1, Window.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWindow_MeasurementSize(), ecorePackage.getELong(), "measurementSize", null, 0, 1, Window.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWindow_TimeSize(), ecorePackage.getELong(), "timeSize", null, 0, 1, Window.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(binaryPatternOperatorTypeEEnum, BinaryPatternOperatorType.class, "BinaryPatternOperatorType");
		addEEnumLiteral(binaryPatternOperatorTypeEEnum, BinaryPatternOperatorType.AND);
		addEEnumLiteral(binaryPatternOperatorTypeEEnum, BinaryPatternOperatorType.OR);
		addEEnumLiteral(binaryPatternOperatorTypeEEnum, BinaryPatternOperatorType.XOR);
		addEEnumLiteral(binaryPatternOperatorTypeEEnum, BinaryPatternOperatorType.PRECEDES);
		addEEnumLiteral(binaryPatternOperatorTypeEEnum, BinaryPatternOperatorType.REPEAT_UNTIL);
		addEEnumLiteral(binaryPatternOperatorTypeEEnum, BinaryPatternOperatorType.WHILE);

		initEEnum(comparisonOperatorTypeEEnum, ComparisonOperatorType.class, "ComparisonOperatorType");
		addEEnumLiteral(comparisonOperatorTypeEEnum, ComparisonOperatorType.GREATER_THAN);
		addEEnumLiteral(comparisonOperatorTypeEEnum, ComparisonOperatorType.GREATER_EQUAL_THAN);
		addEEnumLiteral(comparisonOperatorTypeEEnum, ComparisonOperatorType.LESS_THAN);
		addEEnumLiteral(comparisonOperatorTypeEEnum, ComparisonOperatorType.LESS_EQUAL_THAN);
		addEEnumLiteral(comparisonOperatorTypeEEnum, ComparisonOperatorType.EQUAL);
		addEEnumLiteral(comparisonOperatorTypeEEnum, ComparisonOperatorType.NOT_EQUAL);

		initEEnum(layerTypeEEnum, LayerType.class, "LayerType");
		addEEnumLiteral(layerTypeEEnum, LayerType.SAA_S);
		addEEnumLiteral(layerTypeEEnum, LayerType.PAA_S);
		addEEnumLiteral(layerTypeEEnum, LayerType.IAA_S);
		addEEnumLiteral(layerTypeEEnum, LayerType.BPM);
		addEEnumLiteral(layerTypeEEnum, LayerType.SCC);

		initEEnum(metricFunctionArityTypeEEnum, MetricFunctionArityType.class, "MetricFunctionArityType");
		addEEnumLiteral(metricFunctionArityTypeEEnum, MetricFunctionArityType.UNARY);
		addEEnumLiteral(metricFunctionArityTypeEEnum, MetricFunctionArityType.BINARY);
		addEEnumLiteral(metricFunctionArityTypeEEnum, MetricFunctionArityType.NARY);

		initEEnum(metricFunctionTypeEEnum, MetricFunctionType.class, "MetricFunctionType");
		addEEnumLiteral(metricFunctionTypeEEnum, MetricFunctionType.PLUS);
		addEEnumLiteral(metricFunctionTypeEEnum, MetricFunctionType.MINUS);
		addEEnumLiteral(metricFunctionTypeEEnum, MetricFunctionType.DIV);
		addEEnumLiteral(metricFunctionTypeEEnum, MetricFunctionType.MODULO);
		addEEnumLiteral(metricFunctionTypeEEnum, MetricFunctionType.AVERAGE);
		addEEnumLiteral(metricFunctionTypeEEnum, MetricFunctionType.MEAN);
		addEEnumLiteral(metricFunctionTypeEEnum, MetricFunctionType.STD);
		addEEnumLiteral(metricFunctionTypeEEnum, MetricFunctionType.COUNT);

		initEEnum(metricTypeEEnum, MetricType.class, "MetricType");
		addEEnumLiteral(metricTypeEEnum, MetricType.RAW);
		addEEnumLiteral(metricTypeEEnum, MetricType.COMPOSITE);

		initEEnum(propertyTypeEEnum, PropertyType.class, "PropertyType");
		addEEnumLiteral(propertyTypeEEnum, PropertyType.ABSTRACT);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.MEASURABLE);

		initEEnum(scheduleTypeEEnum, ScheduleType.class, "ScheduleType");
		addEEnumLiteral(scheduleTypeEEnum, ScheduleType.FIXED_RATE);
		addEEnumLiteral(scheduleTypeEEnum, ScheduleType.FIXED_DELAY);
		addEEnumLiteral(scheduleTypeEEnum, ScheduleType.SINGLE_EVENT);

		initEEnum(statusTypeEEnum, StatusType.class, "StatusType");
		addEEnumLiteral(statusTypeEEnum, StatusType.CRITICAL);
		addEEnumLiteral(statusTypeEEnum, StatusType.WARNING);
		addEEnumLiteral(statusTypeEEnum, StatusType.SUCCESS);
		addEEnumLiteral(statusTypeEEnum, StatusType.FATAL);

		initEEnum(timerTypeEEnum, TimerType.class, "TimerType");
		addEEnumLiteral(timerTypeEEnum, TimerType.WITHIN);
		addEEnumLiteral(timerTypeEEnum, TimerType.WITHIN_MAX);
		addEEnumLiteral(timerTypeEEnum, TimerType.INTERVAL);

		initEEnum(unaryPatternOperatorTypeEEnum, UnaryPatternOperatorType.class, "UnaryPatternOperatorType");
		addEEnumLiteral(unaryPatternOperatorTypeEEnum, UnaryPatternOperatorType.EVERY);
		addEEnumLiteral(unaryPatternOperatorTypeEEnum, UnaryPatternOperatorType.NOT);
		addEEnumLiteral(unaryPatternOperatorTypeEEnum, UnaryPatternOperatorType.REPEAT);
		addEEnumLiteral(unaryPatternOperatorTypeEEnum, UnaryPatternOperatorType.WHERE);

		initEEnum(windowSizeTypeEEnum, WindowSizeType.class, "WindowSizeType");
		addEEnumLiteral(windowSizeTypeEEnum, WindowSizeType.MEASUREMENTS_ONLY);
		addEEnumLiteral(windowSizeTypeEEnum, WindowSizeType.TIME_ONLY);
		addEEnumLiteral(windowSizeTypeEEnum, WindowSizeType.FIRST_MATCH);
		addEEnumLiteral(windowSizeTypeEEnum, WindowSizeType.BOTH_MATCH);

		initEEnum(windowTypeEEnum, WindowType.class, "WindowType");
		addEEnumLiteral(windowTypeEEnum, WindowType.FIXED);
		addEEnumLiteral(windowTypeEEnum, WindowType.SLIDING);

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
		  (metricConditionEClass, 
		   source, 
		   new String[] {
			 "constraints", "CorrectThresholdInMetricCondition"
		   });									
		addAnnotation
		  (binaryEventPatternEClass, 
		   source, 
		   new String[] {
			 "constraints", "BinaryEventPattern_At_Least_Left_Right BinaryEventPattern_Time_One_Event BinaryEventPattern_Occur"
		   });			
		addAnnotation
		  (unaryEventPatternEClass, 
		   source, 
		   new String[] {
			 "constraints", "UnaryEventPattern_correct_values_per_operator"
		   });			
		addAnnotation
		  (eventInstanceEClass, 
		   source, 
		   new String[] {
			 "constraints", "Event_Instance_Same_Layer_of_Metric_As_In_Event"
		   });					
		addAnnotation
		  (metricEClass, 
		   source, 
		   new String[] {
			 "constraints", "RAW_Metric_To_Sensor Composite_Metric_To_Components component_metrics_map_formula_templates bindings_as_in_template component_metrics_refer_to_same_level_or_lower"
		   });				
		addAnnotation
		  (metricFormulaParameterEClass, 
		   source, 
		   new String[] {
			 "constraints", "ValueParamSet_for_Non_Metric_Templates"
		   });			
		addAnnotation
		  (metricFormulaEClass, 
		   source, 
		   new String[] {
			 "constraints", "correct_arity_for_function_wrt_parameters correct_arity_for_function"
		   });			
		addAnnotation
		  (metricTemplateEClass, 
		   source, 
		   new String[] {
			 "constraints", "SingleMetric_No_Formula Measurable_Property MetricTemplate_layer_enforcement MetricTemplate_PERC_Unit_Enforcement MetricTemplate_Composite_Unit_Enforcement metric_template_components_refer_to_same_level_or_lower"
		   });	
		addAnnotation
		  (metricTemplateEClass, 
		   1,
		   "http://www.eclipse.org/emf/2002/GenModel",
		   new String[] {
			 "documentation", "invariant MetricTemplate_No_Cycle_In_Formula:\nself.type = MetricType::RAW or (self.type = MetricType::COMPOSITE and not(self.checkRecursiveness(self,self)));"
		   });				
		addAnnotation
		  (metricComponentBindingEClass, 
		   source, 
		   new String[] {
			 "constraints", "vm_and_sw_comp_connected"
		   });			
		addAnnotation
		  (metricVMBindingEClass, 
		   source, 
		   new String[] {
			 "constraints", "MetricVMBinding_vm_in_dep_model_of_app"
		   });			
		addAnnotation
		  (metricComponentInstanceBindingEClass, 
		   source, 
		   new String[] {
			 "constraints", "vm_and_sw_comp_connected"
		   });			
		addAnnotation
		  (metricVMInstanceBindingEClass, 
		   source, 
		   new String[] {
			 "constraints", "MetricVMBinding_vm_in_dep_model_of_app"
		   });				
		addAnnotation
		  (horizontalScalabilityPolicyEClass, 
		   source, 
		   new String[] {
			 "constraints", "ScalabilityPolicy_min_max_enforcement"
		   });			
		addAnnotation
		  (verticalScalabilityPolicyEClass, 
		   source, 
		   new String[] {
			 "constraints", "VertScalPol_correct_param_vals VertScalPol_activ_one_alt"
		   });			
		addAnnotation
		  (scalabilityRuleEClass, 
		   source, 
		   new String[] {
			 "constraints", "Scal_Rule_Horiz_Policy_Count Scal_Rule_Vert_Policy_Correct_Vals Scal_Rule_No_Conficting_Policies"
		   });			
		addAnnotation
		  (scalingActionEClass, 
		   source, 
		   new String[] {
			 "constraints", "scale_action_correct_count scale_action_check_type_wrt_properties"
		   });			
		addAnnotation
		  (scheduleEClass, 
		   source, 
		   new String[] {
			 "constraints", "Schedule_Start_Before_End Schedule_Correct_values"
		   });					
		addAnnotation
		  (timerEClass, 
		   source, 
		   new String[] {
			 "constraints", "Timer_correctValues"
		   });			
		addAnnotation
		  (windowEClass, 
		   source, 
		   new String[] {
			 "constraints", "window_positive_params window_right_params_exist"
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
		  (metricConditionEClass, 
		   source, 
		   new String[] {
			 "CorrectThresholdInMetricCondition", "\n\t\t\t\t\tif (metric.valueType.oclIsTypeOf(camel::type::Range)) then metric.valueType.oclAsType(camel::type::Range).includesValue(self.threshold)\n\t\t\t\t\telse if (metric.valueType.oclIsTypeOf(camel::type::RangeUnion)) then metric.valueType.oclAsType(camel::type::RangeUnion).includesValue(self.threshold)\n\t\t\t\t\telse true endif\n\t\t\t\t\tendif"
		   });		
		addAnnotation
		  (getEventPattern__IncludesEvent__SimpleEvent(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\tif (self.oclIsTypeOf(UnaryEventPattern)) then self.oclAsType(UnaryEventPattern).event = e \n\t\t\t\t\t\telse (includesLeftEvent(e) or includesRightEvent(e)) endif"
		   });		
		addAnnotation
		  (getEventPattern__IncludesLeftEvent__SimpleEvent(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).left.oclIsKindOf(EventPattern)) then self.oclAsType(BinaryEventPattern).left.oclAsType(EventPattern).includesEvent(e)\n\t\t\t\t\t\t\telse(\n\t\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).left.oclIsKindOf(SimpleEvent)) then self.oclAsType(BinaryEventPattern).left.oclAsType(SimpleEvent) = e\n\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t)\n\t\t\t\t\t\t\tendif"
		   });		
		addAnnotation
		  (getEventPattern__IncludesRightEvent__SimpleEvent(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).right.oclIsKindOf(EventPattern)) then self.oclAsType(BinaryEventPattern).right.oclAsType(EventPattern).includesEvent(e)\n\t\t\t\t\t\t\telse (\n\t\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).right.oclIsKindOf(SimpleEvent)) then self.oclAsType(BinaryEventPattern).right.oclAsType(SimpleEvent) = e else false endif\n\t\t\t\t\t\t\t\t )\n\t\t\t\t\t\t\tendif"
		   });		
		addAnnotation
		  (getEventPattern__RelatedToExecutionContext__ExecutionContext(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\t\tif (self.oclIsTypeOf(UnaryEventPattern)) then \n\t\t\t\t\t\t\t\tif (self.oclAsType(UnaryEventPattern).event.oclIsTypeOf(NonFunctionalEvent)) then self.oclAsType(UnaryEventPattern).event.oclAsType(NonFunctionalEvent).condition.metric.objectBinding.executionContext = ec\n\t\t\t\t\t\t\t\telse true endif  \n\t\t\t\t\t\t\telse (\n\t\t\t\t\t\t\t\tleftRelatedToExecutionContext(ec) and rightRelatedToExecutionContext(ec)\n\t\t\t\t\t\t\t) \n\t\t\t\t\t\t\tendif"
		   });		
		addAnnotation
		  (getEventPattern__LeftRelatedToExecutionContext__ExecutionContext(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).left.oclIsKindOf(EventPattern)) then self.oclAsType(BinaryEventPattern).left.oclAsType(EventPattern).relatedToExecutionContext(ec)\n\t\t\t\t\t\t\telse(\n\t\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).left.oclIsTypeOf(NonFunctionalEvent)) then self.oclAsType(BinaryEventPattern).left.oclAsType(NonFunctionalEvent).condition.metric.objectBinding.executionContext = ec\n\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t)\n\t\t\t\t\t\t\tendif"
		   });		
		addAnnotation
		  (getEventPattern__RightRelatedToExecutionContext__ExecutionContext(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).right.oclIsTypeOf(EventPattern)) then self.oclAsType(BinaryEventPattern).right.oclAsType(EventPattern).relatedToExecutionContext(ec)\n\t\t\t\t\t\t\telse (\n\t\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).right.oclIsTypeOf(NonFunctionalEvent)) then self.oclAsType(BinaryEventPattern).right.oclAsType(NonFunctionalEvent).condition.metric.objectBinding.executionContext = ec \n\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t)\n\t\t\t\t\t\t\tendif"
		   });			
		addAnnotation
		  (binaryEventPatternEClass, 
		   source, 
		   new String[] {
			 "BinaryEventPattern_At_Least_Left_Right", "\n\t\t\tself.left <> null or self.right <> null",
			 "BinaryEventPattern_Time_One_Event", "\n\t\t\tself.oclAsType(EventPattern).timer <> null implies (self.left = null or self.right = null)",
			 "BinaryEventPattern_Occur", "\n\t\t\t(self.operator <> BinaryPatternOperatorType::REPEAT_UNTIL implies (self.lowerOccurrenceBound = 0 and self.upperOccurrenceBound = 0)) and (self.operator = BinaryPatternOperatorType::REPEAT_UNTIL and self.lowerOccurrenceBound >= 0 and self.upperOccurrenceBound > 0 implies self.lowerOccurrenceBound <= upperOccurrenceBound)"
		   });			
		addAnnotation
		  (unaryEventPatternEClass, 
		   source, 
		   new String[] {
			 "UnaryEventPattern_correct_values_per_operator", "\n\t\t\t(self.operator = UnaryPatternOperatorType::REPEAT implies occurrenceNum > 0) and (self.operator <> UnaryPatternOperatorType::REPEAT implies occurrenceNum = 0) and (self.operator = UnaryPatternOperatorType::WHERE implies self.oclAsType(EventPattern).timer <> null) and (self.operator <> UnaryPatternOperatorType::WHERE implies self.oclAsType(EventPattern).timer = null)"
		   });			
		addAnnotation
		  (eventInstanceEClass, 
		   source, 
		   new String[] {
			 "Event_Instance_Same_Layer_of_Metric_As_In_Event", "\n\t\t\t\tif (self.onEvent.oclIsTypeOf(NonFunctionalEvent)) then self.equalLayer(self.layer,self.onEvent.oclAsType(NonFunctionalEvent).condition.metric.hasTemplate.layer) else true endif"
		   });			
		addAnnotation
		  (getEventInstance__EqualLayer__LayerType_LayerType(), 
		   source, 
		   new String[] {
			 "body", "if (l1 = LayerType::SaaS) then (if (l2 = LayerType::SaaS) then true else false endif) \n\t\t\t\t\t\telse\n\t\t\t\t\t\t\t(if (l1 = LayerType::PaaS) then \n\t\t\t\t\t\t\t\t(if (l2 = LayerType::PaaS) then true else false endif)\n\t\t\t\t\t\t\t\telse (if (l2 = LayerType::IaaS) then true else false endif)\n\t\t\t\t\t\t\t\tendif)\n\t\t\t\t\t\tendif"
		   });			
		addAnnotation
		  (metricEClass, 
		   source, 
		   new String[] {
			 "RAW_Metric_To_Sensor", "\n\t\t\t\t\t(self.hasTemplate.type = MetricType::RAW implies sensor <> null)",
			 "Composite_Metric_To_Components", "\n\t\t\t\t\t(self.hasTemplate.type = MetricType::COMPOSITE implies componentMetrics->notEmpty())",
			 "component_metrics_map_formula_templates", "\n\t\t\t\t\t(self.hasTemplate.type = MetricType::RAW) or (self.hasTemplate.type = MetricType::COMPOSITE  and self.hasTemplate.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies self.componentMetrics->exists(hasTemplate = p)) and self.hasTemplate.formula.parameters->select(p | p.oclIsTypeOf(MetricTemplate))->size() = self.componentMetrics->size())",
			 "bindings_as_in_template", "\n\t\t\t\t\t\tif (self.hasTemplate.objectBinding <> null) then\n\t\t\t\t\t\t\t(self.hasTemplate.objectBinding.oclIsTypeOf(MetricApplicationBinding) implies (\n\t\t\t\t\t\t\t\tself.objectBinding.oclIsTypeOf(MetricApplicationInstanceBinding) and \n\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricApplicationInstanceBinding).executionContext.ofApplication = self.hasTemplate.objectBinding.oclAsType(MetricApplicationBinding).application\n\t\t\t\t\t\t\t))\n\t\t\t\t\t\t\tand (self.hasTemplate.objectBinding.oclIsTypeOf(MetricComponentBinding) implies (\n\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type = self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).component\n\t\t\t\t\t\t\t\tand\n\t\t\t\t\t\t\t\tif (self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::InternalComponent)) then \n\t\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.oclIsTypeOf(camel::deployment::InternalComponentInstance) and\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).vmInstance.type= self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).vm \n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then\n\t\t\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)\n\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t))\n\t\t\t\t\t\t\tand (self.hasTemplate.objectBinding.oclIsTypeOf(MetricVMBinding) implies (\n\t\t\t\t\t\t\t\tself.objectBinding.oclIsTypeOf(MetricVMInstanceBinding) and \n\t\t\t\t\t\t\t\tself.hasTemplate.objectBinding.oclAsType(MetricVMBinding).vm = self.objectBinding.oclAsType(MetricVMInstanceBinding).vmInstance.type\n\t\t\t\t\t\t\t))\n\t\t\t\t\t\telse true\n\t\t\t\t\t\tendif",
			 "component_metrics_refer_to_same_level_or_lower", "\n\t\t\t\t\tif (objectBinding.oclIsTypeOf(MetricApplicationInstanceBinding)) then \n\t\t\t\t\t\tcomponentMetrics->forAll(p | p.objectBinding.executionContext = objectBinding.executionContext)\n\t\t\t\t\telse \n\t\t\t\t\t\tif (objectBinding.oclIsTypeOf(MetricComponentInstanceBinding)) then componentMetrics->forAll(p | p.objectBinding.executionContext = self.objectBinding.executionContext \n\t\t\t\t\t\t\tand not(p.objectBinding.oclIsTypeOf(MetricApplicationInstanceBinding)) and\n\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.oclIsKindOf(camel::deployment::InternalComponentInstance)) then\n\t\t\t\t\t\t\t\tif (p.objectBinding.oclIsTypeOf(MetricVMInstanceBinding)) then self.objectBinding.executionContext.involvesDeployment.hostingInstances->exists(q | q.providedHostInstance.componentInstance=p.objectBinding.oclAsType(MetricVMInstanceBinding).vmInstance and q.requiredHostInstance.componentInstance=self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance)\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (p.objectBinding.oclIsTypeOf(MetricComponentInstanceBinding) and p.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then self.objectBinding.executionContext.involvesDeployment.hostingInstances->exists(q | q.providedHostInstance.componentInstance=p.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance and q.requiredHostInstance.componentInstance=self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance)\n\t\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then \n\t\t\t\t\t\t\t\t\tif (p.objectBinding.oclIsTypeOf(MetricComponentInstanceBinding) and p.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then true\n\t\t\t\t\t\t\t\t\telse false endif\n\t\t\t\t\t\t\t\telse false endif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t)\n\t\t\t\t\t\telse componentMetrics->forAll(p | p.objectBinding.executionContext = objectBinding.executionContext and p.objectBinding.oclIsTypeOf(MetricVMInstanceBinding))\n\t\t\t\t\t\tendif\n\t\t\t\t\tendif"
		   });				
		addAnnotation
		  (metricFormulaParameterEClass, 
		   source, 
		   new String[] {
			 "ValueParamSet_for_Non_Metric_Templates", "\n\t\t\t\t\t\tnot(self.oclIsTypeOf(MetricTemplate) or (self.oclIsTypeOf(MetricFormula))) implies self.value <> null"
		   });			
		addAnnotation
		  (metricFormulaEClass, 
		   source, 
		   new String[] {
			 "correct_arity_for_function_wrt_parameters", "\n\t\t\t((self.functionArity = MetricFunctionArityType::UNARY) implies (self.parameters->size() = 1 and self.parameters->select(p | p.oclIsTypeOf(MetricTemplate))->size() = 1)) and ((self.functionArity = MetricFunctionArityType::BINARY) implies self.parameters->size() = 2) and ((self.functionArity = MetricFunctionArityType::N_ARY) implies self.parameters->size() >= 2)",
			 "correct_arity_for_function", "\n\t\t\t((self.function = MetricFunctionType::DIV or self.function = MetricFunctionType::MODULO or self.function = MetricFunctionType::COUNT) implies self.functionArity = MetricFunctionArityType::BINARY) and (((self.function = MetricFunctionType::AVERAGE or self.function = MetricFunctionType::MEAN or self.function = MetricFunctionType::STD) implies self.functionArity = MetricFunctionArityType::UNARY)) and (((self.function = MetricFunctionType::PLUS) implies (self.functionArity = MetricFunctionArityType::BINARY or self.functionArity = MetricFunctionArityType::N_ARY)) and ((self.function = MetricFunctionType::MINUS) implies (self.functionArity = MetricFunctionArityType::UNARY or self.functionArity = MetricFunctionArityType::BINARY)))"
		   });			
		addAnnotation
		  (metricTemplateEClass, 
		   source, 
		   new String[] {
			 "SingleMetric_No_Formula", "\n\t\t\t\t\tself.type = MetricType::RAW implies self.formula = null",
			 "Measurable_Property", "\n\t\t\t\t\tself.measures.type = PropertyType::MEASURABLE",
			 "MetricTemplate_layer_enforcement", "\n\t\t\t\t\tself.type = MetricType::RAW or (self.type = MetricType::COMPOSITE and self.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies self.greaterEqualThanLayer(self.layer, p.oclAsType(MetricTemplate).layer)))",
			 "MetricTemplate_PERC_Unit_Enforcement", "\n\t\t\t\t\tif (self.unit.unit = camel::UnitType::PERCENTAGE and self.type = MetricType::COMPOSITE) then (self.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies p.oclAsType(MetricTemplate).unit.unit = camel::UnitType::PERCENTAGE) or (self.formula.function = MetricFunctionType::DIV and self.formula.parameters->forAll(p1, p2 | (p1.oclIsTypeOf(MetricTemplate) and p2.oclIsTypeOf(MetricTemplate)) implies p1.oclAsType(MetricTemplate).unit.unit = p2.oclAsType(MetricTemplate).unit.unit))) else true endif",
			 "MetricTemplate_Composite_Unit_Enforcement", "\n\t\t\t\t\tif (self.type = MetricType::COMPOSITE and self.formula.function = MetricFunctionType::DIV) then (\n\t\t\t\t\t\tif (self.unit.unit = camel::UnitType::BYTES_PER_SECOND) then (self.formula.parameters->at(1).oclAsType(MetricTemplate).unit.unit = camel::UnitType::BYTES and self.formula.parameters->at(2).oclAsType(MetricTemplate).unit.unit = camel::UnitType::SECONDS)\n\t\t\t\t\t\telse ( \n\t\t\t\t\t\t\tif (self.unit.unit = camel::UnitType::REQUESTS_PER_SECOND) then (self.formula.parameters->at(1).oclAsType(MetricTemplate).unit.unit = camel::UnitType::REQUESTS and self.formula.parameters->at(2).oclAsType(MetricTemplate).unit.unit = camel::UnitType::SECONDS) else true endif\n\t\t\t\t\t\t)\n\t\t\t\t\t\tendif ) \n\t\t\t\t\telse true endif",
			 "metric_template_components_refer_to_same_level_or_lower", "\n\t\t\t\t\t(objectBinding <> null and self.type = MetricType::COMPOSITE) implies  \n\t\t\t\t\t(if (objectBinding.oclIsTypeOf(MetricApplicationBinding)) then \n\t\t\t\t\t\tself.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies (p.oclAsType(MetricTemplate).objectBinding <> null and p.oclAsType(MetricTemplate).objectBinding.application = objectBinding.application)) \n\t\t\t\t\telse \n\t\t\t\t\t\tif (objectBinding.oclIsTypeOf(MetricComponentBinding)) then self.formula.parameters->forAll(p | \n\t\t\t\t\t\t\tp.oclIsTypeOf(MetricTemplate) implies (p.oclAsType(MetricTemplate).objectBinding <> null and p.oclAsType(MetricTemplate).objectBinding.application = self.objectBinding.application) \n\t\t\t\t\t\t\tand not(p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricApplicationBinding)) and\n\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::InternalComponent)) then\n\t\t\t\t\t\t\t\tif (p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricVMBinding)) then self.objectBinding.application.deploymentModels->exists(q | q.hostings->exists(t | t.providedHost.component=p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricVMBinding).vm and t.requiredHost.component=self.objectBinding.oclAsType(MetricComponentBinding).component))\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricComponentBinding) and p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then self.objectBinding.application.deploymentModels->exists(t | t.hostings->exists(q | q.providedHost.component=p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricComponentBinding).component and q.requiredHost.component=self.objectBinding.oclAsType(MetricComponentBinding).component)\n\t\t\t\t\t\t\t\t\t)\n\t\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then \n\t\t\t\t\t\t\t\t\tif (p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricComponentBinding) and p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then true\n\t\t\t\t\t\t\t\t\telse false endif\n\t\t\t\t\t\t\t\telse false endif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t)\n\t\t\t\t\t\telse self.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies (p.oclAsType(MetricTemplate).objectBinding <> null and p.oclAsType(MetricTemplate).objectBinding.application = self.objectBinding.application) and p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricVMBinding))\n\t\t\t\t\t\tendif\n\t\t\t\t\tendif)"
		   });
		addAnnotation
		  (metricTemplateEClass, 
		   1,
		   "http://www.eclipse.org/emf/2002/GenModel",
		   new String[] {
			 "documentation", "invariant MetricTemplate_No_Cycle_In_Formula:\nself.type = MetricType::RAW or (self.type = MetricType::COMPOSITE and not(self.checkRecursiveness(self,self)));"
		   });			
		addAnnotation
		  (getMetricTemplate__GreaterEqualThanLayer__LayerType_LayerType(), 
		   source, 
		   new String[] {
			 "body", "if (l1 = LayerType::SaaS) then true \n\t\t\t\t\t\telse\n\t\t\t\t\t\t\t(if (l1 = LayerType::PaaS) then \n\t\t\t\t\t\t\t\t(if (l2 = LayerType::PaaS or l2 = LayerType::IaaS) then true else false endif)\n\t\t\t\t\t\t\t\telse (if (l2 = LayerType::IaaS) then true else false endif)\n\t\t\t\t\t\t\t\tendif)\n\t\t\t\t\t\tendif"
		   });			
		addAnnotation
		  (metricComponentBindingEClass, 
		   source, 
		   new String[] {
			 "vm_and_sw_comp_connected", "\n\t\t\t\t\tif (component.oclIsTypeOf(camel::deployment::InternalComponent)) then application.deploymentModels->exists(d | d.hostings->exists(c | c.requiredHost.component=vm and c.providedHost.component=component))\n\t\t\t\t\telse \n\t\t\t\t\t\tif (component.oclIsTypeOf(camel::deployment::ExternalComponent)) then vm = null\n\t\t\t\t\t\telse false endif\n\t\t\t\t\tendif"
		   });			
		addAnnotation
		  (metricVMBindingEClass, 
		   source, 
		   new String[] {
			 "MetricVMBinding_vm_in_dep_model_of_app", "\n\t\t\t\t\t\tapplication.deploymentModels->exists(d | d.components->includes(vm))"
		   });			
		addAnnotation
		  (metricComponentInstanceBindingEClass, 
		   source, 
		   new String[] {
			 "vm_and_sw_comp_connected", "\n\t\t\t\t\tif (componentInstance.oclIsTypeOf(camel::deployment::InternalComponentInstance)) then executionContext.involvesDeployment.hostingInstances->exists(c | c.requiredHostInstance.componentInstance=vmInstance and c.providedHostInstance.componentInstance=componentInstance)\n\t\t\t\t\telse \n\t\t\t\t\t\tif (componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then vmInstance = null\n\t\t\t\t\t\telse false endif\n\t\t\t\t\tendif"
		   });			
		addAnnotation
		  (metricVMInstanceBindingEClass, 
		   source, 
		   new String[] {
			 "MetricVMBinding_vm_in_dep_model_of_app", "\n\t\t\t\t\t\texecutionContext.involvesDeployment.componentInstances->includes(vmInstance)"
		   });		
		addAnnotation
		  (getProperty_Type(), 
		   source, 
		   new String[] {
			 "derivation", "if (self.oclIsTypeOf(camel::security::Certifiable)) then PropertyType::MEASURABLE else (if (self.realizedBy->isEmpty()) then PropertyType::MEASURABLE else PropertyType::ABSTRACT endif) endif"
		   });			
		addAnnotation
		  (horizontalScalabilityPolicyEClass, 
		   source, 
		   new String[] {
			 "ScalabilityPolicy_min_max_enforcement", "\n\t\t\t\t\t\t\t self.minInstances > 0 and self.maxInstances > 0 and self.minInstances <= self.maxInstances"
		   });			
		addAnnotation
		  (verticalScalabilityPolicyEClass, 
		   source, 
		   new String[] {
			 "VertScalPol_correct_param_vals", "\n\t\t\t\t\t\t\tminCores >= 0 and maxCores >= 0 and minCores <= maxCores and minMemory >= 0 and maxMemory >= 0 and minMemory <= maxMemory and minCPU >= 0 and maxCPU >= 0 and minCPU <= maxCPU and minStorage >=0 and maxStorage >= 0 and minStorage <= maxStorage",
			 "VertScalPol_activ_one_alt", "\n\t\t\t\t\t\t\tmaxCores > 0 or maxCPU > 0 or maxMemory > 0 or maxStorage > 0"
		   });			
		addAnnotation
		  (scalabilityRuleEClass, 
		   source, 
		   new String[] {
			 "Scal_Rule_Horiz_Policy_Count", "\n\t\t\t\t\t\tself.mapsToActions->forAll(p | (p.oclIsTypeOf(ScalingAction) and p.oclAsType(ScalingAction).count > 0 and (p.type = camel::ActionType::SCALE_IN or p.type = camel::ActionType::SCALE_OUT and self.invariantPolicies->exists(q | q.oclIsTypeOf(camel::scalability::HorizontalScalabilityPolicy))) implies self.invariantPolicies->forAll(t | \n\t\t\t\t\t\t\tif (t.oclIsTypeOf(camel::scalability::HorizontalScalabilityPolicy)) then p.oclAsType(ScalingAction).count <= (t.oclAsType(camel::scalability::HorizontalScalabilityPolicy).maxInstances-t.oclAsType(camel::scalability::HorizontalScalabilityPolicy).minInstances)\n\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t)))",
			 "Scal_Rule_Vert_Policy_Correct_Vals", "\n\t\t\t\t\t\tself.mapsToActions->forAll(p | (p.oclIsTypeOf(ScalingAction) and (p.type = camel::ActionType::SCALE_UP or p.type = camel::ActionType::SCALE_DOWN and self.invariantPolicies->exists(t | t.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy))) implies self.invariantPolicies->forAll(q | q.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy) implies \n\t\t\t\t\t\t\t\t\t((p.oclAsType(ScalingAction).coreUpdate > 0) implies (p.oclAsType(ScalingAction).coreUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxCores - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minCores)))\n\t\t\t\t\t\t\t\t\tand ((p.oclAsType(ScalingAction).storageUpdate > 0) implies (p.oclAsType(ScalingAction).storageUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxStorage - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minStorage)))\n\t\t\t\t\t\t\t\t\tand ((p.oclAsType(ScalingAction).CPUUpdate > 0) implies (p.oclAsType(ScalingAction).CPUUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxCPU - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minCPU)))\n\t\t\t\t\t\t\t\t\tand ((p.oclAsType(ScalingAction).memoryUpdate > 0) implies (p.oclAsType(ScalingAction).memoryUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxMemory - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minMemory)))\n\t\t\t\t\t\t)))",
			 "Scal_Rule_No_Conficting_Policies", "\n\t\t\t\t\t\tself.invariantPolicies->forAll(p1, p2 | \n\t\t\t\t\t\t\tif (p1 <> p2 and p1.oclType() = p2.oclType()) then\n\t\t\t\t\t\t\t\tif (p1.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy)) then p1.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm <> p2.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm\n\t\t\t\t\t\t\t\telse p1.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component <> p2.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse true\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t)"
		   });			
		addAnnotation
		  (scalingActionEClass, 
		   source, 
		   new String[] {
			 "scale_action_correct_count", "\n\t\t\t\t\t\tcount >= 0",
			 "scale_action_check_type_wrt_properties", "\n\t\t\t\t\t\tif (self.type = camel::ActionType::SCALE_IN or self.type = camel::ActionType::SCALE_OUT) then (componentInstance <> null and count <> 0 and (containmentInstance <> null implies (containmentInstance.providedHostInstance.componentInstance=vmInstance and containmentInstance.requiredHostInstance.componentInstance = componentInstance)))\n\t\t\t\t\t\telse (componentInstance = null and containmentInstance = null and count = 0 and (memoryUpdate <> 0 or CPUUpdate <> 0.0 or coreUpdate <> 0 or storageUpdate <> 0))\n\t\t\t\t\t\tendif"
		   });			
		addAnnotation
		  (scheduleEClass, 
		   source, 
		   new String[] {
			 "Schedule_Start_Before_End", "\n\t\t\t\t\tcheckStartEndDates(self)",
			 "Schedule_Correct_values", "\n\t\t\t\t\t(self.type <> ScheduleType::SINGLE_EVENT implies (self.interval > 0 and self.unit <> null) and checkIntervalRepetitions(self)) and ((self.type = ScheduleType::SINGLE_EVENT implies (interval = 0 and start = null and end = null and unit = null)))"
		   });					
		addAnnotation
		  (timerEClass, 
		   source, 
		   new String[] {
			 "Timer_correctValues", "\n\t\t\t\t\t\ttimeValue > 0 and (self.type = TimerType::WITHIN_MAX implies self.maxOccurrenceNum > 0) and (self.type <> TimerType::WITHIN_MAX implies self.maxOccurrenceNum = 0)"
		   });			
		addAnnotation
		  (windowEClass, 
		   source, 
		   new String[] {
			 "window_positive_params", "\n\t\t\t\t\t\t(measurementSize >= 0) and (timeSize >= 0)",
			 "window_right_params_exist", "\n\t\t\t\t\t\t(self.sizeType = WindowSizeType::MEASUREMENTS_ONLY implies (unit = null and timeSize = 0 and measurementSize > 0)) and (self.sizeType = WindowSizeType::TIME_ONLY implies (unit <> null and timeSize > 0 and measurementSize = 0)) and ((self.sizeType = WindowSizeType::FIRST_MATCH or self.sizeType = WindowSizeType::BOTH_MATCH) implies (timeSize > 0 and unit <> null and measurementSize > 0))"
		   });
	}

} //ScalabilityPackageImpl
