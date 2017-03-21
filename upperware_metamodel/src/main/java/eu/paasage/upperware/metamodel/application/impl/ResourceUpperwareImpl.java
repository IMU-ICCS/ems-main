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
import eu.paasage.upperware.metamodel.application.ResourceUpperware;

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import eu.paasage.upperware.metamodel.types.typesPaasage.impl.PaaSageCPElementImpl;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ResourceUpperwareImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public abstract class ResourceUpperwareImpl extends PaaSageCPElementImpl implements ResourceUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected ResourceUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.RESOURCE_UPPERWARE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public NumericValueUpperware getValue() {
		return (NumericValueUpperware)eGet(ApplicationPackage.Literals.RESOURCE_UPPERWARE__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setValue(NumericValueUpperware newValue) {
		eSet(ApplicationPackage.Literals.RESOURCE_UPPERWARE__VALUE, newValue);
	}

} //ResourceUpperwareImpl
