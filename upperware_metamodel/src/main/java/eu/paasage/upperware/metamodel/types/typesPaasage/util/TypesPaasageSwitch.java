/**
 */
package eu.paasage.upperware.metamodel.types.typesPaasage.util;

import eu.paasage.upperware.metamodel.types.typesPaasage.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage
 * @generated
 */
public class TypesPaasageSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TypesPaasagePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesPaasageSwitch() {
		if (modelPackage == null) {
			modelPackage = TypesPaasagePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TypesPaasagePackage.PAA_SAGE_CP_ELEMENT: {
				PaaSageCPElement paaSageCPElement = (PaaSageCPElement)theEObject;
				T result = casePaaSageCPElement(paaSageCPElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.OS: {
				OS os = (OS)theEObject;
				T result = caseOS(os);
				if (result == null) result = casePaaSageCPElement(os);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.LOCATION_UPPERWARE: {
				LocationUpperware locationUpperware = (LocationUpperware)theEObject;
				T result = caseLocationUpperware(locationUpperware);
				if (result == null) result = casePaaSageCPElement(locationUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.CONTINENT_UPPERWARE: {
				ContinentUpperware continentUpperware = (ContinentUpperware)theEObject;
				T result = caseContinentUpperware(continentUpperware);
				if (result == null) result = caseLocationUpperware(continentUpperware);
				if (result == null) result = casePaaSageCPElement(continentUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.COUNTRY_UPPERWARE: {
				CountryUpperware countryUpperware = (CountryUpperware)theEObject;
				T result = caseCountryUpperware(countryUpperware);
				if (result == null) result = caseLocationUpperware(countryUpperware);
				if (result == null) result = casePaaSageCPElement(countryUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.CITY_UPPERWARE: {
				CityUpperware cityUpperware = (CityUpperware)theEObject;
				T result = caseCityUpperware(cityUpperware);
				if (result == null) result = caseLocationUpperware(cityUpperware);
				if (result == null) result = casePaaSageCPElement(cityUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.LOCATIONS: {
				Locations locations = (Locations)theEObject;
				T result = caseLocations(locations);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.APPLICATION_COMPONENT_PROFILE: {
				ApplicationComponentProfile applicationComponentProfile = (ApplicationComponentProfile)theEObject;
				T result = caseApplicationComponentProfile(applicationComponentProfile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.APPLICATION_COMPONENT_PROFILES: {
				ApplicationComponentProfiles applicationComponentProfiles = (ApplicationComponentProfiles)theEObject;
				T result = caseApplicationComponentProfiles(applicationComponentProfiles);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.OPERATING_SYSTEMS: {
				OperatingSystems operatingSystems = (OperatingSystems)theEObject;
				T result = caseOperatingSystems(operatingSystems);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.DATABASE_PROFILE: {
				DatabaseProfile databaseProfile = (DatabaseProfile)theEObject;
				T result = caseDatabaseProfile(databaseProfile);
				if (result == null) result = caseApplicationComponentProfile(databaseProfile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.WEB_SERVER_PROFILE: {
				WebServerProfile webServerProfile = (WebServerProfile)theEObject;
				T result = caseWebServerProfile(webServerProfile);
				if (result == null) result = caseApplicationComponentProfile(webServerProfile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.APPLICATION_SERVER_PROFILE: {
				ApplicationServerProfile applicationServerProfile = (ApplicationServerProfile)theEObject;
				T result = caseApplicationServerProfile(applicationServerProfile);
				if (result == null) result = caseApplicationComponentProfile(applicationServerProfile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.WAR_PROFILE: {
				WarProfile warProfile = (WarProfile)theEObject;
				T result = caseWarProfile(warProfile);
				if (result == null) result = caseApplicationComponentProfile(warProfile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.JAR_PROFILE: {
				JarProfile jarProfile = (JarProfile)theEObject;
				T result = caseJarProfile(jarProfile);
				if (result == null) result = caseApplicationComponentProfile(jarProfile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.APPLICATION_COMPONENT_TYPE: {
				ApplicationComponentType applicationComponentType = (ApplicationComponentType)theEObject;
				T result = caseApplicationComponentType(applicationComponentType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.PROVIDER_TYPE: {
				ProviderType providerType = (ProviderType)theEObject;
				T result = caseProviderType(providerType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.PROVIDER_TYPES: {
				ProviderTypes providerTypes = (ProviderTypes)theEObject;
				T result = caseProviderTypes(providerTypes);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.APPLICATION_COMPONENT_TYPES: {
				ApplicationComponentTypes applicationComponentTypes = (ApplicationComponentTypes)theEObject;
				T result = caseApplicationComponentTypes(applicationComponentTypes);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.ACTION_TYPE: {
				ActionType actionType = (ActionType)theEObject;
				T result = caseActionType(actionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.ACTION_TYPES: {
				ActionTypes actionTypes = (ActionTypes)theEObject;
				T result = caseActionTypes(actionTypes);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.FUNCTION_TYPE: {
				FunctionType functionType = (FunctionType)theEObject;
				T result = caseFunctionType(functionType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPaasagePackage.FUNCTION_TYPES: {
				FunctionTypes functionTypes = (FunctionTypes)theEObject;
				T result = caseFunctionTypes(functionTypes);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Paa Sage CP Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Paa Sage CP Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePaaSageCPElement(PaaSageCPElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>OS</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>OS</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOS(OS object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Location Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Location Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocationUpperware(LocationUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Continent Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Continent Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContinentUpperware(ContinentUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Country Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Country Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCountryUpperware(CountryUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>City Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>City Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCityUpperware(CityUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Locations</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Locations</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocations(Locations object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Application Component Profile</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Application Component Profile</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApplicationComponentProfile(ApplicationComponentProfile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Application Component Profiles</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Application Component Profiles</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApplicationComponentProfiles(ApplicationComponentProfiles object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operating Systems</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operating Systems</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperatingSystems(OperatingSystems object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Database Profile</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Database Profile</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDatabaseProfile(DatabaseProfile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Web Server Profile</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Web Server Profile</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWebServerProfile(WebServerProfile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Application Server Profile</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Application Server Profile</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApplicationServerProfile(ApplicationServerProfile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>War Profile</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>War Profile</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWarProfile(WarProfile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Jar Profile</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Jar Profile</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJarProfile(JarProfile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Application Component Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Application Component Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApplicationComponentType(ApplicationComponentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Provider Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Provider Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProviderType(ProviderType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Provider Types</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Provider Types</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProviderTypes(ProviderTypes object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Application Component Types</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Application Component Types</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApplicationComponentTypes(ApplicationComponentTypes object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionType(ActionType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Types</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Types</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionTypes(ActionTypes object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionType(FunctionType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Types</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Types</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionTypes(FunctionTypes object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //TypesPaasageSwitch
