/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Goal;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.cp.NumericExpression;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Goal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.GoalImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.GoalImpl#getGoalType <em>Goal Type</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.GoalImpl#getPriority <em>Priority</em>}</li>
 * </ul>
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getPriority() {
		return (Double)eGet(CpPackage.Literals.GOAL__PRIORITY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(double newPriority) {
		eSet(CpPackage.Literals.GOAL__PRIORITY, newPriority);
	}

} //GoalImpl
