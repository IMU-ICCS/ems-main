/**
 */
package camel.execution.impl;

import camel.execution.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExecutionFactoryImpl extends EFactoryImpl implements ExecutionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ExecutionFactory init() {
		try {
			ExecutionFactory theExecutionFactory = (ExecutionFactory)EPackage.Registry.INSTANCE.getEFactory(ExecutionPackage.eNS_URI);
			if (theExecutionFactory != null) {
				return theExecutionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ExecutionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ExecutionPackage.EXECUTION_MODEL: return (EObject)createExecutionModel();
			case ExecutionPackage.ACTION_REALIZATION: return (EObject)createActionRealization();
			case ExecutionPackage.EXECUTION_CONTEXT: return (EObject)createExecutionContext();
			case ExecutionPackage.MEASUREMENT: return (EObject)createMeasurement();
			case ExecutionPackage.APPLICATION_MEASUREMENT: return (EObject)createApplicationMeasurement();
			case ExecutionPackage.INTERNAL_COMPONENT_MEASUREMENT: return (EObject)createInternalComponentMeasurement();
			case ExecutionPackage.RESOURCE_COUPLING_MEASUREMENT: return (EObject)createResourceCouplingMeasurement();
			case ExecutionPackage.RESOURCE_MEASUREMENT: return (EObject)createResourceMeasurement();
			case ExecutionPackage.RULE_TRIGGER: return (EObject)createRuleTrigger();
			case ExecutionPackage.SLO_ASSESSMENT: return (EObject)createSLOAssessment();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionModel createExecutionModel() {
		ExecutionModelImpl executionModel = new ExecutionModelImpl();
		return executionModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionRealization createActionRealization() {
		ActionRealizationImpl actionRealization = new ActionRealizationImpl();
		return actionRealization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionContext createExecutionContext() {
		ExecutionContextImpl executionContext = new ExecutionContextImpl();
		return executionContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Measurement createMeasurement() {
		MeasurementImpl measurement = new MeasurementImpl();
		return measurement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationMeasurement createApplicationMeasurement() {
		ApplicationMeasurementImpl applicationMeasurement = new ApplicationMeasurementImpl();
		return applicationMeasurement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InternalComponentMeasurement createInternalComponentMeasurement() {
		InternalComponentMeasurementImpl internalComponentMeasurement = new InternalComponentMeasurementImpl();
		return internalComponentMeasurement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceCouplingMeasurement createResourceCouplingMeasurement() {
		ResourceCouplingMeasurementImpl resourceCouplingMeasurement = new ResourceCouplingMeasurementImpl();
		return resourceCouplingMeasurement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceMeasurement createResourceMeasurement() {
		ResourceMeasurementImpl resourceMeasurement = new ResourceMeasurementImpl();
		return resourceMeasurement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleTrigger createRuleTrigger() {
		RuleTriggerImpl ruleTrigger = new RuleTriggerImpl();
		return ruleTrigger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SLOAssessment createSLOAssessment() {
		SLOAssessmentImpl sloAssessment = new SLOAssessmentImpl();
		return sloAssessment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionPackage getExecutionPackage() {
		return (ExecutionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ExecutionPackage getPackage() {
		return ExecutionPackage.eINSTANCE;
	}

} //ExecutionFactoryImpl
