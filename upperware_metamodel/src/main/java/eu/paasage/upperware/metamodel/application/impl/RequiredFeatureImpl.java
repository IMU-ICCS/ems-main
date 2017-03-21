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
import eu.paasage.upperware.metamodel.application.RequiredFeature;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Required Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl#getProvidedBy <em>Provided By</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl#isRemote <em>Remote</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl#isOptional <em>Optional</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl#isContaiment <em>Contaiment</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class RequiredFeatureImpl extends CDOObjectImpl implements RequiredFeature {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected RequiredFeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.REQUIRED_FEATURE;
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
	public String getFeature() {
		return (String)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__FEATURE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setFeature(String newFeature) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__FEATURE, newFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public CloudMLElementUpperware getProvidedBy() {
		return (CloudMLElementUpperware)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__PROVIDED_BY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setProvidedBy(CloudMLElementUpperware newProvidedBy) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__PROVIDED_BY, newProvidedBy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public boolean isRemote() {
		return (Boolean)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__REMOTE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setRemote(boolean newRemote) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__REMOTE, newRemote);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public boolean isOptional() {
		return (Boolean)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__OPTIONAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setOptional(boolean newOptional) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__OPTIONAL, newOptional);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public boolean isContaiment() {
		return (Boolean)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__CONTAIMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setContaiment(boolean newContaiment) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__CONTAIMENT, newContaiment);
	}

} //RequiredFeatureImpl
