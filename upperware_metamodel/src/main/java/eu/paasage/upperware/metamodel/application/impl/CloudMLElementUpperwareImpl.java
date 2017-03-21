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
import eu.paasage.upperware.metamodel.application.CloudMLElementUpperware;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cloud ML Element Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.CloudMLElementUpperwareImpl#getCloudMLId <em>Cloud ML Id</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public abstract class CloudMLElementUpperwareImpl extends CDOObjectImpl implements CloudMLElementUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected CloudMLElementUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.CLOUD_ML_ELEMENT_UPPERWARE;
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
	public String getCloudMLId() {
		return (String)eGet(ApplicationPackage.Literals.CLOUD_ML_ELEMENT_UPPERWARE__CLOUD_ML_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setCloudMLId(String newCloudMLId) {
		eSet(ApplicationPackage.Literals.CLOUD_ML_ELEMENT_UPPERWARE__CLOUD_ML_ID, newCloudMLId);
	}

} //CloudMLElementUpperwareImpl
