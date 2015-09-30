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
 * A representation of the model object '<em><b>Cloud ML Element Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.CloudMLElementUpperware#getCloudMLId <em>Cloud ML Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getCloudMLElementUpperware()
 *  abstract="true"
 * @extends CDOObject
 * 
 */
public interface CloudMLElementUpperware extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Cloud ML Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cloud ML Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cloud ML Id</em>' attribute.
	 * @see #setCloudMLId(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getCloudMLElementUpperware_CloudMLId()
	 * 
	 * 
	 */
	String getCloudMLId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.CloudMLElementUpperware#getCloudMLId <em>Cloud ML Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cloud ML Id</em>' attribute.
	 * @see #getCloudMLId()
	 * 
	 */
	void setCloudMLId(String value);

} // CloudMLElementUpperware
