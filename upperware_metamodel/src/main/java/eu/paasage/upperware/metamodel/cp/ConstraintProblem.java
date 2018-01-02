/**
 */
package eu.paasage.upperware.metamodel.cp;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint Problem</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getGoals <em>Goals</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstants <em>Constants</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getVariables <em>Variables</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getAuxExpressions <em>Aux Expressions</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getMetricVariables <em>Metric Variables</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getSolution <em>Solution</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConstraintProblem()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ConstraintProblem extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConstraintProblem_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Goals</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.Goal}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goals</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goals</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConstraintProblem_Goals()
	 * @model containment="true"
	 * @generated
	 */
	EList<Goal> getGoals();

	/**
	 * Returns the value of the '<em><b>Constants</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.Constant}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constants</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constants</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConstraintProblem_Constants()
	 * @model containment="true"
	 * @generated
	 */
	EList<Constant> getConstants();

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.Variable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConstraintProblem_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getVariables();

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.ComparisonExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConstraintProblem_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<ComparisonExpression> getConstraints();

	/**
	 * Returns the value of the '<em><b>Aux Expressions</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aux Expressions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aux Expressions</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConstraintProblem_AuxExpressions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getAuxExpressions();

	/**
	 * Returns the value of the '<em><b>Metric Variables</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.MetricVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metric Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metric Variables</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConstraintProblem_MetricVariables()
	 * @model containment="true"
	 * @generated
	 */
	EList<MetricVariable> getMetricVariables();

	/**
	 * Returns the value of the '<em><b>Solution</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.Solution}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Solution</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solution</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConstraintProblem_Solution()
	 * @model containment="true"
	 * @generated
	 */
	EList<Solution> getSolution();

} // ConstraintProblem
