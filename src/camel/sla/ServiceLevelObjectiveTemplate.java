/**
 */
package camel.sla;

import camel.Requirement;

import camel.scalability.MetricTemplateCondition;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service Level Objective Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.ServiceLevelObjectiveTemplate#getCondition <em>Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getServiceLevelObjectiveTemplate()
 * @model
 * @generated
 */
public interface ServiceLevelObjectiveTemplate extends Requirement {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' reference.
	 * @see #setCondition(MetricTemplateCondition)
	 * @see camel.sla.SlaPackage#getServiceLevelObjectiveTemplate_Condition()
	 * @model required="true"
	 * @generated
	 */
	MetricTemplateCondition getCondition();

	/**
	 * Sets the value of the '{@link camel.sla.ServiceLevelObjectiveTemplate#getCondition <em>Condition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(MetricTemplateCondition value);

} // ServiceLevelObjectiveTemplate
