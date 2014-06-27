/**
 */
package camel.security;

import camel.scalability.Property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.security.SecurityProperty#getDomain <em>Domain</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.security.SecurityPackage#getSecurityProperty()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Sec_Prop_Realized_Only_Certifiable'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Sec_Prop_Realized_Only_Certifiable='\n\t\t\tself.type = camel::scalability::PropertyType::ABSTRACT implies self.realizedBy->forAll(p | p.oclIsTypeOf(Certifiable))'"
 * @generated
 */
public interface SecurityProperty extends Property {
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
	 * @see camel.security.SecurityPackage#getSecurityProperty_Domain()
	 * @model required="true"
	 * @generated
	 */
	String getDomain();

	/**
	 * Sets the value of the '{@link camel.security.SecurityProperty#getDomain <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' attribute.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(String value);

} // SecurityProperty
