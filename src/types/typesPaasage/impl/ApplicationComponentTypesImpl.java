/**
 */
package types.typesPaasage.impl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import types.typesPaasage.ApplicationComponentType;
import types.typesPaasage.ApplicationComponentTypes;
import types.typesPaasage.TypesPaasagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application Component Types</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link types.typesPaasage.impl.ApplicationComponentTypesImpl#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ApplicationComponentTypesImpl extends CDOObjectImpl implements ApplicationComponentTypes {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicationComponentTypesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.APPLICATION_COMPONENT_TYPES;
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
	public EList<ApplicationComponentType> getTypes() {
		return (EList<ApplicationComponentType>)eGet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_TYPES__TYPES, true);
	}

} //ApplicationComponentTypesImpl
