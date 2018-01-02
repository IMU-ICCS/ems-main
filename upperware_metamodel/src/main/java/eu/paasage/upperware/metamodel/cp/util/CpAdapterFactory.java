/**
 */
package eu.paasage.upperware.metamodel.cp.util;

import eu.paasage.upperware.metamodel.cp.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.cp.CpPackage
 * @generated
 */
public class CpAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CpPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CpAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CpPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CpSwitch<Adapter> modelSwitch =
		new CpSwitch<Adapter>() {
			@Override
			public Adapter caseCPElement(CPElement object) {
				return createCPElementAdapter();
			}
			@Override
			public Adapter caseConstraintProblem(ConstraintProblem object) {
				return createConstraintProblemAdapter();
			}
			@Override
			public Adapter caseExpression(Expression object) {
				return createExpressionAdapter();
			}
			@Override
			public Adapter caseNumericExpression(NumericExpression object) {
				return createNumericExpressionAdapter();
			}
			@Override
			public Adapter caseVariable(Variable object) {
				return createVariableAdapter();
			}
			@Override
			public Adapter caseDomain(Domain object) {
				return createDomainAdapter();
			}
			@Override
			public Adapter caseNumericDomain(NumericDomain object) {
				return createNumericDomainAdapter();
			}
			@Override
			public Adapter caseRangeDomain(RangeDomain object) {
				return createRangeDomainAdapter();
			}
			@Override
			public Adapter caseNumericListDomain(NumericListDomain object) {
				return createNumericListDomainAdapter();
			}
			@Override
			public Adapter caseConstant(Constant object) {
				return createConstantAdapter();
			}
			@Override
			public Adapter caseComposedExpression(ComposedExpression object) {
				return createComposedExpressionAdapter();
			}
			@Override
			public Adapter caseComparisonExpression(ComparisonExpression object) {
				return createComparisonExpressionAdapter();
			}
			@Override
			public Adapter caseGoal(Goal object) {
				return createGoalAdapter();
			}
			@Override
			public Adapter caseBooleanExpression(BooleanExpression object) {
				return createBooleanExpressionAdapter();
			}
			@Override
			public Adapter caseListDomain(ListDomain object) {
				return createListDomainAdapter();
			}
			@Override
			public Adapter caseMultiRangeDomain(MultiRangeDomain object) {
				return createMultiRangeDomainAdapter();
			}
			@Override
			public Adapter caseUnaryExpression(UnaryExpression object) {
				return createUnaryExpressionAdapter();
			}
			@Override
			public Adapter caseSimpleUnaryExpression(SimpleUnaryExpression object) {
				return createSimpleUnaryExpressionAdapter();
			}
			@Override
			public Adapter caseComposedUnaryExpression(ComposedUnaryExpression object) {
				return createComposedUnaryExpressionAdapter();
			}
			@Override
			public Adapter caseBooleanDomain(BooleanDomain object) {
				return createBooleanDomainAdapter();
			}
			@Override
			public Adapter caseMetricVariable(MetricVariable object) {
				return createMetricVariableAdapter();
			}
			@Override
			public Adapter caseSolution(Solution object) {
				return createSolutionAdapter();
			}
			@Override
			public Adapter caseVariableValue(VariableValue object) {
				return createVariableValueAdapter();
			}
			@Override
			public Adapter caseMetricVariableValue(MetricVariableValue object) {
				return createMetricVariableValueAdapter();
			}
			@Override
			public Adapter caseParameter(Parameter object) {
				return createParameterAdapter();
			}
			@Override
			public Adapter caseNormalisedUtilityDimension(NormalisedUtilityDimension object) {
				return createNormalisedUtilityDimensionAdapter();
			}
			@Override
			public Adapter caseFunction(Function object) {
				return createFunctionAdapter();
			}
			@Override
			public Adapter caseConfigurationUpperware(ConfigurationUpperware object) {
				return createConfigurationUpperwareAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.CPElement <em>CP Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.CPElement
	 * @generated
	 */
	public Adapter createCPElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem <em>Constraint Problem</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem
	 * @generated
	 */
	public Adapter createConstraintProblemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.Expression
	 * @generated
	 */
	public Adapter createExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.NumericExpression <em>Numeric Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.NumericExpression
	 * @generated
	 */
	public Adapter createNumericExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.Variable
	 * @generated
	 */
	public Adapter createVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.Domain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.Domain
	 * @generated
	 */
	public Adapter createDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.NumericDomain <em>Numeric Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.NumericDomain
	 * @generated
	 */
	public Adapter createNumericDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.RangeDomain <em>Range Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.RangeDomain
	 * @generated
	 */
	public Adapter createRangeDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.NumericListDomain <em>Numeric List Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.NumericListDomain
	 * @generated
	 */
	public Adapter createNumericListDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.Constant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.Constant
	 * @generated
	 */
	public Adapter createConstantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.ComposedExpression <em>Composed Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedExpression
	 * @generated
	 */
	public Adapter createComposedExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression <em>Comparison Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.ComparisonExpression
	 * @generated
	 */
	public Adapter createComparisonExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.Goal <em>Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.Goal
	 * @generated
	 */
	public Adapter createGoalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.BooleanExpression <em>Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.BooleanExpression
	 * @generated
	 */
	public Adapter createBooleanExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.ListDomain <em>List Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.ListDomain
	 * @generated
	 */
	public Adapter createListDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.MultiRangeDomain <em>Multi Range Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.MultiRangeDomain
	 * @generated
	 */
	public Adapter createMultiRangeDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.UnaryExpression
	 * @generated
	 */
	public Adapter createUnaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression <em>Simple Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression
	 * @generated
	 */
	public Adapter createSimpleUnaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression <em>Composed Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression
	 * @generated
	 */
	public Adapter createComposedUnaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.BooleanDomain <em>Boolean Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.BooleanDomain
	 * @generated
	 */
	public Adapter createBooleanDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.MetricVariable <em>Metric Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariable
	 * @generated
	 */
	public Adapter createMetricVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.Solution <em>Solution</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.Solution
	 * @generated
	 */
	public Adapter createSolutionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.VariableValue <em>Variable Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.VariableValue
	 * @generated
	 */
	public Adapter createVariableValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.MetricVariableValue <em>Metric Variable Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariableValue
	 * @generated
	 */
	public Adapter createMetricVariableValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.Parameter
	 * @generated
	 */
	public Adapter createParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension <em>Normalised Utility Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension
	 * @generated
	 */
	public Adapter createNormalisedUtilityDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.Function
	 * @generated
	 */
	public Adapter createFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link eu.paasage.upperware.metamodel.cp.ConfigurationUpperware <em>Configuration Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see eu.paasage.upperware.metamodel.cp.ConfigurationUpperware
	 * @generated
	 */
	public Adapter createConfigurationUpperwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //CpAdapterFactory
