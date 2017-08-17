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
import eu.paasage.upperware.metamodel.application.ImageUpperware;
import eu.paasage.upperware.metamodel.application.Memory;
import eu.paasage.upperware.metamodel.application.ProviderDimension;
import eu.paasage.upperware.metamodel.application.Storage;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;

import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.OS;
import eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Virtual Machine Profile</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getSize <em>Size</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getMemory <em>Memory</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getStorage <em>Storage</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getCpu <em>Cpu</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getOs <em>Os</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getProviderDimension <em>Provider Dimension</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getImage <em>Image</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getRelatedCloudVMId <em>Related Cloud VM Id</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class VirtualMachineProfileImpl extends CloudMLElementUpperwareImpl implements VirtualMachineProfile {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected VirtualMachineProfileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public VMSizeEnum getSize() {
		return (VMSizeEnum)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__SIZE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setSize(VMSizeEnum newSize) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__SIZE, newSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Memory getMemory() {
		return (Memory)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__MEMORY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setMemory(Memory newMemory) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__MEMORY, newMemory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Storage getStorage() {
		return (Storage)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__STORAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setStorage(Storage newStorage) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__STORAGE, newStorage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public CPU getCpu() {
		return (CPU)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__CPU, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setCpu(CPU newCpu) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__CPU, newCpu);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public OS getOs() {
		return (OS)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__OS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setOs(OS newOs) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__OS, newOs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<ProviderDimension> getProviderDimension() {
		return (EList<ProviderDimension>)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__PROVIDER_DIMENSION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public LocationUpperware getLocation() {
		return (LocationUpperware)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setLocation(LocationUpperware newLocation) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__LOCATION, newLocation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ImageUpperware getImage() {
		return (ImageUpperware)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__IMAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setImage(ImageUpperware newImage) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__IMAGE, newImage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getRelatedCloudVMId() {
		return (String)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__RELATED_CLOUD_VM_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setRelatedCloudVMId(String newRelatedCloudVMId) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__RELATED_CLOUD_VM_ID, newRelatedCloudVMId);
	}

	@Override
	public String getFlavourName() {
		return (String)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__FLAVOUR_NAME_ID, true);
	}

	@Override
	public void setFlavourName(String newFlavourName) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__FLAVOUR_NAME_ID, newFlavourName);
	}

} //VirtualMachineProfileImpl
