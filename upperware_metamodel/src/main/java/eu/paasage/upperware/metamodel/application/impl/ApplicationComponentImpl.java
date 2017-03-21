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

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.RequiredFeature;
import eu.paasage.upperware.metamodel.application.VirtualMachine;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;

import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl#getVm <em>Vm</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl#getPreferredLocations <em>Preferred Locations</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl#getRequiredProfile <em>Required Profile</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl#getFeatures <em>Features</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl#getRequiredFeatures <em>Required Features</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl#getPreferredProviders <em>Preferred Providers</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl#getMin <em>Min</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl#getMax <em>Max</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class ApplicationComponentImpl extends CloudMLElementUpperwareImpl implements ApplicationComponent {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected ApplicationComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.APPLICATION_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public VirtualMachine getVm() {
		return (VirtualMachine)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__VM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setVm(VirtualMachine newVm) {
		eSet(ApplicationPackage.Literals.APPLICATION_COMPONENT__VM, newVm);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<LocationUpperware> getPreferredLocations() {
		return (EList<LocationUpperware>)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__PREFERRED_LOCATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<VirtualMachineProfile> getRequiredProfile() {
		return (EList<VirtualMachineProfile>)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__REQUIRED_PROFILE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<String> getFeatures() {
		return (EList<String>)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__FEATURES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<RequiredFeature> getRequiredFeatures() {
		return (EList<RequiredFeature>)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__REQUIRED_FEATURES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<ProviderType> getPreferredProviders() {
		return (EList<ProviderType>)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__PREFERRED_PROVIDERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public int getMin() {
		return (Integer)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__MIN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setMin(int newMin) {
		eSet(ApplicationPackage.Literals.APPLICATION_COMPONENT__MIN, newMin);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public int getMax() {
		return (Integer)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__MAX, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setMax(int newMax) {
		eSet(ApplicationPackage.Literals.APPLICATION_COMPONENT__MAX, newMax);
	}

} //ApplicationComponentImpl
