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
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;

import eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Paa Sage Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl#getPaasageType <em>Paasage Type</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl#getRelatedComponent <em>Related Component</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl#getCpVariableId <em>Cp Variable Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl#getRelatedProvider <em>Related Provider</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl#getRelatedVirtualMachineProfile <em>Related Virtual Machine Profile</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class PaaSageVariableImpl extends CDOObjectImpl implements PaaSageVariable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected PaaSageVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.PAA_SAGE_VARIABLE;
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
	public VariableElementTypeEnum getPaasageType() {
		return (VariableElementTypeEnum)eGet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__PAASAGE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setPaasageType(VariableElementTypeEnum newPaasageType) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__PAASAGE_TYPE, newPaasageType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ApplicationComponent getRelatedComponent() {
		return (ApplicationComponent)eGet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setRelatedComponent(ApplicationComponent newRelatedComponent) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_COMPONENT, newRelatedComponent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getCpVariableId() {
		return (String)eGet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__CP_VARIABLE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setCpVariableId(String newCpVariableId) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__CP_VARIABLE_ID, newCpVariableId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Provider getRelatedProvider() {
		return (Provider)eGet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setRelatedProvider(Provider newRelatedProvider) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_PROVIDER, newRelatedProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public VirtualMachineProfile getRelatedVirtualMachineProfile() {
		return (VirtualMachineProfile)eGet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_VIRTUAL_MACHINE_PROFILE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setRelatedVirtualMachineProfile(VirtualMachineProfile newRelatedVirtualMachineProfile) {
		eSet(ApplicationPackage.Literals.PAA_SAGE_VARIABLE__RELATED_VIRTUAL_MACHINE_PROFILE, newRelatedVirtualMachineProfile);
	}

} //PaaSageVariableImpl
