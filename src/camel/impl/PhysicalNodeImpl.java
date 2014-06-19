/**
 */
package camel.impl;

import camel.CamelPackage;
import camel.PhysicalNode;

import camel.cerif.DataCenter;

import camel.cerif.impl.ResourceImpl;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Physical Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.impl.PhysicalNodeImpl#getIP <em>IP</em>}</li>
 *   <li>{@link camel.impl.PhysicalNodeImpl#getHardware <em>Hardware</em>}</li>
 *   <li>{@link camel.impl.PhysicalNodeImpl#getInDataCenter <em>In Data Center</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PhysicalNodeImpl extends ResourceImpl implements PhysicalNode {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PhysicalNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CamelPackage.Literals.PHYSICAL_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIP() {
		return (String)eGet(CamelPackage.Literals.PHYSICAL_NODE__IP, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIP(String newIP) {
		eSet(CamelPackage.Literals.PHYSICAL_NODE__IP, newIP);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHardware() {
		return (String)eGet(CamelPackage.Literals.PHYSICAL_NODE__HARDWARE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHardware(String newHardware) {
		eSet(CamelPackage.Literals.PHYSICAL_NODE__HARDWARE, newHardware);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataCenter getInDataCenter() {
		return (DataCenter)eGet(CamelPackage.Literals.PHYSICAL_NODE__IN_DATA_CENTER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInDataCenter(DataCenter newInDataCenter) {
		eSet(CamelPackage.Literals.PHYSICAL_NODE__IN_DATA_CENTER, newInDataCenter);
	}

} //PhysicalNodeImpl
