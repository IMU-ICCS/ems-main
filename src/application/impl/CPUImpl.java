/**
 */
package application.impl;

import application.ApplicationPackage;
import application.CPU;

import org.eclipse.emf.ecore.EClass;

import types.typesPaasage.FrequencyEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CPU</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.CPUImpl#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link application.impl.CPUImpl#getCores <em>Cores</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CPUImpl extends ResourceImpl implements CPU {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CPUImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.CPU;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FrequencyEnum getFrequency() {
		return (FrequencyEnum)eGet(ApplicationPackage.Literals.CPU__FREQUENCY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrequency(FrequencyEnum newFrequency) {
		eSet(ApplicationPackage.Literals.CPU__FREQUENCY, newFrequency);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCores() {
		return (Integer)eGet(ApplicationPackage.Literals.CPU__CORES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCores(int newCores) {
		eSet(ApplicationPackage.Literals.CPU__CORES, newCores);
	}

} //CPUImpl
