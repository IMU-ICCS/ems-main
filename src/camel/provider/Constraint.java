/**
 */
package camel.provider;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.provider.Constraint#getFrom <em>From</em>}</li>
 *   <li>{@link camel.provider.Constraint#getTo <em>To</em>}</li>
 *   <li>{@link camel.provider.Constraint#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.provider.ProviderPackage#getConstraint()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='fromAttributesBelongtoFromFeatureAndSymmetric'"
 *        annotation="Ecore fromAttributeConstraintsvalidation='fromAttributesBelongsToFromFeature' toAttributeConstraintsvalidation='toAttributesBelongsToToFeature'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot fromAttributesBelongtoFromFeatureAndSymmetric='\n\t\t\tattributeConstraints->forAll(p | (from.attributes->includes(p.from) and to.attributes->includes(p.to)))'"
 * @extends CDOObject
 * @generated
 */
public interface Constraint extends CDOObject {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Feature)
	 * @see camel.provider.ProviderPackage#getConstraint_From()
	 * @model required="true"
	 * @generated
	 */
	Feature getFrom();

	/**
	 * Sets the value of the '{@link camel.provider.Constraint#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(Feature value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Feature)
	 * @see camel.provider.ProviderPackage#getConstraint_To()
	 * @model required="true"
	 * @generated
	 */
	Feature getTo();

	/**
	 * Sets the value of the '{@link camel.provider.Constraint#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Feature value);

	/**
	 * Returns the value of the '<em><b>Attribute Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link camel.provider.AttributeConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Constraints</em>' containment reference list.
	 * @see camel.provider.ProviderPackage#getConstraint_AttributeConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<AttributeConstraint> getAttributeConstraints();

} // Constraint
