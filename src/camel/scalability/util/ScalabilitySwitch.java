/**
 */
package camel.scalability.util;

import camel.Action;
import camel.Requirement;

import camel.scalability.*;

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
 * @see camel.scalability.ScalabilityPackage
 * @generated
 */
public class ScalabilitySwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ScalabilityPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalabilitySwitch() {
		if (modelPackage == null) {
			modelPackage = ScalabilityPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
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
			case ScalabilityPackage.SCALABILITY_MODEL: {
				ScalabilityModel scalabilityModel = (ScalabilityModel)theEObject;
				T result = caseScalabilityModel(scalabilityModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.CONDITION: {
				Condition condition = (Condition)theEObject;
				T result = caseCondition(condition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_CONDITION: {
				MetricCondition metricCondition = (MetricCondition)theEObject;
				T result = caseMetricCondition(metricCondition);
				if (result == null) result = caseCondition(metricCondition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_TEMPLATE_CONDITION: {
				MetricTemplateCondition metricTemplateCondition = (MetricTemplateCondition)theEObject;
				T result = caseMetricTemplateCondition(metricTemplateCondition);
				if (result == null) result = caseCondition(metricTemplateCondition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.EVENT: {
				Event event = (Event)theEObject;
				T result = caseEvent(event);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.EVENT_PATTERN: {
				EventPattern eventPattern = (EventPattern)theEObject;
				T result = caseEventPattern(eventPattern);
				if (result == null) result = caseEvent(eventPattern);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.BINARY_EVENT_PATTERN: {
				BinaryEventPattern binaryEventPattern = (BinaryEventPattern)theEObject;
				T result = caseBinaryEventPattern(binaryEventPattern);
				if (result == null) result = caseEventPattern(binaryEventPattern);
				if (result == null) result = caseEvent(binaryEventPattern);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.UNARY_EVENT_PATTERN: {
				UnaryEventPattern unaryEventPattern = (UnaryEventPattern)theEObject;
				T result = caseUnaryEventPattern(unaryEventPattern);
				if (result == null) result = caseEventPattern(unaryEventPattern);
				if (result == null) result = caseEvent(unaryEventPattern);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.SIMPLE_EVENT: {
				SimpleEvent simpleEvent = (SimpleEvent)theEObject;
				T result = caseSimpleEvent(simpleEvent);
				if (result == null) result = caseEvent(simpleEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.FUNCTIONAL_EVENT: {
				FunctionalEvent functionalEvent = (FunctionalEvent)theEObject;
				T result = caseFunctionalEvent(functionalEvent);
				if (result == null) result = caseSimpleEvent(functionalEvent);
				if (result == null) result = caseEvent(functionalEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.NON_FUNCTIONAL_EVENT: {
				NonFunctionalEvent nonFunctionalEvent = (NonFunctionalEvent)theEObject;
				T result = caseNonFunctionalEvent(nonFunctionalEvent);
				if (result == null) result = caseSimpleEvent(nonFunctionalEvent);
				if (result == null) result = caseEvent(nonFunctionalEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.EVENT_INSTANCE: {
				EventInstance eventInstance = (EventInstance)theEObject;
				T result = caseEventInstance(eventInstance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC: {
				Metric metric = (Metric)theEObject;
				T result = caseMetric(metric);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_FORMULA_PARAMETER: {
				MetricFormulaParameter metricFormulaParameter = (MetricFormulaParameter)theEObject;
				T result = caseMetricFormulaParameter(metricFormulaParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_FORMULA: {
				MetricFormula metricFormula = (MetricFormula)theEObject;
				T result = caseMetricFormula(metricFormula);
				if (result == null) result = caseMetricFormulaParameter(metricFormula);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_TEMPLATE: {
				MetricTemplate metricTemplate = (MetricTemplate)theEObject;
				T result = caseMetricTemplate(metricTemplate);
				if (result == null) result = caseMetricFormulaParameter(metricTemplate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_OBJECT_BINDING: {
				MetricObjectBinding metricObjectBinding = (MetricObjectBinding)theEObject;
				T result = caseMetricObjectBinding(metricObjectBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_APPLICATION_BINDING: {
				MetricApplicationBinding metricApplicationBinding = (MetricApplicationBinding)theEObject;
				T result = caseMetricApplicationBinding(metricApplicationBinding);
				if (result == null) result = caseMetricObjectBinding(metricApplicationBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_COMPONENT_BINDING: {
				MetricComponentBinding metricComponentBinding = (MetricComponentBinding)theEObject;
				T result = caseMetricComponentBinding(metricComponentBinding);
				if (result == null) result = caseMetricObjectBinding(metricComponentBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_VM_BINDING: {
				MetricVMBinding metricVMBinding = (MetricVMBinding)theEObject;
				T result = caseMetricVMBinding(metricVMBinding);
				if (result == null) result = caseMetricObjectBinding(metricVMBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_OBJECT_INSTANCE_BINDING: {
				MetricObjectInstanceBinding metricObjectInstanceBinding = (MetricObjectInstanceBinding)theEObject;
				T result = caseMetricObjectInstanceBinding(metricObjectInstanceBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_APPLICATION_INSTANCE_BINDING: {
				MetricApplicationInstanceBinding metricApplicationInstanceBinding = (MetricApplicationInstanceBinding)theEObject;
				T result = caseMetricApplicationInstanceBinding(metricApplicationInstanceBinding);
				if (result == null) result = caseMetricObjectInstanceBinding(metricApplicationInstanceBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_COMPONENT_INSTANCE_BINDING: {
				MetricComponentInstanceBinding metricComponentInstanceBinding = (MetricComponentInstanceBinding)theEObject;
				T result = caseMetricComponentInstanceBinding(metricComponentInstanceBinding);
				if (result == null) result = caseMetricObjectInstanceBinding(metricComponentInstanceBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.METRIC_VM_INSTANCE_BINDING: {
				MetricVMInstanceBinding metricVMInstanceBinding = (MetricVMInstanceBinding)theEObject;
				T result = caseMetricVMInstanceBinding(metricVMInstanceBinding);
				if (result == null) result = caseMetricObjectInstanceBinding(metricVMInstanceBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.PROPERTY: {
				Property property = (Property)theEObject;
				T result = caseProperty(property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.SCALABILITY_POLICY: {
				ScalabilityPolicy scalabilityPolicy = (ScalabilityPolicy)theEObject;
				T result = caseScalabilityPolicy(scalabilityPolicy);
				if (result == null) result = caseRequirement(scalabilityPolicy);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.HORIZONTAL_SCALABILITY_POLICY: {
				HorizontalScalabilityPolicy horizontalScalabilityPolicy = (HorizontalScalabilityPolicy)theEObject;
				T result = caseHorizontalScalabilityPolicy(horizontalScalabilityPolicy);
				if (result == null) result = caseScalabilityPolicy(horizontalScalabilityPolicy);
				if (result == null) result = caseRequirement(horizontalScalabilityPolicy);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.VERTICAL_SCALABILITY_POLICY: {
				VerticalScalabilityPolicy verticalScalabilityPolicy = (VerticalScalabilityPolicy)theEObject;
				T result = caseVerticalScalabilityPolicy(verticalScalabilityPolicy);
				if (result == null) result = caseScalabilityPolicy(verticalScalabilityPolicy);
				if (result == null) result = caseRequirement(verticalScalabilityPolicy);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.SCALABILITY_RULE: {
				ScalabilityRule scalabilityRule = (ScalabilityRule)theEObject;
				T result = caseScalabilityRule(scalabilityRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.SCALING_ACTION: {
				ScalingAction scalingAction = (ScalingAction)theEObject;
				T result = caseScalingAction(scalingAction);
				if (result == null) result = caseAction(scalingAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.SCHEDULE: {
				Schedule schedule = (Schedule)theEObject;
				T result = caseSchedule(schedule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.SENSOR: {
				Sensor sensor = (Sensor)theEObject;
				T result = caseSensor(sensor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.TIMER: {
				Timer timer = (Timer)theEObject;
				T result = caseTimer(timer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ScalabilityPackage.WINDOW: {
				Window window = (Window)theEObject;
				T result = caseWindow(window);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseScalabilityModel(ScalabilityModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCondition(Condition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricCondition(MetricCondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric Template Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric Template Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricTemplateCondition(MetricTemplateCondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEvent(Event object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventPattern(EventPattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binary Event Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBinaryEventPattern(BinaryEventPattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unary Event Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnaryEventPattern(UnaryEventPattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleEvent(SimpleEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Functional Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Functional Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionalEvent(FunctionalEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Non Functional Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Non Functional Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNonFunctionalEvent(NonFunctionalEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventInstance(EventInstance object) {
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
	public T caseMetric(Metric object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric Formula Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric Formula Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricFormulaParameter(MetricFormulaParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric Formula</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric Formula</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricFormula(MetricFormula object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricTemplate(MetricTemplate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric Object Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric Object Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricObjectBinding(MetricObjectBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric Application Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric Application Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricApplicationBinding(MetricApplicationBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric Component Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric Component Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricComponentBinding(MetricComponentBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric VM Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric VM Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricVMBinding(MetricVMBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric Object Instance Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric Object Instance Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricObjectInstanceBinding(MetricObjectInstanceBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric Application Instance Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric Application Instance Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricApplicationInstanceBinding(MetricApplicationInstanceBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric Component Instance Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric Component Instance Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricComponentInstanceBinding(MetricComponentInstanceBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metric VM Instance Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metric VM Instance Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetricVMInstanceBinding(MetricVMInstanceBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProperty(Property object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Policy</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Policy</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseScalabilityPolicy(ScalabilityPolicy object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Horizontal Scalability Policy</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Horizontal Scalability Policy</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHorizontalScalabilityPolicy(HorizontalScalabilityPolicy object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Vertical Scalability Policy</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Vertical Scalability Policy</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVerticalScalabilityPolicy(VerticalScalabilityPolicy object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseScalabilityRule(ScalabilityRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Scaling Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Scaling Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseScalingAction(ScalingAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Schedule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Schedule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSchedule(Schedule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sensor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sensor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSensor(Sensor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Timer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Timer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTimer(Timer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Window</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Window</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWindow(Window object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Requirement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRequirement(Requirement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAction(Action object) {
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

} //ScalabilitySwitch
