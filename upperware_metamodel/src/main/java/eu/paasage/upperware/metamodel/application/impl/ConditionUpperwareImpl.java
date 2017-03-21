/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.ConditionUpperware;

import eu.paasage.upperware.metamodel.cp.BooleanExpression;

import eu.paasage.upperware.metamodel.cp.impl.BooleanExpressionImpl;

import eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Condition Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ConditionUpperwareImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ConditionUpperwareImpl#getExp1 <em>Exp1</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ConditionUpperwareImpl#getExp2 <em>Exp2</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class ConditionUpperwareImpl extends BooleanExpressionImpl implements ConditionUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected ConditionUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.CONDITION_UPPERWARE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public LogicOperatorEnum getOperator() {
		return (LogicOperatorEnum)eGet(ApplicationPackage.Literals.CONDITION_UPPERWARE__OPERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setOperator(LogicOperatorEnum newOperator) {
		eSet(ApplicationPackage.Literals.CONDITION_UPPERWARE__OPERATOR, newOperator);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public BooleanExpression getExp1() {
		return (BooleanExpression)eGet(ApplicationPackage.Literals.CONDITION_UPPERWARE__EXP1, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setExp1(BooleanExpression newExp1) {
		eSet(ApplicationPackage.Literals.CONDITION_UPPERWARE__EXP1, newExp1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public BooleanExpression getExp2() {
		return (BooleanExpression)eGet(ApplicationPackage.Literals.CONDITION_UPPERWARE__EXP2, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setExp2(BooleanExpression newExp2) {
		eSet(ApplicationPackage.Literals.CONDITION_UPPERWARE__EXP2, newExp2);
	}

} //ConditionUpperwareImpl
