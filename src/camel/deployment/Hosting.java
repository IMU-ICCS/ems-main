/**
 */
package camel.deployment;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Hosting</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.Hosting#getProvidedHost <em>Provided Host</em>}</li>
 *   <li>{@link camel.deployment.Hosting#getRequiredHost <em>Required Host</em>}</li>
 *   <li>{@link camel.deployment.Hosting#getRequiredHostResource <em>Required Host Resource</em>}</li>
 *   <li>{@link camel.deployment.Hosting#getProvidedHostResource <em>Provided Host Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getHosting()
 * @model
 * @generated
 */
public interface Hosting extends CloudMLElement {
	/**
	 * Returns the value of the '<em><b>Provided Host</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Host</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Host</em>' reference.
	 * @see #setProvidedHost(ProvidedHost)
	 * @see camel.deployment.DeploymentPackage#getHosting_ProvidedHost()
	 * @model required="true"
	 * @generated
	 */
	ProvidedHost getProvidedHost();

	/**
	 * Sets the value of the '{@link camel.deployment.Hosting#getProvidedHost <em>Provided Host</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided Host</em>' reference.
	 * @see #getProvidedHost()
	 * @generated
	 */
	void setProvidedHost(ProvidedHost value);

	/**
	 * Returns the value of the '<em><b>Required Host</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Host</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Host</em>' reference.
	 * @see #setRequiredHost(RequiredHost)
	 * @see camel.deployment.DeploymentPackage#getHosting_RequiredHost()
	 * @model required="true"
	 * @generated
	 */
	RequiredHost getRequiredHost();

	/**
	 * Sets the value of the '{@link camel.deployment.Hosting#getRequiredHost <em>Required Host</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Host</em>' reference.
	 * @see #getRequiredHost()
	 * @generated
	 */
	void setRequiredHost(RequiredHost value);

	/**
	 * Returns the value of the '<em><b>Required Host Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Host Resource</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Host Resource</em>' containment reference.
	 * @see #setRequiredHostResource(ComputationalResource)
	 * @see camel.deployment.DeploymentPackage#getHosting_RequiredHostResource()
	 * @model containment="true"
	 * @generated
	 */
	ComputationalResource getRequiredHostResource();

	/**
	 * Sets the value of the '{@link camel.deployment.Hosting#getRequiredHostResource <em>Required Host Resource</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Host Resource</em>' containment reference.
	 * @see #getRequiredHostResource()
	 * @generated
	 */
	void setRequiredHostResource(ComputationalResource value);

	/**
	 * Returns the value of the '<em><b>Provided Host Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Host Resource</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Host Resource</em>' containment reference.
	 * @see #setProvidedHostResource(ComputationalResource)
	 * @see camel.deployment.DeploymentPackage#getHosting_ProvidedHostResource()
	 * @model containment="true"
	 * @generated
	 */
	ComputationalResource getProvidedHostResource();

	/**
	 * Sets the value of the '{@link camel.deployment.Hosting#getProvidedHostResource <em>Provided Host Resource</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided Host Resource</em>' containment reference.
	 * @see #getProvidedHostResource()
	 * @generated
	 */
	void setProvidedHostResource(ComputationalResource value);

} // Hosting
