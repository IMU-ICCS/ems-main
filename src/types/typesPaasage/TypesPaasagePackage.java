/**
 */
package types.typesPaasage;

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
 * @see types.typesPaasage.TypesPaasageFactory
 * @model kind="package"
 * @generated
 */
public interface TypesPaasagePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "typesPaasage";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://paasage.inria.fr/metamodel/types/typesPaasage";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "typesPaasage";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TypesPaasagePackage eINSTANCE = types.typesPaasage.impl.TypesPaasagePackageImpl.init();

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.PaaSageCPElementImpl <em>Paa Sage CP Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.PaaSageCPElementImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getPaaSageCPElement()
	 * @generated
	 */
	int PAA_SAGE_CP_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_CP_ELEMENT__TYPE_ID = 0;

	/**
	 * The number of structural features of the '<em>Paa Sage CP Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_CP_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Paa Sage CP Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_CP_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.OSImpl <em>OS</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.OSImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getOS()
	 * @generated
	 */
	int OS = 1;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS__TYPE_ID = PAA_SAGE_CP_ELEMENT__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS__NAME = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS__VERS = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Architecture</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS__ARCHITECTURE = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>OS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS_FEATURE_COUNT = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>OS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS_OPERATION_COUNT = PAA_SAGE_CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.LocationImpl <em>Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.LocationImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getLocation()
	 * @generated
	 */
	int LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__TYPE_ID = PAA_SAGE_CP_ELEMENT__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__NAME = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Alternative Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__ALTERNATIVE_NAMES = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_FEATURE_COUNT = PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_OPERATION_COUNT = PAA_SAGE_CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.ContinentImpl <em>Continent</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.ContinentImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getContinent()
	 * @generated
	 */
	int CONTINENT = 3;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINENT__TYPE_ID = LOCATION__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINENT__NAME = LOCATION__NAME;

	/**
	 * The feature id for the '<em><b>Alternative Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINENT__ALTERNATIVE_NAMES = LOCATION__ALTERNATIVE_NAMES;

	/**
	 * The number of structural features of the '<em>Continent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINENT_FEATURE_COUNT = LOCATION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Continent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINENT_OPERATION_COUNT = LOCATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.CountryImpl <em>Country</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.CountryImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getCountry()
	 * @generated
	 */
	int COUNTRY = 4;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY__TYPE_ID = LOCATION__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY__NAME = LOCATION__NAME;

	/**
	 * The feature id for the '<em><b>Alternative Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY__ALTERNATIVE_NAMES = LOCATION__ALTERNATIVE_NAMES;

	/**
	 * The feature id for the '<em><b>Continent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY__CONTINENT = LOCATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Country</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_FEATURE_COUNT = LOCATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Country</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_OPERATION_COUNT = LOCATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.CityImpl <em>City</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.CityImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getCity()
	 * @generated
	 */
	int CITY = 5;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CITY__TYPE_ID = LOCATION__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CITY__NAME = LOCATION__NAME;

	/**
	 * The feature id for the '<em><b>Alternative Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CITY__ALTERNATIVE_NAMES = LOCATION__ALTERNATIVE_NAMES;

	/**
	 * The feature id for the '<em><b>Country</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CITY__COUNTRY = LOCATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>City</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CITY_FEATURE_COUNT = LOCATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>City</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CITY_OPERATION_COUNT = LOCATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.LocationsImpl <em>Locations</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.LocationsImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getLocations()
	 * @generated
	 */
	int LOCATIONS = 6;

	/**
	 * The feature id for the '<em><b>Locations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATIONS__LOCATIONS = 0;

	/**
	 * The number of structural features of the '<em>Locations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATIONS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Locations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATIONS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.ApplicationComponentProfileImpl <em>Application Component Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.ApplicationComponentProfileImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentProfile()
	 * @generated
	 */
	int APPLICATION_COMPONENT_PROFILE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_PROFILE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_PROFILE__VERS = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_PROFILE__TYPE = 2;

	/**
	 * The number of structural features of the '<em>Application Component Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Application Component Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.ApplicationComponentProfilesImpl <em>Application Component Profiles</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.ApplicationComponentProfilesImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentProfiles()
	 * @generated
	 */
	int APPLICATION_COMPONENT_PROFILES = 8;

	/**
	 * The feature id for the '<em><b>Profiles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_PROFILES__PROFILES = 0;

	/**
	 * The number of structural features of the '<em>Application Component Profiles</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_PROFILES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Application Component Profiles</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_PROFILES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.OperatingSystemsImpl <em>Operating Systems</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.OperatingSystemsImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getOperatingSystems()
	 * @generated
	 */
	int OPERATING_SYSTEMS = 9;

	/**
	 * The feature id for the '<em><b>Oss</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATING_SYSTEMS__OSS = 0;

	/**
	 * The number of structural features of the '<em>Operating Systems</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATING_SYSTEMS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Operating Systems</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATING_SYSTEMS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.DatabaseProfileImpl <em>Database Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.DatabaseProfileImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getDatabaseProfile()
	 * @generated
	 */
	int DATABASE_PROFILE = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATABASE_PROFILE__NAME = APPLICATION_COMPONENT_PROFILE__NAME;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATABASE_PROFILE__VERS = APPLICATION_COMPONENT_PROFILE__VERS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATABASE_PROFILE__TYPE = APPLICATION_COMPONENT_PROFILE__TYPE;

	/**
	 * The number of structural features of the '<em>Database Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATABASE_PROFILE_FEATURE_COUNT = APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Database Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATABASE_PROFILE_OPERATION_COUNT = APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.WebServerProfileImpl <em>Web Server Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.WebServerProfileImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getWebServerProfile()
	 * @generated
	 */
	int WEB_SERVER_PROFILE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_SERVER_PROFILE__NAME = APPLICATION_COMPONENT_PROFILE__NAME;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_SERVER_PROFILE__VERS = APPLICATION_COMPONENT_PROFILE__VERS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_SERVER_PROFILE__TYPE = APPLICATION_COMPONENT_PROFILE__TYPE;

	/**
	 * The number of structural features of the '<em>Web Server Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_SERVER_PROFILE_FEATURE_COUNT = APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Web Server Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_SERVER_PROFILE_OPERATION_COUNT = APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.ApplicationServerProfileImpl <em>Application Server Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.ApplicationServerProfileImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationServerProfile()
	 * @generated
	 */
	int APPLICATION_SERVER_PROFILE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_SERVER_PROFILE__NAME = APPLICATION_COMPONENT_PROFILE__NAME;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_SERVER_PROFILE__VERS = APPLICATION_COMPONENT_PROFILE__VERS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_SERVER_PROFILE__TYPE = APPLICATION_COMPONENT_PROFILE__TYPE;

	/**
	 * The number of structural features of the '<em>Application Server Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_SERVER_PROFILE_FEATURE_COUNT = APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Application Server Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_SERVER_PROFILE_OPERATION_COUNT = APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.WarProfileImpl <em>War Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.WarProfileImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getWarProfile()
	 * @generated
	 */
	int WAR_PROFILE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WAR_PROFILE__NAME = APPLICATION_COMPONENT_PROFILE__NAME;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WAR_PROFILE__VERS = APPLICATION_COMPONENT_PROFILE__VERS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WAR_PROFILE__TYPE = APPLICATION_COMPONENT_PROFILE__TYPE;

	/**
	 * The number of structural features of the '<em>War Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WAR_PROFILE_FEATURE_COUNT = APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>War Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WAR_PROFILE_OPERATION_COUNT = APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.JarProfileImpl <em>Jar Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.JarProfileImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getJarProfile()
	 * @generated
	 */
	int JAR_PROFILE = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAR_PROFILE__NAME = APPLICATION_COMPONENT_PROFILE__NAME;

	/**
	 * The feature id for the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAR_PROFILE__VERS = APPLICATION_COMPONENT_PROFILE__VERS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAR_PROFILE__TYPE = APPLICATION_COMPONENT_PROFILE__TYPE;

	/**
	 * The number of structural features of the '<em>Jar Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAR_PROFILE_FEATURE_COUNT = APPLICATION_COMPONENT_PROFILE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Jar Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAR_PROFILE_OPERATION_COUNT = APPLICATION_COMPONENT_PROFILE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.ApplicationComponentTypeImpl <em>Application Component Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.ApplicationComponentTypeImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentType()
	 * @generated
	 */
	int APPLICATION_COMPONENT_TYPE = 15;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_TYPE__ID = 0;

	/**
	 * The number of structural features of the '<em>Application Component Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Application Component Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.ProviderTypeImpl <em>Provider Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.ProviderTypeImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getProviderType()
	 * @generated
	 */
	int PROVIDER_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_TYPE__ID = 0;

	/**
	 * The number of structural features of the '<em>Provider Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Provider Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.ProviderTypesImpl <em>Provider Types</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.ProviderTypesImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getProviderTypes()
	 * @generated
	 */
	int PROVIDER_TYPES = 17;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_TYPES__TYPES = 0;

	/**
	 * The number of structural features of the '<em>Provider Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_TYPES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Provider Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_TYPES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.ApplicationComponentTypesImpl <em>Application Component Types</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.ApplicationComponentTypesImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentTypes()
	 * @generated
	 */
	int APPLICATION_COMPONENT_TYPES = 18;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_TYPES__TYPES = 0;

	/**
	 * The number of structural features of the '<em>Application Component Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_TYPES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Application Component Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_TYPES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.ActionTypeImpl <em>Action Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.ActionTypeImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getActionType()
	 * @generated
	 */
	int ACTION_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_TYPE__ID = 0;

	/**
	 * The number of structural features of the '<em>Action Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Action Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.ActionTypesImpl <em>Action Types</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.ActionTypesImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getActionTypes()
	 * @generated
	 */
	int ACTION_TYPES = 20;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_TYPES__TYPES = 0;

	/**
	 * The number of structural features of the '<em>Action Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_TYPES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Action Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_TYPES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.FunctionTypeImpl <em>Function Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.FunctionTypeImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getFunctionType()
	 * @generated
	 */
	int FUNCTION_TYPE = 21;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE__ID = 0;

	/**
	 * The number of structural features of the '<em>Function Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Function Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.impl.FunctionTypesImpl <em>Function Types</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.impl.FunctionTypesImpl
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getFunctionTypes()
	 * @generated
	 */
	int FUNCTION_TYPES = 22;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPES__TYPES = 0;

	/**
	 * The number of structural features of the '<em>Function Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Function Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link types.typesPaasage.FrequencyEnum <em>Frequency Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.FrequencyEnum
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getFrequencyEnum()
	 * @generated
	 */
	int FREQUENCY_ENUM = 23;

	/**
	 * The meta object id for the '{@link types.typesPaasage.VMSizeEnum <em>VM Size Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.VMSizeEnum
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getVMSizeEnum()
	 * @generated
	 */
	int VM_SIZE_ENUM = 24;

	/**
	 * The meta object id for the '{@link types.typesPaasage.LogicOperatorEnum <em>Logic Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.LogicOperatorEnum
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getLogicOperatorEnum()
	 * @generated
	 */
	int LOGIC_OPERATOR_ENUM = 25;

	/**
	 * The meta object id for the '{@link types.typesPaasage.DataUnitEnum <em>Data Unit Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.DataUnitEnum
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getDataUnitEnum()
	 * @generated
	 */
	int DATA_UNIT_ENUM = 26;

	/**
	 * The meta object id for the '{@link types.typesPaasage.VariableElementTypeEnum <em>Variable Element Type Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.VariableElementTypeEnum
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getVariableElementTypeEnum()
	 * @generated
	 */
	int VARIABLE_ELEMENT_TYPE_ENUM = 27;

	/**
	 * The meta object id for the '{@link types.typesPaasage.OSArchitectureEnum <em>OS Architecture Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see types.typesPaasage.OSArchitectureEnum
	 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getOSArchitectureEnum()
	 * @generated
	 */
	int OS_ARCHITECTURE_ENUM = 28;


	/**
	 * Returns the meta object for class '{@link types.typesPaasage.PaaSageCPElement <em>Paa Sage CP Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Paa Sage CP Element</em>'.
	 * @see types.typesPaasage.PaaSageCPElement
	 * @generated
	 */
	EClass getPaaSageCPElement();

	/**
	 * Returns the meta object for the attribute '{@link types.typesPaasage.PaaSageCPElement#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see types.typesPaasage.PaaSageCPElement#getTypeId()
	 * @see #getPaaSageCPElement()
	 * @generated
	 */
	EAttribute getPaaSageCPElement_TypeId();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.OS <em>OS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>OS</em>'.
	 * @see types.typesPaasage.OS
	 * @generated
	 */
	EClass getOS();

	/**
	 * Returns the meta object for the attribute '{@link types.typesPaasage.OS#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see types.typesPaasage.OS#getName()
	 * @see #getOS()
	 * @generated
	 */
	EAttribute getOS_Name();

	/**
	 * Returns the meta object for the attribute '{@link types.typesPaasage.OS#getVers <em>Vers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vers</em>'.
	 * @see types.typesPaasage.OS#getVers()
	 * @see #getOS()
	 * @generated
	 */
	EAttribute getOS_Vers();

	/**
	 * Returns the meta object for the attribute '{@link types.typesPaasage.OS#getArchitecture <em>Architecture</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Architecture</em>'.
	 * @see types.typesPaasage.OS#getArchitecture()
	 * @see #getOS()
	 * @generated
	 */
	EAttribute getOS_Architecture();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Location</em>'.
	 * @see types.typesPaasage.Location
	 * @generated
	 */
	EClass getLocation();

	/**
	 * Returns the meta object for the attribute '{@link types.typesPaasage.Location#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see types.typesPaasage.Location#getName()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Name();

	/**
	 * Returns the meta object for the attribute list '{@link types.typesPaasage.Location#getAlternativeNames <em>Alternative Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Alternative Names</em>'.
	 * @see types.typesPaasage.Location#getAlternativeNames()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_AlternativeNames();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.Continent <em>Continent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Continent</em>'.
	 * @see types.typesPaasage.Continent
	 * @generated
	 */
	EClass getContinent();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.Country <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Country</em>'.
	 * @see types.typesPaasage.Country
	 * @generated
	 */
	EClass getCountry();

	/**
	 * Returns the meta object for the reference '{@link types.typesPaasage.Country#getContinent <em>Continent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Continent</em>'.
	 * @see types.typesPaasage.Country#getContinent()
	 * @see #getCountry()
	 * @generated
	 */
	EReference getCountry_Continent();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.City <em>City</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>City</em>'.
	 * @see types.typesPaasage.City
	 * @generated
	 */
	EClass getCity();

	/**
	 * Returns the meta object for the reference '{@link types.typesPaasage.City#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Country</em>'.
	 * @see types.typesPaasage.City#getCountry()
	 * @see #getCity()
	 * @generated
	 */
	EReference getCity_Country();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.Locations <em>Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Locations</em>'.
	 * @see types.typesPaasage.Locations
	 * @generated
	 */
	EClass getLocations();

	/**
	 * Returns the meta object for the containment reference list '{@link types.typesPaasage.Locations#getLocations <em>Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Locations</em>'.
	 * @see types.typesPaasage.Locations#getLocations()
	 * @see #getLocations()
	 * @generated
	 */
	EReference getLocations_Locations();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.ApplicationComponentProfile <em>Application Component Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Component Profile</em>'.
	 * @see types.typesPaasage.ApplicationComponentProfile
	 * @generated
	 */
	EClass getApplicationComponentProfile();

	/**
	 * Returns the meta object for the attribute '{@link types.typesPaasage.ApplicationComponentProfile#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see types.typesPaasage.ApplicationComponentProfile#getName()
	 * @see #getApplicationComponentProfile()
	 * @generated
	 */
	EAttribute getApplicationComponentProfile_Name();

	/**
	 * Returns the meta object for the attribute '{@link types.typesPaasage.ApplicationComponentProfile#getVers <em>Vers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vers</em>'.
	 * @see types.typesPaasage.ApplicationComponentProfile#getVers()
	 * @see #getApplicationComponentProfile()
	 * @generated
	 */
	EAttribute getApplicationComponentProfile_Vers();

	/**
	 * Returns the meta object for the reference '{@link types.typesPaasage.ApplicationComponentProfile#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see types.typesPaasage.ApplicationComponentProfile#getType()
	 * @see #getApplicationComponentProfile()
	 * @generated
	 */
	EReference getApplicationComponentProfile_Type();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.ApplicationComponentProfiles <em>Application Component Profiles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Component Profiles</em>'.
	 * @see types.typesPaasage.ApplicationComponentProfiles
	 * @generated
	 */
	EClass getApplicationComponentProfiles();

	/**
	 * Returns the meta object for the containment reference list '{@link types.typesPaasage.ApplicationComponentProfiles#getProfiles <em>Profiles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Profiles</em>'.
	 * @see types.typesPaasage.ApplicationComponentProfiles#getProfiles()
	 * @see #getApplicationComponentProfiles()
	 * @generated
	 */
	EReference getApplicationComponentProfiles_Profiles();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.OperatingSystems <em>Operating Systems</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operating Systems</em>'.
	 * @see types.typesPaasage.OperatingSystems
	 * @generated
	 */
	EClass getOperatingSystems();

	/**
	 * Returns the meta object for the containment reference list '{@link types.typesPaasage.OperatingSystems#getOss <em>Oss</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Oss</em>'.
	 * @see types.typesPaasage.OperatingSystems#getOss()
	 * @see #getOperatingSystems()
	 * @generated
	 */
	EReference getOperatingSystems_Oss();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.DatabaseProfile <em>Database Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Database Profile</em>'.
	 * @see types.typesPaasage.DatabaseProfile
	 * @generated
	 */
	EClass getDatabaseProfile();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.WebServerProfile <em>Web Server Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Web Server Profile</em>'.
	 * @see types.typesPaasage.WebServerProfile
	 * @generated
	 */
	EClass getWebServerProfile();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.ApplicationServerProfile <em>Application Server Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Server Profile</em>'.
	 * @see types.typesPaasage.ApplicationServerProfile
	 * @generated
	 */
	EClass getApplicationServerProfile();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.WarProfile <em>War Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>War Profile</em>'.
	 * @see types.typesPaasage.WarProfile
	 * @generated
	 */
	EClass getWarProfile();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.JarProfile <em>Jar Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Jar Profile</em>'.
	 * @see types.typesPaasage.JarProfile
	 * @generated
	 */
	EClass getJarProfile();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.ApplicationComponentType <em>Application Component Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Component Type</em>'.
	 * @see types.typesPaasage.ApplicationComponentType
	 * @generated
	 */
	EClass getApplicationComponentType();

	/**
	 * Returns the meta object for the attribute '{@link types.typesPaasage.ApplicationComponentType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see types.typesPaasage.ApplicationComponentType#getId()
	 * @see #getApplicationComponentType()
	 * @generated
	 */
	EAttribute getApplicationComponentType_Id();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.ProviderType <em>Provider Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provider Type</em>'.
	 * @see types.typesPaasage.ProviderType
	 * @generated
	 */
	EClass getProviderType();

	/**
	 * Returns the meta object for the attribute '{@link types.typesPaasage.ProviderType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see types.typesPaasage.ProviderType#getId()
	 * @see #getProviderType()
	 * @generated
	 */
	EAttribute getProviderType_Id();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.ProviderTypes <em>Provider Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provider Types</em>'.
	 * @see types.typesPaasage.ProviderTypes
	 * @generated
	 */
	EClass getProviderTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link types.typesPaasage.ProviderTypes#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see types.typesPaasage.ProviderTypes#getTypes()
	 * @see #getProviderTypes()
	 * @generated
	 */
	EReference getProviderTypes_Types();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.ApplicationComponentTypes <em>Application Component Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Component Types</em>'.
	 * @see types.typesPaasage.ApplicationComponentTypes
	 * @generated
	 */
	EClass getApplicationComponentTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link types.typesPaasage.ApplicationComponentTypes#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see types.typesPaasage.ApplicationComponentTypes#getTypes()
	 * @see #getApplicationComponentTypes()
	 * @generated
	 */
	EReference getApplicationComponentTypes_Types();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.ActionType <em>Action Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Type</em>'.
	 * @see types.typesPaasage.ActionType
	 * @generated
	 */
	EClass getActionType();

	/**
	 * Returns the meta object for the attribute '{@link types.typesPaasage.ActionType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see types.typesPaasage.ActionType#getId()
	 * @see #getActionType()
	 * @generated
	 */
	EAttribute getActionType_Id();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.ActionTypes <em>Action Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Types</em>'.
	 * @see types.typesPaasage.ActionTypes
	 * @generated
	 */
	EClass getActionTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link types.typesPaasage.ActionTypes#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see types.typesPaasage.ActionTypes#getTypes()
	 * @see #getActionTypes()
	 * @generated
	 */
	EReference getActionTypes_Types();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.FunctionType <em>Function Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Type</em>'.
	 * @see types.typesPaasage.FunctionType
	 * @generated
	 */
	EClass getFunctionType();

	/**
	 * Returns the meta object for the attribute '{@link types.typesPaasage.FunctionType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see types.typesPaasage.FunctionType#getId()
	 * @see #getFunctionType()
	 * @generated
	 */
	EAttribute getFunctionType_Id();

	/**
	 * Returns the meta object for class '{@link types.typesPaasage.FunctionTypes <em>Function Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Types</em>'.
	 * @see types.typesPaasage.FunctionTypes
	 * @generated
	 */
	EClass getFunctionTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link types.typesPaasage.FunctionTypes#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see types.typesPaasage.FunctionTypes#getTypes()
	 * @see #getFunctionTypes()
	 * @generated
	 */
	EReference getFunctionTypes_Types();

	/**
	 * Returns the meta object for enum '{@link types.typesPaasage.FrequencyEnum <em>Frequency Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Frequency Enum</em>'.
	 * @see types.typesPaasage.FrequencyEnum
	 * @generated
	 */
	EEnum getFrequencyEnum();

	/**
	 * Returns the meta object for enum '{@link types.typesPaasage.VMSizeEnum <em>VM Size Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>VM Size Enum</em>'.
	 * @see types.typesPaasage.VMSizeEnum
	 * @generated
	 */
	EEnum getVMSizeEnum();

	/**
	 * Returns the meta object for enum '{@link types.typesPaasage.LogicOperatorEnum <em>Logic Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Logic Operator Enum</em>'.
	 * @see types.typesPaasage.LogicOperatorEnum
	 * @generated
	 */
	EEnum getLogicOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link types.typesPaasage.DataUnitEnum <em>Data Unit Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Unit Enum</em>'.
	 * @see types.typesPaasage.DataUnitEnum
	 * @generated
	 */
	EEnum getDataUnitEnum();

	/**
	 * Returns the meta object for enum '{@link types.typesPaasage.VariableElementTypeEnum <em>Variable Element Type Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Variable Element Type Enum</em>'.
	 * @see types.typesPaasage.VariableElementTypeEnum
	 * @generated
	 */
	EEnum getVariableElementTypeEnum();

	/**
	 * Returns the meta object for enum '{@link types.typesPaasage.OSArchitectureEnum <em>OS Architecture Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>OS Architecture Enum</em>'.
	 * @see types.typesPaasage.OSArchitectureEnum
	 * @generated
	 */
	EEnum getOSArchitectureEnum();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
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
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.PaaSageCPElementImpl <em>Paa Sage CP Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.PaaSageCPElementImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getPaaSageCPElement()
		 * @generated
		 */
		EClass PAA_SAGE_CP_ELEMENT = eINSTANCE.getPaaSageCPElement();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAA_SAGE_CP_ELEMENT__TYPE_ID = eINSTANCE.getPaaSageCPElement_TypeId();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.OSImpl <em>OS</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.OSImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getOS()
		 * @generated
		 */
		EClass OS = eINSTANCE.getOS();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OS__NAME = eINSTANCE.getOS_Name();

		/**
		 * The meta object literal for the '<em><b>Vers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OS__VERS = eINSTANCE.getOS_Vers();

		/**
		 * The meta object literal for the '<em><b>Architecture</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OS__ARCHITECTURE = eINSTANCE.getOS_Architecture();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.LocationImpl <em>Location</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.LocationImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getLocation()
		 * @generated
		 */
		EClass LOCATION = eINSTANCE.getLocation();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__NAME = eINSTANCE.getLocation_Name();

		/**
		 * The meta object literal for the '<em><b>Alternative Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__ALTERNATIVE_NAMES = eINSTANCE.getLocation_AlternativeNames();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.ContinentImpl <em>Continent</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.ContinentImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getContinent()
		 * @generated
		 */
		EClass CONTINENT = eINSTANCE.getContinent();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.CountryImpl <em>Country</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.CountryImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getCountry()
		 * @generated
		 */
		EClass COUNTRY = eINSTANCE.getCountry();

		/**
		 * The meta object literal for the '<em><b>Continent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUNTRY__CONTINENT = eINSTANCE.getCountry_Continent();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.CityImpl <em>City</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.CityImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getCity()
		 * @generated
		 */
		EClass CITY = eINSTANCE.getCity();

		/**
		 * The meta object literal for the '<em><b>Country</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CITY__COUNTRY = eINSTANCE.getCity_Country();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.LocationsImpl <em>Locations</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.LocationsImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getLocations()
		 * @generated
		 */
		EClass LOCATIONS = eINSTANCE.getLocations();

		/**
		 * The meta object literal for the '<em><b>Locations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOCATIONS__LOCATIONS = eINSTANCE.getLocations_Locations();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.ApplicationComponentProfileImpl <em>Application Component Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.ApplicationComponentProfileImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentProfile()
		 * @generated
		 */
		EClass APPLICATION_COMPONENT_PROFILE = eINSTANCE.getApplicationComponentProfile();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLICATION_COMPONENT_PROFILE__NAME = eINSTANCE.getApplicationComponentProfile_Name();

		/**
		 * The meta object literal for the '<em><b>Vers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLICATION_COMPONENT_PROFILE__VERS = eINSTANCE.getApplicationComponentProfile_Vers();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_COMPONENT_PROFILE__TYPE = eINSTANCE.getApplicationComponentProfile_Type();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.ApplicationComponentProfilesImpl <em>Application Component Profiles</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.ApplicationComponentProfilesImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentProfiles()
		 * @generated
		 */
		EClass APPLICATION_COMPONENT_PROFILES = eINSTANCE.getApplicationComponentProfiles();

		/**
		 * The meta object literal for the '<em><b>Profiles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_COMPONENT_PROFILES__PROFILES = eINSTANCE.getApplicationComponentProfiles_Profiles();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.OperatingSystemsImpl <em>Operating Systems</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.OperatingSystemsImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getOperatingSystems()
		 * @generated
		 */
		EClass OPERATING_SYSTEMS = eINSTANCE.getOperatingSystems();

		/**
		 * The meta object literal for the '<em><b>Oss</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATING_SYSTEMS__OSS = eINSTANCE.getOperatingSystems_Oss();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.DatabaseProfileImpl <em>Database Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.DatabaseProfileImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getDatabaseProfile()
		 * @generated
		 */
		EClass DATABASE_PROFILE = eINSTANCE.getDatabaseProfile();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.WebServerProfileImpl <em>Web Server Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.WebServerProfileImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getWebServerProfile()
		 * @generated
		 */
		EClass WEB_SERVER_PROFILE = eINSTANCE.getWebServerProfile();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.ApplicationServerProfileImpl <em>Application Server Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.ApplicationServerProfileImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationServerProfile()
		 * @generated
		 */
		EClass APPLICATION_SERVER_PROFILE = eINSTANCE.getApplicationServerProfile();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.WarProfileImpl <em>War Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.WarProfileImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getWarProfile()
		 * @generated
		 */
		EClass WAR_PROFILE = eINSTANCE.getWarProfile();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.JarProfileImpl <em>Jar Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.JarProfileImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getJarProfile()
		 * @generated
		 */
		EClass JAR_PROFILE = eINSTANCE.getJarProfile();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.ApplicationComponentTypeImpl <em>Application Component Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.ApplicationComponentTypeImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentType()
		 * @generated
		 */
		EClass APPLICATION_COMPONENT_TYPE = eINSTANCE.getApplicationComponentType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLICATION_COMPONENT_TYPE__ID = eINSTANCE.getApplicationComponentType_Id();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.ProviderTypeImpl <em>Provider Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.ProviderTypeImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getProviderType()
		 * @generated
		 */
		EClass PROVIDER_TYPE = eINSTANCE.getProviderType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROVIDER_TYPE__ID = eINSTANCE.getProviderType_Id();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.ProviderTypesImpl <em>Provider Types</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.ProviderTypesImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getProviderTypes()
		 * @generated
		 */
		EClass PROVIDER_TYPES = eINSTANCE.getProviderTypes();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDER_TYPES__TYPES = eINSTANCE.getProviderTypes_Types();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.ApplicationComponentTypesImpl <em>Application Component Types</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.ApplicationComponentTypesImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getApplicationComponentTypes()
		 * @generated
		 */
		EClass APPLICATION_COMPONENT_TYPES = eINSTANCE.getApplicationComponentTypes();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_COMPONENT_TYPES__TYPES = eINSTANCE.getApplicationComponentTypes_Types();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.ActionTypeImpl <em>Action Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.ActionTypeImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getActionType()
		 * @generated
		 */
		EClass ACTION_TYPE = eINSTANCE.getActionType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_TYPE__ID = eINSTANCE.getActionType_Id();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.ActionTypesImpl <em>Action Types</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.ActionTypesImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getActionTypes()
		 * @generated
		 */
		EClass ACTION_TYPES = eINSTANCE.getActionTypes();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_TYPES__TYPES = eINSTANCE.getActionTypes_Types();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.FunctionTypeImpl <em>Function Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.FunctionTypeImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getFunctionType()
		 * @generated
		 */
		EClass FUNCTION_TYPE = eINSTANCE.getFunctionType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION_TYPE__ID = eINSTANCE.getFunctionType_Id();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.impl.FunctionTypesImpl <em>Function Types</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.impl.FunctionTypesImpl
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getFunctionTypes()
		 * @generated
		 */
		EClass FUNCTION_TYPES = eINSTANCE.getFunctionTypes();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TYPES__TYPES = eINSTANCE.getFunctionTypes_Types();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.FrequencyEnum <em>Frequency Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.FrequencyEnum
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getFrequencyEnum()
		 * @generated
		 */
		EEnum FREQUENCY_ENUM = eINSTANCE.getFrequencyEnum();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.VMSizeEnum <em>VM Size Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.VMSizeEnum
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getVMSizeEnum()
		 * @generated
		 */
		EEnum VM_SIZE_ENUM = eINSTANCE.getVMSizeEnum();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.LogicOperatorEnum <em>Logic Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.LogicOperatorEnum
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getLogicOperatorEnum()
		 * @generated
		 */
		EEnum LOGIC_OPERATOR_ENUM = eINSTANCE.getLogicOperatorEnum();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.DataUnitEnum <em>Data Unit Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.DataUnitEnum
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getDataUnitEnum()
		 * @generated
		 */
		EEnum DATA_UNIT_ENUM = eINSTANCE.getDataUnitEnum();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.VariableElementTypeEnum <em>Variable Element Type Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.VariableElementTypeEnum
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getVariableElementTypeEnum()
		 * @generated
		 */
		EEnum VARIABLE_ELEMENT_TYPE_ENUM = eINSTANCE.getVariableElementTypeEnum();

		/**
		 * The meta object literal for the '{@link types.typesPaasage.OSArchitectureEnum <em>OS Architecture Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see types.typesPaasage.OSArchitectureEnum
		 * @see types.typesPaasage.impl.TypesPaasagePackageImpl#getOSArchitectureEnum()
		 * @generated
		 */
		EEnum OS_ARCHITECTURE_ENUM = eINSTANCE.getOSArchitectureEnum();

	}

} //TypesPaasagePackage
