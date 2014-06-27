/**
 */
package camel.sla.impl;

import camel.MonetaryUnit;

import camel.sla.AssessmentIntervalType;
import camel.sla.CompensationType;
import camel.sla.SlaPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compensation Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.CompensationTypeImpl#getAssessmentInterval <em>Assessment Interval</em>}</li>
 *   <li>{@link camel.sla.impl.CompensationTypeImpl#getValueUnit <em>Value Unit</em>}</li>
 *   <li>{@link camel.sla.impl.CompensationTypeImpl#getValueExpression <em>Value Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompensationTypeImpl extends CDOObjectImpl implements CompensationType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompensationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.COMPENSATION_TYPE;
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
	public AssessmentIntervalType getAssessmentInterval() {
		return (AssessmentIntervalType)eGet(SlaPackage.Literals.COMPENSATION_TYPE__ASSESSMENT_INTERVAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssessmentInterval(AssessmentIntervalType newAssessmentInterval) {
		eSet(SlaPackage.Literals.COMPENSATION_TYPE__ASSESSMENT_INTERVAL, newAssessmentInterval);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MonetaryUnit getValueUnit() {
		return (MonetaryUnit)eGet(SlaPackage.Literals.COMPENSATION_TYPE__VALUE_UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueUnit(MonetaryUnit newValueUnit) {
		eSet(SlaPackage.Literals.COMPENSATION_TYPE__VALUE_UNIT, newValueUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValueExpression() {
		return (String)eGet(SlaPackage.Literals.COMPENSATION_TYPE__VALUE_EXPRESSION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueExpression(String newValueExpression) {
		eSet(SlaPackage.Literals.COMPENSATION_TYPE__VALUE_EXPRESSION, newValueExpression);
	}

} //CompensationTypeImpl
