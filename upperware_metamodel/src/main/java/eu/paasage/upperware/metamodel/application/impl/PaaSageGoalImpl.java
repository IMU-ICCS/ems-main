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
import eu.paasage.upperware.metamodel.application.ComponentMetricRelationship;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;

import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;

import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Paa Sage Goal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageGoalImpl#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageGoalImpl#getGoal <em>Goal</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageGoalImpl#getFunction <em>Function</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageGoalImpl#getApplicationComponent <em>Application Component</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageGoalImpl#getApplicationMetric <em>Application Metric</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class PaaSageGoalImpl extends CDOObjectImpl implements PaaSageGoal {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected PaaSageGoalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.PAA_SAGE_GOAL;
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
		return (String)eGet(ApplicationPackage.Literals.PAA_SAGE_GOAL__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setId(String newId) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_GOAL__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public GoalOperatorEnum getGoal() {
		return (GoalOperatorEnum)eGet(ApplicationPackage.Literals.PAA_SAGE_GOAL__GOAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setGoal(GoalOperatorEnum newGoal) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_GOAL__GOAL, newGoal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public FunctionType getFunction() {
		return (FunctionType)eGet(ApplicationPackage.Literals.PAA_SAGE_GOAL__FUNCTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setFunction(FunctionType newFunction) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_GOAL__FUNCTION, newFunction);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<ComponentMetricRelationship> getApplicationComponent() {
		return (EList<ComponentMetricRelationship>)eGet(ApplicationPackage.Literals.PAA_SAGE_GOAL__APPLICATION_COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getApplicationMetric() {
		return (String)eGet(ApplicationPackage.Literals.PAA_SAGE_GOAL__APPLICATION_METRIC, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setApplicationMetric(String newApplicationMetric) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_GOAL__APPLICATION_METRIC, newApplicationMetric);
	}

	@Override
	public void setOptimisationAttribute(String optimisationAttribute) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_GOAL__EXT_OPTIMISATION_ATTRIBUTE_ID, optimisationAttribute);
	}

	@Override
	public String getOptimisationAttribute() {
		return (String)eGet(ApplicationPackage.Literals.PAA_SAGE_GOAL__EXT_OPTIMISATION_ATTRIBUTE_ID, true);
	}

} //PaaSageGoalImpl
