/**
 */
package camel.organisation.impl;

import camel.organisation.OrganisationPackage;
import camel.organisation.Organization;
import camel.organisation.Role;
import camel.organisation.RoleAssignment;
import camel.organisation.User;
import camel.organisation.UserGroup;

import java.lang.reflect.InvocationTargetException;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Role Assignment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.organisation.impl.RoleAssignmentImpl#getOfUser <em>Of User</em>}</li>
 *   <li>{@link camel.organisation.impl.RoleAssignmentImpl#getToRole <em>To Role</em>}</li>
 *   <li>{@link camel.organisation.impl.RoleAssignmentImpl#getOfGroup <em>Of Group</em>}</li>
 *   <li>{@link camel.organisation.impl.RoleAssignmentImpl#getStart <em>Start</em>}</li>
 *   <li>{@link camel.organisation.impl.RoleAssignmentImpl#getEnd <em>End</em>}</li>
 *   <li>{@link camel.organisation.impl.RoleAssignmentImpl#getAssignedOn <em>Assigned On</em>}</li>
 *   <li>{@link camel.organisation.impl.RoleAssignmentImpl#getAssignedBy <em>Assigned By</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RoleAssignmentImpl extends CDOObjectImpl implements RoleAssignment {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RoleAssignmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrganisationPackage.Literals.ROLE_ASSIGNMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User getOfUser() {
		return (User)eGet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__OF_USER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfUser(User newOfUser) {
		eSet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__OF_USER, newOfUser);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role getToRole() {
		return (Role)eGet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__TO_ROLE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToRole(Role newToRole) {
		eSet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__TO_ROLE, newToRole);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserGroup getOfGroup() {
		return (UserGroup)eGet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__OF_GROUP, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfGroup(UserGroup newOfGroup) {
		eSet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__OF_GROUP, newOfGroup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getStart() {
		return (Date)eGet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__START, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStart(Date newStart) {
		eSet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__START, newStart);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getEnd() {
		return (Date)eGet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__END, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnd(Date newEnd) {
		eSet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__END, newEnd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getAssignedOn() {
		return (Date)eGet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__ASSIGNED_ON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssignedOn(Date newAssignedOn) {
		eSet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__ASSIGNED_ON, newAssignedOn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Organization getAssignedBy() {
		return (Organization)eGet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__ASSIGNED_BY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssignedBy(Organization newAssignedBy) {
		eSet(OrganisationPackage.Literals.ROLE_ASSIGNMENT__ASSIGNED_BY, newAssignedBy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkAssignedOnDates(final RoleAssignment this_) {
		System.out.println("CHECKING Assignment_Assigned_Before_Start: " + this + " " + this.getStart() + " " + this.getEnd() + " " + this.getAssignedOn()); java.util.Date date1 = this.getStart(); java.util.Date date2 = this.getEnd(); java.util.Date date = this.getAssignedOn(); if (date == null) return Boolean.TRUE; else if (date1 != null){ if (date.equals(date1) || date.before(date1)) return Boolean.TRUE; else return Boolean.FALSE;} else if (date2 != null && date.before(date2)) return Boolean.TRUE; return Boolean.FALSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkStartEndDates(final RoleAssignment this_) {
		System.out.println("CHECKING Assignment_Start_Before_End: " + this + " " + this.getStart() + " " + this.getEnd() + " " + this.getAssignedOn()); java.util.Date date1 = this.getStart(); java.util.Date date2 = this.getEnd(); if (date1 == null || date2 == null || (date1 != null && date2 != null && date1.before(date2))) return Boolean.TRUE; return Boolean.FALSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case OrganisationPackage.ROLE_ASSIGNMENT___CHECK_ASSIGNED_ON_DATES__ROLEASSIGNMENT:
				return checkAssignedOnDates((RoleAssignment)arguments.get(0));
			case OrganisationPackage.ROLE_ASSIGNMENT___CHECK_START_END_DATES__ROLEASSIGNMENT:
				return checkStartEndDates((RoleAssignment)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //RoleAssignmentImpl
