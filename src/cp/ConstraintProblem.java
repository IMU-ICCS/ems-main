/**
 */
package cp;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint Problem</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link cp.ConstraintProblem#getGoals <em>Goals</em>}</li>
 *   <li>{@link cp.ConstraintProblem#getConstants <em>Constants</em>}</li>
 *   <li>{@link cp.ConstraintProblem#getVariables <em>Variables</em>}</li>
 *   <li>{@link cp.ConstraintProblem#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link cp.ConstraintProblem#getAuxExpressions <em>Aux Expressions</em>}</li>
 * </ul>
 * </p>
 *
 * @see cp.CpPackage#getConstraintProblem()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ConstraintProblem extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Goals</b></em>' containment reference list.
	 * The list contents are of type {@link cp.Goal}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goals</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goals</em>' containment reference list.
	 * @see cp.CpPackage#getConstraintProblem_Goals()
	 * @model containment="true"
	 * @generated
	 */
	EList<Goal> getGoals();

	/**
	 * Returns the value of the '<em><b>Constants</b></em>' containment reference list.
	 * The list contents are of type {@link cp.Constant}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constants</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constants</em>' containment reference list.
	 * @see cp.CpPackage#getConstraintProblem_Constants()
	 * @model containment="true"
	 * @generated
	 */
	EList<Constant> getConstants();

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link cp.Variable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see cp.CpPackage#getConstraintProblem_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getVariables();

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link cp.ComparisonExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' containment reference list.
	 * @see cp.CpPackage#getConstraintProblem_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<ComparisonExpression> getConstraints();

	/**
	 * Returns the value of the '<em><b>Aux Expressions</b></em>' containment reference list.
	 * The list contents are of type {@link cp.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aux Expressions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aux Expressions</em>' containment reference list.
	 * @see cp.CpPackage#getConstraintProblem_AuxExpressions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getAuxExpressions();

} // ConstraintProblem
