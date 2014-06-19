/**
 */
package camel;

import camel.cerif.User;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Requirement Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.RequirementGroup#getPosedBy <em>Posed By</em>}</li>
 *   <li>{@link camel.RequirementGroup#getRequirement <em>Requirement</em>}</li>
 *   <li>{@link camel.RequirementGroup#getOnApplication <em>On Application</em>}</li>
 *   <li>{@link camel.RequirementGroup#getRequirementOperator <em>Requirement Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getRequirementGroup()
 * @model
 * @generated
 */
public interface RequirementGroup extends Requirement {
	/**
	 * Returns the value of the '<em><b>Posed By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Posed By</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Posed By</em>' reference.
	 * @see #setPosedBy(User)
	 * @see camel.CamelPackage#getRequirementGroup_PosedBy()
	 * @model required="true"
	 * @generated
	 */
	User getPosedBy();

	/**
	 * Sets the value of the '{@link camel.RequirementGroup#getPosedBy <em>Posed By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Posed By</em>' reference.
	 * @see #getPosedBy()
	 * @generated
	 */
	void setPosedBy(User value);

	/**
	 * Returns the value of the '<em><b>Requirement</b></em>' reference list.
	 * The list contents are of type {@link camel.Requirement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requirement</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requirement</em>' reference list.
	 * @see camel.CamelPackage#getRequirementGroup_Requirement()
	 * @model required="true"
	 * @generated
	 */
	EList<Requirement> getRequirement();

	/**
	 * Returns the value of the '<em><b>On Application</b></em>' reference list.
	 * The list contents are of type {@link camel.Application}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>On Application</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Application</em>' reference list.
	 * @see camel.CamelPackage#getRequirementGroup_OnApplication()
	 * @model
	 * @generated
	 */
	EList<Application> getOnApplication();

	/**
	 * Returns the value of the '<em><b>Requirement Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.RequirementOperatorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requirement Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requirement Operator</em>' attribute.
	 * @see camel.RequirementOperatorType
	 * @see #setRequirementOperator(RequirementOperatorType)
	 * @see camel.CamelPackage#getRequirementGroup_RequirementOperator()
	 * @model required="true"
	 * @generated
	 */
	RequirementOperatorType getRequirementOperator();

	/**
	 * Sets the value of the '{@link camel.RequirementGroup#getRequirementOperator <em>Requirement Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requirement Operator</em>' attribute.
	 * @see camel.RequirementOperatorType
	 * @see #getRequirementOperator()
	 * @generated
	 */
	void setRequirementOperator(RequirementOperatorType value);

} // RequirementGroup
