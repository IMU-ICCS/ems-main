/**
 */
package cp.impl;

import application.ApplicationPackage;

import application.impl.ApplicationPackageImpl;

import cp.BooleanDomain;
import cp.BooleanExpression;
import cp.CPElement;
import cp.ComparatorEnum;
import cp.ComparisonExpression;
import cp.ComposedExpression;
import cp.ComposedUnaryExpression;
import cp.ComposedUnaryOperatorEnum;
import cp.Constant;
import cp.ConstraintProblem;
import cp.CpFactory;
import cp.CpPackage;
import cp.Domain;
import cp.Expression;
import cp.Goal;
import cp.GoalOperatorEnum;
import cp.ListDomain;
import cp.MultiRangeDomain;
import cp.NumericDomain;
import cp.NumericExpression;
import cp.NumericListDomain;
import cp.OperatorEnum;
import cp.RangeDomain;
import cp.SimpleUnaryExpression;
import cp.SimpleUnaryOperatorEnum;
import cp.UnaryExpression;
import cp.Variable;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import types.TypesPackage;

import types.impl.TypesPackageImpl;

import types.typesPaasage.TypesPaasagePackage;

import types.typesPaasage.impl.TypesPaasagePackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CpPackageImpl extends EPackageImpl implements CpPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cpElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constraintProblemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numericExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass domainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numericDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rangeDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numericListDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass composedExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass comparisonExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass goalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass listDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiRangeDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleUnaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass composedUnaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum operatorEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum goalOperatorEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum comparatorEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum simpleUnaryOperatorEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum composedUnaryOperatorEnumEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see cp.CpPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CpPackageImpl() {
		super(eNS_URI, CpFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link CpPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CpPackage init() {
		if (isInited) return (CpPackage)EPackage.Registry.INSTANCE.getEPackage(CpPackage.eNS_URI);

		// Obtain or create and register package
		CpPackageImpl theCpPackage = (CpPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CpPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CpPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ApplicationPackageImpl theApplicationPackage = (ApplicationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ApplicationPackage.eNS_URI) instanceof ApplicationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ApplicationPackage.eNS_URI) : ApplicationPackage.eINSTANCE);
		TypesPackageImpl theTypesPackage = (TypesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI) instanceof TypesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI) : TypesPackage.eINSTANCE);
		TypesPaasagePackageImpl theTypesPaasagePackage = (TypesPaasagePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypesPaasagePackage.eNS_URI) instanceof TypesPaasagePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypesPaasagePackage.eNS_URI) : TypesPaasagePackage.eINSTANCE);

		// Create package meta-data objects
		theCpPackage.createPackageContents();
		theApplicationPackage.createPackageContents();
		theTypesPackage.createPackageContents();
		theTypesPaasagePackage.createPackageContents();

		// Initialize created meta-data
		theCpPackage.initializePackageContents();
		theApplicationPackage.initializePackageContents();
		theTypesPackage.initializePackageContents();
		theTypesPaasagePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCpPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CpPackage.eNS_URI, theCpPackage);
		return theCpPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCPElement() {
		return cpElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCPElement_Id() {
		return (EAttribute)cpElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstraintProblem() {
		return constraintProblemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraintProblem_Goals() {
		return (EReference)constraintProblemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraintProblem_Constants() {
		return (EReference)constraintProblemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraintProblem_Variables() {
		return (EReference)constraintProblemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraintProblem_Constraints() {
		return (EReference)constraintProblemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraintProblem_AuxExpressions() {
		return (EReference)constraintProblemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpression() {
		return expressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumericExpression() {
		return numericExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVariable_Value() {
		return (EReference)variableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVariable_Domain() {
		return (EReference)variableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDomain() {
		return domainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumericDomain() {
		return numericDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNumericDomain_Type() {
		return (EAttribute)numericDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNumericDomain_Value() {
		return (EReference)numericDomainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRangeDomain() {
		return rangeDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRangeDomain_From() {
		return (EReference)rangeDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRangeDomain_To() {
		return (EReference)rangeDomainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumericListDomain() {
		return numericListDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNumericListDomain_Values() {
		return (EReference)numericListDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstant() {
		return constantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConstant_Type() {
		return (EAttribute)constantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstant_Value() {
		return (EReference)constantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComposedExpression() {
		return composedExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComposedExpression_Expressions() {
		return (EReference)composedExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComposedExpression_Operator() {
		return (EAttribute)composedExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComparisonExpression() {
		return comparisonExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComparisonExpression_Exp1() {
		return (EReference)comparisonExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComparisonExpression_Exp2() {
		return (EReference)comparisonExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComparisonExpression_Comparator() {
		return (EAttribute)comparisonExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGoal() {
		return goalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGoal_Expression() {
		return (EReference)goalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGoal_GoalType() {
		return (EAttribute)goalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanExpression() {
		return booleanExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getListDomain() {
		return listDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getListDomain_Values() {
		return (EReference)listDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getListDomain_Value() {
		return (EReference)listDomainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMultiRangeDomain() {
		return multiRangeDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMultiRangeDomain_Ranges() {
		return (EReference)multiRangeDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUnaryExpression() {
		return unaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUnaryExpression_Expression() {
		return (EReference)unaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimpleUnaryExpression() {
		return simpleUnaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleUnaryExpression_Operator() {
		return (EAttribute)simpleUnaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComposedUnaryExpression() {
		return composedUnaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComposedUnaryExpression_Operator() {
		return (EAttribute)composedUnaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComposedUnaryExpression_Value() {
		return (EAttribute)composedUnaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanDomain() {
		return booleanDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOperatorEnum() {
		return operatorEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getGoalOperatorEnum() {
		return goalOperatorEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getComparatorEnum() {
		return comparatorEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSimpleUnaryOperatorEnum() {
		return simpleUnaryOperatorEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getComposedUnaryOperatorEnum() {
		return composedUnaryOperatorEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CpFactory getCpFactory() {
		return (CpFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		cpElementEClass = createEClass(CP_ELEMENT);
		createEAttribute(cpElementEClass, CP_ELEMENT__ID);

		constraintProblemEClass = createEClass(CONSTRAINT_PROBLEM);
		createEReference(constraintProblemEClass, CONSTRAINT_PROBLEM__GOALS);
		createEReference(constraintProblemEClass, CONSTRAINT_PROBLEM__CONSTANTS);
		createEReference(constraintProblemEClass, CONSTRAINT_PROBLEM__VARIABLES);
		createEReference(constraintProblemEClass, CONSTRAINT_PROBLEM__CONSTRAINTS);
		createEReference(constraintProblemEClass, CONSTRAINT_PROBLEM__AUX_EXPRESSIONS);

		expressionEClass = createEClass(EXPRESSION);

		numericExpressionEClass = createEClass(NUMERIC_EXPRESSION);

		variableEClass = createEClass(VARIABLE);
		createEReference(variableEClass, VARIABLE__VALUE);
		createEReference(variableEClass, VARIABLE__DOMAIN);

		domainEClass = createEClass(DOMAIN);

		numericDomainEClass = createEClass(NUMERIC_DOMAIN);
		createEAttribute(numericDomainEClass, NUMERIC_DOMAIN__TYPE);
		createEReference(numericDomainEClass, NUMERIC_DOMAIN__VALUE);

		rangeDomainEClass = createEClass(RANGE_DOMAIN);
		createEReference(rangeDomainEClass, RANGE_DOMAIN__FROM);
		createEReference(rangeDomainEClass, RANGE_DOMAIN__TO);

		numericListDomainEClass = createEClass(NUMERIC_LIST_DOMAIN);
		createEReference(numericListDomainEClass, NUMERIC_LIST_DOMAIN__VALUES);

		constantEClass = createEClass(CONSTANT);
		createEAttribute(constantEClass, CONSTANT__TYPE);
		createEReference(constantEClass, CONSTANT__VALUE);

		composedExpressionEClass = createEClass(COMPOSED_EXPRESSION);
		createEReference(composedExpressionEClass, COMPOSED_EXPRESSION__EXPRESSIONS);
		createEAttribute(composedExpressionEClass, COMPOSED_EXPRESSION__OPERATOR);

		comparisonExpressionEClass = createEClass(COMPARISON_EXPRESSION);
		createEReference(comparisonExpressionEClass, COMPARISON_EXPRESSION__EXP1);
		createEReference(comparisonExpressionEClass, COMPARISON_EXPRESSION__EXP2);
		createEAttribute(comparisonExpressionEClass, COMPARISON_EXPRESSION__COMPARATOR);

		goalEClass = createEClass(GOAL);
		createEReference(goalEClass, GOAL__EXPRESSION);
		createEAttribute(goalEClass, GOAL__GOAL_TYPE);

		booleanExpressionEClass = createEClass(BOOLEAN_EXPRESSION);

		listDomainEClass = createEClass(LIST_DOMAIN);
		createEReference(listDomainEClass, LIST_DOMAIN__VALUES);
		createEReference(listDomainEClass, LIST_DOMAIN__VALUE);

		multiRangeDomainEClass = createEClass(MULTI_RANGE_DOMAIN);
		createEReference(multiRangeDomainEClass, MULTI_RANGE_DOMAIN__RANGES);

		unaryExpressionEClass = createEClass(UNARY_EXPRESSION);
		createEReference(unaryExpressionEClass, UNARY_EXPRESSION__EXPRESSION);

		simpleUnaryExpressionEClass = createEClass(SIMPLE_UNARY_EXPRESSION);
		createEAttribute(simpleUnaryExpressionEClass, SIMPLE_UNARY_EXPRESSION__OPERATOR);

		composedUnaryExpressionEClass = createEClass(COMPOSED_UNARY_EXPRESSION);
		createEAttribute(composedUnaryExpressionEClass, COMPOSED_UNARY_EXPRESSION__OPERATOR);
		createEAttribute(composedUnaryExpressionEClass, COMPOSED_UNARY_EXPRESSION__VALUE);

		booleanDomainEClass = createEClass(BOOLEAN_DOMAIN);

		// Create enums
		operatorEnumEEnum = createEEnum(OPERATOR_ENUM);
		goalOperatorEnumEEnum = createEEnum(GOAL_OPERATOR_ENUM);
		comparatorEnumEEnum = createEEnum(COMPARATOR_ENUM);
		simpleUnaryOperatorEnumEEnum = createEEnum(SIMPLE_UNARY_OPERATOR_ENUM);
		composedUnaryOperatorEnumEEnum = createEEnum(COMPOSED_UNARY_OPERATOR_ENUM);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		expressionEClass.getESuperTypes().add(this.getCPElement());
		numericExpressionEClass.getESuperTypes().add(this.getExpression());
		variableEClass.getESuperTypes().add(this.getNumericExpression());
		numericDomainEClass.getESuperTypes().add(this.getDomain());
		rangeDomainEClass.getESuperTypes().add(this.getNumericDomain());
		numericListDomainEClass.getESuperTypes().add(this.getNumericDomain());
		constantEClass.getESuperTypes().add(this.getNumericExpression());
		composedExpressionEClass.getESuperTypes().add(this.getNumericExpression());
		comparisonExpressionEClass.getESuperTypes().add(this.getBooleanExpression());
		goalEClass.getESuperTypes().add(this.getCPElement());
		booleanExpressionEClass.getESuperTypes().add(this.getExpression());
		listDomainEClass.getESuperTypes().add(this.getDomain());
		multiRangeDomainEClass.getESuperTypes().add(this.getNumericDomain());
		unaryExpressionEClass.getESuperTypes().add(this.getNumericExpression());
		simpleUnaryExpressionEClass.getESuperTypes().add(this.getUnaryExpression());
		composedUnaryExpressionEClass.getESuperTypes().add(this.getUnaryExpression());
		booleanDomainEClass.getESuperTypes().add(this.getDomain());

		// Initialize classes, features, and operations; add parameters
		initEClass(cpElementEClass, CPElement.class, "CPElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCPElement_Id(), ecorePackage.getEString(), "id", null, 1, 1, CPElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(constraintProblemEClass, ConstraintProblem.class, "ConstraintProblem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConstraintProblem_Goals(), this.getGoal(), null, "goals", null, 0, -1, ConstraintProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraintProblem_Constants(), this.getConstant(), null, "constants", null, 0, -1, ConstraintProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraintProblem_Variables(), this.getVariable(), null, "variables", null, 0, -1, ConstraintProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraintProblem_Constraints(), this.getComparisonExpression(), null, "constraints", null, 0, -1, ConstraintProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraintProblem_AuxExpressions(), this.getExpression(), null, "auxExpressions", null, 0, -1, ConstraintProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expressionEClass, Expression.class, "Expression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(numericExpressionEClass, NumericExpression.class, "NumericExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariable_Value(), theTypesPackage.getNumericValue(), null, "value", null, 0, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVariable_Domain(), this.getDomain(), null, "domain", null, 1, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(domainEClass, Domain.class, "Domain", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(numericDomainEClass, NumericDomain.class, "NumericDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNumericDomain_Type(), theTypesPackage.getBasicTypeEnum(), "type", null, 1, 1, NumericDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNumericDomain_Value(), theTypesPackage.getNumericValue(), null, "value", null, 0, 1, NumericDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rangeDomainEClass, RangeDomain.class, "RangeDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRangeDomain_From(), theTypesPackage.getNumericValue(), null, "from", null, 1, 1, RangeDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRangeDomain_To(), theTypesPackage.getNumericValue(), null, "to", null, 1, 1, RangeDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(numericListDomainEClass, NumericListDomain.class, "NumericListDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNumericListDomain_Values(), theTypesPackage.getNumericValue(), null, "values", null, 1, -1, NumericListDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(constantEClass, Constant.class, "Constant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConstant_Type(), theTypesPackage.getBasicTypeEnum(), "type", null, 1, 1, Constant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstant_Value(), theTypesPackage.getNumericValue(), null, "value", null, 1, 1, Constant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(composedExpressionEClass, ComposedExpression.class, "ComposedExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComposedExpression_Expressions(), this.getNumericExpression(), null, "expressions", null, 2, -1, ComposedExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComposedExpression_Operator(), this.getOperatorEnum(), "operator", null, 0, 1, ComposedExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(comparisonExpressionEClass, ComparisonExpression.class, "ComparisonExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComparisonExpression_Exp1(), this.getExpression(), null, "exp1", null, 1, 1, ComparisonExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComparisonExpression_Exp2(), this.getExpression(), null, "exp2", null, 1, 1, ComparisonExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComparisonExpression_Comparator(), this.getComparatorEnum(), "comparator", null, 1, 1, ComparisonExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(goalEClass, Goal.class, "Goal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGoal_Expression(), this.getNumericExpression(), null, "expression", null, 1, 1, Goal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGoal_GoalType(), this.getGoalOperatorEnum(), "goalType", null, 1, 1, Goal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanExpressionEClass, BooleanExpression.class, "BooleanExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(listDomainEClass, ListDomain.class, "ListDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getListDomain_Values(), theTypesPackage.getStringValue(), null, "values", null, 1, -1, ListDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getListDomain_Value(), theTypesPackage.getStringValue(), null, "value", null, 0, 1, ListDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(multiRangeDomainEClass, MultiRangeDomain.class, "MultiRangeDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMultiRangeDomain_Ranges(), this.getRangeDomain(), null, "ranges", null, 2, -1, MultiRangeDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(unaryExpressionEClass, UnaryExpression.class, "UnaryExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUnaryExpression_Expression(), this.getNumericExpression(), null, "expression", null, 1, 1, UnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simpleUnaryExpressionEClass, SimpleUnaryExpression.class, "SimpleUnaryExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimpleUnaryExpression_Operator(), this.getSimpleUnaryOperatorEnum(), "operator", null, 1, 1, SimpleUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(composedUnaryExpressionEClass, ComposedUnaryExpression.class, "ComposedUnaryExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComposedUnaryExpression_Operator(), this.getComposedUnaryOperatorEnum(), "operator", null, 1, 1, ComposedUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComposedUnaryExpression_Value(), ecorePackage.getEInt(), "value", null, 1, 1, ComposedUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanDomainEClass, BooleanDomain.class, "BooleanDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(operatorEnumEEnum, OperatorEnum.class, "OperatorEnum");
		addEEnumLiteral(operatorEnumEEnum, OperatorEnum.PLUS);
		addEEnumLiteral(operatorEnumEEnum, OperatorEnum.MINUS);
		addEEnumLiteral(operatorEnumEEnum, OperatorEnum.TIMES);
		addEEnumLiteral(operatorEnumEEnum, OperatorEnum.DIV);

		initEEnum(goalOperatorEnumEEnum, GoalOperatorEnum.class, "GoalOperatorEnum");
		addEEnumLiteral(goalOperatorEnumEEnum, GoalOperatorEnum.MAX);
		addEEnumLiteral(goalOperatorEnumEEnum, GoalOperatorEnum.MIN);

		initEEnum(comparatorEnumEEnum, ComparatorEnum.class, "ComparatorEnum");
		addEEnumLiteral(comparatorEnumEEnum, ComparatorEnum.GREATER_THAN);
		addEEnumLiteral(comparatorEnumEEnum, ComparatorEnum.LESS_THAN);
		addEEnumLiteral(comparatorEnumEEnum, ComparatorEnum.GREATER_OR_EQUAL_TO);
		addEEnumLiteral(comparatorEnumEEnum, ComparatorEnum.LESS_OR_EQUAL_TO);
		addEEnumLiteral(comparatorEnumEEnum, ComparatorEnum.EQUAL_TO);
		addEEnumLiteral(comparatorEnumEEnum, ComparatorEnum.DIFFERENT);

		initEEnum(simpleUnaryOperatorEnumEEnum, SimpleUnaryOperatorEnum.class, "SimpleUnaryOperatorEnum");
		addEEnumLiteral(simpleUnaryOperatorEnumEEnum, SimpleUnaryOperatorEnum.ABSTRACT_VALUE);
		addEEnumLiteral(simpleUnaryOperatorEnumEEnum, SimpleUnaryOperatorEnum.LN_VALUE);

		initEEnum(composedUnaryOperatorEnumEEnum, ComposedUnaryOperatorEnum.class, "ComposedUnaryOperatorEnum");
		addEEnumLiteral(composedUnaryOperatorEnumEEnum, ComposedUnaryOperatorEnum.EXPONENTIAL_VALUE);
		addEEnumLiteral(composedUnaryOperatorEnumEEnum, ComposedUnaryOperatorEnum.LOG_VALUE);

		// Create resource
		createResource(eNS_URI);
	}

} //CpPackageImpl
