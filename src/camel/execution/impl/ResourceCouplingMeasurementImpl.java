/**
 */
package camel.execution.impl;

import camel.deployment.VMInstance;

import camel.execution.ExecutionPackage;
import camel.execution.ResourceCouplingMeasurement;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Coupling Measurement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.execution.impl.ResourceCouplingMeasurementImpl#getSourceVMInstance <em>Source VM Instance</em>}</li>
 *   <li>{@link camel.execution.impl.ResourceCouplingMeasurementImpl#getDestinationVMInstance <em>Destination VM Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceCouplingMeasurementImpl extends MeasurementImpl implements ResourceCouplingMeasurement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceCouplingMeasurementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionPackage.Literals.RESOURCE_COUPLING_MEASUREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMInstance getSourceVMInstance() {
		return (VMInstance)eGet(ExecutionPackage.Literals.RESOURCE_COUPLING_MEASUREMENT__SOURCE_VM_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceVMInstance(VMInstance newSourceVMInstance) {
		eSet(ExecutionPackage.Literals.RESOURCE_COUPLING_MEASUREMENT__SOURCE_VM_INSTANCE, newSourceVMInstance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMInstance getDestinationVMInstance() {
		return (VMInstance)eGet(ExecutionPackage.Literals.RESOURCE_COUPLING_MEASUREMENT__DESTINATION_VM_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinationVMInstance(VMInstance newDestinationVMInstance) {
		eSet(ExecutionPackage.Literals.RESOURCE_COUPLING_MEASUREMENT__DESTINATION_VM_INSTANCE, newDestinationVMInstance);
	}

} //ResourceCouplingMeasurementImpl
