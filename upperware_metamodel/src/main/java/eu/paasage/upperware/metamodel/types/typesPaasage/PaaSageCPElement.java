/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.types.typesPaasage;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paa Sage CP Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement#getTypeId <em>Type Id</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getPaaSageCPElement()
 *  abstract="true"
 *  CDOObject
 * 
 */
public interface PaaSageCPElement extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Id</em>' attribute.
	 * @see #setTypeId(long)
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getPaaSageCPElement_TypeId()
	 *  required="true"
	 * 
	 */
	long getTypeId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement#getTypeId <em>Type Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Id</em>' attribute.
	 * @see #getTypeId()
	 * 
	 */
	void setTypeId(long value);

} // PaaSageCPElement
