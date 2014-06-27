/**
 */
package camel.sla.impl;

import camel.TimeIntervalUnit;

import camel.sla.AssessmentIntervalType;
import camel.sla.SlaPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Assessment Interval Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.AssessmentIntervalTypeImpl#getTimeInterval <em>Time Interval</em>}</li>
 *   <li>{@link camel.sla.impl.AssessmentIntervalTypeImpl#getCount <em>Count</em>}</li>
 *   <li>{@link camel.sla.impl.AssessmentIntervalTypeImpl#getTimeUnit <em>Time Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AssessmentIntervalTypeImpl extends CDOObjectImpl implements AssessmentIntervalType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssessmentIntervalTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.ASSESSMENT_INTERVAL_TYPE;
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
	public long getTimeInterval() {
		return (Long)eGet(SlaPackage.Literals.ASSESSMENT_INTERVAL_TYPE__TIME_INTERVAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeInterval(long newTimeInterval) {
		eSet(SlaPackage.Literals.ASSESSMENT_INTERVAL_TYPE__TIME_INTERVAL, newTimeInterval);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCount() {
		return (Integer)eGet(SlaPackage.Literals.ASSESSMENT_INTERVAL_TYPE__COUNT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCount(int newCount) {
		eSet(SlaPackage.Literals.ASSESSMENT_INTERVAL_TYPE__COUNT, newCount);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeIntervalUnit getTimeUnit() {
		return (TimeIntervalUnit)eGet(SlaPackage.Literals.ASSESSMENT_INTERVAL_TYPE__TIME_UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeUnit(TimeIntervalUnit newTimeUnit) {
		eSet(SlaPackage.Literals.ASSESSMENT_INTERVAL_TYPE__TIME_UNIT, newTimeUnit);
	}

} //AssessmentIntervalTypeImpl
