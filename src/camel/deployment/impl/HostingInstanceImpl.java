/**
 */
package camel.deployment.impl;

import camel.deployment.DeploymentPackage;
import camel.deployment.Hosting;
import camel.deployment.HostingInstance;
import camel.deployment.ProvidedHostInstance;
import camel.deployment.RequiredHostInstance;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Hosting Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.HostingInstanceImpl#getProvidedHostInstance <em>Provided Host Instance</em>}</li>
 *   <li>{@link camel.deployment.impl.HostingInstanceImpl#getRequiredHostInstance <em>Required Host Instance</em>}</li>
 *   <li>{@link camel.deployment.impl.HostingInstanceImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HostingInstanceImpl extends CloudMLElementImpl implements HostingInstance {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HostingInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.HOSTING_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProvidedHostInstance getProvidedHostInstance() {
		return (ProvidedHostInstance)eGet(DeploymentPackage.Literals.HOSTING_INSTANCE__PROVIDED_HOST_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvidedHostInstance(ProvidedHostInstance newProvidedHostInstance) {
		eSet(DeploymentPackage.Literals.HOSTING_INSTANCE__PROVIDED_HOST_INSTANCE, newProvidedHostInstance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredHostInstance getRequiredHostInstance() {
		return (RequiredHostInstance)eGet(DeploymentPackage.Literals.HOSTING_INSTANCE__REQUIRED_HOST_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequiredHostInstance(RequiredHostInstance newRequiredHostInstance) {
		eSet(DeploymentPackage.Literals.HOSTING_INSTANCE__REQUIRED_HOST_INSTANCE, newRequiredHostInstance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Hosting getType() {
		return (Hosting)eGet(DeploymentPackage.Literals.HOSTING_INSTANCE__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(Hosting newType) {
		eSet(DeploymentPackage.Literals.HOSTING_INSTANCE__TYPE, newType);
	}

} //HostingInstanceImpl
