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
 * A representation of the model object '<em><b>Comparison Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp1 <em>Exp1</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp2 <em>Exp2</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getComparator <em>Comparator</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComparisonExpression()
 * 
 * 
 */
public interface ComparisonExpression extends BooleanExpression {
	/**
	 * Returns the value of the '<em><b>Exp1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exp1</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exp1</em>' reference.
	 * @see #setExp1(Expression)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComparisonExpression_Exp1()
	 *  required="true"
	 * 
	 */
	Expression getExp1();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp1 <em>Exp1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exp1</em>' reference.
	 * @see #getExp1()
	 * 
	 */
	void setExp1(Expression value);

	/**
	 * Returns the value of the '<em><b>Exp2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exp2</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exp2</em>' reference.
	 * @see #setExp2(Expression)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComparisonExpression_Exp2()
	 *  required="true"
	 * 
	 */
	Expression getExp2();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp2 <em>Exp2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exp2</em>' reference.
	 * @see #getExp2()
	 * 
	 */
	void setExp2(Expression value);

	/**
	 * Returns the value of the '<em><b>Comparator</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.cp.ComparatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comparator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comparator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.ComparatorEnum
	 * @see #setComparator(ComparatorEnum)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComparisonExpression_Comparator()
	 *  required="true"
	 * 
	 */
	ComparatorEnum getComparator();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getComparator <em>Comparator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comparator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.ComparatorEnum
	 * @see #getComparator()
	 * 
	 */
	void setComparator(ComparatorEnum value);

} // ComparisonExpression
