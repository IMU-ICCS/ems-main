/**
 */
package camel.util;

import camel.*;

import camel.cerif.Resource;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see camel.CamelPackage
 * @generated
 */
public class CamelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CamelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CamelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CamelPackage.eINSTANCE;
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
	protected CamelSwitch<Adapter> modelSwitch =
		new CamelSwitch<Adapter>() {
			@Override
			public Adapter caseCamelModel(CamelModel object) {
				return createCamelModelAdapter();
			}
			@Override
			public Adapter caseAction(Action object) {
				return createActionAdapter();
			}
			@Override
			public Adapter casePhysicalNode(PhysicalNode object) {
				return createPhysicalNodeAdapter();
			}
			@Override
			public Adapter caseDataObject(DataObject object) {
				return createDataObjectAdapter();
			}
			@Override
			public Adapter caseRequirementGroup(RequirementGroup object) {
				return createRequirementGroupAdapter();
			}
			@Override
			public Adapter caseRequirement(Requirement object) {
				return createRequirementAdapter();
			}
			@Override
			public Adapter caseScalabilityPolicy(ScalabilityPolicy object) {
				return createScalabilityPolicyAdapter();
			}
			@Override
			public Adapter caseVMInfo(VMInfo object) {
				return createVMInfoAdapter();
			}
			@Override
			public Adapter caseCloudIndependentVMInfo(CloudIndependentVMInfo object) {
				return createCloudIndependentVMInfoAdapter();
			}
			@Override
			public Adapter caseVMType(VMType object) {
				return createVMTypeAdapter();
			}
			@Override
			public Adapter caseApplication(Application object) {
				return createApplicationAdapter();
			}
			@Override
			public Adapter caseUnit(Unit object) {
				return createUnitAdapter();
			}
			@Override
			public Adapter caseMonetaryUnit(MonetaryUnit object) {
				return createMonetaryUnitAdapter();
			}
			@Override
			public Adapter caseStorageUnit(StorageUnit object) {
				return createStorageUnitAdapter();
			}
			@Override
			public Adapter caseTimeIntervalUnit(TimeIntervalUnit object) {
				return createTimeIntervalUnitAdapter();
			}
			@Override
			public Adapter caseThroughputUnit(ThroughputUnit object) {
				return createThroughputUnitAdapter();
			}
			@Override
			public Adapter caseRequestUnit(RequestUnit object) {
				return createRequestUnitAdapter();
			}
			@Override
			public Adapter caseUnitless(Unitless object) {
				return createUnitlessAdapter();
			}
			@Override
			public Adapter caseResource(Resource object) {
				return createResourceAdapter();
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
	 * Creates a new adapter for an object of class '{@link camel.CamelModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.CamelModel
	 * @generated
	 */
	public Adapter createCamelModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.Action
	 * @generated
	 */
	public Adapter createActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.PhysicalNode <em>Physical Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.PhysicalNode
	 * @generated
	 */
	public Adapter createPhysicalNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.DataObject <em>Data Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.DataObject
	 * @generated
	 */
	public Adapter createDataObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.RequirementGroup <em>Requirement Group</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.RequirementGroup
	 * @generated
	 */
	public Adapter createRequirementGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.Requirement <em>Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.Requirement
	 * @generated
	 */
	public Adapter createRequirementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.ScalabilityPolicy <em>Scalability Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.ScalabilityPolicy
	 * @generated
	 */
	public Adapter createScalabilityPolicyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.VMInfo <em>VM Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.VMInfo
	 * @generated
	 */
	public Adapter createVMInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.CloudIndependentVMInfo <em>Cloud Independent VM Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.CloudIndependentVMInfo
	 * @generated
	 */
	public Adapter createCloudIndependentVMInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.VMType <em>VM Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.VMType
	 * @generated
	 */
	public Adapter createVMTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.Application <em>Application</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.Application
	 * @generated
	 */
	public Adapter createApplicationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.Unit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.Unit
	 * @generated
	 */
	public Adapter createUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.MonetaryUnit <em>Monetary Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.MonetaryUnit
	 * @generated
	 */
	public Adapter createMonetaryUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.StorageUnit <em>Storage Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.StorageUnit
	 * @generated
	 */
	public Adapter createStorageUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.TimeIntervalUnit <em>Time Interval Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.TimeIntervalUnit
	 * @generated
	 */
	public Adapter createTimeIntervalUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.ThroughputUnit <em>Throughput Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.ThroughputUnit
	 * @generated
	 */
	public Adapter createThroughputUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.RequestUnit <em>Request Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.RequestUnit
	 * @generated
	 */
	public Adapter createRequestUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.Unitless <em>Unitless</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.Unitless
	 * @generated
	 */
	public Adapter createUnitlessAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.cerif.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.cerif.Resource
	 * @generated
	 */
	public Adapter createResourceAdapter() {
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

} //CamelAdapterFactory
