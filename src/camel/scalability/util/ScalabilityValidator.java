/**
 */
package camel.scalability.util;

import camel.scalability.*;

import camel.util.CamelValidator;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see camel.scalability.ScalabilityPackage
 * @generated
 */
public class ScalabilityValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ScalabilityValidator INSTANCE = new ScalabilityValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "camel.scalability";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CamelValidator camelValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalabilityValidator() {
		super();
		camelValidator = CamelValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return ScalabilityPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case ScalabilityPackage.SCALABILITY_MODEL:
				return validateScalabilityModel((ScalabilityModel)value, diagnostics, context);
			case ScalabilityPackage.CONDITION:
				return validateCondition((Condition)value, diagnostics, context);
			case ScalabilityPackage.METRIC_CONDITION:
				return validateMetricCondition((MetricCondition)value, diagnostics, context);
			case ScalabilityPackage.METRIC_TEMPLATE_CONDITION:
				return validateMetricTemplateCondition((MetricTemplateCondition)value, diagnostics, context);
			case ScalabilityPackage.EVENT:
				return validateEvent((Event)value, diagnostics, context);
			case ScalabilityPackage.EVENT_PATTERN:
				return validateEventPattern((EventPattern)value, diagnostics, context);
			case ScalabilityPackage.BINARY_EVENT_PATTERN:
				return validateBinaryEventPattern((BinaryEventPattern)value, diagnostics, context);
			case ScalabilityPackage.UNARY_EVENT_PATTERN:
				return validateUnaryEventPattern((UnaryEventPattern)value, diagnostics, context);
			case ScalabilityPackage.SIMPLE_EVENT:
				return validateSimpleEvent((SimpleEvent)value, diagnostics, context);
			case ScalabilityPackage.FUNCTIONAL_EVENT:
				return validateFunctionalEvent((FunctionalEvent)value, diagnostics, context);
			case ScalabilityPackage.NON_FUNCTIONAL_EVENT:
				return validateNonFunctionalEvent((NonFunctionalEvent)value, diagnostics, context);
			case ScalabilityPackage.EVENT_INSTANCE:
				return validateEventInstance((EventInstance)value, diagnostics, context);
			case ScalabilityPackage.METRIC:
				return validateMetric((Metric)value, diagnostics, context);
			case ScalabilityPackage.METRIC_FORMULA_PARAMETER:
				return validateMetricFormulaParameter((MetricFormulaParameter)value, diagnostics, context);
			case ScalabilityPackage.METRIC_FORMULA:
				return validateMetricFormula((MetricFormula)value, diagnostics, context);
			case ScalabilityPackage.METRIC_TEMPLATE:
				return validateMetricTemplate((MetricTemplate)value, diagnostics, context);
			case ScalabilityPackage.METRIC_OBJECT_BINDING:
				return validateMetricObjectBinding((MetricObjectBinding)value, diagnostics, context);
			case ScalabilityPackage.METRIC_APPLICATION_BINDING:
				return validateMetricApplicationBinding((MetricApplicationBinding)value, diagnostics, context);
			case ScalabilityPackage.METRIC_COMPONENT_BINDING:
				return validateMetricComponentBinding((MetricComponentBinding)value, diagnostics, context);
			case ScalabilityPackage.METRIC_VM_BINDING:
				return validateMetricVMBinding((MetricVMBinding)value, diagnostics, context);
			case ScalabilityPackage.METRIC_OBJECT_INSTANCE_BINDING:
				return validateMetricObjectInstanceBinding((MetricObjectInstanceBinding)value, diagnostics, context);
			case ScalabilityPackage.METRIC_APPLICATION_INSTANCE_BINDING:
				return validateMetricApplicationInstanceBinding((MetricApplicationInstanceBinding)value, diagnostics, context);
			case ScalabilityPackage.METRIC_COMPONENT_INSTANCE_BINDING:
				return validateMetricComponentInstanceBinding((MetricComponentInstanceBinding)value, diagnostics, context);
			case ScalabilityPackage.METRIC_VM_INSTANCE_BINDING:
				return validateMetricVMInstanceBinding((MetricVMInstanceBinding)value, diagnostics, context);
			case ScalabilityPackage.PROPERTY:
				return validateProperty((Property)value, diagnostics, context);
			case ScalabilityPackage.SCALABILITY_POLICY:
				return validateScalabilityPolicy((ScalabilityPolicy)value, diagnostics, context);
			case ScalabilityPackage.HORIZONTAL_SCALABILITY_POLICY:
				return validateHorizontalScalabilityPolicy((HorizontalScalabilityPolicy)value, diagnostics, context);
			case ScalabilityPackage.VERTICAL_SCALABILITY_POLICY:
				return validateVerticalScalabilityPolicy((VerticalScalabilityPolicy)value, diagnostics, context);
			case ScalabilityPackage.SCALABILITY_RULE:
				return validateScalabilityRule((ScalabilityRule)value, diagnostics, context);
			case ScalabilityPackage.SCALING_ACTION:
				return validateScalingAction((ScalingAction)value, diagnostics, context);
			case ScalabilityPackage.SCHEDULE:
				return validateSchedule((Schedule)value, diagnostics, context);
			case ScalabilityPackage.SENSOR:
				return validateSensor((Sensor)value, diagnostics, context);
			case ScalabilityPackage.TIMER:
				return validateTimer((Timer)value, diagnostics, context);
			case ScalabilityPackage.WINDOW:
				return validateWindow((Window)value, diagnostics, context);
			case ScalabilityPackage.BINARY_PATTERN_OPERATOR_TYPE:
				return validateBinaryPatternOperatorType((BinaryPatternOperatorType)value, diagnostics, context);
			case ScalabilityPackage.COMPARISON_OPERATOR_TYPE:
				return validateComparisonOperatorType((ComparisonOperatorType)value, diagnostics, context);
			case ScalabilityPackage.LAYER_TYPE:
				return validateLayerType((LayerType)value, diagnostics, context);
			case ScalabilityPackage.METRIC_FUNCTION_ARITY_TYPE:
				return validateMetricFunctionArityType((MetricFunctionArityType)value, diagnostics, context);
			case ScalabilityPackage.METRIC_FUNCTION_TYPE:
				return validateMetricFunctionType((MetricFunctionType)value, diagnostics, context);
			case ScalabilityPackage.METRIC_TYPE:
				return validateMetricType((MetricType)value, diagnostics, context);
			case ScalabilityPackage.PROPERTY_TYPE:
				return validatePropertyType((PropertyType)value, diagnostics, context);
			case ScalabilityPackage.SCHEDULE_TYPE:
				return validateScheduleType((ScheduleType)value, diagnostics, context);
			case ScalabilityPackage.STATUS_TYPE:
				return validateStatusType((StatusType)value, diagnostics, context);
			case ScalabilityPackage.TIMER_TYPE:
				return validateTimerType((TimerType)value, diagnostics, context);
			case ScalabilityPackage.UNARY_PATTERN_OPERATOR_TYPE:
				return validateUnaryPatternOperatorType((UnaryPatternOperatorType)value, diagnostics, context);
			case ScalabilityPackage.WINDOW_SIZE_TYPE:
				return validateWindowSizeType((WindowSizeType)value, diagnostics, context);
			case ScalabilityPackage.WINDOW_TYPE:
				return validateWindowType((WindowType)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScalabilityModel(ScalabilityModel scalabilityModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)scalabilityModel, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCondition(Condition condition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)condition, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricCondition(MetricCondition metricCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)metricCondition, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)metricCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)metricCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)metricCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)metricCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)metricCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)metricCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)metricCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)metricCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricCondition_CorrectThresholdInMetricCondition(metricCondition, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the CorrectThresholdInMetricCondition constraint of '<em>Metric Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_CONDITION__CORRECT_THRESHOLD_IN_METRIC_CONDITION__EEXPRESSION = "\n" +
		"\t\t\t\t\tif (metric.valueType.oclIsTypeOf(camel::type::Range)) then metric.valueType.oclAsType(camel::type::Range).includesValue(self.threshold)\n" +
		"\t\t\t\t\telse if (metric.valueType.oclIsTypeOf(camel::type::RangeUnion)) then metric.valueType.oclAsType(camel::type::RangeUnion).includesValue(self.threshold)\n" +
		"\t\t\t\t\telse true endif\n" +
		"\t\t\t\t\tendif";

	/**
	 * Validates the CorrectThresholdInMetricCondition constraint of '<em>Metric Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricCondition_CorrectThresholdInMetricCondition(MetricCondition metricCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_CONDITION,
				 metricCondition,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "CorrectThresholdInMetricCondition",
				 METRIC_CONDITION__CORRECT_THRESHOLD_IN_METRIC_CONDITION__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricTemplateCondition(MetricTemplateCondition metricTemplateCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)metricTemplateCondition, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEvent(Event event, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)event, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEventPattern(EventPattern eventPattern, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)eventPattern, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBinaryEventPattern(BinaryEventPattern binaryEventPattern, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)binaryEventPattern, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)binaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)binaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)binaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)binaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)binaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)binaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)binaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)binaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validateBinaryEventPattern_BinaryEventPattern_At_Least_Left_Right(binaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validateBinaryEventPattern_BinaryEventPattern_Time_One_Event(binaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validateBinaryEventPattern_BinaryEventPattern_Occur(binaryEventPattern, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the BinaryEventPattern_At_Least_Left_Right constraint of '<em>Binary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String BINARY_EVENT_PATTERN__BINARY_EVENT_PATTERN_AT_LEAST_LEFT_RIGHT__EEXPRESSION = "\n" +
		"\t\t\tself.left <> null or self.right <> null";

	/**
	 * Validates the BinaryEventPattern_At_Least_Left_Right constraint of '<em>Binary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBinaryEventPattern_BinaryEventPattern_At_Least_Left_Right(BinaryEventPattern binaryEventPattern, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN,
				 binaryEventPattern,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "BinaryEventPattern_At_Least_Left_Right",
				 BINARY_EVENT_PATTERN__BINARY_EVENT_PATTERN_AT_LEAST_LEFT_RIGHT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the BinaryEventPattern_Time_One_Event constraint of '<em>Binary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String BINARY_EVENT_PATTERN__BINARY_EVENT_PATTERN_TIME_ONE_EVENT__EEXPRESSION = "\n" +
		"\t\t\tself.oclAsType(EventPattern).timer <> null implies (self.left = null or self.right = null)";

	/**
	 * Validates the BinaryEventPattern_Time_One_Event constraint of '<em>Binary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBinaryEventPattern_BinaryEventPattern_Time_One_Event(BinaryEventPattern binaryEventPattern, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN,
				 binaryEventPattern,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "BinaryEventPattern_Time_One_Event",
				 BINARY_EVENT_PATTERN__BINARY_EVENT_PATTERN_TIME_ONE_EVENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the BinaryEventPattern_Occur constraint of '<em>Binary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String BINARY_EVENT_PATTERN__BINARY_EVENT_PATTERN_OCCUR__EEXPRESSION = "\n" +
		"\t\t\t(self.operator <> BinaryPatternOperatorType::REPEAT_UNTIL implies (self.lowerOccurrenceBound = 0 and self.upperOccurrenceBound = 0)) and (self.operator = BinaryPatternOperatorType::REPEAT_UNTIL and self.lowerOccurrenceBound >= 0 and self.upperOccurrenceBound > 0 implies self.lowerOccurrenceBound <= upperOccurrenceBound)";

	/**
	 * Validates the BinaryEventPattern_Occur constraint of '<em>Binary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBinaryEventPattern_BinaryEventPattern_Occur(BinaryEventPattern binaryEventPattern, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.BINARY_EVENT_PATTERN,
				 binaryEventPattern,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "BinaryEventPattern_Occur",
				 BINARY_EVENT_PATTERN__BINARY_EVENT_PATTERN_OCCUR__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnaryEventPattern(UnaryEventPattern unaryEventPattern, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)unaryEventPattern, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)unaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)unaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)unaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)unaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)unaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)unaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)unaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)unaryEventPattern, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnaryEventPattern_UnaryEventPattern_correct_values_per_operator(unaryEventPattern, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the UnaryEventPattern_correct_values_per_operator constraint of '<em>Unary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String UNARY_EVENT_PATTERN__UNARY_EVENT_PATTERN_CORRECT_VALUES_PER_OPERATOR__EEXPRESSION = "\n" +
		"\t\t\t(self.operator = UnaryPatternOperatorType::REPEAT implies occurrenceNum > 0) and (self.operator <> UnaryPatternOperatorType::REPEAT implies occurrenceNum = 0) and (self.operator = UnaryPatternOperatorType::WHERE implies self.oclAsType(EventPattern).timer <> null) and (self.operator <> UnaryPatternOperatorType::WHERE implies self.oclAsType(EventPattern).timer = null)";

	/**
	 * Validates the UnaryEventPattern_correct_values_per_operator constraint of '<em>Unary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnaryEventPattern_UnaryEventPattern_correct_values_per_operator(UnaryEventPattern unaryEventPattern, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.UNARY_EVENT_PATTERN,
				 unaryEventPattern,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "UnaryEventPattern_correct_values_per_operator",
				 UNARY_EVENT_PATTERN__UNARY_EVENT_PATTERN_CORRECT_VALUES_PER_OPERATOR__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSimpleEvent(SimpleEvent simpleEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)simpleEvent, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFunctionalEvent(FunctionalEvent functionalEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)functionalEvent, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNonFunctionalEvent(NonFunctionalEvent nonFunctionalEvent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)nonFunctionalEvent, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEventInstance(EventInstance eventInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)eventInstance, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)eventInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)eventInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)eventInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)eventInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)eventInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)eventInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)eventInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)eventInstance, diagnostics, context);
		if (result || diagnostics != null) result &= validateEventInstance_Event_Instance_Same_Layer_of_Metric_As_In_Event(eventInstance, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Event_Instance_Same_Layer_of_Metric_As_In_Event constraint of '<em>Event Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String EVENT_INSTANCE__EVENT_INSTANCE_SAME_LAYER_OF_METRIC_AS_IN_EVENT__EEXPRESSION = "\n" +
		"\t\t\t\tif (self.onEvent.oclIsTypeOf(NonFunctionalEvent)) then self.equalLayer(self.layer,self.onEvent.oclAsType(NonFunctionalEvent).condition.metric.hasTemplate.layer) else true endif";

	/**
	 * Validates the Event_Instance_Same_Layer_of_Metric_As_In_Event constraint of '<em>Event Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEventInstance_Event_Instance_Same_Layer_of_Metric_As_In_Event(EventInstance eventInstance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.EVENT_INSTANCE,
				 eventInstance,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Event_Instance_Same_Layer_of_Metric_As_In_Event",
				 EVENT_INSTANCE__EVENT_INSTANCE_SAME_LAYER_OF_METRIC_AS_IN_EVENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetric(Metric metric, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)metric, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)metric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)metric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)metric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)metric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)metric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)metric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)metric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)metric, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetric_RAW_Metric_To_Sensor(metric, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetric_Composite_Metric_To_Components(metric, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetric_component_metrics_map_formula_templates(metric, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetric_bindings_as_in_template(metric, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetric_component_metrics_refer_to_same_level_or_lower(metric, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the RAW_Metric_To_Sensor constraint of '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC__RAW_METRIC_TO_SENSOR__EEXPRESSION = "\n" +
		"\t\t\t\t\t(self.hasTemplate.type = MetricType::RAW implies sensor <> null)";

	/**
	 * Validates the RAW_Metric_To_Sensor constraint of '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetric_RAW_Metric_To_Sensor(Metric metric, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC,
				 metric,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "RAW_Metric_To_Sensor",
				 METRIC__RAW_METRIC_TO_SENSOR__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Composite_Metric_To_Components constraint of '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC__COMPOSITE_METRIC_TO_COMPONENTS__EEXPRESSION = "\n" +
		"\t\t\t\t\t(self.hasTemplate.type = MetricType::COMPOSITE implies componentMetrics->notEmpty())";

	/**
	 * Validates the Composite_Metric_To_Components constraint of '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetric_Composite_Metric_To_Components(Metric metric, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC,
				 metric,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Composite_Metric_To_Components",
				 METRIC__COMPOSITE_METRIC_TO_COMPONENTS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the component_metrics_map_formula_templates constraint of '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC__COMPONENT_METRICS_MAP_FORMULA_TEMPLATES__EEXPRESSION = "\n" +
		"\t\t\t\t\t(self.hasTemplate.type = MetricType::RAW) or (self.hasTemplate.type = MetricType::COMPOSITE  and self.hasTemplate.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies self.componentMetrics->exists(hasTemplate = p)) and self.hasTemplate.formula.parameters->select(p | p.oclIsTypeOf(MetricTemplate))->size() = self.componentMetrics->size())";

	/**
	 * Validates the component_metrics_map_formula_templates constraint of '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetric_component_metrics_map_formula_templates(Metric metric, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC,
				 metric,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "component_metrics_map_formula_templates",
				 METRIC__COMPONENT_METRICS_MAP_FORMULA_TEMPLATES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the bindings_as_in_template constraint of '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC__BINDINGS_AS_IN_TEMPLATE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tif (self.hasTemplate.objectBinding <> null) then\n" +
		"\t\t\t\t\t\t\t(self.hasTemplate.objectBinding.oclIsTypeOf(MetricApplicationBinding) implies (\n" +
		"\t\t\t\t\t\t\t\tself.objectBinding.oclIsTypeOf(MetricApplicationInstanceBinding) and \n" +
		"\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricApplicationInstanceBinding).executionContext.ofApplication = self.hasTemplate.objectBinding.oclAsType(MetricApplicationBinding).application\n" +
		"\t\t\t\t\t\t\t))\n" +
		"\t\t\t\t\t\t\tand (self.hasTemplate.objectBinding.oclIsTypeOf(MetricComponentBinding) implies (\n" +
		"\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type = self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).component\n" +
		"\t\t\t\t\t\t\t\tand\n" +
		"\t\t\t\t\t\t\t\tif (self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::InternalComponent)) then \n" +
		"\t\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.oclIsTypeOf(camel::deployment::InternalComponentInstance) and\t\t\t\t\t\t\t\t\t\n" +
		"\t\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).vmInstance.type= self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).vm \n" +
		"\t\t\t\t\t\t\t\telse \n" +
		"\t\t\t\t\t\t\t\t\tif (self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then\n" +
		"\t\t\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)\n" +
		"\t\t\t\t\t\t\t\t\telse false\n" +
		"\t\t\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t\t))\n" +
		"\t\t\t\t\t\t\tand (self.hasTemplate.objectBinding.oclIsTypeOf(MetricVMBinding) implies (\n" +
		"\t\t\t\t\t\t\t\tself.objectBinding.oclIsTypeOf(MetricVMInstanceBinding) and \n" +
		"\t\t\t\t\t\t\t\tself.hasTemplate.objectBinding.oclAsType(MetricVMBinding).vm = self.objectBinding.oclAsType(MetricVMInstanceBinding).vmInstance.type\n" +
		"\t\t\t\t\t\t\t))\n" +
		"\t\t\t\t\t\telse true\n" +
		"\t\t\t\t\t\tendif";

	/**
	 * Validates the bindings_as_in_template constraint of '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetric_bindings_as_in_template(Metric metric, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC,
				 metric,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "bindings_as_in_template",
				 METRIC__BINDINGS_AS_IN_TEMPLATE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the component_metrics_refer_to_same_level_or_lower constraint of '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC__COMPONENT_METRICS_REFER_TO_SAME_LEVEL_OR_LOWER__EEXPRESSION = "\n" +
		"\t\t\t\t\tif (objectBinding.oclIsTypeOf(MetricApplicationInstanceBinding)) then \n" +
		"\t\t\t\t\t\tcomponentMetrics->forAll(p | p.objectBinding.executionContext = objectBinding.executionContext)\n" +
		"\t\t\t\t\telse \n" +
		"\t\t\t\t\t\tif (objectBinding.oclIsTypeOf(MetricComponentInstanceBinding)) then componentMetrics->forAll(p | p.objectBinding.executionContext = self.objectBinding.executionContext \n" +
		"\t\t\t\t\t\t\tand not(p.objectBinding.oclIsTypeOf(MetricApplicationInstanceBinding)) and\n" +
		"\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.oclIsKindOf(camel::deployment::InternalComponentInstance)) then\n" +
		"\t\t\t\t\t\t\t\tif (p.objectBinding.oclIsTypeOf(MetricVMInstanceBinding)) then self.objectBinding.executionContext.involvesDeployment.hostingInstances->exists(q | q.providedHostInstance.componentInstance=p.objectBinding.oclAsType(MetricVMInstanceBinding).vmInstance and q.requiredHostInstance.componentInstance=self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance)\n" +
		"\t\t\t\t\t\t\t\telse \n" +
		"\t\t\t\t\t\t\t\t\tif (p.objectBinding.oclIsTypeOf(MetricComponentInstanceBinding) and p.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then self.objectBinding.executionContext.involvesDeployment.hostingInstances->exists(q | q.providedHostInstance.componentInstance=p.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance and q.requiredHostInstance.componentInstance=self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance)\n" +
		"\t\t\t\t\t\t\t\t\telse true endif\n" +
		"\t\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t\telse\n" +
		"\t\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then \n" +
		"\t\t\t\t\t\t\t\t\tif (p.objectBinding.oclIsTypeOf(MetricComponentInstanceBinding) and p.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then true\n" +
		"\t\t\t\t\t\t\t\t\telse false endif\n" +
		"\t\t\t\t\t\t\t\telse false endif\n" +
		"\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t)\n" +
		"\t\t\t\t\t\telse componentMetrics->forAll(p | p.objectBinding.executionContext = objectBinding.executionContext and p.objectBinding.oclIsTypeOf(MetricVMInstanceBinding))\n" +
		"\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\tendif";

	/**
	 * Validates the component_metrics_refer_to_same_level_or_lower constraint of '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetric_component_metrics_refer_to_same_level_or_lower(Metric metric, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC,
				 metric,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "component_metrics_refer_to_same_level_or_lower",
				 METRIC__COMPONENT_METRICS_REFER_TO_SAME_LEVEL_OR_LOWER__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricFormulaParameter(MetricFormulaParameter metricFormulaParameter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)metricFormulaParameter, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)metricFormulaParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)metricFormulaParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)metricFormulaParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)metricFormulaParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)metricFormulaParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)metricFormulaParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)metricFormulaParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)metricFormulaParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricFormulaParameter_ValueParamSet_for_Non_Metric_Templates(metricFormulaParameter, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ValueParamSet_for_Non_Metric_Templates constraint of '<em>Metric Formula Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_FORMULA_PARAMETER__VALUE_PARAM_SET_FOR_NON_METRIC_TEMPLATES__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tnot(self.oclIsTypeOf(MetricTemplate) or (self.oclIsTypeOf(MetricFormula))) implies self.value <> null";

	/**
	 * Validates the ValueParamSet_for_Non_Metric_Templates constraint of '<em>Metric Formula Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricFormulaParameter_ValueParamSet_for_Non_Metric_Templates(MetricFormulaParameter metricFormulaParameter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_FORMULA_PARAMETER,
				 metricFormulaParameter,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ValueParamSet_for_Non_Metric_Templates",
				 METRIC_FORMULA_PARAMETER__VALUE_PARAM_SET_FOR_NON_METRIC_TEMPLATES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricFormula(MetricFormula metricFormula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)metricFormula, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)metricFormula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)metricFormula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)metricFormula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)metricFormula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)metricFormula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)metricFormula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)metricFormula, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)metricFormula, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricFormulaParameter_ValueParamSet_for_Non_Metric_Templates(metricFormula, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricFormula_correct_arity_for_function_wrt_parameters(metricFormula, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricFormula_correct_arity_for_function(metricFormula, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the correct_arity_for_function_wrt_parameters constraint of '<em>Metric Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_FORMULA__CORRECT_ARITY_FOR_FUNCTION_WRT_PARAMETERS__EEXPRESSION = "\n" +
		"\t\t\t((self.functionArity = MetricFunctionArityType::UNARY) implies (self.parameters->size() = 1 and self.parameters->select(p | p.oclIsTypeOf(MetricTemplate))->size() = 1)) and ((self.functionArity = MetricFunctionArityType::BINARY) implies self.parameters->size() = 2) and ((self.functionArity = MetricFunctionArityType::N_ARY) implies self.parameters->size() >= 2)";

	/**
	 * Validates the correct_arity_for_function_wrt_parameters constraint of '<em>Metric Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricFormula_correct_arity_for_function_wrt_parameters(MetricFormula metricFormula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_FORMULA,
				 metricFormula,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "correct_arity_for_function_wrt_parameters",
				 METRIC_FORMULA__CORRECT_ARITY_FOR_FUNCTION_WRT_PARAMETERS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the correct_arity_for_function constraint of '<em>Metric Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_FORMULA__CORRECT_ARITY_FOR_FUNCTION__EEXPRESSION = "\n" +
		"\t\t\t((self.function = MetricFunctionType::DIV or self.function = MetricFunctionType::MODULO or self.function = MetricFunctionType::COUNT) implies self.functionArity = MetricFunctionArityType::BINARY) and (((self.function = MetricFunctionType::AVERAGE or self.function = MetricFunctionType::MEAN or self.function = MetricFunctionType::STD) implies self.functionArity = MetricFunctionArityType::UNARY)) and (((self.function = MetricFunctionType::PLUS) implies (self.functionArity = MetricFunctionArityType::BINARY or self.functionArity = MetricFunctionArityType::N_ARY)) and ((self.function = MetricFunctionType::MINUS) implies (self.functionArity = MetricFunctionArityType::UNARY or self.functionArity = MetricFunctionArityType::BINARY)))";

	/**
	 * Validates the correct_arity_for_function constraint of '<em>Metric Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricFormula_correct_arity_for_function(MetricFormula metricFormula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_FORMULA,
				 metricFormula,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "correct_arity_for_function",
				 METRIC_FORMULA__CORRECT_ARITY_FOR_FUNCTION__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricTemplate(MetricTemplate metricTemplate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)metricTemplate, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricFormulaParameter_ValueParamSet_for_Non_Metric_Templates(metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricTemplate_SingleMetric_No_Formula(metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricTemplate_Measurable_Property(metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricTemplate_MetricTemplate_layer_enforcement(metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricTemplate_MetricTemplate_PERC_Unit_Enforcement(metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricTemplate_MetricTemplate_Composite_Unit_Enforcement(metricTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricTemplate_metric_template_components_refer_to_same_level_or_lower(metricTemplate, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the SingleMetric_No_Formula constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_TEMPLATE__SINGLE_METRIC_NO_FORMULA__EEXPRESSION = "\n" +
		"\t\t\t\t\tself.type = MetricType::RAW implies self.formula = null";

	/**
	 * Validates the SingleMetric_No_Formula constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricTemplate_SingleMetric_No_Formula(MetricTemplate metricTemplate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_TEMPLATE,
				 metricTemplate,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "SingleMetric_No_Formula",
				 METRIC_TEMPLATE__SINGLE_METRIC_NO_FORMULA__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Measurable_Property constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_TEMPLATE__MEASURABLE_PROPERTY__EEXPRESSION = "\n" +
		"\t\t\t\t\tself.measures.type = PropertyType::MEASURABLE";

	/**
	 * Validates the Measurable_Property constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricTemplate_Measurable_Property(MetricTemplate metricTemplate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_TEMPLATE,
				 metricTemplate,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Measurable_Property",
				 METRIC_TEMPLATE__MEASURABLE_PROPERTY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the MetricTemplate_layer_enforcement constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_TEMPLATE__METRIC_TEMPLATE_LAYER_ENFORCEMENT__EEXPRESSION = "\n" +
		"\t\t\t\t\tself.type = MetricType::RAW or (self.type = MetricType::COMPOSITE and self.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies self.greaterEqualThanLayer(self.layer, p.oclAsType(MetricTemplate).layer)))";

	/**
	 * Validates the MetricTemplate_layer_enforcement constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricTemplate_MetricTemplate_layer_enforcement(MetricTemplate metricTemplate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_TEMPLATE,
				 metricTemplate,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "MetricTemplate_layer_enforcement",
				 METRIC_TEMPLATE__METRIC_TEMPLATE_LAYER_ENFORCEMENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the MetricTemplate_PERC_Unit_Enforcement constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_TEMPLATE__METRIC_TEMPLATE_PERC_UNIT_ENFORCEMENT__EEXPRESSION = "\n" +
		"\t\t\t\t\tif (self.unit.unit = camel::UnitType::PERCENTAGE and self.type = MetricType::COMPOSITE) then (self.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies p.oclAsType(MetricTemplate).unit.unit = camel::UnitType::PERCENTAGE) or (self.formula.function = MetricFunctionType::DIV and self.formula.parameters->forAll(p1, p2 | (p1.oclIsTypeOf(MetricTemplate) and p2.oclIsTypeOf(MetricTemplate)) implies p1.oclAsType(MetricTemplate).unit.unit = p2.oclAsType(MetricTemplate).unit.unit))) else true endif";

	/**
	 * Validates the MetricTemplate_PERC_Unit_Enforcement constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricTemplate_MetricTemplate_PERC_Unit_Enforcement(MetricTemplate metricTemplate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_TEMPLATE,
				 metricTemplate,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "MetricTemplate_PERC_Unit_Enforcement",
				 METRIC_TEMPLATE__METRIC_TEMPLATE_PERC_UNIT_ENFORCEMENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the MetricTemplate_Composite_Unit_Enforcement constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_TEMPLATE__METRIC_TEMPLATE_COMPOSITE_UNIT_ENFORCEMENT__EEXPRESSION = "\n" +
		"\t\t\t\t\tif (self.type = MetricType::COMPOSITE and self.formula.function = MetricFunctionType::DIV) then (\n" +
		"\t\t\t\t\t\tif (self.unit.unit = camel::UnitType::BYTES_PER_SECOND) then (self.formula.parameters->at(1).oclAsType(MetricTemplate).unit.unit = camel::UnitType::BYTES and self.formula.parameters->at(2).oclAsType(MetricTemplate).unit.unit = camel::UnitType::SECONDS)\n" +
		"\t\t\t\t\t\telse ( \n" +
		"\t\t\t\t\t\t\tif (self.unit.unit = camel::UnitType::REQUESTS_PER_SECOND) then (self.formula.parameters->at(1).oclAsType(MetricTemplate).unit.unit = camel::UnitType::REQUESTS and self.formula.parameters->at(2).oclAsType(MetricTemplate).unit.unit = camel::UnitType::SECONDS) else true endif\n" +
		"\t\t\t\t\t\t)\n" +
		"\t\t\t\t\t\tendif ) \n" +
		"\t\t\t\t\telse true endif";

	/**
	 * Validates the MetricTemplate_Composite_Unit_Enforcement constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricTemplate_MetricTemplate_Composite_Unit_Enforcement(MetricTemplate metricTemplate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_TEMPLATE,
				 metricTemplate,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "MetricTemplate_Composite_Unit_Enforcement",
				 METRIC_TEMPLATE__METRIC_TEMPLATE_COMPOSITE_UNIT_ENFORCEMENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the metric_template_components_refer_to_same_level_or_lower constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_TEMPLATE__METRIC_TEMPLATE_COMPONENTS_REFER_TO_SAME_LEVEL_OR_LOWER__EEXPRESSION = "\n" +
		"\t\t\t\t\t(objectBinding <> null and self.type = MetricType::COMPOSITE) implies  \n" +
		"\t\t\t\t\t(if (objectBinding.oclIsTypeOf(MetricApplicationBinding)) then \n" +
		"\t\t\t\t\t\tself.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies (p.oclAsType(MetricTemplate).objectBinding <> null and p.oclAsType(MetricTemplate).objectBinding.application = objectBinding.application)) \n" +
		"\t\t\t\t\telse \n" +
		"\t\t\t\t\t\tif (objectBinding.oclIsTypeOf(MetricComponentBinding)) then self.formula.parameters->forAll(p | \n" +
		"\t\t\t\t\t\t\tp.oclIsTypeOf(MetricTemplate) implies (p.oclAsType(MetricTemplate).objectBinding <> null and p.oclAsType(MetricTemplate).objectBinding.application = self.objectBinding.application) \n" +
		"\t\t\t\t\t\t\tand not(p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricApplicationBinding)) and\n" +
		"\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::InternalComponent)) then\n" +
		"\t\t\t\t\t\t\t\tif (p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricVMBinding)) then self.objectBinding.application.deploymentModels->exists(q | q.hostings->exists(t | t.providedHost.component=p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricVMBinding).vm and t.requiredHost.component=self.objectBinding.oclAsType(MetricComponentBinding).component))\n" +
		"\t\t\t\t\t\t\t\telse \n" +
		"\t\t\t\t\t\t\t\t\tif (p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricComponentBinding) and p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then self.objectBinding.application.deploymentModels->exists(t | t.hostings->exists(q | q.providedHost.component=p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricComponentBinding).component and q.requiredHost.component=self.objectBinding.oclAsType(MetricComponentBinding).component)\n" +
		"\t\t\t\t\t\t\t\t\t)\n" +
		"\t\t\t\t\t\t\t\t\telse true endif\n" +
		"\t\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t\telse\n" +
		"\t\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then \n" +
		"\t\t\t\t\t\t\t\t\tif (p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricComponentBinding) and p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then true\n" +
		"\t\t\t\t\t\t\t\t\telse false endif\n" +
		"\t\t\t\t\t\t\t\telse false endif\n" +
		"\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t)\n" +
		"\t\t\t\t\t\telse self.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies (p.oclAsType(MetricTemplate).objectBinding <> null and p.oclAsType(MetricTemplate).objectBinding.application = self.objectBinding.application) and p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricVMBinding))\n" +
		"\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\tendif)";

	/**
	 * Validates the metric_template_components_refer_to_same_level_or_lower constraint of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricTemplate_metric_template_components_refer_to_same_level_or_lower(MetricTemplate metricTemplate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_TEMPLATE,
				 metricTemplate,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "metric_template_components_refer_to_same_level_or_lower",
				 METRIC_TEMPLATE__METRIC_TEMPLATE_COMPONENTS_REFER_TO_SAME_LEVEL_OR_LOWER__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricObjectBinding(MetricObjectBinding metricObjectBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)metricObjectBinding, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricApplicationBinding(MetricApplicationBinding metricApplicationBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)metricApplicationBinding, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricComponentBinding(MetricComponentBinding metricComponentBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)metricComponentBinding, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)metricComponentBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)metricComponentBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)metricComponentBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)metricComponentBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)metricComponentBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)metricComponentBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)metricComponentBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)metricComponentBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricComponentBinding_vm_and_sw_comp_connected(metricComponentBinding, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the vm_and_sw_comp_connected constraint of '<em>Metric Component Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_COMPONENT_BINDING__VM_AND_SW_COMP_CONNECTED__EEXPRESSION = "\n" +
		"\t\t\t\t\tif (component.oclIsTypeOf(camel::deployment::InternalComponent)) then application.deploymentModels->exists(d | d.hostings->exists(c | c.requiredHost.component=vm and c.providedHost.component=component))\n" +
		"\t\t\t\t\telse \n" +
		"\t\t\t\t\t\tif (component.oclIsTypeOf(camel::deployment::ExternalComponent)) then vm = null\n" +
		"\t\t\t\t\t\telse false endif\n" +
		"\t\t\t\t\tendif";

	/**
	 * Validates the vm_and_sw_comp_connected constraint of '<em>Metric Component Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricComponentBinding_vm_and_sw_comp_connected(MetricComponentBinding metricComponentBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_COMPONENT_BINDING,
				 metricComponentBinding,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "vm_and_sw_comp_connected",
				 METRIC_COMPONENT_BINDING__VM_AND_SW_COMP_CONNECTED__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricVMBinding(MetricVMBinding metricVMBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)metricVMBinding, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)metricVMBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)metricVMBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)metricVMBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)metricVMBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)metricVMBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)metricVMBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)metricVMBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)metricVMBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricVMBinding_MetricVMBinding_vm_in_dep_model_of_app(metricVMBinding, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the MetricVMBinding_vm_in_dep_model_of_app constraint of '<em>Metric VM Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_VM_BINDING__METRIC_VM_BINDING_VM_IN_DEP_MODEL_OF_APP__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tapplication.deploymentModels->exists(d | d.components->includes(vm))";

	/**
	 * Validates the MetricVMBinding_vm_in_dep_model_of_app constraint of '<em>Metric VM Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricVMBinding_MetricVMBinding_vm_in_dep_model_of_app(MetricVMBinding metricVMBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_VM_BINDING,
				 metricVMBinding,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "MetricVMBinding_vm_in_dep_model_of_app",
				 METRIC_VM_BINDING__METRIC_VM_BINDING_VM_IN_DEP_MODEL_OF_APP__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricObjectInstanceBinding(MetricObjectInstanceBinding metricObjectInstanceBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)metricObjectInstanceBinding, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricApplicationInstanceBinding(MetricApplicationInstanceBinding metricApplicationInstanceBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)metricApplicationInstanceBinding, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricComponentInstanceBinding(MetricComponentInstanceBinding metricComponentInstanceBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)metricComponentInstanceBinding, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)metricComponentInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)metricComponentInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)metricComponentInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)metricComponentInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)metricComponentInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)metricComponentInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)metricComponentInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)metricComponentInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricComponentInstanceBinding_vm_and_sw_comp_connected(metricComponentInstanceBinding, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the vm_and_sw_comp_connected constraint of '<em>Metric Component Instance Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_COMPONENT_INSTANCE_BINDING__VM_AND_SW_COMP_CONNECTED__EEXPRESSION = "\n" +
		"\t\t\t\t\tif (componentInstance.oclIsTypeOf(camel::deployment::InternalComponentInstance)) then executionContext.involvesDeployment.hostingInstances->exists(c | c.requiredHostInstance.componentInstance=vmInstance and c.providedHostInstance.componentInstance=componentInstance)\n" +
		"\t\t\t\t\telse \n" +
		"\t\t\t\t\t\tif (componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then vmInstance = null\n" +
		"\t\t\t\t\t\telse false endif\n" +
		"\t\t\t\t\tendif";

	/**
	 * Validates the vm_and_sw_comp_connected constraint of '<em>Metric Component Instance Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricComponentInstanceBinding_vm_and_sw_comp_connected(MetricComponentInstanceBinding metricComponentInstanceBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_COMPONENT_INSTANCE_BINDING,
				 metricComponentInstanceBinding,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "vm_and_sw_comp_connected",
				 METRIC_COMPONENT_INSTANCE_BINDING__VM_AND_SW_COMP_CONNECTED__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricVMInstanceBinding(MetricVMInstanceBinding metricVMInstanceBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)metricVMInstanceBinding, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)metricVMInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)metricVMInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)metricVMInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)metricVMInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)metricVMInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)metricVMInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)metricVMInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)metricVMInstanceBinding, diagnostics, context);
		if (result || diagnostics != null) result &= validateMetricVMInstanceBinding_MetricVMBinding_vm_in_dep_model_of_app(metricVMInstanceBinding, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the MetricVMBinding_vm_in_dep_model_of_app constraint of '<em>Metric VM Instance Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String METRIC_VM_INSTANCE_BINDING__METRIC_VM_BINDING_VM_IN_DEP_MODEL_OF_APP__EEXPRESSION = "\n" +
		"\t\t\t\t\t\texecutionContext.involvesDeployment.componentInstances->includes(vmInstance)";

	/**
	 * Validates the MetricVMBinding_vm_in_dep_model_of_app constraint of '<em>Metric VM Instance Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricVMInstanceBinding_MetricVMBinding_vm_in_dep_model_of_app(MetricVMInstanceBinding metricVMInstanceBinding, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.METRIC_VM_INSTANCE_BINDING,
				 metricVMInstanceBinding,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "MetricVMBinding_vm_in_dep_model_of_app",
				 METRIC_VM_INSTANCE_BINDING__METRIC_VM_BINDING_VM_IN_DEP_MODEL_OF_APP__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProperty(Property property, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)property, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScalabilityPolicy(ScalabilityPolicy scalabilityPolicy, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)scalabilityPolicy, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)scalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)scalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)scalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)scalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)scalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)scalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)scalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)scalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= camelValidator.validateRequirement_non_negative_priorities_for_requirement(scalabilityPolicy, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHorizontalScalabilityPolicy(HorizontalScalabilityPolicy horizontalScalabilityPolicy, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)horizontalScalabilityPolicy, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)horizontalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)horizontalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)horizontalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)horizontalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)horizontalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)horizontalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)horizontalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)horizontalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= camelValidator.validateRequirement_non_negative_priorities_for_requirement(horizontalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validateHorizontalScalabilityPolicy_ScalabilityPolicy_min_max_enforcement(horizontalScalabilityPolicy, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ScalabilityPolicy_min_max_enforcement constraint of '<em>Horizontal Scalability Policy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String HORIZONTAL_SCALABILITY_POLICY__SCALABILITY_POLICY_MIN_MAX_ENFORCEMENT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t self.minInstances > 0 and self.maxInstances > 0 and self.minInstances <= self.maxInstances";

	/**
	 * Validates the ScalabilityPolicy_min_max_enforcement constraint of '<em>Horizontal Scalability Policy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHorizontalScalabilityPolicy_ScalabilityPolicy_min_max_enforcement(HorizontalScalabilityPolicy horizontalScalabilityPolicy, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.HORIZONTAL_SCALABILITY_POLICY,
				 horizontalScalabilityPolicy,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ScalabilityPolicy_min_max_enforcement",
				 HORIZONTAL_SCALABILITY_POLICY__SCALABILITY_POLICY_MIN_MAX_ENFORCEMENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVerticalScalabilityPolicy(VerticalScalabilityPolicy verticalScalabilityPolicy, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)verticalScalabilityPolicy, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)verticalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)verticalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)verticalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)verticalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)verticalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)verticalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)verticalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)verticalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= camelValidator.validateRequirement_non_negative_priorities_for_requirement(verticalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validateVerticalScalabilityPolicy_VertScalPol_correct_param_vals(verticalScalabilityPolicy, diagnostics, context);
		if (result || diagnostics != null) result &= validateVerticalScalabilityPolicy_VertScalPol_activ_one_alt(verticalScalabilityPolicy, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the VertScalPol_correct_param_vals constraint of '<em>Vertical Scalability Policy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String VERTICAL_SCALABILITY_POLICY__VERT_SCAL_POL_CORRECT_PARAM_VALS__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\tminCores >= 0 and maxCores >= 0 and minCores <= maxCores and minMemory >= 0 and maxMemory >= 0 and minMemory <= maxMemory and minCPU >= 0 and maxCPU >= 0 and minCPU <= maxCPU and minStorage >=0 and maxStorage >= 0 and minStorage <= maxStorage";

	/**
	 * Validates the VertScalPol_correct_param_vals constraint of '<em>Vertical Scalability Policy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVerticalScalabilityPolicy_VertScalPol_correct_param_vals(VerticalScalabilityPolicy verticalScalabilityPolicy, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY,
				 verticalScalabilityPolicy,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "VertScalPol_correct_param_vals",
				 VERTICAL_SCALABILITY_POLICY__VERT_SCAL_POL_CORRECT_PARAM_VALS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the VertScalPol_activ_one_alt constraint of '<em>Vertical Scalability Policy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String VERTICAL_SCALABILITY_POLICY__VERT_SCAL_POL_ACTIV_ONE_ALT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\tmaxCores > 0 or maxCPU > 0 or maxMemory > 0 or maxStorage > 0";

	/**
	 * Validates the VertScalPol_activ_one_alt constraint of '<em>Vertical Scalability Policy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVerticalScalabilityPolicy_VertScalPol_activ_one_alt(VerticalScalabilityPolicy verticalScalabilityPolicy, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY,
				 verticalScalabilityPolicy,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "VertScalPol_activ_one_alt",
				 VERTICAL_SCALABILITY_POLICY__VERT_SCAL_POL_ACTIV_ONE_ALT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScalabilityRule(ScalabilityRule scalabilityRule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)scalabilityRule, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)scalabilityRule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)scalabilityRule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)scalabilityRule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)scalabilityRule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)scalabilityRule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)scalabilityRule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)scalabilityRule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)scalabilityRule, diagnostics, context);
		if (result || diagnostics != null) result &= validateScalabilityRule_Scal_Rule_Horiz_Policy_Count(scalabilityRule, diagnostics, context);
		if (result || diagnostics != null) result &= validateScalabilityRule_Scal_Rule_Vert_Policy_Correct_Vals(scalabilityRule, diagnostics, context);
		if (result || diagnostics != null) result &= validateScalabilityRule_Scal_Rule_No_Conficting_Policies(scalabilityRule, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Scal_Rule_Horiz_Policy_Count constraint of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SCALABILITY_RULE__SCAL_RULE_HORIZ_POLICY_COUNT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.mapsToActions->forAll(p | (p.oclIsTypeOf(ScalingAction) and p.oclAsType(ScalingAction).count > 0 and (p.type = camel::ActionType::SCALE_IN or p.type = camel::ActionType::SCALE_OUT and self.invariantPolicies->exists(q | q.oclIsTypeOf(camel::scalability::HorizontalScalabilityPolicy))) implies self.invariantPolicies->forAll(t | \n" +
		"\t\t\t\t\t\t\tif (t.oclIsTypeOf(camel::scalability::HorizontalScalabilityPolicy)) then p.oclAsType(ScalingAction).count <= (t.oclAsType(camel::scalability::HorizontalScalabilityPolicy).maxInstances-t.oclAsType(camel::scalability::HorizontalScalabilityPolicy).minInstances)\n" +
		"\t\t\t\t\t\t\telse true endif\n" +
		"\t\t\t\t\t\t)))";

	/**
	 * Validates the Scal_Rule_Horiz_Policy_Count constraint of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScalabilityRule_Scal_Rule_Horiz_Policy_Count(ScalabilityRule scalabilityRule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.SCALABILITY_RULE,
				 scalabilityRule,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Scal_Rule_Horiz_Policy_Count",
				 SCALABILITY_RULE__SCAL_RULE_HORIZ_POLICY_COUNT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Scal_Rule_Vert_Policy_Correct_Vals constraint of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SCALABILITY_RULE__SCAL_RULE_VERT_POLICY_CORRECT_VALS__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.mapsToActions->forAll(p | (p.oclIsTypeOf(ScalingAction) and (p.type = camel::ActionType::SCALE_UP or p.type = camel::ActionType::SCALE_DOWN and self.invariantPolicies->exists(t | t.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy))) implies self.invariantPolicies->forAll(q | q.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy) implies \n" +
		"\t\t\t\t\t\t\t\t\t((p.oclAsType(ScalingAction).coreUpdate > 0) implies (p.oclAsType(ScalingAction).coreUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxCores - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minCores)))\n" +
		"\t\t\t\t\t\t\t\t\tand ((p.oclAsType(ScalingAction).storageUpdate > 0) implies (p.oclAsType(ScalingAction).storageUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxStorage - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minStorage)))\n" +
		"\t\t\t\t\t\t\t\t\tand ((p.oclAsType(ScalingAction).CPUUpdate > 0) implies (p.oclAsType(ScalingAction).CPUUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxCPU - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minCPU)))\n" +
		"\t\t\t\t\t\t\t\t\tand ((p.oclAsType(ScalingAction).memoryUpdate > 0) implies (p.oclAsType(ScalingAction).memoryUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxMemory - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minMemory)))\n" +
		"\t\t\t\t\t\t)))";

	/**
	 * Validates the Scal_Rule_Vert_Policy_Correct_Vals constraint of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScalabilityRule_Scal_Rule_Vert_Policy_Correct_Vals(ScalabilityRule scalabilityRule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.SCALABILITY_RULE,
				 scalabilityRule,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Scal_Rule_Vert_Policy_Correct_Vals",
				 SCALABILITY_RULE__SCAL_RULE_VERT_POLICY_CORRECT_VALS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Scal_Rule_No_Conficting_Policies constraint of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SCALABILITY_RULE__SCAL_RULE_NO_CONFICTING_POLICIES__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.invariantPolicies->forAll(p1, p2 | \n" +
		"\t\t\t\t\t\t\tif (p1 <> p2 and p1.oclType() = p2.oclType()) then\n" +
		"\t\t\t\t\t\t\t\tif (p1.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy)) then p1.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm <> p2.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm\n" +
		"\t\t\t\t\t\t\t\telse p1.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component <> p2.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component\n" +
		"\t\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t\telse true\n" +
		"\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t)";

	/**
	 * Validates the Scal_Rule_No_Conficting_Policies constraint of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScalabilityRule_Scal_Rule_No_Conficting_Policies(ScalabilityRule scalabilityRule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.SCALABILITY_RULE,
				 scalabilityRule,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Scal_Rule_No_Conficting_Policies",
				 SCALABILITY_RULE__SCAL_RULE_NO_CONFICTING_POLICIES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScalingAction(ScalingAction scalingAction, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)scalingAction, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)scalingAction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)scalingAction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)scalingAction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)scalingAction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)scalingAction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)scalingAction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)scalingAction, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)scalingAction, diagnostics, context);
		if (result || diagnostics != null) result &= camelValidator.validateAction_correct_action_type(scalingAction, diagnostics, context);
		if (result || diagnostics != null) result &= validateScalingAction_scale_action_correct_count(scalingAction, diagnostics, context);
		if (result || diagnostics != null) result &= validateScalingAction_scale_action_check_type_wrt_properties(scalingAction, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the scale_action_correct_count constraint of '<em>Scaling Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SCALING_ACTION__SCALE_ACTION_CORRECT_COUNT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tcount >= 0";

	/**
	 * Validates the scale_action_correct_count constraint of '<em>Scaling Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScalingAction_scale_action_correct_count(ScalingAction scalingAction, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.SCALING_ACTION,
				 scalingAction,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "scale_action_correct_count",
				 SCALING_ACTION__SCALE_ACTION_CORRECT_COUNT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the scale_action_check_type_wrt_properties constraint of '<em>Scaling Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SCALING_ACTION__SCALE_ACTION_CHECK_TYPE_WRT_PROPERTIES__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tif (self.type = camel::ActionType::SCALE_IN or self.type = camel::ActionType::SCALE_OUT) then (componentInstance <> null and count <> 0 and (containmentInstance <> null implies (containmentInstance.providedHostInstance.componentInstance=vmInstance and containmentInstance.requiredHostInstance.componentInstance = componentInstance)))\n" +
		"\t\t\t\t\t\telse (componentInstance = null and containmentInstance = null and count = 0 and (memoryUpdate <> 0 or CPUUpdate <> 0.0 or coreUpdate <> 0 or storageUpdate <> 0))\n" +
		"\t\t\t\t\t\tendif";

	/**
	 * Validates the scale_action_check_type_wrt_properties constraint of '<em>Scaling Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScalingAction_scale_action_check_type_wrt_properties(ScalingAction scalingAction, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.SCALING_ACTION,
				 scalingAction,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "scale_action_check_type_wrt_properties",
				 SCALING_ACTION__SCALE_ACTION_CHECK_TYPE_WRT_PROPERTIES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSchedule(Schedule schedule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)schedule, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)schedule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)schedule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)schedule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)schedule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)schedule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)schedule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)schedule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)schedule, diagnostics, context);
		if (result || diagnostics != null) result &= validateSchedule_Schedule_Start_Before_End(schedule, diagnostics, context);
		if (result || diagnostics != null) result &= validateSchedule_Schedule_Correct_values(schedule, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Schedule_Start_Before_End constraint of '<em>Schedule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SCHEDULE__SCHEDULE_START_BEFORE_END__EEXPRESSION = "\n" +
		"\t\t\t\t\tcheckStartEndDates(self)";

	/**
	 * Validates the Schedule_Start_Before_End constraint of '<em>Schedule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSchedule_Schedule_Start_Before_End(Schedule schedule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.SCHEDULE,
				 schedule,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Schedule_Start_Before_End",
				 SCHEDULE__SCHEDULE_START_BEFORE_END__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Schedule_Correct_values constraint of '<em>Schedule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SCHEDULE__SCHEDULE_CORRECT_VALUES__EEXPRESSION = "\n" +
		"\t\t\t\t\t(self.type <> ScheduleType::SINGLE_EVENT implies (self.interval > 0 and self.unit <> null) and checkIntervalRepetitions(self)) and ((self.type = ScheduleType::SINGLE_EVENT implies (interval = 0 and start = null and end = null and unit = null)))";

	/**
	 * Validates the Schedule_Correct_values constraint of '<em>Schedule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSchedule_Schedule_Correct_values(Schedule schedule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.SCHEDULE,
				 schedule,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Schedule_Correct_values",
				 SCHEDULE__SCHEDULE_CORRECT_VALUES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSensor(Sensor sensor, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)sensor, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTimer(Timer timer, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)timer, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)timer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)timer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)timer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)timer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)timer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)timer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)timer, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)timer, diagnostics, context);
		if (result || diagnostics != null) result &= validateTimer_Timer_correctValues(timer, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Timer_correctValues constraint of '<em>Timer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String TIMER__TIMER_CORRECT_VALUES__EEXPRESSION = "\n" +
		"\t\t\t\t\t\ttimeValue > 0 and (self.type = TimerType::WITHIN_MAX implies self.maxOccurrenceNum > 0) and (self.type <> TimerType::WITHIN_MAX implies self.maxOccurrenceNum = 0)";

	/**
	 * Validates the Timer_correctValues constraint of '<em>Timer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTimer_Timer_correctValues(Timer timer, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.TIMER,
				 timer,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Timer_correctValues",
				 TIMER__TIMER_CORRECT_VALUES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWindow(Window window, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)window, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)window, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)window, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)window, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)window, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)window, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)window, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)window, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)window, diagnostics, context);
		if (result || diagnostics != null) result &= validateWindow_window_positive_params(window, diagnostics, context);
		if (result || diagnostics != null) result &= validateWindow_window_right_params_exist(window, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the window_positive_params constraint of '<em>Window</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String WINDOW__WINDOW_POSITIVE_PARAMS__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t(measurementSize >= 0) and (timeSize >= 0)";

	/**
	 * Validates the window_positive_params constraint of '<em>Window</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWindow_window_positive_params(Window window, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.WINDOW,
				 window,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "window_positive_params",
				 WINDOW__WINDOW_POSITIVE_PARAMS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the window_right_params_exist constraint of '<em>Window</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String WINDOW__WINDOW_RIGHT_PARAMS_EXIST__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t(self.sizeType = WindowSizeType::MEASUREMENTS_ONLY implies (unit = null and timeSize = 0 and measurementSize > 0)) and (self.sizeType = WindowSizeType::TIME_ONLY implies (unit <> null and timeSize > 0 and measurementSize = 0)) and ((self.sizeType = WindowSizeType::FIRST_MATCH or self.sizeType = WindowSizeType::BOTH_MATCH) implies (timeSize > 0 and unit <> null and measurementSize > 0))";

	/**
	 * Validates the window_right_params_exist constraint of '<em>Window</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWindow_window_right_params_exist(Window window, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ScalabilityPackage.Literals.WINDOW,
				 window,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "window_right_params_exist",
				 WINDOW__WINDOW_RIGHT_PARAMS_EXIST__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBinaryPatternOperatorType(BinaryPatternOperatorType binaryPatternOperatorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComparisonOperatorType(ComparisonOperatorType comparisonOperatorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLayerType(LayerType layerType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricFunctionArityType(MetricFunctionArityType metricFunctionArityType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricFunctionType(MetricFunctionType metricFunctionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetricType(MetricType metricType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePropertyType(PropertyType propertyType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScheduleType(ScheduleType scheduleType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStatusType(StatusType statusType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTimerType(TimerType timerType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnaryPatternOperatorType(UnaryPatternOperatorType unaryPatternOperatorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWindowSizeType(WindowSizeType windowSizeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWindowType(WindowType windowType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //ScalabilityValidator
