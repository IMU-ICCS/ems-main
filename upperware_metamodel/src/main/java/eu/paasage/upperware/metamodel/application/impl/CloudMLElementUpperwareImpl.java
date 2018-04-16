/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.CloudMLElementUpperware;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cloud ML Element Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.CloudMLElementUpperwareImpl#getCloudMLId <em>Cloud ML Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class CloudMLElementUpperwareImpl extends CDOObjectImpl implements CloudMLElementUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CloudMLElementUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.CLOUD_ML_ELEMENT_UPPERWARE;
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
		return (String)eGet(ApplicationPackage.Literals.CLOUD_ML_ELEMENT_UPPERWARE__CLOUD_ML_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCloudMLId(String newCloudMLId) {
		eSet(ApplicationPackage.Literals.CLOUD_ML_ELEMENT_UPPERWARE__CLOUD_ML_ID, newCloudMLId);
	}

} //CloudMLElementUpperwareImpl
