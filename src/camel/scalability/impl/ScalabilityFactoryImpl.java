/**
 */
package camel.scalability.impl;

import camel.scalability.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ScalabilityFactoryImpl extends EFactoryImpl implements ScalabilityFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScalabilityFactory init() {
		try {
			ScalabilityFactory theScalabilityFactory = (ScalabilityFactory)EPackage.Registry.INSTANCE.getEFactory(ScalabilityPackage.eNS_URI);
			if (theScalabilityFactory != null) {
				return theScalabilityFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ScalabilityFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalabilityFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ScalabilityPackage.SCALABILITY_MODEL: return (EObject)createScalabilityModel();
			case ScalabilityPackage.METRIC_CONDITION: return (EObject)createMetricCondition();
			case ScalabilityPackage.METRIC_TEMPLATE_CONDITION: return (EObject)createMetricTemplateCondition();
			case ScalabilityPackage.EVENT: return (EObject)createEvent();
			case ScalabilityPackage.BINARY_EVENT_PATTERN: return (EObject)createBinaryEventPattern();
			case ScalabilityPackage.UNARY_EVENT_PATTERN: return (EObject)createUnaryEventPattern();
			case ScalabilityPackage.SIMPLE_EVENT: return (EObject)createSimpleEvent();
			case ScalabilityPackage.FUNCTIONAL_EVENT: return (EObject)createFunctionalEvent();
			case ScalabilityPackage.NON_FUNCTIONAL_EVENT: return (EObject)createNonFunctionalEvent();
			case ScalabilityPackage.EVENT_INSTANCE: return (EObject)createEventInstance();
			case ScalabilityPackage.METRIC: return (EObject)createMetric();
			case ScalabilityPackage.METRIC_FORMULA_PARAMETER: return (EObject)createMetricFormulaParameter();
			case ScalabilityPackage.METRIC_FORMULA: return (EObject)createMetricFormula();
			case ScalabilityPackage.METRIC_TEMPLATE: return (EObject)createMetricTemplate();
			case ScalabilityPackage.METRIC_APPLICATION_BINDING: return (EObject)createMetricApplicationBinding();
			case ScalabilityPackage.METRIC_COMPONENT_BINDING: return (EObject)createMetricComponentBinding();
			case ScalabilityPackage.METRIC_VM_BINDING: return (EObject)createMetricVMBinding();
			case ScalabilityPackage.METRIC_APPLICATION_INSTANCE_BINDING: return (EObject)createMetricApplicationInstanceBinding();
			case ScalabilityPackage.METRIC_COMPONENT_INSTANCE_BINDING: return (EObject)createMetricComponentInstanceBinding();
			case ScalabilityPackage.METRIC_VM_INSTANCE_BINDING: return (EObject)createMetricVMInstanceBinding();
			case ScalabilityPackage.PROPERTY: return (EObject)createProperty();
			case ScalabilityPackage.HORIZONTAL_SCALABILITY_POLICY: return (EObject)createHorizontalScalabilityPolicy();
			case ScalabilityPackage.VERTICAL_SCALABILITY_POLICY: return (EObject)createVerticalScalabilityPolicy();
			case ScalabilityPackage.SCALABILITY_RULE: return (EObject)createScalabilityRule();
			case ScalabilityPackage.SCALING_ACTION: return (EObject)createScalingAction();
			case ScalabilityPackage.SCHEDULE: return (EObject)createSchedule();
			case ScalabilityPackage.SENSOR: return (EObject)createSensor();
			case ScalabilityPackage.TIMER: return (EObject)createTimer();
			case ScalabilityPackage.WINDOW: return (EObject)createWindow();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ScalabilityPackage.BINARY_PATTERN_OPERATOR_TYPE:
				return createBinaryPatternOperatorTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.COMPARISON_OPERATOR_TYPE:
				return createComparisonOperatorTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.LAYER_TYPE:
				return createLayerTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.METRIC_FUNCTION_ARITY_TYPE:
				return createMetricFunctionArityTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.METRIC_FUNCTION_TYPE:
				return createMetricFunctionTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.METRIC_TYPE:
				return createMetricTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.PROPERTY_TYPE:
				return createPropertyTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.SCHEDULE_TYPE:
				return createScheduleTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.STATUS_TYPE:
				return createStatusTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.TIMER_TYPE:
				return createTimerTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.UNARY_PATTERN_OPERATOR_TYPE:
				return createUnaryPatternOperatorTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.WINDOW_SIZE_TYPE:
				return createWindowSizeTypeFromString(eDataType, initialValue);
			case ScalabilityPackage.WINDOW_TYPE:
				return createWindowTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ScalabilityPackage.BINARY_PATTERN_OPERATOR_TYPE:
				return convertBinaryPatternOperatorTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.COMPARISON_OPERATOR_TYPE:
				return convertComparisonOperatorTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.LAYER_TYPE:
				return convertLayerTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.METRIC_FUNCTION_ARITY_TYPE:
				return convertMetricFunctionArityTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.METRIC_FUNCTION_TYPE:
				return convertMetricFunctionTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.METRIC_TYPE:
				return convertMetricTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.PROPERTY_TYPE:
				return convertPropertyTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.SCHEDULE_TYPE:
				return convertScheduleTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.STATUS_TYPE:
				return convertStatusTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.TIMER_TYPE:
				return convertTimerTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.UNARY_PATTERN_OPERATOR_TYPE:
				return convertUnaryPatternOperatorTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.WINDOW_SIZE_TYPE:
				return convertWindowSizeTypeToString(eDataType, instanceValue);
			case ScalabilityPackage.WINDOW_TYPE:
				return convertWindowTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalabilityModel createScalabilityModel() {
		ScalabilityModelImpl scalabilityModel = new ScalabilityModelImpl();
		return scalabilityModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricCondition createMetricCondition() {
		MetricConditionImpl metricCondition = new MetricConditionImpl();
		return metricCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricTemplateCondition createMetricTemplateCondition() {
		MetricTemplateConditionImpl metricTemplateCondition = new MetricTemplateConditionImpl();
		return metricTemplateCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Event createEvent() {
		EventImpl event = new EventImpl();
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryEventPattern createBinaryEventPattern() {
		BinaryEventPatternImpl binaryEventPattern = new BinaryEventPatternImpl();
		return binaryEventPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryEventPattern createUnaryEventPattern() {
		UnaryEventPatternImpl unaryEventPattern = new UnaryEventPatternImpl();
		return unaryEventPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleEvent createSimpleEvent() {
		SimpleEventImpl simpleEvent = new SimpleEventImpl();
		return simpleEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionalEvent createFunctionalEvent() {
		FunctionalEventImpl functionalEvent = new FunctionalEventImpl();
		return functionalEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonFunctionalEvent createNonFunctionalEvent() {
		NonFunctionalEventImpl nonFunctionalEvent = new NonFunctionalEventImpl();
		return nonFunctionalEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventInstance createEventInstance() {
		EventInstanceImpl eventInstance = new EventInstanceImpl();
		return eventInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Metric createMetric() {
		MetricImpl metric = new MetricImpl();
		return metric;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricFormulaParameter createMetricFormulaParameter() {
		MetricFormulaParameterImpl metricFormulaParameter = new MetricFormulaParameterImpl();
		return metricFormulaParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricFormula createMetricFormula() {
		MetricFormulaImpl metricFormula = new MetricFormulaImpl();
		return metricFormula;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricTemplate createMetricTemplate() {
		MetricTemplateImpl metricTemplate = new MetricTemplateImpl();
		return metricTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricApplicationBinding createMetricApplicationBinding() {
		MetricApplicationBindingImpl metricApplicationBinding = new MetricApplicationBindingImpl();
		return metricApplicationBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricComponentBinding createMetricComponentBinding() {
		MetricComponentBindingImpl metricComponentBinding = new MetricComponentBindingImpl();
		return metricComponentBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricVMBinding createMetricVMBinding() {
		MetricVMBindingImpl metricVMBinding = new MetricVMBindingImpl();
		return metricVMBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricApplicationInstanceBinding createMetricApplicationInstanceBinding() {
		MetricApplicationInstanceBindingImpl metricApplicationInstanceBinding = new MetricApplicationInstanceBindingImpl();
		return metricApplicationInstanceBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricComponentInstanceBinding createMetricComponentInstanceBinding() {
		MetricComponentInstanceBindingImpl metricComponentInstanceBinding = new MetricComponentInstanceBindingImpl();
		return metricComponentInstanceBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricVMInstanceBinding createMetricVMInstanceBinding() {
		MetricVMInstanceBindingImpl metricVMInstanceBinding = new MetricVMInstanceBindingImpl();
		return metricVMInstanceBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HorizontalScalabilityPolicy createHorizontalScalabilityPolicy() {
		HorizontalScalabilityPolicyImpl horizontalScalabilityPolicy = new HorizontalScalabilityPolicyImpl();
		return horizontalScalabilityPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VerticalScalabilityPolicy createVerticalScalabilityPolicy() {
		VerticalScalabilityPolicyImpl verticalScalabilityPolicy = new VerticalScalabilityPolicyImpl();
		return verticalScalabilityPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalabilityRule createScalabilityRule() {
		ScalabilityRuleImpl scalabilityRule = new ScalabilityRuleImpl();
		return scalabilityRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalingAction createScalingAction() {
		ScalingActionImpl scalingAction = new ScalingActionImpl();
		return scalingAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Schedule createSchedule() {
		ScheduleImpl schedule = new ScheduleImpl();
		return schedule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sensor createSensor() {
		SensorImpl sensor = new SensorImpl();
		return sensor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Timer createTimer() {
		TimerImpl timer = new TimerImpl();
		return timer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Window createWindow() {
		WindowImpl window = new WindowImpl();
		return window;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryPatternOperatorType createBinaryPatternOperatorTypeFromString(EDataType eDataType, String initialValue) {
		BinaryPatternOperatorType result = BinaryPatternOperatorType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBinaryPatternOperatorTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComparisonOperatorType createComparisonOperatorTypeFromString(EDataType eDataType, String initialValue) {
		ComparisonOperatorType result = ComparisonOperatorType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertComparisonOperatorTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LayerType createLayerTypeFromString(EDataType eDataType, String initialValue) {
		LayerType result = LayerType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLayerTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricFunctionArityType createMetricFunctionArityTypeFromString(EDataType eDataType, String initialValue) {
		MetricFunctionArityType result = MetricFunctionArityType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMetricFunctionArityTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricFunctionType createMetricFunctionTypeFromString(EDataType eDataType, String initialValue) {
		MetricFunctionType result = MetricFunctionType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMetricFunctionTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricType createMetricTypeFromString(EDataType eDataType, String initialValue) {
		MetricType result = MetricType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMetricTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType createPropertyTypeFromString(EDataType eDataType, String initialValue) {
		PropertyType result = PropertyType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPropertyTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScheduleType createScheduleTypeFromString(EDataType eDataType, String initialValue) {
		ScheduleType result = ScheduleType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertScheduleTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusType createStatusTypeFromString(EDataType eDataType, String initialValue) {
		StatusType result = StatusType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStatusTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimerType createTimerTypeFromString(EDataType eDataType, String initialValue) {
		TimerType result = TimerType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTimerTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryPatternOperatorType createUnaryPatternOperatorTypeFromString(EDataType eDataType, String initialValue) {
		UnaryPatternOperatorType result = UnaryPatternOperatorType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUnaryPatternOperatorTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowSizeType createWindowSizeTypeFromString(EDataType eDataType, String initialValue) {
		WindowSizeType result = WindowSizeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertWindowSizeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowType createWindowTypeFromString(EDataType eDataType, String initialValue) {
		WindowType result = WindowType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertWindowTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalabilityPackage getScalabilityPackage() {
		return (ScalabilityPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ScalabilityPackage getPackage() {
		return ScalabilityPackage.eINSTANCE;
	}

} //ScalabilityFactoryImpl
