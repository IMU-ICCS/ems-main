/**
 */
package application.impl;

import application.ApplicationPackage;
import application.PaaSageGoal;

import cp.GoalOperatorEnum;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import types.typesPaasage.FunctionType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Paa Sage Goal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.PaaSageGoalImpl#getId <em>Id</em>}</li>
 *   <li>{@link application.impl.PaaSageGoalImpl#getGoal <em>Goal</em>}</li>
 *   <li>{@link application.impl.PaaSageGoalImpl#getFunction <em>Function</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PaaSageGoalImpl extends CDOObjectImpl implements PaaSageGoal {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PaaSageGoalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.PAA_SAGE_GOAL;
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
		return (String)eGet(ApplicationPackage.Literals.PAA_SAGE_GOAL__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_GOAL__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GoalOperatorEnum getGoal() {
		return (GoalOperatorEnum)eGet(ApplicationPackage.Literals.PAA_SAGE_GOAL__GOAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGoal(GoalOperatorEnum newGoal) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_GOAL__GOAL, newGoal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionType getFunction() {
		return (FunctionType)eGet(ApplicationPackage.Literals.PAA_SAGE_GOAL__FUNCTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunction(FunctionType newFunction) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_GOAL__FUNCTION, newFunction);
	}

} //PaaSageGoalImpl
