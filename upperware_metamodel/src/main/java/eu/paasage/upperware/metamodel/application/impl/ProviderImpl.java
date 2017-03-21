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
import eu.paasage.upperware.metamodel.application.Provider;

import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType;

import eu.paasage.upperware.metamodel.types.typesPaasage.impl.PaaSageCPElementImpl;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Provider</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ProviderImpl#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ProviderImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ProviderImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class ProviderImpl extends PaaSageCPElementImpl implements Provider {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected ProviderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.PROVIDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getId() {
		return (String)eGet(ApplicationPackage.Literals.PROVIDER__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setId(String newId) {
		eSet(ApplicationPackage.Literals.PROVIDER__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public LocationUpperware getLocation() {
		return (LocationUpperware)eGet(ApplicationPackage.Literals.PROVIDER__LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setLocation(LocationUpperware newLocation) {
		eSet(ApplicationPackage.Literals.PROVIDER__LOCATION, newLocation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ProviderType getType() {
		return (ProviderType)eGet(ApplicationPackage.Literals.PROVIDER__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setType(ProviderType newType) {
		eSet(ApplicationPackage.Literals.PROVIDER__TYPE, newType);
	}

} //ProviderImpl
