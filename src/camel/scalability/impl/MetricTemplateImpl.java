/**
 */
package camel.scalability.impl;

import camel.Unit;

import camel.scalability.LayerType;
import camel.scalability.MetricFormula;
import camel.scalability.MetricObjectBinding;
import camel.scalability.MetricTemplate;
import camel.scalability.MetricType;
import camel.scalability.Property;
import camel.scalability.ScalabilityPackage;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.MetricTemplateImpl#getName <em>Name</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricTemplateImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricTemplateImpl#getValueDirection <em>Value Direction</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricTemplateImpl#getUnit <em>Unit</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricTemplateImpl#getLayer <em>Layer</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricTemplateImpl#getMeasures <em>Measures</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricTemplateImpl#getType <em>Type</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricTemplateImpl#getFormula <em>Formula</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricTemplateImpl#getObjectBinding <em>Object Binding</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetricTemplateImpl extends MetricFormulaParameterImpl implements MetricTemplate {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.METRIC_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(ScalabilityPackage.Literals.METRIC_TEMPLATE__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(ScalabilityPackage.Literals.METRIC_TEMPLATE__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eGet(ScalabilityPackage.Literals.METRIC_TEMPLATE__DESCRIPTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eSet(ScalabilityPackage.Literals.METRIC_TEMPLATE__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getValueDirection() {
		return (Short)eGet(ScalabilityPackage.Literals.METRIC_TEMPLATE__VALUE_DIRECTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueDirection(short newValueDirection) {
		eSet(ScalabilityPackage.Literals.METRIC_TEMPLATE__VALUE_DIRECTION, newValueDirection);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Unit getUnit() {
		return (Unit)eGet(ScalabilityPackage.Literals.METRIC_TEMPLATE__UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnit(Unit newUnit) {
		eSet(ScalabilityPackage.Literals.METRIC_TEMPLATE__UNIT, newUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LayerType getLayer() {
		return (LayerType)eGet(ScalabilityPackage.Literals.METRIC_TEMPLATE__LAYER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLayer(LayerType newLayer) {
		eSet(ScalabilityPackage.Literals.METRIC_TEMPLATE__LAYER, newLayer);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property getMeasures() {
		return (Property)eGet(ScalabilityPackage.Literals.METRIC_TEMPLATE__MEASURES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMeasures(Property newMeasures) {
		eSet(ScalabilityPackage.Literals.METRIC_TEMPLATE__MEASURES, newMeasures);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricType getType() {
		return (MetricType)eGet(ScalabilityPackage.Literals.METRIC_TEMPLATE__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(MetricType newType) {
		eSet(ScalabilityPackage.Literals.METRIC_TEMPLATE__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricFormula getFormula() {
		return (MetricFormula)eGet(ScalabilityPackage.Literals.METRIC_TEMPLATE__FORMULA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormula(MetricFormula newFormula) {
		eSet(ScalabilityPackage.Literals.METRIC_TEMPLATE__FORMULA, newFormula);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricObjectBinding getObjectBinding() {
		return (MetricObjectBinding)eGet(ScalabilityPackage.Literals.METRIC_TEMPLATE__OBJECT_BINDING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectBinding(MetricObjectBinding newObjectBinding) {
		eSet(ScalabilityPackage.Literals.METRIC_TEMPLATE__OBJECT_BINDING, newObjectBinding);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkRecursiveness(final MetricTemplate mt1, final MetricTemplate mt2) {
		System.out.println("Checking recursiveness for MetricTemplate: " + mt1.getName());
				for (camel.scalability.MetricFormulaParameter param: mt1.getFormula().getParameters()){
					if (param instanceof MetricTemplate){
						MetricTemplate mt = (MetricTemplate)param;
						if (mt.getName().equals(mt2.getName())) return Boolean.TRUE;
						if (mt.getType()== MetricType.COMPOSITE && checkRecursiveness(mt,mt2)) return Boolean.TRUE;
					}
				}
				return Boolean.FALSE;
	}

	/**
	 * The cached invocation delegate for the '{@link #greaterEqualThanLayer(camel.scalability.LayerType, camel.scalability.LayerType) <em>Greater Equal Than Layer</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #greaterEqualThanLayer(camel.scalability.LayerType, camel.scalability.LayerType)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate GREATER_EQUAL_THAN_LAYER_LAYER_TYPE_LAYER_TYPE__EINVOCATION_DELEGATE = ((EOperation.Internal)ScalabilityPackage.Literals.METRIC_TEMPLATE___GREATER_EQUAL_THAN_LAYER__LAYERTYPE_LAYERTYPE).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean greaterEqualThanLayer(LayerType l1, LayerType l2) {
		try {
			return (Boolean)GREATER_EQUAL_THAN_LAYER_LAYER_TYPE_LAYER_TYPE__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(2, new Object[]{l1, l2}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ScalabilityPackage.METRIC_TEMPLATE___CHECK_RECURSIVENESS__METRICTEMPLATE_METRICTEMPLATE:
				return checkRecursiveness((MetricTemplate)arguments.get(0), (MetricTemplate)arguments.get(1));
			case ScalabilityPackage.METRIC_TEMPLATE___GREATER_EQUAL_THAN_LAYER__LAYERTYPE_LAYERTYPE:
				return greaterEqualThanLayer((LayerType)arguments.get(0), (LayerType)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
	}

} //MetricTemplateImpl
