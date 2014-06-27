/**
 */
package camel.provider;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Clone</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.provider.Clone#getName <em>Name</em>}</li>
 *   <li>{@link camel.provider.Clone#getSubClones <em>Sub Clones</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.provider.ProviderPackage#getClone()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Clone extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see camel.provider.ProviderPackage#getClone_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.provider.Clone#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Sub Clones</b></em>' reference list.
	 * The list contents are of type {@link camel.provider.Clone}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Clones</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Clones</em>' reference list.
	 * @see camel.provider.ProviderPackage#getClone_SubClones()
	 * @model
	 * @generated
	 */
	EList<Clone> getSubClones();

} // Clone
