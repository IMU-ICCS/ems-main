/**
 */
package camel.scalability;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see camel.scalability.ScalabilityPackage
 * @generated
 */
public interface ScalabilityFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ScalabilityFactory eINSTANCE = camel.scalability.impl.ScalabilityFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model</em>'.
	 * @generated
	 */
	ScalabilityModel createScalabilityModel();

	/**
	 * Returns a new object of class '<em>Metric Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Condition</em>'.
	 * @generated
	 */
	MetricCondition createMetricCondition();

	/**
	 * Returns a new object of class '<em>Metric Template Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Template Condition</em>'.
	 * @generated
	 */
	MetricTemplateCondition createMetricTemplateCondition();

	/**
	 * Returns a new object of class '<em>Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event</em>'.
	 * @generated
	 */
	Event createEvent();

	/**
	 * Returns a new object of class '<em>Binary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binary Event Pattern</em>'.
	 * @generated
	 */
	BinaryEventPattern createBinaryEventPattern();

	/**
	 * Returns a new object of class '<em>Unary Event Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unary Event Pattern</em>'.
	 * @generated
	 */
	UnaryEventPattern createUnaryEventPattern();

	/**
	 * Returns a new object of class '<em>Simple Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Event</em>'.
	 * @generated
	 */
	SimpleEvent createSimpleEvent();

	/**
	 * Returns a new object of class '<em>Functional Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Functional Event</em>'.
	 * @generated
	 */
	FunctionalEvent createFunctionalEvent();

	/**
	 * Returns a new object of class '<em>Non Functional Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Non Functional Event</em>'.
	 * @generated
	 */
	NonFunctionalEvent createNonFunctionalEvent();

	/**
	 * Returns a new object of class '<em>Event Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Instance</em>'.
	 * @generated
	 */
	EventInstance createEventInstance();

	/**
	 * Returns a new object of class '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric</em>'.
	 * @generated
	 */
	Metric createMetric();

	/**
	 * Returns a new object of class '<em>Metric Formula Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Formula Parameter</em>'.
	 * @generated
	 */
	MetricFormulaParameter createMetricFormulaParameter();

	/**
	 * Returns a new object of class '<em>Metric Formula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Formula</em>'.
	 * @generated
	 */
	MetricFormula createMetricFormula();

	/**
	 * Returns a new object of class '<em>Metric Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Template</em>'.
	 * @generated
	 */
	MetricTemplate createMetricTemplate();

	/**
	 * Returns a new object of class '<em>Metric Application Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Application Binding</em>'.
	 * @generated
	 */
	MetricApplicationBinding createMetricApplicationBinding();

	/**
	 * Returns a new object of class '<em>Metric Component Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Component Binding</em>'.
	 * @generated
	 */
	MetricComponentBinding createMetricComponentBinding();

	/**
	 * Returns a new object of class '<em>Metric VM Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric VM Binding</em>'.
	 * @generated
	 */
	MetricVMBinding createMetricVMBinding();

	/**
	 * Returns a new object of class '<em>Metric Application Instance Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Application Instance Binding</em>'.
	 * @generated
	 */
	MetricApplicationInstanceBinding createMetricApplicationInstanceBinding();

	/**
	 * Returns a new object of class '<em>Metric Component Instance Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Component Instance Binding</em>'.
	 * @generated
	 */
	MetricComponentInstanceBinding createMetricComponentInstanceBinding();

	/**
	 * Returns a new object of class '<em>Metric VM Instance Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric VM Instance Binding</em>'.
	 * @generated
	 */
	MetricVMInstanceBinding createMetricVMInstanceBinding();

	/**
	 * Returns a new object of class '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property</em>'.
	 * @generated
	 */
	Property createProperty();

	/**
	 * Returns a new object of class '<em>Horizontal Scalability Policy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Horizontal Scalability Policy</em>'.
	 * @generated
	 */
	HorizontalScalabilityPolicy createHorizontalScalabilityPolicy();

	/**
	 * Returns a new object of class '<em>Vertical Scalability Policy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Vertical Scalability Policy</em>'.
	 * @generated
	 */
	VerticalScalabilityPolicy createVerticalScalabilityPolicy();

	/**
	 * Returns a new object of class '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule</em>'.
	 * @generated
	 */
	ScalabilityRule createScalabilityRule();

	/**
	 * Returns a new object of class '<em>Scaling Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Scaling Action</em>'.
	 * @generated
	 */
	ScalingAction createScalingAction();

	/**
	 * Returns a new object of class '<em>Schedule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Schedule</em>'.
	 * @generated
	 */
	Schedule createSchedule();

	/**
	 * Returns a new object of class '<em>Sensor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sensor</em>'.
	 * @generated
	 */
	Sensor createSensor();

	/**
	 * Returns a new object of class '<em>Timer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Timer</em>'.
	 * @generated
	 */
	Timer createTimer();

	/**
	 * Returns a new object of class '<em>Window</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Window</em>'.
	 * @generated
	 */
	Window createWindow();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ScalabilityPackage getScalabilityPackage();

} //ScalabilityFactory
