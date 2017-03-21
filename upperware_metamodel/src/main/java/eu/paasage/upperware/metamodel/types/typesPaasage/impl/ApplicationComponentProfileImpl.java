/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.types.typesPaasage.impl;

import eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile;
import eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentType;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application Component Profile</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentProfileImpl#getName <em>Name</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentProfileImpl#getVers <em>Vers</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentProfileImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public abstract class ApplicationComponentProfileImpl extends CDOObjectImpl implements ApplicationComponentProfile {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected ApplicationComponentProfileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getName() {
		return (String)eGet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setName(String newName) {
		eSet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getVers() {
		return (String)eGet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__VERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setVers(String newVers) {
		eSet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__VERS, newVers);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ApplicationComponentType getType() {
		return (ApplicationComponentType)eGet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setType(ApplicationComponentType newType) {
		eSet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__TYPE, newType);
	}

} //ApplicationComponentProfileImpl
