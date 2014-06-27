/**
 */
package camel.execution.impl;

import camel.DataObject;
import camel.PhysicalNode;

import camel.deployment.VMInstance;

import camel.execution.ExecutionPackage;
import camel.execution.ResourceMeasurement;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Measurement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.execution.impl.ResourceMeasurementImpl#getOfVMInstance <em>Of VM Instance</em>}</li>
 *   <li>{@link camel.execution.impl.ResourceMeasurementImpl#getResourceClass <em>Resource Class</em>}</li>
 *   <li>{@link camel.execution.impl.ResourceMeasurementImpl#getPhysicalNode <em>Physical Node</em>}</li>
 *   <li>{@link camel.execution.impl.ResourceMeasurementImpl#getDataObject <em>Data Object</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceMeasurementImpl extends MeasurementImpl implements ResourceMeasurement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceMeasurementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionPackage.Literals.RESOURCE_MEASUREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMInstance getOfVMInstance() {
		return (VMInstance)eGet(ExecutionPackage.Literals.RESOURCE_MEASUREMENT__OF_VM_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfVMInstance(VMInstance newOfVMInstance) {
		eSet(ExecutionPackage.Literals.RESOURCE_MEASUREMENT__OF_VM_INSTANCE, newOfVMInstance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResourceClass() {
		return (EClass)eGet(ExecutionPackage.Literals.RESOURCE_MEASUREMENT__RESOURCE_CLASS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceClass(EClass newResourceClass) {
		eSet(ExecutionPackage.Literals.RESOURCE_MEASUREMENT__RESOURCE_CLASS, newResourceClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhysicalNode getPhysicalNode() {
		return (PhysicalNode)eGet(ExecutionPackage.Literals.RESOURCE_MEASUREMENT__PHYSICAL_NODE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPhysicalNode(PhysicalNode newPhysicalNode) {
		eSet(ExecutionPackage.Literals.RESOURCE_MEASUREMENT__PHYSICAL_NODE, newPhysicalNode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataObject getDataObject() {
		return (DataObject)eGet(ExecutionPackage.Literals.RESOURCE_MEASUREMENT__DATA_OBJECT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataObject(DataObject newDataObject) {
		eSet(ExecutionPackage.Literals.RESOURCE_MEASUREMENT__DATA_OBJECT, newDataObject);
	}

} //ResourceMeasurementImpl
