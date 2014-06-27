/**
 */
package camel.execution;

import camel.sla.ServiceLevelObjectiveType;

import java.util.Date;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>SLO Assessment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.execution.SLOAssessment#getItSLO <em>It SLO</em>}</li>
 *   <li>{@link camel.execution.SLOAssessment#isAssessment <em>Assessment</em>}</li>
 *   <li>{@link camel.execution.SLOAssessment#getInExecutionContext <em>In Execution Context</em>}</li>
 *   <li>{@link camel.execution.SLOAssessment#getMeasurement <em>Measurement</em>}</li>
 *   <li>{@link camel.execution.SLOAssessment#getAssessmentTime <em>Assessment Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.execution.ExecutionPackage#getSLOAssessment()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='SLOAss_Same_metric SLOAss_itSLO_in_Reqs_for_EC SLOAss_same_exec_context'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot SLOAss_Same_metric='\n\t\t\t\t\t\t\t\tmeasurement.ofMetric = itSLO.customServiceLevel.metric' SLOAss_itSLO_in_Reqs_for_EC='\n\t\t\t\t\t\t\t\tself.inExecutionContext.basedOnRequirements.requirement->includes(self.itSLO)' SLOAss_same_exec_context='\n\t\t\t\t\t\t\t\tinExecutionContext = measurement.inExecutionContext'"
 * @extends CDOObject
 * @generated
 */
public interface SLOAssessment extends CDOObject {
	/**
	 * Returns the value of the '<em><b>It SLO</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>It SLO</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>It SLO</em>' reference.
	 * @see #setItSLO(ServiceLevelObjectiveType)
	 * @see camel.execution.ExecutionPackage#getSLOAssessment_ItSLO()
	 * @model required="true"
	 * @generated
	 */
	ServiceLevelObjectiveType getItSLO();

	/**
	 * Sets the value of the '{@link camel.execution.SLOAssessment#getItSLO <em>It SLO</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>It SLO</em>' reference.
	 * @see #getItSLO()
	 * @generated
	 */
	void setItSLO(ServiceLevelObjectiveType value);

	/**
	 * Returns the value of the '<em><b>Assessment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assessment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assessment</em>' attribute.
	 * @see #setAssessment(boolean)
	 * @see camel.execution.ExecutionPackage#getSLOAssessment_Assessment()
	 * @model required="true"
	 * @generated
	 */
	boolean isAssessment();

	/**
	 * Sets the value of the '{@link camel.execution.SLOAssessment#isAssessment <em>Assessment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assessment</em>' attribute.
	 * @see #isAssessment()
	 * @generated
	 */
	void setAssessment(boolean value);

	/**
	 * Returns the value of the '<em><b>In Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Execution Context</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Execution Context</em>' reference.
	 * @see #setInExecutionContext(ExecutionContext)
	 * @see camel.execution.ExecutionPackage#getSLOAssessment_InExecutionContext()
	 * @model required="true"
	 * @generated
	 */
	ExecutionContext getInExecutionContext();

	/**
	 * Sets the value of the '{@link camel.execution.SLOAssessment#getInExecutionContext <em>In Execution Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>In Execution Context</em>' reference.
	 * @see #getInExecutionContext()
	 * @generated
	 */
	void setInExecutionContext(ExecutionContext value);

	/**
	 * Returns the value of the '<em><b>Measurement</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Measurement</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Measurement</em>' reference.
	 * @see #setMeasurement(Measurement)
	 * @see camel.execution.ExecutionPackage#getSLOAssessment_Measurement()
	 * @model required="true"
	 * @generated
	 */
	Measurement getMeasurement();

	/**
	 * Sets the value of the '{@link camel.execution.SLOAssessment#getMeasurement <em>Measurement</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Measurement</em>' reference.
	 * @see #getMeasurement()
	 * @generated
	 */
	void setMeasurement(Measurement value);

	/**
	 * Returns the value of the '<em><b>Assessment Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assessment Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assessment Time</em>' attribute.
	 * @see #setAssessmentTime(Date)
	 * @see camel.execution.ExecutionPackage#getSLOAssessment_AssessmentTime()
	 * @model required="true"
	 * @generated
	 */
	Date getAssessmentTime();

	/**
	 * Sets the value of the '{@link camel.execution.SLOAssessment#getAssessmentTime <em>Assessment Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assessment Time</em>' attribute.
	 * @see #getAssessmentTime()
	 * @generated
	 */
	void setAssessmentTime(Date value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='Date reportedOn = sa.getAssessmentTime();\n\t\tMeasurement m = sa.getMeasurement();\n\t\tDate date = m.getReportedOn();\n\t\tif (reportedOn.before(date)) return Boolean.FALSE;\n\t\treturn Boolean.TRUE;'"
	 * @generated
	 */
	boolean checkDate(SLOAssessment sa);

} // SLOAssessment
