/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.types.typesPaasage;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>OS</b></em>'.
 * <!-- end-user-doc -->
 *
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getName <em>Name</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getVers <em>Vers</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getArchitecture <em>Architecture</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getOS()
 * 
 * 
 */
public interface OS extends PaaSageCPElement {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getOS_Name()
	 *  required="true"
	 * 
	 */
	String getName();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * 
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vers</em>' attribute.
	 * @see #setVers(String)
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getOS_Vers()
	 * 
	 * 
	 */
	String getVers();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getVers <em>Vers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vers</em>' attribute.
	 * @see #getVers()
	 * 
	 */
	void setVers(String value);

	/**
	 * Returns the value of the '<em><b>Architecture</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Architecture</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Architecture</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum
	 * @see #setArchitecture(OSArchitectureEnum)
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getOS_Architecture()
	 *  required="true"
	 * 
	 */
	OSArchitectureEnum getArchitecture();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getArchitecture <em>Architecture</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Architecture</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum
	 * @see #getArchitecture()
	 * 
	 */
	void setArchitecture(OSArchitectureEnum value);

} // OS
