/**
 */
package camel.deployment;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Image</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.Image#getOs <em>Os</em>}</li>
 *   <li>{@link camel.deployment.Image#isIs64os <em>Is64os</em>}</li>
 *   <li>{@link camel.deployment.Image#getImageId <em>Image Id</em>}</li>
 *   <li>{@link camel.deployment.Image#getImageGroup <em>Image Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getImage()
 * @model
 * @generated
 */
public interface Image extends CloudMLElement {
	/**
	 * Returns the value of the '<em><b>Os</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Os</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Os</em>' attribute.
	 * @see #setOs(String)
	 * @see camel.deployment.DeploymentPackage#getImage_Os()
	 * @model
	 * @generated
	 */
	String getOs();

	/**
	 * Sets the value of the '{@link camel.deployment.Image#getOs <em>Os</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Os</em>' attribute.
	 * @see #getOs()
	 * @generated
	 */
	void setOs(String value);

	/**
	 * Returns the value of the '<em><b>Is64os</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is64os</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is64os</em>' attribute.
	 * @see #setIs64os(boolean)
	 * @see camel.deployment.DeploymentPackage#getImage_Is64os()
	 * @model default="true"
	 * @generated
	 */
	boolean isIs64os();

	/**
	 * Sets the value of the '{@link camel.deployment.Image#isIs64os <em>Is64os</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is64os</em>' attribute.
	 * @see #isIs64os()
	 * @generated
	 */
	void setIs64os(boolean value);

	/**
	 * Returns the value of the '<em><b>Image Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image Id</em>' attribute.
	 * @see #setImageId(String)
	 * @see camel.deployment.DeploymentPackage#getImage_ImageId()
	 * @model
	 * @generated
	 */
	String getImageId();

	/**
	 * Sets the value of the '{@link camel.deployment.Image#getImageId <em>Image Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image Id</em>' attribute.
	 * @see #getImageId()
	 * @generated
	 */
	void setImageId(String value);

	/**
	 * Returns the value of the '<em><b>Image Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image Group</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image Group</em>' attribute.
	 * @see #setImageGroup(String)
	 * @see camel.deployment.DeploymentPackage#getImage_ImageGroup()
	 * @model
	 * @generated
	 */
	String getImageGroup();

	/**
	 * Sets the value of the '{@link camel.deployment.Image#getImageGroup <em>Image Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image Group</em>' attribute.
	 * @see #getImageGroup()
	 * @generated
	 */
	void setImageGroup(String value);

} // Image
