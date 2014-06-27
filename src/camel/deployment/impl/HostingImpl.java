/**
 */
package camel.deployment.impl;

import camel.deployment.ComputationalResource;
import camel.deployment.DeploymentPackage;
import camel.deployment.Hosting;
import camel.deployment.ProvidedHost;
import camel.deployment.RequiredHost;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Hosting</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.HostingImpl#getProvidedHost <em>Provided Host</em>}</li>
 *   <li>{@link camel.deployment.impl.HostingImpl#getRequiredHost <em>Required Host</em>}</li>
 *   <li>{@link camel.deployment.impl.HostingImpl#getRequiredHostResource <em>Required Host Resource</em>}</li>
 *   <li>{@link camel.deployment.impl.HostingImpl#getProvidedHostResource <em>Provided Host Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HostingImpl extends CloudMLElementImpl implements Hosting {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HostingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.HOSTING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProvidedHost getProvidedHost() {
		return (ProvidedHost)eGet(DeploymentPackage.Literals.HOSTING__PROVIDED_HOST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvidedHost(ProvidedHost newProvidedHost) {
		eSet(DeploymentPackage.Literals.HOSTING__PROVIDED_HOST, newProvidedHost);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredHost getRequiredHost() {
		return (RequiredHost)eGet(DeploymentPackage.Literals.HOSTING__REQUIRED_HOST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequiredHost(RequiredHost newRequiredHost) {
		eSet(DeploymentPackage.Literals.HOSTING__REQUIRED_HOST, newRequiredHost);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComputationalResource getRequiredHostResource() {
		return (ComputationalResource)eGet(DeploymentPackage.Literals.HOSTING__REQUIRED_HOST_RESOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequiredHostResource(ComputationalResource newRequiredHostResource) {
		eSet(DeploymentPackage.Literals.HOSTING__REQUIRED_HOST_RESOURCE, newRequiredHostResource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComputationalResource getProvidedHostResource() {
		return (ComputationalResource)eGet(DeploymentPackage.Literals.HOSTING__PROVIDED_HOST_RESOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvidedHostResource(ComputationalResource newProvidedHostResource) {
		eSet(DeploymentPackage.Literals.HOSTING__PROVIDED_HOST_RESOURCE, newProvidedHostResource);
	}

} //HostingImpl
