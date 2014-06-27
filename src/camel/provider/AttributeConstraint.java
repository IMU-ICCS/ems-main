/**
 */
package camel.provider;

import camel.type.Value;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.provider.AttributeConstraint#getFrom <em>From</em>}</li>
 *   <li>{@link camel.provider.AttributeConstraint#getTo <em>To</em>}</li>
 *   <li>{@link camel.provider.AttributeConstraint#getFromValue <em>From Value</em>}</li>
 *   <li>{@link camel.provider.AttributeConstraint#getToValue <em>To Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.provider.ProviderPackage#getAttributeConstraint()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='AttributeConstraint_correct_values differentAttrs_in_Constraint'"
 *        annotation="Ecore attributeConstraintValidation='atLeastOneDefinied'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot AttributeConstraint_correct_values='\n\t\t\t\t\t\tfrom.checkValue(fromValue,true) and to.checkValue(toValue,true)' differentAttrs_in_Constraint='\n\t\t\t\t\t\tfrom <> to'"
 * @extends CDOObject
 * @generated
 */
public interface AttributeConstraint extends CDOObject {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Attribute)
	 * @see camel.provider.ProviderPackage#getAttributeConstraint_From()
	 * @model required="true"
	 * @generated
	 */
	Attribute getFrom();

	/**
	 * Sets the value of the '{@link camel.provider.AttributeConstraint#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(Attribute value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Attribute)
	 * @see camel.provider.ProviderPackage#getAttributeConstraint_To()
	 * @model required="true"
	 * @generated
	 */
	Attribute getTo();

	/**
	 * Sets the value of the '{@link camel.provider.AttributeConstraint#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Attribute value);

	/**
	 * Returns the value of the '<em><b>From Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From Value</em>' containment reference.
	 * @see #setFromValue(Value)
	 * @see camel.provider.ProviderPackage#getAttributeConstraint_FromValue()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Value getFromValue();

	/**
	 * Sets the value of the '{@link camel.provider.AttributeConstraint#getFromValue <em>From Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From Value</em>' containment reference.
	 * @see #getFromValue()
	 * @generated
	 */
	void setFromValue(Value value);

	/**
	 * Returns the value of the '<em><b>To Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To Value</em>' containment reference.
	 * @see #setToValue(Value)
	 * @see camel.provider.ProviderPackage#getAttributeConstraint_ToValue()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Value getToValue();

	/**
	 * Sets the value of the '{@link camel.provider.AttributeConstraint#getToValue <em>To Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To Value</em>' containment reference.
	 * @see #getToValue()
	 * @generated
	 */
	void setToValue(Value value);

} // AttributeConstraint
