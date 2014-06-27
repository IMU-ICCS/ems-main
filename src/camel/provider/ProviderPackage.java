/**
 */
package camel.provider;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see camel.provider.ProviderFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface ProviderPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "provider";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.paasage.eu/camel/saloon/feature";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "provider";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProviderPackage eINSTANCE = camel.provider.impl.ProviderPackageImpl.init();

	/**
	 * The meta object id for the '{@link camel.provider.impl.ProviderModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.ProviderModelImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getProviderModel()
	 * @generated
	 */
	int PROVIDER_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_MODEL__CONSTRAINTS = 0;

	/**
	 * The feature id for the '<em><b>Root Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_MODEL__ROOT_FEATURE = 1;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_MODEL_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.AttributeImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Value Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__VALUE_TYPE = 2;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Check Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE___CHECK_VALUE__VALUE_BOOLEAN = 0;

	/**
	 * The number of operations of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link camel.provider.impl.AttributeConstraintImpl <em>Attribute Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.AttributeConstraintImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getAttributeConstraint()
	 * @generated
	 */
	int ATTRIBUTE_CONSTRAINT = 2;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT__TO = 1;

	/**
	 * The feature id for the '<em><b>From Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT__FROM_VALUE = 2;

	/**
	 * The feature id for the '<em><b>To Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT__TO_VALUE = 3;

	/**
	 * The number of structural features of the '<em>Attribute Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Attribute Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.CardinalityImpl <em>Cardinality</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.CardinalityImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getCardinality()
	 * @generated
	 */
	int CARDINALITY = 3;

	/**
	 * The feature id for the '<em><b>Cardinality Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARDINALITY__CARDINALITY_MIN = 0;

	/**
	 * The feature id for the '<em><b>Cardinality Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARDINALITY__CARDINALITY_MAX = 1;

	/**
	 * The number of structural features of the '<em>Cardinality</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARDINALITY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Cardinality</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARDINALITY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.FeatCardinalityImpl <em>Feat Cardinality</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.FeatCardinalityImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getFeatCardinality()
	 * @generated
	 */
	int FEAT_CARDINALITY = 4;

	/**
	 * The feature id for the '<em><b>Cardinality Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEAT_CARDINALITY__CARDINALITY_MIN = CARDINALITY__CARDINALITY_MIN;

	/**
	 * The feature id for the '<em><b>Cardinality Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEAT_CARDINALITY__CARDINALITY_MAX = CARDINALITY__CARDINALITY_MAX;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEAT_CARDINALITY__VALUE = CARDINALITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Feat Cardinality</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEAT_CARDINALITY_FEATURE_COUNT = CARDINALITY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Feat Cardinality</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEAT_CARDINALITY_OPERATION_COUNT = CARDINALITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.GroupCardinalityImpl <em>Group Cardinality</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.GroupCardinalityImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getGroupCardinality()
	 * @generated
	 */
	int GROUP_CARDINALITY = 5;

	/**
	 * The feature id for the '<em><b>Cardinality Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_CARDINALITY__CARDINALITY_MIN = CARDINALITY__CARDINALITY_MIN;

	/**
	 * The feature id for the '<em><b>Cardinality Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_CARDINALITY__CARDINALITY_MAX = CARDINALITY__CARDINALITY_MAX;

	/**
	 * The number of structural features of the '<em>Group Cardinality</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_CARDINALITY_FEATURE_COUNT = CARDINALITY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Group Cardinality</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_CARDINALITY_OPERATION_COUNT = CARDINALITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.CloneImpl <em>Clone</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.CloneImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getClone()
	 * @generated
	 */
	int CLONE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Sub Clones</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE__SUB_CLONES = 1;

	/**
	 * The number of structural features of the '<em>Clone</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Clone</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.ConstraintImpl <em>Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.ConstraintImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getConstraint()
	 * @generated
	 */
	int CONSTRAINT = 7;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__TO = 1;

	/**
	 * The feature id for the '<em><b>Attribute Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__ATTRIBUTE_CONSTRAINTS = 2;

	/**
	 * The number of structural features of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.ExcludesImpl <em>Excludes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.ExcludesImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getExcludes()
	 * @generated
	 */
	int EXCLUDES = 8;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUDES__FROM = CONSTRAINT__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUDES__TO = CONSTRAINT__TO;

	/**
	 * The feature id for the '<em><b>Attribute Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUDES__ATTRIBUTE_CONSTRAINTS = CONSTRAINT__ATTRIBUTE_CONSTRAINTS;

	/**
	 * The number of structural features of the '<em>Excludes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUDES_FEATURE_COUNT = CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Excludes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUDES_OPERATION_COUNT = CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.ImpliesImpl <em>Implies</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.ImpliesImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getImplies()
	 * @generated
	 */
	int IMPLIES = 9;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLIES__FROM = CONSTRAINT__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLIES__TO = CONSTRAINT__TO;

	/**
	 * The feature id for the '<em><b>Attribute Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLIES__ATTRIBUTE_CONSTRAINTS = CONSTRAINT__ATTRIBUTE_CONSTRAINTS;

	/**
	 * The number of structural features of the '<em>Implies</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLIES_FEATURE_COUNT = CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Implies</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPLIES_OPERATION_COUNT = CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.RequiresImpl <em>Requires</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.RequiresImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getRequires()
	 * @generated
	 */
	int REQUIRES = 10;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRES__FROM = CONSTRAINT__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRES__TO = CONSTRAINT__TO;

	/**
	 * The feature id for the '<em><b>Attribute Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRES__ATTRIBUTE_CONSTRAINTS = CONSTRAINT__ATTRIBUTE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Scope From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRES__SCOPE_FROM = CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Scope To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRES__SCOPE_TO = CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Card From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRES__CARD_FROM = CONSTRAINT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Card To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRES__CARD_TO = CONSTRAINT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Requires</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRES_FEATURE_COUNT = CONSTRAINT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Requires</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRES_OPERATION_COUNT = CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.FunctionalImpl <em>Functional</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.FunctionalImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getFunctional()
	 * @generated
	 */
	int FUNCTIONAL = 11;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL__FROM = REQUIRES__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL__TO = REQUIRES__TO;

	/**
	 * The feature id for the '<em><b>Attribute Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL__ATTRIBUTE_CONSTRAINTS = REQUIRES__ATTRIBUTE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Scope From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL__SCOPE_FROM = REQUIRES__SCOPE_FROM;

	/**
	 * The feature id for the '<em><b>Scope To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL__SCOPE_TO = REQUIRES__SCOPE_TO;

	/**
	 * The feature id for the '<em><b>Card From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL__CARD_FROM = REQUIRES__CARD_FROM;

	/**
	 * The feature id for the '<em><b>Card To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL__CARD_TO = REQUIRES__CARD_TO;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL__TYPE = REQUIRES_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL__ORDER = REQUIRES_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL__VALUE = REQUIRES_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Functional</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL_FEATURE_COUNT = REQUIRES_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Functional</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL_OPERATION_COUNT = REQUIRES_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.FeatureImpl <em>Feature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.FeatureImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getFeature()
	 * @generated
	 */
	int FEATURE = 12;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__ATTRIBUTES = 0;

	/**
	 * The feature id for the '<em><b>Sub Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__SUB_FEATURES = 1;

	/**
	 * The feature id for the '<em><b>Feature Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__FEATURE_CARDINALITY = 2;

	/**
	 * The feature id for the '<em><b>Clones</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__CLONES = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__NAME = 4;

	/**
	 * The number of structural features of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.AlternativeImpl <em>Alternative</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.AlternativeImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getAlternative()
	 * @generated
	 */
	int ALTERNATIVE = 13;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALTERNATIVE__ATTRIBUTES = FEATURE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Sub Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALTERNATIVE__SUB_FEATURES = FEATURE__SUB_FEATURES;

	/**
	 * The feature id for the '<em><b>Feature Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALTERNATIVE__FEATURE_CARDINALITY = FEATURE__FEATURE_CARDINALITY;

	/**
	 * The feature id for the '<em><b>Clones</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALTERNATIVE__CLONES = FEATURE__CLONES;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALTERNATIVE__NAME = FEATURE__NAME;

	/**
	 * The feature id for the '<em><b>Group Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALTERNATIVE__GROUP_CARDINALITY = FEATURE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Variants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALTERNATIVE__VARIANTS = FEATURE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Alternative</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALTERNATIVE_FEATURE_COUNT = FEATURE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Alternative</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALTERNATIVE_OPERATION_COUNT = FEATURE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.ExclusiveImpl <em>Exclusive</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.ExclusiveImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getExclusive()
	 * @generated
	 */
	int EXCLUSIVE = 14;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE__ATTRIBUTES = ALTERNATIVE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Sub Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE__SUB_FEATURES = ALTERNATIVE__SUB_FEATURES;

	/**
	 * The feature id for the '<em><b>Feature Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE__FEATURE_CARDINALITY = ALTERNATIVE__FEATURE_CARDINALITY;

	/**
	 * The feature id for the '<em><b>Clones</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE__CLONES = ALTERNATIVE__CLONES;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE__NAME = ALTERNATIVE__NAME;

	/**
	 * The feature id for the '<em><b>Group Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE__GROUP_CARDINALITY = ALTERNATIVE__GROUP_CARDINALITY;

	/**
	 * The feature id for the '<em><b>Variants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE__VARIANTS = ALTERNATIVE__VARIANTS;

	/**
	 * The number of structural features of the '<em>Exclusive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE_FEATURE_COUNT = ALTERNATIVE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Exclusive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUSIVE_OPERATION_COUNT = ALTERNATIVE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.ScopeImpl <em>Scope</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.ScopeImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getScope()
	 * @generated
	 */
	int SCOPE = 15;

	/**
	 * The number of structural features of the '<em>Scope</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Scope</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.InstanceImpl <em>Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.InstanceImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getInstance()
	 * @generated
	 */
	int INSTANCE = 16;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__FEATURE = SCOPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_FEATURE_COUNT = SCOPE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_OPERATION_COUNT = SCOPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.provider.impl.ProductImpl <em>Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.impl.ProductImpl
	 * @see camel.provider.impl.ProviderPackageImpl#getProduct()
	 * @generated
	 */
	int PRODUCT = 17;

	/**
	 * The number of structural features of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_FEATURE_COUNT = SCOPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_OPERATION_COUNT = SCOPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.provider.Operator <em>Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.provider.Operator
	 * @see camel.provider.impl.ProviderPackageImpl#getOperator()
	 * @generated
	 */
	int OPERATOR = 18;


	/**
	 * Returns the meta object for class '{@link camel.provider.ProviderModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see camel.provider.ProviderModel
	 * @generated
	 */
	EClass getProviderModel();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.provider.ProviderModel#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraints</em>'.
	 * @see camel.provider.ProviderModel#getConstraints()
	 * @see #getProviderModel()
	 * @generated
	 */
	EReference getProviderModel_Constraints();

	/**
	 * Returns the meta object for the containment reference '{@link camel.provider.ProviderModel#getRootFeature <em>Root Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Root Feature</em>'.
	 * @see camel.provider.ProviderModel#getRootFeature()
	 * @see #getProviderModel()
	 * @generated
	 */
	EReference getProviderModel_RootFeature();

	/**
	 * Returns the meta object for class '{@link camel.provider.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see camel.provider.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the attribute '{@link camel.provider.Attribute#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.provider.Attribute#getName()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Name();

	/**
	 * Returns the meta object for the containment reference '{@link camel.provider.Attribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see camel.provider.Attribute#getValue()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_Value();

	/**
	 * Returns the meta object for the containment reference '{@link camel.provider.Attribute#getValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Type</em>'.
	 * @see camel.provider.Attribute#getValueType()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_ValueType();

	/**
	 * Returns the meta object for the '{@link camel.provider.Attribute#checkValue(camel.type.Value, boolean) <em>Check Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Value</em>' operation.
	 * @see camel.provider.Attribute#checkValue(camel.type.Value, boolean)
	 * @generated
	 */
	EOperation getAttribute__CheckValue__Value_boolean();

	/**
	 * Returns the meta object for class '{@link camel.provider.AttributeConstraint <em>Attribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Constraint</em>'.
	 * @see camel.provider.AttributeConstraint
	 * @generated
	 */
	EClass getAttributeConstraint();

	/**
	 * Returns the meta object for the reference '{@link camel.provider.AttributeConstraint#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see camel.provider.AttributeConstraint#getFrom()
	 * @see #getAttributeConstraint()
	 * @generated
	 */
	EReference getAttributeConstraint_From();

	/**
	 * Returns the meta object for the reference '{@link camel.provider.AttributeConstraint#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see camel.provider.AttributeConstraint#getTo()
	 * @see #getAttributeConstraint()
	 * @generated
	 */
	EReference getAttributeConstraint_To();

	/**
	 * Returns the meta object for the containment reference '{@link camel.provider.AttributeConstraint#getFromValue <em>From Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>From Value</em>'.
	 * @see camel.provider.AttributeConstraint#getFromValue()
	 * @see #getAttributeConstraint()
	 * @generated
	 */
	EReference getAttributeConstraint_FromValue();

	/**
	 * Returns the meta object for the containment reference '{@link camel.provider.AttributeConstraint#getToValue <em>To Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>To Value</em>'.
	 * @see camel.provider.AttributeConstraint#getToValue()
	 * @see #getAttributeConstraint()
	 * @generated
	 */
	EReference getAttributeConstraint_ToValue();

	/**
	 * Returns the meta object for class '{@link camel.provider.Cardinality <em>Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cardinality</em>'.
	 * @see camel.provider.Cardinality
	 * @generated
	 */
	EClass getCardinality();

	/**
	 * Returns the meta object for the attribute '{@link camel.provider.Cardinality#getCardinalityMin <em>Cardinality Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cardinality Min</em>'.
	 * @see camel.provider.Cardinality#getCardinalityMin()
	 * @see #getCardinality()
	 * @generated
	 */
	EAttribute getCardinality_CardinalityMin();

	/**
	 * Returns the meta object for the attribute '{@link camel.provider.Cardinality#getCardinalityMax <em>Cardinality Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cardinality Max</em>'.
	 * @see camel.provider.Cardinality#getCardinalityMax()
	 * @see #getCardinality()
	 * @generated
	 */
	EAttribute getCardinality_CardinalityMax();

	/**
	 * Returns the meta object for class '{@link camel.provider.FeatCardinality <em>Feat Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feat Cardinality</em>'.
	 * @see camel.provider.FeatCardinality
	 * @generated
	 */
	EClass getFeatCardinality();

	/**
	 * Returns the meta object for the attribute '{@link camel.provider.FeatCardinality#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see camel.provider.FeatCardinality#getValue()
	 * @see #getFeatCardinality()
	 * @generated
	 */
	EAttribute getFeatCardinality_Value();

	/**
	 * Returns the meta object for class '{@link camel.provider.GroupCardinality <em>Group Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group Cardinality</em>'.
	 * @see camel.provider.GroupCardinality
	 * @generated
	 */
	EClass getGroupCardinality();

	/**
	 * Returns the meta object for class '{@link camel.provider.Clone <em>Clone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Clone</em>'.
	 * @see camel.provider.Clone
	 * @generated
	 */
	EClass getClone();

	/**
	 * Returns the meta object for the attribute '{@link camel.provider.Clone#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.provider.Clone#getName()
	 * @see #getClone()
	 * @generated
	 */
	EAttribute getClone_Name();

	/**
	 * Returns the meta object for the reference list '{@link camel.provider.Clone#getSubClones <em>Sub Clones</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Clones</em>'.
	 * @see camel.provider.Clone#getSubClones()
	 * @see #getClone()
	 * @generated
	 */
	EReference getClone_SubClones();

	/**
	 * Returns the meta object for class '{@link camel.provider.Constraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint</em>'.
	 * @see camel.provider.Constraint
	 * @generated
	 */
	EClass getConstraint();

	/**
	 * Returns the meta object for the reference '{@link camel.provider.Constraint#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see camel.provider.Constraint#getFrom()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_From();

	/**
	 * Returns the meta object for the reference '{@link camel.provider.Constraint#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see camel.provider.Constraint#getTo()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_To();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.provider.Constraint#getAttributeConstraints <em>Attribute Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Constraints</em>'.
	 * @see camel.provider.Constraint#getAttributeConstraints()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_AttributeConstraints();

	/**
	 * Returns the meta object for class '{@link camel.provider.Excludes <em>Excludes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Excludes</em>'.
	 * @see camel.provider.Excludes
	 * @generated
	 */
	EClass getExcludes();

	/**
	 * Returns the meta object for class '{@link camel.provider.Implies <em>Implies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Implies</em>'.
	 * @see camel.provider.Implies
	 * @generated
	 */
	EClass getImplies();

	/**
	 * Returns the meta object for class '{@link camel.provider.Requires <em>Requires</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Requires</em>'.
	 * @see camel.provider.Requires
	 * @generated
	 */
	EClass getRequires();

	/**
	 * Returns the meta object for the containment reference '{@link camel.provider.Requires#getScopeFrom <em>Scope From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Scope From</em>'.
	 * @see camel.provider.Requires#getScopeFrom()
	 * @see #getRequires()
	 * @generated
	 */
	EReference getRequires_ScopeFrom();

	/**
	 * Returns the meta object for the containment reference '{@link camel.provider.Requires#getScopeTo <em>Scope To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Scope To</em>'.
	 * @see camel.provider.Requires#getScopeTo()
	 * @see #getRequires()
	 * @generated
	 */
	EReference getRequires_ScopeTo();

	/**
	 * Returns the meta object for the containment reference '{@link camel.provider.Requires#getCardFrom <em>Card From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Card From</em>'.
	 * @see camel.provider.Requires#getCardFrom()
	 * @see #getRequires()
	 * @generated
	 */
	EReference getRequires_CardFrom();

	/**
	 * Returns the meta object for the containment reference '{@link camel.provider.Requires#getCardTo <em>Card To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Card To</em>'.
	 * @see camel.provider.Requires#getCardTo()
	 * @see #getRequires()
	 * @generated
	 */
	EReference getRequires_CardTo();

	/**
	 * Returns the meta object for class '{@link camel.provider.Functional <em>Functional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Functional</em>'.
	 * @see camel.provider.Functional
	 * @generated
	 */
	EClass getFunctional();

	/**
	 * Returns the meta object for the attribute '{@link camel.provider.Functional#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see camel.provider.Functional#getType()
	 * @see #getFunctional()
	 * @generated
	 */
	EAttribute getFunctional_Type();

	/**
	 * Returns the meta object for the attribute '{@link camel.provider.Functional#getOrder <em>Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Order</em>'.
	 * @see camel.provider.Functional#getOrder()
	 * @see #getFunctional()
	 * @generated
	 */
	EAttribute getFunctional_Order();

	/**
	 * Returns the meta object for the attribute '{@link camel.provider.Functional#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see camel.provider.Functional#getValue()
	 * @see #getFunctional()
	 * @generated
	 */
	EAttribute getFunctional_Value();

	/**
	 * Returns the meta object for class '{@link camel.provider.Feature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature</em>'.
	 * @see camel.provider.Feature
	 * @generated
	 */
	EClass getFeature();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.provider.Feature#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see camel.provider.Feature#getAttributes()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Attributes();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.provider.Feature#getSubFeatures <em>Sub Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Features</em>'.
	 * @see camel.provider.Feature#getSubFeatures()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_SubFeatures();

	/**
	 * Returns the meta object for the containment reference '{@link camel.provider.Feature#getFeatureCardinality <em>Feature Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feature Cardinality</em>'.
	 * @see camel.provider.Feature#getFeatureCardinality()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_FeatureCardinality();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.provider.Feature#getClones <em>Clones</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Clones</em>'.
	 * @see camel.provider.Feature#getClones()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Clones();

	/**
	 * Returns the meta object for the attribute '{@link camel.provider.Feature#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.provider.Feature#getName()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Name();

	/**
	 * Returns the meta object for class '{@link camel.provider.Alternative <em>Alternative</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alternative</em>'.
	 * @see camel.provider.Alternative
	 * @generated
	 */
	EClass getAlternative();

	/**
	 * Returns the meta object for the containment reference '{@link camel.provider.Alternative#getGroupCardinality <em>Group Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Group Cardinality</em>'.
	 * @see camel.provider.Alternative#getGroupCardinality()
	 * @see #getAlternative()
	 * @generated
	 */
	EReference getAlternative_GroupCardinality();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.provider.Alternative#getVariants <em>Variants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variants</em>'.
	 * @see camel.provider.Alternative#getVariants()
	 * @see #getAlternative()
	 * @generated
	 */
	EReference getAlternative_Variants();

	/**
	 * Returns the meta object for class '{@link camel.provider.Exclusive <em>Exclusive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exclusive</em>'.
	 * @see camel.provider.Exclusive
	 * @generated
	 */
	EClass getExclusive();

	/**
	 * Returns the meta object for class '{@link camel.provider.Scope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scope</em>'.
	 * @see camel.provider.Scope
	 * @generated
	 */
	EClass getScope();

	/**
	 * Returns the meta object for class '{@link camel.provider.Instance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instance</em>'.
	 * @see camel.provider.Instance
	 * @generated
	 */
	EClass getInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.provider.Instance#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see camel.provider.Instance#getFeature()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Feature();

	/**
	 * Returns the meta object for class '{@link camel.provider.Product <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product</em>'.
	 * @see camel.provider.Product
	 * @generated
	 */
	EClass getProduct();

	/**
	 * Returns the meta object for enum '{@link camel.provider.Operator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operator</em>'.
	 * @see camel.provider.Operator
	 * @generated
	 */
	EEnum getOperator();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProviderFactory getProviderFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link camel.provider.impl.ProviderModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.ProviderModelImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getProviderModel()
		 * @generated
		 */
		EClass PROVIDER_MODEL = eINSTANCE.getProviderModel();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDER_MODEL__CONSTRAINTS = eINSTANCE.getProviderModel_Constraints();

		/**
		 * The meta object literal for the '<em><b>Root Feature</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDER_MODEL__ROOT_FEATURE = eINSTANCE.getProviderModel_RootFeature();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.AttributeImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__NAME = eINSTANCE.getAttribute_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__VALUE = eINSTANCE.getAttribute_Value();

		/**
		 * The meta object literal for the '<em><b>Value Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__VALUE_TYPE = eINSTANCE.getAttribute_ValueType();

		/**
		 * The meta object literal for the '<em><b>Check Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE___CHECK_VALUE__VALUE_BOOLEAN = eINSTANCE.getAttribute__CheckValue__Value_boolean();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.AttributeConstraintImpl <em>Attribute Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.AttributeConstraintImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getAttributeConstraint()
		 * @generated
		 */
		EClass ATTRIBUTE_CONSTRAINT = eINSTANCE.getAttributeConstraint();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_CONSTRAINT__FROM = eINSTANCE.getAttributeConstraint_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_CONSTRAINT__TO = eINSTANCE.getAttributeConstraint_To();

		/**
		 * The meta object literal for the '<em><b>From Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_CONSTRAINT__FROM_VALUE = eINSTANCE.getAttributeConstraint_FromValue();

		/**
		 * The meta object literal for the '<em><b>To Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_CONSTRAINT__TO_VALUE = eINSTANCE.getAttributeConstraint_ToValue();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.CardinalityImpl <em>Cardinality</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.CardinalityImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getCardinality()
		 * @generated
		 */
		EClass CARDINALITY = eINSTANCE.getCardinality();

		/**
		 * The meta object literal for the '<em><b>Cardinality Min</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARDINALITY__CARDINALITY_MIN = eINSTANCE.getCardinality_CardinalityMin();

		/**
		 * The meta object literal for the '<em><b>Cardinality Max</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARDINALITY__CARDINALITY_MAX = eINSTANCE.getCardinality_CardinalityMax();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.FeatCardinalityImpl <em>Feat Cardinality</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.FeatCardinalityImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getFeatCardinality()
		 * @generated
		 */
		EClass FEAT_CARDINALITY = eINSTANCE.getFeatCardinality();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEAT_CARDINALITY__VALUE = eINSTANCE.getFeatCardinality_Value();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.GroupCardinalityImpl <em>Group Cardinality</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.GroupCardinalityImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getGroupCardinality()
		 * @generated
		 */
		EClass GROUP_CARDINALITY = eINSTANCE.getGroupCardinality();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.CloneImpl <em>Clone</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.CloneImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getClone()
		 * @generated
		 */
		EClass CLONE = eINSTANCE.getClone();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLONE__NAME = eINSTANCE.getClone_Name();

		/**
		 * The meta object literal for the '<em><b>Sub Clones</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLONE__SUB_CLONES = eINSTANCE.getClone_SubClones();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.ConstraintImpl <em>Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.ConstraintImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getConstraint()
		 * @generated
		 */
		EClass CONSTRAINT = eINSTANCE.getConstraint();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT__FROM = eINSTANCE.getConstraint_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT__TO = eINSTANCE.getConstraint_To();

		/**
		 * The meta object literal for the '<em><b>Attribute Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT__ATTRIBUTE_CONSTRAINTS = eINSTANCE.getConstraint_AttributeConstraints();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.ExcludesImpl <em>Excludes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.ExcludesImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getExcludes()
		 * @generated
		 */
		EClass EXCLUDES = eINSTANCE.getExcludes();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.ImpliesImpl <em>Implies</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.ImpliesImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getImplies()
		 * @generated
		 */
		EClass IMPLIES = eINSTANCE.getImplies();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.RequiresImpl <em>Requires</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.RequiresImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getRequires()
		 * @generated
		 */
		EClass REQUIRES = eINSTANCE.getRequires();

		/**
		 * The meta object literal for the '<em><b>Scope From</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIRES__SCOPE_FROM = eINSTANCE.getRequires_ScopeFrom();

		/**
		 * The meta object literal for the '<em><b>Scope To</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIRES__SCOPE_TO = eINSTANCE.getRequires_ScopeTo();

		/**
		 * The meta object literal for the '<em><b>Card From</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIRES__CARD_FROM = eINSTANCE.getRequires_CardFrom();

		/**
		 * The meta object literal for the '<em><b>Card To</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIRES__CARD_TO = eINSTANCE.getRequires_CardTo();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.FunctionalImpl <em>Functional</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.FunctionalImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getFunctional()
		 * @generated
		 */
		EClass FUNCTIONAL = eINSTANCE.getFunctional();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTIONAL__TYPE = eINSTANCE.getFunctional_Type();

		/**
		 * The meta object literal for the '<em><b>Order</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTIONAL__ORDER = eINSTANCE.getFunctional_Order();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTIONAL__VALUE = eINSTANCE.getFunctional_Value();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.FeatureImpl <em>Feature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.FeatureImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getFeature()
		 * @generated
		 */
		EClass FEATURE = eINSTANCE.getFeature();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__ATTRIBUTES = eINSTANCE.getFeature_Attributes();

		/**
		 * The meta object literal for the '<em><b>Sub Features</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__SUB_FEATURES = eINSTANCE.getFeature_SubFeatures();

		/**
		 * The meta object literal for the '<em><b>Feature Cardinality</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__FEATURE_CARDINALITY = eINSTANCE.getFeature_FeatureCardinality();

		/**
		 * The meta object literal for the '<em><b>Clones</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__CLONES = eINSTANCE.getFeature_Clones();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__NAME = eINSTANCE.getFeature_Name();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.AlternativeImpl <em>Alternative</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.AlternativeImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getAlternative()
		 * @generated
		 */
		EClass ALTERNATIVE = eINSTANCE.getAlternative();

		/**
		 * The meta object literal for the '<em><b>Group Cardinality</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALTERNATIVE__GROUP_CARDINALITY = eINSTANCE.getAlternative_GroupCardinality();

		/**
		 * The meta object literal for the '<em><b>Variants</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALTERNATIVE__VARIANTS = eINSTANCE.getAlternative_Variants();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.ExclusiveImpl <em>Exclusive</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.ExclusiveImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getExclusive()
		 * @generated
		 */
		EClass EXCLUSIVE = eINSTANCE.getExclusive();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.ScopeImpl <em>Scope</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.ScopeImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getScope()
		 * @generated
		 */
		EClass SCOPE = eINSTANCE.getScope();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.InstanceImpl <em>Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.InstanceImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getInstance()
		 * @generated
		 */
		EClass INSTANCE = eINSTANCE.getInstance();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE__FEATURE = eINSTANCE.getInstance_Feature();

		/**
		 * The meta object literal for the '{@link camel.provider.impl.ProductImpl <em>Product</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.impl.ProductImpl
		 * @see camel.provider.impl.ProviderPackageImpl#getProduct()
		 * @generated
		 */
		EClass PRODUCT = eINSTANCE.getProduct();

		/**
		 * The meta object literal for the '{@link camel.provider.Operator <em>Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.provider.Operator
		 * @see camel.provider.impl.ProviderPackageImpl#getOperator()
		 * @generated
		 */
		EEnum OPERATOR = eINSTANCE.getOperator();

	}

} //ProviderPackage
