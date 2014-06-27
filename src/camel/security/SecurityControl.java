/**
 */
package camel.security;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Control</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.security.SecurityControl#getId <em>Id</em>}</li>
 *   <li>{@link camel.security.SecurityControl#getDomain <em>Domain</em>}</li>
 *   <li>{@link camel.security.SecurityControl#getSpecification <em>Specification</em>}</li>
 *   <li>{@link camel.security.SecurityControl#getLinkToSecProp <em>Link To Sec Prop</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.security.SecurityPackage#getSecurityControl()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='SecurityControl_diff'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot SecurityControl_diff='\n\t\t\tSecurityControl.allInstances()->forAll(p1,p2 | p1 <> p2 implies (p1.id <> p2.id and p1.specification <> p2.specification))'"
 * @extends CDOObject
 * @generated
 */
public interface SecurityControl extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see camel.security.SecurityPackage#getSecurityControl_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link camel.security.SecurityControl#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' attribute.
	 * @see #setDomain(String)
	 * @see camel.security.SecurityPackage#getSecurityControl_Domain()
	 * @model required="true"
	 * @generated
	 */
	String getDomain();

	/**
	 * Sets the value of the '{@link camel.security.SecurityControl#getDomain <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' attribute.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(String value);

	/**
	 * Returns the value of the '<em><b>Specification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specification</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specification</em>' attribute.
	 * @see #setSpecification(String)
	 * @see camel.security.SecurityPackage#getSecurityControl_Specification()
	 * @model required="true"
	 * @generated
	 */
	String getSpecification();

	/**
	 * Sets the value of the '{@link camel.security.SecurityControl#getSpecification <em>Specification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specification</em>' attribute.
	 * @see #getSpecification()
	 * @generated
	 */
	void setSpecification(String value);

	/**
	 * Returns the value of the '<em><b>Link To Sec Prop</b></em>' reference list.
	 * The list contents are of type {@link camel.security.SecurityProperty}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link To Sec Prop</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link To Sec Prop</em>' reference list.
	 * @see camel.security.SecurityPackage#getSecurityControl_LinkToSecProp()
	 * @model
	 * @generated
	 */
	EList<SecurityProperty> getLinkToSecProp();

} // SecurityControl
