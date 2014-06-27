/**
 */
package camel.deployment.impl;

import camel.deployment.CommunicationPort;
import camel.deployment.Component;
import camel.deployment.DeploymentPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Communication Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.CommunicationPortImpl#getComponent <em>Component</em>}</li>
 *   <li>{@link camel.deployment.impl.CommunicationPortImpl#isIsLocal <em>Is Local</em>}</li>
 *   <li>{@link camel.deployment.impl.CommunicationPortImpl#getPortNumber <em>Port Number</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class CommunicationPortImpl extends CloudMLElementImpl implements CommunicationPort {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommunicationPortImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.COMMUNICATION_PORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Component getComponent() {
		return (Component)eGet(DeploymentPackage.Literals.COMMUNICATION_PORT__COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(Component newComponent) {
		eSet(DeploymentPackage.Literals.COMMUNICATION_PORT__COMPONENT, newComponent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsLocal() {
		return (Boolean)eGet(DeploymentPackage.Literals.COMMUNICATION_PORT__IS_LOCAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsLocal(boolean newIsLocal) {
		eSet(DeploymentPackage.Literals.COMMUNICATION_PORT__IS_LOCAL, newIsLocal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPortNumber() {
		return (Integer)eGet(DeploymentPackage.Literals.COMMUNICATION_PORT__PORT_NUMBER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPortNumber(int newPortNumber) {
		eSet(DeploymentPackage.Literals.COMMUNICATION_PORT__PORT_NUMBER, newPortNumber);
	}

} //CommunicationPortImpl
