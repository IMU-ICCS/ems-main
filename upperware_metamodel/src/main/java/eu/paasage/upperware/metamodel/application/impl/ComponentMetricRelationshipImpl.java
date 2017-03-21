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

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.ComponentMetricRelationship;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component Metric Relationship</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ComponentMetricRelationshipImpl#getComponent <em>Component</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ComponentMetricRelationshipImpl#getMetricId <em>Metric Id</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class ComponentMetricRelationshipImpl extends CDOObjectImpl implements ComponentMetricRelationship {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected ComponentMetricRelationshipImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.COMPONENT_METRIC_RELATIONSHIP;
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
	public ApplicationComponent getComponent() {
		return (ApplicationComponent)eGet(ApplicationPackage.Literals.COMPONENT_METRIC_RELATIONSHIP__COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setComponent(ApplicationComponent newComponent) {
		eSet(ApplicationPackage.Literals.COMPONENT_METRIC_RELATIONSHIP__COMPONENT, newComponent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String getMetricId() {
		return (String)eGet(ApplicationPackage.Literals.COMPONENT_METRIC_RELATIONSHIP__METRIC_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setMetricId(String newMetricId) {
		eSet(ApplicationPackage.Literals.COMPONENT_METRIC_RELATIONSHIP__METRIC_ID, newMetricId);
	}

} //ComponentMetricRelationshipImpl
