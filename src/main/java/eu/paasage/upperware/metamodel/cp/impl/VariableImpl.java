/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Domain;
import eu.paasage.upperware.metamodel.cp.Variable;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getLocationId <em>Location Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getProviderId <em>Provider Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getVmId <em>Vm Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getOsImageId <em>Os Image Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl#getHardwareId <em>Hardware Id</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class VariableImpl extends NumericExpressionImpl implements Variable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected VariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Domain getDomain() {
		return (Domain)eGet(CpPackage.Literals.VARIABLE__DOMAIN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setDomain(Domain newDomain) {
		eSet(CpPackage.Literals.VARIABLE__DOMAIN, newDomain);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getLocationId() {
		return (String)eGet(CpPackage.Literals.VARIABLE__LOCATION_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setLocationId(String newLocationId) {
		eSet(CpPackage.Literals.VARIABLE__LOCATION_ID, newLocationId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getProviderId() {
		return (String)eGet(CpPackage.Literals.VARIABLE__PROVIDER_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setProviderId(String newProviderId) {
		eSet(CpPackage.Literals.VARIABLE__PROVIDER_ID, newProviderId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getVmId() {
		return (String)eGet(CpPackage.Literals.VARIABLE__VM_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setVmId(String newVmId) {
		eSet(CpPackage.Literals.VARIABLE__VM_ID, newVmId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getOsImageId() {
		return (String)eGet(CpPackage.Literals.VARIABLE__OS_IMAGE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setOsImageId(String newOsImageId) {
		eSet(CpPackage.Literals.VARIABLE__OS_IMAGE_ID, newOsImageId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getHardwareId() {
		return (String)eGet(CpPackage.Literals.VARIABLE__HARDWARE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setHardwareId(String newHardwareId) {
		eSet(CpPackage.Literals.VARIABLE__HARDWARE_ID, newHardwareId);
	}

} //VariableImpl
