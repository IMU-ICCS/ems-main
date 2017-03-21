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
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.ProviderDimension;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Provider Dimension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ProviderDimensionImpl#getValue <em>Value</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ProviderDimensionImpl#getProvider <em>Provider</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ProviderDimensionImpl#getMetricID <em>Metric ID</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class ProviderDimensionImpl extends CDOObjectImpl implements ProviderDimension {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected ProviderDimensionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.PROVIDER_DIMENSION;
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
	public double getValue() {
		return (Double)eGet(ApplicationPackage.Literals.PROVIDER_DIMENSION__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setValue(double newValue) {
		eSet(ApplicationPackage.Literals.PROVIDER_DIMENSION__VALUE, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Provider getProvider() {
		return (Provider)eGet(ApplicationPackage.Literals.PROVIDER_DIMENSION__PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setProvider(Provider newProvider) {
		eSet(ApplicationPackage.Literals.PROVIDER_DIMENSION__PROVIDER, newProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getMetricID() {
		return (String)eGet(ApplicationPackage.Literals.PROVIDER_DIMENSION__METRIC_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setMetricID(String newMetricID) {
		eSet(ApplicationPackage.Literals.PROVIDER_DIMENSION__METRIC_ID, newMetricID);
	}

} //ProviderDimensionImpl
