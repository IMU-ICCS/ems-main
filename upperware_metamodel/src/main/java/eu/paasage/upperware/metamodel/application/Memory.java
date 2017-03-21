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

import eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Memory</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.Memory#getUnit <em>Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getMemory()
 * 
 * 
 */
public interface Memory extends ResourceUpperware {
	/**
	 * Returns the value of the '<em><b>Unit</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum
	 * @see #setUnit(DataUnitEnum)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getMemory_Unit()
	 * 
	 * 
	 */
	DataUnitEnum getUnit();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.Memory#getUnit <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum
	 * @see #getUnit()
	 * 
	 */
	void setUnit(DataUnitEnum value);

} // Memory
