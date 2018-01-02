/**
 */
package eu.paasage.upperware.metamodel.cp;

import eu.paasage.upperware.metamodel.types.BasicTypeEnum;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.MetricVariable#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getMetricVariable()
 * @model
 * @generated
 */
public interface MetricVariable extends NumericExpression {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.BasicTypeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.BasicTypeEnum
	 * @see #setType(BasicTypeEnum)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getMetricVariable_Type()
	 * @model required="true"
	 * @generated
	 */
	BasicTypeEnum getType();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.MetricVariable#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.BasicTypeEnum
	 * @see #getType()
	 * @generated
	 */
	void setType(BasicTypeEnum value);

} // MetricVariable
