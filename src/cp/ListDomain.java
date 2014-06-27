/**
 */
package cp;

import org.eclipse.emf.common.util.EList;

import types.StringValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>List Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link cp.ListDomain#getValues <em>Values</em>}</li>
 *   <li>{@link cp.ListDomain#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see cp.CpPackage#getListDomain()
 * @model
 * @generated
 */
public interface ListDomain extends Domain {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link types.StringValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see cp.CpPackage#getListDomain_Values()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<StringValue> getValues();

	/**
	 * Returns the value of the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' reference.
	 * @see #setValue(StringValue)
	 * @see cp.CpPackage#getListDomain_Value()
	 * @model
	 * @generated
	 */
	StringValue getValue();

	/**
	 * Sets the value of the '{@link cp.ListDomain#getValue <em>Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(StringValue value);

} // ListDomain
