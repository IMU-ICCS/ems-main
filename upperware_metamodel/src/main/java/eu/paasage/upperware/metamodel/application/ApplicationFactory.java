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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage
 * @generated
 */
public interface ApplicationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	ApplicationFactory eINSTANCE = eu.paasage.upperware.metamodel.application.impl.ApplicationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Paasage Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Paasage Configuration</em>'.
	 */
	PaasageConfiguration createPaasageConfiguration();

	/**
	 * Returns a new object of class '<em>Virtual Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Virtual Machine</em>'.
	 */
	VirtualMachine createVirtualMachine();

	/**
	 * Returns a new object of class '<em>Virtual Machine Profile</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Virtual Machine Profile</em>'.
	 */
	VirtualMachineProfile createVirtualMachineProfile();

	/**
	 * Returns a new object of class '<em>Memory</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Memory</em>'.
	 */
	Memory createMemory();

	/**
	 * Returns a new object of class '<em>Storage</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Storage</em>'.
	 */
	Storage createStorage();

	/**
	 * Returns a new object of class '<em>CPU</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>CPU</em>'.
	 */
	CPU createCPU();

	/**
	 * Returns a new object of class '<em>Provider</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provider</em>'.
	 */
	Provider createProvider();

	/**
	 * Returns a new object of class '<em>Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Component</em>'.
	 */
	ApplicationComponent createApplicationComponent();

	/**
	 * Returns a new object of class '<em>Elasticity Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Elasticity Rule</em>'.
	 */
	ElasticityRule createElasticityRule();

	/**
	 * Returns a new object of class '<em>Action Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Upperware</em>'.
	 */
	ActionUpperware createActionUpperware();

	/**
	 * Returns a new object of class '<em>Condition Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Condition Upperware</em>'.
	 */
	ConditionUpperware createConditionUpperware();

	/**
	 * Returns a new object of class '<em>Paa Sage Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Paa Sage Variable</em>'.
	 */
	PaaSageVariable createPaaSageVariable();

	/**
	 * Returns a new object of class '<em>Paa Sage Goal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Paa Sage Goal</em>'.
	 */
	PaaSageGoal createPaaSageGoal();

	/**
	 * Returns a new object of class '<em>Required Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Required Feature</em>'.
	 */
	RequiredFeature createRequiredFeature();

	/**
	 * Returns a new object of class '<em>Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dimension</em>'.
	 */
	Dimension createDimension();

	/**
	 * Returns a new object of class '<em>Provider Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provider Dimension</em>'.
	 */
	ProviderDimension createProviderDimension();

	/**
	 * Returns a new object of class '<em>Image Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Image Upperware</em>'.
	 */
	ImageUpperware createImageUpperware();

	/**
	 * Returns a new object of class '<em>Component Metric Relationship</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Component Metric Relationship</em>'.
	 */
	ComponentMetricRelationship createComponentMetricRelationship();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 */
	ApplicationPackage getApplicationPackage();

} //ApplicationFactory
