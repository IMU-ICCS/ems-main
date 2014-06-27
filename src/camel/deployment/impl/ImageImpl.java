/**
 */
package camel.deployment.impl;

import camel.deployment.DeploymentPackage;
import camel.deployment.Image;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Image</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.ImageImpl#getOs <em>Os</em>}</li>
 *   <li>{@link camel.deployment.impl.ImageImpl#isIs64os <em>Is64os</em>}</li>
 *   <li>{@link camel.deployment.impl.ImageImpl#getImageId <em>Image Id</em>}</li>
 *   <li>{@link camel.deployment.impl.ImageImpl#getImageGroup <em>Image Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ImageImpl extends CloudMLElementImpl implements Image {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.IMAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOs() {
		return (String)eGet(DeploymentPackage.Literals.IMAGE__OS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOs(String newOs) {
		eSet(DeploymentPackage.Literals.IMAGE__OS, newOs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIs64os() {
		return (Boolean)eGet(DeploymentPackage.Literals.IMAGE__IS64OS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIs64os(boolean newIs64os) {
		eSet(DeploymentPackage.Literals.IMAGE__IS64OS, newIs64os);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImageId() {
		return (String)eGet(DeploymentPackage.Literals.IMAGE__IMAGE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImageId(String newImageId) {
		eSet(DeploymentPackage.Literals.IMAGE__IMAGE_ID, newImageId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImageGroup() {
		return (String)eGet(DeploymentPackage.Literals.IMAGE__IMAGE_GROUP, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImageGroup(String newImageGroup) {
		eSet(DeploymentPackage.Literals.IMAGE__IMAGE_GROUP, newImageGroup);
	}

} //ImageImpl
