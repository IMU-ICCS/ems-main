/**
 */
package camel.sla;

import camel.Requirement;

import camel.scalability.MetricCondition;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service Level Objective Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.ServiceLevelObjectiveType#getKPITarget <em>KPI Target</em>}</li>
 *   <li>{@link camel.sla.ServiceLevelObjectiveType#getCustomServiceLevel <em>Custom Service Level</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getServiceLevelObjectiveType()
 * @model
 * @generated
 */
public interface ServiceLevelObjectiveType extends Requirement {
	/**
	 * Returns the value of the '<em><b>KPI Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>KPI Target</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>KPI Target</em>' containment reference.
	 * @see #setKPITarget(KPITargetType)
	 * @see camel.sla.SlaPackage#getServiceLevelObjectiveType_KPITarget()
	 * @model containment="true"
	 * @generated
	 */
	KPITargetType getKPITarget();

	/**
	 * Sets the value of the '{@link camel.sla.ServiceLevelObjectiveType#getKPITarget <em>KPI Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>KPI Target</em>' containment reference.
	 * @see #getKPITarget()
	 * @generated
	 */
	void setKPITarget(KPITargetType value);

	/**
	 * Returns the value of the '<em><b>Custom Service Level</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Custom Service Level</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Custom Service Level</em>' reference.
	 * @see #setCustomServiceLevel(MetricCondition)
	 * @see camel.sla.SlaPackage#getServiceLevelObjectiveType_CustomServiceLevel()
	 * @model
	 * @generated
	 */
	MetricCondition getCustomServiceLevel();

	/**
	 * Sets the value of the '{@link camel.sla.ServiceLevelObjectiveType#getCustomServiceLevel <em>Custom Service Level</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Custom Service Level</em>' reference.
	 * @see #getCustomServiceLevel()
	 * @generated
	 */
	void setCustomServiceLevel(MetricCondition value);

} // ServiceLevelObjectiveType
