/**
 */
package application;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

import types.typesPaasage.ActionType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link application.Action#getParameters <em>Parameters</em>}</li>
 *   <li>{@link application.Action#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see application.ApplicationPackage#getAction()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Action extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' attribute list.
	 * @see application.ApplicationPackage#getAction_Parameters()
	 * @model
	 * @generated
	 */
	EList<String> getParameters();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(ActionType)
	 * @see application.ApplicationPackage#getAction_Type()
	 * @model required="true"
	 * @generated
	 */
	ActionType getType();

	/**
	 * Sets the value of the '{@link application.Action#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ActionType value);

} // Action
