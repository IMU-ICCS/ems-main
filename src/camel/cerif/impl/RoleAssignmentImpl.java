/**
 */
package camel.cerif.impl;

import camel.cerif.CerifPackage;
import camel.cerif.Organization;
import camel.cerif.Role;
import camel.cerif.RoleAssignment;
import camel.cerif.User;
import camel.cerif.UserGroup;

import java.util.Date;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Role Assignment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.cerif.impl.RoleAssignmentImpl#getOfUser <em>Of User</em>}</li>
 *   <li>{@link camel.cerif.impl.RoleAssignmentImpl#getToRole <em>To Role</em>}</li>
 *   <li>{@link camel.cerif.impl.RoleAssignmentImpl#getOfGroup <em>Of Group</em>}</li>
 *   <li>{@link camel.cerif.impl.RoleAssignmentImpl#getStart <em>Start</em>}</li>
 *   <li>{@link camel.cerif.impl.RoleAssignmentImpl#getEnd <em>End</em>}</li>
 *   <li>{@link camel.cerif.impl.RoleAssignmentImpl#getAssignedOn <em>Assigned On</em>}</li>
 *   <li>{@link camel.cerif.impl.RoleAssignmentImpl#getAssignedBy <em>Assigned By</em>}</li>
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
		return CerifPackage.Literals.ROLE_ASSIGNMENT;
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
		return (User)eGet(CerifPackage.Literals.ROLE_ASSIGNMENT__OF_USER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfUser(User newOfUser) {
		eSet(CerifPackage.Literals.ROLE_ASSIGNMENT__OF_USER, newOfUser);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role getToRole() {
		return (Role)eGet(CerifPackage.Literals.ROLE_ASSIGNMENT__TO_ROLE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToRole(Role newToRole) {
		eSet(CerifPackage.Literals.ROLE_ASSIGNMENT__TO_ROLE, newToRole);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserGroup getOfGroup() {
		return (UserGroup)eGet(CerifPackage.Literals.ROLE_ASSIGNMENT__OF_GROUP, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfGroup(UserGroup newOfGroup) {
		eSet(CerifPackage.Literals.ROLE_ASSIGNMENT__OF_GROUP, newOfGroup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getStart() {
		return (Date)eGet(CerifPackage.Literals.ROLE_ASSIGNMENT__START, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStart(Date newStart) {
		eSet(CerifPackage.Literals.ROLE_ASSIGNMENT__START, newStart);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getEnd() {
		return (Date)eGet(CerifPackage.Literals.ROLE_ASSIGNMENT__END, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnd(Date newEnd) {
		eSet(CerifPackage.Literals.ROLE_ASSIGNMENT__END, newEnd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getAssignedOn() {
		return (Date)eGet(CerifPackage.Literals.ROLE_ASSIGNMENT__ASSIGNED_ON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssignedOn(Date newAssignedOn) {
		eSet(CerifPackage.Literals.ROLE_ASSIGNMENT__ASSIGNED_ON, newAssignedOn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Organization getAssignedBy() {
		return (Organization)eGet(CerifPackage.Literals.ROLE_ASSIGNMENT__ASSIGNED_BY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssignedBy(Organization newAssignedBy) {
		eSet(CerifPackage.Literals.ROLE_ASSIGNMENT__ASSIGNED_BY, newAssignedBy);
	}

} //RoleAssignmentImpl
