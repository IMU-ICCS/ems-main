/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.ConditionUpperware;

import eu.paasage.upperware.metamodel.cp.BooleanExpression;

import eu.paasage.upperware.metamodel.cp.impl.BooleanExpressionImpl;

import eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Condition Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ConditionUpperwareImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ConditionUpperwareImpl#getExp1 <em>Exp1</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ConditionUpperwareImpl#getExp2 <em>Exp2</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionUpperwareImpl extends BooleanExpressionImpl implements ConditionUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.CONDITION_UPPERWARE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogicOperatorEnum getOperator() {
		return (LogicOperatorEnum)eGet(ApplicationPackage.Literals.CONDITION_UPPERWARE__OPERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(LogicOperatorEnum newOperator) {
		eSet(ApplicationPackage.Literals.CONDITION_UPPERWARE__OPERATOR, newOperator);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanExpression getExp1() {
		return (BooleanExpression)eGet(ApplicationPackage.Literals.CONDITION_UPPERWARE__EXP1, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExp1(BooleanExpression newExp1) {
		eSet(ApplicationPackage.Literals.CONDITION_UPPERWARE__EXP1, newExp1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanExpression getExp2() {
		return (BooleanExpression)eGet(ApplicationPackage.Literals.CONDITION_UPPERWARE__EXP2, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExp2(BooleanExpression newExp2) {
		eSet(ApplicationPackage.Literals.CONDITION_UPPERWARE__EXP2, newExp2);
	}

} //ConditionUpperwareImpl
