/**
 */
package camel.execution.impl;

import camel.execution.ExecutionContext;
import camel.execution.ExecutionPackage;
import camel.execution.Measurement;

import camel.scalability.EventInstance;
import camel.scalability.Metric;

import camel.sla.ServiceLevelObjectiveType;

import java.lang.reflect.InvocationTargetException;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Measurement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.execution.impl.MeasurementImpl#getID <em>ID</em>}</li>
 *   <li>{@link camel.execution.impl.MeasurementImpl#getInExecutionContext <em>In Execution Context</em>}</li>
 *   <li>{@link camel.execution.impl.MeasurementImpl#getOfMetric <em>Of Metric</em>}</li>
 *   <li>{@link camel.execution.impl.MeasurementImpl#getValue <em>Value</em>}</li>
 *   <li>{@link camel.execution.impl.MeasurementImpl#getRawData <em>Raw Data</em>}</li>
 *   <li>{@link camel.execution.impl.MeasurementImpl#getReportedOn <em>Reported On</em>}</li>
 *   <li>{@link camel.execution.impl.MeasurementImpl#getItSLO <em>It SLO</em>}</li>
 *   <li>{@link camel.execution.impl.MeasurementImpl#getTriggers <em>Triggers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MeasurementImpl extends CDOObjectImpl implements Measurement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MeasurementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionPackage.Literals.MEASUREMENT;
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
	public String getID() {
		return (String)eGet(ExecutionPackage.Literals.MEASUREMENT__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setID(String newID) {
		eSet(ExecutionPackage.Literals.MEASUREMENT__ID, newID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionContext getInExecutionContext() {
		return (ExecutionContext)eGet(ExecutionPackage.Literals.MEASUREMENT__IN_EXECUTION_CONTEXT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInExecutionContext(ExecutionContext newInExecutionContext) {
		eSet(ExecutionPackage.Literals.MEASUREMENT__IN_EXECUTION_CONTEXT, newInExecutionContext);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Metric getOfMetric() {
		return (Metric)eGet(ExecutionPackage.Literals.MEASUREMENT__OF_METRIC, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfMetric(Metric newOfMetric) {
		eSet(ExecutionPackage.Literals.MEASUREMENT__OF_METRIC, newOfMetric);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getValue() {
		return (Double)eGet(ExecutionPackage.Literals.MEASUREMENT__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(double newValue) {
		eSet(ExecutionPackage.Literals.MEASUREMENT__VALUE, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRawData() {
		return (String)eGet(ExecutionPackage.Literals.MEASUREMENT__RAW_DATA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRawData(String newRawData) {
		eSet(ExecutionPackage.Literals.MEASUREMENT__RAW_DATA, newRawData);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getReportedOn() {
		return (Date)eGet(ExecutionPackage.Literals.MEASUREMENT__REPORTED_ON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReportedOn(Date newReportedOn) {
		eSet(ExecutionPackage.Literals.MEASUREMENT__REPORTED_ON, newReportedOn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceLevelObjectiveType getItSLO() {
		return (ServiceLevelObjectiveType)eGet(ExecutionPackage.Literals.MEASUREMENT__IT_SLO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setItSLO(ServiceLevelObjectiveType newItSLO) {
		eSet(ExecutionPackage.Literals.MEASUREMENT__IT_SLO, newItSLO);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventInstance getTriggers() {
		return (EventInstance)eGet(ExecutionPackage.Literals.MEASUREMENT__TRIGGERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTriggers(EventInstance newTriggers) {
		eSet(ExecutionPackage.Literals.MEASUREMENT__TRIGGERS, newTriggers);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDate(final Measurement m) {
		Date reportedOn = m.getReportedOn();
				ExecutionContext ec = m.getInExecutionContext();
				Date start = ec.getStartTime();
				Date end = ec.getEndTime();
				if (reportedOn.before(start) || (end != null && end.before(reportedOn))) return Boolean.FALSE;
				return Boolean.TRUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ExecutionPackage.MEASUREMENT___CHECK_DATE__MEASUREMENT:
				return checkDate((Measurement)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //MeasurementImpl
