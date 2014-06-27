/**
 */
package camel.scalability.util;

import camel.Action;
import camel.Requirement;

import camel.scalability.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see camel.scalability.ScalabilityPackage
 * @generated
 */
public class ScalabilityAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ScalabilityPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalabilityAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ScalabilityPackage.eINSTANCE;
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
	protected ScalabilitySwitch<Adapter> modelSwitch =
		new ScalabilitySwitch<Adapter>() {
			@Override
			public Adapter caseScalabilityModel(ScalabilityModel object) {
				return createScalabilityModelAdapter();
			}
			@Override
			public Adapter caseCondition(Condition object) {
				return createConditionAdapter();
			}
			@Override
			public Adapter caseMetricCondition(MetricCondition object) {
				return createMetricConditionAdapter();
			}
			@Override
			public Adapter caseMetricTemplateCondition(MetricTemplateCondition object) {
				return createMetricTemplateConditionAdapter();
			}
			@Override
			public Adapter caseEvent(Event object) {
				return createEventAdapter();
			}
			@Override
			public Adapter caseEventPattern(EventPattern object) {
				return createEventPatternAdapter();
			}
			@Override
			public Adapter caseBinaryEventPattern(BinaryEventPattern object) {
				return createBinaryEventPatternAdapter();
			}
			@Override
			public Adapter caseUnaryEventPattern(UnaryEventPattern object) {
				return createUnaryEventPatternAdapter();
			}
			@Override
			public Adapter caseSimpleEvent(SimpleEvent object) {
				return createSimpleEventAdapter();
			}
			@Override
			public Adapter caseFunctionalEvent(FunctionalEvent object) {
				return createFunctionalEventAdapter();
			}
			@Override
			public Adapter caseNonFunctionalEvent(NonFunctionalEvent object) {
				return createNonFunctionalEventAdapter();
			}
			@Override
			public Adapter caseEventInstance(EventInstance object) {
				return createEventInstanceAdapter();
			}
			@Override
			public Adapter caseMetric(Metric object) {
				return createMetricAdapter();
			}
			@Override
			public Adapter caseMetricFormulaParameter(MetricFormulaParameter object) {
				return createMetricFormulaParameterAdapter();
			}
			@Override
			public Adapter caseMetricFormula(MetricFormula object) {
				return createMetricFormulaAdapter();
			}
			@Override
			public Adapter caseMetricTemplate(MetricTemplate object) {
				return createMetricTemplateAdapter();
			}
			@Override
			public Adapter caseMetricObjectBinding(MetricObjectBinding object) {
				return createMetricObjectBindingAdapter();
			}
			@Override
			public Adapter caseMetricApplicationBinding(MetricApplicationBinding object) {
				return createMetricApplicationBindingAdapter();
			}
			@Override
			public Adapter caseMetricComponentBinding(MetricComponentBinding object) {
				return createMetricComponentBindingAdapter();
			}
			@Override
			public Adapter caseMetricVMBinding(MetricVMBinding object) {
				return createMetricVMBindingAdapter();
			}
			@Override
			public Adapter caseMetricObjectInstanceBinding(MetricObjectInstanceBinding object) {
				return createMetricObjectInstanceBindingAdapter();
			}
			@Override
			public Adapter caseMetricApplicationInstanceBinding(MetricApplicationInstanceBinding object) {
				return createMetricApplicationInstanceBindingAdapter();
			}
			@Override
			public Adapter caseMetricComponentInstanceBinding(MetricComponentInstanceBinding object) {
				return createMetricComponentInstanceBindingAdapter();
			}
			@Override
			public Adapter caseMetricVMInstanceBinding(MetricVMInstanceBinding object) {
				return createMetricVMInstanceBindingAdapter();
			}
			@Override
			public Adapter caseProperty(Property object) {
				return createPropertyAdapter();
			}
			@Override
			public Adapter caseScalabilityPolicy(ScalabilityPolicy object) {
				return createScalabilityPolicyAdapter();
			}
			@Override
			public Adapter caseHorizontalScalabilityPolicy(HorizontalScalabilityPolicy object) {
				return createHorizontalScalabilityPolicyAdapter();
			}
			@Override
			public Adapter caseVerticalScalabilityPolicy(VerticalScalabilityPolicy object) {
				return createVerticalScalabilityPolicyAdapter();
			}
			@Override
			public Adapter caseScalabilityRule(ScalabilityRule object) {
				return createScalabilityRuleAdapter();
			}
			@Override
			public Adapter caseScalingAction(ScalingAction object) {
				return createScalingActionAdapter();
			}
			@Override
			public Adapter caseSchedule(Schedule object) {
				return createScheduleAdapter();
			}
			@Override
			public Adapter caseSensor(Sensor object) {
				return createSensorAdapter();
			}
			@Override
			public Adapter caseTimer(Timer object) {
				return createTimerAdapter();
			}
			@Override
			public Adapter caseWindow(Window object) {
				return createWindowAdapter();
			}
			@Override
			public Adapter caseRequirement(Requirement object) {
				return createRequirementAdapter();
			}
			@Override
			public Adapter caseAction(Action object) {
				return createActionAdapter();
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
	 * Creates a new adapter for an object of class '{@link camel.scalability.ScalabilityModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.ScalabilityModel
	 * @generated
	 */
	public Adapter createScalabilityModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.Condition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.Condition
	 * @generated
	 */
	public Adapter createConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricCondition <em>Metric Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricCondition
	 * @generated
	 */
	public Adapter createMetricConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricTemplateCondition <em>Metric Template Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricTemplateCondition
	 * @generated
	 */
	public Adapter createMetricTemplateConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.Event
	 * @generated
	 */
	public Adapter createEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.EventPattern <em>Event Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.EventPattern
	 * @generated
	 */
	public Adapter createEventPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.BinaryEventPattern <em>Binary Event Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.BinaryEventPattern
	 * @generated
	 */
	public Adapter createBinaryEventPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.UnaryEventPattern <em>Unary Event Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.UnaryEventPattern
	 * @generated
	 */
	public Adapter createUnaryEventPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.SimpleEvent <em>Simple Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.SimpleEvent
	 * @generated
	 */
	public Adapter createSimpleEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.FunctionalEvent <em>Functional Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.FunctionalEvent
	 * @generated
	 */
	public Adapter createFunctionalEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.NonFunctionalEvent <em>Non Functional Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.NonFunctionalEvent
	 * @generated
	 */
	public Adapter createNonFunctionalEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.EventInstance <em>Event Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.EventInstance
	 * @generated
	 */
	public Adapter createEventInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.Metric <em>Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.Metric
	 * @generated
	 */
	public Adapter createMetricAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricFormulaParameter <em>Metric Formula Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricFormulaParameter
	 * @generated
	 */
	public Adapter createMetricFormulaParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricFormula <em>Metric Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricFormula
	 * @generated
	 */
	public Adapter createMetricFormulaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricTemplate <em>Metric Template</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricTemplate
	 * @generated
	 */
	public Adapter createMetricTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricObjectBinding <em>Metric Object Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricObjectBinding
	 * @generated
	 */
	public Adapter createMetricObjectBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricApplicationBinding <em>Metric Application Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricApplicationBinding
	 * @generated
	 */
	public Adapter createMetricApplicationBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricComponentBinding <em>Metric Component Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricComponentBinding
	 * @generated
	 */
	public Adapter createMetricComponentBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricVMBinding <em>Metric VM Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricVMBinding
	 * @generated
	 */
	public Adapter createMetricVMBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricObjectInstanceBinding <em>Metric Object Instance Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricObjectInstanceBinding
	 * @generated
	 */
	public Adapter createMetricObjectInstanceBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricApplicationInstanceBinding <em>Metric Application Instance Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricApplicationInstanceBinding
	 * @generated
	 */
	public Adapter createMetricApplicationInstanceBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricComponentInstanceBinding <em>Metric Component Instance Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricComponentInstanceBinding
	 * @generated
	 */
	public Adapter createMetricComponentInstanceBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.MetricVMInstanceBinding <em>Metric VM Instance Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.MetricVMInstanceBinding
	 * @generated
	 */
	public Adapter createMetricVMInstanceBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.Property
	 * @generated
	 */
	public Adapter createPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.ScalabilityPolicy <em>Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.ScalabilityPolicy
	 * @generated
	 */
	public Adapter createScalabilityPolicyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.HorizontalScalabilityPolicy <em>Horizontal Scalability Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.HorizontalScalabilityPolicy
	 * @generated
	 */
	public Adapter createHorizontalScalabilityPolicyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.VerticalScalabilityPolicy <em>Vertical Scalability Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.VerticalScalabilityPolicy
	 * @generated
	 */
	public Adapter createVerticalScalabilityPolicyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.ScalabilityRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.ScalabilityRule
	 * @generated
	 */
	public Adapter createScalabilityRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.ScalingAction <em>Scaling Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.ScalingAction
	 * @generated
	 */
	public Adapter createScalingActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.Schedule <em>Schedule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.Schedule
	 * @generated
	 */
	public Adapter createScheduleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.Sensor <em>Sensor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.Sensor
	 * @generated
	 */
	public Adapter createSensorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.Timer <em>Timer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.Timer
	 * @generated
	 */
	public Adapter createTimerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.scalability.Window <em>Window</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.scalability.Window
	 * @generated
	 */
	public Adapter createWindowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.Requirement <em>Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.Requirement
	 * @generated
	 */
	public Adapter createRequirementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.Action
	 * @generated
	 */
	public Adapter createActionAdapter() {
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

} //ScalabilityAdapterFactory
