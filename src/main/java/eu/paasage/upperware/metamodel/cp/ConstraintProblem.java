/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
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
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getGoals <em>Goals</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstants <em>Constants</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getVariables <em>Variables</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getAuxExpressions <em>Aux Expressions</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getMetricVariables <em>Metric Variables</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getSolution <em>Solution</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConstraintProblem()
 * 
 * @extends CDOObject
 * 
 */
public interface ConstraintProblem extends CDOObject {
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
	 *  containment="true"
	 * 
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
	 *  containment="true"
	 * 
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
	 *  containment="true"
	 * 
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
	 *  containment="true"
	 * 
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
	 *  containment="true"
	 * 
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
	 *  containment="true"
	 * 
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
	 *  containment="true"
	 * 
	 */
	EList<Solution> getSolution();

} // ConstraintProblem
