/**
 */
package camel.security;

import camel.CamelPackage;

import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see camel.security.SecurityFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface SecurityPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "security";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.paasage.eu/camel/security";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "security";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SecurityPackage eINSTANCE = camel.security.impl.SecurityPackageImpl.init();

	/**
	 * The meta object id for the '{@link camel.security.impl.SecurityModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.security.impl.SecurityModelImpl
	 * @see camel.security.impl.SecurityPackageImpl#getSecurityModel()
	 * @generated
	 */
	int SECURITY_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Sec Controls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_MODEL__SEC_CONTROLS = 0;

	/**
	 * The feature id for the '<em><b>Sec Req</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_MODEL__SEC_REQ = 1;

	/**
	 * The feature id for the '<em><b>Sec Props</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_MODEL__SEC_PROPS = 2;

	/**
	 * The feature id for the '<em><b>Sec Metric</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_MODEL__SEC_METRIC = 3;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_MODEL_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.security.impl.SecurityControlImpl <em>Control</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.security.impl.SecurityControlImpl
	 * @see camel.security.impl.SecurityPackageImpl#getSecurityControl()
	 * @generated
	 */
	int SECURITY_CONTROL = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CONTROL__ID = 0;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CONTROL__DOMAIN = 1;

	/**
	 * The feature id for the '<em><b>Specification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CONTROL__SPECIFICATION = 2;

	/**
	 * The feature id for the '<em><b>Link To Sec Prop</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CONTROL__LINK_TO_SEC_PROP = 3;

	/**
	 * The number of structural features of the '<em>Control</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CONTROL_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Control</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CONTROL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.security.impl.SecurityMetricImpl <em>Metric</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.security.impl.SecurityMetricImpl
	 * @see camel.security.impl.SecurityPackageImpl#getSecurityMetric()
	 * @generated
	 */
	int SECURITY_METRIC = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_METRIC__ID = ScalabilityPackage.METRIC__ID;

	/**
	 * The feature id for the '<em><b>Has Template</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_METRIC__HAS_TEMPLATE = ScalabilityPackage.METRIC__HAS_TEMPLATE;

	/**
	 * The feature id for the '<em><b>Has Schedule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_METRIC__HAS_SCHEDULE = ScalabilityPackage.METRIC__HAS_SCHEDULE;

	/**
	 * The feature id for the '<em><b>Window</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_METRIC__WINDOW = ScalabilityPackage.METRIC__WINDOW;

	/**
	 * The feature id for the '<em><b>Component Metrics</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_METRIC__COMPONENT_METRICS = ScalabilityPackage.METRIC__COMPONENT_METRICS;

	/**
	 * The feature id for the '<em><b>Sensor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_METRIC__SENSOR = ScalabilityPackage.METRIC__SENSOR;

	/**
	 * The feature id for the '<em><b>Object Binding</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_METRIC__OBJECT_BINDING = ScalabilityPackage.METRIC__OBJECT_BINDING;

	/**
	 * The feature id for the '<em><b>Value Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_METRIC__VALUE_TYPE = ScalabilityPackage.METRIC__VALUE_TYPE;

	/**
	 * The number of structural features of the '<em>Metric</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_METRIC_FEATURE_COUNT = ScalabilityPackage.METRIC_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Check Recursiveness</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_METRIC___CHECK_RECURSIVENESS__METRIC_METRIC = ScalabilityPackage.METRIC___CHECK_RECURSIVENESS__METRIC_METRIC;

	/**
	 * The number of operations of the '<em>Metric</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_METRIC_OPERATION_COUNT = ScalabilityPackage.METRIC_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.security.impl.SecurityPropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.security.impl.SecurityPropertyImpl
	 * @see camel.security.impl.SecurityPackageImpl#getSecurityProperty()
	 * @generated
	 */
	int SECURITY_PROPERTY = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_PROPERTY__ID = ScalabilityPackage.PROPERTY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_PROPERTY__NAME = ScalabilityPackage.PROPERTY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_PROPERTY__DESCRIPTION = ScalabilityPackage.PROPERTY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Realized By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_PROPERTY__REALIZED_BY = ScalabilityPackage.PROPERTY__REALIZED_BY;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_PROPERTY__TYPE = ScalabilityPackage.PROPERTY__TYPE;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_PROPERTY__DOMAIN = ScalabilityPackage.PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_PROPERTY_FEATURE_COUNT = ScalabilityPackage.PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_PROPERTY_OPERATION_COUNT = ScalabilityPackage.PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.security.impl.CertifiableImpl <em>Certifiable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.security.impl.CertifiableImpl
	 * @see camel.security.impl.SecurityPackageImpl#getCertifiable()
	 * @generated
	 */
	int CERTIFIABLE = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERTIFIABLE__ID = SECURITY_PROPERTY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERTIFIABLE__NAME = SECURITY_PROPERTY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERTIFIABLE__DESCRIPTION = SECURITY_PROPERTY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Realized By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERTIFIABLE__REALIZED_BY = SECURITY_PROPERTY__REALIZED_BY;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERTIFIABLE__TYPE = SECURITY_PROPERTY__TYPE;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERTIFIABLE__DOMAIN = SECURITY_PROPERTY__DOMAIN;

	/**
	 * The number of structural features of the '<em>Certifiable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERTIFIABLE_FEATURE_COUNT = SECURITY_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Certifiable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERTIFIABLE_OPERATION_COUNT = SECURITY_PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.security.impl.SecurityRequirementImpl <em>Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.security.impl.SecurityRequirementImpl
	 * @see camel.security.impl.SecurityPackageImpl#getSecurityRequirement()
	 * @generated
	 */
	int SECURITY_REQUIREMENT = 5;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUIREMENT__PRIORITY = CamelPackage.REQUIREMENT__PRIORITY;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUIREMENT__ID = CamelPackage.REQUIREMENT__ID;

	/**
	 * The feature id for the '<em><b>On Sec Control</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUIREMENT__ON_SEC_CONTROL = CamelPackage.REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUIREMENT_FEATURE_COUNT = CamelPackage.REQUIREMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_REQUIREMENT_OPERATION_COUNT = CamelPackage.REQUIREMENT_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link camel.security.SecurityModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see camel.security.SecurityModel
	 * @generated
	 */
	EClass getSecurityModel();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.security.SecurityModel#getSecControls <em>Sec Controls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sec Controls</em>'.
	 * @see camel.security.SecurityModel#getSecControls()
	 * @see #getSecurityModel()
	 * @generated
	 */
	EReference getSecurityModel_SecControls();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.security.SecurityModel#getSecReq <em>Sec Req</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sec Req</em>'.
	 * @see camel.security.SecurityModel#getSecReq()
	 * @see #getSecurityModel()
	 * @generated
	 */
	EReference getSecurityModel_SecReq();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.security.SecurityModel#getSecProps <em>Sec Props</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sec Props</em>'.
	 * @see camel.security.SecurityModel#getSecProps()
	 * @see #getSecurityModel()
	 * @generated
	 */
	EReference getSecurityModel_SecProps();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.security.SecurityModel#getSecMetric <em>Sec Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sec Metric</em>'.
	 * @see camel.security.SecurityModel#getSecMetric()
	 * @see #getSecurityModel()
	 * @generated
	 */
	EReference getSecurityModel_SecMetric();

	/**
	 * Returns the meta object for class '{@link camel.security.SecurityControl <em>Control</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Control</em>'.
	 * @see camel.security.SecurityControl
	 * @generated
	 */
	EClass getSecurityControl();

	/**
	 * Returns the meta object for the attribute '{@link camel.security.SecurityControl#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see camel.security.SecurityControl#getId()
	 * @see #getSecurityControl()
	 * @generated
	 */
	EAttribute getSecurityControl_Id();

	/**
	 * Returns the meta object for the attribute '{@link camel.security.SecurityControl#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain</em>'.
	 * @see camel.security.SecurityControl#getDomain()
	 * @see #getSecurityControl()
	 * @generated
	 */
	EAttribute getSecurityControl_Domain();

	/**
	 * Returns the meta object for the attribute '{@link camel.security.SecurityControl#getSpecification <em>Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specification</em>'.
	 * @see camel.security.SecurityControl#getSpecification()
	 * @see #getSecurityControl()
	 * @generated
	 */
	EAttribute getSecurityControl_Specification();

	/**
	 * Returns the meta object for the reference list '{@link camel.security.SecurityControl#getLinkToSecProp <em>Link To Sec Prop</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Link To Sec Prop</em>'.
	 * @see camel.security.SecurityControl#getLinkToSecProp()
	 * @see #getSecurityControl()
	 * @generated
	 */
	EReference getSecurityControl_LinkToSecProp();

	/**
	 * Returns the meta object for class '{@link camel.security.SecurityMetric <em>Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric</em>'.
	 * @see camel.security.SecurityMetric
	 * @generated
	 */
	EClass getSecurityMetric();

	/**
	 * Returns the meta object for class '{@link camel.security.SecurityProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see camel.security.SecurityProperty
	 * @generated
	 */
	EClass getSecurityProperty();

	/**
	 * Returns the meta object for the attribute '{@link camel.security.SecurityProperty#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain</em>'.
	 * @see camel.security.SecurityProperty#getDomain()
	 * @see #getSecurityProperty()
	 * @generated
	 */
	EAttribute getSecurityProperty_Domain();

	/**
	 * Returns the meta object for class '{@link camel.security.Certifiable <em>Certifiable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Certifiable</em>'.
	 * @see camel.security.Certifiable
	 * @generated
	 */
	EClass getCertifiable();

	/**
	 * Returns the meta object for class '{@link camel.security.SecurityRequirement <em>Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Requirement</em>'.
	 * @see camel.security.SecurityRequirement
	 * @generated
	 */
	EClass getSecurityRequirement();

	/**
	 * Returns the meta object for the reference '{@link camel.security.SecurityRequirement#getOnSecControl <em>On Sec Control</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>On Sec Control</em>'.
	 * @see camel.security.SecurityRequirement#getOnSecControl()
	 * @see #getSecurityRequirement()
	 * @generated
	 */
	EReference getSecurityRequirement_OnSecControl();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SecurityFactory getSecurityFactory();

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
		 * The meta object literal for the '{@link camel.security.impl.SecurityModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.security.impl.SecurityModelImpl
		 * @see camel.security.impl.SecurityPackageImpl#getSecurityModel()
		 * @generated
		 */
		EClass SECURITY_MODEL = eINSTANCE.getSecurityModel();

		/**
		 * The meta object literal for the '<em><b>Sec Controls</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECURITY_MODEL__SEC_CONTROLS = eINSTANCE.getSecurityModel_SecControls();

		/**
		 * The meta object literal for the '<em><b>Sec Req</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECURITY_MODEL__SEC_REQ = eINSTANCE.getSecurityModel_SecReq();

		/**
		 * The meta object literal for the '<em><b>Sec Props</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECURITY_MODEL__SEC_PROPS = eINSTANCE.getSecurityModel_SecProps();

		/**
		 * The meta object literal for the '<em><b>Sec Metric</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECURITY_MODEL__SEC_METRIC = eINSTANCE.getSecurityModel_SecMetric();

		/**
		 * The meta object literal for the '{@link camel.security.impl.SecurityControlImpl <em>Control</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.security.impl.SecurityControlImpl
		 * @see camel.security.impl.SecurityPackageImpl#getSecurityControl()
		 * @generated
		 */
		EClass SECURITY_CONTROL = eINSTANCE.getSecurityControl();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SECURITY_CONTROL__ID = eINSTANCE.getSecurityControl_Id();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SECURITY_CONTROL__DOMAIN = eINSTANCE.getSecurityControl_Domain();

		/**
		 * The meta object literal for the '<em><b>Specification</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SECURITY_CONTROL__SPECIFICATION = eINSTANCE.getSecurityControl_Specification();

		/**
		 * The meta object literal for the '<em><b>Link To Sec Prop</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECURITY_CONTROL__LINK_TO_SEC_PROP = eINSTANCE.getSecurityControl_LinkToSecProp();

		/**
		 * The meta object literal for the '{@link camel.security.impl.SecurityMetricImpl <em>Metric</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.security.impl.SecurityMetricImpl
		 * @see camel.security.impl.SecurityPackageImpl#getSecurityMetric()
		 * @generated
		 */
		EClass SECURITY_METRIC = eINSTANCE.getSecurityMetric();

		/**
		 * The meta object literal for the '{@link camel.security.impl.SecurityPropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.security.impl.SecurityPropertyImpl
		 * @see camel.security.impl.SecurityPackageImpl#getSecurityProperty()
		 * @generated
		 */
		EClass SECURITY_PROPERTY = eINSTANCE.getSecurityProperty();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SECURITY_PROPERTY__DOMAIN = eINSTANCE.getSecurityProperty_Domain();

		/**
		 * The meta object literal for the '{@link camel.security.impl.CertifiableImpl <em>Certifiable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.security.impl.CertifiableImpl
		 * @see camel.security.impl.SecurityPackageImpl#getCertifiable()
		 * @generated
		 */
		EClass CERTIFIABLE = eINSTANCE.getCertifiable();

		/**
		 * The meta object literal for the '{@link camel.security.impl.SecurityRequirementImpl <em>Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.security.impl.SecurityRequirementImpl
		 * @see camel.security.impl.SecurityPackageImpl#getSecurityRequirement()
		 * @generated
		 */
		EClass SECURITY_REQUIREMENT = eINSTANCE.getSecurityRequirement();

		/**
		 * The meta object literal for the '<em><b>On Sec Control</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECURITY_REQUIREMENT__ON_SEC_CONTROL = eINSTANCE.getSecurityRequirement_OnSecControl();

	}

} //SecurityPackage
