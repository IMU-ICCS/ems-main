/**
 */
package types.typesPaasage.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import types.typesPaasage.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see types.typesPaasage.TypesPaasagePackage
 * @generated
 */
public class TypesPaasageAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TypesPaasagePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesPaasageAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TypesPaasagePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypesPaasageSwitch<Adapter> modelSwitch =
		new TypesPaasageSwitch<Adapter>() {
			@Override
			public Adapter casePaaSageCPElement(PaaSageCPElement object) {
				return createPaaSageCPElementAdapter();
			}
			@Override
			public Adapter caseOS(OS object) {
				return createOSAdapter();
			}
			@Override
			public Adapter caseLocation(Location object) {
				return createLocationAdapter();
			}
			@Override
			public Adapter caseContinent(Continent object) {
				return createContinentAdapter();
			}
			@Override
			public Adapter caseCountry(Country object) {
				return createCountryAdapter();
			}
			@Override
			public Adapter caseCity(City object) {
				return createCityAdapter();
			}
			@Override
			public Adapter caseLocations(Locations object) {
				return createLocationsAdapter();
			}
			@Override
			public Adapter caseApplicationComponentProfile(ApplicationComponentProfile object) {
				return createApplicationComponentProfileAdapter();
			}
			@Override
			public Adapter caseApplicationComponentProfiles(ApplicationComponentProfiles object) {
				return createApplicationComponentProfilesAdapter();
			}
			@Override
			public Adapter caseOperatingSystems(OperatingSystems object) {
				return createOperatingSystemsAdapter();
			}
			@Override
			public Adapter caseDatabaseProfile(DatabaseProfile object) {
				return createDatabaseProfileAdapter();
			}
			@Override
			public Adapter caseWebServerProfile(WebServerProfile object) {
				return createWebServerProfileAdapter();
			}
			@Override
			public Adapter caseApplicationServerProfile(ApplicationServerProfile object) {
				return createApplicationServerProfileAdapter();
			}
			@Override
			public Adapter caseWarProfile(WarProfile object) {
				return createWarProfileAdapter();
			}
			@Override
			public Adapter caseJarProfile(JarProfile object) {
				return createJarProfileAdapter();
			}
			@Override
			public Adapter caseApplicationComponentType(ApplicationComponentType object) {
				return createApplicationComponentTypeAdapter();
			}
			@Override
			public Adapter caseProviderType(ProviderType object) {
				return createProviderTypeAdapter();
			}
			@Override
			public Adapter caseProviderTypes(ProviderTypes object) {
				return createProviderTypesAdapter();
			}
			@Override
			public Adapter caseApplicationComponentTypes(ApplicationComponentTypes object) {
				return createApplicationComponentTypesAdapter();
			}
			@Override
			public Adapter caseActionType(ActionType object) {
				return createActionTypeAdapter();
			}
			@Override
			public Adapter caseActionTypes(ActionTypes object) {
				return createActionTypesAdapter();
			}
			@Override
			public Adapter caseFunctionType(FunctionType object) {
				return createFunctionTypeAdapter();
			}
			@Override
			public Adapter caseFunctionTypes(FunctionTypes object) {
				return createFunctionTypesAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.PaaSageCPElement <em>Paa Sage CP Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.PaaSageCPElement
	 * @generated
	 */
	public Adapter createPaaSageCPElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.OS <em>OS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.OS
	 * @generated
	 */
	public Adapter createOSAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.Location
	 * @generated
	 */
	public Adapter createLocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.Continent <em>Continent</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.Continent
	 * @generated
	 */
	public Adapter createContinentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.Country <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.Country
	 * @generated
	 */
	public Adapter createCountryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.City <em>City</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.City
	 * @generated
	 */
	public Adapter createCityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.Locations <em>Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.Locations
	 * @generated
	 */
	public Adapter createLocationsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.ApplicationComponentProfile <em>Application Component Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.ApplicationComponentProfile
	 * @generated
	 */
	public Adapter createApplicationComponentProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.ApplicationComponentProfiles <em>Application Component Profiles</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.ApplicationComponentProfiles
	 * @generated
	 */
	public Adapter createApplicationComponentProfilesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.OperatingSystems <em>Operating Systems</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.OperatingSystems
	 * @generated
	 */
	public Adapter createOperatingSystemsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.DatabaseProfile <em>Database Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.DatabaseProfile
	 * @generated
	 */
	public Adapter createDatabaseProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.WebServerProfile <em>Web Server Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.WebServerProfile
	 * @generated
	 */
	public Adapter createWebServerProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.ApplicationServerProfile <em>Application Server Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.ApplicationServerProfile
	 * @generated
	 */
	public Adapter createApplicationServerProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.WarProfile <em>War Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.WarProfile
	 * @generated
	 */
	public Adapter createWarProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.JarProfile <em>Jar Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.JarProfile
	 * @generated
	 */
	public Adapter createJarProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.ApplicationComponentType <em>Application Component Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.ApplicationComponentType
	 * @generated
	 */
	public Adapter createApplicationComponentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.ProviderType <em>Provider Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.ProviderType
	 * @generated
	 */
	public Adapter createProviderTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.ProviderTypes <em>Provider Types</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.ProviderTypes
	 * @generated
	 */
	public Adapter createProviderTypesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.ApplicationComponentTypes <em>Application Component Types</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.ApplicationComponentTypes
	 * @generated
	 */
	public Adapter createApplicationComponentTypesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.ActionType <em>Action Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.ActionType
	 * @generated
	 */
	public Adapter createActionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.ActionTypes <em>Action Types</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.ActionTypes
	 * @generated
	 */
	public Adapter createActionTypesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.FunctionType <em>Function Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.FunctionType
	 * @generated
	 */
	public Adapter createFunctionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link types.typesPaasage.FunctionTypes <em>Function Types</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see types.typesPaasage.FunctionTypes
	 * @generated
	 */
	public Adapter createFunctionTypesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //TypesPaasageAdapterFactory
