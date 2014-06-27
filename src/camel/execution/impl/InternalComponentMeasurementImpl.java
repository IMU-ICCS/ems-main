/**
 */
package camel.execution.impl;

import camel.deployment.ComponentInstance;

import camel.execution.ExecutionPackage;
import camel.execution.InternalComponentMeasurement;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Internal Component Measurement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.execution.impl.InternalComponentMeasurementImpl#getOfComponentInstance <em>Of Component Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InternalComponentMeasurementImpl extends MeasurementImpl implements InternalComponentMeasurement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InternalComponentMeasurementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionPackage.Literals.INTERNAL_COMPONENT_MEASUREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentInstance getOfComponentInstance() {
		return (ComponentInstance)eGet(ExecutionPackage.Literals.INTERNAL_COMPONENT_MEASUREMENT__OF_COMPONENT_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfComponentInstance(ComponentInstance newOfComponentInstance) {
		eSet(ExecutionPackage.Literals.INTERNAL_COMPONENT_MEASUREMENT__OF_COMPONENT_INSTANCE, newOfComponentInstance);
	}

} //InternalComponentMeasurementImpl
