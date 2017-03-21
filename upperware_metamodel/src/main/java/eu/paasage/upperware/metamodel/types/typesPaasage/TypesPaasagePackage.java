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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasageFactory
 *  kind="package"
 * 
 */
public interface TypesPaasagePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	String eNAME = "typesPaasage";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	String eNS_URI = "http://www.paasage.eu/eu/paasage/upperware/metamodel/types/typesPaasage";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	String eNS_PREFIX = "typesPaasage";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	TypesPaasagePackage eINSTANCE = eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl.init();

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.PaaSageCPElementImpl <em>Paa Sage CP Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.PaaSageCPElementImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getPaaSageCPElement()
	 * 
	 */
	int PAA_SAGE_CP_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int PAA_SAGE_CP_ELEMENT__TYPE_ID = 0;

	/**
	 * The number of structural features of the '<em>Paa Sage CP Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int PAA_SAGE_CP_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Paa Sage CP Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int PAA_SAGE_CP_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.OSImpl <em>OS</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.OSImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getOS()
	 * 
	 */
	int OS = 1;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int OS__TYPE_ID = PAA_SAGE_CP_ELEMENT__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int OS__NAME = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int OS__VERS = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Architecture</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int OS__ARCHITECTURE = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>OS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int OS_FEATURE_COUNT = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>OS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int OS_OPERATION_COUNT = PAA_SAGE_CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.LocationUpperwareImpl <em>Location Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.LocationUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getLocationUpperware()
	 * 
	 */
	int LOCATION_UPPERWARE = 2;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int LOCATION_UPPERWARE__TYPE_ID = PAA_SAGE_CP_ELEMENT__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int LOCATION_UPPERWARE__NAME = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Alternative Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int LOCATION_UPPERWARE__ALTERNATIVE_NAMES = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Location Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int LOCATION_UPPERWARE_FEATURE_COUNT = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Location Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int LOCATION_UPPERWARE_OPERATION_COUNT = PAA_SAGE_CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ContinentUpperwareImpl <em>Continent Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ContinentUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getContinentUpperware()
	 * 
	 */
	int CONTINENT_UPPERWARE = 3;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int CONTINENT_UPPERWARE__TYPE_ID = LOCATION_UPPERWARE__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int CONTINENT_UPPERWARE__NAME = LOCATION_UPPERWARE__NAME;

	/**
	 * The feature id for the '<em><b>Alternative Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int CONTINENT_UPPERWARE__ALTERNATIVE_NAMES = LOCATION_UPPERWARE__ALTERNATIVE_NAMES;

	/**
	 * The number of structural features of the '<em>Continent Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int CONTINENT_UPPERWARE_FEATURE_COUNT = LOCATION_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Continent Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int CONTINENT_UPPERWARE_OPERATION_COUNT = LOCATION_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.CountryUpperwareImpl <em>Country Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.CountryUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getCountryUpperware()
	 * 
	 */
	int COUNTRY_UPPERWARE = 4;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int COUNTRY_UPPERWARE__TYPE_ID = LOCATION_UPPERWARE__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int COUNTRY_UPPERWARE__NAME = LOCATION_UPPERWARE__NAME;

	/**
	 * The feature id for the '<em><b>Alternative Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int COUNTRY_UPPERWARE__ALTERNATIVE_NAMES = LOCATION_UPPERWARE__ALTERNATIVE_NAMES;

	/**
	 * The feature id for the '<em><b>Continent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int COUNTRY_UPPERWARE__CONTINENT = LOCATION_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Country Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int COUNTRY_UPPERWARE_FEATURE_COUNT = LOCATION_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Country Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int COUNTRY_UPPERWARE_OPERATION_COUNT = LOCATION_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.CityUpperwareImpl <em>City Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.CityUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getCityUpperware()
	 * 
	 */
	int CITY_UPPERWARE = 5;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int CITY_UPPERWARE__TYPE_ID = LOCATION_UPPERWARE__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int CITY_UPPERWARE__NAME = LOCATION_UPPERWARE__NAME;

	/**
	 * The feature id for the '<em><b>Alternative Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int CITY_UPPERWARE__ALTERNATIVE_NAMES = LOCATION_UPPERWARE__ALTERNATIVE_NAMES;

	/**
	 * The feature id for the '<em><b>Country</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int CITY_UPPERWARE__COUNTRY = LOCATION_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>City Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int CITY_UPPERWARE_FEATURE_COUNT = LOCATION_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>City Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int CITY_UPPERWARE_OPERATION_COUNT = LOCATION_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.LocationsImpl <em>Locations</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.LocationsImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getLocations()
	 * 
	 */
	int LOCATIONS = 6;

	/**
	 * The feature id for the '<em><b>Locations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int LOCATIONS__LOCATIONS = 0;

	/**
	 * The number of structural features of the '<em>Locations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int LOCATIONS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Locations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int LOCATIONS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentProfileImpl <em>Application Component Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentProfileImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentProfile()
	 * 
	 */
	int APPLICATION_COMPONENT_PROFILE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_PROFILE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_PROFILE__VERS = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_PROFILE__TYPE = 2;

	/**
	 * The number of structural features of the '<em>Application Component Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Application Component Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentProfilesImpl <em>Application Component Profiles</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentProfilesImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentProfiles()
	 * 
	 */
	int APPLICATION_COMPONENT_PROFILES = 8;

	/**
	 * The feature id for the '<em><b>Profiles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_PROFILES__PROFILES = 0;

	/**
	 * The number of structural features of the '<em>Application Component Profiles</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_PROFILES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Application Component Profiles</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_PROFILES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.OperatingSystemsImpl <em>Operating Systems</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.OperatingSystemsImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getOperatingSystems()
	 * 
	 */
	int OPERATING_SYSTEMS = 9;

	/**
	 * The feature id for the '<em><b>Oss</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int OPERATING_SYSTEMS__OSS = 0;

	/**
	 * The number of structural features of the '<em>Operating Systems</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int OPERATING_SYSTEMS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Operating Systems</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int OPERATING_SYSTEMS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.DatabaseProfileImpl <em>Database Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.DatabaseProfileImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getDatabaseProfile()
	 * 
	 */
	int DATABASE_PROFILE = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int DATABASE_PROFILE__NAME = APPLICATION_COMPONENT_PROFILE__NAME;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int DATABASE_PROFILE__VERS = APPLICATION_COMPONENT_PROFILE__VERS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int DATABASE_PROFILE__TYPE = APPLICATION_COMPONENT_PROFILE__TYPE;

	/**
	 * The number of structural features of the '<em>Database Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int DATABASE_PROFILE_FEATURE_COUNT = APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Database Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int DATABASE_PROFILE_OPERATION_COUNT = APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.WebServerProfileImpl <em>Web Server Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.WebServerProfileImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getWebServerProfile()
	 * 
	 */
	int WEB_SERVER_PROFILE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int WEB_SERVER_PROFILE__NAME = APPLICATION_COMPONENT_PROFILE__NAME;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int WEB_SERVER_PROFILE__VERS = APPLICATION_COMPONENT_PROFILE__VERS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int WEB_SERVER_PROFILE__TYPE = APPLICATION_COMPONENT_PROFILE__TYPE;

	/**
	 * The number of structural features of the '<em>Web Server Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int WEB_SERVER_PROFILE_FEATURE_COUNT = APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Web Server Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int WEB_SERVER_PROFILE_OPERATION_COUNT = APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationServerProfileImpl <em>Application Server Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationServerProfileImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationServerProfile()
	 * 
	 */
	int APPLICATION_SERVER_PROFILE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_SERVER_PROFILE__NAME = APPLICATION_COMPONENT_PROFILE__NAME;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_SERVER_PROFILE__VERS = APPLICATION_COMPONENT_PROFILE__VERS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_SERVER_PROFILE__TYPE = APPLICATION_COMPONENT_PROFILE__TYPE;

	/**
	 * The number of structural features of the '<em>Application Server Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_SERVER_PROFILE_FEATURE_COUNT = APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Application Server Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_SERVER_PROFILE_OPERATION_COUNT = APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.WarProfileImpl <em>War Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.WarProfileImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getWarProfile()
	 * 
	 */
	int WAR_PROFILE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int WAR_PROFILE__NAME = APPLICATION_COMPONENT_PROFILE__NAME;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int WAR_PROFILE__VERS = APPLICATION_COMPONENT_PROFILE__VERS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int WAR_PROFILE__TYPE = APPLICATION_COMPONENT_PROFILE__TYPE;

	/**
	 * The number of structural features of the '<em>War Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int WAR_PROFILE_FEATURE_COUNT = APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>War Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int WAR_PROFILE_OPERATION_COUNT = APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.JarProfileImpl <em>Jar Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.JarProfileImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getJarProfile()
	 * 
	 */
	int JAR_PROFILE = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int JAR_PROFILE__NAME = APPLICATION_COMPONENT_PROFILE__NAME;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int JAR_PROFILE__VERS = APPLICATION_COMPONENT_PROFILE__VERS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int JAR_PROFILE__TYPE = APPLICATION_COMPONENT_PROFILE__TYPE;

	/**
	 * The number of structural features of the '<em>Jar Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int JAR_PROFILE_FEATURE_COUNT = APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Jar Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int JAR_PROFILE_OPERATION_COUNT = APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentTypeImpl <em>Application Component Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentTypeImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentType()
	 * 
	 */
	int APPLICATION_COMPONENT_TYPE = 15;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_TYPE__ID = 0;

	/**
	 * The number of structural features of the '<em>Application Component Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Application Component Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ProviderTypeImpl <em>Provider Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ProviderTypeImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getProviderType()
	 * 
	 */
	int PROVIDER_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int PROVIDER_TYPE__ID = 0;

	/**
	 * The number of structural features of the '<em>Provider Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int PROVIDER_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Provider Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int PROVIDER_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ProviderTypesImpl <em>Provider Types</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ProviderTypesImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getProviderTypes()
	 * 
	 */
	int PROVIDER_TYPES = 17;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int PROVIDER_TYPES__TYPES = 0;

	/**
	 * The number of structural features of the '<em>Provider Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int PROVIDER_TYPES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Provider Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int PROVIDER_TYPES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentTypesImpl <em>Application Component Types</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentTypesImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentTypes()
	 * 
	 */
	int APPLICATION_COMPONENT_TYPES = 18;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_TYPES__TYPES = 0;

	/**
	 * The number of structural features of the '<em>Application Component Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_TYPES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Application Component Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int APPLICATION_COMPONENT_TYPES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ActionTypeImpl <em>Action Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ActionTypeImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getActionType()
	 * 
	 */
	int ACTION_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int ACTION_TYPE__ID = 0;

	/**
	 * The number of structural features of the '<em>Action Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int ACTION_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Action Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int ACTION_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ActionTypesImpl <em>Action Types</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ActionTypesImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getActionTypes()
	 * 
	 */
	int ACTION_TYPES = 20;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int ACTION_TYPES__TYPES = 0;

	/**
	 * The number of structural features of the '<em>Action Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int ACTION_TYPES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Action Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int ACTION_TYPES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.FunctionTypeImpl <em>Function Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.FunctionTypeImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getFunctionType()
	 * 
	 */
	int FUNCTION_TYPE = 21;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int FUNCTION_TYPE__ID = 0;

	/**
	 * The number of structural features of the '<em>Function Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int FUNCTION_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Function Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int FUNCTION_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.FunctionTypesImpl <em>Function Types</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.FunctionTypesImpl
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getFunctionTypes()
	 * 
	 */
	int FUNCTION_TYPES = 22;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int FUNCTION_TYPES__TYPES = 0;

	/**
	 * The number of structural features of the '<em>Function Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int FUNCTION_TYPES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Function Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * 
	 */
	int FUNCTION_TYPES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum <em>Frequency Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getFrequencyEnum()
	 * 
	 */
	int FREQUENCY_ENUM = 23;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum <em>VM Size Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getVMSizeEnum()
	 * 
	 */
	int VM_SIZE_ENUM = 24;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum <em>Logic Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getLogicOperatorEnum()
	 * 
	 */
	int LOGIC_OPERATOR_ENUM = 25;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum <em>Data Unit Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getDataUnitEnum()
	 * 
	 */
	int DATA_UNIT_ENUM = 26;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum <em>Variable Element Type Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getVariableElementTypeEnum()
	 * 
	 */
	int VARIABLE_ELEMENT_TYPE_ENUM = 27;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum <em>OS Architecture Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getOSArchitectureEnum()
	 * 
	 */
	int OS_ARCHITECTURE_ENUM = 28;


	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement <em>Paa Sage CP Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Paa Sage CP Element</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement
	 * 
	 */
	EClass getPaaSageCPElement();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement#getTypeId()
	 * @see #getPaaSageCPElement()
	 * 
	 */
	EAttribute getPaaSageCPElement_TypeId();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS <em>OS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>OS</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OS
	 * 
	 */
	EClass getOS();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OS#getName()
	 * @see #getOS()
	 * 
	 */
	EAttribute getOS_Name();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getVers <em>Vers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vers</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OS#getVers()
	 * @see #getOS()
	 * 
	 */
	EAttribute getOS_Vers();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getArchitecture <em>Architecture</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Architecture</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OS#getArchitecture()
	 * @see #getOS()
	 * 
	 */
	EAttribute getOS_Architecture();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware <em>Location Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Location Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware
	 * 
	 */
	EClass getLocationUpperware();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware#getName()
	 * @see #getLocationUpperware()
	 * 
	 */
	EAttribute getLocationUpperware_Name();

	/**
	 * Returns the meta object for the attribute list '{@link eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware#getAlternativeNames <em>Alternative Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Alternative Names</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware#getAlternativeNames()
	 * @see #getLocationUpperware()
	 * 
	 */
	EAttribute getLocationUpperware_AlternativeNames();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ContinentUpperware <em>Continent Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Continent Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ContinentUpperware
	 * 
	 */
	EClass getContinentUpperware();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware <em>Country Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Country Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware
	 * 
	 */
	EClass getCountryUpperware();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware#getContinent <em>Continent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Continent</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware#getContinent()
	 * @see #getCountryUpperware()
	 * 
	 */
	EReference getCountryUpperware_Continent();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.CityUpperware <em>City Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>City Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.CityUpperware
	 * 
	 */
	EClass getCityUpperware();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.types.typesPaasage.CityUpperware#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Country</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.CityUpperware#getCountry()
	 * @see #getCityUpperware()
	 * 
	 */
	EReference getCityUpperware_Country();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.Locations <em>Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Locations</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.Locations
	 * 
	 */
	EClass getLocations();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.types.typesPaasage.Locations#getLocations <em>Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Locations</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.Locations#getLocations()
	 * @see #getLocations()
	 * 
	 */
	EReference getLocations_Locations();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile <em>Application Component Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Component Profile</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile
	 * 
	 */
	EClass getApplicationComponentProfile();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile#getName()
	 * @see #getApplicationComponentProfile()
	 * 
	 */
	EAttribute getApplicationComponentProfile_Name();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile#getVers <em>Vers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vers</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile#getVers()
	 * @see #getApplicationComponentProfile()
	 * 
	 */
	EAttribute getApplicationComponentProfile_Vers();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile#getType()
	 * @see #getApplicationComponentProfile()
	 * 
	 */
	EReference getApplicationComponentProfile_Type();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfiles <em>Application Component Profiles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Component Profiles</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfiles
	 * 
	 */
	EClass getApplicationComponentProfiles();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfiles#getProfiles <em>Profiles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Profiles</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfiles#getProfiles()
	 * @see #getApplicationComponentProfiles()
	 * 
	 */
	EReference getApplicationComponentProfiles_Profiles();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems <em>Operating Systems</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operating Systems</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems
	 * 
	 */
	EClass getOperatingSystems();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems#getOss <em>Oss</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Oss</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems#getOss()
	 * @see #getOperatingSystems()
	 * 
	 */
	EReference getOperatingSystems_Oss();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.DatabaseProfile <em>Database Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Database Profile</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.DatabaseProfile
	 * 
	 */
	EClass getDatabaseProfile();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.WebServerProfile <em>Web Server Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Web Server Profile</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.WebServerProfile
	 * 
	 */
	EClass getWebServerProfile();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationServerProfile <em>Application Server Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Server Profile</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationServerProfile
	 * 
	 */
	EClass getApplicationServerProfile();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.WarProfile <em>War Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>War Profile</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.WarProfile
	 * 
	 */
	EClass getWarProfile();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.JarProfile <em>Jar Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Jar Profile</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.JarProfile
	 * 
	 */
	EClass getJarProfile();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentType <em>Application Component Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Component Type</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentType
	 * 
	 */
	EClass getApplicationComponentType();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentType#getId()
	 * @see #getApplicationComponentType()
	 * 
	 */
	EAttribute getApplicationComponentType_Id();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType <em>Provider Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provider Type</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType
	 * 
	 */
	EClass getProviderType();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType#getId()
	 * @see #getProviderType()
	 * 
	 */
	EAttribute getProviderType_Id();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes <em>Provider Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provider Types</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes
	 * 
	 */
	EClass getProviderTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes#getTypes()
	 * @see #getProviderTypes()
	 * 
	 */
	EReference getProviderTypes_Types();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentTypes <em>Application Component Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Component Types</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentTypes
	 * 
	 */
	EClass getApplicationComponentTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentTypes#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentTypes#getTypes()
	 * @see #getApplicationComponentTypes()
	 * 
	 */
	EReference getApplicationComponentTypes_Types();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ActionType <em>Action Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Type</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ActionType
	 * 
	 */
	EClass getActionType();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ActionType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ActionType#getId()
	 * @see #getActionType()
	 * 
	 */
	EAttribute getActionType_Id();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ActionTypes <em>Action Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Types</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ActionTypes
	 * 
	 */
	EClass getActionTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ActionTypes#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ActionTypes#getTypes()
	 * @see #getActionTypes()
	 * 
	 */
	EReference getActionTypes_Types();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType <em>Function Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Type</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType
	 * 
	 */
	EClass getFunctionType();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType#getId()
	 * @see #getFunctionType()
	 * 
	 */
	EAttribute getFunctionType_Id();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes <em>Function Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Types</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes
	 * 
	 */
	EClass getFunctionTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes#getTypes()
	 * @see #getFunctionTypes()
	 * 
	 */
	EReference getFunctionTypes_Types();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum <em>Frequency Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Frequency Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum
	 * 
	 */
	EEnum getFrequencyEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum <em>VM Size Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>VM Size Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum
	 * 
	 */
	EEnum getVMSizeEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum <em>Logic Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Logic Operator Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum
	 * 
	 */
	EEnum getLogicOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum <em>Data Unit Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Unit Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum
	 * 
	 */
	EEnum getDataUnitEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum <em>Variable Element Type Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Variable Element Type Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum
	 * 
	 */
	EEnum getVariableElementTypeEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum <em>OS Architecture Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>OS Architecture Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum
	 * 
	 */
	EEnum getOSArchitectureEnum();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * 
	 */
	TypesPaasageFactory getTypesPaasageFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.PaaSageCPElementImpl <em>Paa Sage CP Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.PaaSageCPElementImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getPaaSageCPElement()
		 * 
		 */
		EClass PAA_SAGE_CP_ELEMENT = eINSTANCE.getPaaSageCPElement();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute PAA_SAGE_CP_ELEMENT__TYPE_ID = eINSTANCE.getPaaSageCPElement_TypeId();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.OSImpl <em>OS</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.OSImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getOS()
		 * 
		 */
		EClass OS = eINSTANCE.getOS();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute OS__NAME = eINSTANCE.getOS_Name();

		/**
		 * The meta object literal for the '<em><b>Vers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute OS__VERS = eINSTANCE.getOS_Vers();

		/**
		 * The meta object literal for the '<em><b>Architecture</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute OS__ARCHITECTURE = eINSTANCE.getOS_Architecture();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.LocationUpperwareImpl <em>Location Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.LocationUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getLocationUpperware()
		 * 
		 */
		EClass LOCATION_UPPERWARE = eINSTANCE.getLocationUpperware();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute LOCATION_UPPERWARE__NAME = eINSTANCE.getLocationUpperware_Name();

		/**
		 * The meta object literal for the '<em><b>Alternative Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute LOCATION_UPPERWARE__ALTERNATIVE_NAMES = eINSTANCE.getLocationUpperware_AlternativeNames();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ContinentUpperwareImpl <em>Continent Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ContinentUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getContinentUpperware()
		 * 
		 */
		EClass CONTINENT_UPPERWARE = eINSTANCE.getContinentUpperware();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.CountryUpperwareImpl <em>Country Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.CountryUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getCountryUpperware()
		 * 
		 */
		EClass COUNTRY_UPPERWARE = eINSTANCE.getCountryUpperware();

		/**
		 * The meta object literal for the '<em><b>Continent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference COUNTRY_UPPERWARE__CONTINENT = eINSTANCE.getCountryUpperware_Continent();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.CityUpperwareImpl <em>City Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.CityUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getCityUpperware()
		 * 
		 */
		EClass CITY_UPPERWARE = eINSTANCE.getCityUpperware();

		/**
		 * The meta object literal for the '<em><b>Country</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference CITY_UPPERWARE__COUNTRY = eINSTANCE.getCityUpperware_Country();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.LocationsImpl <em>Locations</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.LocationsImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getLocations()
		 * 
		 */
		EClass LOCATIONS = eINSTANCE.getLocations();

		/**
		 * The meta object literal for the '<em><b>Locations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference LOCATIONS__LOCATIONS = eINSTANCE.getLocations_Locations();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentProfileImpl <em>Application Component Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentProfileImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentProfile()
		 * 
		 */
		EClass APPLICATION_COMPONENT_PROFILE = eINSTANCE.getApplicationComponentProfile();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute APPLICATION_COMPONENT_PROFILE__NAME = eINSTANCE.getApplicationComponentProfile_Name();

		/**
		 * The meta object literal for the '<em><b>Vers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute APPLICATION_COMPONENT_PROFILE__VERS = eINSTANCE.getApplicationComponentProfile_Vers();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference APPLICATION_COMPONENT_PROFILE__TYPE = eINSTANCE.getApplicationComponentProfile_Type();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentProfilesImpl <em>Application Component Profiles</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentProfilesImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentProfiles()
		 * 
		 */
		EClass APPLICATION_COMPONENT_PROFILES = eINSTANCE.getApplicationComponentProfiles();

		/**
		 * The meta object literal for the '<em><b>Profiles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference APPLICATION_COMPONENT_PROFILES__PROFILES = eINSTANCE.getApplicationComponentProfiles_Profiles();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.OperatingSystemsImpl <em>Operating Systems</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.OperatingSystemsImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getOperatingSystems()
		 * 
		 */
		EClass OPERATING_SYSTEMS = eINSTANCE.getOperatingSystems();

		/**
		 * The meta object literal for the '<em><b>Oss</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference OPERATING_SYSTEMS__OSS = eINSTANCE.getOperatingSystems_Oss();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.DatabaseProfileImpl <em>Database Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.DatabaseProfileImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getDatabaseProfile()
		 * 
		 */
		EClass DATABASE_PROFILE = eINSTANCE.getDatabaseProfile();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.WebServerProfileImpl <em>Web Server Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.WebServerProfileImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getWebServerProfile()
		 * 
		 */
		EClass WEB_SERVER_PROFILE = eINSTANCE.getWebServerProfile();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationServerProfileImpl <em>Application Server Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationServerProfileImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationServerProfile()
		 * 
		 */
		EClass APPLICATION_SERVER_PROFILE = eINSTANCE.getApplicationServerProfile();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.WarProfileImpl <em>War Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.WarProfileImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getWarProfile()
		 * 
		 */
		EClass WAR_PROFILE = eINSTANCE.getWarProfile();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.JarProfileImpl <em>Jar Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.JarProfileImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getJarProfile()
		 * 
		 */
		EClass JAR_PROFILE = eINSTANCE.getJarProfile();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentTypeImpl <em>Application Component Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentTypeImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentType()
		 * 
		 */
		EClass APPLICATION_COMPONENT_TYPE = eINSTANCE.getApplicationComponentType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute APPLICATION_COMPONENT_TYPE__ID = eINSTANCE.getApplicationComponentType_Id();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ProviderTypeImpl <em>Provider Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ProviderTypeImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getProviderType()
		 * 
		 */
		EClass PROVIDER_TYPE = eINSTANCE.getProviderType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute PROVIDER_TYPE__ID = eINSTANCE.getProviderType_Id();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ProviderTypesImpl <em>Provider Types</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ProviderTypesImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getProviderTypes()
		 * 
		 */
		EClass PROVIDER_TYPES = eINSTANCE.getProviderTypes();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference PROVIDER_TYPES__TYPES = eINSTANCE.getProviderTypes_Types();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentTypesImpl <em>Application Component Types</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ApplicationComponentTypesImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentTypes()
		 * 
		 */
		EClass APPLICATION_COMPONENT_TYPES = eINSTANCE.getApplicationComponentTypes();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference APPLICATION_COMPONENT_TYPES__TYPES = eINSTANCE.getApplicationComponentTypes_Types();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ActionTypeImpl <em>Action Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ActionTypeImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getActionType()
		 * 
		 */
		EClass ACTION_TYPE = eINSTANCE.getActionType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute ACTION_TYPE__ID = eINSTANCE.getActionType_Id();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.ActionTypesImpl <em>Action Types</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.ActionTypesImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getActionTypes()
		 * 
		 */
		EClass ACTION_TYPES = eINSTANCE.getActionTypes();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference ACTION_TYPES__TYPES = eINSTANCE.getActionTypes_Types();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.FunctionTypeImpl <em>Function Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.FunctionTypeImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getFunctionType()
		 * 
		 */
		EClass FUNCTION_TYPE = eINSTANCE.getFunctionType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute FUNCTION_TYPE__ID = eINSTANCE.getFunctionType_Id();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.FunctionTypesImpl <em>Function Types</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.FunctionTypesImpl
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getFunctionTypes()
		 * 
		 */
		EClass FUNCTION_TYPES = eINSTANCE.getFunctionTypes();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference FUNCTION_TYPES__TYPES = eINSTANCE.getFunctionTypes_Types();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum <em>Frequency Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getFrequencyEnum()
		 * 
		 */
		EEnum FREQUENCY_ENUM = eINSTANCE.getFrequencyEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum <em>VM Size Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getVMSizeEnum()
		 * 
		 */
		EEnum VM_SIZE_ENUM = eINSTANCE.getVMSizeEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum <em>Logic Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getLogicOperatorEnum()
		 * 
		 */
		EEnum LOGIC_OPERATOR_ENUM = eINSTANCE.getLogicOperatorEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum <em>Data Unit Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getDataUnitEnum()
		 * 
		 */
		EEnum DATA_UNIT_ENUM = eINSTANCE.getDataUnitEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum <em>Variable Element Type Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getVariableElementTypeEnum()
		 * 
		 */
		EEnum VARIABLE_ELEMENT_TYPE_ENUM = eINSTANCE.getVariableElementTypeEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum <em>OS Architecture Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum
		 * @see eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl#getOSArchitectureEnum()
		 * 
		 */
		EEnum OS_ARCHITECTURE_ENUM = eINSTANCE.getOSArchitectureEnum();

	}

} //TypesPaasagePackage
