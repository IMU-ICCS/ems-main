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
import eu.paasage.upperware.metamodel.application.Dimension;
import eu.paasage.upperware.metamodel.application.ElasticityRule;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachine;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;

import eu.paasage.upperware.metamodel.cp.Expression;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Paasage Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl#getGoals <em>Goals</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl#getRules <em>Rules</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl#getComponents <em>Components</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl#getProviders <em>Providers</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl#getVmProfiles <em>Vm Profiles</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl#getAuxExpressions <em>Aux Expressions</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl#getVms <em>Vms</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl#getMonitoredDimensions <em>Monitored Dimensions</em>}</li>
 * </ul>
 * </p>
 *
 * 
 */
public class PaasageConfigurationImpl extends CDOObjectImpl implements PaasageConfiguration {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected PaasageConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.PAASAGE_CONFIGURATION;
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
	public String getId() {
		return (String)eGet(ApplicationPackage.Literals.PAASAGE_CONFIGURATION__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void setId(String newId) {
		eSet(ApplicationPackage.Literals.PAASAGE_CONFIGURATION__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<PaaSageGoal> getGoals() {
		return (EList<PaaSageGoal>)eGet(ApplicationPackage.Literals.PAASAGE_CONFIGURATION__GOALS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<PaaSageVariable> getVariables() {
		return (EList<PaaSageVariable>)eGet(ApplicationPackage.Literals.PAASAGE_CONFIGURATION__VARIABLES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<ElasticityRule> getRules() {
		return (EList<ElasticityRule>)eGet(ApplicationPackage.Literals.PAASAGE_CONFIGURATION__RULES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<ApplicationComponent> getComponents() {
		return (EList<ApplicationComponent>)eGet(ApplicationPackage.Literals.PAASAGE_CONFIGURATION__COMPONENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<Provider> getProviders() {
		return (EList<Provider>)eGet(ApplicationPackage.Literals.PAASAGE_CONFIGURATION__PROVIDERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<VirtualMachineProfile> getVmProfiles() {
		return (EList<VirtualMachineProfile>)eGet(ApplicationPackage.Literals.PAASAGE_CONFIGURATION__VM_PROFILES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<Expression> getAuxExpressions() {
		return (EList<Expression>)eGet(ApplicationPackage.Literals.PAASAGE_CONFIGURATION__AUX_EXPRESSIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<VirtualMachine> getVms() {
		return (EList<VirtualMachine>)eGet(ApplicationPackage.Literals.PAASAGE_CONFIGURATION__VMS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EList<Dimension> getMonitoredDimensions() {
		return (EList<Dimension>)eGet(ApplicationPackage.Literals.PAASAGE_CONFIGURATION__MONITORED_DIMENSIONS, true);
	}

} //PaasageConfigurationImpl
