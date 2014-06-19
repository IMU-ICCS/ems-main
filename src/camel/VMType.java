/**
 */
package camel;

import camel.cerif.Resource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VM Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.VMType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getVMType()
 * @model
 * @generated
 */
public interface VMType extends Resource {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * property feature : camel::featureModel::Feature;
	 * property constraints : camel::featureModel::Constraint[+] { ordered };
	 * <!-- end-model-doc -->
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
