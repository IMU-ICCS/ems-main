/**
 */
package types.typesPaasage.impl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import types.typesPaasage.ApplicationComponentProfile;
import types.typesPaasage.ApplicationComponentProfiles;
import types.typesPaasage.TypesPaasagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application Component Profiles</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link types.typesPaasage.impl.ApplicationComponentProfilesImpl#getProfiles <em>Profiles</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ApplicationComponentProfilesImpl extends CDOObjectImpl implements ApplicationComponentProfiles {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicationComponentProfilesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILES;
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
	@SuppressWarnings("unchecked")
	public EList<ApplicationComponentProfile> getProfiles() {
		return (EList<ApplicationComponentProfile>)eGet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_PROFILES__PROFILES, true);
	}

} //ApplicationComponentProfilesImpl
