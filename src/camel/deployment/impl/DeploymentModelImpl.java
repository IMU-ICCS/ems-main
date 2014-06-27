/**
 */
package camel.deployment.impl;

import camel.deployment.Communication;
import camel.deployment.CommunicationInstance;
import camel.deployment.Component;
import camel.deployment.ComponentInstance;
import camel.deployment.DeploymentModel;
import camel.deployment.DeploymentPackage;
import camel.deployment.Hosting;
import camel.deployment.HostingInstance;
import camel.deployment.Image;

import camel.organisation.CloudProvider;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.DeploymentModelImpl#getComponents <em>Components</em>}</li>
 *   <li>{@link camel.deployment.impl.DeploymentModelImpl#getCommunications <em>Communications</em>}</li>
 *   <li>{@link camel.deployment.impl.DeploymentModelImpl#getHostings <em>Hostings</em>}</li>
 *   <li>{@link camel.deployment.impl.DeploymentModelImpl#getImages <em>Images</em>}</li>
 *   <li>{@link camel.deployment.impl.DeploymentModelImpl#getProviders <em>Providers</em>}</li>
 *   <li>{@link camel.deployment.impl.DeploymentModelImpl#getComponentInstances <em>Component Instances</em>}</li>
 *   <li>{@link camel.deployment.impl.DeploymentModelImpl#getCommunicationInstances <em>Communication Instances</em>}</li>
 *   <li>{@link camel.deployment.impl.DeploymentModelImpl#getHostingInstances <em>Hosting Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeploymentModelImpl extends CloudMLElementImpl implements DeploymentModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeploymentModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.DEPLOYMENT_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Component> getComponents() {
		return (EList<Component>)eGet(DeploymentPackage.Literals.DEPLOYMENT_MODEL__COMPONENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Communication> getCommunications() {
		return (EList<Communication>)eGet(DeploymentPackage.Literals.DEPLOYMENT_MODEL__COMMUNICATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Hosting> getHostings() {
		return (EList<Hosting>)eGet(DeploymentPackage.Literals.DEPLOYMENT_MODEL__HOSTINGS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Image> getImages() {
		return (EList<Image>)eGet(DeploymentPackage.Literals.DEPLOYMENT_MODEL__IMAGES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<CloudProvider> getProviders() {
		return (EList<CloudProvider>)eGet(DeploymentPackage.Literals.DEPLOYMENT_MODEL__PROVIDERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ComponentInstance> getComponentInstances() {
		return (EList<ComponentInstance>)eGet(DeploymentPackage.Literals.DEPLOYMENT_MODEL__COMPONENT_INSTANCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<CommunicationInstance> getCommunicationInstances() {
		return (EList<CommunicationInstance>)eGet(DeploymentPackage.Literals.DEPLOYMENT_MODEL__COMMUNICATION_INSTANCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<HostingInstance> getHostingInstances() {
		return (EList<HostingInstance>)eGet(DeploymentPackage.Literals.DEPLOYMENT_MODEL__HOSTING_INSTANCES, true);
	}

} //DeploymentModelImpl
