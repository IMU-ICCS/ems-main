/**
 */
package camel.deployment.impl;

import camel.deployment.DeploymentPackage;
import camel.deployment.InternalComponentInstance;
import camel.deployment.RequiredCommunicationInstance;
import camel.deployment.RequiredHostInstance;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Internal Component Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.InternalComponentInstanceImpl#getRequiredCommunicationInstances <em>Required Communication Instances</em>}</li>
 *   <li>{@link camel.deployment.impl.InternalComponentInstanceImpl#getRequiredHostInstance <em>Required Host Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InternalComponentInstanceImpl extends ComponentInstanceImpl implements InternalComponentInstance {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InternalComponentInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.INTERNAL_COMPONENT_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<RequiredCommunicationInstance> getRequiredCommunicationInstances() {
		return (EList<RequiredCommunicationInstance>)eGet(DeploymentPackage.Literals.INTERNAL_COMPONENT_INSTANCE__REQUIRED_COMMUNICATION_INSTANCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredHostInstance getRequiredHostInstance() {
		return (RequiredHostInstance)eGet(DeploymentPackage.Literals.INTERNAL_COMPONENT_INSTANCE__REQUIRED_HOST_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequiredHostInstance(RequiredHostInstance newRequiredHostInstance) {
		eSet(DeploymentPackage.Literals.INTERNAL_COMPONENT_INSTANCE__REQUIRED_HOST_INSTANCE, newRequiredHostInstance);
	}

} //InternalComponentInstanceImpl
