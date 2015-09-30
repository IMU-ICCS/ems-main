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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Goal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Goal#getExpression <em>Expression</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Goal#getGoalType <em>Goal Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getGoal()
 * 
 * 
 */
public interface Goal extends CPElement {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(NumericExpression)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getGoal_Expression()
	 *  containment="true" required="true"
	 * 
	 */
	NumericExpression getExpression();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Goal#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * 
	 */
	void setExpression(NumericExpression value);

	/**
	 * Returns the value of the '<em><b>Goal Type</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.cp.GoalOperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goal Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goal Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
	 * @see #setGoalType(GoalOperatorEnum)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getGoal_GoalType()
	 *  required="true"
	 * 
	 */
	GoalOperatorEnum getGoalType();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Goal#getGoalType <em>Goal Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Goal Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
	 * @see #getGoalType()
	 * 
	 */
	void setGoalType(GoalOperatorEnum value);

} // Goal
