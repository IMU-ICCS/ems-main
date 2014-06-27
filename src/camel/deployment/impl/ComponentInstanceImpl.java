/**
 */
package camel.deployment.impl;

import camel.deployment.Component;
import camel.deployment.ComponentInstance;
import camel.deployment.DeploymentPackage;
import camel.deployment.ProvidedCommunicationInstance;
import camel.deployment.ProvidedHostInstance;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.ComponentInstanceImpl#getProvidedCommunicationInstances <em>Provided Communication Instances</em>}</li>
 *   <li>{@link camel.deployment.impl.ComponentInstanceImpl#getType <em>Type</em>}</li>
 *   <li>{@link camel.deployment.impl.ComponentInstanceImpl#getProvidedHostInstances <em>Provided Host Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ComponentInstanceImpl extends CloudMLElementImpl implements ComponentInstance {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.COMPONENT_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProvidedCommunicationInstance> getProvidedCommunicationInstances() {
		return (EList<ProvidedCommunicationInstance>)eGet(DeploymentPackage.Literals.COMPONENT_INSTANCE__PROVIDED_COMMUNICATION_INSTANCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Component getType() {
		return (Component)eGet(DeploymentPackage.Literals.COMPONENT_INSTANCE__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(Component newType) {
		eSet(DeploymentPackage.Literals.COMPONENT_INSTANCE__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProvidedHostInstance> getProvidedHostInstances() {
		return (EList<ProvidedHostInstance>)eGet(DeploymentPackage.Literals.COMPONENT_INSTANCE__PROVIDED_HOST_INSTANCES, true);
	}

} //ComponentInstanceImpl
