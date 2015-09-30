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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composed Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ComposedExpression#getExpressions <em>Expressions</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ComposedExpression#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComposedExpression()
 * 
 * 
 */
public interface ComposedExpression extends NumericExpression {
	/**
	 * Returns the value of the '<em><b>Expressions</b></em>' reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.NumericExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expressions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expressions</em>' reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComposedExpression_Expressions()
	 *  lower="2"
	 * 
	 */
	EList<NumericExpression> getExpressions();

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.cp.OperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.OperatorEnum
	 * @see #setOperator(OperatorEnum)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComposedExpression_Operator()
	 * 
	 * 
	 */
	OperatorEnum getOperator();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ComposedExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.OperatorEnum
	 * @see #getOperator()
	 * 
	 */
	void setOperator(OperatorEnum value);

} // ComposedExpression
