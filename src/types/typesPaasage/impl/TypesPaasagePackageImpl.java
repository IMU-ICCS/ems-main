/**
 */
package types.typesPaasage.impl;

import application.ApplicationPackage;

import application.impl.ApplicationPackageImpl;

import cp.CpPackage;

import cp.impl.CpPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import types.TypesPackage;

import types.impl.TypesPackageImpl;

import types.typesPaasage.ActionType;
import types.typesPaasage.ActionTypes;
import types.typesPaasage.ApplicationComponentProfile;
import types.typesPaasage.ApplicationComponentProfiles;
import types.typesPaasage.ApplicationComponentType;
import types.typesPaasage.ApplicationComponentTypes;
import types.typesPaasage.ApplicationServerProfile;
import types.typesPaasage.City;
import types.typesPaasage.Continent;
import types.typesPaasage.Country;
import types.typesPaasage.DataUnitEnum;
import types.typesPaasage.DatabaseProfile;
import types.typesPaasage.FrequencyEnum;
import types.typesPaasage.FunctionType;
import types.typesPaasage.FunctionTypes;
import types.typesPaasage.JarProfile;
import types.typesPaasage.Location;
import types.typesPaasage.Locations;
import types.typesPaasage.LogicOperatorEnum;
import types.typesPaasage.OSArchitectureEnum;
import types.typesPaasage.OperatingSystems;
import types.typesPaasage.PaaSageCPElement;
import types.typesPaasage.ProviderType;
import types.typesPaasage.ProviderTypes;
import types.typesPaasage.TypesPaasageFactory;
import types.typesPaasage.TypesPaasagePackage;
import types.typesPaasage.VMSizeEnum;
import types.typesPaasage.VariableElementTypeEnum;
import types.typesPaasage.WarProfile;
import types.typesPaasage.WebServerProfile;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesPaasagePackageImpl extends EPackageImpl implements TypesPaasagePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paaSageCPElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass osEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass locationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass continentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass countryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass locationsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationComponentProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationComponentProfilesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operatingSystemsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass databaseProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass webServerProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationServerProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass warProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jarProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationComponentTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass providerTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass providerTypesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationComponentTypesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionTypesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionTypesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum frequencyEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum vmSizeEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum logicOperatorEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dataUnitEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum variableElementTypeEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
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
	 * @see types.typesPaasage.TypesPaasagePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TypesPaasagePackageImpl() {
		super(eNS_URI, TypesPaasageFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
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
	 * @generated
	 */
	public static TypesPaasagePackage init() {
		if (isInited) return (TypesPaasagePackage)EPackage.Registry.INSTANCE.getEPackage(TypesPaasagePackage.eNS_URI);

		// Obtain or create and register package
		TypesPaasagePackageImpl theTypesPaasagePackage = (TypesPaasagePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TypesPaasagePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TypesPaasagePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ApplicationPackageImpl theApplicationPackage = (ApplicationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ApplicationPackage.eNS_URI) instanceof ApplicationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ApplicationPackage.eNS_URI) : ApplicationPackage.eINSTANCE);
		CpPackageImpl theCpPackage = (CpPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CpPackage.eNS_URI) instanceof CpPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CpPackage.eNS_URI) : CpPackage.eINSTANCE);
		TypesPackageImpl theTypesPackage = (TypesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI) instanceof TypesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI) : TypesPackage.eINSTANCE);

		// Create package meta-data objects
		theTypesPaasagePackage.createPackageContents();
		theApplicationPackage.createPackageContents();
		theCpPackage.createPackageContents();
		theTypesPackage.createPackageContents();

		// Initialize created meta-data
		theTypesPaasagePackage.initializePackageContents();
		theApplicationPackage.initializePackageContents();
		theCpPackage.initializePackageContents();
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
	 * @generated
	 */
	public EClass getPaaSageCPElement() {
		return paaSageCPElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaaSageCPElement_TypeId() {
		return (EAttribute)paaSageCPElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOS() {
		return osEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOS_Name() {
		return (EAttribute)osEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOS_Vers() {
		return (EAttribute)osEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOS_Architecture() {
		return (EAttribute)osEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocation() {
		return locationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocation_Name() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocation_AlternativeNames() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContinent() {
		return continentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCountry() {
		return countryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCountry_Continent() {
		return (EReference)countryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCity() {
		return cityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCity_Country() {
		return (EReference)cityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocations() {
		return locationsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocations_Locations() {
		return (EReference)locationsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplicationComponentProfile() {
		return applicationComponentProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApplicationComponentProfile_Name() {
		return (EAttribute)applicationComponentProfileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApplicationComponentProfile_Vers() {
		return (EAttribute)applicationComponentProfileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationComponentProfile_Type() {
		return (EReference)applicationComponentProfileEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplicationComponentProfiles() {
		return applicationComponentProfilesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationComponentProfiles_Profiles() {
		return (EReference)applicationComponentProfilesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperatingSystems() {
		return operatingSystemsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperatingSystems_Oss() {
		return (EReference)operatingSystemsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDatabaseProfile() {
		return databaseProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWebServerProfile() {
		return webServerProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplicationServerProfile() {
		return applicationServerProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWarProfile() {
		return warProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJarProfile() {
		return jarProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplicationComponentType() {
		return applicationComponentTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApplicationComponentType_Id() {
		return (EAttribute)applicationComponentTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProviderType() {
		return providerTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProviderType_Id() {
		return (EAttribute)providerTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProviderTypes() {
		return providerTypesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProviderTypes_Types() {
		return (EReference)providerTypesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplicationComponentTypes() {
		return applicationComponentTypesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationComponentTypes_Types() {
		return (EReference)applicationComponentTypesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionType() {
		return actionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionType_Id() {
		return (EAttribute)actionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionTypes() {
		return actionTypesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActionTypes_Types() {
		return (EReference)actionTypesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunctionType() {
		return functionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunctionType_Id() {
		return (EAttribute)functionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunctionTypes() {
		return functionTypesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionTypes_Types() {
		return (EReference)functionTypesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getFrequencyEnum() {
		return frequencyEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getVMSizeEnum() {
		return vmSizeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getLogicOperatorEnum() {
		return logicOperatorEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDataUnitEnum() {
		return dataUnitEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getVariableElementTypeEnum() {
		return variableElementTypeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOSArchitectureEnum() {
		return osArchitectureEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesPaasageFactory getTypesPaasageFactory() {
		return (TypesPaasageFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
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

		locationEClass = createEClass(LOCATION);
		createEAttribute(locationEClass, LOCATION__NAME);
		createEAttribute(locationEClass, LOCATION__ALTERNATIVE_NAMES);

		continentEClass = createEClass(CONTINENT);

		countryEClass = createEClass(COUNTRY);
		createEReference(countryEClass, COUNTRY__CONTINENT);

		cityEClass = createEClass(CITY);
		createEReference(cityEClass, CITY__COUNTRY);

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
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
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
		locationEClass.getESuperTypes().add(this.getPaaSageCPElement());
		continentEClass.getESuperTypes().add(this.getLocation());
		countryEClass.getESuperTypes().add(this.getLocation());
		cityEClass.getESuperTypes().add(this.getLocation());
		databaseProfileEClass.getESuperTypes().add(this.getApplicationComponentProfile());
		webServerProfileEClass.getESuperTypes().add(this.getApplicationComponentProfile());
		applicationServerProfileEClass.getESuperTypes().add(this.getApplicationComponentProfile());
		warProfileEClass.getESuperTypes().add(this.getApplicationComponentProfile());
		jarProfileEClass.getESuperTypes().add(this.getApplicationComponentProfile());

		// Initialize classes, features, and operations; add parameters
		initEClass(paaSageCPElementEClass, PaaSageCPElement.class, "PaaSageCPElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPaaSageCPElement_TypeId(), ecorePackage.getEInt(), "typeId", null, 1, 1, PaaSageCPElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(osEClass, types.typesPaasage.OS.class, "OS", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOS_Name(), ecorePackage.getEString(), "name", null, 1, 1, types.typesPaasage.OS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOS_Vers(), ecorePackage.getEString(), "vers", null, 0, 1, types.typesPaasage.OS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOS_Architecture(), this.getOSArchitectureEnum(), "architecture", null, 1, 1, types.typesPaasage.OS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(locationEClass, Location.class, "Location", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocation_Name(), ecorePackage.getEString(), "name", null, 1, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_AlternativeNames(), ecorePackage.getEString(), "alternativeNames", null, 0, -1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(continentEClass, Continent.class, "Continent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(countryEClass, Country.class, "Country", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCountry_Continent(), this.getContinent(), null, "continent", null, 0, 1, Country.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cityEClass, City.class, "City", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCity_Country(), this.getCountry(), null, "country", null, 0, 1, City.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(locationsEClass, Locations.class, "Locations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLocations_Locations(), this.getLocation(), null, "locations", null, 1, -1, Locations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
