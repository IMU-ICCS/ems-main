/**
 */
package camel.deployment;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Required Communication</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.RequiredCommunication#isIsMandatory <em>Is Mandatory</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getRequiredCommunication()
 * @model
 * @generated
 */
public interface RequiredCommunication extends CommunicationPort {
	/**
	 * Returns the value of the '<em><b>Is Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Mandatory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Mandatory</em>' attribute.
	 * @see #setIsMandatory(boolean)
	 * @see camel.deployment.DeploymentPackage#getRequiredCommunication_IsMandatory()
	 * @model required="true"
	 * @generated
	 */
	boolean isIsMandatory();

	/**
	 * Sets the value of the '{@link camel.deployment.RequiredCommunication#isIsMandatory <em>Is Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Mandatory</em>' attribute.
	 * @see #isIsMandatory()
	 * @generated
	 */
	void setIsMandatory(boolean value);

} // RequiredCommunication
