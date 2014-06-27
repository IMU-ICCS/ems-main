/**
 */
package camel.scalability;

import camel.Unit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.MetricTemplate#getName <em>Name</em>}</li>
 *   <li>{@link camel.scalability.MetricTemplate#getDescription <em>Description</em>}</li>
 *   <li>{@link camel.scalability.MetricTemplate#getValueDirection <em>Value Direction</em>}</li>
 *   <li>{@link camel.scalability.MetricTemplate#getUnit <em>Unit</em>}</li>
 *   <li>{@link camel.scalability.MetricTemplate#getLayer <em>Layer</em>}</li>
 *   <li>{@link camel.scalability.MetricTemplate#getMeasures <em>Measures</em>}</li>
 *   <li>{@link camel.scalability.MetricTemplate#getType <em>Type</em>}</li>
 *   <li>{@link camel.scalability.MetricTemplate#getFormula <em>Formula</em>}</li>
 *   <li>{@link camel.scalability.MetricTemplate#getObjectBinding <em>Object Binding</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetricTemplate()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='SingleMetric_No_Formula Measurable_Property MetricTemplate_layer_enforcement MetricTemplate_PERC_Unit_Enforcement MetricTemplate_Composite_Unit_Enforcement metric_template_components_refer_to_same_level_or_lower'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot SingleMetric_No_Formula='\n\t\t\t\t\tself.type = MetricType::RAW implies self.formula = null' Measurable_Property='\n\t\t\t\t\tself.measures.type = PropertyType::MEASURABLE' MetricTemplate_layer_enforcement='\n\t\t\t\t\tself.type = MetricType::RAW or (self.type = MetricType::COMPOSITE and self.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies self.greaterEqualThanLayer(self.layer, p.oclAsType(MetricTemplate).layer)))' MetricTemplate_PERC_Unit_Enforcement='\n\t\t\t\t\tif (self.unit.unit = camel::UnitType::PERCENTAGE and self.type = MetricType::COMPOSITE) then (self.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies p.oclAsType(MetricTemplate).unit.unit = camel::UnitType::PERCENTAGE) or (self.formula.function = MetricFunctionType::DIV and self.formula.parameters->forAll(p1, p2 | (p1.oclIsTypeOf(MetricTemplate) and p2.oclIsTypeOf(MetricTemplate)) implies p1.oclAsType(MetricTemplate).unit.unit = p2.oclAsType(MetricTemplate).unit.unit))) else true endif' MetricTemplate_Composite_Unit_Enforcement='\n\t\t\t\t\tif (self.type = MetricType::COMPOSITE and self.formula.function = MetricFunctionType::DIV) then (\n\t\t\t\t\t\tif (self.unit.unit = camel::UnitType::BYTES_PER_SECOND) then (self.formula.parameters->at(1).oclAsType(MetricTemplate).unit.unit = camel::UnitType::BYTES and self.formula.parameters->at(2).oclAsType(MetricTemplate).unit.unit = camel::UnitType::SECONDS)\n\t\t\t\t\t\telse ( \n\t\t\t\t\t\t\tif (self.unit.unit = camel::UnitType::REQUESTS_PER_SECOND) then (self.formula.parameters->at(1).oclAsType(MetricTemplate).unit.unit = camel::UnitType::REQUESTS and self.formula.parameters->at(2).oclAsType(MetricTemplate).unit.unit = camel::UnitType::SECONDS) else true endif\n\t\t\t\t\t\t)\n\t\t\t\t\t\tendif ) \n\t\t\t\t\telse true endif' metric_template_components_refer_to_same_level_or_lower='\n\t\t\t\t\t(objectBinding <> null and self.type = MetricType::COMPOSITE) implies  \n\t\t\t\t\t(if (objectBinding.oclIsTypeOf(MetricApplicationBinding)) then \n\t\t\t\t\t\tself.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies (p.oclAsType(MetricTemplate).objectBinding <> null and p.oclAsType(MetricTemplate).objectBinding.application = objectBinding.application)) \n\t\t\t\t\telse \n\t\t\t\t\t\tif (objectBinding.oclIsTypeOf(MetricComponentBinding)) then self.formula.parameters->forAll(p | \n\t\t\t\t\t\t\tp.oclIsTypeOf(MetricTemplate) implies (p.oclAsType(MetricTemplate).objectBinding <> null and p.oclAsType(MetricTemplate).objectBinding.application = self.objectBinding.application) \n\t\t\t\t\t\t\tand not(p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricApplicationBinding)) and\n\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::InternalComponent)) then\n\t\t\t\t\t\t\t\tif (p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricVMBinding)) then self.objectBinding.application.deploymentModels->exists(q | q.hostings->exists(t | t.providedHost.component=p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricVMBinding).vm and t.requiredHost.component=self.objectBinding.oclAsType(MetricComponentBinding).component))\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricComponentBinding) and p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then self.objectBinding.application.deploymentModels->exists(t | t.hostings->exists(q | q.providedHost.component=p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricComponentBinding).component and q.requiredHost.component=self.objectBinding.oclAsType(MetricComponentBinding).component)\n\t\t\t\t\t\t\t\t\t)\n\t\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\tif (self.objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then \n\t\t\t\t\t\t\t\t\tif (p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricComponentBinding) and p.oclAsType(MetricTemplate).objectBinding.oclAsType(MetricComponentBinding).component.oclIsTypeOf(camel::deployment::ExternalComponent)) then true\n\t\t\t\t\t\t\t\t\telse false endif\n\t\t\t\t\t\t\t\telse false endif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t)\n\t\t\t\t\t\telse self.formula.parameters->forAll(p | p.oclIsTypeOf(MetricTemplate) implies (p.oclAsType(MetricTemplate).objectBinding <> null and p.oclAsType(MetricTemplate).objectBinding.application = self.objectBinding.application) and p.oclAsType(MetricTemplate).objectBinding.oclIsTypeOf(MetricVMBinding))\n\t\t\t\t\t\tendif\n\t\t\t\t\tendif)'"
 * @generated
 */
public interface MetricTemplate extends MetricFormulaParameter {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see camel.scalability.ScalabilityPackage#getMetricTemplate_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricTemplate#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see camel.scalability.ScalabilityPackage#getMetricTemplate_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricTemplate#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Value Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Direction</em>' attribute.
	 * @see #setValueDirection(short)
	 * @see camel.scalability.ScalabilityPackage#getMetricTemplate_ValueDirection()
	 * @model required="true"
	 * @generated
	 */
	short getValueDirection();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricTemplate#getValueDirection <em>Value Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Direction</em>' attribute.
	 * @see #getValueDirection()
	 * @generated
	 */
	void setValueDirection(short value);

	/**
	 * Returns the value of the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' reference.
	 * @see #setUnit(Unit)
	 * @see camel.scalability.ScalabilityPackage#getMetricTemplate_Unit()
	 * @model required="true"
	 * @generated
	 */
	Unit getUnit();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricTemplate#getUnit <em>Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' reference.
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(Unit value);

	/**
	 * Returns the value of the '<em><b>Layer</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.LayerType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Layer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Layer</em>' attribute.
	 * @see camel.scalability.LayerType
	 * @see #setLayer(LayerType)
	 * @see camel.scalability.ScalabilityPackage#getMetricTemplate_Layer()
	 * @model
	 * @generated
	 */
	LayerType getLayer();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricTemplate#getLayer <em>Layer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Layer</em>' attribute.
	 * @see camel.scalability.LayerType
	 * @see #getLayer()
	 * @generated
	 */
	void setLayer(LayerType value);

	/**
	 * Returns the value of the '<em><b>Measures</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Measures</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Measures</em>' reference.
	 * @see #setMeasures(Property)
	 * @see camel.scalability.ScalabilityPackage#getMetricTemplate_Measures()
	 * @model required="true"
	 * @generated
	 */
	Property getMeasures();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricTemplate#getMeasures <em>Measures</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Measures</em>' reference.
	 * @see #getMeasures()
	 * @generated
	 */
	void setMeasures(Property value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.MetricType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see camel.scalability.MetricType
	 * @see #setType(MetricType)
	 * @see camel.scalability.ScalabilityPackage#getMetricTemplate_Type()
	 * @model required="true"
	 * @generated
	 */
	MetricType getType();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricTemplate#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see camel.scalability.MetricType
	 * @see #getType()
	 * @generated
	 */
	void setType(MetricType value);

	/**
	 * Returns the value of the '<em><b>Formula</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Formula</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formula</em>' reference.
	 * @see #setFormula(MetricFormula)
	 * @see camel.scalability.ScalabilityPackage#getMetricTemplate_Formula()
	 * @model
	 * @generated
	 */
	MetricFormula getFormula();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricTemplate#getFormula <em>Formula</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Formula</em>' reference.
	 * @see #getFormula()
	 * @generated
	 */
	void setFormula(MetricFormula value);

	/**
	 * Returns the value of the '<em><b>Object Binding</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Binding</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Binding</em>' reference.
	 * @see #setObjectBinding(MetricObjectBinding)
	 * @see camel.scalability.ScalabilityPackage#getMetricTemplate_ObjectBinding()
	 * @model
	 * @generated
	 */
	MetricObjectBinding getObjectBinding();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricTemplate#getObjectBinding <em>Object Binding</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Binding</em>' reference.
	 * @see #getObjectBinding()
	 * @generated
	 */
	void setObjectBinding(MetricObjectBinding value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"Checking recursiveness for MetricTemplate: \" + mt1.getName());\r\n\t\tfor (camel.scalability.MetricFormulaParameter param: mt1.getFormula().getParameters()){\r\n\t\t\tif (param instanceof MetricTemplate){\r\n\t\t\t\tMetricTemplate mt = (MetricTemplate)param;\r\n\t\t\t\tif (mt.getName().equals(mt2.getName())) return Boolean.TRUE;\r\n\t\t\t\tif (mt.getType()== MetricType.COMPOSITE && checkRecursiveness(mt,mt2)) return Boolean.TRUE;\r\n\t\t\t}\r\n\t\t}\r\n\t\treturn Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkRecursiveness(MetricTemplate mt1, MetricTemplate mt2);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model l1Required="true" l2Required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='if (l1 = LayerType::SaaS) then true \n\t\t\t\t\t\telse\n\t\t\t\t\t\t\t(if (l1 = LayerType::PaaS) then \n\t\t\t\t\t\t\t\t(if (l2 = LayerType::PaaS or l2 = LayerType::IaaS) then true else false endif)\n\t\t\t\t\t\t\t\telse (if (l2 = LayerType::IaaS) then true else false endif)\n\t\t\t\t\t\t\t\tendif)\n\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean greaterEqualThanLayer(LayerType l1, LayerType l2);

} // MetricTemplate
