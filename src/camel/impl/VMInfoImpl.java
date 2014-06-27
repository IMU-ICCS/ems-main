/**
 */
package camel.impl;

import camel.CamelPackage;
import camel.MonetaryUnit;
import camel.VMInfo;
import camel.VMType;

import camel.organisation.DataCenter;

import java.util.Date;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>VM Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.impl.VMInfoImpl#getOfVM <em>Of VM</em>}</li>
 *   <li>{@link camel.impl.VMInfoImpl#getDataCenter <em>Data Center</em>}</li>
 *   <li>{@link camel.impl.VMInfoImpl#getCostPerHour <em>Cost Per Hour</em>}</li>
 *   <li>{@link camel.impl.VMInfoImpl#getCostUnit <em>Cost Unit</em>}</li>
 *   <li>{@link camel.impl.VMInfoImpl#getBenchmarkRate <em>Benchmark Rate</em>}</li>
 *   <li>{@link camel.impl.VMInfoImpl#getEvaluatedOn <em>Evaluated On</em>}</li>
 *   <li>{@link camel.impl.VMInfoImpl#getClassifiedOn <em>Classified On</em>}</li>
 *   <li>{@link camel.impl.VMInfoImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VMInfoImpl extends CDOObjectImpl implements VMInfo {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VMInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CamelPackage.Literals.VM_INFO;
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
	public VMType getOfVM() {
		return (VMType)eGet(CamelPackage.Literals.VM_INFO__OF_VM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfVM(VMType newOfVM) {
		eSet(CamelPackage.Literals.VM_INFO__OF_VM, newOfVM);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataCenter getDataCenter() {
		return (DataCenter)eGet(CamelPackage.Literals.VM_INFO__DATA_CENTER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataCenter(DataCenter newDataCenter) {
		eSet(CamelPackage.Literals.VM_INFO__DATA_CENTER, newDataCenter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getCostPerHour() {
		return (Double)eGet(CamelPackage.Literals.VM_INFO__COST_PER_HOUR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCostPerHour(double newCostPerHour) {
		eSet(CamelPackage.Literals.VM_INFO__COST_PER_HOUR, newCostPerHour);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MonetaryUnit getCostUnit() {
		return (MonetaryUnit)eGet(CamelPackage.Literals.VM_INFO__COST_UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCostUnit(MonetaryUnit newCostUnit) {
		eSet(CamelPackage.Literals.VM_INFO__COST_UNIT, newCostUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getBenchmarkRate() {
		return (Double)eGet(CamelPackage.Literals.VM_INFO__BENCHMARK_RATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBenchmarkRate(double newBenchmarkRate) {
		eSet(CamelPackage.Literals.VM_INFO__BENCHMARK_RATE, newBenchmarkRate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getEvaluatedOn() {
		return (Date)eGet(CamelPackage.Literals.VM_INFO__EVALUATED_ON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvaluatedOn(Date newEvaluatedOn) {
		eSet(CamelPackage.Literals.VM_INFO__EVALUATED_ON, newEvaluatedOn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getClassifiedOn() {
		return (Date)eGet(CamelPackage.Literals.VM_INFO__CLASSIFIED_ON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClassifiedOn(Date newClassifiedOn) {
		eSet(CamelPackage.Literals.VM_INFO__CLASSIFIED_ON, newClassifiedOn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(CamelPackage.Literals.VM_INFO__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(CamelPackage.Literals.VM_INFO__NAME, newName);
	}

} //VMInfoImpl
