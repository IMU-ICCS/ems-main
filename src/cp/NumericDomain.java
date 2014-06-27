/**
 */
package cp;

import types.BasicTypeEnum;
import types.NumericValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Numeric Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link cp.NumericDomain#getType <em>Type</em>}</li>
 *   <li>{@link cp.NumericDomain#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see cp.CpPackage#getNumericDomain()
 * @model
 * @generated
 */
public interface NumericDomain extends Domain {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link types.BasicTypeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see types.BasicTypeEnum
	 * @see #setType(BasicTypeEnum)
	 * @see cp.CpPackage#getNumericDomain_Type()
	 * @model required="true"
	 * @generated
	 */
	BasicTypeEnum getType();

	/**
	 * Sets the value of the '{@link cp.NumericDomain#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see types.BasicTypeEnum
	 * @see #getType()
	 * @generated
	 */
	void setType(BasicTypeEnum value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' reference.
	 * @see #setValue(NumericValue)
	 * @see cp.CpPackage#getNumericDomain_Value()
	 * @model
	 * @generated
	 */
	NumericValue getValue();

	/**
	 * Sets the value of the '{@link cp.NumericDomain#getValue <em>Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(NumericValue value);

} // NumericDomain
