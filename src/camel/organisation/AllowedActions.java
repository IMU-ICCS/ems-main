/**
 */
package camel.organisation;

import camel.Action;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Allowed Actions</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.organisation.AllowedActions#getResourceClass <em>Resource Class</em>}</li>
 *   <li>{@link camel.organisation.AllowedActions#getActions <em>Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.organisation.OrganisationPackage#getAllowedActions()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface AllowedActions extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Resource Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Class</em>' reference.
	 * @see #setResourceClass(EClass)
	 * @see camel.organisation.OrganisationPackage#getAllowedActions_ResourceClass()
	 * @model required="true"
	 * @generated
	 */
	EClass getResourceClass();

	/**
	 * Sets the value of the '{@link camel.organisation.AllowedActions#getResourceClass <em>Resource Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Class</em>' reference.
	 * @see #getResourceClass()
	 * @generated
	 */
	void setResourceClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' reference list.
	 * The list contents are of type {@link camel.Action}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' reference list.
	 * @see camel.organisation.OrganisationPackage#getAllowedActions_Actions()
	 * @model required="true"
	 * @generated
	 */
	EList<Action> getActions();

} // AllowedActions
