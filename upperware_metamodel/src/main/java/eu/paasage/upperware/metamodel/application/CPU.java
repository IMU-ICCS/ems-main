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

import eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CPU</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.CPU#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.CPU#getCores <em>Cores</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getCPU()
 * 
 * 
 */
public interface CPU extends ResourceUpperware {
	/**
	 * Returns the value of the '<em><b>Frequency</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Frequency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Frequency</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum
	 * @see #setFrequency(FrequencyEnum)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getCPU_Frequency()
	 * 
	 * 
	 */
	FrequencyEnum getFrequency();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.CPU#getFrequency <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Frequency</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum
	 * @see #getFrequency()
	 * 
	 */
	void setFrequency(FrequencyEnum value);

	/**
	 * Returns the value of the '<em><b>Cores</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cores</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cores</em>' attribute.
	 * @see #setCores(int)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getCPU_Cores()
	 *  required="true"
	 * 
	 */
	int getCores();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.CPU#getCores <em>Cores</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cores</em>' attribute.
	 * @see #getCores()
	 * 
	 */
	void setCores(int value);

} // CPU
