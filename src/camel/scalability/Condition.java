/**
 */
package camel.scalability;

import java.util.Date;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.Condition#getValidity <em>Validity</em>}</li>
 *   <li>{@link camel.scalability.Condition#getThreshold <em>Threshold</em>}</li>
 *   <li>{@link camel.scalability.Condition#getComparisonOperator <em>Comparison Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getCondition()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface Condition extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Validity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validity</em>' attribute.
	 * @see #setValidity(Date)
	 * @see camel.scalability.ScalabilityPackage#getCondition_Validity()
	 * @model
	 * @generated
	 */
	Date getValidity();

	/**
	 * Sets the value of the '{@link camel.scalability.Condition#getValidity <em>Validity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validity</em>' attribute.
	 * @see #getValidity()
	 * @generated
	 */
	void setValidity(Date value);

	/**
	 * Returns the value of the '<em><b>Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Threshold</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Threshold</em>' attribute.
	 * @see #setThreshold(double)
	 * @see camel.scalability.ScalabilityPackage#getCondition_Threshold()
	 * @model required="true"
	 * @generated
	 */
	double getThreshold();

	/**
	 * Sets the value of the '{@link camel.scalability.Condition#getThreshold <em>Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Threshold</em>' attribute.
	 * @see #getThreshold()
	 * @generated
	 */
	void setThreshold(double value);

	/**
	 * Returns the value of the '<em><b>Comparison Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.ComparisonOperatorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comparison Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comparison Operator</em>' attribute.
	 * @see camel.scalability.ComparisonOperatorType
	 * @see #setComparisonOperator(ComparisonOperatorType)
	 * @see camel.scalability.ScalabilityPackage#getCondition_ComparisonOperator()
	 * @model required="true"
	 * @generated
	 */
	ComparisonOperatorType getComparisonOperator();

	/**
	 * Sets the value of the '{@link camel.scalability.Condition#getComparisonOperator <em>Comparison Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comparison Operator</em>' attribute.
	 * @see camel.scalability.ComparisonOperatorType
	 * @see #getComparisonOperator()
	 * @generated
	 */
	void setComparisonOperator(ComparisonOperatorType value);

} // Condition
