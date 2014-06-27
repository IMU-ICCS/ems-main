/**
 */
package camel.scalability.impl;

import camel.execution.ExecutionContext;

import camel.scalability.MetricObjectInstanceBinding;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric Object Instance Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.MetricObjectInstanceBindingImpl#getExecutionContext <em>Execution Context</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class MetricObjectInstanceBindingImpl extends CDOObjectImpl implements MetricObjectInstanceBinding {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricObjectInstanceBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.METRIC_OBJECT_INSTANCE_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionContext getExecutionContext() {
		return (ExecutionContext)eGet(ScalabilityPackage.Literals.METRIC_OBJECT_INSTANCE_BINDING__EXECUTION_CONTEXT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecutionContext(ExecutionContext newExecutionContext) {
		eSet(ScalabilityPackage.Literals.METRIC_OBJECT_INSTANCE_BINDING__EXECUTION_CONTEXT, newExecutionContext);
	}

} //MetricObjectInstanceBindingImpl
