/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression;
import eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum;
import eu.paasage.upperware.metamodel.cp.CpPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composed Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public abstract class ComposedUnaryExpressionImpl extends UnaryExpressionImpl implements ComposedUnaryExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected ComposedUnaryExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.COMPOSED_UNARY_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ComposedUnaryOperatorEnum getOperator() {
		return (ComposedUnaryOperatorEnum)eGet(CpPackage.Literals.COMPOSED_UNARY_EXPRESSION__OPERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setOperator(ComposedUnaryOperatorEnum newOperator) {
		eSet(CpPackage.Literals.COMPOSED_UNARY_EXPRESSION__OPERATOR, newOperator);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public int getValue() {
		return (Integer)eGet(CpPackage.Literals.COMPOSED_UNARY_EXPRESSION__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setValue(int newValue) {
		eSet(CpPackage.Literals.COMPOSED_UNARY_EXPRESSION__VALUE, newValue);
	}

} //ComposedUnaryExpressionImpl
