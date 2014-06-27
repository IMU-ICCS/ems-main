/**
 */
package camel.deployment;

import camel.DataObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Communication</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.Communication#getRequiredCommunication <em>Required Communication</em>}</li>
 *   <li>{@link camel.deployment.Communication#getProvidedCommunication <em>Provided Communication</em>}</li>
 *   <li>{@link camel.deployment.Communication#getRequiredPortResource <em>Required Port Resource</em>}</li>
 *   <li>{@link camel.deployment.Communication#getProvidedPortResource <em>Provided Port Resource</em>}</li>
 *   <li>{@link camel.deployment.Communication#getDataObject <em>Data Object</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getCommunication()
 * @model
 * @generated
 */
public interface Communication extends CloudMLElement {
	/**
	 * Returns the value of the '<em><b>Required Communication</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Communication</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Communication</em>' reference.
	 * @see #setRequiredCommunication(RequiredCommunication)
	 * @see camel.deployment.DeploymentPackage#getCommunication_RequiredCommunication()
	 * @model required="true"
	 * @generated
	 */
	RequiredCommunication getRequiredCommunication();

	/**
	 * Sets the value of the '{@link camel.deployment.Communication#getRequiredCommunication <em>Required Communication</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Communication</em>' reference.
	 * @see #getRequiredCommunication()
	 * @generated
	 */
	void setRequiredCommunication(RequiredCommunication value);

	/**
	 * Returns the value of the '<em><b>Provided Communication</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Communication</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Communication</em>' reference.
	 * @see #setProvidedCommunication(ProvidedCommunication)
	 * @see camel.deployment.DeploymentPackage#getCommunication_ProvidedCommunication()
	 * @model required="true"
	 * @generated
	 */
	ProvidedCommunication getProvidedCommunication();

	/**
	 * Sets the value of the '{@link camel.deployment.Communication#getProvidedCommunication <em>Provided Communication</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided Communication</em>' reference.
	 * @see #getProvidedCommunication()
	 * @generated
	 */
	void setProvidedCommunication(ProvidedCommunication value);

	/**
	 * Returns the value of the '<em><b>Required Port Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Port Resource</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Port Resource</em>' containment reference.
	 * @see #setRequiredPortResource(ComputationalResource)
	 * @see camel.deployment.DeploymentPackage#getCommunication_RequiredPortResource()
	 * @model containment="true"
	 * @generated
	 */
	ComputationalResource getRequiredPortResource();

	/**
	 * Sets the value of the '{@link camel.deployment.Communication#getRequiredPortResource <em>Required Port Resource</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Port Resource</em>' containment reference.
	 * @see #getRequiredPortResource()
	 * @generated
	 */
	void setRequiredPortResource(ComputationalResource value);

	/**
	 * Returns the value of the '<em><b>Provided Port Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Port Resource</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Port Resource</em>' containment reference.
	 * @see #setProvidedPortResource(ComputationalResource)
	 * @see camel.deployment.DeploymentPackage#getCommunication_ProvidedPortResource()
	 * @model containment="true"
	 * @generated
	 */
	ComputationalResource getProvidedPortResource();

	/**
	 * Sets the value of the '{@link camel.deployment.Communication#getProvidedPortResource <em>Provided Port Resource</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided Port Resource</em>' containment reference.
	 * @see #getProvidedPortResource()
	 * @generated
	 */
	void setProvidedPortResource(ComputationalResource value);

	/**
	 * Returns the value of the '<em><b>Data Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Object</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Object</em>' reference.
	 * @see #setDataObject(DataObject)
	 * @see camel.deployment.DeploymentPackage#getCommunication_DataObject()
	 * @model
	 * @generated
	 */
	DataObject getDataObject();

	/**
	 * Sets the value of the '{@link camel.deployment.Communication#getDataObject <em>Data Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Object</em>' reference.
	 * @see #getDataObject()
	 * @generated
	 */
	void setDataObject(DataObject value);

} // Communication
