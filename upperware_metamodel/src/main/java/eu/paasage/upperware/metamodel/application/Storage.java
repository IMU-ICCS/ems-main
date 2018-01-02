/**
 */
package eu.paasage.upperware.metamodel.application;

import eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Storage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.Storage#getUnit <em>Unit</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getStorage()
 * @model
 * @generated
 */
public interface Storage extends ResourceUpperware {
	/**
	 * Returns the value of the '<em><b>Unit</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum
	 * @see #setUnit(DataUnitEnum)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getStorage_Unit()
	 * @model
	 * @generated
	 */
	DataUnitEnum getUnit();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.Storage#getUnit <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(DataUnitEnum value);

} // Storage
