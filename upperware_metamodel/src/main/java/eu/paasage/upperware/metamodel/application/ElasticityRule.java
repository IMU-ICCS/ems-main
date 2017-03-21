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

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Elasticity Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getAction <em>Action</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getCondition <em>Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getElasticityRule()
 * 
 * @extends CDOObject
 * 
 */
public interface ElasticityRule extends CDOObject {
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
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getElasticityRule_Id()
	 *  required="true"
	 * 
	 */
	String getId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * 
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' containment reference.
	 * @see #setAction(ActionUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getElasticityRule_Action()
	 *  containment="true" required="true"
	 * 
	 */
	ActionUpperware getAction();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getAction <em>Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' containment reference.
	 * @see #getAction()
	 * 
	 */
	void setAction(ActionUpperware value);

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(ConditionUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getElasticityRule_Condition()
	 *  containment="true" required="true"
	 * 
	 */
	ConditionUpperware getCondition();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * 
	 */
	void setCondition(ConditionUpperware value);

} // ElasticityRule
