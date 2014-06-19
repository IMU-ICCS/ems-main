/**
 */
package camel.cerif;

import camel.Action;

import java.util.Date;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Permission</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.cerif.Permission#getForRole <em>For Role</em>}</li>
 *   <li>{@link camel.cerif.Permission#getStart <em>Start</em>}</li>
 *   <li>{@link camel.cerif.Permission#getEnd <em>End</em>}</li>
 *   <li>{@link camel.cerif.Permission#getIssuedBy <em>Issued By</em>}</li>
 *   <li>{@link camel.cerif.Permission#getOnResource <em>On Resource</em>}</li>
 *   <li>{@link camel.cerif.Permission#getToAction <em>To Action</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.cerif.CerifPackage#getPermission()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Permission extends CDOObject {
	/**
	 * Returns the value of the '<em><b>For Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>For Role</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>For Role</em>' reference.
	 * @see #setForRole(Role)
	 * @see camel.cerif.CerifPackage#getPermission_ForRole()
	 * @model required="true"
	 * @generated
	 */
	Role getForRole();

	/**
	 * Sets the value of the '{@link camel.cerif.Permission#getForRole <em>For Role</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>For Role</em>' reference.
	 * @see #getForRole()
	 * @generated
	 */
	void setForRole(Role value);

	/**
	 * Returns the value of the '<em><b>Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start</em>' attribute.
	 * @see #setStart(Date)
	 * @see camel.cerif.CerifPackage#getPermission_Start()
	 * @model
	 * @generated
	 */
	Date getStart();

	/**
	 * Sets the value of the '{@link camel.cerif.Permission#getStart <em>Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start</em>' attribute.
	 * @see #getStart()
	 * @generated
	 */
	void setStart(Date value);

	/**
	 * Returns the value of the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End</em>' attribute.
	 * @see #setEnd(Date)
	 * @see camel.cerif.CerifPackage#getPermission_End()
	 * @model
	 * @generated
	 */
	Date getEnd();

	/**
	 * Sets the value of the '{@link camel.cerif.Permission#getEnd <em>End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End</em>' attribute.
	 * @see #getEnd()
	 * @generated
	 */
	void setEnd(Date value);

	/**
	 * Returns the value of the '<em><b>Issued By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Issued By</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Issued By</em>' reference.
	 * @see #setIssuedBy(Organization)
	 * @see camel.cerif.CerifPackage#getPermission_IssuedBy()
	 * @model required="true"
	 * @generated
	 */
	Organization getIssuedBy();

	/**
	 * Sets the value of the '{@link camel.cerif.Permission#getIssuedBy <em>Issued By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Issued By</em>' reference.
	 * @see #getIssuedBy()
	 * @generated
	 */
	void setIssuedBy(Organization value);

	/**
	 * Returns the value of the '<em><b>On Resource</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>On Resource</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Resource</em>' reference.
	 * @see #setOnResource(Resource)
	 * @see camel.cerif.CerifPackage#getPermission_OnResource()
	 * @model required="true"
	 * @generated
	 */
	Resource getOnResource();

	/**
	 * Sets the value of the '{@link camel.cerif.Permission#getOnResource <em>On Resource</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>On Resource</em>' reference.
	 * @see #getOnResource()
	 * @generated
	 */
	void setOnResource(Resource value);

	/**
	 * Returns the value of the '<em><b>To Action</b></em>' reference list.
	 * The list contents are of type {@link camel.Action}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To Action</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To Action</em>' reference list.
	 * @see camel.cerif.CerifPackage#getPermission_ToAction()
	 * @model required="true"
	 * @generated
	 */
	EList<Action> getToAction();

} // Permission
