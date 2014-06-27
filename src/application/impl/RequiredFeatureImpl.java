/**
 */
package application.impl;

import application.ApplicationPackage;
import application.CloudML_Element;
import application.RequiredFeature;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Required Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.RequiredFeatureImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link application.impl.RequiredFeatureImpl#getProvidedBy <em>Provided By</em>}</li>
 *   <li>{@link application.impl.RequiredFeatureImpl#isRemote <em>Remote</em>}</li>
 *   <li>{@link application.impl.RequiredFeatureImpl#isOptional <em>Optional</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RequiredFeatureImpl extends CDOObjectImpl implements RequiredFeature {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequiredFeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.REQUIRED_FEATURE;
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
	public String getFeature() {
		return (String)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__FEATURE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeature(String newFeature) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__FEATURE, newFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloudML_Element getProvidedBy() {
		return (CloudML_Element)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__PROVIDED_BY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvidedBy(CloudML_Element newProvidedBy) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__PROVIDED_BY, newProvidedBy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRemote() {
		return (Boolean)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__REMOTE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemote(boolean newRemote) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__REMOTE, newRemote);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOptional() {
		return (Boolean)eGet(ApplicationPackage.Literals.REQUIRED_FEATURE__OPTIONAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOptional(boolean newOptional) {
		eSet(ApplicationPackage.Literals.REQUIRED_FEATURE__OPTIONAL, newOptional);
	}

} //RequiredFeatureImpl
