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

import eu.paasage.upperware.metamodel.application.ActionUpperware;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.ConditionUpperware;
import eu.paasage.upperware.metamodel.application.ElasticityRule;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Elasticity Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ElasticityRuleImpl#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ElasticityRuleImpl#getAction <em>Action</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ElasticityRuleImpl#getCondition <em>Condition</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class ElasticityRuleImpl extends CDOObjectImpl implements ElasticityRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected ElasticityRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.ELASTICITY_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getId() {
		return (String)eGet(ApplicationPackage.Literals.ELASTICITY_RULE__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setId(String newId) {
		eSet(ApplicationPackage.Literals.ELASTICITY_RULE__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ActionUpperware getAction() {
		return (ActionUpperware)eGet(ApplicationPackage.Literals.ELASTICITY_RULE__ACTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setAction(ActionUpperware newAction) {
		eSet(ApplicationPackage.Literals.ELASTICITY_RULE__ACTION, newAction);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ConditionUpperware getCondition() {
		return (ConditionUpperware)eGet(ApplicationPackage.Literals.ELASTICITY_RULE__CONDITION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setCondition(ConditionUpperware newCondition) {
		eSet(ApplicationPackage.Literals.ELASTICITY_RULE__CONDITION, newCondition);
	}

} //ElasticityRuleImpl
