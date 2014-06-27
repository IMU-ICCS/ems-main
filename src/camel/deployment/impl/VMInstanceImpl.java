/**
 */
package camel.deployment.impl;

import camel.VMInfo;

import camel.deployment.DeploymentPackage;
import camel.deployment.Image;
import camel.deployment.VMInstance;

import java.lang.reflect.InvocationTargetException;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>VM Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.VMInstanceImpl#getPublicAddress <em>Public Address</em>}</li>
 *   <li>{@link camel.deployment.impl.VMInstanceImpl#getCreatedOn <em>Created On</em>}</li>
 *   <li>{@link camel.deployment.impl.VMInstanceImpl#getDestroyedOn <em>Destroyed On</em>}</li>
 *   <li>{@link camel.deployment.impl.VMInstanceImpl#getHasConfig <em>Has Config</em>}</li>
 *   <li>{@link camel.deployment.impl.VMInstanceImpl#getHasInfo <em>Has Info</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VMInstanceImpl extends ExternalComponentInstanceImpl implements VMInstance {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VMInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.VM_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPublicAddress() {
		return (String)eGet(DeploymentPackage.Literals.VM_INSTANCE__PUBLIC_ADDRESS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublicAddress(String newPublicAddress) {
		eSet(DeploymentPackage.Literals.VM_INSTANCE__PUBLIC_ADDRESS, newPublicAddress);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getCreatedOn() {
		return (Date)eGet(DeploymentPackage.Literals.VM_INSTANCE__CREATED_ON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreatedOn(Date newCreatedOn) {
		eSet(DeploymentPackage.Literals.VM_INSTANCE__CREATED_ON, newCreatedOn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDestroyedOn() {
		return (Date)eGet(DeploymentPackage.Literals.VM_INSTANCE__DESTROYED_ON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestroyedOn(Date newDestroyedOn) {
		eSet(DeploymentPackage.Literals.VM_INSTANCE__DESTROYED_ON, newDestroyedOn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Image getHasConfig() {
		return (Image)eGet(DeploymentPackage.Literals.VM_INSTANCE__HAS_CONFIG, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasConfig(Image newHasConfig) {
		eSet(DeploymentPackage.Literals.VM_INSTANCE__HAS_CONFIG, newHasConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMInfo getHasInfo() {
		return (VMInfo)eGet(DeploymentPackage.Literals.VM_INSTANCE__HAS_INFO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasInfo(VMInfo newHasInfo) {
		eSet(DeploymentPackage.Literals.VM_INSTANCE__HAS_INFO, newHasInfo);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDates(final VMInstance vm) {
		System.out.println("Checking dates for VMInstance: " + vm);
				Date createdOn = vm.getCreatedOn();
				Date destroyedOn = vm.getDestroyedOn();
				if (createdOn != null && destroyedOn != null && destroyedOn.before(createdOn)) return Boolean.FALSE;
				return Boolean.TRUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case DeploymentPackage.VM_INSTANCE___CHECK_DATES__VMINSTANCE:
				return checkDates((VMInstance)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //VMInstanceImpl
