/**
 */
package camel.scalability;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.MetricCondition#getMetric <em>Metric</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetricCondition()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='CorrectThresholdInMetricCondition'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot CorrectThresholdInMetricCondition='\n\t\t\t\t\tif (metric.valueType.oclIsTypeOf(camel::type::Range)) then metric.valueType.oclAsType(camel::type::Range).includesValue(self.threshold)\n\t\t\t\t\telse if (metric.valueType.oclIsTypeOf(camel::type::RangeUnion)) then metric.valueType.oclAsType(camel::type::RangeUnion).includesValue(self.threshold)\n\t\t\t\t\telse true endif\n\t\t\t\t\tendif'"
 * @generated
 */
public interface MetricCondition extends Condition {
	/**
	 * Returns the value of the '<em><b>Metric</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metric</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metric</em>' reference.
	 * @see #setMetric(Metric)
	 * @see camel.scalability.ScalabilityPackage#getMetricCondition_Metric()
	 * @model required="true"
	 * @generated
	 */
	Metric getMetric();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricCondition#getMetric <em>Metric</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metric</em>' reference.
	 * @see #getMetric()
	 * @generated
	 */
	void setMetric(Metric value);

} // MetricCondition
