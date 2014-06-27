/**
 */
package camel.scalability;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric Formula</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.MetricFormula#getFunction <em>Function</em>}</li>
 *   <li>{@link camel.scalability.MetricFormula#getFunctionArity <em>Function Arity</em>}</li>
 *   <li>{@link camel.scalability.MetricFormula#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetricFormula()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='correct_arity_for_function_wrt_parameters correct_arity_for_function'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot correct_arity_for_function_wrt_parameters='\n\t\t\t((self.functionArity = MetricFunctionArityType::UNARY) implies (self.parameters->size() = 1 and self.parameters->select(p | p.oclIsTypeOf(MetricTemplate))->size() = 1)) and ((self.functionArity = MetricFunctionArityType::BINARY) implies self.parameters->size() = 2) and ((self.functionArity = MetricFunctionArityType::N_ARY) implies self.parameters->size() >= 2)' correct_arity_for_function='\n\t\t\t((self.function = MetricFunctionType::DIV or self.function = MetricFunctionType::MODULO or self.function = MetricFunctionType::COUNT) implies self.functionArity = MetricFunctionArityType::BINARY) and (((self.function = MetricFunctionType::AVERAGE or self.function = MetricFunctionType::MEAN or self.function = MetricFunctionType::STD) implies self.functionArity = MetricFunctionArityType::UNARY)) and (((self.function = MetricFunctionType::PLUS) implies (self.functionArity = MetricFunctionArityType::BINARY or self.functionArity = MetricFunctionArityType::N_ARY)) and ((self.function = MetricFunctionType::MINUS) implies (self.functionArity = MetricFunctionArityType::UNARY or self.functionArity = MetricFunctionArityType::BINARY)))'"
 * @generated
 */
public interface MetricFormula extends MetricFormulaParameter {
	/**
	 * Returns the value of the '<em><b>Function</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.MetricFunctionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' attribute.
	 * @see camel.scalability.MetricFunctionType
	 * @see #setFunction(MetricFunctionType)
	 * @see camel.scalability.ScalabilityPackage#getMetricFormula_Function()
	 * @model required="true"
	 * @generated
	 */
	MetricFunctionType getFunction();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricFormula#getFunction <em>Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' attribute.
	 * @see camel.scalability.MetricFunctionType
	 * @see #getFunction()
	 * @generated
	 */
	void setFunction(MetricFunctionType value);

	/**
	 * Returns the value of the '<em><b>Function Arity</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.MetricFunctionArityType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Arity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Arity</em>' attribute.
	 * @see camel.scalability.MetricFunctionArityType
	 * @see #setFunctionArity(MetricFunctionArityType)
	 * @see camel.scalability.ScalabilityPackage#getMetricFormula_FunctionArity()
	 * @model required="true"
	 * @generated
	 */
	MetricFunctionArityType getFunctionArity();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricFormula#getFunctionArity <em>Function Arity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Arity</em>' attribute.
	 * @see camel.scalability.MetricFunctionArityType
	 * @see #getFunctionArity()
	 * @generated
	 */
	void setFunctionArity(MetricFunctionArityType value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' reference list.
	 * The list contents are of type {@link camel.scalability.MetricFormulaParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' reference list.
	 * @see camel.scalability.ScalabilityPackage#getMetricFormula_Parameters()
	 * @model required="true"
	 * @generated
	 */
	EList<MetricFormulaParameter> getParameters();

} // MetricFormula
