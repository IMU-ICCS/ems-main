/**
 */
package camel.scalability.impl;

import camel.scalability.Metric;
import camel.scalability.MetricObjectInstanceBinding;
import camel.scalability.MetricTemplate;
import camel.scalability.ScalabilityPackage;
import camel.scalability.Schedule;
import camel.scalability.Sensor;
import camel.scalability.Window;

import camel.type.ValueType;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.MetricImpl#getId <em>Id</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricImpl#getHasTemplate <em>Has Template</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricImpl#getHasSchedule <em>Has Schedule</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricImpl#getWindow <em>Window</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricImpl#getComponentMetrics <em>Component Metrics</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricImpl#getSensor <em>Sensor</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricImpl#getObjectBinding <em>Object Binding</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricImpl#getValueType <em>Value Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetricImpl extends CDOObjectImpl implements Metric {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.METRIC;
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
	public String getId() {
		return (String)eGet(ScalabilityPackage.Literals.METRIC__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eSet(ScalabilityPackage.Literals.METRIC__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricTemplate getHasTemplate() {
		return (MetricTemplate)eGet(ScalabilityPackage.Literals.METRIC__HAS_TEMPLATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasTemplate(MetricTemplate newHasTemplate) {
		eSet(ScalabilityPackage.Literals.METRIC__HAS_TEMPLATE, newHasTemplate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Schedule getHasSchedule() {
		return (Schedule)eGet(ScalabilityPackage.Literals.METRIC__HAS_SCHEDULE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasSchedule(Schedule newHasSchedule) {
		eSet(ScalabilityPackage.Literals.METRIC__HAS_SCHEDULE, newHasSchedule);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Window getWindow() {
		return (Window)eGet(ScalabilityPackage.Literals.METRIC__WINDOW, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWindow(Window newWindow) {
		eSet(ScalabilityPackage.Literals.METRIC__WINDOW, newWindow);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Metric> getComponentMetrics() {
		return (EList<Metric>)eGet(ScalabilityPackage.Literals.METRIC__COMPONENT_METRICS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sensor getSensor() {
		return (Sensor)eGet(ScalabilityPackage.Literals.METRIC__SENSOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSensor(Sensor newSensor) {
		eSet(ScalabilityPackage.Literals.METRIC__SENSOR, newSensor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricObjectInstanceBinding getObjectBinding() {
		return (MetricObjectInstanceBinding)eGet(ScalabilityPackage.Literals.METRIC__OBJECT_BINDING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectBinding(MetricObjectInstanceBinding newObjectBinding) {
		eSet(ScalabilityPackage.Literals.METRIC__OBJECT_BINDING, newObjectBinding);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueType getValueType() {
		return (ValueType)eGet(ScalabilityPackage.Literals.METRIC__VALUE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueType(ValueType newValueType) {
		eSet(ScalabilityPackage.Literals.METRIC__VALUE_TYPE, newValueType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkRecursiveness(final Metric m1, final Metric m2) {
		System.out.println("Checking recursiveness for Metric: " + m1.getId());
				for (Metric m: m1.getComponentMetrics()){
						if (m.getId().equals(m2.getId())) return Boolean.TRUE;
						if (m.getHasTemplate().getType() == camel.scalability.MetricType.COMPOSITE && checkRecursiveness(m,m2)) return Boolean.TRUE;
				}
				return Boolean.FALSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ScalabilityPackage.METRIC___CHECK_RECURSIVENESS__METRIC_METRIC:
				return checkRecursiveness((Metric)arguments.get(0), (Metric)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

} //MetricImpl
