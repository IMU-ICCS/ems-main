/**
 */
package eu.paasage.upperware.metamodel.types.typesPaasage.impl;

import eu.paasage.upperware.metamodel.types.typesPaasage.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesPaasageFactoryImpl extends EFactoryImpl implements TypesPaasageFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TypesPaasageFactory init() {
		try {
			TypesPaasageFactory theTypesPaasageFactory = (TypesPaasageFactory)EPackage.Registry.INSTANCE.getEFactory(TypesPaasagePackage.eNS_URI);
			if (theTypesPaasageFactory != null) {
				return theTypesPaasageFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TypesPaasageFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesPaasageFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TypesPaasagePackage.OS: return (EObject)createOS();
			case TypesPaasagePackage.CONTINENT_UPPERWARE: return (EObject)createContinentUpperware();
			case TypesPaasagePackage.COUNTRY_UPPERWARE: return (EObject)createCountryUpperware();
			case TypesPaasagePackage.CITY_UPPERWARE: return (EObject)createCityUpperware();
			case TypesPaasagePackage.LOCATIONS: return (EObject)createLocations();
			case TypesPaasagePackage.APPLICATION_COMPONENT_PROFILES: return (EObject)createApplicationComponentProfiles();
			case TypesPaasagePackage.OPERATING_SYSTEMS: return (EObject)createOperatingSystems();
			case TypesPaasagePackage.DATABASE_PROFILE: return (EObject)createDatabaseProfile();
			case TypesPaasagePackage.WEB_SERVER_PROFILE: return (EObject)createWebServerProfile();
			case TypesPaasagePackage.APPLICATION_SERVER_PROFILE: return (EObject)createApplicationServerProfile();
			case TypesPaasagePackage.WAR_PROFILE: return (EObject)createWarProfile();
			case TypesPaasagePackage.JAR_PROFILE: return (EObject)createJarProfile();
			case TypesPaasagePackage.APPLICATION_COMPONENT_TYPE: return (EObject)createApplicationComponentType();
			case TypesPaasagePackage.PROVIDER_TYPE: return (EObject)createProviderType();
			case TypesPaasagePackage.PROVIDER_TYPES: return (EObject)createProviderTypes();
			case TypesPaasagePackage.APPLICATION_COMPONENT_TYPES: return (EObject)createApplicationComponentTypes();
			case TypesPaasagePackage.ACTION_TYPE: return (EObject)createActionType();
			case TypesPaasagePackage.ACTION_TYPES: return (EObject)createActionTypes();
			case TypesPaasagePackage.FUNCTION_TYPE: return (EObject)createFunctionType();
			case TypesPaasagePackage.FUNCTION_TYPES: return (EObject)createFunctionTypes();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TypesPaasagePackage.FREQUENCY_ENUM:
				return createFrequencyEnumFromString(eDataType, initialValue);
			case TypesPaasagePackage.VM_SIZE_ENUM:
				return createVMSizeEnumFromString(eDataType, initialValue);
			case TypesPaasagePackage.LOGIC_OPERATOR_ENUM:
				return createLogicOperatorEnumFromString(eDataType, initialValue);
			case TypesPaasagePackage.DATA_UNIT_ENUM:
				return createDataUnitEnumFromString(eDataType, initialValue);
			case TypesPaasagePackage.VARIABLE_ELEMENT_TYPE_ENUM:
				return createVariableElementTypeEnumFromString(eDataType, initialValue);
			case TypesPaasagePackage.OS_ARCHITECTURE_ENUM:
				return createOSArchitectureEnumFromString(eDataType, initialValue);
			case TypesPaasagePackage.COMMUNICATION_TYPE_UPPERWARE:
				return createCommunicationTypeUpperwareFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TypesPaasagePackage.FREQUENCY_ENUM:
				return convertFrequencyEnumToString(eDataType, instanceValue);
			case TypesPaasagePackage.VM_SIZE_ENUM:
				return convertVMSizeEnumToString(eDataType, instanceValue);
			case TypesPaasagePackage.LOGIC_OPERATOR_ENUM:
				return convertLogicOperatorEnumToString(eDataType, instanceValue);
			case TypesPaasagePackage.DATA_UNIT_ENUM:
				return convertDataUnitEnumToString(eDataType, instanceValue);
			case TypesPaasagePackage.VARIABLE_ELEMENT_TYPE_ENUM:
				return convertVariableElementTypeEnumToString(eDataType, instanceValue);
			case TypesPaasagePackage.OS_ARCHITECTURE_ENUM:
				return convertOSArchitectureEnumToString(eDataType, instanceValue);
			case TypesPaasagePackage.COMMUNICATION_TYPE_UPPERWARE:
				return convertCommunicationTypeUpperwareToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OS createOS() {
		OSImpl os = new OSImpl();
		return os;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContinentUpperware createContinentUpperware() {
		ContinentUpperwareImpl continentUpperware = new ContinentUpperwareImpl();
		return continentUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CountryUpperware createCountryUpperware() {
		CountryUpperwareImpl countryUpperware = new CountryUpperwareImpl();
		return countryUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CityUpperware createCityUpperware() {
		CityUpperwareImpl cityUpperware = new CityUpperwareImpl();
		return cityUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Locations createLocations() {
		LocationsImpl locations = new LocationsImpl();
		return locations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationComponentProfiles createApplicationComponentProfiles() {
		ApplicationComponentProfilesImpl applicationComponentProfiles = new ApplicationComponentProfilesImpl();
		return applicationComponentProfiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperatingSystems createOperatingSystems() {
		OperatingSystemsImpl operatingSystems = new OperatingSystemsImpl();
		return operatingSystems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DatabaseProfile createDatabaseProfile() {
		DatabaseProfileImpl databaseProfile = new DatabaseProfileImpl();
		return databaseProfile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WebServerProfile createWebServerProfile() {
		WebServerProfileImpl webServerProfile = new WebServerProfileImpl();
		return webServerProfile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationServerProfile createApplicationServerProfile() {
		ApplicationServerProfileImpl applicationServerProfile = new ApplicationServerProfileImpl();
		return applicationServerProfile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WarProfile createWarProfile() {
		WarProfileImpl warProfile = new WarProfileImpl();
		return warProfile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JarProfile createJarProfile() {
		JarProfileImpl jarProfile = new JarProfileImpl();
		return jarProfile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationComponentType createApplicationComponentType() {
		ApplicationComponentTypeImpl applicationComponentType = new ApplicationComponentTypeImpl();
		return applicationComponentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProviderType createProviderType() {
		ProviderTypeImpl providerType = new ProviderTypeImpl();
		return providerType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProviderTypes createProviderTypes() {
		ProviderTypesImpl providerTypes = new ProviderTypesImpl();
		return providerTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationComponentTypes createApplicationComponentTypes() {
		ApplicationComponentTypesImpl applicationComponentTypes = new ApplicationComponentTypesImpl();
		return applicationComponentTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionType createActionType() {
		ActionTypeImpl actionType = new ActionTypeImpl();
		return actionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionTypes createActionTypes() {
		ActionTypesImpl actionTypes = new ActionTypesImpl();
		return actionTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionType createFunctionType() {
		FunctionTypeImpl functionType = new FunctionTypeImpl();
		return functionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionTypes createFunctionTypes() {
		FunctionTypesImpl functionTypes = new FunctionTypesImpl();
		return functionTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FrequencyEnum createFrequencyEnumFromString(EDataType eDataType, String initialValue) {
		FrequencyEnum result = FrequencyEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFrequencyEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMSizeEnum createVMSizeEnumFromString(EDataType eDataType, String initialValue) {
		VMSizeEnum result = VMSizeEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVMSizeEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogicOperatorEnum createLogicOperatorEnumFromString(EDataType eDataType, String initialValue) {
		LogicOperatorEnum result = LogicOperatorEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLogicOperatorEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataUnitEnum createDataUnitEnumFromString(EDataType eDataType, String initialValue) {
		DataUnitEnum result = DataUnitEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataUnitEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableElementTypeEnum createVariableElementTypeEnumFromString(EDataType eDataType, String initialValue) {
		VariableElementTypeEnum result = VariableElementTypeEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVariableElementTypeEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OSArchitectureEnum createOSArchitectureEnumFromString(EDataType eDataType, String initialValue) {
		OSArchitectureEnum result = OSArchitectureEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOSArchitectureEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommunicationTypeUpperware createCommunicationTypeUpperwareFromString(EDataType eDataType, String initialValue) {
		CommunicationTypeUpperware result = CommunicationTypeUpperware.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCommunicationTypeUpperwareToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesPaasagePackage getTypesPaasagePackage() {
		return (TypesPaasagePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TypesPaasagePackage getPackage() {
		return TypesPaasagePackage.eINSTANCE;
	}

} //TypesPaasageFactoryImpl
