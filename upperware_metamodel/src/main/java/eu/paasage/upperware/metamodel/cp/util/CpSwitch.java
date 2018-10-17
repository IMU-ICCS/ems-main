/**
 */
package eu.paasage.upperware.metamodel.cp.util;

import eu.paasage.upperware.metamodel.cp.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.cp.CpPackage
 * @generated
 */
public class CpSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CpPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CpSwitch() {
		if (modelPackage == null) {
			modelPackage = CpPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case CpPackage.CP_ELEMENT: {
				CPElement cpElement = (CPElement)theEObject;
				T result = caseCPElement(cpElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.CONSTRAINT_PROBLEM: {
				ConstraintProblem constraintProblem = (ConstraintProblem)theEObject;
				T result = caseConstraintProblem(constraintProblem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.EXPRESSION: {
				Expression expression = (Expression)theEObject;
				T result = caseExpression(expression);
				if (result == null) result = caseCPElement(expression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.NUMERIC_EXPRESSION: {
				NumericExpression numericExpression = (NumericExpression)theEObject;
				T result = caseNumericExpression(numericExpression);
				if (result == null) result = caseExpression(numericExpression);
				if (result == null) result = caseCPElement(numericExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.CP_VARIABLE: {
				CpVariable cpVariable = (CpVariable)theEObject;
				T result = caseCpVariable(cpVariable);
				if (result == null) result = caseNumericExpression(cpVariable);
				if (result == null) result = caseExpression(cpVariable);
				if (result == null) result = caseCPElement(cpVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.DOMAIN: {
				Domain domain = (Domain)theEObject;
				T result = caseDomain(domain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.NUMERIC_DOMAIN: {
				NumericDomain numericDomain = (NumericDomain)theEObject;
				T result = caseNumericDomain(numericDomain);
				if (result == null) result = caseDomain(numericDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.RANGE_DOMAIN: {
				RangeDomain rangeDomain = (RangeDomain)theEObject;
				T result = caseRangeDomain(rangeDomain);
				if (result == null) result = caseNumericDomain(rangeDomain);
				if (result == null) result = caseDomain(rangeDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.NUMERIC_LIST_DOMAIN: {
				NumericListDomain numericListDomain = (NumericListDomain)theEObject;
				T result = caseNumericListDomain(numericListDomain);
				if (result == null) result = caseNumericDomain(numericListDomain);
				if (result == null) result = caseDomain(numericListDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.CONSTANT: {
				Constant constant = (Constant)theEObject;
				T result = caseConstant(constant);
				if (result == null) result = caseNumericExpression(constant);
				if (result == null) result = caseExpression(constant);
				if (result == null) result = caseCPElement(constant);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.COMPOSED_EXPRESSION: {
				ComposedExpression composedExpression = (ComposedExpression)theEObject;
				T result = caseComposedExpression(composedExpression);
				if (result == null) result = caseNumericExpression(composedExpression);
				if (result == null) result = caseExpression(composedExpression);
				if (result == null) result = caseCPElement(composedExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.COMPARISON_EXPRESSION: {
				ComparisonExpression comparisonExpression = (ComparisonExpression)theEObject;
				T result = caseComparisonExpression(comparisonExpression);
				if (result == null) result = caseBooleanExpression(comparisonExpression);
				if (result == null) result = caseExpression(comparisonExpression);
				if (result == null) result = caseCPElement(comparisonExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.GOAL: {
				Goal goal = (Goal)theEObject;
				T result = caseGoal(goal);
				if (result == null) result = caseCPElement(goal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.BOOLEAN_EXPRESSION: {
				BooleanExpression booleanExpression = (BooleanExpression)theEObject;
				T result = caseBooleanExpression(booleanExpression);
				if (result == null) result = caseExpression(booleanExpression);
				if (result == null) result = caseCPElement(booleanExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.LIST_DOMAIN: {
				ListDomain listDomain = (ListDomain)theEObject;
				T result = caseListDomain(listDomain);
				if (result == null) result = caseDomain(listDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.MULTI_RANGE_DOMAIN: {
				MultiRangeDomain multiRangeDomain = (MultiRangeDomain)theEObject;
				T result = caseMultiRangeDomain(multiRangeDomain);
				if (result == null) result = caseNumericDomain(multiRangeDomain);
				if (result == null) result = caseDomain(multiRangeDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.UNARY_EXPRESSION: {
				UnaryExpression unaryExpression = (UnaryExpression)theEObject;
				T result = caseUnaryExpression(unaryExpression);
				if (result == null) result = caseNumericExpression(unaryExpression);
				if (result == null) result = caseExpression(unaryExpression);
				if (result == null) result = caseCPElement(unaryExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.SIMPLE_UNARY_EXPRESSION: {
				SimpleUnaryExpression simpleUnaryExpression = (SimpleUnaryExpression)theEObject;
				T result = caseSimpleUnaryExpression(simpleUnaryExpression);
				if (result == null) result = caseUnaryExpression(simpleUnaryExpression);
				if (result == null) result = caseNumericExpression(simpleUnaryExpression);
				if (result == null) result = caseExpression(simpleUnaryExpression);
				if (result == null) result = caseCPElement(simpleUnaryExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.COMPOSED_UNARY_EXPRESSION: {
				ComposedUnaryExpression composedUnaryExpression = (ComposedUnaryExpression)theEObject;
				T result = caseComposedUnaryExpression(composedUnaryExpression);
				if (result == null) result = caseUnaryExpression(composedUnaryExpression);
				if (result == null) result = caseNumericExpression(composedUnaryExpression);
				if (result == null) result = caseExpression(composedUnaryExpression);
				if (result == null) result = caseCPElement(composedUnaryExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.BOOLEAN_DOMAIN: {
				BooleanDomain booleanDomain = (BooleanDomain)theEObject;
				T result = caseBooleanDomain(booleanDomain);
				if (result == null) result = caseDomain(booleanDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.CP_METRIC: {
				CpMetric cpMetric = (CpMetric)theEObject;
				T result = caseCpMetric(cpMetric);
				if (result == null) result = caseNumericExpression(cpMetric);
				if (result == null) result = caseExpression(cpMetric);
				if (result == null) result = caseCPElement(cpMetric);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.SOLUTION: {
				Solution solution = (Solution)theEObject;
				T result = caseSolution(solution);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.CP_VARIABLE_VALUE: {
				CpVariableValue cpVariableValue = (CpVariableValue)theEObject;
				T result = caseCpVariableValue(cpVariableValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.PARAMETER: {
				Parameter parameter = (Parameter)theEObject;
				T result = caseParameter(parameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.NORMALISED_UTILITY_DIMENSION: {
				NormalisedUtilityDimension normalisedUtilityDimension = (NormalisedUtilityDimension)theEObject;
				T result = caseNormalisedUtilityDimension(normalisedUtilityDimension);
				if (result == null) result = caseCpFunction(normalisedUtilityDimension);
				if (result == null) result = caseComposedExpression(normalisedUtilityDimension);
				if (result == null) result = caseNumericExpression(normalisedUtilityDimension);
				if (result == null) result = caseExpression(normalisedUtilityDimension);
				if (result == null) result = caseCPElement(normalisedUtilityDimension);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.CP_FUNCTION: {
				CpFunction cpFunction = (CpFunction)theEObject;
				T result = caseCpFunction(cpFunction);
				if (result == null) result = caseComposedExpression(cpFunction);
				if (result == null) result = caseNumericExpression(cpFunction);
				if (result == null) result = caseExpression(cpFunction);
				if (result == null) result = caseCPElement(cpFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CpPackage.CONFIGURATION_UPPERWARE: {
				ConfigurationUpperware configurationUpperware = (ConfigurationUpperware)theEObject;
				T result = caseConfigurationUpperware(configurationUpperware);
				if (result == null) result = caseNumericExpression(configurationUpperware);
				if (result == null) result = caseExpression(configurationUpperware);
				if (result == null) result = caseCPElement(configurationUpperware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CP Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CP Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCPElement(CPElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constraint Problem</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constraint Problem</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConstraintProblem(ConstraintProblem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Numeric Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Numeric Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNumericExpression(NumericExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCpVariable(CpVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDomain(Domain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Numeric Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Numeric Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNumericDomain(NumericDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Range Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Range Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRangeDomain(RangeDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Numeric List Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Numeric List Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNumericListDomain(NumericListDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConstant(Constant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composed Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composed Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComposedExpression(ComposedExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comparison Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comparison Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComparisonExpression(ComparisonExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Goal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Goal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGoal(Goal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanExpression(BooleanExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>List Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>List Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseListDomain(ListDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multi Range Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multi Range Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultiRangeDomain(MultiRangeDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnaryExpression(UnaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Unary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleUnaryExpression(SimpleUnaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composed Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composed Unary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComposedUnaryExpression(ComposedUnaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanDomain(BooleanDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCpMetric(CpMetric object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Solution</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Solution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSolution(Solution object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCpVariableValue(CpVariableValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameter(Parameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Normalised Utility Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Normalised Utility Dimension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNormalisedUtilityDimension(NormalisedUtilityDimension object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCpFunction(CpFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Configuration Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Configuration Upperware</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigurationUpperware(ConfigurationUpperware object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //CpSwitch
