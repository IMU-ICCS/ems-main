/**
 */
package types.typesPaasage.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import types.typesPaasage.ApplicationComponentProfile;
import types.typesPaasage.ApplicationComponentType;
import types.typesPaasage.TypesPaasagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application Component Profile</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link types.typesPaasage.impl.ApplicationComponentProfileImpl#getName <em>Name</em>}</li>
 *   <li>{@link types.typesPaasage.impl.ApplicationComponentProfileImpl#getVers <em>Vers</em>}</li>
 *   <li>{@link types.typesPaasage.impl.ApplicationComponentProfileImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ApplicationComponentProfileImpl extends CDOObjectImpl implements ApplicationComponentProfile {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicationComponentProfileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE;
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
		return (String)eGet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVers() {
		return (String)eGet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__VERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVers(String newVers) {
		eSet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__VERS, newVers);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationComponentType getType() {
		return (ApplicationComponentType)eGet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ApplicationComponentType newType) {
		eSet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILE__TYPE, newType);
	}

} //ApplicationComponentProfileImpl
