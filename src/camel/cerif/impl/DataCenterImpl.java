/**
 */
package camel.cerif.impl;

import camel.cerif.CerifPackage;
import camel.cerif.CloudProvider;
import camel.cerif.DataCenter;
import camel.cerif.Location;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Center</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.cerif.impl.DataCenterImpl#getName <em>Name</em>}</li>
 *   <li>{@link camel.cerif.impl.DataCenterImpl#getCodeName <em>Code Name</em>}</li>
 *   <li>{@link camel.cerif.impl.DataCenterImpl#getHasLocation <em>Has Location</em>}</li>
 *   <li>{@link camel.cerif.impl.DataCenterImpl#getOfCloudProvider <em>Of Cloud Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DataCenterImpl extends CDOObjectImpl implements DataCenter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataCenterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CerifPackage.Literals.DATA_CENTER;
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
	public String getName() {
		return (String)eGet(CerifPackage.Literals.DATA_CENTER__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(CerifPackage.Literals.DATA_CENTER__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCodeName() {
		return (String)eGet(CerifPackage.Literals.DATA_CENTER__CODE_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCodeName(String newCodeName) {
		eSet(CerifPackage.Literals.DATA_CENTER__CODE_NAME, newCodeName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Location getHasLocation() {
		return (Location)eGet(CerifPackage.Literals.DATA_CENTER__HAS_LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasLocation(Location newHasLocation) {
		eSet(CerifPackage.Literals.DATA_CENTER__HAS_LOCATION, newHasLocation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloudProvider getOfCloudProvider() {
		return (CloudProvider)eGet(CerifPackage.Literals.DATA_CENTER__OF_CLOUD_PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfCloudProvider(CloudProvider newOfCloudProvider) {
		eSet(CerifPackage.Literals.DATA_CENTER__OF_CLOUD_PROVIDER, newOfCloudProvider);
	}

} //DataCenterImpl
