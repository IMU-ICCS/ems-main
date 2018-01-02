/**
 */
package eu.paasage.upperware.metamodel.cp;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.cp.CpPackage
 * @generated
 */
public interface CpFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CpFactory eINSTANCE = eu.paasage.upperware.metamodel.cp.impl.CpFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Constraint Problem</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constraint Problem</em>'.
	 * @generated
	 */
	ConstraintProblem createConstraintProblem();

	/**
	 * Returns a new object of class '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable</em>'.
	 * @generated
	 */
	Variable createVariable();

	/**
	 * Returns a new object of class '<em>Numeric Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Numeric Domain</em>'.
	 * @generated
	 */
	NumericDomain createNumericDomain();

	/**
	 * Returns a new object of class '<em>Range Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Range Domain</em>'.
	 * @generated
	 */
	RangeDomain createRangeDomain();

	/**
	 * Returns a new object of class '<em>Numeric List Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Numeric List Domain</em>'.
	 * @generated
	 */
	NumericListDomain createNumericListDomain();

	/**
	 * Returns a new object of class '<em>Constant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constant</em>'.
	 * @generated
	 */
	Constant createConstant();

	/**
	 * Returns a new object of class '<em>Composed Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composed Expression</em>'.
	 * @generated
	 */
	ComposedExpression createComposedExpression();

	/**
	 * Returns a new object of class '<em>Comparison Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comparison Expression</em>'.
	 * @generated
	 */
	ComparisonExpression createComparisonExpression();

	/**
	 * Returns a new object of class '<em>Goal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Goal</em>'.
	 * @generated
	 */
	Goal createGoal();

	/**
	 * Returns a new object of class '<em>List Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>List Domain</em>'.
	 * @generated
	 */
	ListDomain createListDomain();

	/**
	 * Returns a new object of class '<em>Multi Range Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Multi Range Domain</em>'.
	 * @generated
	 */
	MultiRangeDomain createMultiRangeDomain();

	/**
	 * Returns a new object of class '<em>Boolean Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Domain</em>'.
	 * @generated
	 */
	BooleanDomain createBooleanDomain();

	/**
	 * Returns a new object of class '<em>Metric Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Variable</em>'.
	 * @generated
	 */
	MetricVariable createMetricVariable();

	/**
	 * Returns a new object of class '<em>Solution</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Solution</em>'.
	 * @generated
	 */
	Solution createSolution();

	/**
	 * Returns a new object of class '<em>Variable Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable Value</em>'.
	 * @generated
	 */
	VariableValue createVariableValue();

	/**
	 * Returns a new object of class '<em>Metric Variable Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Variable Value</em>'.
	 * @generated
	 */
	MetricVariableValue createMetricVariableValue();

	/**
	 * Returns a new object of class '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter</em>'.
	 * @generated
	 */
	Parameter createParameter();

	/**
	 * Returns a new object of class '<em>Normalised Utility Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Normalised Utility Dimension</em>'.
	 * @generated
	 */
	NormalisedUtilityDimension createNormalisedUtilityDimension();

	/**
	 * Returns a new object of class '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function</em>'.
	 * @generated
	 */
	Function createFunction();

	/**
	 * Returns a new object of class '<em>Configuration Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Configuration Upperware</em>'.
	 * @generated
	 */
	ConfigurationUpperware createConfigurationUpperware();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CpPackage getCpPackage();

} //CpFactory
