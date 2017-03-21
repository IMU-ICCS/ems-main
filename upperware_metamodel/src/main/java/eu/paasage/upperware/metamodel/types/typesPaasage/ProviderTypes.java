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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Provider Types</b></em>'.
 * <!-- end-user-doc -->
 *
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes#getTypes <em>Types</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getProviderTypes()
 * 
 *  CDOObject
 * 
 */
public interface ProviderTypes extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getProviderTypes_Types()
	 *  containment="true" required="true"
	 * 
	 */
	EList<ProviderType> getTypes();

} // ProviderTypes
