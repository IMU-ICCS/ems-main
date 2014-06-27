/**
 */
package camel.deployment;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Communication Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.CommunicationInstance#getType <em>Type</em>}</li>
 *   <li>{@link camel.deployment.CommunicationInstance#getRequiredCommunicationInstance <em>Required Communication Instance</em>}</li>
 *   <li>{@link camel.deployment.CommunicationInstance#getProvidedCommunicationInstance <em>Provided Communication Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getCommunicationInstance()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='communication_instance_correct_port_instances'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot communication_instance_correct_port_instances='\n\t\t\t\t\t\trequiredCommunicationInstance.type = type.requiredCommunication\n\t\t\t\t\t\tand\n\t\t\t\t\t\tprovidedCommunicationInstance.type = type.providedCommunication'"
 * @generated
 */
public interface CommunicationInstance extends CloudMLElement {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Communication)
	 * @see camel.deployment.DeploymentPackage#getCommunicationInstance_Type()
	 * @model required="true"
	 * @generated
	 */
	Communication getType();

	/**
	 * Sets the value of the '{@link camel.deployment.CommunicationInstance#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Communication value);

	/**
	 * Returns the value of the '<em><b>Required Communication Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Communication Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Communication Instance</em>' reference.
	 * @see #setRequiredCommunicationInstance(RequiredCommunicationInstance)
	 * @see camel.deployment.DeploymentPackage#getCommunicationInstance_RequiredCommunicationInstance()
	 * @model required="true"
	 * @generated
	 */
	RequiredCommunicationInstance getRequiredCommunicationInstance();

	/**
	 * Sets the value of the '{@link camel.deployment.CommunicationInstance#getRequiredCommunicationInstance <em>Required Communication Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Communication Instance</em>' reference.
	 * @see #getRequiredCommunicationInstance()
	 * @generated
	 */
	void setRequiredCommunicationInstance(RequiredCommunicationInstance value);

	/**
	 * Returns the value of the '<em><b>Provided Communication Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Communication Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Communication Instance</em>' reference.
	 * @see #setProvidedCommunicationInstance(ProvidedCommunicationInstance)
	 * @see camel.deployment.DeploymentPackage#getCommunicationInstance_ProvidedCommunicationInstance()
	 * @model required="true"
	 * @generated
	 */
	ProvidedCommunicationInstance getProvidedCommunicationInstance();

	/**
	 * Sets the value of the '{@link camel.deployment.CommunicationInstance#getProvidedCommunicationInstance <em>Provided Communication Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided Communication Instance</em>' reference.
	 * @see #getProvidedCommunicationInstance()
	 * @generated
	 */
	void setProvidedCommunicationInstance(ProvidedCommunicationInstance value);

} // CommunicationInstance
