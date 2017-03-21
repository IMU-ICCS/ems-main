/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.types.typesPaasage.impl;

import eu.paasage.upperware.metamodel.types.TypesPackage;

import eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl;

import eu.paasage.upperware.metamodel.types.typesPaasage.ActionType;
import eu.paasage.upperware.metamodel.types.typesPaasage.ActionTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile;
import eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfiles;
import eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentType;
import eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationServerProfile;
import eu.paasage.upperware.metamodel.types.typesPaasage.CityUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.ContinentUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum;
import eu.paasage.upperware.metamodel.types.typesPaasage.DatabaseProfile;
import eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum;
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType;
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.JarProfile;
import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.Locations;
import eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum;
import eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum;
import eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems;
import eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasageFactory;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum;
import eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum;
import eu.paasage.upperware.metamodel.types.typesPaasage.WarProfile;
import eu.paasage.upperware.metamodel.types.typesPaasage.WebServerProfile;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 */
public class TypesPaasagePackageImpl extends EPackageImpl implements TypesPaasagePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass paaSageCPElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass osEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass locationUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass continentUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass countryUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass cityUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass locationsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass applicationComponentProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass applicationComponentProfilesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass operatingSystemsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass databaseProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass webServerProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass applicationServerProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass warProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass jarProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass applicationComponentTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass providerTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass providerTypesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass applicationComponentTypesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass actionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass actionTypesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass functionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EClass functionTypesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EEnum frequencyEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EEnum vmSizeEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EEnum logicOperatorEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EEnum dataUnitEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EEnum variableElementTypeEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private EEnum osArchitectureEnumEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#eNS_URI
	 * @see #init()
	 * 
	 */
	private TypesPaasagePackageImpl() {
		super(eNS_URI, TypesPaasageFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link TypesPaasagePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * 
	 */
	public static TypesPaasagePackage init() {
		if (isInited) return (TypesPaasagePackage)EPackage.Registry.INSTANCE.getEPackage(TypesPaasagePackage.eNS_URI);

		// Obtain or create and register package
		TypesPaasagePackageImpl theTypesPaasagePackage = (TypesPaasagePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TypesPaasagePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TypesPaasagePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		TypesPackageImpl theTypesPackage = (TypesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI) instanceof TypesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI) : TypesPackage.eINSTANCE);

		// Create package meta-data objects
		theTypesPaasagePackage.createPackageContents();
		theTypesPackage.createPackageContents();

		// Initialize created meta-data
		theTypesPaasagePackage.initializePackageContents();
		theTypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTypesPaasagePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TypesPaasagePackage.eNS_URI, theTypesPaasagePackage);
		return theTypesPaasagePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getPaaSageCPElement() {
		return paaSageCPElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getPaaSageCPElement_TypeId() {
		return (EAttribute)paaSageCPElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getOS() {
		return osEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getOS_Name() {
		return (EAttribute)osEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getOS_Vers() {
		return (EAttribute)osEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getOS_Architecture() {
		return (EAttribute)osEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getLocationUpperware() {
		return locationUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getLocationUpperware_Name() {
		return (EAttribute)locationUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getLocationUpperware_AlternativeNames() {
		return (EAttribute)locationUpperwareEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getContinentUpperware() {
		return continentUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getCountryUpperware() {
		return countryUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EReference getCountryUpperware_Continent() {
		return (EReference)countryUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getCityUpperware() {
		return cityUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EReference getCityUpperware_Country() {
		return (EReference)cityUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getLocations() {
		return locationsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EReference getLocations_Locations() {
		return (EReference)locationsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getApplicationComponentProfile() {
		return applicationComponentProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getApplicationComponentProfile_Name() {
		return (EAttribute)applicationComponentProfileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getApplicationComponentProfile_Vers() {
		return (EAttribute)applicationComponentProfileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EReference getApplicationComponentProfile_Type() {
		return (EReference)applicationComponentProfileEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getApplicationComponentProfiles() {
		return applicationComponentProfilesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EReference getApplicationComponentProfiles_Profiles() {
		return (EReference)applicationComponentProfilesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getOperatingSystems() {
		return operatingSystemsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EReference getOperatingSystems_Oss() {
		return (EReference)operatingSystemsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getDatabaseProfile() {
		return databaseProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getWebServerProfile() {
		return webServerProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getApplicationServerProfile() {
		return applicationServerProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getWarProfile() {
		return warProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getJarProfile() {
		return jarProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getApplicationComponentType() {
		return applicationComponentTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getApplicationComponentType_Id() {
		return (EAttribute)applicationComponentTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getProviderType() {
		return providerTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getProviderType_Id() {
		return (EAttribute)providerTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getProviderTypes() {
		return providerTypesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EReference getProviderTypes_Types() {
		return (EReference)providerTypesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getApplicationComponentTypes() {
		return applicationComponentTypesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EReference getApplicationComponentTypes_Types() {
		return (EReference)applicationComponentTypesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getActionType() {
		return actionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getActionType_Id() {
		return (EAttribute)actionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getActionTypes() {
		return actionTypesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EReference getActionTypes_Types() {
		return (EReference)actionTypesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getFunctionType() {
		return functionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EAttribute getFunctionType_Id() {
		return (EAttribute)functionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EClass getFunctionTypes() {
		return functionTypesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EReference getFunctionTypes_Types() {
		return (EReference)functionTypesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EEnum getFrequencyEnum() {
		return frequencyEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EEnum getVMSizeEnum() {
		return vmSizeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EEnum getLogicOperatorEnum() {
		return logicOperatorEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EEnum getDataUnitEnum() {
		return dataUnitEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EEnum getVariableElementTypeEnum() {
		return variableElementTypeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public EEnum getOSArchitectureEnum() {
		return osArchitectureEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public TypesPaasageFactory getTypesPaasageFactory() {
		return (TypesPaasageFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		paaSageCPElementEClass = createEClass(PAA_SAGE_CP_ELEMENT);
		createEAttribute(paaSageCPElementEClass, PAA_SAGE_CP_ELEMENT__TYPE_ID);

		osEClass = createEClass(OS);
		createEAttribute(osEClass, OS__NAME);
		createEAttribute(osEClass, OS__VERS);
		createEAttribute(osEClass, OS__ARCHITECTURE);

		locationUpperwareEClass = createEClass(LOCATION_UPPERWARE);
		createEAttribute(locationUpperwareEClass, LOCATION_UPPERWARE__NAME);
		createEAttribute(locationUpperwareEClass, LOCATION_UPPERWARE__ALTERNATIVE_NAMES);

		continentUpperwareEClass = createEClass(CONTINENT_UPPERWARE);

		countryUpperwareEClass = createEClass(COUNTRY_UPPERWARE);
		createEReference(countryUpperwareEClass, COUNTRY_UPPERWARE__CONTINENT);

		cityUpperwareEClass = createEClass(CITY_UPPERWARE);
		createEReference(cityUpperwareEClass, CITY_UPPERWARE__COUNTRY);

		locationsEClass = createEClass(LOCATIONS);
		createEReference(locationsEClass, LOCATIONS__LOCATIONS);

		applicationComponentProfileEClass = createEClass(APPLICATION_COMPONENT_PROFILE);
		createEAttribute(applicationComponentProfileEClass, APPLICATION_COMPONENT_PROFILE__NAME);
		createEAttribute(applicationComponentProfileEClass, APPLICATION_COMPONENT_PROFILE__VERS);
		createEReference(applicationComponentProfileEClass, APPLICATION_COMPONENT_PROFILE__TYPE);

		applicationComponentProfilesEClass = createEClass(APPLICATION_COMPONENT_PROFILES);
		createEReference(applicationComponentProfilesEClass, APPLICATION_COMPONENT_PROFILES__PROFILES);

		operatingSystemsEClass = createEClass(OPERATING_SYSTEMS);
		createEReference(operatingSystemsEClass, OPERATING_SYSTEMS__OSS);

		databaseProfileEClass = createEClass(DATABASE_PROFILE);

		webServerProfileEClass = createEClass(WEB_SERVER_PROFILE);

		applicationServerProfileEClass = createEClass(APPLICATION_SERVER_PROFILE);

		warProfileEClass = createEClass(WAR_PROFILE);

		jarProfileEClass = createEClass(JAR_PROFILE);

		applicationComponentTypeEClass = createEClass(APPLICATION_COMPONENT_TYPE);
		createEAttribute(applicationComponentTypeEClass, APPLICATION_COMPONENT_TYPE__ID);

		providerTypeEClass = createEClass(PROVIDER_TYPE);
		createEAttribute(providerTypeEClass, PROVIDER_TYPE__ID);

		providerTypesEClass = createEClass(PROVIDER_TYPES);
		createEReference(providerTypesEClass, PROVIDER_TYPES__TYPES);

		applicationComponentTypesEClass = createEClass(APPLICATION_COMPONENT_TYPES);
		createEReference(applicationComponentTypesEClass, APPLICATION_COMPONENT_TYPES__TYPES);

		actionTypeEClass = createEClass(ACTION_TYPE);
		createEAttribute(actionTypeEClass, ACTION_TYPE__ID);

		actionTypesEClass = createEClass(ACTION_TYPES);
		createEReference(actionTypesEClass, ACTION_TYPES__TYPES);

		functionTypeEClass = createEClass(FUNCTION_TYPE);
		createEAttribute(functionTypeEClass, FUNCTION_TYPE__ID);

		functionTypesEClass = createEClass(FUNCTION_TYPES);
		createEReference(functionTypesEClass, FUNCTION_TYPES__TYPES);

		// Create enums
		frequencyEnumEEnum = createEEnum(FREQUENCY_ENUM);
		vmSizeEnumEEnum = createEEnum(VM_SIZE_ENUM);
		logicOperatorEnumEEnum = createEEnum(LOGIC_OPERATOR_ENUM);
		dataUnitEnumEEnum = createEEnum(DATA_UNIT_ENUM);
		variableElementTypeEnumEEnum = createEEnum(VARIABLE_ELEMENT_TYPE_ENUM);
		osArchitectureEnumEEnum = createEEnum(OS_ARCHITECTURE_ENUM);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		osEClass.getESuperTypes().add(this.getPaaSageCPElement());
		locationUpperwareEClass.getESuperTypes().add(this.getPaaSageCPElement());
		continentUpperwareEClass.getESuperTypes().add(this.getLocationUpperware());
		countryUpperwareEClass.getESuperTypes().add(this.getLocationUpperware());
		cityUpperwareEClass.getESuperTypes().add(this.getLocationUpperware());
		databaseProfileEClass.getESuperTypes().add(this.getApplicationComponentProfile());
		webServerProfileEClass.getESuperTypes().add(this.getApplicationComponentProfile());
		applicationServerProfileEClass.getESuperTypes().add(this.getApplicationComponentProfile());
		warProfileEClass.getESuperTypes().add(this.getApplicationComponentProfile());
		jarProfileEClass.getESuperTypes().add(this.getApplicationComponentProfile());

		// Initialize classes, features, and operations; add parameters
		initEClass(paaSageCPElementEClass, PaaSageCPElement.class, "PaaSageCPElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPaaSageCPElement_TypeId(), ecorePackage.getELong(), "typeId", null, 1, 1, PaaSageCPElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(osEClass, eu.paasage.upperware.metamodel.types.typesPaasage.OS.class, "OS", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOS_Name(), ecorePackage.getEString(), "name", null, 1, 1, eu.paasage.upperware.metamodel.types.typesPaasage.OS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOS_Vers(), ecorePackage.getEString(), "vers", null, 0, 1, eu.paasage.upperware.metamodel.types.typesPaasage.OS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOS_Architecture(), this.getOSArchitectureEnum(), "architecture", null, 1, 1, eu.paasage.upperware.metamodel.types.typesPaasage.OS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(locationUpperwareEClass, LocationUpperware.class, "LocationUpperware", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocationUpperware_Name(), ecorePackage.getEString(), "name", null, 1, 1, LocationUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocationUpperware_AlternativeNames(), ecorePackage.getEString(), "alternativeNames", null, 0, -1, LocationUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(continentUpperwareEClass, ContinentUpperware.class, "ContinentUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(countryUpperwareEClass, CountryUpperware.class, "CountryUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCountryUpperware_Continent(), this.getContinentUpperware(), null, "continent", null, 0, 1, CountryUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cityUpperwareEClass, CityUpperware.class, "CityUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCityUpperware_Country(), this.getCountryUpperware(), null, "country", null, 0, 1, CityUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(locationsEClass, Locations.class, "Locations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLocations_Locations(), this.getLocationUpperware(), null, "locations", null, 1, -1, Locations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(applicationComponentProfileEClass, ApplicationComponentProfile.class, "ApplicationComponentProfile", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getApplicationComponentProfile_Name(), ecorePackage.getEString(), "name", null, 1, 1, ApplicationComponentProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getApplicationComponentProfile_Vers(), ecorePackage.getEString(), "vers", null, 0, 1, ApplicationComponentProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplicationComponentProfile_Type(), this.getApplicationComponentType(), null, "type", null, 1, 1, ApplicationComponentProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(applicationComponentProfilesEClass, ApplicationComponentProfiles.class, "ApplicationComponentProfiles", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getApplicationComponentProfiles_Profiles(), this.getApplicationComponentProfile(), null, "profiles", null, 1, -1, ApplicationComponentProfiles.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(operatingSystemsEClass, OperatingSystems.class, "OperatingSystems", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOperatingSystems_Oss(), this.getOS(), null, "oss", null, 1, -1, OperatingSystems.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(databaseProfileEClass, DatabaseProfile.class, "DatabaseProfile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(webServerProfileEClass, WebServerProfile.class, "WebServerProfile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(applicationServerProfileEClass, ApplicationServerProfile.class, "ApplicationServerProfile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(warProfileEClass, WarProfile.class, "WarProfile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jarProfileEClass, JarProfile.class, "JarProfile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(applicationComponentTypeEClass, ApplicationComponentType.class, "ApplicationComponentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getApplicationComponentType_Id(), ecorePackage.getEString(), "id", null, 1, 1, ApplicationComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(providerTypeEClass, ProviderType.class, "ProviderType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProviderType_Id(), ecorePackage.getEString(), "id", null, 1, 1, ProviderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(providerTypesEClass, ProviderTypes.class, "ProviderTypes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProviderTypes_Types(), this.getProviderType(), null, "types", null, 1, -1, ProviderTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(applicationComponentTypesEClass, ApplicationComponentTypes.class, "ApplicationComponentTypes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getApplicationComponentTypes_Types(), this.getApplicationComponentType(), null, "types", null, 1, -1, ApplicationComponentTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actionTypeEClass, ActionType.class, "ActionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getActionType_Id(), ecorePackage.getEString(), "id", null, 1, 1, ActionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actionTypesEClass, ActionTypes.class, "ActionTypes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActionTypes_Types(), this.getActionType(), null, "types", null, 1, -1, ActionTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(functionTypeEClass, FunctionType.class, "FunctionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFunctionType_Id(), ecorePackage.getEString(), "id", null, 1, 1, FunctionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(functionTypesEClass, FunctionTypes.class, "FunctionTypes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFunctionTypes_Types(), this.getFunctionType(), null, "types", null, 1, -1, FunctionTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(frequencyEnumEEnum, FrequencyEnum.class, "FrequencyEnum");
		addEEnumLiteral(frequencyEnumEEnum, FrequencyEnum.MHZ);
		addEEnumLiteral(frequencyEnumEEnum, FrequencyEnum.GHZ);

		initEEnum(vmSizeEnumEEnum, VMSizeEnum.class, "VMSizeEnum");
		addEEnumLiteral(vmSizeEnumEEnum, VMSizeEnum.XS);
		addEEnumLiteral(vmSizeEnumEEnum, VMSizeEnum.S);
		addEEnumLiteral(vmSizeEnumEEnum, VMSizeEnum.L);
		addEEnumLiteral(vmSizeEnumEEnum, VMSizeEnum.XL);
		addEEnumLiteral(vmSizeEnumEEnum, VMSizeEnum.XXL);
		addEEnumLiteral(vmSizeEnumEEnum, VMSizeEnum.A6);
		addEEnumLiteral(vmSizeEnumEEnum, VMSizeEnum.A7);

		initEEnum(logicOperatorEnumEEnum, LogicOperatorEnum.class, "LogicOperatorEnum");
		addEEnumLiteral(logicOperatorEnumEEnum, LogicOperatorEnum.AND);
		addEEnumLiteral(logicOperatorEnumEEnum, LogicOperatorEnum.OR);

		initEEnum(dataUnitEnumEEnum, DataUnitEnum.class, "DataUnitEnum");
		addEEnumLiteral(dataUnitEnumEEnum, DataUnitEnum.MB);
		addEEnumLiteral(dataUnitEnumEEnum, DataUnitEnum.GB);
		addEEnumLiteral(dataUnitEnumEEnum, DataUnitEnum.TB);

		initEEnum(variableElementTypeEnumEEnum, VariableElementTypeEnum.class, "VariableElementTypeEnum");
		addEEnumLiteral(variableElementTypeEnumEEnum, VariableElementTypeEnum.GEO_LOCATION);
		addEEnumLiteral(variableElementTypeEnumEEnum, VariableElementTypeEnum.PHYSICAL_LOCCATION);
		addEEnumLiteral(variableElementTypeEnumEEnum, VariableElementTypeEnum.VIRTUAL_LOCATION);
		addEEnumLiteral(variableElementTypeEnumEEnum, VariableElementTypeEnum.RESPONSE_TIME);
		addEEnumLiteral(variableElementTypeEnumEEnum, VariableElementTypeEnum.PROVIDER);
		addEEnumLiteral(variableElementTypeEnumEEnum, VariableElementTypeEnum.BANDWIDTH);
		addEEnumLiteral(variableElementTypeEnumEEnum, VariableElementTypeEnum.USERS);
		addEEnumLiteral(variableElementTypeEnumEEnum, VariableElementTypeEnum.QUANTITY);

		initEEnum(osArchitectureEnumEEnum, OSArchitectureEnum.class, "OSArchitectureEnum");
		addEEnumLiteral(osArchitectureEnumEEnum, OSArchitectureEnum.THIRTY_TWO_BITS);
		addEEnumLiteral(osArchitectureEnumEEnum, OSArchitectureEnum.SIXTY_FOUR_BITS);
	}

} //TypesPaasagePackageImpl
