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
import eu.paasage.upperware.metamodel.application.CPU;

import eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CPU</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.CPUImpl#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.CPUImpl#getCores <em>Cores</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class CPUImpl extends ResourceUpperwareImpl implements CPU {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected CPUImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.CPU;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public FrequencyEnum getFrequency() {
		return (FrequencyEnum)eGet(ApplicationPackage.Literals.CPU__FREQUENCY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setFrequency(FrequencyEnum newFrequency) {
		eSet(ApplicationPackage.Literals.CPU__FREQUENCY, newFrequency);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public int getCores() {
		return (Integer)eGet(ApplicationPackage.Literals.CPU__CORES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setCores(int newCores) {
		eSet(ApplicationPackage.Literals.CPU__CORES, newCores);
	}

} //CPUImpl
