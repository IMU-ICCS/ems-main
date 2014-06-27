/**
 */
package camel.deployment.impl;

import camel.deployment.ComponentInstance;
import camel.deployment.DeploymentPackage;
import camel.deployment.HostingPort;
import camel.deployment.HostingPortInstance;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Hosting Port Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.HostingPortInstanceImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link camel.deployment.impl.HostingPortInstanceImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class HostingPortInstanceImpl extends CloudMLElementImpl implements HostingPortInstance {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HostingPortInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.HOSTING_PORT_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentInstance getOwner() {
		return (ComponentInstance)eGet(DeploymentPackage.Literals.HOSTING_PORT_INSTANCE__OWNER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwner(ComponentInstance newOwner) {
		eSet(DeploymentPackage.Literals.HOSTING_PORT_INSTANCE__OWNER, newOwner);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HostingPort getType() {
		return (HostingPort)eGet(DeploymentPackage.Literals.HOSTING_PORT_INSTANCE__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(HostingPort newType) {
		eSet(DeploymentPackage.Literals.HOSTING_PORT_INSTANCE__TYPE, newType);
	}

} //HostingPortInstanceImpl
