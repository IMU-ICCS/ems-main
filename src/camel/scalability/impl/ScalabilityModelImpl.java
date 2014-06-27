/**
 */
package camel.scalability.impl;

import camel.Unit;

import camel.scalability.Condition;
import camel.scalability.Event;
import camel.scalability.EventInstance;
import camel.scalability.EventPattern;
import camel.scalability.Metric;
import camel.scalability.MetricFormula;
import camel.scalability.MetricFormulaParameter;
import camel.scalability.MetricObjectBinding;
import camel.scalability.MetricObjectInstanceBinding;
import camel.scalability.MetricTemplate;
import camel.scalability.Property;
import camel.scalability.ScalabilityModel;
import camel.scalability.ScalabilityPackage;
import camel.scalability.ScalabilityPolicy;
import camel.scalability.ScalabilityRule;
import camel.scalability.ScalingAction;
import camel.scalability.Schedule;
import camel.scalability.Sensor;
import camel.scalability.Timer;
import camel.scalability.Window;

import camel.type.ValueType;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getMetricTemplates <em>Metric Templates</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getMetrics <em>Metrics</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getRules <em>Rules</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getEvents <em>Events</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getEventInstances <em>Event Instances</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getConditions <em>Conditions</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getFormulas <em>Formulas</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getBindings <em>Bindings</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getBindingInstances <em>Binding Instances</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getWindows <em>Windows</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getSchedules <em>Schedules</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getPatterns <em>Patterns</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getTimers <em>Timers</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getSensors <em>Sensors</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getUnits <em>Units</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getValueTypes <em>Value Types</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalabilityModelImpl#getPolicies <em>Policies</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScalabilityModelImpl extends CDOObjectImpl implements ScalabilityModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScalabilityModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.SCALABILITY_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<MetricTemplate> getMetricTemplates() {
		return (EList<MetricTemplate>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__METRIC_TEMPLATES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Metric> getMetrics() {
		return (EList<Metric>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__METRICS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ScalabilityRule> getRules() {
		return (EList<ScalabilityRule>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__RULES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Event> getEvents() {
		return (EList<Event>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__EVENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EventInstance> getEventInstances() {
		return (EList<EventInstance>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__EVENT_INSTANCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Condition> getConditions() {
		return (EList<Condition>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__CONDITIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Property> getProperties() {
		return (EList<Property>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__PROPERTIES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ScalingAction> getActions() {
		return (EList<ScalingAction>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__ACTIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<MetricFormula> getFormulas() {
		return (EList<MetricFormula>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__FORMULAS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<MetricObjectBinding> getBindings() {
		return (EList<MetricObjectBinding>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__BINDINGS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<MetricObjectInstanceBinding> getBindingInstances() {
		return (EList<MetricObjectInstanceBinding>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__BINDING_INSTANCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Window> getWindows() {
		return (EList<Window>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__WINDOWS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Schedule> getSchedules() {
		return (EList<Schedule>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__SCHEDULES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<MetricFormulaParameter> getParameters() {
		return (EList<MetricFormulaParameter>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__PARAMETERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EventPattern> getPatterns() {
		return (EList<EventPattern>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__PATTERNS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Timer> getTimers() {
		return (EList<Timer>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__TIMERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Sensor> getSensors() {
		return (EList<Sensor>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__SENSORS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Unit> getUnits() {
		return (EList<Unit>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__UNITS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ValueType> getValueTypes() {
		return (EList<ValueType>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__VALUE_TYPES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ScalabilityPolicy> getPolicies() {
		return (EList<ScalabilityPolicy>)eGet(ScalabilityPackage.Literals.SCALABILITY_MODEL__POLICIES, true);
	}

} //ScalabilityModelImpl
