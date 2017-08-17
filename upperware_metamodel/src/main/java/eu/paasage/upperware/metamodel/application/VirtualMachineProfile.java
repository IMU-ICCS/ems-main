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

import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.OS;
import eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Virtual Machine Profile</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getSize <em>Size</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getMemory <em>Memory</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getStorage <em>Storage</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getCpu <em>Cpu</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getOs <em>Os</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getProviderDimension <em>Provider Dimension</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getLocation <em>Location</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getImage <em>Image</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getRelatedCloudVMId <em>Related Cloud VM Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile()
 * 
 * 
 */
public interface VirtualMachineProfile extends CloudMLElementUpperware {
	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum
	 * @see #setSize(VMSizeEnum)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Size()
	 *  required="true"
	 * 
	 */
	VMSizeEnum getSize();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum
	 * @see #getSize()
	 * 
	 */
	void setSize(VMSizeEnum value);

	/**
	 * Returns the value of the '<em><b>Memory</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memory</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Memory</em>' containment reference.
	 * @see #setMemory(Memory)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Memory()
	 *  containment="true"
	 * 
	 */
	Memory getMemory();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getMemory <em>Memory</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory</em>' containment reference.
	 * @see #getMemory()
	 * 
	 */
	void setMemory(Memory value);

	/**
	 * Returns the value of the '<em><b>Storage</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Storage</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Storage</em>' containment reference.
	 * @see #setStorage(Storage)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Storage()
	 *  containment="true"
	 * 
	 */
	Storage getStorage();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getStorage <em>Storage</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Storage</em>' containment reference.
	 * @see #getStorage()
	 * 
	 */
	void setStorage(Storage value);

	/**
	 * Returns the value of the '<em><b>Cpu</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cpu</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cpu</em>' containment reference.
	 * @see #setCpu(CPU)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Cpu()
	 *  containment="true"
	 * 
	 */
	CPU getCpu();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getCpu <em>Cpu</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cpu</em>' containment reference.
	 * @see #getCpu()
	 * 
	 */
	void setCpu(CPU value);

	/**
	 * Returns the value of the '<em><b>Os</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Os</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Os</em>' containment reference.
	 * @see #setOs(OS)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Os()
	 *  containment="true"
	 * 
	 */
	OS getOs();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getOs <em>Os</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Os</em>' containment reference.
	 * @see #getOs()
	 * 
	 */
	void setOs(OS value);

	/**
	 * Returns the value of the '<em><b>Provider Dimension</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.ProviderDimension}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider Dimension</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider Dimension</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_ProviderDimension()
	 *  containment="true"
	 * 
	 */
	EList<ProviderDimension> getProviderDimension();

	/**
	 * Returns the value of the '<em><b>Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' reference.
	 * @see #setLocation(LocationUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Location()
	 * 
	 * 
	 */
	LocationUpperware getLocation();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getLocation <em>Location</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' reference.
	 * @see #getLocation()
	 * 
	 */
	void setLocation(LocationUpperware value);

	/**
	 * Returns the value of the '<em><b>Image</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image</em>' containment reference.
	 * @see #setImage(ImageUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_Image()
	 *  containment="true"
	 * 
	 */
	ImageUpperware getImage();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getImage <em>Image</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image</em>' containment reference.
	 * @see #getImage()
	 * 
	 */
	void setImage(ImageUpperware value);

	/**
	 * Returns the value of the '<em><b>Related Cloud VM Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Cloud VM Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Cloud VM Id</em>' attribute.
	 * @see #setRelatedCloudVMId(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getVirtualMachineProfile_RelatedCloudVMId()
	 *  required="true"
	 * 
	 */
	String getRelatedCloudVMId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getRelatedCloudVMId <em>Related Cloud VM Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Cloud VM Id</em>' attribute.
	 * @see #getRelatedCloudVMId()
	 * 
	 */
	void setRelatedCloudVMId(String value);

	String getFlavourName();

	void setFlavourName(String newFlavourName);

} // VirtualMachineProfile
