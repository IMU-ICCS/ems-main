/**
 */
package eu.paasage.upperware.metamodel.cp;

import eu.paasage.upperware.metamodel.types.StringValueUpperware;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>List Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ListDomain#getValues <em>Values</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ListDomain#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getListDomain()
 * @model
 * @generated
 */
public interface ListDomain extends Domain {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.types.StringValueUpperware}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getListDomain_Values()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<StringValueUpperware> getValues();

	/**
	 * Returns the value of the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' reference.
	 * @see #setValue(StringValueUpperware)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getListDomain_Value()
	 * @model
	 * @generated
	 */
	StringValueUpperware getValue();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ListDomain#getValue <em>Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(StringValueUpperware value);

} // ListDomain
