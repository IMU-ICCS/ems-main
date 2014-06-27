/**
 */
package camel.execution;

import camel.DataObject;
import camel.PhysicalNode;

import camel.deployment.VMInstance;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Measurement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.execution.ResourceMeasurement#getOfVMInstance <em>Of VM Instance</em>}</li>
 *   <li>{@link camel.execution.ResourceMeasurement#getResourceClass <em>Resource Class</em>}</li>
 *   <li>{@link camel.execution.ResourceMeasurement#getPhysicalNode <em>Physical Node</em>}</li>
 *   <li>{@link camel.execution.ResourceMeasurement#getDataObject <em>Data Object</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.execution.ExecutionPackage#getResourceMeasurement()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='RM_At_Least_One_Not_Null RM_DataObject_Alone RM_EC_ClouMLModel_VM_INSTANCE RM_Metric_VM_Instance RM_DataObject_ec'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot RM_At_Least_One_Not_Null='\n\t\t\t\t\t\t\t\tofVMInstance <> null or physicalNode <> null or dataObject <> null' RM_DataObject_Alone='\n\t\t\t\t\t\t\t\tdataObject <> null implies (ofVMInstance = null and physicalNode = null)' RM_EC_ClouMLModel_VM_INSTANCE='\n\t\t\t\t\t\t\t\tofVMInstance <> null implies inExecutionContext.involvesDeployment.componentInstances->includes(ofVMInstance)' RM_Metric_VM_Instance='\n\t\t\t\t\t\t\t\tofVMInstance <> null implies (ofMetric.objectBinding.oclIsTypeOf(camel::scalability::MetricVMInstanceBinding) and ofMetric.objectBinding.oclAsType(camel::scalability::MetricVMInstanceBinding).vmInstance = ofVMInstance)' RM_DataObject_ec='\n\t\t\t\t\t\t\t\tdataObject <> null implies inExecutionContext.involvesDeployment.communications->exists(p | p.dataObject = dataObject)'"
 * @generated
 */
public interface ResourceMeasurement extends Measurement {
	/**
	 * Returns the value of the '<em><b>Of VM Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Of VM Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Of VM Instance</em>' reference.
	 * @see #setOfVMInstance(VMInstance)
	 * @see camel.execution.ExecutionPackage#getResourceMeasurement_OfVMInstance()
	 * @model
	 * @generated
	 */
	VMInstance getOfVMInstance();

	/**
	 * Sets the value of the '{@link camel.execution.ResourceMeasurement#getOfVMInstance <em>Of VM Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Of VM Instance</em>' reference.
	 * @see #getOfVMInstance()
	 * @generated
	 */
	void setOfVMInstance(VMInstance value);

	/**
	 * Returns the value of the '<em><b>Resource Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Class</em>' reference.
	 * @see #setResourceClass(EClass)
	 * @see camel.execution.ExecutionPackage#getResourceMeasurement_ResourceClass()
	 * @model
	 * @generated
	 */
	EClass getResourceClass();

	/**
	 * Sets the value of the '{@link camel.execution.ResourceMeasurement#getResourceClass <em>Resource Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Class</em>' reference.
	 * @see #getResourceClass()
	 * @generated
	 */
	void setResourceClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Physical Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Physical Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Physical Node</em>' reference.
	 * @see #setPhysicalNode(PhysicalNode)
	 * @see camel.execution.ExecutionPackage#getResourceMeasurement_PhysicalNode()
	 * @model
	 * @generated
	 */
	PhysicalNode getPhysicalNode();

	/**
	 * Sets the value of the '{@link camel.execution.ResourceMeasurement#getPhysicalNode <em>Physical Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Physical Node</em>' reference.
	 * @see #getPhysicalNode()
	 * @generated
	 */
	void setPhysicalNode(PhysicalNode value);

	/**
	 * Returns the value of the '<em><b>Data Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Object</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Object</em>' reference.
	 * @see #setDataObject(DataObject)
	 * @see camel.execution.ExecutionPackage#getResourceMeasurement_DataObject()
	 * @model
	 * @generated
	 */
	DataObject getDataObject();

	/**
	 * Sets the value of the '{@link camel.execution.ResourceMeasurement#getDataObject <em>Data Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Object</em>' reference.
	 * @see #getDataObject()
	 * @generated
	 */
	void setDataObject(DataObject value);

} // ResourceMeasurement
