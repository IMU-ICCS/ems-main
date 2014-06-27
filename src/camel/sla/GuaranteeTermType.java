/**
 */
package camel.sla;

import camel.scalability.MetricCondition;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Guarantee Term Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.GuaranteeTermType#getServiceScope <em>Service Scope</em>}</li>
 *   <li>{@link camel.sla.GuaranteeTermType#getServiceLevelObjective <em>Service Level Objective</em>}</li>
 *   <li>{@link camel.sla.GuaranteeTermType#getBusinessValueList <em>Business Value List</em>}</li>
 *   <li>{@link camel.sla.GuaranteeTermType#getObligated <em>Obligated</em>}</li>
 *   <li>{@link camel.sla.GuaranteeTermType#getQualifyingCondition <em>Qualifying Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getGuaranteeTermType()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface GuaranteeTermType extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Service Scope</b></em>' containment reference list.
	 * The list contents are of type {@link camel.sla.ServiceSelectorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Scope</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Scope</em>' containment reference list.
	 * @see camel.sla.SlaPackage#getGuaranteeTermType_ServiceScope()
	 * @model containment="true"
	 * @generated
	 */
	EList<ServiceSelectorType> getServiceScope();

	/**
	 * Returns the value of the '<em><b>Service Level Objective</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Level Objective</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Level Objective</em>' containment reference.
	 * @see #setServiceLevelObjective(ServiceLevelObjectiveType)
	 * @see camel.sla.SlaPackage#getGuaranteeTermType_ServiceLevelObjective()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ServiceLevelObjectiveType getServiceLevelObjective();

	/**
	 * Sets the value of the '{@link camel.sla.GuaranteeTermType#getServiceLevelObjective <em>Service Level Objective</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service Level Objective</em>' containment reference.
	 * @see #getServiceLevelObjective()
	 * @generated
	 */
	void setServiceLevelObjective(ServiceLevelObjectiveType value);

	/**
	 * Returns the value of the '<em><b>Business Value List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Business Value List</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Business Value List</em>' containment reference.
	 * @see #setBusinessValueList(BusinessValueListType)
	 * @see camel.sla.SlaPackage#getGuaranteeTermType_BusinessValueList()
	 * @model containment="true"
	 * @generated
	 */
	BusinessValueListType getBusinessValueList();

	/**
	 * Sets the value of the '{@link camel.sla.GuaranteeTermType#getBusinessValueList <em>Business Value List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Business Value List</em>' containment reference.
	 * @see #getBusinessValueList()
	 * @generated
	 */
	void setBusinessValueList(BusinessValueListType value);

	/**
	 * Returns the value of the '<em><b>Obligated</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.sla.ServiceRoleType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Obligated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Obligated</em>' attribute.
	 * @see camel.sla.ServiceRoleType
	 * @see #setObligated(ServiceRoleType)
	 * @see camel.sla.SlaPackage#getGuaranteeTermType_Obligated()
	 * @model
	 * @generated
	 */
	ServiceRoleType getObligated();

	/**
	 * Sets the value of the '{@link camel.sla.GuaranteeTermType#getObligated <em>Obligated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Obligated</em>' attribute.
	 * @see camel.sla.ServiceRoleType
	 * @see #getObligated()
	 * @generated
	 */
	void setObligated(ServiceRoleType value);

	/**
	 * Returns the value of the '<em><b>Qualifying Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qualifying Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qualifying Condition</em>' containment reference.
	 * @see #setQualifyingCondition(MetricCondition)
	 * @see camel.sla.SlaPackage#getGuaranteeTermType_QualifyingCondition()
	 * @model containment="true"
	 * @generated
	 */
	MetricCondition getQualifyingCondition();

	/**
	 * Sets the value of the '{@link camel.sla.GuaranteeTermType#getQualifyingCondition <em>Qualifying Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualifying Condition</em>' containment reference.
	 * @see #getQualifyingCondition()
	 * @generated
	 */
	void setQualifyingCondition(MetricCondition value);

} // GuaranteeTermType
