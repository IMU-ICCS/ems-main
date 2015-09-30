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
 * A representation of the model object '<em><b>Component Metric Relationship</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getComponent <em>Component</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getMetricId <em>Metric Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getComponentMetricRelationship()
 * 
 * @extends CDOObject
 * 
 */
public interface ComponentMetricRelationship extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' reference.
	 * @see #setComponent(ApplicationComponent)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getComponentMetricRelationship_Component()
	 *  required="true"
	 * 
	 */
	ApplicationComponent getComponent();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getComponent <em>Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' reference.
	 * @see #getComponent()
	 * 
	 */
	void setComponent(ApplicationComponent value);

	/**
	 * Returns the value of the '<em><b>Metric Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metric Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metric Id</em>' attribute.
	 * @see #setMetricId(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getComponentMetricRelationship_MetricId()
	 * 
	 * 
	 */
	String getMetricId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getMetricId <em>Metric Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metric Id</em>' attribute.
	 * @see #getMetricId()
	 * 
	 */
	void setMetricId(String value);

} // ComponentMetricRelationship
