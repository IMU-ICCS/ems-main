/**
 */
package types.typesPaasage.impl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import types.typesPaasage.ActionType;
import types.typesPaasage.ActionTypes;
import types.typesPaasage.TypesPaasagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Action Types</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link types.typesPaasage.impl.ActionTypesImpl#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActionTypesImpl extends CDOObjectImpl implements ActionTypes {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionTypesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.ACTION_TYPES;
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
	public EList<ActionType> getTypes() {
		return (EList<ActionType>)eGet(TypesPaasagePackage.Literals.ACTION_TYPES__TYPES, true);
	}

} //ActionTypesImpl
