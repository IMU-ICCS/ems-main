/**
 */
package camel.sla;

import camel.scalability.MetricCondition;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>KPI Target Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.KPITargetType#getKPIName <em>KPI Name</em>}</li>
 *   <li>{@link camel.sla.KPITargetType#getCustomServiceLevel <em>Custom Service Level</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getKPITargetType()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface KPITargetType extends CDOObject {
	/**
	 * Returns the value of the '<em><b>KPI Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>KPI Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>KPI Name</em>' attribute.
	 * @see #setKPIName(String)
	 * @see camel.sla.SlaPackage#getKPITargetType_KPIName()
	 * @model required="true"
	 * @generated
	 */
	String getKPIName();

	/**
	 * Sets the value of the '{@link camel.sla.KPITargetType#getKPIName <em>KPI Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>KPI Name</em>' attribute.
	 * @see #getKPIName()
	 * @generated
	 */
	void setKPIName(String value);

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
	 * @see camel.sla.SlaPackage#getKPITargetType_CustomServiceLevel()
	 * @model required="true"
	 * @generated
	 */
	MetricCondition getCustomServiceLevel();

	/**
	 * Sets the value of the '{@link camel.sla.KPITargetType#getCustomServiceLevel <em>Custom Service Level</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Custom Service Level</em>' reference.
	 * @see #getCustomServiceLevel()
	 * @generated
	 */
	void setCustomServiceLevel(MetricCondition value);

} // KPITargetType
