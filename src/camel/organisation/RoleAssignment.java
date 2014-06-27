/**
 */
package camel.organisation;

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
 *   <li>{@link camel.organisation.RoleAssignment#getOfUser <em>Of User</em>}</li>
 *   <li>{@link camel.organisation.RoleAssignment#getToRole <em>To Role</em>}</li>
 *   <li>{@link camel.organisation.RoleAssignment#getOfGroup <em>Of Group</em>}</li>
 *   <li>{@link camel.organisation.RoleAssignment#getStart <em>Start</em>}</li>
 *   <li>{@link camel.organisation.RoleAssignment#getEnd <em>End</em>}</li>
 *   <li>{@link camel.organisation.RoleAssignment#getAssignedOn <em>Assigned On</em>}</li>
 *   <li>{@link camel.organisation.RoleAssignment#getAssignedBy <em>Assigned By</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.organisation.OrganisationPackage#getRoleAssignment()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='At_least_User_or_Group SameRoleConcurrentAssignment UserWorksForOrganization'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot At_least_User_or_Group='\n\t\t\tself.ofUser <> null or self.ofGroup <> null' SameRoleConcurrentAssignment='\n\t\t\tRoleAssignment.allInstances()->forAll(p1, p2 |p1 <> p2  and p1.assignedBy = p2.assignedBy and (p1.ofUser = p2.ofUser or p1.ofGroup = p2.ofGroup) implies p1.toRole <> p2.toRole  )' UserWorksForOrganization='\n\t\t\t(self.ofUser = null or (self.ofUser <> null and self.ofUser.worksFor->includes(self.assignedBy)) or (self.ofGroup <> null and self.ofGroup.member->forAll(worksFor->includes(self.assignedBy))))'"
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
	 * @see camel.organisation.OrganisationPackage#getRoleAssignment_OfUser()
	 * @model
	 * @generated
	 */
	User getOfUser();

	/**
	 * Sets the value of the '{@link camel.organisation.RoleAssignment#getOfUser <em>Of User</em>}' reference.
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
	 * @see camel.organisation.OrganisationPackage#getRoleAssignment_ToRole()
	 * @model required="true"
	 * @generated
	 */
	Role getToRole();

	/**
	 * Sets the value of the '{@link camel.organisation.RoleAssignment#getToRole <em>To Role</em>}' reference.
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
	 * @see camel.organisation.OrganisationPackage#getRoleAssignment_OfGroup()
	 * @model
	 * @generated
	 */
	UserGroup getOfGroup();

	/**
	 * Sets the value of the '{@link camel.organisation.RoleAssignment#getOfGroup <em>Of Group</em>}' reference.
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
	 * @see camel.organisation.OrganisationPackage#getRoleAssignment_Start()
	 * @model
	 * @generated
	 */
	Date getStart();

	/**
	 * Sets the value of the '{@link camel.organisation.RoleAssignment#getStart <em>Start</em>}' attribute.
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
	 * @see camel.organisation.OrganisationPackage#getRoleAssignment_End()
	 * @model
	 * @generated
	 */
	Date getEnd();

	/**
	 * Sets the value of the '{@link camel.organisation.RoleAssignment#getEnd <em>End</em>}' attribute.
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
	 * @see camel.organisation.OrganisationPackage#getRoleAssignment_AssignedOn()
	 * @model required="true"
	 * @generated
	 */
	Date getAssignedOn();

	/**
	 * Sets the value of the '{@link camel.organisation.RoleAssignment#getAssignedOn <em>Assigned On</em>}' attribute.
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
	 * @see camel.organisation.OrganisationPackage#getRoleAssignment_AssignedBy()
	 * @model required="true"
	 * @generated
	 */
	Organization getAssignedBy();

	/**
	 * Sets the value of the '{@link camel.organisation.RoleAssignment#getAssignedBy <em>Assigned By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assigned By</em>' reference.
	 * @see #getAssignedBy()
	 * @generated
	 */
	void setAssignedBy(Organization value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model thisRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"CHECKING Assignment_Assigned_Before_Start: \" + this + \" \" + this.getStart() + \" \" + this.getEnd() + \" \" + this.getAssignedOn()); java.util.Date date1 = this.getStart(); java.util.Date date2 = this.getEnd(); java.util.Date date = this.getAssignedOn(); if (date == null) return Boolean.TRUE; else if (date1 != null){ if (date.equals(date1) || date.before(date1)) return Boolean.TRUE; else return Boolean.FALSE;} else if (date2 != null && date.before(date2)) return Boolean.TRUE; return Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkAssignedOnDates(RoleAssignment this_);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model thisRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"CHECKING Assignment_Start_Before_End: \" + this + \" \" + this.getStart() + \" \" + this.getEnd() + \" \" + this.getAssignedOn()); java.util.Date date1 = this.getStart(); java.util.Date date2 = this.getEnd(); if (date1 == null || date2 == null || (date1 != null && date2 != null && date1.before(date2))) return Boolean.TRUE; return Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkStartEndDates(RoleAssignment this_);

} // RoleAssignment
