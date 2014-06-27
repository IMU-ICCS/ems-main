/**
 */
package cp.impl;

import cp.CpPackage;
import cp.Goal;
import cp.GoalOperatorEnum;
import cp.NumericExpression;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Goal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link cp.impl.GoalImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link cp.impl.GoalImpl#getGoalType <em>Goal Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GoalImpl extends CPElementImpl implements Goal {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GoalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.GOAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericExpression getExpression() {
		return (NumericExpression)eGet(CpPackage.Literals.GOAL__EXPRESSION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpression(NumericExpression newExpression) {
		eSet(CpPackage.Literals.GOAL__EXPRESSION, newExpression);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GoalOperatorEnum getGoalType() {
		return (GoalOperatorEnum)eGet(CpPackage.Literals.GOAL__GOAL_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGoalType(GoalOperatorEnum newGoalType) {
		eSet(CpPackage.Literals.GOAL__GOAL_TYPE, newGoalType);
	}

} //GoalImpl
