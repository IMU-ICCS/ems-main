/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.types.typesPaasage.util;

import eu.paasage.upperware.metamodel.types.typesPaasage.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage
 * 
 */
public class TypesPaasageAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	protected static TypesPaasagePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
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
	 * 
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
	 * 
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
			public Adapter caseLocationUpperware(LocationUpperware object) {
				return createLocationUpperwareAdapter();
			}
			@Override
			public Adapter caseContinentUpperware(ContinentUpperware object) {
				return createContinentUpperwareAdapter();
			}
			@Override
			public Adapter caseCountryUpperware(CountryUpperware object) {
				return createCountryUpperwareAdapter();
			}
			@Override
			public Adapter caseCityUpperware(CityUpperware object) {
				return createCityUpperwareAdapter();
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
	 * 
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement <em>Paa Sage CP Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement
	 * 
	 */
	public Adapter createPaaSageCPElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS <em>OS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OS
	 * 
	 */
	public Adapter createOSAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware <em>Location Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware
	 * 
	 */
	public Adapter createLocationUpperwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ContinentUpperware <em>Continent Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ContinentUpperware
	 * 
	 */
	public Adapter createContinentUpperwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware <em>Country Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware
	 * 
	 */
	public Adapter createCountryUpperwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.CityUpperware <em>City Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.CityUpperware
	 * 
	 */
	public Adapter createCityUpperwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.Locations <em>Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.Locations
	 * 
	 */
	public Adapter createLocationsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile <em>Application Component Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfile
	 * 
	 */
	public Adapter createApplicationComponentProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfiles <em>Application Component Profiles</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentProfiles
	 * 
	 */
	public Adapter createApplicationComponentProfilesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems <em>Operating Systems</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems
	 * 
	 */
	public Adapter createOperatingSystemsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.DatabaseProfile <em>Database Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.DatabaseProfile
	 * 
	 */
	public Adapter createDatabaseProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.WebServerProfile <em>Web Server Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.WebServerProfile
	 * 
	 */
	public Adapter createWebServerProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationServerProfile <em>Application Server Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationServerProfile
	 * 
	 */
	public Adapter createApplicationServerProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.WarProfile <em>War Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.WarProfile
	 * 
	 */
	public Adapter createWarProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.JarProfile <em>Jar Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.JarProfile
	 * 
	 */
	public Adapter createJarProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentType <em>Application Component Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentType
	 * 
	 */
	public Adapter createApplicationComponentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType <em>Provider Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType
	 * 
	 */
	public Adapter createProviderTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes <em>Provider Types</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes
	 * 
	 */
	public Adapter createProviderTypesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentTypes <em>Application Component Types</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentTypes
	 * 
	 */
	public Adapter createApplicationComponentTypesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ActionType <em>Action Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ActionType
	 * 
	 */
	public Adapter createActionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.ActionTypes <em>Action Types</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.ActionTypes
	 * 
	 */
	public Adapter createActionTypesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType <em>Function Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType
	 * 
	 */
	public Adapter createFunctionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes <em>Function Types</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes
	 * 
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
	 * 
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //TypesPaasageAdapterFactory
