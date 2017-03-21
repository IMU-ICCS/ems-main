/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.application;

import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;

import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paa Sage Goal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getGoal <em>Goal</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getFunction <em>Function</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getApplicationComponent <em>Application Component</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getApplicationMetric <em>Application Metric</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal()
 * 
 * @extends CDOObject
 * 
 */
public interface PaaSageGoal extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal_Id()
	 *  required="true"
	 * 
	 */
	String getId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * 
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Goal</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.cp.GoalOperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goal</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
	 * @see #setGoal(GoalOperatorEnum)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal_Goal()
	 *  required="true"
	 * 
	 */
	GoalOperatorEnum getGoal();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getGoal <em>Goal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Goal</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
	 * @see #getGoal()
	 * 
	 */
	void setGoal(GoalOperatorEnum value);

	/**
	 * Returns the value of the '<em><b>Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' reference.
	 * @see #setFunction(FunctionType)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal_Function()
	 *  required="true"
	 * 
	 */
	FunctionType getFunction();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getFunction <em>Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' reference.
	 * @see #getFunction()
	 * 
	 */
	void setFunction(FunctionType value);

	/**
	 * Returns the value of the '<em><b>Application Component</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application Component</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Application Component</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal_ApplicationComponent()
	 *  containment="true"
	 * 
	 */
	EList<ComponentMetricRelationship> getApplicationComponent();

	/**
	 * Returns the value of the '<em><b>Application Metric</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application Metric</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Application Metric</em>' attribute.
	 * @see #setApplicationMetric(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal_ApplicationMetric()
	 * 
	 * 
	 */
	String getApplicationMetric();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getApplicationMetric <em>Application Metric</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Application Metric</em>' attribute.
	 * @see #getApplicationMetric()
	 * 
	 */
	void setApplicationMetric(String value);

} // PaaSageGoal
