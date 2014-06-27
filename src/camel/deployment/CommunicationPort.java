/**
 */
package camel.deployment;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Communication Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.CommunicationPort#getComponent <em>Component</em>}</li>
 *   <li>{@link camel.deployment.CommunicationPort#isIsLocal <em>Is Local</em>}</li>
 *   <li>{@link camel.deployment.CommunicationPort#getPortNumber <em>Port Number</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getCommunicationPort()
 * @model abstract="true"
 * @generated
 */
public interface CommunicationPort extends CloudMLElement {
	/**
	 * Returns the value of the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' reference.
	 * @see #setComponent(Component)
	 * @see camel.deployment.DeploymentPackage#getCommunicationPort_Component()
	 * @model required="true"
	 * @generated
	 */
	Component getComponent();

	/**
	 * Sets the value of the '{@link camel.deployment.CommunicationPort#getComponent <em>Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(Component value);

	/**
	 * Returns the value of the '<em><b>Is Local</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Local</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Local</em>' attribute.
	 * @see #setIsLocal(boolean)
	 * @see camel.deployment.DeploymentPackage#getCommunicationPort_IsLocal()
	 * @model required="true"
	 * @generated
	 */
	boolean isIsLocal();

	/**
	 * Sets the value of the '{@link camel.deployment.CommunicationPort#isIsLocal <em>Is Local</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Local</em>' attribute.
	 * @see #isIsLocal()
	 * @generated
	 */
	void setIsLocal(boolean value);

	/**
	 * Returns the value of the '<em><b>Port Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port Number</em>' attribute.
	 * @see #setPortNumber(int)
	 * @see camel.deployment.DeploymentPackage#getCommunicationPort_PortNumber()
	 * @model
	 * @generated
	 */
	int getPortNumber();

	/**
	 * Sets the value of the '{@link camel.deployment.CommunicationPort#getPortNumber <em>Port Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port Number</em>' attribute.
	 * @see #getPortNumber()
	 * @generated
	 */
	void setPortNumber(int value);

} // CommunicationPort
