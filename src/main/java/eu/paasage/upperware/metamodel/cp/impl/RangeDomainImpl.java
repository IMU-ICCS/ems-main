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
import eu.paasage.upperware.metamodel.cp.RangeDomain;

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Range Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl#getFrom <em>From</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class RangeDomainImpl extends NumericDomainImpl implements RangeDomain {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected RangeDomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.RANGE_DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public NumericValueUpperware getFrom() {
		return (NumericValueUpperware)eGet(CpPackage.Literals.RANGE_DOMAIN__FROM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setFrom(NumericValueUpperware newFrom) {
		eSet(CpPackage.Literals.RANGE_DOMAIN__FROM, newFrom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public NumericValueUpperware getTo() {
		return (NumericValueUpperware)eGet(CpPackage.Literals.RANGE_DOMAIN__TO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setTo(NumericValueUpperware newTo) {
		eSet(CpPackage.Literals.RANGE_DOMAIN__TO, newTo);
	}

} //RangeDomainImpl
