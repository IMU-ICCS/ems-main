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

import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Expression;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comparison Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl#getExp1 <em>Exp1</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl#getExp2 <em>Exp2</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl#getComparator <em>Comparator</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class ComparisonExpressionImpl extends BooleanExpressionImpl implements ComparisonExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected ComparisonExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.COMPARISON_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Expression getExp1() {
		return (Expression)eGet(CpPackage.Literals.COMPARISON_EXPRESSION__EXP1, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setExp1(Expression newExp1) {
		eSet(CpPackage.Literals.COMPARISON_EXPRESSION__EXP1, newExp1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Expression getExp2() {
		return (Expression)eGet(CpPackage.Literals.COMPARISON_EXPRESSION__EXP2, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setExp2(Expression newExp2) {
		eSet(CpPackage.Literals.COMPARISON_EXPRESSION__EXP2, newExp2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ComparatorEnum getComparator() {
		return (ComparatorEnum)eGet(CpPackage.Literals.COMPARISON_EXPRESSION__COMPARATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setComparator(ComparatorEnum newComparator) {
		eSet(CpPackage.Literals.COMPARISON_EXPRESSION__COMPARATOR, newComparator);
	}

} //ComparisonExpressionImpl
