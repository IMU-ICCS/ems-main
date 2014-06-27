/**
 */
package application.impl;

import application.ApplicationPackage;
import application.Condition;

import cp.BooleanExpression;

import cp.impl.BooleanExpressionImpl;

import org.eclipse.emf.ecore.EClass;

import types.typesPaasage.LogicOperatorEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Condition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.ConditionImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link application.impl.ConditionImpl#getExp1 <em>Exp1</em>}</li>
 *   <li>{@link application.impl.ConditionImpl#getExp2 <em>Exp2</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConditionImpl extends BooleanExpressionImpl implements Condition {
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
		return ApplicationPackage.Literals.CONDITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogicOperatorEnum getOperator() {
		return (LogicOperatorEnum)eGet(ApplicationPackage.Literals.CONDITION__OPERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(LogicOperatorEnum newOperator) {
		eSet(ApplicationPackage.Literals.CONDITION__OPERATOR, newOperator);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanExpression getExp1() {
		return (BooleanExpression)eGet(ApplicationPackage.Literals.CONDITION__EXP1, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExp1(BooleanExpression newExp1) {
		eSet(ApplicationPackage.Literals.CONDITION__EXP1, newExp1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanExpression getExp2() {
		return (BooleanExpression)eGet(ApplicationPackage.Literals.CONDITION__EXP2, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExp2(BooleanExpression newExp2) {
		eSet(ApplicationPackage.Literals.CONDITION__EXP2, newExp2);
	}

} //ConditionImpl
