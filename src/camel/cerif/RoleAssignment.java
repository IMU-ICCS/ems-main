/**
 */
package camel.cerif;

import java.util.Date;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Role Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.cerif.RoleAssignment#getOfUser <em>Of User</em>}</li>
 *   <li>{@link camel.cerif.RoleAssignment#getToRole <em>To Role</em>}</li>
 *   <li>{@link camel.cerif.RoleAssignment#getOfGroup <em>Of Group</em>}</li>
 *   <li>{@link camel.cerif.RoleAssignment#getStart <em>Start</em>}</li>
 *   <li>{@link camel.cerif.RoleAssignment#getEnd <em>End</em>}</li>
 *   <li>{@link camel.cerif.RoleAssignment#getAssignedOn <em>Assigned On</em>}</li>
 *   <li>{@link camel.cerif.RoleAssignment#getAssignedBy <em>Assigned By</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.cerif.CerifPackage#getRoleAssignment()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface RoleAssignment extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Of User</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Of User</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Of User</em>' reference.
	 * @see #setOfUser(User)
	 * @see camel.cerif.CerifPackage#getRoleAssignment_OfUser()
	 * @model
	 * @generated
	 */
	User getOfUser();

	/**
	 * Sets the value of the '{@link camel.cerif.RoleAssignment#getOfUser <em>Of User</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Of User</em>' reference.
	 * @see #getOfUser()
	 * @generated
	 */
	void setOfUser(User value);

	/**
	 * Returns the value of the '<em><b>To Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To Role</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To Role</em>' reference.
	 * @see #setToRole(Role)
	 * @see camel.cerif.CerifPackage#getRoleAssignment_ToRole()
	 * @model required="true"
	 * @generated
	 */
	Role getToRole();

	/**
	 * Sets the value of the '{@link camel.cerif.RoleAssignment#getToRole <em>To Role</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To Role</em>' reference.
	 * @see #getToRole()
	 * @generated
	 */
	void setToRole(Role value);

	/**
	 * Returns the value of the '<em><b>Of Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Of Group</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Of Group</em>' reference.
	 * @see #setOfGroup(UserGroup)
	 * @see camel.cerif.CerifPackage#getRoleAssignment_OfGroup()
	 * @model
	 * @generated
	 */
	UserGroup getOfGroup();

	/**
	 * Sets the value of the '{@link camel.cerif.RoleAssignment#getOfGroup <em>Of Group</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Of Group</em>' reference.
	 * @see #getOfGroup()
	 * @generated
	 */
	void setOfGroup(UserGroup value);

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
	 * @see camel.cerif.CerifPackage#getRoleAssignment_Start()
	 * @model
	 * @generated
	 */
	Date getStart();

	/**
	 * Sets the value of the '{@link camel.cerif.RoleAssignment#getStart <em>Start</em>}' attribute.
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
	 * @see camel.cerif.CerifPackage#getRoleAssignment_End()
	 * @model
	 * @generated
	 */
	Date getEnd();

	/**
	 * Sets the value of the '{@link camel.cerif.RoleAssignment#getEnd <em>End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End</em>' attribute.
	 * @see #getEnd()
	 * @generated
	 */
	void setEnd(Date value);

	/**
	 * Returns the value of the '<em><b>Assigned On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assigned On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assigned On</em>' attribute.
	 * @see #setAssignedOn(Date)
	 * @see camel.cerif.CerifPackage#getRoleAssignment_AssignedOn()
	 * @model required="true"
	 * @generated
	 */
	Date getAssignedOn();

	/**
	 * Sets the value of the '{@link camel.cerif.RoleAssignment#getAssignedOn <em>Assigned On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assigned On</em>' attribute.
	 * @see #getAssignedOn()
	 * @generated
	 */
	void setAssignedOn(Date value);

	/**
	 * Returns the value of the '<em><b>Assigned By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assigned By</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assigned By</em>' reference.
	 * @see #setAssignedBy(Organization)
	 * @see camel.cerif.CerifPackage#getRoleAssignment_AssignedBy()
	 * @model required="true"
	 * @generated
	 */
	Organization getAssignedBy();

	/**
	 * Sets the value of the '{@link camel.cerif.RoleAssignment#getAssignedBy <em>Assigned By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assigned By</em>' reference.
	 * @see #getAssignedBy()
	 * @generated
	 */
	void setAssignedBy(Organization value);

} // RoleAssignment
