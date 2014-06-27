/**
 */
package camel.execution;

import camel.scalability.EventInstance;
import camel.scalability.Metric;

import camel.sla.ServiceLevelObjectiveType;

import java.util.Date;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Measurement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.execution.Measurement#getID <em>ID</em>}</li>
 *   <li>{@link camel.execution.Measurement#getInExecutionContext <em>In Execution Context</em>}</li>
 *   <li>{@link camel.execution.Measurement#getOfMetric <em>Of Metric</em>}</li>
 *   <li>{@link camel.execution.Measurement#getValue <em>Value</em>}</li>
 *   <li>{@link camel.execution.Measurement#getRawData <em>Raw Data</em>}</li>
 *   <li>{@link camel.execution.Measurement#getReportedOn <em>Reported On</em>}</li>
 *   <li>{@link camel.execution.Measurement#getItSLO <em>It SLO</em>}</li>
 *   <li>{@link camel.execution.Measurement#getTriggers <em>Triggers</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.execution.ExecutionPackage#getMeasurement()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Measurement_ItSLO_refer_to_Correct_Metric Measurement_eventInstance_same_Metric Correct_Measurement_Value Measurement_metric_refers_to_correct_ec'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Measurement_ItSLO_refer_to_Correct_Metric='\n\t\t\t\t\t\tself.itSLO <> null implies self.itSLO.customServiceLevel.metric = self.ofMetric' Measurement_eventInstance_same_Metric='\n\t\t\t\t\t\t(self.triggers <> null and self.triggers.onEvent.oclIsTypeOf(camel::scalability::NonFunctionalEvent)) implies self.ofMetric = self.triggers.onEvent.oclAsType(camel::scalability::NonFunctionalEvent).condition.metric' Correct_Measurement_Value='\n\t\t\t\t\t\tif (ofMetric.valueType.oclIsTypeOf(camel::type::Range)) then ofMetric.valueType.oclAsType(camel::type::Range).includesValue(self.value)\n\t\t\t\t\t\telse if (ofMetric.valueType.oclIsTypeOf(camel::type::RangeUnion)) then ofMetric.valueType.oclAsType(camel::type::RangeUnion).includesValue(self.value)\n\t\t\t\t\t\telse true endif\n\t\t\t\t\t\tendif' Measurement_metric_refers_to_correct_ec='\n\t\t\t\t\t\t\t\tinExecutionContext = ofMetric.objectBinding.executionContext'"
 * @extends CDOObject
 * @generated
 */
public interface Measurement extends CDOObject {
	/**
	 * Returns the value of the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ID</em>' attribute.
	 * @see #setID(String)
	 * @see camel.execution.ExecutionPackage#getMeasurement_ID()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link camel.execution.Measurement#getID <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ID</em>' attribute.
	 * @see #getID()
	 * @generated
	 */
	void setID(String value);

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
	 * @see camel.execution.ExecutionPackage#getMeasurement_InExecutionContext()
	 * @model required="true"
	 * @generated
	 */
	ExecutionContext getInExecutionContext();

	/**
	 * Sets the value of the '{@link camel.execution.Measurement#getInExecutionContext <em>In Execution Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>In Execution Context</em>' reference.
	 * @see #getInExecutionContext()
	 * @generated
	 */
	void setInExecutionContext(ExecutionContext value);

	/**
	 * Returns the value of the '<em><b>Of Metric</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Of Metric</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Of Metric</em>' reference.
	 * @see #setOfMetric(Metric)
	 * @see camel.execution.ExecutionPackage#getMeasurement_OfMetric()
	 * @model required="true"
	 * @generated
	 */
	Metric getOfMetric();

	/**
	 * Sets the value of the '{@link camel.execution.Measurement#getOfMetric <em>Of Metric</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Of Metric</em>' reference.
	 * @see #getOfMetric()
	 * @generated
	 */
	void setOfMetric(Metric value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(double)
	 * @see camel.execution.ExecutionPackage#getMeasurement_Value()
	 * @model required="true"
	 * @generated
	 */
	double getValue();

	/**
	 * Sets the value of the '{@link camel.execution.Measurement#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(double value);

	/**
	 * Returns the value of the '<em><b>Raw Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Raw Data</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Raw Data</em>' attribute.
	 * @see #setRawData(String)
	 * @see camel.execution.ExecutionPackage#getMeasurement_RawData()
	 * @model
	 * @generated
	 */
	String getRawData();

	/**
	 * Sets the value of the '{@link camel.execution.Measurement#getRawData <em>Raw Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Raw Data</em>' attribute.
	 * @see #getRawData()
	 * @generated
	 */
	void setRawData(String value);

	/**
	 * Returns the value of the '<em><b>Reported On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reported On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reported On</em>' attribute.
	 * @see #setReportedOn(Date)
	 * @see camel.execution.ExecutionPackage#getMeasurement_ReportedOn()
	 * @model required="true"
	 * @generated
	 */
	Date getReportedOn();

	/**
	 * Sets the value of the '{@link camel.execution.Measurement#getReportedOn <em>Reported On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reported On</em>' attribute.
	 * @see #getReportedOn()
	 * @generated
	 */
	void setReportedOn(Date value);

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
	 * @see camel.execution.ExecutionPackage#getMeasurement_ItSLO()
	 * @model
	 * @generated
	 */
	ServiceLevelObjectiveType getItSLO();

	/**
	 * Sets the value of the '{@link camel.execution.Measurement#getItSLO <em>It SLO</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>It SLO</em>' reference.
	 * @see #getItSLO()
	 * @generated
	 */
	void setItSLO(ServiceLevelObjectiveType value);

	/**
	 * Returns the value of the '<em><b>Triggers</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Triggers</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Triggers</em>' reference.
	 * @see #setTriggers(EventInstance)
	 * @see camel.execution.ExecutionPackage#getMeasurement_Triggers()
	 * @model
	 * @generated
	 */
	EventInstance getTriggers();

	/**
	 * Sets the value of the '{@link camel.execution.Measurement#getTriggers <em>Triggers</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Triggers</em>' reference.
	 * @see #getTriggers()
	 * @generated
	 */
	void setTriggers(EventInstance value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='Date reportedOn = m.getReportedOn();\n\t\tExecutionContext ec = m.getInExecutionContext();\n\t\tDate start = ec.getStartTime();\n\t\tDate end = ec.getEndTime();\n\t\tif (reportedOn.before(start) || (end != null && end.before(reportedOn))) return Boolean.FALSE;\n\t\treturn Boolean.TRUE;'"
	 * @generated
	 */
	boolean checkDate(Measurement m);

} // Measurement
