/**
 */
package application.impl;

import application.ApplicationPackage;
import application.Storage;

import org.eclipse.emf.ecore.EClass;

import types.typesPaasage.DataUnitEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Storage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.StorageImpl#getUnit <em>Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StorageImpl extends ResourceImpl implements Storage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StorageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.STORAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataUnitEnum getUnit() {
		return (DataUnitEnum)eGet(ApplicationPackage.Literals.STORAGE__UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnit(DataUnitEnum newUnit) {
		eSet(ApplicationPackage.Literals.STORAGE__UNIT, newUnit);
	}

} //StorageImpl
