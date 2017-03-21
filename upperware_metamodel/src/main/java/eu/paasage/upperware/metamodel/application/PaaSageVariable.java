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

import eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paa Sage Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getPaasageType <em>Paasage Type</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedComponent <em>Related Component</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getCpVariableId <em>Cp Variable Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedProvider <em>Related Provider</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedVirtualMachineProfile <em>Related Virtual Machine Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable()
 * 
 * @extends CDOObject
 * 
 */
public interface PaaSageVariable extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Paasage Type</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Paasage Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paasage Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum
	 * @see #setPaasageType(VariableElementTypeEnum)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable_PaasageType()
	 * 
	 * 
	 */
	VariableElementTypeEnum getPaasageType();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getPaasageType <em>Paasage Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Paasage Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum
	 * @see #getPaasageType()
	 * 
	 */
	void setPaasageType(VariableElementTypeEnum value);

	/**
	 * Returns the value of the '<em><b>Related Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Component</em>' reference.
	 * @see #setRelatedComponent(ApplicationComponent)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable_RelatedComponent()
	 * 
	 * 
	 */
	ApplicationComponent getRelatedComponent();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedComponent <em>Related Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Component</em>' reference.
	 * @see #getRelatedComponent()
	 * 
	 */
	void setRelatedComponent(ApplicationComponent value);

	/**
	 * Returns the value of the '<em><b>Cp Variable Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cp Variable Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cp Variable Id</em>' attribute.
	 * @see #setCpVariableId(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable_CpVariableId()
	 *  required="true"
	 * 
	 */
	String getCpVariableId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getCpVariableId <em>Cp Variable Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cp Variable Id</em>' attribute.
	 * @see #getCpVariableId()
	 * 
	 */
	void setCpVariableId(String value);

	/**
	 * Returns the value of the '<em><b>Related Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Provider</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Provider</em>' reference.
	 * @see #setRelatedProvider(Provider)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable_RelatedProvider()
	 * 
	 * 
	 */
	Provider getRelatedProvider();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedProvider <em>Related Provider</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Provider</em>' reference.
	 * @see #getRelatedProvider()
	 * 
	 */
	void setRelatedProvider(Provider value);

	/**
	 * Returns the value of the '<em><b>Related Virtual Machine Profile</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Virtual Machine Profile</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Virtual Machine Profile</em>' reference.
	 * @see #setRelatedVirtualMachineProfile(VirtualMachineProfile)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageVariable_RelatedVirtualMachineProfile()
	 * 
	 * 
	 */
	VirtualMachineProfile getRelatedVirtualMachineProfile();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedVirtualMachineProfile <em>Related Virtual Machine Profile</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Virtual Machine Profile</em>' reference.
	 * @see #getRelatedVirtualMachineProfile()
	 * 
	 */
	void setRelatedVirtualMachineProfile(VirtualMachineProfile value);

} // PaaSageVariable
