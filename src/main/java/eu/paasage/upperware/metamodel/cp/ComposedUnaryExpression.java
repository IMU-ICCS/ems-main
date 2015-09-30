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
 * A representation of the model object '<em><b>Composed Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getOperator <em>Operator</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComposedUnaryExpression()
 *  abstract="true"
 * 
 */
public interface ComposedUnaryExpression extends UnaryExpression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum
	 * @see #setOperator(ComposedUnaryOperatorEnum)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComposedUnaryExpression_Operator()
	 *  required="true"
	 * 
	 */
	ComposedUnaryOperatorEnum getOperator();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum
	 * @see #getOperator()
	 * 
	 */
	void setOperator(ComposedUnaryOperatorEnum value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(int)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComposedUnaryExpression_Value()
	 *  required="true"
	 * 
	 */
	int getValue();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * 
	 */
	void setValue(int value);

} // ComposedUnaryExpression
