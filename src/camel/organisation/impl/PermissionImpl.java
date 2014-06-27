/**
 */
package camel.organisation.impl;

import camel.Action;

import camel.organisation.OrganisationPackage;
import camel.organisation.Organization;
import camel.organisation.Permission;
import camel.organisation.Resource;
import camel.organisation.Role;

import java.lang.reflect.InvocationTargetException;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Permission</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.organisation.impl.PermissionImpl#getForRole <em>For Role</em>}</li>
 *   <li>{@link camel.organisation.impl.PermissionImpl#getStart <em>Start</em>}</li>
 *   <li>{@link camel.organisation.impl.PermissionImpl#getEnd <em>End</em>}</li>
 *   <li>{@link camel.organisation.impl.PermissionImpl#getIssuedBy <em>Issued By</em>}</li>
 *   <li>{@link camel.organisation.impl.PermissionImpl#getOnResource <em>On Resource</em>}</li>
 *   <li>{@link camel.organisation.impl.PermissionImpl#getToAction <em>To Action</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PermissionImpl extends CDOObjectImpl implements Permission {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PermissionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrganisationPackage.Literals.PERMISSION;
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
	public Role getForRole() {
		return (Role)eGet(OrganisationPackage.Literals.PERMISSION__FOR_ROLE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForRole(Role newForRole) {
		eSet(OrganisationPackage.Literals.PERMISSION__FOR_ROLE, newForRole);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getStart() {
		return (Date)eGet(OrganisationPackage.Literals.PERMISSION__START, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStart(Date newStart) {
		eSet(OrganisationPackage.Literals.PERMISSION__START, newStart);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getEnd() {
		return (Date)eGet(OrganisationPackage.Literals.PERMISSION__END, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnd(Date newEnd) {
		eSet(OrganisationPackage.Literals.PERMISSION__END, newEnd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Organization getIssuedBy() {
		return (Organization)eGet(OrganisationPackage.Literals.PERMISSION__ISSUED_BY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIssuedBy(Organization newIssuedBy) {
		eSet(OrganisationPackage.Literals.PERMISSION__ISSUED_BY, newIssuedBy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource getOnResource() {
		return (Resource)eGet(OrganisationPackage.Literals.PERMISSION__ON_RESOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnResource(Resource newOnResource) {
		eSet(OrganisationPackage.Literals.PERMISSION__ON_RESOURCE, newOnResource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Action> getToAction() {
		return (EList<Action>)eGet(OrganisationPackage.Literals.PERMISSION__TO_ACTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkStartEndDates(final Permission this_) {
		System.out.println("CHECKING Permission_Start_Before_End: " + this + " " + this.getStart() + " " + this.getEnd()); java.util.Date date1 = this.getStart(); java.util.Date date2 = this.getEnd(); if (date1 == null || date2 == null || (date1 != null && date2 != null && date1.before(date2))) return Boolean.TRUE; return Boolean.FALSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case OrganisationPackage.PERMISSION___CHECK_START_END_DATES__PERMISSION:
				return checkStartEndDates((Permission)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //PermissionImpl
