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

import eu.paasage.upperware.metamodel.cp.Expression;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paasage Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getGoals <em>Goals</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getVariables <em>Variables</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getRules <em>Rules</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getComponents <em>Components</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getProviders <em>Providers</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getVmProfiles <em>Vm Profiles</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getAuxExpressions <em>Aux Expressions</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getVms <em>Vms</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getMonitoredDimensions <em>Monitored Dimensions</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaasageConfiguration()
 * 
 * @extends CDOObject
 * 
 */
public interface PaasageConfiguration extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaasageConfiguration_Id()
	 *  required="true"
	 * 
	 */
	String getId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * 
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Goals</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.PaaSageGoal}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goals</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goals</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaasageConfiguration_Goals()
	 *  containment="true"
	 * 
	 */
	EList<PaaSageGoal> getGoals();

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.PaaSageVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaasageConfiguration_Variables()
	 *  containment="true"
	 * 
	 */
	EList<PaaSageVariable> getVariables();

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.ElasticityRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaasageConfiguration_Rules()
	 *  containment="true"
	 * 
	 */
	EList<ElasticityRule> getRules();

	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.ApplicationComponent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Components</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaasageConfiguration_Components()
	 *  containment="true"
	 * 
	 */
	EList<ApplicationComponent> getComponents();

	/**
	 * Returns the value of the '<em><b>Providers</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.Provider}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Providers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Providers</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaasageConfiguration_Providers()
	 *  containment="true" required="true"
	 * 
	 */
	EList<Provider> getProviders();

	/**
	 * Returns the value of the '<em><b>Vm Profiles</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm Profiles</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm Profiles</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaasageConfiguration_VmProfiles()
	 *  containment="true"
	 * 
	 */
	EList<VirtualMachineProfile> getVmProfiles();

	/**
	 * Returns the value of the '<em><b>Aux Expressions</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aux Expressions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aux Expressions</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaasageConfiguration_AuxExpressions()
	 *  containment="true"
	 * 
	 */
	EList<Expression> getAuxExpressions();

	/**
	 * Returns the value of the '<em><b>Vms</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.VirtualMachine}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vms</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vms</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaasageConfiguration_Vms()
	 *  containment="true"
	 * 
	 */
	EList<VirtualMachine> getVms();

	/**
	 * Returns the value of the '<em><b>Monitored Dimensions</b></em>' reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.Dimension}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Monitored Dimensions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Monitored Dimensions</em>' reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaasageConfiguration_MonitoredDimensions()
	 * 
	 * 
	 */
	EList<Dimension> getMonitoredDimensions();

} // PaasageConfiguration
