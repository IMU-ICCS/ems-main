/**
 */
package camel.scalability.impl;

import camel.scalability.ComparisonOperatorType;
import camel.scalability.Condition;
import camel.scalability.ScalabilityPackage;

import java.util.Date;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Condition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.ConditionImpl#getValidity <em>Validity</em>}</li>
 *   <li>{@link camel.scalability.impl.ConditionImpl#getThreshold <em>Threshold</em>}</li>
 *   <li>{@link camel.scalability.impl.ConditionImpl#getComparisonOperator <em>Comparison Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ConditionImpl extends CDOObjectImpl implements Condition {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.CONDITION;
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
	public Date getValidity() {
		return (Date)eGet(ScalabilityPackage.Literals.CONDITION__VALIDITY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValidity(Date newValidity) {
		eSet(ScalabilityPackage.Literals.CONDITION__VALIDITY, newValidity);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getThreshold() {
		return (Double)eGet(ScalabilityPackage.Literals.CONDITION__THRESHOLD, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThreshold(double newThreshold) {
		eSet(ScalabilityPackage.Literals.CONDITION__THRESHOLD, newThreshold);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComparisonOperatorType getComparisonOperator() {
		return (ComparisonOperatorType)eGet(ScalabilityPackage.Literals.CONDITION__COMPARISON_OPERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComparisonOperator(ComparisonOperatorType newComparisonOperator) {
		eSet(ScalabilityPackage.Literals.CONDITION__COMPARISON_OPERATOR, newComparisonOperator);
	}

} //ConditionImpl
