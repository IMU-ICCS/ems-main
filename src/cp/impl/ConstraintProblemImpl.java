/**
 */
package cp.impl;

import cp.ComparisonExpression;
import cp.Constant;
import cp.ConstraintProblem;
import cp.CpPackage;
import cp.Expression;
import cp.Goal;
import cp.Variable;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constraint Problem</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link cp.impl.ConstraintProblemImpl#getGoals <em>Goals</em>}</li>
 *   <li>{@link cp.impl.ConstraintProblemImpl#getConstants <em>Constants</em>}</li>
 *   <li>{@link cp.impl.ConstraintProblemImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link cp.impl.ConstraintProblemImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link cp.impl.ConstraintProblemImpl#getAuxExpressions <em>Aux Expressions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConstraintProblemImpl extends CDOObjectImpl implements ConstraintProblem {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstraintProblemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.CONSTRAINT_PROBLEM;
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
	@SuppressWarnings("unchecked")
	public EList<Goal> getGoals() {
		return (EList<Goal>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__GOALS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Constant> getConstants() {
		return (EList<Constant>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__CONSTANTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Variable> getVariables() {
		return (EList<Variable>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__VARIABLES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ComparisonExpression> getConstraints() {
		return (EList<ComparisonExpression>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__CONSTRAINTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Expression> getAuxExpressions() {
		return (EList<Expression>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__AUX_EXPRESSIONS, true);
	}

} //ConstraintProblemImpl
