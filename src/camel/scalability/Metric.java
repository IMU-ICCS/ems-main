/**
 */
package camel.scalability;

import camel.type.ValueType;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.Metric#getId <em>Id</em>}</li>
 *   <li>{@link camel.scalability.Metric#getHasTemplate <em>Has Template</em>}</li>
 *   <li>{@link camel.scalability.Metric#getHasSchedule <em>Has Schedule</em>}</li>
 *   <li>{@link camel.scalability.Metric#getWindow <em>Window</em>}</li>
 *   <li>{@link camel.scalability.Metric#getComponentMetrics <em>Component Metrics</em>}</li>
 *   <li>{@link camel.scalability.Metric#getSensor <em>Sensor</em>}</li>
 *   <li>{@link camel.scalability.Metric#getObjectBinding <em>Object Binding</em>}</li>
 *   <li>{@link camel.scalability.Metric#getValueType <em>Value Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetric()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='RAW_Metric_To_Sensor Composite_Metric_To_Components component_metrics_map_formula_templates bindings_as_in_template component_metrics_refer_to_same_level_or_lower'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot RAW_Metric_To_Sensor='\n\t\t\t\t\t(self.hasTemplate.type = MetricType::RAW implies sensor <> null)' Composite_Metric_To_Components='\n\t\t\t\t\t(self.hasTemplate.type = MetricType::COMPOSITE implies componentMetrics->notEmpty())' component_metrics_map_formula_templates='\n\t\t\t\t\t(self.hasTemplate.type = MetricType::RAW) or (self.hasTemplate.type = MetricType::COMPOSITE  and self.hasTemplate.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies self.componentMetrics->exists(hasTemplate = p)) and self.hasTemplate.formula.parameters->select(p | p.oclIsTypeOf(MetricTemplate))->size() = self.componentMetrics->size())' bindings_as_in_template='\n\t\t\t\t\t\tif (self.hasTemplate.objectBinding <> null) then\n\t\t\t\t\t\t\t(self.hasTemplate.objectBinding.oclIsTypeOf(MetricApplicationBinding) implies (\n\t\t\t\t\t\t\t\tself.objectBinding.oclIsTypeOf(MetricApplicationInstanceBinding) and \n\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricApplicationInstanceBinding).executionContext.ofApplication = self.hasTemplate.objectBinding.oclAsType(MetricApplicationBinding).application\n\t\t\t\t\t\t\t))\n\t\t\t\t\t\t\tand (self.hasTemplate.objectBinding.oclIsTypeOf(MetricComponentBinding) implies (\n\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type = self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).component\n\t\t\t\t\t\t\t\tand\n\t\t\t\t\t\t\t\tif (self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::InternalComponent)) then \n\t\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.oclIsTypeOf(camel::deployment::InternalComponentInstance) and\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).vmInstance.type= self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).vm \n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (self.hasTemplate.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then\n\t\t\t\t\t\t\t\t\t\tself.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)\n\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t))\n\t\t\t\t\t\t\tand (self.hasTemplate.objectBinding.oclIsTypeOf(MetricVMBinding) implies (\n\t\t\t\t\t\t\t\tself.objectBinding.oclIsTypeOf(MetricVMInstanceBinding) and \n\t\t\t\t\t\t\t\tself.hasTemplate.objectBinding.oclAsType(MetricVMBinding).vm = self.objectBinding.oclAsType(MetricVMInstanceBinding).vmInstance.type\n\t\t\t\t\t\t\t))\n\t\t\t\t\t\telse true\n\t\t\t\t\t\tendif' component_metrics_refer_to_same_level_or_lower='\n\t\t\t\t\tif (objectBinding.oclIsTypeOf(MetricApplicationInstanceBinding)) then \n\t\t\t\t\t\tcomponentMetrics->forAll(p | p.objectBinding.executionContext = objectBinding.executionContext)\n\t\t\t\t\telse \n\t\t\t\t\t\tif (objectBinding.oclIsTypeOf(MetricComponentInstanceBinding)) then componentMetrics->forAll(p | p.objectBinding.executionContext = self.objectBinding.executionContext \n\t\t\t\t\t\t\tand not(p.objectBinding.oclIsTypeOf(MetricApplicationInstanceBinding)) and\n\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.oclIsKindOf(camel::deployment::InternalComponentInstance)) then\n\t\t\t\t\t\t\t\tif (p.objectBinding.oclIsTypeOf(MetricVMInstanceBinding)) then self.objectBinding.executionContext.involvesDeployment.hostingInstances->exists(q | q.providedHostInstance.owner=p.objectBinding.oclAsType(MetricVMInstanceBinding).vmInstance and q.requiredHostInstance.owner=self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance)\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (p.objectBinding.oclIsTypeOf(MetricComponentInstanceBinding) and p.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then self.objectBinding.executionContext.involvesDeployment.hostingInstances->exists(q | q.providedHostInstance.owner=p.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance and q.requiredHostInstance.owner=self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance)\n\t\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then \n\t\t\t\t\t\t\t\t\tif (p.objectBinding.oclIsTypeOf(MetricComponentInstanceBinding) and p.objectBinding.oclAsType(MetricComponentInstanceBinding).componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then true\n\t\t\t\t\t\t\t\t\telse false endif\n\t\t\t\t\t\t\t\telse false endif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t)\n\t\t\t\t\t\telse componentMetrics->forAll(p | p.objectBinding.executionContext = objectBinding.executionContext and p.objectBinding.oclIsTypeOf(MetricVMInstanceBinding))\n\t\t\t\t\t\tendif\n\t\t\t\t\tendif'"
 * @extends CDOObject
 * @generated
 */
public interface Metric extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see camel.scalability.ScalabilityPackage#getMetric_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link camel.scalability.Metric#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Has Template</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Template</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Template</em>' reference.
	 * @see #setHasTemplate(MetricTemplate)
	 * @see camel.scalability.ScalabilityPackage#getMetric_HasTemplate()
	 * @model required="true"
	 * @generated
	 */
	MetricTemplate getHasTemplate();

	/**
	 * Sets the value of the '{@link camel.scalability.Metric#getHasTemplate <em>Has Template</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Template</em>' reference.
	 * @see #getHasTemplate()
	 * @generated
	 */
	void setHasTemplate(MetricTemplate value);

	/**
	 * Returns the value of the '<em><b>Has Schedule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Schedule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Schedule</em>' reference.
	 * @see #setHasSchedule(Schedule)
	 * @see camel.scalability.ScalabilityPackage#getMetric_HasSchedule()
	 * @model
	 * @generated
	 */
	Schedule getHasSchedule();

	/**
	 * Sets the value of the '{@link camel.scalability.Metric#getHasSchedule <em>Has Schedule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Schedule</em>' reference.
	 * @see #getHasSchedule()
	 * @generated
	 */
	void setHasSchedule(Schedule value);

	/**
	 * Returns the value of the '<em><b>Window</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Window</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Window</em>' reference.
	 * @see #setWindow(Window)
	 * @see camel.scalability.ScalabilityPackage#getMetric_Window()
	 * @model
	 * @generated
	 */
	Window getWindow();

	/**
	 * Sets the value of the '{@link camel.scalability.Metric#getWindow <em>Window</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Window</em>' reference.
	 * @see #getWindow()
	 * @generated
	 */
	void setWindow(Window value);

	/**
	 * Returns the value of the '<em><b>Component Metrics</b></em>' reference list.
	 * The list contents are of type {@link camel.scalability.Metric}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Metrics</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Metrics</em>' reference list.
	 * @see camel.scalability.ScalabilityPackage#getMetric_ComponentMetrics()
	 * @model
	 * @generated
	 */
	EList<Metric> getComponentMetrics();

	/**
	 * Returns the value of the '<em><b>Sensor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sensor</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sensor</em>' reference.
	 * @see #setSensor(Sensor)
	 * @see camel.scalability.ScalabilityPackage#getMetric_Sensor()
	 * @model
	 * @generated
	 */
	Sensor getSensor();

	/**
	 * Sets the value of the '{@link camel.scalability.Metric#getSensor <em>Sensor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sensor</em>' reference.
	 * @see #getSensor()
	 * @generated
	 */
	void setSensor(Sensor value);

	/**
	 * Returns the value of the '<em><b>Object Binding</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Binding</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Binding</em>' reference.
	 * @see #setObjectBinding(MetricObjectInstanceBinding)
	 * @see camel.scalability.ScalabilityPackage#getMetric_ObjectBinding()
	 * @model required="true"
	 * @generated
	 */
	MetricObjectInstanceBinding getObjectBinding();

	/**
	 * Sets the value of the '{@link camel.scalability.Metric#getObjectBinding <em>Object Binding</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Binding</em>' reference.
	 * @see #getObjectBinding()
	 * @generated
	 */
	void setObjectBinding(MetricObjectInstanceBinding value);

	/**
	 * Returns the value of the '<em><b>Value Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Type</em>' containment reference.
	 * @see #setValueType(ValueType)
	 * @see camel.scalability.ScalabilityPackage#getMetric_ValueType()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ValueType getValueType();

	/**
	 * Sets the value of the '{@link camel.scalability.Metric#getValueType <em>Value Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Type</em>' containment reference.
	 * @see #getValueType()
	 * @generated
	 */
	void setValueType(ValueType value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"Checking recursiveness for Metric: \" + m1.getId());\r\n\t\tfor (Metric m: m1.getComponentMetrics()){\r\n\t\t\t\tif (m.getId().equals(m2.getId())) return Boolean.TRUE;\r\n\t\t\t\tif (m.getHasTemplate().getType() == camel.scalability.MetricType.COMPOSITE && checkRecursiveness(m,m2)) return Boolean.TRUE;\r\n\t\t}\r\n\t\treturn Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkRecursiveness(Metric m1, Metric m2);

} // Metric
