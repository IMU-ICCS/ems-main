/**
 */
package camel.deployment;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Hosting Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.HostingInstance#getProvidedHostInstance <em>Provided Host Instance</em>}</li>
 *   <li>{@link camel.deployment.HostingInstance#getRequiredHostInstance <em>Required Host Instance</em>}</li>
 *   <li>{@link camel.deployment.HostingInstance#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getHostingInstance()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='containment_instance_correct_port_instance'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot containment_instance_correct_port_instance='\n\t\t\t\t\t\tprovidedHostInstance.type = type.providedHost\n\t\t\t\t\t\tand\n\t\t\t\t\t\trequiredHostInstance.type = type.requiredHost'"
 * @generated
 */
public interface HostingInstance extends CloudMLElement {
	/**
	 * Returns the value of the '<em><b>Provided Host Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Host Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Host Instance</em>' reference.
	 * @see #setProvidedHostInstance(ProvidedHostInstance)
	 * @see camel.deployment.DeploymentPackage#getHostingInstance_ProvidedHostInstance()
	 * @model required="true"
	 * @generated
	 */
	ProvidedHostInstance getProvidedHostInstance();

	/**
	 * Sets the value of the '{@link camel.deployment.HostingInstance#getProvidedHostInstance <em>Provided Host Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided Host Instance</em>' reference.
	 * @see #getProvidedHostInstance()
	 * @generated
	 */
	void setProvidedHostInstance(ProvidedHostInstance value);

	/**
	 * Returns the value of the '<em><b>Required Host Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Host Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Host Instance</em>' reference.
	 * @see #setRequiredHostInstance(RequiredHostInstance)
	 * @see camel.deployment.DeploymentPackage#getHostingInstance_RequiredHostInstance()
	 * @model required="true"
	 * @generated
	 */
	RequiredHostInstance getRequiredHostInstance();

	/**
	 * Sets the value of the '{@link camel.deployment.HostingInstance#getRequiredHostInstance <em>Required Host Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Host Instance</em>' reference.
	 * @see #getRequiredHostInstance()
	 * @generated
	 */
	void setRequiredHostInstance(RequiredHostInstance value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Hosting)
	 * @see camel.deployment.DeploymentPackage#getHostingInstance_Type()
	 * @model required="true"
	 * @generated
	 */
	Hosting getType();

	/**
	 * Sets the value of the '{@link camel.deployment.HostingInstance#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Hosting value);

} // HostingInstance
