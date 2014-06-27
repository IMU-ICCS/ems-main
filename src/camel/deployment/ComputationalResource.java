/**
 */
package camel.deployment;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Computational Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.ComputationalResource#getDownloadCommand <em>Download Command</em>}</li>
 *   <li>{@link camel.deployment.ComputationalResource#getUploadCommand <em>Upload Command</em>}</li>
 *   <li>{@link camel.deployment.ComputationalResource#getInstallCommand <em>Install Command</em>}</li>
 *   <li>{@link camel.deployment.ComputationalResource#getConfigureCommand <em>Configure Command</em>}</li>
 *   <li>{@link camel.deployment.ComputationalResource#getStartCommand <em>Start Command</em>}</li>
 *   <li>{@link camel.deployment.ComputationalResource#getStopCommand <em>Stop Command</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getComputationalResource()
 * @model
 * @generated
 */
public interface ComputationalResource extends CloudMLElement {
	/**
	 * Returns the value of the '<em><b>Download Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Download Command</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Download Command</em>' attribute.
	 * @see #setDownloadCommand(String)
	 * @see camel.deployment.DeploymentPackage#getComputationalResource_DownloadCommand()
	 * @model
	 * @generated
	 */
	String getDownloadCommand();

	/**
	 * Sets the value of the '{@link camel.deployment.ComputationalResource#getDownloadCommand <em>Download Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Download Command</em>' attribute.
	 * @see #getDownloadCommand()
	 * @generated
	 */
	void setDownloadCommand(String value);

	/**
	 * Returns the value of the '<em><b>Upload Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upload Command</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upload Command</em>' attribute.
	 * @see #setUploadCommand(String)
	 * @see camel.deployment.DeploymentPackage#getComputationalResource_UploadCommand()
	 * @model
	 * @generated
	 */
	String getUploadCommand();

	/**
	 * Sets the value of the '{@link camel.deployment.ComputationalResource#getUploadCommand <em>Upload Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upload Command</em>' attribute.
	 * @see #getUploadCommand()
	 * @generated
	 */
	void setUploadCommand(String value);

	/**
	 * Returns the value of the '<em><b>Install Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Install Command</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Install Command</em>' attribute.
	 * @see #setInstallCommand(String)
	 * @see camel.deployment.DeploymentPackage#getComputationalResource_InstallCommand()
	 * @model
	 * @generated
	 */
	String getInstallCommand();

	/**
	 * Sets the value of the '{@link camel.deployment.ComputationalResource#getInstallCommand <em>Install Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Install Command</em>' attribute.
	 * @see #getInstallCommand()
	 * @generated
	 */
	void setInstallCommand(String value);

	/**
	 * Returns the value of the '<em><b>Configure Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configure Command</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configure Command</em>' attribute.
	 * @see #setConfigureCommand(String)
	 * @see camel.deployment.DeploymentPackage#getComputationalResource_ConfigureCommand()
	 * @model
	 * @generated
	 */
	String getConfigureCommand();

	/**
	 * Sets the value of the '{@link camel.deployment.ComputationalResource#getConfigureCommand <em>Configure Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configure Command</em>' attribute.
	 * @see #getConfigureCommand()
	 * @generated
	 */
	void setConfigureCommand(String value);

	/**
	 * Returns the value of the '<em><b>Start Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Command</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Command</em>' attribute.
	 * @see #setStartCommand(String)
	 * @see camel.deployment.DeploymentPackage#getComputationalResource_StartCommand()
	 * @model
	 * @generated
	 */
	String getStartCommand();

	/**
	 * Sets the value of the '{@link camel.deployment.ComputationalResource#getStartCommand <em>Start Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Command</em>' attribute.
	 * @see #getStartCommand()
	 * @generated
	 */
	void setStartCommand(String value);

	/**
	 * Returns the value of the '<em><b>Stop Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stop Command</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stop Command</em>' attribute.
	 * @see #setStopCommand(String)
	 * @see camel.deployment.DeploymentPackage#getComputationalResource_StopCommand()
	 * @model
	 * @generated
	 */
	String getStopCommand();

	/**
	 * Sets the value of the '{@link camel.deployment.ComputationalResource#getStopCommand <em>Stop Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stop Command</em>' attribute.
	 * @see #getStopCommand()
	 * @generated
	 */
	void setStopCommand(String value);

} // ComputationalResource
