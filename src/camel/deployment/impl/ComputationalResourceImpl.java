/**
 */
package camel.deployment.impl;

import camel.deployment.ComputationalResource;
import camel.deployment.DeploymentPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Computational Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.ComputationalResourceImpl#getDownloadCommand <em>Download Command</em>}</li>
 *   <li>{@link camel.deployment.impl.ComputationalResourceImpl#getUploadCommand <em>Upload Command</em>}</li>
 *   <li>{@link camel.deployment.impl.ComputationalResourceImpl#getInstallCommand <em>Install Command</em>}</li>
 *   <li>{@link camel.deployment.impl.ComputationalResourceImpl#getConfigureCommand <em>Configure Command</em>}</li>
 *   <li>{@link camel.deployment.impl.ComputationalResourceImpl#getStartCommand <em>Start Command</em>}</li>
 *   <li>{@link camel.deployment.impl.ComputationalResourceImpl#getStopCommand <em>Stop Command</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComputationalResourceImpl extends CloudMLElementImpl implements ComputationalResource {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComputationalResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDownloadCommand() {
		return (String)eGet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__DOWNLOAD_COMMAND, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDownloadCommand(String newDownloadCommand) {
		eSet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__DOWNLOAD_COMMAND, newDownloadCommand);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUploadCommand() {
		return (String)eGet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__UPLOAD_COMMAND, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUploadCommand(String newUploadCommand) {
		eSet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__UPLOAD_COMMAND, newUploadCommand);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInstallCommand() {
		return (String)eGet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__INSTALL_COMMAND, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstallCommand(String newInstallCommand) {
		eSet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__INSTALL_COMMAND, newInstallCommand);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConfigureCommand() {
		return (String)eGet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__CONFIGURE_COMMAND, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfigureCommand(String newConfigureCommand) {
		eSet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__CONFIGURE_COMMAND, newConfigureCommand);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStartCommand() {
		return (String)eGet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__START_COMMAND, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartCommand(String newStartCommand) {
		eSet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__START_COMMAND, newStartCommand);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStopCommand() {
		return (String)eGet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__STOP_COMMAND, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStopCommand(String newStopCommand) {
		eSet(DeploymentPackage.Literals.COMPUTATIONAL_RESOURCE__STOP_COMMAND, newStopCommand);
	}

} //ComputationalResourceImpl
