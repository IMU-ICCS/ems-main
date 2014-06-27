/**
 */
package camel.scalability;

import camel.CamelPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see camel.scalability.ScalabilityFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface ScalabilityPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "scalability";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.paasage.eu/camel/srl";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "scalability";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ScalabilityPackage eINSTANCE = camel.scalability.impl.ScalabilityPackageImpl.init();

	/**
	 * The meta object id for the '{@link camel.scalability.impl.ScalabilityModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.ScalabilityModelImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getScalabilityModel()
	 * @generated
	 */
	int SCALABILITY_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Metric Templates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__METRIC_TEMPLATES = 0;

	/**
	 * The feature id for the '<em><b>Metrics</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__METRICS = 1;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__RULES = 2;

	/**
	 * The feature id for the '<em><b>Events</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__EVENTS = 3;

	/**
	 * The feature id for the '<em><b>Event Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__EVENT_INSTANCES = 4;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__CONDITIONS = 5;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__PROPERTIES = 6;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__ACTIONS = 7;

	/**
	 * The feature id for the '<em><b>Formulas</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__FORMULAS = 8;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__BINDINGS = 9;

	/**
	 * The feature id for the '<em><b>Binding Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__BINDING_INSTANCES = 10;

	/**
	 * The feature id for the '<em><b>Windows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__WINDOWS = 11;

	/**
	 * The feature id for the '<em><b>Schedules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__SCHEDULES = 12;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__PARAMETERS = 13;

	/**
	 * The feature id for the '<em><b>Patterns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__PATTERNS = 14;

	/**
	 * The feature id for the '<em><b>Timers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__TIMERS = 15;

	/**
	 * The feature id for the '<em><b>Sensors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__SENSORS = 16;

	/**
	 * The feature id for the '<em><b>Units</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__UNITS = 17;

	/**
	 * The feature id for the '<em><b>Value Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__VALUE_TYPES = 18;

	/**
	 * The feature id for the '<em><b>Policies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL__POLICIES = 19;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL_FEATURE_COUNT = 20;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.ConditionImpl <em>Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.ConditionImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getCondition()
	 * @generated
	 */
	int CONDITION = 1;

	/**
	 * The feature id for the '<em><b>Validity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__VALIDITY = 0;

	/**
	 * The feature id for the '<em><b>Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__THRESHOLD = 1;

	/**
	 * The feature id for the '<em><b>Comparison Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__COMPARISON_OPERATOR = 2;

	/**
	 * The number of structural features of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricConditionImpl <em>Metric Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricConditionImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricCondition()
	 * @generated
	 */
	int METRIC_CONDITION = 2;

	/**
	 * The feature id for the '<em><b>Validity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_CONDITION__VALIDITY = CONDITION__VALIDITY;

	/**
	 * The feature id for the '<em><b>Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_CONDITION__THRESHOLD = CONDITION__THRESHOLD;

	/**
	 * The feature id for the '<em><b>Comparison Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_CONDITION__COMPARISON_OPERATOR = CONDITION__COMPARISON_OPERATOR;

	/**
	 * The feature id for the '<em><b>Metric</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_CONDITION__METRIC = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Metric Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Metric Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_CONDITION_OPERATION_COUNT = CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricTemplateConditionImpl <em>Metric Template Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricTemplateConditionImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricTemplateCondition()
	 * @generated
	 */
	int METRIC_TEMPLATE_CONDITION = 3;

	/**
	 * The feature id for the '<em><b>Validity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE_CONDITION__VALIDITY = CONDITION__VALIDITY;

	/**
	 * The feature id for the '<em><b>Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE_CONDITION__THRESHOLD = CONDITION__THRESHOLD;

	/**
	 * The feature id for the '<em><b>Comparison Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE_CONDITION__COMPARISON_OPERATOR = CONDITION__COMPARISON_OPERATOR;

	/**
	 * The feature id for the '<em><b>Metric Template</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE_CONDITION__METRIC_TEMPLATE = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Metric Template Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Metric Template Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE_CONDITION_OPERATION_COUNT = CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.EventImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getEvent()
	 * @generated
	 */
	int EVENT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.EventPatternImpl <em>Event Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.EventPatternImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getEventPattern()
	 * @generated
	 */
	int EVENT_PATTERN = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_PATTERN__NAME = EVENT__NAME;

	/**
	 * The feature id for the '<em><b>Timer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_PATTERN__TIMER = EVENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Event Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_PATTERN_FEATURE_COUNT = EVENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Includes Event</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_PATTERN___INCLUDES_EVENT__SIMPLEEVENT = EVENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Includes Left Event</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_PATTERN___INCLUDES_LEFT_EVENT__SIMPLEEVENT = EVENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Includes Right Event</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_PATTERN___INCLUDES_RIGHT_EVENT__SIMPLEEVENT = EVENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Related To Execution Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_PATTERN___RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = EVENT_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Left Related To Execution Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_PATTERN___LEFT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = EVENT_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Right Related To Execution Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_PATTERN___RIGHT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = EVENT_OPERATION_COUNT + 5;

	/**
	 * The number of operations of the '<em>Event Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_PATTERN_OPERATION_COUNT = EVENT_OPERATION_COUNT + 6;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.BinaryEventPatternImpl <em>Binary Event Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.BinaryEventPatternImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getBinaryEventPattern()
	 * @generated
	 */
	int BINARY_EVENT_PATTERN = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN__NAME = EVENT_PATTERN__NAME;

	/**
	 * The feature id for the '<em><b>Timer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN__TIMER = EVENT_PATTERN__TIMER;

	/**
	 * The feature id for the '<em><b>Left</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN__LEFT = EVENT_PATTERN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN__RIGHT = EVENT_PATTERN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lower Occurrence Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN__LOWER_OCCURRENCE_BOUND = EVENT_PATTERN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Upper Occurrence Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN__UPPER_OCCURRENCE_BOUND = EVENT_PATTERN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN__OPERATOR = EVENT_PATTERN_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Binary Event Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN_FEATURE_COUNT = EVENT_PATTERN_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Includes Event</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN___INCLUDES_EVENT__SIMPLEEVENT = EVENT_PATTERN___INCLUDES_EVENT__SIMPLEEVENT;

	/**
	 * The operation id for the '<em>Includes Left Event</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN___INCLUDES_LEFT_EVENT__SIMPLEEVENT = EVENT_PATTERN___INCLUDES_LEFT_EVENT__SIMPLEEVENT;

	/**
	 * The operation id for the '<em>Includes Right Event</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN___INCLUDES_RIGHT_EVENT__SIMPLEEVENT = EVENT_PATTERN___INCLUDES_RIGHT_EVENT__SIMPLEEVENT;

	/**
	 * The operation id for the '<em>Related To Execution Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN___RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = EVENT_PATTERN___RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT;

	/**
	 * The operation id for the '<em>Left Related To Execution Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN___LEFT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = EVENT_PATTERN___LEFT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT;

	/**
	 * The operation id for the '<em>Right Related To Execution Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN___RIGHT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = EVENT_PATTERN___RIGHT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT;

	/**
	 * The number of operations of the '<em>Binary Event Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EVENT_PATTERN_OPERATION_COUNT = EVENT_PATTERN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.UnaryEventPatternImpl <em>Unary Event Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.UnaryEventPatternImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getUnaryEventPattern()
	 * @generated
	 */
	int UNARY_EVENT_PATTERN = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN__NAME = EVENT_PATTERN__NAME;

	/**
	 * The feature id for the '<em><b>Timer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN__TIMER = EVENT_PATTERN__TIMER;

	/**
	 * The feature id for the '<em><b>Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN__EVENT = EVENT_PATTERN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Occurrence Num</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN__OCCURRENCE_NUM = EVENT_PATTERN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN__OPERATOR = EVENT_PATTERN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Unary Event Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN_FEATURE_COUNT = EVENT_PATTERN_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Includes Event</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN___INCLUDES_EVENT__SIMPLEEVENT = EVENT_PATTERN___INCLUDES_EVENT__SIMPLEEVENT;

	/**
	 * The operation id for the '<em>Includes Left Event</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN___INCLUDES_LEFT_EVENT__SIMPLEEVENT = EVENT_PATTERN___INCLUDES_LEFT_EVENT__SIMPLEEVENT;

	/**
	 * The operation id for the '<em>Includes Right Event</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN___INCLUDES_RIGHT_EVENT__SIMPLEEVENT = EVENT_PATTERN___INCLUDES_RIGHT_EVENT__SIMPLEEVENT;

	/**
	 * The operation id for the '<em>Related To Execution Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN___RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = EVENT_PATTERN___RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT;

	/**
	 * The operation id for the '<em>Left Related To Execution Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN___LEFT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = EVENT_PATTERN___LEFT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT;

	/**
	 * The operation id for the '<em>Right Related To Execution Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN___RIGHT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = EVENT_PATTERN___RIGHT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT;

	/**
	 * The number of operations of the '<em>Unary Event Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EVENT_PATTERN_OPERATION_COUNT = EVENT_PATTERN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.SimpleEventImpl <em>Simple Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.SimpleEventImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getSimpleEvent()
	 * @generated
	 */
	int SIMPLE_EVENT = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT__NAME = EVENT__NAME;

	/**
	 * The number of structural features of the '<em>Simple Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Simple Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EVENT_OPERATION_COUNT = EVENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.FunctionalEventImpl <em>Functional Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.FunctionalEventImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getFunctionalEvent()
	 * @generated
	 */
	int FUNCTIONAL_EVENT = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL_EVENT__NAME = SIMPLE_EVENT__NAME;

	/**
	 * The feature id for the '<em><b>Functional Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL_EVENT__FUNCTIONAL_TYPE = SIMPLE_EVENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Functional Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL_EVENT_FEATURE_COUNT = SIMPLE_EVENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Functional Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL_EVENT_OPERATION_COUNT = SIMPLE_EVENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.NonFunctionalEventImpl <em>Non Functional Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.NonFunctionalEventImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getNonFunctionalEvent()
	 * @generated
	 */
	int NON_FUNCTIONAL_EVENT = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_FUNCTIONAL_EVENT__NAME = SIMPLE_EVENT__NAME;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_FUNCTIONAL_EVENT__CONDITION = SIMPLE_EVENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Violation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_FUNCTIONAL_EVENT__IS_VIOLATION = SIMPLE_EVENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Non Functional Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_FUNCTIONAL_EVENT_FEATURE_COUNT = SIMPLE_EVENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Non Functional Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_FUNCTIONAL_EVENT_OPERATION_COUNT = SIMPLE_EVENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.EventInstanceImpl <em>Event Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.EventInstanceImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getEventInstance()
	 * @generated
	 */
	int EVENT_INSTANCE = 11;

	/**
	 * The feature id for the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_INSTANCE__STATUS = 0;

	/**
	 * The feature id for the '<em><b>Layer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_INSTANCE__LAYER = 1;

	/**
	 * The feature id for the '<em><b>On Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_INSTANCE__ON_EVENT = 2;

	/**
	 * The number of structural features of the '<em>Event Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_INSTANCE_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Equal Layer</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_INSTANCE___EQUAL_LAYER__LAYERTYPE_LAYERTYPE = 0;

	/**
	 * The number of operations of the '<em>Event Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_INSTANCE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricImpl <em>Metric</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetric()
	 * @generated
	 */
	int METRIC = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__ID = 0;

	/**
	 * The feature id for the '<em><b>Has Template</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__HAS_TEMPLATE = 1;

	/**
	 * The feature id for the '<em><b>Has Schedule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__HAS_SCHEDULE = 2;

	/**
	 * The feature id for the '<em><b>Window</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__WINDOW = 3;

	/**
	 * The feature id for the '<em><b>Component Metrics</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__COMPONENT_METRICS = 4;

	/**
	 * The feature id for the '<em><b>Sensor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__SENSOR = 5;

	/**
	 * The feature id for the '<em><b>Object Binding</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__OBJECT_BINDING = 6;

	/**
	 * The feature id for the '<em><b>Value Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__VALUE_TYPE = 7;

	/**
	 * The number of structural features of the '<em>Metric</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FEATURE_COUNT = 8;

	/**
	 * The operation id for the '<em>Check Recursiveness</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC___CHECK_RECURSIVENESS__METRIC_METRIC = 0;

	/**
	 * The number of operations of the '<em>Metric</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricFormulaParameterImpl <em>Metric Formula Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricFormulaParameterImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricFormulaParameter()
	 * @generated
	 */
	int METRIC_FORMULA_PARAMETER = 13;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FORMULA_PARAMETER__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Metric Formula Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FORMULA_PARAMETER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Metric Formula Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FORMULA_PARAMETER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricFormulaImpl <em>Metric Formula</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricFormulaImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricFormula()
	 * @generated
	 */
	int METRIC_FORMULA = 14;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FORMULA__VALUE = METRIC_FORMULA_PARAMETER__VALUE;

	/**
	 * The feature id for the '<em><b>Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FORMULA__FUNCTION = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Function Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FORMULA__FUNCTION_ARITY = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FORMULA__PARAMETERS = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Metric Formula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FORMULA_FEATURE_COUNT = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Metric Formula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FORMULA_OPERATION_COUNT = METRIC_FORMULA_PARAMETER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricTemplateImpl <em>Metric Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricTemplateImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricTemplate()
	 * @generated
	 */
	int METRIC_TEMPLATE = 15;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE__VALUE = METRIC_FORMULA_PARAMETER__VALUE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE__NAME = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE__DESCRIPTION = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE__VALUE_DIRECTION = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE__UNIT = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Layer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE__LAYER = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Measures</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE__MEASURES = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE__TYPE = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Formula</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE__FORMULA = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Object Binding</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE__OBJECT_BINDING = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Metric Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE_FEATURE_COUNT = METRIC_FORMULA_PARAMETER_FEATURE_COUNT + 9;

	/**
	 * The operation id for the '<em>Check Recursiveness</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE___CHECK_RECURSIVENESS__METRICTEMPLATE_METRICTEMPLATE = METRIC_FORMULA_PARAMETER_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Greater Equal Than Layer</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE___GREATER_EQUAL_THAN_LAYER__LAYERTYPE_LAYERTYPE = METRIC_FORMULA_PARAMETER_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Metric Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_TEMPLATE_OPERATION_COUNT = METRIC_FORMULA_PARAMETER_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricObjectBindingImpl <em>Metric Object Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricObjectBindingImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricObjectBinding()
	 * @generated
	 */
	int METRIC_OBJECT_BINDING = 16;

	/**
	 * The feature id for the '<em><b>Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_OBJECT_BINDING__APPLICATION = 0;

	/**
	 * The number of structural features of the '<em>Metric Object Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_OBJECT_BINDING_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Metric Object Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_OBJECT_BINDING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricApplicationBindingImpl <em>Metric Application Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricApplicationBindingImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricApplicationBinding()
	 * @generated
	 */
	int METRIC_APPLICATION_BINDING = 17;

	/**
	 * The feature id for the '<em><b>Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_APPLICATION_BINDING__APPLICATION = METRIC_OBJECT_BINDING__APPLICATION;

	/**
	 * The number of structural features of the '<em>Metric Application Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_APPLICATION_BINDING_FEATURE_COUNT = METRIC_OBJECT_BINDING_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Metric Application Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_APPLICATION_BINDING_OPERATION_COUNT = METRIC_OBJECT_BINDING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricComponentBindingImpl <em>Metric Component Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricComponentBindingImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricComponentBinding()
	 * @generated
	 */
	int METRIC_COMPONENT_BINDING = 18;

	/**
	 * The feature id for the '<em><b>Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_COMPONENT_BINDING__APPLICATION = METRIC_OBJECT_BINDING__APPLICATION;

	/**
	 * The feature id for the '<em><b>Vm</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_COMPONENT_BINDING__VM = METRIC_OBJECT_BINDING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_COMPONENT_BINDING__COMPONENT = METRIC_OBJECT_BINDING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Metric Component Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_COMPONENT_BINDING_FEATURE_COUNT = METRIC_OBJECT_BINDING_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Metric Component Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_COMPONENT_BINDING_OPERATION_COUNT = METRIC_OBJECT_BINDING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricVMBindingImpl <em>Metric VM Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricVMBindingImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricVMBinding()
	 * @generated
	 */
	int METRIC_VM_BINDING = 19;

	/**
	 * The feature id for the '<em><b>Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VM_BINDING__APPLICATION = METRIC_OBJECT_BINDING__APPLICATION;

	/**
	 * The feature id for the '<em><b>Vm</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VM_BINDING__VM = METRIC_OBJECT_BINDING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Metric VM Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VM_BINDING_FEATURE_COUNT = METRIC_OBJECT_BINDING_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Metric VM Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VM_BINDING_OPERATION_COUNT = METRIC_OBJECT_BINDING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricObjectInstanceBindingImpl <em>Metric Object Instance Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricObjectInstanceBindingImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricObjectInstanceBinding()
	 * @generated
	 */
	int METRIC_OBJECT_INSTANCE_BINDING = 20;

	/**
	 * The feature id for the '<em><b>Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_OBJECT_INSTANCE_BINDING__EXECUTION_CONTEXT = 0;

	/**
	 * The number of structural features of the '<em>Metric Object Instance Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_OBJECT_INSTANCE_BINDING_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Metric Object Instance Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_OBJECT_INSTANCE_BINDING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricApplicationInstanceBindingImpl <em>Metric Application Instance Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricApplicationInstanceBindingImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricApplicationInstanceBinding()
	 * @generated
	 */
	int METRIC_APPLICATION_INSTANCE_BINDING = 21;

	/**
	 * The feature id for the '<em><b>Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_APPLICATION_INSTANCE_BINDING__EXECUTION_CONTEXT = METRIC_OBJECT_INSTANCE_BINDING__EXECUTION_CONTEXT;

	/**
	 * The number of structural features of the '<em>Metric Application Instance Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_APPLICATION_INSTANCE_BINDING_FEATURE_COUNT = METRIC_OBJECT_INSTANCE_BINDING_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Metric Application Instance Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_APPLICATION_INSTANCE_BINDING_OPERATION_COUNT = METRIC_OBJECT_INSTANCE_BINDING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricComponentInstanceBindingImpl <em>Metric Component Instance Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricComponentInstanceBindingImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricComponentInstanceBinding()
	 * @generated
	 */
	int METRIC_COMPONENT_INSTANCE_BINDING = 22;

	/**
	 * The feature id for the '<em><b>Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_COMPONENT_INSTANCE_BINDING__EXECUTION_CONTEXT = METRIC_OBJECT_INSTANCE_BINDING__EXECUTION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Vm Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_COMPONENT_INSTANCE_BINDING__VM_INSTANCE = METRIC_OBJECT_INSTANCE_BINDING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Component Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_COMPONENT_INSTANCE_BINDING__COMPONENT_INSTANCE = METRIC_OBJECT_INSTANCE_BINDING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Metric Component Instance Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_COMPONENT_INSTANCE_BINDING_FEATURE_COUNT = METRIC_OBJECT_INSTANCE_BINDING_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Metric Component Instance Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_COMPONENT_INSTANCE_BINDING_OPERATION_COUNT = METRIC_OBJECT_INSTANCE_BINDING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.MetricVMInstanceBindingImpl <em>Metric VM Instance Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.MetricVMInstanceBindingImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricVMInstanceBinding()
	 * @generated
	 */
	int METRIC_VM_INSTANCE_BINDING = 23;

	/**
	 * The feature id for the '<em><b>Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VM_INSTANCE_BINDING__EXECUTION_CONTEXT = METRIC_OBJECT_INSTANCE_BINDING__EXECUTION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Vm Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VM_INSTANCE_BINDING__VM_INSTANCE = METRIC_OBJECT_INSTANCE_BINDING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Metric VM Instance Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VM_INSTANCE_BINDING_FEATURE_COUNT = METRIC_OBJECT_INSTANCE_BINDING_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Metric VM Instance Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VM_INSTANCE_BINDING_OPERATION_COUNT = METRIC_OBJECT_INSTANCE_BINDING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.PropertyImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 24;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Realized By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__REALIZED_BY = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__TYPE = 4;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.ScalabilityPolicyImpl <em>Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.ScalabilityPolicyImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getScalabilityPolicy()
	 * @generated
	 */
	int SCALABILITY_POLICY = 25;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_POLICY__PRIORITY = CamelPackage.REQUIREMENT__PRIORITY;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_POLICY__ID = CamelPackage.REQUIREMENT__ID;

	/**
	 * The number of structural features of the '<em>Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_POLICY_FEATURE_COUNT = CamelPackage.REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_POLICY_OPERATION_COUNT = CamelPackage.REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.HorizontalScalabilityPolicyImpl <em>Horizontal Scalability Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.HorizontalScalabilityPolicyImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getHorizontalScalabilityPolicy()
	 * @generated
	 */
	int HORIZONTAL_SCALABILITY_POLICY = 26;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_SCALABILITY_POLICY__PRIORITY = SCALABILITY_POLICY__PRIORITY;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_SCALABILITY_POLICY__ID = SCALABILITY_POLICY__ID;

	/**
	 * The feature id for the '<em><b>Min Instances</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_SCALABILITY_POLICY__MIN_INSTANCES = SCALABILITY_POLICY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Max Instances</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_SCALABILITY_POLICY__MAX_INSTANCES = SCALABILITY_POLICY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_SCALABILITY_POLICY__COMPONENT = SCALABILITY_POLICY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Horizontal Scalability Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_SCALABILITY_POLICY_FEATURE_COUNT = SCALABILITY_POLICY_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Horizontal Scalability Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_SCALABILITY_POLICY_OPERATION_COUNT = SCALABILITY_POLICY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.VerticalScalabilityPolicyImpl <em>Vertical Scalability Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.VerticalScalabilityPolicyImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getVerticalScalabilityPolicy()
	 * @generated
	 */
	int VERTICAL_SCALABILITY_POLICY = 27;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY__PRIORITY = SCALABILITY_POLICY__PRIORITY;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY__ID = SCALABILITY_POLICY__ID;

	/**
	 * The feature id for the '<em><b>Min Cores</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY__MIN_CORES = SCALABILITY_POLICY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Max Cores</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY__MAX_CORES = SCALABILITY_POLICY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Min Memory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY__MIN_MEMORY = SCALABILITY_POLICY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Max Memory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY__MAX_MEMORY = SCALABILITY_POLICY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Min CPU</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY__MIN_CPU = SCALABILITY_POLICY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Max CPU</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY__MAX_CPU = SCALABILITY_POLICY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Min Storage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY__MIN_STORAGE = SCALABILITY_POLICY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Max Storage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY__MAX_STORAGE = SCALABILITY_POLICY_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Vm</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY__VM = SCALABILITY_POLICY_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Vertical Scalability Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY_FEATURE_COUNT = SCALABILITY_POLICY_FEATURE_COUNT + 9;

	/**
	 * The number of operations of the '<em>Vertical Scalability Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_SCALABILITY_POLICY_OPERATION_COUNT = SCALABILITY_POLICY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.ScalabilityRuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.ScalabilityRuleImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getScalabilityRule()
	 * @generated
	 */
	int SCALABILITY_RULE = 28;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_RULE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Related Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_RULE__RELATED_EVENT = 1;

	/**
	 * The feature id for the '<em><b>Maps To Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_RULE__MAPS_TO_ACTIONS = 2;

	/**
	 * The feature id for the '<em><b>Defined By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_RULE__DEFINED_BY = 3;

	/**
	 * The feature id for the '<em><b>Invariant Policies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_RULE__INVARIANT_POLICIES = 4;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_RULE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_RULE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.ScalingActionImpl <em>Scaling Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.ScalingActionImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getScalingAction()
	 * @generated
	 */
	int SCALING_ACTION = 29;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__NAME = CamelPackage.ACTION__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__TYPE = CamelPackage.ACTION__TYPE;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__COUNT = CamelPackage.ACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Memory Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__MEMORY_UPDATE = CamelPackage.ACTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>CPU Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__CPU_UPDATE = CamelPackage.ACTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Core Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__CORE_UPDATE = CamelPackage.ACTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Storage Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__STORAGE_UPDATE = CamelPackage.ACTION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Io Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__IO_UPDATE = CamelPackage.ACTION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Network Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__NETWORK_UPDATE = CamelPackage.ACTION_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Vm Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__VM_INSTANCE = CamelPackage.ACTION_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Component Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__COMPONENT_INSTANCE = CamelPackage.ACTION_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Containment Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION__CONTAINMENT_INSTANCE = CamelPackage.ACTION_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Scaling Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION_FEATURE_COUNT = CamelPackage.ACTION_FEATURE_COUNT + 10;

	/**
	 * The number of operations of the '<em>Scaling Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALING_ACTION_OPERATION_COUNT = CamelPackage.ACTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.ScheduleImpl <em>Schedule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.ScheduleImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getSchedule()
	 * @generated
	 */
	int SCHEDULE = 30;

	/**
	 * The feature id for the '<em><b>Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__START = 0;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__END = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__TYPE = 2;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__UNIT = 3;

	/**
	 * The feature id for the '<em><b>Repetitions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__REPETITIONS = 4;

	/**
	 * The feature id for the '<em><b>Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__INTERVAL = 5;

	/**
	 * The number of structural features of the '<em>Schedule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE_FEATURE_COUNT = 6;

	/**
	 * The operation id for the '<em>Check Start End Dates</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE___CHECK_START_END_DATES__SCHEDULE = 0;

	/**
	 * The operation id for the '<em>Check Interval Repetitions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE___CHECK_INTERVAL_REPETITIONS__SCHEDULE = 1;

	/**
	 * The number of operations of the '<em>Schedule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.SensorImpl <em>Sensor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.SensorImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getSensor()
	 * @generated
	 */
	int SENSOR = 31;

	/**
	 * The feature id for the '<em><b>Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR__CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Is Push</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR__IS_PUSH = 1;

	/**
	 * The number of structural features of the '<em>Sensor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Sensor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENSOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.TimerImpl <em>Timer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.TimerImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getTimer()
	 * @generated
	 */
	int TIMER = 32;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMER__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Time Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMER__TIME_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Max Occurrence Num</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMER__MAX_OCCURRENCE_NUM = 2;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMER__UNIT = 3;

	/**
	 * The number of structural features of the '<em>Timer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMER_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Timer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.scalability.impl.WindowImpl <em>Window</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.impl.WindowImpl
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getWindow()
	 * @generated
	 */
	int WINDOW = 33;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW__UNIT = 0;

	/**
	 * The feature id for the '<em><b>Window Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW__WINDOW_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Size Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW__SIZE_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Measurement Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW__MEASUREMENT_SIZE = 3;

	/**
	 * The feature id for the '<em><b>Time Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW__TIME_SIZE = 4;

	/**
	 * The number of structural features of the '<em>Window</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Window</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.scalability.BinaryPatternOperatorType <em>Binary Pattern Operator Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.BinaryPatternOperatorType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getBinaryPatternOperatorType()
	 * @generated
	 */
	int BINARY_PATTERN_OPERATOR_TYPE = 34;

	/**
	 * The meta object id for the '{@link camel.scalability.ComparisonOperatorType <em>Comparison Operator Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.ComparisonOperatorType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getComparisonOperatorType()
	 * @generated
	 */
	int COMPARISON_OPERATOR_TYPE = 35;

	/**
	 * The meta object id for the '{@link camel.scalability.LayerType <em>Layer Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.LayerType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getLayerType()
	 * @generated
	 */
	int LAYER_TYPE = 36;

	/**
	 * The meta object id for the '{@link camel.scalability.MetricFunctionArityType <em>Metric Function Arity Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.MetricFunctionArityType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricFunctionArityType()
	 * @generated
	 */
	int METRIC_FUNCTION_ARITY_TYPE = 37;

	/**
	 * The meta object id for the '{@link camel.scalability.MetricFunctionType <em>Metric Function Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.MetricFunctionType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricFunctionType()
	 * @generated
	 */
	int METRIC_FUNCTION_TYPE = 38;

	/**
	 * The meta object id for the '{@link camel.scalability.MetricType <em>Metric Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.MetricType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricType()
	 * @generated
	 */
	int METRIC_TYPE = 39;

	/**
	 * The meta object id for the '{@link camel.scalability.PropertyType <em>Property Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.PropertyType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getPropertyType()
	 * @generated
	 */
	int PROPERTY_TYPE = 40;

	/**
	 * The meta object id for the '{@link camel.scalability.ScheduleType <em>Schedule Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.ScheduleType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getScheduleType()
	 * @generated
	 */
	int SCHEDULE_TYPE = 41;

	/**
	 * The meta object id for the '{@link camel.scalability.StatusType <em>Status Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.StatusType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getStatusType()
	 * @generated
	 */
	int STATUS_TYPE = 42;

	/**
	 * The meta object id for the '{@link camel.scalability.TimerType <em>Timer Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.TimerType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getTimerType()
	 * @generated
	 */
	int TIMER_TYPE = 43;

	/**
	 * The meta object id for the '{@link camel.scalability.UnaryPatternOperatorType <em>Unary Pattern Operator Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.UnaryPatternOperatorType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getUnaryPatternOperatorType()
	 * @generated
	 */
	int UNARY_PATTERN_OPERATOR_TYPE = 44;

	/**
	 * The meta object id for the '{@link camel.scalability.WindowSizeType <em>Window Size Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.WindowSizeType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getWindowSizeType()
	 * @generated
	 */
	int WINDOW_SIZE_TYPE = 45;

	/**
	 * The meta object id for the '{@link camel.scalability.WindowType <em>Window Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.scalability.WindowType
	 * @see camel.scalability.impl.ScalabilityPackageImpl#getWindowType()
	 * @generated
	 */
	int WINDOW_TYPE = 46;


	/**
	 * Returns the meta object for class '{@link camel.scalability.ScalabilityModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see camel.scalability.ScalabilityModel
	 * @generated
	 */
	EClass getScalabilityModel();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getMetricTemplates <em>Metric Templates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metric Templates</em>'.
	 * @see camel.scalability.ScalabilityModel#getMetricTemplates()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_MetricTemplates();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getMetrics <em>Metrics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metrics</em>'.
	 * @see camel.scalability.ScalabilityModel#getMetrics()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Metrics();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see camel.scalability.ScalabilityModel#getRules()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Rules();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getEvents <em>Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Events</em>'.
	 * @see camel.scalability.ScalabilityModel#getEvents()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Events();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getEventInstances <em>Event Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event Instances</em>'.
	 * @see camel.scalability.ScalabilityModel#getEventInstances()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_EventInstances();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conditions</em>'.
	 * @see camel.scalability.ScalabilityModel#getConditions()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Conditions();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see camel.scalability.ScalabilityModel#getProperties()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Properties();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see camel.scalability.ScalabilityModel#getActions()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Actions();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getFormulas <em>Formulas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Formulas</em>'.
	 * @see camel.scalability.ScalabilityModel#getFormulas()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Formulas();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getBindings <em>Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bindings</em>'.
	 * @see camel.scalability.ScalabilityModel#getBindings()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Bindings();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getBindingInstances <em>Binding Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Binding Instances</em>'.
	 * @see camel.scalability.ScalabilityModel#getBindingInstances()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_BindingInstances();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getWindows <em>Windows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Windows</em>'.
	 * @see camel.scalability.ScalabilityModel#getWindows()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Windows();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getSchedules <em>Schedules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Schedules</em>'.
	 * @see camel.scalability.ScalabilityModel#getSchedules()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Schedules();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see camel.scalability.ScalabilityModel#getParameters()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getPatterns <em>Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Patterns</em>'.
	 * @see camel.scalability.ScalabilityModel#getPatterns()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Patterns();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getTimers <em>Timers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Timers</em>'.
	 * @see camel.scalability.ScalabilityModel#getTimers()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Timers();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getSensors <em>Sensors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sensors</em>'.
	 * @see camel.scalability.ScalabilityModel#getSensors()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Sensors();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getUnits <em>Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Units</em>'.
	 * @see camel.scalability.ScalabilityModel#getUnits()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Units();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getValueTypes <em>Value Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value Types</em>'.
	 * @see camel.scalability.ScalabilityModel#getValueTypes()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_ValueTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.scalability.ScalabilityModel#getPolicies <em>Policies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Policies</em>'.
	 * @see camel.scalability.ScalabilityModel#getPolicies()
	 * @see #getScalabilityModel()
	 * @generated
	 */
	EReference getScalabilityModel_Policies();

	/**
	 * Returns the meta object for class '{@link camel.scalability.Condition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Condition</em>'.
	 * @see camel.scalability.Condition
	 * @generated
	 */
	EClass getCondition();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Condition#getValidity <em>Validity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Validity</em>'.
	 * @see camel.scalability.Condition#getValidity()
	 * @see #getCondition()
	 * @generated
	 */
	EAttribute getCondition_Validity();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Condition#getThreshold <em>Threshold</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Threshold</em>'.
	 * @see camel.scalability.Condition#getThreshold()
	 * @see #getCondition()
	 * @generated
	 */
	EAttribute getCondition_Threshold();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Condition#getComparisonOperator <em>Comparison Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comparison Operator</em>'.
	 * @see camel.scalability.Condition#getComparisonOperator()
	 * @see #getCondition()
	 * @generated
	 */
	EAttribute getCondition_ComparisonOperator();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricCondition <em>Metric Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Condition</em>'.
	 * @see camel.scalability.MetricCondition
	 * @generated
	 */
	EClass getMetricCondition();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricCondition#getMetric <em>Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Metric</em>'.
	 * @see camel.scalability.MetricCondition#getMetric()
	 * @see #getMetricCondition()
	 * @generated
	 */
	EReference getMetricCondition_Metric();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricTemplateCondition <em>Metric Template Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Template Condition</em>'.
	 * @see camel.scalability.MetricTemplateCondition
	 * @generated
	 */
	EClass getMetricTemplateCondition();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricTemplateCondition#getMetricTemplate <em>Metric Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Metric Template</em>'.
	 * @see camel.scalability.MetricTemplateCondition#getMetricTemplate()
	 * @see #getMetricTemplateCondition()
	 * @generated
	 */
	EReference getMetricTemplateCondition_MetricTemplate();

	/**
	 * Returns the meta object for class '{@link camel.scalability.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event</em>'.
	 * @see camel.scalability.Event
	 * @generated
	 */
	EClass getEvent();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Event#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.scalability.Event#getName()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_Name();

	/**
	 * Returns the meta object for class '{@link camel.scalability.EventPattern <em>Event Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Pattern</em>'.
	 * @see camel.scalability.EventPattern
	 * @generated
	 */
	EClass getEventPattern();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.EventPattern#getTimer <em>Timer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Timer</em>'.
	 * @see camel.scalability.EventPattern#getTimer()
	 * @see #getEventPattern()
	 * @generated
	 */
	EReference getEventPattern_Timer();

	/**
	 * Returns the meta object for the '{@link camel.scalability.EventPattern#includesEvent(camel.scalability.SimpleEvent) <em>Includes Event</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Includes Event</em>' operation.
	 * @see camel.scalability.EventPattern#includesEvent(camel.scalability.SimpleEvent)
	 * @generated
	 */
	EOperation getEventPattern__IncludesEvent__SimpleEvent();

	/**
	 * Returns the meta object for the '{@link camel.scalability.EventPattern#includesLeftEvent(camel.scalability.SimpleEvent) <em>Includes Left Event</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Includes Left Event</em>' operation.
	 * @see camel.scalability.EventPattern#includesLeftEvent(camel.scalability.SimpleEvent)
	 * @generated
	 */
	EOperation getEventPattern__IncludesLeftEvent__SimpleEvent();

	/**
	 * Returns the meta object for the '{@link camel.scalability.EventPattern#includesRightEvent(camel.scalability.SimpleEvent) <em>Includes Right Event</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Includes Right Event</em>' operation.
	 * @see camel.scalability.EventPattern#includesRightEvent(camel.scalability.SimpleEvent)
	 * @generated
	 */
	EOperation getEventPattern__IncludesRightEvent__SimpleEvent();

	/**
	 * Returns the meta object for the '{@link camel.scalability.EventPattern#relatedToExecutionContext(camel.execution.ExecutionContext) <em>Related To Execution Context</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Related To Execution Context</em>' operation.
	 * @see camel.scalability.EventPattern#relatedToExecutionContext(camel.execution.ExecutionContext)
	 * @generated
	 */
	EOperation getEventPattern__RelatedToExecutionContext__ExecutionContext();

	/**
	 * Returns the meta object for the '{@link camel.scalability.EventPattern#leftRelatedToExecutionContext(camel.execution.ExecutionContext) <em>Left Related To Execution Context</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Left Related To Execution Context</em>' operation.
	 * @see camel.scalability.EventPattern#leftRelatedToExecutionContext(camel.execution.ExecutionContext)
	 * @generated
	 */
	EOperation getEventPattern__LeftRelatedToExecutionContext__ExecutionContext();

	/**
	 * Returns the meta object for the '{@link camel.scalability.EventPattern#rightRelatedToExecutionContext(camel.execution.ExecutionContext) <em>Right Related To Execution Context</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Right Related To Execution Context</em>' operation.
	 * @see camel.scalability.EventPattern#rightRelatedToExecutionContext(camel.execution.ExecutionContext)
	 * @generated
	 */
	EOperation getEventPattern__RightRelatedToExecutionContext__ExecutionContext();

	/**
	 * Returns the meta object for class '{@link camel.scalability.BinaryEventPattern <em>Binary Event Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Event Pattern</em>'.
	 * @see camel.scalability.BinaryEventPattern
	 * @generated
	 */
	EClass getBinaryEventPattern();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.BinaryEventPattern#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Left</em>'.
	 * @see camel.scalability.BinaryEventPattern#getLeft()
	 * @see #getBinaryEventPattern()
	 * @generated
	 */
	EReference getBinaryEventPattern_Left();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.BinaryEventPattern#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Right</em>'.
	 * @see camel.scalability.BinaryEventPattern#getRight()
	 * @see #getBinaryEventPattern()
	 * @generated
	 */
	EReference getBinaryEventPattern_Right();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.BinaryEventPattern#getLowerOccurrenceBound <em>Lower Occurrence Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Occurrence Bound</em>'.
	 * @see camel.scalability.BinaryEventPattern#getLowerOccurrenceBound()
	 * @see #getBinaryEventPattern()
	 * @generated
	 */
	EAttribute getBinaryEventPattern_LowerOccurrenceBound();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.BinaryEventPattern#getUpperOccurrenceBound <em>Upper Occurrence Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Occurrence Bound</em>'.
	 * @see camel.scalability.BinaryEventPattern#getUpperOccurrenceBound()
	 * @see #getBinaryEventPattern()
	 * @generated
	 */
	EAttribute getBinaryEventPattern_UpperOccurrenceBound();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.BinaryEventPattern#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see camel.scalability.BinaryEventPattern#getOperator()
	 * @see #getBinaryEventPattern()
	 * @generated
	 */
	EAttribute getBinaryEventPattern_Operator();

	/**
	 * Returns the meta object for class '{@link camel.scalability.UnaryEventPattern <em>Unary Event Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Event Pattern</em>'.
	 * @see camel.scalability.UnaryEventPattern
	 * @generated
	 */
	EClass getUnaryEventPattern();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.UnaryEventPattern#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Event</em>'.
	 * @see camel.scalability.UnaryEventPattern#getEvent()
	 * @see #getUnaryEventPattern()
	 * @generated
	 */
	EReference getUnaryEventPattern_Event();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.UnaryEventPattern#getOccurrenceNum <em>Occurrence Num</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Occurrence Num</em>'.
	 * @see camel.scalability.UnaryEventPattern#getOccurrenceNum()
	 * @see #getUnaryEventPattern()
	 * @generated
	 */
	EAttribute getUnaryEventPattern_OccurrenceNum();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.UnaryEventPattern#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see camel.scalability.UnaryEventPattern#getOperator()
	 * @see #getUnaryEventPattern()
	 * @generated
	 */
	EAttribute getUnaryEventPattern_Operator();

	/**
	 * Returns the meta object for class '{@link camel.scalability.SimpleEvent <em>Simple Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Event</em>'.
	 * @see camel.scalability.SimpleEvent
	 * @generated
	 */
	EClass getSimpleEvent();

	/**
	 * Returns the meta object for class '{@link camel.scalability.FunctionalEvent <em>Functional Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Functional Event</em>'.
	 * @see camel.scalability.FunctionalEvent
	 * @generated
	 */
	EClass getFunctionalEvent();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.FunctionalEvent#getFunctionalType <em>Functional Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Functional Type</em>'.
	 * @see camel.scalability.FunctionalEvent#getFunctionalType()
	 * @see #getFunctionalEvent()
	 * @generated
	 */
	EAttribute getFunctionalEvent_FunctionalType();

	/**
	 * Returns the meta object for class '{@link camel.scalability.NonFunctionalEvent <em>Non Functional Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Non Functional Event</em>'.
	 * @see camel.scalability.NonFunctionalEvent
	 * @generated
	 */
	EClass getNonFunctionalEvent();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.NonFunctionalEvent#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Condition</em>'.
	 * @see camel.scalability.NonFunctionalEvent#getCondition()
	 * @see #getNonFunctionalEvent()
	 * @generated
	 */
	EReference getNonFunctionalEvent_Condition();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.NonFunctionalEvent#isIsViolation <em>Is Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Violation</em>'.
	 * @see camel.scalability.NonFunctionalEvent#isIsViolation()
	 * @see #getNonFunctionalEvent()
	 * @generated
	 */
	EAttribute getNonFunctionalEvent_IsViolation();

	/**
	 * Returns the meta object for class '{@link camel.scalability.EventInstance <em>Event Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Instance</em>'.
	 * @see camel.scalability.EventInstance
	 * @generated
	 */
	EClass getEventInstance();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.EventInstance#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status</em>'.
	 * @see camel.scalability.EventInstance#getStatus()
	 * @see #getEventInstance()
	 * @generated
	 */
	EAttribute getEventInstance_Status();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.EventInstance#getLayer <em>Layer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Layer</em>'.
	 * @see camel.scalability.EventInstance#getLayer()
	 * @see #getEventInstance()
	 * @generated
	 */
	EAttribute getEventInstance_Layer();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.EventInstance#getOnEvent <em>On Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>On Event</em>'.
	 * @see camel.scalability.EventInstance#getOnEvent()
	 * @see #getEventInstance()
	 * @generated
	 */
	EReference getEventInstance_OnEvent();

	/**
	 * Returns the meta object for the '{@link camel.scalability.EventInstance#equalLayer(camel.scalability.LayerType, camel.scalability.LayerType) <em>Equal Layer</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Equal Layer</em>' operation.
	 * @see camel.scalability.EventInstance#equalLayer(camel.scalability.LayerType, camel.scalability.LayerType)
	 * @generated
	 */
	EOperation getEventInstance__EqualLayer__LayerType_LayerType();

	/**
	 * Returns the meta object for class '{@link camel.scalability.Metric <em>Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric</em>'.
	 * @see camel.scalability.Metric
	 * @generated
	 */
	EClass getMetric();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Metric#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see camel.scalability.Metric#getId()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_Id();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.Metric#getHasTemplate <em>Has Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Has Template</em>'.
	 * @see camel.scalability.Metric#getHasTemplate()
	 * @see #getMetric()
	 * @generated
	 */
	EReference getMetric_HasTemplate();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.Metric#getHasSchedule <em>Has Schedule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Has Schedule</em>'.
	 * @see camel.scalability.Metric#getHasSchedule()
	 * @see #getMetric()
	 * @generated
	 */
	EReference getMetric_HasSchedule();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.Metric#getWindow <em>Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Window</em>'.
	 * @see camel.scalability.Metric#getWindow()
	 * @see #getMetric()
	 * @generated
	 */
	EReference getMetric_Window();

	/**
	 * Returns the meta object for the reference list '{@link camel.scalability.Metric#getComponentMetrics <em>Component Metrics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Component Metrics</em>'.
	 * @see camel.scalability.Metric#getComponentMetrics()
	 * @see #getMetric()
	 * @generated
	 */
	EReference getMetric_ComponentMetrics();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.Metric#getSensor <em>Sensor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sensor</em>'.
	 * @see camel.scalability.Metric#getSensor()
	 * @see #getMetric()
	 * @generated
	 */
	EReference getMetric_Sensor();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.Metric#getObjectBinding <em>Object Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Object Binding</em>'.
	 * @see camel.scalability.Metric#getObjectBinding()
	 * @see #getMetric()
	 * @generated
	 */
	EReference getMetric_ObjectBinding();

	/**
	 * Returns the meta object for the containment reference '{@link camel.scalability.Metric#getValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Type</em>'.
	 * @see camel.scalability.Metric#getValueType()
	 * @see #getMetric()
	 * @generated
	 */
	EReference getMetric_ValueType();

	/**
	 * Returns the meta object for the '{@link camel.scalability.Metric#checkRecursiveness(camel.scalability.Metric, camel.scalability.Metric) <em>Check Recursiveness</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Recursiveness</em>' operation.
	 * @see camel.scalability.Metric#checkRecursiveness(camel.scalability.Metric, camel.scalability.Metric)
	 * @generated
	 */
	EOperation getMetric__CheckRecursiveness__Metric_Metric();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricFormulaParameter <em>Metric Formula Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Formula Parameter</em>'.
	 * @see camel.scalability.MetricFormulaParameter
	 * @generated
	 */
	EClass getMetricFormulaParameter();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricFormulaParameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see camel.scalability.MetricFormulaParameter#getValue()
	 * @see #getMetricFormulaParameter()
	 * @generated
	 */
	EReference getMetricFormulaParameter_Value();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricFormula <em>Metric Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Formula</em>'.
	 * @see camel.scalability.MetricFormula
	 * @generated
	 */
	EClass getMetricFormula();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.MetricFormula#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function</em>'.
	 * @see camel.scalability.MetricFormula#getFunction()
	 * @see #getMetricFormula()
	 * @generated
	 */
	EAttribute getMetricFormula_Function();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.MetricFormula#getFunctionArity <em>Function Arity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function Arity</em>'.
	 * @see camel.scalability.MetricFormula#getFunctionArity()
	 * @see #getMetricFormula()
	 * @generated
	 */
	EAttribute getMetricFormula_FunctionArity();

	/**
	 * Returns the meta object for the reference list '{@link camel.scalability.MetricFormula#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Parameters</em>'.
	 * @see camel.scalability.MetricFormula#getParameters()
	 * @see #getMetricFormula()
	 * @generated
	 */
	EReference getMetricFormula_Parameters();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricTemplate <em>Metric Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Template</em>'.
	 * @see camel.scalability.MetricTemplate
	 * @generated
	 */
	EClass getMetricTemplate();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.MetricTemplate#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.scalability.MetricTemplate#getName()
	 * @see #getMetricTemplate()
	 * @generated
	 */
	EAttribute getMetricTemplate_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.MetricTemplate#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see camel.scalability.MetricTemplate#getDescription()
	 * @see #getMetricTemplate()
	 * @generated
	 */
	EAttribute getMetricTemplate_Description();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.MetricTemplate#getValueDirection <em>Value Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Direction</em>'.
	 * @see camel.scalability.MetricTemplate#getValueDirection()
	 * @see #getMetricTemplate()
	 * @generated
	 */
	EAttribute getMetricTemplate_ValueDirection();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricTemplate#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Unit</em>'.
	 * @see camel.scalability.MetricTemplate#getUnit()
	 * @see #getMetricTemplate()
	 * @generated
	 */
	EReference getMetricTemplate_Unit();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.MetricTemplate#getLayer <em>Layer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Layer</em>'.
	 * @see camel.scalability.MetricTemplate#getLayer()
	 * @see #getMetricTemplate()
	 * @generated
	 */
	EAttribute getMetricTemplate_Layer();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricTemplate#getMeasures <em>Measures</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Measures</em>'.
	 * @see camel.scalability.MetricTemplate#getMeasures()
	 * @see #getMetricTemplate()
	 * @generated
	 */
	EReference getMetricTemplate_Measures();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.MetricTemplate#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see camel.scalability.MetricTemplate#getType()
	 * @see #getMetricTemplate()
	 * @generated
	 */
	EAttribute getMetricTemplate_Type();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricTemplate#getFormula <em>Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Formula</em>'.
	 * @see camel.scalability.MetricTemplate#getFormula()
	 * @see #getMetricTemplate()
	 * @generated
	 */
	EReference getMetricTemplate_Formula();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricTemplate#getObjectBinding <em>Object Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Object Binding</em>'.
	 * @see camel.scalability.MetricTemplate#getObjectBinding()
	 * @see #getMetricTemplate()
	 * @generated
	 */
	EReference getMetricTemplate_ObjectBinding();

	/**
	 * Returns the meta object for the '{@link camel.scalability.MetricTemplate#checkRecursiveness(camel.scalability.MetricTemplate, camel.scalability.MetricTemplate) <em>Check Recursiveness</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Recursiveness</em>' operation.
	 * @see camel.scalability.MetricTemplate#checkRecursiveness(camel.scalability.MetricTemplate, camel.scalability.MetricTemplate)
	 * @generated
	 */
	EOperation getMetricTemplate__CheckRecursiveness__MetricTemplate_MetricTemplate();

	/**
	 * Returns the meta object for the '{@link camel.scalability.MetricTemplate#greaterEqualThanLayer(camel.scalability.LayerType, camel.scalability.LayerType) <em>Greater Equal Than Layer</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Greater Equal Than Layer</em>' operation.
	 * @see camel.scalability.MetricTemplate#greaterEqualThanLayer(camel.scalability.LayerType, camel.scalability.LayerType)
	 * @generated
	 */
	EOperation getMetricTemplate__GreaterEqualThanLayer__LayerType_LayerType();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricObjectBinding <em>Metric Object Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Object Binding</em>'.
	 * @see camel.scalability.MetricObjectBinding
	 * @generated
	 */
	EClass getMetricObjectBinding();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricObjectBinding#getApplication <em>Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Application</em>'.
	 * @see camel.scalability.MetricObjectBinding#getApplication()
	 * @see #getMetricObjectBinding()
	 * @generated
	 */
	EReference getMetricObjectBinding_Application();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricApplicationBinding <em>Metric Application Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Application Binding</em>'.
	 * @see camel.scalability.MetricApplicationBinding
	 * @generated
	 */
	EClass getMetricApplicationBinding();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricComponentBinding <em>Metric Component Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Component Binding</em>'.
	 * @see camel.scalability.MetricComponentBinding
	 * @generated
	 */
	EClass getMetricComponentBinding();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricComponentBinding#getVm <em>Vm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Vm</em>'.
	 * @see camel.scalability.MetricComponentBinding#getVm()
	 * @see #getMetricComponentBinding()
	 * @generated
	 */
	EReference getMetricComponentBinding_Vm();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricComponentBinding#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Component</em>'.
	 * @see camel.scalability.MetricComponentBinding#getComponent()
	 * @see #getMetricComponentBinding()
	 * @generated
	 */
	EReference getMetricComponentBinding_Component();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricVMBinding <em>Metric VM Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric VM Binding</em>'.
	 * @see camel.scalability.MetricVMBinding
	 * @generated
	 */
	EClass getMetricVMBinding();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricVMBinding#getVm <em>Vm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Vm</em>'.
	 * @see camel.scalability.MetricVMBinding#getVm()
	 * @see #getMetricVMBinding()
	 * @generated
	 */
	EReference getMetricVMBinding_Vm();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricObjectInstanceBinding <em>Metric Object Instance Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Object Instance Binding</em>'.
	 * @see camel.scalability.MetricObjectInstanceBinding
	 * @generated
	 */
	EClass getMetricObjectInstanceBinding();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricObjectInstanceBinding#getExecutionContext <em>Execution Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Execution Context</em>'.
	 * @see camel.scalability.MetricObjectInstanceBinding#getExecutionContext()
	 * @see #getMetricObjectInstanceBinding()
	 * @generated
	 */
	EReference getMetricObjectInstanceBinding_ExecutionContext();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricApplicationInstanceBinding <em>Metric Application Instance Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Application Instance Binding</em>'.
	 * @see camel.scalability.MetricApplicationInstanceBinding
	 * @generated
	 */
	EClass getMetricApplicationInstanceBinding();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricComponentInstanceBinding <em>Metric Component Instance Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Component Instance Binding</em>'.
	 * @see camel.scalability.MetricComponentInstanceBinding
	 * @generated
	 */
	EClass getMetricComponentInstanceBinding();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricComponentInstanceBinding#getVmInstance <em>Vm Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Vm Instance</em>'.
	 * @see camel.scalability.MetricComponentInstanceBinding#getVmInstance()
	 * @see #getMetricComponentInstanceBinding()
	 * @generated
	 */
	EReference getMetricComponentInstanceBinding_VmInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricComponentInstanceBinding#getComponentInstance <em>Component Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Component Instance</em>'.
	 * @see camel.scalability.MetricComponentInstanceBinding#getComponentInstance()
	 * @see #getMetricComponentInstanceBinding()
	 * @generated
	 */
	EReference getMetricComponentInstanceBinding_ComponentInstance();

	/**
	 * Returns the meta object for class '{@link camel.scalability.MetricVMInstanceBinding <em>Metric VM Instance Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric VM Instance Binding</em>'.
	 * @see camel.scalability.MetricVMInstanceBinding
	 * @generated
	 */
	EClass getMetricVMInstanceBinding();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.MetricVMInstanceBinding#getVmInstance <em>Vm Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Vm Instance</em>'.
	 * @see camel.scalability.MetricVMInstanceBinding#getVmInstance()
	 * @see #getMetricVMInstanceBinding()
	 * @generated
	 */
	EReference getMetricVMInstanceBinding_VmInstance();

	/**
	 * Returns the meta object for class '{@link camel.scalability.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see camel.scalability.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Property#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see camel.scalability.Property#getId()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Id();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Property#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.scalability.Property#getName()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Property#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see camel.scalability.Property#getDescription()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Description();

	/**
	 * Returns the meta object for the reference list '{@link camel.scalability.Property#getRealizedBy <em>Realized By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Realized By</em>'.
	 * @see camel.scalability.Property#getRealizedBy()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_RealizedBy();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Property#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see camel.scalability.Property#getType()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Type();

	/**
	 * Returns the meta object for class '{@link camel.scalability.ScalabilityPolicy <em>Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Policy</em>'.
	 * @see camel.scalability.ScalabilityPolicy
	 * @generated
	 */
	EClass getScalabilityPolicy();

	/**
	 * Returns the meta object for class '{@link camel.scalability.HorizontalScalabilityPolicy <em>Horizontal Scalability Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Horizontal Scalability Policy</em>'.
	 * @see camel.scalability.HorizontalScalabilityPolicy
	 * @generated
	 */
	EClass getHorizontalScalabilityPolicy();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.HorizontalScalabilityPolicy#getMinInstances <em>Min Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Instances</em>'.
	 * @see camel.scalability.HorizontalScalabilityPolicy#getMinInstances()
	 * @see #getHorizontalScalabilityPolicy()
	 * @generated
	 */
	EAttribute getHorizontalScalabilityPolicy_MinInstances();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.HorizontalScalabilityPolicy#getMaxInstances <em>Max Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Instances</em>'.
	 * @see camel.scalability.HorizontalScalabilityPolicy#getMaxInstances()
	 * @see #getHorizontalScalabilityPolicy()
	 * @generated
	 */
	EAttribute getHorizontalScalabilityPolicy_MaxInstances();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.HorizontalScalabilityPolicy#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Component</em>'.
	 * @see camel.scalability.HorizontalScalabilityPolicy#getComponent()
	 * @see #getHorizontalScalabilityPolicy()
	 * @generated
	 */
	EReference getHorizontalScalabilityPolicy_Component();

	/**
	 * Returns the meta object for class '{@link camel.scalability.VerticalScalabilityPolicy <em>Vertical Scalability Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vertical Scalability Policy</em>'.
	 * @see camel.scalability.VerticalScalabilityPolicy
	 * @generated
	 */
	EClass getVerticalScalabilityPolicy();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.VerticalScalabilityPolicy#getMinCores <em>Min Cores</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Cores</em>'.
	 * @see camel.scalability.VerticalScalabilityPolicy#getMinCores()
	 * @see #getVerticalScalabilityPolicy()
	 * @generated
	 */
	EAttribute getVerticalScalabilityPolicy_MinCores();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.VerticalScalabilityPolicy#getMaxCores <em>Max Cores</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Cores</em>'.
	 * @see camel.scalability.VerticalScalabilityPolicy#getMaxCores()
	 * @see #getVerticalScalabilityPolicy()
	 * @generated
	 */
	EAttribute getVerticalScalabilityPolicy_MaxCores();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.VerticalScalabilityPolicy#getMinMemory <em>Min Memory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Memory</em>'.
	 * @see camel.scalability.VerticalScalabilityPolicy#getMinMemory()
	 * @see #getVerticalScalabilityPolicy()
	 * @generated
	 */
	EAttribute getVerticalScalabilityPolicy_MinMemory();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.VerticalScalabilityPolicy#getMaxMemory <em>Max Memory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Memory</em>'.
	 * @see camel.scalability.VerticalScalabilityPolicy#getMaxMemory()
	 * @see #getVerticalScalabilityPolicy()
	 * @generated
	 */
	EAttribute getVerticalScalabilityPolicy_MaxMemory();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.VerticalScalabilityPolicy#getMinCPU <em>Min CPU</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min CPU</em>'.
	 * @see camel.scalability.VerticalScalabilityPolicy#getMinCPU()
	 * @see #getVerticalScalabilityPolicy()
	 * @generated
	 */
	EAttribute getVerticalScalabilityPolicy_MinCPU();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.VerticalScalabilityPolicy#getMaxCPU <em>Max CPU</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max CPU</em>'.
	 * @see camel.scalability.VerticalScalabilityPolicy#getMaxCPU()
	 * @see #getVerticalScalabilityPolicy()
	 * @generated
	 */
	EAttribute getVerticalScalabilityPolicy_MaxCPU();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.VerticalScalabilityPolicy#getMinStorage <em>Min Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Storage</em>'.
	 * @see camel.scalability.VerticalScalabilityPolicy#getMinStorage()
	 * @see #getVerticalScalabilityPolicy()
	 * @generated
	 */
	EAttribute getVerticalScalabilityPolicy_MinStorage();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.VerticalScalabilityPolicy#getMaxStorage <em>Max Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Storage</em>'.
	 * @see camel.scalability.VerticalScalabilityPolicy#getMaxStorage()
	 * @see #getVerticalScalabilityPolicy()
	 * @generated
	 */
	EAttribute getVerticalScalabilityPolicy_MaxStorage();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.VerticalScalabilityPolicy#getVm <em>Vm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Vm</em>'.
	 * @see camel.scalability.VerticalScalabilityPolicy#getVm()
	 * @see #getVerticalScalabilityPolicy()
	 * @generated
	 */
	EReference getVerticalScalabilityPolicy_Vm();

	/**
	 * Returns the meta object for class '{@link camel.scalability.ScalabilityRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see camel.scalability.ScalabilityRule
	 * @generated
	 */
	EClass getScalabilityRule();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.ScalabilityRule#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.scalability.ScalabilityRule#getName()
	 * @see #getScalabilityRule()
	 * @generated
	 */
	EAttribute getScalabilityRule_Name();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.ScalabilityRule#getRelatedEvent <em>Related Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Related Event</em>'.
	 * @see camel.scalability.ScalabilityRule#getRelatedEvent()
	 * @see #getScalabilityRule()
	 * @generated
	 */
	EReference getScalabilityRule_RelatedEvent();

	/**
	 * Returns the meta object for the reference list '{@link camel.scalability.ScalabilityRule#getMapsToActions <em>Maps To Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Maps To Actions</em>'.
	 * @see camel.scalability.ScalabilityRule#getMapsToActions()
	 * @see #getScalabilityRule()
	 * @generated
	 */
	EReference getScalabilityRule_MapsToActions();

	/**
	 * Returns the meta object for the reference list '{@link camel.scalability.ScalabilityRule#getDefinedBy <em>Defined By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Defined By</em>'.
	 * @see camel.scalability.ScalabilityRule#getDefinedBy()
	 * @see #getScalabilityRule()
	 * @generated
	 */
	EReference getScalabilityRule_DefinedBy();

	/**
	 * Returns the meta object for the reference list '{@link camel.scalability.ScalabilityRule#getInvariantPolicies <em>Invariant Policies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Invariant Policies</em>'.
	 * @see camel.scalability.ScalabilityRule#getInvariantPolicies()
	 * @see #getScalabilityRule()
	 * @generated
	 */
	EReference getScalabilityRule_InvariantPolicies();

	/**
	 * Returns the meta object for class '{@link camel.scalability.ScalingAction <em>Scaling Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scaling Action</em>'.
	 * @see camel.scalability.ScalingAction
	 * @generated
	 */
	EClass getScalingAction();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.ScalingAction#getCount <em>Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Count</em>'.
	 * @see camel.scalability.ScalingAction#getCount()
	 * @see #getScalingAction()
	 * @generated
	 */
	EAttribute getScalingAction_Count();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.ScalingAction#getMemoryUpdate <em>Memory Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Memory Update</em>'.
	 * @see camel.scalability.ScalingAction#getMemoryUpdate()
	 * @see #getScalingAction()
	 * @generated
	 */
	EAttribute getScalingAction_MemoryUpdate();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.ScalingAction#getCPUUpdate <em>CPU Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>CPU Update</em>'.
	 * @see camel.scalability.ScalingAction#getCPUUpdate()
	 * @see #getScalingAction()
	 * @generated
	 */
	EAttribute getScalingAction_CPUUpdate();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.ScalingAction#getCoreUpdate <em>Core Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Core Update</em>'.
	 * @see camel.scalability.ScalingAction#getCoreUpdate()
	 * @see #getScalingAction()
	 * @generated
	 */
	EAttribute getScalingAction_CoreUpdate();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.ScalingAction#getStorageUpdate <em>Storage Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Storage Update</em>'.
	 * @see camel.scalability.ScalingAction#getStorageUpdate()
	 * @see #getScalingAction()
	 * @generated
	 */
	EAttribute getScalingAction_StorageUpdate();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.ScalingAction#getIoUpdate <em>Io Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Io Update</em>'.
	 * @see camel.scalability.ScalingAction#getIoUpdate()
	 * @see #getScalingAction()
	 * @generated
	 */
	EAttribute getScalingAction_IoUpdate();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.ScalingAction#getNetworkUpdate <em>Network Update</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Network Update</em>'.
	 * @see camel.scalability.ScalingAction#getNetworkUpdate()
	 * @see #getScalingAction()
	 * @generated
	 */
	EAttribute getScalingAction_NetworkUpdate();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.ScalingAction#getVmInstance <em>Vm Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Vm Instance</em>'.
	 * @see camel.scalability.ScalingAction#getVmInstance()
	 * @see #getScalingAction()
	 * @generated
	 */
	EReference getScalingAction_VmInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.ScalingAction#getComponentInstance <em>Component Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Component Instance</em>'.
	 * @see camel.scalability.ScalingAction#getComponentInstance()
	 * @see #getScalingAction()
	 * @generated
	 */
	EReference getScalingAction_ComponentInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.ScalingAction#getContainmentInstance <em>Containment Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Containment Instance</em>'.
	 * @see camel.scalability.ScalingAction#getContainmentInstance()
	 * @see #getScalingAction()
	 * @generated
	 */
	EReference getScalingAction_ContainmentInstance();

	/**
	 * Returns the meta object for class '{@link camel.scalability.Schedule <em>Schedule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Schedule</em>'.
	 * @see camel.scalability.Schedule
	 * @generated
	 */
	EClass getSchedule();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Schedule#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start</em>'.
	 * @see camel.scalability.Schedule#getStart()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_Start();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Schedule#getEnd <em>End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End</em>'.
	 * @see camel.scalability.Schedule#getEnd()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_End();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Schedule#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see camel.scalability.Schedule#getType()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_Type();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.Schedule#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Unit</em>'.
	 * @see camel.scalability.Schedule#getUnit()
	 * @see #getSchedule()
	 * @generated
	 */
	EReference getSchedule_Unit();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Schedule#getRepetitions <em>Repetitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repetitions</em>'.
	 * @see camel.scalability.Schedule#getRepetitions()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_Repetitions();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Schedule#getInterval <em>Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Interval</em>'.
	 * @see camel.scalability.Schedule#getInterval()
	 * @see #getSchedule()
	 * @generated
	 */
	EAttribute getSchedule_Interval();

	/**
	 * Returns the meta object for the '{@link camel.scalability.Schedule#checkStartEndDates(camel.scalability.Schedule) <em>Check Start End Dates</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Start End Dates</em>' operation.
	 * @see camel.scalability.Schedule#checkStartEndDates(camel.scalability.Schedule)
	 * @generated
	 */
	EOperation getSchedule__CheckStartEndDates__Schedule();

	/**
	 * Returns the meta object for the '{@link camel.scalability.Schedule#checkIntervalRepetitions(camel.scalability.Schedule) <em>Check Interval Repetitions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Interval Repetitions</em>' operation.
	 * @see camel.scalability.Schedule#checkIntervalRepetitions(camel.scalability.Schedule)
	 * @generated
	 */
	EOperation getSchedule__CheckIntervalRepetitions__Schedule();

	/**
	 * Returns the meta object for class '{@link camel.scalability.Sensor <em>Sensor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sensor</em>'.
	 * @see camel.scalability.Sensor
	 * @generated
	 */
	EClass getSensor();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Sensor#getConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Configuration</em>'.
	 * @see camel.scalability.Sensor#getConfiguration()
	 * @see #getSensor()
	 * @generated
	 */
	EAttribute getSensor_Configuration();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Sensor#isIsPush <em>Is Push</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Push</em>'.
	 * @see camel.scalability.Sensor#isIsPush()
	 * @see #getSensor()
	 * @generated
	 */
	EAttribute getSensor_IsPush();

	/**
	 * Returns the meta object for class '{@link camel.scalability.Timer <em>Timer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Timer</em>'.
	 * @see camel.scalability.Timer
	 * @generated
	 */
	EClass getTimer();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Timer#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see camel.scalability.Timer#getType()
	 * @see #getTimer()
	 * @generated
	 */
	EAttribute getTimer_Type();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Timer#getTimeValue <em>Time Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Value</em>'.
	 * @see camel.scalability.Timer#getTimeValue()
	 * @see #getTimer()
	 * @generated
	 */
	EAttribute getTimer_TimeValue();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Timer#getMaxOccurrenceNum <em>Max Occurrence Num</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Occurrence Num</em>'.
	 * @see camel.scalability.Timer#getMaxOccurrenceNum()
	 * @see #getTimer()
	 * @generated
	 */
	EAttribute getTimer_MaxOccurrenceNum();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.Timer#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Unit</em>'.
	 * @see camel.scalability.Timer#getUnit()
	 * @see #getTimer()
	 * @generated
	 */
	EReference getTimer_Unit();

	/**
	 * Returns the meta object for class '{@link camel.scalability.Window <em>Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Window</em>'.
	 * @see camel.scalability.Window
	 * @generated
	 */
	EClass getWindow();

	/**
	 * Returns the meta object for the reference '{@link camel.scalability.Window#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Unit</em>'.
	 * @see camel.scalability.Window#getUnit()
	 * @see #getWindow()
	 * @generated
	 */
	EReference getWindow_Unit();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Window#getWindowType <em>Window Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Window Type</em>'.
	 * @see camel.scalability.Window#getWindowType()
	 * @see #getWindow()
	 * @generated
	 */
	EAttribute getWindow_WindowType();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Window#getSizeType <em>Size Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size Type</em>'.
	 * @see camel.scalability.Window#getSizeType()
	 * @see #getWindow()
	 * @generated
	 */
	EAttribute getWindow_SizeType();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Window#getMeasurementSize <em>Measurement Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Measurement Size</em>'.
	 * @see camel.scalability.Window#getMeasurementSize()
	 * @see #getWindow()
	 * @generated
	 */
	EAttribute getWindow_MeasurementSize();

	/**
	 * Returns the meta object for the attribute '{@link camel.scalability.Window#getTimeSize <em>Time Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Size</em>'.
	 * @see camel.scalability.Window#getTimeSize()
	 * @see #getWindow()
	 * @generated
	 */
	EAttribute getWindow_TimeSize();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.BinaryPatternOperatorType <em>Binary Pattern Operator Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Binary Pattern Operator Type</em>'.
	 * @see camel.scalability.BinaryPatternOperatorType
	 * @generated
	 */
	EEnum getBinaryPatternOperatorType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.ComparisonOperatorType <em>Comparison Operator Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Comparison Operator Type</em>'.
	 * @see camel.scalability.ComparisonOperatorType
	 * @generated
	 */
	EEnum getComparisonOperatorType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.LayerType <em>Layer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Layer Type</em>'.
	 * @see camel.scalability.LayerType
	 * @generated
	 */
	EEnum getLayerType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.MetricFunctionArityType <em>Metric Function Arity Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Metric Function Arity Type</em>'.
	 * @see camel.scalability.MetricFunctionArityType
	 * @generated
	 */
	EEnum getMetricFunctionArityType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.MetricFunctionType <em>Metric Function Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Metric Function Type</em>'.
	 * @see camel.scalability.MetricFunctionType
	 * @generated
	 */
	EEnum getMetricFunctionType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.MetricType <em>Metric Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Metric Type</em>'.
	 * @see camel.scalability.MetricType
	 * @generated
	 */
	EEnum getMetricType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.PropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Property Type</em>'.
	 * @see camel.scalability.PropertyType
	 * @generated
	 */
	EEnum getPropertyType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.ScheduleType <em>Schedule Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Schedule Type</em>'.
	 * @see camel.scalability.ScheduleType
	 * @generated
	 */
	EEnum getScheduleType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.StatusType <em>Status Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Status Type</em>'.
	 * @see camel.scalability.StatusType
	 * @generated
	 */
	EEnum getStatusType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.TimerType <em>Timer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Timer Type</em>'.
	 * @see camel.scalability.TimerType
	 * @generated
	 */
	EEnum getTimerType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.UnaryPatternOperatorType <em>Unary Pattern Operator Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Unary Pattern Operator Type</em>'.
	 * @see camel.scalability.UnaryPatternOperatorType
	 * @generated
	 */
	EEnum getUnaryPatternOperatorType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.WindowSizeType <em>Window Size Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Window Size Type</em>'.
	 * @see camel.scalability.WindowSizeType
	 * @generated
	 */
	EEnum getWindowSizeType();

	/**
	 * Returns the meta object for enum '{@link camel.scalability.WindowType <em>Window Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Window Type</em>'.
	 * @see camel.scalability.WindowType
	 * @generated
	 */
	EEnum getWindowType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ScalabilityFactory getScalabilityFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link camel.scalability.impl.ScalabilityModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.ScalabilityModelImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getScalabilityModel()
		 * @generated
		 */
		EClass SCALABILITY_MODEL = eINSTANCE.getScalabilityModel();

		/**
		 * The meta object literal for the '<em><b>Metric Templates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__METRIC_TEMPLATES = eINSTANCE.getScalabilityModel_MetricTemplates();

		/**
		 * The meta object literal for the '<em><b>Metrics</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__METRICS = eINSTANCE.getScalabilityModel_Metrics();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__RULES = eINSTANCE.getScalabilityModel_Rules();

		/**
		 * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__EVENTS = eINSTANCE.getScalabilityModel_Events();

		/**
		 * The meta object literal for the '<em><b>Event Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__EVENT_INSTANCES = eINSTANCE.getScalabilityModel_EventInstances();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__CONDITIONS = eINSTANCE.getScalabilityModel_Conditions();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__PROPERTIES = eINSTANCE.getScalabilityModel_Properties();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__ACTIONS = eINSTANCE.getScalabilityModel_Actions();

		/**
		 * The meta object literal for the '<em><b>Formulas</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__FORMULAS = eINSTANCE.getScalabilityModel_Formulas();

		/**
		 * The meta object literal for the '<em><b>Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__BINDINGS = eINSTANCE.getScalabilityModel_Bindings();

		/**
		 * The meta object literal for the '<em><b>Binding Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__BINDING_INSTANCES = eINSTANCE.getScalabilityModel_BindingInstances();

		/**
		 * The meta object literal for the '<em><b>Windows</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__WINDOWS = eINSTANCE.getScalabilityModel_Windows();

		/**
		 * The meta object literal for the '<em><b>Schedules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__SCHEDULES = eINSTANCE.getScalabilityModel_Schedules();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__PARAMETERS = eINSTANCE.getScalabilityModel_Parameters();

		/**
		 * The meta object literal for the '<em><b>Patterns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__PATTERNS = eINSTANCE.getScalabilityModel_Patterns();

		/**
		 * The meta object literal for the '<em><b>Timers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__TIMERS = eINSTANCE.getScalabilityModel_Timers();

		/**
		 * The meta object literal for the '<em><b>Sensors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__SENSORS = eINSTANCE.getScalabilityModel_Sensors();

		/**
		 * The meta object literal for the '<em><b>Units</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__UNITS = eINSTANCE.getScalabilityModel_Units();

		/**
		 * The meta object literal for the '<em><b>Value Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__VALUE_TYPES = eINSTANCE.getScalabilityModel_ValueTypes();

		/**
		 * The meta object literal for the '<em><b>Policies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_MODEL__POLICIES = eINSTANCE.getScalabilityModel_Policies();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.ConditionImpl <em>Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.ConditionImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getCondition()
		 * @generated
		 */
		EClass CONDITION = eINSTANCE.getCondition();

		/**
		 * The meta object literal for the '<em><b>Validity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITION__VALIDITY = eINSTANCE.getCondition_Validity();

		/**
		 * The meta object literal for the '<em><b>Threshold</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITION__THRESHOLD = eINSTANCE.getCondition_Threshold();

		/**
		 * The meta object literal for the '<em><b>Comparison Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITION__COMPARISON_OPERATOR = eINSTANCE.getCondition_ComparisonOperator();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricConditionImpl <em>Metric Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricConditionImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricCondition()
		 * @generated
		 */
		EClass METRIC_CONDITION = eINSTANCE.getMetricCondition();

		/**
		 * The meta object literal for the '<em><b>Metric</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_CONDITION__METRIC = eINSTANCE.getMetricCondition_Metric();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricTemplateConditionImpl <em>Metric Template Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricTemplateConditionImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricTemplateCondition()
		 * @generated
		 */
		EClass METRIC_TEMPLATE_CONDITION = eINSTANCE.getMetricTemplateCondition();

		/**
		 * The meta object literal for the '<em><b>Metric Template</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_TEMPLATE_CONDITION__METRIC_TEMPLATE = eINSTANCE.getMetricTemplateCondition_MetricTemplate();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.EventImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getEvent()
		 * @generated
		 */
		EClass EVENT = eINSTANCE.getEvent();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__NAME = eINSTANCE.getEvent_Name();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.EventPatternImpl <em>Event Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.EventPatternImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getEventPattern()
		 * @generated
		 */
		EClass EVENT_PATTERN = eINSTANCE.getEventPattern();

		/**
		 * The meta object literal for the '<em><b>Timer</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_PATTERN__TIMER = eINSTANCE.getEventPattern_Timer();

		/**
		 * The meta object literal for the '<em><b>Includes Event</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EVENT_PATTERN___INCLUDES_EVENT__SIMPLEEVENT = eINSTANCE.getEventPattern__IncludesEvent__SimpleEvent();

		/**
		 * The meta object literal for the '<em><b>Includes Left Event</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EVENT_PATTERN___INCLUDES_LEFT_EVENT__SIMPLEEVENT = eINSTANCE.getEventPattern__IncludesLeftEvent__SimpleEvent();

		/**
		 * The meta object literal for the '<em><b>Includes Right Event</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EVENT_PATTERN___INCLUDES_RIGHT_EVENT__SIMPLEEVENT = eINSTANCE.getEventPattern__IncludesRightEvent__SimpleEvent();

		/**
		 * The meta object literal for the '<em><b>Related To Execution Context</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EVENT_PATTERN___RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = eINSTANCE.getEventPattern__RelatedToExecutionContext__ExecutionContext();

		/**
		 * The meta object literal for the '<em><b>Left Related To Execution Context</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EVENT_PATTERN___LEFT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = eINSTANCE.getEventPattern__LeftRelatedToExecutionContext__ExecutionContext();

		/**
		 * The meta object literal for the '<em><b>Right Related To Execution Context</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EVENT_PATTERN___RIGHT_RELATED_TO_EXECUTION_CONTEXT__EXECUTIONCONTEXT = eINSTANCE.getEventPattern__RightRelatedToExecutionContext__ExecutionContext();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.BinaryEventPatternImpl <em>Binary Event Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.BinaryEventPatternImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getBinaryEventPattern()
		 * @generated
		 */
		EClass BINARY_EVENT_PATTERN = eINSTANCE.getBinaryEventPattern();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_EVENT_PATTERN__LEFT = eINSTANCE.getBinaryEventPattern_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_EVENT_PATTERN__RIGHT = eINSTANCE.getBinaryEventPattern_Right();

		/**
		 * The meta object literal for the '<em><b>Lower Occurrence Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_EVENT_PATTERN__LOWER_OCCURRENCE_BOUND = eINSTANCE.getBinaryEventPattern_LowerOccurrenceBound();

		/**
		 * The meta object literal for the '<em><b>Upper Occurrence Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_EVENT_PATTERN__UPPER_OCCURRENCE_BOUND = eINSTANCE.getBinaryEventPattern_UpperOccurrenceBound();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_EVENT_PATTERN__OPERATOR = eINSTANCE.getBinaryEventPattern_Operator();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.UnaryEventPatternImpl <em>Unary Event Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.UnaryEventPatternImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getUnaryEventPattern()
		 * @generated
		 */
		EClass UNARY_EVENT_PATTERN = eINSTANCE.getUnaryEventPattern();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNARY_EVENT_PATTERN__EVENT = eINSTANCE.getUnaryEventPattern_Event();

		/**
		 * The meta object literal for the '<em><b>Occurrence Num</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNARY_EVENT_PATTERN__OCCURRENCE_NUM = eINSTANCE.getUnaryEventPattern_OccurrenceNum();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNARY_EVENT_PATTERN__OPERATOR = eINSTANCE.getUnaryEventPattern_Operator();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.SimpleEventImpl <em>Simple Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.SimpleEventImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getSimpleEvent()
		 * @generated
		 */
		EClass SIMPLE_EVENT = eINSTANCE.getSimpleEvent();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.FunctionalEventImpl <em>Functional Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.FunctionalEventImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getFunctionalEvent()
		 * @generated
		 */
		EClass FUNCTIONAL_EVENT = eINSTANCE.getFunctionalEvent();

		/**
		 * The meta object literal for the '<em><b>Functional Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTIONAL_EVENT__FUNCTIONAL_TYPE = eINSTANCE.getFunctionalEvent_FunctionalType();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.NonFunctionalEventImpl <em>Non Functional Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.NonFunctionalEventImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getNonFunctionalEvent()
		 * @generated
		 */
		EClass NON_FUNCTIONAL_EVENT = eINSTANCE.getNonFunctionalEvent();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NON_FUNCTIONAL_EVENT__CONDITION = eINSTANCE.getNonFunctionalEvent_Condition();

		/**
		 * The meta object literal for the '<em><b>Is Violation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NON_FUNCTIONAL_EVENT__IS_VIOLATION = eINSTANCE.getNonFunctionalEvent_IsViolation();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.EventInstanceImpl <em>Event Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.EventInstanceImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getEventInstance()
		 * @generated
		 */
		EClass EVENT_INSTANCE = eINSTANCE.getEventInstance();

		/**
		 * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_INSTANCE__STATUS = eINSTANCE.getEventInstance_Status();

		/**
		 * The meta object literal for the '<em><b>Layer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT_INSTANCE__LAYER = eINSTANCE.getEventInstance_Layer();

		/**
		 * The meta object literal for the '<em><b>On Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_INSTANCE__ON_EVENT = eINSTANCE.getEventInstance_OnEvent();

		/**
		 * The meta object literal for the '<em><b>Equal Layer</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EVENT_INSTANCE___EQUAL_LAYER__LAYERTYPE_LAYERTYPE = eINSTANCE.getEventInstance__EqualLayer__LayerType_LayerType();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricImpl <em>Metric</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetric()
		 * @generated
		 */
		EClass METRIC = eINSTANCE.getMetric();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__ID = eINSTANCE.getMetric_Id();

		/**
		 * The meta object literal for the '<em><b>Has Template</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC__HAS_TEMPLATE = eINSTANCE.getMetric_HasTemplate();

		/**
		 * The meta object literal for the '<em><b>Has Schedule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC__HAS_SCHEDULE = eINSTANCE.getMetric_HasSchedule();

		/**
		 * The meta object literal for the '<em><b>Window</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC__WINDOW = eINSTANCE.getMetric_Window();

		/**
		 * The meta object literal for the '<em><b>Component Metrics</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC__COMPONENT_METRICS = eINSTANCE.getMetric_ComponentMetrics();

		/**
		 * The meta object literal for the '<em><b>Sensor</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC__SENSOR = eINSTANCE.getMetric_Sensor();

		/**
		 * The meta object literal for the '<em><b>Object Binding</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC__OBJECT_BINDING = eINSTANCE.getMetric_ObjectBinding();

		/**
		 * The meta object literal for the '<em><b>Value Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC__VALUE_TYPE = eINSTANCE.getMetric_ValueType();

		/**
		 * The meta object literal for the '<em><b>Check Recursiveness</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METRIC___CHECK_RECURSIVENESS__METRIC_METRIC = eINSTANCE.getMetric__CheckRecursiveness__Metric_Metric();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricFormulaParameterImpl <em>Metric Formula Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricFormulaParameterImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricFormulaParameter()
		 * @generated
		 */
		EClass METRIC_FORMULA_PARAMETER = eINSTANCE.getMetricFormulaParameter();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_FORMULA_PARAMETER__VALUE = eINSTANCE.getMetricFormulaParameter_Value();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricFormulaImpl <em>Metric Formula</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricFormulaImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricFormula()
		 * @generated
		 */
		EClass METRIC_FORMULA = eINSTANCE.getMetricFormula();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC_FORMULA__FUNCTION = eINSTANCE.getMetricFormula_Function();

		/**
		 * The meta object literal for the '<em><b>Function Arity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC_FORMULA__FUNCTION_ARITY = eINSTANCE.getMetricFormula_FunctionArity();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_FORMULA__PARAMETERS = eINSTANCE.getMetricFormula_Parameters();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricTemplateImpl <em>Metric Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricTemplateImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricTemplate()
		 * @generated
		 */
		EClass METRIC_TEMPLATE = eINSTANCE.getMetricTemplate();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC_TEMPLATE__NAME = eINSTANCE.getMetricTemplate_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC_TEMPLATE__DESCRIPTION = eINSTANCE.getMetricTemplate_Description();

		/**
		 * The meta object literal for the '<em><b>Value Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC_TEMPLATE__VALUE_DIRECTION = eINSTANCE.getMetricTemplate_ValueDirection();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_TEMPLATE__UNIT = eINSTANCE.getMetricTemplate_Unit();

		/**
		 * The meta object literal for the '<em><b>Layer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC_TEMPLATE__LAYER = eINSTANCE.getMetricTemplate_Layer();

		/**
		 * The meta object literal for the '<em><b>Measures</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_TEMPLATE__MEASURES = eINSTANCE.getMetricTemplate_Measures();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC_TEMPLATE__TYPE = eINSTANCE.getMetricTemplate_Type();

		/**
		 * The meta object literal for the '<em><b>Formula</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_TEMPLATE__FORMULA = eINSTANCE.getMetricTemplate_Formula();

		/**
		 * The meta object literal for the '<em><b>Object Binding</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_TEMPLATE__OBJECT_BINDING = eINSTANCE.getMetricTemplate_ObjectBinding();

		/**
		 * The meta object literal for the '<em><b>Check Recursiveness</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METRIC_TEMPLATE___CHECK_RECURSIVENESS__METRICTEMPLATE_METRICTEMPLATE = eINSTANCE.getMetricTemplate__CheckRecursiveness__MetricTemplate_MetricTemplate();

		/**
		 * The meta object literal for the '<em><b>Greater Equal Than Layer</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METRIC_TEMPLATE___GREATER_EQUAL_THAN_LAYER__LAYERTYPE_LAYERTYPE = eINSTANCE.getMetricTemplate__GreaterEqualThanLayer__LayerType_LayerType();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricObjectBindingImpl <em>Metric Object Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricObjectBindingImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricObjectBinding()
		 * @generated
		 */
		EClass METRIC_OBJECT_BINDING = eINSTANCE.getMetricObjectBinding();

		/**
		 * The meta object literal for the '<em><b>Application</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_OBJECT_BINDING__APPLICATION = eINSTANCE.getMetricObjectBinding_Application();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricApplicationBindingImpl <em>Metric Application Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricApplicationBindingImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricApplicationBinding()
		 * @generated
		 */
		EClass METRIC_APPLICATION_BINDING = eINSTANCE.getMetricApplicationBinding();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricComponentBindingImpl <em>Metric Component Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricComponentBindingImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricComponentBinding()
		 * @generated
		 */
		EClass METRIC_COMPONENT_BINDING = eINSTANCE.getMetricComponentBinding();

		/**
		 * The meta object literal for the '<em><b>Vm</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_COMPONENT_BINDING__VM = eINSTANCE.getMetricComponentBinding_Vm();

		/**
		 * The meta object literal for the '<em><b>Component</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_COMPONENT_BINDING__COMPONENT = eINSTANCE.getMetricComponentBinding_Component();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricVMBindingImpl <em>Metric VM Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricVMBindingImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricVMBinding()
		 * @generated
		 */
		EClass METRIC_VM_BINDING = eINSTANCE.getMetricVMBinding();

		/**
		 * The meta object literal for the '<em><b>Vm</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_VM_BINDING__VM = eINSTANCE.getMetricVMBinding_Vm();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricObjectInstanceBindingImpl <em>Metric Object Instance Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricObjectInstanceBindingImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricObjectInstanceBinding()
		 * @generated
		 */
		EClass METRIC_OBJECT_INSTANCE_BINDING = eINSTANCE.getMetricObjectInstanceBinding();

		/**
		 * The meta object literal for the '<em><b>Execution Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_OBJECT_INSTANCE_BINDING__EXECUTION_CONTEXT = eINSTANCE.getMetricObjectInstanceBinding_ExecutionContext();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricApplicationInstanceBindingImpl <em>Metric Application Instance Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricApplicationInstanceBindingImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricApplicationInstanceBinding()
		 * @generated
		 */
		EClass METRIC_APPLICATION_INSTANCE_BINDING = eINSTANCE.getMetricApplicationInstanceBinding();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricComponentInstanceBindingImpl <em>Metric Component Instance Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricComponentInstanceBindingImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricComponentInstanceBinding()
		 * @generated
		 */
		EClass METRIC_COMPONENT_INSTANCE_BINDING = eINSTANCE.getMetricComponentInstanceBinding();

		/**
		 * The meta object literal for the '<em><b>Vm Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_COMPONENT_INSTANCE_BINDING__VM_INSTANCE = eINSTANCE.getMetricComponentInstanceBinding_VmInstance();

		/**
		 * The meta object literal for the '<em><b>Component Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_COMPONENT_INSTANCE_BINDING__COMPONENT_INSTANCE = eINSTANCE.getMetricComponentInstanceBinding_ComponentInstance();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.MetricVMInstanceBindingImpl <em>Metric VM Instance Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.MetricVMInstanceBindingImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricVMInstanceBinding()
		 * @generated
		 */
		EClass METRIC_VM_INSTANCE_BINDING = eINSTANCE.getMetricVMInstanceBinding();

		/**
		 * The meta object literal for the '<em><b>Vm Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_VM_INSTANCE_BINDING__VM_INSTANCE = eINSTANCE.getMetricVMInstanceBinding_VmInstance();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.PropertyImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__ID = eINSTANCE.getProperty_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__NAME = eINSTANCE.getProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__DESCRIPTION = eINSTANCE.getProperty_Description();

		/**
		 * The meta object literal for the '<em><b>Realized By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__REALIZED_BY = eINSTANCE.getProperty_RealizedBy();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__TYPE = eINSTANCE.getProperty_Type();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.ScalabilityPolicyImpl <em>Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.ScalabilityPolicyImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getScalabilityPolicy()
		 * @generated
		 */
		EClass SCALABILITY_POLICY = eINSTANCE.getScalabilityPolicy();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.HorizontalScalabilityPolicyImpl <em>Horizontal Scalability Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.HorizontalScalabilityPolicyImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getHorizontalScalabilityPolicy()
		 * @generated
		 */
		EClass HORIZONTAL_SCALABILITY_POLICY = eINSTANCE.getHorizontalScalabilityPolicy();

		/**
		 * The meta object literal for the '<em><b>Min Instances</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HORIZONTAL_SCALABILITY_POLICY__MIN_INSTANCES = eINSTANCE.getHorizontalScalabilityPolicy_MinInstances();

		/**
		 * The meta object literal for the '<em><b>Max Instances</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HORIZONTAL_SCALABILITY_POLICY__MAX_INSTANCES = eINSTANCE.getHorizontalScalabilityPolicy_MaxInstances();

		/**
		 * The meta object literal for the '<em><b>Component</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HORIZONTAL_SCALABILITY_POLICY__COMPONENT = eINSTANCE.getHorizontalScalabilityPolicy_Component();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.VerticalScalabilityPolicyImpl <em>Vertical Scalability Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.VerticalScalabilityPolicyImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getVerticalScalabilityPolicy()
		 * @generated
		 */
		EClass VERTICAL_SCALABILITY_POLICY = eINSTANCE.getVerticalScalabilityPolicy();

		/**
		 * The meta object literal for the '<em><b>Min Cores</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERTICAL_SCALABILITY_POLICY__MIN_CORES = eINSTANCE.getVerticalScalabilityPolicy_MinCores();

		/**
		 * The meta object literal for the '<em><b>Max Cores</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERTICAL_SCALABILITY_POLICY__MAX_CORES = eINSTANCE.getVerticalScalabilityPolicy_MaxCores();

		/**
		 * The meta object literal for the '<em><b>Min Memory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERTICAL_SCALABILITY_POLICY__MIN_MEMORY = eINSTANCE.getVerticalScalabilityPolicy_MinMemory();

		/**
		 * The meta object literal for the '<em><b>Max Memory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERTICAL_SCALABILITY_POLICY__MAX_MEMORY = eINSTANCE.getVerticalScalabilityPolicy_MaxMemory();

		/**
		 * The meta object literal for the '<em><b>Min CPU</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERTICAL_SCALABILITY_POLICY__MIN_CPU = eINSTANCE.getVerticalScalabilityPolicy_MinCPU();

		/**
		 * The meta object literal for the '<em><b>Max CPU</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERTICAL_SCALABILITY_POLICY__MAX_CPU = eINSTANCE.getVerticalScalabilityPolicy_MaxCPU();

		/**
		 * The meta object literal for the '<em><b>Min Storage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERTICAL_SCALABILITY_POLICY__MIN_STORAGE = eINSTANCE.getVerticalScalabilityPolicy_MinStorage();

		/**
		 * The meta object literal for the '<em><b>Max Storage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERTICAL_SCALABILITY_POLICY__MAX_STORAGE = eINSTANCE.getVerticalScalabilityPolicy_MaxStorage();

		/**
		 * The meta object literal for the '<em><b>Vm</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTICAL_SCALABILITY_POLICY__VM = eINSTANCE.getVerticalScalabilityPolicy_Vm();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.ScalabilityRuleImpl <em>Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.ScalabilityRuleImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getScalabilityRule()
		 * @generated
		 */
		EClass SCALABILITY_RULE = eINSTANCE.getScalabilityRule();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALABILITY_RULE__NAME = eINSTANCE.getScalabilityRule_Name();

		/**
		 * The meta object literal for the '<em><b>Related Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_RULE__RELATED_EVENT = eINSTANCE.getScalabilityRule_RelatedEvent();

		/**
		 * The meta object literal for the '<em><b>Maps To Actions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_RULE__MAPS_TO_ACTIONS = eINSTANCE.getScalabilityRule_MapsToActions();

		/**
		 * The meta object literal for the '<em><b>Defined By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_RULE__DEFINED_BY = eINSTANCE.getScalabilityRule_DefinedBy();

		/**
		 * The meta object literal for the '<em><b>Invariant Policies</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALABILITY_RULE__INVARIANT_POLICIES = eINSTANCE.getScalabilityRule_InvariantPolicies();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.ScalingActionImpl <em>Scaling Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.ScalingActionImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getScalingAction()
		 * @generated
		 */
		EClass SCALING_ACTION = eINSTANCE.getScalingAction();

		/**
		 * The meta object literal for the '<em><b>Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALING_ACTION__COUNT = eINSTANCE.getScalingAction_Count();

		/**
		 * The meta object literal for the '<em><b>Memory Update</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALING_ACTION__MEMORY_UPDATE = eINSTANCE.getScalingAction_MemoryUpdate();

		/**
		 * The meta object literal for the '<em><b>CPU Update</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALING_ACTION__CPU_UPDATE = eINSTANCE.getScalingAction_CPUUpdate();

		/**
		 * The meta object literal for the '<em><b>Core Update</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALING_ACTION__CORE_UPDATE = eINSTANCE.getScalingAction_CoreUpdate();

		/**
		 * The meta object literal for the '<em><b>Storage Update</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALING_ACTION__STORAGE_UPDATE = eINSTANCE.getScalingAction_StorageUpdate();

		/**
		 * The meta object literal for the '<em><b>Io Update</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALING_ACTION__IO_UPDATE = eINSTANCE.getScalingAction_IoUpdate();

		/**
		 * The meta object literal for the '<em><b>Network Update</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALING_ACTION__NETWORK_UPDATE = eINSTANCE.getScalingAction_NetworkUpdate();

		/**
		 * The meta object literal for the '<em><b>Vm Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALING_ACTION__VM_INSTANCE = eINSTANCE.getScalingAction_VmInstance();

		/**
		 * The meta object literal for the '<em><b>Component Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALING_ACTION__COMPONENT_INSTANCE = eINSTANCE.getScalingAction_ComponentInstance();

		/**
		 * The meta object literal for the '<em><b>Containment Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCALING_ACTION__CONTAINMENT_INSTANCE = eINSTANCE.getScalingAction_ContainmentInstance();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.ScheduleImpl <em>Schedule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.ScheduleImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getSchedule()
		 * @generated
		 */
		EClass SCHEDULE = eINSTANCE.getSchedule();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__START = eINSTANCE.getSchedule_Start();

		/**
		 * The meta object literal for the '<em><b>End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__END = eINSTANCE.getSchedule_End();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__TYPE = eINSTANCE.getSchedule_Type();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCHEDULE__UNIT = eINSTANCE.getSchedule_Unit();

		/**
		 * The meta object literal for the '<em><b>Repetitions</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__REPETITIONS = eINSTANCE.getSchedule_Repetitions();

		/**
		 * The meta object literal for the '<em><b>Interval</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEDULE__INTERVAL = eINSTANCE.getSchedule_Interval();

		/**
		 * The meta object literal for the '<em><b>Check Start End Dates</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SCHEDULE___CHECK_START_END_DATES__SCHEDULE = eINSTANCE.getSchedule__CheckStartEndDates__Schedule();

		/**
		 * The meta object literal for the '<em><b>Check Interval Repetitions</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SCHEDULE___CHECK_INTERVAL_REPETITIONS__SCHEDULE = eINSTANCE.getSchedule__CheckIntervalRepetitions__Schedule();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.SensorImpl <em>Sensor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.SensorImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getSensor()
		 * @generated
		 */
		EClass SENSOR = eINSTANCE.getSensor();

		/**
		 * The meta object literal for the '<em><b>Configuration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SENSOR__CONFIGURATION = eINSTANCE.getSensor_Configuration();

		/**
		 * The meta object literal for the '<em><b>Is Push</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SENSOR__IS_PUSH = eINSTANCE.getSensor_IsPush();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.TimerImpl <em>Timer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.TimerImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getTimer()
		 * @generated
		 */
		EClass TIMER = eINSTANCE.getTimer();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIMER__TYPE = eINSTANCE.getTimer_Type();

		/**
		 * The meta object literal for the '<em><b>Time Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIMER__TIME_VALUE = eINSTANCE.getTimer_TimeValue();

		/**
		 * The meta object literal for the '<em><b>Max Occurrence Num</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIMER__MAX_OCCURRENCE_NUM = eINSTANCE.getTimer_MaxOccurrenceNum();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TIMER__UNIT = eINSTANCE.getTimer_Unit();

		/**
		 * The meta object literal for the '{@link camel.scalability.impl.WindowImpl <em>Window</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.impl.WindowImpl
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getWindow()
		 * @generated
		 */
		EClass WINDOW = eINSTANCE.getWindow();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WINDOW__UNIT = eINSTANCE.getWindow_Unit();

		/**
		 * The meta object literal for the '<em><b>Window Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WINDOW__WINDOW_TYPE = eINSTANCE.getWindow_WindowType();

		/**
		 * The meta object literal for the '<em><b>Size Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WINDOW__SIZE_TYPE = eINSTANCE.getWindow_SizeType();

		/**
		 * The meta object literal for the '<em><b>Measurement Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WINDOW__MEASUREMENT_SIZE = eINSTANCE.getWindow_MeasurementSize();

		/**
		 * The meta object literal for the '<em><b>Time Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WINDOW__TIME_SIZE = eINSTANCE.getWindow_TimeSize();

		/**
		 * The meta object literal for the '{@link camel.scalability.BinaryPatternOperatorType <em>Binary Pattern Operator Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.BinaryPatternOperatorType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getBinaryPatternOperatorType()
		 * @generated
		 */
		EEnum BINARY_PATTERN_OPERATOR_TYPE = eINSTANCE.getBinaryPatternOperatorType();

		/**
		 * The meta object literal for the '{@link camel.scalability.ComparisonOperatorType <em>Comparison Operator Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.ComparisonOperatorType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getComparisonOperatorType()
		 * @generated
		 */
		EEnum COMPARISON_OPERATOR_TYPE = eINSTANCE.getComparisonOperatorType();

		/**
		 * The meta object literal for the '{@link camel.scalability.LayerType <em>Layer Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.LayerType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getLayerType()
		 * @generated
		 */
		EEnum LAYER_TYPE = eINSTANCE.getLayerType();

		/**
		 * The meta object literal for the '{@link camel.scalability.MetricFunctionArityType <em>Metric Function Arity Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.MetricFunctionArityType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricFunctionArityType()
		 * @generated
		 */
		EEnum METRIC_FUNCTION_ARITY_TYPE = eINSTANCE.getMetricFunctionArityType();

		/**
		 * The meta object literal for the '{@link camel.scalability.MetricFunctionType <em>Metric Function Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.MetricFunctionType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricFunctionType()
		 * @generated
		 */
		EEnum METRIC_FUNCTION_TYPE = eINSTANCE.getMetricFunctionType();

		/**
		 * The meta object literal for the '{@link camel.scalability.MetricType <em>Metric Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.MetricType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getMetricType()
		 * @generated
		 */
		EEnum METRIC_TYPE = eINSTANCE.getMetricType();

		/**
		 * The meta object literal for the '{@link camel.scalability.PropertyType <em>Property Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.PropertyType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getPropertyType()
		 * @generated
		 */
		EEnum PROPERTY_TYPE = eINSTANCE.getPropertyType();

		/**
		 * The meta object literal for the '{@link camel.scalability.ScheduleType <em>Schedule Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.ScheduleType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getScheduleType()
		 * @generated
		 */
		EEnum SCHEDULE_TYPE = eINSTANCE.getScheduleType();

		/**
		 * The meta object literal for the '{@link camel.scalability.StatusType <em>Status Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.StatusType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getStatusType()
		 * @generated
		 */
		EEnum STATUS_TYPE = eINSTANCE.getStatusType();

		/**
		 * The meta object literal for the '{@link camel.scalability.TimerType <em>Timer Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.TimerType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getTimerType()
		 * @generated
		 */
		EEnum TIMER_TYPE = eINSTANCE.getTimerType();

		/**
		 * The meta object literal for the '{@link camel.scalability.UnaryPatternOperatorType <em>Unary Pattern Operator Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.UnaryPatternOperatorType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getUnaryPatternOperatorType()
		 * @generated
		 */
		EEnum UNARY_PATTERN_OPERATOR_TYPE = eINSTANCE.getUnaryPatternOperatorType();

		/**
		 * The meta object literal for the '{@link camel.scalability.WindowSizeType <em>Window Size Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.WindowSizeType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getWindowSizeType()
		 * @generated
		 */
		EEnum WINDOW_SIZE_TYPE = eINSTANCE.getWindowSizeType();

		/**
		 * The meta object literal for the '{@link camel.scalability.WindowType <em>Window Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.scalability.WindowType
		 * @see camel.scalability.impl.ScalabilityPackageImpl#getWindowType()
		 * @generated
		 */
		EEnum WINDOW_TYPE = eINSTANCE.getWindowType();

	}

} //ScalabilityPackage
