/**
 */
package types.typesPaasage.impl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import types.typesPaasage.FunctionType;
import types.typesPaasage.FunctionTypes;
import types.typesPaasage.TypesPaasagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Types</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link types.typesPaasage.impl.FunctionTypesImpl#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FunctionTypesImpl extends CDOObjectImpl implements FunctionTypes {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionTypesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.FUNCTION_TYPES;
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
	public EList<FunctionType> getTypes() {
		return (EList<FunctionType>)eGet(TypesPaasagePackage.Literals.FUNCTION_TYPES__TYPES, true);
	}

} //FunctionTypesImpl
