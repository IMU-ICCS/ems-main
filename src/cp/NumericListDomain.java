/**
 */
package cp;

import org.eclipse.emf.common.util.EList;

import types.NumericValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Numeric List Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link cp.NumericListDomain#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see cp.CpPackage#getNumericListDomain()
 * @model
 * @generated
 */
public interface NumericListDomain extends NumericDomain {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link types.NumericValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see cp.CpPackage#getNumericListDomain_Values()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<NumericValue> getValues();

} // NumericListDomain
