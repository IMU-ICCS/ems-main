/**
 */
package types.typesPaasage.impl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import types.typesPaasage.ProviderType;
import types.typesPaasage.ProviderTypes;
import types.typesPaasage.TypesPaasagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Provider Types</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link types.typesPaasage.impl.ProviderTypesImpl#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProviderTypesImpl extends CDOObjectImpl implements ProviderTypes {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProviderTypesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.PROVIDER_TYPES;
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
	public EList<ProviderType> getTypes() {
		return (EList<ProviderType>)eGet(TypesPaasagePackage.Literals.PROVIDER_TYPES__TYPES, true);
	}

} //ProviderTypesImpl
