/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 */
public class CpFactoryImpl extends EFactoryImpl implements CpFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public static CpFactory init() {
		try {
			CpFactory theCpFactory = (CpFactory)EPackage.Registry.INSTANCE.getEFactory(CpPackage.eNS_URI);
			if (theCpFactory != null) {
				return theCpFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CpFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public CpFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CpPackage.CONSTRAINT_PROBLEM: return (EObject)createConstraintProblem();
			case CpPackage.VARIABLE: return (EObject)createVariable();
			case CpPackage.NUMERIC_DOMAIN: return (EObject)createNumericDomain();
			case CpPackage.RANGE_DOMAIN: return (EObject)createRangeDomain();
			case CpPackage.NUMERIC_LIST_DOMAIN: return (EObject)createNumericListDomain();
			case CpPackage.CONSTANT: return (EObject)createConstant();
			case CpPackage.COMPOSED_EXPRESSION: return (EObject)createComposedExpression();
			case CpPackage.COMPARISON_EXPRESSION: return (EObject)createComparisonExpression();
			case CpPackage.GOAL: return (EObject)createGoal();
			case CpPackage.LIST_DOMAIN: return (EObject)createListDomain();
			case CpPackage.MULTI_RANGE_DOMAIN: return (EObject)createMultiRangeDomain();
			case CpPackage.BOOLEAN_DOMAIN: return (EObject)createBooleanDomain();
			case CpPackage.METRIC_VARIABLE: return (EObject)createMetricVariable();
			case CpPackage.SOLUTION: return (EObject)createSolution();
			case CpPackage.VARIABLE_VALUE: return (EObject)createVariableValue();
			case CpPackage.METRIC_VARIABLE_VALUE: return (EObject)createMetricVariableValue();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case CpPackage.OPERATOR_ENUM:
				return createOperatorEnumFromString(eDataType, initialValue);
			case CpPackage.GOAL_OPERATOR_ENUM:
				return createGoalOperatorEnumFromString(eDataType, initialValue);
			case CpPackage.COMPARATOR_ENUM:
				return createComparatorEnumFromString(eDataType, initialValue);
			case CpPackage.SIMPLE_UNARY_OPERATOR_ENUM:
				return createSimpleUnaryOperatorEnumFromString(eDataType, initialValue);
			case CpPackage.COMPOSED_UNARY_OPERATOR_ENUM:
				return createComposedUnaryOperatorEnumFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case CpPackage.OPERATOR_ENUM:
				return convertOperatorEnumToString(eDataType, instanceValue);
			case CpPackage.GOAL_OPERATOR_ENUM:
				return convertGoalOperatorEnumToString(eDataType, instanceValue);
			case CpPackage.COMPARATOR_ENUM:
				return convertComparatorEnumToString(eDataType, instanceValue);
			case CpPackage.SIMPLE_UNARY_OPERATOR_ENUM:
				return convertSimpleUnaryOperatorEnumToString(eDataType, instanceValue);
			case CpPackage.COMPOSED_UNARY_OPERATOR_ENUM:
				return convertComposedUnaryOperatorEnumToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ConstraintProblem createConstraintProblem() {
		ConstraintProblemImpl constraintProblem = new ConstraintProblemImpl();
		return constraintProblem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Variable createVariable() {
		VariableImpl variable = new VariableImpl();
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public NumericDomain createNumericDomain() {
		NumericDomainImpl numericDomain = new NumericDomainImpl();
		return numericDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public RangeDomain createRangeDomain() {
		RangeDomainImpl rangeDomain = new RangeDomainImpl();
		return rangeDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public NumericListDomain createNumericListDomain() {
		NumericListDomainImpl numericListDomain = new NumericListDomainImpl();
		return numericListDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Constant createConstant() {
		ConstantImpl constant = new ConstantImpl();
		return constant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ComposedExpression createComposedExpression() {
		ComposedExpressionImpl composedExpression = new ComposedExpressionImpl();
		return composedExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ComparisonExpression createComparisonExpression() {
		ComparisonExpressionImpl comparisonExpression = new ComparisonExpressionImpl();
		return comparisonExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Goal createGoal() {
		GoalImpl goal = new GoalImpl();
		return goal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ListDomain createListDomain() {
		ListDomainImpl listDomain = new ListDomainImpl();
		return listDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public MultiRangeDomain createMultiRangeDomain() {
		MultiRangeDomainImpl multiRangeDomain = new MultiRangeDomainImpl();
		return multiRangeDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public BooleanDomain createBooleanDomain() {
		BooleanDomainImpl booleanDomain = new BooleanDomainImpl();
		return booleanDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public MetricVariable createMetricVariable() {
		MetricVariableImpl metricVariable = new MetricVariableImpl();
		return metricVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Solution createSolution() {
		SolutionImpl solution = new SolutionImpl();
		return solution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public VariableValue createVariableValue() {
		VariableValueImpl variableValue = new VariableValueImpl();
		return variableValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public MetricVariableValue createMetricVariableValue() {
		MetricVariableValueImpl metricVariableValue = new MetricVariableValueImpl();
		return metricVariableValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public OperatorEnum createOperatorEnumFromString(EDataType eDataType, String initialValue) {
		OperatorEnum result = OperatorEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String convertOperatorEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public GoalOperatorEnum createGoalOperatorEnumFromString(EDataType eDataType, String initialValue) {
		GoalOperatorEnum result = GoalOperatorEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String convertGoalOperatorEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ComparatorEnum createComparatorEnumFromString(EDataType eDataType, String initialValue) {
		ComparatorEnum result = ComparatorEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String convertComparatorEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public SimpleUnaryOperatorEnum createSimpleUnaryOperatorEnumFromString(EDataType eDataType, String initialValue) {
		SimpleUnaryOperatorEnum result = SimpleUnaryOperatorEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String convertSimpleUnaryOperatorEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ComposedUnaryOperatorEnum createComposedUnaryOperatorEnumFromString(EDataType eDataType, String initialValue) {
		ComposedUnaryOperatorEnum result = ComposedUnaryOperatorEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public String convertComposedUnaryOperatorEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public CpPackage getCpPackage() {
		return (CpPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * 
	 */
	@Deprecated
	public static CpPackage getPackage() {
		return CpPackage.eINSTANCE;
	}

} //CpFactoryImpl
