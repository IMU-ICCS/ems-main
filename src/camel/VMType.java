/**
 */
package camel;

import camel.organisation.Resource;

import camel.provider.Constraint;
import camel.provider.Feature;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VM Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.VMType#getFeature <em>Feature</em>}</li>
 *   <li>{@link camel.VMType#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link camel.VMType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getVMType()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='VMType_one_feature_plus_its_attrs'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot VMType_one_feature_plus_its_attrs='\n\t\t\t\t\t\t\tconstraints->forAll(p | p.from = feature and p.to = feature)'"
 * @generated
 */
public interface VMType extends Resource {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' reference.
	 * @see #setFeature(Feature)
	 * @see camel.CamelPackage#getVMType_Feature()
	 * @model required="true"
	 * @generated
	 */
	Feature getFeature();

	/**
	 * Sets the value of the '{@link camel.VMType#getFeature <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' reference.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(Feature value);

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' reference list.
	 * The list contents are of type {@link camel.provider.Constraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraints</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' reference list.
	 * @see camel.CamelPackage#getVMType_Constraints()
	 * @model required="true"
	 * @generated
	 */
	EList<Constraint> getConstraints();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see camel.CamelPackage#getVMType_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.VMType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // VMType
