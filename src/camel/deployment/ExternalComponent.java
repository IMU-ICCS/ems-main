/**
 */
package camel.deployment;

import camel.organisation.CloudProvider;
import camel.organisation.Location;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.ExternalComponent#getProvider <em>Provider</em>}</li>
 *   <li>{@link camel.deployment.ExternalComponent#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getExternalComponent()
 * @model
 * @generated
 */
public interface ExternalComponent extends Component {
	/**
	 * Returns the value of the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider</em>' reference.
	 * @see #setProvider(CloudProvider)
	 * @see camel.deployment.DeploymentPackage#getExternalComponent_Provider()
	 * @model required="true"
	 * @generated
	 */
	CloudProvider getProvider();

	/**
	 * Sets the value of the '{@link camel.deployment.ExternalComponent#getProvider <em>Provider</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' reference.
	 * @see #getProvider()
	 * @generated
	 */
	void setProvider(CloudProvider value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' reference.
	 * @see #setLocation(Location)
	 * @see camel.deployment.DeploymentPackage#getExternalComponent_Location()
	 * @model required="true"
	 * @generated
	 */
	Location getLocation();

	/**
	 * Sets the value of the '{@link camel.deployment.ExternalComponent#getLocation <em>Location</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' reference.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(Location value);

} // ExternalComponent
