/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.types.typesPaasage;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage
 * 
 */
public interface TypesPaasageFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	TypesPaasageFactory eINSTANCE = eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasageFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>OS</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>OS</em>'.
	 * 
	 */
	OS createOS();

	/**
	 * Returns a new object of class '<em>Continent Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Continent Upperware</em>'.
	 * 
	 */
	ContinentUpperware createContinentUpperware();

	/**
	 * Returns a new object of class '<em>Country Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Country Upperware</em>'.
	 * 
	 */
	CountryUpperware createCountryUpperware();

	/**
	 * Returns a new object of class '<em>City Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>City Upperware</em>'.
	 * 
	 */
	CityUpperware createCityUpperware();

	/**
	 * Returns a new object of class '<em>Locations</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Locations</em>'.
	 * 
	 */
	Locations createLocations();

	/**
	 * Returns a new object of class '<em>Application Component Profiles</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application Component Profiles</em>'.
	 * 
	 */
	ApplicationComponentProfiles createApplicationComponentProfiles();

	/**
	 * Returns a new object of class '<em>Operating Systems</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operating Systems</em>'.
	 * 
	 */
	OperatingSystems createOperatingSystems();

	/**
	 * Returns a new object of class '<em>Database Profile</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Database Profile</em>'.
	 * 
	 */
	DatabaseProfile createDatabaseProfile();

	/**
	 * Returns a new object of class '<em>Web Server Profile</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Web Server Profile</em>'.
	 * 
	 */
	WebServerProfile createWebServerProfile();

	/**
	 * Returns a new object of class '<em>Application Server Profile</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application Server Profile</em>'.
	 * 
	 */
	ApplicationServerProfile createApplicationServerProfile();

	/**
	 * Returns a new object of class '<em>War Profile</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>War Profile</em>'.
	 * 
	 */
	WarProfile createWarProfile();

	/**
	 * Returns a new object of class '<em>Jar Profile</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Jar Profile</em>'.
	 * 
	 */
	JarProfile createJarProfile();

	/**
	 * Returns a new object of class '<em>Application Component Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application Component Type</em>'.
	 * 
	 */
	ApplicationComponentType createApplicationComponentType();

	/**
	 * Returns a new object of class '<em>Provider Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provider Type</em>'.
	 * 
	 */
	ProviderType createProviderType();

	/**
	 * Returns a new object of class '<em>Provider Types</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provider Types</em>'.
	 * 
	 */
	ProviderTypes createProviderTypes();

	/**
	 * Returns a new object of class '<em>Application Component Types</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application Component Types</em>'.
	 * 
	 */
	ApplicationComponentTypes createApplicationComponentTypes();

	/**
	 * Returns a new object of class '<em>Action Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Type</em>'.
	 * 
	 */
	ActionType createActionType();

	/**
	 * Returns a new object of class '<em>Action Types</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Types</em>'.
	 * 
	 */
	ActionTypes createActionTypes();

	/**
	 * Returns a new object of class '<em>Function Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Type</em>'.
	 * 
	 */
	FunctionType createFunctionType();

	/**
	 * Returns a new object of class '<em>Function Types</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Types</em>'.
	 * 
	 */
	FunctionTypes createFunctionTypes();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * 
	 */
	TypesPaasagePackage getTypesPaasagePackage();

} //TypesPaasageFactory
