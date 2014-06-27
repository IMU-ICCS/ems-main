/**
 */
package camel.scalability;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric Template Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.MetricTemplateCondition#getMetricTemplate <em>Metric Template</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetricTemplateCondition()
 * @model
 * @generated
 */
public interface MetricTemplateCondition extends Condition {
	/**
	 * Returns the value of the '<em><b>Metric Template</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metric Template</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metric Template</em>' reference.
	 * @see #setMetricTemplate(MetricTemplate)
	 * @see camel.scalability.ScalabilityPackage#getMetricTemplateCondition_MetricTemplate()
	 * @model required="true"
	 * @generated
	 */
	MetricTemplate getMetricTemplate();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricTemplateCondition#getMetricTemplate <em>Metric Template</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metric Template</em>' reference.
	 * @see #getMetricTemplate()
	 * @generated
	 */
	void setMetricTemplate(MetricTemplate value);

} // MetricTemplateCondition
