/**
 */
package camel.scalability;

import camel.Unit;

import camel.type.ValueType;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.ScalabilityModel#getMetricTemplates <em>Metric Templates</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getMetrics <em>Metrics</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getRules <em>Rules</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getEvents <em>Events</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getEventInstances <em>Event Instances</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getConditions <em>Conditions</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getProperties <em>Properties</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getActions <em>Actions</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getFormulas <em>Formulas</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getBindings <em>Bindings</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getBindingInstances <em>Binding Instances</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getWindows <em>Windows</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getSchedules <em>Schedules</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getParameters <em>Parameters</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getPatterns <em>Patterns</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getTimers <em>Timers</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getSensors <em>Sensors</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getUnits <em>Units</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getValueTypes <em>Value Types</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityModel#getPolicies <em>Policies</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getScalabilityModel()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ScalabilityModel extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Metric Templates</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.MetricTemplate}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metric Templates</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metric Templates</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_MetricTemplates()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<MetricTemplate> getMetricTemplates();

	/**
	 * Returns the value of the '<em><b>Metrics</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.Metric}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metrics</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metrics</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Metrics()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Metric> getMetrics();

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.ScalabilityRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Rules()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ScalabilityRule> getRules();

	/**
	 * Returns the value of the '<em><b>Events</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.Event}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Events</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Events</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Events()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Event> getEvents();

	/**
	 * Returns the value of the '<em><b>Event Instances</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.EventInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Instances</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_EventInstances()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<EventInstance> getEventInstances();

	/**
	 * Returns the value of the '<em><b>Conditions</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.Condition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conditions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conditions</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Conditions()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Condition> getConditions();

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Properties()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Property> getProperties();

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.ScalingAction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Actions()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ScalingAction> getActions();

	/**
	 * Returns the value of the '<em><b>Formulas</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.MetricFormula}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Formulas</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formulas</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Formulas()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<MetricFormula> getFormulas();

	/**
	 * Returns the value of the '<em><b>Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.MetricObjectBinding}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bindings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bindings</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Bindings()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<MetricObjectBinding> getBindings();

	/**
	 * Returns the value of the '<em><b>Binding Instances</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.MetricObjectInstanceBinding}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Binding Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binding Instances</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_BindingInstances()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<MetricObjectInstanceBinding> getBindingInstances();

	/**
	 * Returns the value of the '<em><b>Windows</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.Window}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Windows</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Windows</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Windows()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Window> getWindows();

	/**
	 * Returns the value of the '<em><b>Schedules</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.Schedule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schedules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schedules</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Schedules()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Schedule> getSchedules();

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.MetricFormulaParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Parameters()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<MetricFormulaParameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Patterns</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.EventPattern}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Patterns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Patterns</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Patterns()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<EventPattern> getPatterns();

	/**
	 * Returns the value of the '<em><b>Timers</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.Timer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timers</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Timers()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Timer> getTimers();

	/**
	 * Returns the value of the '<em><b>Sensors</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.Sensor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sensors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sensors</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Sensors()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Sensor> getSensors();

	/**
	 * Returns the value of the '<em><b>Units</b></em>' containment reference list.
	 * The list contents are of type {@link camel.Unit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Units</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Units</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Units()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Unit> getUnits();

	/**
	 * Returns the value of the '<em><b>Value Types</b></em>' containment reference list.
	 * The list contents are of type {@link camel.type.ValueType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Types</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_ValueTypes()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ValueType> getValueTypes();

	/**
	 * Returns the value of the '<em><b>Policies</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.ScalabilityPolicy}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Policies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Policies</em>' containment reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityModel_Policies()
	 * @model containment="true"
	 * @generated
	 */
	EList<ScalabilityPolicy> getPolicies();

} // ScalabilityModel
