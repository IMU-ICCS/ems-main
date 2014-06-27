/**
 */
package camel.organisation;

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
 *   <li>{@link camel.organisation.Permission#getForRole <em>For Role</em>}</li>
 *   <li>{@link camel.organisation.Permission#getStart <em>Start</em>}</li>
 *   <li>{@link camel.organisation.Permission#getEnd <em>End</em>}</li>
 *   <li>{@link camel.organisation.Permission#getIssuedBy <em>Issued By</em>}</li>
 *   <li>{@link camel.organisation.Permission#getOnResource <em>On Resource</em>}</li>
 *   <li>{@link camel.organisation.Permission#getToAction <em>To Action</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.organisation.OrganisationPackage#getPermission()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Actions_Allowed'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Actions_Allowed='\n\t\t\t(self.onResource.oclIsTypeOf(ResourceGroup) and self.onResource.oclAsType(ResourceGroup).allowsActionsOnResources(self.toAction)) or (not(self.onResource.oclIsTypeOf(ResourceGroup)) and self.onResource.oclAsType(Resource).allowsActions(self.toAction))'"
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
	 * @see camel.organisation.OrganisationPackage#getPermission_ForRole()
	 * @model required="true"
	 * @generated
	 */
	Role getForRole();

	/**
	 * Sets the value of the '{@link camel.organisation.Permission#getForRole <em>For Role</em>}' reference.
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
	 * @see camel.organisation.OrganisationPackage#getPermission_Start()
	 * @model required="true"
	 * @generated
	 */
	Date getStart();

	/**
	 * Sets the value of the '{@link camel.organisation.Permission#getStart <em>Start</em>}' attribute.
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
	 * @see camel.organisation.OrganisationPackage#getPermission_End()
	 * @model
	 * @generated
	 */
	Date getEnd();

	/**
	 * Sets the value of the '{@link camel.organisation.Permission#getEnd <em>End</em>}' attribute.
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
	 * @see camel.organisation.OrganisationPackage#getPermission_IssuedBy()
	 * @model required="true"
	 * @generated
	 */
	Organization getIssuedBy();

	/**
	 * Sets the value of the '{@link camel.organisation.Permission#getIssuedBy <em>Issued By</em>}' reference.
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
	 * @see camel.organisation.OrganisationPackage#getPermission_OnResource()
	 * @model required="true"
	 * @generated
	 */
	Resource getOnResource();

	/**
	 * Sets the value of the '{@link camel.organisation.Permission#getOnResource <em>On Resource</em>}' reference.
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
	 * @see camel.organisation.OrganisationPackage#getPermission_ToAction()
	 * @model required="true"
	 * @generated
	 */
	EList<Action> getToAction();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model thisRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"CHECKING Permission_Start_Before_End: \" + this + \" \" + this.getStart() + \" \" + this.getEnd()); java.util.Date date1 = this.getStart(); java.util.Date date2 = this.getEnd(); if (date1 == null || date2 == null || (date1 != null && date2 != null && date1.before(date2))) return Boolean.TRUE; return Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkStartEndDates(Permission this_);

} // Permission
