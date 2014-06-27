/**
 */
package camel.scalability.impl;

import camel.scalability.MetricFormula;
import camel.scalability.MetricFormulaParameter;
import camel.scalability.MetricFunctionArityType;
import camel.scalability.MetricFunctionType;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric Formula</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.MetricFormulaImpl#getFunction <em>Function</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricFormulaImpl#getFunctionArity <em>Function Arity</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricFormulaImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetricFormulaImpl extends MetricFormulaParameterImpl implements MetricFormula {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricFormulaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.METRIC_FORMULA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricFunctionType getFunction() {
		return (MetricFunctionType)eGet(ScalabilityPackage.Literals.METRIC_FORMULA__FUNCTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunction(MetricFunctionType newFunction) {
		eSet(ScalabilityPackage.Literals.METRIC_FORMULA__FUNCTION, newFunction);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricFunctionArityType getFunctionArity() {
		return (MetricFunctionArityType)eGet(ScalabilityPackage.Literals.METRIC_FORMULA__FUNCTION_ARITY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionArity(MetricFunctionArityType newFunctionArity) {
		eSet(ScalabilityPackage.Literals.METRIC_FORMULA__FUNCTION_ARITY, newFunctionArity);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<MetricFormulaParameter> getParameters() {
		return (EList<MetricFormulaParameter>)eGet(ScalabilityPackage.Literals.METRIC_FORMULA__PARAMETERS, true);
	}

} //MetricFormulaImpl
