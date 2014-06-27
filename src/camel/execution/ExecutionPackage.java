/**
 */
package camel.execution;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see camel.execution.ExecutionFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface ExecutionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "execution";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.paasage.eu/camel/execution";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "execution";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExecutionPackage eINSTANCE = camel.execution.impl.ExecutionPackageImpl.init();

	/**
	 * The meta object id for the '{@link camel.execution.impl.ExecutionModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.execution.impl.ExecutionModelImpl
	 * @see camel.execution.impl.ExecutionPackageImpl#getExecutionModel()
	 * @generated
	 */
	int EXECUTION_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Execution Context</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_MODEL__EXECUTION_CONTEXT = 0;

	/**
	 * The feature id for the '<em><b>Measurement</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_MODEL__MEASUREMENT = 1;

	/**
	 * The feature id for the '<em><b>Slo Assess</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_MODEL__SLO_ASSESS = 2;

	/**
	 * The feature id for the '<em><b>Rule Trigger</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_MODEL__RULE_TRIGGER = 3;

	/**
	 * The feature id for the '<em><b>Action Realization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_MODEL__ACTION_REALIZATION = 4;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_MODEL_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.execution.impl.ActionRealizationImpl <em>Action Realization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.execution.impl.ActionRealizationImpl
	 * @see camel.execution.impl.ExecutionPackageImpl#getActionRealization()
	 * @generated
	 */
	int ACTION_REALIZATION = 1;

	/**
	 * The feature id for the '<em><b>Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_REALIZATION__ACTION = 0;

	/**
	 * The feature id for the '<em><b>Started On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_REALIZATION__STARTED_ON = 1;

	/**
	 * The feature id for the '<em><b>Ended On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_REALIZATION__ENDED_ON = 2;

	/**
	 * The feature id for the '<em><b>Low Level Actions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_REALIZATION__LOW_LEVEL_ACTIONS = 3;

	/**
	 * The number of structural features of the '<em>Action Realization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_REALIZATION_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>Check Start End Dates</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_REALIZATION___CHECK_START_END_DATES__ACTIONREALIZATION = 0;

	/**
	 * The number of operations of the '<em>Action Realization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_REALIZATION_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link camel.execution.impl.ExecutionContextImpl <em>Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.execution.impl.ExecutionContextImpl
	 * @see camel.execution.impl.ExecutionPackageImpl#getExecutionContext()
	 * @generated
	 */
	int EXECUTION_CONTEXT = 2;

	/**
	 * The feature id for the '<em><b>Of Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__OF_APPLICATION = 0;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__ID = 1;

	/**
	 * The feature id for the '<em><b>Start Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__START_TIME = 2;

	/**
	 * The feature id for the '<em><b>End Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__END_TIME = 3;

	/**
	 * The feature id for the '<em><b>Total Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__TOTAL_COST = 4;

	/**
	 * The feature id for the '<em><b>Cost Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__COST_UNIT = 5;

	/**
	 * The feature id for the '<em><b>Involves Deployment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__INVOLVES_DEPLOYMENT = 6;

	/**
	 * The feature id for the '<em><b>Based On Requirements</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__BASED_ON_REQUIREMENTS = 7;

	/**
	 * The number of structural features of the '<em>Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT_FEATURE_COUNT = 8;

	/**
	 * The operation id for the '<em>Check Start End Dates</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT___CHECK_START_END_DATES__EXECUTIONCONTEXT = 0;

	/**
	 * The number of operations of the '<em>Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link camel.execution.impl.MeasurementImpl <em>Measurement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.execution.impl.MeasurementImpl
	 * @see camel.execution.impl.ExecutionPackageImpl#getMeasurement()
	 * @generated
	 */
	int MEASUREMENT = 3;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASUREMENT__ID = 0;

	/**
	 * The feature id for the '<em><b>In Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASUREMENT__IN_EXECUTION_CONTEXT = 1;

	/**
	 * The feature id for the '<em><b>Of Metric</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASUREMENT__OF_METRIC = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASUREMENT__VALUE = 3;

	/**
	 * The feature id for the '<em><b>Raw Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASUREMENT__RAW_DATA = 4;

	/**
	 * The feature id for the '<em><b>Reported On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASUREMENT__REPORTED_ON = 5;

	/**
	 * The feature id for the '<em><b>It SLO</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASUREMENT__IT_SLO = 6;

	/**
	 * The feature id for the '<em><b>Triggers</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASUREMENT__TRIGGERS = 7;

	/**
	 * The number of structural features of the '<em>Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASUREMENT_FEATURE_COUNT = 8;

	/**
	 * The operation id for the '<em>Check Date</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASUREMENT___CHECK_DATE__MEASUREMENT = 0;

	/**
	 * The number of operations of the '<em>Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASUREMENT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link camel.execution.impl.ApplicationMeasurementImpl <em>Application Measurement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.execution.impl.ApplicationMeasurementImpl
	 * @see camel.execution.impl.ExecutionPackageImpl#getApplicationMeasurement()
	 * @generated
	 */
	int APPLICATION_MEASUREMENT = 4;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT__ID = MEASUREMENT__ID;

	/**
	 * The feature id for the '<em><b>In Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT__IN_EXECUTION_CONTEXT = MEASUREMENT__IN_EXECUTION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Of Metric</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT__OF_METRIC = MEASUREMENT__OF_METRIC;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT__VALUE = MEASUREMENT__VALUE;

	/**
	 * The feature id for the '<em><b>Raw Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT__RAW_DATA = MEASUREMENT__RAW_DATA;

	/**
	 * The feature id for the '<em><b>Reported On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT__REPORTED_ON = MEASUREMENT__REPORTED_ON;

	/**
	 * The feature id for the '<em><b>It SLO</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT__IT_SLO = MEASUREMENT__IT_SLO;

	/**
	 * The feature id for the '<em><b>Triggers</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT__TRIGGERS = MEASUREMENT__TRIGGERS;

	/**
	 * The feature id for the '<em><b>For Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT__FOR_APPLICATION = MEASUREMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Application Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT_FEATURE_COUNT = MEASUREMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Check Date</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT___CHECK_DATE__MEASUREMENT = MEASUREMENT___CHECK_DATE__MEASUREMENT;

	/**
	 * The number of operations of the '<em>Application Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_MEASUREMENT_OPERATION_COUNT = MEASUREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.execution.impl.InternalComponentMeasurementImpl <em>Internal Component Measurement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.execution.impl.InternalComponentMeasurementImpl
	 * @see camel.execution.impl.ExecutionPackageImpl#getInternalComponentMeasurement()
	 * @generated
	 */
	int INTERNAL_COMPONENT_MEASUREMENT = 5;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT__ID = MEASUREMENT__ID;

	/**
	 * The feature id for the '<em><b>In Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT__IN_EXECUTION_CONTEXT = MEASUREMENT__IN_EXECUTION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Of Metric</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT__OF_METRIC = MEASUREMENT__OF_METRIC;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT__VALUE = MEASUREMENT__VALUE;

	/**
	 * The feature id for the '<em><b>Raw Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT__RAW_DATA = MEASUREMENT__RAW_DATA;

	/**
	 * The feature id for the '<em><b>Reported On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT__REPORTED_ON = MEASUREMENT__REPORTED_ON;

	/**
	 * The feature id for the '<em><b>It SLO</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT__IT_SLO = MEASUREMENT__IT_SLO;

	/**
	 * The feature id for the '<em><b>Triggers</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT__TRIGGERS = MEASUREMENT__TRIGGERS;

	/**
	 * The feature id for the '<em><b>Of Component Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT__OF_COMPONENT_INSTANCE = MEASUREMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Internal Component Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT_FEATURE_COUNT = MEASUREMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Check Date</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT___CHECK_DATE__MEASUREMENT = MEASUREMENT___CHECK_DATE__MEASUREMENT;

	/**
	 * The number of operations of the '<em>Internal Component Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_MEASUREMENT_OPERATION_COUNT = MEASUREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.execution.impl.ResourceCouplingMeasurementImpl <em>Resource Coupling Measurement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.execution.impl.ResourceCouplingMeasurementImpl
	 * @see camel.execution.impl.ExecutionPackageImpl#getResourceCouplingMeasurement()
	 * @generated
	 */
	int RESOURCE_COUPLING_MEASUREMENT = 6;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT__ID = MEASUREMENT__ID;

	/**
	 * The feature id for the '<em><b>In Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT__IN_EXECUTION_CONTEXT = MEASUREMENT__IN_EXECUTION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Of Metric</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT__OF_METRIC = MEASUREMENT__OF_METRIC;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT__VALUE = MEASUREMENT__VALUE;

	/**
	 * The feature id for the '<em><b>Raw Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT__RAW_DATA = MEASUREMENT__RAW_DATA;

	/**
	 * The feature id for the '<em><b>Reported On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT__REPORTED_ON = MEASUREMENT__REPORTED_ON;

	/**
	 * The feature id for the '<em><b>It SLO</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT__IT_SLO = MEASUREMENT__IT_SLO;

	/**
	 * The feature id for the '<em><b>Triggers</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT__TRIGGERS = MEASUREMENT__TRIGGERS;

	/**
	 * The feature id for the '<em><b>Source VM Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT__SOURCE_VM_INSTANCE = MEASUREMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Destination VM Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT__DESTINATION_VM_INSTANCE = MEASUREMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Resource Coupling Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT_FEATURE_COUNT = MEASUREMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Check Date</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT___CHECK_DATE__MEASUREMENT = MEASUREMENT___CHECK_DATE__MEASUREMENT;

	/**
	 * The number of operations of the '<em>Resource Coupling Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_COUPLING_MEASUREMENT_OPERATION_COUNT = MEASUREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.execution.impl.ResourceMeasurementImpl <em>Resource Measurement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.execution.impl.ResourceMeasurementImpl
	 * @see camel.execution.impl.ExecutionPackageImpl#getResourceMeasurement()
	 * @generated
	 */
	int RESOURCE_MEASUREMENT = 7;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__ID = MEASUREMENT__ID;

	/**
	 * The feature id for the '<em><b>In Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__IN_EXECUTION_CONTEXT = MEASUREMENT__IN_EXECUTION_CONTEXT;

	/**
	 * The feature id for the '<em><b>Of Metric</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__OF_METRIC = MEASUREMENT__OF_METRIC;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__VALUE = MEASUREMENT__VALUE;

	/**
	 * The feature id for the '<em><b>Raw Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__RAW_DATA = MEASUREMENT__RAW_DATA;

	/**
	 * The feature id for the '<em><b>Reported On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__REPORTED_ON = MEASUREMENT__REPORTED_ON;

	/**
	 * The feature id for the '<em><b>It SLO</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__IT_SLO = MEASUREMENT__IT_SLO;

	/**
	 * The feature id for the '<em><b>Triggers</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__TRIGGERS = MEASUREMENT__TRIGGERS;

	/**
	 * The feature id for the '<em><b>Of VM Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__OF_VM_INSTANCE = MEASUREMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Resource Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__RESOURCE_CLASS = MEASUREMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Physical Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__PHYSICAL_NODE = MEASUREMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Data Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT__DATA_OBJECT = MEASUREMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Resource Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT_FEATURE_COUNT = MEASUREMENT_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Check Date</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT___CHECK_DATE__MEASUREMENT = MEASUREMENT___CHECK_DATE__MEASUREMENT;

	/**
	 * The number of operations of the '<em>Resource Measurement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_MEASUREMENT_OPERATION_COUNT = MEASUREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.execution.impl.RuleTriggerImpl <em>Rule Trigger</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.execution.impl.RuleTriggerImpl
	 * @see camel.execution.impl.ExecutionPackageImpl#getRuleTrigger()
	 * @generated
	 */
	int RULE_TRIGGER = 8;

	/**
	 * The feature id for the '<em><b>Scalability Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRIGGER__SCALABILITY_RULE = 0;

	/**
	 * The feature id for the '<em><b>Event Instances</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRIGGER__EVENT_INSTANCES = 1;

	/**
	 * The feature id for the '<em><b>Action Realizations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRIGGER__ACTION_REALIZATIONS = 2;

	/**
	 * The feature id for the '<em><b>Fired On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRIGGER__FIRED_ON = 3;

	/**
	 * The feature id for the '<em><b>Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRIGGER__EXECUTION_CONTEXT = 4;

	/**
	 * The number of structural features of the '<em>Rule Trigger</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRIGGER_FEATURE_COUNT = 5;

	/**
	 * The operation id for the '<em>Check Date</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRIGGER___CHECK_DATE__RULETRIGGER = 0;

	/**
	 * The number of operations of the '<em>Rule Trigger</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_TRIGGER_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link camel.execution.impl.SLOAssessmentImpl <em>SLO Assessment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.execution.impl.SLOAssessmentImpl
	 * @see camel.execution.impl.ExecutionPackageImpl#getSLOAssessment()
	 * @generated
	 */
	int SLO_ASSESSMENT = 9;

	/**
	 * The feature id for the '<em><b>It SLO</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLO_ASSESSMENT__IT_SLO = 0;

	/**
	 * The feature id for the '<em><b>Assessment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLO_ASSESSMENT__ASSESSMENT = 1;

	/**
	 * The feature id for the '<em><b>In Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLO_ASSESSMENT__IN_EXECUTION_CONTEXT = 2;

	/**
	 * The feature id for the '<em><b>Measurement</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLO_ASSESSMENT__MEASUREMENT = 3;

	/**
	 * The feature id for the '<em><b>Assessment Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLO_ASSESSMENT__ASSESSMENT_TIME = 4;

	/**
	 * The number of structural features of the '<em>SLO Assessment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLO_ASSESSMENT_FEATURE_COUNT = 5;

	/**
	 * The operation id for the '<em>Check Date</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLO_ASSESSMENT___CHECK_DATE__SLOASSESSMENT = 0;

	/**
	 * The number of operations of the '<em>SLO Assessment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLO_ASSESSMENT_OPERATION_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link camel.execution.ExecutionModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see camel.execution.ExecutionModel
	 * @generated
	 */
	EClass getExecutionModel();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.execution.ExecutionModel#getExecutionContext <em>Execution Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Execution Context</em>'.
	 * @see camel.execution.ExecutionModel#getExecutionContext()
	 * @see #getExecutionModel()
	 * @generated
	 */
	EReference getExecutionModel_ExecutionContext();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.execution.ExecutionModel#getMeasurement <em>Measurement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Measurement</em>'.
	 * @see camel.execution.ExecutionModel#getMeasurement()
	 * @see #getExecutionModel()
	 * @generated
	 */
	EReference getExecutionModel_Measurement();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.execution.ExecutionModel#getSloAssess <em>Slo Assess</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Slo Assess</em>'.
	 * @see camel.execution.ExecutionModel#getSloAssess()
	 * @see #getExecutionModel()
	 * @generated
	 */
	EReference getExecutionModel_SloAssess();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.execution.ExecutionModel#getRuleTrigger <em>Rule Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rule Trigger</em>'.
	 * @see camel.execution.ExecutionModel#getRuleTrigger()
	 * @see #getExecutionModel()
	 * @generated
	 */
	EReference getExecutionModel_RuleTrigger();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.execution.ExecutionModel#getActionRealization <em>Action Realization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Action Realization</em>'.
	 * @see camel.execution.ExecutionModel#getActionRealization()
	 * @see #getExecutionModel()
	 * @generated
	 */
	EReference getExecutionModel_ActionRealization();

	/**
	 * Returns the meta object for class '{@link camel.execution.ActionRealization <em>Action Realization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Realization</em>'.
	 * @see camel.execution.ActionRealization
	 * @generated
	 */
	EClass getActionRealization();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ActionRealization#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Action</em>'.
	 * @see camel.execution.ActionRealization#getAction()
	 * @see #getActionRealization()
	 * @generated
	 */
	EReference getActionRealization_Action();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.ActionRealization#getStartedOn <em>Started On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Started On</em>'.
	 * @see camel.execution.ActionRealization#getStartedOn()
	 * @see #getActionRealization()
	 * @generated
	 */
	EAttribute getActionRealization_StartedOn();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.ActionRealization#getEndedOn <em>Ended On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ended On</em>'.
	 * @see camel.execution.ActionRealization#getEndedOn()
	 * @see #getActionRealization()
	 * @generated
	 */
	EAttribute getActionRealization_EndedOn();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.ActionRealization#getLowLevelActions <em>Low Level Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Low Level Actions</em>'.
	 * @see camel.execution.ActionRealization#getLowLevelActions()
	 * @see #getActionRealization()
	 * @generated
	 */
	EAttribute getActionRealization_LowLevelActions();

	/**
	 * Returns the meta object for the '{@link camel.execution.ActionRealization#checkStartEndDates(camel.execution.ActionRealization) <em>Check Start End Dates</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Start End Dates</em>' operation.
	 * @see camel.execution.ActionRealization#checkStartEndDates(camel.execution.ActionRealization)
	 * @generated
	 */
	EOperation getActionRealization__CheckStartEndDates__ActionRealization();

	/**
	 * Returns the meta object for class '{@link camel.execution.ExecutionContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context</em>'.
	 * @see camel.execution.ExecutionContext
	 * @generated
	 */
	EClass getExecutionContext();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ExecutionContext#getOfApplication <em>Of Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of Application</em>'.
	 * @see camel.execution.ExecutionContext#getOfApplication()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EReference getExecutionContext_OfApplication();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.ExecutionContext#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see camel.execution.ExecutionContext#getID()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EAttribute getExecutionContext_ID();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.ExecutionContext#getStartTime <em>Start Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Time</em>'.
	 * @see camel.execution.ExecutionContext#getStartTime()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EAttribute getExecutionContext_StartTime();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.ExecutionContext#getEndTime <em>End Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Time</em>'.
	 * @see camel.execution.ExecutionContext#getEndTime()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EAttribute getExecutionContext_EndTime();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.ExecutionContext#getTotalCost <em>Total Cost</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Cost</em>'.
	 * @see camel.execution.ExecutionContext#getTotalCost()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EAttribute getExecutionContext_TotalCost();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ExecutionContext#getCostUnit <em>Cost Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Cost Unit</em>'.
	 * @see camel.execution.ExecutionContext#getCostUnit()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EReference getExecutionContext_CostUnit();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ExecutionContext#getInvolvesDeployment <em>Involves Deployment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Involves Deployment</em>'.
	 * @see camel.execution.ExecutionContext#getInvolvesDeployment()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EReference getExecutionContext_InvolvesDeployment();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ExecutionContext#getBasedOnRequirements <em>Based On Requirements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Based On Requirements</em>'.
	 * @see camel.execution.ExecutionContext#getBasedOnRequirements()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EReference getExecutionContext_BasedOnRequirements();

	/**
	 * Returns the meta object for the '{@link camel.execution.ExecutionContext#checkStartEndDates(camel.execution.ExecutionContext) <em>Check Start End Dates</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Start End Dates</em>' operation.
	 * @see camel.execution.ExecutionContext#checkStartEndDates(camel.execution.ExecutionContext)
	 * @generated
	 */
	EOperation getExecutionContext__CheckStartEndDates__ExecutionContext();

	/**
	 * Returns the meta object for class '{@link camel.execution.Measurement <em>Measurement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Measurement</em>'.
	 * @see camel.execution.Measurement
	 * @generated
	 */
	EClass getMeasurement();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.Measurement#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see camel.execution.Measurement#getID()
	 * @see #getMeasurement()
	 * @generated
	 */
	EAttribute getMeasurement_ID();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.Measurement#getInExecutionContext <em>In Execution Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>In Execution Context</em>'.
	 * @see camel.execution.Measurement#getInExecutionContext()
	 * @see #getMeasurement()
	 * @generated
	 */
	EReference getMeasurement_InExecutionContext();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.Measurement#getOfMetric <em>Of Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of Metric</em>'.
	 * @see camel.execution.Measurement#getOfMetric()
	 * @see #getMeasurement()
	 * @generated
	 */
	EReference getMeasurement_OfMetric();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.Measurement#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see camel.execution.Measurement#getValue()
	 * @see #getMeasurement()
	 * @generated
	 */
	EAttribute getMeasurement_Value();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.Measurement#getRawData <em>Raw Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Raw Data</em>'.
	 * @see camel.execution.Measurement#getRawData()
	 * @see #getMeasurement()
	 * @generated
	 */
	EAttribute getMeasurement_RawData();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.Measurement#getReportedOn <em>Reported On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reported On</em>'.
	 * @see camel.execution.Measurement#getReportedOn()
	 * @see #getMeasurement()
	 * @generated
	 */
	EAttribute getMeasurement_ReportedOn();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.Measurement#getItSLO <em>It SLO</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>It SLO</em>'.
	 * @see camel.execution.Measurement#getItSLO()
	 * @see #getMeasurement()
	 * @generated
	 */
	EReference getMeasurement_ItSLO();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.Measurement#getTriggers <em>Triggers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Triggers</em>'.
	 * @see camel.execution.Measurement#getTriggers()
	 * @see #getMeasurement()
	 * @generated
	 */
	EReference getMeasurement_Triggers();

	/**
	 * Returns the meta object for the '{@link camel.execution.Measurement#checkDate(camel.execution.Measurement) <em>Check Date</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Date</em>' operation.
	 * @see camel.execution.Measurement#checkDate(camel.execution.Measurement)
	 * @generated
	 */
	EOperation getMeasurement__CheckDate__Measurement();

	/**
	 * Returns the meta object for class '{@link camel.execution.ApplicationMeasurement <em>Application Measurement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Measurement</em>'.
	 * @see camel.execution.ApplicationMeasurement
	 * @generated
	 */
	EClass getApplicationMeasurement();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ApplicationMeasurement#getForApplication <em>For Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>For Application</em>'.
	 * @see camel.execution.ApplicationMeasurement#getForApplication()
	 * @see #getApplicationMeasurement()
	 * @generated
	 */
	EReference getApplicationMeasurement_ForApplication();

	/**
	 * Returns the meta object for class '{@link camel.execution.InternalComponentMeasurement <em>Internal Component Measurement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Internal Component Measurement</em>'.
	 * @see camel.execution.InternalComponentMeasurement
	 * @generated
	 */
	EClass getInternalComponentMeasurement();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.InternalComponentMeasurement#getOfComponentInstance <em>Of Component Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of Component Instance</em>'.
	 * @see camel.execution.InternalComponentMeasurement#getOfComponentInstance()
	 * @see #getInternalComponentMeasurement()
	 * @generated
	 */
	EReference getInternalComponentMeasurement_OfComponentInstance();

	/**
	 * Returns the meta object for class '{@link camel.execution.ResourceCouplingMeasurement <em>Resource Coupling Measurement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Coupling Measurement</em>'.
	 * @see camel.execution.ResourceCouplingMeasurement
	 * @generated
	 */
	EClass getResourceCouplingMeasurement();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ResourceCouplingMeasurement#getSourceVMInstance <em>Source VM Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source VM Instance</em>'.
	 * @see camel.execution.ResourceCouplingMeasurement#getSourceVMInstance()
	 * @see #getResourceCouplingMeasurement()
	 * @generated
	 */
	EReference getResourceCouplingMeasurement_SourceVMInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ResourceCouplingMeasurement#getDestinationVMInstance <em>Destination VM Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Destination VM Instance</em>'.
	 * @see camel.execution.ResourceCouplingMeasurement#getDestinationVMInstance()
	 * @see #getResourceCouplingMeasurement()
	 * @generated
	 */
	EReference getResourceCouplingMeasurement_DestinationVMInstance();

	/**
	 * Returns the meta object for class '{@link camel.execution.ResourceMeasurement <em>Resource Measurement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Measurement</em>'.
	 * @see camel.execution.ResourceMeasurement
	 * @generated
	 */
	EClass getResourceMeasurement();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ResourceMeasurement#getOfVMInstance <em>Of VM Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of VM Instance</em>'.
	 * @see camel.execution.ResourceMeasurement#getOfVMInstance()
	 * @see #getResourceMeasurement()
	 * @generated
	 */
	EReference getResourceMeasurement_OfVMInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ResourceMeasurement#getResourceClass <em>Resource Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Resource Class</em>'.
	 * @see camel.execution.ResourceMeasurement#getResourceClass()
	 * @see #getResourceMeasurement()
	 * @generated
	 */
	EReference getResourceMeasurement_ResourceClass();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ResourceMeasurement#getPhysicalNode <em>Physical Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Physical Node</em>'.
	 * @see camel.execution.ResourceMeasurement#getPhysicalNode()
	 * @see #getResourceMeasurement()
	 * @generated
	 */
	EReference getResourceMeasurement_PhysicalNode();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.ResourceMeasurement#getDataObject <em>Data Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Data Object</em>'.
	 * @see camel.execution.ResourceMeasurement#getDataObject()
	 * @see #getResourceMeasurement()
	 * @generated
	 */
	EReference getResourceMeasurement_DataObject();

	/**
	 * Returns the meta object for class '{@link camel.execution.RuleTrigger <em>Rule Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Trigger</em>'.
	 * @see camel.execution.RuleTrigger
	 * @generated
	 */
	EClass getRuleTrigger();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.RuleTrigger#getScalabilityRule <em>Scalability Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Scalability Rule</em>'.
	 * @see camel.execution.RuleTrigger#getScalabilityRule()
	 * @see #getRuleTrigger()
	 * @generated
	 */
	EReference getRuleTrigger_ScalabilityRule();

	/**
	 * Returns the meta object for the reference list '{@link camel.execution.RuleTrigger#getEventInstances <em>Event Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Event Instances</em>'.
	 * @see camel.execution.RuleTrigger#getEventInstances()
	 * @see #getRuleTrigger()
	 * @generated
	 */
	EReference getRuleTrigger_EventInstances();

	/**
	 * Returns the meta object for the reference list '{@link camel.execution.RuleTrigger#getActionRealizations <em>Action Realizations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Action Realizations</em>'.
	 * @see camel.execution.RuleTrigger#getActionRealizations()
	 * @see #getRuleTrigger()
	 * @generated
	 */
	EReference getRuleTrigger_ActionRealizations();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.RuleTrigger#getFiredOn <em>Fired On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fired On</em>'.
	 * @see camel.execution.RuleTrigger#getFiredOn()
	 * @see #getRuleTrigger()
	 * @generated
	 */
	EAttribute getRuleTrigger_FiredOn();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.RuleTrigger#getExecutionContext <em>Execution Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Execution Context</em>'.
	 * @see camel.execution.RuleTrigger#getExecutionContext()
	 * @see #getRuleTrigger()
	 * @generated
	 */
	EReference getRuleTrigger_ExecutionContext();

	/**
	 * Returns the meta object for the '{@link camel.execution.RuleTrigger#checkDate(camel.execution.RuleTrigger) <em>Check Date</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Date</em>' operation.
	 * @see camel.execution.RuleTrigger#checkDate(camel.execution.RuleTrigger)
	 * @generated
	 */
	EOperation getRuleTrigger__CheckDate__RuleTrigger();

	/**
	 * Returns the meta object for class '{@link camel.execution.SLOAssessment <em>SLO Assessment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>SLO Assessment</em>'.
	 * @see camel.execution.SLOAssessment
	 * @generated
	 */
	EClass getSLOAssessment();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.SLOAssessment#getItSLO <em>It SLO</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>It SLO</em>'.
	 * @see camel.execution.SLOAssessment#getItSLO()
	 * @see #getSLOAssessment()
	 * @generated
	 */
	EReference getSLOAssessment_ItSLO();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.SLOAssessment#isAssessment <em>Assessment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Assessment</em>'.
	 * @see camel.execution.SLOAssessment#isAssessment()
	 * @see #getSLOAssessment()
	 * @generated
	 */
	EAttribute getSLOAssessment_Assessment();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.SLOAssessment#getInExecutionContext <em>In Execution Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>In Execution Context</em>'.
	 * @see camel.execution.SLOAssessment#getInExecutionContext()
	 * @see #getSLOAssessment()
	 * @generated
	 */
	EReference getSLOAssessment_InExecutionContext();

	/**
	 * Returns the meta object for the reference '{@link camel.execution.SLOAssessment#getMeasurement <em>Measurement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Measurement</em>'.
	 * @see camel.execution.SLOAssessment#getMeasurement()
	 * @see #getSLOAssessment()
	 * @generated
	 */
	EReference getSLOAssessment_Measurement();

	/**
	 * Returns the meta object for the attribute '{@link camel.execution.SLOAssessment#getAssessmentTime <em>Assessment Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Assessment Time</em>'.
	 * @see camel.execution.SLOAssessment#getAssessmentTime()
	 * @see #getSLOAssessment()
	 * @generated
	 */
	EAttribute getSLOAssessment_AssessmentTime();

	/**
	 * Returns the meta object for the '{@link camel.execution.SLOAssessment#checkDate(camel.execution.SLOAssessment) <em>Check Date</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Date</em>' operation.
	 * @see camel.execution.SLOAssessment#checkDate(camel.execution.SLOAssessment)
	 * @generated
	 */
	EOperation getSLOAssessment__CheckDate__SLOAssessment();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExecutionFactory getExecutionFactory();

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
		 * The meta object literal for the '{@link camel.execution.impl.ExecutionModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.execution.impl.ExecutionModelImpl
		 * @see camel.execution.impl.ExecutionPackageImpl#getExecutionModel()
		 * @generated
		 */
		EClass EXECUTION_MODEL = eINSTANCE.getExecutionModel();

		/**
		 * The meta object literal for the '<em><b>Execution Context</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_MODEL__EXECUTION_CONTEXT = eINSTANCE.getExecutionModel_ExecutionContext();

		/**
		 * The meta object literal for the '<em><b>Measurement</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_MODEL__MEASUREMENT = eINSTANCE.getExecutionModel_Measurement();

		/**
		 * The meta object literal for the '<em><b>Slo Assess</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_MODEL__SLO_ASSESS = eINSTANCE.getExecutionModel_SloAssess();

		/**
		 * The meta object literal for the '<em><b>Rule Trigger</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_MODEL__RULE_TRIGGER = eINSTANCE.getExecutionModel_RuleTrigger();

		/**
		 * The meta object literal for the '<em><b>Action Realization</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_MODEL__ACTION_REALIZATION = eINSTANCE.getExecutionModel_ActionRealization();

		/**
		 * The meta object literal for the '{@link camel.execution.impl.ActionRealizationImpl <em>Action Realization</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.execution.impl.ActionRealizationImpl
		 * @see camel.execution.impl.ExecutionPackageImpl#getActionRealization()
		 * @generated
		 */
		EClass ACTION_REALIZATION = eINSTANCE.getActionRealization();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_REALIZATION__ACTION = eINSTANCE.getActionRealization_Action();

		/**
		 * The meta object literal for the '<em><b>Started On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_REALIZATION__STARTED_ON = eINSTANCE.getActionRealization_StartedOn();

		/**
		 * The meta object literal for the '<em><b>Ended On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_REALIZATION__ENDED_ON = eINSTANCE.getActionRealization_EndedOn();

		/**
		 * The meta object literal for the '<em><b>Low Level Actions</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_REALIZATION__LOW_LEVEL_ACTIONS = eINSTANCE.getActionRealization_LowLevelActions();

		/**
		 * The meta object literal for the '<em><b>Check Start End Dates</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ACTION_REALIZATION___CHECK_START_END_DATES__ACTIONREALIZATION = eINSTANCE.getActionRealization__CheckStartEndDates__ActionRealization();

		/**
		 * The meta object literal for the '{@link camel.execution.impl.ExecutionContextImpl <em>Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.execution.impl.ExecutionContextImpl
		 * @see camel.execution.impl.ExecutionPackageImpl#getExecutionContext()
		 * @generated
		 */
		EClass EXECUTION_CONTEXT = eINSTANCE.getExecutionContext();

		/**
		 * The meta object literal for the '<em><b>Of Application</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_CONTEXT__OF_APPLICATION = eINSTANCE.getExecutionContext_OfApplication();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_CONTEXT__ID = eINSTANCE.getExecutionContext_ID();

		/**
		 * The meta object literal for the '<em><b>Start Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_CONTEXT__START_TIME = eINSTANCE.getExecutionContext_StartTime();

		/**
		 * The meta object literal for the '<em><b>End Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_CONTEXT__END_TIME = eINSTANCE.getExecutionContext_EndTime();

		/**
		 * The meta object literal for the '<em><b>Total Cost</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_CONTEXT__TOTAL_COST = eINSTANCE.getExecutionContext_TotalCost();

		/**
		 * The meta object literal for the '<em><b>Cost Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_CONTEXT__COST_UNIT = eINSTANCE.getExecutionContext_CostUnit();

		/**
		 * The meta object literal for the '<em><b>Involves Deployment</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_CONTEXT__INVOLVES_DEPLOYMENT = eINSTANCE.getExecutionContext_InvolvesDeployment();

		/**
		 * The meta object literal for the '<em><b>Based On Requirements</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_CONTEXT__BASED_ON_REQUIREMENTS = eINSTANCE.getExecutionContext_BasedOnRequirements();

		/**
		 * The meta object literal for the '<em><b>Check Start End Dates</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXECUTION_CONTEXT___CHECK_START_END_DATES__EXECUTIONCONTEXT = eINSTANCE.getExecutionContext__CheckStartEndDates__ExecutionContext();

		/**
		 * The meta object literal for the '{@link camel.execution.impl.MeasurementImpl <em>Measurement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.execution.impl.MeasurementImpl
		 * @see camel.execution.impl.ExecutionPackageImpl#getMeasurement()
		 * @generated
		 */
		EClass MEASUREMENT = eINSTANCE.getMeasurement();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEASUREMENT__ID = eINSTANCE.getMeasurement_ID();

		/**
		 * The meta object literal for the '<em><b>In Execution Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEASUREMENT__IN_EXECUTION_CONTEXT = eINSTANCE.getMeasurement_InExecutionContext();

		/**
		 * The meta object literal for the '<em><b>Of Metric</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEASUREMENT__OF_METRIC = eINSTANCE.getMeasurement_OfMetric();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEASUREMENT__VALUE = eINSTANCE.getMeasurement_Value();

		/**
		 * The meta object literal for the '<em><b>Raw Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEASUREMENT__RAW_DATA = eINSTANCE.getMeasurement_RawData();

		/**
		 * The meta object literal for the '<em><b>Reported On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEASUREMENT__REPORTED_ON = eINSTANCE.getMeasurement_ReportedOn();

		/**
		 * The meta object literal for the '<em><b>It SLO</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEASUREMENT__IT_SLO = eINSTANCE.getMeasurement_ItSLO();

		/**
		 * The meta object literal for the '<em><b>Triggers</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEASUREMENT__TRIGGERS = eINSTANCE.getMeasurement_Triggers();

		/**
		 * The meta object literal for the '<em><b>Check Date</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MEASUREMENT___CHECK_DATE__MEASUREMENT = eINSTANCE.getMeasurement__CheckDate__Measurement();

		/**
		 * The meta object literal for the '{@link camel.execution.impl.ApplicationMeasurementImpl <em>Application Measurement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.execution.impl.ApplicationMeasurementImpl
		 * @see camel.execution.impl.ExecutionPackageImpl#getApplicationMeasurement()
		 * @generated
		 */
		EClass APPLICATION_MEASUREMENT = eINSTANCE.getApplicationMeasurement();

		/**
		 * The meta object literal for the '<em><b>For Application</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_MEASUREMENT__FOR_APPLICATION = eINSTANCE.getApplicationMeasurement_ForApplication();

		/**
		 * The meta object literal for the '{@link camel.execution.impl.InternalComponentMeasurementImpl <em>Internal Component Measurement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.execution.impl.InternalComponentMeasurementImpl
		 * @see camel.execution.impl.ExecutionPackageImpl#getInternalComponentMeasurement()
		 * @generated
		 */
		EClass INTERNAL_COMPONENT_MEASUREMENT = eINSTANCE.getInternalComponentMeasurement();

		/**
		 * The meta object literal for the '<em><b>Of Component Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERNAL_COMPONENT_MEASUREMENT__OF_COMPONENT_INSTANCE = eINSTANCE.getInternalComponentMeasurement_OfComponentInstance();

		/**
		 * The meta object literal for the '{@link camel.execution.impl.ResourceCouplingMeasurementImpl <em>Resource Coupling Measurement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.execution.impl.ResourceCouplingMeasurementImpl
		 * @see camel.execution.impl.ExecutionPackageImpl#getResourceCouplingMeasurement()
		 * @generated
		 */
		EClass RESOURCE_COUPLING_MEASUREMENT = eINSTANCE.getResourceCouplingMeasurement();

		/**
		 * The meta object literal for the '<em><b>Source VM Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_COUPLING_MEASUREMENT__SOURCE_VM_INSTANCE = eINSTANCE.getResourceCouplingMeasurement_SourceVMInstance();

		/**
		 * The meta object literal for the '<em><b>Destination VM Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_COUPLING_MEASUREMENT__DESTINATION_VM_INSTANCE = eINSTANCE.getResourceCouplingMeasurement_DestinationVMInstance();

		/**
		 * The meta object literal for the '{@link camel.execution.impl.ResourceMeasurementImpl <em>Resource Measurement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.execution.impl.ResourceMeasurementImpl
		 * @see camel.execution.impl.ExecutionPackageImpl#getResourceMeasurement()
		 * @generated
		 */
		EClass RESOURCE_MEASUREMENT = eINSTANCE.getResourceMeasurement();

		/**
		 * The meta object literal for the '<em><b>Of VM Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_MEASUREMENT__OF_VM_INSTANCE = eINSTANCE.getResourceMeasurement_OfVMInstance();

		/**
		 * The meta object literal for the '<em><b>Resource Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_MEASUREMENT__RESOURCE_CLASS = eINSTANCE.getResourceMeasurement_ResourceClass();

		/**
		 * The meta object literal for the '<em><b>Physical Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_MEASUREMENT__PHYSICAL_NODE = eINSTANCE.getResourceMeasurement_PhysicalNode();

		/**
		 * The meta object literal for the '<em><b>Data Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_MEASUREMENT__DATA_OBJECT = eINSTANCE.getResourceMeasurement_DataObject();

		/**
		 * The meta object literal for the '{@link camel.execution.impl.RuleTriggerImpl <em>Rule Trigger</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.execution.impl.RuleTriggerImpl
		 * @see camel.execution.impl.ExecutionPackageImpl#getRuleTrigger()
		 * @generated
		 */
		EClass RULE_TRIGGER = eINSTANCE.getRuleTrigger();

		/**
		 * The meta object literal for the '<em><b>Scalability Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_TRIGGER__SCALABILITY_RULE = eINSTANCE.getRuleTrigger_ScalabilityRule();

		/**
		 * The meta object literal for the '<em><b>Event Instances</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_TRIGGER__EVENT_INSTANCES = eINSTANCE.getRuleTrigger_EventInstances();

		/**
		 * The meta object literal for the '<em><b>Action Realizations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_TRIGGER__ACTION_REALIZATIONS = eINSTANCE.getRuleTrigger_ActionRealizations();

		/**
		 * The meta object literal for the '<em><b>Fired On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_TRIGGER__FIRED_ON = eINSTANCE.getRuleTrigger_FiredOn();

		/**
		 * The meta object literal for the '<em><b>Execution Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_TRIGGER__EXECUTION_CONTEXT = eINSTANCE.getRuleTrigger_ExecutionContext();

		/**
		 * The meta object literal for the '<em><b>Check Date</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RULE_TRIGGER___CHECK_DATE__RULETRIGGER = eINSTANCE.getRuleTrigger__CheckDate__RuleTrigger();

		/**
		 * The meta object literal for the '{@link camel.execution.impl.SLOAssessmentImpl <em>SLO Assessment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.execution.impl.SLOAssessmentImpl
		 * @see camel.execution.impl.ExecutionPackageImpl#getSLOAssessment()
		 * @generated
		 */
		EClass SLO_ASSESSMENT = eINSTANCE.getSLOAssessment();

		/**
		 * The meta object literal for the '<em><b>It SLO</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLO_ASSESSMENT__IT_SLO = eINSTANCE.getSLOAssessment_ItSLO();

		/**
		 * The meta object literal for the '<em><b>Assessment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLO_ASSESSMENT__ASSESSMENT = eINSTANCE.getSLOAssessment_Assessment();

		/**
		 * The meta object literal for the '<em><b>In Execution Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLO_ASSESSMENT__IN_EXECUTION_CONTEXT = eINSTANCE.getSLOAssessment_InExecutionContext();

		/**
		 * The meta object literal for the '<em><b>Measurement</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLO_ASSESSMENT__MEASUREMENT = eINSTANCE.getSLOAssessment_Measurement();

		/**
		 * The meta object literal for the '<em><b>Assessment Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLO_ASSESSMENT__ASSESSMENT_TIME = eINSTANCE.getSLOAssessment_AssessmentTime();

		/**
		 * The meta object literal for the '<em><b>Check Date</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SLO_ASSESSMENT___CHECK_DATE__SLOASSESSMENT = eINSTANCE.getSLOAssessment__CheckDate__SLOAssessment();

	}

} //ExecutionPackage
