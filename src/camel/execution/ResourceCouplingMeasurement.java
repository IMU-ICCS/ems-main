/**
 */
package camel.execution;

import camel.deployment.VMInstance;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Coupling Measurement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.execution.ResourceCouplingMeasurement#getSourceVMInstance <em>Source VM Instance</em>}</li>
 *   <li>{@link camel.execution.ResourceCouplingMeasurement#getDestinationVMInstance <em>Destination VM Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.execution.ExecutionPackage#getResourceCouplingMeasurement()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='RCMeasurement_Diff_VM_Instances'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot RCMeasurement_Diff_VM_Instances='\n\t\t\t\t\t\t\t\tsourceVMInstance <> destinationVMInstance and inExecutionContext.involvesDeployment.componentInstances->includes(sourceVMInstance) and inExecutionContext.involvesDeployment.componentInstances->includes(destinationVMInstance)'"
 * @generated
 */
public interface ResourceCouplingMeasurement extends Measurement {
	/**
	 * Returns the value of the '<em><b>Source VM Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source VM Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source VM Instance</em>' reference.
	 * @see #setSourceVMInstance(VMInstance)
	 * @see camel.execution.ExecutionPackage#getResourceCouplingMeasurement_SourceVMInstance()
	 * @model required="true"
	 * @generated
	 */
	VMInstance getSourceVMInstance();

	/**
	 * Sets the value of the '{@link camel.execution.ResourceCouplingMeasurement#getSourceVMInstance <em>Source VM Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source VM Instance</em>' reference.
	 * @see #getSourceVMInstance()
	 * @generated
	 */
	void setSourceVMInstance(VMInstance value);

	/**
	 * Returns the value of the '<em><b>Destination VM Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination VM Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination VM Instance</em>' reference.
	 * @see #setDestinationVMInstance(VMInstance)
	 * @see camel.execution.ExecutionPackage#getResourceCouplingMeasurement_DestinationVMInstance()
	 * @model required="true"
	 * @generated
	 */
	VMInstance getDestinationVMInstance();

	/**
	 * Sets the value of the '{@link camel.execution.ResourceCouplingMeasurement#getDestinationVMInstance <em>Destination VM Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination VM Instance</em>' reference.
	 * @see #getDestinationVMInstance()
	 * @generated
	 */
	void setDestinationVMInstance(VMInstance value);

} // ResourceCouplingMeasurement
