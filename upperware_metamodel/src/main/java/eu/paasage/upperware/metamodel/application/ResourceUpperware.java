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

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ResourceUpperware#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getResourceUpperware()
 *  abstract="true"
 * 
 */
public interface ResourceUpperware extends PaaSageCPElement {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(NumericValueUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getResourceUpperware_Value()
	 *  containment="true" required="true"
	 * 
	 */
	NumericValueUpperware getValue();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ResourceUpperware#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * 
	 */
	void setValue(NumericValueUpperware value);

} // ResourceUpperware
