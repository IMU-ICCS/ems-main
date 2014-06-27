/**
 */
package camel.deployment;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Communication Port Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.CommunicationPortInstance#getType <em>Type</em>}</li>
 *   <li>{@link camel.deployment.CommunicationPortInstance#getComponentInstance <em>Component Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getCommunicationPortInstance()
 * @model
 * @generated
 */
public interface CommunicationPortInstance extends CloudMLElement {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(CommunicationPort)
	 * @see camel.deployment.DeploymentPackage#getCommunicationPortInstance_Type()
	 * @model required="true"
	 * @generated
	 */
	CommunicationPort getType();

	/**
	 * Sets the value of the '{@link camel.deployment.CommunicationPortInstance#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(CommunicationPort value);

	/**
	 * Returns the value of the '<em><b>Component Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Instance</em>' reference.
	 * @see #setComponentInstance(ComponentInstance)
	 * @see camel.deployment.DeploymentPackage#getCommunicationPortInstance_ComponentInstance()
	 * @model required="true"
	 * @generated
	 */
	ComponentInstance getComponentInstance();

	/**
	 * Sets the value of the '{@link camel.deployment.CommunicationPortInstance#getComponentInstance <em>Component Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Instance</em>' reference.
	 * @see #getComponentInstance()
	 * @generated
	 */
	void setComponentInstance(ComponentInstance value);

} // CommunicationPortInstance
