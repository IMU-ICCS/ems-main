/**
 */
package camel.cerif.impl;

import camel.Action;

import camel.cerif.CerifPackage;
import camel.cerif.Organization;
import camel.cerif.Permission;
import camel.cerif.Resource;
import camel.cerif.Role;

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
 *   <li>{@link camel.cerif.impl.PermissionImpl#getForRole <em>For Role</em>}</li>
 *   <li>{@link camel.cerif.impl.PermissionImpl#getStart <em>Start</em>}</li>
 *   <li>{@link camel.cerif.impl.PermissionImpl#getEnd <em>End</em>}</li>
 *   <li>{@link camel.cerif.impl.PermissionImpl#getIssuedBy <em>Issued By</em>}</li>
 *   <li>{@link camel.cerif.impl.PermissionImpl#getOnResource <em>On Resource</em>}</li>
 *   <li>{@link camel.cerif.impl.PermissionImpl#getToAction <em>To Action</em>}</li>
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
		return CerifPackage.Literals.PERMISSION;
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
		return (Role)eGet(CerifPackage.Literals.PERMISSION__FOR_ROLE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForRole(Role newForRole) {
		eSet(CerifPackage.Literals.PERMISSION__FOR_ROLE, newForRole);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getStart() {
		return (Date)eGet(CerifPackage.Literals.PERMISSION__START, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStart(Date newStart) {
		eSet(CerifPackage.Literals.PERMISSION__START, newStart);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getEnd() {
		return (Date)eGet(CerifPackage.Literals.PERMISSION__END, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnd(Date newEnd) {
		eSet(CerifPackage.Literals.PERMISSION__END, newEnd);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Organization getIssuedBy() {
		return (Organization)eGet(CerifPackage.Literals.PERMISSION__ISSUED_BY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIssuedBy(Organization newIssuedBy) {
		eSet(CerifPackage.Literals.PERMISSION__ISSUED_BY, newIssuedBy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource getOnResource() {
		return (Resource)eGet(CerifPackage.Literals.PERMISSION__ON_RESOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnResource(Resource newOnResource) {
		eSet(CerifPackage.Literals.PERMISSION__ON_RESOURCE, newOnResource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Action> getToAction() {
		return (EList<Action>)eGet(CerifPackage.Literals.PERMISSION__TO_ACTION, true);
	}

} //PermissionImpl
