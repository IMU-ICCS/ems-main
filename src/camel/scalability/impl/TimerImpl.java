/**
 */
package camel.scalability.impl;

import camel.TimeIntervalUnit;

import camel.scalability.ScalabilityPackage;
import camel.scalability.Timer;
import camel.scalability.TimerType;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Timer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.TimerImpl#getType <em>Type</em>}</li>
 *   <li>{@link camel.scalability.impl.TimerImpl#getTimeValue <em>Time Value</em>}</li>
 *   <li>{@link camel.scalability.impl.TimerImpl#getMaxOccurrenceNum <em>Max Occurrence Num</em>}</li>
 *   <li>{@link camel.scalability.impl.TimerImpl#getUnit <em>Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TimerImpl extends CDOObjectImpl implements Timer {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TimerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.TIMER;
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
	public TimerType getType() {
		return (TimerType)eGet(ScalabilityPackage.Literals.TIMER__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(TimerType newType) {
		eSet(ScalabilityPackage.Literals.TIMER__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTimeValue() {
		return (Integer)eGet(ScalabilityPackage.Literals.TIMER__TIME_VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeValue(int newTimeValue) {
		eSet(ScalabilityPackage.Literals.TIMER__TIME_VALUE, newTimeValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxOccurrenceNum() {
		return (Integer)eGet(ScalabilityPackage.Literals.TIMER__MAX_OCCURRENCE_NUM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxOccurrenceNum(int newMaxOccurrenceNum) {
		eSet(ScalabilityPackage.Literals.TIMER__MAX_OCCURRENCE_NUM, newMaxOccurrenceNum);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeIntervalUnit getUnit() {
		return (TimeIntervalUnit)eGet(ScalabilityPackage.Literals.TIMER__UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnit(TimeIntervalUnit newUnit) {
		eSet(ScalabilityPackage.Literals.TIMER__UNIT, newUnit);
	}

} //TimerImpl
