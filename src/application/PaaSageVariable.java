/**
 */
package application;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

import types.typesPaasage.VariableElementTypeEnum;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paa Sage Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link application.PaaSageVariable#getPaasageType <em>Paasage Type</em>}</li>
 *   <li>{@link application.PaaSageVariable#getRelatedElements <em>Related Elements</em>}</li>
 *   <li>{@link application.PaaSageVariable#getCpVariableId <em>Cp Variable Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see application.ApplicationPackage#getPaaSageVariable()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface PaaSageVariable extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Paasage Type</b></em>' attribute.
	 * The literals are from the enumeration {@link types.typesPaasage.VariableElementTypeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Paasage Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paasage Type</em>' attribute.
	 * @see types.typesPaasage.VariableElementTypeEnum
	 * @see #setPaasageType(VariableElementTypeEnum)
	 * @see application.ApplicationPackage#getPaaSageVariable_PaasageType()
	 * @model
	 * @generated
	 */
	VariableElementTypeEnum getPaasageType();

	/**
	 * Sets the value of the '{@link application.PaaSageVariable#getPaasageType <em>Paasage Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Paasage Type</em>' attribute.
	 * @see types.typesPaasage.VariableElementTypeEnum
	 * @see #getPaasageType()
	 * @generated
	 */
	void setPaasageType(VariableElementTypeEnum value);

	/**
	 * Returns the value of the '<em><b>Related Elements</b></em>' reference list.
	 * The list contents are of type {@link application.CloudML_Element}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Elements</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Elements</em>' reference list.
	 * @see application.ApplicationPackage#getPaaSageVariable_RelatedElements()
	 * @model required="true"
	 * @generated
	 */
	EList<CloudML_Element> getRelatedElements();

	/**
	 * Returns the value of the '<em><b>Cp Variable Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cp Variable Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cp Variable Id</em>' attribute.
	 * @see #setCpVariableId(String)
	 * @see application.ApplicationPackage#getPaaSageVariable_CpVariableId()
	 * @model required="true"
	 * @generated
	 */
	String getCpVariableId();

	/**
	 * Sets the value of the '{@link application.PaaSageVariable#getCpVariableId <em>Cp Variable Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cp Variable Id</em>' attribute.
	 * @see #getCpVariableId()
	 * @generated
	 */
	void setCpVariableId(String value);

} // PaaSageVariable
