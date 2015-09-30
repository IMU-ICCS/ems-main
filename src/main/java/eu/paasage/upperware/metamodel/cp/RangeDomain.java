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

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getFrom <em>From</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getRangeDomain()
 * 
 * 
 */
public interface RangeDomain extends NumericDomain {
	/**
	 * Returns the value of the '<em><b>From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' containment reference.
	 * @see #setFrom(NumericValueUpperware)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getRangeDomain_From()
	 *  containment="true" required="true"
	 * 
	 */
	NumericValueUpperware getFrom();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getFrom <em>From</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' containment reference.
	 * @see #getFrom()
	 * 
	 */
	void setFrom(NumericValueUpperware value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' containment reference.
	 * @see #setTo(NumericValueUpperware)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getRangeDomain_To()
	 *  containment="true" required="true"
	 * 
	 */
	NumericValueUpperware getTo();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getTo <em>To</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' containment reference.
	 * @see #getTo()
	 * 
	 */
	void setTo(NumericValueUpperware value);

} // RangeDomain
