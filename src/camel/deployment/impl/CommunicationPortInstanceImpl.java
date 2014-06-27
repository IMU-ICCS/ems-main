/**
 */
package camel.deployment.impl;

import camel.deployment.CommunicationPort;
import camel.deployment.CommunicationPortInstance;
import camel.deployment.ComponentInstance;
import camel.deployment.DeploymentPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Communication Port Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.CommunicationPortInstanceImpl#getType <em>Type</em>}</li>
 *   <li>{@link camel.deployment.impl.CommunicationPortInstanceImpl#getComponentInstance <em>Component Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CommunicationPortInstanceImpl extends CloudMLElementImpl implements CommunicationPortInstance {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommunicationPortInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.COMMUNICATION_PORT_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommunicationPort getType() {
		return (CommunicationPort)eGet(DeploymentPackage.Literals.COMMUNICATION_PORT_INSTANCE__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(CommunicationPort newType) {
		eSet(DeploymentPackage.Literals.COMMUNICATION_PORT_INSTANCE__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentInstance getComponentInstance() {
		return (ComponentInstance)eGet(DeploymentPackage.Literals.COMMUNICATION_PORT_INSTANCE__COMPONENT_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentInstance(ComponentInstance newComponentInstance) {
		eSet(DeploymentPackage.Literals.COMMUNICATION_PORT_INSTANCE__COMPONENT_INSTANCE, newComponentInstance);
	}

} //CommunicationPortInstanceImpl
