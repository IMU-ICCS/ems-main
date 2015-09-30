/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.cp;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getDomain <em>Domain</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getLocationId <em>Location Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getProviderId <em>Provider Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getVmId <em>Vm Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getOsImageId <em>Os Image Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getHardwareId <em>Hardware Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable()
 * 
 * 
 */
public interface Variable extends NumericExpression {
	/**
	 * Returns the value of the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' containment reference.
	 * @see #setDomain(Domain)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_Domain()
	 *  containment="true" required="true"
	 * 
	 */
	Domain getDomain();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getDomain <em>Domain</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' containment reference.
	 * @see #getDomain()
	 * 
	 */
	void setDomain(Domain value);

	/**
	 * Returns the value of the '<em><b>Location Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location Id</em>' attribute.
	 * @see #setLocationId(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_LocationId()
	 * 
	 * 
	 */
	String getLocationId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getLocationId <em>Location Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location Id</em>' attribute.
	 * @see #getLocationId()
	 * 
	 */
	void setLocationId(String value);

	/**
	 * Returns the value of the '<em><b>Provider Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider Id</em>' attribute.
	 * @see #setProviderId(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_ProviderId()
	 * 
	 * 
	 */
	String getProviderId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getProviderId <em>Provider Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider Id</em>' attribute.
	 * @see #getProviderId()
	 * 
	 */
	void setProviderId(String value);

	/**
	 * Returns the value of the '<em><b>Vm Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm Id</em>' attribute.
	 * @see #setVmId(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_VmId()
	 * 
	 * 
	 */
	String getVmId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getVmId <em>Vm Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vm Id</em>' attribute.
	 * @see #getVmId()
	 * 
	 */
	void setVmId(String value);

	/**
	 * Returns the value of the '<em><b>Os Image Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Os Image Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Os Image Id</em>' attribute.
	 * @see #setOsImageId(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_OsImageId()
	 * 
	 * 
	 */
	String getOsImageId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getOsImageId <em>Os Image Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Os Image Id</em>' attribute.
	 * @see #getOsImageId()
	 * 
	 */
	void setOsImageId(String value);

	/**
	 * Returns the value of the '<em><b>Hardware Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hardware Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hardware Id</em>' attribute.
	 * @see #setHardwareId(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_HardwareId()
	 * 
	 * 
	 */
	String getHardwareId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getHardwareId <em>Hardware Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hardware Id</em>' attribute.
	 * @see #getHardwareId()
	 * 
	 */
	void setHardwareId(String value);

} // Variable
