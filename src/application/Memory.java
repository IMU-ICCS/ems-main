/**
 */
package application;

import types.typesPaasage.DataUnitEnum;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Memory</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link application.Memory#getUnit <em>Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see application.ApplicationPackage#getMemory()
 * @model
 * @generated
 */
public interface Memory extends Resource {
	/**
	 * Returns the value of the '<em><b>Unit</b></em>' attribute.
	 * The literals are from the enumeration {@link types.typesPaasage.DataUnitEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' attribute.
	 * @see types.typesPaasage.DataUnitEnum
	 * @see #setUnit(DataUnitEnum)
	 * @see application.ApplicationPackage#getMemory_Unit()
	 * @model
	 * @generated
	 */
	DataUnitEnum getUnit();

	/**
	 * Sets the value of the '{@link application.Memory#getUnit <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' attribute.
	 * @see types.typesPaasage.DataUnitEnum
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(DataUnitEnum value);

} // Memory
