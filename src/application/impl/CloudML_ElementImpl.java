/**
 */
package application.impl;

import application.ApplicationPackage;
import application.CloudML_Element;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cloud ML Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.CloudML_ElementImpl#getCloudMLId <em>Cloud ML Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class CloudML_ElementImpl extends CDOObjectImpl implements CloudML_Element {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CloudML_ElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.CLOUD_ML_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCloudMLId() {
		return (String)eGet(ApplicationPackage.Literals.CLOUD_ML_ELEMENT__CLOUD_ML_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCloudMLId(String newCloudMLId) {
		eSet(ApplicationPackage.Literals.CLOUD_ML_ELEMENT__CLOUD_ML_ID, newCloudMLId);
	}

} //CloudML_ElementImpl
