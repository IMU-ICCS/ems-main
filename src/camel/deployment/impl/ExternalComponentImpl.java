/**
 */
package camel.deployment.impl;

import camel.deployment.DeploymentPackage;
import camel.deployment.ExternalComponent;

import camel.organisation.CloudProvider;
import camel.organisation.Location;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>External Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.ExternalComponentImpl#getProvider <em>Provider</em>}</li>
 *   <li>{@link camel.deployment.impl.ExternalComponentImpl#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExternalComponentImpl extends ComponentImpl implements ExternalComponent {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExternalComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.EXTERNAL_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloudProvider getProvider() {
		return (CloudProvider)eGet(DeploymentPackage.Literals.EXTERNAL_COMPONENT__PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvider(CloudProvider newProvider) {
		eSet(DeploymentPackage.Literals.EXTERNAL_COMPONENT__PROVIDER, newProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Location getLocation() {
		return (Location)eGet(DeploymentPackage.Literals.EXTERNAL_COMPONENT__LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(Location newLocation) {
		eSet(DeploymentPackage.Literals.EXTERNAL_COMPONENT__LOCATION, newLocation);
	}

} //ExternalComponentImpl
