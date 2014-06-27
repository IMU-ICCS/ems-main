/**
 */
package camel.deployment.util;

import camel.deployment.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see camel.deployment.DeploymentPackage
 * @generated
 */
public class DeploymentAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DeploymentPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DeploymentPackage.eINSTANCE;
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
	protected DeploymentSwitch<Adapter> modelSwitch =
		new DeploymentSwitch<Adapter>() {
			@Override
			public Adapter caseCloudMLElement(CloudMLElement object) {
				return createCloudMLElementAdapter();
			}
			@Override
			public Adapter caseDeploymentModel(DeploymentModel object) {
				return createDeploymentModelAdapter();
			}
			@Override
			public Adapter caseComponent(Component object) {
				return createComponentAdapter();
			}
			@Override
			public Adapter caseInternalComponent(InternalComponent object) {
				return createInternalComponentAdapter();
			}
			@Override
			public Adapter caseExternalComponent(ExternalComponent object) {
				return createExternalComponentAdapter();
			}
			@Override
			public Adapter caseVM(VM object) {
				return createVMAdapter();
			}
			@Override
			public Adapter caseComponentGroup(ComponentGroup object) {
				return createComponentGroupAdapter();
			}
			@Override
			public Adapter caseComputationalResource(ComputationalResource object) {
				return createComputationalResourceAdapter();
			}
			@Override
			public Adapter caseCommunication(Communication object) {
				return createCommunicationAdapter();
			}
			@Override
			public Adapter caseCommunicationPort(CommunicationPort object) {
				return createCommunicationPortAdapter();
			}
			@Override
			public Adapter caseProvidedCommunication(ProvidedCommunication object) {
				return createProvidedCommunicationAdapter();
			}
			@Override
			public Adapter caseRequiredCommunication(RequiredCommunication object) {
				return createRequiredCommunicationAdapter();
			}
			@Override
			public Adapter caseHosting(Hosting object) {
				return createHostingAdapter();
			}
			@Override
			public Adapter caseHostingPort(HostingPort object) {
				return createHostingPortAdapter();
			}
			@Override
			public Adapter caseProvidedHost(ProvidedHost object) {
				return createProvidedHostAdapter();
			}
			@Override
			public Adapter caseRequiredHost(RequiredHost object) {
				return createRequiredHostAdapter();
			}
			@Override
			public Adapter caseImage(Image object) {
				return createImageAdapter();
			}
			@Override
			public Adapter caseComponentInstance(ComponentInstance object) {
				return createComponentInstanceAdapter();
			}
			@Override
			public Adapter caseInternalComponentInstance(InternalComponentInstance object) {
				return createInternalComponentInstanceAdapter();
			}
			@Override
			public Adapter caseExternalComponentInstance(ExternalComponentInstance object) {
				return createExternalComponentInstanceAdapter();
			}
			@Override
			public Adapter caseVMInstance(VMInstance object) {
				return createVMInstanceAdapter();
			}
			@Override
			public Adapter caseCommunicationInstance(CommunicationInstance object) {
				return createCommunicationInstanceAdapter();
			}
			@Override
			public Adapter caseCommunicationPortInstance(CommunicationPortInstance object) {
				return createCommunicationPortInstanceAdapter();
			}
			@Override
			public Adapter caseProvidedCommunicationInstance(ProvidedCommunicationInstance object) {
				return createProvidedCommunicationInstanceAdapter();
			}
			@Override
			public Adapter caseRequiredCommunicationInstance(RequiredCommunicationInstance object) {
				return createRequiredCommunicationInstanceAdapter();
			}
			@Override
			public Adapter caseHostingInstance(HostingInstance object) {
				return createHostingInstanceAdapter();
			}
			@Override
			public Adapter caseHostingPortInstance(HostingPortInstance object) {
				return createHostingPortInstanceAdapter();
			}
			@Override
			public Adapter caseProvidedHostInstance(ProvidedHostInstance object) {
				return createProvidedHostInstanceAdapter();
			}
			@Override
			public Adapter caseRequiredHostInstance(RequiredHostInstance object) {
				return createRequiredHostInstanceAdapter();
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
	 * Creates a new adapter for an object of class '{@link camel.deployment.CloudMLElement <em>Cloud ML Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.CloudMLElement
	 * @generated
	 */
	public Adapter createCloudMLElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.DeploymentModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.DeploymentModel
	 * @generated
	 */
	public Adapter createDeploymentModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.Component <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.Component
	 * @generated
	 */
	public Adapter createComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.InternalComponent <em>Internal Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.InternalComponent
	 * @generated
	 */
	public Adapter createInternalComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.ExternalComponent <em>External Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.ExternalComponent
	 * @generated
	 */
	public Adapter createExternalComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.VM <em>VM</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.VM
	 * @generated
	 */
	public Adapter createVMAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.ComponentGroup <em>Component Group</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.ComponentGroup
	 * @generated
	 */
	public Adapter createComponentGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.ComputationalResource <em>Computational Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.ComputationalResource
	 * @generated
	 */
	public Adapter createComputationalResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.Communication <em>Communication</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.Communication
	 * @generated
	 */
	public Adapter createCommunicationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.CommunicationPort <em>Communication Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.CommunicationPort
	 * @generated
	 */
	public Adapter createCommunicationPortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.ProvidedCommunication <em>Provided Communication</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.ProvidedCommunication
	 * @generated
	 */
	public Adapter createProvidedCommunicationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.RequiredCommunication <em>Required Communication</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.RequiredCommunication
	 * @generated
	 */
	public Adapter createRequiredCommunicationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.Hosting <em>Hosting</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.Hosting
	 * @generated
	 */
	public Adapter createHostingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.HostingPort <em>Hosting Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.HostingPort
	 * @generated
	 */
	public Adapter createHostingPortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.ProvidedHost <em>Provided Host</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.ProvidedHost
	 * @generated
	 */
	public Adapter createProvidedHostAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.RequiredHost <em>Required Host</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.RequiredHost
	 * @generated
	 */
	public Adapter createRequiredHostAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.Image <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.Image
	 * @generated
	 */
	public Adapter createImageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.ComponentInstance <em>Component Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.ComponentInstance
	 * @generated
	 */
	public Adapter createComponentInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.InternalComponentInstance <em>Internal Component Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.InternalComponentInstance
	 * @generated
	 */
	public Adapter createInternalComponentInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.ExternalComponentInstance <em>External Component Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.ExternalComponentInstance
	 * @generated
	 */
	public Adapter createExternalComponentInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.VMInstance <em>VM Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.VMInstance
	 * @generated
	 */
	public Adapter createVMInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.CommunicationInstance <em>Communication Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.CommunicationInstance
	 * @generated
	 */
	public Adapter createCommunicationInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.CommunicationPortInstance <em>Communication Port Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.CommunicationPortInstance
	 * @generated
	 */
	public Adapter createCommunicationPortInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.ProvidedCommunicationInstance <em>Provided Communication Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.ProvidedCommunicationInstance
	 * @generated
	 */
	public Adapter createProvidedCommunicationInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.RequiredCommunicationInstance <em>Required Communication Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.RequiredCommunicationInstance
	 * @generated
	 */
	public Adapter createRequiredCommunicationInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.HostingInstance <em>Hosting Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.HostingInstance
	 * @generated
	 */
	public Adapter createHostingInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.HostingPortInstance <em>Hosting Port Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.HostingPortInstance
	 * @generated
	 */
	public Adapter createHostingPortInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.ProvidedHostInstance <em>Provided Host Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.ProvidedHostInstance
	 * @generated
	 */
	public Adapter createProvidedHostInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.deployment.RequiredHostInstance <em>Required Host Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.deployment.RequiredHostInstance
	 * @generated
	 */
	public Adapter createRequiredHostInstanceAdapter() {
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

} //DeploymentAdapterFactory
