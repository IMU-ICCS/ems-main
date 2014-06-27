/**
 */
package camel.scalability;

import camel.type.Value;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric Formula Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.MetricFormulaParameter#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetricFormulaParameter()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ValueParamSet_for_Non_Metric_Templates'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot ValueParamSet_for_Non_Metric_Templates='\n\t\t\t\t\t\tnot(self.oclIsTypeOf(MetricTemplate) or (self.oclIsTypeOf(MetricFormula))) implies self.value <> null'"
 * @extends CDOObject
 * @generated
 */
public interface MetricFormulaParameter extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' reference.
	 * @see #setValue(Value)
	 * @see camel.scalability.ScalabilityPackage#getMetricFormulaParameter_Value()
	 * @model
	 * @generated
	 */
	Value getValue();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricFormulaParameter#getValue <em>Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Value value);

} // MetricFormulaParameter
