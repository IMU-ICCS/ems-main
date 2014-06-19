/**
 */
package camel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scalability Policy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.ScalabilityPolicy#getMinInstances <em>Min Instances</em>}</li>
 *   <li>{@link camel.ScalabilityPolicy#getMaxInstances <em>Max Instances</em>}</li>
 *   <li>{@link camel.ScalabilityPolicy#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getScalabilityPolicy()
 * @model
 * @generated
 */
public interface ScalabilityPolicy extends Requirement {
	/**
	 * Returns the value of the '<em><b>Min Instances</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Instances</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Instances</em>' attribute.
	 * @see #setMinInstances(int)
	 * @see camel.CamelPackage#getScalabilityPolicy_MinInstances()
	 * @model required="true"
	 * @generated
	 */
	int getMinInstances();

	/**
	 * Sets the value of the '{@link camel.ScalabilityPolicy#getMinInstances <em>Min Instances</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Instances</em>' attribute.
	 * @see #getMinInstances()
	 * @generated
	 */
	void setMinInstances(int value);

	/**
	 * Returns the value of the '<em><b>Max Instances</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Instances</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Instances</em>' attribute.
	 * @see #setMaxInstances(int)
	 * @see camel.CamelPackage#getScalabilityPolicy_MaxInstances()
	 * @model required="true"
	 * @generated
	 */
	int getMaxInstances();

	/**
	 * Sets the value of the '{@link camel.ScalabilityPolicy#getMaxInstances <em>Max Instances</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Instances</em>' attribute.
	 * @see #getMaxInstances()
	 * @generated
	 */
	void setMaxInstances(int value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.ScalabilityType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see camel.ScalabilityType
	 * @see #setType(ScalabilityType)
	 * @see camel.CamelPackage#getScalabilityPolicy_Type()
	 * @model required="true"
	 * @generated
	 */
	ScalabilityType getType();

	/**
	 * Sets the value of the '{@link camel.ScalabilityPolicy#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see camel.ScalabilityType
	 * @see #getType()
	 * @generated
	 */
	void setType(ScalabilityType value);

} // ScalabilityPolicy
