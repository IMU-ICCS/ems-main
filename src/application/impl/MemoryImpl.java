/**
 */
package application.impl;

import application.ApplicationPackage;
import application.Memory;

import org.eclipse.emf.ecore.EClass;

import types.typesPaasage.DataUnitEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Memory</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.MemoryImpl#getUnit <em>Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MemoryImpl extends ResourceImpl implements Memory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MemoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.MEMORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataUnitEnum getUnit() {
		return (DataUnitEnum)eGet(ApplicationPackage.Literals.MEMORY__UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnit(DataUnitEnum newUnit) {
		eSet(ApplicationPackage.Literals.MEMORY__UNIT, newUnit);
	}

} //MemoryImpl
